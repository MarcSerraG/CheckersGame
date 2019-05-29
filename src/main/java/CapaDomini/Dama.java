package CapaDomini;

import java.util.List;
import java.util.Vector;

public class Dama extends Fitxa{
	
	//Constructor
	//0 = white, 1 = black
	public Dama (int iColor) throws IllegalArgumentException{
		
		if(iColor <0 || iColor > 1) throw new IllegalArgumentException("Color only can be 0 or 1");
		this.iID = this.hashCode();
		this.iColor = iColor;
	}
	//Torna una llista de moviments possibles de dama
	public List<int[]> possiblesMoviments(int x, int y) throws IllegalArgumentException{
		
		if (x < 0 || x > 9 || y < 0 || y > 9) throw new IllegalArgumentException("Position out of bounds");
		
			Vector <int[]> llista = new Vector<int[]>();
			//Go through the matrix to get all positions
			for (int i = -9; i <= 9; i++) {
				for (int j = -9; j <= 9; j++) {
					//Select only diagonal movements
					if((Math.abs(i)+Math.abs(j))%2 == 0) {
						int fila = x+i;
						int columna = y+j;
						//Select only positions inside the game
						if (!(fila < 0 || fila > 9 || columna < 0 || columna > 9) && (!(fila==x && columna == y))) {
							//Add position to list
							int[] mov = {fila, columna};
							llista.add(mov);
						}
					}
				}
			}
		return llista;
	}
	//Retorna D per dama blanca i d per dama negra
	public String toString() {
		if(iColor == 0) return "D";
		else return "d";
	}
}
