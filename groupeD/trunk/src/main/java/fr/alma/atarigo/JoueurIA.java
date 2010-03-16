package fr.alma.atarigo;

/**
 * Classe implémentant le type de joueur IA
 * @author 
 *
 */
public class JoueurIA extends Joueur{

	public Position meilleurCoup;
	
	public JoueurIA(Pion couleur, String nom) {
		super(couleur, nom);
		// TODO Auto-generated constructor stub
	}

	public boolean estHumain(){
		return false;
	}
	
	public Position jouerMeilleurCoup(){
		return meilleurCoup;
	}
	
	
}
