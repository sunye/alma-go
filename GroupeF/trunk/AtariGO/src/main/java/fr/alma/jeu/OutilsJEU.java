package fr.alma.jeu;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Méthodes utile dans la classe jeu
 * @author Ngassa Hubert
 *
 */
public class OutilsJEU {

		
	public static ArrayList<Pion> LpionsCapture = new ArrayList<Pion>();
	
	/**
	 * Détermine si le voisin Nord du  pion(x,y) existe et est de la même couleur que celui-ci
	 * x abscissse
	 * y ordonnné
	 * c couleur à tester
	 * g la grille courante
	 */
	public static boolean existN(Grille grille,int x,int y,Couleur c){
		
		if (x>0){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x-1][y]).equals(new Pion(Couleur.NOIR,new Point(x-1,y))));
			else return ((grille.Contenu[x-1][y]).equals(new Pion(Couleur.BLANC,new Point(x-1,y))));
		}else return false;
	}

	/**
	 * Détermine si le voisin SUD du  pion(x,y) existe et est de la même couleur que celui-ci
	 * x abscissse
	 * y ordonnné
	 * c couleur à tester
	 * g coulreur corresponadante 
	 */
	public static boolean existS(Grille grille,int x,int y,Couleur c){
		if (x<8){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x+1][y]).equals(new Pion(Couleur.NOIR,new Point(x+1,y))));
			else return ((grille.Contenu[x+1][y]).equals(new Pion(Couleur.BLANC,new Point(x+1,y))));
		}else return false;
	}

	/**
	 * Détermine si le voisin EST du  pion(x,y) existe et est de la même couleur que celui-ci
	 * x abscissse
	 * y ordonnné
	 * c couleur à teter
	 * g coulreur corresponadante 
	 */
	public static boolean existE(Grille grille,int x,int y,Couleur c){
		if (y<8){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x][y+1]).equals(new Pion(Couleur.NOIR,new Point(x,y+1))));
			else return ((grille.Contenu[x][y+1]).equals(new Pion(Couleur.BLANC,new Point(x,y+1))));
		}else return false;
	}

	/**
	 * Détermine si le voisin OUEST du  pion(x,y) existe et est de la même couleur que celui-ci
	 * x abscissse
	 * y ordonnné
	 * c couleur à tester
	 * g coulreur corresponadante 
	 */
	public static boolean existO(Grille grille,int x,int y,Couleur c){
		if (y>0){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x][y-1]).equals(new Pion(Couleur.NOIR,new Point(x,y-1))));
			else return ((grille.Contenu[x][y-1]).equals(new Pion(Couleur.BLANC,new Point(x,y-1))));
		}else return false;
	}

	/**
	 * methode utilisée pour récuperer un ensemble de pions capturés.
	 * @param g la grille courante.
	 * @param p le pion à partir duquel s'éffectue la capture.
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
	 * détermine si deux pions donnés sont voisins directs sur le goban.
	 * @param coup le 1er pion
	 * @param pion le deuxiemepions
	 * @return vrai si les deux pions sont voisins faux sinon
	 */
	public static boolean EstVoisin(Pion coup, Pion pion) {
		return ((coup.position.x+1==pion.position.x)&&(coup.position.y==pion.position.y))||((coup.position.x-1==pion.position.x)&&(coup.position.y==pion.position.y))||((coup.position.x==pion.position.x)&&(coup.position.y+1==pion.position.y))||((coup.position.x==pion.position.x)&&(coup.position.y-1==pion.position.y));
	}

}
