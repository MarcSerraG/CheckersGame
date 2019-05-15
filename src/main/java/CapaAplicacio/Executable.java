package CapaAplicacio;

import java.awt.EventQueue;

public class Executable {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BaseInterficie frame = new BaseInterficie();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setSize(1200, 700);
					frame.setLocationRelativeTo(null);
					frame.setTitle("Joc de Dames");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
