package fr.alma.modele.intelligence;

import java.util.Iterator;
import java.util.LinkedList;

import fr.alma.modele.Coordonnee;
import fr.alma.modele.CouleurPion;
import fr.alma.modele.Coup;
import fr.alma.modele.GoBan;
import fr.alma.modele.Pion;





/**
 * Classe qui gère l'intelligence articificiel
 * 
 */
public class SunTsu {

	private CouleurPion coul;
	private Difficulte diff;
	
	private GoBan situation;
	private Pion[] bibliothequeLaVengeance;
	
	
	
	
	
	
	public SunTsu(){
		
		
	}
	

	
	public Coordonnee coupJouer(GoBan situation){
		
		this.situation=situation;
		
		
		
		bibliothequeLaVengeance= new Pion[6];
		
		
		/*
		 * -Construction de l'arbre
		 * -evaluation
		 */
		
		
		return  null;
	}
	
	private Integer conseille(CouleurPion joueur){
		
		return 0;
	}

	public CouleurPion inverseCoul(CouleurPion col){
		if (col.equals(CouleurPion.BLANC)){
			return CouleurPion.NOIR;
		}else{
			return CouleurPion.BLANC;
		}
		
		
	}
	
	
	
	
	private Coup alphaLeBeta(int profondeur, Coup precedentResult, CouleurPion ajouer){
		Coup temporaire=null;
		Coup result=null;
		for (int i=0; i<9; i++){
			for (int j=0;j<9;j++){
				if(situation.estLegal(i, j, ajouer)){
					situation.ajouterPion(i, j, ajouer);
					Coup coupActuel= new Coup(i, j,ajouer);
					if ( profondeur ==1){
										
						coupActuel.setNote(this.conseille(ajouer));
						
						if (coul==ajouer){
							result= result==null?coupActuel:(result.getNote()<coupActuel.getNote()?result:coupActuel);
							if ( precedentResult.getNote() > result.getNote()){
								situation.retirerPion(i, j, ajouer);
								return result;
							}
						}else{
							if (coul==ajouer){
								result= result==null?coupActuel:(result.getNote()>coupActuel.getNote()?result:coupActuel);
								if ( precedentResult.getNote() < result.getNote()){
									situation.retirerPion(i, j, ajouer);
									return result;
								}
							}
						}
						//test de min max avec le temporaire
						// qui correspond a l'évaluation actuel
						// et précédent qui est celui de la récursion précédente
						//et donc si celui de la récursion précédente est plus grand //petit
						//on ne va pas jusqu'au bout et on remonte direct en supprimant le coup.
						
						//evaluation du jeu
						//on test min max avec valeur note de ce niveau précédent
						//on test si on break ou non après changement
						//suite.
					}else{
					//on va récupérer le coup évaluer
					//on va le comparer avec tout les autres et finalement on renvoi le meilleur min max
					//on récupére le coup
					//on regarde si il est mieux que celui d'avant
					//si oui on change le coup et la note
					//si
					temporaire=alphaLeBeta(profondeur-1, result, inverseCoul(ajouer));
					coupActuel.setNote(temporaire.getNote());
					if (coul==ajouer){
						result=result==null?coupActuel:result.getNote()<coupActuel.getNote()?result:coupActuel;
					}else{
						result=result==null?coupActuel:result.getNote()>coupActuel.getNote()?result:coupActuel;
					}
					
					
					//min max temporaire
					//et on colle dans result
					
					
						
					}
					situation.retirerPion(i, j, ajouer);
				}
			}
		}
		
		
		
		return result;
	}
	
	
	
	
	
}
