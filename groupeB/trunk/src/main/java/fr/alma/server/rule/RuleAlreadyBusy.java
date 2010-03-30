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


public class RuleAlreadyBusy implements IRule {
	
	
	public RuleAlreadyBusy() {
		super();
	}
	
	
	@Override
	public boolean accept(IStateGame stateGame, IEmplacement emplacement, IPlayer currentPlayer) {
		return stateGame.isFree(emplacement.getCol(), emplacement.getRow());
	}

}
