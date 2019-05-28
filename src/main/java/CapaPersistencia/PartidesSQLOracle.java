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
		sql = ConnectionSQLOracle.SQLUPDATE + " partides SET connectat = 1" + " WHERE jugador = '" + contrincant
				+ "' and contrincant = '" + jugador + "'";
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
	 * Retorna les partides en curs de l'usuari que necessiten la seva atenció
	 * 
	 * @param jugador
	 * @return
	 */
	public List<String> getPartidesTorn(String jugador) {

		List<String> res = new ArrayList<String>();

		ResultSet rs = null;
		String sqlcompro = ConnectionSQLOracle.SQLSELECT;

		sqlcompro += "contrincant from partides where ";
		sqlcompro += "jugador = '" + jugador + "' and torn = '" + jugador + "'";

		try {
			rs = conn.ferSelect(sqlcompro);
			while (rs.next()) {
				res.add(rs.getString("CONTRINCANT"));
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

		sqlcompro += "contrincant from partides where ";
		sqlcompro += "jugador = '" + jugador + "' and torn != '" + jugador + "'";

		try {
			rs = conn.ferSelect(sqlcompro);
			while (rs.next()) {
				res.add(rs.getString("CONTRINCANT"));
			}
		} catch (SQLException e) {
			System.out.println("Error SQL getPartidesNoTorn: " + e);
			return null;
		} catch (Exception e) {
			System.out.println("Error getPartidesNoTorn: " + e);
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
				nomguanyador = rs.getString(1);
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

		sqlcompro += "id from partides where ";
		sqlcompro += " jugador = '" + jugador + " and contrincant = '" + contrincant + "' and "
				+ " estat between 0 AND 2";

		try {
			rs = conn.ferSelect(sqlcompro);
			if (rs.next())
				res = rs.getString(0);
		} catch (SQLException e) {
			System.out.println("Error sql getPartida: " + e);
			return null;
		}
		return res;
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
					return "blanc";
				else if (rs.getString("contrincant").equals(idSessio))
					return "negre";

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
	public String getTaulerAnt(String idSessio, String idPartida) {
		String res = null;
		String sql = null;
		ResultSet rs = null;
		sql = ConnectionSQLOracle.SQLSELECT + "santerio partides where id = " + idPartida;
		try {
			rs = conn.ferSelect(sql);

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
