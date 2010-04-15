// Copyright (C) 2010 Alexandre Garnier & Yann Treguer
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.

package fr.alma.go.goban;

import java.util.ArrayList;

public class Goban implements Cloneable {

	private Stone[][] plate;

	private ArrayList<StonesGroup> whiteGroups;

	private ArrayList<StonesGroup> blackGroups;

	private int whiteTaken;

	private int blackTaken;

	/**
	 * Gets the list of the neighbours groups of a stone
	 * 
	 * @param abs
	 *            Stone absciss
	 * @param ord
	 *            Stone ordinate
	 * @param col
	 *            Stone color
	 * @return The list of the neighbours groups of a stone
	 */
	private ArrayList<StonesGroup> getNeighbours(int abs, int ord, char col) {
		ArrayList<StonesGroup> stonesGroups;
		if (col == 'w') {
			stonesGroups = whiteGroups;
		} else {
			stonesGroups = blackGroups;
		} // if
		ArrayList<StonesGroup> inGroups = new ArrayList<StonesGroup>();
		for (StonesGroup group : stonesGroups) {
			ArrayList<Stone> list = group.getGroup();
			boolean inGroup = false;
			for (Stone stone : list) {
				if ((abs > 0 && plate[abs - 1][ord] == stone)
						| (abs < 8 && plate[abs + 1][ord] == stone)
						| (ord > 0 && plate[abs][ord - 1] == stone)
						| (ord < 8 && plate[abs][ord + 1] == stone)) {
					inGroup = true;
					break;
				} // if
			} // for
			if (inGroup) {
				inGroups.add(group);
			}
		} // for
		return inGroups;
	} // getNeighbours(int,int,char)

	/**
	 * Merges a list of groups into one group
	 * 
	 * @param inGroups
	 *            Groups to merge
	 * @return Merged groups
	 */
	private StonesGroup merge(ArrayList<StonesGroup> inGroups) {
		StonesGroup newGroup = new StonesGroup();
		if (inGroups.size() > 0) {
			for (StonesGroup group : inGroups) {
				ArrayList<Stone> list = group.getGroup();
				for (Stone stone : list) {
					newGroup.add(stone);
				} // for
			} // for
		} // if
		return newGroup;
	}

	/**
	 * Adds a white or black group to the white or black list of groups
	 * 
	 * @param newGroup
	 *            Group to add
	 * @param col
	 *            Group color
	 * @return True if no problem occured
	 */
	private boolean addGroup(StonesGroup newGroup, char col) {
		if (col == 'w') {
			return whiteGroups.add(newGroup);
		} else {
			return blackGroups.add(newGroup);
		} // if
	}

	/**
	 * Removes groups from white or black list
	 * 
	 * @param inGroups
	 *            Groups to remove
	 * @param col
	 *            Groups color
	 * @return True if no problem occured
	 */
	private boolean remove(ArrayList<StonesGroup> inGroups, char col) {
		boolean ok = true;
		if (col == 'w') {
			for (StonesGroup group : inGroups) {
				ok &= whiteGroups.remove(group);
			} // for
		} else {
			for (StonesGroup group : inGroups) {
				ok &= blackGroups.remove(group);
			} // for
		} // if
		return ok;
	}

	/**
	 * Removes groups of stones if they have no liberties
	 * 
	 * @param color
	 *            Groups color
	 */
	private void changes(char color) {
		if (color == 'b') {
			ArrayList<StonesGroup> whiteGroupsToRemove = new ArrayList<StonesGroup>();
			for (StonesGroup group : whiteGroups) {
				if (this.getLiberties(group) == 0) {
					this.remove(group);
					whiteGroupsToRemove.add(group);
				} // if
			} // for
			for (StonesGroup group : whiteGroupsToRemove) {
				whiteTaken += group.size();
				whiteGroups.remove(group);
			} // for
		} else {
			ArrayList<StonesGroup> blackGroupsToRemove = new ArrayList<StonesGroup>();
			for (StonesGroup group : blackGroups) {
				if (this.getLiberties(group) == 0) {
					this.remove(group);
					blackGroupsToRemove.add(group);
				} // if
			} // for
			for (StonesGroup group : blackGroupsToRemove) {
				blackTaken += group.size();
				blackGroups.remove(group);
			} // for
		} // if
	} // changes(char)

	/**
	 * Removes each stone of a group from the goban
	 * 
	 * @param group
	 *            Group to remove
	 * @return True if no problem occured
	 */
	private boolean remove(StonesGroup group) {
		ArrayList<Stone> list = group.getGroup();
		boolean ok = true;
		for (Stone stone : list) {
			ok &= this.remove(stone);
		} // for
		return ok;
	} // boolean remove(StonesGroup)

