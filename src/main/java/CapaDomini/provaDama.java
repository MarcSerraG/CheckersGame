package CapaDomini;

import java.util.List;
import java.util.Vector;

public class provaDama{
	
	public static void main (String[] args) {
	
		Dama d = new Dama(0);
		List<int[]> moviments = d.possiblesMoviments(3, 3);
		for(int[] mov : moviments) System.out.println(mov[0] + " " + mov[1]);
	}

}
