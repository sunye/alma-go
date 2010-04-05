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
	
	static public boolean TRACE = false;

	static ValuedGoban evaluate(Goban goban, Goban parentGoban, Stone stone, Position position){
		ValuedGoban cmpt = new ValuedGoban(0,goban);
		if(TRACE)
			System.out.println(stone+" joue");
		//cmpt.evaluation_+=groupCreation(goban,parentGoban,stone);
		//cmpt.evaluation_+=groupExtension(goban,stone);
		//cmpt.evaluation_+=averageGroupSize(goban,stone);
		cmpt.evaluation_+=isCaught(goban,position,stone);
		cmpt.evaluation_+=underNGroups(goban,stone,6);
		//cmpt.evaluation_+=lastLiberty(goban,stone);

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
		if(stone==Stone.BLACK){
			if(cmptBlack<2){
				return GOOD;
			}else{
				return VERYBAD;
			}
		}else{
			if(cmptWhite<2){
				return GOOD;
			}else{
				return VERYBAD;
			}		
		}
	}
	
	static int isCaught(Goban goban,Position position,Stone stone){
		Group group = goban.groupsList.getGroup(position);
		int prises = goban.hasCaught(position, goban.groupsList).totalStones();
		if(prises>0){
			//if(TRACE)
				System.out.println("---------------------------------> prise de "+prises+" pions par le joueur "+group.stone.toString()+ " alors que "+stone+" joue");
			return prises*VERYGOOD;
		}
		return MINUS;
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
		
		if(TRACE)
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
		if(TRACE)
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
		if(TRACE)
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
	
	public static int underNGroups(Goban goban, Stone stone, int n){
		int cmptBlack=0,cmptWhite=0;
		
		for(Group group : goban.groupsList.gList){
			if(group.stone==Stone.BLACK)
				cmptBlack++;
			else
				cmptWhite++;
		}
		
		if(TRACE)
			System.out.println("joueur en cours = "+stone+" BLACK "+cmptBlack+" WHITE"+cmptWhite);
		
		if(stone==Stone.BLACK){
			if(cmptBlack<n){
				return GOOD;
			}else{
				return MINUS;
			}
		}else{
			if(cmptWhite<n){
				return GOOD;
			}else{
				return MINUS;
			}		
		}
	}
}