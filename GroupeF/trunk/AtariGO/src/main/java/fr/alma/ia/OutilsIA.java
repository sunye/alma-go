package fr.alma.ia;

import static fr.alma.jeu.LibertePion.getLiberte;

import java.awt.Point;
import java.util.ArrayList;

import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;

public class OutilsIA {
	
	public static ArrayList<Point> voisins = new ArrayList<Point>();
	
	/**
	 * 
	 */
	public static boolean existN(Grille grille,int x,int y,Pion.Couleur c){
		if (x>0){
			if(c.equals(Pion.Couleur.BLANC)) return ((grille.Contenu[x-1][y]).equals(Pion.Couleur.BLANC));
			else if(c.equals(Pion.Couleur.NOIR)) return ((grille.Contenu[x-1][y]).equals(Pion.Couleur.NOIR));
				else return false;
		}else return false;
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
			if(c.equals(Pion.Couleur.BLANC)) return ((grille.Contenu[x+1][y]).equals(Pion.Couleur.BLANC));
			else if(c.equals(Pion.Couleur.NOIR)) return ((grille.Contenu[x+1][y]).equals(Pion.Couleur.NOIR));
				else return false;
			
		}else return false;
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
		if (y<8){
			if(c.equals(Pion.Couleur.BLANC)) return ((grille.Contenu[x][y+1]).equals(Pion.Couleur.BLANC));
			else if(c.equals(Pion.Couleur.NOIR)) return ((grille.Contenu[x][y+1]).equals(Pion.Couleur.NOIR));
				else return false;
		}
					
		else return false;
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

		if (y>0){
			if(c.equals(Pion.Couleur.BLANC)) return ((grille.Contenu[x][y-1]).couleur.equals(c));
			else if(c.equals(Pion.Couleur.NOIR))	return ((grille.Contenu[x][y-1]).equals(new Pion(Pion.Couleur.NOIR,new Point(x,y-1))));
				else return false;
			
		}
		else return false; 
	}

	/**
	 * 
	 * @param g
	 * @param p
	 */
	public static void DeterminerVoisins(Grille g,Point p){
		
		if(g.Contenu[p.x][p.y].equals(new Pion(p)))
			return;
		else{
			if(voisins.size()!=0){
				if (!(voisins.contains(p)))	voisins.add(p);
			}else voisins.add(p);
			
			if ((existN(g,p.x,p.y,g.Contenu[p.x][p.y].couleur)))DeterminerVoisins(g,new Point(p.x-1,p.y));
			if ((existS(g,p.x,p.y,g.Contenu[p.x][p.y].couleur)))DeterminerVoisins(g,new Point(p.x+1,p.y));
			if ((existO(g,p.x,p.y,g.Contenu[p.x][p.y].couleur)))DeterminerVoisins(g,new Point(p.x,p.y-1));
			if ((existE(g,p.x,p.y,g.Contenu[p.x][p.y].couleur))) DeterminerVoisins(g,new Point(p.x,p.y+1));
		}
	
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	public static ArrayList<Point> determinerGroupesBlanc(Grille g){
		ArrayList<Point> listeG=new ArrayList<Point>();
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				if(g.Contenu[i][j].couleur.equals(Pion.Couleur.BLANC)){
					voisins = new ArrayList<Point>();
					DeterminerVoisins(g, new Point(i,j));
					boolean groupeExistant = false;
					for(int k=0;k<voisins.size();k++)
						if (listeG.contains(voisins.get(k)))
							groupeExistant = true;
					if(!groupeExistant){
						listeG.add(new Point(i,j));
						
					}
					voisins = new ArrayList<Point>();
				}
			}
		return listeG;
	}
	
	/**
	 * 
	 * @param g
	 * @return
	 */
	public static ArrayList<Point> determinerGroupesNoir(Grille g){
		ArrayList<Point> listeG = new ArrayList<Point>();
		
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				if(g.Contenu[i][j].couleur.equals(Pion.Couleur.NOIR)){
					voisins = new ArrayList<Point> ();
					DeterminerVoisins(g, new Point(i,j));
					boolean groupeExistant = false;
					for(int k=0;k<voisins.size();k++)
						if (listeG.contains(voisins.get(k)))
							groupeExistant = true;
					if(!groupeExistant)
						listeG.add(new Point(i,j));
				}
				voisins = new ArrayList<Point> ();
			}
		return listeG;
	}
	
	/**
	 * 
	 * @param g
	 * @param listeG
	 * @return
	 */
	public static int determineMin(Grille g,ArrayList<Point> listeG){
		int min = getLiberte(g, listeG.get(0), g.Contenu[listeG.get(0).x][listeG.get(0).y].couleur);
		
		for(int k=0;k<listeG.size();k++){
			Point p=listeG.get(k);
			int x = getLiberte(g, p, g.Contenu[p.x][p.y].couleur);
			if(x<min)
				min = x;
		}
		return min;
	}

}
