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

import fr.alma.server.ia.IEvaluation;

/**
 * Represent a strategy for the computer calculation
 * (Min-Max, Alpha-Beta, etc.)
 */
public interface IStrategy {
	/**
	 * @param evaluation
	 * @param trace
	 * @return the best location on the goban
	 */
	public ILocation getBestLocation(IEvaluation evaluation, boolean trace);
	
	/**
	 * @return the current state of the game 
	 */
	public IStateGame getStateGame();
	
	/**
	 * Called to interrupt getBestLocation
	 * @see fr.alma.server.core.IStrategy#getBestLocation()
	 */
	public void interrupt();
}
