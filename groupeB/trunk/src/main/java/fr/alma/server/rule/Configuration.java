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


public class Configuration {

	
	public static final Boolean WHITE = true;
	public static final Boolean BLACK = false;
	
	
	public static boolean getColorFirstPlayer() {
		return BLACK;
	}
	
	
	public static int getMaxDeepLevel(int sizeGoban) {
		if (sizeGoban == 6) {
			return 7;
		} else {
			return 6;
		}
	}
	
}
