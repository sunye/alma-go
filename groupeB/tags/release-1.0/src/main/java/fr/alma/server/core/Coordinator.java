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

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import fr.alma.client.action.Context;
import fr.alma.client.ihm.Goban;
import fr.alma.common.ui.Tools;
import fr.alma.server.ia.FreedomDegrees;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;
import fr.alma.server.rule.StatusCheck;

/**
 * Coordinate the game between the two players
 */
public class Coordinator implements ICoordinator {

	private PlayListener playListener;
	private IPlayer currentPlayer = null;
	private Context context = null;
	private boolean graphicMode = true;

	
	public Coordinator(Context context) {
		this.context = context;
	}

	
	public Coordinator(Context context, boolean graphicMode) {
		this.context = context;
		this.graphicMode = graphicMode;
	}

	
	
	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.ICoordinator#startGame()
	 */
	@Override
	public void startGame() {
		getPlayer().addPlayListener(getPlayListener());
		getComputer().addPlayListener(getPlayListener());
		setCurrentPlayer(getPlayer(Configuration.isColorFirstPlayer()));
		playInThread();
	}

	/**
	 * @param color
	 * @return the IPlayer gave with his color in parameter
	 */
	public IPlayer getPlayer(boolean color) {
		return getPlayer().getColor() == color ? getPlayer() : getComputer();
	}


	public PlayListener getPlayListener() {
		if (playListener == null) {
			playListener = new PlayListener() {
				@Override
				public boolean actionPerformed(PlayEvent event) {
					if (event.getWhen() == PlayEvent.BEFORE) {
						/* Must Verify the rules */
						if (getCurrentPlayer() != event.getPlayer())
							return false;

						return getRuleManager().checkBefore(getStateGame(), event.getEmplacement(), event.getPlayer()).isCanPlay();
					}

					if (graphicMode) {
						
						Runnable runnable = new Runnable() {
							@Override
							public void run() {
								/* AFTER */
								getGoban().repaint();
							}
						};
						SwingUtilities.invokeLater(runnable);
					}

					/* Control if the game is over */
					StatusCheck status = getRuleManager().checkAfter(getStateGame(), event.getEmplacement(), getCurrentPlayer());
					if (status.isGameOver()) {
						FreedomDegrees.showGobanOnConsole(getStateGame());
						if (! status.isCanPlay()) {
							status.setWinner(getPlayer());
						}
						
						if (graphicMode) {
							Tools.message(context.getMainFrame(), "Game over", "Winner is : " + status.getWinner().getName(), JOptionPane.INFORMATION_MESSAGE);
						}
						
						System.out.println("Game over - winner is : " + status.getWinner().getName());
						getPlayer().setEnabled(false);
						getComputer().setEnabled(false);
						return false;
					}

					/* Change the current player and play again */
					if (getCurrentPlayer() == getPlayer()) {
						setCurrentPlayer(getComputer());
					} else {
						setCurrentPlayer(getPlayer());
					}

					/*
					 * when the current player is the computer, the treatments
					 * are realize with a thread -> be carrefull !
					 */
					getCurrentPlayer().play();

					return true;
				}
			};
		}
		return playListener;
	}


	public IPlayer getCurrentPlayer() {
		return currentPlayer;
	}


	/* (non-Javadoc)
	 * @see fr.alma.server.core.IGenerator#getComputer()
	 */
	public IPlayer getComputer() {
		return context.getComputer();
	}


	/* (non-Javadoc)
	 * @see fr.alma.server.core.IGenerator#getPlayer()
	 */
	public IPlayer getPlayer() {
		return context.getPlayer();
	}


	public void setCurrentPlayer(IPlayer playerCurrent) {
		this.currentPlayer = playerCurrent;
	}


	private void playInThread() {
		getCurrentPlayer().play();
	}


	/* (non-Javadoc)
	 * @see fr.alma.server.core.IGenerator#getRuleManager()
	 */
	public RuleManager getRuleManager() {
		return context.getRuleManager();
	}


	/* (non-Javadoc)
	 * @see fr.alma.server.core.IGenerator#getStateGame()
	 */
	public IStateGame getStateGame() {
		return context.getStateGame();
	}

	
	public Goban getGoban() {
		return context.getGoban();
	}


	@Override
	public void cleanUp() {
		getPlayer().removePlayListeners();
		getComputer().removePlayListeners();
		playListener = null;
		currentPlayer = null;
	}

}
