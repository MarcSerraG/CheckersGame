package CapaDomini;
import java.util.List;
import java.util.Vector;

public class Taulell {
	
	//Variables
	private int intLlargada;
	private int intAmplada;
	private Casella casMatCaselles[][];
	private int intNumNegres;
	private int intNumBlanques;
	private final int UPRIGHT = 0;
	private final int DOWNRIGHT = 1;
	private final int DOWNLEFT = 2;
	private final int UPLEFT = 3;
	
	//Constructor
	public Taulell(int llarg, int ample) {
		
		intLlargada = llarg;
		intAmplada = ample;
		intNumBlanques = 0;
		intNumNegres = 0;
		casMatCaselles = new Casella [llarg][ample];
		this.omplirTaulell(llarg, ample);
	}
	//Gets
	public int getNumNegres(){return this.intNumNegres;}
	public int getNumBlanques(){return this.intNumBlanques;}
	//No se que fa aquest metode
	public void actualitzarTaulell() {
		
	}
	//No se que fa aquest metode
	public Casella seleccionarFitxa(int fila, int columna) {
		
		return casMatCaselles[fila][columna];
	}
	//Comprova si es factible realitzar el moviment, el fa, mata si cal i retorna si ha matat o no
	public boolean moviment (Casella casOrigen, Casella casDesti) throws IllegalArgumentException{
		
		if(!casOrigen.getTeFitxa()) throw new IllegalArgumentException("origin empty");
		if(casDesti.getTeFitxa()) throw new IllegalArgumentException("destination full");
		if(casOrigen.equals(casDesti)) throw new IllegalArgumentException("origin and destination are the same");
		
		Casella casMatar = null;
		boolean trobat = false;
		//Store all the possible movements
		List<int[]> moviments = this.veurePossiblesMoviments(casOrigen);
		//Find out if the destination position is within possible movements
		for(int i = 0; i<moviments.size();i++) {
			if(moviments.get(i)[0] == casDesti.getX() && moviments.get(i)[1] == casDesti.getY()) trobat = true;
		}
		if(!trobat) throw new IllegalArgumentException("destination out of range");
		//Find if we can kill any token
		casMatar = this.potMatar(casOrigen, casDesti);
		//Remove killed token if there is any
		if(!(casMatar == null)) {
			casMatar.eliminarFitxa();
			return true;
		}
		casDesti.setFitxa(casOrigen.getFitxa());
		casOrigen.eliminarFitxa();
		return false;
	}
 	//Calcula totes les caselles possibles on la fitxa es pot moure
	private List<int[]> veurePossiblesMoviments(Casella casella) throws IllegalArgumentException{
		
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
	//Modifica els possibles moviments del peo quan hi ha una fitxa al cami
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
	//Modifica els possibles moviments de la dama quan hi ha una fitxa al cami
	private List<int[]> casellaMatadaDama(List<int[]>moviment, Casella casella, int[]mov){
		int auxMov[] = mov;
		boolean diffColor = false;
		//If the color does not match, save the firt next position to kill
		if (!(casMatCaselles[mov[0]][mov[1]].getFitxa().iColor == casella.getFitxa().iColor)) diffColor = true;
			
		//Down
		if(mov[0]>casella.getX()) {
			//Right
			if(mov[1]>casella.getY()) {
				if(diffColor) {
					auxMov[0]+= 2;
					auxMov[1]+= 2;
				}
				else {
					auxMov[0]++;
					auxMov[1]++;
				}
				while (!(auxMov[0]>9 || auxMov[1]>9)) {
					moviment.remove(auxMov);
					auxMov[0]++;
					auxMov[1]++;
				}
			}
			//Left
			else {
				if(diffColor) {
					auxMov[0]+= 2;
					auxMov[1]-= 2;
				}
				else {
					auxMov[0]++;
					auxMov[1]--;
				}
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
				if(diffColor) {
					auxMov[0]-= 2;
					auxMov[1]+= 2;
				}
				else {
					auxMov[0]--;
					auxMov[1]++;
				}
				while (!(auxMov[0]<0 || auxMov[1]>9)) {
					moviment.remove(auxMov);
					auxMov[0]--;
					auxMov[1]++;
				}
			}
			//Left
			else {
				if(diffColor) {
					auxMov[0]+= 2;
					auxMov[1]+= 2;
				}
				else {
					auxMov[0]--;
					auxMov[1]--;
				}
				while (!(auxMov[0]<0 || auxMov[1]<0)) {
					moviment.remove(auxMov);
					auxMov[0]--;
					auxMov[1]--;
				}
			}
		}
		moviment.remove(mov);
		return moviment;
	}
	//Retorna la casella que s'ha de matar si es pot
	private Casella potMatar(Casella casOrigen, Casella casDesti) {
		
		int[] posOrigen = casOrigen.getCoordenades();
		int[] posDesti = casDesti.getCoordenades();
		int direccio;
		Casella casMatar = null;
		//Know the direction of the movement
		//UP
		if(posDesti[0]<posOrigen[0]) {
			//LEFT
			if(posDesti[1]<posOrigen[1]) direccio = UPLEFT; 
			//RIGHT
			else direccio = UPRIGHT;
		}
		//DOWN
		else {
			//LEFT
			if(posDesti[1]<posOrigen[1]) direccio = DOWNLEFT; 
			//RIGHT
			else direccio = DOWNRIGHT;
		}
		//Know if it can kill
		switch (direccio) {
		//UPRIGHT
		case 0:
			if(casMatCaselles[posDesti[0]+1][posDesti[1]-1].getTeFitxa() && !(((casMatCaselles[posDesti[0]+1][posDesti[1]-1]).getX() == posOrigen[0]) && (casMatCaselles[posDesti[0]+1][posDesti[1]-1]).getY() == posOrigen[1])) {
				casMatar = casMatCaselles[posDesti[0]+1][posDesti[1]-1];
				System.out.print("upright");
			}	
		//DOWNRIGHT
		case 1:
			if(casMatCaselles[posDesti[0]-1][posDesti[1]-1].getTeFitxa() && !(((casMatCaselles[posDesti[0]-1][posDesti[1]-1]).getX() == posOrigen[0]) && (casMatCaselles[posDesti[0]-1][posDesti[1]-1]).getY() == posOrigen[1])) {
				casMatar = casMatCaselles[posDesti[0]-1][posDesti[1]-1];
				System.out.print("downright");
			}
		//DOWNLEFT
		case 2:
			if(casMatCaselles[posDesti[0]-1][posDesti[1]+1].getTeFitxa() && !(((casMatCaselles[posDesti[0]-1][posDesti[1]+1]).getX() == posOrigen[0]) && (casMatCaselles[posDesti[0]-1][posDesti[1]+1]).getY() == posOrigen[1])) {
				casMatar = casMatCaselles[posDesti[0]-1][posDesti[1]+1];
				System.out.print("downleft");
			}
		//UPLEFT
		case 3:
			if(casMatCaselles[posDesti[0]+1][posDesti[1]+1].getTeFitxa() && !(((casMatCaselles[posDesti[0]+1][posDesti[1]+1]).getX() == posOrigen[0]) && (casMatCaselles[posDesti[0]+1][posDesti[1]+1]).getY() == posOrigen[1])) {
				casMatar = casMatCaselles[posDesti[0]+1][posDesti[1]+1];
				System.out.print("upleft");
			}
		}
		return casMatar;
	}
	//Recorre el taulell i fa new de les caselles
	private void omplirTaulell(int llarg, int ample) {
		boolean toca = false;
		
		for (int i = 0; i <= llarg - 1; i++) {
			for(int j = 0; j <= ample - 1; j++) {
				if ((i%2 == 0 && j%2 != 0)||(i%2 != 0 && j%2 == 0)) {
					if (i <= 3) {
						toca = true;
						intNumNegres++;
					}
					if(i >= llarg - 4) {
						toca = true;
						intNumBlanques++;
					}
				}
				else toca = false;
				casMatCaselles[i][j] = new Casella(i,j,toca);
			}
		}
	}
	//Retorna el taulell dibuixat amb la disposicio de les fitxes
	public String toString() {
		
		int fila = 0;
		String text = "  0  1  2  3  4  5  6  7  8  9 ";
		
		for(int i = 0; i <= 9; i++ ) {
			text += "\n" + fila;
			fila ++;
			for (int j = 0; j <= 9; j++) {
				text += casMatCaselles[i][j];
			}
		}
		
		return text + "\n";
	}
}
