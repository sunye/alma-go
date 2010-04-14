package fr.alma.modele;

import java.util.Collection;

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
 * Class that represent stone group's and their liberty
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * 
 */
public class Group {

	/**
	 * Color of the group
	 */
	private StoneColor groupColor;

	/**
	 * The stone of the group
	 */
	private HashSet<Stone> stones;

	/**
	 * Coordinate of the group's liberty
	 */
	private HashSet<Coordinate>  libertys;

	/**
	 * constructor
	 * @param coul the color of the group
	 */
	public Group(StoneColor coul) {
		this.groupColor = coul;
		stones = new HashSet<Stone>();
		libertys = new HashSet<Coordinate>();
	}
	
	/**
	 * 
	 * @return the number of liberty
	 */
	public int getNbLiberty() {
		return libertys.size();
	}

	/**
	 * 
	 * @return the number of stone composed by the group
	 */
	public int getStoneNumber() {
		return stones.size();
	}

	/**
	 * @return the corlor
	 */
	public StoneColor getGroupColor() {
		return groupColor;
	}

	/**
	 * @param couleur the color to set
	 */
	public void setGroupColor(StoneColor couleur) {
		this.groupColor = couleur;
	}

	/**
	 * @return the pions
	 */
	public HashSet<Stone> getStones() {
		return stones;
	}

	/**
	 * @param pions the pions to set
	 */
	public void setStones(HashSet<Stone> pions) {
		this.stones = pions;
	}

	/**
	 * @return the libertes
	 */
	public HashSet<Coordinate> getLibertys() {
		return libertys;
	}

	/**
	 * @param libertes the libertes to set
	 */
	public void setLibertys(HashSet<Coordinate> libertes) {
		this.libertys = libertes;
	}

	/**
	 * Add a liberty to the group
	 * @param cood the coordinate of the liberty
	 * @return if the coordinate is add (typically if the liberty where not already in the set)
	 */
	public boolean addLiberty(Coordinate cood){
		return this.libertys.add(cood);
	}
	
	/**
	 * remove 
	 * @param cood
	 * @return if the coordinate where removed (typically if the liberty where in the set)
	 */
	public boolean removeLiberty(Coordinate cood){
		return libertys.remove(cood);
	}
	
	/**
	 * check if a coordinate is a liberty of the group
	 * @param cood
	 * @return
	 */
	public boolean isALiberty(Coordinate cood){
		return libertys.contains(cood);
	}
	
	/**
	 * remove 
	 * @param pi
	 * @return if the stone where removed (typically if the stone where in the set)
	 */
	public boolean removeStone(Stone pi){
		return this.stones.remove(pi);
	}
	
	/**
	 * add 
	 * @param pi
	 * @return if the stone where removed (typically if the stone where not already in the set)
	 */
	public boolean addPion(Stone pi){
		return stones.add(pi);
	}
	
	/**
	 * check if the group containt a stone 
	 * @param pi
	 * @return if the stone is in the group
	 */
	public boolean containStone(Stone pi){
		return stones.contains(pi);
	}
	
	/**
	 * Add a collection of liberty to the group
	 * @param calculPionLiberte
	 */
	public void addLibertys(Collection<Coordinate> calculPionLiberte) {
		this.libertys.addAll(calculPionLiberte);
	}

	/**
	 * remove a collection of liberty to the group
	 * @param toRemove
	 */
	public void removeLibertys(Collection<Coordinate> toRemove) {
		this.libertys.removeAll(toRemove);
	}
	
	/**
	 * use to make fusion between group
	 * it destroy the redundant group
	 * @param group the other group to fusion with
	 * @return the fusioned group 
	 */
	public Group fusionGroup(Group group){
		Group result=this;
		
		if(group.getStoneNumber()>result.getStoneNumber()){
			result=group;
			group=this;
		}
		
		result.addLibertys(group.getLibertys());
		
		for( Stone pi: group.getStones()){
			pi.setGroup(result);
			result.addPion(pi);
		}

		group.clear();
		try {
			group.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	
		return result;
	}
	
	/**
	 * clear the group liberty and stone
	 */
	public void clear(){
		this.libertys.clear();
		this.stones.clear();
	}
	
	

}
