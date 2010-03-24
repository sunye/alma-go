package fr.alma.modele.intelligence;

import java.util.Iterator;
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
 * Classe qui gère l'intelligence articificiel
 * 
 */
public class SunTsu {

	private CouleurPion coul;
	private Difficulte diff;
	private Arbre<Coup> bibliotheque;
	private GoBan situation;
	
	public SunTsu(){
		
		
	}
	

	
	public Coordonnee coupJouer(GoBan situation){
		bibliotheque= new ArbreAdj<Coup>(81, "Itinial", new Coup(null, null, CouleurPion.EMPTY));
		this.situation=situation;
		
		//diff.ordinal()*5;
		constructionArbre(6,bibliotheque.racine(), coul);
		
		
		/*
		 * -Construction de l'arbre
		 * -evaluation
		 */
		
		
		return  null;
	}
	
	private Integer conseille(CouleurPion joueur){
		
		return 0;
	}

	private void constructionArbre(int profondeur, Node<Coup> noeud, CouleurPion ajouer){
		try{
			int compteurFils=1;
			Pion[][] matrice=situation.getGoban();
			for(int i=0; i<9;i++){
				for(int j=0; j<9;j++){
					if(matrice[i][j].getCouleur()==CouleurPion.EMPTY&& situation.estLegal(i, j, ajouer)){
						/*
						 * On ajoute le pion si la case est vide et que le coup est légal
						 * on ajoute le pion et on enregistre le coup dans l'arbre
						 * si on arrive à la dernière profondeur on s'arrête et en remontant on supprime le coup 
						 * de l'arbre
						 * sinon on descend dans l'arbre.
						 */
						situation.ajouterPion(i, j,ajouer);						
						Coup coup= new Coup(i, j, ajouer);
						Node<Coup> node=bibliotheque.getNewNode();
						node.setValeur(coup);
						node.setEtiquette("feuille");
						bibliotheque.ajouterFils(noeud, node, compteurFils);
						compteurFils++;
						if(profondeur>1){
							constructionArbre(profondeur-1, node,inverseCoul(ajouer));
						}
						situation.retirerPion(i, j, ajouer);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private Coup notationArbre(int profondeur, Node<Coup> noeud, CouleurPion ajouer){
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
		
		
		try{
		Node<Coup> noeudCoup;
		Coup cp;
		LinkedList<Coup> notes= new LinkedList<Coup>();
		/*
		 * On descend dans l'arbre en parcourant tout les fils
		 * une fois arrivé au plus bas
		 * on calcul les valeurs des situations 
		 * et on remonte la note
		 * et on redescent et calculer les notes
		 * une fois toute les notes on fait le min max
		 * 
		 */
			for (int i=1; i<=bibliotheque.getArrite(); i++){
				noeudCoup=bibliotheque.iemeFils(noeud, i);
				if( noeudCoup!=null&& ((cp=noeudCoup.getValeur())!=null)){
					situation.ajouterPion(cp.getPosition().getX(), cp.getPosition().getY(), cp.getCoulp());
					if (profondeur==1){
						cp.setNote(conseille(ajouer));
						notes.add(cp);
					}else{
					notes.add(notationArbre(profondeur-1,noeudCoup , inverseCoul(ajouer)));
					}
					situation.retirerPion(cp.getPosition().getX(), cp.getPosition().getY(), cp.getCoulp());
					
				}
			}
			cp=minMaxer(notes, ajouer);
			if (cp==null){
				noeud.getValeur().setNote(conseille(ajouer));
			}else
			{
				noeud.getValeur().setNote(cp.getNote());
			}
			return noeud.getValeur();
			
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	private Coup notationArbreAlphaBeta(int profondeur, Node<Coup> noeud, CouleurPion ajouer){
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
		
		
		try{
		Node<Coup> noeudCoup;
		Coup cp;
		LinkedList<Coup> notes= new LinkedList<Coup>();
		/*
		 * On descend dans l'arbre en parcourant tout les fils
		 * une fois arrivé au plus bas
		 * on calcul les valeurs des situations 
		 * et on remonte la note
		 * et on redescent et calculer les notes
		 * une fois toute les notes on fait le min max
		 * 
		 */
			for (int i=1; i<=bibliotheque.getArrite(); i++){
				noeudCoup=bibliotheque.iemeFils(noeud, i);
				if( noeudCoup!=null&& ((cp=noeudCoup.getValeur())!=null)){
					situation.ajouterPion(cp.getPosition().getX(), cp.getPosition().getY(), cp.getCoulp());
					if (profondeur==1){
						cp.setNote(conseille(ajouer));
						notes.add(cp);
					}else{
					notes.add(notationArbre(profondeur-1,noeudCoup , inverseCoul(ajouer)));
					}
					situation.retirerPion(cp.getPosition().getX(), cp.getPosition().getY(), cp.getCoulp());
					
				}
			}
			cp=minMaxer(notes, ajouer);
			if (cp==null){
				noeud.getValeur().setNote(conseille(ajouer));
			}else
			{
				noeud.getValeur().setNote(cp.getNote());
			}
			return noeud.getValeur();
			
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public CouleurPion inverseCoul(CouleurPion col){
		if (col.equals(CouleurPion.BLANC)){
			return CouleurPion.NOIR;
		}else{
			return CouleurPion.BLANC;
		}
		
		
	}
	
	
	
	
	private Coup minMaxer(LinkedList<Coup> list, CouleurPion ajouer){
		Coup result=null;
		Iterator<Coup> ieter=list.iterator();
		Coup temp=null;
		if( coul==ajouer){
			while(ieter.hasNext()){
				temp=ieter.next();
				if( result==null){
					result=temp;
				}else{
					if(temp!=null){
					result=result.getNote()<temp.getNote()?result:temp;
					}
				}
			}
		}else{
			while(ieter.hasNext()){
				temp=ieter.next();
				if( result==null){
					result=temp;
				}else{
					if(temp!=null){
					result=result.getNote()>temp.getNote()?result:temp;
					}
				}
			}
		}
		return result;
	}
	
	
	
	
}
