package fr.alma.jeu;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import fr.alma.ia.MinMax;
import fr.alma.structure.Arbre;
import fr.alma.structure.Noeud;
import static fr.alma.jeu.LibertePion.*;
import static fr.alma.jeu.OutilsJEU.*;

/**
 * Class managing the game and the strategy
 * @author Ngassa Hubert
 *
 */
public class Jeu {
	
	public static final int SUICIDE = 0; 
	public static final int VALIDE = 1;
	public static final int INVALIDE = 2;
	public static final int CAPTURE = 3;
	
	public static int min = 0;
	private static Pion pionCapture;
			
	/**
	 * Main method of management rules of play determines whether a possible blow to the grid is valid or not..
	 * @param grille the current grid 
	 * @param p the point where the pawn should be raised on the goban
	 * @param t the one which has the hand. 
	 * @return a determinant value if the blow is to be valid, invalid, generates a capture or a suicide 
	 */
	public static int SimulerJeu(Grille grille,Point p,Tour t){
		Pion pion;
		
		int x = p.x;
		int y = p.y;
		
		if(t == Tour.BLANC) pion=new Pion(Couleur.BLANC,new Point(x,y));
		else pion=new Pion(Couleur.NOIR,new Point(x,y));
			
		if((x<0)||(x>8)||(y<0)||(y>8)){
			
		return INVALIDE;
		}
				
		else{			
					
			if (grille.Contenu[x][y].equals(new Pion(new Point(x,y)))){
								
				boolean prise=false;
				int a=getLiberte(grille,new Point(x, y), pion.couleur);
				if (a==0){	
				
					if((existN(grille,x, y,pion.couleur)))
						if((!pion.couleur.equals(grille.Contenu[x-1][y].couleur))){
							
						voisins=new ArrayList<Pion>();
						voisins.add(grille.Contenu[x][y]);
						int b=(getLiberte(grille,new Point(x-1, y), grille.Contenu[x-1][y].couleur));
						
						if(b==0){
						  prise=true;
							
							pionCapture=grille.Contenu[x-1][y];
						}
	
					}
					
					if((existS(grille,x, y,pion.couleur)))
						if((!pion.couleur.equals(grille.Contenu[x+1][y].couleur))){
							
						voisins=new ArrayList<Pion>();
						voisins.add(grille.Contenu[x][y]);
						int b=getLiberte(grille,new Point(x+1, y), grille.Contenu[x+1][y].couleur);
						
						if(b==0){
							prise=true;
							
							pionCapture=grille.Contenu[x+1][y];
						}
					}
					
					if((existE(grille,x, y,pion.couleur)))
						if((!pion.couleur.equals(grille.Contenu[x][y+1].couleur))){
							
						voisins=new ArrayList<Pion>();
						voisins.add(grille.Contenu[x][y]);
						int b=(getLiberte(grille,new Point(x, y+1), grille.Contenu[x][y+1].couleur));
						System.out.println("a l'est b="+b);
						if(b==0){
							prise=true;
							
							pionCapture=grille.Contenu[x][y+1];
						}
					}
										
					if((existO(grille,x, y,pion.couleur)))
						if(!(pion.couleur.equals(grille.Contenu[x][y-1].couleur))){
							
						voisins=new ArrayList<Pion>();
						voisins.add(grille.Contenu[x][y]);
						int b=getLiberte(grille,new Point(x, y-1), grille.Contenu[x][y-1].couleur);
						
						if(b==0){
							prise=true;
							
							pionCapture=grille.Contenu[x][y-1];
						}
					}
					
				    					
					setVoisins(new ArrayList<Pion>());
					if (prise){
						
						
						return CAPTURE;
					}
					else{
						
						return SUICIDE;
					}
					
			}
			else{
								
				if((existN(grille,x, y,pion.couleur))){
					if(!((pion.couleur.equals(grille.Contenu[x-1][y].couleur)))){
						
					voisins=new ArrayList<Pion>();
					voisins.add(grille.Contenu[x][y]);
					int b=getLiberte(grille,new Point(x-1, y), grille.Contenu[x-1][y].couleur);
					
					if(b==0){
					  prise=true;
						
					  	pionCapture=grille.Contenu[x-1][y];
					}
	
				}
				}
				
				if(existS(grille,x, y,pion.couleur))
						if(!(pion.couleur.equals(grille.Contenu[x+1][y].couleur))){
							
					voisins=new ArrayList<Pion>();
					voisins.add(grille.Contenu[x][y]);
					int b=getLiberte(grille,new Point(x+1, y), grille.Contenu[x+1][y].couleur);
					
					if(b==0){
						prise=true;
						
						pionCapture=grille.Contenu[x+1][y];
					}
				}
				
				if((existE(grille, x, y,pion.couleur)))
					if((!(pion.couleur.equals(grille.Contenu[x][y+1].couleur)))){
						
					voisins=new ArrayList<Pion>();
					voisins.add(grille.Contenu[x][y]);
					int b=getLiberte(grille, new Point(x, y+1), grille.Contenu[x][y+1].couleur);
					
					if((b==0)){
						prise=true;
						
						pionCapture=grille.Contenu[x][y+1];
					}
				}
								
				if((existO(grille, x, y,pion.couleur)))
					if(!(pion.couleur.equals(grille.Contenu[x][y-1].couleur))){
					
					voisins=new ArrayList<Pion>();
					voisins.add(grille.Contenu[x][y]);
					int b=getLiberte(grille, new Point(x, y-1), grille.Contenu[x][y-1].couleur);
					
					if((b==0)){
						prise=true;
						
						pionCapture=grille.Contenu[x][y-1];
					}
				}
				setVoisins(new ArrayList<Pion>());
				if (prise){
					
					return CAPTURE;
				}
					
				
				else{
				
					return VALIDE;
					}
				}
						
			}	
			
			
			else{
			
				setVoisins(new ArrayList<Pion>());
				
				return INVALIDE;
				
				}
			}
	}

