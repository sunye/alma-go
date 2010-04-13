package fr.alma.jeu;

import java.awt.Point;
import java.util.ArrayList;

import fr.alma.ia.Ia;
import fr.alma.ihm.Ihm;
import fr.alma.ihm.Ihm.Tour;
import fr.alma.jeu.Pion.Couleur;
import fr.alma.structure.Arbre;
import fr.alma.structure.Noeud;


/**
 * @author landryngassa
 * @author lahuidi
 * 
 */
public class Jeu {
	
	public static final int SUICIDE = 0; 
	public static final int VALIDE = 1;
	public static final int INVALIDE = 2;
	public static final int CAPTURE = 3;
	
	public static int min = 0;
	
	public static ArrayList<Pion> voisins = new ArrayList<Pion>();
	
	/**
	 * 
	 * @return
	 */
	public  ArrayList<Pion> getVoisins() {
		return voisins;
	}


	/**
	 * 
	 * @param vois
	 */
	public static void setVoisins(ArrayList<Pion> vois) {
		voisins = vois;
	}

	/**
	 * 
	 * @param grille
	 * @param p
	 * @param c
	 * @return
	 */
	public static int getLiberte(Grille grille, Point p,Pion.Couleur c){ 
		
		int x = p.x;
		int y = p.y;
				
		int N=0,S=0,E=0,O=0;
		voisins.add(grille.Contenu[x][y]);
		//System.out.println(" dans getliberte");
		
		if(x==0)
			if(y==0){
				
				if ((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x+1][y].equal(new Pion(new Point(x+1,y)))){
					S=1;
					voisins.add(grille.Contenu[x+1][y]);
				}
				else
					if ((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x+1][y].couleur)))
						S=getLiberte(grille,new Point(x+1,y),c);
				if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equal(new Pion(new Point(x,y+1)))){
					E=1;
					voisins.add(grille.Contenu[x][y+1]);
				}
				else{
					if((!voisins.contains(grille.Contenu[x][y+1]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
						E=getLiberte(grille,new Point(x, y+1), c);
					}
				return E+S;
			}
				
			else{
				
				if(y==8){
					if ((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x+1][y].equal(new Pion(new Point(x+1,y)))){
						S=1;
						voisins.add(grille.Contenu[x+1][y]);
					}
					else
						if ((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x+1][y].couleur)))
							S=getLiberte(grille,new Point(x+1,y),c);
					if((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equal(new Pion(new Point(x,y-1)))){
						O=1;
					    voisins.add(grille.Contenu[x][y-1]);
					}
					else{
						if((!voisins.contains(grille.Contenu[x][y-1]))&&(c.equals(grille.Contenu[x][y-1].couleur)))
							O=getLiberte(grille,new Point(x, y-1), c);
						}
					return O+S;
				}				
				else{ //0<y<8
					if((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x+1][y].equal(new Pion(new Point(x+1,y)))){
						S=1;
						voisins.add(grille.Contenu[x+1][y]);
					}
					else{
						if ((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x+1][y].couleur)))
							S=getLiberte(grille,new Point(x+1,y), c);
					}
					if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equal(new Pion(new Point(x,y)))){
						E=1;
						voisins.add(grille.Contenu[x][y+1]);
					}
					else{
						if((!voisins.contains(grille.Contenu[x][y+1]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
							E=getLiberte(grille,new Point(x, y+1), c);
					}
					if((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equal(new Pion(new Point(x,y-1)))){
						O=1;
						voisins.add(grille.Contenu[x][y-1]);
					}
					else{
						if((!voisins.contains(grille.Contenu[x][y-1]))&&(c.equals(grille.Contenu[x][y-1].couleur)))
							O=getLiberte(grille,new Point(x, y-1), c);
					}
					return S+E+O;
			  }
				
			}
				
		else{ //x!=0
			if (x<8)//0<x<8
				if (y==0){
					if((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x][y+1].equal(new Pion(new Point(x,y+1)))){
						S=1;
						voisins.add(grille.Contenu[x+1][y]);
					}
					else{
						if((!voisins.contains(grille.Contenu[x][y+1]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
							S=getLiberte(grille,new Point(x, y+1), c);
					}
					if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equal(new Pion(new Point(x,y+1))))
						E=1;
					else{
						if((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
							 E=getLiberte(grille,new Point(x, y+1), c);
					}
					
					if((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equal(new Pion(new Point(x-1,y)))){
						N=1;
						voisins.add(grille.Contenu[x-1][y]);
					}
					else{
						if((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
							N=getLiberte(grille,new Point(x-1, y), c);
					}
					return N+S+E;
				}
			
				else
					if(y==8){ //0<x<8 && y=8
						if((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equal(new Pion(new Point(x-1,y)))){
							N=1;
							voisins.add(grille.Contenu[x-1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
								N=getLiberte(grille,new Point(x-1, y), c);
						}
						if((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x+1][y].equal(new Pion(new Point(x+1,y)))){
							S=1;
							voisins.add(grille.Contenu[x+1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x+1][y].couleur)))
								S=getLiberte(grille,new Point(x+1, y), c);
						}					
						
						if((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equal(new Pion(new Point(x,y-1)))){
							O=1;
							voisins.add(grille.Contenu[x][y-1]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x][y-1]))&&(c.equals(grille.Contenu[x][y-1].couleur)))
								O=getLiberte(grille,new Point(x, y-1),c);
						}
						return N+S+O;
				  }
				  else{ // 0<x<8 && 0<y<8
					  if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equal(new Pion(new Point(x,y+1)))){
							E=1;
							voisins.add(grille.Contenu[x][y+1]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x][y+1]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
								E=getLiberte(grille,new Point(x, y+1),c);
						}
						if((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x+1][y].equal(new Pion(new Point(x+1,y)))){
							S=1;
							voisins.add(grille.Contenu[x+1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x+1][y].couleur)))
								 S=getLiberte(grille,new Point(x+1, y),c);
						}
						
						if((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equal(new Pion(new Point(x-1,y)))){
							N=1;
							voisins.add(grille.Contenu[x-1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
								N=getLiberte(grille,new Point(x-1, y),c);
						}
						
						if ((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equal(new Pion(new Point(x,y-1)))){
							O=1;
							voisins.add(grille.Contenu[x][y-1]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x][y-1]))&&(c.equals(grille.Contenu[x][y-1].couleur)))
								O=getLiberte(grille,new Point(x, y-1),c);
						}
						return N+S+E+O;
				  }
			
			else{   //x=8
				if(y==0){
					if ((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equal(new Pion(new Point(x-1,y)))){
						N=1;
						voisins.add(grille.Contenu[x-1][y]);
					}
					else
						if ((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
							N=getLiberte(grille,new Point(x-1,y),c);
					if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equal(new Pion(new Point(x,y+1)))){
						E=1;
						voisins.add(grille.Contenu[x][y+1]);
					}
					else{
						if((!voisins.contains(grille.Contenu[x][y+1]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
							E=getLiberte(grille,new Point(x, y+1),c);
						}
					return N+E;
				}
				else 
					if(y==8){
						if((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equal(new Pion(new Point(x-1,y)))){
							N=1;
							voisins.add(grille.Contenu[x-1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
								 N=getLiberte(grille,new Point(x-1, y),c);
						}
						
						if((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equal(new Pion(new Point(x,y-1)))){
							O=1;
							voisins.add(grille.Contenu[x][y-1]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x][y-1]))&&(c.equals(grille.Contenu[x][y-1].couleur)))
								O=getLiberte(grille,new Point(x, y-1),c);
						}
						return N+O;
					}
				
					else{ // x==8 && 0<8<y
						
					
						if((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equal(new Pion(new Point(x-1,y)))){
							N=1;
							voisins.add(grille.Contenu[x-1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
								N=getLiberte(grille,new Point(x-1, y),c);
						}
						if((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equal(new Pion(new Point(x,y-1)))){
							O=1;
							voisins.add(grille.Contenu[x][y-1]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x][y-1]))&&(c.equals(grille.Contenu[x][y-1].couleur)))
								O=getLiberte(grille,new Point(x, y-1),c);
						}
						if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equal(new Pion(new Point(x,y+1)))){
							E=1;
							voisins.add(grille.Contenu[x][y+1]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x][y+1]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
								E=getLiberte(grille,new Point(x, y+1),c);
						}
						return N+E+O;
					}			  	
			}		
		}			
		
	}	
	
	/**
	 * 
	 * @param grille
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public static boolean existN(Grille grille,int x,int y,Pion.Couleur c){
		
		if (x>0)
			if(c.equals(Pion.Couleur.BLANC))
				return ((grille.Contenu[x-1][y]).equal(new Pion(Pion.Couleur.NOIR,new Point(x-1,y))));
			else
				return ((grille.Contenu[x-1][y]).equal(new Pion(Pion.Couleur.BLANC,new Point(x-1,y))));
	
		else
			return false;
	}

	/**
	 * 
	 * @param grille
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public static boolean existS(Grille grille,int x,int y,Pion.Couleur c){
		if (x<8){
			if(c.equals(Pion.Couleur.BLANC))
				return ((grille.Contenu[x+1][y]).equal(new Pion(Pion.Couleur.NOIR,new Point(x+1,y))));
			else
				return ((grille.Contenu[x+1][y]).equal(new Pion(Pion.Couleur.BLANC,new Point(x+1,y))));
		}
		else
			return false;
	}

	/**
	 * 
	 * @param grille
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public static boolean existE(Grille grille,int x,int y,Pion.Couleur c){
		if (y<8)
			if(c.equals(Pion.Couleur.BLANC))
				return ((grille.Contenu[x][y+1]).equal(new Pion(Pion.Couleur.NOIR,new Point(x,y+1))));
			else
				return ((grille.Contenu[x][y+1]).equal(new Pion(Pion.Couleur.BLANC,new Point(x,y+1))));
	
		else
			return false;
	}

	/**
	 * 
	 * @param grille
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public static boolean existO(Grille grille,int x,int y,Pion.Couleur c){
	
		if (y>0)
			if(c.equals(Pion.Couleur.BLANC))
				return ((grille.Contenu[x][y-1]).equal(new Pion(Pion.Couleur.NOIR,new Point(x,y-1))));
			else
				return ((grille.Contenu[x][y-1]).equal(new Pion(Pion.Couleur.BLANC,new Point(x,y-1))));
	
		else
			return false;
	}


	/**
	 * 
	 * @param grille
	 * @param p
	 * @param t
	 * @return
	 */
	private static Pion pionCapture;
	public static int SimulerJeu(Grille grille,Point p,Tour t){
		Pion pion;
		
		int x = p.x;
		int y = p.y;
		
		if(t == Tour.BLANC)
		   pion=new Pion(Pion.Couleur.BLANC,new Point(x,y));
		else
			pion=new Pion(Pion.Couleur.NOIR,new Point(x,y));
		
		//System.out.println("la fonction jouer");
		if((x<0)||(x>8)||(y<0)||(y>8)){
			
		//JOptionPane.showInputDialog("indice insuffisante","erreur");
		//System.out.println("err.");
		return 2;
		}
		
		
		else{			
					
			if (grille.Contenu[x][y].equal(new Pion(new Point(x,y)))){
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
	
	public static Point jouerMachine(Grille g,Ihm.Tour t) {
		
	System.out.println("dans jouermachine");
		Pion.Couleur c;
		if(t==Ihm.Tour.BLANC)
			c=Pion.Couleur.BLANC;
		else 
			c=Pion.Couleur.NOIR;
		
		Point p=new Point();
		ArrayList<Pion> cj=getCoupsJouerAd(g,c);
		ArrayList<Pion> cMachine=getCoupsJouerM(g,c);
 		ArrayList<Pion> cjHumain=new ArrayList<Pion>();
		ArrayList<Pion> cjMachine=new ArrayList<Pion>();
		if(cj.size()==0){
		System.out.println(cj.size());
		
			if (cj.size()<30){
				//int min=getLiberte(g, cj.get(0).position, cj.get(0).couleur);
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
				for(Pion pion:cjHumain){
					if(getLiberteFixe(g,pion.position)<=min){
						min=getLiberteFixe(g,pion.position);
						if(pion.position.x>0)
							if((g.Contenu[pion.position.x-1][pion.position.y]).couleur.equals(Pion.Couleur.NULL))
								p=new Point(pion.position.x-1,pion.position.y);
						if(pion.position.x<8)
							if((g.Contenu[pion.position.x+1][pion.position.y]).couleur.equals(Pion.Couleur.NULL))
								p=new Point(pion.position.x+1,pion.position.y);
						if(pion.position.y>0)
							if((g.Contenu[pion.position.x][pion.position.y-1]).couleur.equals(Pion.Couleur.NULL))
								p=new Point(pion.position.x,pion.position.y-1);
						if(pion.position.y<8)
							if((g.Contenu[pion.position.x][pion.position.y+1]).couleur.equals(Pion.Couleur.NULL))
								p=new Point(pion.position.x,pion.position.y+1);
						
					}
					
					
						
				}
				
				for(Pion pion:cjMachine){
					if(getLiberteFixe(g,pion.position)<min){
						min=getLiberteFixe(g,pion.position);
						if(pion.position.x>0)
							if((g.Contenu[pion.position.x-1][pion.position.y]).couleur.equals(Pion.Couleur.NULL))
								p=new Point(pion.position.x-1,pion.position.y);
						if(pion.position.x<8)
							if((g.Contenu[pion.position.x+1][pion.position.y]).couleur.equals(Pion.Couleur.NULL))
								p=new Point(pion.position.x+1,pion.position.y);
						if(pion.position.y>0)
							if((g.Contenu[pion.position.x][pion.position.y-1]).couleur.equals(Pion.Couleur.NULL))
								p=new Point(pion.position.x,pion.position.y-1);
						if(pion.position.y<8)
							if((g.Contenu[pion.position.x][pion.position.y+1]).couleur.equals(Pion.Couleur.NULL))
								p=new Point(pion.position.x,pion.position.y+1);
						
					}
					
					
						
				}
				
				
				
				
			}
			else{
				
				Arbre a = Ia.constuireArbre(g);
				p = a.remplirArbre();
				ArrayList<Noeud> listeF = a.racine.listeFils;

			for(Noeud n:listeF){
				System.out.println(n.getNote());
				for(Pion pion:cj){
					if((EstVoisin(n.getCoup(),pion))&& (n.getNote()==a.racine.getNote())&&(pion.couleur.equals(Pion.Couleur.BLANC)));
							p = n.getCoup().position;
					
				}
			}
		}
		}
		else
			p=new Point(((int)Math.random()*100000000)%9,((int)Math.random()*10000000)%9);
		return p;
	}
	
	
	

	/**
	 * 
	 * @param g
	 * @param position
	 * @return
	 */
	private static int getLiberteFixe(Grille g, Point position) {
		
		int lib=0;
		if(position.x>0)
			if((g.Contenu[position.x-1][position.y]).couleur.equals(Pion.Couleur.NULL))
				lib++;
		if(position.x<8)
			if((g.Contenu[position.x+1][position.y]).couleur.equals(Pion.Couleur.NULL))
				lib++;
		if(position.y>0)
			if((g.Contenu[position.x][position.y-1]).couleur.equals(Pion.Couleur.NULL))
				lib++;
		if(position.y<8)
			if((g.Contenu[position.x][position.y+1]).couleur.equals(Pion.Couleur.NULL))
				lib++;
		return lib;
	}

	
		
	/**
	 * 
	 * @param g
	 * @param p
	 * @return
	 */
	public static ArrayList<Pion> DeterminerVoisins(Grille g,Pion p){
		Pion.Couleur c;
		if(g.Contenu[p.position.x][p.position.y].equal(new Pion(p.position)))
			return null;
		else{
			if(voisins.size()!=0){
				if (!(voisins.contains(p))) 
					voisins.add(p);
			}
			else
				voisins.add(p);
			if(p.couleur.equals(Pion.Couleur.BLANC))
				c=Pion.Couleur.NOIR;
			else 
				c=Pion.Couleur.BLANC;
		if ((existN(g,p.position.x,p.position.y,c)))
			DeterminerVoisins(g,g.Contenu[p.position.x-1][p.position.y]);
		if ((existS(g,p.position.x,p.position.y,c)))
			DeterminerVoisins(g,g.Contenu[p.position.x+1][p.position.y]);
		if ((existO(g,p.position.x,p.position.y,c)))
			DeterminerVoisins(g,g.Contenu[p.position.x][p.position.y-1]);
		if ((existE(g,p.position.x,p.position.y,c)))
			DeterminerVoisins(g,g.Contenu[p.position.x-1][p.position.y+1]);
		
		return voisins;
		}
		//System.out.println(voisins.toString());
		//return voisins;
		
		}
	

	
	
	
	public static ArrayList<Pion> LpionsCapture=new ArrayList<Pion>();
	public static void DeterminerVoisinsG(Grille g,Pion p){
		if(!(LpionsCapture.contains(p))){
			LpionsCapture.add(p);
		
		if((p.position.x>0))
			if((p.couleur.equals(g.Contenu[p.position.x-1][p.position.y].couleur)))
			DeterminerVoisinsG(g, g.Contenu[p.position.x-1][p.position.y]);
		if((p.position.x<8))
			if(p.couleur.equals(g.Contenu[p.position.x+1][p.position.y].couleur))
			DeterminerVoisinsG(g, g.Contenu[p.position.x+1][p.position.y]);
		if((p.position.y>0))
			if (p.couleur.equals(g.Contenu[p.position.x][p.position.y-1].couleur))
				DeterminerVoisinsG(g, g.Contenu[p.position.x][p.position.y-1]);
		if((p.position.x<8))
			if((p.couleur.equals(g.Contenu[p.position.x][p.position.y+1].couleur)))
			DeterminerVoisinsG(g, g.Contenu[p.position.x][p.position.y+1]);
		}
	}
	
	
	
	public static ArrayList<Pion> getCoupsJouerAd(Grille g,Pion.Couleur c){

	//	System.out.println("Appelé");
		Pion.Couleur cPrime;
		if(c.equals(Pion.Couleur.NOIR))
			cPrime=Pion.Couleur.BLANC;
		else
			cPrime=Pion.Couleur.NOIR;
		ArrayList<Pion> cj = new ArrayList<Pion>();
		for(int i=0;i<9;i++) 
       	 for(int j=0;j<9;j++) {
       		if((g.Contenu[i][j].couleur.equals(cPrime))) {
       			cj.add(g.Contenu[i][j]); 
       		System.out.println("Coup jouer : "+g.Contenu[i][j].position);}
       	 }
		return cj;
                
		 
	}

	
	public static ArrayList<Pion> getCoupsJouerM(Grille g,Pion.Couleur c){
		//	System.out.println("Appelé");
			ArrayList<Pion> cj = new ArrayList<Pion>();
			for(int i=0;i<9;i++) 
	       	 for(int j=0;j<9;j++) {
	       		if((g.Contenu[i][j].couleur.equals(c))) {
	       			cj.add(g.Contenu[i][j]); 
	       		System.out.println("Coup jouer : "+g.Contenu[i][j].position);}
	       	 }
			return cj;
		}
	
	private static boolean EstVoisin(Pion coup, Pion pion) {
		
		return ((coup.position.x+1==pion.position.x)&&(coup.position.y==pion.position.y))||((coup.position.x-1==pion.position.x)&&(coup.position.y==pion.position.y))||((coup.position.x==pion.position.x)&&(coup.position.y+1==pion.position.y))||((coup.position.x==pion.position.x)&&(coup.position.y-1==pion.position.y));
	}


	/**
	 * Met a jour la grille aprés une capture
	 * @param g grille a mettre à jour
	 * @param position la position du pion
	 * @return la grille mis à jour
	 */
	
	
	

	public static Grille miseAjourGrilleApresCapture(Grille g){
		LpionsCapture=new ArrayList<Pion>();
		DeterminerVoisinsG(g, pionCapture);

		Grille other = new Grille();
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				other.Contenu[i][j]=g.Contenu[i][j];
			}
		}
		for (Pion pion:LpionsCapture){
			other.Contenu[pion.position.x][pion.position.y]=new Pion(pion.position);
		}
		//il resque a mettre la grille aprés capture icis
		return other;
	}

}
