package fr.alma.atarigo;

import fr.alma.atarigo.AtariGo.Move;

public interface IGame {

	/**
	 * Play a move and return the result. If necessary, calculates new groups and caught stones.
	 * @param player The player playing the move.
	 * @param position The position of the move.
	 * @return The type of the resulting move INVALID, WIN or NEUTRAL .
	 */
	public abstract Move playMove(Stone player, Position position);

	/**
	 * Tell if the current player can make a move. Usefull to know if the game is over or not.
	 * @param player The player whot got the turn.
	 */
	public abstract boolean canPlayMove(Stone player);

	/**
	 * return if the game is over or not
	 * @return le booleen fini
	 */
	public abstract boolean isOver();

	/**
	 * desactivate the game
	 */
	public abstract void shutDown();

	/**
	 * accessor for lines
	 */
	public abstract int getLines();

	/**
	 * accessor for columns
	 */
	public abstract int getColumns();

	/**
	 * prepare the game for a new game
	 */
	public abstract void newGame();

}