package CapaDominiTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;

import CapaPersistencia.*;

public class PartidesSQLTest {
	
	private static ConnectionSQLOracle connection;
		
	@BeforeClass
	public static void TestConnection() {
		try {
			connection = new ConnectionSQLOracle();
		}
		catch(IllegalArgumentException e) {
			fail("Error TestConnection:" + e.getMessage());			
		} catch (ClassNotFoundException e) {
			fail("Error TestConnection:" + e.getMessage());		
		} catch (Exception e) {
			fail("Error TestConnection:" + e.getMessage());
		}
	}
	@Test
	public void testGetUser() {
		try {
			ResultSet rs = connection.ferSelect("SELECT nom, FROM USUARIS id = 12");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testDamaPossiblesMoviments() {
		try {
			fail("Error testDama PossiblesMoviments");
		}
		catch(IllegalArgumentException e) {
			assertEquals("Position out of bounds", e.getMessage());
		}
	}

}
