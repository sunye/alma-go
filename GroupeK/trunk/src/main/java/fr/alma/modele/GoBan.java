package fr.alma.modele;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
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
 * Class that represent a goban board and is rules
 * @author Manoël Fortun et Anthony "Bambinôme" Caillaud
 * 
 */
public class GoBan {

	/**
	 * The current board 
	 */
	private Stone[][] goban;
	
	/**
	 * the color of the current player
	 */
	private StoneColor turn;
	
	
	/**
	 * The groups of stone
	 */
	private HashSet<Group> group;
	
	/**
	 * special group for empty slot
	 */
	private Group emptyStoneGroup;
	
	/**
	 * Array with 1 and -1 used to move in the matrix 
	 */
	private static int[] modifier={1,-1};
	
	/**
	 * Size of the board
	 */
	public static int GO_BAN_SIZE=9;
	
	/**
	 * Number of black token captured by the white
	 */
	private int whiteScore=0;
	
	/**
	 * Number of white token captured by the black
	 */
	private int blackScore=0;

	/**
	 * Simple constructor that (re)set the game to an initial stat
	 */
	public GoBan() {
		this.goban = new Stone[9][9];
		this.group= new HashSet<Group>();
		this.emptyStoneGroup=new Group(StoneColor.EMPTY);
		this.turn= StoneColor.BLACK;
		this.reset();
	}
	
	

	/**
	 * Reset the game stat
	 */
	public void reset(){
		this.group.clear();
		emptyStoneGroup= new Group(StoneColor.EMPTY);
		for (int i=0; i<9; i++){
			for (int k=0; k<9; k++) {
				goban[i][k] = new Stone(i,k,StoneColor.EMPTY);
				goban[i][k].setGroup(emptyStoneGroup);
				emptyStoneGroup.addPion(goban[i][k]);
			}
		}
		this.whiteScore=0;
		this.blackScore=0;
		this.turn= StoneColor.BLACK;
	}
	
	
	/**
	 * @param tour the tour to set
	 */
	public void setTurn(StoneColor tour) {
		this.turn = tour;
	}

	/**
	 * @return the tour
	 */
	public StoneColor getTurn() {
		return turn;
	}

	/**
	 * @return the goban
	 */
	public Stone[][] getGoban() {
		return goban;
	}

	/**
	 * @param goban the goban to set
	 */
	public void setGoban(Stone[][] goban) {
		this.goban = goban;
	}

	/**
	 * @return the group
	 */
	public HashSet<Group> getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(HashSet<Group> group) {
		this.group = group;
	}

	/**
	 * @return the groupeVide
	 */
	public Group getEmptyStoneGroup() {
		return emptyStoneGroup;
	}

	/**
	 * @param groupeVide the groupeVide to set
	 */
	public void setEmptyStoneGroup(Group groupeVide) {
		this.emptyStoneGroup = groupeVide;
	}

	/**
	 * @return the ptsBlanc
	 */
	public int getWhiteScore() {
		return whiteScore;
	}

	/**
	 * @param ptsBlanc the ptsBlanc to set
	 */
	public void setWhiteScore(int ptsBlanc) {
		this.whiteScore = ptsBlanc;
	}

	/**
	 * @return the ptsNoir
	 */
	public int getBlackScore() {
		return blackScore;
	}

	/**
	 * @param ptsNoir the ptsNoir to set
	 */
	public void setBlackScore(int ptsNoir) {
		this.blackScore = ptsNoir;
	}
	
	// Utils methods

	/**
	 * Calculate if the move is allowed
	 * @return the type of move: Valid, NonValid, Prise
	 */
	public MoveType isMoveAllowed(Coordinate cood, StoneColor coul) {
		
		if( !cood.isValid(GoBan.GO_BAN_SIZE)){
			return MoveType.NONVALID;
		}
			
		Stone pi=goban[cood.getX()][cood.getY()];
		
		if (pi.getColor()!=StoneColor.EMPTY){
			return MoveType.NONVALID;
		}
		
		List<Stone> voisin= getNeigbors(pi);

		for (Stone v:voisin){
			if( v.getColor()==StoneColor.oppose(coul)){
				if (v.getGroup().getNbLiberty()==1){
					return MoveType.CAPTURE;
				}
			} else if (v.getColor()==coul){
				if( v.getGroup().getNbLiberty()!=1){
					return MoveType.VALID;
				}
			}else if (v.getColor()==StoneColor.EMPTY){
				return MoveType.VALID;
			} 
		}
		voisin.clear();
		
		return MoveType.NONVALID;
	}

