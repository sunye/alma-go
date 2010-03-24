package fr.alma.atarigo;

import java.io.*;
import java.util.*;

/**
 * Classe implémentant le jeu AtariGo. Contient les principaux mécanismes de jeu.
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 */
public class AtariGo {

 // +----------------------------------------------------------------------+
 // | Type      : Coup.                                                    |
 // | Utilite   : enumeration representant les differents types de coups.  |
 // | Remarques : aucune.                                                  |
 // +----------------------------------------------------------------------+
/**
 *  Enumération représentant les différents types de coups.
 */
 public enum Move { INVALID, WIN, NEUTRAL };
 
 public int totalMoves;
 public boolean end;
 /**
  *  Plateau de jeu
  */
 public Goban goban;
 /**
  * Liste des groupes de pierres de go
  */
 private GroupsList groupsList;
 public int caughtBlack;
 public int caughtWhite;
 public int captureObjective;
 
 public Player currentPlayer;
 
 public Player player1;
 public Player player2;

 /**
  *  Joue un nouveau coup et retourne le résultat.
  * @param player le joueur jouant le coup.
  * @param position la position du coup
  * @return le type de coup résultant  INVALIDE, GAGNANT ou NEUTRE .
  */
 public Move playMove(Stone player, Position position) {
	// ces conditions n'ont pas besoin de changer l'état du plateau pour savoir si le placement est possible
	if (! goban.isValid(position) || goban.readCell(position) != Stone.EMPTY ) {
		System.out.println("bad move");
	    return Move.INVALID;
	}
	//ecriture sur le plateau nécessaire pour tester le suicide formé par un groupe
	goban.writeCell(position, player);
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
 }
 
 	
/**
 * Constructeur logique basique
 * @param lines nombre de ligne du goban
 * @param columns nombre de colonnes du goban
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
  * Constructeur logique
  * @param lines nombre de lignes du goban
  * @param columns nombre de colonnes du goban
  * @param player1 type de joueur (humain ou IA)
  * @param player2 type de joueur (humain ou IA)
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
  * retourne si la partie est fini ou non
  * @return le booleen fini
  */
 public boolean isOver(){
	 return end;
 }
 
 /*
  * désactive la partie et donc la fini
  */
 public void shutDown(){
	 end = true;
 }
 

 /*
  * accesseur en lecture
  */
 public int getLines() {
	return goban.getLines();
 }

/*
 * accesseur en lecture
 */
 public int getColumns() {
	return goban.getColumns();
 }

/**
 * prépare le jeu pour une nouvelle partie.
 */
 public void newGame() {
	goban.newGame();
	end = false;
	caughtBlack=0;
	caughtWhite=0;
 }

/**
 * @deprecated
 * lance la partie en mode console
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
