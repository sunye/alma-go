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
package fr.alma.server.rule;

import fr.alma.client.action.Context;
import fr.alma.server.core.ILocation;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;

/**
 * Manage the different rules
 */
public class RuleManager {
	
	private RuleSuicide ruleSuicide = null;
	private RuleCapture ruleCapture = null;
	private RuleAlreadyBusy ruleAlreadyBusy = null;
	private Context context = null;
	
	
	public RuleManager(Context context) {
		this.context = context;
		ruleAlreadyBusy = new RuleAlreadyBusy();
		ruleSuicide = new RuleSuicide();
		ruleCapture = new RuleCapture();
	}
	
	
	/**
	 * Check if the player can played at this emplacement
	 * This function must be very fast because 
	 * it is called by alpha beta procedure
	 * @param stateGame
	 * @param emplacement
	 * @param currentPlayer
	 * @return
	 */
	public StatusCheck checkBefore(IStateGame stateGame, ILocation emplacement, IPlayer currentPlayer) {
		StatusCheck status = new StatusCheck();
		
		if (! ruleAlreadyBusy.accept(stateGame, emplacement, currentPlayer)) {
			status.setCanPlay(false);
			return status;
		}
		
		try {
			stateGame.play(emplacement.getCol(), emplacement.getRow(), currentPlayer.getColor());
		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean provokeCapture = ruleCapture.provokeCapture(stateGame, emplacement, currentPlayer);
		stateGame.remove(emplacement.getCol(), emplacement.getRow());
		if (provokeCapture) {
			status.setCanPlay(true);
			status.setGameOver(true);
			status.setEmplacement(emplacement);
			status.setWinner(currentPlayer);
			return status;
		}
		
		if (ruleSuicide.provokeSuicide(stateGame, emplacement, currentPlayer)) {
			status.setCanPlay(false);
			return status;
		}
		
		status.setCanPlay(true);
		return status;
	}
	
	public StatusCheck checkAfter(IStateGame stateGame, ILocation emplacement, IPlayer currentPlayer) {
		StatusCheck status = new StatusCheck();
		
		if ((emplacement == null)) {
			status.setCanPlay(false);
			status.setGameOver(true);
			status.setEmplacement(emplacement);
			status.setWinner(null);
			return status;
		}
		
		if (ruleCapture.provokeCapture(stateGame, emplacement, currentPlayer)) {
			status.setCanPlay(true);
			status.setGameOver(true);
			status.setEmplacement(emplacement);
			status.setWinner(currentPlayer);
			return status;
		}
		
		return status;
	}


	public Context getContext() {
		return context;
	}
	
}
