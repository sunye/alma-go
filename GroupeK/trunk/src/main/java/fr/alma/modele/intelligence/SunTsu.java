package fr.alma.modele.intelligence;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import fr.alma.modele.Coordinate;
import fr.alma.modele.CoordinateAI;
import fr.alma.modele.StoneColor;
import fr.alma.modele.Move;
import fr.alma.modele.GoBan;
import fr.alma.modele.Group;
import fr.alma.modele.Stone;
import fr.alma.modele.MoveType;
import fr.alma.modele.EmptyGroup;
/*$Author$ 
 * $Date$ 
 * $Revision$ 
 * 
 *Copyright (C) 2010  Fortun Manoël & Caillaud Anthony
 *
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * */
/**
 * Our awesome Ai class
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * 
 */
public class SunTsu {

	/**
	 * The actual color for the AI
	 */
	private StoneColor coul;
	
	/**
	 * the actual difficulty
	 */
	private Difficulty diff;
	
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
	private CoordinateAI play;
	
	/**
	 * collection that contain temporary move played by the SunTsu Ai
	 */
	private HashSet<Move> movePlayed;
	
	/**
	 * 
	 * @param goier game board
	 */
	public SunTsu(GoBan goier){
		this.situation=goier;
		this.diff=Difficulty.EASY;
		this.movePlayed= new HashSet<Move>();
		play= new CoordinateAI(null,null);
		
		
	}
	
