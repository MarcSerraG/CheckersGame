package CapaDomini;

import java.util.ArrayList;
import java.util.List;


/*
 * Moviments son Strings amb la següent estructura:
 * moviment;X;Y;X;Y
 * tal que:
 * moure;xInicial;yInicial;xFinal;yFinal
 * 
 * Bufar i fer dama nomes té una posició:
 * bufa;x;y
 * dama;x;y
 * 
 */
public class Moviments {
	
	private List<String> listMovs; // Moviments torn actual, encara per fer...
	private List<String> listMovsAnt; // Moviments torn anterior
	private Taulell taulActual; // Taulell torn actual
	private Taulell taulAnt; // Taulell torn anterior
	private boolean bufaPossible;
	
	public Moviments(String movsAnt, String taulActual, String taulAnterior) {
		this.listMovsAnt = new ArrayList<String>();
		for (String mov : movsAnt.split("/")) {
			this.listMovsAnt.add(mov);
		}
		
		this.listMovs = new ArrayList<String>();
		this.taulActual = new Taulell(taulActual);
		this.taulAnt = new Taulell(taulAnterior);
	}
	
	public Taulell getTaulellActual() {return this.taulActual;}
	public List<String> getMovimentsAct() {return this.listMovs;}
	
	public boolean ferBufa() {
		return false;
	}
}