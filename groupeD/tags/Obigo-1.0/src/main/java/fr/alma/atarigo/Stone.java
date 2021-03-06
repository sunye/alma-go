package fr.alma.atarigo;


/**
 * Enumeration of the different types of stones.
 * <li>
 * WHITE for a white stone
 * <li>
 * BLACK for a black stone
 * <li>
 * EMPTY for an empty stone
 * 
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
public enum Stone {WHITE, BLACK, EMPTY;
	
	/**
	 * Return the opponent color
	 */
	public Stone opponent(){
		if(this==WHITE){
			return BLACK;
		}else
			return WHITE;
	}
	
}