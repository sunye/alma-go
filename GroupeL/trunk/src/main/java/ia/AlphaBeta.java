package ia;

import jeu.Coordonnees;
import jeu.Couleur;
import jeu.GobanStructure;

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
		
		/* We run the recursive function on every free square of the plateau */	
		
		for(int i = 1; i >= 10; i++)
		{
			for(int j = 1; j >= 10; i++)
			{
				if(plateau.getPlateau()[i][j].getCouleur() == Couleur.none)
				{	
					createNode(new Coordonnees(i, j), 1, 3, -1000, 1000);
				}
			}			
		}
	
		
		return toPlay; 
	}
	
	private Integer createNode(Coordonnees coord, Integer depth, Integer depthMax, Integer alpha, Integer beta)
	{
		int note = 0;
		
		if (depth == depthMax)
		{
			/* We are in a leaf */
			return evaluation(coord);
			
		} else 
			{
			
			/* We simulate that we put a token at coord */
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
			
			if ((depth % 2) == 0)
			{
				/* The depth is pair : we search for the minimal note value */
				// note = Math.min(note, b); TODO
				
				
				
			}
			
			
			
			
			
			
		}
		
		return note;
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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	private Integer evaluation(Coordonnees coord)
	{
		Integer note = 0;
		
		
		
		return note;
	}
	
	
	
	
	
	
	
	

}
