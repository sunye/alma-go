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

import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.ia.FreedomDegrees;

/**
 * 
 * @author Romain Gournay & Bruno Belin
 * describe the rule to catch opponent stones
 */
public class RuleCapture {

	/**
	 * Constructor
	 */
	public RuleCapture() {
		super();
	}

	/**
	 * @param stateGame
	 * @param emplacement
	 * @param currentPlayer
	 * @return true if there is a capture by the player who has just played
	 */
	public boolean provokeCapture(IStateGame stateGame, IEmplacement emplacement, IPlayer currentPlayer) {
		int isZeroFreeDegrees = FreedomDegrees.hasCapturedWithThisEmplacement(stateGame, emplacement, currentPlayer);
		return (isZeroFreeDegrees == 0);
	}

}
