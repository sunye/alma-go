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
package fr.alma.server.core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.alma.client.ihm.Goban;
import fr.alma.server.ia.IEvaluation;

public class Player extends AbstractPlayer {

	private Goban goban = null;
	private IStateGame stateGame; 
	protected MouseListener listener = null;
	private boolean enabled = false;
	private IPlayer player = this;
	
	
	public Player(String name, boolean color, Goban goban, IStateGame stateGame) {
		super(name, color);
		this.goban = goban;
		this.stateGame = stateGame;
	}

	@Override
	/**
	 * Call once for the player 
	 */
	public void play() {
		if (listener == null) {
			listener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent event) {
					super.mouseClicked(event);
					int column = (int)Math.round((event.getX()-25.0)/30.0);
					int row = (int)Math.round((event.getY()-25.0)/30.0);

					if (raiseEvent(new PlayEvent(player, PlayEvent.BEFORE, new Location(column, row)))) {
						try {
							if (stateGame.play(column, row, getColor())) {
								raiseEvent(new PlayEvent(player, PlayEvent.AFTER, new Location(column, row)));
							}							
						} catch (Exception exception) {
							System.out.println("Player - Internal error : " + exception.getLocalizedMessage());
						}

					}
				}
			};
			goban.addMouseListener(listener);
		}
	}
	
	
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	
	public String toString() {
		return getName();
	}
	
	
	@Override
	public void setStrategy(IStrategy strategieGame) {
	}

	@Override
	public void setEvaluation(IEvaluation evaluation) {
	}

	public boolean isEnable() {
		return enabled;
	}

	@Override
	public void cleanUp() {
		if (listener != null) {
			goban.removeMouseListener(listener);
			listener = null;
		}
		goban = null;
		stateGame = null;
		listener = null;
		player = null;		
	}

	@Override
	public void interrupt() {
	}
	
}

