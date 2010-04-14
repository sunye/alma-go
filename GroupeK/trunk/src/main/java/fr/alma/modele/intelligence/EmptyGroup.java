package fr.alma.modele.intelligence;

import java.util.HashSet;

import fr.alma.modele.Group;
import fr.alma.modele.Stone;

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
 * Class used to represent group of empty cell
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 */
public class EmptyGroup {

	/**
	 * The neighbors of the empty group
	 */
	private HashSet<Group> neigborsGroup;
	
	/**
	 * empty stone composed by this group
	 * 
	 */
	private HashSet<Stone> emptyStone;
	
	public EmptyGroup() {
		neigborsGroup=new HashSet<Group>();
		emptyStone=new HashSet<Stone>();
	}

	/**
	 * @return the groupeVoisin
	 */
	public HashSet<Group> getNeigborsGroup() {
		return neigborsGroup;
	}




	/**
	 * @param groupeVoisin the groupeVoisin to set
	 */
	public void setNeigborsGroup(HashSet<Group> groupeVoisin) {
		this.neigborsGroup = groupeVoisin;
	}

	/**
	 * @return the pionVides
	 */
	public HashSet<Stone> getEmptyStones() {
		return emptyStone;
	}

	/**
	 * @param pionVides the pionVides to set
	 */
	public void setEmptyStones(HashSet<Stone> pionVides) {
		this.emptyStone = pionVides;
	}

	/**
	 * Add a group to the neighbors
	 * @param g
	 */
	public void addGroup(Group g){
		this.neigborsGroup.add(g);
	}
	
	/**
	 * add an ampty stone to the 'group'
	 * @param p
	 * @return
	 */
	public boolean addPion(Stone p){
		return emptyStone.add(p);
	}
	
	
	
}
