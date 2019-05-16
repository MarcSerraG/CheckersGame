package CapaAplicacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import CapaPersistencia.ConnectionSQLOracle;

public class BaseInterficie extends JFrame implements ActionListener {

	public JButton bLogin, bNewGame, bContinue_Game, bStatistics, bEvents, bConnected_Players, bLogOut;
	public JPanel centerLogin;
	private ConnectionSQLOracle cn;
	private Login log;

	public BaseInterficie() {

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 30, 400, 30);
		getContentPane().setLayout(new BorderLayout());

		ImageIcon ImageIcon = new ImageIcon(getClass().getResource("Logo.png"));
		Image Image = ImageIcon.getImage();
		this.setIconImage(Image);

		MenuBar();

		ServerConnection();

		log = CenterLogin(cn);
		if (cn != null)
			log.labelMessage.setText("Connection Sucessfully");

	}

	public void ServerConnection() {
		try {
			cn = new ConnectionSQLOracle("g3geilab1", "g3geilab1");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error connecting to server. Please try Again!");
			cn = null;
		}
	}

	private Login CenterLogin(ConnectionSQLOracle conn) {
		Login login = new Login(conn, this);
		centerLogin = login.LoginCreate();
		getContentPane().add(centerLogin, BorderLayout.CENTER);
		login.labelMessage.setText("Connecting...");
		return login;
	}

	private void MenuBar() {
		JPanel menu = new JPanel();

		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

		Dimension size = new Dimension(150, 25);

		this.bLogin = createButton("Login", size);
		this.bNewGame = createButton("New Game", size);
		this.bContinue_Game = createButton("Continue Game", size);
		this.bStatistics = createButton("Statistics", size);
		this.bEvents = createButton("Events", size);
		this.bConnected_Players = createButton("Connected Players", size);
		this.bLogOut = createButton("Log Out", size);

		this.bLogin.setBackground(new Color(237, 215, 178));
		this.bNewGame.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bEvents.setBackground(Color.GRAY);
		this.bConnected_Players.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);

		this.bLogin.setForeground(Color.BLACK);
		this.bNewGame.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bEvents.setForeground(Color.WHITE);
		this.bConnected_Players.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);

		this.bLogin.addActionListener(this);
		this.bNewGame.addActionListener(this);
		this.bContinue_Game.addActionListener(this);
		this.bStatistics.addActionListener(this);
		this.bEvents.addActionListener(this);
		this.bConnected_Players.addActionListener(this);
		this.bLogOut.addActionListener(this);

		this.bNewGame.setEnabled(false);
		this.bContinue_Game.setEnabled(false);
		this.bStatistics.setEnabled(false);
		this.bEvents.setEnabled(false);
		this.bConnected_Players.setEnabled(false);
		this.bLogOut.setEnabled(false);

		Image myImage;
		try {
			myImage = ImageIO.read(getClass().getResource("Logo.png"));

			myImage = myImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
			ImageIcon myImageIcon = new ImageIcon(myImage);

			JLabel icon = new JLabel(myImageIcon);

			menu.add(Box.createRigidArea(new Dimension(0, 20)));
			menu.add(Box.createRigidArea(new Dimension(50, 0)));
			menu.add(icon);
			menu.add(Box.createRigidArea(new Dimension(0, 50)));
			menu.add(bLogin);
			menu.add(Box.createRigidArea(new Dimension(0, 15)));
			menu.add(bNewGame);
			menu.add(Box.createRigidArea(new Dimension(0, 15)));
			menu.add(bContinue_Game);
			menu.add(Box.createRigidArea(new Dimension(0, 15)));
			menu.add(bStatistics);
			menu.add(Box.createRigidArea(new Dimension(0, 15)));
			menu.add(bEvents);
			menu.add(Box.createRigidArea(new Dimension(0, 15)));
			menu.add(bConnected_Players);
			menu.add(Box.createRigidArea(new Dimension(0, 180)));
			menu.add(bLogOut);

			getContentPane().add(menu, BorderLayout.WEST);
			menu.setBackground(Color.DARK_GRAY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private JButton createButton(String text, Dimension size) {
		JButton button = new JButton(text);
		button.setPreferredSize(size);
		button.setMinimumSize(size);
		button.setMaximumSize(size);
		return button;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.bLogin) {
			System.out.print("Boto Login!");
			actionLogin();
		} else {
			if (e.getSource() == this.bNewGame) {
				actionNewGame();

			} else {
				if (e.getSource() == this.bContinue_Game) {
					actionContinue();

				} else {
					if (e.getSource() == this.bStatistics) {
						actionStatics();

					} else {

						if (e.getSource() == this.bEvents) {
							actionEvents();

						} else {
							if (e.getSource() == this.bConnected_Players) {
								actionConnected_Players();

							} else {
								actionLogOut();
							}
						}
					}
				}
			}
		}
	}

	private void actionLogin() {
		this.bLogin.setBackground(new Color(237, 215, 178));
		this.bLogin.setForeground(Color.BLACK);

		this.bNewGame.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bEvents.setBackground(Color.GRAY);
		this.bConnected_Players.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);

		this.bNewGame.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bEvents.setForeground(Color.WHITE);
		this.bConnected_Players.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
	}

	private void actionNewGame() {
		this.bNewGame.setBackground(new Color(237, 215, 178));
		this.bNewGame.setForeground(Color.BLACK);

		this.bLogin.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bEvents.setBackground(Color.GRAY);
		this.bConnected_Players.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);

		this.bLogin.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bEvents.setForeground(Color.WHITE);
		this.bConnected_Players.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
	}

	private void actionContinue() {
		this.bContinue_Game.setBackground(new Color(237, 215, 178));
		this.bContinue_Game.setForeground(Color.BLACK);

		this.bLogin.setBackground(Color.GRAY);
		this.bNewGame.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bEvents.setBackground(Color.GRAY);
		this.bConnected_Players.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);

		this.bLogin.setForeground(Color.WHITE);
		this.bNewGame.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bEvents.setForeground(Color.WHITE);
		this.bConnected_Players.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
	}

	private void actionStatics() {
		this.bStatistics.setBackground(new Color(237, 215, 178));
		this.bStatistics.setForeground(Color.BLACK);

		this.bLogin.setBackground(Color.GRAY);
		this.bNewGame.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bEvents.setBackground(Color.GRAY);
		this.bConnected_Players.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);

		this.bLogin.setForeground(Color.WHITE);
		this.bNewGame.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bEvents.setForeground(Color.WHITE);
		this.bConnected_Players.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
	}

	private void actionEvents() {
		this.bEvents.setBackground(new Color(237, 215, 178));
		this.bEvents.setForeground(Color.BLACK);

		this.bLogin.setBackground(Color.GRAY);
		this.bNewGame.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bConnected_Players.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);

		this.bLogin.setForeground(Color.WHITE);
		this.bNewGame.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bConnected_Players.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
	}

	private void actionConnected_Players() {
		this.bConnected_Players.setBackground(new Color(237, 215, 178));
		this.bConnected_Players.setForeground(Color.BLACK);

		this.bLogin.setBackground(Color.GRAY);
		this.bNewGame.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bEvents.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);

		this.bLogin.setForeground(Color.WHITE);
		this.bNewGame.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bEvents.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
	}

	private void actionLogOut() {
		/*
		 * TODO: desconectar l'usuari i tornar a la pagina de login
		 * this.bLogOut.setBackground(new Color(237, 215, 178));
		 * this.bLogOut.setForeground(Color.BLACK);
		 * 
		 * this.bLogin.setBackground(Color.GRAY);
		 * this.bNewGame.setBackground(Color.GRAY);
		 * this.bContinue_Game.setBackground(Color.GRAY);
		 * this.bStatistics.setBackground(Color.GRAY);
		 * this.bEvents.setBackground(Color.GRAY);
		 * this.bConnected_Players.setBackground(Color.GRAY);
		 * 
		 * this.bLogin.setForeground(Color.WHITE);
		 * this.bNewGame.setForeground(Color.WHITE);
		 * this.bContinue_Game.setForeground(Color.WHITE);
		 * this.bStatistics.setForeground(Color.WHITE);
		 * this.bEvents.setForeground(Color.WHITE);
		 * this.bConnected_Players.setForeground(Color.WHITE);
		 * 
		 */

		CloseConnection();
	}

	public void CloseConnection() {
		try {
			// ToDO: posar a BD l'usuari a 0
			cn.tancaConeccio();
		} catch (SQLException e) {
		}

		System.exit(0);

	}

}
