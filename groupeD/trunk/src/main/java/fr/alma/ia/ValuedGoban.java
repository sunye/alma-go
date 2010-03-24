package fr.alma.ia;

import fr.alma.atarigo.Goban;

public class ValuedGoban {
	public Goban goban_;
	public int evaluation_;
	
	public ValuedGoban(int eval){
		evaluation_=eval;
		goban_=new Goban(9,9);
	}
	
	public ValuedGoban(Goban goban){
		evaluation_=0;
		goban_=goban;
	}
	
	public ValuedGoban(int eval,Goban goban){
		evaluation_=eval;
		goban_=new Goban(goban);
	}
	
	public void clone(ValuedGoban goban){
		for(int i=0;i<goban.goban_.getLines();i++)
			for(int j=0;j<goban.goban_.getColumns();j++)
				goban_.matrice[i][j]=goban.goban_.matrice[i][j];
		evaluation_=goban.evaluation_;
	}
	
	public boolean superior(ValuedGoban goban){
		return evaluation_>goban.evaluation_;
	}
	
	public boolean inferior(ValuedGoban goban){
		return evaluation_<goban.evaluation_;
	}
}
