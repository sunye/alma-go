package fr.alma.server.rule;


public class Configuration {

	private static final int LEVEL_MAX = 6;
	
	public static final Boolean WHITE = true;
	public static final Boolean BLACK = false;
	
	public static final short LINE_H = 6;
	public static final short LINE_V = 6;	
	
	public static boolean getColorFirstPlayer() {
		return BLACK;
	}
	
	
	public static Boolean getColorPlayer() {
		return WHITE;
	}
	
	
	public static Boolean getColorComputer() {
		return BLACK;
	}
	
	public static int getMaxDeepLevel() {
		return LEVEL_MAX;
	}
	
}
