package fr.alma.server.core;

import fr.alma.server.ia.IEvaluation;


public class Computer extends AbstractPlayer {
	private IEvaluation evaluation;
	private IStrategy strategy;
	private boolean enable = false;
	IPlayer player = this;
	
	
	public Computer(String name, boolean color) {
		super(name, color);
	}
	
	@Override
	public void play() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Start thread computer");
				IEmplacement emplacement = getStrategy().getEmplacementMax(getEvaluation(), false);
				try {
					strategy.getStateGame().play(emplacement.getCol(), emplacement.getRow(), getColor());
				} catch (Exception e) {
					System.out.println("Internal error : " + e.getLocalizedMessage());
				}
				raiseEvent(new PlayEvent(player, PlayEvent.AFTER, emplacement));
				System.out.println("Stop thread computer : " + emplacement);
			}
		};
		new Thread(runnable).start();
	}
	
	
	@Override
	public void setEnabled(boolean enable) {
		this.enable = enable;
	}

	
	public boolean isEnabled() {
		return enable;
	}

	
	public IStrategy getStrategy() {
		return strategy;
	}
	
	
	public void setStrategy(IStrategy strategieGame) {
		this.strategy = strategieGame;
	}
	

	public IEvaluation getEvaluation() {
		return evaluation;
	}
	

	public void setEvaluation(IEvaluation evaluation) {
		this.evaluation = evaluation;
	}

	
}
