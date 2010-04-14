package fr.alma.modele;

import java.util.HashSet;

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
 */
public class Vide {

	/**
	 * The neighbors of the empty group
	 */
	private HashSet<Groupe> groupeVoisin;
	
	/**
	 * empty stone composed by this group
	 * 
	 */
	private HashSet<Pion> pionVides;
	
	public Vide() {
		groupeVoisin=new HashSet<Groupe>();
		pionVides=new HashSet<Pion>();
	}

	/**
	 * @return the groupeVoisin
	 */
	public HashSet<Groupe> getGroupeVoisin() {
		return groupeVoisin;
	}




	/**
	 * @param groupeVoisin the groupeVoisin to set
	 */
	public void setGroupeVoisin(HashSet<Groupe> groupeVoisin) {
		this.groupeVoisin = groupeVoisin;
	}

	/**
	 * @return the pionVides
	 */
	public HashSet<Pion> getPionVides() {
		return pionVides;
	}

	/**
	 * @param pionVides the pionVides to set
	 */
	public void setPionVides(HashSet<Pion> pionVides) {
		this.pionVides = pionVides;
	}

	/**
	 * Add a group to the neighbors
	 * @param g
	 */
	public void addGroup(Groupe g){
		this.groupeVoisin.add(g);
	}
	
	/**
	 * add an ampty stone to the 'group'
	 * @param p
	 * @return
	 */
	public boolean addPion(Pion p){
		return pionVides.add(p);
	}
	
	
	
}
