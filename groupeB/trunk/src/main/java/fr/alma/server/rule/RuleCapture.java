package fr.alma.server.rule;

import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.ia.Tools;

public class RuleCapture {

	
	public RuleCapture() {
		super();
	}


	public boolean provokeCapture(IStateGame stateGame, IEmplacement emplacement, IPlayer currentPlayer) {
		int isZeroFreeDegrees = Tools.hasCapturedWithThisEmplacement(stateGame, emplacement, currentPlayer);
		return (isZeroFreeDegrees == 0);
	}

}
