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
		int profondeur= diff.ordinal()+1*1;

		return  alphaBeta(profondeur, null, coul).getPosition();
	}
	
	private Integer goBanEvaluation(){
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
		
		
		HashMap<Vide, Integer> mapScoreOeilBlanc= new HashMap<Vide, Integer>();
		HashMap<Vide, Integer> mapScoreOeilNoir= new HashMap<Vide, Integer>();
		
		int nbYeuxNoir=0;
		int nbOeilNoir=0;
		int nbBorgneNoir=0;
		int nbYeuxBlanc=0;
		int nbOeilBlanc=0;
		int nbBorgneBlanc=0;
		
		Iterator<Vide> emptyIterator= groupsVide.iterator();
		while (ite.hasNext()){
			Vide emptTemp=emptyIterator.next();
			if (emptTemp.getGroupeVoisin().size()==1){
				if (((Groupe) emptTemp.getGroupeVoisin().toArray()[0]).getCoul()==CouleurPion.NOIR){
					mapScoreOeilNoir.put(emptTemp,mapScoreOeilNoir.containsKey(emptTemp)?mapScoreOeilNoir.get(emptTemp)+1:new Integer(1) );											
				}else{
					mapScoreOeilBlanc.put(emptTemp,mapScoreOeilBlanc.containsKey(emptTemp)?mapScoreOeilBlanc.get(emptTemp)+1:new Integer(1) );
				}
			}			
		}
		
		
		Iterator<Vide> iterVideNoir=mapScoreOeilNoir.keySet().iterator();
		Iterator<Vide> iterVideBlanc=mapScoreOeilBlanc.keySet().iterator();
		Vide tempiter=null;
		while (iterVideNoir.hasNext()){
			tempiter=iterVideNoir.next();
			if( mapScoreOeilNoir.get(tempiter)==1){
				if (tempiter.getPionVides().size()==1){
					nbBorgneNoir++;
				}else{
					nbOeilNoir++;
				}
				
			}else{
				nbYeuxNoir++;
			}
		}
		while (iterVideBlanc.hasNext()){
			tempiter=iterVideBlanc.next();
			if( mapScoreOeilBlanc.get(tempiter)==1){
				if (tempiter.getPionVides().size()==1){
					nbBorgneBlanc++;
				}else{
					nbOeilBlanc++;
				}
				
			}else{
				nbYeuxBlanc++;
			}
		}
		
		int scoreBlanc=0;
		int scoreNoir=0;
		for (Groupe gblanc: groupeBlanc){
			if( gblanc.nbLibertes()==0){
				scoreBlanc+=-100;
			}else{
				scoreBlanc+=-gblanc.nbLibertes()*10;
			}
		}
		
		
		for (Groupe gNoir: groupeNoir){
			if( gNoir.nbLibertes()==0){
				scoreNoir+=-100;
			}else{
				scoreNoir+=-gNoir.nbLibertes()*10;
			}
		}
		
		 
		int score=0;
		if (coul==CouleurPion.BLANC){
			scoreNoir=scoreNoir*-1;
			score= scoreBlanc-scoreNoir+nbBorgneBlanc*100+nbBorgneNoir*-100+nbOeilBlanc*-50+nbOeilNoir*50+nbYeuxBlanc*-50+nbYeuxNoir*50;
			
			
		}else{
			scoreBlanc=scoreBlanc*-1;
			score= scoreBlanc-scoreNoir+nbBorgneBlanc*-100+nbBorgneNoir*100+nbOeilBlanc*50+nbOeilNoir*-50+nbYeuxBlanc*50+nbYeuxNoir*-50;
		}
		return score;
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
							if ( precedentResult!=null&& precedentResult.getNote() > result.getNote()){
								situation.retirerPion(i, j, ajouer);
								return result;
							}
						}else{
							if (coul==ajouer){
								result= result==null?coupActuel:(result.getNote()>coupActuel.getNote()?result:coupActuel);
								if ( precedentResult!=null&& precedentResult.getNote() < result.getNote()){
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
