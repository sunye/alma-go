package fr.alma.atarigo;

/**
 * HumanPlayer.java represent a human player
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 * @version 1.0
 */
public class HumanPlayer extends Player{

	/**
	 * Logic contructor
	 * @param color Color played
	 * @param name Name of the player (not used finally)
	 */
	public HumanPlayer(Stone color, String name) {
		super(color, name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * return if the player is human type
	 */
	public boolean isHuman(){
		return true;
	}

}
