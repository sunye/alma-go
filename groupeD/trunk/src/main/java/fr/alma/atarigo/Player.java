package fr.alma.atarigo;

/**
 * Player.java is an abstract class representing a type of player.
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 * @version 1.0
 */
public abstract class Player {

	/** Color of the player (black or white) */
	public Stone color;
	/** Name of the player (not used finally) */
	public String name;

	/**
	 * Logic constructor
	 * @param color color of the stones played by the player
	 * @param name name of the player (not used yet)
	 */
	public Player(Stone color, String name){
		this.color = color;
		this.name = name;
	}
	
	/**
	 * verify if the player is human
	 */
	public abstract boolean isHuman();

	/**
	 * Difficulty of a human player is 0. only usefull for AI @see IAPlayer
	 * @return 0
	 */
	public int getDifficulty() {
		// TODO Auto-generated method stub
		return 0;
	}

}
