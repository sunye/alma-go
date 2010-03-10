package fr.alma.server.rule;

import fr.alma.server.core.StateGame;

public class RuleAlreadyBusy implements IRule {

	private StateGame stateGame = null;
	
	
	public RuleAlreadyBusy(StateGame game) {
		this.stateGame = stateGame;
	}
	
	
	@Override
	public boolean accept(short col, short row) {
		return stateGame.isFree(col, row);
	}

}
