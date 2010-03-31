/*
 * IA Project ATARI-GO
 * UNIVERSITY OF NANTES
 * MASTER ALMA 1
 * 2009 - 2010
 * Version 1.0
 * @author Romain Gournay & Bruno Belin
 * 
 * Copyright 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * Use is subject to license terms.
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
