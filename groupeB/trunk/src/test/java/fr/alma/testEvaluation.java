package fr.alma;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.alma.client.action.GameLoader;
import fr.alma.server.core.Computer;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.Player;
import fr.alma.server.core.StateGame;
import fr.alma.server.ia.Evaluation;
import fr.alma.server.ia.Tools;
import fr.alma.server.rule.Configuration;

public class testEvaluation {

	IStateGame stateGame = null;
	Evaluation evaluation = null;
	
	@Before
	public void setUp() throws Exception {
		stateGame = new StateGame();
		stateGame.load(new GameLoader(), "TestEvaluation.txt");
		IPlayer computer = new Computer("computer", Configuration.getColorComputer());
		IPlayer player = new Player("adversaire", Configuration.getColorPlayer(), null, stateGame);
		
		evaluation = new Evaluation(computer, player);
	}

	@Test
	public void testEvaluate() {
		Tools.showGobanOnConsole(stateGame);
		assertTrue(evaluation.evaluate(stateGame, null) == 89100);
	}

}
