package fr.alma.ia;

import fr.alma.atarigo.Plateau;

public class PlateauValue {
	public Plateau plateau_;
	public int evaluation_;
	
	public PlateauValue(int eval){
		evaluation_=eval;
		plateau_=new Plateau(9,9);
	}
	
	public PlateauValue(Plateau pl){
		evaluation_=0;
		plateau_=pl;
	}
	
	public PlateauValue(int eval,Plateau pl){
		evaluation_=eval;
		plateau_=new Plateau(pl);
	}
	
	public void clone(PlateauValue pvl){
		for(int i=0;i<9;i++)
			for(int j=0;j<9;j++)
				plateau_.grille[i][j]=pvl.plateau_.grille[i][j];
		evaluation_=pvl.evaluation_;
	}
	
	public boolean superieur(PlateauValue p){
		return evaluation_>p.evaluation_;
	}
	
	public boolean inferieur(PlateauValue p){
		return evaluation_<p.evaluation_;
	}
}
