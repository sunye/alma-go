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

import fr.alma.server.core.ILocation;
import fr.alma.server.core.IPlayer;

/**
 * Different status of the Game
 */
public class StatusCheck {
	boolean canPlay = true;
	boolean gameOver = false;
	IPlayer winner = null;
	ILocation emplacement = null;
	
	
	public boolean isCanPlay() {
		return canPlay;
	}
	
	public void setCanPlay(boolean canPlay) {
		this.canPlay = canPlay;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public IPlayer getWinner() {
		return winner;
	}
	
	public void setWinner(IPlayer winner) {
		this.winner = winner;
	}
	
	public ILocation getEmplacement() {
		return emplacement;
	}
	
	public void setEmplacement(ILocation emplacement) {
		this.emplacement = emplacement;
	}
}
