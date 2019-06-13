package CapaAplicacio;

import javax.ws.rs.*;
import java.util.List;
import org.json.JSONObject;
import com.lambdaworks.crypto.SCryptUtil;
import CapaDomini.*;
import CapaPersistencia.*;

//http://127.0.0.1/JocDames/ServerJocDames/metode?param&param...
@ApplicationPath("/ServerJocDames/")

public class ServerJocDames implements JocDamesInterficie {
	



	private ConnectionSQLOracle connSQL;
	private UsuariSQLOracle userSQL;
	private PartidesSQLOracle partSQL;
	private EstadistiquesSQLOracle statSQL;
	private Moviments movTornAct;
	private JSONObject json;
	private String contrincant;

	public ServerJocDames() throws Exception {
		connSQL = new ConnectionSQLOracle();
		userSQL = new UsuariSQLOracle(connSQL);
		partSQL = new PartidesSQLOracle(connSQL);
		statSQL = new EstadistiquesSQLOracle(connSQL);
		json = new JSONObject();
		movTornAct = null;
		contrincant = "";
	}

	/**
	 * Comprova les dades de l'usuari passat per parametres, i fa login si son
	 * correctes
	 * 
	 * @param user     nom de l'usuari
	 * @param password contrasenya de l'usuari, SENSE hashing
	 * @return "res": "IdSessio", "err": "usuari o contrasenya incorrecte", "sErr":
	 *         "";
	 */
	@GET
	@Path("login")
	@Produces("application/json")
	public String login(@QueryParam("user") String user, @QueryParam("password") String password)  {

		boolean jaConnectat = this.userSQL.getConnectat(user);
		if (jaConnectat)
			return crearJSON("", "Usuari amb sessió oberta", "");

		/* Password checking */
		String BDPassword = this.userSQL.getPasword(user);
		if (BDPassword == null)
			return crearJSON("", "No User", "");

		boolean passwordMatch = SCryptUtil.check(password, BDPassword);
		if (!passwordMatch)
			return crearJSON("", "User-password incorrecte", "");

		boolean connexioCorrecte = this.userSQL.canviarSessio(user, true);
		if (!connexioCorrecte)
			return crearJSON("", "", "No s'ha pogut crear la sessio");

		return crearJSON(user, "", "");
	}

	@GET
	@Path("registra")
	@Produces("application/json")
	public String registra(@QueryParam("user")String user,@QueryParam("password") String password) {

		if (user.contains(";") || user.contains("\""))
			return crearJSON("", "El nom no pot contenir \" ni ;", "");

		boolean userExists = this.userSQL.getPasword(user) != null;
		if (userExists)
			return crearJSON("", "Usuari existent", "");

		String BDPassword = SCryptUtil.scrypt(password, 2, 2, 2);

		boolean creacioCorrecte = this.userSQL.insertUsuari(user, BDPassword, "-", "1");
		if (!creacioCorrecte)
			return crearJSON("", "Error al crear usuari", "");

		return crearJSON(user, "", "");
	}
	
	@GET
	@Path("logout")
	@Produces("application/json")
	public String logout(@QueryParam("idSessio")String idSessio) {

		boolean errorSessio = !userSQL.canviarSessio(idSessio, false);
		if (errorSessio) {
			return crearJSON("", "Error ID Sessió", "");
		}

		return crearJSON("", "", "");
	}
	
	@GET
	@Path("reconecta")
	@Produces("application/json")
	public String reconecta(@QueryParam("idSessio")String idSessio,@QueryParam("password") String password) {

		boolean sessioCaducada = !this.userSQL.getConnectat(idSessio); // Canviar pel necessari?
		if (sessioCaducada)
			return this.login(idSessio, password);
		else
			return crearJSON("", "La sessió encara està connectada", "");
	}

	@GET
	@Path("getEstadistics")
	@Produces("application/json")
	public String getEstadistics(@QueryParam("idSessio")String idSessio) {
		String res = this.statSQL.getEstadistiquesUsuari(idSessio);
		if (res == null)
			return crearJSON("", "SQL error no hi han dades suficients", "");
		else if (res.equals(""))
			return crearJSON("", "La base de dades ha retornat sense dades", "");
		else
			return crearJSON(res, "", "");
	}

