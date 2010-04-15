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


import java.awt.event.MouseListener;

import javax.swing.JLabel;
import org.junit.Before;
import org.junit.Test;
import fr.alma.client.action.Context;
import fr.alma.client.action.ParamGame;
import fr.alma.client.ihm.Goban;
import fr.alma.server.core.Factory;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.Location;
import fr.alma.server.core.PlayEvent;
import fr.alma.server.core.PlayListener;
import fr.alma.server.core.Player;
import fr.alma.server.core.StateGame;
import fr.alma.server.ia.IEvaluation;
import fr.alma.server.rule.Configuration;


public class TestPlayer {

	IStateGame stateGame = null;
	IEvaluation evaluation = null;
	IPlayer player;
	Context context;
	boolean fin;
	int cpt;
	Goban goban;
	
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
		context.setStatusBar(new JLabel());
				
		stateGame = new StateGame(context);
		goban = Factory.getGoban(context);
		
		player = new InternalPlayer("nom player", Configuration.WHITE, goban, stateGame); 
		player.addPlayListener(new TestPlayListener());

	}

	/**
	 * Test if the player intercept the click and propagate the correct 
	 * play event  
	 */
	@Test
	public void testClick() {
		
		/* After that, the player can play at all times */
		player.play();
		
		/* simulating a mouse click */
		((InternalPlayer)player).getMouseListener().mouseClicked(new java.awt.event.MouseEvent(goban, 1, 1, 1,
                50, 50, 1, false,1));
		
		/* Waiting spread mouse click or maximum periods */
		while (! fin  && cpt++ < 10)
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				fail("Current Thread was interrupted !");
			}
		}
		
		assertTrue("The event play was not propagated !", fin);
	}
	
	
	/* Represents the internal listener on the player */
	class TestPlayListener implements PlayListener {

		@Override
		public boolean actionPerformed(PlayEvent e) {
			assertTrue(e.getPlayer() == player);
			assertTrue((e.getWhen() == PlayEvent.BEFORE) || (e.getWhen() == PlayEvent.AFTER));
			assertTrue("The computer must give a position even if interrupted", e.getEmplacement() != null);
			assertTrue("Intersection corresponding to the click is not good", e.getEmplacement().equals(new Location(1,1)));
			fin = true;
			return true;
		}
	}
	
	

	/**
	 * We must redefine this type of player to access
	 * the internal MouseListener for click simulation 
	 */
	class InternalPlayer extends Player {
		
		public InternalPlayer(String name, boolean color, Goban goban, IStateGame stateGame) {
			super(name, color, goban, stateGame);
		}
		
		MouseListener getMouseListener() {
			return super.listener;
		}
	}
	
}
