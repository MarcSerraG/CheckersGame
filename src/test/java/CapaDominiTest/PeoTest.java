package CapaDominiTest;

import CapaDomini.Peo;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class PeoTest {
	
	private static Peo p1;
	
	@BeforeClass
	public static void TestPeo() {
		try {
		
			p1 = new Peo(0);
		}
		catch(IllegalArgumentException e) {
			fail("Error PeoTest:" + e.getMessage());
		}
	}
	@Test
	public void testPeoColor() {
		try {
			p1 = new Peo(-1);
			p1 = new Peo(2);
			fail("Error testPeoColor");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Color only can be 0 or 1", e.getMessage());
		}
	}
	@Test
	public void testPeoPossiblesMoviments() {
		try {
			p1.possiblesMoviments(-1, 0);
			p1.possiblesMoviments(0, -1);
			p1.possiblesMoviments(10, 0);
			p1.possiblesMoviments(0, 10);
			fail("Error testPeo PossiblesMoviments");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Position out of bounds", e.getMessage());
		}
	}
}
