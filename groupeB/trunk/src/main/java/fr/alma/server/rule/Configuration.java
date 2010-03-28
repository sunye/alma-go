package fr.alma.server.rule;


public class Configuration {

	
	public static final Boolean WHITE = true;
	public static final Boolean BLACK = false;
	
	
	public static boolean getColorFirstPlayer() {
		return BLACK;
	}
	
	
	public static int getMaxDeepLevel(int sizeGoban) {
		if (sizeGoban == 6) {
			return 6;
		} else {
			return 5;
		}
	}
	
}
