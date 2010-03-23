package fr.alma.modele.intelligence;

import fr.alma.modele.Coordonnee;
import fr.alma.modele.CouleurPion;
import fr.alma.modele.Coup;
import fr.alma.modele.GoBan;
import fr.alma.modele.Pion;
import fr.alma.modele.arbres.Arbre;
import fr.alma.modele.arbres.ArbreAdj;
import fr.alma.modele.arbres.Node;




/**
 * Class qui gère l'intelligence articificiel
 * 
 */
public class SunTsu {

	private CouleurPion coul;
	private Difficulte diff;
	//FIXME discuter avec Bambinome pour savoir si on représente des matrice de pion 
	//ou des go ban, voir eventuellement une troisième classe plus complete (pour gestion des groupes etc).
	private Arbre<Coup> bibliotheque;
	private GoBan situation;
	
	public SunTsu(){
		
		
	}
	

	
	private Coordonnee coupJouer(GoBan situation){
		bibliotheque= new ArbreAdj<Coup>(81, "Itinial", new Coup(null, null, CouleurPion.EMPTY));
		this.situation=situation;
		//XXX voir peut être à conserver une partie de l'arbre après chaque coup
		//Mais ça c'est de l'optimisation.
		//diff.ordinal()*5;
		
		constructionArbre(6,bibliotheque.racine());
		
		
		/*
		 * -Construction de l'arbre
		 * -evaluation
		 */
		
		
		return  null;
	}
	
	private void evaluation(){
		/*
		 *  
		 * 
		 * 
		 */
		
		
	}
	

	private void constructionArbre(int profondeur, Node<Coup> noeud){
		try{
			int compteurFils=1;
			Pion[][] matrice=situation.getGoban();
			for(int i=0; i<9;i++){
				for(int j=0; j<9;j++){
					//le modulo 2 indique si on est en impair ou pair, donc la couleur du joueur
					//so impair c'est le joueur pair l'autre.
					CouleurPion couleurPion=profondeur%2==1?CouleurPion.BLANC:CouleurPion.NOIR;
					if(matrice[i][j]==null&& situation.estLegal(i, j, couleurPion)){
						/*
						 * On ajoute le pion si la case est vide et que le coup est légal
						 * on ajoute le pion et on enregistre le coup dans l'arbre
						 * si on arrive à la dernière profondeur on s'arrête et en remontant on supprime le coup 
						 * de l'arbre
						 * sinon on descend dans l'arbre.
						 */
						situation.ajouterPion(i, j,coul);						
						Coup coup= new Coup(i, j, couleurPion);
						Node<Coup> node=bibliotheque.getNewNode();
						node.setValeur(coup);
						node.setEtiquette("feuille");
						bibliotheque.ajouterFils(noeud, node, compteurFils);
						compteurFils++;
						if(profondeur>1){
							constructionArbre(profondeur-1, node);
						}
						situation.retirerPion(i, j, coul);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
}
