package fr.alma.server.core;

import fr.alma.client.ihm.Goban;
import fr.alma.server.ia.AlphaBeta;
import fr.alma.server.rule.RuleAlreadyBusy;
import fr.alma.server.rule.RuleManager;

public class Factory {
	
	public static RuleManager getRuleManager(IStateGame stateGame) {
		RuleManager ruleManager = new RuleManager();	
		ruleManager.addRule(new RuleAlreadyBusy(stateGame));
		return ruleManager;
	}
	
	
	public static IStateGame getStateGame() {
		return new StateGame();
	}
	
	
	public static Goban getGoban(IStateGame stateGame) {
		return new Goban(stateGame);
	}
	
	
	public static IStrategy getStrategy(IStateGame stateGame) {
		return new AlphaBeta(stateGame);
	}
}
