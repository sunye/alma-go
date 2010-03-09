package fr.alma.server.core;

public class Coordinator {

	
	private PlayListener playListener;
	private IPlayer player1;
	private IPlayer player2;
	private IPlayer playerCurrent = null;
	private Rule rule = null;
	private Thread thread;
	private Runnable runnable;
	
	
	public Coordinator(Rule rule) {
		this.rule = rule;
	}

	
	public void startGame() {
		setPlayerCurrent(getPlayer(rule.getColorFirstPlayer()));
		thread = new Thread(getRunnable());
		thread.start();
	}
	

	public void setPlayers(IPlayer player1, IPlayer player2) {
		this.player1 = player1;
		this.player2 = player2;
		
		player2.addPlayListener(getPlayListener());
	}

	
	public IPlayer getPlayer(boolean color) {
		return player1.getColor() == color ? player1 : player2;		
	}
	
	
	public PlayListener getPlayListener() {
		playListener = new PlayListener() {
			@Override
			public boolean actionPerformed(PlayEvent e) {
				System.out.println("Le playeur vient de jouer ...");
				return true;
			}
		};
		
		return playListener;
	}

	
	public IPlayer getPlayerCurrent() {
		return playerCurrent;
	}

	
	public void setPlayerCurrent(IPlayer playerCurrent) {
		this.playerCurrent = playerCurrent;
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
