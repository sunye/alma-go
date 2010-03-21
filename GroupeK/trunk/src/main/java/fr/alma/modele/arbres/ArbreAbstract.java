package fr.alma.modele.arbres;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public abstract class ArbreAbstract<E> implements Arbre<E> {

	private int arrite;
	
	
	
	public ArbreAbstract(int arrite ) {
		this.arrite=arrite;
		
	}



	public int getArrite() {
		return arrite;
	}



	
	
	
	/*
	TS(r : noeud racine )
	-Noeud n
	-Ensemble E
	-E.insérer(r)
	-tant que E≠vide faire
	-n <- E.extraire()
	-E <- E \ {n}
	-pour i de 1 à k faire
	E.insérer(n.Fils(i))
	fin pour
	fin tant que
	
	E = pile => parcours en
profondeur d’abord (Depthfirst
search, DFS)
	*
	*E = file => parcours en
largeur d’abord (Breadth-first
search, BFS)
	*
	*
	*
	*/
	
	public void parcoursProfondeur() {
		Stack<Node<E>> pile=new Stack<Node<E>>();
		Node<E> n=null;
		System.out.println("Parcours en profondeur: ");
		Node<E> temp=null;
		pile.push(this.racine());
		while (!pile.isEmpty()){
		n=pile.pop();
		System.out.println(n.getEtiquette());
		if (n!=null){
		for (int i=0 ; i<getArrite();i++){
			try {
				temp=this.iemeFils(n, i+1);
			} catch (ConflitArriteException e) {}
			if (temp!=null){
				pile.push(temp);
			}
		}
			
			
		}
		}
		System.out.println("\n");
	}

	
	
	public void parcoursLargeur(){
		Queue<Node<E>> file =new LinkedList<Node<E>>();
		System.out.println("Parcours en largeurs: ");
		Node<E> n=null;
		Node<E> temp=null;
		file.offer(this.racine());
		while (!file.isEmpty()){
		n=file.remove();
		System.out.println(n.getEtiquette());
		if (n!=null){
		for (int i=0 ; i<getArrite();i++){
			try {
				temp=this.iemeFils(n, i+1);
			} catch (ConflitArriteException e) {}
			if (temp!=null){
				file.offer(temp);
			}
		}
			
			
		}
		}
		System.out.println("\n");
		
	}
	
	
//utilisation du parcours en profondeur pour l'affichage avec liste d'adjacence 	
	public void affichageListeAdj() {
		
		Stack<Node<E>> pile=new Stack<Node<E>>();
		Node<E> n=null;
		Node<E> temp=null;
		pile.push(this.racine());
		String result;
		while (!pile.isEmpty()){
		n=pile.pop();
		result="";
		result+= "noeud: "+n.getEtiquette() + " fils :";
		if (n!=null){
		for (int i=0 ; i<getArrite();i++){
			try {
				temp=this.iemeFils(n, i+1);
			} catch (ConflitArriteException e) {}
			if (temp!=null){
				pile.push(temp);
				result+=" " + temp.getEtiquette() +",";
				
			}
		}
		System.out.println(result.substring(0, result.length()-1));	
		
			
		}
		}
	}
	
	
	
	//utilisation du parcours en largeur pour l'affichage avec liste noeud puis liste noeud arc 
	public void affichageNoeudArc(){
		
		Queue<Node<E>> file =new LinkedList<Node<E>>();
		//file.offer() ajout
		String listeNoeud="Liste des noeuds: (";
		String listeArc="Liste des arcs: (";
		Node<E> n=null;
		Node<E> temp=null;
		file.offer(this.racine());
		while (!file.isEmpty()){
		n=file.remove();
		listeNoeud+= n.getEtiquette()+",";
		if (n!=null){
		for (int i=0 ; i<getArrite();i++){
			try {
				temp=this.iemeFils(n, i+1);
			} catch (ConflitArriteException e) {}
			if (temp!=null){
				listeArc+=n.getEtiquette()+"->"+temp.getEtiquette()+",";
				file.offer(temp);
			}
		}
			
			
		}
		}
		listeNoeud=listeNoeud.substring(0, listeNoeud.length()-1)+")";
		listeArc=listeArc.substring(0, listeArc.length()-1)+")";
		System.out.println(listeNoeud);
		System.out.println(listeArc);
		
		
	}
	
	@Override
	public int profondeur(Node<E> n) {
		Node<E> temp=n.getPere();
		int result=0;
		while (temp!=null){
			result++;
			temp=temp.getPere();
		}
	
		return result ;
	}
}
