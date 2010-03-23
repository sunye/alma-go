package fr.alma.server.ia;

import java.util.ArrayList;
import java.util.List;

import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Emplacement;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;

public class Evaluation implements IEvaluation {

	private Coordinator coordinator = null;
	private IPlayer computer = null;
	
	public Evaluation(Coordinator coordinator, IPlayer computer) {
		this.coordinator = coordinator;
		this.computer = computer;
	}
	
	
	@Override
	public int evaluate(IStateGame stateGame) {
		int scoreW = getScore(searchDegrees(getCoordinator().getPlayer(IPlayer.BLACK), stateGame));
		int scoreB = getScore(searchDegrees(getCoordinator().getPlayer(IPlayer.WHITE), stateGame));
		int score = 0;
		if (computer.getColor() == IPlayer.WHITE) {
			score = (scoreW - scoreB);
		} else {
			score = (scoreB - scoreW);
		}
		//System.out.println("score W : " + scoreW + " - score B : " + scoreB + " - total : " + score);
		return score;

	}

	private int[] searchDegrees(IPlayer player, IStateGame stateGame) {
		List<IEmplacement> emplacements = new ArrayList<IEmplacement>();
		// Calculer de facon intelligente le degree max pour l'integrer 
		// comme taille du tableau !
		int[] aDegreeFree = new int[324];
		
		int degreeFree = 0;
		boolean isComputer = (player == computer);

		for (short col = 0; col < stateGame.getMaxCol(); col++) {
			for (short row = 0; row < stateGame.getMaxRow(); row++) {
				if ((isComputer && stateGame.isComputer(col, row)) || ((! isComputer) && stateGame.isPlayer(col, row))) {
					IEmplacement emplacement = new Emplacement(col, row);
					boolean find = false;
					for (IEmplacement e : emplacements) {
						if (e.equals(emplacement)) {
							find = true;
						}
					}
					if (! find) {					
						degreeFree = Tools.countFreeDegrees(getCoordinator(), player.getColor(), emplacement, emplacements);
						if (degreeFree > 10)
							//System.out.println("dregree free : " + degreeFree);
						aDegreeFree[degreeFree]++;
					}
					
				}
			}
		}
		return aDegreeFree;

	}


	private int getScore(int[] degrees) {
		int result = 0;
		for (int cpt = degrees.length-1; cpt >= 0; cpt--) {
			result += degrees[cpt] * (degrees.length/2 - cpt);
		}
		return result;
	}
	
	
	public Coordinator getCoordinator() {
		return coordinator;
	}
	

}
