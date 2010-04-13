package fr.alma.go.player;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.interfaces.IPlayer;
import fr.alma.go.ui.GUI;

public class Human implements IPlayer {

	private char color;

	public Human(char col) {
		color = col;
	}

	@Override
	public Place getPlace(Goban goban) {
		return GUI.getPlace(color);
	}

}
