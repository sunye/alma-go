package fr.alma.atarigo;

/**
 * AIPlayer represent a AI player.
 * @author 
 * @version 1.0
 */
public class IAPlayer extends Player{

	/** the bestmove kept by an AI player. */
	public Position bestMove;
	/** the depth that the AI player can go in the search tree. */
	private int difficulty;
	
	/**
	 * Logic constructor.
	 * @param color Color of the player (black or white)
	 * @param name Name of the player (not used finally).
	 * @param dif Difficulty of the player @see difficulty
	 */
	public IAPlayer(Stone color, String name,int dif) {
		super(color, name);
		difficulty = dif;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Return if the player is human type.
	 */
	public boolean isHuman(){
		return false;
	}
	
	/**
	 * An AI player can keep the best move to play. This method return this move.
	 * @return
	 */
	public Position playBestShot(){
		return bestMove;
	}
	
	
	/**
	 * Return the depth of the AI search in the search tree. Can be seen like a difficulty.
	 */
	public int getDifficulty(){
		return difficulty;
	}
	
}
