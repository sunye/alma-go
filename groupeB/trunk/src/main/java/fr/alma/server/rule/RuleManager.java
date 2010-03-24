package fr.alma.server.rule;

import java.util.List;

import fr.alma.server.core.Coordinator;
import fr.alma.server.core.IEmplacement;



public class RuleManager {
	
	List<GameListener> gameListeners;
	
	RuleSuicide ruleSuicide = null;
	RuleCapture ruleCapture = null;
	RuleAlreadyBusy ruleAlreadyBusy = null;
	
	public RuleManager(Coordinator coordinator) {
		ruleAlreadyBusy = new RuleAlreadyBusy(coordinator.getStateGame());
		ruleSuicide = new RuleSuicide(coordinator);
		ruleCapture = new RuleCapture(coordinator);
	}
	
	
	/**
	 * Check if the player can played at this emplacement
	 * @param emplacement
	 * @return
	 */
	public StatusCheck check(IEmplacement emplacement) {
		StatusCheck status = new StatusCheck();
		
		if (! ruleAlreadyBusy.accept(emplacement.getCol(), emplacement.getRow())) {
			status.setCanPlay(false);
			return status;
		}
		
		if (ruleCapture.provokeCapture(emplacement)) {
			status.setCanPlay(true);
			status.setGameOver(true);
			status.setEmplacement(emplacement);
			return status;
		}
		
		if (ruleSuicide.provokeSuicide(emplacement)) {
			status.setCanPlay(false);
			return status;
		}
		
		status.setCanPlay(true);
		return status;
	}
	
}
