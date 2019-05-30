package CapaAplicacio;

import java.util.HashSet;
import java.util.List;

import org.json.JSONObject;

import com.lambdaworks.crypto.SCryptUtil;

import CapaDomini.Casella;
import CapaDomini.Moviments;
import CapaDomini.Partida;
import CapaDomini.Peo;
import CapaDomini.Sessio;
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
	private Sessio sessio;
	private Moviments movTornAct;
	private JSONObject json;

	public JocAPI(String user, String password) throws Exception {
		connSQL = new ConnectionSQLOracle(user, password);
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
		JSONObject json = new JSONObject();
		json.put("res", user);
		json.put("err", "");
		json.put("sErr", "");

		boolean jaConnectat = this.userSQL.getConnectat(user);
		if (jaConnectat)
			json.put("err", "Usuari amb sessió oberta");
		else {
			/* Password checking */
			String BDPassword = this.userSQL.getPasword(user);
			if (BDPassword == null)
				json.put("err", "No User");
			else {
				boolean passwordMatch = SCryptUtil.check(password, BDPassword);
				if (!passwordMatch)
					json.put("err", "User-password incorrecte");
				else {

					boolean connexioCorrecte = this.userSQL.canviarSessio(user, true);
					if (!connexioCorrecte)
						json.put("sErr", "No s'ha pogut crear la sessio");

					try {
						this.sessio = new Sessio(user, new HashSet<Partida>(), 0, "g3geilab1", "g3geilab1");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // TEMPORAL
				}
			}
		}

		return json.toString();
	}

	public String registra(String user, String password) {

		JSONObject json = new JSONObject();
		json.put("res", user);
		json.put("err", "");
		json.put("sErr", "");

		if (user.contains(";") || user.contains("\"")) {
			json.put("err", "El nom no pot contenir \" ni ;");
			return json.toString();
		}

		boolean userExists = this.userSQL.getPasword(user) != null;
		if (userExists)
			json.put("err", "Usuari existent");
		else {
			String BDPassword = SCryptUtil.scrypt(password, 2, 2, 2);

			boolean creacioCorrecte = this.userSQL.insertUsuari(user, BDPassword, "-", "1");
			if (!creacioCorrecte)
				json.put("err", "Error al crear usuari");
		}

		return json.toString();
	}

	public String logout(String idSessio) {

		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");

		boolean errorSessio = !userSQL.canviarSessio(idSessio, false);
		if (errorSessio) {
			json.put("err", "Error ID Sessió");
		} else
			this.sessio = null;
		return json.toString();
	}

	public String reconnecta(String idSessio, String password) {

		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");

		boolean sessioCaducada = !this.sessio.getConnectat();
		if (sessioCaducada)
			return this.login(idSessio, password);
		else {
			json.put("err", "La sessió encara està connectada");
		}
		return json.toString();
	}

	public String getEstadistics(String idSessio) {
		JSONObject json = new JSONObject();
		String res = "";
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");
		System.out.println("idSessio" + idSessio);
		res = this.statSQL.getEstadistiquesUsuari(idSessio);
		if (res == null)
			json.put("err", "Sql error no hi han dades suficients.");
		else if (res.equals(""))
			json.put("err", "La base de dades ha retornat sense dades.");
		else
			json.put("res", res);

		return json.toString();
	}

	/**
	 * Retorna na llista d'usuaris separats per ;
	 * 
	 * @param idSessio
	 * @return
	 */
	public String getCandidatsSol(String idSessio) {
		JSONObject json = new JSONObject();
		String res = "";
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");
		res = this.userSQL.getCandidats(idSessio);
		if (res == null)
			json.put("sErr", "Hi ha hagut un problema de connexió.");
		else {
			if (res == "")
				json.put("sErr", "No hi han usuaris connectats.");
			else
				json.put("res", res).toString();
		}

		return json.toString();
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

	/**
	 * TODO
	 * 
	 * @param idSessio
	 * @return
	 */
	public String solicituds(String idSessio) {
		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");

		List<String> solicituds = this.partSQL.getSolicitudsPendents(idSessio);
		if (solicituds == null) {
			json.put("sErr", "No s'han pogut carregar les solicituds");
			return json.toString();
		}

		if (solicituds.isEmpty()) {
			json.put("err", "No hi ha cap partida");
			return json.toString();
		}
		String nomsUsuaris = "";
		for (String nom : solicituds) {
			nomsUsuaris += nom + ";";
		}

		nomsUsuaris = nomsUsuaris.substring(0, nomsUsuaris.length()); // Borrar ultim ;
		json.put("res", nomsUsuaris);

		// res = this.partSQL.getSolicitudsPendents(idSessio);
		/*
		 * if (res == null) System.out.println("Hi ha hagut un problema de connexió.");
		 * else { if (res.equals(""))
		 * System.out.println("No sa pogut afegir la partida a la BBDD."); else
		 * System.out.println(res); }
		 */
		return json.toString();
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

		String id = this.partSQL.getPartida(idSessio, usuari);

		if (id == null)
			return crearJSON("", "No hi ha partida disponible.", "");

		return crearJSON(id, "", ""); // Remove comment only if the method fails

		/*
		 * String movsAnt = this.partSQL.getMovimentsAnt(id); if (movsAnt == null)
		 * return crearJSON("", "No hi ha moviments anteriors (null)", "");
		 * 
		 * String taulerAnt = this.partSQL.getTaulerAnt(idSessio, id); if (taulerAnt ==
		 * null) return crearJSON("", "No s'ha trobat tauler anterior", "");
		 * 
		 * String taulerAct = this.partSQL.continuarPartida(id); if (taulerAct == null)
		 * return crearJSON("", "No s'ha trobat tauler actual", "");
		 * 
		 * this.movTornAct = new Moviments(movsAnt, taulerAct, taulerAnt);
		 * 
		 * return crearJSON(id, "", "");
		 */
	}

	public String obtenirColor(String idSessio, String idPartida) {

		String color = this.partSQL.getColor(idSessio, idPartida);

		if (color == null)
			return crearJSON("", "No s'ha trobat partida o sessio", "");

		return crearJSON(color, "", "");
	}

	public String obtenirTaulerAnt(String idSessio, String idPartida) {

		String tauler = this.partSQL.getTaulerAnt(idSessio, idPartida);

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
		return null;
	}

	// NO ES POT IMPLEMENTAR PER ARA...
	public String obtenirMovimentsPossibles(String idSessio, String idPartida) {
		return null;
	}

	public String ferMoviment(String idSessio, String idPartida, String posIni, String posFi) {
		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");

		String estatTauler = this.partSQL.continuarPartida(idPartida);
		if (estatTauler == null) {
			json.put("sErr", "No s'ha pogut carregar la partida");
			return json.toString();
		}

		Taulell tauler = new Taulell(10);
		tauler.reconstruirTaulell(estatTauler);

		int xIni = Integer.parseInt(posIni.split(";")[0]);
		int yIni = Integer.parseInt(posIni.split(";")[1]);
		int xFi = Integer.parseInt(posFi.split(";")[0]);
		int yFi = Integer.parseInt(posFi.split(";")[1]);

		Casella casIni = tauler.seleccionarCasella(xIni, yIni);
		Casella casFi = tauler.seleccionarCasella(xFi, yFi);
		try {

			boolean moviment = tauler.moviment(casIni, casFi);
			if (moviment)
				json.put("res", "true");
			else
				json.put("res", "false");

			// tindria que ser contrincant, no idSessio!!
			// boolean canviTorn = this.partSQL.canviarTorn(idPartida, idSessio);
			// if (!canviTorn) {
			// json.put("err", "Error al fer canvi de torn, no s'ha guardat el nou estat del
			// taulell");
			// return json.toString();
			// } else {
			this.partSQL.guardarEstatTauler(idPartida, tauler.toString());
			// }

		} catch (Exception e) {
			json.put("err", e);
		}

		return json.toString();
	}

	public String ferDama(String idSessio, String idPartida, String pos) {
		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");

		String estatTauler = this.partSQL.continuarPartida(idPartida);
		if (estatTauler == null) {
			json.put("err", "No s'ha pogut carregar la partida");
			return json.toString();
		}

		Taulell tauler = new Taulell(10);
		tauler.reconstruirTaulell(estatTauler);

		int xIni = Integer.parseInt(pos.split(";")[0]);
		int yIni = Integer.parseInt(pos.split(";")[1]);

		Casella cas = tauler.seleccionarCasella(xIni, yIni);
		Peo p = (Peo) cas.getFitxa();
		boolean damaFeta = tauler.canviDama(p.getColor(), cas);
		if (damaFeta)
			json.put("res", "true");
		else
			json.put("res", "false");

		this.partSQL.guardarEstatTauler(idPartida, tauler.toString());

		return json.toString();
	}

	public String ferBufa(String idSessio, String idPartida, String pos) {
		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");

		String estatTauler = this.partSQL.continuarPartida(idPartida);
		if (estatTauler == null) {
			json.put("err", "No s'ha pogut carregar la partida");
			return json.toString();
		}

		Taulell tauler = new Taulell(10);
		tauler.reconstruirTaulell(estatTauler);

		int xIni = Integer.parseInt(pos.split(";")[0]);
		int yIni = Integer.parseInt(pos.split(";")[1]);

		Casella cas = tauler.seleccionarCasella(xIni, yIni);
		if (tauler.bufar(cas)) {
			json.put("res", "true");
			return json.toString();
		}
		json.put("res", "false");
		return json.toString();
	}

	public String acceptaTaules(String idSessio, String idPartida) {
		return null;
	}

	public String proposaTaules(String idSessio, String idPartida) {
		return null;
	}

	public String movsPessa(String idSessio, String idPartida, String Pos) {

		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");

		String estatTauler = this.partSQL.continuarPartida(idPartida);
		if (estatTauler == null) {
			json.put("err", "No s'ha pogut carregar la partida");
			return json.toString();
		}

		Taulell tauler = new Taulell(10);
		tauler.reconstruirTaulell(estatTauler);

		int xIni = Integer.parseInt(Pos.split(";")[0]);
		int yIni = Integer.parseInt(Pos.split(";")[1]);

		Casella cas = tauler.seleccionarCasella(xIni, yIni);
		if (!cas.getTeFitxa()) {
			json.put("err", "No hi ha fitxa a la posicio donada");
			return json.toString();
		}
		List<int[]> moviments = tauler.veurePossiblesMoviments(cas);
		String cadena = "";
		for (int i = 0; i < moviments.size() - 1; i++)
			cadena = moviments.get(i)[0] + ";" + moviments.get(i)[1] + "-";
		cadena += moviments.get(moviments.size() - 1)[0] + ";" + moviments.get(moviments.size() - 1)[0];
		json.put("res", cadena);
		return json.toString();
	}

	private String crearJSON(String res, String err, String sErr) {
		if (this.json == null)
			this.json = new JSONObject();
		json.put("res", res);
		json.put("err", err);
		json.put("sErr", sErr);
		return json.toString();
	}
}
