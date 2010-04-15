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

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Tree.java
 * @author FREDERIC DUMONT FREDERIC DUMONCEAUX
 * @version 1.0
 * 
 * revision $Revision$
 */
public class Tree {
     
     public Node root;
     

     public Tree(){
          root = null;
     }
     

     public Tree(Move m, State s){
          root = new Node(m, s);
          root.listeFils = new LinkedList<Node>();
     }
     
     
     public Tree creerArbre(Node n, State s){
          Tree tree= new Tree(n.move,s);
          tree.root.listeFils = n.listeFils;
          return tree;
     }


     public Tree ajouterFils(Node n){
          root.ajouterFils(n);
          return this;
     }


     public Tree ajouterFils(Node child, Node parent){
          parent.ajouterFils(child);
          return this;
     }


     public Tree retirerFils(Node child, Node parent){
          parent.retirerFils(child);
          System.out.println(parent.listeFils);
          return this;
     }
     
      
      public Tree racine(){
          return this;
     }
      
      
     public Node rootNode(){
          return this.root;
     }
      
     
     public Node node(Move m){
          return root.node(m);
     }
      
      
      public Node pere(Node n){
          return n.pere;
     }
     
      public int count()
      {
           int i = 1;
           
           ArrayDeque<Node> queue = new ArrayDeque<Node>();
           queue.add(root);
           
           while (queue.size() > 0)
           {
                 Node r = queue.pollFirst();
                 
                 Iterator<Node> it = r.listeFils.iterator();
                 while (it.hasNext())
                 {
                      queue.add(it.next());
                      ++i;
                 }
           }
           
           return i;
      }
      
        // parcours en profondeur d'abord
      /*public void parcoursP(){
        Parcours p = new Parcours_LA(this);
         ((Parcours_LA)p).parcours_Profondeur();
          System.out.println();;
    }

   // parcours en largeur d'abord
    public void parcoursL(){
        Parcours p = new Parcours_LA(this);
          ((Parcours_LA)p).parcours_Largeur();
         System.out.println();;
    }*/
}
