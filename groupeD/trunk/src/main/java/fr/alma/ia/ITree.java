package fr.alma.ia;

import java.util.ArrayList;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Stone;

public interface ITree {

	/**
	 * Add a child in last position to the current tree-node
	 * @param child the node to add as a child
	 */
	public abstract void addChild(Tree child);

	/**
	 * Test if the tree-node is a leaf
	 * @return true if the node is a leaf
	 */
	public abstract boolean isLeaf();

	/**
	 * Returns the parent of the node
	 * @return a node or "null"
	 */
	public abstract Tree getParent();

	/**
	 * Returns the Goban associated with the node
	 * @return a Goban
	 */
	public abstract Goban getGoban();

	/**
	 * Return the children of the node
	 * @return a LinkedList<Tree>
	 */
	public abstract ArrayList<Tree> getChildren();

	/**
	 * Generate all the possible movements from the current Goban and add them as children
	 * @param stone the color to be played
	 */
	public abstract void generateChildren(AtariGo atariGo, Stone stone,
			Stone currentPlayer);

	/**
	 * Return the depth of the node
	 * @return an integer>=0
	 */
	public abstract int getDepht();

	public abstract Goban getMove();

}