package fr.alma.ia;

import java.awt.Point;
import java.util.ArrayList;

import fr.alma.jeu.Grille;
import fr.alma.jeu.Pion;
import fr.alma.structure.Arbre;
import fr.alma.structure.Noeud;
import static fr.alma.jeu.Jeu.getLiberte; 
 
/**
 * Clase de gestion de l'int�lligence artificielle
 * @author lahuidi
 *
 */
public class Ia {
	
	public static Point alphaBeta(Arbre a){
		return new Point();
		
	}
	/**
	 * Evalue l'arbre en appliquant la fonction d'evaluation a chaque noeud
	 * @param a Arbre a �valu�
	 * @return L'arbre not�
	 */
	/*private Arbre noterArbre(Arbre a){
		
		a.racine.setNote(fonctionDevaluation(a.racine));
		return a;
		
	}*/
	
	/**
	 * Construit l'arbre a partir d'une grille
	 * @param grille la grille a trait�
	 * @return Arbre construit
	 */
	
	public static ArrayList<Point> voisins=new ArrayList<Point>();
	public static Arbre constuireArbre(Grille grille){
		
		Arbre a = new Arbre(grille);
				
		return a;
				
	}
	
	/**
	 * Fonction qui evalue un arbre
	 * @param n grille correspondant � un �tat du jeu dans une feille de l'arbre
	 * @return la valeur de l'evaluation
	 */
	
	
	// contrairement aux test d'existance des pions advesrse comme voisins dans la classe Jeu
	//ici on teste plutot le voisinnage d'un pion de m�me couleur  
	
	public static boolean existN(Grille grille,int x,int y,Pion.Couleur c){
		if (x>0)
			//return ((grille.Contenu[x-1][y]).couleur.equals(c));
			
			if(c.equals(Pion.Couleur.BLANC))
				return ((grille.Contenu[x-1][y]).equal(new Pion(Pion.Couleur.BLANC,new Point(x-1,y))));
			else
				if(c.equals(Pion.Couleur.NOIR))
					return ((grille.Contenu[x-1][y]).equal(new Pion(Pion.Couleur.NOIR,new Point(x-1,y))));
				else 
					return false;
			
		else
			return false;
	}

	public static boolean existS(Grille grille,int x,int y,Pion.Couleur c){
		if (x<8)
			//return ((grille.Contenu[x+1][y]).couleur.equals(c));
			
			if(c.equals(Pion.Couleur.BLANC))
				return ((grille.Contenu[x+1][y]).equal(new Pion(Pion.Couleur.BLANC,new Point(x+1,y))));
			else
				if(c.equals(Pion.Couleur.NOIR))
					return ((grille.Contenu[x+1][y]).equal(new Pion(Pion.Couleur.NOIR,new Point(x+1,y))));
				else
					return false;
		
		else
			return false;
	}

	public static boolean existE(Grille grille,int x,int y,Pion.Couleur c){
		if (y<8)
			//return ((grille.Contenu[x][y+1]).couleur.equals(c));
			
			if(c.equals(Pion.Couleur.BLANC))
				return ((grille.Contenu[x][y+1]).equal(new Pion(Pion.Couleur.BLANC,new Point(x,y+1))));
			else
				if(c.equals(Pion.Couleur.NOIR))
					return ((grille.Contenu[x][y+1]).equal(new Pion(Pion.Couleur.NOIR,new Point(x,y+1))));
				else
					return false;
					
		else
			return false;
	}

	public static boolean existO(Grille grille,int x,int y,Pion.Couleur c){

		if (y>0)
			//return ((grille.Contenu[x][y-1]).couleur.equals(c));
			if(c.equals(Pion.Couleur.BLANC))
				return ((grille.Contenu[x][y-1]).couleur.equals(c));
			else
				if(c.equals(Pion.Couleur.NOIR))
					return ((grille.Contenu[x][y-1]).equal(new Pion(Pion.Couleur.NOIR,new Point(x,y-1))));
				else
					return false;
		else
			return false;
	}

	
	public static void DeterminerVoisins(Grille g,Point p){
	ArrayList<Point> v=new ArrayList<Point>();
	
	if(g.Contenu[p.x][p.y].equal(new Pion(p)))
		return;
	else{
		if(voisins.size()!=0)
			if (!(voisins.contains(p))) 
				voisins.add(p);
	if ((existN(g,p.x,p.y,g.Contenu[p.x][p.y].couleur)))
		DeterminerVoisins(g,new Point(p.x-1,p.y));
	if ((existS(g,p.x,p.y,g.Contenu[p.x][p.y].couleur)))
		DeterminerVoisins(g,new Point(p.x+1,p.y));
	if ((existO(g,p.x,p.y,g.Contenu[p.x][p.y].couleur)))
		DeterminerVoisins(g,new Point(p.x,p.y-1));
	if ((existE(g,p.x,p.y,g.Contenu[p.x][p.y].couleur)))
		DeterminerVoisins(g,new Point(p.x,p.y+1));
	}
	System.out.println(voisins.toString());
	//return voisins;
	
	}
	private static ArrayList<Point> determinerGroupesBlanc(Grille g){
		ArrayList<Point> listeG=new ArrayList<Point>();
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				if(g.Contenu[i][j].couleur.equals(Pion.Couleur.BLANC)){
					voisins=new ArrayList<Point>();
					DeterminerVoisins(g, new Point(i,j));
					boolean groupeExistant=false;
					for(int k=0;k<voisins.size();k++)
						if (listeG.contains(voisins.get(k)))
							groupeExistant=true;
					if(!groupeExistant){
						listeG.add(new Point(i,j));
						//System.out.println("ajout de "+i+"-"+j);
					}
					voisins=new ArrayList<Point>();
				}
			}
		return listeG;
				
	}
	
	private static ArrayList<Point> determinerGroupesNoir(Grille g){
		ArrayList<Point> listeG=new ArrayList<Point>();
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				if(g.Contenu[i][j].couleur.equals(Pion.Couleur.NOIR)){
					voisins=new ArrayList<Point> ();
					DeterminerVoisins(g, new Point(i,j));
					boolean groupeExistant=false;
					for(int k=0;k<voisins.size();k++)
						if (listeG.contains(voisins.get(k)))
							groupeExistant=true;
					if(!groupeExistant)
						listeG.add(new Point(i,j));
				}
				voisins=new ArrayList<Point> ();
			}
		return listeG;

	}
	
	private static int determineMin(Grille g,ArrayList<Point> listeG){
		int min=getLiberte(g, listeG.get(0), g.Contenu[listeG.get(0).x][listeG.get(0).y].couleur);
		for(int k=0;k<listeG.size();k++){
			Point p=listeG.get(k);
			int x=getLiberte(g, p, g.Contenu[p.x][p.y].couleur);
			if(x<min)
				min=x;
		}
		return min;
	}
	public static int fonctionEvaluation(Grille g){
		ArrayList<Point> listeGN=determinerGroupesNoir(g);
		ArrayList<Point> listeGB=determinerGroupesBlanc(g);
		System.out.println(listeGB.size());
		System.out.println(listeGN.size());
		int minB,minN;
		if (listeGB.size()==0)
			minB=0;
		else
		 minB = determineMin(g, listeGB);
		if (listeGN.size()==0)
			minN=0;
		else
		 minN = determineMin(g, listeGN);
		return minN-minB;
	}
	
	private void attribuerNote(Noeud n){
		// d�terminer la grille correspondant au noeud n, l'�valuer et mettre dans le noeud la note retourn�e 
	}
}
