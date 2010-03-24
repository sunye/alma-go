package fr.alma.atarigo;

/**
 * classe abstraite implémentant un type de joueur
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 *
 */
public abstract class Player {

	public Stone color;
	public String name;

	/**
	 * 
	 * @param color couleur du joueur
	 * @param name nom du joueur (pas encore utilisé)
	 */
	public Player(Stone color, String name){
		this.color = color;
		this.name = name;
	}
	
	/**
	 * vérifie si le joueur est humain
	 */
	public abstract boolean isHuman();

}
