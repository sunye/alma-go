/*
 *   
 *   IA Project ATARI-GO
 *   UNIVERSITY OF NANTES
 *   MASTER ALMA 1
 *   2009 - 2010
 * 	 Version 1.0
 *   
 *   $Date$
 *   $Author$
 * 	 $Revision$
 * 
 *   Copyright (C) 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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

	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.IPlayer#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enable) {
		this.enable = enable;
	}


	public boolean isEnabled() {
		return enable;
	}

	/**
	 * @return the strategy adopted
	 */
	public IStrategy getStrategy() {
		return strategy;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.IPlayer#setStrategy(fr.alma.server.core.IStrategy)
	 */
	@Override
	public void setStrategy(IStrategy strategieGame) {
		this.strategy = strategieGame;
	}


	public IEvaluation getEvaluation() {
		return evaluation;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.alma.server.core.IPlayer#setEvaluation(fr.alma.server.ia.IEvaluation)
	 */
	@Override
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

	/**
	 * use to start the computer calculation
	 */
	private void startTimer() {
		if (context.getParamGame().getTimeLimite() > 0) {
			if (timer == null) {
				timer = new Timer(getDelay(), getActionTimer());
				timer.setRepeats(false);
			}
			timer.start();
		}
	}

	/**
	 * use to stop the computer calculation
	 */
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

	/**
	 * use for the time calculation
	 * @return the time in seconds
	 */
	public int getDelay() {
		return context.getParamGame().getTimeLimite() * 1000;
	}

}
