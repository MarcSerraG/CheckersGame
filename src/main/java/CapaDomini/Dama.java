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
			
		int[] mov1 = {x-1,y+1};
		int[] mov2 = {x-1,y-1};
		int[] mov3 = {x+1,y+1};
		int[] mov4 = {x+1,y-1};
		
		//Select only positions inside the game
		if(!(mov1[0]<0 || mov1[0]>9 || mov1[1]<0 || mov1[1]>9)) llista.add(mov1);
		if(!(mov2[0]<0 || mov2[0]>9 || mov2[1]<0 || mov2[1]>9)) llista.add(mov2);
		if(!(mov3[0]<0 || mov3[0]>9 || mov3[1]<0 || mov3[1]>9)) llista.add(mov3);
		if(!(mov4[0]<0 || mov4[0]>9 || mov4[1]<0 || mov4[1]>9)) llista.add(mov4);
		return llista;
	}
	//Retorna D per dama blanca i d per dama negra
	public String toString() {
		if(iColor == 0) return "D";
		else return "d";
	}
}
