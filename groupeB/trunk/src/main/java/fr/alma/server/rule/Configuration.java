/*
 *   
 *   IA Project ATARI-GO
 *   UNIVERSITY OF NANTES
 *   MASTER ALMA 1
 *   2009 - 2010
 * 	 Version 1.0
 *   
 *   $Date$
 *   $Author$
 * 	 $Revision$
 * 
 *   Copyright (C) 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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
	public static boolean isColorFirstPlayer() {
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
