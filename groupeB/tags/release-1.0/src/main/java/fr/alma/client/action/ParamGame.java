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
package fr.alma.client.action;


/**
 * Game parameters
 */
public class ParamGame {
	
	private int timeLimite;
	private boolean possiblyToStop;
	private int sizeGoban;
	private boolean colorComputer;
	private boolean assistant;
	private int maxCatchComput;
	private int maxCatchPlayer;
	private boolean opponent;
	
	
	/**
	 * @return Calculation time limit for the computer
	 */
	public int getTimeLimite() {
		return timeLimite;
	}
	
	
	/**
	 * @param timeLimite
	 */
	public void setTimeLimite(int timeLimite) {
		this.timeLimite = timeLimite;
	}
	
	
	/**
	 * @return true if the user can interrupt the computer calculation
	 */
	public boolean isPossibilityInterruption() {
		return possiblyToStop;
	}
	
	
	/**
	 * @param possiblyToStop
	 */
	public void setPossibilityInterruption(boolean possiblyToStop) {
		this.possiblyToStop = possiblyToStop;
	}
	
	/**
	 * @return  the size of the goban
	 */
	public int getSizeGoban() {
		return sizeGoban;
	}
	
	/**
	 * @param sizeGoban
	 */
	public void setSizeGoban(int sizeGoban) {
		this.sizeGoban = sizeGoban;
	}
	
	/**
	 * @return limit number of target capture of the computer
	 */
	public int getTargetCaptureComputer() {
		return maxCatchComput;
	}
	
	/**
	 * @param maxCatchComput
	 */
	public void setTargetCaptureComputer(int maxCatchComput) {
		this.maxCatchComput = maxCatchComput;
	}
	
	/**
	 * @return limit number of target capture of the player
	 */
	public int getTargetCapturePlayer() {
		return maxCatchPlayer;
	}
	
	/**
	 * @param maxCatchPlayer
	 */
	public void setTargetCapturePlayer(int maxCatchPlayer) {
		this.maxCatchPlayer = maxCatchPlayer;
	}

	/**
	 * @return true if the color is the computer
	 */
	public boolean isColorComputer() {
		return colorComputer;
	}

	/**
	 * @param colorComputer
	 */
	public void setColorComputer(boolean colorComputer) {
		this.colorComputer = colorComputer;
	}
	
	/**
	 * @return true if assistant mode is enabled
	 */
	public boolean isAssistant(){
		return assistant;
	}

	/**
	 * @param assistant
	 */
	public void setAssistant(boolean assistant) {
		this.assistant = assistant;
		
	}
	
	/**
	 * @return true if the second player opponent is the computer, false if human
	 */
	public boolean isOpponent(){
		return opponent;
	}
	
	/**
	 * @param opponent
	 */
	public void setOpponent(boolean opponent) {
		this.opponent = opponent;
	}
}
