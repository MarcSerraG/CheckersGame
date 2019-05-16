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

public class Login extends JPanel implements ActionListener {

	JLabel labelMain, labelUsername, labelPassword, labelMessage;

	JButton bEntrar;
	JTextField tfUsuari;
	JPasswordField fPassword;

	public Login() {

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

		panelLogin.add(labelMain);
		panelLogin.add(labelUsername);
		panelLogin.add(labelPassword);
		panelLogin.add(tfUsuari);
		panelLogin.add(fPassword);
		panelLogin.add(bEntrar);
		panelLogin.add(labelMessage);

		return panelLogin;
	}

	public void actionPerformed(ActionEvent e) {

		String Usuari = labelUsername.getText();
		String Password = labelPassword.getText();

	}

	public void changeMessage(String Message) {
		labelMessage.setText(Message);
	}

}
