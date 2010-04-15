/*$Author$ 
 * $Date$ 
 * $Revision$ 
 * 
 *Copyright (C) 2010  Dejean Charles and Pottier Vincent
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
 * @author Dejean Charles - Pottier Vincent
 * 
 * Classe permettant de gerer des coordonnees entieres bidimensionnelles. 
 * 
 */

package game;

public class Coordinates {
	private Integer x;
	private Integer y;
	
	/* Getters - Setters */
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	
	/* Constructors */
	public Coordinates(Integer x, Integer y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Coordinates() {
		super();
		this.x = 0;
		this.y = 0;
	}
	
	/* function */
	/**
	 * @param coord : Coordinates to test;
	 * @return true if the Coordinates are the same
	 */
	public boolean equals(Coordinates coord) {
		return ((coord.getX()==this.x) && (coord.getY()==this.y));
	}
	/**
	 * @param coord : Coordinates to test;
	 * @return true if the Coordinates are adjacent
	 */
	public boolean isAdjacent(Coordinates coord) {
		return ( ((coord.getX()+1 == this.x)||(coord.getX()-1 == this.x)) && (coord.getY() == this.y) ) || ( ((coord.getY()+1 == this.y)||(coord.getY()-1 == this.y)) && (coord.getX() == this.x) ) ;
	}
	
	@Override
	public String toString() {
		return  "(" + x + "," + y + ")";
	}	
	
	
	
	
}
