package test;

import java.util.LinkedList;

import jeu.Coordonnees;
import jeu.Couleur;
import jeu.GroupePieces;

public class testSimple {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		GobanStructure jeu = new GobanStructure();
		Couleur couleur = Couleur.blanc;
		
		jeu.ajoutPiece(new Coordonnees(1, 1), couleur);
		jeu.ajoutPiece(new Coordonnees(1, 2), couleur);
		jeu.ajoutPiece(new Coordonnees(1, 3), couleur);
		jeu.ajoutPiece(new Coordonnees(1, 4), couleur);
		jeu.ajoutPiece(new Coordonnees(1, 5), couleur);
		jeu.ajoutPiece(new Coordonnees(2, 3), couleur);
		jeu.ajoutPiece(new Coordonnees(3, 2), couleur);
		jeu.ajoutPiece(new Coordonnees(3, 3), couleur);
		jeu.ajoutPiece(new Coordonnees(3, 4), couleur);
		*/
		
		Coordonnees sup = new Coordonnees(1, 3);
		
		GroupePieces gp = new GroupePieces();
		gp.setCouleur(Couleur.blanc);
		gp.getPieces().add(sup);


		gp.getPieces().remove(sup);		
		LinkedList<GroupePieces> test = gp .separeGroupe();
		
		for(GroupePieces g : test){
			
			for(Coordonnees c : g.getPieces()){
				System.out.print(c.toString()+" ");
			}
			System.out.println();
			System.out.println(" ---------- ");
		}
		
	}

}
