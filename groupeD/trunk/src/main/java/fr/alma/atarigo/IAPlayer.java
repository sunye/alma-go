package fr.alma.atarigo;

/**
 * AIPlayer represent a AI player.
 * @author 
 *
 */
public class IAPlayer extends Player{

	public Position bestMove;
	private int difficulty;
	
	public IAPlayer(Stone color, String name,int dif) {
		super(color, name);
		difficulty = dif;
		// TODO Auto-generated constructor stub
	}

	public boolean isHuman(){
		return false;
	}
	
	public Position playBestShot(){
		return bestMove;
	}
	
	public int getDifficulty(){
		return difficulty;
	}
	
}
