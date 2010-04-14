package fr.alma.structure;

import java.util.ArrayList;

import fr.alma.jeu.Pion;



/**
 * Classe du noeud de l'arbre
 * @author ZERBITA Mohamed El Hadi
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
	  * @param noeudPere noeud pere du fils
	  * @param noeud fils
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

	/**
	 * Affecte le pion au noeud
	 * @param coup pion du noeud
	 */
	public void setCoup(Pion coup) {
		this.coup = coup;		
	}

	/**
	 * Affecte un pere au noeud
	 * @param pere pere du noeud
	 */
	public void setPere(Noeud pere) {
		this.pere = pere;
	}

	/**
	 * Retourne le pere d'un noeud
	 * @return le pere
	 */
	public Noeud getPere() {
		return pere;
	}

	/**
	 * Affecte une note au noeud
	 * @param note note du noeud
	 */
	public void setNote(int note) {
		this.note = note;
	}

	/**
	 * Retourne la note du noeud
	 * @return note note du noeud
	 */
	public int getNote() {
		return note;
	}

	/**
	 * Test si un noeud a des fils
	 * @return resultat
	 */
	public boolean existeFils(){
		return this.listeFils.size()!= 0;
	}
	
	
}