	/**
	 * Retorna na llista d'usuaris separats per ;
	 * 
	 * @param idSessio
	 * @return
	 */
	@GET
	@Path("getCandidatsSol")
	@Produces("application/json")
	public String getCandidatsSol(@QueryParam("idSessio")String idSessio) {

		String res = this.userSQL.getCandidats(idSessio);
		if (res == null)
			return crearJSON("", "", "Hi ha hagut un problema de connexió.");

		if (res.isEmpty())
			return crearJSON("", "", "No hi han usuaris connectats.");

		return crearJSON(res, "", "");
	}

	// CreaPartida esta comprovat que funciona a la part de SQL
	@GET
	@Path("enviaSol")
	@Produces("application/json")
	public String enviaSol(@QueryParam("idSessio")String idSessio,@QueryParam("usuari") String usuari) {

		String res = "";
		res = this.partSQL.crearPartidaNova(idSessio, usuari);
		if (res == null)
			System.out.println("Hi ha hagut un problema de connexió.");
		else {
			if (res.equals(""))
				System.out.println("No sa pogut afegir la partida a la BBDD.");
			else
				System.out.println(res);
		}
	}

	@GET
	@Path("solicituds")
	@Produces("application/json")
	public String solicituds(@QueryParam("idSessio")String idSessio) {

		List<String> solicituds = this.partSQL.getSolicitudsPendents(idSessio);
		if (solicituds == null) {
			return crearJSON("", "", "No s'han pogut carregar les solicituds");
		}

		if (solicituds.isEmpty()) {
			return crearJSON("", "No hi ha cap partida", "");
		}
		String nomsUsuaris = "";
		for (String nom : solicituds) {
			nomsUsuaris += nom + ";";
		}

		nomsUsuaris = nomsUsuaris.substring(0, nomsUsuaris.length()); // Borrar ultim ;
		return crearJSON(nomsUsuaris, "", "");
	}

	@GET
	@Path("acceptaSol")
	@Produces("application/json")
	public String acceptaSol(@QueryParam("idSessio")String idSessio,@QueryParam("usuari") String usuari) {
		this.partSQL.acceptarSolicitud(idSessio, usuari);
	}

	@GET
	@Path("rebutjaSol")
	@Produces("application/json")
	public String rebutjaSol(@QueryParam("idSessio")String idSessio,@QueryParam("usuari") String usuari) {
		this.partSQL.rebutjaSolicitud(idSessio, usuari);
	}

	@GET
	@Path("getPartidesTorn")
	@Produces("application/json")
	public String getPartidesTorn(@QueryParam("idSessio")String idSessio) {

		String nomsUsuaris = "";
		List<String> partides = this.partSQL.getPartidesTorn(idSessio);
		if (partides == null)
			return crearJSON("", "", "Error amb el servidor");

		if (partides.isEmpty())
			return crearJSON("", "No hi ha cap partida", "");

		for (String nom : partides) {
			nomsUsuaris += nom + ";";
		}

		nomsUsuaris = nomsUsuaris.substring(0, nomsUsuaris.length());

		return crearJSON(nomsUsuaris, "", "");
	}

	@GET
	@Path("getPartidesNoTorn")
	@Produces("application/json")
	public String getPartidesNoTorn(@QueryParam("idSessio")String idSessio) {

		String nomsUsuaris = "";
		List<String> partides = this.partSQL.getPartidesNoTorn(idSessio);
		if (partides == null)
			return crearJSON("", "", "Error amb el servidor");

		if (partides.isEmpty())
			return crearJSON("", "No hi ha cap partida", "");

		for (String nom : partides) {
			nomsUsuaris += nom + ";";
		}

		nomsUsuaris = nomsUsuaris.substring(0, nomsUsuaris.length()); // Borrar ultim ;
		return crearJSON(nomsUsuaris, "", "");
	}

	@GET
	@Path("getPartidesAcabades")
	@Produces("application/json")
	public String getPartidesAcabades(@QueryParam("idSessio")String idSessio) {

		String llistaAcabades = "";

		List<String> res = this.partSQL.getPartidesAcabada(idSessio);

		if (res == null)
			return crearJSON("", "", "Error amb el servidor");

		if (res.isEmpty())
			return crearJSON("", "No hi ha cap partida", "");

		for (String r : res)
			llistaAcabades += r + ";";

		return crearJSON(llistaAcabades, "", "");
	}

	@GET
	@Path("triaPartida")
	@Produces("application/json")
	public String triaPartida(@QueryParam("idSessio")String idSessio,@QueryParam("usuari") String usuari) {

		String idPartida = this.partSQL.getPartida(idSessio, usuari);

		if (idPartida == null)
			return crearJSON("", "No hi ha partida disponible.", "");

		contrincant = usuari;

		this.instanciarMoviments(idSessio, idPartida);
		return crearJSON(idPartida, "", "");
	}

