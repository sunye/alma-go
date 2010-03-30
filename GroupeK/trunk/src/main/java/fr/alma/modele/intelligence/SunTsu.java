package fr.alma.modele.intelligence;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import fr.alma.modele.Coordonnee;
import fr.alma.modele.CouleurPion;
import fr.alma.modele.Coup;
import fr.alma.modele.GoBan;
import fr.alma.modele.Groupe;
import fr.alma.modele.Pion;
import fr.alma.modele.Vide;





/**
 * Classe qui gère l'intelligence articificiel
 * 
 */
public class SunTsu {

	private CouleurPion coul;
	private Difficulte diff;
	private GoBan situation;
	private static int[] modifier={1,-1};
	
	
	
	
	
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
	
	
	
	public Coordonnee nextMove(GoBan actuel, CouleurPion coulp){
		//recopie du goban actuel
		situation.setGoban(actuel.getGoban().clone());
		situation.setNbBlanc(actuel.getNbBlanc());
		situation.setNbNoir(actuel.getNbNoir());
		situation.setNum(actuel.getNum());
		
		this.coul=coulp;
		
		//calcul profondeur en fonction de la difficulté
		//modulé difficulté par le nombre de pion sur le plateau ?
		int profondeur= diff.ordinal()+1*3;

		return  alphaBeta(profondeur, null, coul).getPosition();
	}
	
	private Integer goBanEvaluation(){
		
		return 0;
	}

	public CouleurPion reverseColor(CouleurPion col){
		if (col.equals(CouleurPion.BLANC)){
			return CouleurPion.NOIR;
		}else{
			return CouleurPion.BLANC;
		}
	}
	
	
	
	
	private Coup alphaBeta(int profondeur, Coup precedentResult, CouleurPion ajouer){
		Coup temporaire=null;
		Coup result=null;
		for (int i=0; i<GoBan.TAILLE_GO_BAN; i++){
			for (int j=0;j<GoBan.TAILLE_GO_BAN;j++){
				if(situation.estLegal(i, j, ajouer)){
					situation.ajouterPion(i, j, ajouer);
					Coup coupActuel= new Coup(i, j,ajouer);
					if ( profondeur ==1){
										
						coupActuel.setNote(this.goBanEvaluation());
						
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
					temporaire=alphaBeta(profondeur-1, result, reverseColor(ajouer));
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
	 * une case vide entouré par un seul et même groupe ne possédant qu'une liberté
	 * 
	 * 
	 * oeil:
	 * groupe de case vide entouré par un seul et même groupe
	 * 
	 * 
	 * yeux: 
	 * plusieurs groupe de vide différent entouré par un seul et même groupe
	 */
	private void constructionEuristique(){
		Pion[][] matrice= situation.getGoban();
		HashSet<Coordonnee> caseVide=new HashSet<Coordonnee>();
		HashSet<Groupe> groupeNoir= new HashSet<Groupe>();
		HashSet<Groupe> groupeBlanc= new HashSet<Groupe>();
		
		//on récupère toutes les cases vides
		//et tous les groupes de pions
		for(int i=0;i<GoBan.TAILLE_GO_BAN;i++){
			for(int j=0; j<GoBan.TAILLE_GO_BAN;j++){
				if( matrice[i][j].getCouleur()==CouleurPion.EMPTY){
					matrice[i][j].setMarque(false);
					caseVide.add(new Coordonnee(i, j));
				}else if(matrice[i][j].getCouleur()==CouleurPion.BLANC){
					groupeBlanc.add(matrice[i][j].getGroupe());					
				} else{
					groupeNoir.add(matrice[i][j].getGroupe());
				}
			}
		}
		
		/*
		 * Parcours du set contenant les cases vides pour constitué des groupes de cases vides
		 * pour pouvoir calculer les yeux etc.
		 */
		Iterator<Coordonnee> ite= caseVide.iterator();
		LinkedList<Vide> groupsVide= new LinkedList<Vide>();
		
		while (ite.hasNext()){
			Coordonnee temp= ite.next();
			if(!matrice[temp.getX()][temp.getY()].isMarque()){
				groupsVide.add(backtrackingEmptyCell(matrice, new Vide(), temp));
			}
		}
		
		
		HashMap<Groupe, Integer> mapScoreOeilBlanc= new HashMap<Groupe, Integer>();
		HashMap<Groupe, Integer> mapScoreOeilNoir= new HashMap<Groupe, Integer>();
		
		Iterator<Vide> emptyIterator= groupsVide.iterator();
		while (ite.hasNext()){
			Vide emptTemp=emptyIterator.next();
			if (emptTemp.getGroupeVoisin().size()==1){
				
			}			
		}
		
		
		
		
		
	}
	
	
	/**
	 * Method to backtracking all the goban to determine empty cell blocks
	 * and neigbours(non empty cells) groups
	 * @param goban the go ban with
	 * @param groupVide the group we work on
	 * @param coord the coordinate of the actuel cell
	 * @return the group complete with all neigbours
	 */
	private Vide backtrackingEmptyCell(Pion[][] goban, Vide groupVide, Coordonnee coord){
		if( coord.isValid(GoBan.TAILLE_GO_BAN)){
			Pion itere=goban[coord.getX()][coord.getY()];
			if( itere.getCouleur()!=CouleurPion.EMPTY){
				groupVide.addGroup(itere.getGroupe());
			}else{
				itere.setMarque(true);
				if( groupVide.addPion(itere)){
					for (int i=0; i<modifier.length;i++){
						groupVide=backtrackingEmptyCell(goban, groupVide, new Coordonnee(coord.getX()+modifier[i], coord.getY()));
					}
					for (int i=0; i<modifier.length;i++){
						groupVide=backtrackingEmptyCell(goban, groupVide, new Coordonnee(coord.getX(), coord.getY()+modifier[i]));
					}	
				}
			}
		
		
		}
		
		return groupVide;
	}
	
	
	
}
