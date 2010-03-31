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
