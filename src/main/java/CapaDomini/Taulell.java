package CapaDomini;
import java.util.List;
import java.util.Vector;

public class Taulell {

	private int intLlargada;
	private int intAmplada;
	private Casella casMatCaselles[][];
	private int intNumFitxes;
	
	public Taulell(int llarg, int ample) throws IllegalArgumentException{
		//Falta especificar comportament del constructor!
		//omplir taulell anira amb amplada i llargada o sera generic?
	}
	
	public void actualitzarTaulell() {
		
	}
	
	public boolean moviment (Casella casOrigen, Casella casDesti) throws Exception{
		
		if(!casOrigen.getTeFitxa()) throw new IllegalArgumentException("wrong origin");
		if(casDesti.getTeFitxa()) throw new IllegalArgumentException("destination full");
		
		try {
		int [] posicio = {casDesti.getX(), casDesti.getY()};
		List moviments = this.veurePossiblesMoviments(casOrigen);
		if(!moviments.contains(posicio)) throw new IllegalArgumentException("destination out of range");
		
		}
		
		catch (Exception e) {
			
			throw new Exception();
		}
		return true;
	}
	
	public boolean seleccionarFitxa(Casella casella) {
		
		return casella.getTeFitxa();
	}
	
	public List veurePossiblesMoviments(Casella casella) throws Exception{
		
		if(casella == null) throw new IllegalArgumentException("Parameter can not be empty");
		if(casella.getTeFitxa() == false) throw new IllegalArgumentException("You selected an empty space");
		List<int[]> moviment;
		
		try {
			//Get full movements
			moviment = casella.getFitxa().possiblesMoviments(casella.getX(), casella.getY());
			int mida = moviment.size();
			//Go through all possibilities
			for (int i = 0; i < mida; i++) {
				int [] mov = moviment.get(i);
				//If a position is full
				if (casMatCaselles[mov[0]][mov[1]].getTeFitxa()) {
					//If the color match with own
					if (casMatCaselles[mov[0]][mov[1]].getFitxa().strColor == casella.getFitxa().strColor) 
						moviment.remove(i);
			}
			
		}
		}
		catch(Exception e) {
			
			throw new Exception();
		}
		return moviment;
	}
	
	public void omplirTaulell() {
		boolean toca = false;
		
		for (int i = 0; i <= 9; i++) {
			for(int j = 0; j <= 9; j++) {
				if ((i%2 == 0 && j%2 != 0)||(i%2 != 0 && j%2 == 0)) {
					if (i <= 3 && i >= 6) {
						toca = true;
						intNumFitxes++;
					}
				}
				else toca = false;
				casMatCaselles[i][j] = new Casella(i,j,toca);
			}
		}
	}
	
}
