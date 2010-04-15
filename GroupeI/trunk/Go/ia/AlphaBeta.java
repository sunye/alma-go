/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ia;

import coeur.Joueur;
import coeur.Plateau;
import coeur.GoDonnees;

/**
 * Classe permettant a une IA de calculer un coup a jouer  
 */
public class AlphaBeta {	
	
	/** Profondeur maximal que l'on pourra atteindre dans l'arbre alpha-beta */
	private int profmax;
	/** Ligne choisie a la fin du calcul alpha-beta */
	private int ligne;
	/** Colonne choisie a la fin du calcul alpha-beta */
	private int colonne;
	/** Le joueur de type IA qui cherche ou jouer et qui declanche donc la construction de l'arbre alpha-beta */
	private Joueur joueurRacine;
	/** La couleur du joueur adverse */
	private int couleurAdverse;
	
	
	
	
	/**
	 * Contructeur permettant de chercher le coup le plus interessant a jouer d'apres les conditions donnees 
	 * @param plateau Le plateau de jeu
	 * @param joueur Le joueur de type IA qui cherche où jouer 
	 */
	public AlphaBeta(Plateau plateau, Joueur joueur){		
		// Determination de la profondeur maximale pour cet ordi
		  // Si l'IA est au niveau debutant
		  if(joueur.getNiveauIA() == GoDonnees.IA_Debutant){
			  this.profmax = 3;			
			
		  }
          // Si l'IA est au niveau intermediaire
		  else if(joueur.getNiveauIA() == GoDonnees.IA_Intermediaire){
			  this.profmax = 3;			
	
		  }
		  // Si l'IA est au niveau expert
		  else if(joueur.getNiveauIA() == GoDonnees.IA_Expert){
			  this.profmax = 4;			
		
			
		  }	
		  //si l'ia joue pour l'humain (dans le cas de l'aide au joueur)
		  else if(joueur.getType()==GoDonnees.HUMAIN){
			  this.profmax=3;
			  joueur.setNiveauIA(GoDonnees.IA_Intermediaire);
		  }
		
		this.joueurRacine = joueur;
		if (this.joueurRacine.getCouleur() == GoDonnees.BLANC){ this.couleurAdverse = GoDonnees.NOIR;
		}else {this.couleurAdverse = GoDonnees.BLANC;}
		
		Noeud racine = new Noeud(plateau,joueurRacine.getCouleur());
		// On lance alpha-beta
		alpha_beta(racine,-IADonnee.INFINI,IADonnee.INFINI);
	
		joueurRacine.setForceAjouer(false);
	}
	
	/**
	 * La fonction construisant l'arbre alpha-beta pour chercher le coup le plus interressant possible
	 * @param noeud Le noeud de l'arbre contenant une representation du jeu 
	 * @param alpha
	 * @param beta
	 * @return une note calculee par l'heuristique
	 */
    private int alpha_beta(Noeud noeud,int alpha,int beta){
	   int sauv;
       //Si on est sur une feuille
	   if ((noeud.getProfondeur()==this.profmax) || ( !noeud.getPlateau().existeCoupPossible(noeud.getCouleur()))){
		     if(this.joueurRacine.getNiveauIA() == GoDonnees.IA_Expert){
		    	     return heuristiqueExpert(noeud);
		     }else if(this.joueurRacine.getNiveauIA() == GoDonnees.IA_Intermediaire){
		             return this.heuristiqueIntermediaire(noeud);		
		     }else { return this.heuristiqueDebutant(noeud);}
       } 
	   else	
	      if ((noeud.getProfondeur() % 2) == 0) {// Noeux de type MAX 	
		    Plateau plateauFils;
		    Noeud noeudFils;			
			int i=0;
			int j;
			boolean A_joue = false;
			
			//Si le joueur n'est pas oblige de jouer immediatement, on regarde si l'on peut descendre dans l'arbre. 
			if(!joueurRacine.estForceAjouer()){
			   //Pour tous les coups possibles et tant que alpha est inferieur a beta on descend dans l'arbre.			
			   while((alpha<beta) && (i!=9 )){
			   	  j=0;
				  while((alpha<beta) && (j!=9)){			
				       if(noeud.getPlateau().coupPossible(i, j,noeud.getCouleur())){
				    	   A_joue = true;
				    	   plateauFils = noeud.getPlateau().copie();
						   plateauFils.poserPion(i,j,noeud.getCouleur()); 
				    	   noeudFils = new Noeud(plateauFils, noeud.getCouleurAdverse(),noeud.getProfondeur()+1);
				    
					       sauv=alpha;	//on sauvegarde la valeur de alpha
					       int val = alpha_beta(noeudFils,alpha,beta);
					       alpha=Math.max(val,alpha);
					       
                           //Si la valeur de alpha a change et que l'on est a la racine, on a trouve un meilleur coup
					       if ((alpha!=sauv) && (noeud.getProfondeur()==0)) {						       	   
					    	   this.ligne = i;
					    	   this.colonne = j;
					       }				
				       };
					   j++;
				   }
				   i++;
			   }		
		     //Si aucun coup n'a pu etre joue, on passe notre tour. On passe donc au MIN en descendant dans l'arbre.	
		     if(!A_joue){
		  	   plateauFils = noeud.getPlateau().copie();
			   noeudFils = new Noeud(plateauFils, noeud.getCouleurAdverse(),noeud.getProfondeur()+1);
			   alpha=Math.max(alpha_beta(noeudFils,alpha,beta),alpha);
			   
		     }	
		 
			 return alpha; 
	    }// On est force a jouer on renvoie + infini afin que dans le noeud Min superieur on ne s'interesse pas a cette branche
		// Puisque sinon alpha vaudrait - infini et cette branche serait choisie dans le noeud Min superieur
			else{
				//System.out.println("return +infini = "+Donnee.INFINI);
				return IADonnee.INFINI;
			}
				
	}else {// Noeux de type MIN 	
		Plateau plateauFils;
	    Noeud noeudFils;			
		int i=0;
		int j;
		boolean A_joue = false;
		
        //Si le joueur n'est pas oblige de jouer immediatement, on regarde si l'on peut descendre dans l'arbre. 
		if(!joueurRacine.estForceAjouer()){
		   //Pour tous les coups possibles et tant que alpha est inferieur a beta on descend dans l'arbre.
		   while((alpha<beta) && (i!=9 )){
			   j=0;
			   while((alpha<beta) && (j!=9)){			
			       if(noeud.getPlateau().coupPossible(i, j,noeud.getCouleur())){
			       	   A_joue = true;
			    	   plateauFils = noeud.getPlateau().copie();
					   plateauFils.poserPion(i,j,noeud.getCouleur()); 
			    	   noeudFils = new Noeud(plateauFils, noeud.getCouleurAdverse(),noeud.getProfondeur()+1);
	  		
			    	   int val = alpha_beta(noeudFils,alpha,beta);
	  		           beta=Math.min(val,beta);
	  		        
			        };
				    j++;
			   }
			   i++;
		    }		
           //Si aucun coup n'a pu etre joue, on passe notre tour. On passe donc au MAX en descendant dans l'arbre.	
		   if(!A_joue){
			   plateauFils = noeud.getPlateau().copie();
			   noeudFils = new Noeud(plateauFils, noeud.getCouleurAdverse(),noeud.getProfondeur()+1);
			   beta=Math.min(alpha_beta(noeudFils,alpha,beta),beta);
			   
		   }
		   

		return beta;
		}// On est force a jouer on renvoie - infini afin que dans le noeud Max superieur on ne s'interesse pas a cette branche
		// Puisque sinon beta vaudrait + infini et cette branche serait choisie dans le noeud Max superieur
		else{
	
			return (-1*IADonnee.INFINI);
		}  
		
	  }		
	}
    
