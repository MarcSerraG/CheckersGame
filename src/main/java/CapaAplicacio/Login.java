package CapaAplicacio;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {

	JLabel labelMain, labelUsername, labelPassword;

	JButton bEntrar;
	JTextField tfUsuari;
	JPasswordField fPassword;

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 30, 400, 30);
		getContentPane().setLayout(null);

		ImageIcon ImageIcon = new ImageIcon(getClass().getResource("Tecno2.png"));
		Image Image = ImageIcon.getImage();
		this.setIconImage(Image);

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

		getContentPane().add(labelMain);
		getContentPane().add(labelUsername);
		getContentPane().add(tfUsuari);
		getContentPane().add(labelPassword);
		getContentPane().add(fPassword);
		getContentPane().add(bEntrar);

	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String Usuari = labelUsername.getText();
		String Password = labelPassword.getText();

	}

}
