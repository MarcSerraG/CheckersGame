package CapaPersistencia;

import java.sql.Connection;
import java.sql.SQLException;

import CapaDomini.Sessio;

public class UsuariSQLOracle {
	
	private String SQLINSERT =  "INSERT INTO ";
	private static ConnectionSQLOracle conn;
	
	public UsuariSQLOracle (ConnectionSQLOracle con) {
		this.conn = con;
	}
	
	//Usuari sessio no
	public Sessio selectUsuari() {
		
		
		
		
		
		return null;
	}
	
	public boolean deleteUsuari(String nom) {
		return false;
	}
	
	//Insert usuari
	public boolean insertUsuari(String nom, String contrasena, String email, String user) {
		
		//String sql
		String sql = SQLINSERT;
		sql += "USUARIS ";
		sql += "VALUES ";
		sql += "('"+nom+"','"+contrasena+"','"+email+"','"+user+"')";
		
		System.out.println(sql);
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
