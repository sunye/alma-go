package fr.alma.atarigo;

/**
 * Player.java is an abstract class representing a type of player.
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 *
 */
public abstract class Player {

	public Stone color;
	public String name;

	/**
	 * 
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

	public int getDifficulty() {
		// TODO Auto-generated method stub
		return 0;
	}

}
