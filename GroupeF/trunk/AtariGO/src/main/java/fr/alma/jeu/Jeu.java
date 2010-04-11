package fr.alma.jeu;

import java.awt.Point;
import java.util.ArrayList;

import fr.alma.ia.Ia;
import fr.alma.ihm.Ihm.Tour;
import fr.alma.structure.Arbre;
import fr.alma.structure.Noeud;


/**
 * 
 * @author lahuidi
 * @author landryngassa
 */
public class Jeu {
	
	public static final int SUICIDE = 0; 
	public static final int VALIDE = 1;
	public static final int INVALIDE = 2;
	public static final int CAPTURE = 3;
	
	public static ArrayList<Pion> voisins = new ArrayList<Pion>();
	
	public  ArrayList<Pion> getVoisins() {
		return voisins;
	}


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

	public static boolean existE(Grille grille,int x,int y,Pion.Couleur c){
		if (y<8)
			if(c.equals(Pion.Couleur.BLANC))
				return ((grille.Contenu[x][y+1]).equal(new Pion(Pion.Couleur.NOIR,new Point(x,y+1))));
			else
				return ((grille.Contenu[x][y+1]).equal(new Pion(Pion.Couleur.BLANC,new Point(x,y+1))));
	
		else
			return false;
	}

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
						}
					}
					
				     /*si suicide arreter le jeu
				      * sinon c'est une situation de suicide*/
					
					setVoisins(new ArrayList<Pion>());
					if (prise){
						grille.Contenu[x][y]=pion;
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
					}
				}
				setVoisins(new ArrayList<Pion>());
				if (prise){
					grille.Contenu[x][y]=pion;
					//System.out.println("Prise");
					return CAPTURE;
				}
					
				
				else{
					grille.Contenu[x][y]=pion;
					return VALIDE;
					}
				}
						
			}	
			
			
			else{
				//System.out.println("erreur");
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
	public static Point jouerMachine(Grille g) {
		
			
		Arbre a = Ia.constuireArbre(g);
		Point p = a.remplirArbre();
		ArrayList<Noeud> listeF = a.racine.listeFils;
		a.getCoupsJouer();
		
		for(Noeud n:listeF){
			for(Pion pion:a.cj){
				if((EstVoisin(n.getCoup(),pion))&& (n.getNote()==a.racine.getNote()))
						p = n.getCoup().position;
				
			}
		}
		return p;
	}
	private static boolean EstVoisin(Pion coup, Pion pion) {
		// TODO Auto-generated method stub
		return ((coup.position.x+1==pion.position.x)&&(coup.position.y==pion.position.y))||((coup.position.x-1==pion.position.x)&&(coup.position.y==pion.position.y))||((coup.position.x==pion.position.x)&&(coup.position.y+1==pion.position.y))||((coup.position.x==pion.position.x)&&(coup.position.y-1==pion.position.y));
	}


	/**
	 * Met a jour la grille aprés une capture
	 * @param g grille a mettre à jour
	 * @param position la position du pion
	 * @return la grille mis à jour
	 */
	public static Grille miseAjourGrilleApresCapture(Grille g, Point position){
		
		Grille other = new Grille();
	
		return other;
	}

}
