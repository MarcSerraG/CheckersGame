package CapaDomini;

import java.util.LinkedList;
import java.util.List;

public class Dama extends Fitxa{
	
	//Constructor
	//0 = white, 1 = black
	public Dama (int iColor) throws IllegalArgumentException{
		
		if(iColor <0 || iColor > 1) throw new IllegalArgumentException("Color only can be 0 or 1");
		this.iID = this.hashCode();
		this.iColor = iColor;
	}
	//Torna una llista de moviments possibles de dama
	public List<int[]> possiblesMoviments(int x, int y, Casella[][] matCas) throws IllegalArgumentException{
		
		if (x < 0 || x > 9 || y < 0 || y > 9) throw new IllegalArgumentException("Position out of bounds");
		
			LinkedList <int[]> llista = new LinkedList<int[]>();
			//Go through the matrix to get all positions
			List<int[]> dig1 = this.calcularDiagonal(x, y, matCas, 0);
			List<int[]> dig2 = this.calcularDiagonal(x, y, matCas, 1);
			List<int[]> dig3 = this.calcularDiagonal(x, y, matCas, 2);
			List<int[]> dig4 = this.calcularDiagonal(x, y, matCas, 3);
		
		if(dig1 != null) for(int[] mov : dig1) llista.add(mov);
		if(dig2 != null) for(int[] mov : dig2) llista.add(mov);
		if(dig3 != null) for(int[] mov : dig3) llista.add(mov);
		if(dig4 != null) for(int[] mov : dig4) llista.add(mov);
		
		for (int[] mov : llista) System.out.println(mov[0] + mov[1]);
		return llista;
	}
	//Retorna una llista que va omplint en diagonal fins que troba una fitxa
	public List<int[]> calcularDiagonal(int x, int y, Casella[][]matCas, int dir){
		//dir 0 = UPRIGHT
		//dir 1 = DOWNRIGHT
		//dir 2 = DOWNLEFT
		//dir 3 = UPLEFT
		LinkedList<int[]> llista = new LinkedList<int[]>();
		int[] pos = {x,y};
		
		if(dir == 0) {
			System.out.println("entra 0");
			//initial position
			pos[0] -= 1; pos[1] += 1;
			System.out.println("posicio inicial 0: "+pos[0]+ pos[1]);
			//if position is not out of bounds
			while(!(pos[0] < 0 || pos[0] > 9 || pos[1] < 0 || pos[1] > 9)) {
				//if position is full
				if(matCas[pos[0]][pos[1]].getTeFitxa()) {
					System.out.println("passa te fitxa");
					//if it has different color
					if(matCas[pos[0]][pos[1]].getFitxa().iColor != matCas[x][y].getFitxa().iColor) {
						System.out.println("passa color");
						pos[0]--;
						pos[1]++;
						System.out.println("adeu" +pos[0]+pos[1]);
						if(!matCas[pos[0]][pos[1]].getTeFitxa()) {
							System.out.println(pos[0]+" "+pos[1]);
							int[] j = {pos[0], pos[1]};
							llista.add(j);
							return llista;
						}
						else return llista;
					}
					else return llista;
				}
				System.out.println("hola" +pos[0]+pos[1]);
				int[] i = {pos[0], pos[1]}; 
				llista.add(i);
				pos[0]--;
				pos[1]++;
			}
		}
		if(dir == 1) {
			System.out.println("entra 1");
			//initial position
			pos[0]= x+1; pos[1] = y+1;
			System.out.println("posicio inicial 1: "+pos[0]+ pos[1]);
			//if position is not out of bounds
			while(!(pos[0] < 0 || pos[0] > 9 || pos[1] < 0 || pos[1] > 9)) {
				//if position is full
				if(matCas[pos[0]][pos[1]].getTeFitxa()) {
					System.out.println("passa te fitxa");
					//if it has different color
					if(matCas[pos[0]][pos[1]].getFitxa().iColor != matCas[x][y].getFitxa().iColor) {
						System.out.println("passa color");
						pos[0]++;
						pos[1]++;
						System.out.println("adeu" +pos[0]+pos[1]);
						if(!matCas[pos[0]][pos[1]].getTeFitxa()) {
							int[] j = {pos[0], pos[1]};
							llista.add(j);
							return llista;
						}
						else return llista;	
					}
					else return llista;
				}
				int[] i = {pos[0], pos[1]}; 
				llista.add(i);
				System.out.println("hola" +pos[0]+pos[1]);
				pos[0]++;
				pos[1]++;
			}
		}
		if(dir == 2) {
			System.out.println("entra 2");
			//initial position
			pos[0]= x+1; pos[1] = y-1;
			System.out.println("posicio inicial 2: "+pos[0]+ pos[1]);
			//if position is not out of bounds
			while(!(pos[0] < 0 || pos[0] > 9 || pos[1] < 0 || pos[1] > 9)) {
				//if position is full
				if(matCas[pos[0]][pos[1]].getTeFitxa()) {
					System.out.println("passa te fitxa");
					//if it has different color
					if(matCas[pos[0]][pos[1]].getFitxa().iColor != matCas[x][y].getFitxa().iColor) {
						System.out.println("passa color");
						pos[0]++;
						pos[1]--;
						System.out.println("adeu" +pos[0]+pos[1]);
						if(!matCas[pos[0]][pos[1]].getTeFitxa()) {
							int[] j = {pos[0], pos[1]};
							llista.add(j);
							return llista;
						}
						else return llista;	
					}
					else return llista;
				}
				int[] i = {pos[0], pos[1]}; 
				llista.add(i);
				System.out.println("hola" +pos[0]+pos[1]);
				pos[0]++;
				pos[1]--;
			}
		}
		if(dir == 3) {
			System.out.println("entra 3");
			//initial position
			pos[0]= x-1; pos[1] = y-1;
			System.out.println("posicio inicial 3: "+pos[0]+ pos[1]);
			//if position is not out of bounds
			while(!(pos[0] < 0 || pos[0] > 9 || pos[1] < 0 || pos[1] > 9)) {
				//if position is full
				if(matCas[pos[0]][pos[1]].getTeFitxa()) {
					System.out.println("passa te fitxa");
					//if it has different color
					if(matCas[pos[0]][pos[1]].getFitxa().iColor != matCas[x][y].getFitxa().iColor) {
						System.out.println("passa color");
						pos[0]--;
						pos[1]--;
						System.out.println("adeu" +pos[0]+pos[1]);
						if(!matCas[pos[0]][pos[1]].getTeFitxa()) {
							llista.add(pos);
							return llista;
						}
						else return llista;	
					}
					else return llista;
				}
				int[] i = {pos[0], pos[1]}; 
				llista.add(i);
				System.out.println("hola" +pos[0]+pos[1]);
				pos[0]--;
				pos[1]--;
			}
			
		}
		return llista;
	}
	//Retorna D per dama blanca i d per dama negra
	public String toString() {
		if(iColor == 0) return "D";
		else return "d";
	}
	@Override
	public List<int[]> possiblesMoviments(int x, int y) throws IllegalArgumentException {
		
		return null;
	}
}
