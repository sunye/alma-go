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
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Start thread computer");
				IEmplacement emplacement = strategieGame.getEmplacementMax();
				try {
					strategieGame.getStateGame().play(emplacement.getCol(), emplacement.getRow(), getColor());
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
	
	
	public void setStrategieGame(IStrategy strategieGame) {
		this.strategieGame = strategieGame;
	}

}
