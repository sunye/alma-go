package fr.alma.server.rule;

import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Emplacement;
import fr.alma.server.ia.Tools;

public class RuleCapture implements IRule {

	private Coordinator coordinator;

	public RuleCapture(Coordinator coordinator){
		this.coordinator = coordinator;
	}

	@Override
	public boolean accept(short col, short row) {
		int isZeroFreeDegrees = Tools.hasCapturedWithThisEmplacement(coordinator,new Emplacement(col, row));
		if(isZeroFreeDegrees==0) System.out.println("WINNER");
		return (isZeroFreeDegrees>0);
	}

}
