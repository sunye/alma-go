package fr.alma.jeu;

import java.awt.Point;
import java.util.ArrayList;

public class OutilsJEU {

		
	public static ArrayList<Pion> LpionsCapture = new ArrayList<Pion>();
	
	/**
	 * 
	 * @param grille
	 * @param x
	 * @param y
	 * @param c
	 * @return
	 */
	public static boolean existN(Grille grille,int x,int y,Pion.Couleur c){
		
		if (x>0){
			if(c.equals(Pion.Couleur.BLANC)) return ((grille.Contenu[x-1][y]).equals(new Pion(Pion.Couleur.NOIR,new Point(x-1,y))));
			else return ((grille.Contenu[x-1][y]).equals(new Pion(Pion.Couleur.BLANC,new Point(x-1,y))));
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
			if(c.equals(Pion.Couleur.BLANC)) return ((grille.Contenu[x+1][y]).equals(new Pion(Pion.Couleur.NOIR,new Point(x+1,y))));
			else return ((grille.Contenu[x+1][y]).equals(new Pion(Pion.Couleur.BLANC,new Point(x+1,y))));
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
			if(c.equals(Pion.Couleur.BLANC)) return ((grille.Contenu[x][y+1]).equals(new Pion(Pion.Couleur.NOIR,new Point(x,y+1))));
			else return ((grille.Contenu[x][y+1]).equals(new Pion(Pion.Couleur.BLANC,new Point(x,y+1))));
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
	public static boolean existO(Grille grille,int x,int y,Pion.Couleur c){
		if (y>0){
			if(c.equals(Pion.Couleur.BLANC)) return ((grille.Contenu[x][y-1]).equals(new Pion(Pion.Couleur.NOIR,new Point(x,y-1))));
			else return ((grille.Contenu[x][y-1]).equals(new Pion(Pion.Couleur.BLANC,new Point(x,y-1))));
		}else return false;
	}

	/**
	 * 
	 * @param g
	 * @param p
	 */
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
		if((p.position.y<8))
			if((p.couleur.equals(g.Contenu[p.position.x][p.position.y+1].couleur)))
			DeterminerVoisinsG(g, g.Contenu[p.position.x][p.position.y+1]);
		}
	}
	
	/**
	 * 
	 * @param coup
	 * @param pion
	 * @return
	 */
	public static boolean EstVoisin(Pion coup, Pion pion) {
		return ((coup.position.x+1==pion.position.x)&&(coup.position.y==pion.position.y))||((coup.position.x-1==pion.position.x)&&(coup.position.y==pion.position.y))||((coup.position.x==pion.position.x)&&(coup.position.y+1==pion.position.y))||((coup.position.x==pion.position.x)&&(coup.position.y-1==pion.position.y));
	}

}
