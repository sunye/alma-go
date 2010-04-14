package fr.alma.go.goban;

import java.util.ArrayList;

public class Goban {

	private Stone[][] plate;

	private ArrayList<StonesGroup> whiteGroups;

	private ArrayList<StonesGroup> blackGroups;

	private int whiteTaken;

	private int blackTaken;

	/**
	 * Gets the list of the neighbours groups of a stone
	 * 
	 * @param abs
	 * @param ord
	 * @param col
	 * @return
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
	 * @return
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
	 * @param col
	 * @return
	 */
	private boolean addGroup(StonesGroup newGroup, char col) {
		if (col == 'w') {
			return whiteGroups.add(newGroup);
		} else {
			return blackGroups.add(newGroup);
		} // if
	}

	/**
	 * Removes a group from white or black list
	 * 
	 * @param inGroups
	 * @param col
	 * @return
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
	 * @param turn
	 */
	private void changes(char turn) {
		if (turn == 'b') {
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
	 * @return
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
	 * @return
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
	 * @param ord
	 * @return
	 */
	private boolean isFree(int abs, int ord) {
		return plate[abs][ord].isUndefined();
	} // boolean isFree(int,int)

	/**
	 * Gets the number of liberties of a stone
	 * 
	 * @param stone
	 * @return
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
	 * @return
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
	 * @param ord
	 * @param col
	 * @return
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
	 * @return
	 */
	public boolean gameOver() {
		return (whiteTaken > 0 | blackTaken > 0);
	}

	public Stone[][] getPlate() {
		return plate;
	}

	// public boolean testGroups(){
	// return (whiteGroups.size()==1 && blackGroups.size()==2);
	// }

} // class Goban
