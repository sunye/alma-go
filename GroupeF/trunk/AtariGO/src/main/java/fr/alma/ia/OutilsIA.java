package fr.alma.ia;

import static fr.alma.jeu.LibertePion.getLiberte;

import java.awt.Point;
import java.util.ArrayList;
import fr.alma.jeu.Couleur;
import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;

/**
 * Méthode utile pour la classe IA
 * @author Ngassa Hubert
 *
 */
public class OutilsIA {
	
	public static ArrayList<Point> voisins = new ArrayList<Point>();
	
	/**
	 * Détermine si le voisin Nord du  pion(x,y) existe et est de la même couleur que celui-ci
	 * x abscissse
	 * y ordonnné
	 * c couleur à teter
	 * g coulreur corresponadante 
	 */
	public static boolean existN(Grille grille,int x,int y,Couleur c){
		if (x>0){
			if(c.equals(Couleur.BLANC)) 
				return ((grille.Contenu[x-1][y]).equals(Couleur.BLANC));
			else 
				if(c.equals(Couleur.NOIR)) 
					return ((grille.Contenu[x-1][y]).equals(Couleur.NOIR));
				else
					return false;
		}else 
			return false;
	}

	/**
	 * Détermine si le voisin SUD du  pion(x,y) existe et est de la même couleur que celui-ci
	 * x abscissse
	 * y ordonnné
	 * c couleur à teter
	 * g coulreur corresponadante 
	 */
	public static boolean existS(Grille grille,int x,int y,Couleur c){
		if (x<8){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x+1][y]).equals(Couleur.BLANC));
			else if(c.equals(Couleur.NOIR)) return ((grille.Contenu[x+1][y]).equals(Couleur.NOIR));
				else return false;
			
		}else return false;
	}

	/**
	 * Détermine si le voisin Est du  pion(x,y) existe et est de la même couleur que celui-ci
	 * x abscissse
	 * y ordonnné
	 * c couleur à teter
	 * g goban 
	 */
	public static boolean existE(Grille grille,int x,int y,Couleur c){
		if (y<8){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x][y+1]).equals(Couleur.BLANC));
			else if(c.equals(Couleur.NOIR)) return ((grille.Contenu[x][y+1]).equals(Couleur.NOIR));
				else return false;
		}
					
		else return false;
	}

	/**
	 * Détermine si le voisin OUEST du  pion(x,y) existe et est de la même couleur que celui-ci
	 * x abscissse
	 * y ordonnné
	 * c couleur à teter
	 * g goban 
	 */
	public static boolean existO(Grille grille,int x,int y,Couleur c){

		if (y>0){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x][y-1]).couleur.equals(c));
			else if(c.equals(Couleur.NOIR))	return ((grille.Contenu[x][y-1]).equals(new Pion(Couleur.NOIR,new Point(x,y-1))));
				else return false;
			
		}
		else return false; 
	}

	/**
	 * Détermine un groupe de pions (voisins directs, ou par transition. on a donc besoin: 
	 * @param g du goban
	 * @param lu point à partir duquel on veut déterminer les voisins
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
	 * Détermine un ensemble de représentant des différents groupe de pions de couleur blanche sur le goban
	 * il est à noter que le fait de disposer d'un seul representant par groupe est suffisant car avec DeterminerVoisins on peut reconstruire le groupe entier 
	 * @param g le goban
	 * @return retourne donc une liste de pions représentants les diférents groupes de pionsblancs sur la grille.
	 * 
	 */
	public static ArrayList<Point> determinerGroupesBlanc(Grille g){
		ArrayList<Point> listeG=new ArrayList<Point>();
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				if(g.Contenu[i][j].couleur.equals(Couleur.BLANC)){
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
	 * Pareil que la précédente mais avec des pions de couleur Noir
	 * @param g 
	 * @return
	 */
	public static ArrayList<Point> determinerGroupesNoir(Grille g){
		ArrayList<Point> listeG = new ArrayList<Point>();
		
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				if(g.Contenu[i][j].couleur.equals(Couleur.NOIR)){
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
	 * Détermine pour une grille le groupe de pions qui a la liberté minimale 
	 * @param g la grille courante
	 * @param listeG la liste des pions representant les différents groupes de pions du goban d'une couleur donnée.
	 * @return un entier qui représente cette valeur minimale.
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
