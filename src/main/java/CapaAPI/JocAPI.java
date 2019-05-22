package CapaAPI;

import CapaPersistencia.*;
import CapaDomini.*;
import org.json.JSONObject;
import com.lambdaworks.crypto.SCryptUtil;
import java.util.*;

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
	 * Comprova les dades de l'usuari passat per parametres, i fa login si son correctes
	 * @param user nom de l'usuari
	 * @param password contrasenya de l'usuari, SENSE hashing
	 * @return "res": "IdSessio", "err": "usuari o contrasenya incorrecte", "sErr": "";
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
}
