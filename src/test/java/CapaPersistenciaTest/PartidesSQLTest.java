package CapaPersistenciaTest;

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
			ResultSet rs = connection.ferSelect("SELECT nom FROM USUARIS WHERE nom = 'Marc'");
			
			if (!rs.next()) 
				fail("Expected result on command");
			else
				assertEquals(rs.getString("NOM"), "Marc");
			
		} catch (SQLException e) {
			fail("Error testGetUser "+e.getMessage());
			
		} catch (Exception e) {
			fail("Error testGetUser "+e.getMessage());
		}
	}
	@Test
	public void testGetPartides() {
		try {
			ResultSet rs = connection.ferSelect("SELECT estat FROM PARTIDES WHERE id = 41");
			
			if (!rs.next()) 
				fail("Expected result on command");
			else
				assertEquals(rs.getInt("ESTAT"), 3);
		} catch (SQLException e) {
			fail("Error testGetUser "+e.getMessage());
		
		} catch (Exception e) {
			fail("Error testGetUser "+e.getMessage());
		}
	}

}
