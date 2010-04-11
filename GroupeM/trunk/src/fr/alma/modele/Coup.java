package fr.alma.modele;

/**
 *Class used to represent a future state of the game
 *with the goban and the position(x,y) and the color of the last
 *last stone played
 */
public class Coup {

private CouleurPion coulp;
private Coordonnee position;
private Integer note;

public Coup(Integer x, Integer y, CouleurPion coulp) {
	this.position=new Coordonnee(x, y);
	this.coulp = coulp;
	this.note=0;
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

public Integer getNote() {
	return note;
}

public void setNote(Integer note) {
	this.note = note;
}




	
	
	
}
