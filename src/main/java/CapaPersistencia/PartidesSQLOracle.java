package CapaPersistencia;

import java.sql.Statement;
import java.util.Set;

import CapaDomini.Partida;
import CapaDomini.Usuari;

public class PartidesSQLOracle {
	private ConnectionSQLOracle conn;
	
	public PartidesSQLOracle(ConnectionSQLOracle connection) {
		this.conn = connection;
	}

	public boolean crearPartidaNova(Usuari jugador, Usuari contrincant) {
		
		Statement st = null;
		String sql = ConnectionSQLOracle.SQLINSERT;
		String sql2 = ConnectionSQLOracle.SQLINSERT;
		
		Set<Partida> ptPd = getPartidesPendents(jugador);
		Set<Partida> ptCd = getPartidesPendents(contrincant);
		if (ptPd != null && ptCd != null) {
			if (ptPd.retainAll(ptCd))
				return false;
		}
		sql += "partides VALUES (getDate,null,0,null,null,0)";
		//sql2 += "usuaripartides ("
		return true;
	}
	
	public Partida continuarPartida(String idPartida, String nom) {
		
		return null;
	}
	
	public Set<Partida> getPartidesPendents(Usuari jugador){
		return null;
	}
}
