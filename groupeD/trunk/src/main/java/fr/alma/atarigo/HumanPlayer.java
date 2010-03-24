package fr.alma.atarigo;

/**
 * Classe implémentant le type de joueur humain
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
