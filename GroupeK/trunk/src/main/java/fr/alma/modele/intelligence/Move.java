package fr.alma.modele.intelligence;

import fr.alma.modele.Coordinate;
import fr.alma.modele.StoneColor;

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
 * Class that represent a move and is mark
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 *
 */
public class Move {
/**
 * Color of the move
 */
private StoneColor color;
/**
 * The coordinate of the move
 */
private Coordinate position;

/**
 * the mark of the move
 */
private Integer mark;

/**
 * Construct a move
 * @param x x coordinate
 * @param y y coordinate
 * @param coulp the color
 */
public Move(Integer x, Integer y, StoneColor coulp) {
	this.position=new Coordinate(x, y);
	this.color = coulp;
	this.mark=0;
}

/**
 * @return the coulp
 */
public StoneColor getCoulp() {
	return color;
}

/**
 * @param coulp the coulp to set
 */
public void setCoulp(StoneColor coulp) {
	this.color = coulp;
}

/**
 * @return the position
 */
public Coordinate getPosition() {
	return position;
}

/**
 * @param position the position to set
 */
public void setPosition(Coordinate position) {
	this.position = position;
}

/**
 * @return the note
 */
public Integer getMark() {
	return mark;
}

/**
 * @param note the note to set
 */
public void setMark(Integer note) {
	this.mark = note;
}





	
	
	
}
