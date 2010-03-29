package fr.alma.server.core;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import fr.alma.client.action.Context;
import fr.alma.client.ihm.Goban;
import fr.alma.common.ui.Tools;
import fr.alma.server.ia.FreedomDegrees;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;

public class Coordinator implements ICoordinator {
	
	private PlayListener playListener;
	private IPlayer currentPlayer = null;
	private Runnable runnable;
	private Context context = null;
	
	
	public Coordinator(Context context) {
		this.context = context;
	}

	
	public void startGame() {
		getPlayer().addPlayListener(getPlayListener());
		getComputer().addPlayListener(getPlayListener());
		setCurrentPlayer(getPlayer(Configuration.getColorFirstPlayer()));
		playInThread();
	}
	

	
	public IPlayer getPlayer(boolean color) {
		return getPlayer().getColor() == color ? getPlayer() : getComputer();
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
						
						return getRuleManager().checkBefore(getStateGame(), e.getEmplacement(), e.getPlayer()).isCanPlay();
					}
					
					/* AFTER */
					getGoban().repaint();
					
					/* Control if the game is over */
					if (getRuleManager().checkAfter(getStateGame(), e.getEmplacement(), getCurrentPlayer()).isGameOver()) {
						FreedomDegrees.showGobanOnConsole(getStateGame());
						Tools.message(context.getMainFrame(), "Game over", "Winner is : " + getCurrentPlayer().getName(), JOptionPane.INFORMATION_MESSAGE);
						System.out.println("Game over - winner is : " + getCurrentPlayer().getName());
						getPlayer().setEnabled(false);
						getComputer().setEnabled(false);
						return false;
					}
					
					/* Change the current player and play again */
					if (getCurrentPlayer() == getPlayer()) {
						setCurrentPlayer(getComputer());
					} else {
						setCurrentPlayer(getPlayer());
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

	
	/* (non-Javadoc)
	 * @see fr.alma.server.core.IGenerator#getComputer()
	 */
	public IPlayer getComputer() {
		return context.getComputer();
	}
	
	
	/* (non-Javadoc)
	 * @see fr.alma.server.core.IGenerator#getPlayer()
	 */
	public IPlayer getPlayer() {
		return context.getPlayer();
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


	/* (non-Javadoc)
	 * @see fr.alma.server.core.IGenerator#getRuleManager()
	 */
	public RuleManager getRuleManager() {
		return context.getRuleManager();
	}


	/* (non-Javadoc)
	 * @see fr.alma.server.core.IGenerator#getStateGame()
	 */
	public IStateGame getStateGame() {
		return context.getStateGame();
	}
	
	public Goban getGoban() {
		return context.getGoban();
	}
	
	
	@Override
	public void cleanUp() {
		getPlayer().removePlayListeners();
		getComputer().removePlayListeners();
		playListener = null;
		currentPlayer = null;
		runnable = null;
	}

}
