package fr.alma.modele;
/*$Author$ 
 * $Date$ 
 * $Revision$ 
 * 
 *Copyright (C) 2010  Fortun Manoël & Caillaud Anthony
 *
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * */
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
