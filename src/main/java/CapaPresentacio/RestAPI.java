package CapaPresentacio;
import CapaAplicacio.JocDamesInterficie;
 

public class RestAPI implements JocDamesInterficie{

	@Override
	public String login(String nom, String passwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registra(String nom, String passwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String logout(String idSessio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String reconecta(String idSessio, String passwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEstadistics(String idSessio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCandidatsSol(String idSessio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String enviaSol(String idSessio, String usuari) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String solicituds(String idSessio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String acceptaSol(String idSessio, String usuari) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rebutjaSol(String idSessio, String usuari) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPartidesTorn(String idSessio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPartidesNoTorn(String idSessio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPartidesAcabades(String idSessio) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String triaPartida(String idSessio, String usuari) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenirColor(String idSessio, String idPartida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenirTaulerAnt(String idSessio, String idPartida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenirTaulerAct(String idSessio, String idPartida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenirTaulerRes(String idSessio, String idPartida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenirMovsAnt(String idSessio, String idPartida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String grabarTirada(String idSessio, String idPartida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String obtenirMovimentsPossibles(String idSessio, String idPartida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ferMoviment(String idSessio, String idPartida, String posIni, String posFi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ferDama(String idSessio, String idPartida, String pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String ferBufa(String idSessio, String idPartida, String pos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String acceptaTaules(String idSessio, String idPartida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String proposaTaules(String idSessio, String idPartida) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String movsPessa(String idSessio, String idPartida, String pos) {
		// TODO Auto-generated method stub
		return null;
	}
}
 
