package CapaAplicacio;

import java.util.List;

import org.json.JSONObject;

import com.lambdaworks.crypto.SCryptUtil;

import CapaDomini.Casella;
import CapaDomini.Moviments;
import CapaDomini.Peo;
import CapaDomini.Taulell;
import CapaPersistencia.ConnectionSQLOracle;
import CapaPersistencia.EstadistiquesSQLOracle;
import CapaPersistencia.PartidesSQLOracle;
import CapaPersistencia.UsuariSQLOracle;

public class JocAPI {

	private ConnectionSQLOracle connSQL;
	private UsuariSQLOracle userSQL;
	private PartidesSQLOracle partSQL;
	private EstadistiquesSQLOracle statSQL;
	private Moviments movTornAct;
	private JSONObject json;

	public JocAPI() throws Exception {
		connSQL = new ConnectionSQLOracle();
		userSQL = new UsuariSQLOracle(connSQL);
		partSQL = new PartidesSQLOracle(connSQL);
		statSQL = new EstadistiquesSQLOracle(connSQL);
		json = new JSONObject();
		movTornAct = null;
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
	public String login(String user, String password) {

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

	public String registra(String user, String password) {

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

	public String logout(String idSessio) {

		boolean errorSessio = !userSQL.canviarSessio(idSessio, false);
		if (errorSessio) {
			return crearJSON("", "Error ID Sessió", "");
		}

		return crearJSON("", "", "");
	}

	public String reconnecta(String idSessio, String password) {

		boolean sessioCaducada = !this.userSQL.getConnectat(idSessio); // Canviar pel necessari?
		if (sessioCaducada)
			return this.login(idSessio, password);
		else
			return crearJSON("", "La sessió encara està connectada", "");
	}

	public String getEstadistics(String idSessio) {
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
	public String getCandidatsSol(String idSessio) {

		String res = this.userSQL.getCandidats(idSessio);
		if (res == null)
			return crearJSON("", "", "Hi ha hagut un problema de connexió.");

		if (res.isEmpty())
			return crearJSON("", "", "No hi han usuaris connectats.");

		return crearJSON(res, "", "");
	}

	// CreaPartida esta comprovat que funciona a la part de SQL
	public void enviaSol(String idSessio, String usuari) {

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

	public String solicituds(String idSessio) {

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

	public void acceptaSol(String idSessio, String usuari) {
		this.partSQL.acceptarSolicitud(idSessio, usuari);
	}

	public void rebutjaSol(String idSessio, String usuari) {
		this.partSQL.rebutjaSolicitud(idSessio, usuari);
	}

	public String getPartidesTorn(String idSessio) {

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

	public String getPartidesNoTorn(String idSessio) {

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

	public String getPartidesAcabades(String idSessio) {

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

	public String triaPartida(String idSessio, String usuari) {

		String idPartida = this.partSQL.getPartida(idSessio, usuari);

		if (idPartida == null)
			return crearJSON("", "No hi ha partida disponible.", "");

		this.instanciarMoviments(idSessio, idPartida);
		return crearJSON(idPartida, "", "");
	}

	public String obtenirColor(String idSessio, String idPartida) {

		String color = this.partSQL.getColor(idSessio, idPartida);

		if (color == null)
			return crearJSON("", "No s'ha trobat partida o sessio", "");

		return crearJSON(color, "", "");
	}

	public String obtenirTaulerAnt(String idSessio, String idPartida) {

		String tauler = this.partSQL.getTaulerAnt(idPartida);

		if (tauler == null)
			return crearJSON("", "No s'ha trobat partida o sessio, o no hi ha tauler anterior", "");

		return crearJSON(tauler, "", "");
	}

	public String obtenirTaulerAct(String idSessio, String idPartida) {

		String tauler = this.partSQL.continuarPartida(idPartida);

		if (tauler == null)
			return crearJSON("", "No s'ha trobat partida o sessio", "");

		return crearJSON(tauler, "", "");
	}

	public String obtenirTaulerRes(String idSessio, String idPartida) {

		if (this.movTornAct == null)
			return crearJSON("", "ERROR no hi ha taulerRes", "");

		String tauler = this.movTornAct.getTaulellActual().toString();

		if (tauler == null)
			return crearJSON("", "No s'ha trobat partida o sessio", "");

		return crearJSON(tauler, "", "");
	}

	public String obtenirMovsAnt(String idSessio, String idPartida) {

		String movsAnt = this.partSQL.getMovimentsAnt(idPartida);
		if (movsAnt == null)
			return crearJSON("", "No s'han trobat moviments anteriors", "");

		return crearJSON(movsAnt, "", "");
	}

	public String grabarTirada(String idSessio, String idPartida) {
		// NO FUNCIONA EN AQUESTA VERSIÓ
		// TODO: Comprovar / implementar taules...

		this.movTornAct = Moviments.getInstance();

		String movs = this.movTornAct.movsToString();
		if (movs == null || movs.isEmpty())
			return crearJSON("", "No hi han moviments en aquest torn", "");

		boolean guardat = this.partSQL.guardarMovimentsAnt(idPartida, movs);
		if (!guardat)
			return crearJSON("", "No s'han pogut guardar els moviments a la BD", "");

		String taulellRes = this.movTornAct.getTaulellActual().toString();
		this.partSQL.guardarEstatTauler(idPartida, taulellRes);

		System.out.println("Canvi de torn! " + this.partSQL.canviarTorn(idPartida, idSessio));

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

	// Implementació mínima... no comprova peça per peça ni diu si es pot matar
	public String obtenirMovimentsPossibles(String idSessio, String idPartida) {
		if (this.movTornAct == null)
			return crearJSON("", "Error, no s'ha fet triaPartida abans...", "");
		String possibles = this.movTornAct.movimentsPossibles();
		return crearJSON(possibles, "", "");
	}

	public String ferMoviment(String idSessio, String idPartida, String posIni, String posFi) {

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

	public String ferDama(String idSessio, String idPartida, String pos) {

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

	public String ferBufa(String idSessio, String idPartida, String pos) {

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

	public String acceptaTaules(String idSessio, String idPartida) {
		return null;
	}

	public String proposaTaules(String idSessio, String idPartida) {
		return null;
	}

	public String movsPessa(String idSessio, String idPartida, String Pos) {

		String estatTauler = this.partSQL.continuarPartida(idPartida);
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

	private String crearJSON(String res, String err, String sErr) {
		if (this.json == null)
			this.json = new JSONObject();
		json.put("res", res);
		json.put("err", err);
		json.put("sErr", sErr);
		return json.toString();
	}

	private void instanciarMoviments(String idSessio, String idPartida) {

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

	// Converteix un string d'un taulell de la capa domini o aplicacio,
	// Retornant un string de taulell com especifica l'API
	private String taulellConversor(String taulell) {
		taulell = taulell.replace('x', ' ');
		taulell = taulell.replaceAll(",", "");
		taulell = taulell.replace("\n", ";");
		taulell = taulell.replace('0', '♗');
		taulell = taulell.replace('1', '♝');
		taulell = taulell.replace('D', '♕');
		taulell = taulell.replace('d', '♛');
		return taulell;
	}

	public ConnectionSQLOracle getConnectionSQL() {
		return connSQL;
	}
}
