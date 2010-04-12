/**
 * Université de Nantes
 * Master 1 ALMA
 * 
 * Projet de IA
 * @author lahuidi
 * @author landryngassa 
 * 
 * Copyright © 2010 ZERBITA Mohamed El Hadi & Ngassa Hubert, Tous droits réservés
 */

package fr.alma.main;
import fr.alma.ihm.Ihm;
import fr.alma.jeu.Grille;
import fr.alma.structure.Arbre;

/**
 * Classe du main
 */
	public class Main {

	/**
	 * Entrés du programme
	 * @param args
	 */
	public static void main(String[] args) {
		
		//new Ihm();
		 Grille g = new Grille(); 
         
         Arbre a = new Arbre(g); 
                   
         long begin = System.currentTimeMillis(); 
         a.remplirArbre(); 
         long end = System.currentTimeMillis(); 
         float time = ((float) (end-begin)) ; 
          
         //a.ParcoursProf(); 
         //a.AffichageNA(); 
             
         System.out.println("Temp d'execution : "+time/1000+" secondes"); 

	}

}
