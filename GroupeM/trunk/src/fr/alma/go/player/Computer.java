package fr.alma.go.player;

import fr.alma.go.interfaces.IPlayer;

public class Computer implements IPlayer {

	private char color;

	private int level;

	public Computer(char col, int lvl) {
		color = col;
		level = lvl;
	}

	@Override
	public int getPlace() {
		/*
		 * TODO Here is the call to the so called AI
		 */
		return 0;
	}

}
