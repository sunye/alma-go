package ia;

import game.Coordinates;
import game.Color;
import game.GobanStructure;
import game.GroupPawns;

import java.util.LinkedList;
import java.util.List;


public class AlphaBeta {
	
	private final static int NOTE_MIN = -100000;
	private final static int NOTE_MAX = 100000;
	
	private GobanStructure goban;
	private Color color; 

	private boolean moveForced = false;
	private Coordinates bestMove;

	private Integer depthMax;


	/* Constructor */
	public AlphaBeta(GobanStructure plateau, Color color, Integer prof) {
		super();
		this.goban = plateau;
		this.color = color;
		this.depthMax = prof;
	}
	
	public AlphaBeta(Integer profondeur) {
		super();
		this.goban = new GobanStructure();
		this.color = Color.NONE;
		this.depthMax = profondeur;
	}
	
	public AlphaBeta() {
		super();
		this.goban = new GobanStructure();
		this.color = Color.NONE;
		this.depthMax = 3;
	}
	
	/* Function of decision tree creation (without parameters) */
	public Coordinates createTree()
	{
		Coordinates toPlay = new Coordinates();
		int bestNote = NOTE_MIN;
		moveForced = false;
		
		/* We run the recursive function on every free square of the plateau */	
		List<Coordinates> emptySquares = goban.getFreeCoord();
		int note = NOTE_MIN;
		
		for(Coordinates coordTmp : emptySquares){
			
			if(goban.moveValid(coordTmp, color)){
			
				note = createNode(coordTmp, 1, NOTE_MIN, NOTE_MAX);
				
				if (note>bestNote)
				{
					/* We have the best move and we store it */
					bestNote = note;
					toPlay = coordTmp;
					bestMove = toPlay;
				}
				
			}
		}
		
		return toPlay; 
	}
	
	/* Function of decision tree creation (with parameters) */
	public Coordinates createTree(GobanStructure plateau, Color color)
	{
		this.goban = plateau;
		this.color = color;
		moveForced = false;
		
		Coordinates toPlay = new Coordinates();
		int bestNote = NOTE_MIN;
		
		/* We run the recursive function on every free square of the plateau */	
		List<Coordinates> emptySquares = plateau.getFreeCoord();
		int note = NOTE_MIN;
		
		for(Coordinates coordTmp : emptySquares){
			
			if(plateau.moveValid(coordTmp, color)){
			
				note = createNode(coordTmp, 1, NOTE_MIN, NOTE_MAX);
				
				//System.out.print(">"+coordTmp.toString()+":"+note);
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
	
	private Integer createNode(Coordinates coord, Integer depth, Integer alpha, Integer beta)
	{
		int note = 0;
					
		/* We simulate that we put a token of the right color at coord */
		hitSimul(coord, depth);
		

		if (moveForced){
			System.out.println("coup forcé");
		}
		
		if ((depth == depthMax) && (!moveForced))

		{
			/* We are in a leaf */
			note = evaluation(depth);
			goban.removePawn(coord);
			
			return note;
			
		} else {

				/* We try to trunk the tree */
				List<Coordinates> emptySquares = goban.getFreeCoord();
				note = evaluation(depth);
				
				if ((depth % 2) != 0)
				{
					//note = NOTE_MAX;

					/* The depth is pair : we search for the minimal note value of all its sons. */
					/* Here, we create its sons when we encounter an empty square */
					
					for(Coordinates coordTmp : emptySquares){
						
						if(goban.moveValid(coordTmp, color.invColor())){
							
							note = Math.min(note, createNode(coordTmp, depth+1, alpha, beta));
						
							if (alpha >= note)
							{
								/* We don't need to go further, so we stop here */
								goban.removePawn(coord);
								return note;							
							}	
							
							beta = Math.min(beta, note);
						}
					}
	
				} else {
					
					//note = NOTE_MIN;
					
					/* The depth is not pair : we search for the maximal note value of all its sons. */
					/* Here, we create its sons when we encounter an empty square */
					
					for(Coordinates coordTmp : emptySquares){

						if(goban.moveValid(coordTmp, color)){
							
							note = Math.max(note, createNode(coordTmp, depth+1, alpha, beta));
									
							if (beta <= note)
							{
								/* We don't need to go further, so we stop here */
								goban.removePawn(coord);
								return note;							
							}
									
							alpha = Math.max(alpha, note);
						}
			
					}
					
				}		
		}
		goban.removePawn(coord);
		return note;
	}
	
	private void hitSimul(Coordinates coord, int depth){
		
		if ((depth % 2) == 0)
		{					
			goban.addPawn(coord, color);
		} else {
			goban.addPawn(coord, color.invColor());
								
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
				
		if (goban.isWinner(color)){
			note = NOTE_MAX;
		}else{
			note = note - 1000 * derniereLiberte(color);
			note = note + 100 * derniereLiberte(color.invColor());
						
			note = note + 10 * goban.getGroups(color).size();
			note = note - 10 * goban.getGroups(color.invColor()).size();
			
			note = note - nbLiberte(color.invColor());
		}
		
		return note;
	}
	
	private Integer nbLiberte(Color couleur){
		Integer note=0;
		
		for(GroupPawns g : goban.getGroups(couleur)){
			note = note - g.getFreedoms();
		}			
		return note;
	}

	
	/**
	 * This function return the number of true eyes and false eyes.
	 * The beginnig is to find an eye, wich is a structure formed by 4 points :
	 *   X
	 * X   X
	 *   X
	 * 
	 * A false eye is an eye where two "O" points, or more, are taken by the opponent :
	 * O X O
	 * X   X
	 * O X O
	 * 
	 * 
	 * TODO add a link to the definition of true and false eyes.
	 * @param plateau
	 * @return [trueEyes, falseEye] : - trueEyes : number of true eyes
	 * 								  - falseEyes : number of false eyes
	 */
	
	private List<Integer> eyeNumber(GobanStructure plateau){
		
		int trueEyes = 0;
		int falseEyes = 0;
		int form = 0;
		
		for(int i = 1; i < 10; i++)
		{
			for(int j = 1; j < 10; j++)
			{
				form = 0;
				
				
				
			}			
		}
		
		/* We create the return variable */
		List<Integer> listTmp = new LinkedList<Integer>();
		listTmp.add(0, trueEyes);
		listTmp.add(1, falseEyes);
		
		return listTmp;
	}
	
		
	/**
	 * 
	 * @param couleur
	 * @return
	 */
	private Integer derniereLiberte(Color couleur){
		
		Integer cpt=0;
		
		for(GroupPawns g : goban.getGroups(couleur)){
			if(g.getFreedoms() == 1){
				cpt++;
			}
		}	
		
		return cpt;
	}

	public Coordinates forceToPlay() {
		moveForced = true;
		return bestMove;
		
	}
	
}

