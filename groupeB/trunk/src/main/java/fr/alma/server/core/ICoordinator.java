package fr.alma.server.core;

import fr.alma.client.ihm.GoObject;
import fr.alma.server.rule.RuleManager;

public interface ICoordinator extends GoObject{

	public abstract IPlayer getComputer();

	public abstract IPlayer getPlayer();

	public abstract RuleManager getRuleManager();

	public abstract IStateGame getStateGame();
	
	public void startGame();

}