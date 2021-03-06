/*****************************************************************************
**
** This program is free software; you can redistribute it and/or modify
** it under the terms of the GNU General Public License as published by
** the Free Software Foundation; either version 2 of the License, or
** (at your option) any later version.
** 
** This program is distributed in the hope that it will be useful,
** but WITHOUT ANY WARRANTY; without even the implied warranty of
** MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
** GNU General Public License for more details.
** 
** You should have received a copy of the GNU General Public License
** along with this program; if not, write to the Free Software
** Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*****************************************************************************/



package go;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Node.java
 * @author FREDERIC DUMONT FREDERIC DUMONCEAUX
 * @version 1.0
 * 
 * revision $Revision$
 */
public class Node{

     public transient LinkedList<Node> listeFils;
     public Node pere;
     public int hauteur;
     public Move move;
     public State state;


     public Node(final Move move, final State state){
          this.move = move;
          this.listeFils = new LinkedList<Node>();
          this.hauteur = 1;
          this.state = state;
     }


     public Node node(final Move movement){

          Node result = null;

          if(move.x == movement.x && move.y == movement.y){
               result = this;
          }
          else {
               final Iterator<Node> iter = listeFils.iterator();
               while (iter.hasNext()) {
                    final Node fils = iter.next();
                    if(fils.node(movement) != null){
                         result = fils.node(movement);
                    }
               }     
          }

          return result;
     }


     public void ajouterFils(final Node fils){
          fils.pere = this;
          listeFils.add(fils);
     }

     public void retirerFils(final Node fils){
          listeFils.remove(fils);
     }


     /* (non-Javadoc)
      * @see java.lang.Object#toString()
      */
     @Override
     public String toString() {
          return "Node [move=" + move + "]";
     }


     public State getState() {
          return state;
     }


     public void setState(final State state) {
          this.state = state;
     }


     public Move getMove() {
          return move;
     }


     public void setMove(final Move move) {
          this.move = move;
     }


     public int getHauteur() {
          return hauteur;
     }


     public void setHauteur(final int hauteur) {
          this.hauteur = hauteur;
     }


     public Node getPere() {
          return pere;
     }


     public void setPere(final Node pere) {
          this.pere = pere;
     }
}
