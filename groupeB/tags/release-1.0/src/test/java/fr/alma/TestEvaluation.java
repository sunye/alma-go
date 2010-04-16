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

import java.awt.BorderLayout;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.Test;

import fr.alma.client.action.Context;
import fr.alma.client.action.GameLoader;
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
		
		FreedomDegrees.showGobanOnConsole(stateGame2);
		int result2 = evaluation2.evaluate(stateGame2, null);
		
		assertEquals(result1, 5450);
		assertEquals(result2, -4);
	}

}
