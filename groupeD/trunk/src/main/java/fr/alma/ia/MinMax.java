package fr.alma.ia;

import fr.alma.atarigo.Pion;


public class MinMax {
	
	static public PlateauValue meilleur_coup;
	
	public static void init(){
		meilleur_coup=new PlateauValue(0);
	}
	
	public static PlateauValue calculer(Arbre n,Pion pion,int prof){
		if(n.estFeuille()){
			return Evaluation.evaluer(n.getPlateau(),pion.oppose());

		}else{
			if(prof==0){
				PlateauValue max = new PlateauValue(0);
				for(Arbre a : n.getFils()){
					PlateauValue minmax = new PlateauValue(0);// calculer(a,Pion.NOIR);
					minmax.clone(calculer(a,Pion.NOIR,prof+1));
					if(minmax.superieur(max)){
						max.clone(minmax);
						meilleur_coup.clone(max);
					}
				}
				return max;
			}
			else{
				PlateauValue min = new PlateauValue(1000000);
				for(Arbre a : n.getFils()){
					PlateauValue minmax = new PlateauValue(0); //=calculer(a,Pion.BLANC);
					minmax.clone(calculer(a,Pion.BLANC,prof+1));
					if(minmax.inferieur(min))
						min.clone(minmax);
				}
				return min;
			}		
		}	
	}
}


/*
 *  MiniMax(p)=f(p) si p est une position terminale
 * MiniMax(p)=max(MiniMax(o1), ..., MiniMax(on)) si p est une position joueur avec fils o1, ..., on
 * MiniMax(p)=min(MiniMax(j1), ..., MiniMax(jm)) si p est une position opposant avec fils j1, ..., jm
*/