package CapaPersistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartidesSQLOracle {

	private ConnectionSQLOracle conn;

	public PartidesSQLOracle(ConnectionSQLOracle connection) {
		this.conn = connection;
	}

	/**
	 * 
	 * // Option 0 no acceptat // Option 1 jugan // Option 2 proposat taules //
	 * Option 3 acabat // Option 4 rebutada
	 * 
	 * @param jugador
	 * @param contrincant
	 * @return
	 */
	public String crearPartidaNova(String jugador, String contrincant) {

		String id = null;
		ResultSet rs = null;
		ResultSet rsc = null;
		try {
			conn.setAutocommit(false);
		} catch (SQLException e) {
			System.out.println("Error de SQL partidaNova: " + e);
			return id;
		} catch (Exception e) {
			System.out.println("Error de getValue:  " + e);
			return null;
		}
		String sql = ConnectionSQLOracle.SQLINSERT;
		String sql2 = ConnectionSQLOracle.SQLSELECT;
		String sqlcompro = ConnectionSQLOracle.SQLSELECT;

		sqlcompro += "id from partides where ";
		sqlcompro += "jugador = '" + jugador + "' and contrincant = '" + contrincant + "' and "
				+ " estat between 0 AND 2";

		try {
			rsc = conn.ferSelect(sqlcompro);
			if (rsc.next()) {
				System.out.println("Hi han partides amb aquest contrincant.");
				return null;
			}
		} catch (SQLException e) {
			System.out.println("Error de SQL partidaNova: " + e);
			return null;
		}

		// Comprovar partida no existent amb el usuari
		//

		sql += "partides " + "(jugador,contrincant,data_inici,salvat,estat,torn) " + "VALUES ('" + jugador + "','"
				+ contrincant + "',sysdate,'" + new CapaDomini.Taulell(10).toString() + "',0,'" + jugador + "')";

		// select nomseq.currval from dual;
		sql2 += "partides_sequence.currval from dual";
		try {
			conn.crearInsert(sql);

			rs = conn.ferSelect(sql2);

			while (rs.next()) {
				id += "" + rs.getInt("currval");
			}

			conn.ferCommit();
			conn.setAutocommit(true);

		} catch (SQLException e) {
			System.out.println("Error de SQL crearPartidaNova dual: " + e);
			return null;
		} catch (Exception e) {
			System.out.println("Error de getValue dual:  " + e);
			return null;
		}

		return id;
	}

	/**
	 * retorna true si sa fet be el canvi, retorna false si hi ha hagut algun error
	 * 
	 * @param idPartida
	 * @param usuariTorn
	 * @return
	 */
	public boolean canviarTorn(String idPartida, String usuari) {

		//
		String sqlsel = ConnectionSQLOracle.SQLSELECT;
		String usuariTorn = "";
		String contrincant = "";
		String sql = null;
		sqlsel += "contrincant,torn FROM partides where id = " + idPartida;

		ResultSet rs = null;
		try {
			rs = conn.ferSelect(sqlsel);
			if (rs.next()) {
				usuariTorn = rs.getString("torn");
				contrincant = rs.getString("contrincant");
			} else {
				System.out.println("Error sql no hi ha dades");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error sql canviarTorn: " + e);
			return false;
		} catch (Exception e) {
			System.out.println("Error canviarTorn: " + e);
			return false;
		}

		if (usuariTorn.equals(usuari)) {
			usuariTorn = contrincant;
		} else {
			usuariTorn = usuari;
		}

		sql = ConnectionSQLOracle.SQLUPDATE + " partides SET torn = '" + usuariTorn + "'";
		sql += " WHERE id = " + idPartida + "";

		try {
			if (conn == null)
				return false;
			return conn.crearInsert(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Retorna null si no ha pogut carregar la partida de bbdd retorna string amb la
	 * partida /TODO falta comprovar
	 * 
	 * @param idPartida
	 * @return
	 */
	public String continuarPartida(String idPartida) {
		String res = null;

		String id = idPartida;
		ResultSet rsc = null;

		String sqlcompro = ConnectionSQLOracle.SQLSELECT;

		sqlcompro += "salvat from partides where ";
		sqlcompro += " id = " + id + "";

		try {
			rsc = conn.ferSelect(sqlcompro);
			if (rsc.next())
				res = rsc.getString("salvat");
		} catch (SQLException e) {
			System.out.println("Error SQL continaurPartida: " + e);
			return null;
		} catch (Exception e) {
			System.out.println("Error continaurPartida: " + e);
			return null;
		}
		return res;
	}

	/**
	 * 
	 * @param jugador
	 * @return
	 */
	public List<String> getSolicitudsPendents(String jugador) {

		List<String> res = new ArrayList<String>();

		ResultSet rs = null;
		String sqlcompro = ConnectionSQLOracle.SQLSELECT;

		sqlcompro += "jugador from partides where ";
		sqlcompro += "contrincant = '" + jugador + "' and estat = 0";

		try {
			rs = conn.ferSelect(sqlcompro);
			while (rs.next()) {
				res.add(rs.getString("JUGADOR"));
			}
		} catch (SQLException e) {
			System.out.println("Error SQL getSolicitudsPendents: " + e);
			return res;
		} catch (Exception e) {
			System.out.println("Error getSolicitudsPendents: " + e);
		}
		return res;
	}

	/**
	 * TODO comprovar
	 * 
	 * @param jugador
	 * @param contrincant
	 * @return
	 */
	public boolean acceptarSolicitud(String jugador, String contrincant) {

		String sql;
		sql = ConnectionSQLOracle.SQLUPDATE + " partides SET estat = 1" + " WHERE jugador = '" + contrincant
				+ "' and contrincant = '" + jugador + "'";
		try {
			if (conn == null)
				return false;
			return conn.crearInsert(sql);
		} catch (SQLException e) {
			System.out.println("Error sql acceptarSolicitud: " + e);
			return false;
		}
	}

	public boolean rebutjaSolicitud(String jugador, String contrincant) {

		String sql;
		ResultSet rs;
		String id = "";
		sql = "SELECT id FROM partides WHERE estat = 0 and (jugador = '" + contrincant + "'" + " and contrincant = '"
				+ jugador + "' or jugador = '"+jugador+"' and contrincant = '"+contrincant+"')";
		try {
			rs = conn.ferSelect(sql);
			if (rs.next())
				id = rs.getString("id");

			if (id.isEmpty()) {
				System.out.println("Error no hay partidas con ese id.");
				return false;
			}

		} catch (SQLException e) {
			System.out.println("Error SQL rebutja: " + e);
			return false;
		}
		
		System.out.println(id);
		String sql2 = "DELETE FROM partides WHERE id = " + id + "";

		try {
			rs = conn.ferSelect(sql);
			if (rs.next())
				id = rs.getString("id");

			if (id.isEmpty()) {
				System.out.println("Error no hay partidas con ese id.");
				return false;
			}
			
			conn.ferDelete(sql2);

		} catch (SQLException e) {
			System.out.println("Error SQL rebutja: " + e);
			return false;
		}

		return false;
	}

	/**
	 * Retorna les partides en curs de l'usuari que necessiten la seva atenció
	 * 
	 * @param jugador
	 * @return
	 */
	public List<String> getPartidesTorn(String jugador) {

		List<String> res = new ArrayList<String>();

		ResultSet rs = null;
		String sqlcompro = ConnectionSQLOracle.SQLSELECT;

		sqlcompro = "SELECT jugador, contrincant FROM partides WHERE (jugador = '" + jugador + "' "
				+ "or contrincant = '" + jugador + "') and torn = '" + jugador + "' and estat between 1 AND 2";
		String contrincant = "";
		String usuari = "";
		try {
			rs = conn.ferSelect(sqlcompro);
			while (rs.next()) {
				usuari = rs.getString("JUGADOR");
				contrincant = rs.getString("CONTRINCANT");
				if (contrincant.equals(jugador)) {
					res.add(usuari);
				} else
					res.add(contrincant);
			}
		} catch (SQLException e) {
			System.out.println("Error SQL getPartidesTorn: " + e);
			return res;
		} catch (Exception e) {
			System.out.println("Error getPartidesTorn: " + e);
		}
		return res;
	}

	/**
	 * Retorn un string de usuaris
	 * 
	 * @param jugador
	 * @return
	 */
	public List<String> getPartidesNoTorn(String jugador) {

		List<String> res = new ArrayList<String>();

		ResultSet rs = null;
		String sqlcompro = ConnectionSQLOracle.SQLSELECT;

		sqlcompro = "SELECT jugador, contrincant FROM partides WHERE (jugador = '" + jugador + "' "
				+ "or contrincant = '" + jugador + "') and torn != '" + jugador + "' and estat between 1 AND 2";
		String contrincant = "";
		String usuari = "";
		try {
			rs = conn.ferSelect(sqlcompro);
			while (rs.next()) {
				usuari = rs.getString("JUGADOR");
				contrincant = rs.getString("CONTRINCANT");
				if (contrincant.equals(jugador)) {
					res.add(usuari);
				} else
					res.add(contrincant);
			}
		} catch (SQLException e) {
			System.out.println("Error SQL getPartidesTorn: " + e);
			return res;
		} catch (Exception e) {
			System.out.println("Error getPartidesTorn: " + e);
		}
		return res;
	}

	/**
	 * retorna null si no hi ha, retorna Liststring
	 * 
	 * @param usuari
	 * @return
	 */
	public List<String> getPartidesAcabada(String usuari) {
		List<String> res = new ArrayList<String>();

		ResultSet rs = null;
		String sqlcompro = ConnectionSQLOracle.SQLSELECT;
		String nomguanyador = "";

		sqlcompro += "nom_guanyador from partides where ";
		sqlcompro += "jugador = '" + usuari + "' and estat = 3";

		try {
			rs = conn.ferSelect(sqlcompro);
			while (rs.next()) {
				nomguanyador = rs.getString("nom_guanyador");
				if (nomguanyador.equals(usuari))
					res.add(nomguanyador + ",guanya");
				else
					res.add(nomguanyador + ",perdut");
			}
		} catch (SQLException e) {
			System.out.println("Error SQL getPartidesAcabada: " + e);
			return null;
		} catch (Exception e) {
			System.out.println("Error getPartidesAcabada: " + e);
		}
		return res;
	}

	/**
	 * retorna null si no exiteix retorna id si hi ha sense acabar
	 * 
	 * @param idUsuari
	 * @param idContrincat
	 * @return
	 */
	public String getPartida(String jugador, String contrincant) {

		String res = null;
		ResultSet rs = null;
		String sqlcompro = ConnectionSQLOracle.SQLSELECT;

		sqlcompro = "SELECT id FROM PARTIDES WHERE" + " jugador = '" + jugador + "' and contrincant ='" + contrincant
				+ "' or " + "jugador = '" + contrincant + "' and contrincant ='" + jugador
				+ "' and estat between 1 and 2";

		try {
			rs = conn.ferSelect(sqlcompro);
			if (rs.next())
				return rs.getString("id");
		} catch (SQLException e) {
			System.out.println("Error sql getPartida: " + e);
			return null;
		}

		try {
			rs = conn.ferSelect(sqlcompro);
			if (rs.next())
				return rs.getString("id");
		} catch (SQLException e) {
			System.out.println("Error sql getPartida: " + e);
			return null;
		}

		return "";
	}

	/**
	 * String amb blanc/negre, color del qui juga retorna null si hi ha hagut algun
	 * problema
	 * 
	 * @param idSessio
	 * @param idPartida
	 * @return
	 */
	public String getColor(String idSessio, String idPartida) {
		String sql = null;
		ResultSet rs = null;
		sql = ConnectionSQLOracle.SQLSELECT + "jugador, contrincant FROM partides where id = " + idPartida;
		try {
			rs = conn.ferSelect(sql);

			if (rs.next())
				if (rs.getString("jugador").equals(idSessio))
					return "Red";
				else if (rs.getString("contrincant").equals(idSessio))
					return "Black";

		} catch (SQLException e) {
			System.out.println("Error SQL getColor: " + e);
			return null;

		} catch (Exception e) {
			System.out.println("Error getColor: " + e);
			return null;

		}
		return null;
	}

	/**
	 * String amb el tauler anterior al torn actual
	 * 
	 * @param idSessio
	 * @param idPartida
	 * @return
	 */
	public String getTaulerAnt(String idPartida) {
		String res = null;
		String sql2;;
		ResultSet rs = null;
		sql2 = "SELECT santerio FROM partides WHERE id ="+idPartida;
		try {
			rs = conn.ferSelect(sql2);
			if (rs.next())
				res = rs.getString("santerio");

		} catch (SQLException e) {
			System.out.println("Error SQL getTaulerAnt: " + e);
			return null;

		} catch (Exception e) {
			System.out.println("Error getTaulerAnt: " + e);
			return null;

		}
		return res;
	}

	public String getTaulerRes(String idSessio, String idPartida) {

		String res = null;
		String sql = null;
		ResultSet rs = null;
		sql = ConnectionSQLOracle.SQLSELECT + "salvat partides where id = " + idPartida;
		try {
			rs = conn.ferSelect(sql);

			if (rs.next())
				res = rs.getString("salva");

		} catch (SQLException e) {
			System.out.println("Error SQL getTaulerRes: " + e);
			return null;

		} catch (Exception e) {
			System.out.println("Error getTaulerRes: " + e);
			return null;

		}
		return res;
	}

	/**
	 * Guarda l'estat nou al tauler, posant l'estat actual a anterior
	 * 
	 * @param idPartida
	 * @param string
	 */
	public void guardarEstatTauler(String idPartida, String estatNou) {
		if (!this.actualitzarTaulell(idPartida, estatNou))
			System.out.println("No s'ha pogut guardar el nou estat.");
	}

	/**
	 * Guarda els moviments anteriors a la BD
	 * 
	 * @param idPartida
	 * @return true si s'ha guardat correctament
	 */
	public boolean guardarMovimentsAnt(String idPartida, String movsAnt) {
		
		String sqldel =  "DELETE FROM MOVIMENTS WHERE partides_id = "+idPartida+"";
		String sqlinsert;
		try {
			this.conn.ferDelete(sqldel);
		} catch (SQLException e) {
			System.out.println("0 dades borrades.");
		}
		
		String[] mov = movsAnt.split("/");
		String[] sep;
		String tipus,inix,iniy,fix,fiy;
		if (mov.length == 0) {
			System.out.println("No hi han moviments per guardar. SPLIT "+mov.length);
			return false;
		}
		
		for (String m : mov) {
			sep = m.split(";");
			if (sep.length == 0 || sep.length < 5) {
				System.out.println("No hi han moviments per guardar. SPLIT sep"+sep.length);
				return false;
			}
			tipus = sep[0];
			inix =  sep[1];
			iniy = sep[2];
			fix = sep[3];
			fiy = sep[4];
			sqlinsert =  "INSERT INTO MOVIMENTS (filaorigen,columnaorigen,filadesti,columnadesti,partides_id,tipus) "
					+ "VALUES ("+inix+","+iniy+","+fix+","+fiy+","+idPartida+",'"+tipus+"'";
			
			try {
				boolean rs = this.conn.crearInsert(sqlinsert);
				return rs;
			} catch (SQLException e) {
				System.out.println("Error SQL al fer insert gaurdarMovimentsAnt: "+e);
				return false;
			}
		}
		
		
		return false;
	}

	/**
	 * Retorna els moviments anteriors
	 * 
	 * @param idPartida
	 */
	public String getMovimentsAnt(String idPartida) {
		
		String sql = "SELECT filaorigen,columnaorigen,filadesti,columnadesti,tipus "
				+ "FROM MOVIMENTS WHERE partides_id = "+idPartida+"";
		
		ResultSet rs;
		String res;
		try {
			rs = this.conn.ferSelect(sql);
			res = "";
			while (rs.next()) {
				res += rs.getString("tipus")+";";
				res += rs.getString("filaorigen")+";";
				res += rs.getString("columnaorigen")+";";
				res += rs.getString("filadesti")+";";
				res += rs.getString("columnadesti")+";";
				res += "/";
			}
			
			return res;
			
		} catch (SQLException e) {
			
			System.out.println("Error SQL getMovimentsAnt: "+e);
			return null;
		} catch (Exception e) {
			System.out.println("Error getMovimentsAnt: "+e);
			return null;
		}
	}

	/**
	 * PRIVATES
	 */

	/**
	 * retorna true si ha pogut fer update del tauler de la partida retorna false si
	 * ha succeit algun problema
	 * 
	 * @param jugador
	 * @param tauler
	 * @return
	 */
	private boolean actualitzarTaulell(String idPartida, String tauler) {

		// String sql
		String sql1 = "", sql2 = "";
		ResultSet rs = null;
		String salvat = "";

		sql1 = ConnectionSQLOracle.SQLSELECT + " salvat from partides where id = " + idPartida;
		try {
			rs = conn.ferSelect(sql1);
			if (rs.next())
				salvat = rs.getString("salvat");
			else {
				System.out.println("Error actualitzarTaulell no hi ha dades compatibles");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error SQL actualitzarTaulell: " + e);
			return false;

		} catch (Exception e) {
			System.out.println("Error actualitzarTaulell: " + e);
			return false;

		}

		sql2 = ConnectionSQLOracle.SQLUPDATE + " partides SET " + "salvat = '" + tauler + "'" + ", santerio = '"
				+ salvat + "'";
		sql2 += " WHERE id = " + idPartida + "";

		try {
			if (conn == null)
				return false;
			return conn.crearInsert(sql2);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}
}
