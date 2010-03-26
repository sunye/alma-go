package fr.alma.server.core;


import fr.alma.client.ihm.Goban;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;

public class Coordinator {
	
	private PlayListener playListener;
	private IPlayer player;
	private IPlayer computer;
	private IPlayer currentPlayer = null;
	private Runnable runnable;
	private Goban goban = null;
	private IStateGame stateGame;
	private RuleManager ruleManager = null;

	
	public Coordinator(Goban goban, IStateGame stateGame, RuleManager ruleManager) {
		this.goban = goban;
		this.stateGame = stateGame;
		this.ruleManager = ruleManager;
	}

	
	public void startGame() {
		setCurrentPlayer(getPlayer(Configuration.getColorFirstPlayer()));
		playInThread();
	}
	

	public void setPlayers(IPlayer player, IPlayer computer) {
		this.player = player;
		this.computer = computer;
		
		player.addPlayListener(getPlayListener());
		computer.addPlayListener(getPlayListener());
	}

	
	public IPlayer getPlayer(boolean color) {
		return player.getColor() == color ? player : computer;
	}
	
	
	public PlayListener getPlayListener() {
		if (playListener == null) {
			playListener = new PlayListener() {
				@Override
				public boolean actionPerformed(PlayEvent e) {
					if (e.getWhen() == PlayEvent.BEFORE) {
						//System.out.println("Le playeur veut jouer ..." + e.getPlayer());
						/* Must Verify the rules */
						if (getCurrentPlayer() != e.getPlayer())
							return false;
						
						return getRuleManager().checkBefore(stateGame, e.getEmplacement(), e.getPlayer()).isCanPlay();
					}
					
					/* AFTER */
					goban.repaint();
					
					/* Control if the game is over */
					if (getRuleManager().checkAfter(stateGame, e.getEmplacement(), getCurrentPlayer()).isGameOver()) {
						System.out.println("Game over - winner is : " + getCurrentPlayer().getName());
						getPlayer().setEnabled(false);
						getComputer().setEnabled(false);
						return false;
					}
					
					/* Change the current player and play again */
					if (getCurrentPlayer() == player) {
						setCurrentPlayer(computer);
					} else {
						setCurrentPlayer(player);
					}
					
					/*
					 * when the current player is the computer, the treatments
					 * are realize with a thread -> be carrefull !
					 */
					getCurrentPlayer().play();

					return true;
				}
			};
		}
		return playListener;
	}

	
	public IPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	
	public IPlayer getComputer() {
		return computer;
	}
	
	
	public IPlayer getPlayer() {
		return player;
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
