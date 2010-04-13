package fr.alma.go.player;

import fr.alma.go.Place;
import fr.alma.go.heuristics.Beginner;
import fr.alma.go.heuristics.Expert;
import fr.alma.go.heuristics.Medium;
import fr.alma.go.interfaces.IHeuristics;
import fr.alma.go.interfaces.IPlayer;

public class Computer implements IPlayer {

	private char color;

	private IHeuristics level;

	public Computer(char col, int lvl) {
		color = col;
		switch (lvl) {
		case 1:
			level = new Beginner();
		case 2:
			level = new Medium();
		case 3:
			level = new Expert();
		default:
			level = null;
		} // switch
	} // Computer()

	@Override
	public Place getPlace() {
		return level.getBestPlace();
	} // Place getPlace()

}
