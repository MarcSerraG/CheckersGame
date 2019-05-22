package CapaAPI;

import org.json.JSONObject;

import com.lambdaworks.crypto.SCryptUtil;
import java.util.*;

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
		String BDPassword;
		boolean passwordMatch;
		json.put("res", user);
		json.put("err", "");
		json.put("sErr", "");
		
		
		/* Password checking */
		BDPassword = this.userSQL.getPasword(user);
		if (BDPassword == null) json.put("err", "User-password incorrecte");
		else {
			passwordMatch = SCryptUtil.check(password, BDPassword);
			if (!passwordMatch) json.put("err", "User-password incorrecte");
		}
		boolean jaConnectat = false;
		if (jaConnectat) json.put("err", "Usuari amb sessió oberta");
		
		this.sessio = new Sessio(user, new HashSet<Partida>(), 0); // TEMPORAL
		
		return json.toString();
	}
	
	public String registra(String user, String password) {
		
		JSONObject json = new JSONObject();
		String BDPassword;
		boolean userExists = false;
		json.put("res", user);
		json.put("err", "");
		json.put("sErr", "");
		
		
	}
}
