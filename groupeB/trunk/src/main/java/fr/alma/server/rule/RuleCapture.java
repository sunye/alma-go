package fr.alma.server.rule;

import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Emplacement;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.ia.Tools;

public class RuleCapture {

	private Coordinator coordinator;

	public RuleCapture(Coordinator coordinator){
		this.coordinator = coordinator;
	}


	public boolean provokeCapture(IEmplacement emplacement) {
		int isZeroFreeDegrees = Tools.hasCapturedWithThisEmplacement(coordinator, emplacement);
		//if(isZeroFreeDegrees==0) System.out.println("WINNER");
		return (isZeroFreeDegrees==0);
	}

}
