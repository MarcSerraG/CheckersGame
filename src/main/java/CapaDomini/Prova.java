package CapaDomini;

import java.util.List;
import java.util.Scanner;

public class Prova {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Taulell taulell = new Taulell(10,10);
		Scanner keyboard = new Scanner(System.in);
		int fOrigen, cOrigen, fDesti, cDesti;
		
		while (taulell.getNumNegres()!= 0 || taulell.getNumBlanques()!= 0) {
			System.out.print(taulell);
			System.out.println("A quina fila es la fitxa?");
			fOrigen = keyboard.nextInt();
			System.out.println("A quina columna es la fitxa?");
			cOrigen = keyboard.nextInt();
			System.out.println("A quina fila vols moure?");
			fDesti = keyboard.nextInt();
			System.out.println("A quina columna vols moure?");
			cDesti = keyboard.nextInt();
			taulell.moviment(taulell.seleccionarFitxa(fOrigen, cOrigen), taulell.seleccionarFitxa(fDesti, cDesti));
			
		}
		System.out.println("Fi del joc");
	}
		
}
