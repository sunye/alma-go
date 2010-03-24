package fr.alma.server.core;

import fr.alma.client.ihm.Goban;
import fr.alma.server.ia.AlphaBeta;
import fr.alma.server.rule.RuleCapture;
import fr.alma.server.rule.RuleManager;


public class Factory {
	
	public static RuleManager getRuleManager(Coordinator coordinator) {
		RuleManager ruleManager = new RuleManager(coordinator);	
		return ruleManager;
	}
	
	
	public static IStateGame getStateGame() {
		return new StateGame();
	}
	
	
	public static Goban getGoban(IStateGame stateGame) {
		return new Goban(stateGame);
	}
	
	
	public static IStrategy getStrategy(Coordinator coordinator, IPlayer computer) {
		return new AlphaBeta(coordinator, computer);
	}
}
