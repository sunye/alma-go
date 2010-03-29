package fr.alma.server.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import fr.alma.server.ia.IEvaluation;


public class Computer extends AbstractPlayer {
	private IEvaluation evaluation;
	private IStrategy strategy;
	private boolean enable = false;
	private IPlayer player = this;
	private Timer timer = null;
	private ActionListener timerAction = null;
	private int timeLimite = 0;
	
	
	public Computer(String name, boolean color, int timeLimite) {
		super(name, color);
		this.timeLimite = timeLimite;
	}
	
	
	@Override
	public void play() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				System.out.println("Start thread computer");
				startTimer();
				IEmplacement emplacement = getStrategy().getEmplacementMax(getEvaluation(), false);
				stopTimer();
				if ((emplacement.getCol() != -1) && (emplacement.getRow() != -1)) {
					try {
						strategy.getStateGame().play(emplacement.getCol(), emplacement.getRow(), getColor());
					} catch (Exception e) {
						System.out.println("Computer - Internal error : " + e.getLocalizedMessage());
					}
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

	
	@Override
	public void cleanUp() {
		evaluation = null;
		strategy = null;
		player = null;
		timer.stop();
		timer = null;
		timerAction = null;
	}

	
	@Override
	public void interrupt() {
		strategy.interrupt();
	}
	
	
	private void startTimer() {
		if (timeLimite > 0) {
			if (timer == null) {
				timer = new Timer(getDelay(), getActionTimer());
				timer.setRepeats(false);
			}
			timer.start();
		}
	}
	
	
	private void stopTimer() {
		if (timer != null) {
			timer.stop();
		}
	}
	
	
	private ActionListener getActionTimer() {
		if (timerAction == null) {
			timerAction = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("Time exceeded : interruption of the calculation ....");
					interrupt();
				}
			};
		}
		return timerAction;
	}
	
	
	public int getDelay() {
		return timeLimite * 1000;
	}

}
