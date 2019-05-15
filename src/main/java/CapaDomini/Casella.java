package CapaDomini;

public class Casella {
	
	//Variables
	private int intPosicioX;
	private int intPosicioY;
	private boolean boolTeFitxa;
	private Fitxa fitxa;
	
	//Constructor
	public Casella(int x, int y, boolean teFitxa){
	
		this.intPosicioX = x;
		this.intPosicioY = y;
		this.boolTeFitxa = teFitxa;
		
		if(teFitxa) {
			if (x < 4) fitxa = new Peo(1);
			else fitxa = new Peo(0);
		}
	}
	//Gets
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
	//Crea una dama quan un peo arriba al final del taulell
	public void afegirDama(Fitxa fitxa) throws IllegalArgumentException{
	
		int color = fitxa.iColor;			
		fitxa = new Dama(color);
	}
	//Retorna el valor de la fitxa envoltat per la casella
	public String toString() {
		
		if(this.boolTeFitxa) return "["+fitxa+"]";
		else return "[ ]";
	}
}
