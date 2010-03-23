package fr.alma.server.rule;

import java.util.List;
import java.util.ArrayList;
import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Emplacement;
import fr.alma.server.core.IEmplacement;
import fr.alma.server.ia.Tools;


public class RuleSuicide implements IRule {
	private Coordinator coordinator;

	public RuleSuicide(Coordinator coordinator) {
		this.coordinator = coordinator;
	}

	@Override
	public boolean accept(short col, short row) {
		List<IEmplacement> emplacements = new ArrayList<IEmplacement>();
		int degrees = Tools.countFreeDegrees(coordinator,coordinator.getCurrentPlayer().getColor(), new Emplacement(col, row), emplacements);
		//System.out.println("degré de liberté du groupe ["+col+"]["+row+"] d="+degrees);
		if(degrees == 0)System.out.println("SUICIDE");
		return (degrees!=0);
	}
}
