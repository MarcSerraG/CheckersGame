package CapaPersistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import CapaDomini.Taulell;

public class ConnectionSQLOracle {

	private Connection conn;

	public static String SQLINSERT = "INSERT INTO ";
	public static String SQLSELECT = "SELECT ";
	public static String SQLUPDATE = "UPDATE";

	public ConnectionSQLOracle(String usuari, String contrasena) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		ConfigurationSQLOracle propietes = ConfigurationSQLOracle.getInstancia();
		String server = propietes.getPropietat("Server.server");
		String user = propietes.getPropietat("Server.user");
		String pass = propietes.getPropietat("Server.pass");

		conn = DriverManager.getConnection(server, user, pass);
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
			System.out.println("Error en Select de "+sql+" tipus de error "+e);
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
			System.out.println(sql);
			st = conn.createStatement();
			st.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			System.out.println("Error en Select de "+sql+" tipus de error "+e);
			return false;
		}
	}
	
	/**
	 * Crear delete a la bbdd
	 * @param sql
	 * @throws SQLException
	 */
	public void crearDelete(String sql) throws SQLException {
		Statement st = null;
		try {
			System.out.println(sql);
			st = conn.createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			System.out.println("Error en Select de "+sql+" tipus de error "+e);
		}
	}
	
	public void ferCommit() throws SQLException {
		conn.commit();
	}
	
	public void setAutocommit(boolean opt) throws SQLException {
		conn.setAutoCommit(opt);
	}
	
	public static void main(String[] args) {
		// Sa dimportar odbc.jar
		try {
			
			ConnectionSQLOracle cn =  new ConnectionSQLOracle("g3geilab1", "g3geilab1");
			UsuariSQLOracle usu =  new UsuariSQLOracle(cn);
			//usu.insertUsuari("Marc", "1234", "msg@gmail.com","0");
			//System.out.println(usu.getConnectat("Marc"));
			//System.out.println(tb.toString());
			PartidesSQLOracle pat = new PartidesSQLOracle(cn);
			String id = pat.getColor("Prova3", "66");
			System.out.println(id);
			//System.out.println(pat.getTaullelnou().toString());
			
			} catch (Exception e) {
			System.out.println(e);
		}
	}

}
