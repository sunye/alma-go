package fr.alma.modele;
/*$Author$ 
 * $Date$ 
 * $Revision$ */
/**
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * Class that represent a move and is mark
 */
public class Coup {
/**
 * Color of the move
 */
private CouleurPion coulp;
/**
 * The coordinate of the move
 */
private Coordonnee position;

/**
 * the mark of the move
 */
private Integer note;

/**
 * Construct a move
 * @param x x coordinate
 * @param y y coordinate
 * @param coulp the color
 */
public Coup(Integer x, Integer y, CouleurPion coulp) {
	this.position=new Coordonnee(x, y);
	this.coulp = coulp;
	this.note=0;
}

/**
 * @return the coulp
 */
public CouleurPion getCoulp() {
	return coulp;
}

/**
 * @param coulp the coulp to set
 */
public void setCoulp(CouleurPion coulp) {
	this.coulp = coulp;
}

/**
 * @return the position
 */
public Coordonnee getPosition() {
	return position;
}

/**
 * @param position the position to set
 */
public void setPosition(Coordonnee position) {
	this.position = position;
}

/**
 * @return the note
 */
public Integer getNote() {
	return note;
}

/**
 * @param note the note to set
 */
public void setNote(Integer note) {
	this.note = note;
}





	
	
	
}
