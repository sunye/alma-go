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

		if(isCaught(goban,position,stone))
			cmpt.evaluation_=VERYGOOD;
		else{
			if(underNGroups(goban,stone,6)){
				if(commonBorder(goban,position,stone))
					cmpt.evaluation_=GOOD;
				else
					cmpt.evaluation_=PLUS;
			}else{
				cmpt.evaluation_=MINUS;
			}
		}
		
				
		//cmpt.evaluation_+=lastLiberty(goban,stone);

		return cmpt;
	}
	
	/*static boolean constantContact(Goban goban, Goban gobanParent, Stone stone){
		int cmptBefore=0;
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++){
				if(goban.matrice[i][j]==Stone.BLACK)
					goban.contact(new Position(i,j),stone.opponent());
			}
		return TRACE;
		
	}*/
		
	static boolean commonBorder(Goban goban, Position position, Stone stone){
		int cmpt = goban.contact(position,stone.opponent());
		if(goban.readCell(position)==stone){
			return cmpt>0 && cmpt<4;
		}else{
			return cmpt==0;
		}
	}
	
	static boolean isCaught(Goban goban,Position position,Stone stone){
		Group group = goban.groupsList.getGroup(position);
		int prises = goban.hasCaught(position, goban.groupsList).totalStones();
		if(prises>0){
			if(TRACE)
				System.out.println("---------------------------------> prise de "+prises+" pions par le joueur "+group.stone.toString()+ " alors que "+stone+" joue");
			return true;
		}
		return false;
	}
	
	public static boolean underNGroups(Goban goban, Stone stone, int n){
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
				return true;
			}else{
				return false;
			}
		}else{
			if(cmptWhite<n){
				return true;
			}else{
				return false;
			}		
		}
	}
}