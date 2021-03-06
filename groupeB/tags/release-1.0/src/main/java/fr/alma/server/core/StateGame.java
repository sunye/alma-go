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
package fr.alma.server.core;


import fr.alma.client.action.Context;

/**
 * Implement a state game
 */
public class StateGame implements IStateGame {

	private Boolean[][] intersection = null;
	private Context context = null;
	//private int countLimitComputer = 0;   To implement additional option
	//private int countLimitPlayer = 0;		To implement additional option
	private int level = 0;
	
	
	public StateGame(Context context) {
		this.context = context;
		clear();
	}
	
	@Override
	public int getMaxCol() {
		return context.getSizeGoban();
	}

	
	@Override
	public int getMaxRow() {
		return context.getSizeGoban();
	}

	/**
	 * Verifier les regles du jeu !
	 * @throws Exception 
	 */
	@Override
	public boolean play(int col, int row, boolean computer) throws Exception {
		if (! isFree(col, row)) {
			throw new Exception("busy box at col = " + col + ", row = " + row + " !");
		}
		
		intersection[col][row] = computer;
		return true;
	}

	@Override
	public void remove(int col, int row) {
		intersection[col][row] = null;
	}

	@Override
	public boolean isComputer(int col, int row) {
		return (intersection[col][row] == context.getComputer().getColor());
	}

	@Override
	public boolean isFree(int col, int row) {
		if (onGoban(col, row)) {
			return (intersection[col][row] == null);
		}
		return false;
	}

	@Override
	public boolean onGoban(int col, int row) {
		return (col >= 0 && col < getMaxCol() && row >= 0 &&  row < getMaxRow());
	}


	@Override
	public boolean isPlayer(int col, int row) {
		return (intersection[col][row] == context.getPlayer().getColor());
	}
	
	
	public Boolean[][] getGoban(){
		return intersection;
	}

	
	@Override
	public Object getIntersection(int col, int row) {
		return intersection[col][row];
	}


	@Override
	public void load(Boolean[][] intersection) {
		this.intersection = intersection;
	}
	
	@Override
	public Object clone() {
		StateGame clone = new StateGame(context);
		
		for (int cptCol = 0; cptCol < getMaxRow(); cptCol++) {
			for (int cptRow = 0; cptRow < getMaxCol(); cptRow++) {
				if (! isFree(cptCol, cptRow)) {
					try {
						clone.play(cptCol, cptRow, (Boolean)getIntersection(cptCol, cptRow));
					} catch (Exception e) {
						System.out.println("Internal error in clone operation : " + e.getLocalizedMessage());
					}
				}
			}
		}
		return clone;
	}

	@Override
	public void clear() {
		intersection = new Boolean[context.getSizeGoban()][context.getSizeGoban()];
	}

	/*@Override  To implement additional option
	public int countLimitComputer() {
		return countLimitComputer;
	}

	@Override   To implement additional option
	public int countLimitPlayer() {
		return countLimitPlayer;
	}*/

	@Override
	public void cleanUp() {
		clear();
		
	}

	@Override
	public int getLevel() {
		return level;
	}
	
	@Override
	public void setLevel(int level) {
		this.level = level;
	}
}
