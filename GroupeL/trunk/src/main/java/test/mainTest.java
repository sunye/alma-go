package test;

import game.Coordinates;
import game.Color;
import game.GobanStructure;
import game.GroupPawns;

import java.util.Scanner;


public class mainTest {

	
	public static void main(String args[]){
	
		GobanStructure gb = new GobanStructure();
		
		Scanner scInt = new Scanner(System.in);
		 
		Color joueur = Color.BLACK;
		boolean ok = true;
		
		/* placage de pion pour les test */
		
		/* on fait un oeil */
		gb.addPawn(new Coordinates(6, 6), Color.BLACK);
		gb.removePawn(new Coordinates(6, 6));
		
		/* ----------------------------- */
		
		do{
			joueur = joueur.invColor();

			affGB(gb);
			if(joueur == Color.WHITE){
				System.out.println("c'est au blanc de joueur : ");
			}else{
				System.out.println("c'est au noir de joueur : ");
			}
			
			Coordinates coup;
						
			do{
				ok = true;
				scInt.hasNext();
				int x = scInt.nextInt();
				scInt.hasNext();
				int y = scInt.nextInt();
				
				coup = new Coordinates(x, y);
				
				if(!gb.moveValid(coup, joueur)){
					System.out.println("coup invalide");
					ok = false;
				}
				
			}while(!ok);
				
			gb.addPawn(coup, joueur);
			
			System.out.println("liberte des groupes blancs :");
			for(GroupPawns g : gb.getWhites()){
				System.out.println(g.getFreedoms());
			}
			System.out.println("liberte des groupes noirs :");
			for(GroupPawns g : gb.getBlacks()){
				System.out.println(g.getFreedoms());
			}			
			
		}while(!gb.isWinner(joueur));
		
		affGB(gb);
		
		if(joueur==Color.WHITE){
			System.out.println("les blancs ont gagner");
		}else{
			System.out.println("les noirs ont gagner");
		}
	}
	
	public static void affGB(GobanStructure gb){
				
		System.out.print(" ");
		for( int t=1 ; t<=gb.getSize() ; t++){
			System.out.print(t);
		}	
		System.out.println();
		
		for( int y=1 ; y<=gb.getSize() ;  y++){
			System.out.print(y);
			for( int x =1 ; x<=gb.getSize() ; x++){
							
				if(gb.getGoban()[x][y] == null){
					System.out.print(" ");
				}else if(gb.getGoban()[x][y].getColor() == Color.BLACK){
					System.out.print("X");
				}else if(gb.getGoban()[x][y].getColor() == Color.WHITE){
					System.out.print("O");
				}else{
					System.out.print("8");
				}
			}
			System.out.println(y);
		}
		
		System.out.print(" ");
		for( int t=1 ; t<=gb.getSize() ; t++){
			System.out.print(t);
		}	
		System.out.println();
	}
	
	
}
