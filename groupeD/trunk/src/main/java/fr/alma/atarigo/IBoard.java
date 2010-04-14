package fr.alma.atarigo;

import java.util.ArrayList;

public interface IBoard {

	/**
	 * remove all the stones from the board for a new game.
	 */
	public abstract void newGame();

	/**
	 * accessor for lines
	 */
	public abstract int getLines();

	/**
	 * accessor for columns
	 */
	public abstract int getColumns();

	/**
	 * indicate if the position belongs to the board.
	 * @param position tested position
	 */
	public abstract boolean isValid(Position position);

	/**
	 * Find all cells of a given color
	 * @param stone
	 * @return a list of the stones
	 */
	public abstract ArrayList<Position> getCells(Stone stone);

	/**
	 * Compares two goban and find the difference
	 * @param goban
	 * @return the position of the difference, if there's one, or returns an invalid position.
	 */
	public abstract Position getDifference(Goban goban);

	public abstract boolean canPlay(Stone color);

}