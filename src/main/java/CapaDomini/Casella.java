package CapaDomini;

public class Casella {

	private int intPosicioX;
	private int intPosicioY;
	private boolean boolTeFitxa;
	private Fitxa fitxa;
	
	public Casella(int x, int y, boolean teFitxa){
	
		this.intPosicioX = x;
		this.intPosicioY = y;
		this.boolTeFitxa = teFitxa;
	}
	
	public Fitxa getFitxa() {
		
		return fitxa;
	}
	
	public boolean getTeFitxa() {
		
		return boolTeFitxa;
	}
	
	public int getX() {
		
		return intPosicioX;
	}
	
	public int getY() {
		
		return intPosicioY;
	}
	
	public boolean eliminarFitxa() {
		
		if(boolTeFitxa) this.boolTeFitxa = false;
		else return false;
		return true;
	}
	
	public boolean afegirFitxa(Fitxa tipus) throws Exception{
		
		try {
			if(!boolTeFitxa) {
				this.boolTeFitxa = true;
				this.fitxa = tipus;
			}
			else return false;
			return true;
		}
		
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException();
		
		}
	}
}
