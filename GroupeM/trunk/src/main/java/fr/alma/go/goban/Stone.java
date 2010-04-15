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

public class Stone {

	private char color; // u : undefined ; w : white ; b : black

	public Stone() {
		color = 'u'; // undefined by default
	} // Stone()

	public Stone(char col) {
		color = col;
	} // Stone(char)

	public void setColor(char col) {
		color = col;
	} // void setColor(char)

	public char getColor() {
		return color;
	} // char getColor()

	public boolean isUndefined() {
		return color=='u';
	}

} // class Stone
