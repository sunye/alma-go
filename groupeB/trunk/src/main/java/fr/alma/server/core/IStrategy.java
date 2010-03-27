package fr.alma.server.core;

import fr.alma.server.ia.IEvaluation;

public interface IStrategy {
	public IEmplacement getEmplacementMax(IEvaluation evaluation, boolean trace);
	public IStateGame getStateGame();
}
