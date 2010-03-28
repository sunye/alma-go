/**
 * @author Dejean Charles - Pottier Vincent
 * 
 * Classe permettant de gere un plateau de go. 
 * 
 */


package jeu;

import java.util.LinkedList;
import java.util.List;

public class GobanStructure {
	
	private List<GroupePieces> blancs;
	private List<GroupePieces> noirs;
	private Integer taille;
	
	private GroupePieces[][] plateau; 
	
	/* Getters - Setters */		
	public List<GroupePieces> getBlancs() {
		return blancs;
	}

	public List<GroupePieces> getNoirs() {
		return noirs;
	}
	public Integer getTaille() {
		return taille;
	}
	
	/* Constructeur */	
	public GobanStructure() {
		super();
		blancs = new LinkedList<GroupePieces>();
		noirs = new LinkedList<GroupePieces>();
		taille = 9;
		plateau = new GroupePieces[taille+1][taille+1];
		
	}
	
	public GobanStructure(Integer t) {
		super();
		blancs = new LinkedList<GroupePieces>();
		noirs = new LinkedList<GroupePieces>(); 		
		this.taille = t;
		plateau = new GroupePieces[taille+1][taille+1];
	}
	
	/* Fonctions */
	
	/**
	 * Cette fonction retourne ce qu'il y a a une position donnee.
	 * @param coord : coordonnee de la position a tester;
	 * @return 0 : position vide
	 *         1 : position blanche
	 *         2 : position noire
	 *         -1 : position hors plateau
	 * 
	 */
	public Integer testPosition(Coordonnees coord){
		return testPosition(coord.getX(),coord.getY());
	}
	
