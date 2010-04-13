package fr.alma.go.heuristics;

import java.util.ArrayList;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.goban.Stone;

public class GameTree {

	private GameNode root;

	private ArrayList<GameTree> sons;

	int[][] coeffs;

	public GameTree() {
		root = new GameNode();
		sons = new ArrayList<GameTree>();
		int[][] cfs = { { 0, 1, 1, 1, 1, 1, 1, 1, 0 },
				{ 1, 1, 2, 2, 2, 2, 2, 1, 1 }, { 1, 2, 3, 3, 2, 3, 3, 2, 1 },
				{ 1, 2, 3, 2, 2, 2, 3, 2, 1 }, { 1, 2, 2, 2, 1, 2, 2, 2, 1 },
				{ 1, 2, 3, 2, 2, 2, 3, 2, 1 }, { 1, 2, 3, 3, 2, 3, 3, 2, 1 },
				{ 1, 1, 2, 2, 2, 2, 2, 1, 1 }, { 0, 1, 1, 1, 1, 1, 1, 1, 0 } };
		coeffs = cfs;
	} // GameTree()

	public ArrayList<GameTree> getSons() {
		return sons;
	} // ArrayList<GameTree> getSons()

	public GameNode getRoot() {
		return root;
	} // GameNode getRoot()

	private void alphaBetaPruning(Goban goban, boolean color, int deep) {
		
	}

	public Place alphaBetaGetPlace(Goban goban, boolean color) {
		this.alphaBetaPruning(goban, color, 0);
		return root.getPlace();
	} // Place alphaBetaPruning(Goban)

} // class GameTree
