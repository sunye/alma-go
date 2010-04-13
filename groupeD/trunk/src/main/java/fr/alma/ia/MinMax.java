package fr.alma.ia;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.GroupsList;
import fr.alma.atarigo.Position;
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
 * 
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

public class MinMax {
	static public ValuedGoban bestMove;
	static public Stone currentPlayer;
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
	 * @param pion the current color to play
	 * @return a ValuedGoban which indicates the best move
	 */	
	public static ValuedGoban value(int level, Tree stateOfGame, Stone stone, AtariGo atariGo, Position position){
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
				return max(level, stateOfGame, stone, atariGo, position);
			}else{
				//recherche du min
				return min(level, stateOfGame, stone, atariGo, position);
			}
		}else{
			return Evaluation.evaluate(stateOfGame.getGoban(), initialGoban, stone, position);
		}
	}

	public static ValuedGoban max(int niveau, Tree edj,Stone pion, AtariGo atariGo, Position pos){
		//recherche du max
		ValuedGoban max = new ValuedGoban(-100000);
		int i = 0;
		while(edj.getChildren().size()>i){
			totalNodes++;
			Position position = edj.getGoban().getDifference(edj.getChildren().get(i).getGoban());
			ValuedGoban V = value(niveau+1,edj.getChildren().get(i),pion.opponent(),atariGo,position);
			if(V.evaluation_>max.evaluation_){
				max.clone(new ValuedGoban(V.evaluation_,edj.getChildren().get(i).getMove()));
			}
			i++;
		}
		return max;		
	}
	
	public static ValuedGoban min(int niveau, Tree edj,Stone pion, AtariGo atariGo, Position pos){
		//recherche du min
		ValuedGoban min = new ValuedGoban(100000);
		int i = 0;
		while(edj.getChildren().size()>i){
			totalNodes++;
			Position position = edj.getGoban().getDifference(edj.getChildren().get(i).getGoban());
			ValuedGoban V = value(niveau+1,edj.getChildren().get(i),pion.opponent(),atariGo,position);
			if(V.evaluation_<min.evaluation_){
				min.clone(new ValuedGoban(V.evaluation_,edj.getChildren().get(i).getMove()));
			}
			i++;
		}
		return min;		
	}
}

