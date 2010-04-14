package fr.alma.go.heuristics;

import java.util.ArrayList;
import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;

public class GameTree {

	private GameNode root;

	private ArrayList<GameTree> sons;

	int[][] coeffs;

	private final static int INFINITE = Integer.MAX_VALUE;

	public ArrayList<GameTree> getSons() {
		return sons;
	} // ArrayList<GameTree> getSons()

	private GameNode getRoot() {
		return root;
	} // GameNode getRoot()

	private int alphaBeta(int alpha, int beta, int deepness) {
		if (this.isLeaf()) {
			return root.getNote();
		} else if (deepness % 2 == 0) {
			int note = -INFINITE;
			for (GameTree son : sons) {
				note = Math.max(note, son.alphaBeta(alpha, beta, deepness + 1));
				if (note >= beta) {
					root.setNote(note + note);
					return root.getNote();
				}
				alpha = Math.max(alpha, note);
			} // for
			root.setNote(root.getNote() + note);
			return root.getNote();
		} else {
			int note = INFINITE;
			for (GameTree son : sons) {
				note = Math.min(note, son.alphaBeta(alpha, beta, deepness + 1));
				if (note <= alpha) {
					root.setNote(note + note);
					return root.getNote();
				}
				beta = Math.min(beta, note);
			} // for
			root.setNote(root.getNote() + note);
			return root.getNote();
		} // if
	} // int alphaBeta(int,int,int)

	private void alphaBetaPruning() {
		for (GameTree son : sons) {
			son.alphaBeta(-INFINITE, INFINITE, 1);
		} // for
	} // void alphaBetaPruning()

	private Place pickPlace() {
		int max = -INFINITE;
		ArrayList<Place> places = new ArrayList<Place>();
		for (GameTree son : sons) {
			int note = son.getRoot().getNote();
			if (note >= max) {
				if (note > max) {
					max = note;
					places.clear();
				} // if
				places.add(son.getRoot().getPlace());
			} // if
		} // for
		return places.get((int) (Math.random() * places.size()));
	} // Place pickPlace()

	private void generateTree(Goban goban, boolean color, int deepness) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Goban tmp = (Goban) goban.clone();
				if (color ? tmp.play(i, j, 'b') : tmp.play(i, j, 'w')) {
					GameTree son = new GameTree();
					son.root.setCoords(i, j);
					son.root.setNote(coeffs[i][j]);
					if (deepness > 0) {
						son.generateTree(tmp, !color, deepness - 1);
					} // if
					sons.add(son);
				} // if
			} // for
		} // for
	} // void generateTree(Goban,boolean,int)

	private boolean isLeaf() {
		return sons.isEmpty();
	} // boolean isLeaf()

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

	public Place alphaBetaGetPlace(Goban goban, boolean color) {
		this.generateTree(goban, color, 1); // Beyond 1, you can take a coffee
		// this.print("", true);
		this.alphaBetaPruning();
		// this.print("", true);
		return this.pickPlace();
	} // Place alphaBetaGetPlace(Goban)

//	private void print(String prefix, boolean first) {
//		System.out.println(prefix + root.getNote());
//		for (GameTree son : sons) {
//			son.print(prefix + "| ", false);
//			if (first)
//				break;
//		} // for
//	} // void print(String)

} // class GameTree
