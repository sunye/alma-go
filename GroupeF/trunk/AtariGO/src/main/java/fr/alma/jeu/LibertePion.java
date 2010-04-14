package fr.alma.jeu;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Classe pour g�rer les libert�s des pions
 * @author Ngassa Hubert
 *
 */
public class LibertePion {
	

	public static ArrayList<Pion> voisins = new ArrayList<Pion>();
	
	/**
	 * Getter
	 * @return retourne les voisins
	 */
	public static ArrayList<Pion> getVoisins() {
		return voisins;
	}

	/**
	 *Setter
	 * @param voisins sp�cifie les voisisn
	 */
	public static void setVoisins(ArrayList<Pion> voisins) {
		LibertePion.voisins = voisins;
	}
	
	/**
	 * Determine la libert� fixe d'un pion( nombre de case non occup�e autour d'elle
	 * @param g grille courante
	 * @param position la position du pion sur la grille 
	 * @return le nombre de libert�(entre 0et 4)
	 */
	public static int getLiberteFixe(Grille g, Point position) {
		
		int lib = 0;
		if(position.x>0)
			if((g.Contenu[position.x-1][position.y]).couleur.equals(Couleur.NULL))
				lib++;
		if(position.x<8)
			if((g.Contenu[position.x+1][position.y]).couleur.equals(Couleur.NULL))
				lib++;
		if(position.y>0)
			if((g.Contenu[position.x][position.y-1]).couleur.equals(Couleur.NULL))
				lib++;
		if(position.y<8)
			if((g.Contenu[position.x][position.y+1]).couleur.equals(Couleur.NULL))
				lib++;
		return lib;
	}
	
	/**
	 * D�termine la libert� d'un groupe de pions par simple connaissance d'un repr�sentant du groupe et de la grille courante. 
	 * @param grille la grille courante
	 * @param p la position du repr�sentant sur la grille
	 * @param c la couleur du groupe
	 * @return le nombre de libert� aux fronti�res du groupe
	 */
	public static int getLiberte(Grille grille, Point p,Couleur c){ 
		
		int x = p.x;
		int y = p.y;
				
		int N=0,S=0,E=0,O=0;
		voisins.add(grille.Contenu[x][y]);
				
		if(x==0)
			if(y==0){
				
				if ((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x+1][y].equals(new Pion(new Point(x+1,y)))){
					S=1;
					voisins.add(grille.Contenu[x+1][y]);
				}
				else
					if ((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x+1][y].couleur)))
						S=getLiberte(grille,new Point(x+1,y),c);
				if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equals(new Pion(new Point(x,y+1)))){
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
					if ((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x+1][y].equals(new Pion(new Point(x+1,y)))){
						S=1;
						voisins.add(grille.Contenu[x+1][y]);
					}
					else
						if ((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x+1][y].couleur)))
							S=getLiberte(grille,new Point(x+1,y),c);
					if((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equals(new Pion(new Point(x,y-1)))){
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
					if((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x+1][y].equals(new Pion(new Point(x+1,y)))){
						S=1;
						voisins.add(grille.Contenu[x+1][y]);
					}
					else{
						if ((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x+1][y].couleur)))
							S=getLiberte(grille,new Point(x+1,y), c);
					}
					if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equals(new Pion(new Point(x,y)))){
						E=1;
						voisins.add(grille.Contenu[x][y+1]);
					}
					else{
						if((!voisins.contains(grille.Contenu[x][y+1]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
							E=getLiberte(grille,new Point(x, y+1), c);
					}
					if((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equals(new Pion(new Point(x,y-1)))){
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
					if((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x][y+1].equals(new Pion(new Point(x,y+1)))){
						S=1;
						voisins.add(grille.Contenu[x+1][y]);
					}
					else{
						if((!voisins.contains(grille.Contenu[x][y+1]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
							S=getLiberte(grille,new Point(x, y+1), c);
					}
					if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equals(new Pion(new Point(x,y+1))))
						E=1;
					else{
						if((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
							 E=getLiberte(grille,new Point(x, y+1), c);
					}
					
					if((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equals(new Pion(new Point(x-1,y)))){
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
						if((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equals(new Pion(new Point(x-1,y)))){
							N=1;
							voisins.add(grille.Contenu[x-1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
								N=getLiberte(grille,new Point(x-1, y), c);
						}
						if((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x+1][y].equals(new Pion(new Point(x+1,y)))){
							S=1;
							voisins.add(grille.Contenu[x+1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x+1][y].couleur)))
								S=getLiberte(grille,new Point(x+1, y), c);
						}					
						
						if((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equals(new Pion(new Point(x,y-1)))){
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
					  if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equals(new Pion(new Point(x,y+1)))){
							E=1;
							voisins.add(grille.Contenu[x][y+1]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x][y+1]))&&(c.equals(grille.Contenu[x][y+1].couleur)))
								E=getLiberte(grille,new Point(x, y+1),c);
						}
						if((!voisins.contains(grille.Contenu[x+1][y]))&&grille.Contenu[x+1][y].equals(new Pion(new Point(x+1,y)))){
							S=1;
							voisins.add(grille.Contenu[x+1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x+1][y]))&&(c.equals(grille.Contenu[x+1][y].couleur)))
								 S=getLiberte(grille,new Point(x+1, y),c);
						}
						
						if((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equals(new Pion(new Point(x-1,y)))){
							N=1;
							voisins.add(grille.Contenu[x-1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
								N=getLiberte(grille,new Point(x-1, y),c);
						}
						
						if ((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equals(new Pion(new Point(x,y-1)))){
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
					if ((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equals(new Pion(new Point(x-1,y)))){
						N=1;
						voisins.add(grille.Contenu[x-1][y]);
					}
					else
						if ((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
							N=getLiberte(grille,new Point(x-1,y),c);
					if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equals(new Pion(new Point(x,y+1)))){
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
						if((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equals(new Pion(new Point(x-1,y)))){
							N=1;
							voisins.add(grille.Contenu[x-1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
								 N=getLiberte(grille,new Point(x-1, y),c);
						}
						
						if((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equals(new Pion(new Point(x,y-1)))){
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
											
						if((!voisins.contains(grille.Contenu[x-1][y]))&&grille.Contenu[x-1][y].equals(new Pion(new Point(x-1,y)))){
							N=1;
							voisins.add(grille.Contenu[x-1][y]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x-1][y]))&&(c.equals(grille.Contenu[x-1][y].couleur)))
								N=getLiberte(grille,new Point(x-1, y),c);
						}
						if((!voisins.contains(grille.Contenu[x][y-1]))&&grille.Contenu[x][y-1].equals(new Pion(new Point(x,y-1)))){
							O=1;
							voisins.add(grille.Contenu[x][y-1]);
						}
						else{
							if((!voisins.contains(grille.Contenu[x][y-1]))&&(c.equals(grille.Contenu[x][y-1].couleur)))
								O=getLiberte(grille,new Point(x, y-1),c);
						}
						if((!voisins.contains(grille.Contenu[x][y+1]))&&grille.Contenu[x][y+1].equals(new Pion(new Point(x,y+1)))){
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

}