	/**
	 * Cette fonction retourne ce qu'il y a a une position donnee.
	 * @param x,y : coordonnee de la position a tester;
	 * @return  0 : position vide
	 *          1 : position blanche
	 *          2 : position noire
	 *         -1 : position hors plateau

	 */
	public Integer testPosition(Integer x, Integer y){
		
		Coordonnees coord = new Coordonnees(x,y);
		
		if(coord.getX()>taille || coord.getY()>taille || coord.getX()<=0 || coord.getY()<=0){
			return -1;
		}
				
		if( (plateau[x][y]) == null ){
			return 0;
		}else if((plateau[x][y]).getCouleur() == Couleur.noir){
			return 2;
		}
		
		/* sans matrice
		for(GroupePieces g : blancs){
			if(g.testPosition(coord)){
				return 1;
			}			
		}
		for(GroupePieces g : noirs){
			if(g.testPosition(coord)){
				return 2;
			}			
		}*/
		
		return 1;
	}	

	
	/**
	 * @param coord : coordonnee de la position ou poser la piece
	 * @param couleur : couleur de la piece a poser. true blanc, false noir.
	 * @return vrai si la piece a ete posee
	 */
	public boolean ajoutPiece(Coordonnees coord, Couleur couleur){
		
		if (this.testPosition(coord)==0){
			/* La position est vide */
			
			/* On test si la piece a poser est adjacente a un groupe de sa couleur deja existant */
			GroupePieces nouv=new GroupePieces(coord,this.testLiberte(coord),couleur);
			
			/* On fusionne les groupes adjacent */				
			fusion(nouv,groupesAdjacents(coord,couleur),couleur);

			
			for(Coordonnees c : nouv.getPieces()){
				plateau[c.getX()][c.getY()] = nouv;
			}
			
			for(GroupePieces g : blancs){
				g.setLiberte(caluleLibertees(g));			
			}
			for(GroupePieces g : noirs){
				g.setLiberte(caluleLibertees(g));		
			}			
					
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * fusion un groupe de pieces avec une liste de groupe de pieces et l'ajoute dans la liste d'une des couleurs
	 * @param nouv : groupe de piece qui va recevoir la fusion
	 * @param groupesAdjacents : liste des groupe a fusionner a nouv
	 * @param estBlanc : true si la couleur des groupes est blanc , false pour noir
	 */
	private void fusion(GroupePieces nouv,
			List<GroupePieces> groupesAdjacents,Couleur couleur) {
		
		/* TEST
		for(Coordonnees p : nouv.getPieces()  ){
			System.out.print("- (" + p.getX() + "," + p.getY() + ") est fusionner avec : ");
		}
		for(GroupePieces gp : groupesAdjacents){
			System.out.print("[ ");
			for(Coordonnees pa : gp.getPieces()){
				System.out.print("(" + pa.getX() + "," + pa.getY() + ")");
			}
			System.out.print(" ]");
		}
		System.out.println();
	*/
		
		/* on parcour les groupe de piece adjacent */
		for(GroupePieces g : groupesAdjacents){
			for (Coordonnees coord : g.getPieces() ){
				nouv.getPieces().add(coord);
			}
			
			if(couleur == Couleur.blanc){		
				blancs.remove(g);
			}else{
				noirs.remove(g);
			}			
		}	
				
		if(couleur == Couleur.blanc){		
			blancs.add(nouv);
		}else{
			noirs.add(nouv);
		}
		
	}
	
	/**
	 * calcul le nombre de liberte d'un groupe de pieces
	 * @param nouv
	 * @return
	 */
	private Integer caluleLibertees(GroupePieces nouv) {
		
		LinkedList<Coordonnees> coordLib = new LinkedList<Coordonnees>();
		
		for(Coordonnees coord : nouv.getPieces()){
			
			if(testPosition(coord.getX()+1, coord.getY()) == 0){
				Coordonnees nouvCoord = new Coordonnees(coord.getX()+1, coord.getY());
				boolean dejaVu = false;
				for(Coordonnees cl : coordLib){
					dejaVu = dejaVu || nouvCoord.estEgal(cl);
				}
				for(Coordonnees cg : nouv.getPieces()){
					dejaVu = dejaVu || nouvCoord.estEgal(cg);
				}
				if(!dejaVu){
					coordLib.add(nouvCoord);
				}
			}
			if(testPosition(coord.getX()-1, coord.getY()) == 0){
				Coordonnees nouvCoord = new Coordonnees(coord.getX()-1, coord.getY());
				boolean dejaVu = false;
				for(Coordonnees cl : coordLib){
					dejaVu = dejaVu || nouvCoord.estEgal(cl);
				}
				for(Coordonnees cg : nouv.getPieces()){
					dejaVu = dejaVu || nouvCoord.estEgal(cg);
				}
				if(!dejaVu){
					coordLib.add(nouvCoord);
				}
			}
			if(testPosition(coord.getX(), coord.getY()+1) == 0){
				Coordonnees nouvCoord = new Coordonnees(coord.getX(), coord.getY()+1);
				boolean dejaVu = false;
				for(Coordonnees cl : coordLib){
					dejaVu = dejaVu || nouvCoord.estEgal(cl);
				}
				for(Coordonnees cg : nouv.getPieces()){
					dejaVu = dejaVu || nouvCoord.estEgal(cg);
				}
				if(!dejaVu){
					coordLib.add(nouvCoord);
				}
			}
			if(testPosition(coord.getX(), coord.getY()-1) == 0){
				Coordonnees nouvCoord = new Coordonnees(coord.getX(), coord.getY()-1);
				boolean dejaVu = false;
				for(Coordonnees cl : coordLib){
					dejaVu = dejaVu || nouvCoord.estEgal(cl);
				}
				for(Coordonnees cg : nouv.getPieces()){
					dejaVu = dejaVu || nouvCoord.estEgal(cg);
				}
				if(!dejaVu){
					coordLib.add(nouvCoord);
				}
			}		
		}		
			
		/* TEST
		for(Coordonnees c : coordLib){
			System.out.print("("+ c.getX() +","+c.getY() +"),");
		}
		System.out.println();
		 */
		return coordLib.size();
	}
	
	/**
	 *  calcul le nombre de liberte d'une piece
	 * @param coord
	 * @return le nombre de liberte
	 */
	private Integer testLiberte(Coordonnees coord) {
		Integer lib=0;
		
		if( testPosition(coord.getX()+1, coord.getY()) == 0 ){
			++lib;
		}
		if( testPosition(coord.getX()-1, coord.getY()) == 0 ){
			++lib;
		}
		if( testPosition(coord.getX(), coord.getY()+1) == 0 ){
			++lib;
		}
		if( testPosition(coord.getX(), coord.getY()-1) == 0 ){
			++lib;
		}

		return lib;
	}
	
	/**
	 * renvoie l'ensemble des groupes d'une couleur adjacent a une coordonnée
	 * @param c
	 * @param estBlanc
	 * @return
	 */
	private List<GroupePieces> groupesAdjacents(Coordonnees c , Couleur couleur){
		
		List<GroupePieces> gadj = new LinkedList<GroupePieces>();
		
		if( (plateau[c.getX()-1][c.getY()] != null) && ( plateau[c.getX()-1][c.getY()].getCouleur() == couleur) ){
			gadj.add(plateau[c.getX()-1][c.getY()]);
		}
		if( (plateau[c.getX()+1][c.getY()] != null) && ( plateau[c.getX()+1][c.getY()].getCouleur() == couleur) ){
			gadj.add(plateau[c.getX()+1][c.getY()]);
		}
		if( (plateau[c.getX()][c.getY()-1] != null) && ( plateau[c.getX()][c.getY()-1].getCouleur() == couleur) ){
			gadj.add(plateau[c.getX()][c.getY()-1]);
		}
		if( (plateau[c.getX()][c.getY()+1] != null) && ( plateau[c.getX()][c.getY()+1].getCouleur() == couleur) ){
			gadj.add(plateau[c.getX()][c.getY()+1]);
		}
		/* sans matrice
		List<GroupePieces> groupeTraite; 
		 
		if(couleur == Couleur.blanc){		
			groupeTraite=blancs;
		}else{
			groupeTraite=noirs;
		}
		
		for(GroupePieces g : groupeTraite){
			for(Coordonnees coord : g.getPieces() ){
				if( c.estAdjacent(coord) && (!gadj.contains(g))){
					gadj.add(g);				
				}
			}
		}
		
		*/
		return gadj;
	}
	
	/**
	 * 
	 * @return
	 */
	public Integer fin(){
		
		for(GroupePieces g : blancs ){
			if(g.getLiberte() == 0){
				return 2;
			}
		}
		for(GroupePieces g : noirs ){
			if(g.getLiberte() == 0){
				return 1;
			}
		}
		return 0;
	}

	/**
	 * 
	 * @param coord
	 * @param couleur
	 * @return
	 */
	public boolean coupValide(Coordonnees coord, Couleur couleur){
		
		int liberte = testLiberte(coord);
		
		for(GroupePieces ga : groupesAdjacents(coord, couleur)){
			liberte = liberte + ga.getLiberte() -1;
		}
		
		return liberte>0;
	}

	
}
