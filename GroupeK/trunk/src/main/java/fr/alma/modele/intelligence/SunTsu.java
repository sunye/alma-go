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
 * 
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * Our awesome Ai class
 */
public class SunTsu {

	/**
	 * The actual color for the AI
	 */
	private CouleurPion coul;
	
	/**
	 * the actual difficulty
	 */
	private Difficulte diff;
	
	/**
	 * The gale board
	 */
	private GoBan situation;
	
	/**
	 * Array with 1 and -1 used to move in the matrix 
	 */
	private static int[] modifier={1,-1};
	
	/**
	 * calculated position for the next move
	 */
	private CoordonneeIA play;
	
	/**
	 * collection that contain temporary move played by the SunTsu Ai
	 */
	private HashSet<Coup> coupJouer;
	
	/**
	 * 
	 * @param goier game board
	 */
	public SunTsu(GoBan goier){
		this.situation=goier;
		this.diff=Difficulte.Debutant;
		this.coupJouer= new HashSet<Coup>();
		play= new CoordonneeIA(null,null);
		
		
	}
	
	/**
	 * 
	 * @return the difficulty
	 */
	public Difficulte getDiff() {
		return diff;
	}
	/**
	 * set the difficulty
	 * @param diff
	 */
	public void setDiff(Difficulte diff) {
		this.diff = diff;
	}
	
	
	/**
	 * prepare the next move for the color
	 * @param coulp
	 */
	public void prepareNextMove(CouleurPion coulp){
		this.coul=coulp;
		coupJouer.clear();
		synchronized (play) {
			play.setTermine(false);
			play.setCoordinate(new Coordonnee(null, null));
		}
		
		//calcul profondeur en fonction de la difficulté
		//modulé difficulté par le nombre de pion sur le plateau ?
		int profondeur= diff.ordinal()+1*2;

		Coordonnee temp= alphaBeta(profondeur,profondeur, null, coul).getPosition();
		
		synchronized (play) {
			play.setCoordinate(temp);
			play.setTermine(true);
		}
	
		
		

		synchronized (play) {
			play.notify();
		} 
		
	}
	/**
	 * Get the next move to play
	 * this is a waiting method it block until a solution calculated by the method(prepareNextMove) or if the move is force by terminerTraitement
	 * @return the next move to play 
	 */
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
			System.out.println(result.getX()+" "+result.getY());
			play.setTermine(false);
			return result;
		}
	}
	
	/**
	 * Force the calcultation to end
	 */
	public void terminerTraitement(){
		
		
		synchronized (situation) {
			for (Coup cp: coupJouer){
				situation.retirerPion(cp.getPosition(), cp.getCoulp());
			}
		}
		
		synchronized (play) {
			play.setTermine(true);
			play.notify();
		} 
	}

	/**
	 * the Alpha beta algorithm
	 * @param profondeurmarx the maximum deep
	 * @param profondeur 
	 * @param precedentResult the last best move
	 * @param ajouer the turn color
	 * @return a good move
	 */
	private Coup alphaBeta(int profondeurmarx, int profondeur, Coup precedentResult, CouleurPion ajouer){
		Coup temporaire=null;
		Coup result=null;
		for (int i=0; i<GoBan.TAILLE_GO_BAN; i++){
			for (int j=0;j<GoBan.TAILLE_GO_BAN;j++){
				Coordonnee coodCoup=new Coordonnee(i, j);
				Coup coupActuel= new Coup(i, j,ajouer);
				TypeCoup typ=situation.isMoveAllowed(coodCoup, ajouer);
				if(typ==TypeCoup.PRISE&&ajouer==this.coul){
					coupActuel.setNote(-50000);
					return coupActuel;
				}
				if(typ!=TypeCoup.NONVALID){
					situation.addPion(coodCoup, ajouer);
					if( play.isCoordinateNotEmpty() && ajouer==coul){
						play.setCoordinate(coodCoup);
					}
					coupJouer.add(coupActuel);
					if ( profondeur ==1){
										
						coupActuel.setNote(this.goBanEvaluation());
						
						if (coul==ajouer){
							result= result==null?coupActuel:(result.getNote()<coupActuel.getNote()?result:coupActuel);
							if ( precedentResult!=null&& precedentResult.getNote() > result.getNote()){
								situation.retirerPion(coodCoup, ajouer);
								coupJouer.remove(coupActuel);
								return result;
							}
						}else{
								result= result==null?coupActuel:(result.getNote()>coupActuel.getNote()?result:coupActuel);
								if ( precedentResult!=null&& precedentResult.getNote() < result.getNote()){
									situation.retirerPion(coodCoup, ajouer);
									coupJouer.remove(coupActuel);
									return result;
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
					coupJouer.remove(coupActuel);
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
	/**
	 * 
	 * @return a mark for the actual situation
	 */
	private Integer goBanEvaluation(){
		Pion[][] matrice= situation.getGoban();
		HashSet<Coordonnee> caseVide=new HashSet<Coordonnee>();
		HashSet<Groupe> groupeNoir= new HashSet<Groupe>();
		HashSet<Groupe> groupeBlanc= new HashSet<Groupe>();
		int scoreBlanc=0;
		int scoreNoir=0;
		//on récupère toutes les cases vides
		//et tous les groupes de pions
		
		
		for (Pion vide :situation.getGroupeVide().getPions()){
			vide.setMarque(false);
			caseVide.add(vide.getPosition());
			
		}
			
		
		for (Groupe g: situation.getGroup()){
			if( g.getCouleur()==CouleurPion.BLANC){
				groupeBlanc.add(g);
			}else{
				groupeNoir.add(g);
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
			score= scoreBlanc-scoreNoir+nbBorgneBlanc*100+nbBorgneNoir*-100+nbOeilBlanc*-50+nbOeilNoir*50+nbYeuxBlanc*-50+nbYeuxNoir*50;
			
			
		}else{
			scoreBlanc=scoreBlanc*-1;
			score= scoreBlanc-scoreNoir+nbBorgneBlanc*-100+nbBorgneNoir*100+nbOeilBlanc*50+nbOeilNoir*-50+nbYeuxBlanc*50+nbYeuxNoir*-50;
		}
		return score;
	}
	
	
}
