package fr.alma.server.rule;

import java.util.List;
import java.util.ArrayList;
import fr.alma.server.core.Coordinator;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.ia.Tools;


public class RuleSuicide {
	private Coordinator coordinator;

	public RuleSuicide(Coordinator coordinator) {
		this.coordinator = coordinator;
	}

	public boolean provokeSuicide(IEmplacement emplacement) {
		List<IEmplacement> emplacements = new ArrayList<IEmplacement>();
		int degrees = Tools.countFreeDegrees(coordinator,coordinator.getCurrentPlayer().getColor(), emplacement, emplacements);
		//System.out.println("Freedom degrees for group ["+col+"]["+row+"] d="+degrees);
		//if (degrees == 0) System.out.println("SUICIDE");
		return (degrees==0);
	}
}
