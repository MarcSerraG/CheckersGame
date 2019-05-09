package CapaDomini;

import java.util.List;
import java.util.Vector;

public class Dama extends Fitxa{
	
	//0 = white, 1 = black
	public Dama (String strID, int strColor) throws IllegalArgumentException{
		//Falta especificar strID (pot ser int?)
		if(strColor <0 || strColor > 1) throw new IllegalArgumentException("Color only can be 0 or 1");
		
		this.strID = strID;
		this.strColor = strColor;
	}

	public List<int[]> possiblesMoviments(int x, int y) throws IllegalArgumentException{
		
		if (x < 0 || x > 9 || y < 0 || y > 9) throw new IllegalArgumentException("Position out of bounds");
		
			Vector <int[]> llista = new Vector<int[]>();
			//Go through the matrix to get all positions
			for (int i = 0; i <= 9; i++) {
				for (int j = 0; j <= 9; j++) {
					//Select only diagonal movements
					if(Math.abs(i) == Math.abs(j)) {
						
						int fila = x+i;
						int columna = y+j;
						//Select only positions inside the game
						if (fila < 0 || fila > 9 || columna < 0 || columna > 9) {
							//Add position to list
							int[] mov = {x + i, y + j};
							llista.add(mov);
						}
					}
				}
			}
		return llista;
	}	
}
