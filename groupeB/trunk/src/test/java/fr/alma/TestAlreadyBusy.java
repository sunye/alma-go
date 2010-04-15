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
package fr.alma;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.alma.client.action.Context;
import fr.alma.client.action.ParamGame;
import fr.alma.server.core.Location;
import fr.alma.server.core.Factory;
import fr.alma.server.core.ILocation;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.Player;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.IRule;
import fr.alma.server.rule.RuleAlreadyBusy;


/**
 * Verifies the coherence of locations
 */
public class TestAlreadyBusy {

	IRule rule;
	IStateGame stateGame;
	ILocation location;
	IPlayer player;
	Context context;
	
	
	@Before
	public void setUp() throws Exception {

		ParamGame param = new ParamGame();
		
		param.setSizeGoban(9);
		param.setAssistant(false);
		param.setColorComputer(Configuration.BLACK);
		param.setOpponent(true);
		param.setPossibilityInterruption(true);
		param.setTimeLimite(15);
		
		context = new Context();
		context.setParamGame(param);
		rule = new RuleAlreadyBusy();
		stateGame = Factory.getStateGame(context);
		location = new Location(1, 1);
		player = new Player("name player", Configuration.getColorFirstPlayer(), null, stateGame);
	}

	@Test
	public void testRuleAlreadyBusy() {
		assertTrue("location refused when it is available", rule.accept(stateGame, new Location(1, 1), player));
		
		try {
			stateGame.play(1, 1, player.getColor());
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
		assertFalse("agreed location while it is busy", rule.accept(stateGame, new Location(1, 1), player));
	}

}
