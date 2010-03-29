package fr.alma.modele.intelligence;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import fr.alma.modele.Coordonnee;
import fr.alma.modele.CouleurPion;
import fr.alma.modele.Coup;
import fr.alma.modele.GoBan;
import fr.alma.modele.Groupe;
import fr.alma.modele.Pion;





/**
 * Classe qui g�re l'intelligence articificiel
 * 
 */
public class SunTsu {

	private CouleurPion coul;
	private Difficulte diff;
	private GoBan situation;
		
	
	
	
	
	
	public SunTsu(){
		this.situation=new GoBan();
		this.diff=Difficulte.Debutant;
	}
	
	public Difficulte getDiff() {
		return diff;
	}
	
	public void setDiff(Difficulte diff) {
		this.diff = diff;
	}
	

	
	public Coordonnee coupJouer(GoBan actuel, CouleurPion coulp){
		//recopie du goban actuel
		situation.setGoban(actuel.getGoban().clone());
		situation.setNbBlanc(actuel.getNbBlanc());
		situation.setNbNoir(actuel.getNbNoir());
		situation.setNum(actuel.getNum());
		
		this.coul=coulp;
		
		//calcul profondeur en fonction de la difficult�
		int profondeur= diff.ordinal()+1*3;

		return  alphaLeBeta(profondeur, null, coul).getPosition();
	}
	
	private Integer conseille(){
		
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
		for (int i=0; i<GoBan.TAILLE_GO_BAN; i++){
			for (int j=0;j<GoBan.TAILLE_GO_BAN;j++){
				if(situation.estLegal(i, j, ajouer)){
					situation.ajouterPion(i, j, ajouer);
					Coup coupActuel= new Coup(i, j,ajouer);
					if ( profondeur ==1){
										
						coupActuel.setNote(this.conseille());
						
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
						// qui correspond a l'�valuation actuel
						// et pr�c�dent qui est celui de la r�cursion pr�c�dente
						//et donc si celui de la r�cursion pr�c�dente est plus grand //petit
						//on ne va pas jusqu'au bout et on remonte direct en supprimant le coup.
						
						//evaluation du jeu
						//on test min max avec valeur note de ce niveau pr�c�dent
						//on test si on break ou non apr�s changement
						//suite.
					}else{
					//on va r�cup�rer le coup �valuer
					//on va le comparer avec tout les autres et finalement on renvoi le meilleur min max
					//on r�cup�re le coup
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

	
	
	/*
	 * 
	 * Borgne:
	 * une case vide entour� par un seul et m�me groupe ne poss�dant qu'une libert�
	 * 
	 * 
	 * oeil:
	 * groupe de case vide entour� par un seul et m�me groupe
	 * 
	 * 
	 * yeux: 
	 * plusieurs groupe de vide diff�rent entour� par un seul et m�me groupe
	 */
	private void constructionEuristique(){
		Pion[][] matrice= situation.getGoban();
		HashSet<Pion> caseVide=new HashSet<Pion>();
		HashSet<Groupe> groupeNoir= new HashSet<Groupe>();
		HashSet<Groupe> groupeBlanc= new HashSet<Groupe>();
		
		//on r�cup�re toutes les cases vides
		//et tous les groupes de pions
		for(int i=0;i<GoBan.TAILLE_GO_BAN;i++){
			for(int j=0; j<GoBan.TAILLE_GO_BAN;j++){
				if( matrice[i][j].getCouleur()==CouleurPion.EMPTY){
					caseVide.add(matrice[i][j]);
				}else if(matrice[i][j].getCouleur()==CouleurPion.BLANC){
					groupeBlanc.add(matrice[i][j].getGroupe());					
				} else{
					groupeNoir.add(matrice[i][j].getGroupe());
				}
			}
		}
		
		/*
		 * Parcours du set contenant les cases vides pour constitu� des groupes de cases vides
		 * pour pouvoir calculer les yeux etc.
		 */
		
		
		
	}
	
	
	
}
