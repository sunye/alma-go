package fr.alma.jeu;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Used method for the class jeu
 * @author Ngassa Hubert
 *
 */
public class OutilsJEU {

		
	public static ArrayList<Pion> LpionsCapture = new ArrayList<Pion>();
	
	/**
	 * Determine if the North neighbor of pawn (x,y) exists and if they have the same color
	 * x abscissa
	 * y ordinates
	 * c color to test
	 * g currnet grid
	 */
	public static boolean existN(Grille grille,int x,int y,Couleur c){
		
		if (x>0){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x-1][y]).equals(new Pion(Couleur.NOIR,new Point(x-1,y))));
			else return ((grille.Contenu[x-1][y]).equals(new Pion(Couleur.BLANC,new Point(x-1,y))));
		}else return false;
	}

	/**
	 * Determine if the South neighbor of pawn (x,y) exists and if they have the same color
	 * x:abscissa
	 * y:ordinates
	 * c:color to test
	 * g:corresponding color
	 */
	public static boolean existS(Grille grille,int x,int y,Couleur c){
		if (x<8){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x+1][y]).equals(new Pion(Couleur.NOIR,new Point(x+1,y))));
			else return ((grille.Contenu[x+1][y]).equals(new Pion(Couleur.BLANC,new Point(x+1,y))));
		}else return false;
	}

	/**
	 * Determine if the East neighbor of pawn (x,y) exists and if they have the same color
	 * x abscissa
	 * y ordinates
	 * c color to test
	 * g corresponding color
	 */
	public static boolean existE(Grille grille,int x,int y,Couleur c){
		if (y<8){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x][y+1]).equals(new Pion(Couleur.NOIR,new Point(x,y+1))));
			else return ((grille.Contenu[x][y+1]).equals(new Pion(Couleur.BLANC,new Point(x,y+1))));
		}else return false;
	}

	/**
	 * Determine if the West neighbor of pawn (x,y) exists and if they have the same color
	 * x abscissa
	 * y ordinates
	 * c color to test
	 * g corresponding color
	 */
	public static boolean existO(Grille grille,int x,int y,Couleur c){
		if (y>0){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x][y-1]).equals(new Pion(Couleur.NOIR,new Point(x,y-1))));
			else return ((grille.Contenu[x][y-1]).equals(new Pion(Couleur.BLANC,new Point(x,y-1))));
		}else return false;
	}

	/**
	 * Method used to retrieve a set of pawns caught.
	 * @param g The current grid.
	 * @param p The pawn from which capture occurs.
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
	 * Determine whether two given pawns are direct neighbors on the goban.
	 * @param coup the first pawn
	 * @param pion  the first pawn
	 * @return true if both pawns are neighbors false otherwise
	 */
	public static boolean EstVoisin(Pion coup, Pion pion) {
		return ((coup.position.x+1==pion.position.x)&&(coup.position.y==pion.position.y))||((coup.position.x-1==pion.position.x)&&(coup.position.y==pion.position.y))||((coup.position.x==pion.position.x)&&(coup.position.y+1==pion.position.y))||((coup.position.x==pion.position.x)&&(coup.position.y-1==pion.position.y));
	}

}
