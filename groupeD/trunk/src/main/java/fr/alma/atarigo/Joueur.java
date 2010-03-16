package fr.alma.atarigo;

/**
 * classe abstraite implémentant un type de joueur
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 *
 */
public abstract class Joueur {

	public Pion couleur;
	public String nom;

	/**
	 * 
	 * @param couleur couleur du joueur
	 * @param nom nom du joueur (pas encore utilisé)
	 */
	public Joueur(Pion couleur, String nom){
		this.couleur = couleur;
		this.nom = nom;
	}
	
	/**
	 * vérifie si le joueur est humain
	 */
	public abstract boolean estHumain();

}
