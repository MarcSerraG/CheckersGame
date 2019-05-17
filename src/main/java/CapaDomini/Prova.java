package CapaDomini;

import java.util.Scanner;

public class Prova {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Usuari u1 = new Usuari("Blanques");
		Usuari u2 = new Usuari("Negres");
		Partida partida = new Partida("0000", u1, u2);
		Scanner keyboard = new Scanner(System.in);
		int fOrigen, cOrigen, fDesti, cDesti, decisio;
		
		while (partida.getTaulell().getNumNegres()!= 0 || partida.getTaulell().getNumBlanques()!= 0) {
			System.out.println("Que vols fer? (0 = tirar 1 = porposar taules 2 = finalitzar partida)");
			decisio = keyboard.nextInt();
			switch (decisio) {
				
				case 0:	
					System.out.print(partida);
					System.out.println("A quina fila es la fitxa?");
					fOrigen = keyboard.nextInt();
					System.out.println("A quina columna es la fitxa?");
					cOrigen = keyboard.nextInt();
					System.out.println("A quina fila vols moure?");
					fDesti = keyboard.nextInt();
					System.out.println("A quina columna vols moure?");
					cDesti = keyboard.nextInt();
					partida.movimentTorn(partida.getTaulell().seleccionarCasella(fOrigen,cOrigen), partida.getTaulell().seleccionarCasella(fDesti, cDesti));
					break;
				
				case 1:
					System.out.println("Fi del joc");
					keyboard.close();
					System.exit(0);
					break;
					
				case 2:
					System.out.println("Fi del joc");
					keyboard.close();
					System.exit(0);
					break;
			}
		}
		System.out.println("Fi del joc");
		keyboard.close();
	}
}
