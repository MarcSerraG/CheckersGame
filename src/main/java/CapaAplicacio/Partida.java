package CapaAplicacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import CapaPersistencia.ConnectionSQLOracle;
import CapaPersistencia.PartidesSQLOracle;
import CapaPersistencia.UsuariSQLOracle;

public class Partida extends JPanel implements ActionListener {
	
	UsuariSQLOracle userSQL;
	PartidesSQLOracle partSQL;
	BaseInterficie interficieBase;
	
	public Partida(ConnectionSQLOracle connection, BaseInterficie base) {
		userSQL = new UsuariSQLOracle(connection);
		partSQL = new PartidesSQLOracle(connection);
		interficieBase = base;
	}
	
	public JPanel partidaCreate() {
		JPanel panelTaulell = new JPanel();
		JFrame taulell =  new JFrame();

		panelTaulell.setLayout(null);	
		panelTaulell.setBackground(Color.DARK_GRAY);
		
		
		Image myImage;
		try {
			myImage = ImageIO.read(getClass().getResource("taulell.png"));

			myImage = myImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
			
			ImageIcon myImageTaulell = new ImageIcon(myImage);
			
			JLabel tb = new JLabel(myImageTaulell);
			
			taulell.add(tb);
			
		} catch (Exception e) {
			
		}
		panelTaulell.add(taulell);
		return panelTaulell;
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