	/**
	 * 
	 * @return the difficulty
	 */
	public Difficulty getDiff() {
		return diff;
	}
	/**
	 * set the difficulty
	 * @param diff
	 */
	public void setDiff(Difficulty diff) {
		this.diff = diff;
	}
	
	
	/**
	 * prepare the next move for the color
	 * @param coulp
	 */
	public void prepareNextMove(StoneColor coulp){
		this.coul=coulp;
		movePlayed.clear();
		synchronized (play) {
			play.setTermine(false);
			play.setCoordinate(new Coordinate(null, null));
		}
		
		//calcul profondeur en fonction de la difficulté
		//modulé difficulté par le nombre de pion sur le plateau ?
		int profondeur= diff.ordinal()+1*2;

		Coordinate temp= alphaBeta(profondeur,profondeur, null, coul).getPosition();
		
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
	public Coordinate getPlay() {
		synchronized (play) {
			
			if (!play.isTermine()) {
				
				try {
					play.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			Coordinate result= new Coordinate(play.getX(), play.getY());
			System.out.println(result.getX()+" "+result.getY());
			play.setTermine(false);
			return result;
		}
	}
	
	/**
	 * Force the calcultation to end
	 */
	public void endWithTheAlphaBeta(){
		
		
		synchronized (situation) {
			for (Move cp: movePlayed){
				situation.removeStone(cp.getPosition(), cp.getCoulp());
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
	private Move alphaBeta(int profondeurmarx, int profondeur, Move precedentResult, StoneColor ajouer){
		Move temporaire=null;
		Move result=null;
		for (int i=0; i<GoBan.GO_BAN_SIZE; i++){
			for (int j=0;j<GoBan.GO_BAN_SIZE;j++){
				Coordinate coodCoup=new Coordinate(i, j);
				Move coupActuel= new Move(i, j,ajouer);
				MoveType typ=situation.isMoveAllowed(coodCoup, ajouer);
				if(typ==MoveType.CAPTURE&&ajouer==this.coul){
					coupActuel.setMark(-50000);
					return coupActuel;
				}
				if(typ!=MoveType.NONVALID){
					situation.addPion(coodCoup, ajouer);
					if( play.isCoordinateNotEmpty() && ajouer==coul){
						play.setCoordinate(coodCoup);
					}
					movePlayed.add(coupActuel);
					if ( profondeur ==1){
										
						coupActuel.setMark(this.goBanEvaluation());
						
						if (coul==ajouer){
							result= result==null?coupActuel:(result.getMark()<coupActuel.getMark()?result:coupActuel);
							if ( precedentResult!=null&& precedentResult.getMark() > result.getMark()){
								situation.removeStone(coodCoup, ajouer);
								movePlayed.remove(coupActuel);
								return result;
							}
						}else{
								result= result==null?coupActuel:(result.getMark()>coupActuel.getMark()?result:coupActuel);
								if ( precedentResult!=null&& precedentResult.getMark() < result.getMark()){
									situation.removeStone(coodCoup, ajouer);
									movePlayed.remove(coupActuel);
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
					temporaire=alphaBeta(profondeurmarx,profondeur-1, result, StoneColor.oppose(ajouer));
					coupActuel.setMark(temporaire.getMark());
					if (coul==ajouer){
						result=result==null?coupActuel:result.getMark()<coupActuel.getMark()?result:coupActuel;
					}else{
						result=result==null?coupActuel:result.getMark()>coupActuel.getMark()?result:coupActuel;
					}
					if( profondeur== profondeurmarx){
						this.play.setCoordinate(result.getPosition());
					}
					
					//min max temporaire
					//et on colle dans result
					
					
						
					}
					situation.removeStone(coodCoup, ajouer);
					movePlayed.remove(coupActuel);
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
	private EmptyGroup backtrackingEmptyCell(Stone[][] goban, EmptyGroup groupVide, Coordinate coord){
		if( coord.isValid(GoBan.GO_BAN_SIZE)){
			Stone itere=goban[coord.getX()][coord.getY()];
			if( itere.getColor()!=StoneColor.EMPTY){
				groupVide.addGroup(itere.getGroup());
			}else{
				itere.setMarque(true);
				if( groupVide.addPion(itere)){
					for (int i=0; i<modifier.length;i++){
						groupVide=backtrackingEmptyCell(goban, groupVide, new Coordinate(coord.getX()+modifier[i], coord.getY()));
					}
					for (int i=0; i<modifier.length;i++){
						groupVide=backtrackingEmptyCell(goban, groupVide, new Coordinate(coord.getX(), coord.getY()+modifier[i]));
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
		Stone[][] matrice= situation.getGoban();
		HashSet<Coordinate> caseVide=new HashSet<Coordinate>();
		HashSet<Group> groupeNoir= new HashSet<Group>();
		HashSet<Group> groupeBlanc= new HashSet<Group>();
		int scoreBlanc=0;
		int scoreNoir=0;
		//on récupère toutes les cases vides
		//et tous les groupes de pions
		
		
		for (Stone vide :situation.getEmptyStoneGroup().getStones()){
			vide.setMarque(false);
			caseVide.add(vide.getPosition());
			
		}
			
		
		for (Group g: situation.getGroup()){
			if( g.getGroupColor()==StoneColor.WHITE){
				groupeBlanc.add(g);
			}else{
				groupeNoir.add(g);
			}
			
		}
		
		
		
		
		/*
		 * Parcours du set contenant les cases vides pour constitué des groupes de cases vides
		 * pour pouvoir calculer les yeux etc.
		 */
		Iterator<Coordinate> ite= caseVide.iterator();
		LinkedList<EmptyGroup> groupsVide= new LinkedList<EmptyGroup>();
		
		while (ite.hasNext()){
			Coordinate temp= ite.next();
			if(!matrice[temp.getX()][temp.getY()].isMarque()){
				groupsVide.add(backtrackingEmptyCell(matrice, new EmptyGroup(), temp));
			}
		}
		
		
		HashMap<EmptyGroup, Integer> mapScoreOeilBlanc= new HashMap<EmptyGroup, Integer>();
		HashMap<EmptyGroup, Integer> mapScoreOeilNoir= new HashMap<EmptyGroup, Integer>();
		
		int nbYeuxNoir=0;
		int nbOeilNoir=0;
		int nbBorgneNoir=0;
		int nbYeuxBlanc=0;
		int nbOeilBlanc=0;
		int nbBorgneBlanc=0;
		
		Iterator<EmptyGroup> emptyIterator= groupsVide.iterator();
		while (ite.hasNext()){
			EmptyGroup emptTemp=emptyIterator.next();
			if (emptTemp.getNeigborsGroup().size()==1){
				if (((Group) emptTemp.getNeigborsGroup().toArray()[0]).getGroupColor()==StoneColor.BLACK){
					mapScoreOeilNoir.put(emptTemp,mapScoreOeilNoir.containsKey(emptTemp)?mapScoreOeilNoir.get(emptTemp)+1:new Integer(1) );											
				}else{
					mapScoreOeilBlanc.put(emptTemp,mapScoreOeilBlanc.containsKey(emptTemp)?mapScoreOeilBlanc.get(emptTemp)+1:new Integer(1) );
				}
			}			
		}
		
		
		Iterator<EmptyGroup> iterVideNoir=mapScoreOeilNoir.keySet().iterator();
		Iterator<EmptyGroup> iterVideBlanc=mapScoreOeilBlanc.keySet().iterator();
		EmptyGroup tempiter=null;
		while (iterVideNoir.hasNext()){
			tempiter=iterVideNoir.next();
			if( mapScoreOeilNoir.get(tempiter)==1){
				if (tempiter.getEmptyStones().size()==1){
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
				if (tempiter.getEmptyStones().size()==1){
					nbBorgneBlanc++;
				}else{
					nbOeilBlanc++;
				}
				
			}else{
				nbYeuxBlanc++;
			}
		}
		
	
		for (Group gblanc: groupeBlanc){
			if( gblanc.getNbLiberty()==0){
				scoreBlanc+=-100;
			}else{
				scoreBlanc+=-gblanc.getNbLiberty()*10;
			}
		}
		
		
		for (Group gNoir: groupeNoir){
			if( gNoir.getNbLiberty()==0){
				scoreNoir+=-100;
			}else{
				scoreNoir+=-gNoir.getNbLiberty()*10;
			}
		}
	
		 
		int score=0;
		if (coul==StoneColor.WHITE){
			scoreNoir=scoreNoir*-1;
			score= scoreBlanc-scoreNoir+nbBorgneBlanc*100+nbBorgneNoir*-100+nbOeilBlanc*-50+nbOeilNoir*50+nbYeuxBlanc*-50+nbYeuxNoir*50;
			
			
		}else{
			scoreBlanc=scoreBlanc*-1;
			score= scoreBlanc-scoreNoir+nbBorgneBlanc*-100+nbBorgneNoir*100+nbOeilBlanc*50+nbOeilNoir*-50+nbYeuxBlanc*50+nbYeuxNoir*-50;
		}
		return score;
	}
	
	
}
