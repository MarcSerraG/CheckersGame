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
		this.omplirTaulell();
	}
	
	public void actualitzarTaulell() {
		
	}
	//En stand by
	public boolean moviment (Casella casOrigen, Casella casDesti) throws Exception{
		
		if(!casOrigen.getTeFitxa()) throw new IllegalArgumentException("wrong origin");
		if(casDesti.getTeFitxa()) throw new IllegalArgumentException("destination full");
		
		try {
		int [] posicio = {casDesti.getX(), casDesti.getY()};
		List<int[]> moviments = this.veurePossiblesMoviments(casOrigen);
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
	//Aquest metode obte una casella i calcula totes les caselles possibles on la fitxa es pot moure
	public List<int[]> veurePossiblesMoviments(Casella casella) throws Exception{
		
		if(casella == null) throw new IllegalArgumentException("Parameter can not be empty");
		if(!casella.getTeFitxa()) throw new IllegalArgumentException("You selected an empty space");
		List<int[]> moviment;
		
		//Get full movements
		moviment = casella.getFitxa().possiblesMoviments(casella.getX(), casella.getY());
		int mida = moviment.size();
		//Go through all possibilities
		for (int i = 0; i < mida; i++) {
			int [] mov = moviment.get(i);
			//If a position is full
			if (casMatCaselles[mov[0]][mov[1]].getTeFitxa()) {
				if(casella.getFitxa() instanceof Peo) this.casellaMatadaPeo(moviment, casella, mov);
				else this.casellaMatadaDama(moviment, casella, mov);
			}
		}
		return moviment;
	}
	//Modifica els possibles moviments del peo quan es pot matar un contrincant
	private List<int[]> casellaMatadaPeo(List<int[]>moviment, Casella casella, int[] mov) {
		
		//If the color match
		if (casMatCaselles[mov[0]][mov[1]].getFitxa().iColor == casella.getFitxa().iColor)	moviment.remove(mov);
		
		//If the color does not match
		else {
			int newMov[] = mov;
			if (casella.getFitxa() instanceof Peo) {
				
				switch (casella.getFitxa().iColor) {
				//White side, always up
				case 0:
					//Movement to the right
					if(mov[1] > casella.getY()){
						newMov[0]--;
						newMov[1]++;
					}
					//Movement to the left
					else {
						newMov[0]--;
						newMov[1]--;
					}
					//Black side, always down
				case 1:
					//Movement to the right
					if(mov[1] > casella.getY()){
						newMov[0]++;
						newMov[1]++;
					}
					//Movement to the left
					else {
						newMov[0]++;
						newMov[1]--;
					}
				}
				//If the new movement is inside the game and it is empty
				if (!((newMov[0]<0 || newMov[0]>9 || newMov[1]<0 || newMov[1]>9) &&
					casMatCaselles[newMov[0]][newMov[1]].getTeFitxa())) moviment.add(newMov);
				//Always remove previous possible movement
				moviment.remove(mov);
			}
		}
		return moviment;
	}
	//Modifica els possibles moviments de la dama quan es pot matar un contrincant
	private List<int[]> casellaMatadaDama(List<int[]>moviment, Casella casella, int[]mov){
		int auxMov[] = mov;
		//If the color match
		if (casMatCaselles[mov[0]][mov[1]].getFitxa().iColor == casella.getFitxa().iColor) {
			//Down
			if(mov[0]>casella.getX()) {
				//Right
				if(mov[1]>casella.getY()) {
					auxMov[0]++;
					auxMov[1]++;
					while (!(auxMov[0]>9 || auxMov[1]>9)) {
						moviment.remove(auxMov);
						auxMov[0]++;
						auxMov[1]++;
					}
				}
				//Left
				else {
					auxMov[0]++;
					auxMov[1]--;
					while (!(auxMov[0]>9 || auxMov[1]<0)) {
						moviment.remove(auxMov);
						auxMov[0]++;
						auxMov[1]--;
					}
				}
			}
			//Up
			else{
				//Right
				if(mov[1]>casella.getY()) {
					auxMov[0]--;
					auxMov[1]++;
					while (!(auxMov[0]<0 || auxMov[1]>9)) {
						moviment.remove(auxMov);
						auxMov[0]--;
						auxMov[1]++;
					}
				}
				//Left
				else {
					auxMov[0]--;
					auxMov[1]--;
					while (!(auxMov[0]<0 || auxMov[1]<0)) {
						moviment.remove(auxMov);
						auxMov[0]--;
						auxMov[1]--;
					}
				}
			}
			moviment.remove(mov);
		}	
		//If the color does not match
		else {}
		
		return moviment;
	}
	//Aquest metode recorre el taulell i li assigna 
	private void omplirTaulell() {
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
