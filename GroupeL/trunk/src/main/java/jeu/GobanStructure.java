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
	
	/* representation du plateau de jeux dans chaque case est stocker 
	 * une reference vers un groupe de pieces 
	 */
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
	public GroupePieces[][] getPlateau() {
		return plateau;
	}
	
	/* permet de recupere le groupe de pieces correspondant a la couleur chosi  */
	public List<GroupePieces> getGroupes(Couleur couleur){
		if(couleur == Couleur.blanc){
			return blancs;
		}else if (couleur == Couleur.noir){
			return noirs;
		}
		return null;
	}

	/**
	 * fonction permettant de recuperer une liste des coordonnée libre du plateau
	 * @return
	 */
	public List<Coordonnees> getCoordLibre(){
		
		LinkedList<Coordonnees> lcl = new LinkedList<Coordonnees>();
		
		for(int y=1 ; y<= taille ; y++){
			for(int x=1 ; x<=taille ; x++){
				if(plateau[x][y]==null){
					lcl.add(new Coordonnees(x, y));
				}
			}
		}
		
		return lcl;
	}
	
	
	
	/* Constructeur */	
	public GobanStructure() {
		super();
		blancs = new LinkedList<GroupePieces>();
		noirs = new LinkedList<GroupePieces>();
		taille = 9;
		/* on prend le plateau avec 2 ligne et colonne de plus pour avoir toujours des cases vide autour */
		plateau = new GroupePieces[taille+2][taille+2];
		
	}
	
	public GobanStructure(Integer t) {
		super();
		blancs = new LinkedList<GroupePieces>();
		noirs = new LinkedList<GroupePieces>(); 		
		this.taille = t;
		/* on prend le plateau avec 2 ligne et colonne de plus pour avoir toujours des cases vide autour */
		plateau = new GroupePieces[taille+2][taille+2];
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
		
		return 1;
	}	

	
	/**
	 * @param coord : coordonnee de la position ou poser la piece
	 * @param couleur : couleur de la piece a poser. true blanc, false noir.
	 * @return vrai si la piece a ete posee
	 */
	public boolean ajoutPiece(Coordonnees coord, Couleur couleur){
		
		if (testPosition(coord)==0 ){
			/* La position est vide */
			
			/* On cré un nouveau groupe de piece en lui donnant la nouvel piece */
			GroupePieces nouv=new GroupePieces(coord,testLiberte(coord), couleur);
			
			/* On fusionne les groupes adjacent */
			for(GroupePieces g : groupesAdjacents(coord,couleur)){
				nouv.getPieces().addAll(g.getPieces());
				
				if(couleur == Couleur.blanc){
					blancs.remove(g);
				}else{
					noirs.remove(g);
				}
			}
						
			/* on calcul le nombre de liberte du nouveau groupe */
			nouv.setLiberte(calculeLibertees(nouv));
			
			getGroupes(couleur).add(nouv);
			
			/* on diminu les liberte de chaque groupe de la couleur adverse adjacent de 1*/
			for(GroupePieces g : groupesAdjacents(coord,couleur.invCouleur())){
				g.setLiberte(g.getLiberte()-1);
			}
			
			/* on met a jour le plateau de jeu */
			for(Coordonnees c : nouv.getPieces()){
				plateau[c.getX()][c.getY()] = nouv;
			}			
					
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 
	 * @param coord
	 * @return
	 */
	public boolean retirePiece(Coordonnees coord){
		
		if( testPosition(coord) > 0 ){
			
			GroupePieces gc = plateau[coord.getX()][coord.getY()];			
					
			for(Coordonnees p : gc.getPieces()){
				if(p.estEgal(coord)){
					gc.getPieces().remove(p);
					break;
				}
			}
			
			plateau[coord.getX()][coord.getY()] = null;		
			
			if(!gc.getPieces().isEmpty()){
				gc.setLiberte(calculeLibertees(gc));
			}else{
				getGroupes(gc.getCouleur()).remove(gc);
			}
			
			for(GroupePieces ga : groupesAdjacents(coord, gc.getCouleur().invCouleur())){
				ga.setLiberte(calculeLibertees(ga));
			}
			
			
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * calcul le nombre de liberte d'un groupe de pieces
	 * @param nouv
	 * @return
	 */
	private Integer calculeLibertees(GroupePieces nouv) {
		
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
		
		GroupePieces gt = plateau[c.getX()-1][c.getY()];
		if( (gt != null) && ( gt.getCouleur() == couleur) ){
			gadj.add(gt);
		}
		
		gt = plateau[c.getX()+1][c.getY()];
		if( (gt != null) && ( gt.getCouleur() == couleur) && !gadj.contains(gt)){
			gadj.add(gt);
		}
		
		gt=plateau[c.getX()][c.getY()-1];
		if( (gt != null) && ( gt.getCouleur() == couleur) && !gadj.contains(gt)){
			gadj.add(gt);
		}
		
		gt=plateau[c.getX()][c.getY()+1];
		if( (gt != null) && ( gt.getCouleur() == couleur) && !gadj.contains(gt)){
			gadj.add(gt);
		}

		return gadj;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean fin(Couleur joueur){
		
		for(GroupePieces g : getGroupes(joueur.invCouleur()) ){
			if(g.getLiberte() == 0){
				return true;
			}
		}

		return false;
	}

	/**
	 * Test si le coup jouer par coord n'est pas un scuicide
	 * @param coord
	 * @param couleur
	 * @return
	 */
	public boolean coupValide(Coordonnees coord, Couleur couleur){
		
		boolean ok=false;
		
		/* on test si la place est libre */
		if(plateau[coord.getX()][coord.getY()] == null){
			ok=true;
			/* si la place tester n'a pas de liberter */
			if(testLiberte(coord) == 0){
				ok=false;
				/* on verifie si cette place est la derniere liberte des groupes ajacent de meme couleur */
				for(GroupePieces ga : groupesAdjacents(coord, couleur)){
					ok = ok || (ga.getLiberte() > 1)  ;
				}
				/* si en prenant cette place on suprime la derniere liberter des groupe adjacent de meme couleur */
				if(!ok){
					/* on verifie si on suprime la derniere liberte d'un groupe adjacent de la couleur adverse */
					for(GroupePieces ga : groupesAdjacents(coord, couleur.invCouleur())){
						ok = ok || (ga.getLiberte() == 1) ;
					}
				}	
			}
		}
		return ok;
	}

	
}
