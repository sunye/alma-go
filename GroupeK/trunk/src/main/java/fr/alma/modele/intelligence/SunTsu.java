package fr.alma.modele.intelligence;

import java.util.LinkedList;

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
	

	
	public Coordonnee coupJouer(GoBan situation){
		bibliotheque= new ArbreAdj<Coup>(81, "Itinial", new Coup(null, null, CouleurPion.EMPTY));
		this.situation=situation;
		
		//diff.ordinal()*5;
		constructionArbre(6,bibliotheque.racine());
		
		
		/*
		 * -Construction de l'arbre
		 * -evaluation
		 */
		
		
		return  null;
	}
	
	private Integer conseille(CouleurPion joueur){
		
		return 0;
	}

	private void constructionArbre(int profondeur, Node<Coup> noeud){
		try{
			int compteurFils=1;
			Pion[][] matrice=situation.getGoban();
			for(int i=0; i<9;i++){
				for(int j=0; j<9;j++){
					//TODO revoir le choix de la couleur en fonction de la profondeur
					//rajouter un paramètre pour déterminer le tour de qui c'est.
					//même chose à faire pour la notation de l'arbre.
					
					
					//le modulo 2 indique si on est en impair ou pair, donc la couleur du joueur
					//so impair c'est le joueur pair l'autre.
					CouleurPion couleurPion=profondeur%2==1?CouleurPion.BLANC:CouleurPion.NOIR;
					if(matrice[i][j].getCouleur()==CouleurPion.EMPTY&& situation.estLegal(i, j, couleurPion)){
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
	
	
	private Coup notationArbre(int profondeur, Node<Coup> noeud){
		/*Algo de notation de l'arbre:
		 * 
		 * descend jusqu'a la profondeur max
		 * on evalue chaque noeud
		 * on supprime tout les fils
		 * on remonte la note
		 * on redescend dans chaque fils
		 * une fois qu'on remonte on compare toutes les notes
		 * avant de remonter et decsendre.
		 * 
		 * 
		 * 
		 * 
		 */
		CouleurPion coulp= coul;
		
		try{
		Node<Coup> noeudCoup;
		Coup cp;
		LinkedList<Coup> notes= new LinkedList<Coup>();
		if (profondeur==1){
			for (int i=1; i<=bibliotheque.getArrite(); i++){
				noeudCoup=bibliotheque.iemeFils(noeud, i);
				if( noeudCoup!=null&& ((cp=noeudCoup.getValeur())!=null)){
					
					situation.ajouterPion(cp.getPosition().getX(), cp.getPosition().getY(), cp.getCoulp());
					cp.setNote(conseille(coulp));
					situation.retirerPion(cp.getPosition().getX(), cp.getPosition().getY(), cp.getCoulp());
					notes.add(cp);
				}
			}
			//TODO a finir
			
			
		}else{
			
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
}
