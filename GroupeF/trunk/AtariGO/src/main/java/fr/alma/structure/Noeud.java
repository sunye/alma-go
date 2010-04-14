package fr.alma.structure;

import java.util.ArrayList;

import fr.alma.jeu.Pion;



/**
 * Classe du noeud de l'abre
 * @author lahuidi
 *
 */
public class Noeud {

	private Noeud pere;
	private int note;
	
	private Pion coup;
	public ArrayList<Noeud> listeFils;
	
	/**
	 * Constructeur de la classe.
	 * @param coup valeur initiale du noeud.
	 */
	public Noeud(Pion coup){
		this.pere = null;
		this.note = -1;
		this.coup = coup;
		this.listeFils = new ArrayList<Noeud>();
	}
	
	/**
	 * Retourne la liste des fils du noeud.
	 * @return la liste des fils.
	 */
	public ArrayList<Noeud> getListeFils() {
		return listeFils;
	}

	 /**
	  * Ajoute un fils
	  * @param noeudPere
	  * @param noeud
	  */
	 public void AjouterFils(Noeud noeud){
		 this.getListeFils().add(noeud);
		 noeud.setPere(this);
		
	 }
	/**
	 * Retourne la valeur du noeud.
	 * @return la valeur retourné.
	 */
	public Pion getCoup() {
		return coup;
	}

	/**
	 * Retourne le nombre de fils du noeud.
	 * @return le nombre retourné.
	 */
	public int getNbFils() {
		return this.listeFils.size();
	}

	public void setCoup(Pion coup) {
		this.coup = coup;		
	}

	public void setPere(Noeud pere) {
		this.pere = pere;
	}

	public Noeud getPere() {
		return pere;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public int getNote() {
		return note;
	}

	public boolean existeFils(){
		return this.listeFils.size()!= 0;
	}
	
	
}
