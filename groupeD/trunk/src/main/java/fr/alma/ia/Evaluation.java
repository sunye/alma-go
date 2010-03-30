package fr.alma.ia;

import fr.alma.atarigo.Group;
import fr.alma.atarigo.GroupsList;
import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;

public class Evaluation {

	static ValuedGoban evaluate(Goban goban, GroupsList currentGroups, Stone stone, GroupsList groups, Position position){
		ValuedGoban cmpt = new ValuedGoban(0,goban);
		cmpt.evaluation_+=groupExtension(goban,currentGroups,stone,groups);
		return cmpt;
	}
		
	static int groupExtension(Goban goban, GroupsList currentGroups, Stone stone, GroupsList groups){
		int cmpt = 0;
		
		int cmptCurrentBlack=0, cmptCurrentWhite=0, cmptNowBlack=0, cmptNowWhite =0;
		for(Group group : currentGroups.getListe())
			if(group.stone==Stone.BLACK)
				cmptCurrentBlack++;
			else
				cmptCurrentWhite++;
		for(Group group : groups.getListe())
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