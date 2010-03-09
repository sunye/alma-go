package fr.alma.server.ia;

import fr.alma.server.core.Emplacement;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;

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


	private int levelMax;
	private IPlayer computer;
	
	/* Interet de les avoir en global : toujours disponibes ! */
	private short rowMax = -1, colMax = -1;
	
	
	public AlphaBeta(IStateGame stateGame) {
		this.stateGame = stateGame;
		this.computer = computer;
		evaluation = new Evaluation();
	}
	
	
	public IEmplacement getEmplacementMax() {
		int max = getValue(2, stateGame);
		return new Emplacement(colMax, rowMax);
	}
	
	/*
	 * levelMax  : niveau correspondant aux feuilles
	 * level = 1 : racine de l'arbre (etat courant du jeu)
	 * level = 2 : prochain coups possibles pour l'ordinateur
	 * level = 3 : prochain coups possibles pour le joueur
	 * level = 4 : coups suivants pour l'ordinateur ...etc.
	 * 
	 * => On commence avec un level a� 2
	 */
	public int getValue(int level, IStateGame stateGame) {
		int value;
		
		if ((level < levelMax) && ! stateGame.isOver()) {
			/* Test nombre pair */
			if (level%2 == 0) {
				/* Recherche du Max */
				value = max(level, stateGame);
			} else { /* Niveau impaire, c'est à l'adversaire de jouer */
				/* Recherche du Min */
				value = min(level, stateGame);
			}
		} else { /* On se trouve sur une feuille */
			value = evaluation.evaluate(stateGame);
		}
		return value;
	}
	
		
	/*
	 * Recherche du Max
	 */
	private int max(int level, IStateGame stateGame) {
		int max = Integer.MIN_VALUE;
		
		for (short col = 0; col < stateGame.getMaxCol(); col++) {
			for (short row = 0; row < stateGame.getMaxCol(); row++) {
				if (stateGame.isPlayable(col, row)) {
					stateGame.play(col, row, computer.getColor());
					int value = getValue(level+1, stateGame);
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
	
	
	private int min(int level, IStateGame stateGame) {
		int min = Integer.MAX_VALUE;
		
		for (short col = 0; col < stateGame.getMaxCol(); col++) {
			for (short row = 0; row < stateGame.getMaxCol(); row++) {
				if (stateGame.isPlayable(col, row)) {
					stateGame.play(col, row, ! computer.getColor());
					int value = getValue(level+1, stateGame);
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
		return stateGame;
	}

	public void setPlayer(IPlayer player) {
		this.computer = player;
	}
	
}
