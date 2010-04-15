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
package fr.alma.server.core;

import java.util.List;

import fr.alma.client.ihm.GoObject;
import fr.alma.server.ia.IEvaluation;

public interface IPlayer extends GoObject {
	
	/**
	 * @return the color that have a player
	 */
	Boolean getColor();
	
	
	/**
	 * Define all the actions when the player put a stone on the goban
	 */
	void play();
	
	/**
	 * OBSERVER PATTERN
	 * Define listeners on player actions
	 */
	boolean addPlayListener(PlayListener actionListener);
	
	/**
	 * Remove all listeners on player
	 */
	public void removePlayListeners();
	
	List<PlayListener> getPlayerListeners();
	
	/**
	 * @param enable false when the game is over
	 */
	void setEnabled(boolean enable);
	
	/**
	 * @param strategyGame
	 */
	public void setStrategy(IStrategy strategyGame);

	/**
	 * Interupt the calculation of the computer
	 */
	public void interrupt();
	
	public void setEvaluation(IEvaluation evaluation);
	
	public String getName();
}
