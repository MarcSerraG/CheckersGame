package CapaPersistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

import CapaDomini.Partida;
import CapaDomini.Taulell;
import CapaDomini.Usuari;

public class PartidesSQLOracle {
	
	private ConnectionSQLOracle conn;
	
	public PartidesSQLOracle(ConnectionSQLOracle connection) {
		this.conn = connection;
	}

	/**
	 * Return ID sino null
	 * @param jugador
	 * @param contrincant
	 * @return
	 */
	public String crearPartidaNova(Usuari jugador, Usuari contrincant) {
		
		String id = null;
		ResultSet rs = null;
		ResultSet rsc = null;
		try { conn.setAutocommit(false); }
		catch (SQLException e) {
			return id;
		}
		String sql = ConnectionSQLOracle.SQLINSERT;
		String sql2 = ConnectionSQLOracle.SQLSELECT;
		String sqlcompro = ConnectionSQLOracle.SQLSELECT;
		
		sqlcompro += "(id) from partides where ";
		sqlcompro += " jugador = '"+jugador+" and contrincant = '"+contrincant+"' and "
				+ " estat between 0 AND 2";
		
		try {
			rsc = conn.ferSelect(sqlcompro);
			if (rsc.next())
				return null;	
		} catch (SQLException e) {
			
		}
		
		//Option 0 no acceptat
		//Option 1 jugan
		//Option 3 acabat
		//Option 2 proposat taules
		//Comprovar partida no existent amb el usuari
		//
		
		sql += "partides "
				+ "(jugador,contrincant,data_inici,salvat,estat,torn) "
				+ "VALUES ('"+jugador+"','"+contrincant+"',sysdate,'"+getTaullelnou().toString()+"',0,'"+jugador+"')";
		
		//select nomseq.currval from dual;
		sql2  += "partides_sequence.currval from dual";
		try {
			conn.crearInsert(sql);
			
			
			rs = conn.ferSelect(sql2);
			
			while (rs.next()) {
				id += ""+rs.getInt(0);
			}
			
			conn.ferCommit();
			conn.setAutocommit(true);
			
		} catch (SQLException e){
			return id;
		}
		return id;
	}
	
	public Taulell continuarPartida(String idPartida) {
		
		Taulell tb = new Taulell();
		
		
		return tb;
	}
	
	/**
	 * Retorna les partides en curs de l'usuari que necessiten la seva atenció
	 * @param jugador
	 * @return
	 */
	public List<String> getPartidesTorn(Usuari jugador){
		return null;
	}
	
	/**
	 * Retorn un string de usuaris
	 * independentment de si es el seu torn o no
	 * @param jugador
	 * @return
	 */
	public List<String> getPartidesNoTorn(Usuari jugador){
		
		return null;
	}
	
	private Taulell getTaullelnou() {
		
		Taulell p = new Taulell();
		return p;
	}
	
	private Taulell carregaPartida(String idPartida) {
		Taulell tb = new Taulell();
		
		//recontstruir taulell a partir dstring
		//tb.reconstruirTaulell(text);
		return tb;
	}
	
	
}
