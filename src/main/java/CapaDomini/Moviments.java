package CapaDomini;

import java.util.ArrayList;
import java.util.List;


/*
 * Moviments son Strings amb la següent estructura:
 * moviment;X;Y;X;Y
 * tal que:
 * moure;xInicial;yInicial;xFinal;yFinal
 * 
 * Bufar nomes té una posició:
 * bufa;x;y
 * 
 */
public class Moviments {
	
	private List<String> listMovs; // Moviments torn actual, encara per fer...
	private List<String> listMovsAnt; // Moviments torn anterior
	private Taulell taulActual; // Taulell torn actual
	private Taulell taulAnt; // Taulell torn anterior
	
	public Moviments(String movsAnt, String taulActual, String taulAnterior) {
		this.listMovsAnt = new ArrayList<String>();
		for (String mov : movsAnt.split("/")) {
			this.listMovsAnt.add(mov);
		}
		
		this.listMovs = new ArrayList<String>();
		
	}
}
