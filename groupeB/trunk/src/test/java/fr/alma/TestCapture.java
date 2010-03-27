package fr.alma;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fr.alma.client.action.GameLoader;
import fr.alma.server.core.Computer;
import fr.alma.server.core.Emplacement;
import fr.alma.server.core.Factory;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.Player;
import fr.alma.server.ia.Evaluation;
import fr.alma.server.ia.Tools;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;
import fr.alma.server.rule.StatusCheck;

public class TestCapture {

	IStateGame stateGame = null;
	Evaluation evaluation = null;
	RuleManager ruleManager = null;
	IEmplacement emplacement = null;
	IPlayer computer = null;
	IPlayer player = null;
	
	
	@Before
	public void setUp() throws Exception {
		stateGame = Factory.getStateGame();
		stateGame.load(new GameLoader(), "TestEvaluation-1.txt");
		computer = new Computer("computer", Configuration.getColorComputer());
		player = new Player("adversaire", Configuration.getColorPlayer(), null, stateGame);
		
		//evaluation = new Evaluation(computer, player);
		ruleManager = Factory.getRuleManager();	
	}

	@Test
	public void testCapture() {
		Tools.showGobanOnConsole(stateGame);
		emplacement = new Emplacement((short)0, (short)1);
		StatusCheck status = ruleManager.checkAfter(stateGame, emplacement, computer);
		assertFalse(status.isGameOver());
	}

}