	@GET
	@Path("obtenirColor")
	@Produces("application/json")
	public String obtenirColor(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida) {

		String color = this.partSQL.getColor(idSessio, idPartida);

		if (color == null)
			return crearJSON("", "No s'ha trobat partida o sessio", "");

		return crearJSON(color, "", "");
	}

	@GET
	@Path("obtenirTaulerAnt")
	@Produces("application/json")
	public String obtenirTaulerAnt(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida) {

		String tauler = this.partSQL.getTaulerAnt(idPartida);

		if (tauler == null)
			return crearJSON("", "No s'ha trobat partida o sessio, o no hi ha tauler anterior", "");

		return crearJSON(tauler, "", "");
	}

	@GET
	@Path("obtenirTaulerAct")
	@Produces("application/json")
	public String obtenirTaulerAct(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida) {

		String tauler = this.partSQL.continuarPartida(idPartida);

		if (tauler == null)
			return crearJSON("", "No s'ha trobat partida o sessio", "");

		return crearJSON(tauler, "", "");
	}

	@GET
	@Path("obtenirTaulerRes")
	@Produces("application/json")
	public String obtenirTaulerRes(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida) {

		if (this.movTornAct == null)
			return crearJSON("", "ERROR no hi ha taulerRes", "");

		String tauler = this.movTornAct.getTaulellActual().toString();

		if (tauler == null)
			return crearJSON("", "No s'ha trobat partida o sessio", "");

		return crearJSON(tauler, "", "");
	}

	@GET
	@Path("obtenirMovsAnt")
	@Produces("application/json")
	public String obtenirMovsAnt(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida) {

		String movsAnt = this.partSQL.getMovimentsAnt(idPartida);
		if (movsAnt == null)
			return crearJSON("", "No s'han trobat moviments anteriors", "");

		return crearJSON(movsAnt, "", "");
	}

	@GET
	@Path("grabarTirada")
	@Produces("application/json")
	public String grabarTirada(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida) {

		this.movTornAct = Moviments.getInstance();

		String movs = this.movTornAct.movsToString();
		if (movs == null || movs.isEmpty())
			return crearJSON("", "No hi han moviments en aquest torn", "");

		boolean guardat = this.partSQL.guardarMovimentsAnt(idPartida, movs);
		if (!guardat)
			return crearJSON("", "No s'han pogut guardar els moviments a la BD", "");

		String taulellRes = this.movTornAct.getTaulellActual().toString();
		this.partSQL.guardarEstatTauler(idPartida, taulellRes);

		System.out.println("Canvi de torn! " + this.partSQL.canviarTorn(idPartida, contrincant));

		String resultat = this.movTornAct.partidaAcabada();
		String idColor = this.partSQL.getColor(idSessio, idPartida);

		if (resultat.equalsIgnoreCase(idColor)) {
			this.partSQL.acabarPartida(idSessio, idPartida);
			return crearJSON("guanya", "", "");
		} else if (resultat.equalsIgnoreCase("taules")) {
			this.partSQL.acabarPartida(idSessio, idPartida);
			return crearJSON("taules", "", "");
		} else if (resultat.equalsIgnoreCase("continua"))
			return crearJSON("continua", "", "");
		else
			return crearJSON("perd", "", "");
	}

	@GET
	@Path("obtenirMovimentsPossibles")
	@Produces("application/json")
	// Implementació mínima... no comprova peça per peça ni diu si es pot matar
	public String obtenirMovimentsPossibles(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida) {
		if (this.movTornAct == null)
			return crearJSON("", "Error, no s'ha fet triaPartida abans...", "");
		String possibles = this.movTornAct.movimentsPossibles();
		return crearJSON(possibles, "", "");
	}

	@GET
	@Path("ferMoviment")
	@Produces("application/json")
	public String ferMoviment(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida,@QueryParam("posIni") String posIni,@QueryParam("posFi") String posFi) {

		this.movTornAct = Moviments.getInstance();

		String estatTauler = this.partSQL.continuarPartida(idPartida);
		if (estatTauler == null)
			return crearJSON("", "", "No s'ha pogut carregar la partida");

		int xIni = Integer.parseInt(posIni.split(";")[0]);
		int yIni = Integer.parseInt(posIni.split(";")[1]);
		int xFi = Integer.parseInt(posFi.split(";")[0]);
		int yFi = Integer.parseInt(posFi.split(";")[1]);

		boolean moviment = this.movTornAct.ferMoure(xIni, yIni, xFi, yFi);
		if (moviment) {
			System.out.println("Moviment bé!!");
			return crearJSON("true", "", "");
		} else {
			System.out.println("Falla moviment!!");
			return crearJSON("false", "", "");
		}

	}

