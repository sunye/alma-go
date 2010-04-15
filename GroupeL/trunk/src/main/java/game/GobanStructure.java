/*$Author$ 
 * $Date$ 
 * $Revision$ 
 * 
 *Copyright (C) 2010  Dejean Charles and Pottier Vincent
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
 * @author Dejean Charles - Pottier Vincent
 * 
 * Classe permettant de gere un plateau de go. 
 * 
 */

package game;

import java.util.LinkedList;
import java.util.List;

public class GobanStructure {
	
	private List<GroupPawns> whites;
	private List<GroupPawns> blacks;
	private Integer size;
	
	// goban's representation where in each case we have a reference to a GroupPawn   
	private GroupPawns[][] goban; 
	
	/* Getters - Setters */		
	public List<GroupPawns> getWhites() {
		return whites;
	}
	public List<GroupPawns> getBlacks() {
		return blacks;
	}
	public Integer getSize() {
		return size;
	}
	public GroupPawns[][] getGoban() {
		return goban;
	}
	
	/**
	 * unction to get list of pawn's group of a specify color  
	 * @param col
	 * @return
	 */
	public List<GroupPawns> getGroups(Color col){
		if(col == Color.WHITE){
			return whites;
		}else if (col == Color.BLACK){
			return blacks;
		}
		return null;
	}

	/**
	 * function to get list of free coordinates of the goban
	 * @return
	 */
	public List<Coordinates> getFreeCoord(){
		
		LinkedList<Coordinates> lcl = new LinkedList<Coordinates>();
		
		for(int y=1 ; y<= size ; y++){
			for(int x=1 ; x<=size ; x++){

				if(goban[x][y]==null){
					lcl.add(new Coordinates(x, y));
				}
			}
		}

		
		return lcl;
	}
	
	
	
	/* Constructor */	
	public GobanStructure() {
		super();
		whites = new LinkedList<GroupPawns>();
		blacks = new LinkedList<GroupPawns>();
		size = 9;
		/* we take the boban with 2 line and row for always have a free place around */
		goban = new GroupPawns[size+2][size+2];
		
	}
	
	public GobanStructure(Integer t) {
		super();
		whites = new LinkedList<GroupPawns>();
		blacks = new LinkedList<GroupPawns>(); 		
		this.size = t;
		/* we take the boban with 2 line and row for always have a free place around */
		goban = new GroupPawns[size+2][size+2];
	}
	
	/* Fonctions */
	
	/**
	 * this function return what he have at a position 
	 * @param coord : coordinates of the testing position;
	 * @return 0 : free position
	 *         1 : white position 
	 *         2 : black position 
	 *         -1 : outside position
	 * 
	 */
	public Integer testPosition(Coordinates coord){
		return testPosition(coord.getX(),coord.getY());
	}
	
