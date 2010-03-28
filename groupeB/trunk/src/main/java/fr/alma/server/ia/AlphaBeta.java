package fr.alma.server.ia;


import fr.alma.client.action.Context;
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

	private IEvaluation evaluation;
	private IStateGame stateGame;
	private Context context;

	
	/* Interet de les avoir en global : toujours disponibes ! */
	private int rowMax = -1, colMax = -1;
	private int cpt = 0;
	private boolean trace;
	private boolean interrupted = false;
	
	
	public AlphaBeta(Context context) {
		this.context = context;
	}
	
	
	public IEmplacement getEmplacementMax(IEvaluation evaluation, boolean trace) {
		cpt = 0;
		this.evaluation = evaluation;
		this.trace = trace;
		/* The stateGame will be modified to simulate 
		 * the differents possibilities. For that, we muste
		 * clone it */
		stateGame = (IStateGame)getStateGame().clone();
		setInterrupted(false);
		
		getValue(2, stateGame, Integer.MAX_VALUE, null);
		
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
		
		if (trace)
			System.out.println("Alpha-Beta - getValue() Level : " + level);
		
		if ((level < Configuration.getMaxDeepLevel(context.getSizeGoban())) && existChildStateGame(status) && ! isInterrupted()) {
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
		
		if (trace)
			System.out.println("return the getValue(level="+level+")" + value);
		
		return value;
	}
	
	
	private boolean existChildStateGame(StatusCheck status) {
		boolean result = ((status == null)  || ! status.isGameOver());
		if (trace && (! result)) 
			System.out.println("Alpha-Beta : Game is over - winner = " + status.getWinner());
		
		return result;
	}
	
	
	/*
	 * Recherche du Max
	 */
	private int max(int level, IStateGame stateGame, int extremum) {
		int max = Integer.MIN_VALUE;
		if (trace)
			System.out.println("Level " + level + " -> Recherche max");
		
		for (int col = 0; col < stateGame.getMaxCol(); col++) {
			for (int row = 0; row < stateGame.getMaxRow(); row++) {
				if (max < extremum) {
					StatusCheck status = context.getRuleManager().checkBefore(stateGame, new Emplacement(col, row), getComputer());
					if (status.isCanPlay()) {
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
				} else {
					col = stateGame.getMaxCol();
					row = stateGame.getMaxRow();					
				}
			}
		}
		if (trace)
			System.out.println("Level(" + level + ") -> return max = " + max);
		return max;
	}
	
	
	private int min(int level, IStateGame stateGame, int extremum) {
		int min = Integer.MAX_VALUE;
		
		if (trace)
			System.out.println("Level " + level + " -> Recherche min");
		for (int col = 0; col < stateGame.getMaxCol(); col++) {
			for (int row = 0; row < stateGame.getMaxRow(); row++) {
				if (min > extremum) {
					StatusCheck status = context.getRuleManager().checkBefore(stateGame, new Emplacement(col, row), getPlayer());
					if (status.isCanPlay()) {
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
				} else {
					col = stateGame.getMaxCol();
					row = stateGame.getMaxRow();
				}
			}
		}
		if (trace)
			System.out.println("Level(" + level + ") -> return min = " + min);
		return min;	
	}
	
	
	public IStateGame getStateGame() {
		return context.getStateGame();
	}
	
	
	public IPlayer getComputer() {
		return context.getComputer();
	}

	
	public IPlayer getPlayer() {
		return context.getPlayer();
	}
	
	@Override
	public void interrupt() {
		setInterrupted(true);	
	}


	public synchronized boolean isInterrupted() {
		return interrupted;
	}


	public synchronized void setInterrupted(boolean interrupted) {
		this.interrupted = interrupted;
	}
}
