package CapaAplicacio;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import CapaPersistencia.ConnectionSQLOracle;
import CapaPersistencia.PartidesSQLOracle;
import CapaPersistencia.UsuariSQLOracle;

public class Taulell extends JPanel implements ActionListener {
	
	UsuariSQLOracle userSQL;
	PartidesSQLOracle partSQL;
	BaseInterficie interficieBase;
	
	public Taulell(ConnectionSQLOracle connection, BaseInterficie base) {
		userSQL = new UsuariSQLOracle(connection);
		partSQL = new PartidesSQLOracle(connection);
		interficieBase = base;
	}
	
	
	
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