	/**
	 * this function return what he have at a position
	 * @param x,y : coordonnee de la position a tester;
	 * @return 0 : free position
	 *         1 : white position 
	 *         2 : black position 
	 *         -1 : outside position
	 */
	public Integer testPosition(Integer x, Integer y){
		
		Coordinates coord = new Coordinates(x,y);
		
		if(coord.getX()>size || coord.getY()>size || coord.getX()<=0 || coord.getY()<=0){
			return -1;
		}
				
		if( (goban[x][y]) == null ){
			return 0;
		}else if((goban[x][y]).getColor() == Color.BLACK){
			return 2;
		}
		
		return 1;
	}	

	
	/**
	 * @param coord : coordinate of the new pawn
	 * @param couleur : color of the new pawn
	 * @return true if the add is Completed
	 */
	public boolean addPawn(Coordinates coord, Color couleur){
		
		if (testPosition(coord)==0 ){
			/* it's a free position */
			
			/* we create a new groupPawn with the new coordinate */
			GroupPawns newGroup=new GroupPawns(coord,testFreedom(coord), couleur);
			
			/* we merge the adjacent's group */
			for(GroupPawns g : groupsAdjacents(coord,couleur)){
				newGroup.getPawns().addAll(g.getPawns());
				
				if(couleur == Color.WHITE){
					whites.remove(g);
				}else{
					blacks.remove(g);
				}
			}
						
			/* calculation of the freedom's number of the new groups */
			newGroup.setFreedoms(calculatedFreedom(newGroup));
			
			getGroups(couleur).add(newGroup);
			
			/* we decrease the freedom's number of the adjacent groups of enemy's color */
			for(GroupPawns g : groupsAdjacents(coord,couleur.invColor())){
				g.setFreedoms(g.getFreedoms()-1);
			}
			
			/* update of the goban */
			for(Coordinates c : newGroup.getPawns()){
				goban[c.getX()][c.getY()] = newGroup;
			}			
					
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * remove pawn from the goban
	 * @param coord
	 * @return
	 */
	public boolean removePawn(Coordinates coord){
		
		if( testPosition(coord) > 0 ){
			
			GroupPawns gc = goban[coord.getX()][coord.getY()];			
					
			for(Coordinates p : gc.getPawns()){
				if(p.equals(coord)){
					// remove the pawn of the group
					gc.getPawns().remove(p);
					// divide group
					LinkedList<GroupPawns> groupAdj = gc.divideGroup();
					// reset goban's case
					goban[coord.getX()][coord.getY()] = null;
					// remove the group of the groups list
					getGroups(gc.getColor()).remove(gc);
					
					// calculation of freedoms for each adjacent group of the opposing color
					for(GroupPawns g : groupsAdjacents(coord, gc.getColor().invColor())){
						g.setFreedoms(calculatedFreedom(g));
					}
					
					// calculation of freedoms of each group born in the divide
					// and reset goban
					for(GroupPawns g : groupAdj){
						g.setFreedoms(calculatedFreedom(g));
						for(Coordinates c : g.getPawns()){
							goban[c.getX()][c.getY()] = g;
						}
					}
					
					// add new groups
					getGroups(gc.getColor()).addAll(groupAdj);
					
					break;
				}
			}
			
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * calculation of freedoms of a group's pawn
	 * @param newGroup
	 * @return
	 */
	private Integer calculatedFreedom(GroupPawns newGroup) {
		
		LinkedList<Coordinates> coordLib = new LinkedList<Coordinates>();
		
		for(Coordinates coord : newGroup.getPawns()){
			
			if(testPosition(coord.getX()+1, coord.getY()) == 0){
				Coordinates nouvCoord = new Coordinates(coord.getX()+1, coord.getY());
				boolean dejaVu = false;
				for(Coordinates cl : coordLib){
					dejaVu = dejaVu || nouvCoord.equals(cl);
				}
				for(Coordinates cg : newGroup.getPawns()){
					dejaVu = dejaVu || nouvCoord.equals(cg);
				}
				if(!dejaVu){
					coordLib.add(nouvCoord);
				}
			}
			if(testPosition(coord.getX()-1, coord.getY()) == 0){
				Coordinates nouvCoord = new Coordinates(coord.getX()-1, coord.getY());
				boolean dejaVu = false;
				for(Coordinates cl : coordLib){
					dejaVu = dejaVu || nouvCoord.equals(cl);
				}
				for(Coordinates cg : newGroup.getPawns()){
					dejaVu = dejaVu || nouvCoord.equals(cg);
				}
				if(!dejaVu){
					coordLib.add(nouvCoord);
				}
			}
			if(testPosition(coord.getX(), coord.getY()+1) == 0){
				Coordinates nouvCoord = new Coordinates(coord.getX(), coord.getY()+1);
				boolean dejaVu = false;
				for(Coordinates cl : coordLib){
					dejaVu = dejaVu || nouvCoord.equals(cl);
				}
				for(Coordinates cg : newGroup.getPawns()){
					dejaVu = dejaVu || nouvCoord.equals(cg);
				}
				if(!dejaVu){
					coordLib.add(nouvCoord);
				}
			}
			if(testPosition(coord.getX(), coord.getY()-1) == 0){
				Coordinates nouvCoord = new Coordinates(coord.getX(), coord.getY()-1);
				boolean dejaVu = false;
				for(Coordinates cl : coordLib){
					dejaVu = dejaVu || nouvCoord.equals(cl);
				}
				for(Coordinates cg : newGroup.getPawns()){
					dejaVu = dejaVu || nouvCoord.equals(cg);
				}
				if(!dejaVu){
					coordLib.add(nouvCoord);
				}
			}		
		}		

		return coordLib.size();
	}
	
	/**
	 * @param coord
	 * @return freedom's number of one pawn
	 */
	private Integer testFreedom(Coordinates coord) {
		Integer lib=0;
		
		if( testPosition(coord.getX()+1, coord.getY()) == 0 ){
			++lib;
		}
		if( testPosition(coord.getX()-1, coord.getY()) == 0 ){
			++lib;
		}
		if( testPosition(coord.getX(), coord.getY()+1) == 0 ){
			++lib;
		}
		if( testPosition(coord.getX(), coord.getY()-1) == 0 ){
			++lib;
		}

		return lib;
	}
	
	/**
	 * @param coord
	 * @param col : color of the groups
	 * @return all adjacent group of a specify color
	 */
	private List<GroupPawns> groupsAdjacents(Coordinates coord , Color col){
		
		List<GroupPawns> gadj = new LinkedList<GroupPawns>();
		
		GroupPawns gt = goban[coord.getX()-1][coord.getY()];
		if( (gt != null) && ( gt.getColor() == col) ){
			gadj.add(gt);
		}
		
		gt = goban[coord.getX()+1][coord.getY()];
		if( (gt != null) && ( gt.getColor() == col) && !gadj.contains(gt)){
			gadj.add(gt);
		}
		
		gt=goban[coord.getX()][coord.getY()-1];
		if( (gt != null) && ( gt.getColor() == col) && !gadj.contains(gt)){
			gadj.add(gt);
		}
		
		gt=goban[coord.getX()][coord.getY()+1];
		if( (gt != null) && ( gt.getColor() == col) && !gadj.contains(gt)){
			gadj.add(gt);
		}

		return gadj;
	}
	
	/**
	 * 
	 * @param player
	 * @return true if the player win the game
	 */
	public boolean isWinner(Color player){
		
		for(GroupPawns g : getGroups(player.invColor()) ){
			if(g.getFreedoms() == 0){
				return true;
			}
		}

		return false;
	}

	/**
	 * suicide test
	 * @param coord
	 * @param col
	 * @return true if play this pawn is a suicide
	 */
	public boolean moveValid(Coordinates coord, Color col){
		
		boolean ok=false;
		
		/* test if the case is free*/
		if(goban[coord.getX()][coord.getY()] == null){
			ok=true;
			/* is the case has no freedom */
			if(testFreedom(coord) == 0){
				ok=false;
				/* testing if the adjacent group of the player color have just one freedom */
				for(GroupPawns ga : groupsAdjacents(coord, col)){
					ok = ok || (ga.getFreedoms() > 1)  ;
				}
				
				if(!ok){
					/* if it's a suicide do we take a opposing group */
					for(GroupPawns ga : groupsAdjacents(coord, col.invColor())){
						ok = ok || (ga.getFreedoms() == 1) ;
					}
				}	
			}
		}
		return ok;
	}

	
}
