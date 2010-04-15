/*   This program is free software: you can redistribute it and/or modify
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

package fr.alma.ia;

import java.util.LinkedList;

import fr.alma.atarigo.Group;
import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;

/**
 * Evaluation.java is a static class which implements our heuristic.
 * It is composed of several component function 
 * and a central function that uses them to associate a score with a goban.
 *
 * @author      Adrien GUILLE
 * @author      Vincent FERREIRA
 * 
 * @version 1.0
 * 
 * revision $Revision$
 * 
 */

public class Evaluation {
	
	static public int VERYGOOD = 750;
	static public int GOOD = 500;
	static public int PLUS = 250;
	static public int MINUS = -250;
	static public int BAD = -500;
	static public int VERYBAD = -750;
	
	static public boolean TRACE = false;

	/**
	 * Central function
	 * @param goban
	 * @param parentGoban
	 * @param stone
	 * @param position
	 * @return a ValuedGoban
	 */
	static public ValuedGoban evaluate(Goban goban, Goban parentGoban, Stone stone, Position position){
		ValuedGoban cmpt = new ValuedGoban(0,goban);
		if(TRACE)
			System.out.println(stone+" joue");

		int hasCaught=hasCaught(goban,position,stone);
		if(hasCaught==0){
			int underNGroups = underNGroups(goban,stone,6);
			if(underNGroups==0){
				cmpt.evaluation_=PLUS*commonBorder(goban,position,stone);
			}else{
				cmpt.evaluation_=GOOD*underNGroups;
			}
		}else{
			cmpt.evaluation_=VERYGOOD*hasCaught;
		}
				
		return cmpt;
	}

	/**
	 * Checks if there's a common border with the opponent
	 * @param goban
	 * @param position
	 * @param stone
	 * @return -1, 0 or +1
	 */
	static public int commonBorder(Goban goban, Position position, Stone stone){
		int cmpt = goban.contact(position,stone.opponent());
		if(cmpt>0 && cmpt<4){
			if(goban.readCell(position)==stone)
				return 1;
			else 
				return -1;
		}else{
			if(goban.readCell(position)==stone)
				return -1;
			else 
				return 1;
		}
	}
	
	/**
	 * Checks if some stones are caught
	 * @param goban
	 * @param position
	 * @param stone
	 * @return -1, 0 or +1
	 */
	static public int hasCaught(Goban goban,Position position,Stone stone){
		Group group = goban.groupsList.getGroup(position);
		int prises = goban.hasCaught(position, goban.groupsList).totalStones();
		if(prises>0){
			if(TRACE)
				System.out.println("---------------------------------> prise de "+prises+" pions par le joueur "+group.stone.toString()+ " alors que "+stone+" joue");
			if(goban.readCell(position)==stone)
				return 1;
			else
				return -1;
		}
		return 0;
	}
	
	/**
	 * Checks the amount of groups per player
	 * @param goban
	 * @param stone
	 * @param n the maximum amount of groups allowed
	 * @return -1, 0 or +1
	 */
	static public int underNGroups(Goban goban, Stone stone, int n){
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
				if(cmptWhite>n)
					return 1;
				else
					return 0;
			}else
				return -1;
		}else{
			if(cmptWhite<n){
				if(cmptBlack>n)
					return 1;
				else
					return 0;
			}else
				return -1;
		}
	}
}