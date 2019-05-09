package CapaDomini;
import java.util.List;

public abstract class Fitxa {
	
	//0 = white, 1 = black
	public String strID;
	public int strColor;
	
	public abstract List<int[]> possiblesMoviments(int x, int y) throws Exception;
	
}
