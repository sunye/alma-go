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
import fr.alma.server.core.Computer;
import fr.alma.server.core.Factory;
import fr.alma.server.core.ICoordinator;
import fr.alma.server.core.ILocation;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;
import fr.alma.server.core.Player;
import fr.alma.server.core.StateGame;
import fr.alma.server.ia.IEvaluation;
import fr.alma.server.ia.FreedomDegrees;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;
import fr.alma.server.rule.StatusCheck;

public class TestAlphaBeta {

	IStateGame stateGame = null;
	IEvaluation evaluation = null;
	IPlayer computer;
	IPlayer player;
	RuleManager ruleManager;
	ICoordinator coordinator;
	IStrategy strategy;
	Context context;
	
	
	@Before
	public void setUp() throws Exception {
		
		context = new Context();
		// Configuration.BLACK, 0
		ParamGame param = new ParamGame();
		param.setSizeGoban(2);
				
		stateGame = new StateGame(context);
		computer = new Computer("computer", context);
		player = new Player("adversaire", Configuration.WHITE, null, stateGame);	
		stateGame.play(0, 0, player.getColor());
		evaluation = new TestEvaluation();
		ruleManager = Factory.getRuleManager(context);
		
		coordinator = new ICoordinator() {

			@Override
			public IPlayer getComputer() {
				return computer;
			}

			@Override
			public IPlayer getPlayer() {
				return player;
			}

			@Override
			public RuleManager getRuleManager() {
				return ruleManager;
			}

			@Override
			public IStateGame getStateGame() {
				return stateGame;
			}

			@Override
			public void startGame() {
			}

			@Override
			public void cleanUp() {
			}
			
		};
		strategy = Factory.getStrategy(context);
	}

	@Test
	public void testElphaBeta() {
		FreedomDegrees.showGobanOnConsole(stateGame);
		ILocation emplacement = strategy.getBestLocation(evaluation, true);
		System.out.println("-> Emplacement trouve : " + emplacement);
		assertTrue(emplacement.getCol()==1 && emplacement.getRow()==1);
	}
	
	
	/**
	 * Evaluation function for current test only
	 */
	class TestEvaluation implements IEvaluation {

		public TestEvaluation() {
		}
		
		
		@Override
		public int evaluate(IStateGame stateGame, StatusCheck status) {
			
			System.out.println("\nEvaluation");
			FreedomDegrees.showGobanOnConsole(stateGame);
			
			/* P C 
			 * P C */
			if	(stateGame.isPlayer(0, 0) 
				&& stateGame.isPlayer(0, 1)
				&& stateGame.isComputer(1, 0)
				&& stateGame.isComputer(1, 1)) {

				if (status.getWinner() == computer)
					return 1;
				else
					return -1;

				/* P P
				 * C C */
			} else if (stateGame.isPlayer(0, 0) 
				&& stateGame.isPlayer(1, 0)
				&& stateGame.isComputer(0, 1)
				&& stateGame.isComputer(1, 1)) {

				if (status.getWinner() == computer)
					return 1;
				else
					return -1;

				/* P C
				 *   P */
			} else if (stateGame.isPlayer(0, 0) 
				&& stateGame.isPlayer(1, 1)
				&& stateGame.isComputer(1, 0)
				&& stateGame.isFree(0, 1)) {
				
				assertTrue(status.getWinner() == player);
				return -1;
				
			/* P 
			 * C P */
			} else if (stateGame.isPlayer(0, 0) 
				&& stateGame.isPlayer(1, 1)
				&& stateGame.isFree(1, 0)
				&& stateGame.isComputer(0, 1)) {				
			
				assertTrue(status.getWinner() == player);
				return -1;
			} else {
				fail("incoherent situation !");
				return 0;
			}
		
		}
	}

}
