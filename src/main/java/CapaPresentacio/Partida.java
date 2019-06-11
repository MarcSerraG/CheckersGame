package CapaPresentacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONObject;

public class Partida extends JPanel implements ActionListener {

	BaseInterficie interficieBase;
	JPanel panelTaulell, panelNord, panelSud, panelEst, panelOest, panelCentral;
	JButton bTaules, bSeleccioInicial, bBufar, bGrabarTirada;
	JLabel lMessage, lPlayerBlancas, lPlayerNegras; // (Blancas=0, Negras = 1)
	Map<JButton, String> taulell2;
	String posInicial = "", posFinal = "", idPartida, NomContrincant, ContrincantColor, JugadorColor;
	Boolean torn;

	public Partida(BaseInterficie base, String contrincant, Boolean torn) {
		interficieBase = base;
		panelNord = new JPanel();
		panelSud = new JPanel();
		panelEst = new JPanel();
		panelOest = new JPanel();
		lMessage = new JLabel();
		NomContrincant = contrincant;
		this.torn = torn;

	}

	public JPanel partidaCreate() {
		panelTaulell = new JPanel();

		panelCentral = new JPanel();
		panelCentral.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 5));
		panelCentral.setBackground(Color.ORANGE);

		panelTaulell.setLayout(new BorderLayout());

		panelTaulell.add(panelCentral, BorderLayout.CENTER);
		AjustaPantalla(panelNord, panelSud, panelEst, panelOest);

		return panelTaulell;
	}

	public JPanel partidaCreate(String idPartida) {
		panelTaulell = new JPanel();
		this.idPartida = idPartida;

		panelTaulell.setLayout(new BorderLayout());

		AjustaPantalla(panelNord, panelSud, panelEst, panelOest);

		String nouTaulell = interficieBase.getAPI().obtenirTaulerAct(interficieBase.getPlayerID(), idPartida);
		JSONObject json = new JSONObject(nouTaulell);

		String err = json.getString("err");
		String mss = json.getString("res");

		if (!err.equals("")) {
			lMessage.setText(err);

		} else {
			setAnticTaulell(mss);
		}
		panelTaulell.add(panelCentral, BorderLayout.CENTER);

		return panelTaulell;
	}

	private void setAnticTaulell(String taulellSQL) {

		if (panelCentral != null) {
			panelCentral.setVisible(false);
			panelCentral.setLayout(null);
		}
		taulell2 = null;
		panelCentral = new JPanel();
		panelCentral.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
		panelCentral.setBackground(Color.ORANGE);

		GridLayout layoutTaulell = new GridLayout(8, 8);
		panelCentral.setLayout(layoutTaulell);

		int color = -1;
		int contador = 0;

		taulell2 = new LinkedHashMap<JButton, String>();
		Dimension size = new Dimension(50, 50);

		String[] divisioTaulell = taulellSQL.split("[,\n]");

		try {

			Image myImage = ImageIO.read(getClass().getResource("/PeoNegre.png"));
			myImage = myImage.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
			ImageIcon peoNegre = new ImageIcon(myImage);

			Image myImage2 = ImageIO.read(getClass().getResource("/PeoBlanca.png"));
			myImage2 = myImage2.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
			ImageIcon peoBlanca = new ImageIcon(myImage2);

			Image myImage3 = ImageIO.read(getClass().getResource("/DamaBlanca.png"));
			myImage3 = myImage3.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
			ImageIcon DamaBlanca = new ImageIcon(myImage3);

			Image myImage4 = ImageIO.read(getClass().getResource("/DamaNegra.png"));
			myImage4 = myImage4.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
			ImageIcon DamaNegra = new ImageIcon(myImage4);

			for (int x = 0; x < 8; x++) {
				switch (color) {
				case -1:
				case 0:
					color++;
					break;
				case 1:
					color--;
					break;
				}

				for (int y = 0; y < 8; y++) {
					switch (divisioTaulell[contador]) {
					case "1":
						taulell2.put(createButton(size, color, peoNegre, ContrincantPesses("Black")), x + ";" + y);
						break;
					case "0":
						taulell2.put(createButton(size, color, peoBlanca, ContrincantPesses("Red")), x + ";" + y);
						break;
					case "d":
						taulell2.put(createButton(size, color, DamaNegra, ContrincantPesses("Black")), x + ";" + y);
						break;
					case "D":
						taulell2.put(createButton(size, color, DamaBlanca, ContrincantPesses("Red")), x + ";" + y);
						break;
					default:
						taulell2.put(createButton(size, color, null, true), x + ";" + y);
						break;
					}

					contador++;

					if (color == 0)
						color++;
					else
						color--;
				}
			}

			for (JButton b : taulell2.keySet())
				panelCentral.add(b);

			panelTaulell.add(panelCentral, BorderLayout.CENTER);
			panelCentral.setVisible(true);
			validate();

		} catch (IOException e) {
			System.out.println("setAnticTaulell Error: " + e);
		}

	}

	private Boolean ContrincantPesses(String pessa) {
		return !ContrincantColor.equals(pessa);
	}

	private JButton createButton(Dimension size, int color, ImageIcon peo, Boolean ContrincantPesses) {
		JButton button = new JButton();
		if (peo != null)
			button.setIcon(peo);
		button.setPreferredSize(size);
		if (ContrincantPesses && torn)
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

	private JButton createButton(Dimension size, Color color, String text) {
		JButton button = new JButton(text);
		button.setForeground(new Color(237, 215, 178));
		button.setPreferredSize(size);
		button.addActionListener(this);
		button.setMinimumSize(size);
		button.setMaximumSize(size);
		button.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		button.setBackground(color);

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
		panelOest.add(Box.createRigidArea(new Dimension(250, 0)));

		PanellEst();
		lMessage.setForeground(Color.WHITE);
		PanellSud();

	}

	private void PanellEst() {
		JPanel ColorJugadorsPanel = new JPanel();

		ColorJugadorsPanel.setLayout(new BoxLayout(ColorJugadorsPanel, BoxLayout.Y_AXIS));
		ColorJugadorsPanel.setBackground(Color.DARK_GRAY);

		JLabel LabelNegres = new JLabel();
		JLabel LabelVermelles = new JLabel();

		String ClJugador = interficieBase.getAPI().obtenirColor(interficieBase.getPlayerID(), idPartida);

		JSONObject json = new JSONObject(ClJugador);

		String err = json.getString("err");
		ClJugador = json.getString("res");
		String sErr = json.getString("sErr");

		if (err.equals("")) {
			JugadorColor = ClJugador;
			if (ClJugador.equals("Red")) {
				ContrincantColor = "Black";
				LabelNegres.setText(NomContrincant + " Black");
				LabelVermelles.setText(interficieBase.getPlayerID() + " Red");
			} else {
				ContrincantColor = "Red";
				LabelNegres.setText(interficieBase.getPlayerID() + " Black");
				LabelVermelles.setText(NomContrincant + " Red");
			}
		} else
			lMessage.setText("Error: " + err + " " + sErr);

		LabelNegres.setFont(new Font("SansSerif", Font.BOLD, 20));
		LabelNegres.setForeground(new Color(237, 215, 178));

		LabelVermelles.setFont(new Font("SansSerif", Font.BOLD, 20));
		LabelVermelles.setForeground(new Color(237, 215, 178));

		ColorJugadorsPanel.add(LabelNegres);
		ColorJugadorsPanel.add(Box.createRigidArea(new Dimension(0, 370)));
		ColorJugadorsPanel.add(LabelVermelles);
		panelEst.add(Box.createRigidArea(new Dimension(150, 0)));

		panelEst.add(ColorJugadorsPanel);

	}

	private void PanellSud() {
		panelSud.setLayout(new BorderLayout());

		Dimension size = new Dimension(150, 25);

		JPanel panelCSud = new JPanel();
		panelCSud.setLayout(new BoxLayout(panelCSud, BoxLayout.X_AXIS));
		panelCSud.setBackground(Color.DARK_GRAY);

		bTaules = createButton(size, Color.GRAY, "Fer Taules");
		bBufar = createButton(size, Color.GRAY, "Bufar");
		bGrabarTirada = createButton(size, Color.GRAY, "Grabar Tirada");

		panelCSud.add(bTaules);
		panelCSud.add(Box.createRigidArea(new Dimension(25, 0)), BorderLayout.WEST);
		panelCSud.add(bGrabarTirada);
		panelCSud.add(Box.createRigidArea(new Dimension(25, 0)), BorderLayout.WEST);
		panelCSud.add(bBufar);

		panelSud.add(Box.createRigidArea(new Dimension(243, 110)), BorderLayout.WEST);
		panelSud.add(panelCSud, BorderLayout.CENTER);
		panelSud.add(lMessage, BorderLayout.EAST);

		if (torn)
			lMessage.setText("It's your turn!");
		else
			lMessage.setText("It's " + NomContrincant + " turn");

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bGrabarTirada)
			GrabaTirada();
		else if (e.getSource() != bTaules && e.getSource() != bBufar)
			MourePessa((JButton) e.getSource(), taulell2.get(e.getSource()));

	}

	private void MourePessa(JButton boto, String posicioBoto) {

		if (posInicial.equals("")) {
			posInicial = posicioBoto;
			bSeleccioInicial = boto;
			bSeleccioInicial.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
			VeurePossiblesMoviments(posicioBoto);
		} else {
			if (posFinal.equals("")) {
				posFinal = posicioBoto;
				String moviment = interficieBase.getAPI().ferMoviment(interficieBase.getPlayerID(), idPartida,
						posInicial, posFinal);

				JSONObject json = new JSONObject(moviment);

				String err = json.getString("err");
				String mss = json.getString("res");
				String sErr = json.getString("sErr");

				if (Boolean.parseBoolean(mss)) {

					json = new JSONObject(
							interficieBase.getAPI().obtenirTaulerRes(interficieBase.getPlayerID(), idPartida));
					err = json.getString("err");
					mss = json.getString("res");

					if (err.equals("")) {
						setAnticTaulell(mss);
						lMessage.setText("Graba la Tirada!");
					} else {
						lMessage.setText(err);
					}

				}

				for (JButton b : taulell2.keySet())
					b.setBorder(BorderFactory.createEmptyBorder());

			}
		}
	}

	private void VeurePossiblesMoviments(String pos) {

		String moviments = interficieBase.getAPI().movsPessa(interficieBase.getPlayerID(), idPartida, pos);
		JSONObject json = new JSONObject(moviments);

		String err = json.getString("err");
		String mss = json.getString("res");
		// String sErr = json.getString("sErr");

		if (!err.equals(""))
			lMessage.setText(err);
		else {

			String[] posicions = mss.split("-");

			for (String posicio : posicions) {
				for (Map.Entry<JButton, String> entry : taulell2.entrySet()) {
					if (entry.getValue().equals(posicio)) {
						entry.getKey().setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
					}
				}
			}
		}
	}

	private void GrabaTirada() {
		String grabar = interficieBase.getAPI().grabarTirada(interficieBase.getPlayerID(), idPartida);
		JSONObject json = new JSONObject(grabar);

		String err = json.getString("err");
		String mss = json.getString("res");

		if (err.equals("")) {
			switch (mss) {
			case "guanya":
				JOptionPane.showMessageDialog(null, "YOU WIN!");
				break;
			case "perd":
				JOptionPane.showMessageDialog(null, "LOOOOOOSEEERR!!!");
				break;
			// case "continua":
			// lMessage.setText("It's your turn Again!");
			// interficieBase.refresh();
			// break;
			case "taules":
				JOptionPane.showMessageDialog(null, "Perdre per Taules? LOOOSSEEERRR!");
				break;
			default:
				lMessage.setText("It's " + NomContrincant + " turn");

			}
		} else {
			lMessage.setText(err);
		}
	}

}
