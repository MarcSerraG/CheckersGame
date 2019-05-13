package CapaDominiTest;

import CapaDomini.Peo;
import

public class PeoTest {
	
	private static Peo p1;
	private static Peo p2;
	private static Peo p3;
	private static Peo p4;
	private static Peo p5;
	
	@BeforeClass
	public static void testPeo() {
		
		try {
			p1 = new Peo(0,0);
			p2 = new Peo (0,1);
			p3 = new Peo(-1,-1);
			p4 = new Peo(4,5);
			p5 = new Peo(2,2);
		}
		catch() {}
	}
	

}
