package fr.alma.server.core;


public class Computer extends AbstractPlayer {
	private IStrategy strategieGame;
	private boolean enable = false;
	IPlayer player = this;
	
	
	public Computer(String name, boolean color) {
		super(name, color);
	}
	
	@Override
	public void play() {
		System.out.println("Computer start play ...");
		IEmplacement emplacement = strategieGame.getEmplacementMax();
		strategieGame.getStateGame().play(emplacement.getCol(), emplacement.getRow(), getColor());
		System.out.println("Computer stop play ...");
		raiseEvent(new PlayEvent(player, PlayEvent.APRES, emplacement));
	}
	
	
	@Override
	public void setEnabled(boolean enable) {
		this.enable = enable;
	}

	
	public void setStrategieGame(IStrategy strategieGame) {
		this.strategieGame = strategieGame;
	}

}
