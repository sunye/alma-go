package fr.alma.server.rule;

import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;


public class RuleAlreadyBusy implements IRule {
	
	
	public RuleAlreadyBusy() {
		super();
	}
	
	
	@Override
	public boolean accept(IStateGame stateGame, IEmplacement emplacement, IPlayer currentPlayer) {
		return stateGame.isFree(emplacement.getCol(), emplacement.getRow());
	}

}
