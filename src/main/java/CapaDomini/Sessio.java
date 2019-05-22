package CapaDomini;
import java.util.*;

public class Sessio {

	private Usuari usrJugador; // L'usuari jugant
	private Timer timInactivitat;
	// private int iNumPartides; Length del set 
	private int iNumPendents; // Partides pendents de la teva atenció
	private boolean boolConnectat;
	private TimerTask task;
	private Set<Partida> setPartides;
	
	/**
	 * Construeix una sessió, possant el timer a 10 minuts del time-out
	 * en el cas de tenir moviments pendents, 20 minuts altrament.
	 * @param strNom Nom de l'usuari que utilitza la sessió
	 * @param iPartides Número de partides que té l'usuari
	 * @param iPendents Número de partides pendents de l'atenció de l'usuari
	 * @throws IllegalArgumentException Nom no pot ser null, ni tenir menys de tres caracters. Partides no pot ser menor que zero.
	 */
	public Sessio(String strNom, Set<Partida> setPartides, int iPendents) throws IllegalArgumentException {
		/* Comprovació d'arguments */
		if (strNom == null) {
			throw new IllegalArgumentException("El nom no pot ser null.");
		}
		if (strNom.length() < 3) {
			throw new IllegalArgumentException("El nom no pot tenir menys de tres caracters.");
		}
		if (iPendents < 0) {
			throw new IllegalArgumentException("Les partides pendents no poden ser menys de 0.");
		}
		
		/* Assignació d'atributs */
		this.usrJugador = new Usuari(strNom);
		this.iNumPendents = iPendents;
		this.boolConnectat = true;
		this.setPartides = new HashSet<Partida>(setPartides);
		
		/* Configurem el timer */
		this.timInactivitat = new Timer();
		this.task = new TimerTask() {
	        @Override
	        public void run()
	        {
	            /* TO-DO: Disconnect task... Com? Potser el timer hauria d'estar al main?*/
	        	boolConnectat = false;
	        }
		};
	}
	
	public int getNumPartides() {return this.setPartides.size();}
	public String getUsuariNom() {return this.usrJugador.getNom();}
	public int getPartidesPendents() {return this.iNumPendents;}
	
	
	public void timeOut() {
		this.timInactivitat.cancel();
		this.timInactivitat.purge();
		this.actualitzarPartides();
		
		if (this.iNumPendents > 0) {
			this.timInactivitat.schedule(task, 10*1000*60); // 10 minuts
		}
		else {
			this.timInactivitat.schedule(task, 20*1000*60); // 20 minuts
		}
	}
	
	
	public Set<String> veureUsuaris() {
		
		return null;
	}
	
	public void actualitzarPartides() {
	
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
