package fr.alma.server.rule;

import fr.alma.server.core.IStateGame;

public class RuleAlreadyBusy implements IRule {

	private IStateGame stateGame = null;
	
	
	public RuleAlreadyBusy(IStateGame stateGame) {
		this.stateGame = stateGame;
	}
	
	
	@Override
	public boolean accept(short col, short row) {
		return stateGame.isFree(col, row);
	}

}
