package CapaPresentacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class NewGame extends JPanel implements ActionListener {

	BaseInterficie interficieBase;
	JPanel panelNewGame;

	public NewGame(BaseInterficie base) {
		this.interficieBase = base;
	}

	public JPanel NewGameCreate() {
		panelNewGame = new JPanel();

		panelNewGame.setLayout(new BorderLayout());

		JPanel panelNord = new JPanel();
		JPanel panelSud = new JPanel();
		JPanel panelEst = new JPanel();
		JPanel panelOest = new JPanel();
		JList<String> listUsuaris = new JList<String>();

		AjustaPantalla(panelNord, panelSud, panelEst, panelOest);
		listUsuaris.setBackground(Color.BLUE);

		panelNewGame.add(listUsuaris, BorderLayout.CENTER);

		return panelNewGame;
	}

	private void AjustaPantalla(JPanel panelNord, JPanel panelSud, JPanel panelEst, JPanel panelOest) {

		panelNewGame.add(panelNord, BorderLayout.NORTH);
		panelNewGame.add(panelSud, BorderLayout.SOUTH);
		panelNewGame.add(panelEst, BorderLayout.EAST);
		panelNewGame.add(panelOest, BorderLayout.WEST);

		panelNord.setBackground(Color.DARK_GRAY);
		panelSud.setBackground(Color.DARK_GRAY);
		panelEst.setBackground(Color.DARK_GRAY);
		panelOest.setBackground(Color.DARK_GRAY);

		panelNord.add(Box.createRigidArea(new Dimension(0, 100)));
		panelSud.add(Box.createRigidArea(new Dimension(0, 100)));
		panelEst.add(Box.createRigidArea(new Dimension(100, 0)));
		panelOest.add(Box.createRigidArea(new Dimension(100, 0)));

		TitolNord(panelNord);
	}

	private void TitolNord(JPanel panelNord) {
		JLabel TitolUsuaris = new JLabel("Usuaris Conectats");
		TitolUsuaris.setForeground(Color.white);

		panelNord.add(TitolUsuaris);
	}

	public void actionPerformed(ActionEvent e) {
	}

}
