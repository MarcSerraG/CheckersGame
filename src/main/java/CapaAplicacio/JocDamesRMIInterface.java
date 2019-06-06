package CapaAplicacio;

import java.rmi.Remote;
import java.rmi.RemoteException;

import CapaPersistencia.ConnectionSQLOracle;

public interface JocDamesRMIInterface extends Remote {

	String login(String user, String password) throws RemoteException;

	String registra(String user, String password) throws RemoteException;

	String logout(String idSessio) throws RemoteException;

	String reconnecta(String idSessio, String password) throws RemoteException;

	String getEstadistics(String idSessio) throws RemoteException;

	String getCandidatsSol(String idSessio) throws RemoteException;

	void enviaSol(String idSessio, String usuari) throws RemoteException;

	String solicituds(String idSessio) throws RemoteException;

	void acceptaSol(String idSessio, String usuari) throws RemoteException;

	void rebutjaSol(String idSessio, String usuari) throws RemoteException;

	String getPartidesTorn(String idSessio) throws RemoteException;

	String getPartidesNoTorn(String idSessio) throws RemoteException;

	String getPartidesAcabades(String idSessio) throws RemoteException;

	String triaPartida(String idSessio, String usuari) throws RemoteException;

	String obtenirColor(String idSessio, String idPartida) throws RemoteException;

	String obtenirTaulerAnt(String idSessio, String idPartida) throws RemoteException;

	String obtenirTaulerAct(String idSessio, String idPartida) throws RemoteException;

	String obtenirTaulerRes(String idSessio, String idPartida) throws RemoteException;

	String obtenirMovsAnt(String idSessio, String idPartida) throws RemoteException;

	String grabarTirada(String idSessio, String idPartida) throws RemoteException;

	String obtenirMovimentsPossibles(String idSessio, String idPartida) throws RemoteException;

	String ferMoviment(String idSessio, String idPartida, String posIni, String posFi) throws RemoteException;

	String ferDama(String idSessio, String idPartida, String pos) throws RemoteException;

	String ferBufa(String idSessio, String idPartida, String pos) throws RemoteException;

	String acceptaTaules(String idSessio, String idPartida) throws RemoteException;

	String proposaTaules(String idSessio, String idPartida) throws RemoteException;

	String movsPessa(String idSessio, String idPartida, String Pos) throws RemoteException;
	
	ConnectionSQLOracle getConnectionSQL() throws RemoteException;
}