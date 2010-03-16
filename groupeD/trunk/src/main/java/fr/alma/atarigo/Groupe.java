package fr.alma.atarigo;

import java.util.LinkedList;
import java.util.ListIterator;
/**
 * Classe implémentant un groupe de positions d'une meme couleur.
 * @author VINCENT FERREIRA, ADRIEN GUILLE 
 */
public class Groupe implements Cloneable{

	public LinkedList<Position> linkedPions;
	public Pion pion;
	
/**
 * Constructeur
 * @param linkedList liste de pierre
 * @param couleur du groupe
 */
	public Groupe(LinkedList<Position> linkedList, Pion couleur) {
		// TODO Auto-generated constructor stub
		this.linkedPions = linkedList;
		this.pion = couleur;
	}
	
	/**
	 * retourne une copie du groupe
	 * @return groupe clonée
	 */
	public Groupe clone(){
		Groupe groupe = new Groupe(new LinkedList<Position>(),pion);
		Position positionEnCours;
		ListIterator itr = linkedPions.listIterator();
 		while(itr.hasNext()){
 			positionEnCours = (Position)itr.next();
 			groupe.linkedPions.add(new Position(positionEnCours));
 		}		
		return groupe;
	}
	
	/**
	 * retourne si la position fait partie du groupe
	 * @param position
	 * @return vrai si la position fait partie du groupe
	 */
	public boolean hasPos(Position position){
		Position positionEnCours;
		boolean hasPos = false;
		ListIterator itr = linkedPions.listIterator();
 		while(itr.hasNext()){
 			positionEnCours = (Position)itr.next();
 			//System.out.println("position ["+position.lireLigne()+","+position.lireColonne()+"] comparé à ["+positionEnCours.lireLigne()+","+positionEnCours.lireColonne()+"]"); 			
 			if(position.estEgale(positionEnCours)){
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
	public void afficher(){
		//System.out.println("affichage du groupe");
		Position positionEnCours;
		ListIterator itr = linkedPions.listIterator();
		while(itr.hasNext()){
 			positionEnCours = (Position)itr.next();
 			System.out.print("["+positionEnCours.lireLigne()+","+positionEnCours.lireColonne()+"] ");
 		}
		System.out.println("");
	}
	
	/**
	 * ajoute une position dans le groupe
	 * @param position position de la pierre
	 */
	public void ajouter(Position position){
		linkedPions.add(position);
	}
	
	
	
	
}
