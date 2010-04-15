/*
 *   
 *   IA Project ATARI-GO
 *   UNIVERSITY OF NANTES
 *   MASTER ALMA 1
 *   2009 - 2010
 * 	 Version 1.0
 *   
 *   $Date$
 *   $Author$
 * 	 $Revision$
 * 
 *   Copyright (C) 2010 Romain Gournay & Bruno Belin, All rights reserved.
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
package fr.alma.server.core;

import fr.alma.client.ihm.GoObject;

/**
 * Represent a state game
 */
public interface IStateGame extends Cloneable, GoObject {
	
	/**
	 * 
	 * @param col
	 * @param row
	 * @param computer
	 * @return true
	 * @throws Exception
	 */
	boolean play(int col, int row, boolean computer) throws Exception;
	
	/**
	 * Remove the stone on a location 
	 * Use for the strategy function 
	 * @param col
	 * @param row
	 */
	void remove(int col, int row);
	
	/**
	 * @return the width of the Goban
	 */
	int getMaxCol();
	
	/**
	 * @return the height of Goban
	 */
	int getMaxRow();
	
	/**
	 * @param col
	 * @param row
	 * @return true if the location is free
	 */
	boolean isFree(int col, int row);
	
	/**
	 * @param col
	 * @param row
	 * @return true if the computer takes this location
	 */
	boolean isComputer(int col, int row);
	
	/**
	 * @param col
	 * @param row
	 * @return true if the player takes this location
	 */
	boolean isPlayer(int col, int row);

	/**
	 * @param col
	 * @param row
	 * @return true if the Location on the Goban
	 */
	boolean onGoban(int col, int row);

	/**
	 * @param col
	 * @param row
	 * @return the color of the stone on the location
	 */
	Object getIntersection(int col, int row);

	/**
	 * Load a state game
	 * @param intersection
	 */
	void load(Boolean[][] intersection);
	
	
	/**
	 * @return a state game cloned
	 */
	public Object clone();
	
	
	public void clear();
	
	/**
	 * @return the depth of the tree for the strategy algorithm used
	 */
	public int getLevel();
	public void setLevel(int level);

}
