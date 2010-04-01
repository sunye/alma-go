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
		cmpt.evaluation_+=isCaught(goban,position,stone);
		//System.out.println("score="+cmpt.evaluation_);
		return cmpt;
	}
		
	static int isCaught(Goban goban,Position position,Stone stone){
		Group group = goban.groupsList.getGroup(position);
		int prises = goban.hasCaught(position, goban.groupsList).totalStones();
		if(prises>0){
			if(group.stone==stone){
				System.out.println("-----> prise de "+prises+" pions par le joueur en cours");
				return prises*VERYGOOD;
			}else{
				System.out.println("-----> prise de "+prises+" pions par le joueur ennemi");
				return prises*VERYBAD;
			}
		}
		return 0;
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
				cmpt=GOOD*cmptNowBlack;
			if(white)
				cmpt=BAD*cmptNowWhite;
		}else{
			if(white)
				cmpt=GOOD*cmptNowWhite;
			if(black)
				cmpt=BAD*cmptNowBlack;
		}
		
		return cmpt;
	}
}