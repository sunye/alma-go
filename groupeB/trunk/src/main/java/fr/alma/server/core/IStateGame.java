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


public interface IStateGame extends Cloneable, GoObject {
	
	boolean existChild();
	
	boolean isPlayable(int col, int row);
	boolean play(int col, int row, boolean computer) throws Exception;
	void remove(int col, int row);
	int getMaxCol();
	int getMaxRow();
	boolean isOver();
	boolean isFree(int col, int row);
	boolean isComputer(int col, int row);
	boolean isPlayer(int col, int row);

	boolean onGoban(int col, int row);

	Object getIntersection(int col, int row);

	void load(Boolean[][] intersection);
	public int countLocationOccupied();
	public Object clone();
	public void clear();
	public int countLimitComputer();
	public int countLimitPlayer();
	
	public int getLevel();
	public void setLevel(int level);

}
