package go;

import java.util.LinkedList;

/**
 * @author Frédéric Dumonceaux
 *
 */


public class Tree {
	
	byte x;
	byte y;
	

	public Tree(){
	
	}
	

	public Tree(Move m){
		
	}
	
	
	public Tree creerArbre(Node n){
		Tree a = new Tree();
		return a;
		
	}


	public Tree ajouterFils(Node n){
		Tree a = new Tree();
		return a;
		
	}


	public Tree ajouterFils(Node child, Node parent){
		Tree a = new Tree();
		return a;
		
	}
	
 	
 	public Tree racine(){
 		Tree a = new Tree();
		return a;
		
	}
 	
 	
	public Node rootNode(){
		Move m = new Move (x, y);
		Node a = new Node(m);
		return a;
	}
 	
	
	public Node node(Move m){
		Node a = new Node(m);
		return a;
	}
 	
 	
 	public Node pere(Node n){
		return n;
	}
 	
}
