package fr.alma.atarigo;

import java.io.*;
import java.util.*;

/**
 * AtariGo.java contains the game rules and mechanisms of Atarigo.
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 */
public class AtariGo {

/**
 *  Enumeration of the different types of moves.
 */
 public enum Move { INVALID, WIN, NEUTRAL };
 
 /**
  * Total number of moves played during a game.
  */
 public int totalMoves;
 public boolean end;
 /**
  *  Game board
  */
 public Goban goban;
 /**
  * List of stone groups 
  */
 public GroupsList groupsList;
 public int caughtBlack;
 public int caughtWhite;
 public int captureObjective;
 
 public Player currentPlayer;
 
 public Player player1;
 public Player player2;

 /**
  * Play a move and return the result. If necessary, calculates new groups and caught stones.
  * @param player The player playing the move.
  * @param position The position of the move.
  * @return The type of the resulting move INVALID, WIN or NEUTRAL .
  */
 public Move playMove(Stone player, Position position) {
	// ces conditions n'ont pas besoin de changer l'état du plateau pour savoir si le placement est possible
	if (! goban.isValid(position) || goban.readCell(position) != Stone.EMPTY ) {
		System.out.println("bad move");
	    return Move.INVALID;
	}
	//ecriture sur le plateau nécessaire pour tester le suicide formé par un groupe
	return goban.writeCell(this,position, player,true);
	
	/*
	//formation d'une nouvelle liste comprenant un nouveau groupe formé
	GroupsList newGL = groupsList.updateGroups(goban,position,player);
	//test de la prise sur le nouveau groupe
	GroupsList caughtList = goban.hasCaught(position,newGL);
	if(goban.isCaught(newGL.getGroup(position))){
		if (!caughtList.isEmpty()) {
			totalMoves++;
			if(player==Stone.WHITE){
				caughtBlack += caughtList.totalStones();
				if(caughtBlack>=captureObjective){
					return Move.WIN;
				}
			}
			else{
				caughtWhite += caughtList.totalStones();
				if(caughtWhite>=captureObjective){
					return Move.WIN;
				}
			}
			System.out.println(player.toString()+"has won");
		}
		System.out.println("is cuaght !!");
		System.out.println("printing gList");
		groupsList.print();
		goban.emptyCell(position);
		return Move.INVALID;
	}
	//coup valide, on garde le plateau et on recopie la nouvelle liste de groupes sur l'ancienne
	totalMoves++;
	groupsList = newGL;
	caughtList = goban.hasCaught(position,groupsList);
	if (!caughtList.isEmpty()) {
		if(player==Stone.WHITE){
			caughtBlack += caughtList.totalStones();
			if(caughtBlack>=captureObjective){
				return Move.WIN;
			}
		}
		else{
			caughtWhite += caughtList.totalStones();
			if(caughtWhite>=captureObjective){
				return Move.WIN;
			}
		}
		System.out.println(player.toString()+"has won");
		//return Coup.GAGNANT;
	}
	return Move.NEUTRAL;
	*/
 }
 
 	
/**
 * Logic constructor
 * @param lines number of lines of the Goban
 * @param columns number of columns of the Goban
 */
 public AtariGo(int lines, int columns) {
	goban = new Goban(lines, columns);
	totalMoves=0;
	groupsList = new GroupsList();
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
 public AtariGo(int lines, int columns,Player player1,Player player2) {
		goban = new Goban(lines, columns);
		end = false;
		totalMoves=0;
		groupsList = new GroupsList();
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

/**
 * @deprecated
 * launch the game in console mode
 */
 public void jouer(Stone joueur, InputStream entree, PrintStream sortie) {
	int nombreCoups = getLines() * getColumns();
	Scanner scanner = new Scanner(entree);
	while (nombreCoups != 0) {
	    sortie.println(goban.toString());
	    sortie.print("=> Joueur " + joueur.toString() + " : ");
	    int ligne;
	    int colonne;
	    try {
		ligne = scanner.nextInt();
		colonne = scanner.nextInt();
	    }
	    catch (InputMismatchException erreur) {
		sortie.println("=> Erreur : position incorrecte");
		continue;
	    }
	    Position position = new Position(ligne, colonne);
	    // joue coup en fonction du joueur...
	    switch (playMove(joueur, position)) {
	    case WIN:
	    end = true;
		sortie.println(goban.toString());
		sortie.println("=> Vous avez gagne"); 
		scanner.close();
		return;
	    case NEUTRAL:
		nombreCoups --;
		joueur = joueur == Stone.WHITE ? Stone.BLACK : Stone.WHITE;
		break;
	    default:
		sortie.println("=> Erreur : position invalide");
		break;
	    }
	}
	sortie.println(goban.toString());
	sortie.println("=> Match nul");
	scanner.close();
 }

}
