package CapaDominiTest;

import CapaDomini.Dama;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class DamaTest {
		
	private static Dama d1;
		
	@BeforeClass
	public static void TestDama() {
		try {
			d1 = new Dama(0);
		}
		catch(IllegalArgumentException e) {
			fail("Error DamaTest:" + e.getMessage());
		}
	}
	@Test
	public void testDamaColor() {
		try {
			d1 = new Dama(-1);
			d1 = new Dama(2);
			fail("Error testDamaColor");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Color only can be 0 or 1", e.getMessage());
		}
	}
	@Test
	public void testDamaPossiblesMoviments() {
		try {
			d1.possiblesMoviments(-1, 0);
			d1.possiblesMoviments(0, -1);
			d1.possiblesMoviments(10, 0);
			d1.possiblesMoviments(0, 10);
			fail("Error testDama PossiblesMoviments");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Position out of bounds", e.getMessage());
		}
	}
}