	@GET
	@Path("ferDama")
	@Produces("application/json")
	public String ferDama(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida,@QueryParam("pos") String pos) {

		String estatTauler = this.partSQL.continuarPartida(idPartida);
		if (estatTauler == null)
			return crearJSON("", "No s'ha pogut carregar la partida", "");

		Taulell tauler = new Taulell();
		tauler.reconstruirTaulell(estatTauler);

		int xIni = Integer.parseInt(pos.split(";")[0]);
		int yIni = Integer.parseInt(pos.split(";")[1]);

		Casella cas = tauler.seleccionarCasella(xIni, yIni);
		Peo p = (Peo) cas.getFitxa();

		boolean damaFeta = tauler.canviDama(p.getColor(), cas);

		this.partSQL.guardarEstatTauler(idPartida, tauler.toString());

		if (damaFeta)
			return crearJSON("true", "", "");
		else
			return crearJSON("false", "", "");
	}

	@GET
	@Path("ferBufa")
	@Produces("application/json")
	public String ferBufa(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida,@QueryParam("pos") String pos) {

		this.movTornAct = Moviments.getInstance();

		int xIni = Integer.parseInt(pos.split(";")[0]);
		int yIni = Integer.parseInt(pos.split(";")[1]);

		boolean bufa = this.movTornAct.ferBufa(xIni, yIni);
		String res;
		if (bufa)
			res = "true";
		else
			res = "false";

		return crearJSON(res, "", "");
	}

	@GET
	@Path("acceptaTaules")
	@Produces("application/json")
	public String acceptaTaules(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida) {
		return null;
	}

	@GET
	@Path("proposaTaules")
	@Produces("application/json")
	public String proposaTaules(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida) {
		return null;
	}

	@GET
	@Path("movsPessa")
	@Produces("application/json")
	public String movsPessa(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida,@QueryParam("pos") String Pos) {

		// String estatTauler = this.partSQL.continuarPartida(idPartida);
		String estatTauler = this.movTornAct.getTaulellActual().toString();
		if (estatTauler == null)
			return crearJSON("", "No s'ha pogut carregar la partida", "");

		Taulell tauler = new Taulell();
		tauler.reconstruirTaulell(estatTauler);

		int xIni = Integer.parseInt(Pos.split(";")[0]);
		int yIni = Integer.parseInt(Pos.split(";")[1]);

		Casella cas = tauler.seleccionarCasella(xIni, yIni);
		if (!cas.getTeFitxa())
			return crearJSON("", "No hi ha fitxa a la posicio donada", "");

		List<int[]> moviments = cas.getFitxa().possiblesMoviments(cas.getX(), cas.getY(), tauler.getMatriu());

		if (moviments.isEmpty())
			return crearJSON("", "You can't move", "");

		String cadena = "";
		for (int i[] : moviments) {
			cadena += i[0] + ";" + i[1] + "-";
		}

		return crearJSON(cadena, "", "");
	}

	private String crearJSON(@QueryParam("res")String res,@QueryParam("err") String err,@QueryParam("sErr") String sErr) {
		if (this.json == null)
			this.json = new JSONObject();
		json.put("res", res);
		json.put("err", err);
		json.put("sErr", sErr);
		return json.toString();
	}

	private void instanciarMoviments(@QueryParam("idSessio")String idSessio,@QueryParam("idPartida") String idPartida) {

		String movsAnt = this.partSQL.getMovimentsAnt(idPartida);
		if (movsAnt == null)
			movsAnt = "";

		String taulerAnt = this.partSQL.getTaulerAnt(idPartida);
		if (taulerAnt == null)
			taulerAnt = "";

		String taulerAct = this.partSQL.continuarPartida(idPartida);
		if (taulerAct == null)
			taulerAct = "";

		boolean tornJugador = this.partSQL.getTorn(idPartida).equals(idSessio);
		System.out.println(idPartida);
		System.out.println(this.partSQL.getTorn(idPartida));

		this.movTornAct = new Moviments(movsAnt, taulerAct, taulerAnt, tornJugador);
	}

	public ConnectionSQLOracle getConnectionSQL() {
		return connSQL;
	}
}
