package CapaDomini;

import java.util.Scanner;

public class Prova {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Usuari u1 = new Usuari("Blanques");
		Usuari u2 = new Usuari("Negres");
		Partida partida = new Partida("0000", u1, u2);
		partida.getTaulell().seleccionarCasella(6, 1).setFitxa(new Dama(0));
		Scanner keyboard = new Scanner(System.in);
		int fOrigen, cOrigen, fDesti, cDesti, decisio;
		
		while (!partida.getTaulell().comprovarFitxes() && partida.getPartidaEnCurs()) {
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
					partida.setUsuariTorn();
					System.out.println(partida);
					System.out.println("El teu contrincant ha proposat taules.");
					System.out.println("Selecciona casella origen i casella desti o escriu 10 si acceptes");
					fOrigen = keyboard.nextInt();
					if (fOrigen == 10) {
						partida.acabarPartida();
						System.out.println("Empat");
						keyboard.close();
						System.exit(0);
						break;
					}
					else {
						System.out.println("A quina columna es la fitxa?");
						cOrigen = keyboard.nextInt();
						System.out.println("A quina fila vols moure?");
						fDesti = keyboard.nextInt();
						System.out.println("A quina columna vols moure?");
						cDesti = keyboard.nextInt();
						if(partida.comprovarTaules(partida.getTaulell().seleccionarCasella(fOrigen, cOrigen), partida.getTaulell().seleccionarCasella(fDesti, cDesti))) {
							
							System.out.println("El contrincant podia fer un moviment, tu guanyes");
							partida.acabarPartida();
							System.out.println("Fi de joc");
							keyboard.close();
							System.exit(0);
							}
						else {
							System.out.println("El contrincant no podia fer mes moviments, empat");
							partida.acabarPartida();
							keyboard.close();
							System.exit(0);
						}
					}
				case 2:
					partida.acabarPartida();
					keyboard.close();
					System.out.println("Fi del joc");
					System.exit(0);
					break;
			}
		}
		System.out.println("Fi del joc");
		keyboard.close();
	}
}
