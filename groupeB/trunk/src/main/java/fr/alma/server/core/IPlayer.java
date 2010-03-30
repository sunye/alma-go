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

import java.util.List;

import fr.alma.client.ihm.GoObject;
import fr.alma.server.ia.IEvaluation;

public interface IPlayer extends GoObject {
	
	Boolean getColor();
	
	
	/**
	 * Define all the actions when the player put a stone on the goban
	 */
	void play();
	
	/**
	 * OBSERVER PATTERN
	 * Define listeners on player actions
	 */
	boolean addPlayListener(PlayListener actionListener);
	
	/**
	 * Remove a listener on player
	 */
	boolean removePlayListener(PlayListener playListener);
	
	/**
	 * Remove all listeners on player
	 */
	public void removePlayListeners();
	
	List<PlayListener> getPlayerListeners();
	
	/**
	 * @param enable false when the game is over
	 */
	void setEnabled(boolean enable);
	
	/**
	 * @param strategyGame
	 */
	public void setStrategy(IStrategy strategyGame);

	public void interrupt();
	public void setEvaluation(IEvaluation evaluation);
	public String getName();
}
