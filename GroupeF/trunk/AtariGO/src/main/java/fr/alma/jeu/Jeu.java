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
 * Classe qui gere le jeu et la stratégie
 * @author Hubert Ngassa
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
	 * methode principale de gestion des regles de jeu. détermine si un éventuel coup sur la grille est valable ou pas.
	 * @param grille: la grille en cours 
	 * @param p: le point où devrait être posé le pion sur le goban
	 * @param t: celui qui a la main.
	 * @return : une valeur déterminant si le coup devant etre joué est valide, est invalide, génere une capture ou un suicide
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
				//grille.Contenu[x][y]=pion;
				/*
				 * si la case est vide trois situations se prï¿½sentent soit: 
				 * une prise
				 * un suicide
				 * rien, une pose simple du pion*/
				
				boolean prise=false;
				int a=getLiberte(grille,new Point(x, y), pion.couleur);//System.out.println(a);
				if (a==0){	
				
				// test de la situation de prise pour le cas de prise avec suicide
					if((existN(grille,x, y,pion.couleur)))
						if((!pion.couleur.equals(grille.Contenu[x-1][y].couleur))){
							System.out.println("test si prise au nord");
						voisins=new ArrayList<Pion>();
						voisins.add(grille.Contenu[x][y]);
						int b=(getLiberte(grille,new Point(x-1, y), grille.Contenu[x-1][y].couleur));
						System.out.println("lib au nord="+b);
						if(b==0){
						  prise=true;
							System.out.println("Prise au Nord de ["+x+","+y+"]");
							pionCapture=grille.Contenu[x-1][y];
						}
	
					}
					
					if((existS(grille,x, y,pion.couleur)))
						if((!pion.couleur.equals(grille.Contenu[x+1][y].couleur))){
							System.out.println("test si prise au Sud");
						voisins=new ArrayList<Pion>();
						voisins.add(grille.Contenu[x][y]);
						int b=getLiberte(grille,new Point(x+1, y), grille.Contenu[x+1][y].couleur);
						System.out.println("lib au sud="+b);
						if(b==0){
							prise=true;
							System.out.println("Prise au sud de ["+x+","+y+"]");
							pionCapture=grille.Contenu[x+1][y];
						}
					}
					
					if((existE(grille,x, y,pion.couleur)))
						if((!pion.couleur.equals(grille.Contenu[x][y+1].couleur))){
							System.out.println("test si prise a Est");
						voisins=new ArrayList<Pion>();
						voisins.add(grille.Contenu[x][y]);
						int b=(getLiberte(grille,new Point(x, y+1), grille.Contenu[x][y+1].couleur));
						System.out.println("a l'est b="+b);
						if(b==0){
							prise=true;
							System.out.println("Prise -> l'est de ["+x+" , "+y+"]");
							pionCapture=grille.Contenu[x][y+1];
						}
					}
										
					if((existO(grille,x, y,pion.couleur)))
						if(!(pion.couleur.equals(grille.Contenu[x][y-1].couleur))){
							System.out.println("test si prise a l'ouest");
						voisins=new ArrayList<Pion>();
						voisins.add(grille.Contenu[x][y]);
						int b=getLiberte(grille,new Point(x, y-1), grille.Contenu[x][y-1].couleur);
						System.out.println("à l'ouest sa lib:"+b);
						if(b==0){
							prise=true;
							//System.out.println("Prise -> l'ouest de ["+x+" , "+y+"]");
							pionCapture=grille.Contenu[x][y-1];
						}
					}
					
				     /*si suicide arreter le jeu
				      * sinon c'est une situation de suicide*/
					
					setVoisins(new ArrayList<Pion>());
					if (prise){
						//grille.Contenu[x][y]=pion;
						System.out.println("prise");
						return CAPTURE;
					}
					else{
						//System.out.println("Situation de suicide interdite!!!!");
						return SUICIDE;
					}
					
			}
			else{
				
				/*si la libertï¿½ de la case [x,y] dans la matrice est non nulle
				 * on teste d'abors s'il y'a une prise 
				 *  sinon on pose simplement le pion sur le goban*/
				if((existN(grille,x, y,pion.couleur))){
					if(!((pion.couleur.equals(grille.Contenu[x-1][y].couleur)))){
						//System.out.println("test si prise au nord");
					voisins=new ArrayList<Pion>();
					voisins.add(grille.Contenu[x][y]);
					int b=getLiberte(grille,new Point(x-1, y), grille.Contenu[x-1][y].couleur);
					//System.out.println("au nord sa lib="+b);
					if(b==0){
					  prise=true;
						//System.out.println("Prise au Nord de ["+x+","+y+"]");
					  	pionCapture=grille.Contenu[x-1][y];
					}
	
				}
				}
				
				if(existS(grille,x, y,pion.couleur))
						if(!(pion.couleur.equals(grille.Contenu[x+1][y].couleur))){
							//System.out.println("test si prise au Sud");
					voisins=new ArrayList<Pion>();
					voisins.add(grille.Contenu[x][y]);
					int b=getLiberte(grille,new Point(x+1, y), grille.Contenu[x+1][y].couleur);
					//System.out.println("au sud"+b);
					if(b==0){
						prise=true;
						//System.out.println("Prise au sud de ["+x+","+y+"]");
						pionCapture=grille.Contenu[x+1][y];
					}
				}
				
				if((existE(grille, x, y,pion.couleur)))
					if((!(pion.couleur.equals(grille.Contenu[x][y+1].couleur)))){
						//System.out.println("test si prise a L'Est");
					voisins=new ArrayList<Pion>();
					voisins.add(grille.Contenu[x][y]);
					int b=getLiberte(grille, new Point(x, y+1), grille.Contenu[x][y+1].couleur);
					//System.out.println("à l'est lib="+b);
					if((b==0)){
						prise=true;
						//System.out.println("Prise -> l'est de ["+x+" , "+y+"]");
						pionCapture=grille.Contenu[x][y+1];
					}
				}
								
				if((existO(grille, x, y,pion.couleur)))
					if(!(pion.couleur.equals(grille.Contenu[x][y-1].couleur))){
						//System.out.println("test si prise a O");
					voisins=new ArrayList<Pion>();
					voisins.add(grille.Contenu[x][y]);
					int b=getLiberte(grille, new Point(x, y-1), grille.Contenu[x][y-1].couleur);
					//System.out.println("à l'oeust lib="+b);
					if((b==0)){
						prise=true;
						//System.out.println("Prise -> l'ouest de ["+x+" , "+y+"]");
						pionCapture=grille.Contenu[x][y-1];
					}
				}
				setVoisins(new ArrayList<Pion>());
				if (prise){
					//grille.Contenu[x][y]=pion;
					//System.out.println("Prise");
					return CAPTURE;
				}
					
				
				else{
					//grille.Contenu[x][y]=pion;
					return VALIDE;
					}
				}
						
			}	
			
			
			else{
				System.out.println("erreur");
				setVoisins(new ArrayList<Pion>());
				
				return INVALIDE;
				
				}
			}
	}

	/**
	 * 
	 * @param g
	 * @return
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
		System.out.println(cj.size());
		
			if (cj.size()<30){
				
				setVoisins(new ArrayList<Pion>());
				System.out.println("initialisation des voisins");
				for(Pion pion:cj){
					if((getLiberteFixe(g, pion.position)>0))
						cjHumain.add(pion);
				}
				for(Pion pion:cMachine){
					if((getLiberteFixe(g, pion.position)>0))
						cjMachine.add(pion);
				}
				
				System.out.println(cjHumain.size());
				
				min=getLiberteFixe(g,cjHumain.get(0).position);System.out.println(min);
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
				p = a.remplirArbre();
				MinMax.execute(a);
				ArrayList<Noeud> listeF = a.racine.listeFils;

			for(Noeud n:listeF){
				System.out.println(n.getNote());
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
	 * determine la liste des coups joués par l'adversaire sur la grille courante.
	 * @param g: grille courante
	 * @param c:de la couleur des pions de l'adversaire
	 * @return: retourne l'ensemble de tours les pions posés par l'adversaire sur la grille.
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
       		System.out.println("Coup jouer : "+g.Contenu[i][j].position);}
       	 }
		return cj;
    }
	
	/**
	 * determine la liste des coups joués par la machine sur la grille courante.
	 * @param g: grille courante
	 * @param c: de la couleur des pions de la machine
	 * @return: retourne l'ensemble de tous les pions posés par la machine sur la grille.
	 */
	public static ArrayList<Pion> getCoupsJouerM(Grille g,Couleur c){
		
			ArrayList<Pion> cj = new ArrayList<Pion>();
			for(int i=0;i<9;i++) 
	       	 for(int j=0;j<9;j++) {
	       		if((g.Contenu[i][j].couleur.equals(c))) {
	       			cj.add(g.Contenu[i][j]); 
	       		System.out.println("Coup jouer : "+g.Contenu[i][j].position);}
	       	 }
			return cj;
		}
	
	/**
	 * Met a jour la grille aprés une capture
	 * @param g grille a mettre à jour
	 * @param position la position du pion
	 * @return la grille mis à jour
	 */
	public static Grille miseAjourGrilleApresCapture(Grille g){
		
		Grille other = new Grille();
		LpionsCapture = new ArrayList<Pion>();
		
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
