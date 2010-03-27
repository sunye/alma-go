package fr.alma;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.alma.server.core.Computer;
import fr.alma.server.core.Factory;
import fr.alma.server.core.ICoordinator;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;
import fr.alma.server.core.Player;
import fr.alma.server.core.StateGame;
import fr.alma.server.ia.IEvaluation;
import fr.alma.server.ia.Tools;
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
	
	
	@Before
	public void setUp() throws Exception {
		
		short lineV = 2;
		short lineH = 2;
		stateGame = new StateGame(lineV, lineH);
		computer = new Computer("computer", Configuration.getColorComputer());
		player = new Player("adversaire", Configuration.getColorPlayer(), null, stateGame);	
		stateGame.play((short)0, (short)0, player.getColor());
		evaluation = new TestEvaluation();
		ruleManager = Factory.getRuleManager();
		
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
			
		};
		strategy = Factory.getStrategy(coordinator);
	}

	@Test
	public void testElphaBeta() {
		Tools.showGobanOnConsole(stateGame);
		IEmplacement emplacement = strategy.getEmplacementMax(evaluation, true);
		System.out.println("-> Emplacement trouve : " + emplacement);
		assertTrue(emplacement.getCol()==1 && emplacement.getRow()==1);
	}
	
	
	/**
	 * @author bruno
	 * Evaluation function for current test only
	 */
	class TestEvaluation implements IEvaluation {

		public TestEvaluation() {
		}
		
		
		@Override
		public int evaluate(IStateGame stateGame, StatusCheck status) {
			
			System.out.println("\nEvaluation");
			Tools.showGobanOnConsole(stateGame);
			
			/* P C 
			 * P C */
			if	(stateGame.isPlayer((short)0, (short)0) 
				&& stateGame.isPlayer((short)0, (short)1)
				&& stateGame.isComputer((short)1, (short)0)
				&& stateGame.isComputer((short)1, (short)1)) {

				if (status.getWinner() == computer)
					return 1;
				else
					return -1;

				/* P P
				 * C C */
			} else if (stateGame.isPlayer((short)0, (short)0) 
				&& stateGame.isPlayer((short)1, (short)0)
				&& stateGame.isComputer((short)0, (short)1)
				&& stateGame.isComputer((short)1, (short)1)) {

				if (status.getWinner() == computer)
					return 1;
				else
					return -1;

				/* P C
				 *   P */
			} else if (stateGame.isPlayer((short)0, (short)0) 
				&& stateGame.isPlayer((short)1, (short)1)
				&& stateGame.isComputer((short)1, (short)0)
				&& stateGame.isFree((short)0, (short)1)) {
				
				assertTrue(status.getWinner() == player);
				return -1;
				
			/* P 
			 * C P */
			} else if (stateGame.isPlayer((short)0, (short)0) 
				&& stateGame.isPlayer((short)1, (short)1)
				&& stateGame.isFree((short)1, (short)0)
				&& stateGame.isComputer((short)0, (short)1)) {				
			
				assertTrue(status.getWinner() == player);
				return -1;
			} else {
				fail("incoherent situation !");
				return 0;
			}
		
		}
	}

}
