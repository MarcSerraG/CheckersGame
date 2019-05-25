package CapaAplicacio;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.lambdaworks.crypto.SCryptUtil;

import CapaDomini.Partida;
import CapaDomini.Sessio;
import CapaDomini.Usuari;
import CapaPersistencia.ConnectionSQLOracle;
import CapaPersistencia.PartidesSQLOracle;
import CapaPersistencia.UsuariSQLOracle;

public class JocAPI {

	private ConnectionSQLOracle connSQL;
	private UsuariSQLOracle userSQL;
	private PartidesSQLOracle partSQL;
	private Sessio sessio;

	public JocAPI(String user, String password) throws Exception {
		connSQL = new ConnectionSQLOracle(user, password);
		userSQL = new UsuariSQLOracle(connSQL);
		partSQL = new PartidesSQLOracle(connSQL);
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
		return null;
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
		res = this.userSQL.retornaUsuaris();
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

	public void enviaSol(String idSessio, String usuari) {

	}

	public String solicituds(String idSessio) {
		return null;
	}

	public void acceptaSol(String idSessio, String usuari) {

	}

	public void rebutjaSol(String idSessio, String usuari) {

	}

	public String getPartidesTorn(String idSessio) {
		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");

		String nomsUsuaris = "";
		List<String> partides = this.partSQL.getPartidesTorn(new Usuari(idSessio));
		if (partides == null) return json.toString();
		
		for (String nom : partides) {
			nomsUsuaris += nom + ";";
		}

		nomsUsuaris = nomsUsuaris.substring(0, nomsUsuaris.length() - 2); // Borrar ultim ;
		json.put("res", nomsUsuaris);

		return json.toString();
	}

	public String getPartidesNoTorn(String idSessio) {
		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");

		String nomsUsuaris = "";
		List<String> partides = this.partSQL.getPartidesNoTorn(new Usuari(idSessio));
		if (partides == null) return json.toString();
		
		for (String nom : partides) {
			nomsUsuaris += nom + ";";
		}

		nomsUsuaris = nomsUsuaris.substring(0, nomsUsuaris.length() - 2); // Borrar ultim ;
		json.put("res", nomsUsuaris);

		return json.toString();
	}

	public String getPartidesAcabades(String idSessio) {
		return null;
	}
	
	public String triaPartida(String idSessio, String usuari) {
	}

	public String obtenirColor(String idSessio, String idPartida) {
		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");
		
		String color = this.partSQL.getColor(idSessio, idPartida);
		
		if (color == null)
			json.put("err", "No s'ha trobat partida o sessio");
		else 
			json.put("res", color);
		
		return json.toString();
	}

	public String obtenirTaulerAnt(String idSessio, String idPartida) {
		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");
		
		String tauler = this.partSQL.getTaulerAnt(idSessio, idPartida);
		
		if (tauler == null)
			json.put("err", "No s'ha trobat partida o sessio, o no hi ha tauler anterior");
		else 
			json.put("res", tauler);
		
		return json.toString();
	}

	public String obtenirTaulerAct(String idSessio, String idPartida) {
		return null;
	}

	public String obtenirTaulerRes(String idSessio, String idPartida) {
		return null;
	}

	public String obtenirMovsAnt(String idSessio, String idPartida) {
		return null;
	}

	public String grabarTirada(String idSessio, String idPartida) {
		return null;
	}

	public String obtenirMovimentsPossibles(String idSessio, String idPartida) {
		return null;
	}

	public String ferMoviment(String idSessio, String idPartida, String posIni, String posFi) {
		return null;
	}

	public String ferDama(String idSessio, String idPartida, String pos) {

		return null;
	}

	public String ferBufa(String idSessio, String idPartida, String pos) {
		return null;
	}

	public String acceptaTaules(String idSessio, String idPartida) {
		return null;
	}

	public String proposaTaules(String idSessio, String idPartida) {
		return null;
	}

	public String movsPessa(String idSessio, String idPartida, String Pos) {
		return null;
	}
}
