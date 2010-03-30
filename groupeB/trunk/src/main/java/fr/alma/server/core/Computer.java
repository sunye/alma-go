/*
 * IA Project ATARI-GO
 * UNIVERSITY OF NANTES
 * MASTER ALMA 1
 * 2009 - 2010
 * Version 1.0
 * @author Romain Gournay & Bruno Belin
 * 
 * Copyright 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * Use is subject to license terms.
 */
package fr.alma.server.core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import fr.alma.client.action.Context;
import fr.alma.server.ia.IEvaluation;


/**
 * Define the computer
 */
public class Computer extends AbstractPlayer {
	private IEvaluation evaluation;
	private IStrategy strategy;
	private boolean enable = false;
	private IPlayer player = this;
	private Timer timer = null;
	private ActionListener timerAction = null;
	private Context context = null;
	
	public Computer(String name, Context context) {
		super(name, context.getParamGame().getColorComputer());
		this.context = context;
	}
	
	
	/** 
	 * Thread is builded to execute the different action for the calculation
	 * @see fr.alma.server.core.IPlayer#play()
	 */
	@Override
	public void play() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				context.getStatusBar().setText("Computer is playing");
				startTimer();
				ILocation location = getStrategy().getBestLocation(getEvaluation(), false);
				stopTimer();
				if ((location != null)) {
					try {
						strategy.getStateGame().play(location.getCol(), location.getRow(), getColor());
						context.getStatusBar().setText("Last position : " + location);
					} catch (Exception e) {
						System.out.println("Computer - Internal error : " + e.getLocalizedMessage());
					}
				} else {
					context.getStatusBar().setText("");
				}
				raiseEvent(new PlayEvent(player, PlayEvent.AFTER, location));
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
		if (context.getParamGame().getTimeLimite() > 0) {
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
		return context.getParamGame().getTimeLimite() * 1000;
	}

}
