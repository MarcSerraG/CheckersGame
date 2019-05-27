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

import org.json.JSONObject;

public class Partida extends JPanel implements ActionListener {

	BaseInterficie interficieBase;
	JTextField tfUsuari;
	JPanel panelTaulell, panelNord, panelSud, panelEst, panelOest, panelCentral;
	JButton bTaules, bSeleccioInicial;
	JLabel lMessage, lPlayerBlancas, lPlayerNegras; // (Blancas=0, Negras = 1)
	Map<String, JButton> taulell;
	String posInicial = "", posFinal = "";

	public Partida(BaseInterficie base) {
		interficieBase = base;
		panelNord = new JPanel();
		panelSud = new JPanel();
		panelEst = new JPanel();
		panelOest = new JPanel();
		lMessage = new JLabel();

	}

	public JPanel partidaCreate() {
		panelTaulell = new JPanel();

		panelCentral = new JPanel();
		panelCentral.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		panelCentral.setBackground(Color.ORANGE);

		panelTaulell.setLayout(new BorderLayout());

		setNouTaulell(panelCentral);
		AjustaPantalla(panelNord, panelSud, panelEst, panelOest);

		panelTaulell.add(panelCentral, BorderLayout.CENTER);

		return panelTaulell;
	}

	public JPanel partidaCreate(String idPartida) {
		panelTaulell = new JPanel();

		String nouTaulell = interficieBase.getAPI().obtenirTaulerAct(interficieBase.getPlayerID(), idPartida);
		JSONObject json = new JSONObject(nouTaulell);

		String err = json.getString("err");
		String mss = json.getString("res");

		if (!err.equals("")) {
			lMessage.setText(err);
			setNouTaulell(panelCentral);
		} else {
			;
			setAnticTaulell(mss);
		}

		panelTaulell.setLayout(new BorderLayout());

		AjustaPantalla(panelNord, panelSud, panelEst, panelOest);

		panelTaulell.add(panelCentral, BorderLayout.CENTER);

		return panelTaulell;
	}

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

	private void setAnticTaulell(String taulellSQL) {
		if (panelCentral != null) {
			panelCentral.setVisible(false);
			panelCentral.setLayout(null);
		}
		taulell = null;
		panelCentral = new JPanel();
		panelCentral.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		panelCentral.setBackground(Color.ORANGE);

		GridLayout layoutTaulell = new GridLayout(10, 10);
		panelCentral.setLayout(layoutTaulell);

		int color = -1;
		int contador = 0;

		taulell = new LinkedHashMap<String, JButton>();
		Dimension size = new Dimension(50, 50);

		String[] divisioTaulell = taulellSQL.split("[,\n]");
		for (String i : divisioTaulell)
			System.out.print(i);
		System.out.println();

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
					if (divisioTaulell[contador].equals("1")) {
						taulell.put(x + ";" + y, createButton("", size, color, peoNegre));
						System.out.print("1");
					} else {

						if (divisioTaulell[contador].equals("0")) {
							taulell.put(x + ";" + y, createButton("", size, color, peoBlanca));
							System.out.print("0");
						} else {
							taulell.put(x + ";" + y, createButton("", size, color, null));
							System.out.print("x");
						}
					}
					contador++;
					if (color == 0)
						color++;
					else
						color--;
				}
			}

			for (Map.Entry<String, JButton> entry : taulell.entrySet()) {
				panelCentral.add(entry.getValue());
			}
			panelTaulell.add(panelCentral, BorderLayout.CENTER);
			panelCentral.setVisible(true);
			validate();

		} catch (IOException e) {
			System.out.println("setAnticTaulell Error: " + e);
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
		panelEst.add(Box.createRigidArea(new Dimension(250, 0)));
		panelOest.add(Box.createRigidArea(new Dimension(250, 0)));
		PanellSud(panelSud);

	}

	private void PanellSud(JPanel panelSud) {
		panelSud.setLayout(new BorderLayout());
		panelSud.add(Box.createRigidArea(new Dimension(0, 100)), BorderLayout.CENTER);
		panelSud.add(lMessage, BorderLayout.WEST);

		lMessage.setText("Tu ets el: " + interficieBase.getAPI().obtenirColor(interficieBase.getPlayerID(), "26"));
		lMessage.setForeground(Color.white);
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
			bSeleccioInicial = pessa.getValue();
			bSeleccioInicial.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		} else {
			if (posFinal.equals("")) {
				posFinal = pessa.getKey();
				System.out.println(posInicial + " " + posFinal);
				String moviment = interficieBase.getAPI().ferMoviment(interficieBase.getPlayerID(), "26", posInicial,
						posFinal);

				JSONObject json = new JSONObject(moviment);

				String err = json.getString("err");
				String mss = json.getString("res");
				String sErr = json.getString("sErr");

				if (sErr.equals("")) {
					if (err.equals("")) {

						if (Boolean.parseBoolean(mss)) {
							lMessage.setText("It's your turn again");

						} else {
							lMessage.setText("It's rival turn");
						}

						String nouTaulell = interficieBase.getAPI().obtenirTaulerAct(interficieBase.getPlayerID(),
								"26");
						json = new JSONObject(nouTaulell);

						err = json.getString("err");
						mss = json.getString("res");

						if (!err.equals("")) {
							lMessage.setText(err);
						} else {

							System.out.println("Taulell: " + mss);
							setAnticTaulell(mss);
						}

					} else {
						lMessage.setText(err);
						posInicial = "";
						posFinal = "";
					}
				} else
					lMessage.setText(sErr);

				bSeleccioInicial.setBorder(BorderFactory.createEmptyBorder());

			}
		}
	}

}
