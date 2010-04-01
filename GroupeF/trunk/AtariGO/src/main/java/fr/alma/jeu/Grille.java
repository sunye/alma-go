
package fr.alma.jeu; 

import java.awt.Point;

  
  /** 
  * Classe qui contien la grille du jeu et le dictionnaire des valeurs positions. 
  * @author lahuidi 
  * 
  */ 
 public class Grille { 
          
         public Pion [][] Contenu; 
                   
         public Grille() { 
                  
                 Contenu = new Pion[9][9]; 
                  
                for(int i=0;i<9;i++) 
                	 for(int j=0;j<9;j++) 
                         Contenu[i][j] = new Pion(new Point(i,j)); 
                               
         } 
           
 } 