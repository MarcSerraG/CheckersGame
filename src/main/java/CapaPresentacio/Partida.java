package CapaPresentacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Partida extends JPanel implements ActionListener {

	BaseInterficie interficieBase;
	JTextField tfUsuari;
	JPanel panelTaulell, panelNord, panelSud, panelEst, panelOest;
	JButton bTaules;
	JLabel lMessage, lPlayerBlancas, lPlayerNegras; // (Blancas=0, Negras = 1)
	Map<String, JButton> taulell;
	String posInicial = "", posFinal = "";

	public Partida(BaseInterficie base) {
		interficieBase = base;
		panelNord = new JPanel();
		panelSud = new JPanel();
		panelEst = new JPanel();
		panelOest = new JPanel();

	}

	public JPanel partidaCreate() {
		panelTaulell = new JPanel();

		JPanel panelCentral = new JPanel();
		panelCentral.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		panelCentral.setBackground(Color.ORANGE);

		panelTaulell.setLayout(new BorderLayout());

		setNouTaulell(panelCentral);
		AjustaPantalla(panelNord, panelSud, panelEst, panelOest);

		panelTaulell.add(panelCentral, BorderLayout.CENTER);

		return panelTaulell;
	}
	/*
	 * private void setTaulell(JPanel panelCentral) { GridLayout layoutTaulell = new
	 * GridLayout(10, 10); panelCentral.setLayout(layoutTaulell); int color = -1;
	 * 
	 * taulell = new LinkedHashMap<String, JButton>(); Dimension size = new
	 * Dimension(50, 50);
	 * 
	 * for (int x = 0; x < 10; x++) { if (color == 0 || color == -1) color++; else
	 * color--; for (int y = 0; y < 10; y++) { taulell.put(x + "" + y,
	 * createButton("", size, color)); if (color == 0) color++; else color--; } }
	 * 
	 * for (Map.Entry<String, JButton> entry : taulell.entrySet()) {
	 * panelCentral.add(entry.getValue()); }
	 * 
	 * }
	 */

	private void setNouTaulell(JPanel panelCentral) {
		GridLayout layoutTaulell = new GridLayout(10, 10);
		panelCentral.setLayout(layoutTaulell);
		int color = -1;

		taulell = new LinkedHashMap<String, JButton>();
		Dimension size = new Dimension(50, 50);

		try {

			Image myImage = ImageIO.read(getClass().getResource("/PeoNegre.png"));
			myImage = myImage.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
			ImageIcon peoNegre = new ImageIcon(myImage);

			Image myImage2 = ImageIO.read(getClass().getResource("/PeoBlanca.png"));
			myImage2 = myImage2.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
			ImageIcon peoBlanca = new ImageIcon(myImage2);

			for (int x = 0; x < 10; x++) {
				if (color == 0 || color == -1)
					color++;
				else
					color--;
				for (int y = 0; y < 10; y++) {
					if ((x == 0 && y % 2 != 0) || (x == 1 && y % 2 == 0) || (x == 2 && y % 2 != 0)
							|| (x == 3 && y % 2 == 0))
						taulell.put(x + ";" + y, createButton("", size, color, peoNegre));
					else {

						if ((x == 7 && y % 2 == 0) || (x == 8 && y % 2 != 0) || (x == 9 && y % 2 == 0)
								|| (x == 6 && y % 2 != 0))
							taulell.put(x + ";" + y, createButton("", size, color, peoBlanca));
						else
							taulell.put(x + ";" + y, createButton("", size, color, null));
					}

					if (color == 0)
						color++;
					else
						color--;
				}
			}

			for (Map.Entry<String, JButton> entry : taulell.entrySet()) {
				panelCentral.add(entry.getValue());
			}

		} catch (IOException e) {
			System.out.println("setNouTaulell Error: " + e);
		}

	}

	private JButton createButton(String text, Dimension size, int color, ImageIcon peo) {
		JButton button = new JButton(text);
		if (peo != null)
			button.setIcon(peo);
		button.setPreferredSize(size);
		button.addActionListener(this);
		button.setMinimumSize(size);
		button.setMaximumSize(size);
		button.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		switch (color) {
		case 0:
			button.setBackground(new Color(237, 215, 178));
			break;
		case 1:
			button.setBackground(new Color(95, 95, 95));
		}
		return button;
	}

	private void AjustaPantalla(JPanel panelNord, JPanel panelSud, JPanel panelEst, JPanel panelOest) {

		panelTaulell.add(panelNord, BorderLayout.NORTH);
		panelTaulell.add(panelSud, BorderLayout.SOUTH);
		panelTaulell.add(panelEst, BorderLayout.EAST);
		panelTaulell.add(panelOest, BorderLayout.WEST);

		panelNord.setBackground(Color.DARK_GRAY);
		panelSud.setBackground(Color.DARK_GRAY);
		panelEst.setBackground(Color.DARK_GRAY);
		panelOest.setBackground(Color.DARK_GRAY);

		panelNord.add(Box.createRigidArea(new Dimension(0, 100)));
		panelSud.add(Box.createRigidArea(new Dimension(0, 100)));
		panelEst.add(Box.createRigidArea(new Dimension(250, 0)));
		panelOest.add(Box.createRigidArea(new Dimension(250, 0)));

	}

	public void actionPerformed(ActionEvent e) {

		for (Map.Entry<String, JButton> entry : taulell.entrySet()) {
			if (entry.getValue() == e.getSource()) {
				MourePessa(entry);
			}
		}

	}

	private void MourePessa(Map.Entry<String, JButton> pessa) { // id26
		if (posInicial.equals("")) {
			posInicial = pessa.getKey();
		} else {
			if (posFinal.equals("")) {
				posFinal = pessa.getKey();
				System.out.println(posInicial + " " + posFinal);
				String moviment = interficieBase.getAPI().ferMoviment(interficieBase.getPlayerID(), "26", posInicial,
						posFinal);
				System.out.println(moviment);
				if (Boolean.parseBoolean(moviment)) {
					System.out.println("polla!");
				} else {
					System.out.println("polla2!");
				}
			}
		}
	}

}
