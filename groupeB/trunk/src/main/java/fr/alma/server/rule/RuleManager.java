package fr.alma.server.rule;

import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;


public class RuleManager {
	
	RuleSuicide ruleSuicide = null;
	RuleCapture ruleCapture = null;
	RuleAlreadyBusy ruleAlreadyBusy = null;
	
	
	public RuleManager() {
		ruleAlreadyBusy = new RuleAlreadyBusy();
		ruleSuicide = new RuleSuicide();
		ruleCapture = new RuleCapture();
	}
	
	
	/**
	 * Check if the player can played at this emplacement
	 * This function must be very fast because 
	 * it is called by alpha beta procedure
	 * @param emplacement
	 * @return
	 */
	public StatusCheck checkBefore(IStateGame stateGame, IEmplacement emplacement, IPlayer currentPlayer) {
		StatusCheck status = new StatusCheck();
		
		if (! ruleAlreadyBusy.accept(stateGame, emplacement, currentPlayer)) {
			status.setCanPlay(false);
			return status;
		}
		
		if (ruleCapture.provokeCapture(stateGame, emplacement, currentPlayer)) {
			status.setCanPlay(true);
			status.setGameOver(true);
			status.setEmplacement(emplacement);
			return status;
		}
		
		if (ruleSuicide.provokeSuicide(stateGame, emplacement, currentPlayer)) {
			status.setCanPlay(false);
			return status;
		}
		
		status.setCanPlay(true);
		return status;
	}
	
	public StatusCheck checkAfter(IStateGame stateGame, IEmplacement emplacement, IPlayer currentPlayer) {
		StatusCheck status = new StatusCheck();
		
		if (ruleCapture.provokeCapture(stateGame, emplacement, currentPlayer)) {
			status.setCanPlay(true);
			status.setGameOver(true);
			status.setEmplacement(emplacement);
			return status;
		}
		
		return status;
	}
	
}
