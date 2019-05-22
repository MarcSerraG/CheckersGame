package CapaAplicacio;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import CapaPersistencia.ConnectionSQLOracle;
import CapaPersistencia.UsuariSQLOracle;

public class Login extends JPanel implements ActionListener {

	JLabel labelMain, labelUsername, labelPassword, labelMessage;

	JButton bEntrar;
	JTextField tfUsuari;
	JPasswordField fPassword;
	UsuariSQLOracle userSQL;
	BaseInterficie interficieBase;

	public Login(ConnectionSQLOracle connection, BaseInterficie base) {

		userSQL = new UsuariSQLOracle(connection);
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

		tfUsuari = new JTextField();
		fPassword = new JPasswordField();

		bEntrar = new JButton("Let's Play!");
		labelMain.setFont(new Font("Monospaced", Font.BOLD, 60));
		labelUsername.setFont(new Font("SansSerif", Font.BOLD, 12));
		labelPassword.setFont(new Font("SansSerif", Font.BOLD, 12));
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

		panelLogin.setBackground(Color.DARK_GRAY);
		bEntrar.setBackground(Color.GRAY);
		bEntrar.setForeground(Color.WHITE);
		tfUsuari.setBackground(Color.LIGHT_GRAY);
		fPassword.setBackground(Color.LIGHT_GRAY);
		labelMain.setForeground(new Color(237, 215, 178));
		labelUsername.setForeground(new Color(237, 215, 178));
		labelPassword.setForeground(new Color(237, 215, 178));
		labelMessage.setForeground(new Color(237, 215, 178));

		fPassword.addActionListener(this);
		bEntrar.addActionListener(this);

		panelLogin.add(labelMain);
		panelLogin.add(labelUsername);
		panelLogin.add(labelPassword);
		panelLogin.add(tfUsuari);
		panelLogin.add(fPassword);
		panelLogin.add(bEntrar);
		panelLogin.add(labelMessage);

		fPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				comprovarUsuari();
			}
		});

		return panelLogin;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.bEntrar) {
			entrar();
		}

	}

	private void entrar() {

		if (tfUsuari.getText().equals("")) {
			labelMessage.setText("UserName is required");
		} else {
			if (labelMessage.getText().equals("Welcome New User!")) {
				userSQL.insertUsuari(tfUsuari.getText(), fPassword.getText(), "-", "1");
				desbloquejarBotons();
				canviPantalla();
				// toDo = Entrar al Joc
			} else {
				if (fPassword.getText().equals(userSQL.getPasword(tfUsuari.getText()))) {
					labelMessage.setText(tfUsuari.getText() + " is connected!");
					desbloquejarBotons();
					canviPantalla();

					// toDo = Entrar al Joc
				} else {
					labelMessage.setText("Incorrect Password!");
				}
			}
		}
	}

	private void comprovarUsuari() {

		if (!(tfUsuari.getText().equals(""))) {
			String PassSQL = userSQL.getPasword(tfUsuari.getText());
			if (PassSQL == null) {
				labelMessage.setText("Welcome New User!");
			} else {
				labelMessage.setText("Welcome back " + tfUsuari.getText() + "!");
			}
		}

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
		interficieBase.centerLogin.setVisible(false);
		interficieBase.bEvents.setBackground(new Color(237, 215, 178));
		interficieBase.bEvents.setForeground(Color.BLACK);
		interficieBase.bLogin.setBackground(Color.GRAY);
		interficieBase.bLogin.setForeground(Color.WHITE);
	}

}
