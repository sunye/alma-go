package fr.alma.ia;

import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;

public class MinMax {
		
		static public ValuedGoban bestMove;
		static public int extremum;
		static public int totalNodes;
		static public int maxLevel;
		
		public static void init(int nvmax){
			bestMove=new ValuedGoban(0);
			extremum=0;
			totalNodes=0;
			maxLevel=nvmax;
		}


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

