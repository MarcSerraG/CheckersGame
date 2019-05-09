package CapaDomini;
import java.util.List;

public abstract class Fitxa {

	private String strID;
	private String strColor;
	
	
	public abstract List possiblesMoviments(int x, int y, int maxX, int maxY);
	
}
