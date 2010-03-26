package fr.alma.server.core;

public interface IStrategy {
	public IEmplacement getEmplacementMax();
	public IStateGame getStateGame();
}
