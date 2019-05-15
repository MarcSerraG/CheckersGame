package CapaDomini;

public class Partida {

	private String strID;
	private Usuari usrJugador; // Blancas
	private Usuari usrContrincant; // Negras
	private Usuari usrUsuariTorn;
	private boolean boolPartidaEnCurs;
	
	public Partida(String strID, Usuari usrJugador, Usuari usrContrincant) {
		this.strID = strID;
		this.usrJugador = usrJugador;
		this.usrContrincant = usrContrincant;
		this.usrUsuariTorn = this.usrJugador;
	}
	
	public String getID() {return this.strID;}
	public Usuari getJugador() {return this.usrJugador;}
	public Usuari getContrincant() {return this.usrContrincant;}
	public Usuari getUsuariTorn() {return this.usrUsuariTorn;}
	public boolean getPartidaEnCurs() {return this.boolPartidaEnCurs;}
	
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
}
