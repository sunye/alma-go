package fr.alma.ia;

import fr.alma.atarigo.Stone;
import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;

public class Evaluation {

	static ValuedGoban evaluer2(Goban plateau, Stone pion){
		ValuedGoban cmpt = new ValuedGoban(0,plateau);
		for(Position pos : plateau.getCells(pion))
			cmpt.evaluation_=cmpt.evaluation_+plateau.getLiberty(pos);
		
		return cmpt;
	}
	
	static ValuedGoban evaluate(Goban plateau, Stone pion){
		ValuedGoban cmpt = new ValuedGoban(0,plateau);
		for(Position pos : plateau.getCells(pion.opponent()))
			if(plateau.getLiberty(pos)==1)
				cmpt.evaluation_=10000;
			else
				cmpt.evaluation_=cmpt.evaluation_+plateau.getLiberty(pos);
		for(Position posi : plateau.getCells(pion))
			if(plateau.getLiberty(posi)==1)
				cmpt.evaluation_=cmpt.evaluation_-100;
		return cmpt;
	}
}