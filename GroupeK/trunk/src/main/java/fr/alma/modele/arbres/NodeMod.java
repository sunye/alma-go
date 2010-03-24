package fr.alma.modele.arbres;



public class NodeMod<E> implements Node<E>{
	
	private int position;
	private String etiquette;
	private E valeur;
	private NodeMod<E> pere;
	private int nbFils;
	
	public NodeMod() {
		nbFils=0;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	
	
	
	public String getEtiquette() {
		return etiquette;
	}
	
	public void setEtiquette(String etiq) {
	this.etiquette=etiq;
		
	}

	public E getValeur() {
		return valeur;
	}

	public void setValeur(E valeur) {
		this.valeur = valeur;
	}

	public NodeMod<E> getPere() {
		return pere;
	}

	public void setPere(NodeMod<E> pere) {
		this.pere = pere;
	}

	public NodeMod(int position, String etiquette, E valeur, NodeMod<E> pere) {
		
		this.position = position;
		this.etiquette = etiquette;
		this.valeur = valeur;
		this.pere = pere;
	}
	
	public int nbFils() {
		return nbFils;
	}
	
	public void countFilsPlus(){
		nbFils++;
	}
	
	public void countFilsMoins(){
		nbFils++;
	}
	

}
