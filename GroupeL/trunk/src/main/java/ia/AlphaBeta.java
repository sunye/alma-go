package ia;

import java.util.Calendar;
import java.util.List;

import game.Coordinates;
import game.Color;
import game.GobanStructure;
import game.GroupPawns;


public class AlphaBeta{
	
    private GobanStructure game;
    private Color color;
    
	private final static int NOTE_MIN = -100000;
	private final static int NOTE_MAX = 100000;
	
	private boolean moveForced = false;

	private Integer depthMax;
	
	private long startTime;
	
	private Integer playTimeIA;
   

	/* Constructor */
	public AlphaBeta(GobanStructure game, Color color, Integer depthMax, Integer playTimeIA) {
		super();
		this.game = game;
		this.color = color;
		this.depthMax = depthMax;
		this.playTimeIA = playTimeIA * 1000;
	}
	
	public AlphaBeta(Integer depthMax) {
		super();
		this.game = new GobanStructure();
		this.color = Color.NONE;
		this.depthMax = depthMax;
	}
	
	public AlphaBeta() {
		super();
		this.game = new GobanStructure();
		this.color = Color.NONE;
		this.depthMax = 3;
	}
	
	public void manageAlphaBeta(GobanStructure game, Color color){
		this.game = game;
		this.color = color;
	}
	
		
	/* Function of decision tree creation (with parameters) */
	public Coordinates createTree(GobanStructure game, Color color)
	{
		this.game = game;
		this.color = color;
		moveForced = false;
		
		startTime = Calendar.getInstance().getTimeInMillis();
			
		Coordinates toPlay = new Coordinates();
		int bestNote = NOTE_MIN;
		
		/* We run the recursive function on every free square of the plateau */	
		List<Coordinates> emptySquares = game.getFreeCoord();
		int note = NOTE_MIN;
		
		for(Coordinates coordTmp : emptySquares){
			
			if(game.moveValid(coordTmp, color)){
			
				note = createNode(coordTmp, 1, NOTE_MIN, NOTE_MAX);
												
				if (note>bestNote)
				{
					/* We have the best move and we store it */
					bestNote = note;
					toPlay = coordTmp;
				}
			}
		}
		
		if((toPlay.getX() == 0) && (toPlay.getY() == 0)){
			toPlay = emptySquares.get(0);
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
		
		Integer eval = evaluation();
		
		if((eval == NOTE_MAX) && (depth == 1)){
			game.removePawn(coord);
			return eval;
		}
		
		if((eval == NOTE_MIN) && (depth == 1)){
			game.removePawn(coord);
			return eval;
		}
		
		if((eval == NOTE_MIN) &&  (depth != 1)){
			eval = NOTE_MIN + 1;
		}
		
		if((eval == NOTE_MAX) &&  (depth != 1)){
			eval = NOTE_MAX - 1;
		}
			
		if(!(Calendar.getInstance().getTimeInMillis() - startTime > playTimeIA)) {
			
			if ((depth == depthMax) && (!moveForced)){
				/* We are in a leaf */
				note = eval;
				
				game.removePawn(coord);
				
				return note;
				
			} else {
	
					/* We try to trunk the tree */
					List<Coordinates> emptySquares = game.getFreeCoord();
					
					if ((depth % 2) != 0)
					{
						
						note = NOTE_MAX;
						
						/* The depth is not pair : we search for the minimum note value of all its sons. */
						/* Here, we create its sons when we encounter an empty square */
						
						for(Coordinates coordTmp : emptySquares){
						
							if(game.moveValid(coordTmp, color.invColor())){
								
								note = Math.min(note, createNode(coordTmp, depth+1, alpha, beta));
								
								if (alpha >= note)
								{
									/* We don't need to go further, so we stop here */
									game.removePawn(coord);
									return note;							
								}	
								
								beta = Math.min(beta, note);
							}
						}
		
					} else {
						
						note = NOTE_MIN;
						/* The depth is pair : we search for the maximum note value of all its sons. */
						/* Here, we create its sons when we encounter an empty square */
	
						for(Coordinates coordTmp : emptySquares){
	
							if(game.moveValid(coordTmp, color)){
								
								note = Math.max(note, createNode(coordTmp, depth+1, alpha, beta));
								
								if (beta <= note)
								{
									/* We don't need to go further, so we stop here */
									game.removePawn(coord);
									return note;							
								}
										
								alpha = Math.max(alpha, note);
							}
				
						}					
					}		
			}
			game.removePawn(coord);
			return note;
		} else {
			game.removePawn(coord);
			return eval;
		}
	}
	
	private void hitSimul(Coordinates coord, int depth){
		
		if ((depth % 2) != 0)
		{					
			game.addPawn(coord, color);
		} else {
			game.addPawn(coord, color.invColor());						
		}		
	}
	
	/**
	 * return a note of a game based on the AI's color.
	 * @return note
	 */
	private Integer evaluation()
	{
		Integer note=0;
		
		if (((game.isWinner(color)))){
			return NOTE_MAX;
		}		
		else if ((lastFreedom(color)>0)){
			return NOTE_MIN;
		}else{
			note = note + 1000 * lastFreedom(color.invColor());
			note = note + 1000 * ((game.isWinner(color)?1:0));
			note = note - 1000 * (lastFreedom(color));
			
			note = note + 200 * game.getGroups(color).size();
			note = note - 100 * game.getGroups(color.invColor()).size();
			
			note = note + 200 * nbFreedom(color);
			note = note - 100 * nbFreedom(color.invColor());
			
		}
		
		// add a little random value
		Double rand = Math.random()*10;
		note = note + rand.intValue();
		
		return note;
	}
	
	/**
	 * give the number of freedom from all the color's group 
	 * @param player color
	 * @return
	 */
	private Integer nbFreedom(Color player){
		Integer note=0;
		
		for(GroupPawns g : game.getGroups(player)){
			note = note + g.getFreedoms();
		}			
		return note;
	}
		
	/**
	 * give the number of group which have only one freedom 
	 * @param player color
	 * @return
	 */
	private Integer lastFreedom(Color player){
		
		Integer cpt=0;
		
		for(GroupPawns g : game.getGroups(player)){
			if(g.getFreedoms() == 1){
				cpt++;
			}
		}	
		
		return cpt;
	}

}


