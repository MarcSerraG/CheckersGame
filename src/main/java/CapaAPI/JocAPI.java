package CapaAPI;

import CapaPersistencia.*;
import CapaDomini.*;
import org.json.JSONObject;
import com.lambdaworks.crypto.SCryptUtil;

public class JocAPI {
	
	private ConnectionSQLOracle connSQL;
	private UsuariSQLOracle userSQL;
	private PartidesSQLOracle partSQL;
	
	public JocAPI() throws Exception {
		connSQL = new ConnectionSQLOracle("g3geilab1", "g3geilab1");
		userSQL = new UsuariSQLOracle(connSQL);
		partSQL = new PartidesSQLOracle(connSQL);
	}

	/**
	 * Comprova les dades de l'usuari passat per parametres, i fa login si son correctes
	 * @param user nom de l'usuari
	 * @param password contrasenya de l'usuari, SENSE hashing
	 * @return "res": "IdSessio", "err": "usuari o contrasenya incorrecte", "sErr": "";
	 */
	public String login(String user, String password) {
		
		JSONObject json = new JSONObject();
		String BDPassword;
		boolean passwordMatch;
		json.put("res", "temporalID");
		json.put("err", "");
		json.put("sErr", "");
		
		/* Password checking */
		BDPassword = this.userSQL.getPasword(user);
		if (BDPassword == null) json.put("err", "usuari o contrasenya incorrecte");
		else {
			passwordMatch = SCryptUtil.check(password, BDPassword);
			if (!passwordMatch) json.put("err", "usuari o contrasenya incorrecte");
		}
		
		return json.toString();
	}
	
	
}
