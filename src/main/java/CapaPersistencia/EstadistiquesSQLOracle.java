package CapaPersistencia;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadistiquesSQLOracle {
	
	private ConnectionSQLOracle conn;
	
	public EstadistiquesSQLOracle(ConnectionSQLOracle connection) {
		this.conn = connection;
	}
	
	public String getEstadistiquesUsuari(String idUsuari) {
		
		String res = "";
		ResultSet rs = null;
		
		String sql = ConnectionSQLOracle.SQLSELECT;
		String sql1 = ConnectionSQLOracle.SQLSELECT;
		
		int numPar = 0, numWin = 0;
		sql += " COUNT(*) from partides where jugador = '"+idUsuari+"' or contrincant = '"+idUsuari+"' and estat = 3";
		try {
			rs = this.conn.ferSelect(sql);
			if (rs.next())
				numPar = rs.getInt("COUNT(*)");
			else
				numPar = 0;
			
		} catch (SQLException e) {
			System.out.println("Error SQL getEstadistiquesUsuari: "+e);
		}
		
		sql1 += "nom_guanyador from partides where jugador = '"+idUsuari+"' or contrincant = '"+idUsuari+"' and estat = 3";
		try {
			rs = this.conn.ferSelect(sql);
			while (rs.next()) {
				String nom = rs.getString("nom_guanyador");
				if (nom.equals(idUsuari))
					numWin++;
			}
			
		} catch (SQLException e) {
			System.out.println("Error SQL getEstadistiquesUsuari: "+e);
		}
		
		return res;
	}

}
