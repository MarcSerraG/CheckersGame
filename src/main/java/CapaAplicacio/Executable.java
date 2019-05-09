package CapaAplicacio;

import java.awt.EventQueue;

public class Executable {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setSize(500, 250);
					frame.setLocationRelativeTo(null);
					frame.setTitle("Joc de Dames");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
