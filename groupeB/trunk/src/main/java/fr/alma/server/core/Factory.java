package fr.alma.server.core;

import javax.swing.JMenuBar;

import fr.alma.client.action.ActionManager;
import fr.alma.client.action.Context;
import fr.alma.client.ihm.Goban;
import fr.alma.client.ihm.IHMParam;
import fr.alma.client.ihm.Menu;
import fr.alma.server.ia.AlphaBeta;
import fr.alma.server.ia.Evaluation;
import fr.alma.server.ia.IEvaluation;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;


public class Factory {
	
	public static RuleManager getRuleManager() {
		RuleManager ruleManager = new RuleManager();	
		return ruleManager;
	}
	
	
	public static IStateGame getStateGame() {
		return new StateGame(Configuration.LINE_V, Configuration.LINE_H);
	}
	
	
	public static Goban getGoban(IStateGame stateGame) {
		return new Goban(stateGame);
	}
	
	
	public static IStrategy getStrategy(ICoordinator coordinator) {
		return new AlphaBeta(coordinator);
	}

	
	public static IEvaluation getEvaluation(IPlayer computer, IPlayer player) {
		return new Evaluation(computer, player);
	}
	
	
	
	public static JMenuBar getMenuBar(Context context) {
		return new Menu().getMenuBar(new ActionManager(context));
	}
	
	
	public static IHMParam getIHMParam(Context context) {
		return new IHMParam(context);
	}
}
