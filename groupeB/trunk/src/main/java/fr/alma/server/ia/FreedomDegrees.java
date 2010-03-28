package fr.alma.server.ia;

import java.util.ArrayList;
import java.util.List;

import fr.alma.client.action.Context;
import fr.alma.server.core.Emplacement;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;

/**
 * 
 * @author Romain Gournay & Bruno Belin
 * list of functions about freedom degrees
 *
 */
public class FreedomDegrees {

	public static int getMaxDegreeFree(Context context) {
		return ((context.getSizeGoban() * context.getSizeGoban()) / 2) + 2;
	}

	/**
	 * work with countCaseFreeDegrees
	 * @param stateGame
	 * @param player
	 * @param e
	 * @param locationsAlreadyVisited
	 * @return number degrees of freedom of the player stone location e and its group
	 */
	public static int countFreeDegrees(IStateGame stateGame, boolean player, IEmplacement e, List<IEmplacement> locationsAlreadyVisited) {
		int nbFreeDegrees = 0;	
		locationsAlreadyVisited.add(e);

		//case Top
		int rowTop = (e.getRow() - 1);
		nbFreeDegrees += countCaseFreeDegrees(new Emplacement(e.getCol(), rowTop), stateGame, player, locationsAlreadyVisited);

		//case Bottom
		int rowBottom = (e.getRow() + 1);
		nbFreeDegrees += countCaseFreeDegrees(new Emplacement(e.getCol(), rowBottom), stateGame, player, locationsAlreadyVisited);

		//case Left
		int colLeft = (e.getCol() -1);
		nbFreeDegrees += countCaseFreeDegrees(new Emplacement(colLeft, e.getRow()), stateGame, player, locationsAlreadyVisited);

		//case Right
		int colRight = (e.getCol() +1);
		nbFreeDegrees += countCaseFreeDegrees(new Emplacement(colRight, e.getRow()), stateGame, player, locationsAlreadyVisited);
		
		return nbFreeDegrees;
	}

	/**
	 * work with countFreeDegrees
	 * @param e
	 * @param stateGame
	 * @param player
	 * @param emplacements
	 * @return the number of freedom degrees for a stone location
	 */

	private static int countCaseFreeDegrees(IEmplacement e, IStateGame stateGame, boolean player, List<IEmplacement> emplacements){
		int nbFreeDegrees = 0;
		if (stateGame.onGoban(e.getCol(), e.getRow())) {
			if (!((IEmplacement)e).isIn(emplacements)) {
				if (stateGame.isFree(e.getCol(), e.getRow())) {
					nbFreeDegrees++;
					emplacements.add(e);
				} else if (stateGame.getIntersection(e.getCol(), e.getRow()).equals(player)) {
					nbFreeDegrees += countFreeDegrees(stateGame, player, e, emplacements);
				}
			}
		}
		return nbFreeDegrees;
	}

	/**
	 * Display the goban in the console view
	 * @param stateGame
	 */
	public static void showGobanOnConsole(IStateGame stateGame) {
		StringBuffer chaine = new StringBuffer();
		for (int col = 0; col < stateGame.getMaxCol(); col++) {
			chaine.append(" " + col);
		}
		System.out.println("\n " + chaine.toString());

		for (int row = 0; row < stateGame.getMaxRow(); row++) {
			System.out.print(row);
			for (int col = 0; col < stateGame.getMaxCol(); col++) {
				if (stateGame.isPlayer(col, row)) {
					System.out.print("|P");
				} else if (stateGame.isComputer(col, row)) {
					System.out.print("|C");
				} else {
					System.out.print("| ");
				}
			}
			System.out.println("|" + row);
		}
		System.out.println(" " + chaine.toString() + "\n");
	}


	/**
	 * @param coordinator
	 * @param e is the location chosen by the current player
	 * @return true if the player capturing opponent stones
	 */
	public static int hasCapturedWithThisEmplacement(IStateGame stateGame, IEmplacement e, IPlayer currentPlayer) {
		int hasFreedom = 999;
		Boolean otherIPlayer = ! currentPlayer.getColor();
		List<IEmplacement> emplacements = new ArrayList<IEmplacement>();
		emplacements.add(e);
		//case Top
		int rowTop = (e.getRow() - 1);

		if (stateGame.onGoban(e.getCol(), rowTop)) {
			hasFreedom = checkOpponentHasFreedom(stateGame, otherIPlayer, new Emplacement(e.getCol(), rowTop), emplacements);
		}
		if (hasFreedom != 0) {
			//case Bottom
			int rowBottom = (e.getRow() + 1);
			if (stateGame.onGoban(e.getCol(), rowBottom)) {
				hasFreedom = checkOpponentHasFreedom(stateGame, otherIPlayer, new Emplacement(e.getCol(), rowBottom), emplacements);
			}
			if (hasFreedom != 0) {
				//case Left
				int colLeft = (e.getCol() -1);
				if(stateGame.onGoban(colLeft, e.getRow())) {
					hasFreedom = checkOpponentHasFreedom(stateGame, otherIPlayer, new Emplacement(colLeft, e.getRow()), emplacements);
				}
				if (hasFreedom != 0) {
					//case Right
					int colRight = (e.getCol() +1);
					if(stateGame.onGoban(colRight, e.getRow())) {
						hasFreedom = checkOpponentHasFreedom(stateGame, otherIPlayer, new Emplacement(colRight, e.getRow()), emplacements);
					}
				}
			}
		}
		//System.out.println("Tools.hasCapturedWithThisEmplacement() retour de hasFreedom = "+hasFreedom);
		return hasFreedom;
	}

	/**
	 * Require that the opponentEmplacement tested must exist on the goban
	 * @param coordinator
	 * @param otherIPlayer
	 * @param opponentEmplacement
	 * @param locationsAlreadyVisited
	 * @return the number of freedom degrees of the opponent
	 */
	public static int checkOpponentHasFreedom(IStateGame stateGame, Boolean otherIPlayer, IEmplacement opponentEmplacement, List<IEmplacement> locationsAlreadyVisited){
		int hasFreedom = 999;
		// if the opponentEmplacement have been already tested
		if (!((opponentEmplacement).isIn(locationsAlreadyVisited))) {
			locationsAlreadyVisited.add(opponentEmplacement);
			//if opponentEmplacement is a free Emplacement
			if (!stateGame.isFree(opponentEmplacement.getCol(), opponentEmplacement.getRow())) {
				// if opponentEmplacement is the opponent player
				if (stateGame.getIntersection(opponentEmplacement.getCol(), opponentEmplacement.getRow()).equals(otherIPlayer)) {
					hasFreedom = countFreeDegrees(stateGame, otherIPlayer, opponentEmplacement, new ArrayList<IEmplacement>()); 
				}
			}
		}
		return hasFreedom;
	}
}
