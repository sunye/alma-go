package fr.alma.server.core;

public class PlayEvent {

	public static final int AVANT = 0;
	public static final int APRES = 1;
	
	private IPlayer player = null;
	private int when;
	
	
	public PlayEvent(IPlayer player, int when) {
		super();
		setPlayer(player);
		setWhen(when);
	}
	

	public int getWhen() {
		return when;
	}

	
	public void setWhen(int when) {
		this.when = when;
	}


	public IPlayer getPlayer() {
		return player;
	}


	public void setPlayer(IPlayer player) {
		this.player = player;
	}
	
	public String toString() {
		return "Player : " + player.toString() + " When : " + ((when == AVANT) ? "AVANT" : "APRES");
	}
	
}
