package CapaDomini;

import java.util.List;
import java.util.Vector;

public class provaDama{
	
	public static void main (String[] args) {
	
		Usuari u1 = new Usuari("Blanques");
		Usuari u2 = new Usuari("Negres");
		Partida partida = new Partida("0000", u1, u2);
		Dama d = new Dama(0);
		partida.getTaulell().seleccionarCasella(6, 1).setFitxa(d);
		
		List<int[]> mov = d.possiblesMoviments(6, 1, partida.getTaulell().getMatriu());
		for(int[] i : mov) System.out.println(i[0] + " "+ i[1]);
		
	}

}
