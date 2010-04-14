package fr.alma.modele;
/*$Author$ 
 * $Date$ 
 * $Revision$ 
 * 
 *Copyright (C) 2010  Fortun Mano�l & Caillaud Anthony
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
 * Used to represent the color of the stone
 * @author Mano�l Fortun et Anthony "Bambin�me" Caillaud
 * 
 */

public enum StoneColor {

	BLACK,
	WHITE,
	EMPTY;
	
	/**
	 * get the opposite color
	 * @param coul
	 * @return Empty-> Empty, Black-> White, White->Black
	 */
	public static StoneColor oppose(StoneColor coul){
		return coul==EMPTY?EMPTY:coul==BLACK?WHITE:BLACK;
	}
	
}
 

