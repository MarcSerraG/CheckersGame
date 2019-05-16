package CapaPersistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import CapaDomini.Usuari;

public class UsuariSQLOracle {
	
	
	private ConnectionSQLOracle conn;

	public UsuariSQLOracle(ConnectionSQLOracle con) {
		this.conn = con;
	}

	/**
	 * Retorna la contrasenya tipo string del usuari passat
	 * @param nomUsu
	 * @return si no existeix null
	 */
	public String getPasword(String nomUsu) {

		String psw = null;
		String sql = ConnectionSQLOracle.SQLSELECT;
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
	 * Torna un resultset de nom usuaris i conectats o no
	 * @return
	 * FENSE
	 */
	public Set<Usuari> retornaUsuaris() {
		
		ResultSet rs = null;
		String sql = ConnectionSQLOracle.SQLSELECT;
		sql += "(nom,connectat) FROM USUARIS";
		try {
			rs = conn.ferSelect(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
		
	}
	
	/**
	 * Canvia la sessio del usuari per nom i quin estat
	 * @param nomUsu
	 * @return
	 */
	public boolean canviarSessio(String nomUsu, boolean connectat) {
		//String sql
		String sql;
		if (connectat)
			sql = ConnectionSQLOracle.SQLUPDATE+" USUARIS SET (connectat = 1)";
		else
			sql = ConnectionSQLOracle.SQLUPDATE+" USUARIS SET (connectat = 0)";
		try {
			if (conn == null)
				return false;
			return conn.crearInsert(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Insereix un usuari amb sessio
	 * @param nom
	 * @param contrasena
	 * @param email
	 * @return
	 */
	public boolean insertUsuari(String nom, String contrasena, String email, String connected) {
		
		String sql = ConnectionSQLOracle.SQLINSERT;
		
		sql += "USUARIS ";
		sql += "VALUES ";
		sql += "('" + nom + "','" + contrasena + "','" + email + "'," + connected + ")";
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
