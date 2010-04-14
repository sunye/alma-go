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
 * Class that represent a coordinate on the board
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * 
 */
public class Coordinate {
	/**
	 * the x coordinate
	 */
	private Integer x;
	
	/**
	 * the y coordinate 
	 */
	private Integer y;
	
	
	/**
	 * @return the x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(Integer x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(Integer y) {
		this.y = y;
	}

	/**
	 * Construct the coordinate with the value(that can be null)
	 * @param x
	 * @param y
	 */
	public Coordinate(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * is the coordinate are not empty
	 * @param max the maximum
	 * @return y>=0 && x>=0 && x<max && y<max
	 */
	public boolean isValid(int max){
		return isCoordinateNotEmpty() && x>=0&&y>=0&&x<max&&y<max;
	}
	
	/**
	 * check if the x and y are not null
	 * @return x!=null && y!=null
	 */
	public boolean isCoordinateNotEmpty(){
		return this.getX()!=null && this.getY()!=null;
	}
	
}
