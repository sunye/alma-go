package fr.alma.atarigo.utils;

import fr.alma.atarigo.utils.PionVal;

public class Stone {

	private PionVal couleur;
	private short column;
	private short line;
	
	public Stone(PionVal couleur, short ligne, short colonne) {
		super();
		this.couleur = couleur;
		this.column = colonne;
		this.line = ligne;
	}

	
	public Stone(PionVal couleur2, int realLigne, int realCol) {
		this.couleur = couleur2;
		this.column = (short) realCol;
		this.line = (short) realLigne;
	}


	public short getColumn() {
		return column;
	}


	public short getLine() {
		return line;
	}


	@Override
	public int hashCode() {
		return (Goban.getTaille()*column+line)*((couleur == null) ?0:couleur.valeur());
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stone other = (Stone) obj;
		if (column != other.column)
			return false;
		if (couleur == null) {
			if (other.couleur != null)
				return false;
		} else if (!couleur.equals(other.couleur))
			return false;
		if (line != other.line)
			return false;
		return true;
	}


	@Override
	public String toString() {
            return "Pion(x:"+column+",y:"+line+",c:"+couleur+")";
	}


	public PionVal getCouleur() {
            return couleur;
	}
	
	
	
	
}
