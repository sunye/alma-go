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
 * Classe permettant de gere un groupe de pieces adjacentes (dans un jeu de go). 
 * 
 */

package game;

import java.util.LinkedList;
import java.util.List;

public class GroupPawns {

	private List<Coordinates> pawns;
	private Integer freedoms;
	private Color color; 
	
	/* Constructor */
	public GroupPawns() {
		super();
		pawns = new LinkedList<Coordinates>();
		freedoms = 0;
		color = Color.NONE;
	}
	
	public GroupPawns(Coordinates coord, Integer freedom, Color coul) {
		super();
		pawns = new LinkedList<Coordinates>();
		pawns.add(coord);
		this.freedoms = freedom;
		color = coul;
	}
	
	/* Getters - Setters */
	public List<Coordinates> getPawns() {
		return pawns;
	}
	public void setPawns(List<Coordinates> pawn) {
		this.pawns = pawn;
	}
	public Integer getFreedoms() {
		return freedoms;
	}
	public void setFreedoms(Integer free) {
		this.freedoms = free;
	}
	public void setColor(Color col) {
		this.color = col;
	}
	public Color getColor() {
		return color;
	}
	
	/**
	 * Divide one group into under group. Work if the original group is fragmented 
	 * @return 
	 */
	public LinkedList<GroupPawns> divideGroup() {
				
		LinkedList<GroupPawns> groupAdj = new LinkedList<GroupPawns>();
		
		while(pawns.size() != 0)
		{
			/* take first pawn */
			/* take off it of gc */
			GroupPawns gcTmp = new GroupPawns(pawns.get(0),0,color);
			pawns.remove(0);
			
			int i = 0;
			/* travel the list of adjacent's pawn */
			while(i < gcTmp.getPawns().size()) {
				
				Coordinates coordAdj = gcTmp.getPawns().get(i);
				int j = 0;
				
				/* travel the group */
				while(j < pawns.size() ){
					Coordinates coordTest = pawns.get(j);
					/* if they are adjacent we take it */
					if(coordAdj.isAdjacent(coordTest)){
						gcTmp.getPawns().add(coordTest);
						pawns.remove(coordTest);
					}else{
						// else we pass
						j++;
					}
				}
				i++;
			}
			
			groupAdj.add(gcTmp);
		}
		
		return groupAdj;
	}
	
	
	/**
	 * @param coord : coordinate of the testing position
	 * @return true if this position is occupied by the group
	 */	
	public boolean testPosition(Coordinates coord){
		for(Coordinates c : pawns){						
			if( c.equals(coord) ){
				return true;
			}
		}
		return false;
	}
		
	
}
