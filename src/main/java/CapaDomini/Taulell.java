package CapaDomini;
import java.util.List;
import java.util.Stack;

public class Taulell {
	
	//Variables
	private Casella casMatCaselles[][];
	private int intNumNegres;
	private int intNumBlanques;
	private final int UPRIGHT = 0;
	private final int DOWNRIGHT = 1;
	private final int DOWNLEFT = 2;
	private final int UPLEFT = 3;
	
	//Constructor
	public Taulell() {
		
		intNumBlanques = 0;
		intNumNegres = 0;
		casMatCaselles = new Casella [10][10];
		this.omplirTaulell(10,10);
	}
	//Retorna si algun jugador no te fitxes per moure
	public boolean comprovarFitxes(){return (intNumNegres == 0 || intNumBlanques == 0);}
	public Casella seleccionarCasella(int x, int y){return this.casMatCaselles[x][y];}
	//Comprova si es factible realitzar el moviment, el fa, mata si cal i retorna si ha matat o no
	public boolean moviment (Casella casOrigen, Casella casDesti) throws IllegalArgumentException{
		
		if(casOrigen.equals(casDesti)) throw new IllegalArgumentException("origin and destination are the same");
		if(!casOrigen.getTeFitxa()) throw new IllegalArgumentException("origin empty");
		if(casDesti.getTeFitxa()) throw new IllegalArgumentException("destination full");
		
		Casella casMatar = null;
		boolean trobat = false;
		boolean haMatat = false;
		boolean potMoure = false;
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
			haMatat = true;
		}
		//Move selected token
		casDesti.setFitxa(casOrigen.getFitxa());
		casOrigen.eliminarFitxa();
		this.canviDama(casDesti.getFitxa().iColor, casDesti);
		//Look if in the next position there is any movement available
		if(this.veurePossiblesMoviments(casDesti).size()!= 0) potMoure = true;
		return haMatat && potMoure;
	}
	//Calcula totes les caselles possibles on la fitxa es pot moure
	public List<int[]> veurePossiblesMoviments(Casella casOrigen) throws IllegalArgumentException{
		
		int[] movMatada = null;
		List<int[]> moviment;
		//Get full movements
		moviment = casOrigen.getFitxa().possiblesMoviments(casOrigen.getX(), casOrigen.getY());
		//Go through all possibilities
		for (int i = 0; i < moviment.size(); i++) {
			int [] mov = moviment.get(i);
			//If a position is full
			if (casMatCaselles[mov[0]][mov[1]].getTeFitxa()) {
				if(casOrigen.getFitxa() instanceof Peo) {
					movMatada = this.casellaMatadaPeo(casOrigen, mov);
					if (movMatada != null) moviment.set(i, movMatada);
					else moviment.remove(i);
				}
				else {
					movMatada = this.casellaMatadaDama(casOrigen, mov);
					if (movMatada != null) moviment.set(i, movMatada);
					else moviment.remove(i);
				}
			}
		}
		return moviment;
	}
	//Modifica els possibles moviments del peo quan hi ha una fitxa al cami
	private int[] casellaMatadaPeo(Casella casella, int[] mov) {
		
		//If the color match
		if (casMatCaselles[mov[0]][mov[1]].getFitxa().iColor == casella.getFitxa().iColor)	return null;
		//If the color does not match
		else {
			int newMov[] = {mov[0], mov[1]};
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
					}break;
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
					}break;
				}
				//If the new movement is inside the game and it is empty
				if (!(newMov[0]<0 || newMov[0]>9 || newMov[1]<0 || newMov[1]>9) &&
					!(casMatCaselles[newMov[0]][newMov[1]].getTeFitxa())) {
					return newMov;
				}
			}
		}
		return null;
	}
	//Modifica els possibles moviments de la dama quan hi ha una fitxa al cami
	private int[] casellaMatadaDama(Casella casella, int[] mov){
			
		//If the color match
		if (casMatCaselles[mov[0]][mov[1]].getFitxa().iColor == casella.getFitxa().iColor)	return null;
		//If the color does not match
		else {
			int newMov[] = {mov[0], mov[1]};
			if (casella.getFitxa() instanceof Dama) {
				//Movement up
				if(mov[0] < casella.getX()) {
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
				}
				//Movement down
				else {
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
				if (!(newMov[0]<0 || newMov[0]>9 || newMov[1]<0 || newMov[1]>9) &&
					!(casMatCaselles[newMov[0]][newMov[1]].getTeFitxa())) {
					return newMov;
				}
			}
		}
		return null;
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
			}break;	
		//DOWNRIGHT
		case 1:
			if(casMatCaselles[posDesti[0]-1][posDesti[1]-1].getTeFitxa() && !(((casMatCaselles[posDesti[0]-1][posDesti[1]-1]).getX() == posOrigen[0]) && (casMatCaselles[posDesti[0]-1][posDesti[1]-1]).getY() == posOrigen[1])) {
				casMatar = casMatCaselles[posDesti[0]-1][posDesti[1]-1];
			}break;
		//DOWNLEFT
		case 2:
			if(casMatCaselles[posDesti[0]-1][posDesti[1]+1].getTeFitxa() && !(((casMatCaselles[posDesti[0]-1][posDesti[1]+1]).getX() == posOrigen[0]) && (casMatCaselles[posDesti[0]-1][posDesti[1]+1]).getY() == posOrigen[1])) {
				casMatar = casMatCaselles[posDesti[0]-1][posDesti[1]+1];
			}break;
		//UPLEFT
		case 3:
			if(casMatCaselles[posDesti[0]+1][posDesti[1]+1].getTeFitxa() && !(((casMatCaselles[posDesti[0]+1][posDesti[1]+1]).getX() == posOrigen[0]) && (casMatCaselles[posDesti[0]+1][posDesti[1]+1]).getY() == posOrigen[1])) {
				casMatar = casMatCaselles[posDesti[0]+1][posDesti[1]+1];
			}break;
		}
		return casMatar;
	}
	//Canvia a dama una fitxa que hagi arribat al final del taulell
	public void canviDama(int color, Casella casella) {
		//White 
		if(color == 0 && casella.getX()== 0) {
			casMatCaselles[casella.getX()][casella.getY()].setFitxa(new Dama(0));
		}
		//Black
		if(color == 1 && casella.getX()== 9) {
			casMatCaselles[casella.getX()][casella.getY()].setFitxa(new Dama(1));
		}
	}
	//Recorre el taulell i fa new de les caselles per iniciar la partida
	private void omplirTaulell(int llarg, int ample) {
		boolean toca = false;
		int color = 0;
		
		for (int i = 0; i <= llarg - 1; i++) {
			for(int j = 0; j <= ample - 1; j++) {
				if ((i%2 == 0 && j%2 != 0)||(i%2 != 0 && j%2 == 0)) {
					if (i <= 3) {
						toca = true;
						color = 1;
						intNumNegres++;
						
					}
					if(i >= llarg - 4) {
						toca = true;
						color = 0;
						intNumBlanques++;
					}
				}
				else toca = false;
				casMatCaselles[i][j] = new Casella(i,j,toca,0, color);
			}
		}
	}
	//Omplena la matriu segons el parametre String
	public void reconstruirTaulell(String text) {
		
		String[] taula = text.split("\n");
		String [] casella;;
		int fil = 0, col = 0;
		
		for(String fila : taula) {
			casella = fila.split(",");
			for(String posicio : casella) {
				 new Casella(fil,col,true,0,0);
				if(posicio.equals("0"))casMatCaselles[fil][col] = new Casella(fil, col,true, 0, 0);
				if(posicio.equals("1"))casMatCaselles[fil][col] = new Casella(fil,col,true,0,1);
				if(posicio.equals("x"))casMatCaselles[fil][col] = new Casella(fil, col, false,0,0);
				if(posicio.equals("D"))casMatCaselles[fil][col] = new Casella(fil, col, true,1,0);
				if(posicio.equals("d"))casMatCaselles[fil][col] = new Casella(fil, col, true,1,1);
				col++;
			}
			fil++;
		}
	}
	//Retorna el taulell dibuixat amb la disposicio de les fitxes
	public String toString() {
		
		String text = "";
		
		for(int i = 0; i <= 9; i++ ) {
			for (int j = 0; j <= 9; j++) {
				if(j != 9) text += casMatCaselles[i][j] + ",";
				else text += casMatCaselles[i][j] + "\n";
			}
		}
		return text;
	}
}
