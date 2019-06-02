package CapaPresentacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONObject;

import CapaAplicacio.JocAPI;

public class ContinueGame extends JPanel implements ActionListener, ListSelectionListener {

	static BaseInterficie interficieBase;
	JPanel panelContinueGame, panelCentral, panelNord, panelSud, panelEst, panelOest;
	JLabel labelErrorMessage;
	JocAPI api;
	JButton bPlayGame, bRefresh, bYourTurn, bRivalTurn, bFinishedMatches;
	String contrincant;
	JList<String> listPartides;
	JScrollPane scrollPanel;
	JLabel icon;
	Boolean torn;

	public ContinueGame(BaseInterficie base, JocAPI API) {
		interficieBase = base;
		api = API;
		Dimension size = new Dimension(150, 25);

		panelNord = new JPanel();
		panelSud = new JPanel();
		panelEst = new JPanel();
		panelOest = new JPanel();
		bYourTurn = createButton("Your Turn", size);
		bRivalTurn = createButton("Rival's Turn", size);
		bFinishedMatches = createButton("Finished Matches", size);
		listPartides = new JList<String>();
		panelCentral = new JPanel();
		labelErrorMessage = new JLabel();
		scrollPanel = new JScrollPane(listPartides);
	}

	public JPanel ContinueGameCreate() {
		panelContinueGame = new JPanel();

		panelContinueGame.setLayout(new BorderLayout());

		labelErrorMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
		labelErrorMessage.setForeground(new Color(237, 215, 178));

		AjustaPantalla(panelNord, panelSud, panelEst, panelOest);
		listPartides.setBackground(Color.GRAY);
		panelCentral.setBackground(Color.GRAY);
		panelContinueGame.add(panelCentral, BorderLayout.CENTER);

		listPartides.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		scrollPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		panelCentral.setLayout(null);
		BotonsSud(panelSud);
		BotonsEst(panelEst);
		YourTurn();

		return panelContinueGame;
	}

	private JButton createButton(String text, Dimension size) {

		// Metode propi, crea un Jbutton amb el text indicat i de la mida indicada i el
		// retorna

		JButton button = new JButton(text);
		button.setPreferredSize(size);
		button.setMinimumSize(size);
		button.setMaximumSize(size);
		return button;
	}

	private void AjustaPantalla(JPanel panelNord, JPanel panelSud, JPanel panelEst, JPanel panelOest) {

		panelContinueGame.add(panelNord, BorderLayout.NORTH);
		panelContinueGame.add(panelSud, BorderLayout.SOUTH);
		panelContinueGame.add(panelEst, BorderLayout.EAST);
		panelContinueGame.add(panelOest, BorderLayout.WEST);

		panelNord.setBackground(Color.DARK_GRAY);
		panelSud.setBackground(Color.DARK_GRAY);
		panelEst.setBackground(Color.DARK_GRAY);
		panelOest.setBackground(Color.DARK_GRAY);

		panelNord.add(Box.createRigidArea(new Dimension(0, 150)));
		panelSud.add(Box.createRigidArea(new Dimension(0, 170)));
		panelEst.add(Box.createRigidArea(new Dimension(10, 0)));
		panelOest.add(Box.createRigidArea(new Dimension(200, 0)));

		TitolNord(panelNord);
	}

	private void TitolNord(JPanel panelNord) {
		JLabel TitolUsuaris = new JLabel("Continue Game");
		TitolUsuaris.setForeground(new Color(237, 215, 178));
		TitolUsuaris.setFont(new Font("Monospaced", Font.BOLD, 60));

		panelNord.add(TitolUsuaris);
	}

