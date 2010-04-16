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
package fr.alma.server.ia;

import java.util.ArrayList;
import java.util.List;

import fr.alma.client.action.Context;
import fr.alma.server.core.Location;
import fr.alma.server.core.ILocation;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.rule.StatusCheck;

public class Evaluation implements IEvaluation {

	Context context = null;
	
	
	public Evaluation(Context context) {
		this.context = context;
	}
	
	
	/**
	 * -100000.. <= value <= 100000..
	 *  100000.. represents a very favorable situation for the computer
	 * -100000.. represents a very favorable situation for the player
	 */
	@Override
	public int evaluate(IStateGame stateGame, StatusCheck status) {
		
		int scoreComputer;
		int scorePlayer;
		int score;
		
		/* Here we exploit recent inspection findings */
		if ((status != null) && status.isGameOver()) {
			if (status.getWinner() == getComputer()) {
				score = 1000000 / (stateGame.getLevel() - 2);
				
				return score;
			} else {
				score = -1000000 / (stateGame.getLevel() - 2);
				
				return score;
			}
		}
		scoreComputer = getScore(searchDegrees(getComputer(), stateGame));
		scorePlayer = getScore(searchDegrees(getPlayer(), stateGame));
		score = scoreComputer - scorePlayer;
		
		return (score / (stateGame.getLevel()-2));
	}
	

	/**
	 * Research degrees of freedom of the adversary 
	 * @param player
	 * @param stateGame
	 * @return
	 */
	private int[] searchDegrees(IPlayer player, IStateGame stateGame) {
		
		List<ILocation> emplacements = new ArrayList<ILocation>();
		int[] aDegreeFree = new int[FreedomDegrees.getMaxDegreeFree(context)];
		ILocation emplacement = null;
		int degreeFree = 0;
		boolean find;
		boolean isComputer = (player == getComputer());

		for (int col = 0; col < stateGame.getMaxCol(); col++) {
			for (int row = 0; row < stateGame.getMaxRow(); row++) {
				if ((isComputer && stateGame.isPlayer(col, row)) || ((! isComputer) && stateGame.isComputer(col, row))) {
					emplacement = new Location(col, row);
					find = false;
					for (ILocation e : emplacements) {
						if (e.equals(emplacement)) {
							find = true;
						}
					}
					if (! find) {
						degreeFree = FreedomDegrees.countFreeDegrees(stateGame, ! player.getColor(), emplacement, emplacements);
						aDegreeFree[degreeFree]++;
					}
				}
			}
		}
		return aDegreeFree;
	}


	/**
	 * the score is high with reduced degrees of freedom to the adversary
	 * @param degrees
	 * @return
	 */
	private int getScore(int[] degrees) {
		int result = 0;
		for (int cpt = 0; cpt < degrees.length; cpt++) {
			result += degrees[cpt] * Math.pow(10, cpt > 4 ? 0 : 5-cpt);
		}
		return result;
	}
	
	
	public IPlayer getComputer() {
		return context.getComputer();
	}

	
	public IPlayer getPlayer() {
		return context.getPlayer();
	}

}
