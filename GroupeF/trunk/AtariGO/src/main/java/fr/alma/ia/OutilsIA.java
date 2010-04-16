package fr.alma.ia;

import static fr.alma.jeu.LibertePion.getLiberte;

import java.awt.Point;
import java.util.ArrayList;
import fr.alma.jeu.Couleur;
import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;

/**
 * Used method for the class ia
 * @author Ngassa Hubert
 *
 */
public class OutilsIA {
	
	public static ArrayList<Point> voisins = new ArrayList<Point>();
	
	/**
	 * Determine if the north neighbor of pawn (x,y) exists and if they have the same color
	 * x abscissa
	 * y ordinates
	 * c color to test
	 * g corresponding color
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
	 * Determine if the south neighbor of pawn (x,y) exists and if they have the same color
	 * x abscissa
	 * y ordinates
	 * c color to test
	 * g corresponding color
	 */
	
	public static boolean existS(Grille grille,int x,int y,Couleur c){
		if (x<8){
			if(c.equals(Couleur.BLANC)) return ((grille.Contenu[x+1][y]).equals(Couleur.BLANC));
			else if(c.equals(Couleur.NOIR)) return ((grille.Contenu[x+1][y]).equals(Couleur.NOIR));
				else return false;
			
		}else return false;
	}

	/**
	 * Determine if the East neighbor of pawn (x,y) exists and if they have the same color
	 * x abscissa
	 * y ordinates
	 * c color to test
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
	 * Determine if the West neighbor of pawn (x,y) exists and if they have the same color
	 * x abscissa
	 * y ordinates
	 * c color to test
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
	 * This method determine a group of pawns (Direct neighbors or by transition)So we need: 
	 * @param g of the goban
	 * @param p Point from which we can determine all the neighbors
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
	 * This method determine a set of pawns representing the different groups of white pawns on the goban
	 * It will be notice that the fact that we dispose of only one representative per group is enough.Because with DeterminerVoisins we can reconstruct the entire group 
	 * @param g the goban
	 * @return listeG a list of pawns representing the different groups of white pawns on the goban
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
	 * Same as the preceding method but with black pawns
	 * @param g the goban
	 * @return listeG a list of pawns representing the different groups of black pawns on the goban
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
	 * This Method determine the group of pawns with the less freedom for a goban
	 * @param g The current grid
	 * @param listeG a list of pawns representing the different groups of pawns with a given color on the goban
	 * @return min This integer represents the minimal value.
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
