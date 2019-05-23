package CapaAPI;

import java.util.HashSet;

import org.json.JSONObject;

import com.lambdaworks.crypto.SCryptUtil;

import CapaDomini.Partida;
import CapaDomini.Sessio;
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
		}
		else this.sessio = null;
		return json.toString();
	}

	public String reconnecta(String idSessio, String password) {
		
		JSONObject json = new JSONObject();
		json.put("res", "");
		json.put("err", "");
		json.put("sErr", "");
		
		boolean sessioCaducada = !this.sessio.getConnectat();
		if (sessioCaducada)	return this.login(idSessio, password);
		else {
			json.put("err", "La sessió encara està connectada");
		}
		return json.toString();
	}

	public String getEstadistics(String idSessio) {
		return null;
	}

	public String getCandidatsSol(String idSessio) {
		return null;
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

	public String gerPartidesTorn(String idSessio) {
		return null;
	}

	public String getPartidesNoTorn(String idSessio) {
		return null;
	}

	public String getPartidesAcabades(String idSessio) {
		return null;
	}

	public void tiraPartida(String idSessio, String usuari) {

	}

	public String obtenirColor(String idSessio, String idPartida) {
		return null;
	}

	public String obtenirTaulerAnt(String idSessio, String idPartida) {
		return null;
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