	private void BotonsSud(JPanel panelSud) {
		bRefresh = new JButton("Refresh");
		bPlayGame = new JButton("Continue gaming");
		bPlayGame.setEnabled(false);

		bRefresh.setBackground(Color.GRAY);
		bRefresh.setForeground(Color.WHITE);
		bPlayGame.setBackground(Color.GRAY);
		bPlayGame.setForeground(Color.WHITE);
		bRefresh.setOpaque(true);
		bPlayGame.setOpaque(true);

		bRefresh.setPreferredSize(new Dimension(100, 40));
		bPlayGame.setPreferredSize(new Dimension(300, 40));

		bRefresh.setFont(new Font("SansSerif", Font.BOLD, 14));
		bPlayGame.setFont(new Font("SansSerif", Font.BOLD, 14));

		bRefresh.addActionListener(this);
		bPlayGame.addActionListener(this);

		panelSud.add(bRefresh);
		panelSud.add(Box.createRigidArea(new Dimension(180, 0)));
		panelSud.add(bPlayGame);
		panelSud.add(Box.createRigidArea(new Dimension(0, 180)));
		panelSud.add(labelErrorMessage);

	}

	private void BotonsEst(JPanel panelEst) {

		JPanel menu = new JPanel();

		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		menu.setBackground(Color.DARK_GRAY);

		bYourTurn.addActionListener(this);
		bRivalTurn.addActionListener(this);
		bFinishedMatches.addActionListener(this);

		bYourTurn.setBackground(new Color(237, 215, 178));
		bRivalTurn.setBackground(Color.GRAY);
		bFinishedMatches.setBackground(Color.GRAY);

		bYourTurn.setOpaque(true);
		bRivalTurn.setOpaque(true);
		bFinishedMatches.setOpaque(true);

		bYourTurn.setForeground(Color.BLACK);
		bRivalTurn.setForeground(Color.WHITE);
		bFinishedMatches.setForeground(Color.WHITE);

		menu.add(bYourTurn);
		menu.add(Box.createRigidArea(new Dimension(0, 15)));
		menu.add(bRivalTurn);
		menu.add(Box.createRigidArea(new Dimension(0, 15)));
		menu.add(bFinishedMatches);
		menu.add(Box.createRigidArea(new Dimension(40, 0)));
		panelEst.add(menu);
	}

