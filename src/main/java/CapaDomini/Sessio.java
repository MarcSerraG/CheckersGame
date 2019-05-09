package CapaDomini;
import java.util.List;
import java.util.TimerTask;
import java.util.Timer;

public class Sessio {

	private String strUsuariNom;
	private Timer timInactivitat;
	private int iNumPartides;
	private int iNumPendents; // Partides pendents de la teva atenció
	private boolean boolConnectat;
	private TimerTask task;
	
	/**
	 * Construeix una sessió, possant el timer a 10 minuts del time-out
	 * en el cas de tenir moviments pendents, 20 minuts altrament.
	 * @param strNom Nom de l'usuari que utilitza la sessió
	 * @param iPartides Número de partides que té l'usuari
	 * @param iPendents Número de partides pendents de l'atenció de l'usuari
	 * @throws IllegalArgumentException Nom no pot ser null, ni tenir menys de tres caracters. Partides no pot ser menor que zero.
	 */
	public Sessio(String strNom, int iPartides, int iPendents) throws IllegalArgumentException {
		/* Comprovació d'arguments */
		if (strNom == null) {
			throw new IllegalArgumentException("El nom no pot ser null.");
		}
		if (strNom.length() < 3) {
			throw new IllegalArgumentException("El nom no pot tenir menys de tres caracters.");
		}
		if (iPartides < 0) {
			throw new IllegalArgumentException("L'usuari no pot tenir menys de 0 partides.");
		}
		if (iPendents < 0) {
			throw new IllegalArgumentException("Les partides pendents no poden ser menys de 0.");
		}
		
		/* Assignació d'atributs */
		this.strUsuariNom = strNom;
		this.iNumPartides = iPartides;
		this.iNumPendents = iPendents;
		
		/* Configurem el timer */
		this.timInactivitat = new Timer();
		this.task = new TimerTask() {
	        @Override
	        public void run()
	        {
	            timeOut();
	        }
		};
		this.timInactivitat.schedule(task, 10*1000*60);
	}
	
	public void timeOut() {
		
	}
	
	public boolean registrarUsuari() {
		
		return false;
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
