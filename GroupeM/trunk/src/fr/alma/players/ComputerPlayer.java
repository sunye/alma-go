package fr.alma.players;

import fr.alma.goban.Color;
import fr.alma.goban.Goban;
import fr.alma.goban.Place;
import fr.alma.interfaces.IPlayer;

public class ComputerPlayer implements IPlayer {

	private Goban goban;
	private Color color;

	private Place bestPlace() { // TODO Here is the call to the heuristic
		return null;
	}

	public ComputerPlayer(Goban gb) {
		goban = gb;
	}

	public boolean play() {
		Place place = bestPlace();
		return goban.play(place, color.toString());
	}

}
