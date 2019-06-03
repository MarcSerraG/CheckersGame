package CapaDomini;

import java.util.List;
import java.util.Vector;

public class Peo extends Fitxa {

	// Constructor
	// 0 = white, 1 = black
	public Peo(int iColor) throws IllegalArgumentException {

		if (iColor < 0 || iColor > 1)
			throw new IllegalArgumentException("Color only can be 0 or 1");

		this.iID = this.hashCode();
		this.iColor = iColor;
	}

	public int getColor() {
		return this.iColor;
	}

	// Torna una llista de moviments possibles de peo
	public List<int[]> possiblesMoviments(int x, int y, Casella[][]casMat) throws IllegalArgumentException {

		if (x < 0 || x > 7 || y < 0 || y > 7) throw new IllegalArgumentException("Position out of bounds");

		Vector<int[]> llista = new Vector<int[]>();
		int mov1[] = new int[2];
		int mov2[] = new int[2];

		switch (this.iColor) {
		// In case it is white it can only go up
		case 0:
			mov1[0] = x - 1;
			mov1[1] = y + 1;
			if(!(mov1[0] < 0 || mov1[0] > 7 || mov1[1] < 0 || mov1[1] > 7)) {
				if(casMat[mov1[0]][mov1[1]].getTeFitxa()) {
					if(casMat[mov1[0]][mov1[1]].getFitxa().iColor != this.iColor) {
						mov1[0] -= 1;
						mov1[1] += 1;
					}
					if(!(mov1[0] < 0 || mov1[0] > 7 || mov1[1] < 0 || mov1[1] > 7)) {
						if(!casMat[mov1[0]][mov1[1]].getTeFitxa())llista.add(mov1);
					}
				}
				else llista.add(mov1);
			}
			
			mov2[0] = x - 1;
			mov2[1] = y - 1;
			if(!(mov2[0] < 0 || mov2[0] > 7 || mov2[1] < 0 || mov2[1] > 7)) {
				if(casMat[mov2[0]][mov2[1]].getTeFitxa()) {
					if(casMat[mov2[0]][mov2[1]].getFitxa().iColor != this.iColor) {
						mov2[0] -= 1;
						mov2[1] -= 1;
					}
					if(!(mov2[0] < 0 || mov2[0] > 7 || mov2[1] < 0 || mov2[1] > 7)) {
						if(!casMat[mov2[0]][mov2[1]].getTeFitxa()) llista.add(mov2);
					}
				}
				else llista.add(mov2);
			}
			break;

		// In case it is black it can only go down
		case 1:
			mov1[0] = x + 1;
			mov1[1] = y + 1;
			if(!(mov1[0] < 0 || mov1[0] > 7 || mov1[1] < 0 || mov1[1] > 7)) {
				if(casMat[mov1[0]][mov1[1]].getTeFitxa()) {
					if(casMat[mov1[0]][mov1[1]].getFitxa().iColor != this.iColor) {
						mov1[0] += 1;
						mov1[1] += 1;
					}
					if(!(mov1[0] < 0 || mov1[0] > 7 || mov1[1] < 0 || mov1[1] > 7)) {
						if(!casMat[mov1[0]][mov1[1]].getTeFitxa())llista.add(mov1);
					}
				}
				else llista.add(mov1);
			}
			
			mov2[0] = x + 1;
			mov2[1] = y - 1;
			if(!(mov2[0] < 0 || mov2[0] > 7 || mov2[1] < 0 || mov2[1] > 7)) {
				if(casMat[mov2[0]][mov2[1]].getTeFitxa()) {
					if(casMat[mov2[0]][mov2[1]].getFitxa().iColor != this.iColor) {
						mov2[0] += 1;
						mov2[1] -= 1;
					}
					if(!(mov2[0] < 0 || mov2[0] > 7 || mov2[1] < 0 || mov2[1] > 7)) {
						if(!casMat[mov2[0]][mov2[1]].getTeFitxa())llista.add(mov2);
					}
				}
				else llista.add(mov2);
			}
			break;
		}
		return llista;
	}

	// Torna el color del peo
	public String toString() {

		return String.valueOf(iColor);
	}
}