    /**
	 * Retourne la ligne calculee par la fonction alpha-beta
	 * @return la ligne choisie
	 */
	public int getLigne(){
		return this.ligne;
	}
	
	/**
	 * Retoure la colonne calculee par la fonction alpha-beta
	 * @return la colonne choisie
	 */
	public int getColonne(){
		return this.colonne;
	}
	
	
	
    /**
    * Permet de donner une note a une feuille de l'arbre alpha-beta pour une IA de niveau Expert
    * @param noeud Le noeud correspondant a une feuille
    * @return la note calculee pour cette feuille
    */
   private int heuristiqueExpert(Noeud noeud){	
	
	return heuristiqueIntermediaire(noeud);
  }
    
   /**
    * Permet de donner une note a une feuille de l'arbre alpha-beta pour une IA de niveau Intermediaire.
    * @param noeud Le noeud correspondant a une feuille
    * @return la note calculee pour cette feuille
    */
   private int heuristiqueIntermediaire(Noeud noeud){
	   
	   if (noeud.getPlateau().getnbrPioncapture(couleurAdverse)>=noeud.getPlateau().getObjectif(joueurRacine.getCouleur())){
		   return IADonnee.INFINI;
	   } else if (noeud.getPlateau().getnbrPioncapture(joueurRacine.getCouleur())>=noeud.getPlateau().getObjectif(couleurAdverse)){
		   return -IADonnee.INFINI;
	   }else{		   
		   		   
		   int nombre_liberte_min_joueur=noeud.getPlateau().nombre_liberte_min(joueurRacine.getCouleur());
		   int nombre_liberte_min_joueur_adverse=noeud.getPlateau().nombre_liberte_min(couleurAdverse);

   
		   int resultat=0;
		   if (joueurRacine.getplateau().a_deux_yeux(joueurRacine.getCouleur())){
			   resultat+=1000;
		   }
		   if (joueurRacine.getplateau().a_deux_yeux(couleurAdverse)){
			   resultat-=1000;
		   }
		   
		  
		   resultat+=20*(joueurRacine.getplateau().nombre_liberte_total(joueurRacine.getCouleur())-joueurRacine.getplateau().nombre_liberte_total(couleurAdverse));
	
		 
		   resultat+=10*(nombre_liberte_min_joueur-nombre_liberte_min_joueur_adverse)+500*(joueurRacine.getplateau().getNbrPions(joueurRacine.getCouleur())-joueurRacine.getplateau().getNbrPions(couleurAdverse));
		   
		   resultat+=Math.random()*10;
	       return resultat;
	   }
  }
        
   
   /**
    * Permet de donner une note a une feuille de l'arbre alpha-beta pour une IA de niveau Debutant
    * @param noeud Le noeud correspondant a une feuille
    * @return la note calculee pour cette feuille
    */
   private int heuristiqueDebutant(Noeud noeud){
	  if (noeud.getPlateau().getnbrPioncapture(couleurAdverse)>=noeud.getPlateau().getObjectif(joueurRacine.getCouleur())){
		   return IADonnee.INFINI;
	   } else if (noeud.getPlateau().getnbrPioncapture(joueurRacine.getCouleur())>=noeud.getPlateau().getObjectif(couleurAdverse)){
		   return -IADonnee.INFINI;
	   } else return (int) (Math.random()*100);
	
   }   
   
}
