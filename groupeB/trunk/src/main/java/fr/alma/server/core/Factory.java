/*
 * IA Project ATARI-GO
 * UNIVERSITY OF NANTES
 * MASTER ALMA 1
 * 2009 - 2010
 * Version 1.0
 * @author Romain Gournay & Bruno Belin
 * 
 * Copyright 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * Use is subject to license terms.
 */
package fr.alma.server.core;

import javax.swing.JLabel;
import javax.swing.JMenuBar;

import fr.alma.client.action.ActionEscape;
import fr.alma.client.action.ActionManager;
import fr.alma.client.action.Context;
import fr.alma.client.action.IAction;
import fr.alma.client.ihm.Goban;
import fr.alma.client.ihm.IHMParam;
import fr.alma.client.ihm.Menu;
import fr.alma.server.ia.AlphaBeta;
import fr.alma.server.ia.Evaluation;
import fr.alma.server.ia.IEvaluation;
import fr.alma.server.rule.RuleManager;


public class Factory {
	
	public static RuleManager getRuleManager(Context context) {
		RuleManager ruleManager = new RuleManager(context);	
		return ruleManager;
	}
	
	
	public static IStateGame getStateGame(Context context) {
		return new StateGame(context);
	}
	
	
	public static Goban getGoban(Context context) {
		return new Goban(context);
	}
	
	
	public static IStrategy getStrategy(Context context) {
		return new AlphaBeta(context);
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
	
	
	public static IAction getActionEscape(Context context) {
		IAction action = new ActionEscape(context);
		return action;
	}
	
	public static JLabel getStatutBarre() {
		JLabel labelStatus = new JLabel("Powered by Romain Gournay & Bruno BELIN");
		labelStatus.setOpaque(true);
		return labelStatus;	
	}
}
