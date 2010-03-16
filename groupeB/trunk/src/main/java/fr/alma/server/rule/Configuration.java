package fr.alma.server.rule;

import fr.alma.server.core.IPlayer;

public class Configuration {

	private static final int LEVEL_MAX = 5;
	
	public boolean getColorFirstPlayer() {
		return IPlayer.BLACK;
	}
	
	public static int getMaxDeepLevel() {
		return LEVEL_MAX;
	}
	
}