	/**
	 * Method which allows a machine to play
	 * @param g  the state of the grid
	 * @return The point that the machine as posed to play the pawn
	 */
	public static Point jouerMachine(Grille g,Tour t) {
		
		Point p=new Point();
		Couleur c;
		if(t==Tour.BLANC) c = Couleur.BLANC;
		else c = Couleur.NOIR;
				
		ArrayList<Pion> cj=getCoupsJouerAd(g,c);
		ArrayList<Pion> cMachine=getCoupsJouerM(g,c);
 		ArrayList<Pion> cjHumain=new ArrayList<Pion>();
		ArrayList<Pion> cjMachine=new ArrayList<Pion>();
		if(cj.size()!=0){
		
		
			if (cj.size()<30){
				
				setVoisins(new ArrayList<Pion>());
				
				for(Pion pion:cj){
					if((getLiberteFixe(g, pion.position)>0))
						cjHumain.add(pion);
				}
				for(Pion pion:cMachine){
					if((getLiberteFixe(g, pion.position)>0))
						cjMachine.add(pion);
				}
				
				
				
				min=getLiberteFixe(g,cjHumain.get(0).position);
				for(Pion pion:cjMachine){
					if((getLiberteFixe(g,pion.position)<min)&&(SimulerJeu(g, pion.position, t)!=SUICIDE)){
						min=getLiberteFixe(g,pion.position);
						if(pion.position.x>0)
							if(((g.Contenu[pion.position.x-1][pion.position.y]).couleur.equals(Couleur.NULL)))
								p=new Point(pion.position.x-1,pion.position.y);
						if(pion.position.x<8)
							if((g.Contenu[pion.position.x+1][pion.position.y]).couleur.equals(Couleur.NULL))
								p=new Point(pion.position.x+1,pion.position.y);
						if(pion.position.y>0)
							if((g.Contenu[pion.position.x][pion.position.y-1]).couleur.equals(Couleur.NULL))
								p=new Point(pion.position.x,pion.position.y-1);
						if(pion.position.y<8)
							if((g.Contenu[pion.position.x][pion.position.y+1]).couleur.equals(Couleur.NULL))
								p=new Point(pion.position.x,pion.position.y+1);
						
					}
				}
							
				for(Pion pion:cjHumain){
					if((getLiberteFixe(g,pion.position)<=min)&&(SimulerJeu(g, pion.position, t)!=SUICIDE)){
						min=getLiberteFixe(g,pion.position);
						if(pion.position.x>0)
							if((g.Contenu[pion.position.x-1][pion.position.y]).couleur.equals(Couleur.NULL))
								p=new Point(pion.position.x-1,pion.position.y);
						if(pion.position.x<8)
							if((g.Contenu[pion.position.x+1][pion.position.y]).couleur.equals(Couleur.NULL))
								p=new Point(pion.position.x+1,pion.position.y);
						if(pion.position.y>0)
							if((g.Contenu[pion.position.x][pion.position.y-1]).couleur.equals(Couleur.NULL))
								p=new Point(pion.position.x,pion.position.y-1);
						if(pion.position.y<8)
							if((g.Contenu[pion.position.x][pion.position.y+1]).couleur.equals(Couleur.NULL))
								p=new Point(pion.position.x,pion.position.y+1);
					}
				}
					
			}
			else{
				
				Arbre a = new Arbre(g);
				a.remplirArbre();
				MinMax.execute(a);
				ArrayList<Noeud> listeF = a.racine.listeFils;

			for(Noeud n:listeF){
				
				for(Pion pion:cj){
					if((EstVoisin(n.getCoup(),pion))&& (n.getNote()==a.racine.getNote())&&(pion.couleur.equals(Couleur.BLANC)));
							p = n.getCoup().position;
				}
			}
		}
		}
		else{
			Random n = new Random();
			p = new Point(n.nextInt(9),n.nextInt(9));
		}
		return p;
	}
		
	
	/**
	 * Determine the move list cheeks by the opponent on the current grid.
	 * @param g current grid
	 * @param c the color of the pawns of the opponent
	 * @return the set of all the pawns poses by the opponent on the grid.
	 */
	public static ArrayList<Pion> getCoupsJouerAd(Grille g,Couleur c){

		Couleur cPrime;
		if(c.equals(Couleur.NOIR))
			cPrime=Couleur.BLANC;
		else
			cPrime=Couleur.NOIR;
		ArrayList<Pion> cj = new ArrayList<Pion>();
		for(int i=0;i<9;i++) 
       	 for(int j=0;j<9;j++) {
       		if((g.Contenu[i][j].couleur.equals(cPrime))) {
       			cj.add(g.Contenu[i][j]); 
       		}
       	 }
		return cj;
    }
	
