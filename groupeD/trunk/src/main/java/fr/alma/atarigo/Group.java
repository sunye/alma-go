/*   This program is free software: you can redistribute it and/or modify
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

package fr.alma.atarigo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
/**
 * Group.java represents a group of stones from the same color.
 * @author VINCENT FERREIRA, ADRIEN GUILLE 
 * @version 1.0
 * 
 * revision $Revision$
 * 
 */
public class Group implements Cloneable{

	public ArrayList<Position> linkedStones;
	public Stone stone;
	
/**
 * Logic constructor
 * @param linkedList list of stones
 * @param color of the group
 */
	public Group(ArrayList<Position> linkedList, Stone color) {
		// TODO Auto-generated constructor stub
		this.linkedStones = linkedList;
		this.stone = color;
	}
	
	/**
	 * retourne une copy of the group
	 * @return cloned group
	 */
	public Group clone(){
		Group group = new Group(new ArrayList<Position>(),stone);
		Position currentPosition;
		ListIterator itr = linkedStones.listIterator();
 		while(itr.hasNext()){
 			currentPosition = (Position)itr.next();
 			group.linkedStones.add(new Position(currentPosition));
 		}		
		return group;
	}
	
	/**
	 * return if the position is in the group
	 * @param position
	 * @return true if the position is in the group
	 */
	public boolean hasPos(Position position){
		Position currentPosition;
		boolean hasPos = false;
		ListIterator itr = linkedStones.listIterator();
 		while(itr.hasNext()){
 			currentPosition = (Position)itr.next();
 			if(position.isEqual(currentPosition)){
 				hasPos = true;
 			}
 		}
		return hasPos;
	}
	
	/**
	 * print the group. Usefull for debugging.
	 *
	 */
	public void print(){
		Position currentPosition;
		ListIterator itr = linkedStones.listIterator();
		while(itr.hasNext()){
 			currentPosition = (Position)itr.next();
 			System.out.print("["+currentPosition.getLine()+","+currentPosition.getColumn()+"] ");
 		}
		System.out.println("");
	}
	
	/**
	 * add a position in the group
	 * @param position position of the stone
	 */
	public void add(Position position){
		linkedStones.add(position);
	}
	
	
	
	
}
