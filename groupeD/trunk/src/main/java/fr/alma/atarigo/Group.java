package fr.alma.atarigo;

import java.util.LinkedList;
import java.util.ListIterator;
/**
 * Classe implémentant un groupe de positions d'une meme couleur.
 * @author VINCENT FERREIRA, ADRIEN GUILLE 
 */
public class Group implements Cloneable{

	public LinkedList<Position> linkedStones;
	public Stone stone;
	
/**
 * Constructeur
 * @param linkedList liste de pierre
 * @param color du groupe
 */
	public Group(LinkedList<Position> linkedList, Stone color) {
		// TODO Auto-generated constructor stub
		this.linkedStones = linkedList;
		this.stone = color;
	}
	
	/**
	 * retourne une copie du groupe
	 * @return groupe clonée
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
	 * retourne si la position fait partie du groupe
	 * @param position
	 * @return vrai si la position fait partie du groupe
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
	 * affiche le groupe. utile pour le déboguage
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
	 * ajoute une position dans le groupe
	 * @param position position de la pierre
	 */
	public void add(Position position){
		linkedStones.add(position);
	}
	
	
	
	
}
