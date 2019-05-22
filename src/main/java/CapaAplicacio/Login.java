package CapaAplicacio;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import CapaAPI.JocAPI;

public class Login extends JPanel implements ActionListener {

	JLabel labelMain, labelUsername, labelPassword, labelRepeatPassword, labelMessage;

	JButton bEntrar, bRegistrar;
	JTextField tfUsuari;
	JPasswordField fPassword, fRepeatPassword;
	JocAPI api;
	BaseInterficie interficieBase;
	String user;

	public Login(JocAPI api, BaseInterficie base) {
		this.api = api;
		interficieBase = base;
	}

	public JPanel LoginCreate() {
		JPanel panelLogin = new JPanel();

		panelLogin.setLayout(null);

		labelMain = new JLabel("DRAUGHTS", JLabel.TRAILING);
		labelMessage = new JLabel("", JLabel.TRAILING); // Connecting... ; Welcome New User! ;
														// Incorrect Password...

		labelUsername = new JLabel("UserName", JLabel.TRAILING);
		labelPassword = new JLabel("Password", JLabel.TRAILING);
		labelRepeatPassword = new JLabel("Repeat Password", JLabel.TRAILING);

		tfUsuari = new JTextField();
		fPassword = new JPasswordField();
		fRepeatPassword = new JPasswordField();

		bEntrar = new JButton("Let's Play!");
		bRegistrar = new JButton("Register");
		labelMain.setFont(new Font("Monospaced", Font.BOLD, 60));
		labelUsername.setFont(new Font("SansSerif", Font.BOLD, 12));
		labelPassword.setFont(new Font("SansSerif", Font.BOLD, 12));
		labelRepeatPassword.setFont(new Font("SansSerif", Font.BOLD, 12));
		fRepeatPassword.setFont(new Font("SansSerif", Font.BOLD, 12));
		labelMessage.setFont(new Font("SansSerif", Font.BOLD, 12));

		labelMain.setBounds(210, 100, 400, 50);
		labelUsername.setBounds(-225, 220, 500, 30);
		tfUsuari.setBounds(210, 250, 500, 30);
		labelPassword.setBounds(-230, 320, 500, 30);
		fPassword.setBounds(210, 350, 500, 30);
		bEntrar.setBounds(210, 450, 500, 40);
		labelMessage.setBounds(450, 625, 500, 40);

		fPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		tfUsuari.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		fRepeatPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder());

		panelLogin.setBackground(Color.DARK_GRAY);
		bEntrar.setBackground(Color.GRAY);
		bEntrar.setForeground(Color.WHITE);
		bRegistrar.setBackground(Color.GRAY);
		bRegistrar.setForeground(Color.WHITE);
		tfUsuari.setBackground(Color.LIGHT_GRAY);
		fPassword.setBackground(Color.LIGHT_GRAY);
		fRepeatPassword.setBackground(Color.LIGHT_GRAY);
		labelMain.setForeground(new Color(237, 215, 178));
		labelUsername.setForeground(new Color(237, 215, 178));
		labelPassword.setForeground(new Color(237, 215, 178));
		labelRepeatPassword.setForeground(new Color(237, 215, 178));
		labelMessage.setForeground(new Color(237, 215, 178));

		bEntrar.addActionListener(this);
		bRegistrar.addActionListener(this);

		panelLogin.add(labelMain);
		panelLogin.add(labelUsername);
		panelLogin.add(labelPassword);
		panelLogin.add(tfUsuari);
		panelLogin.add(fPassword);
		panelLogin.add(bEntrar);
		panelLogin.add(bRegistrar);
		panelLogin.add(labelMessage);
		panelLogin.add(labelRepeatPassword);
		panelLogin.add(fRepeatPassword);

		return panelLogin;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.bEntrar) {
			APIentrar();
		} else {
			APIregister();
		}

	}

	private void APIregister() {
		String pass = fPassword.getText();
		String repeatPass = fRepeatPassword.getText();

		if (!pass.equals(repeatPass)) {
			this.labelMessage.setText("Passwords are not the same");
		} else {
			JSONObject json = new JSONObject(api.registra(tfUsuari.getText(), fPassword.getText()));
			String Err = json.getString("err");
			String Mss = json.getString("res");

			if (!Err.equals("")) {
				labelMessage.setText(Err);
			} else {
				this.user = Mss;
				interficieBase.setTitle("Joc de Dames          Jugador: " + Mss);
				desbloquejarBotons();
				canviPantalla();
			}
		}
	}

	private void APIentrar() {

		if (tfUsuari.getText().equals("")) {
			labelMessage.setText("UserName is required");
		} else {

			String apiReturn = api.login(tfUsuari.getText(), fPassword.getText());

			JSONObject json = new JSONObject(apiReturn);

			String sErr = json.getString("sErr");
			String Err = json.getString("err");
			String Mss = json.getString("res");

			if (Err.contentEquals("No User")) {
				register();
			} else {
				if (!sErr.equals("")) {
					this.labelMessage.setText(sErr);
				} else {
					if (!Err.equals("")) {
						this.labelMessage.setText(Err);
					} else {
						this.labelMessage.setText("Welcome: " + Mss);
						this.user = Mss;
						interficieBase.setTitle("Joc de Dames          Jugador: " + Mss);
						desbloquejarBotons();
						canviPantalla();
					}
				}
			}

		}
	}

	private void register() {

		labelMain.setBounds(210, 100, 400, 50);
		labelUsername.setBounds(-230, 220, 500, 30);
		tfUsuari.setBounds(210, 250, 500, 30);
		labelPassword.setBounds(-235, 320, 500, 30);
		fPassword.setBounds(210, 350, 500, 30);
		labelRepeatPassword.setBounds(-190, 420, 500, 30);
		fRepeatPassword.setBounds(210, 450, 500, 30);
		bRegistrar.setBounds(210, 520, 500, 40);
		labelMessage.setBounds(450, 625, 500, 40);
		bEntrar.setVisible(false);

	}

	private void desbloquejarBotons() {
		interficieBase.bLogin.setEnabled(false);
		interficieBase.bNewGame.setEnabled(true);
		interficieBase.bContinue_Game.setEnabled(true);
		interficieBase.bStatistics.setEnabled(true);
		interficieBase.bEvents.setEnabled(true);
		interficieBase.bLogOut.setEnabled(true);
	}

	private void canviPantalla() {
		interficieBase.centerPanel.setVisible(false);
		interficieBase.bEvents.setBackground(new Color(237, 215, 178));
		interficieBase.bEvents.setForeground(Color.BLACK);
		interficieBase.bLogin.setBackground(Color.GRAY);
		interficieBase.bLogin.setForeground(Color.WHITE);
	}

}
