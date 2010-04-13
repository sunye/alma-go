package fr.alma.go.interfaces;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;

public interface IHeuristics {

	public Place getBestPlace(Goban goban, boolean color);
	
} // interface IHeuristics
