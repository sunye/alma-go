package game;

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


public enum Color {
	NONE ,WHITE ,BLACK ;
	
	public Color invColor() {
		if(this == WHITE){
			return BLACK;
		}else if (this == BLACK){
			return WHITE;
		}
		return NONE;
	}
	
	public String toString(){
		if(this == WHITE){
			return "blanc";
		}else if (this == BLACK){
			return "noir";
		}
		return "none";
	}
}
