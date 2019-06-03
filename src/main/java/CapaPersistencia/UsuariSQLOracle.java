package CapaPersistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuariSQLOracle {

	private ConnectionSQLOracle conn;

	public UsuariSQLOracle(ConnectionSQLOracle con) {
		this.conn = con;
	}

	/**
	 * Retorna la contrasenya tipo string del usuari passat
	 * 
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
			rs.close();
			return psw;
		} catch (SQLException e) {
			e.printStackTrace();
			return psw;
		}
	}
	
	
	public String getCandidats(String nomUsu) {
		ResultSet rs = null;
		String sortida = "";
		String sql = ConnectionSQLOracle.SQLSELECT;
		
		sql = "SELECT nom FROM USUARIS WHERE nom NOT IN "
				+ "("
				+ "SELECT jugador FROM partides WHERE ((contrincant = '"+nomUsu+"') and estat between 0 and 2) " + 
				"UNION " + 
				"SELECT contrincant FROM partides WHERE ((jugador = '"+nomUsu+"') and estat between 0 and 2)" + 
				") AND nom != '"+nomUsu+"' AND connectat = 1";
		
		try {
			rs = conn.ferSelect(sql);
			while (rs.next())
				sortida += rs.getString("nom")+";";
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error sql usuarisCandidats: "+e);
		} catch (Exception e) {
			System.out.println("Error usuarisCandidats: "+e);
		}
		return sortida;
	}
	/**
	 * Torna un resultset de nom usuaris conectats
	 * 
	 * @return
	 */
	public String retornaUsuaris(String nomUsu) {

		ResultSet rs = null;
		String sortida = "";
		String sql = ConnectionSQLOracle.SQLSELECT;
		sql += "(nom) FROM USUARIS where connectat = 1 and nom != '"+nomUsu+"'";
		try {
			rs = conn.ferSelect(sql);
			while (rs.next()) {
				sortida += rs.getString("nom");
				sortida += ";";
			}
			rs.close();
		} catch (SQLException e) {
			System.out.print("Error SQL retorna Usuaris: "+e.getMessage());
			return null;

		}
		return sortida;

	}

	public boolean getConnectat(String nomUsu) {
		int con = 0;
		String sql = ConnectionSQLOracle.SQLSELECT;
		sql += "(CONNECTAT) FROM USUARIS WHERE ";
		sql += "nom = '" + nomUsu + "'";
		try {
			ResultSet rs = conn.ferSelect(sql);
			while (rs.next()) {
				con = rs.getInt("CONNECTAT");
			}
			if (con == 1) {
				rs.close();
				return true;
			}
			else {
				rs.close();
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Canvia la sessio del usuari per nom i quin estat
	 * 
	 * @param nomUsu
	 * @return
	 */
	public boolean canviarSessio(String nomUsu, boolean connectat) {
		// String sql
		String sql;
		if (connectat)
			sql = ConnectionSQLOracle.SQLUPDATE + " USUARIS SET connectat = 1";
		else
			sql = ConnectionSQLOracle.SQLUPDATE + " USUARIS SET connectat = 0";
		sql += " WHERE NOM = '" + nomUsu + "'";
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
	 * 
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
