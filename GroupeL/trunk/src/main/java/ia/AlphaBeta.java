package ia;

import java.util.LinkedList;
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
	
	/* Function of decision tree creation */
	public Coordonnees createTree()
	{
		Coordonnees toPlay = new Coordonnees();
		int bestNote = -1;
		
		/* We run the recursive function on every free square of the plateau */	
		List<Coordonnees> emptySquares = plateau.getCoordLibre();
		int note = -100000;
		
		for(Coordonnees coordTmp : emptySquares){
			
			note = createNode(coordTmp, 1, 3, -100000, 100000);
			
			if (note>bestNote)
			{
				/* We have the best move and we store it */
				bestNote = note;
				toPlay = coordTmp;
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
		
		if (depth == depthMax)
		{
			/* We are in a leaf */
			return evaluation(coord);
			
		} else {
				/* We simulate that we put a token of the right color at coord */
				hitSimul(coord, color, depth);
				
				/* We try to trunk the tree */
				List<Coordonnees> emptySquares = plateau.getCoordLibre();

				if ((depth % 2) != 0)
				{
					/* The depth is pair : we search for the minimal note value of all its sons. */
					/* Here, we create its sons when we encounter an empty square */
					
					for(Coordonnees coordTmp : emptySquares){
						
						note = Math.min(note, createNode(coordTmp, depth+1, depthMax, alpha, beta));
						
						if (alpha >= note)
						{
							/* We don't need to go further, so we stop here */
							return note;							
						}
						
						beta = Math.min(beta, note);
					}
	
				} else {
					
					/* The depth is not pair : we search for the maximal note value of all its sons. */
					/* Here, we create its sons when we encounter an empty square */
					
					for(Coordonnees coordTmp : emptySquares){
						
						note = Math.max(note, createNode(coordTmp, 1, 3, alpha, beta));
								
						if (beta <= note)
						{
							/* We don't need to go further, so we stop here */
							return note;							
						}
								
						alpha = Math.max(alpha, note);
			
					}
					
				}
			
		}
		
		return note;
	}
	
	private void hitSimul(Coordonnees coord, Couleur col, int depth){
		
		if ((depth % 2) == 0)
		{					
			if (color == Couleur.blanc)
			{
				plateau.ajoutPiece(coord, Couleur.blanc);
			} else {
				plateau.ajoutPiece(coord, Couleur.noir);
			}
		} else {
			if (color == Couleur.blanc)
			{
				plateau.ajoutPiece(coord, Couleur.noir);
			} else {
				plateau.ajoutPiece(coord, Couleur.blanc);
			}					
		}		
	}
	
	private Integer evaluation(Coordonnees coord)
	{
		Integer note = 0;
		
		// TODO
		
		return note;
	}
	
	
	
	
	
	
	
	

}
