package CapaAplicacio;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Login extends JPanel implements ActionListener {

	JLabel labelMain, labelUsername, labelPassword;

	JButton bEntrar;
	JTextField tfUsuari;
	JPasswordField fPassword;

	public Login() {

	}

	public JPanel LoginCreate() {
		JPanel panelLogin = new JPanel();

		panelLogin.setLayout(new SpringLayout());

		JLabel labelName = new JLabel("Enter Name: ");
		JLabel labelDescription = new JLabel("Enter Description: ");
		JTextField textName = new JTextField(20);
		JTextField textDescription = new JTextField(20);
		JButton buttonLogin = new JButton("Button Me");

		labelMain = new JLabel("Login");

		labelUsername = new JLabel("Usuari");
		labelPassword = new JLabel("Contrasenya");

		tfUsuari = new JTextField();
		fPassword = new JPasswordField();

		bEntrar = new JButton("Entrar");

		labelMain.setBounds(210, 10, 200, 30);
		labelUsername.setBounds(80, 50, 200, 30);
		labelPassword.setBounds(80, 90, 200, 30);
		tfUsuari.setBounds(200, 50, 200, 30);
		fPassword.setBounds(200, 90, 200, 30);
		bEntrar.setBounds(190, 140, 100, 30);

		labelMain.setFont(new Font("SansSerif", Font.BOLD, 16));

		panelLogin.add(labelMain);
		panelLogin.add(labelUsername);
		panelLogin.add(labelPassword);
		panelLogin.add(tfUsuari);
		panelLogin.add(fPassword);
		panelLogin.add(bEntrar);

		return panelLogin;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String Usuari = labelUsername.getText();
		String Password = labelPassword.getText();

	}

}
