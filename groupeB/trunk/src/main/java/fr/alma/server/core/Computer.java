package fr.alma.server.core;

import java.util.List;

import fr.alma.server.ia.AlphaBeta;

public class Computer extends AbstractPlayer {
	private IStrategy strategieGame;
	private boolean enable = false;
	
	
	public Computer(String name, boolean color, IStrategy strategy) {
		super(name, color);
		strategieGame = strategy;
	}
	
	@Override
	public void play() {
		IEmplacement emplacement = strategieGame.getEmplacementMax();
		strategieGame.getStateGame().play(emplacement.getCol(), emplacement.getRow(), getColor());
		
	}

	@Override
	public boolean addPlayListener(PlayListener actionListener) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<PlayListener> getPlayerListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removePlayListener(PlayListener playListener) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	@Override
	public void setEnabled(boolean enable) {
		this.enable = enable;
	}

}
