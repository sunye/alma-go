package fr.alma.atarigo;

/**
 * @deprecated
 * test le plateau
 * @author vincent
 *
 */
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
		plateau.ecrireCase(positionValide, Pion.BLANC);
		System.out.println("Nouveau contenu du plateau :\n" +
				   plateau.toString());	
		
		
	}

}
