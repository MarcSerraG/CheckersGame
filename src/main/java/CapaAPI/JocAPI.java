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

	public JocAPI() throws Exception {
		connSQL = new ConnectionSQLOracle("g3geilab1", "g3geilab1");
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
		if (jaConnectat) json.put("err", "Usuari amb sessió oberta");
		
		/* Password checking */
		String BDPassword = this.userSQL.getPasword(user);
		if (BDPassword == null) json.put("err", "User-password incorrecte");
		else {
			boolean passwordMatch = SCryptUtil.check(password, BDPassword);
			if (!passwordMatch) json.put("err", "User-password incorrecte");
		}
		
		boolean connexioCorrecte = this.userSQL.canviarSessio(user, true);
		if (!connexioCorrecte) json.put("sErr", "No s'ha pogut crear la sessio");
		
		this.sessio = new Sessio(user, new HashSet<Partida>(), 0); // TEMPORAL

		return json.toString();
	}

	public String registra(String user, String password) {

		JSONObject json = new JSONObject();
		json.put("res", user);
		json.put("err", "");
		json.put("sErr", "");
		
		boolean userExists = this.userSQL.getPasword(user) != null;
		if (userExists) json.put("err", "Usuari existent");
		
	}

	public void logout(String idSessio) {

	}

	public void reconnecta(String idSessio, String password) {

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