	/**
	 * Removes a stone from the goban
	 * 
	 * @param stone
	 *            Stone to remove
	 * @return True if no problem occured
	 */
	private boolean remove(Stone stone) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 0; j++) {
				if (plate[i][j] == stone) {
					plate[i][j].setColor('u');
					return true;
				} // if
			} // for
		} // for
		return false;
	} // boolean remove(Stone)

	/**
	 * True if the stone is undefined
	 * 
	 * @param abs
	 *            Stone absciss
	 * @param ord
	 *            Stone ordinate
	 * @return True if the stone is undefined
	 */
	private boolean isFree(int abs, int ord) {
		return plate[abs][ord].isUndefined();
	} // boolean isFree(int,int)

	/**
	 * Gets the number of liberties of a stone
	 * 
	 * @param stone
	 *            Considered stone
	 * @return The number of liberties of a stone
	 */
	private int getLiberties(Stone stone) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (plate[i][j] == stone) {
					int liberties = 0;
					if (i > 0 && plate[i - 1][j].getColor() == 'u') {
						liberties++;
					} // if
					if (i < 8 && plate[i + 1][j].getColor() == 'u') {
						liberties++;
					} // if
					if (j > 0 && plate[i][j - 1].getColor() == 'u') {
						liberties++;
					} // if
					if (j < 8 && plate[i][j + 1].getColor() == 'u') {
						liberties++;
					} // if
					return liberties;
				} // if
			} // for
		} // for
		return 0;
	} // int getLiberties(Stone)

	/**
	 * Gets the number of liberties of a group
	 * 
	 * @param group
	 *            Considered Group
	 * @return The number of liberties of a group
	 */
	private int getLiberties(StonesGroup group) {
		int liberties = 0;
		ArrayList<Stone> list = group.getGroup();
		for (Stone stone : list) {
			liberties += this.getLiberties(stone);
		} // for
		return liberties;
	} // int getLiberties(StonesGroup)

	/**
	 * Constructor
	 */
	public Goban() {
		plate = new Stone[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				plate[i][j] = new Stone();
			} // for
		} // for
		whiteGroups = new ArrayList<StonesGroup>();
		blackGroups = new ArrayList<StonesGroup>();
		whiteTaken = 0;
		blackTaken = 0;
	} // Goban()

	/**
	 * Play a black or white turn on the place (abs,ord)
	 * 
	 * @param abs
	 *            Stone absciss
	 * @param ord
	 *            Stone ordinate
	 * @param col
	 *            Stone color
	 * @return True if place was free and not suicide
	 */
	public boolean play(int abs, int ord, char col) {
		if (this.isFree(abs, ord)) {

			plate[abs][ord].setColor(col);
			this.changes(col);

			ArrayList<StonesGroup> inGroups = this.getNeighbours(abs, ord, col);
			StonesGroup newGroup = this.merge(inGroups);
			newGroup.add(plate[abs][ord]);

			if (this.getLiberties(newGroup) == 0) {
				plate[abs][ord].setColor('u');
				return false;
			} // if

			return (this.remove(inGroups, col) && this.addGroup(newGroup, col));

		} // if

		return false;
	}// void play(int,int,char)

	/**
	 * True if the game is over
	 * 
	 * @return True if the game is over
	 */
	public boolean gameOver() {
		return (whiteTaken > 0 | blackTaken > 0);
	} // boolean gameOver()

	/**
	 * Get the goban's plate
	 * 
	 * @return The goban's plate
	 */
	public Stone[][] getPlate() {
		return plate;
	} // Stone[][] getPlate()

	/**
	 * True if plate is empty
	 * 
	 * @return True if all plate's stones are undefined
	 */
	public boolean isEmpty() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (!plate[i][j].isUndefined()) {
					return false;
				} // if
			} // for
		} // for
		return true;
	} // boolean isEmpty()

	// public boolean testGroups(){
	// return (whiteGroups.size()==1 && blackGroups.size()==2);
	// }

	@Override
	public Goban clone() {
		Goban goban = new Goban();
		goban.plate = new Stone[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				goban.plate[i][j] = new Stone(plate[i][j].getColor());
			} // for
		} // for
		goban.whiteGroups = new ArrayList<StonesGroup>();
		for (StonesGroup group : whiteGroups) {
			StonesGroup newGroup = new StonesGroup();
			ArrayList<Stone> list = group.getGroup();
			for (Stone stone : list) {
				newGroup.add(new Stone(stone.getColor()));
			} // for
			goban.whiteGroups.add(newGroup);
		} // for
		goban.blackGroups = new ArrayList<StonesGroup>();
		for (StonesGroup group : blackGroups) {
			StonesGroup newGroup = new StonesGroup();
			ArrayList<Stone> list = group.getGroup();
			for (Stone stone : list) {
				newGroup.add(new Stone(stone.getColor()));
			} // for
			goban.blackGroups.add(newGroup);
		} // for
		goban.whiteTaken = whiteTaken;
		goban.blackTaken = blackTaken;
		return goban;
	} // Goban clone()

	/**
	 * Get numbers of taken white stones
	 * 
	 * @return Actual value of whiteTaken
	 */
	public int getWhiteTaken() {
		return whiteTaken;
	} // int getWhiteTaken()

	/**
	 * Get numbers of taken black stones
	 * 
	 * @return Actual value of blackTaken
	 */
	public int getBlackTaken() {
		return blackTaken;
	} // int getBlackTaken()

} // class Goban
