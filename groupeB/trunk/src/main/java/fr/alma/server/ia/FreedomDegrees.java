/*
 *   
 *   IA Project ATARI-GO
 *   UNIVERSITY OF NANTES
 *   MASTER ALMA 1
 *   2009 - 2010
 * 	 Version 1.0
 *   
 *   $Date$
 *   $Author$
 * 	 $Revision$
 * 
 *   Copyright (C) 2010 Romain Gournay & Bruno Belin, All rights reserved.
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
package fr.alma.server.ia;

import java.util.ArrayList;
import java.util.List;

import fr.alma.client.action.Context;
import fr.alma.server.core.Location;
import fr.alma.server.core.ILocation;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;

/**
 * list of functions about freedom degrees
 */
public class FreedomDegrees {

	public static int getMaxDegreeFree(Context context) {
		return ((context.getSizeGoban() * context.getSizeGoban()) / 2) + 2;
	}

	/**
	 * work with countCaseFreeDegrees
	 * @param stateGame
	 * @param player
	 * @param location
	 * @param alreadyVisit
	 * @return number degrees of freedom of the player stone location e and its group
	 */
	public static int countFreeDegrees(IStateGame stateGame, boolean player, ILocation location, List<ILocation> alreadyVisit) {
		int nbFreeDegrees = 0;	
		alreadyVisit.add(location);

		//case Top
		int rowTop = (location.getRow() - 1);
		nbFreeDegrees += countCaseFreeDegrees(new Location(location.getCol(), rowTop), stateGame, player, alreadyVisit);

		//case Bottom
		int rowBottom = (location.getRow() + 1);
		nbFreeDegrees += countCaseFreeDegrees(new Location(location.getCol(), rowBottom), stateGame, player, alreadyVisit);

		//case Left
		int colLeft = (location.getCol() -1);
		nbFreeDegrees += countCaseFreeDegrees(new Location(colLeft, location.getRow()), stateGame, player, alreadyVisit);

		//case Right
		int colRight = (location.getCol() +1);
		nbFreeDegrees += countCaseFreeDegrees(new Location(colRight, location.getRow()), stateGame, player, alreadyVisit);
		
		return nbFreeDegrees;
	}

	/**
	 * work with countFreeDegrees
	 * @param location
	 * @param stateGame
	 * @param player
	 * @param alreadyVisited
	 * @return the number of freedom degrees for a stone location
	 */

	private static int countCaseFreeDegrees(ILocation location, IStateGame stateGame, boolean player, List<ILocation> alreadyVisited){
		int nbFreeDegrees = 0;
		if (stateGame.onGoban(location.getCol(), location.getRow())) {
			if (!((ILocation)location).isIn(alreadyVisited)) {
				if (stateGame.isFree(location.getCol(), location.getRow())) {
					nbFreeDegrees++;
					alreadyVisited.add(location);
				} else if (stateGame.getIntersection(location.getCol(), location.getRow()).equals(player)) {
					nbFreeDegrees += countFreeDegrees(stateGame, player, location, alreadyVisited);
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
	 * @param location is the location chosen by the current player
	 * @return 0 if the player capturing opponent stones
	 */
	public static int hasCapturedWithThisEmplacement(IStateGame stateGame, ILocation location, IPlayer currentPlayer) {
		int hasFreedom = 999;
		Boolean otherIPlayer = ! currentPlayer.getColor();
		List<ILocation> emplacements = new ArrayList<ILocation>();
		emplacements.add(location);
		//case Top
		int rowTop = (location.getRow() - 1);

		if (stateGame.onGoban(location.getCol(), rowTop)) {
			hasFreedom = checkOpponentHasFreedom(stateGame, otherIPlayer, new Location(location.getCol(), rowTop), emplacements);
		}
		if (hasFreedom != 0) {
			//case Bottom
			int rowBottom = (location.getRow() + 1);
			if (stateGame.onGoban(location.getCol(), rowBottom)) {
				hasFreedom = checkOpponentHasFreedom(stateGame, otherIPlayer, new Location(location.getCol(), rowBottom), emplacements);
			}
			if (hasFreedom != 0) {
				//case Left
				int colLeft = (location.getCol() -1);
				if(stateGame.onGoban(colLeft, location.getRow())) {
					hasFreedom = checkOpponentHasFreedom(stateGame, otherIPlayer, new Location(colLeft, location.getRow()), emplacements);
				}
				if (hasFreedom != 0) {
					//case Right
					int colRight = (location.getCol() +1);
					if(stateGame.onGoban(colRight, location.getRow())) {
						hasFreedom = checkOpponentHasFreedom(stateGame, otherIPlayer, new Location(colRight, location.getRow()), emplacements);
					}
				}
			}
		}	
		return hasFreedom;
	}

	/**
	 * Require that the opponentEmplacement tested must exist on the goban
	 * @param coordinator
	 * @param otherIPlayer
	 * @param opLocation opponent location
	 * @param alreadyVisited
	 * @return the number of freedom degrees of the opponent
	 */
	public static int checkOpponentHasFreedom(IStateGame stateGame, Boolean otherIPlayer, ILocation opLocation, List<ILocation> alreadyVisited){
		int hasFreedom = 999;
		// if the opponentEmplacement have been already tested
		if (!((opLocation).isIn(alreadyVisited))) {
			alreadyVisited.add(opLocation);
			//if opponentEmplacement is not a free Emplacement
			if (!stateGame.isFree(opLocation.getCol(), opLocation.getRow())) {
				// if opponentEmplacement is the opponent player
				if (stateGame.getIntersection(opLocation.getCol(), opLocation.getRow()).equals(otherIPlayer)) {
					hasFreedom = countFreeDegrees(stateGame, otherIPlayer, opLocation, new ArrayList<ILocation>()); 
				}
			}
		}
		return hasFreedom;
	}
}
