package CapaPresentacio;

import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.json.JSONObject;

import CapaAplicacio.JocAPI;

public class Estadistica extends JPanel {

	JLabel labelMain, labelUsername, labelWins, labelLost, labelMatchesTotal, labelRatio, labelTaules;
	JLabel labelRank;

	JList<String> listRank;

	String[] rank;

	static BaseInterficie interficieBase;
	JocAPI api;

	private String matches, win, lost, taules, ratio;

	public Estadistica(BaseInterficie base, JocAPI API) {
		interficieBase = base;
		listRank = new JList<String>();
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) listRank.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		api = API;
		ApiGetStats();
	}

	public JPanel StaticsCreate() {

		JPanel panStatics = new JPanel();

		panStatics.setBackground(Color.DARK_GRAY);

		panStatics.setLayout(null);

		labelUsername = new JLabel(interficieBase.getPlayerID(), JLabel.TRAILING);
		labelUsername.setBounds(-100, 90, 400, 50);
		int a = 180;
		labelMatchesTotal = new JLabel("Matches: " + matches, JLabel.TRAILING);
		labelMatchesTotal.setBounds(0, a, 400, 50);

		labelWins = new JLabel("Won:     " + win, JLabel.TRAILING);
		labelWins.setBounds(0, a = a + 60, 400, 50);

		labelLost = new JLabel("Lost:    " + lost, JLabel.TRAILING);
		labelLost.setBounds(0, a = a + 60, 400, 50);

		labelRatio = new JLabel("Ratio W/L:    " + ratio, JLabel.TRAILING);
		labelRatio.setBounds(0, a = a + 60, 400, 50);

		labelTaules = new JLabel("Draws:    " + taules, JLabel.TRAILING);
		labelTaules.setBounds(0, a = a + 60, 400, 50);

		labelRank = new JLabel("Top Global Players:", JLabel.TRAILING);
		labelRank.setBounds(460, 90, 400, 50);

		panStatics.add(labelUsername);
		panStatics.add(labelRank);
		panStatics.add(labelMatchesTotal);
		panStatics.add(labelWins);
		panStatics.add(labelLost);
		panStatics.add(labelRatio);
		panStatics.add(labelTaules);

		labelUsername.setFont(new Font("Monospace", Font.BOLD, 50));
		labelUsername.setForeground(new Color(237, 215, 178));

		labelMatchesTotal.setFont(new Font("Monospace", Font.PLAIN, 40));
		labelMatchesTotal.setForeground(new Color(237, 215, 178));

		labelWins.setFont(new Font("Monospace", Font.PLAIN, 40));
		labelWins.setForeground(new Color(237, 215, 178));

		labelLost.setFont(new Font("Monospace", Font.PLAIN, 40));
		labelLost.setForeground(new Color(237, 215, 178));

		labelRatio.setFont(new Font("Monospace", Font.PLAIN, 40));
		labelRatio.setForeground(new Color(237, 215, 178));

		labelTaules.setFont(new Font("Monospace", Font.PLAIN, 40));
		labelTaules.setForeground(new Color(237, 215, 178));

		labelRank.setFont(new Font("Monospace", Font.PLAIN, 40));
		labelRank.setForeground(new Color(237, 215, 178));

		// List Ranking
		listRank.setVisible(true);
		listRank.setFont(new Font("SansSerif", Font.BOLD, 18));
		listRank.setForeground(new Color(237, 215, 178));
		listRank.setSelectionMode(0);
		listRank.setBackground(Color.gray);
		listRank.setBounds(500, 150, 400, 400);
		listRank.setListData(rank);
		listRank.setSelectedIndex(0); // TODO: que no es pugui seleccionar

		panStatics.add(listRank);

		return panStatics;
	}

	private void ApiGetStats() {

		String res = api.getEstadistics(interficieBase.getPlayerID());
		JSONObject json = new JSONObject(res);

		String stat = json.getString("res");

		String[] st = stat.split(";");

		if (st.length == 0)
			System.out.println("Error no hi ha estadistiques.");
		else {
			int rk = 0;

			for (int i = 0; i < st.length; i++) {
				if (i == 0)
					matches = st[i];
				else if (i == 1)
					win = st[i];
				else if (i == 2)
					lost = st[i];
				else if (i == 3)
					taules = st[i];
				else if (i == 4)
					ratio = st[i];
				else if (i == 5) {
					rank = new String[Integer.parseInt(st[i])];
				} else {
					rank[rk] = (rk + 1) + "       " + st[i];
					rk++;
				}
			}
		}

	}

}
