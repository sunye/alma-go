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
 * special class used by the AI to synchronised thread
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * 
 */
public class CoordinateAI extends Coordinate {

	/**
	 * An indicator to set is the search is over
	 */
	private boolean termine; 
	
	/**
	 * construct a classical coordinate
	 * @param x
	 * @param y
	 */
	public CoordinateAI(Integer x, Integer y) {
		super(x, y);
		setTermine(false);
	}

	/**
	 * set the search end
	 * @param termine
	 */
	public void setTermine(boolean termine) {
		this.termine = termine;
	}

	/**
	 * if the seach if over
	 * @return
	 */
	public boolean isTermine() {
		return termine;
	}
	
	/**
	 * set the attribute from a super class
	 * @param coord
	 */
	public void setCoordinate(Coordinate coord){
		this.setX(coord.getX());
		this.setY(coord.getY());
	}
	
	/**
	 * the equivalent with a super class
	 * @return equivalent
	 */
	public Coordinate convert(){
		return new Coordinate(getX(), getY());
	}
	
}
