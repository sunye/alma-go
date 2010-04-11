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

import java.util.List;
import java.util.ArrayList;
import fr.alma.server.core.ILocation;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.ia.FreedomDegrees;

/**
 * Check the rule of the "Suicide"
 */
public class RuleSuicide {

	public RuleSuicide() {
		super();
	}

	
	public boolean provokeSuicide(IStateGame stateGame, ILocation location, IPlayer currentPlayer) {
		List<ILocation> emplacements = new ArrayList<ILocation>();
		int degrees = FreedomDegrees.countFreeDegrees(stateGame, currentPlayer.getColor(), location, emplacements);

		return (degrees == 0);
	}
}
