package fr.alma.ia;

import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;

/**
 * MinMax.java is a static class which implement the MinMax algorithm.
 * It's a recursive algorithm for choosing the next move in an n-player game, 
 * usually a two-player game. A value is associated with each position or state of the game. 
 * This value is computed by means of a position evaluation function and it indicates how good 
 * it would be for a player to reach that position. The player then makes the move that maximizes 
 * the minimum value of the position resulting from the opponent's possible following moves. 
 *
 * @author      Adrien GUILLE
 * @author      Vincent FERREIRA
 */

public class MinMax {
		
		static public ValuedGoban bestMove;
		static public int extremum;
		static public int totalNodes;
		static public int maxLevel;
		
	
	/**
	 * Static method that initialize static properties
	 * @param nvmax the maximum depth of the search
	 */	
		public static void init(int nvmax){
			bestMove=new ValuedGoban(0);
			extremum=0;
			totalNodes=0;
			maxLevel=nvmax;
		}


	/**
	 * Static method which returns the best move to make
	 * @param level the current depth of the search
	 * @param edj the current state of game
	 * @param pion the current color to play
	 * @return a ValuedGoban which indicates the best move
	 */	
	public static ValuedGoban value(int level, Tree stateOfGame, Stone stone){
		if(level<maxLevel)
			stateOfGame.generateChildren(stone);
		
		if(level<maxLevel && !stateOfGame.isLeaf()){
		//appel récursif
			if(level%2==0 || level==0){
				//recherche du max
				return max(level, stateOfGame, stone);
			}else{
				//recherche du min
				return min(level, stateOfGame, stone);
			}
		}else{
			return Evaluation.evaluate(stateOfGame.getGoban(),stone);
		}
	}

	public static ValuedGoban max(int niveau, Tree edj,Stone pion){
		//recherche du max
		ValuedGoban max = new ValuedGoban(-100000);
		int i = 0;
		while(edj.getChildren().size()>i){
			totalNodes++;
			ValuedGoban V = value(niveau+1,edj.getChildren().get(i),pion.opponent());
			if(V.evaluation_>max.evaluation_){
				max.clone(V);
			}
			i++;
		}
		return max;		
	}
	
	public static ValuedGoban min(int niveau, Tree edj,Stone pion){
		//recherche du min
		ValuedGoban min = new ValuedGoban(100000);
		int i = 0;
		while(edj.getChildren().size()>i){
			totalNodes++;
			ValuedGoban V = value(niveau+1,edj.getChildren().get(i),pion.opponent());
			if(V.evaluation_<min.evaluation_){
				min.clone(V);
			}
			i++;
		}
		return min;		
	}
}

