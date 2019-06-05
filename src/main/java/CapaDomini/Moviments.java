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
	
	private static Moviments instancia;
	
	public static Moviments getInstance() {
		return Moviments.instancia;
	}
	
	private List<String> listMovs; // Moviments torn actual, encara per fer...
	private List<String> listMovsAnt; // Moviments torn anterior
	private Taulell taulActual; // Taulell torn actual
	private Taulell taulAnt; // Taulell torn anterior
	private boolean tornAcabat;
	private boolean potBufar;
	
	public Moviments(String movsAnt, String taulActual, String taulAnterior, boolean torn) {
		
		this.listMovsAnt = new ArrayList<String>();
		
		if (!movsAnt.isEmpty()) {
			for (String mov : movsAnt.split("/")) {
				this.listMovsAnt.add(mov);
			}
		}
		
		this.listMovs = new ArrayList<String>();
		
		this.taulActual = new Taulell();
		if (!taulActual.isEmpty())
			this.taulActual.reconstruirTaulell(taulActual);
		
		this.taulAnt = new Taulell();
		if (taulAnterior.isEmpty() && !taulActual.isEmpty()) {
			this.taulAnt.reconstruirTaulell(taulActual);
		}
		else if (!taulAnterior.isEmpty()) {
			this.taulAnt.reconstruirTaulell(taulAnterior);
		}
		
		if (torn)
			this.tornAcabat = false;
		else
			this.tornAcabat = true;
		
		if (taulAnterior.isEmpty())
			this.potBufar = false;
		else
			this.potBufar = true;
		
		// Si en el torn anterior s'ha matat, no pot bufar
		for (String mov : this.listMovsAnt) {
			if (mov.contains("matar"))
				this.potBufar = false;
		}
		
		Moviments.instancia = this;
	}
	
	public Taulell getTaulellActual() {return this.taulActual;}
	public List<String> getMovimentsAct() {return this.listMovs;}
	public List<String> getMovimentsAnt() {return this.listMovsAnt;}
	
	public boolean ferBufa(int x, int y) {
		if (this.tornAcabat)
			return false;
		
		if (!this.potBufar)
			return false;
		
		int copyX = x; // Safety copy
		int copyY = y; // Safety copy
		
		// Comprovem si s'ha matat en el torn anterior, si es aixi no es pot bufar
		// Altrament guardem posicio anterior de la peça, si es que s'ha mogut
		for (String mov : this.listMovsAnt) {
			if (mov.contains("matar"))
				return false;
			else if (mov.contains("moure")) {
				int xAct, yAct;
				xAct = Integer.parseInt(mov.split(";")[3]);
				yAct = Integer.parseInt(mov.split(";")[4]);
				if (x == xAct && y == yAct) {
					x = Integer.parseInt(mov.split(";")[1]);
					y = Integer.parseInt(mov.split(";")[2]);
				}
			}
		}
		
		// Comprovem si la peça seleccionada podria haver matat en el torn anterior
		Casella casSelec = this.taulAnt.seleccionarCasella(x, y);
		if (!casSelec.getTeFitxa())
			return false;
		
		List<int[]> movimentsFitxa = casSelec.getFitxa().possiblesMoviments(x, y, this.taulAnt.getMatriu());
		
		for (int[] pos : movimentsFitxa) {
			Casella casDesti = this.taulAnt.seleccionarCasella(pos[0], pos[1]);
			boolean potMatar = this.taulAnt.potMatar(casSelec, casDesti) != null;
			
			if (potMatar) {
				this.listMovs.add("bufar;" + copyX + ";" + copyY);
				this.taulActual.seleccionarCasella(copyX, copyY).eliminarFitxa();
				this.potBufar = false;
				return true;
			}
		}
		
		return false;
	}
	
	public boolean ferMoure(int xIni, int yIni, int xFi, int yFi) {
		if (this.tornAcabat) {
			System.out.println("Torn acabat!");
			return false;
		}
		
		Casella casOrigen = this.taulActual.seleccionarCasella(xIni, yIni);
		Casella casDesti = this.taulActual.seleccionarCasella(xFi, yFi);
		boolean potMatar = this.taulActual.potMatar(casOrigen, casDesti) != null;
		String moviment = "";
		try {
			if (this.taulActual.moviment(casOrigen, casDesti)) {
				moviment = "matar;" + xIni + ";" + yIni + ";" + xFi + ";" + yFi;
			}
			else {
				moviment = "moure;" + xIni + ";" + yIni + ";" + xFi + ";" + yFi;
				this.tornAcabat = true;
			}
			this.listMovs.add(moviment);
			this.potBufar = false;
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	public String movsToString() {
		String movs = "";
		
		for (String mov : this.listMovs) {
			movs += mov + "/";
		}
		movs = movs.substring(0, movs.length());
		return movs;
	}
	
	// Retorna "Continua" si no s'ha acabat la partida
	// Altrament retorna qui ha guanyat
	public String partidaAcabada() {
		String res = "";
		String taulellAct = this.taulActual.toString();
		
		int peonsBlancs = countOccurrences(taulellAct, '0');
		int peonsNegres = countOccurrences(taulellAct, '1');
		int damesBlancs = countOccurrences(taulellAct, 'D');
		int damesNegres = countOccurrences(taulellAct, 'd');
		
		if (peonsBlancs == 0 && damesBlancs == 0)
			res = "Red";
		else if (peonsNegres == 0 && damesNegres == 0)
			res = "Black";
		else
			res = "Continua";
		
		return res;
	}
	
	public String movimentsPossibles() {
		String possibles = "";
		if (!this.tornAcabat && partidaAcabada().equals("Continua"))
			possibles += "moure" + ";";
		if (this.potBufar)
			possibles += "bufar";
		return possibles;
	}
	
	// Compta les ocurrencies d'un char en un string
	public static int countOccurrences(String comptat, char comptar)
	{
	    int count = 0;
	    for (int i=0; i < comptat.length(); i++)
	    {
	        if (comptat.charAt(i) == comptar)
	        {
	             count++;
	        }
	    }
	    return count;
	}
}
