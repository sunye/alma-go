package fr.alma.server.rule;

import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;

public class StatusCheck {
	boolean canPlay = true;
	boolean gameOver = false;
	IPlayer winner = null;
	IEmplacement emplacement = null;
	
	
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
	
	public IEmplacement getEmplacement() {
		return emplacement;
	}
	
	public void setEmplacement(IEmplacement emplacement) {
		this.emplacement = emplacement;
	}
}
