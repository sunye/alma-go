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
import fr.alma.client.action.GameLoader;
import fr.alma.client.action.ParamGame;
import fr.alma.server.core.Computer;
import fr.alma.server.core.Emplacement;
import fr.alma.server.core.Factory;
import fr.alma.server.core.ILocation;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.Player;
import fr.alma.server.ia.Evaluation;
import fr.alma.server.ia.FreedomDegrees;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;
import fr.alma.server.rule.StatusCheck;

public class TestCapture {

	IStateGame stateGame = null;
	Evaluation evaluation = null;
	RuleManager ruleManager = null;
	ILocation emplacement = null;
	IPlayer computer = null;
	IPlayer player = null;
	Context context;
	
	
	@Before
	public void setUp() throws Exception {
		
		context = new Context();
		// Configuration.BLACK, 0
		ParamGame param = new ParamGame();
		param.setSizeGoban(9);
		
		stateGame = Factory.getStateGame(context);
		GameLoader gl = new GameLoader();
		gl.load("TestEvaluation-1.txt", context);
		computer = new Computer("computer", context);
		player = new Player("adversaire", Configuration.WHITE, null, stateGame);
		
		//evaluation = new Evaluation(computer, player);
		ruleManager = Factory.getRuleManager(context);	
	}

	@Test
	public void testCapture() {
		FreedomDegrees.showGobanOnConsole(stateGame);
		emplacement = new Emplacement((short)0, (short)1);
		StatusCheck status = ruleManager.checkAfter(stateGame, emplacement, computer);
		assertFalse(status.isGameOver());
	}

}
