package fr.alma.atarigo;

/**
 * Classe implémentant le type de joueur IA
 * @author 
 *
 */
public class IAPlayer extends Player{

	public Position bestMove;
	
	public IAPlayer(Stone color, String name) {
		super(color, name);
		// TODO Auto-generated constructor stub
	}

	public boolean isHuman(){
		return false;
	}
	
	public Position playBestShot(){
		return bestMove;
	}
	
	
}
