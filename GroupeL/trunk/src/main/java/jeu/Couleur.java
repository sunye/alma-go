package jeu;

public enum Couleur {
	none ,blanc ,noir ;
	
	public Couleur invCouleur() {
		if(this == blanc){
			return noir;
		}else if (this == noir){
			return blanc;
		}
		return none;
	}
}
