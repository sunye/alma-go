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
 * Class used to represent a stone.
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * 
 */
public class Stone {

	/**
	 * The color of the stone
	 */
	private StoneColor color;
	
	
	/**
	 * The coordinate of the stone
	 */
	private Coordinate position;	
	/**
	 * The group owner of the stone
	 */
	private Group group;
	
	/**
	 * Boolean used by the AI, Usefull for some method
	 */
	private boolean marque;
	
	/**
	 *  Constructor
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param couleur
	 */
	public Stone(Integer x, Integer y, StoneColor couleur) {
		this.position= new Coordinate(x, y);
		this.color = couleur;
		
	}

	/**
	 * @return the couleur
	 */
	public StoneColor getColor() {
		return color;
	}

	/**
	 * @param couleur the couleur to set
	 */
	public void setColor(StoneColor couleur) {
		this.color = couleur;
	}

	/**
	 * @return the position
	 */
	public Coordinate getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Coordinate position) {
		this.position = position;
	}

	/**
	 * @return the groupe
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param groupe the groupe to set
	 */
	public void setGroup(Group groupe) {
		this.group = groupe;
	}

	/**
	 * @return the marque
	 */
	public boolean isMarque() {
		return marque;
	}

	/**
	 * @param marque the marque to set
	 */
	public void setMarque(boolean marque) {
		this.marque = marque;
	}


	
	
}
