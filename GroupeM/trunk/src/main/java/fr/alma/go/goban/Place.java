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

public class Place {

	private int abs;

	private int ord;

	/**
	 * Constructor
	 * 
	 * @param a
	 *            Place's absciss
	 * @param o
	 *            Place's ordinate
	 */
	public Place(int a, int o) {
		abs = a;
		ord = o;
	} // Place(int,int)

	/**
	 * Get place's absciss
	 * 
	 * @return Actual value of abs
	 */
	public int getAbs() {
		return abs;
	} // getAbs()

	/**
	 * Get place's ordinate
	 * 
	 * @return Actual value of ord
	 */
	public int getOrd() {
		return ord;
	} // getOrd()

	/**
	 * True if place equals this
	 * 
	 * @param place
	 *            Place to test
	 * @return True if places have the same coordinates
	 */
	public boolean equals(Place place) {
		return (this.abs == place.abs && this.ord == place.ord);
	} // boolean equals(Place)

	public String toString() {
		return abs + "," + ord;
	} // String toString()

}
