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

public class StonesGroup {

	private ArrayList<Stone> group;

	/**
	 * Constructor
	 */
	public StonesGroup() {
		group = new ArrayList<Stone>();
	} // StonesGroup()

	/**
	 * Add stone to group
	 * 
	 * @param stone
	 *            Stone to add
	 * @return True if stone was correctly added
	 */
	public boolean add(Stone stone) {
		return group.add(stone);
	} // boolean add(Stone)

	/**
	 * Remove stone from group
	 * 
	 * @param stone
	 *            Stone to remove
	 * @return True if stone was correctly removed
	 */
	public boolean remove(Stone stone) {
		return group.remove(stone);
	} // boolean remove(Stone)

	/**
	 * Get group's stones
	 * 
	 * @return List of group's stones
	 */
	public ArrayList<Stone> getGroup() {
		return group;
	} // ArrayList<Stone> getGroup()

	/**
	 * True if group contains stone
	 * 
	 * @param stone
	 *            Stone to check
	 * @return True if group contains stone
	 */
	public boolean contains(Stone stone) {
		for (Stone innerStone : group) {
			if (innerStone == stone) {
				return true;
			} // if
		} // for
		return false;
	} // boolean contains(stone)

	/**
	 * Get group's size
	 * 
	 * @return Group's size
	 */
	public int size() {
		return group.size();
	} // int size()

}
