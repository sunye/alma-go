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
package fr.alma.server.rule;

import fr.alma.server.core.ILocation;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;

/**
 * Rule interface
 */
public interface IRule {
	/**
	 * @param stateGame
	 * @param emplacement
	 * @param currentPlayer
	 * @return true if the rule is respected
	 */
	public boolean accept(IStateGame stateGame, ILocation emplacement, IPlayer currentPlayer);
}