	private void addPlayers(String APIplayers) {

		JSONObject json = new JSONObject(APIplayers);

		String err = json.getString("err");
		String Mss = json.getString("res");

		if (!Mss.equals("")) {
			String player[] = Mss.split(";");
			panelCentral.setVisible(false);
			listPartides.setVisible(true);
			scrollPanel.setVisible(true);
			panelContinueGame.add(scrollPanel, BorderLayout.CENTER);
			panelContinueGame.repaint();
			validate();

			DefaultListCellRenderer renderer = (DefaultListCellRenderer) listPartides.getCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);

			listPartides.setFont(new Font("SansSerif", Font.BOLD, 18));
			listPartides.setForeground(Color.white);
			listPartides.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listPartides.addListSelectionListener(this);

			listPartides.setListData(player);
		} else {
			if (err.equals("No hi ha cap partida")) {

				Error("You don't have friends? Start a New Game Now!", "/NoPlayers.png", 70, 200, 500, 30);

			} else { // ServerError.png

				Error("Internal Server Error. Please try again", "/ServerError.png", 120, 200, 500, 30);
			}
		}

	}

	private void Error(String mess, String imag, int x, int y, int w, int h) {
		panelCentral.setVisible(true);
		listPartides.setVisible(false);
		scrollPanel.setVisible(false);
		if (icon != null)
			panelCentral.remove(icon);
		panelContinueGame.add(panelCentral, BorderLayout.CENTER);
		panelContinueGame.repaint();
		validate();
		Image myImage;
		try {
			myImage = ImageIO.read(getClass().getResource(imag));
			myImage = myImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
			ImageIcon myImageIcon = new ImageIcon(myImage);

			icon = new JLabel(myImageIcon);
			labelErrorMessage.setText(mess);
			icon.setBounds(250, 50, 100, 100);
			labelErrorMessage.setBounds(x, y, w, h);
			bPlayGame.setText("Play");

			panelCentral.add(icon);
			panelCentral.add(labelErrorMessage);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		int presButton = 0;

		if (e.getSource() == bRefresh) {
			switch (presButton) {
			case 0:
				YourTurn();
				break;
			case 1:
				RivalTurn();
				break;
			case 2:
				FineshedMaches();
				break;
			}
		} else {
			if (e.getSource() == bYourTurn) {
				YourTurn();
				presButton = 0;
			} else {
				if (e.getSource() == bRivalTurn) {
					RivalTurn();
					presButton = 1;
				} else {
					if (e.getSource() == bFinishedMatches) {
						FineshedMaches();
						bPlayGame.setEnabled(false);
						presButton = 2;
					} else {
						ComenssarJoc(torn);
					}
				}
			}
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		String str = (String) listPartides.getSelectedValue();
		bPlayGame.setEnabled(true);
		contrincant = str;
		bPlayGame.setText("Continue playing with " + str);

	}

	public void ComenssarJoc(boolean torn) {

		String triaPartida = api.triaPartida(interficieBase.getPlayerID(), contrincant);

		JSONObject json = new JSONObject(triaPartida);

		String err = json.getString("err");
		String Mss = json.getString("res");

		if (!err.equals("")) {
			labelErrorMessage.setText(err);
		} else {
			interficieBase.bRefresh.setVisible(true);
			interficieBase.bRefresh.setEnabled(true);

			AdaptarBaseInterfaceNewJoc();
			interficieBase.centerPanel.setVisible(false);
			Partida partida = new Partida(interficieBase, contrincant, torn);
			interficieBase.centerPanel = partida.partidaCreate(Mss);
			partida.setVisible(true);
			interficieBase.getContentPane().add(interficieBase.centerPanel, BorderLayout.CENTER);
			interficieBase.getContentPane().repaint();
			validate();
		}

	}

	private void AdaptarBaseInterfaceNewJoc() {
		interficieBase.bLogOut.setBackground(Color.GRAY);
		interficieBase.bNewGame.setBackground(Color.GRAY);
		interficieBase.bContinue_Game.setBackground(Color.GRAY);
		interficieBase.bStatistics.setBackground(Color.GRAY);
		interficieBase.bRequests.setBackground(Color.GRAY);

		interficieBase.bLogOut.setForeground(Color.WHITE);
		interficieBase.bNewGame.setForeground(Color.WHITE);
		interficieBase.bContinue_Game.setForeground(Color.WHITE);
		interficieBase.bStatistics.setForeground(Color.WHITE);
		interficieBase.bRequests.setForeground(Color.WHITE);
	}

	public void YourTurn() {
		String APIplayers = api.getPartidesTorn(interficieBase.getPlayerID());
		addPlayers(APIplayers);
		torn = true;

		this.bYourTurn.setBackground(new Color(237, 215, 178));
		this.bYourTurn.setForeground(Color.BLACK);

		this.bRivalTurn.setBackground(Color.GRAY);
		this.bFinishedMatches.setBackground(Color.GRAY);

		this.bRivalTurn.setForeground(Color.WHITE);
		this.bFinishedMatches.setForeground(Color.WHITE);

		bPlayGame.setText("Play");

	}

	public void RivalTurn() {
		String APIplayers = api.getPartidesNoTorn(interficieBase.getPlayerID());
		addPlayers(APIplayers);
		torn = false;

		this.bRivalTurn.setBackground(new Color(237, 215, 178));
		this.bRivalTurn.setForeground(Color.BLACK);

		this.bYourTurn.setBackground(Color.GRAY);
		this.bFinishedMatches.setBackground(Color.GRAY);

		this.bYourTurn.setForeground(Color.WHITE);
		this.bFinishedMatches.setForeground(Color.WHITE);
		bPlayGame.setText("Play");

	}

	public void FineshedMaches() {
		String APIplayers = api.getPartidesAcabades(interficieBase.getPlayerID());
		addPlayers(APIplayers);

		this.bFinishedMatches.setBackground(new Color(237, 215, 178));
		this.bFinishedMatches.setForeground(Color.BLACK);

		this.bYourTurn.setBackground(Color.GRAY);
		this.bRivalTurn.setBackground(Color.GRAY);

		this.bYourTurn.setForeground(Color.WHITE);
		this.bRivalTurn.setForeground(Color.WHITE);
		bPlayGame.setText("Play");

	}

	public String TornPartidaEnCurs() {
		String APIplayers = api.getPartidesTorn(interficieBase.getPlayerID());

		String usuaris[] = APIplayers.split(";");

		for (String s : usuaris)
			if (s.equals(contrincant))
				return interficieBase.getPlayerID();

		return "";
	}
}
