package CapaPersistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionSQLOracle {
	
	private Connection conn;
	private String usuari;
	private String contrasena;
	
	public ConnectionSQLOracle(String usuari, String contrasena) throws Exception {
		this.usuari = usuari;
		this.contrasena = contrasena;
		
		Class.forName("oracle.jdbc.driver.OracleDriver"); 
		
		conn = DriverManager.getConnection("jdbc:oracle:thin:@kali.tecnocampus.cat:1521:sapiens",
				usuari, contrasena);
	}
	
	public void tancaConeccio() throws SQLException {
		conn.close();
	}
	
	public boolean crearInsert(String sql) throws SQLException {
		Statement st = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			st.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		//Sa dimportar odbc.jar
		try {
			ConnectionSQLOracle cn =  new ConnectionSQLOracle("g3geilab1", "g3geilab1");
			UsuariSQLOracle usu =  new UsuariSQLOracle(cn);
			usu.insertUsuari("Marc", "1234", "msg@gmail.com", "MSerra");
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
