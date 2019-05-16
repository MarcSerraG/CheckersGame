package CapaPersistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariSQLOracle {

	private String SQLINSERT = "INSERT INTO ";
	private String SQLSELECT = "SELECT ";
	private ConnectionSQLOracle conn;

	public UsuariSQLOracle(ConnectionSQLOracle con) {
		this.conn = con;
	}

	/**
	 * 
	 * @param nomUsu
	 * @return si no existeix null
	 */
	public String getPasword(String nomUsu) {

		String psw = null;
		String sql = SQLSELECT;
		sql += "(CONTRASENYA) FROM USUARIS WHERE ";
		sql += "nom = '" + nomUsu + "'";
		try {
			ResultSet rs = conn.ferSelect(sql);
			while (rs.next()) {
				psw = rs.getString("CONTRASENYA");
			}
			return psw;

		} catch (SQLException e) {
			e.printStackTrace();
			return psw;
		}
	}

	// Insert usuari
	/**
	 * 
	 * @param nom
	 * @param contrasena
	 * @param email
	 * @return
	 */
	public boolean insertUsuari(String nom, String contrasena, String email, String connected) {

		// String sql
		String sql = SQLINSERT;
		sql += "USUARIS ";
		sql += "VALUES ";
		sql += "('" + nom + "','" + contrasena + "','" + email + "','" + connected + "')";
		try {
			if (conn == null)
				return false;
			return conn.crearInsert(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
