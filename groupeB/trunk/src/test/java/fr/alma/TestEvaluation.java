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

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.Before;
import org.junit.Test;

import fr.alma.client.action.Context;
import fr.alma.client.action.GameLoader;
import fr.alma.client.action.IAction;
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
	IStateGame stateGame2 = null;
	Evaluation evaluation = null;
	Evaluation evaluation2 = null;
	@Before
	public void setUp() throws Exception {
		
		JFrame jf = new JFrame();
		
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setBounds(100, 100, 300, 360);
		
		
		Context context = new Context();
		context.setMainFrame(jf);
		
		context.setStatusBar(Factory.getStatutBarre());
		
		jf.setJMenuBar(Factory.getMenuBar(context));
		jf.add(context.getStatusBar(), BorderLayout.SOUTH);
	
			
		GameLoader gl = new GameLoader();
		gl.load("TestEvaluation.txt", context);

		stateGame = context.getStateGame();
		IPlayer computer = new Computer("computer", context);
		IPlayer player = new Player("adversaire", Configuration.WHITE, null, stateGame);
		context.setComputer(computer);
		context.setPlayer(player);
		
		
		evaluation = new Evaluation(context);
		
		gl.load("TestEvaluation-1.txt", context);
		stateGame2 = context.getStateGame();
		evaluation2 = new Evaluation(context);
	}

	@Test
	public void testEvaluate() {
		FreedomDegrees.showGobanOnConsole(stateGame);
		int result1 = evaluation.evaluate(stateGame, null);
		//System.out.println("Resultat de l'évalution 1 : " + result1);

		
		FreedomDegrees.showGobanOnConsole(stateGame2);
		int result2 = evaluation2.evaluate(stateGame2, null);
		//System.out.println("Resultat de l'évalution 2 : " + result2);
		
		assertEquals(result1, 5450);
		assertEquals(result2, -4);

	}

}
