package test;

import java.util.Scanner;

import jeu.Coordonnees;
import jeu.Couleur;
import jeu.GobanStructure;
import jeu.GroupePieces;

public class mainTest {

	
	public static void main(String args[]){
	
		GobanStructure gb = new GobanStructure();
		
		Scanner scInt = new Scanner(System.in);
		 
		Couleur joueur = Couleur.noir;
		boolean ok = true;
		
		/* placage de pion pour les test */
		
		/* on fait un oeil */
		gb.ajoutPiece(new Coordonnees(6, 6), Couleur.noir);
		gb.ajoutPiece(new Coordonnees(5, 5), Couleur.noir);
		gb.ajoutPiece(new Coordonnees(4, 5), Couleur.noir);
		gb.ajoutPiece(new Coordonnees(3, 6), Couleur.noir);
		gb.ajoutPiece(new Coordonnees(4, 7), Couleur.noir);
		gb.ajoutPiece(new Coordonnees(5, 7), Couleur.noir);
		
		gb.ajoutPiece(new Coordonnees(3, 5), Couleur.blanc);
		gb.ajoutPiece(new Coordonnees(2, 6), Couleur.blanc);
		gb.ajoutPiece(new Coordonnees(3, 7), Couleur.blanc);
		
		gb.ajoutPiece(new Coordonnees(2, 2), Couleur.blanc);
		gb.retirePiece(new Coordonnees(2, 2));
		
		/* ----------------------------- */
		
		do{
			joueur = joueur.invCouleur();

			affGB(gb);
			if(joueur == Couleur.blanc){
				System.out.println("c'est au blanc de joueur : ");
			}else{
				System.out.println("c'est au noir de joueur : ");
			}
			
			Coordonnees coup;
						
			do{
				ok = true;
				scInt.hasNext();
				int x = scInt.nextInt();
				scInt.hasNext();
				int y = scInt.nextInt();
				
				coup = new Coordonnees(x, y);
				
				if(!gb.coupValide(coup, joueur)){
					System.out.println("coup invalide");
					ok = false;
				}
				
			}while(!ok);
				
			gb.ajoutPiece(coup, joueur);
			
			System.out.println("liberte des groupes blancs :");
			for(GroupePieces g : gb.getBlancs()){
				System.out.println(g.getLiberte());
			}
			System.out.println("liberte des groupes noirs :");
			for(GroupePieces g : gb.getNoirs()){
				System.out.println(g.getLiberte());
			}			
			
		}while(!gb.fin(joueur));
		
		affGB(gb);
		
		if(joueur==Couleur.blanc){
			System.out.println("les blancs ont gagner");
		}else{
			System.out.println("les noirs ont gagner");
		}
	}
	
	public static void affGB(GobanStructure gb){
				
		System.out.print(" ");
		for( int t=1 ; t<=gb.getTaille() ; t++){
			System.out.print(t);
		}	
		System.out.println();
		
		for( int y=1 ; y<=gb.getTaille() ;  y++){
			System.out.print(y);
			for( int x =1 ; x<=gb.getTaille() ; x++){
							
				if(gb.getPlateau()[x][y] == null){
					System.out.print(" ");
				}else if(gb.getPlateau()[x][y].getCouleur() == Couleur.noir){
					System.out.print("X");
				}else if(gb.getPlateau()[x][y].getCouleur() == Couleur.blanc){
					System.out.print("O");
				}else{
					System.out.print("8");
				}
			}
			System.out.println(y);
		}
		
		System.out.print(" ");
		for( int t=1 ; t<=gb.getTaille() ; t++){
			System.out.print(t);
		}	
		System.out.println();
	}
	
	
}
