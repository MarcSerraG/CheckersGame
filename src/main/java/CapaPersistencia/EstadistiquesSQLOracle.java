package CapaPersistencia;

import java.sql.SQLException;

public class EstadistiquesSQLOracle {
	
	private ConnectionSQLOracle conn;
	
	public EstadistiquesSQLOracle(ConnectionSQLOracle connection) {
		this.conn = connection;
	}
	
	public String getEstadistiquesUsuari(String idUsuari) {
		//TODO
		
		try {
			this.conn.ferSelect("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
