package fr.alma.server.ia;

import java.util.ArrayList;
import java.util.List;

import fr.alma.server.core.Emplacement;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.rule.StatusCheck;

public class Evaluation implements IEvaluation {

	private IPlayer computer = null;
	private IPlayer player = null;

	
	public Evaluation(IPlayer computer, IPlayer player) {
		this.computer = computer;
		this.player = player;
	}
	
	
	/**
	 * -100000.. <= value <= 100000..
	 *  100000.. represents a very favorable situation for the computer
	 * -100000.. represents a very favorable situation for the player
	 */
	@Override
	public int evaluate(IStateGame stateGame, StatusCheck status) {
		/* Here we exploit recent inspection findings */
		if ((status != null) && status.isGameOver()) {
			if (status.getWinner() == getComputer()) {
				return 100000;
			} else {
				return -100000;
			}
		}
		int scoreComputer = getScore(searchDegrees(getComputer(), stateGame));
		int scorePlayer = getScore(searchDegrees(getPlayer(), stateGame));
		int score = scoreComputer - scorePlayer;
		//System.out.println("score computer : " + scoreComputer + " - score player : " + scorePlayer + " - total : " + score);
		return score;
	}
	

	/**
	 * Research degrees of freedom of the adversary 
	 * @param player
	 * @param stateGame
	 * @return
	 */
	private int[] searchDegrees(IPlayer player, IStateGame stateGame) {
		
		List<IEmplacement> emplacements = new ArrayList<IEmplacement>();
		int[] aDegreeFree = new int[Tools.getMaxDegreeFree()];
		IEmplacement emplacement = null;
		int degreeFree = 0;
		boolean find;
		boolean isComputer = (player == getComputer());

		for (short col = 0; col < stateGame.getMaxCol(); col++) {
			for (short row = 0; row < stateGame.getMaxRow(); row++) {
				if ((isComputer && stateGame.isPlayer(col, row)) || ((! isComputer) && stateGame.isComputer(col, row))) {
					emplacement = new Emplacement(col, row);
					find = false;
					for (IEmplacement e : emplacements) {
						if (e.equals(emplacement)) {
							find = true;
						}
					}
					if (! find) {
						degreeFree = Tools.countFreeDegrees(stateGame, ! player.getColor(), emplacement, emplacements);
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
		return computer;
	}

	
	public IPlayer getPlayer() {
		return player;
	}

}
