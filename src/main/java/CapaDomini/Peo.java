package CapaDomini;

import java.util.List;
import java.util.Vector;

public class Peo extends Fitxa{
	
	//Constructor
	//0 = white, 1 = black
	public Peo (int iColor) throws IllegalArgumentException{
		
		if(iColor < 0 || iColor > 1) throw new IllegalArgumentException("Color only can  be 0 or 1");
		
		this.iID = this.hashCode();
		this.iColor = iColor;
	}
	//Torna una llista de moviments possibles de peo
	public List<int[]> possiblesMoviments(int x, int y) throws IllegalArgumentException{
		
		if (x < 0 || x > 9 || y < 0 || y > 9) throw new IllegalArgumentException("Position out of bounds");
		
		Vector <int[]> llista = new Vector<int[]>();
		int mov1[] = new int[2];
		int mov2[] = new int[2];
		
		switch (this.iColor) {
		//In case it is white it can only go up
			case 0: 
				mov1[0] = x-1;
				mov1[1] = y+1;
				mov2[0] = x-1;
				mov2[1] = y-1;
				break;
				
			//In case it is black it can only go down	
			case 1:
				mov1[0] = x+1;
				mov1[1] = y+1;
				mov2[0] = x+1;
				mov2[1] = y-1;
				break;
		}
		//Select only positions inside the game
		if(!(mov1[0]<0 || mov1[0]>9 || mov1[1]<0 || mov1[1]>9))
		llista.add(mov1);
		if(!(mov2[0]<0 || mov2[0]>9 || mov2[1]<0 || mov2[1]>9))
		llista.add(mov2);
		return llista;
	}
	//Torna el color del peo
	public String toString() {
		
		return String.valueOf(iColor);
	}
}
