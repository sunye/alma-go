package fr.alma.atarigo;

/**
 * Classe implémentant le type de joueur humain
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 *
 */
public class JoueurHumain extends Joueur{

	public JoueurHumain(Pion couleur, String nom) {
		super(couleur, nom);
		// TODO Auto-generated constructor stub
	}

	public boolean estHumain(){
		return true;
	}

}