	/**
	 * Calculate the liberty of a stone
	 * @param pi the stone
	 * @return list of his liberty
	 */
	public List<Coordinate> calculStoneLiberty(Stone pi){
		List<Coordinate> result= new Vector<Coordinate>();
				
		List<Stone> voisin= getNeigbors(pi);
		
		for (Stone v:voisin){
			if( v.getColor()==StoneColor.EMPTY){
				result.add(v.getPosition());
			}
		}
		voisin.clear();

		return result;
	}

	/**
	 * Obtain the neighbors of a stone
	 * @param pi
	 * @return list of the neighbors
	 */ 
	public List<Stone> getNeigbors(Stone pi){
		List<Stone> listVoisin= new Vector<Stone>();
		int x=pi.getPosition().getX();
		int y=pi.getPosition().getY();
				
		for (int i=0; i<modifier.length;i++){
			Coordinate coordtemp=new Coordinate(x+modifier[i], y);
			if (coordtemp.isValid(GoBan.GO_BAN_SIZE)){
				listVoisin.add(goban[coordtemp.getX()][coordtemp.getY()]);
			}
		}
		for (int i=0; i<modifier.length;i++){
			Coordinate coordtemp=new Coordinate(x, y+modifier[i]);
			if (coordtemp.isValid(GoBan.GO_BAN_SIZE)){
				listVoisin.add(goban[coordtemp.getX()][coordtemp.getY()]);
			}
		}
		
		return listVoisin;
		
	}
	//end of Utils methods	
	
	
	//Goban pion(stone) public management method
	/**
	 * Add a stone on the board with capture enabled (classical play)
	 * @param cood the coordinate
	 * @return if the stone where add
	 */
	public boolean addPion(Coordinate cood){
		
		
		boolean jouer=isMoveAllowed(cood, turn)!=MoveType.NONVALID;
		if (jouer){
			this.addPion(cood, turn, true);
			
		}
		
		return jouer;
	}
	
	/**
	 * add a stone on the board with capture disabled (AI calculation play)
	 * @param cood the coorninate
	 * @param cc the coolor
	 * @return if the stone where add
	 */
	public boolean addPion(Coordinate cood, StoneColor cc){
		boolean jouer =isMoveAllowed(cood, cc)!=MoveType.NONVALID;
		if (jouer){
		this.addPion(cood, cc, false);
		}
		return jouer;
	}
	
	/**
	 * remove a stone of the board (AI calculation play)
	 * @param cood the coordinate of the stone
	 * @param coul the color	
	 */
	public void removeStone(Coordinate cood, StoneColor coul){
		removeStone(goban[cood.getX()][cood.getY()]);
	}

	//end of Goban pion(stone) public management method
	
	/**
	 * Add a stone on the board
	 * @param cood the coordinate
	 * @param coul the color of the stone to add
	 * @param enlevement if captured stone has to be remove
	 */
	private void addPion(Coordinate coord, StoneColor coul, boolean enlevement){
	
		Stone pi=goban[coord.getX()][coord.getY()];
		emptyStoneGroup.removeStone(pi);
		
		pi.setColor(coul);
		pi.setGroup(new Group(coul));
		pi.getGroup().addPion(pi);
		List<Stone> voisin= getNeigbors(pi);
			
		for (Stone v:voisin){
			if (v.getColor()==StoneColor.EMPTY){
				pi.getGroup().addLiberty(v.getPosition());
			}else if( 
				v.getColor()== StoneColor.oppose(pi.getColor())){
				v.getGroup().removeLiberty(pi.getPosition());
			}else if(v.getColor()== pi.getColor()){
				group.remove(v.getGroup());
				pi.getGroup().fusionGroup(v.getGroup());
				pi.getGroup().removeLiberty(pi.getPosition());
			}
		}

		group.add(pi.getGroup());
		
		
		if (enlevement){
			for (Stone v : voisin) {
				if ((v.getColor() == StoneColor.oppose(pi.getColor()))
						&& (v.getGroup().getNbLiberty() == 0)) {
					removeStoneAndGroup(v.getGroup());
				}
			}
		}
		
		voisin.clear();
		turn= StoneColor.oppose(turn);
	}
	
