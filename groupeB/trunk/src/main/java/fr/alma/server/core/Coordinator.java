package fr.alma.server.core;

import fr.alma.client.ihm.Goban;
import fr.alma.server.rule.Rule;
import fr.alma.server.rule.RuleAlreadyBusy;
import fr.alma.server.rule.RuleManager;

public class Coordinator {
	
	private PlayListener playListener;
	private IPlayer player1;
	private IPlayer player2;
	private IPlayer currentPlayer = null;
	private Rule rule = null;
	private Thread thread;
	private Runnable runnable;
	private Goban goban = null;
	
	private RuleManager ruleManager = null;
	
	
	public Coordinator(Rule rule, Goban goban) {
		this.rule = rule;
		this.goban = goban;
		
		// A mettre dans un factory
		//ruleManager = new RuleManager();
		//ruleManager.addRule(new RuleAlreadyBusy(game))
		
	}

	
	public void startGame() {
		setCurrentPlayer(getPlayer(rule.getColorFirstPlayer()));
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
						System.out.println("Le playeur veut jouer ..." + e.getPlayer());
					}
					
					System.out.println("Le playeur vient de jouer ..." + e.getPlayer());
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
}
