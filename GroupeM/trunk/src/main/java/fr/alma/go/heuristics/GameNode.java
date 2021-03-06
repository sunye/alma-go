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

package fr.alma.go.heuristics;

import fr.alma.go.goban.Place;

public class GameNode {

	private Place place;

	private int note;

	/**
	 * Set place coordinates
	 * 
	 * @param a
	 *            Place absciss
	 * @param o
	 *            Place ordinate
	 */
	public void setCoords(int a, int o) {
		place = new Place(a, o);
	} // void setCoords(int,int)

	/**
	 * Set place
	 * 
	 * @param pl
	 *            Place to set
	 */
	public void setPlace(Place pl) {
		place = pl;
	} // void setCoords(int,int)

	/**
	 * Set note
	 * 
	 * @param n
	 *            Note to set
	 */
	public void setNote(int n) {
		note = n;
	} // void setNote(int)

	/**
	 * Get place
	 * 
	 * @return Place
	 */
	public Place getPlace() {
		return place;
	} // int getAbs()

	/**
	 * Get note
	 * 
	 * @return Note
	 */
	public int getNote() {
		return note;
	} // int getNote()

} // class GameNode
