// Copyright (C) 2010 Alexandre Garnier & Yann Treguer
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.

package fr.alma.go.heuristics;

import java.util.ArrayList;
import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;

public class GameTree {

	private GameNode root;

	private ArrayList<GameTree> sons;

	int[][] coeffs;

	private final static int INFINITE = Integer.MAX_VALUE;

	/**
	 * Get root's sons
	 * 
	 * @return List of root's sons
	 */
	public ArrayList<GameTree> getSons() {
		return sons;
	} // ArrayList<GameTree> getSons()

	/**
	 * Get root
	 * 
	 * @return Root
	 */
	public GameNode getRoot() {
		return root;
	} // GameNode getRoot()

	/**
	 * Alpha-Beta Pruning
	 * 
	 * @param alpha
	 *            Alpha value
	 * @param beta
	 *            Beta value
	 * @param depth
	 *            Depth to apply pruning
	 * @return Min or max value, depending on depth
	 */
	public int alphaBeta(int alpha, int beta, int depth) {
		if (this.isLeaf()) {
			return root.getNote();
		} else if (depth % 2 == 0) {
			int note = -INFINITE;
			for (GameTree son : sons) {
				note = Math.max(note, son.alphaBeta(alpha, beta, depth + 1));
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
				note = Math.min(note, son.alphaBeta(alpha, beta, depth + 1));
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

	/**
	 * Alpha-Beta pruning on root
	 */
	private void alphaBetaPruning() {
		for (GameTree son : sons) {
			son.alphaBeta(-INFINITE, INFINITE, 1);
		} // for
	} // void alphaBetaPruning()

	/**
	 * Get a place to play
	 * 
	 * @return A random place among all top rated places
	 */
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

	/**
	 * Generate game tree for alpha-beta pruning
	 * 
	 * @param goban
	 *            Actual state of the goban
	 * @param color
	 *            CPU color
	 * @param depth
	 *            Depth of generated tree
	 */
	private void generateTree(Goban goban, boolean color, int depth) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				Goban tmp = (Goban) goban.clone();
				if (color ? tmp.play(i, j, 'b') : tmp.play(i, j, 'w')) {
					GameTree son = new GameTree();
					son.root.setCoords(i, j);
					son.root.setNote(coeffs[i][j]);
					if (depth > 0) {
						son.generateTree(tmp, !color, depth - 1);
					} // if
					sons.add(son);
				} // if
			} // for
		} // for
	} // void generateTree(Goban,boolean,int)

	/**
	 * True if tree is leaf
	 * 
	 * @return True if tree has no son
	 */
	private boolean isLeaf() {
		return sons.isEmpty();
	} // boolean isLeaf()

	/**
	 * Default Constructor
	 */
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

	/**
	 * Get place by applying alpha-beta pruning
	 * 
	 * @param goban
	 *            Actual state of the goban
	 * @param color
	 *            CPU color
	 * @return Place to play
	 */
	public Place alphaBetaGetPlace(Goban goban, boolean color) {
		this.generateTree(goban, color, 1); // Beyond 1, you can take a coffee
		// this.print("", true);
		this.alphaBetaPruning();
		// this.print("", true);
		return this.pickPlace();
	} // Place alphaBetaGetPlace(Goban)

} // class GameTree
