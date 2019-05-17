package CapaDomini;

public class Partida {

	private String strID;
	private Usuari usrJugador; // Blancas
	private Usuari usrContrincant; // Negras
	private Usuari usrUsuariTorn;
	private boolean boolPartidaEnCurs;
	private Taulell taulell;
	
	public Partida(String strID, Usuari usrJugador, Usuari usrContrincant) {
		this.strID = strID;
		this.usrJugador = usrJugador;
		this.usrContrincant = usrContrincant;
		this.usrUsuariTorn = this.usrJugador;
		this.taulell = new Taulell();
	}
	
	public String getID() {return this.strID;}
	public Usuari getJugador() {return this.usrJugador;}
	public Usuari getContrincant() {return this.usrContrincant;}
	public Usuari getUsuariTorn() {return this.usrUsuariTorn;}
	public boolean getPartidaEnCurs() {return this.boolPartidaEnCurs;}
	public Taulell getTaulell() {return this.taulell;}
	//Comprova els moviments que pot fer un jugador fins al final del seu torn
	public void movimentTorn(Casella casOrigen, Casella casDesti) {
		
		if (this.usrUsuariTorn == usrJugador && casOrigen.getFitxa().iColor == 1) throw new IllegalArgumentException("Invalid color for this user");
		if (this.usrUsuariTorn == usrContrincant && casOrigen.getFitxa().iColor == 0) throw new IllegalArgumentException("Invalid color for this user");
		
		boolean potMoure = taulell.moviment(casOrigen, casDesti);
		
		if(!potMoure) {
			if(usrUsuariTorn == usrJugador) usrUsuariTorn = usrContrincant;
			else usrUsuariTorn = usrJugador;
		}
	}
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (!(o instanceof Partida)) {
			return false;
		}
		
		return this.strID.equals(((Partida)o).strID);
	}
	
	@Override
	public int hashCode() {
		return this.strID.hashCode();
	}
	
	public boolean comprovarTorn() {
		return this.usrJugador.equals(this.usrUsuariTorn);
	}
	
	public boolean proposarTaules() {
		
		return true;
	}
	
	public void acabarPartida () {
		
	}
	public String toString() {
		
		String torn = ("Torn del jugador: " + usrUsuariTorn + "\n");
		return torn + taulell;
		
	}
}
