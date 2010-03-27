package fr.alma.atarigo;

import java.util.LinkedList;
import java.util.ListIterator;
/**
 * Group.java represents a group of stones from the same color.
 * @author VINCENT FERREIRA, ADRIEN GUILLE 
 */
public class Group implements Cloneable{

	public LinkedList<Position> linkedStones;
	public Stone stone;
	
/**
 * Logic constructor
 * @param linkedList list of stones
 * @param color of the group
 */
	public Group(LinkedList<Position> linkedList, Stone color) {
		// TODO Auto-generated constructor stub
		this.linkedStones = linkedList;
		this.stone = color;
	}
	
	/**
	 * retourne une copy of the group
	 * @return cloned group
	 */
	public Group clone(){
		Group group = new Group(new LinkedList<Position>(),stone);
		Position currentPosition;
		ListIterator itr = linkedStones.listIterator();
 		while(itr.hasNext()){
 			currentPosition = (Position)itr.next();
 			group.linkedStones.add(new Position(currentPosition));
 		}		
		return group;
	}
	
	/**
	 * return if the position is in the group
	 * @param position
	 * @return true if the position is in the group
	 */
	public boolean hasPos(Position position){
		Position currentPosition;
		boolean hasPos = false;
		ListIterator itr = linkedStones.listIterator();
 		while(itr.hasNext()){
 			currentPosition = (Position)itr.next();
 			//System.out.println("position ["+position.lireLigne()+","+position.lireColonne()+"] comparé à ["+currentPosition.lireLigne()+","+currentPosition.lireColonne()+"]"); 			
 			if(position.isEqual(currentPosition)){
 				//System.out.println("estvrai");
 				hasPos = true;
 			}
 		}
		return hasPos;
	}
	
	/**
	 * print the group. Usefull for debugging.
	 *
	 */
	public void print(){
		//System.out.println("affichage du groupe");
		Position currentPosition;
		ListIterator itr = linkedStones.listIterator();
		while(itr.hasNext()){
 			currentPosition = (Position)itr.next();
 			System.out.print("["+currentPosition.getLine()+","+currentPosition.getColumn()+"] ");
 		}
		System.out.println("");
	}
	
	/**
	 * add a position in the group
	 * @param position position of the stone
	 */
	public void add(Position position){
		linkedStones.add(position);
	}
	
	
	
	
}
