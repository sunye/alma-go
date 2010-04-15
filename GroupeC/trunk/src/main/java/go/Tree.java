package go;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author Frédéric Dumonceaux
 *
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
