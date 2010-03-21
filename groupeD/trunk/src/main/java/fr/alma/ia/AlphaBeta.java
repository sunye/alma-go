package fr.alma.ia;

import fr.alma.atarigo.Pion;
import fr.alma.atarigo.Plateau;

public class AlphaBeta {
		
		static public PlateauValue meilleur_coup;
		static public int extremum;
		static public int nb_noeuds;
		static public int niveau_max;
		
		public static void init(int nvmax){
			meilleur_coup=new PlateauValue(0);
			extremum=0;
			nb_noeuds=0;
			niveau_max=nvmax;
		}


	public static PlateauValue Valeur(int niveau, Arbre edj, int ExtremumCourant, Pion pion){
		if(niveau<niveau_max)
			edj.genererFils(pion);
		
		if(niveau<niveau_max && !edj.estFeuille()){
		//appel récursif
			if(niveau%2==0 || niveau==0){
				//recherche du max
				return max(niveau, edj, ExtremumCourant, pion);
			}else{
				//recherche du min
				return min(niveau, edj, ExtremumCourant, pion);
			}
		}else{
			return Evaluation.evaluer(edj.getPlateau(),pion);
		}
	}

	public static PlateauValue max(int niveau, Arbre edj,int ExtremumCourant,Pion pion){
		//recherche du max
		PlateauValue max = new PlateauValue(-100000);
		int i = 0;
		while(max.evaluation_<ExtremumCourant && edj.getFils().size()>i){
			nb_noeuds++;
			PlateauValue V = Valeur(niveau+1,edj.getFils().get(i),max.evaluation_,pion.oppose());
			if(V.evaluation_>max.evaluation_){
				max.clone(V);
			}
			i++;
		}
		return max;		
	}
	
	public static PlateauValue min(int niveau, Arbre edj,int ExtremumCourant,Pion pion){
		//recherche du min
		PlateauValue min = new PlateauValue(100000);
		int i = 0;
		while(min.evaluation_>ExtremumCourant && edj.getFils().size()>i){
			nb_noeuds++;
			PlateauValue V = Valeur(niveau+1,edj.getFils().get(i),min.evaluation_,pion.oppose());
			if(V.evaluation_<min.evaluation_){
				min.clone(V);
			}
			i++;
		}
		return min;		
	}
}

