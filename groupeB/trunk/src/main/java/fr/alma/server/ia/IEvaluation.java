package fr.alma.server.ia;

import fr.alma.server.core.IStateGame;
import fr.alma.server.rule.StatusCheck;

public interface IEvaluation {

	int evaluate(IStateGame stateGame, StatusCheck status);
}
