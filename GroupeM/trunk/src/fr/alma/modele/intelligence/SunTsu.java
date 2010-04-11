package fr.alma.modele.intelligence;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import fr.alma.modele.Coordonnee;
import fr.alma.modele.CoordonneeIA;
import fr.alma.modele.CouleurPion;
import fr.alma.modele.Coup;
import fr.alma.modele.GoBan;
import fr.alma.modele.Groupe;
import fr.alma.modele.Pion;
import fr.alma.modele.TypeCoup;
import fr.alma.modele.Vide;







/**
 * Classe qui g�re l'intelligence articificiel
 * 
 */
public class SunTsu {

	/**
	 * The actual color for the AI
	 */
	private CouleurPion coul;
	private Difficulte diff;
	private GoBan situation;
	private static int[] modifier={1,-1};
	private CoordonneeIA play;
	
	
	public SunTsu(GoBan goier){
		this.situation=goier;
		this.diff=Difficulte.Debutant;
		
		play= new CoordonneeIA(0, 0);
	}
	
	public Difficulte getDiff() {
		return diff;
	}
	
	public void setDiff(Difficulte diff) {
		this.diff = diff;
	}
	
	
	
	public void prepareNextMove(CouleurPion coulp){
		this.coul=coulp;
		
		//calcul profondeur en fonction de la difficult�
		//modul� difficult� par le nombre de pion sur le plateau ?
		int profondeur= diff.ordinal()+1*2;

	
			play.setCoordinate(alphaBeta(profondeur,profondeur, null, coul).getPosition());
		//	synchronized (play) {	}
				
		play.setTermine(true);

		synchronized (play) {
			play.notify();
		} 
		
	}
	
	public Coordonnee getPlay() {
		synchronized (play) {
			
			if (!play.isTermine()) {
				try {
					play.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Coordonnee result= new Coordonnee(play.getX(), play.getY());
			play.setTermine(false);
			return result;
		}
	}
	
	public void terminerTraitement(){
		
		play.setTermine(true);
		synchronized (play) {
			play.notify();
		} 
		play.setTermine(false);
	}

	private Coup alphaBeta(int profondeurmarx, int profondeur, Coup precedentResult, CouleurPion ajouer){
		Coup temporaire=null;
		Coup result=null;
		for (int i=0; i<GoBan.TAILLE_GO_BAN; i++){
			for (int j=0;j<GoBan.TAILLE_GO_BAN;j++){
				Coordonnee coodCoup=new Coordonnee(i, j);
				TypeCoup typ=situation.isMoveAllowed(coodCoup, ajouer);
				if(typ!=TypeCoup.NONVALID){
					situation.addPion(coodCoup, ajouer);
					Coup coupActuel= new Coup(i, j,ajouer);
					if ( profondeur ==1){
										
						coupActuel.setNote(this.goBanEvaluation());
						
						if (coul==ajouer){
							result= result==null?coupActuel:(result.getNote()<coupActuel.getNote()?result:coupActuel);
							if ( precedentResult!=null&& precedentResult.getNote() > result.getNote()){
								situation.retirerPion(coodCoup, ajouer);
								return result;
							}
						}else{
								result= result==null?coupActuel:(result.getNote()>coupActuel.getNote()?result:coupActuel);
								if ( precedentResult!=null&& precedentResult.getNote() < result.getNote()){
									situation.retirerPion(coodCoup, ajouer);
									return result;
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
						
						//FIXME change here
					temporaire=alphaBeta(profondeurmarx,profondeur-1, result, CouleurPion.oppose(ajouer));
					coupActuel.setNote(temporaire.getNote());
					if (coul==ajouer){
						result=result==null?coupActuel:result.getNote()<coupActuel.getNote()?result:coupActuel;
					}else{
						result=result==null?coupActuel:result.getNote()>coupActuel.getNote()?result:coupActuel;
					}
					if( profondeur== profondeurmarx){
						this.play.setCoordinate(result.getPosition());
					}
					
					//min max temporaire
					//et on colle dans result
					
					
						
					}
					situation.retirerPion(coodCoup, ajouer);
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

	private Integer goBanEvaluation(){
		Pion[][] matrice= situation.getGoban();
		HashSet<Coordonnee> caseVide=new HashSet<Coordonnee>();
		HashSet<Groupe> groupeNoir= new HashSet<Groupe>();
		HashSet<Groupe> groupeBlanc= new HashSet<Groupe>();
		int scoreBlanc=0;
		int scoreNoir=0;
		//on r�cup�re toutes les cases vides
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
		 * Parcours du set contenant les cases vides pour constitu� des groupes de cases vides
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
				if (((Groupe) emptTemp.getGroupeVoisin().toArray()[0]).getCouleur()==CouleurPion.NOIR){
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
		
	
		for (Groupe gblanc: groupeBlanc){
			if( gblanc.getNbLiberty()==0){
				scoreBlanc+=-100;
			}else{
				scoreBlanc+=-gblanc.getNbLiberty()*10;
			}
		}
		
		
		for (Groupe gNoir: groupeNoir){
			if( gNoir.getNbLiberty()==0){
				scoreNoir+=-100;
			}else{
				scoreNoir+=-gNoir.getNbLiberty()*10;
			}
		}
	
		 
		int score=0;
		if (coul==CouleurPion.BLANC){
			scoreNoir=scoreNoir*-1;
			score= scoreBlanc-scoreNoir;//+nbBorgneBlanc*100+nbBorgneNoir*-100+nbOeilBlanc*-50+nbOeilNoir*50+nbYeuxBlanc*-50+nbYeuxNoir*50;
			
			
		}else{
			scoreBlanc=scoreBlanc*-1;
			score= scoreBlanc-scoreNoir;//+nbBorgneBlanc*-100+nbBorgneNoir*100+nbOeilBlanc*50+nbOeilNoir*-50+nbYeuxBlanc*50+nbYeuxNoir*-50;
		}
		return score;
	}
	
	
}
