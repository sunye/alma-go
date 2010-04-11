package fr.alma.players;

import fr.alma.goban.Color;
import fr.alma.goban.Goban;
import fr.alma.goban.Place;
import fr.alma.interfaces.IPlayer;

public class HumanPlayer implements IPlayer {

	private Goban goban;
	private Color color;

	private Place askPlace() { // TODO À adapter selon IHM
		return null;
	}

	public HumanPlayer(Goban gb) {
		goban = gb;
	}

	public boolean play() {
		Place place=askPlace();
		return goban.play(place, color.toString());
	}
	
}
