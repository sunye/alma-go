package fr.alma.ia;

import fr.alma.atarigo.Group;
import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;


public class Evaluation {
	
	static public int VERYGOOD = 1000;
	static public int GOOD = 500;
	static public int BAD = -500;
	static public int VERYBAD = -1000;

	static ValuedGoban evaluate(Goban goban, Goban parentGoban, Stone stone, Position position){
		ValuedGoban cmpt = new ValuedGoban(0,goban);
		cmpt.evaluation_+=groupExtension(goban,parentGoban,stone);
		return cmpt;
	}
		
	static int groupExtension(Goban goban, Goban parentGoban, Stone stone){
		int cmpt = 0;
		
		int cmptCurrentBlack=0, cmptCurrentWhite=0, cmptNowBlack=0, cmptNowWhite =0;
		for(Group group : parentGoban.groupsList.getListe())
			if(group.stone==Stone.BLACK)
				cmptCurrentBlack++;
			else
				cmptCurrentWhite++;
		for(Group group : goban.groupsList.getListe())
			if(group.stone==Stone.BLACK)
				cmptNowBlack++;
			else
				cmptNowWhite++;
		
		boolean black=false,white=false;
				
		if(cmptCurrentBlack<cmptNowBlack){
			black=true;
		}
		if(cmptCurrentWhite<cmptNowWhite){
			white=true;
		}
		
		if(stone==Stone.BLACK){
			if(black)
				cmpt=10000;
			if(white)
				cmpt=-10000;
		}else{
			if(white)
				cmpt=10000;
			if(black)
				cmpt=-10000;
		}
		
		return cmpt;
	}
}