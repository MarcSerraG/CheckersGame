package CapaDomini;
import java.util.List;

public abstract class Fitxa {
	
	//Variables
	//0 = white, 1 = black
	protected int iID;
	protected int iColor;
	
	//Torna una llista de moviments possibles sense comprobar si les caselles existeixen
	public abstract List<int[]> possiblesMoviments(int x, int y) throws IllegalArgumentException;	
}
