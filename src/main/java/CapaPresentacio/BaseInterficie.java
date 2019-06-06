package CapaPresentacio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import CapaAplicacio.JocAPI;
import CapaAplicacio.JocDamesRMIInterface;

public class BaseInterficie extends JFrame implements ActionListener {

	public JButton bLogin, bNewGame, bContinue_Game, bStatistics, bLogOut, bRequests, bRefresh;
	public JPanel centerPanel;
	private JLabel versio;
	private JocDamesRMIInterface api;
	private Login log;
	private static NewGame newGame;
	private ContinueGame ContinueGame;

	public BaseInterficie() {

		// definim parametres de la app
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 30, 400, 30);
		getContentPane().setLayout(new BorderLayout());

		// definim la icona de la app
		ImageIcon ImageIcon = new ImageIcon(getClass().getResource("/Logo.png"));
		Image Image = ImageIcon.getImage();
		this.setIconImage(Image);

		// carreguem els botns del lateral al panell principal
		MenuBar();

		try {
			api = new JocAPI();// usuari i contrasenya del server
			// carrega la pagina de login la qual es situa en el centre
			log = CenterLogin();
			log.labelMessage.setText("Server Connection: Correct");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error Connecting server!");
			// carrega la pagina de login la qual es situa en el centre
			log = CenterLogin();
			log.labelMessage.setText("Server Connection: Fail");
		}

	}

	public String getPlayerID() {
		return log.user;
	}

	public JocDamesRMIInterface getAPI() {
		return api;
	}

	private Login CenterLogin() {

		// Creem la pantalla de login i la situem en el contre de la applicacio
		if (centerPanel != null)
			centerPanel.setVisible(false);
		Login login = new Login(api, this);
		centerPanel = login.LoginCreate();
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().repaint();
		validate();
		return login;
	}

	private void MenuBar() {

		// Creem el menu lateral el qual estara sempre visible

		JPanel menu = new JPanel();

		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

		Dimension size = new Dimension(150, 25);

		// Creem els buttons amb el seu nom y tots amb la mateixa mida cridant a un
		// metode nostre
		this.bLogin = createButton("Login", size);
		this.bNewGame = createButton("New Game", size);
		this.bContinue_Game = createButton("Continue Game", size);
		this.bStatistics = createButton("Statistics", size);
		this.bRequests = createButton("Requests", size);
		this.bLogOut = createButton("Log Out", size);
		this.bRefresh = createButton("Refresh", size);

		// Versio //OK MARC ALPHA 0.0.0.1
		this.versio = new JLabel("Version: 0.0.0.1 (Alpha)");

		// Definim el color de fons dels colors a gris menys el de login ja que sera el
		// seleccionat sempre la primera vegada
		this.bLogin.setBackground(new Color(237, 215, 178));
		this.bNewGame.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);
		this.bRequests.setBackground(Color.GRAY);
		this.bRefresh.setBackground(Color.GRAY);

		// Solucio error color Mac: (No funciona...)

		this.bLogin.setOpaque(true);
		this.bNewGame.setOpaque(true);
		this.bContinue_Game.setOpaque(true);
		this.bStatistics.setOpaque(true);
		this.bRequests.setOpaque(true);
		this.bLogOut.setOpaque(true);
		this.bRefresh.setOpaque(true);

		// Definim el color de les lletres dels botons a blanc menys la de login que
		// sera negre
		this.bLogin.setForeground(Color.BLACK);
		this.bNewGame.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
		this.bRequests.setForeground(Color.WHITE);
		this.versio.setForeground(Color.WHITE);
		this.bRefresh.setForeground(Color.WHITE);

		// Definim que els botons "llancin" una accio al ser premuts
		this.bLogin.addActionListener(this);
		this.bNewGame.addActionListener(this);
		this.bContinue_Game.addActionListener(this);
		this.bStatistics.addActionListener(this);
		this.bRequests.addActionListener(this);
		this.bLogOut.addActionListener(this);
		this.bRefresh.addActionListener(this);

		// Bloquejem tots els botons de tal manera que l'usuari tingui de entrar avans
		// de poder cambiar de pantalla
		this.bNewGame.setEnabled(false);
		this.bContinue_Game.setEnabled(false);
		this.bStatistics.setEnabled(false);
		this.bRequests.setEnabled(false);
		this.bLogOut.setEnabled(false);
		this.bRefresh.setEnabled(false);

		this.bRefresh.setVisible(false);

		// carreguem el logo del joc que esta situat adalt a la dreta
		Image myImage;
		try {
			myImage = ImageIO.read(getClass().getResource("/Logo.png"));

			myImage = myImage.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
			ImageIcon myImageIcon = new ImageIcon(myImage);

			JLabel icon = new JLabel(myImageIcon);

			// afegim al menu els botons i el logo i espais en blanc per separar-los entre
			// si
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
			menu.add(bRequests);
			menu.add(Box.createRigidArea(new Dimension(0, 15)));
			menu.add(bStatistics);
			menu.add(Box.createRigidArea(new Dimension(0, 15)));
			menu.add(bRefresh);
			menu.add(Box.createRigidArea(new Dimension(0, 150))); // 190 sense brefresh
			menu.add(bLogOut);
			menu.add(Box.createRigidArea(new Dimension(0, 50)));
			menu.add(versio);

			// afegim el panell menu a la esquerra del panell principal
			getContentPane().add(menu, BorderLayout.WEST);

			// posem el color de fons del menu a gris fosc
			menu.setBackground(Color.DARK_GRAY);
		} catch (IOException e) {

			// en cas de no carregar el logo del joc mostrem un missatge per pantalla
			JOptionPane.showMessageDialog(null, "Impossible loading game logo: " + e);
		}

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

	public void actionPerformed(ActionEvent e) {

		// Captura qualsevol de les accions que passin en els botons del menu i crida al
		// metode del boto corresponent

		// Per futures versions es pot millorar la crida

		if (e.getSource() == this.bLogin) {
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
						if (e.getSource() == this.bRequests)
							actionRequest();
						else {
							if (e.getSource() == this.bLogOut)
								actionLogOut();
							else
								refresh();
						}
					}
				}
			}
		}
	}

	private void refresh() {

		if (ContinueGame.TornPartidaEnCurs().equals(this.getPlayerID()))
			ContinueGame.ComenssarJoc(true);
		else
			ContinueGame.ComenssarJoc(false);
	}

	private void actionLogin() {
		this.bLogin.setBackground(new Color(237, 215, 178));
		this.bLogin.setForeground(Color.BLACK);

		this.bRefresh.setVisible(false);
		this.bRefresh.setEnabled(false);

		this.bNewGame.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);
		this.bRequests.setBackground(Color.GRAY);

		this.bNewGame.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
		this.bRequests.setForeground(Color.WHITE);
	}

	public void actionNewGame() {
		centerPanel.setVisible(false);
		this.bNewGame.setBackground(new Color(237, 215, 178));
		this.bNewGame.setForeground(Color.BLACK);

		this.bRefresh.setVisible(false);
		this.bRefresh.setEnabled(false);

		this.bLogin.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);
		this.bRequests.setBackground(Color.GRAY);

		this.bLogin.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
		this.bRequests.setForeground(Color.WHITE);

		newGame = new NewGame(this, api);
		centerPanel = newGame.NewGameCreate();

		newGame.setVisible(true);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().repaint();
		validate();
	}

	private void actionContinue() {

		centerPanel.setVisible(false);

		this.bRefresh.setVisible(false);
		this.bRefresh.setEnabled(false);

		this.bContinue_Game.setBackground(new Color(237, 215, 178));
		this.bContinue_Game.setForeground(Color.BLACK);

		this.bLogin.setBackground(Color.GRAY);
		this.bNewGame.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);
		this.bRequests.setBackground(Color.GRAY);

		this.bLogin.setForeground(Color.WHITE);
		this.bNewGame.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
		this.bRequests.setForeground(Color.WHITE);

		ContinueGame = new ContinueGame(this, api);
		centerPanel = ContinueGame.ContinueGameCreate();

		centerPanel.setVisible(true);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().repaint();
		validate();

	}

	private void actionRequest() {

		centerPanel.setVisible(false);

		this.bRequests.setBackground(new Color(237, 215, 178));
		this.bRequests.setForeground(Color.BLACK);

		this.bRefresh.setVisible(false);
		this.bRefresh.setEnabled(false);

		this.bLogin.setBackground(Color.GRAY);
		this.bNewGame.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);

		this.bLogin.setForeground(Color.WHITE);
		this.bNewGame.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);

		Requests req = new Requests(this, api);
		centerPanel = req.RequestsGameCreate();

		centerPanel.setVisible(true);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().repaint();
		validate();
	}

	private void actionStatics() {

		centerPanel.setVisible(false);

		this.bStatistics.setBackground(new Color(237, 215, 178));
		this.bStatistics.setForeground(Color.BLACK);

		this.bRefresh.setVisible(false);
		this.bRefresh.setEnabled(false);

		this.bLogin.setBackground(Color.GRAY);
		this.bNewGame.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bLogOut.setBackground(Color.GRAY);
		this.bRequests.setBackground(Color.GRAY);

		this.bLogin.setForeground(Color.WHITE);
		this.bNewGame.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bLogOut.setForeground(Color.WHITE);
		this.bRequests.setForeground(Color.WHITE);

		Estadistica stat = new Estadistica(this, api);
		centerPanel = stat.StaticsCreate();

		centerPanel.setVisible(true);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().repaint();
		validate();
	}

	private void actionLogOut() {

		api.logout(log.user);
		log = null;
		this.setTitle("Joc de Dames");

		this.bLogin.setBackground(new Color(237, 215, 178));
		this.bLogin.setForeground(Color.BLACK);

		this.bRefresh.setVisible(false);
		this.bRefresh.setEnabled(false);

		this.bLogOut.setBackground(Color.GRAY);
		this.bNewGame.setBackground(Color.GRAY);
		this.bContinue_Game.setBackground(Color.GRAY);
		this.bStatistics.setBackground(Color.GRAY);
		this.bRequests.setBackground(Color.GRAY);

		this.bLogOut.setForeground(Color.WHITE);
		this.bNewGame.setForeground(Color.WHITE);
		this.bContinue_Game.setForeground(Color.WHITE);
		this.bStatistics.setForeground(Color.WHITE);
		this.bRequests.setForeground(Color.WHITE);

		this.bLogin.setEnabled(true);
		this.bNewGame.setEnabled(false);
		this.bContinue_Game.setEnabled(false);
		this.bStatistics.setEnabled(false);
		this.bRequests.setEnabled(false);
		this.bLogOut.setEnabled(false);

		log = CenterLogin();
		log.labelMessage.setText("Server Connection: Correct");

	}

	public void CloseConnection() {
		try {
			// Desconecta del servidor l'usuari i tanca la aplicacio
			if (log.user != null)
				api.logout(log.user);
			else {
				api.getConnectionSQL().tancaConeccio();
				JOptionPane.showMessageDialog(null, "Server Connection Closed");
			}
			System.exit(0);
		} catch (Exception e) {
			this.setTitle(e.toString());
		}

	}

}
