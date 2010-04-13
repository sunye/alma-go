package fr.alma.go.player;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.heuristics.Beginner;
import fr.alma.go.heuristics.Expert;
import fr.alma.go.heuristics.Medium;
import fr.alma.go.interfaces.IHeuristics;
import fr.alma.go.interfaces.IPlayer;

public class Computer implements IPlayer {

	private boolean color;

	private IHeuristics level;

	public Computer(boolean col, int lvl) {
		color = col;
		switch (lvl) {
		case 1:
			level = new Beginner();
			break;
		case 2:
			level = new Medium();
			break;
		case 3:
			level = new Expert();
			break;
		default:
			level = null;
			System.err.println("Error : inexistant level.");
		} // switch
	} // Computer()

	@Override
	public Place getPlace(Goban goban) {
		return level.getBestPlace(goban,color);
	} // Place getPlace()

}
