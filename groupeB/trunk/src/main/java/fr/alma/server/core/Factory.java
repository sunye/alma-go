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
import fr.alma.server.rule.RuleManager;


public class Factory {
	
	public static RuleManager getRuleManager() {
		RuleManager ruleManager = new RuleManager();	
		return ruleManager;
	}
	
	
	public static IStateGame getStateGame(Context context) {
		return new StateGame(context);
	}
	
	
	public static Goban getGoban(Context context) {
		return new Goban(context);
	}
	
	
	public static IStrategy getStrategy(ICoordinator coordinator) {
		return new AlphaBeta(coordinator);
	}

	
	public static IEvaluation getEvaluation(Context context) {
		return new Evaluation(context);
	}
	
	
	
	public static JMenuBar getMenuBar(Context context) {
		return new Menu().getMenuBar(new ActionManager(context));
	}
	
	
	public static IHMParam getIHMParam(Context context) {
		return new IHMParam(context);
	}
}
