package fr.alma.modele;

/**
 *Class used to represent a future state of the game
 *with the goban and the position(x,y) and the color of the last
 *last stone played
 */
public class Coup {

//XXX ne pas stocker de matrice mais stocker seulement le coup^et la couleur
	// so voir pour comment on fait l'algo d'évaluation en question
private CouleurPion coulp;
private Coordonnee position;

public Coup(Integer x, Integer y, CouleurPion coulp) {
	this.position=new Coordonnee(x, y);
	this.coulp = coulp;
}

public CouleurPion getCoulp() {
	return coulp;
}

public void setCoulp(CouleurPion coulp) {
	this.coulp = coulp;
}


public Coordonnee getPosition() {
	return position;
}

public void setPosition(Coordonnee position) {
	this.position = position;
}




	
	
	
}
