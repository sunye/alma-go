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

import fr.alma.atarigo.Goban;

/**
 * ValuedGoban.java implements a class based on the Goban class.
 * It associates a goban with an evaluation.
 *
 * @author      Adrien GUILLE
 * @author      Vincent FERREIRA
 * 
 * @version 1.0
 * 
 * revision $Revision$
 * 
 */

public class ValuedGoban {
	public Goban goban_;
	public int evaluation_;
	
	public ValuedGoban(int eval){
		evaluation_=eval;
		goban_=new Goban(9,9);
	}
	
	public ValuedGoban(Goban goban){
		evaluation_=0;
		goban_=goban;
	}
	
	public ValuedGoban(int eval,Goban goban){
		evaluation_=eval;
		goban_=new Goban(goban);
	}
	
	public void clone(ValuedGoban goban){
		for(int i=0;i<goban.goban_.getLines();i++)
			for(int j=0;j<goban.goban_.getColumns();j++)
				goban_.matrice[i][j]=goban.goban_.matrice[i][j];
		evaluation_=goban.evaluation_;
	}
	
	public boolean superior(ValuedGoban goban){
		return evaluation_>goban.evaluation_;
	}
	
	public boolean inferior(ValuedGoban goban){
		return evaluation_<goban.evaluation_;
	}
}
