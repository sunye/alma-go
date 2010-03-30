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
package fr.alma.server.ia;

import java.util.ArrayList;
import java.util.List;

import fr.alma.client.action.Context;
import fr.alma.server.core.Emplacement;
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
				//System.out.println("Level : " + stateGame.getLevel() + " - computer can win at : " + status.getEmplacement() + " score : " + score);
				return score;
			} else {
				score = -1000000 / (stateGame.getLevel() - 2);
				//System.out.println("Level : " + stateGame.getLevel() + " - player can win at : " + status.getEmplacement() + " score : " + score);
				return score;
			}
		}
		scoreComputer = getScore(searchDegrees(getComputer(), stateGame));
		scorePlayer = getScore(searchDegrees(getPlayer(), stateGame));
		score = scoreComputer - scorePlayer;
		//int score = scoreComputer - (scorePlayer * 2);
		//System.out.println("score computer : " + scoreComputer + " - score player : " + scorePlayer + " - total : " + score);
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
					emplacement = new Emplacement(col, row);
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
