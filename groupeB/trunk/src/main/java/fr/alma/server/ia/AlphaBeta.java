package fr.alma.server.ia;

import java.awt.event.ActionEvent;

import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Emplacement;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.GameListener;
import fr.alma.server.rule.RuleManager;
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
	private IStateGame stateGameRef;
	private IStateGame stateGame;
	private RuleManager ruleManager;
	private GameListener gameListener;
	private StatusCheck status = new StatusCheck();

	private IPlayer computer;
	
	/* Interet de les avoir en global : toujours disponibes ! */
	private short rowMax = -1, colMax = -1;
	int cpt = 0;
	boolean gameOver = false;
	
	public AlphaBeta(Coordinator coordinator, IPlayer computer) {
		this.stateGameRef = coordinator.getStateGame();
		this.computer = computer;
		this.ruleManager = coordinator.getRuleManager();
		
		evaluation = new Evaluation(coordinator, computer);
		
		gameListener = new GameListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameOver = true;
			}
		};
	}
	
	short cptCol = 0;
	short cptRow = 0;
	
	public IEmplacement getEmplacementMax() {
		cpt = 0;
		stateGame = (IStateGame)stateGameRef.clone();
		int max = getValue(2, stateGame, 0);
		System.out.println("nb appels a gatValue() : " + cpt);
		return new Emplacement(colMax, rowMax);
	}
	
	/*
	 * levelMax  : niveau correspondant aux feuilles
	 * level = 1 : racine de l'arbre (etat courant du jeu)
	 * level = 2 : prochain coups possibles pour l'ordinateur
	 * level = 3 : prochain coups possibles pour le joueur
	 * level = 4 : coups suivants pour l'ordinateur ...etc.
	 * 
	 * => On commence avec un level a 2
	 */
	public int getValue(int level, IStateGame stateGame, int extremum) {
		int value;
		cpt++;
		
		//System.out.println("Nombre de place prises : " + stateGame.countLocationOccupied());
		if ((level < Configuration.getMaxDeepLevel()) && ! status.isGameOver()) {
			/* Test nombre pair */
			if (level%2 == 0) {
				/* Recherche du Max */
				value = max(level, stateGame, extremum);
			} else { /* Niveau impaire, c'est à l'adversaire de jouer */
				/* Recherche du Min */
				value = min(level, stateGame, extremum);
			}
		} else { /* On se trouve sur une feuille */
			value = evaluation.evaluate(stateGame, status);
		}
		return value;
	}
	
	
	/*
	 * Recherche du Max
	 */
	private int max(int level, IStateGame stateGame, int extremum) {
		int max = Integer.MIN_VALUE;
		
		for (short col = 0; col < stateGame.getMaxCol(); col++) {
			for (short row = 0; row < stateGame.getMaxRow(); row++) {
				status = ruleManager.check(new Emplacement(col, row));
				if ((max < extremum) && status.isCanPlay()) {
					stateGame.play(col, row, computer.getColor());
					int value = getValue(level+1, stateGame, max);
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
		
		for (short col = 0; col < stateGame.getMaxCol(); col++) {
			for (short row = 0; row < stateGame.getMaxRow(); row++) {
				status = ruleManager.check(new Emplacement(col, row));
				if ((min > extremum) && status.isCanPlay()) {
					stateGame.play(col, row, ! computer.getColor());
					int value = getValue(level+1, stateGame, min);
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
		return stateGameRef;
	}

	public void setPlayer(IPlayer player) {
		this.computer = player;
	}
	
}
