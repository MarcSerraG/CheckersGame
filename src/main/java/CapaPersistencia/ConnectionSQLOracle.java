
package CapaPersistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionSQLOracle {

	private Connection conn;

	public static String SQLINSERT = "INSERT INTO ";
	public static String SQLSELECT = "SELECT ";
	public static String SQLUPDATE = "UPDATE";

	public ConnectionSQLOracle() throws Exception {
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
	 * @throws SQLException
	 */
	public void ferDelete(String sql) throws SQLException {
		Statement st = null;
		try {
			st = conn.createStatement();
			st.execute(sql);
			st.close();
		} catch (SQLException e) {
			System.out.println("Error en Select de " + sql + " tipus de error " + e);
		}
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
			System.out.println("Error en Select de " + sql + " tipus de error " + e);
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
			st.executeUpdate(sql);
			st.close();
			return true;
		} catch (SQLException e) {
			System.out.println("Error en Select de " + sql + " tipus de error " + e);
			return false;
		}
	}

	public void ferCommit() throws SQLException {
		conn.commit();
	}

	public void setAutocommit(boolean opt) throws SQLException {
		conn.setAutoCommit(opt);
	}
}
