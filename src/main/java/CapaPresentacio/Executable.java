package CapaPresentacio;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;

public class Executable {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final BaseInterficie frame = new BaseInterficie();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setSize(1200, 700);
					frame.setLocationRelativeTo(null);
					frame.setTitle("Joc de Dames");
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
					frame.addWindowListener(new WindowAdapter() {

						@Override
						public void windowClosing(WindowEvent event) {
							frame.CloseConnection();
						}

					});

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
