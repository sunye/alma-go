
package fr.alma.jeu; 

import java.awt.Component;
import java.awt.Point;
import java.util.HashMap;

import javax.swing.JLabel;

import fr.alma.ihm.JBlanc;
import fr.alma.ihm.JNoir;
import fr.alma.ihm.Tour;
import fr.alma.jeu.Pion.Couleur;

  
  /** 
  * Classe qui contien la grille du jeu et le dictionnaire des valeurs positions. 
  * @author lahuidi 
  * 
  */ 
 public class Grille { 
          
         public Pion [][] Contenu; 
         public HashMap <Point, Point> ghmp; 
                   
         public Grille() { 
                  
                Contenu = new Pion[9][9]; 
                  
                for(int i=0;i<9;i++) 
                	 for(int j=0;j<9;j++) 
                         Contenu[i][j] = new Pion(new Point(i,j)); 
              
                initHashMap();
         } 
         
         
        /**
     	 * Méthode qui permet d'effacer la grille de tout les pions
     	 */
     	public void effacerGrille(JLabel Grille){
     		Component[]contenu;
     		contenu = Grille.getComponents();
     		for(Component each : contenu){
     			each.setVisible(false);
     			each = null;
     		}
     	}
         
     	/**
    	 * Cette méthode met à jour la grille avec le pion ajouté.
    	 * @param position position du pion ajouté. 
    	 */
    	public Tour mettrePion(Point position,JLabel LGrille, JBlanc Blanc, JNoir Noir, Tour t) {
    				
    		if(t == Tour.NOIR ){
    				System.out.println(t);
    				Contenu[position.x][position.y].couleur = Couleur.NOIR;
    				(Contenu[position.x][position.y]).position = position;
    				mettrePionNoir(LGrille, Noir, position);
    				t = Tour.BLANC;
    			}else{
    				System.out.println(t);
    				Contenu[position.x][position.y].couleur = Couleur.BLANC;
    				(Contenu[position.x][position.y]).position=position;
    				mettrePionBlanc(LGrille,Blanc, position);
    				t = Tour.NOIR;
    			}
    		return t;
    	}
    	
    	
    	
     	/**
    	 * Méthode qui met un pion blanc dans une position
    	 * @param position position du pion
    	 */
    	public void mettrePionBlanc(JLabel LGrille,JBlanc Blanc,Point position) {
    		JLabel blanc = (JLabel) Blanc.clone();
    		LGrille.add(blanc);
    		blanc.setLocation(ghmp.get(position).x-15,ghmp.get(position).y-15);
    		blanc.setVisible(true);
    	}
    	
    	/**
    	 * Méthode qui met un pion noir dans une position
    	 * @param position position du pion
    	 */
    	public void mettrePionNoir(JLabel LGrille,JNoir Noir, Point position) {
    		JLabel noir = (JLabel) Noir.clone();
    		LGrille.add(noir);
    		
    		noir.setLocation(ghmp.get(position).x-15,ghmp.get(position).y-15);
    		noir.setVisible(true);
    	}
    	
    	

    	/**
    	 * Méthode qui met à jour toute la grille lors d'une capture
    	 * @param other contenu de la nouvelle grille
    	 */
    	public void miseAjourGrille(Grille other, JLabel LGrille, JBlanc Blanc, JNoir Noir){
    		effacerGrille(LGrille);
    				
    		for(int i=0;i<9;i++) 
    			for(int j=0;j<9;j++) 
                     if(other.Contenu[i][j] == Contenu[i][j] && other.Contenu[i][j].couleur == Couleur.NOIR){
                    	Contenu[i][j] = other.Contenu[i][j];
                    	mettrePionNoir(LGrille, Noir, new Point(i,j));
                    }else if(other.Contenu[i][j] == Contenu[i][j] && other.Contenu[i][j].couleur == Couleur.BLANC){
                    	Contenu[i][j] = other.Contenu[i][j];
                   	 	mettrePionBlanc(LGrille, Blanc,new Point(i,j));
                    }
        }
         /** 
          * Initialise la table de hachage avec les points qui correspondent aux valeurs. 
          */ 
         private void initHashMap(){ 
                  
                  
                 ghmp = new HashMap< Point, Point>(); 
                 //------------------------------ 
                 ghmp.put(new Point(0,0), new Point(45,45)); 
                 ghmp.put(new Point(1,0), new Point(70,45));   
                 ghmp.put(new Point(2,0), new Point(94,45)); 
                 ghmp.put(new Point(3,0), new Point(118,45)); 
                 ghmp.put(new Point(4,0), new Point(142,45)); 
                 ghmp.put(new Point(5,0), new Point(167,45)); 
                 ghmp.put(new Point(6,0), new Point(190,45)); 
                 ghmp.put(new Point(7,0), new Point(215,45)); 
                 ghmp.put(new Point(8,0), new Point(240,45)); 
                  
                 //------------------------------ 
                 ghmp.put(new Point(0,1), new Point(45,70)); 
                 ghmp.put(new Point(1,1), new Point(70,70));  
                 ghmp.put(new Point(2,1), new Point(94,70)); 
                 ghmp.put(new Point(3,1), new Point(118,70)); 
                 ghmp.put(new Point(4,1), new Point(142,70)); 
                 ghmp.put(new Point(5,1), new Point(167,70)); 
                 ghmp.put(new Point(6,1), new Point(190,70)); 
                 ghmp.put(new Point(7,1), new Point(215,70)); 
                 ghmp.put(new Point(8,1), new Point(240,70)); 
                  
                 //------------------------------ 
                 ghmp.put(new Point(0,2), new Point(45,94)); 
                 ghmp.put(new Point(1,2), new Point(70,94));  
                 ghmp.put(new Point(2,2), new Point(94,94)); 
                 ghmp.put(new Point(3,2), new Point(118,94)); 
                 ghmp.put(new Point(4,2), new Point(142,94)); 
                 ghmp.put(new Point(5,2), new Point(167,94)); 
                 ghmp.put(new Point(6,2), new Point(190,94)); 
                 ghmp.put(new Point(7,2), new Point(215,94)); 
                 ghmp.put(new Point(8,2), new Point(240,94)); 
                  
                 //------------------------------ 
                 ghmp.put(new Point(0,3), new Point(45,118)); 
                 ghmp.put(new Point(1,3), new Point(70,118));         
                 ghmp.put(new Point(2,3), new Point(94,118)); 
                 ghmp.put(new Point(3,3), new Point(118,118)); 
                 ghmp.put(new Point(4,3), new Point(142,118)); 
                 ghmp.put(new Point(5,3), new Point(167,118)); 
                 ghmp.put(new Point(6,3), new Point(190,118)); 
                 ghmp.put(new Point(7,3), new Point(215,118)); 
                 ghmp.put(new Point(8,3), new Point(240,118)); 
                  
                 //------------------------------ 
                 ghmp.put(new Point(0,4), new Point(45,142)); 
                 ghmp.put(new Point(1,4), new Point(70,142));         
                 ghmp.put(new Point(2,4), new Point(94,142)); 
                 ghmp.put(new Point(3,4), new Point(118,142)); 
                 ghmp.put(new Point(4,4), new Point(142,142)); 
                 ghmp.put(new Point(5,4), new Point(167,142)); 
                 ghmp.put(new Point(6,4), new Point(190,142)); 
                 ghmp.put(new Point(7,4), new Point(215,142)); 
                 ghmp.put(new Point(8,4), new Point(240,142)); 
                  
                 //------------------------------ 
                 ghmp.put(new Point(0,5), new Point(45,167)); 
                 ghmp.put(new Point(1,5), new Point(70,167));         
                 ghmp.put(new Point(2,5), new Point(94,167)); 
                 ghmp.put(new Point(3,5), new Point(118,167)); 
                 ghmp.put(new Point(4,5), new Point(142,167)); 
                 ghmp.put(new Point(5,5), new Point(167,167)); 
                 ghmp.put(new Point(6,5), new Point(190,167)); 
                 ghmp.put(new Point(7,5), new Point(215,167)); 
                 ghmp.put(new Point(8,5), new Point(240,167)); 
                  
                 //------------------------------ 
                 ghmp.put(new Point(0,6), new Point(45,190)); 
                 ghmp.put(new Point(1,6), new Point(70,190));         
                 ghmp.put(new Point(2,6), new Point(94,190)); 
                 ghmp.put(new Point(3,6), new Point(118,190)); 
                 ghmp.put(new Point(4,6), new Point(142,190)); 
                 ghmp.put(new Point(5,6), new Point(167,190)); 
                 ghmp.put(new Point(6,6), new Point(190,190)); 
                 ghmp.put(new Point(7,6), new Point(215,190)); 
                 ghmp.put(new Point(8,6), new Point(240,190)); 
                  
                 //------------------------------ 
                 ghmp.put(new Point(0,7), new Point(45,215)); 
                 ghmp.put(new Point(1,7), new Point(70,215));         
                 ghmp.put(new Point(2,7), new Point(94,215)); 
                 ghmp.put(new Point(3,7), new Point(118,215)); 
                 ghmp.put(new Point(4,7), new Point(142,215)); 
                 ghmp.put(new Point(5,7), new Point(167,215)); 
                 ghmp.put(new Point(6,7), new Point(190,215)); 
                 ghmp.put(new Point(7,7), new Point(215,215)); 
                 ghmp.put(new Point(8,7), new Point(240,215)); 
                  
                 //------------------------------ 
                 ghmp.put(new Point(0,8), new Point(45,240)); 
                 ghmp.put(new Point(1,8), new Point(70,240));         
                 ghmp.put(new Point(2,8), new Point(94,240)); 
                 ghmp.put(new Point(3,8), new Point(118,240)); 
                 ghmp.put(new Point(4,8), new Point(142,240)); 
                 ghmp.put(new Point(5,8), new Point(167,240)); 
                 ghmp.put(new Point(6,8), new Point(190,240)); 
                 ghmp.put(new Point(7,8), new Point(215,240)); 
                 ghmp.put(new Point(8,8), new Point(240,240)); 
         }
         
           
 } 