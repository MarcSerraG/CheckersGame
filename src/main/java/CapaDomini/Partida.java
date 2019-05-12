package CapaDomini;

public class Partida {

	private String strID;
	private String strContrincant;
	private String strUsuariTorn;
	private boolean boolPartidaEnCurs;
	
	public Partida() {
		
	}
	
	public String getID() {return this.strID;}
	public String getContrincant() {return this.strContrincant;}
	public String getUsuariTorn() {return this.strUsuariTorn;}
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
		
		return true;
	}
	
	public boolean proposarTaules() {
		
		return true;
	}
	
	public void acabarPartida () {
		
	}
}
