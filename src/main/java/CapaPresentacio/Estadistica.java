package CapaPresentacio;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import CapaAplicacio.JocAPI;

public class Estadistica extends JPanel {
	
	JLabel labelMain, labelUsername, labelRank, labelWins, labelLost, labelMatchesTotal, labelRatio;
	
	static BaseInterficie interficieBase;
	JocAPI api;
	
	public Estadistica(BaseInterficie base, JocAPI API) {
		interficieBase = base;
		api = API;
	}

	public JPanel StaticsCreate() {
		
		JPanel panStatics = new JPanel();
		
		panStatics.setBackground(Color.DARK_GRAY);

		panStatics.setLayout(null);
		
		labelUsername = new JLabel(interficieBase.getPlayerID(), JLabel.TRAILING);
		labelUsername.setBounds(-100, 100, 400, 50);
		
		labelRank = new JLabel("Rank:    ", JLabel.TRAILING);
		labelRank.setBounds(0,180,400,50);
		
		labelMatchesTotal = new JLabel("Matches: ", JLabel.TRAILING);
		labelMatchesTotal.setBounds(0,270,400,50);
		
		labelWins = new JLabel("Won:     ", JLabel.TRAILING);
		labelWins.setBounds(0,360,400,50);
		
		labelLost = new JLabel("Lost:    ", JLabel.TRAILING);
		labelLost.setBounds(0,450,400,50);
		
		labelRatio = new JLabel("Lost:    ", JLabel.TRAILING);
		labelRatio.setBounds(0,450,400,50);
		
		
		panStatics.add(labelUsername);
		panStatics.add(labelRank);
		panStatics.add(labelMatchesTotal);
		panStatics.add(labelWins);
		panStatics.add(labelLost);
		
		labelUsername.setFont(new Font("Monospace", Font.BOLD, 50));
		labelUsername.setForeground(new Color(237, 215, 178));
		
		labelRank.setFont(new Font("Monospace", Font.PLAIN, 40));
		labelRank.setForeground(new Color(237, 215, 178));
		
		labelMatchesTotal.setFont(new Font("Monospace", Font.PLAIN, 40));
		labelMatchesTotal.setForeground(new Color(237, 215, 178));
		
		labelWins.setFont(new Font("Monospace", Font.PLAIN, 40));
		labelWins.setForeground(new Color(237, 215, 178));
		
		labelLost.setFont(new Font("Monospace", Font.PLAIN, 40));
		labelLost.setForeground(new Color(237, 215, 178));
		
		return panStatics;
	}
	
	

}
