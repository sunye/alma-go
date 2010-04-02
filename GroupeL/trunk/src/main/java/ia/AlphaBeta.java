package ia;

import java.util.List;

import jeu.Coordonnees;
import jeu.Couleur;
import jeu.GobanStructure;
import jeu.GroupePieces;

public class AlphaBeta {
	
	private GobanStructure plateau;
	private Couleur color; 

	/* Constructor */
	public AlphaBeta(GobanStructure plateau, Couleur color) {
		super();
		this.plateau = plateau;
		this.color = color;
	}
	
	public AlphaBeta() {
		super();
		this.plateau = new GobanStructure();
		this.color = Couleur.none;
	}
	
	/* Function of decision tree creation (without parameters) */
	public Coordonnees createTree()
	{
		Coordonnees toPlay = new Coordonnees();
		int bestNote = -100000;
		
		/* We run the recursive function on every free square of the plateau */	
		List<Coordonnees> emptySquares = plateau.getCoordLibre();
		int note = -100000;
		
		for(Coordonnees coordTmp : emptySquares){
			
			if(plateau.coupValide(coordTmp, color)){
			
				note = createNode(coordTmp, 1, 3, -100000, 100000);
				
				if (note>bestNote)
				{
					/* We have the best move and we store it */
					bestNote = note;
					toPlay = coordTmp;
				}
				
			}
		}
		
		return toPlay; 
	}
	
	/* Function of decision tree creation (with parameters) */
	public Coordonnees createTree(GobanStructure plateau, Couleur color)
	{
		this.plateau = plateau;
		this.color = color;		
		
		Coordonnees toPlay = new Coordonnees();
		int bestNote = -100000;
		
		/* We run the recursive function on every free square of the plateau */	
		List<Coordonnees> emptySquares = plateau.getCoordLibre();
		int note = -100000;
		
		for(Coordonnees coordTmp : emptySquares){
			
			if(plateau.coupValide(coordTmp, color)){
			
				note = createNode(coordTmp, 1, 3, -100000, 100000);
				//System.out.print(">"+coordTmp.toString()+":"+note);
				if (note>bestNote)
				{
					System.out.println("->"+note);
					/* We have the best move and we store it */
					bestNote = note;
					toPlay = coordTmp;
				}
			
			}
		}
		
		
		return toPlay; 
	}	
	/*
	 * Algorithme Alpha-Beta
	 * 
	 * fonction ALPHABETA(P, alpha, beta)  alpha est toujours inferieur à beta 
     * si P est une feuille alors
     *  retourner la valeur de P
     * sinon
     *  si P est un noeud Min alors
     *      Val = infini
     *      pour tout fils Pi de P faire
     *          Val = Min(Val, ALPHABETA(Pi, alpha, beta))                
     *          si alpha >= Val alors   coupure alpha 
     *              retourner Val
     *          beta = Min(beta, Val)
     *      finpour            
     *  sinon
     *      Val = -infini
     *      pour tout fils Pi de P faire
     *          Val = Max(Val, ALPHABETA(Pi, alpha, beta))                
     *          si Val >= beta alors  coupure beta 
     *              retourner Val
     *          alpha = Max(alpha, Val)
     *      finpour
     *  finsi
     * retourner Val
     * finsi
     * fin
	 * 
	 */
	
	private Integer createNode(Coordonnees coord, Integer depth, Integer depthMax, Integer alpha, Integer beta)
	{
		int note = 0;
					
		/* We simulate that we put a token of the right color at coord */
		hitSimul(coord, depth);
		
		if (depth == depthMax)
		{
			/* We are in a leaf */
			note = evaluation(depth);
			plateau.retirePiece(coord);
			
			return note;
			
		} else {

				/* We try to trunk the tree */
				List<Coordonnees> emptySquares = plateau.getCoordLibre();

				if ((depth % 2) != 0)
				{
					note = 100000;
					
					/* The depth is pair : we search for the minimal note value of all its sons. */
					/* Here, we create its sons when we encounter an empty square */
					
					for(Coordonnees coordTmp : emptySquares){
						
						if(plateau.coupValide(coordTmp, color.invCouleur())){
							
							note = Math.min(note, createNode(coordTmp, depth+1, depthMax, alpha, beta));
						
							if (alpha >= note)
							{
								/* We don't need to go further, so we stop here */
								plateau.retirePiece(coord);
								return note;							
							}	
							
							beta = Math.min(beta, note);
						}
					}
	
				} else {
					
					note = -100000;
					
					/* The depth is not pair : we search for the maximal note value of all its sons. */
					/* Here, we create its sons when we encounter an empty square */
					
					for(Coordonnees coordTmp : emptySquares){

						if(plateau.coupValide(coordTmp, color)){
							
							note = Math.max(note, createNode(coordTmp, depth+1, depthMax, alpha, beta));
									
							if (beta <= note)
							{
								/* We don't need to go further, so we stop here */
								plateau.retirePiece(coord);
								return note;							
							}
									
							alpha = Math.max(alpha, note);
						}
			
					}
					
				}		
		}
		plateau.retirePiece(coord);
		return note;
	}
	
	private void hitSimul(Coordonnees coord, int depth){
		
		if ((depth % 2) == 0)
		{					
			plateau.ajoutPiece(coord, color);
		} else {
			plateau.ajoutPiece(coord, color.invCouleur());
								
		}		
	}
	
	/**
	 * 
	 * @param depth
	 * @return
	 */
	private Integer evaluation(Integer depth)
	{
		Integer note=0;
		/*
		System.out.print(" ");
		for( int t=1 ; t<=plateau.getTaille() ; t++){
			System.out.print(t);
		}	
		System.out.println();
		
		for( int y=1 ; y<=plateau.getTaille() ;  y++){
			System.out.print(y);
			for( int x =1 ; x<=plateau.getTaille() ; x++){
							
				if(plateau.getPlateau()[x][y] == null){
					System.out.print(" ");
				}else if(plateau.getPlateau()[x][y].getCouleur() == Couleur.noir){
					System.out.print("X");
				}else if(plateau.getPlateau()[x][y].getCouleur() == Couleur.blanc){
					System.out.print("O");
				}else{
					System.out.print("8");
				}
			}
			System.out.println(y);
		}
		
		System.out.print(" ");
		for( int t=1 ; t<=plateau.getTaille() ; t++){
			System.out.print(t);
		}	
		System.out.println();
		*/
	
		
		if (gagne(color)){
			note = 100000;
		}else{
			note = note + 1000 * derniereLiberte(color);
			note = note - 1000 * derniereLiberte(color.invCouleur());
			
			note = note - nbLiberte(color.invCouleur());
		}
		
		return note;
	}
	
	private Integer nbLiberte(Couleur couleur){
		Integer note=0;
		
		for(GroupePieces g : plateau.getGroupes(couleur)){
			note = note - g.getLiberte();
		}			
		return note;
	}
	
	private boolean gagne(Couleur couleur){
		
		for(GroupePieces g : plateau.getGroupes(couleur.invCouleur())){
			if(g.getLiberte()==0){
				return true;
			}
		}			
		return false;
		
	}
	
	/**
	 * 
	 * @param couleur
	 * @return
	 */
	private Integer derniereLiberte(Couleur couleur){
		
		Integer cpt=0;
		
		for(GroupePieces g : plateau.getGroupes(couleur)){
			if(g.getLiberte() == 1){
				cpt++;
			}
		}	
		
		return cpt;
	}
	
	
	
	
	
}

