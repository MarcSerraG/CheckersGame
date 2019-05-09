package CapaDomini;
import java.util.List;

public class Sessio {

	private String strUsuariNom;
	private Timer timInactivitat;
	private int iNumPartides;
	private boolean boolConnectat;
	
	public Sessio() {
		
	}
	
	public void timeOut() {
		
	}
	
	public boolean registrarUsuari() {
		
	}
	
	public List<String> veureUsuaris() {
		
		return null;
	}
	
	public List<Partida> actualitzarPartides() {
	
		return null;
	}
	
	public boolean convidarUsuariPartida(String strNom) {
		
		return true;
	}
	
	public Partida acceptarPartida(String strID) {
		
		return null;
	}
	
	public Partida continuarPartida(String strID) {
		
		return null;
	}
	
}
