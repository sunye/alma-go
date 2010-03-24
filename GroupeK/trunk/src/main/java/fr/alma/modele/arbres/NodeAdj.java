package fr.alma.modele.arbres;


import java.util.Iterator;
import java.util.LinkedList;

public class NodeAdj<E> implements Node<E> {
	
	


	private String etiquette;
	private NodeAdj<E> pere;
	private E valeur;
	private LinkedList<NodeAdj<E>> fils;
	private int nbFils;
	
	public NodeAdj(int arrite) {
		this.fils=new LinkedList<NodeAdj<E>>();
		for (int i=0; i<arrite;i++){
			fils.add(null);
		}
		nbFils=0;
		
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

	public NodeAdj<E> getPere() {
		return pere;
	}

	public void setPere(NodeAdj<E> pere) {
		this.pere = pere;
	}

	public LinkedList<NodeAdj<E>> getFils() {
		return fils;
	}

	public void setFils(LinkedList<NodeAdj<E>> fils) {
		this.fils = fils;
	}
	
	public void addFils(NodeAdj<E> fils){
		fils.addFils(fils);
		
		
	}
	
	public NodeAdj<E> rechercher( String etiq){
		
		for (int i=0; i<this.fils.size();i++){
			NodeAdj<E> mesfils=this.fils.get(i);
			if (mesfils!=null){
				if (mesfils.getEtiquette().equals(etiq)){
					return mesfils;
				}
			}
		}
		
		NodeAdj<E> temp=null;
		for (int i=0; i<this.fils.size();i++){
			NodeAdj<E> fils=this.fils.get(i);
			if (fils!=null){
				temp=fils.rechercher(etiq);
			  if (temp!=null ){
				  return temp;
			  }
			}
		}
		return null;
	}
	
	
	
	public boolean hasFils(){
		
		boolean result= false; 
		
		Iterator<NodeAdj<E>> ite=this.fils.iterator();
		while (ite.hasNext()){
			NodeAdj<E> temp=ite.next();
			if( temp!=null){
				result=true;
			}
	}

		return result;
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
