/*
 *   
 *   IA Project ATARI-GO
 *   UNIVERSITY OF NANTES
 *   MASTER ALMA 1
 *   2009 - 2010
 * 	 Version 1.0
 *   
 *   $Date$
 *   $Author$
 * 	 $Revision$
 * 
 *   Copyright (C) 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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

	public final int EXPECT_NUMBER_ITER = 10;
	
	RuleSuicide rule;
	IStateGame stateGame;
	ILocation location;
	IPlayer player1;
	IPlayer player2;
	Context context;
	Coordinator coordinator;
	RuleManager ruleManager;
	int cptCol = 0;
	int cptRow = 0;
	int nbIteration = 0;
	
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

	/**
	 * the coordinator should rotate a series of actions between the two players
	 */
	@Test
	public void testCoordinationGame() {
		
		coordinator.startGame();
		//assertTrue("the game does not start with the right color", coordinator.getCurrentPlayer().getColor() == Configuration.getColorFirstPlayer());
		assertTrue("wrong number of iterations expected : " + nbIteration , nbIteration == EXPECT_NUMBER_ITER);
	}
	
	
	/*
	 * redefines the players' actions
	 */
	class InternalPlayer extends AbstractPlayer {
		
		public InternalPlayer(String name, boolean color) {
			super(name, color);
		}
		
		@Override
		public void interrupt() {			
		}

		@Override
		public void play() {
			
			nbIteration++;
			
			ILocation location = new Location(cptCol, cptRow);

			PlayEvent eventBefore = new PlayEvent(this, PlayEvent.BEFORE, location);
			boolean accept = raiseEvent(eventBefore);
			assertTrue("the player should be able to play at this location : " + location, accept);

			try {
				stateGame.play(cptCol, cptRow, this.getColor());
			} catch (Exception e) {
				fail(e.getMessage());
			}
			
			
			PlayEvent eventAfter = new PlayEvent(this, PlayEvent.AFTER, location);
			if (cptCol < context.getSizeGoban()-1) {
				cptCol += 1;
			} else {
				if (cptRow  < context.getSizeGoban()-1) {
					cptRow += 1;
				}
			}
			raiseEvent(eventAfter);
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
	
	
	/*
	 * Determines the ending of arbitrarily
	 */
	class InternalRuleManager extends RuleManager {
		public InternalRuleManager(Context context) {
			super(context);
		}
		
		
		public StatusCheck checkAfter(IStateGame stateGame, ILocation emplacement, IPlayer currentPlayer) {
			
			if ((emplacement.getCol() == 8) && (emplacement.getRow() == 8)) {
				StatusCheck status = new StatusCheck();
				status.setCanPlay(true);
				status.setGameOver(true);
				status.setEmplacement(emplacement);
				status.setWinner(currentPlayer);
				return status;
			}

			return super.checkAfter(stateGame, emplacement, currentPlayer);
		}
	}
	
	

}
