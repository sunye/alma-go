package fr.alma.go.player;

import fr.alma.go.interfaces.IPlayer;

public class Human implements IPlayer {

	private char color;

	public Human(char col) {
		color = col;
	}

	@Override
	public int getPlace() {
		/*
		 * TODO Here is some kinda user interface waiting for player's turn
		 */
		return 0;
	}

}
