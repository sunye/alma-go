package fr.alma.server.core;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import fr.alma.client.ihm.Goban;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;

public class Coordinator {
	
	private PlayListener playListener;
	private IPlayer player1;
	private IPlayer player2;
	private IPlayer currentPlayer = null;
	private Configuration config = null;
	private Thread thread;
	private Runnable runnable;
	private Goban goban = null;
	private IStateGame stateGame;
	private RuleManager ruleManager = null;
	private Coordinator coordinator = this;
	
	public Coordinator(Configuration config, Goban goban, IStateGame stateGame ) {
		this.config = config;
		this.goban = goban;
		this.stateGame = stateGame;
		ruleManager = Factory.getRuleManager(coordinator);
	}

	
	public void startGame() {
		setCurrentPlayer(getPlayer(config.getColorFirstPlayer()));
		playInThread();
	}
	

	public void setPlayers(IPlayer player1, IPlayer player2) {
		this.player1 = player1;
		this.player2 = player2;
		
		player1.addPlayListener(getPlayListener());
		player2.addPlayListener(getPlayListener());
	}

	
	public IPlayer getPlayer(boolean color) {
		return player1.getColor() == color ? player1 : player2;		
	}
	
	
	public PlayListener getPlayListener() {
		if (playListener == null) {
			playListener = new PlayListener() {
				@Override
				public boolean actionPerformed(PlayEvent e) {
					if (e.getWhen() == PlayEvent.AVANT) {
						//System.out.println("Le playeur veut jouer ..." + e.getPlayer());
						/* Must Verify the rules */
						return getRuleManager().check(e.getEmplacement()).isCanPlay();
					}
					
					//System.out.println("Le playeur vient de jouer ..." + e.getPlayer());
					if (getCurrentPlayer() == player1) {
						setCurrentPlayer(player2);
					} else {
						setCurrentPlayer(player1);
					}
					
					getCurrentPlayer().play();
					goban.repaint();
					
					return true;
				}
			};
		}
		return playListener;
	}

	
	public IPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	
	public void setCurrentPlayer(IPlayer playerCurrent) {
		this.currentPlayer = playerCurrent;
	}
	
	
	private void playInThread() {
		//thread = new Thread(getRunnable());
		//thread.start();
		getCurrentPlayer().play();
	}
	
	
	public Runnable getRunnable() {
		runnable = new Runnable() {
			@Override
			public void run() {
				/**
				 * Boucle infinie ---tester gameOver() !
				 */
				while (true) {
					
				}
			}
		};
		return runnable;
	}


	public RuleManager getRuleManager() {
		return ruleManager;
	}


	public IStateGame getStateGame() {
		return stateGame;
	}
}
