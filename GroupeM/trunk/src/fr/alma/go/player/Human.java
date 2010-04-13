package fr.alma.go.player;

import fr.alma.go.Place;
import fr.alma.go.interfaces.IPlayer;

public class Human implements IPlayer {

	private char color;

	public Human(char col) {
		color = col;
	}

	@Override
	public Place getPlace() {
		/*
		 * TODO Here is some kinda user interface waiting for player's turn
		 */
		return null;
	}

}
