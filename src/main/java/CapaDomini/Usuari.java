package CapaDomini;

public class Usuari {
	
	private String strNom;
	
	public Usuari(String nom) throws IllegalArgumentException {
		if (strNom == null) {
			throw new IllegalArgumentException("El nom no pot ser null.");
		}
		if (strNom.length() < 3) {
			throw new IllegalArgumentException("El nom no pot tenir menys de tres caracters.");
		}
		this.strNom = nom;
	}
	
	public String getNom() {
		return this.strNom;
	}
	
	public int hashCode() {
		return this.strNom.hashCode();
	}
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (!(o instanceof Usuari)) {
			return false;
		}
		
		return this.strNom.equals(((Usuari)o).strNom);
	}
}
