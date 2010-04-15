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

import javax.swing.JLabel;

import org.junit.Before;
import org.junit.Test;

import fr.alma.client.action.Context;
import fr.alma.client.action.ParamGame;
import fr.alma.server.core.Computer;
import fr.alma.server.core.Factory;
import fr.alma.server.core.ICoordinator;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;
import fr.alma.server.core.PlayEvent;
import fr.alma.server.core.PlayListener;
import fr.alma.server.core.Player;
import fr.alma.server.core.StateGame;
import fr.alma.server.ia.IEvaluation;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;


public class TestComputer {

	IStateGame stateGame = null;
	IEvaluation evaluation = null;
	IPlayer computer;
	IPlayer player;
	RuleManager ruleManager;
	ICoordinator coordinator;
	IStrategy strategy;
	Context context;
	boolean fin;
	int cpt;
	
	
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
		context.setStatusBar(new JLabel());
				
		stateGame = new StateGame(context);
		computer = new Computer("computer", context);
		player = new Player("adversaire", Configuration.WHITE, null, stateGame);
		stateGame.play(0, 0, player.getColor());
		evaluation = Factory.getEvaluation(context);
		ruleManager = Factory.getRuleManager(context);
		strategy = Factory.getStrategy(context);
		
		computer.setStrategy(strategy);
		computer.setEvaluation(evaluation);
		computer.addPlayListener(new TestPlayListener());
		
		context.setPlayer(player);
		context.setComputer(computer);
		context.setStateGame(stateGame);
		context.setRuleManager(ruleManager);
	}

	/**
	 * Test if the computer 
	 */
	@Test
	public void testInterrupt() {
		
		computer.play();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			fail("Current Thread was interrupted !");
		}
		computer.interrupt();

		while (! fin && cpt++ < 10)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				fail("Current Thread was interrupted !");
			}
		}
		assertTrue("The event play was not propagated !", fin);
	}
	
	
	class TestPlayListener implements PlayListener {

		@Override
		public boolean actionPerformed(PlayEvent e) {
			System.out.println("Propagated event play");
			assertTrue(e.getPlayer() == computer);
			assertTrue(e.getWhen() == PlayEvent.AFTER);
			assertTrue("The computer must give a position even if interrupted", e.getEmplacement() != null);
			fin = true;
			return true;
		}
	}
	
}
