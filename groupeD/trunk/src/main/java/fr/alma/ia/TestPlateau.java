package fr.alma.ia;

import fr.alma.atarigo.Pion;
import fr.alma.atarigo.Plateau;
import fr.alma.atarigo.Position;


public class TestPlateau {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Plateau plateau = new Plateau(9, 9);
		System.out.println("lignes   : " + plateau.lireLignes());
		System.out.println("Colonnes : " + plateau.lireColonnes());
		Position positionInvalide = new Position(-2, 3);
		System.out.println(positionInvalide.toString() + 
				   " valide ? " +
				   plateau.estValide(positionInvalide));
		Position positionValide = new Position(1, 1);
		System.out.println(positionValide.toString() + 
				   " valide ? " +
				   plateau.estValide(positionValide));
		System.out.println("Contenu du plateau :\n" +
				   plateau.toString());
		plateau.ecrireCase(positionValide, Pion.NOIR);
		System.out.println("Nouveau contenu du plateau :\n" +
				   plateau.toString());	
		
		Arbre jeu = new Arbre(plateau);

		jeu.genererArbre(jeu, Pion.BLANC, 1);
		
		PlateauValue plv = new PlateauValue(0);
		MinMax.init();
		plv = MinMax.calculer(jeu,Pion.BLANC,0);
	
		System.out.println("etat du jeu = "+plv.evaluation_);
		System.out.println("Pion joue par l'ordinateur :\n" +MinMax.meilleur_coup.plateau_);
	}

}
