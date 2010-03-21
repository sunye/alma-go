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
	
	public SunTsu(){
		
		
	}
	

	
	private Coordonnee coupJouer(Pion[][] situationIniale){
		bibliotheque= new ArbreAdj<Coup>(81, "Itinial", new Coup(null, null, CouleurPion.EMPTY, situationIniale));
		
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
			Pion[][] matrice= noeud.getValeur().getGoban();
			for(int i=0; i<9;i++){
				for(int j=0; j<9;j++){
					if(matrice[i][j]==null){
						//FIXME voir ici si besoin d'implémenter
						// la méthode clone de pion
						//voir pour le fonctionnement de groupe etc.
						Pion[][] clone=matrice.clone();
						//le modulo 2 indique si on est en impair ou pair, donc la couleur du joueur
						//so impair c'est le joueur pair l'autre.
						CouleurPion couleurPion=profondeur%2==1?CouleurPion.BLANC:CouleurPion.NOIR;
						clone[i][j]=new Pion(couleurPion, 0);
						Coup coup= new Coup(i, j, couleurPion, clone);
						Node<Coup> node=bibliotheque.getNewNode();
						node.setValeur(coup);
						node.setEtiquette("feuille");
						bibliotheque.ajouterFils(noeud, node, compteurFils);
						compteurFils++;
						if(profondeur>1){
							constructionArbre(profondeur-1, node);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	
}
