package fr.alma.ia;

import java.util.LinkedList;

import fr.alma.atarigo.Group;
import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;


public class Evaluation {
	
	static public int VERYGOOD = 750;
	static public int GOOD = 500;
	static public int PLUS = 250;
	static public int MINUS = -250;
	static public int BAD = -500;
	static public int VERYBAD = -750;

	static ValuedGoban evaluate(Goban goban, Goban parentGoban, Stone stone, Position position){
		ValuedGoban cmpt = new ValuedGoban(0,goban);
		
		System.out.println();
		cmpt.evaluation_+=groupCreation(goban,parentGoban,stone);
		cmpt.evaluation_+=groupExtension(goban,stone);
		cmpt.evaluation_+=averageGroupSize(goban,stone);
		cmpt.evaluation_+=isCaught(goban,position,stone);
		cmpt.evaluation_+=lastLiberty(goban,stone);

		return cmpt;
	}
		
	static int lastLiberty(Goban goban, Stone stone){
		int cmptBlack=0,cmptWhite=0;
		
		for(int i=0;i<goban.getLines();i++){
			for(int j=0;j<goban.getLines();j++){
				if(goban.surrounded(new Position(i,j),goban.matrice[i][j].opponent())==0){
					if(goban.matrice[i][j]==Stone.BLACK)
						cmptBlack++;
					if(goban.matrice[i][j]==Stone.WHITE)
						cmptWhite++;
				}
			}
		}
		if(stone==Stone.BLACK)
			return GOOD*cmptWhite+BAD*cmptBlack;
		else
			return BAD*cmptWhite+GOOD*cmptBlack;
	}
	
	static int isCaught(Goban goban,Position position,Stone stone){
		Group group = goban.groupsList.getGroup(position);
		int prises = goban.hasCaught(position, goban.groupsList).totalStones();
		if(prises>0){
				System.out.println("---------------------------------> prise de "+prises+" pions par le joueur "+group.stone.toString()+ " alors que "+stone+" joue");
				System.out.println(goban);
				if(group.stone==stone)
					return VERYBAD;
				else
					return VERYGOOD;
		}
		return 0;
	}
	
	static int averageGroupSize(Goban goban, Stone stone){
		int maxBlack=0, maxWhite=0;
		for(Group group : goban.groupsList.gList){
			if(group.stone==Stone.BLACK){
				if(group.linkedStones.size()>maxBlack)
					maxBlack=group.linkedStones.size();
			}
			else{
				if(group.linkedStones.size()>maxWhite)
					maxWhite=group.linkedStones.size();
			}
		}
		int cmptNowBlack=0, cmptNowWhite =0;
		for(Group group : goban.groupsList.getListe()){
			if(group.stone==Stone.BLACK)
				cmptNowBlack++;
			else
				cmptNowWhite++;
		}
		int scoreBlack = (maxBlack*1000)/cmptNowBlack;
		int scoreWhite = (maxWhite*1000)/cmptNowWhite;
		
		System.out.println("////////////////////////// score BLACK = "+scoreBlack+" score WHITE = "+scoreWhite);
		
		if(stone==Stone.BLACK)
			return scoreBlack-scoreWhite;
		else
			return scoreWhite-scoreBlack;
	}
	
	static int groupExtension(Goban goban, Stone stone){
		int maxBlack=0, maxWhite=0;
		for(Group group : goban.groupsList.gList){
			if(group.stone==Stone.BLACK){
				if(group.linkedStones.size()>maxBlack)
					maxBlack=group.linkedStones.size();
			}
			else{
				if(group.linkedStones.size()>maxWhite)
					maxWhite=group.linkedStones.size();
			}
		}
		
		System.out.println("///// plus grand groupe --> BLACK = "+maxBlack+", WHITE = "+maxWhite);
		
		if(stone==Stone.BLACK)
			return maxBlack*GOOD;
		else
			return maxWhite*BAD;
	}
	
	static int groupCreation(Goban goban, Goban parentGoban, Stone stone){
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
		System.out.println("////////// BLACK = "+cmptNowBlack+" groupes, WHITE = "+cmptNowWhite+" groupes");
		boolean black=false,white=false;
				
		if(cmptCurrentBlack<cmptNowBlack){
			black=true;
		}
		if(cmptCurrentWhite<cmptNowWhite){
			white=true;
		}
		
		if(stone==Stone.BLACK){
			if(black)
				cmpt=PLUS*cmptNowBlack;
			if(white)
				cmpt=MINUS*cmptNowWhite;
		}else{
			if(white)
				cmpt=PLUS*cmptNowWhite;
			if(black)
				cmpt=MINUS*cmptNowBlack;
		}
		
		return cmpt;
	}
}