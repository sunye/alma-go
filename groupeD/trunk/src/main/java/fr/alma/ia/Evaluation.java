package fr.alma.ia;

import fr.alma.atarigo.Pion;
import fr.alma.atarigo.Plateau;
import fr.alma.atarigo.Position;

public class Evaluation {

	static PlateauValue evaluer2(Plateau plateau, Pion pion){
		PlateauValue cmpt = new PlateauValue(0,plateau);
		for(Position pos : plateau.getCases(pion))
			cmpt.evaluation_=cmpt.evaluation_+plateau.lireLibertes(pos);
		
		return cmpt;
	}
	
	static PlateauValue evaluer(Plateau plateau, Pion pion){
		PlateauValue cmpt = new PlateauValue(0,plateau);
		for(Position pos : plateau.getCases(pion.oppose()))
			if(plateau.lireLibertes(pos)==1)
				cmpt.evaluation_=10000;
			else
				cmpt.evaluation_=cmpt.evaluation_+plateau.lireLibertes(pos);
		for(Position posi : plateau.getCases(pion))
			if(plateau.lireLibertes(posi)==1)
				cmpt.evaluation_=cmpt.evaluation_-100;
		return cmpt;
	}
}