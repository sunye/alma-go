package utils;

import utils.PionVal;

public class Pion {

	private PionVal couleur;
	private short colonne;
	private short ligne;
	
	public Pion(PionVal couleur, short ligne, short colonne) {
		super();
		this.couleur = couleur;
		this.colonne = colonne;
		this.ligne = ligne;
	}

	
	public Pion(PionVal couleur2, int realLigne, int realCol) {
		this.couleur = couleur2;
		this.colonne = (short) realCol;
		this.ligne = (short) realLigne;
	}


	public short getColonne() {
		return colonne;
	}


	public short getLigne() {
		return ligne;
	}


	@Override
	public int hashCode() {
		return (Goban.getTaille()*colonne+ligne)*((couleur == null) ?0:couleur.valeur());
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pion other = (Pion) obj;
		if (colonne != other.colonne)
			return false;
		if (couleur == null) {
			if (other.couleur != null)
				return false;
		} else if (!couleur.equals(other.couleur))
			return false;
		if (ligne != other.ligne)
			return false;
		return true;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Pion(x:"+colonne+",y:"+ligne+",c:"+couleur+")";
	}


	public PionVal getCouleur() {
		// TODO Auto-generated method stub
		return couleur;
	}
	
	
	
	
}
