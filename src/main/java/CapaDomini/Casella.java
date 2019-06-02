package CapaDomini;

public class Casella {
	
	//Variables
	private int intPosicioX;
	private int intPosicioY;
	private boolean boolTeFitxa;
	private Fitxa fitxa;
	
	//Constructor
	public Casella(int x, int y, boolean teFitxa, int tipus, int color){
	
		this.intPosicioX = x;
		this.intPosicioY = y;
		this.boolTeFitxa = teFitxa;
		
		if(teFitxa) {
			fitxa = tipus == 0 ? new Peo(color) : new Dama(color);
			}
	}
	//Gets & Sets
	public Fitxa getFitxa() {return fitxa;}
	public boolean getTeFitxa() {return boolTeFitxa;}
	public int getX() {return intPosicioX;}
	public int getY() {return intPosicioY;}
	public int[] getCoordenades() {
		int[] coordenades = {intPosicioX, intPosicioY};
		return coordenades;
		}
	public void setFitxa(Fitxa fitxa) {
		this.fitxa = fitxa;
		boolTeFitxa = true;
	}
	//posa la fitxa a null
	public void eliminarFitxa() {
		
		fitxa = null;
		boolTeFitxa = false;
	}
	//Retorna el valor de la fitxa envoltat per la casella
	public String toString() {
		
		if(this.boolTeFitxa) return fitxa.toString();
		else return "x";
	}
	public boolean equals(Object o) {
		
		if(!(o instanceof Casella)) return false;
		Casella cas = (Casella) o;
		if ((this.getX() == cas.getX()) && (this.getY()==cas.getY())) return true;
		return false;
	}
}
