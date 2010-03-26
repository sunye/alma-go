package fr.alma.server.ia;


import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Emplacement;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.StatusCheck;


/**
 * @author Romain & bruno
 * 
 * Questions :
 * - abstraction du tree avec déduction des enfants au fil de l'eau
 * - jouer chaque coup, faire les traitements et retirer le coup afin de limiter l'emprunte mémoire
 * - niveau 2 : premier coup à jouer par l'ordinateur.
 * - dans l'analyse, ne prendre en compte que les zones occupées avec des éléments proches sauf
 *   pour le premier coup qui sera joué au hasard.
 */
public class AlphaBeta implements IStrategy {

	private Coordinator coordinator;
	private IEvaluation evaluation;
	private IStateGame stateGame;

	
	/* Interet de les avoir en global : toujours disponibes ! */
	private short rowMax = -1, colMax = -1;
	int cpt = 0;
	boolean gameOver = false;
	short cptCol = 0;
	short cptRow = 0;
	
	
	public AlphaBeta(Coordinator coordinator) {
		this.coordinator = coordinator;
		evaluation = new Evaluation(coordinator.getComputer(), coordinator.getPlayer());
	}
	
	
	public IEmplacement getEmplacementMax() {
		cpt = 0;
		
		/* The stateGame will be modified to simulate 
		 * the differents possibilities. For that, we muste
		 * clone it */
		stateGame = (IStateGame)coordinator.getStateGame().clone();
		
		getValue(2, stateGame, 0, null);
		
		//System.out.println("nb appels a gatValue() : " + cpt);
		return new Emplacement(colMax, rowMax);
	}
	
	/*
	 * levelMax  : niveau correspondant aux feuilles
	 * level = 1 : racine de l'arbre (etat courant du jeu)
	 * level = 2 : prochain coups possibles pour l'ordinateur
	 * level = 3 : prochain coups possibles pour le joueur
	 * level = 4 : coups suivants pour l'ordinateur ...etc.
	 * 
	 * => We start at level 2
	 * The state of the game will undergo changes
	 */
	public int getValue(int level, IStateGame stateGame, int extremum, StatusCheck status) {
		int value;
		cpt++;
		
		//System.out.println("Alpha-Beta - getValue() Level : " + level);
		if ((level < Configuration.getMaxDeepLevel()) && existChildStateGame(status)) {
			/* who has to play : if Odd level, it is the turn of the player
			 * otherwise it is the computer */
			if (level%2 == 0) {
				/* Search the Max value for the computer */
				value = max(level, stateGame, extremum);
			} else {
				/* Odd level, it is the turn of the player
				 * -> Search the Min value 
				 */
				value = min(level, stateGame, extremum);
			}
		} else { 
			/* it is a leaf - Refresh the value of the game for this level*/
			value = evaluation.evaluate(stateGame, status);
			//System.out.println("Level " + level + " -> Resultat evaluation : " + value);
			//Tools.showGobanOnConsole(stateGame);
		}
		//System.out.println("return the getValue(level="+level+")" + value);
		return value;
	}
	
	
	private boolean existChildStateGame(StatusCheck status) {
		boolean result = ((status == null)  || ! status.isGameOver());
		//if (! result)
		//	System.out.println("Alpha-Beta : Game is over");
		return result;
	}
	
	
	/*
	 * Recherche du Max
	 */
	private int max(int level, IStateGame stateGame, int extremum) {
		int max = Integer.MIN_VALUE;
		//System.out.println("Level " + level + " -> Recherche max");
		
		for (short col = 0; col < stateGame.getMaxCol(); col++) {
			for (short row = 0; row < stateGame.getMaxRow(); row++) {
				StatusCheck status = coordinator.getRuleManager().checkBefore(stateGame, new Emplacement(col, row), getComputer());
				if ((max < extremum) && status.isCanPlay()) {
					try {
						stateGame.play(col, row, getComputer().getColor());
					} catch (Exception e) {
						System.out.println("Internal error : " + e.getLocalizedMessage());
					}
					int value = getValue(level+1, stateGame, max, status);
					if (value > max) { 
						max = value;
						colMax = col;
						rowMax = row;
					}
					stateGame.remove(col, row);
				}
			}
		}
		return max;
	}
	
	
	private int min(int level, IStateGame stateGame, int extremum) {
		int min = Integer.MAX_VALUE;
		
		//System.out.println("Level " + level + " -> Recherche min");
		for (short col = 0; col < stateGame.getMaxCol(); col++) {
			for (short row = 0; row < stateGame.getMaxRow(); row++) {
				StatusCheck status = coordinator.getRuleManager().checkBefore(stateGame, new Emplacement(col, row), getPlayer());
				if ((min > extremum) && status.isCanPlay()) {
					try {
						stateGame.play(col, row, getPlayer().getColor());
					} catch (Exception e) {
						System.out.println("Internal error : " + e.getLocalizedMessage());
					}
					int value = getValue(level+1, stateGame, min, status);
					if (value < min) { 
						min = value;
					}
					stateGame.remove(col, row);
				}
			}
		}
		return min;	
	}

	
	public IStateGame getStateGame() {
		return coordinator.getStateGame();
	}
	
	
	public IPlayer getComputer() {
		return coordinator.getComputer();
	}

	
	public IPlayer getPlayer() {
		return coordinator.getPlayer();
	}
}
