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

/**
 * Define the event play
 */
public class PlayEvent {

	public static final int BEFORE = 0;
	public static final int AFTER = 1;
	
	private IPlayer player = null;
	private int when;
	private ILocation emplacement;
	
	
	public PlayEvent(IPlayer player, int when, ILocation emplacement) {
		super();
		setPlayer(player);
		setWhen(when);
		setEmplacement(emplacement);
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
		return "Player : " + player.toString() + " When : " + ((when == BEFORE) ? "BEFORE" : "AFTER");
	}


	public ILocation getEmplacement() {
		return emplacement;
	}


	public void setEmplacement(ILocation emplacement) {
		this.emplacement = emplacement;
	}
	
}
