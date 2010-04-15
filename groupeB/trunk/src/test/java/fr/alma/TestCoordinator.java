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
import fr.alma.client.ihm.Goban;
import fr.alma.server.core.AbstractPlayer;
import fr.alma.server.core.Coordinator;
import fr.alma.server.core.IStrategy;
import fr.alma.server.core.Factory;
import fr.alma.server.core.ILocation;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.Location;
import fr.alma.server.core.PlayEvent;
import fr.alma.server.ia.IEvaluation;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;
import fr.alma.server.rule.RuleSuicide;
import fr.alma.server.rule.StatusCheck;


/**
 * Verifies the coherence of locations
 */
public class TestCoordinator {

	RuleSuicide rule;
	IStateGame stateGame;
	ILocation location;
	IPlayer player1;
	IPlayer player2;
	Context context;
	Coordinator coordinator;
	RuleManager ruleManager;
	int col = 0;
	int row = 0;
	
	
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
		rule = new RuleSuicide();
		stateGame = Factory.getStateGame(context);
		player1 = new InternalPlayer("name player 1", Configuration.getColorFirstPlayer());
		player2 = new InternalPlayer("name player 2", ! Configuration.getColorFirstPlayer());
		context.setComputer(player1);
		context.setPlayer(player2);
		context.setStateGame(stateGame);
		
		ruleManager = new InternalRuleManager(context);	
		context.setRuleManager(ruleManager);
		
		Goban goban = Factory.getGoban(context);
		context.setGoban(goban);
		
		coordinator = new Coordinator(context, false);
	}

	@Test
	public void testCoordinationGame() {
		
		coordinator.startGame();
		assertTrue("the game does not start with the right color", coordinator.getCurrentPlayer().getColor() == Configuration.getColorFirstPlayer());
		
		//coordinator.getCurrentPlayer()
	}
	
	
	class InternalPlayer extends AbstractPlayer {
		
		public InternalPlayer(String name, boolean color) {
			super(name, color);
		}
		
		
		@Override
		public void interrupt() {			
		}

		@Override
		public void play() {
			ILocation location = new Location(col, row);
			System.out.println("player " + getName() + " at location : " + location);
			PlayEvent eventBefore = new PlayEvent(this, PlayEvent.BEFORE, location);
			boolean accept = raiseEvent(eventBefore);
			assertTrue("the player should be able to play at this location : " + location, accept);

			PlayEvent eventAfter = new PlayEvent(this, PlayEvent.AFTER, location);
			accept = raiseEvent(eventAfter);
			assertTrue("the player should be able to play at this location : " + location, accept);
			if (col < context.getSizeGoban()) {
				col += 1;
			}
			else {
				if (row  <context.getSizeGoban()) {
					row += 1;
				}
			}
		}

		@Override
		public void setEnabled(boolean enable) {
		}

		@Override
		public void setEvaluation(IEvaluation evaluation) {
		}

		@Override
		public void setStrategy(IStrategy strategyGame) {
		}

		@Override
		public void cleanUp() {
		}
		
	}
	
	class InternalRuleManager extends RuleManager {
		public InternalRuleManager(Context context) {
			super(context);
		}
		
		
		public StatusCheck checkAfter(IStateGame stateGame, ILocation emplacement, IPlayer currentPlayer) {
			System.out.println("checkAfter : " + emplacement);
			
			StatusCheck status = new StatusCheck();
			status.setCanPlay(true);
			status.setGameOver(true);
			status.setEmplacement(emplacement);
			status.setWinner(currentPlayer);
			return status;

			
			//return super.checkAfter(stateGame, emplacement, currentPlayer);
		}
	}
	
	

}
