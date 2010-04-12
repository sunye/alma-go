package ia;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import game.Coordinates;
import game.Color;
import game.GobanStructure;
import game.GroupPawns;


public class AiThread{
	
    private GobanStructure plateau;
    private Color color;
    
	private final static int NOTE_MIN = -100000;
	private final static int NOTE_MAX = 100000;
	
	private boolean moveForced = false;

	private Integer profondeur;
	
	private long dateDebut;
	
	private Integer playTimeIA;
   

	/* Constructor */
	public AiThread(GobanStructure plateau, Color color, Integer prof, Integer playTimeIA) {
		super();
		this.plateau = plateau;
		this.color = color;
		this.profondeur = prof;
		this.playTimeIA = playTimeIA * 1000;
	}
	
	public AiThread(Integer profondeur) {
		super();
		this.plateau = new GobanStructure();
		this.color = Color.NONE;
		this.profondeur = profondeur;
	}
	
	public AiThread() {
		super();
		this.plateau = new GobanStructure();
		this.color = Color.NONE;
		this.profondeur = 3;
	}
	
	public void majAiThread(GobanStructure plateau, Color color){
		this.plateau = plateau;
		this.color = color;
	}
	
	/* Function of decision tree creation (without parameters) 
	public Coordinates createTree()
	{
		Coordinates toPlay = new Coordinates();
		int bestNote = NOTE_MIN-1;
		moveForced = false;
		

		List<Coordinates> emptySquares = plateau.getFreeCoord();
		int note = NOTE_MIN;
		
		for(Coordinates coordTmp : emptySquares){
			if(plateau.moveValid(coordTmp, color)){
			
				note = createNode(coordTmp, 1, NOTE_MIN, NOTE_MAX);
				
				if (note>bestNote)
				{

					bestNote = note;
					toPlay = coordTmp;
					bestMove = toPlay;
				}
				
			}
		}
		return toPlay; 
	}*/
	
	/* Function of decision tree creation (with parameters) */
	public Coordinates createTree(GobanStructure plateau, Color color)
	{
		this.plateau = plateau;
		this.color = color;
		moveForced = false;
		
		dateDebut = Calendar.getInstance().getTimeInMillis();
			
		Coordinates toPlay = new Coordinates();
		int bestNote = NOTE_MIN;
		
		/* We run the recursive function on every free square of the plateau */	
		List<Coordinates> emptySquares = plateau.getFreeCoord();
		int note = NOTE_MIN;
		
		for(Coordinates coordTmp : emptySquares){
			
			if(plateau.moveValid(coordTmp, color)){
			
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
		
		Integer eval = evaluation(depth);
		
		if((eval == NOTE_MAX) && (depth == 1)){
			plateau.removePawn(coord);
			return eval;
		}
		
		if((eval == NOTE_MIN) && (depth == 1)){
			plateau.removePawn(coord);
			return eval;
		}
		
		if((eval == NOTE_MIN) &&  (depth != 1)){
			eval = NOTE_MIN + 1;
		}
		
		if((eval == NOTE_MAX) &&  (depth != 1)){
			eval = NOTE_MAX - 1;
		}
			
		if(!(Calendar.getInstance().getTimeInMillis() - dateDebut > playTimeIA)) {
			
			if ((depth == profondeur) && (!moveForced)){
				/* We are in a leaf */
				note = eval;
				
				plateau.removePawn(coord);
				
				return note;
				
			} else {
	
					/* We try to trunk the tree */
					List<Coordinates> emptySquares = plateau.getFreeCoord();
					
					if ((depth % 2) != 0)
					{
						
						note = NOTE_MAX;
						
						/* The depth is not pair : we search for the minimum note value of all its sons. */
						/* Here, we create its sons when we encounter an empty square */
						
						for(Coordinates coordTmp : emptySquares){
						
							if(plateau.moveValid(coordTmp, color.invColor())){
								
								note = Math.min(note, createNode(coordTmp, depth+1, alpha, beta));
								
								if (alpha >= note)
								{
									/* We don't need to go further, so we stop here */
									plateau.removePawn(coord);
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
	
							if(plateau.moveValid(coordTmp, color)){
								
								note = Math.max(note, createNode(coordTmp, depth+1, alpha, beta));
								
								if (beta <= note)
								{
									/* We don't need to go further, so we stop here */
									plateau.removePawn(coord);
									return note;							
								}
										
								alpha = Math.max(alpha, note);
							}
				
						}					
					}		
			}
			plateau.removePawn(coord);
			return note;
		} else {
			plateau.removePawn(coord);
			return eval;
		}
	}
	
	private void hitSimul(Coordinates coord, int depth){
		
		if ((depth % 2) != 0)
		{					
			plateau.addPawn(coord, color);
		} else {
			plateau.addPawn(coord, color.invColor());						
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
		
		if (((plateau.isWinner(color)))){
			return NOTE_MAX;
		}		
		else if ((derniereLiberte(color)>0)){
			return NOTE_MIN;
		}else{
			note = note + 1000 * derniereLiberte(color.invColor());
			note = note + 1000 * ((plateau.isWinner(color)?1:0));
			note = note - 1000 * (derniereLiberte(color));
			
			note = note + 200 * tailleGroupe(color);
			note = note - 100 * tailleGroupe(color.invColor());
			
			note = note + 200 * plateau.getGroups(color).size();
			note = note - 100 * plateau.getGroups(color.invColor()).size();
			
			note = note + 200 * nbLiberte(color);
			note = note - 100 * nbLiberte(color.invColor());
			
		}
		
		// add a little random value
		Double rand = Math.random()*10;
		note = note + rand.intValue();
		
		return note;
	}
	
	private Integer nbCoup() {
		return (plateau.getSize() * plateau.getSize() - plateau.getFreeCoord().size())/2;
	}

	private boolean pieceHorsCentre(Color couleur) {
		for(int y =1 ; y<=3 ; y++){
			for(int x =1 ; x<=3 ; x++){
				if(plateau.getGoban()[x][y].getColor()==couleur){
					return true;
				}
			}
		}
		for(int y = plateau.getSize() ; y<= plateau.getSize()-3 ; y--){
			for(int x = plateau.getSize() ; x<= plateau.getSize()-3 ; x--){
				if(plateau.getGoban()[x][y].getColor()==couleur){
					return true;
				}
			}
		}
		
		return false;
	}
	
	private Integer nbLiberte(Color couleur){
		Integer note=0;
		
		for(GroupPawns g : plateau.getGroups(couleur)){
			note = note + g.getFreedoms();
		}			
		return note;
	}

	private Integer tailleGroupe(Color couleur) {
		
		Integer note = 0;
		
		for(GroupPawns g : plateau.getGroups(couleur)){
			note = note + g.getPawns().size();
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
		
		for(GroupPawns g : plateau.getGroups(couleur)){
			if(g.getFreedoms() == 1){
				cpt++;
			}
		}	
		
		return cpt;
	}

}


