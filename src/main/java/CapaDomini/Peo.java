package CapaDomini;

import java.util.List;
import java.util.Vector;

public class Peo extends Fitxa{
	
	//0 = white, 1 = black
	public Peo (String strID, int strColor) throws IllegalArgumentException{
		//Falta especificar strID (pot ser int?)
		if(strColor <0 || strColor > 1) throw new IllegalArgumentException("Color only can be 0 or 1");
		
		this.strID = strID;
		this.strColor = strColor;
	}

	public List<int[]> possiblesMoviments(int x, int y) throws Exception{
		
		if (x < 0 || x > 9 || y < 0 || y > 9) throw new IllegalArgumentException("Position out of bounds");
		
		try {
			Vector <int[]> llista = new Vector<int[]>();
			
			switch (this.strColor) {
			//In case it is white it can only go up
			case 0: 
				int mov1[] = {x+1, y+1};
				int mov2[] = {x+1, y-1};
				llista.add(mov1);
				llista.add(mov2);
			//In case it is black it can only go down	
			case 1:
				int mov3[] = {x+1, y+1};
				int mov4[] = {x+1, y-1};
				llista.add(mov3);
				llista.add(mov4);
			}
			return llista;
		}
		
		catch(Exception e) {
			throw new Exception();
		}
	}
	
}
