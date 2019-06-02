package CapaDomini;

public class Usuari {
	
	//Variables
	private String strNom;
	
	//Constructor
	public Usuari(String nom) throws IllegalArgumentException {
		
		if (nom == null) throw new IllegalArgumentException("El nom no pot ser null.");
		if (nom.length() < 3) throw new IllegalArgumentException("El nom no pot tenir menys de tres caracters.");
		this.strNom = nom;
	}
	//Gets
	public String getNom() {return this.strNom;}
	public int hashCode() {return this.strNom.hashCode();}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (!(o instanceof Usuari)) {
			return false;
		}
		
		return this.strNom.equals(((Usuari)o).strNom);
	}
	public String toString() {
		return this.strNom;
	}
}
