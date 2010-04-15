/*   This program is free software: you can redistribute it and/or modify
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

package fr.alma.ia;

import java.util.ArrayList;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Stone;

/**
 * 
 * @author Adrien GUILLE, Vincent FERREIRA
 *
 * revision $Revision$
 * 
 */

public interface ITree {

	/**
	 * Add a child in last position to the current tree-node
	 * @param child the node to add as a child
	 */
	public abstract void addChild(Tree child);

	/**
	 * Test if the tree-node is a leaf
	 * @return true if the node is a leaf
	 */
	public abstract boolean isLeaf();

	/**
	 * Returns the parent of the node
	 * @return a node or "null"
	 */
	public abstract Tree getParent();

	/**
	 * Returns the Goban associated with the node
	 * @return a Goban
	 */
	public abstract Goban getGoban();

	/**
	 * Return the children of the node
	 * @return a LinkedList<Tree>
	 */
	public abstract ArrayList<Tree> getChildren();

	/**
	 * Generate all the possible movements from the current Goban and add them as children
	 * @param stone the color to be played
	 */
	public abstract void generateChildren(AtariGo atariGo, Stone stone,
			Stone currentPlayer);

	/**
	 * Return the depth of the node
	 * @return an integer>=0
	 */
	public abstract int getDepht();

	public abstract Goban getMove();

}