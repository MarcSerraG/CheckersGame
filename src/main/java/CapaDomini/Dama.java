package CapaDomini;

import java.util.List;
import java.util.Vector;

public class Dama extends Fitxa{
	
	//0 = white, 1 = black
	public Dama (String strID, int strColor) {
		
		this.strID = strID;
		this.strColor = strColor;
	}


	public List<int[]> possiblesMoviments(int x, int y) throws Exception{
		

		if (x < 0 || x > 9 || y < 0 || y > 9) throw new IllegalArgumentException("Position out of bounds");
		
		try {
			Vector <int[]> llista = new Vector<int[]>();
	
			for (int i = 0; i <= 9; i++) {
				for (int j = 0; j <= 9; j++) {
					int[] mov = {0 + i, 0 + j};
					llista.add(mov);
				}
			}
		return llista;
		}
		
		catch(Exception e) {
			throw new Exception();
		}
	}
}
