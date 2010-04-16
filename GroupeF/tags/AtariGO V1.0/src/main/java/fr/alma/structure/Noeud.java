package fr.alma.structure;

import java.util.ArrayList;

import fr.alma.jeu.Pion;

/**
 * Class Node of the tree
 * @author ZERBITA Mohamed El Hadi
 *
 */
public class Noeud {

	private Noeud pere;
	private int note;
	
	private Pion coup;
	public ArrayList<Noeud> listeFils;
	
	/**
	 * Constructor of the class.
	 * @param coup initial value of the node.
	 */
	public Noeud(Pion coup){
		this.pere = null;
		this.note = -1;
		this.coup = coup;
		this.listeFils = new ArrayList<Noeud>();
	}
	
	/**
	 * Return the list of the children of a node.
	 * @return the list of children.
	 */
	public ArrayList<Noeud> getListeFils() {
		return listeFils;
	}

	 /**
	  * Add a child
	  * @param noeudPere parent node of the child node
	  * @param noeud child
	  */
	 public void AjouterFils(Noeud noeud){
		 this.getListeFils().add(noeud);
		 noeud.setPere(this);
		
	 }
	/**
	 * Return the value of a node.
	 * @return the value.
	 */
	public Pion getCoup() {
		return coup;
	}

	/**
	 * Return the number of children of a node
	 * @return the number.
	 */
	public int getNbFils() {
		return this.listeFils.size();
	}

	/**
	 * Affect a pawn to a node
	 * @param coup pawn of the node
	 */
	public void setCoup(Pion coup) {
		this.coup = coup;		
	}

	/**
	 * Affect a parent to a node
	 * @param pere parent of a node
	 */
	public void setPere(Noeud pere) {
		this.pere = pere;
	}

	/**
	 * Return the parent of the node
	 * @return the parent
	 */
	public Noeud getPere() {
		return pere;
	}

	/**
	 * Affect a mark to a node
	 * @param note mark of the node
	 */
	public void setNote(int note) {
		this.note = note;
	}

	/**
	 * Return the mark of the node
	 * @return note mark of the node
	 */
	public int getNote() {
		return note;
	}

	/**
	 * Test if a node has a child
	 * @return resultat
	 */
	public boolean existeFils(){
		return this.listeFils.size()!= 0;
	}
	
	
}