	/**
	 * remove a stone from the board (not capture, just remove a move) 
	 * @param pi
	 */
	private void removeStone(Stone pi){
		int x=pi.getPosition().getX();
		int y=pi.getPosition().getY();
		
		StoneColor coulinitial=pi.getColor();
		Group groupInitial=goban[x][y].getGroup();
		
		goban[x][y].getGroup().removeStone(goban[x][y]);
		group.remove(groupInitial);
		
		
		goban[x][y].setColor(StoneColor.EMPTY); 
		goban[x][y].setGroup(emptyStoneGroup);
		emptyStoneGroup.addPion(goban[x][y]);
		
		
		List<Stone> voisin= getNeigbors(pi);
		for (Stone v:voisin){
			if( v.getColor()== StoneColor.oppose(coulinitial)){
				v.getGroup().addLiberty(pi.getPosition());
			}else if(v.getColor()== coulinitial){
				Group newGroup= new Group(v.getColor());
				group.add(newGroup);
				reformGroup(newGroup, v);
			}
		}
		
		voisin.clear();
		turn= StoneColor.oppose(turn);
	}
	
	/**
	 * remove a group that is captured
	 * @param grop
	 */
	private void removeStoneAndGroup(Group grop){
		this.group.remove(grop);
		
		if (grop.getGroupColor()==StoneColor.WHITE){
			blackScore+=grop.getStoneNumber();
		}else{
			whiteScore+=grop.getStoneNumber();
		}
		
		
		Collection<Stone> list= grop.getStones();
		for (Stone v:list){
			v.setGroup(emptyStoneGroup);
			v.setColor(StoneColor.EMPTY);
			emptyStoneGroup.addPion(v);
		}
		
		for(Stone v: list){
			Collection<Stone> voisinVoisin=getNeigbors(v);
			for (Stone capt: voisinVoisin){
				Collection <Coordinate> libertes=calculStoneLiberty(capt);
				capt.getGroup().addLibertys(libertes);
				libertes.clear();
			}
		}
		grop.clear();
		
	}
	
	/**
	 * used to reform the group the removal of a stone (backtracking)
	 * @param grop the new group for the stone
	 * @param pi the stone
	 */
	private void reformGroup(Group grop, Stone pi){
		if( pi.getColor()==grop.getGroupColor()){
			pi.setGroup(grop);
			grop.addPion(pi);
			Collection<Coordinate> libert=calculStoneLiberty(pi);
			grop.addLibertys(libert);
			libert.clear();
			
			int x=pi.getPosition().getX();
			int y=pi.getPosition().getY();
			Stone avisiter=null;
			
			for (int i=0; i<modifier.length;i++){
					Coordinate coordtemp=new Coordinate(x+modifier[i], y);
					if (coordtemp.isValid(GoBan.GO_BAN_SIZE)){
						avisiter=goban[coordtemp.getX()][coordtemp.getY()];
						reformGroupBackTrackEnd(grop, avisiter, modifier[i]*-1, 0);
					}
				
			}
			for (int i=0; i<modifier.length;i++){
				Coordinate coordtemp=new Coordinate(x, y+modifier[i]);
				if (coordtemp.isValid(GoBan.GO_BAN_SIZE)){
					avisiter=goban[coordtemp.getX()][coordtemp.getY()];
					reformGroupBackTrackEnd(grop, avisiter, 0, modifier[i]*-1);
				}
			}
		}
	}
	
	/**
	 * Don't call it. It used by the reformeGroup Method (backtracking)
	 * @param grop
	 * @param pi
	 * @param intmodifierx
	 * @param intmodifiery
	 */
	private void reformGroupBackTrackEnd(Group grop, Stone pi, int intmodifierx, int intmodifiery){
		
		if( pi.getColor()==grop.getGroupColor()){
			pi.setGroup(grop);
			grop.addPion(pi);
			
			Collection<Coordinate> libert=calculStoneLiberty(pi);
			grop.addLibertys(libert);
			libert.clear();
			
			int x=pi.getPosition().getX();
			int y=pi.getPosition().getY();
			Stone avisiter=null;
			
			for (int i=0; i<modifier.length;i++){
				if(intmodifierx!= modifier[i]){
					Coordinate coordtemp=new Coordinate(x+modifier[i], y);
					if (coordtemp.isValid(GoBan.GO_BAN_SIZE)){
						avisiter=goban[coordtemp.getX()][coordtemp.getY()];
						reformGroupBackTrackEnd(grop, avisiter, modifier[i]*-1, 0);
					}
				}
			}
			for (int i=0; i<modifier.length;i++){
				if(intmodifiery!= modifier[i]){
					Coordinate coordtemp=new Coordinate(x, y+modifier[i]);
					if (coordtemp.isValid(GoBan.GO_BAN_SIZE)){
						avisiter=goban[coordtemp.getX()][coordtemp.getY()];
						reformGroupBackTrackEnd(grop, avisiter, 0, modifier[i]*-1);
					}
				}
			}
		}
	}
}
