package fr.alma.ia;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;

/**
 * AlphaBeta.java is a static class which implement the AlphaBeta algorithm.
 * Alpha-beta pruning is a search algorithm which seeks to reduce the number 
 * of nodes that are evaluated in the search tree by the minimax algorithm.
 *
 * @author      Adrien GUILLE
 * @author      Vincent FERREIRA
 */

public class AlphaBeta {
		
		static public ValuedGoban bestMove;
		static public Stone currentPlayer;
		static public int extremum;
		static public int totalNodes;
		static public int maxLevel;
		static public Goban initialGoban;
		
	/**
	 * Static method that initialize static properties
	 * @param nvmax the maximum depth of the search
	 */
		public static void init(int nvmax,Goban goban,Stone player){
			bestMove=new ValuedGoban(0);
			currentPlayer=player;
			totalNodes=0;
			maxLevel=nvmax;
			initialGoban=goban;
		}

	/**
	 * Static method which returns the best move to make
	 * @param level the current depth of the search
	 * @param edj the current state of game
	 * @param ExtremumCourant the current extremum
	 * @param pion the current color to play
	 * @return a ValuedGoban which indicates the best move
	 */
	public static ValuedGoban value(int level, Tree stateOfGame, int currentExtremum, Stone stone, AtariGo atariGo, Position position){
		if(level<maxLevel){
			if(level%2==0 || level==0)
				stateOfGame.generateChildren(atariGo,stone,currentPlayer);
			else
				stateOfGame.generateChildren(atariGo,stone.opponent(),currentPlayer);
		}
		
		if(level<maxLevel && !stateOfGame.isLeaf()){
		//appel rcursif
			if(level%2==0 || level==0){
				//recherche du max
				return max(level, stateOfGame, currentExtremum, stone, atariGo, position);
			}else{
				//recherche du min
				return min(level, stateOfGame, currentExtremum, stone, atariGo, position);
			}
		}else{
			return Evaluation.evaluate(stateOfGame.getGoban(), initialGoban, stone, position);
		}
	}

	public static ValuedGoban max(int niveau, Tree edj,int ExtremumCourant,Stone pion, AtariGo atariGo, Position pos){
		//recherche du max
		ValuedGoban max = new ValuedGoban(-100000);
		int i = 0;
		//On ignore l'extremum : 		while(max.evaluation_<ExtremumCourant && edj.getChildren().size()>i){
		while(max.evaluation_<ExtremumCourant && edj.getChildren().size()>i){
			totalNodes++;
			Position position = edj.getGoban().getDifference(edj.getChildren().get(i).getGoban());
			ValuedGoban V = value(niveau+1,edj.getChildren().get(i),max.evaluation_,pion,atariGo,position);
			if(V.evaluation_>max.evaluation_){
				max.clone(new ValuedGoban(V.evaluation_,edj.getChildren().get(i).getMove()));
			}
			i++;
		}
		return max;		
	}
	
	public static ValuedGoban min(int niveau, Tree edj,int ExtremumCourant,Stone pion, AtariGo atariGo, Position pos){
		//recherche du min
		ValuedGoban min = new ValuedGoban(100000);
		int i = 0;
		while(min.evaluation_>ExtremumCourant && edj.getChildren().size()>i){
			totalNodes++;
			Position position = edj.getGoban().getDifference(edj.getChildren().get(i).getGoban());
			ValuedGoban V = value(niveau+1,edj.getChildren().get(i),min.evaluation_,pion,atariGo,position);
			if(V.evaluation_<min.evaluation_){
				min.clone(new ValuedGoban(V.evaluation_,edj.getChildren().get(i).getMove()));
			}
			i++;
		}
		return min;		
	}
}