	/**
	 *  Determine the move list cheeks by the machine on the current grid.
	 * @param g current grid
	 * @param c the color of the pawns of the machine
	 * @return the set of all the pawns poses by the machine on the grid.
	 */
	public static ArrayList<Pion> getCoupsJouerM(Grille g,Couleur c){
		
			ArrayList<Pion> cj = new ArrayList<Pion>();
			for(int i=0;i<9;i++) 
	       	 for(int j=0;j<9;j++) {
	       		if((g.Contenu[i][j].couleur.equals(c))) {
	       			cj.add(g.Contenu[i][j]); 
	       		}
	       	 }
			return cj;
		}
	
	/**
	 * Updates the grid after a catch
	 * @param g  grid update
	 * @param position  the position of the pawn
	 * @return the grid updated
	 */
	public static Grille miseAjourGrilleApresCapture(Grille g){
		
		Grille other = new Grille();
		LpionsCapture = new ArrayList<Pion>();
		if(pionCapture==null)
			return new Grille();
		else{
		DeterminerVoisinsG(g, pionCapture);
		
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				other.Contenu[i][j]=g.Contenu[i][j];
			}
		}
		for (Pion pion:LpionsCapture){
			other.Contenu[pion.position.x][pion.position.y]=new Pion(pion.position);
		}
		
		return other;
		}
	}

}
