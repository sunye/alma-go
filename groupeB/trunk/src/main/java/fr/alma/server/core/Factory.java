/*
 *   
 *   IA Project ATARI-GO
 *   UNIVERSITY OF NANTES
 *   MASTER ALMA 1
 *   2009 - 2010
 * 	 Version 1.0
 *   
 *   $Date$
 *   $Author$
 * 	 $Revision$
 * 
 *   Copyright (C) 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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

/**
 * To build the different element of the Atari-Go game
 */
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
