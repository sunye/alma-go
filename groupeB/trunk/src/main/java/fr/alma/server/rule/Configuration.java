/*
 * IA Project ATARI-GO
 * UNIVERSITY OF NANTES
 * MASTER ALMA 1
 * 2009 - 2010
 * Version 1.0
 * @author Romain Gournay & Bruno Belin
 * 
 * Copyright 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * Use is subject to license terms.
 */
package fr.alma.server.rule;

/**
 *	Some configuration
 */
public class Configuration {

	
	public static final Boolean WHITE = true;
	public static final Boolean BLACK = false;
	
	/**
	 * @return the color which begin the game
	 */
	public static boolean getColorFirstPlayer() {
		return BLACK;
	}
	
	/**
	 * @param sizeGoban
	 * @return the depth of the tree for the computer calculation
	 */
	public static int getMaxDeepLevel(int sizeGoban) {
		if (sizeGoban == 6) {
			return 6;
		} else {
			return 5;
		}
	}
	
}
