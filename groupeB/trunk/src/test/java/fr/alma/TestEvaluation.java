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
import fr.alma.server.core.Factory;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.Player;
import fr.alma.server.ia.Evaluation;
import fr.alma.server.ia.FreedomDegrees;
import fr.alma.server.rule.Configuration;

public class TestEvaluation {

	IStateGame stateGame = null;
	Evaluation evaluation = null;
	
	@Before
	public void setUp() throws Exception {
				
		Context context;
		context = new Context();
		ParamGame param = new ParamGame();
		param.setSizeGoban(9);
		
		
		GameLoader gl = new GameLoader();
		gl.load("TestEvaluation.txt", context);
		stateGame = Factory.getStateGame(context);
		IPlayer computer = new Computer("computer", Configuration.BLACK, 0);
		IPlayer player = new Player("adversaire", Configuration.WHITE, null, stateGame);
		
		context.setComputer(computer);
		context.setPlayer(player);
		context.setStateGame(stateGame);
		
		evaluation = new Evaluation(context);
	}

	@Test
	public void testEvaluate() {
		FreedomDegrees.showGobanOnConsole(stateGame);
		int result1 = evaluation.evaluate(stateGame, null);
		System.out.println("Resultat de l'évalution 1 : " + result1);
		GameLoader gl = new GameLoader();
		Context context = new Context();
		gl.load("TestEvaluation-1.txt", context);
		FreedomDegrees.showGobanOnConsole(stateGame);
		int result2 = evaluation.evaluate(stateGame, null);
		System.out.println("Resultat de l'évalution 2 : " + result2);
		
		assertTrue(result1 == 89100);
		assertTrue(result2 == 89100);

	}

}
