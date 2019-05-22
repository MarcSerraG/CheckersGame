package CapaPersistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import CapaDomini.Usuari;

public class ConnectionSQLOracle {

	private Connection conn;
	
	public static String SQLINSERT =  "INSERT INTO ";
	public static String SQLSELECT = "SELECT ";
	public static String SQLUPDATE = "UPDATE";
	
	public ConnectionSQLOracle(String usuari, String contrasena) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		conn = DriverManager.getConnection("jdbc:oracle:thin:@kali.tecnocampus.cat:1521:sapiens", usuari, contrasena);
	}

	public void tancaConeccio() throws SQLException {
		conn.close();
	}

	/**
	 * 
	 * @param sql
	 * @return retorna el resultats, null si no hi han
	 * @throws SQLException
	 */
	public ResultSet ferSelect(String sql) throws SQLException {

		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		try {
			rs = st.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * 
	 * @param sql script sql
	 * @return Retorna true si sa fet la transaccio correctament false si no
	 *         funciona
	 * @throws SQLException
	 * 
	 */
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
			//usu.insertUsuari("Marc", "1234", "msg@gmail.com","0");
			
			System.out.println(usu.getConnectat("Marc"));
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
