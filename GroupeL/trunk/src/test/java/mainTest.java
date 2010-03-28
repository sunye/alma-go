

import java.util.Scanner;

import jeu.Coordonnees;
import jeu.Couleur;
import jeu.GobanStructure;

public class mainTest {

	
	
	public static void main(String args[]){
	
		GobanStructure gb = new GobanStructure();
		
		Scanner scInt = new Scanner(System.in);
		 
		Couleur joueur = Couleur.noir;
		boolean ok = true;
		
		do{
			if(joueur == Couleur.blanc){
				joueur = Couleur.noir;
			}else{
				joueur = Couleur.blanc;
			}

			
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
				
				if(gb.testPosition(coup) != 0){
					System.out.println("coup invalide");
					ok = false;
				}
				
			}while(!ok);
				
			gb.ajoutPiece(coup, joueur);
			
		}while(gb.fin()==0);
		affGB(gb);
		if(gb.fin()==1){
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
				
				int coul = gb.testPosition(new Coordonnees(x, y));
				
				if(coul == 0){
					System.out.print(" ");
				}else if(coul == 1){
					System.out.print("X");
				}else{
					System.out.print("O");
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
