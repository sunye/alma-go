package fr.alma.atarigo;

/**
 * HumanPlayer.java represent a human player
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 *
 */
public class HumanPlayer extends Player{

	public HumanPlayer(Stone color, String name) {
		super(color, name);
		// TODO Auto-generated constructor stub
	}

	public boolean isHuman(){
		return true;
	}

}
