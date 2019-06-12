package CapaPresentacio;

import CapaPersistencia.ConnectionSQLOracle;

public class RestAPI implements APIInterface{

	String login(String user, String password) {
		
		return "";
	}

	String registra(String user, String password) {
		
		return "";
	}

	String logout(String idSessio) {
		
		return "";
	}

	String reconnecta(String idSessio, String password) {
		
		return "";
	}

	String getEstadistics(String idSessio) {
		
		return "";
	}

	String getCandidatsSol(String idSessio) {
		
		return "";
	}

	void enviaSol(String idSessio, String usuari) {}

	String solicituds(String idSessio) {
		
		return "";
	}

	void acceptaSol(String idSessio, String usuari) {}

	void rebutjaSol(String idSessio, String usuari) {}

	String getPartidesTorn(String idSessio) {
		
		return "";
	}

	String getPartidesNoTorn(String idSessio) {
		
		return "";
	}

	String getPartidesAcabades(String idSessio) {
		
		return "";
	}

	String triaPartida(String idSessio, String usuari) {
		
		return "";
	}

	String obtenirColor(String idSessio, String idPartida) {
		
		return "";
	}

	String obtenirTaulerAnt(String idSessio, String idPartida) {
		
		return "";
	}

	String obtenirTaulerAct(String idSessio, String idPartida) {
		
		return "";
	}

	String obtenirTaulerRes(String idSessio, String idPartida) {
		
		return "";
	}

	String obtenirMovsAnt(String idSessio, String idPartida) {
		
		return "";
	}

	String grabarTirada(String idSessio, String idPartida) {
		
		return "";
	}

	String obtenirMovimentsPossibles(String idSessio, String idPartida) {
		
		return "";
	}

	String ferMoviment(String idSessio, String idPartida, String posIni, String posFi) {
		
		return "";
	}

	String ferDama(String idSessio, String idPartida, String pos) {
		
		return "";
	}

	String ferBufa(String idSessio, String idPartida, String pos) {
		
		return "";
	}

	String acceptaTaules(String idSessio, String idPartida) {
		
		return "";
	}

	String proposaTaules(String idSessio, String idPartida) {
		
		return "";
	}

	String movsPessa(String idSessio, String idPartida, String Pos) {
		
		return "";
	}
	
	ConnectionSQLOracle getConnectionSQL() throws Exception {
		
		return new ConnectionSQLOracle();
	}

}
