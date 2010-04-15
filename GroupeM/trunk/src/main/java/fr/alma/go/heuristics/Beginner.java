package fr.alma.go.heuristics;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.interfaces.IHeuristics;

public class Beginner implements IHeuristics {

	public Place getBestPlace(Goban goban, boolean color) {
		GameTree tree = new GameTree();
		return tree.alphaBetaGetPlace(goban,color);
	} // Place getBestPlace()

} // class Beginner
