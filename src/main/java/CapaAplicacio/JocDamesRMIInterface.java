package CapaAplicacio;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JocDamesRMIInterface {

	String login(String user, String password);

	String registra(String user, String password);

	String logout(String idSessio);

	String reconnecta(String idSessio, String password);

	String getEstadistics(String idSessio);

	String getCandidatsSol(String idSessio);

	void enviaSol(String idSessio, String usuari);

	String solicituds(String idSessio);

	void acceptaSol(String idSessio, String usuari);

	void rebutjaSol(String idSessio, String usuari);

	String getPartidesTorn(String idSessio);

	String getPartidesNoTorn(String idSessio);

	String getPartidesAcabades(String idSessio);

	String triaPartida(String idSessio, String usuari);

	String obtenirColor(String idSessio, String idPartida);

	String obtenirTaulerAnt(String idSessio, String idPartida);

	String obtenirTaulerAct(String idSessio, String idPartida);

	String obtenirTaulerRes(String idSessio, String idPartida);

	String obtenirMovsAnt(String idSessio, String idPartida);

	String grabarTirada(String idSessio, String idPartida);

	String obtenirMovimentsPossibles(String idSessio, String idPartida);

	String ferMoviment(String idSessio, String idPartida, String posIni, String posFi);

	String ferDama(String idSessio, String idPartida, String pos);

	String ferBufa(String idSessio, String idPartida, String pos);

	String acceptaTaules(String idSessio, String idPartida);

	String proposaTaules(String idSessio, String idPartida);

	String movsPessa(String idSessio, String idPartida, String Pos);

	ConnectionSQLOracle getConnectionSQL();

}