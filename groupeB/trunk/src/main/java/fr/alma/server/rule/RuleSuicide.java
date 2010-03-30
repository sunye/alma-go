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
import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.ia.FreedomDegrees;


public class RuleSuicide {

	public RuleSuicide() {
		super();
	}

	
	public boolean provokeSuicide(IStateGame stateGame, IEmplacement emplacement, IPlayer currentPlayer) {
		List<IEmplacement> emplacements = new ArrayList<IEmplacement>();
		int degrees = FreedomDegrees.countFreeDegrees(stateGame, currentPlayer.getColor(), emplacement, emplacements);
		//System.out.println("Freedom degrees for group ["+col+"]["+row+"] d="+degrees);
		//if (degrees == 0) System.out.println("SUICIDE");
		return (degrees == 0);
	}
}
