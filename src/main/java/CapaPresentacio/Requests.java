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

import org.json.JSONException;
import org.json.JSONObject;

public class Requests extends JPanel implements ActionListener, ListSelectionListener {

	static BaseInterficie interficieBase;
	JPanel panelRequests, panelCentral, panelNord, panelSud, panelEst, panelOest;
	JLabel labelErrorMessage;
	RestAPI api;
	JButton bAcceptGame, bRefresh, bRefuseGame;
	JList<String> listPartides;
	JScrollPane scrollPanel;
	JLabel icon;
	String contrincant;

	public Requests(BaseInterficie base, RestAPI API) {
		interficieBase = base;
		api = API;

		panelNord = new JPanel();
		panelSud = new JPanel();
		panelEst = new JPanel();
		panelOest = new JPanel();
		listPartides = new JList<String>();
		panelCentral = new JPanel();
		labelErrorMessage = new JLabel();
		scrollPanel = new JScrollPane(listPartides);
	}

	public JPanel RequestsGameCreate() {
		panelRequests = new JPanel();

		panelRequests.setLayout(new BorderLayout());

		labelErrorMessage.setFont(new Font("SansSerif", Font.BOLD, 20));
		labelErrorMessage.setForeground(new Color(237, 215, 178));

		AjustaPantalla(panelNord, panelSud, panelEst, panelOest);
		listPartides.setBackground(Color.GRAY);
		panelCentral.setBackground(Color.GRAY);
		panelRequests.add(panelCentral, BorderLayout.CENTER);

		listPartides.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		scrollPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		panelCentral.setLayout(null);
		BotonsSud(panelSud);
		addPlayers();

		return panelRequests;
	}

	private void AjustaPantalla(JPanel panelNord, JPanel panelSud, JPanel panelEst, JPanel panelOest) {

		panelRequests.add(panelNord, BorderLayout.NORTH);
		panelRequests.add(panelSud, BorderLayout.SOUTH);
		panelRequests.add(panelEst, BorderLayout.EAST);
		panelRequests.add(panelOest, BorderLayout.WEST);

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
		JLabel TitolUsuaris = new JLabel("Request");
		TitolUsuaris.setForeground(new Color(237, 215, 178));
		TitolUsuaris.setFont(new Font("Monospaced", Font.BOLD, 60));

		panelNord.add(TitolUsuaris);
	}

	private void BotonsSud(JPanel panelSud) {
		bRefresh = new JButton("Refresh");
		bAcceptGame = new JButton("Accept");
		bRefuseGame = new JButton("Refuse");

		bRefresh.setBackground(Color.GRAY);
		bRefresh.setForeground(Color.WHITE);
		bAcceptGame.setBackground(Color.GRAY);
		bAcceptGame.setForeground(Color.WHITE);
		bRefuseGame.setBackground(Color.GRAY);
		bRefuseGame.setForeground(Color.WHITE);

		bRefresh.setOpaque(true);
		bAcceptGame.setOpaque(true);
		bRefuseGame.setOpaque(true);

		bRefresh.setPreferredSize(new Dimension(100, 40));
		bAcceptGame.setPreferredSize(new Dimension(300, 40));
		bRefuseGame.setPreferredSize(new Dimension(300, 40));

		bRefresh.setFont(new Font("SansSerif", Font.BOLD, 14));
		bAcceptGame.setFont(new Font("SansSerif", Font.BOLD, 14));
		bRefuseGame.setFont(new Font("SansSerif", Font.BOLD, 14));

		bRefresh.addActionListener(this);
		bAcceptGame.addActionListener(this);
		bRefuseGame.addActionListener(this);

		panelSud.add(bRefresh);
		panelSud.add(Box.createRigidArea(new Dimension(20, 0)));
		panelSud.add(bAcceptGame);
		panelSud.add(Box.createRigidArea(new Dimension(20, 0)));
		panelSud.add(bRefuseGame);

	}

	private void addPlayers() {
		try {
			String APIplayers = api.solicituds(interficieBase.getPlayerID());

			JSONObject json = new JSONObject(APIplayers);

			String err = json.getString("err");
			String Mss = json.getString("res");

			if (!Mss.equals("")) {
				String player[] = Mss.split(";");
				panelCentral.setVisible(false);
				listPartides.setVisible(true);
				scrollPanel.setVisible(true);
				panelRequests.add(scrollPanel, BorderLayout.CENTER);
				panelRequests.repaint();
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
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	private void Error(String mess, String imag, int x, int y, int w, int h) {
		panelCentral.setVisible(true);
		listPartides.setVisible(false);
		scrollPanel.setVisible(false);
		if (icon != null)
			panelCentral.remove(icon);
		panelRequests.add(panelCentral, BorderLayout.CENTER);
		panelRequests.repaint();
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
			bAcceptGame.setText("Accept");
			bRefuseGame.setText("Refuse");

			panelCentral.add(icon);
			panelCentral.add(labelErrorMessage);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == bRefresh) {
			addPlayers();
			bAcceptGame.setText("Accept");
			bRefuseGame.setText("Refuse");
		} else {
			if (e.getSource() == bAcceptGame) {
				Acceptar();
			} else {
				Rebutjar();
			}
		}

	}

	private void Rebutjar() {
		api.rebutjaSol(interficieBase.getPlayerID(), contrincant);
		addPlayers();
	}

	private void Acceptar() {
		api.acceptaSol(interficieBase.getPlayerID(), contrincant);
		addPlayers();
	}

	public void valueChanged(ListSelectionEvent e) {
		String str = (String) listPartides.getSelectedValue();
		contrincant = str;
		bAcceptGame.setText("Accept " + str);
		bRefuseGame.setText("Refuse " + str);

	}

}
