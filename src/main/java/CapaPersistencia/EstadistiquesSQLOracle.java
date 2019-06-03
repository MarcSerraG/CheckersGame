package CapaPersistencia;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EstadistiquesSQLOracle {
	
	private ConnectionSQLOracle conn;
	
	public EstadistiquesSQLOracle(ConnectionSQLOracle connection) {
		this.conn = connection;
	}
	
	/**
	 * Return string amb les dades de stadistiques del usuari i un ranking
	 * @param idUsuari
	 * @return
	 */
	public String getEstadistiquesUsuari(String idUsuari) {
		
		String res = "";
		List<String> rank = new ArrayList<String>();
		ResultSet rs = null;
		
		String sql = ConnectionSQLOracle.SQLSELECT;
		String sql1 = ConnectionSQLOracle.SQLSELECT;
		String ranksql = ConnectionSQLOracle.SQLSELECT;
		
		int numPar = 0, numWin = 0,numT = 0,numLose = 0;
		float ratio = 0.00f;
		sql += " COUNT(*) from partides where estat = 3 and (jugador = '"+idUsuari+"' or contrincant = '"+idUsuari+"')";
		try {
			rs = this.conn.ferSelect(sql);
			if (rs.next())
				numPar = rs.getInt("COUNT(*)");
			else
				numPar = 0;
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("Error SQL getEstadistiquesUsuari: "+e);
		}
		
		sql1 += "nom_guanyador from partides where jugador = '"+idUsuari+"' or contrincant = '"+idUsuari+"' and estat = 3";
		try {
			rs = this.conn.ferSelect(sql1);
			while (rs.next()) {
				String nom = rs.getString("nom_guanyador");
				if (nom != null) {
					if (nom.equals(idUsuari))
						numWin++;
					else if (nom.equals("taules"))
						numT++;
					else
						numLose++;
				}
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("Error SQL getEstadistiquesUsuari: "+e);
		}
		
		ranksql = "SELECT nom_guanyador FROM (SELECT count(*) as repes, p.nom_guanyador "
				+ "FROM partides p WHERE nom_guanyador IS NOT NULL AND estat = 3 " + 
				"GROUP BY p.nom_guanyador ORDER BY repes DESC) " + 
				"WHERE ROWNUM <= 10";
		try {
			rs = this.conn.ferSelect(ranksql);
			while (rs.next()) {
				String nom = rs.getString("nom_guanyador");
				if (nom != null) {
					rank.add(nom);
				}
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error SQL getEstadistiquesUsuari: "+e);
		}
		
		if (numLose==0)
			ratio = ((float)numWin);
		else
			ratio = (float)numWin/(float)numLose;
		
		numT = numPar - (numWin+numLose);
		res = ""+numPar+";"+numWin+";"+numLose+";"+numT+";"+ratio;
		if (rank.size()>0) {
			res += ";"+rank.size();
			for (String n : rank) {
				res += ";"+n;
			}
		}
		else 
			res+= ";0";
		return res;
	}

}
