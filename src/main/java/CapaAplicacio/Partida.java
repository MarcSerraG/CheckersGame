package CapaAplicacio;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Partida extends JPanel implements ActionListener {

	BaseInterficie interficieBase;

	public Partida(BaseInterficie base) {
		interficieBase = base;
	}

	public JPanel partidaCreate() {
		JPanel panelTaulell = new JPanel();

		panelTaulell.setLayout(null);
		panelTaulell.setBackground(Color.DARK_GRAY);

		Image myImage;
		try {
			myImage = ImageIO.read(getClass().getResource("taulell.png"));

			myImage = myImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);

			ImageIcon myImageTaulell = new ImageIcon(myImage);

			JLabel tb = new JLabel(myImageTaulell);

			JLabel miss = new JLabel("UserName", JLabel.TRAILING);

			miss.setBounds(450, 625, 500, 40);
			miss.setFont(new Font("Monospaced", Font.BOLD, 60));

			panelTaulell.add(tb);
			panelTaulell.add(miss);

			System.out.println("Fi panel");
		} catch (Exception e) {

		}
		return panelTaulell;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
