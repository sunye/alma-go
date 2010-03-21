package fr.alma.modele;

/**
 *Class used to represent a future state of the game
 *with the goban and the position(x,y) and the color of the last
 *last stone played
 */
public class Coup {


private CouleurPion coulp;
private Pion[][] goban;
private Coordonnee position;

public Coup(Integer x, Integer y, CouleurPion coulp, Pion[][] goban) {
	this.position=new Coordonnee(x, y);
	this.coulp = coulp;
	this.goban = goban;
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

public Pion[][] getGoban() {
	return goban;
}

public void setGoban(Pion[][] goban) {
	this.goban = goban;
}



	
	
	
}
