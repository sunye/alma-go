package fr.alma.server.core;

public class PlayEvent {

	public static final int AVANT = 0;
	public static final int APRES = 1;
	
	
	private int when;

	public int getWhen() {
		return when;
	}

	
	public void setWhen(int when) {
		this.when = when;
	}
	
}
