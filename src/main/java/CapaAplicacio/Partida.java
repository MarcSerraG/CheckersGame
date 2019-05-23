package CapaAplicacio;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Partida extends JPanel implements ActionListener {

	BaseInterficie interficieBase;
	JTextField tfUsuari;

	public Partida(BaseInterficie base) {
		interficieBase = base;
	}

	public JPanel partidaCreate() {
		JPanel panelTaulell = new JPanel();

		panelTaulell.setLayout(null);

		Image myImage;
		try {
			myImage = ImageIO.read(getClass().getResource("/taulell.png"));

			myImage = myImage.getScaledInstance(500, 500, java.awt.Image.SCALE_SMOOTH);
			ImageIcon myImageIcon = new ImageIcon(myImage);

			JLabel icon = new JLabel(myImageIcon);

			tfUsuari = new JTextField();

			// tfUsuari.setBounds(210, 250, 500, 30);
			icon.setBounds(230, 100, 500, 500);

			tfUsuari.setBorder(javax.swing.BorderFactory.createEmptyBorder());

			panelTaulell.setBackground(Color.DARK_GRAY);
			tfUsuari.setBackground(Color.LIGHT_GRAY);

			// panelTaulell.add(tfUsuari);
			panelTaulell.add(icon);

		} catch (Exception e) {

		}
		return panelTaulell;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
