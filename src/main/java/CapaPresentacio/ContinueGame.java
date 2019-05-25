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
	JLabel labelMessage, labelErrorMessage;
	JocAPI api;
	JButton bPlayGame;
	JButton bRefresh;
	JList<String> listPartides;
	JScrollPane scrollPanel;
	JLabel icon;

	public ContinueGame(BaseInterficie base, JocAPI API) {
		interficieBase = base;
		api = API;

		panelNord = new JPanel();
		panelSud = new JPanel();
		panelEst = new JPanel();
		panelOest = new JPanel();
		listPartides = new JList<String>();
		panelCentral = new JPanel();
		labelMessage = new JLabel();
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

		panelCentral.setLayout(null);
		BotonsSud(panelSud);
		addPlayers();

		return panelContinueGame;
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
		panelEst.add(Box.createRigidArea(new Dimension(200, 0)));
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

	}

	private void addPlayers() {
		String APIplayers = api.getCandidatsSol(interficieBase.getPlayerID());

		JSONObject json = new JSONObject(APIplayers);

		String sErr = json.getString("sErr");
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
			if (sErr.equals("No hi han usuaris connectats.")) {

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

		if (e.getSource() == bRefresh) {
			addPlayers();
		}
	}

	public void valueChanged(ListSelectionEvent e) {
		String str = (String) listPartides.getSelectedValue();
		bPlayGame.setEnabled(true);

		bPlayGame.setText("Continue playing with " + str);

	}

}
