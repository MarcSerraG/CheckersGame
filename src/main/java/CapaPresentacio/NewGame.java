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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONObject;

import CapaAplicacio.JocAPI;

public class NewGame extends JPanel implements ActionListener, ListSelectionListener {

	static BaseInterficie interficieBase;
	JPanel panelNewGame, panelCentral, panelNord, panelSud, panelEst, panelOest;
	JLabel labelMessage, labelErrorMessage;
	JocAPI api;
	JButton bRefresh;
	JButton bPlayGame;
	JList<String> listUsuaris;
	JScrollPane scrollPanel;
	JLabel icon;
	String Contrincant;

	public NewGame(BaseInterficie base, JocAPI API) {
		interficieBase = base;
		api = API;

		panelNord = new JPanel();
		panelSud = new JPanel();
		panelEst = new JPanel();
		panelOest = new JPanel();
		listUsuaris = new JList<String>();
		panelCentral = new JPanel();
		labelMessage = new JLabel();
		labelErrorMessage = new JLabel();
		scrollPanel = new JScrollPane(listUsuaris);
	}

	public JPanel NewGameCreate() {
		panelNewGame = new JPanel();

		panelNewGame.setLayout(new BorderLayout());

		labelErrorMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
		labelErrorMessage.setForeground(new Color(237, 215, 178));

		AjustaPantalla(panelNord, panelSud, panelEst, panelOest);
		listUsuaris.setBackground(Color.GRAY);
		listUsuaris.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		scrollPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panelCentral.setBackground(Color.GRAY);
		panelNewGame.add(panelCentral, BorderLayout.CENTER);

		panelCentral.setLayout(null);
		BotonsSud(panelSud);
		addPlayers();

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

		panelNord.add(Box.createRigidArea(new Dimension(0, 150)));
		panelSud.add(Box.createRigidArea(new Dimension(0, 170)));
		panelEst.add(Box.createRigidArea(new Dimension(200, 0)));
		panelOest.add(Box.createRigidArea(new Dimension(200, 0)));

		TitolNord(panelNord);
	}

	private void TitolNord(JPanel panelNord) {
		JLabel TitolUsuaris = new JLabel("New Game");
		TitolUsuaris.setForeground(new Color(237, 215, 178));
		TitolUsuaris.setFont(new Font("Monospaced", Font.BOLD, 60));

		panelNord.add(TitolUsuaris);
	}

	private void BotonsSud(JPanel panelSud) {
		bRefresh = new JButton("Refresh");
		bPlayGame = new JButton("Play");
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
			listUsuaris.setVisible(true);
			scrollPanel.setVisible(true);
			panelNewGame.add(scrollPanel, BorderLayout.CENTER);
			panelNewGame.repaint();
			validate();

			DefaultListCellRenderer renderer = (DefaultListCellRenderer) listUsuaris.getCellRenderer();
			renderer.setHorizontalAlignment(SwingConstants.CENTER);

			listUsuaris.setFont(new Font("SansSerif", Font.BOLD, 18));
			listUsuaris.setForeground(Color.white);
			listUsuaris.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listUsuaris.addListSelectionListener(this);

			listUsuaris.setListData(player);
		} else {
			if (sErr.equals("No hi han usuaris connectats.")) {

				Error("Sorry. Nobody is playing", "/NoPlayers.png", 180, 200, 500, 30);

			} else { // ServerError.png

				Error("Internal Server Error. Please try again", "/ServerError.png", 120, 200, 500, 30);
			}
		}

	}

	private void Error(String mess, String imag, int x, int y, int w, int h) {
		panelCentral.setVisible(true);
		listUsuaris.setVisible(false);
		scrollPanel.setVisible(false);
		if (icon != null)
			panelCentral.remove(icon);
		panelNewGame.add(panelCentral, BorderLayout.CENTER);
		panelNewGame.repaint();
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
			bPlayGame.setText("Play");
		} else {
			SendRequest();
		}
	}

	private void SendRequest() {
		api.enviaSol(interficieBase.getPlayerID(), Contrincant);
		JOptionPane.showMessageDialog(null, "Request send to: " + Contrincant);
		addPlayers();
		bPlayGame.setText("Play");
	}

	public void valueChanged(ListSelectionEvent e) {
		String str = (String) listUsuaris.getSelectedValue();
		bPlayGame.setEnabled(true);
		Contrincant = str;
		bPlayGame.setText("Play with " + str);

	}

}
