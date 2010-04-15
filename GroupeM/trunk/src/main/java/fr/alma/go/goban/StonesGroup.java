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

	public StonesGroup() {
		group = new ArrayList<Stone>();
	} // StonesGroup()

	public boolean add(Stone stone) {
		return group.add(stone);
	} // boolean add(Stone)
	
	public boolean remove(Stone stone) {
		return group.remove(stone);
	} // boolean remove(Stone)
	
	public ArrayList<Stone> getGroup(){
		return group;
	}

	public boolean contains(Stone stone) {
		for (Stone innerStone : group) {
			if (innerStone == stone) {
				return true;
			} // if
		} // for
		return false;
	} // boolean contains(stone)

	public int size() {
		return group.size();
	}

}
