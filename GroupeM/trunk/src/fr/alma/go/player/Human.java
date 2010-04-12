package fr.alma.go.player;

import fr.alma.go.interfaces.IPlayer;

public class Human implements IPlayer {

	private char color;

	public Human(char col) {
		color = col;
	}

	@Override
	public int getPlace() {
		// TODO Auto-generated method stub
		return 0;
	}

}
