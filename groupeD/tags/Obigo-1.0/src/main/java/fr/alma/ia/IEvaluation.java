package fr.alma.ia;

import fr.alma.atarigo.Goban;
import fr.alma.atarigo.Position;
import fr.alma.atarigo.Stone;

public interface IEvaluation {
	public ValuedGoban evaluate(Goban goban, Goban parentGoban, Stone stone, Position position);
}
