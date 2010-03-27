package fr.alma;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.alma.client.action.GameLoader;
import fr.alma.server.core.Computer;
import fr.alma.server.core.Factory;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.Player;
import fr.alma.server.ia.Evaluation;
import fr.alma.server.ia.Tools;
import fr.alma.server.rule.Configuration;

public class TestEvaluation {

	IStateGame stateGame = null;
	Evaluation evaluation = null;
	
	@Before
	public void setUp() throws Exception {
		stateGame = Factory.getStateGame();
		stateGame.load(new GameLoader(), "TestEvaluation.txt");
		IPlayer computer = new Computer("computer", Configuration.getColorComputer());
		IPlayer player = new Player("adversaire", Configuration.getColorPlayer(), null, stateGame);
		
		evaluation = new Evaluation(computer, player);
	}

	@Test
	public void testEvaluate() {
		Tools.showGobanOnConsole(stateGame);
		int result1 = evaluation.evaluate(stateGame, null);
		System.out.println("Resultat de l'évalution 1 : " + result1);
		
		stateGame.load(new GameLoader(), "TestEvaluation-1.txt");
		Tools.showGobanOnConsole(stateGame);
		int result2 = evaluation.evaluate(stateGame, null);
		System.out.println("Resultat de l'évalution 2 : " + result2);
		
		assertTrue(result1 == 89100);
		assertTrue(result2 == 89100);

	}

}
