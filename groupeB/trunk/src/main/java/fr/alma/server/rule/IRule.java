package fr.alma.server.rule;

import fr.alma.server.core.IEmplacement;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;

public interface IRule {
	public boolean accept(IStateGame stateGame, IEmplacement emplacement, IPlayer currentPlayer);
}
