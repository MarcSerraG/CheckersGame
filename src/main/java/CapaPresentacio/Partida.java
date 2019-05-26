package CapaPresentacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
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

		setTaulell(panelCentral);
		AjustaPantalla(panelNord, panelSud, panelEst, panelOest);

		panelTaulell.add(panelCentral, BorderLayout.CENTER);

		return panelTaulell;
	}

	private void setTaulell(JPanel panelCentral) {
		GridLayout layoutTaulell = new GridLayout(10, 10);
		panelCentral.setLayout(layoutTaulell);
		int color = -1;

		taulell = new LinkedHashMap<String, JButton>();
		Dimension size = new Dimension(50, 50);

		for (int x = 0; x < 10; x++) {
			if (color == 0 || color == -1)
				color++;
			else
				color--;
			for (int y = 0; y < 10; y++) {
				taulell.put(x + "" + y, createButton("", size, color));
				if (color == 0)
					color++;
				else
					color--;
			}
		}

		for (Map.Entry<String, JButton> entry : taulell.entrySet()) {
			panelCentral.add(entry.getValue());
		}

	}

	private JButton createButton(String text, Dimension size, int color) {
		JButton button = new JButton(text);
		button.setPreferredSize(size);
		button.setMinimumSize(size);
		button.setMaximumSize(size);
		button.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		switch (color) {
		case 0:
			button.setBackground(new Color(237, 215, 178));
			break;
		case 1:
			button.setBackground(Color.DARK_GRAY);
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
		// TODO Auto-generated method stub

	}

}
