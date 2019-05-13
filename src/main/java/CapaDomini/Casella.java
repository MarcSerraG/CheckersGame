package CapaDomini;

public class Casella {
	
	//Variables
	private static int ident = 0;
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
			if (x < 4) {
				fitxa = new Peo(ident, 1);
				ident++;
			}
			else {
				fitxa = new Peo(ident, 0);
				ident++;
			}
		}
	}
	
	//Gets
	public Fitxa getFitxa() {return fitxa;}
	public boolean getTeFitxa() {return boolTeFitxa;}
	public int getX() {return intPosicioX;}
	public int getY() {return intPosicioY;}
	
	//posa la fitxa a null
	public void eliminarFitxa() {
		
		fitxa = null;
	}
	
	//Crea una dama quan un peo arriba al final del taulell
	public void afegirDama(Fitxa fitxa) throws IllegalArgumentException{
	
		int ID = fitxa.iID;
		int color = fitxa.iColor;
				
		fitxa = new Dama(ID, color);
	
	}
	public String toString() {
		
		if(this.boolTeFitxa) return "["+fitxa+"]";
		else return "[ ]";
	}
}
