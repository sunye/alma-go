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
package fr.alma.client.action;


/**
 * Consideration of an interrupt request
 */
public class ActionEscape implements IAction {

	
	private Context context = null;
	
	
	/**
	 * @param context Applicative Context
	 */
	public ActionEscape(Context context) {
		this.context = context;
	}
	
	
	/**
	 * Interrupt the computer calculation
	 * @see fr.alma.client.action.IAction#run()
	 */
	@Override
	public void run() {
		if (context.getParamGame().isPossibilityInterruption()) {
			context.getComputer().interrupt();
		}
	}

}
