package CapaPersistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSQLOracle {
	
	private Connection conn;
	private String usuari;
	private String contrasena;
	
	public ConnectionSQLOracle(String usuari, String contrasena) throws Exception {
		this.usuari = usuari;
		this.contrasena = contrasena;
		
		Class.forName("oracle.jdbc.driver.OracleDriver");  
	}
	
	public void obreConeccio() throws SQLException{
		conn = DriverManager.getConnection("jdbc:oracle:thin:@kali.tecnocampus.cat:1521:sapiens",
						usuari, contrasena);
	}
	
	public void tancaConeccio() throws SQLException {
		conn.close();
	}
	
	public static void main(String[] args) {
		//Sa dimportar odbc.jar
		try {
			ConnectionSQLOracle cn =  new ConnectionSQLOracle("g3geilab1", "g3geilab1");
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
