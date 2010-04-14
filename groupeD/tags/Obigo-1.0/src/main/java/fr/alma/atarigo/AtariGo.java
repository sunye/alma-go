package fr.alma.atarigo;

import java.io.*;
import java.util.*;

/**
 * AtariGo.java contains the game rules and mechanisms of Atarigo.
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 * @version 1.0
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
public class AtariGo {

	/**
	 *  Enumeration of the different types of moves.
	 *  <li>
	 *  INVALID for an invalid move
	 *  <li>
	 *  WIN for a winning move
	 *  <li>
	 *  NEUTRAL for a neutral move
	 */
 public enum Move { INVALID, WIN, NEUTRAL };
 
 /** Total number of moves played during a game. */
 public int totalMoves;
 /** Tell if the game is over */
 public boolean end;
 /** Game board */
 public Goban goban;
 /** Amount of captured black stones*/
 public int caughtBlack;
 /** Amount of captured white stones*/
 public int caughtWhite;
 /** Objective of captured stones to win the game */
 public int captureObjective;
 /** currentPlayer is player1 or player2. Depends on who got the turn. */
 public AbstractPlayer currentPlayer;
 /** the player1 (black stones) */
 public AbstractPlayer player1;
 /** the player2 (white stones) */
 public AbstractPlayer player2;

 /**
  * Play a move and return the result. If necessary, calculates new groups and caught stones.
  * @param player The player playing the move.
  * @param position The position of the move.
  * @return The type of the resulting move INVALID, WIN or NEUTRAL .
  */
 public Move playMove(Stone player, Position position) {
	// these conditions don't need to change the goban stateto know if the move is possible
	if (! goban.isValid(position) || goban.readCell(position) != Stone.EMPTY ) {
	    return Move.INVALID;
	}
	//write on the goban necessary to test the suicide made by a group
	return goban.writeCell(this,position, player,true);
}
 
/**
* Tell if the current player can make a move. Usefull to know if the game is over or not.
* @param player The player whot got the turn.
*/
public boolean canPlayMove(Stone player){
	AtariGo atariGoTest;
	boolean peutJouer = false;
	boolean fini = false;
	int i = 0;
	int j = 0;
	Position postest;
	while(!fini){
		postest = new Position(i,j);
		atariGoTest = new AtariGo(this);		
		if(atariGoTest.playMove(player,postest) != Move.INVALID){
			return true;
		}
		if(i==8){
			if(j==8){
				fini=true;
			}else{
				i=0;
				j++;
			}
		}
		else{
			i++;
		}
		
	}
	return false;
}
 
 
 
 	
/**
 * Logic constructor
 * @param lines number of lines of the Goban
 * @param columns number of columns of the Goban
 */
 public AtariGo(int lines, int columns) {
	goban = new Goban(lines, columns);
	totalMoves=0;
	end = false;
	currentPlayer=player1;
	caughtBlack=0;
	caughtWhite=0;
	captureObjective = 1;
 }
 /**
  * Logic constructor
  * @param lines number of lines of the Goban
  * @param columns number of columns of the Goban
  * @param player1 type of the player (human or AI)
  * @param player2 type of the player (humain or AI)
  */
 public AtariGo(int lines, int columns,AbstractPlayer player1,AbstractPlayer player2) {
		goban = new Goban(lines, columns);
		end = false;
		totalMoves=0;
		currentPlayer=player1;
		this.player1=player1;
		player1.color=Stone.BLACK;
		this.player2=player2;
		player2.color=Stone.WHITE;
		caughtBlack=0;
		caughtWhite=0;
		captureObjective = 1;
	 }
 /**
  * Copy constructor
  * @param atarigo Atarigo instance we want to copy.
  */
 public AtariGo(AtariGo atarigo){
	goban = new Goban(atarigo.goban);
	currentPlayer = atarigo.currentPlayer;
	player1=atarigo.player1;
	player1.color=Stone.BLACK;
	player2=atarigo.player2;
	player2.color=Stone.WHITE;
	caughtBlack=atarigo.caughtBlack;
	caughtWhite=atarigo.caughtWhite;
	captureObjective = atarigo.captureObjective;
 }

 /**
  * return if the game is over or not
  * @return le booleen fini
  */
 public boolean isOver(){
	 return end;
 }
 
 /**
  * desactivate the game
  */
 public void shutDown(){
	 end = true;
 }
 
 /**
  * accessor for lines
  */
 public int getLines() {
	return goban.getLines();
 }

/**
 * accessor for columns
 */
 public int getColumns() {
	return goban.getColumns();
 }

/**
 * prepare the game for a new game
 */
 public void newGame() {
	goban.newGame();
	end = false;
	caughtBlack=0;
	caughtWhite=0;
 }

}
