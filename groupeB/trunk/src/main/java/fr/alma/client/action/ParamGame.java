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
 *	 $Licence$	
 *
 */
package fr.alma.client.action;


/**
 * Game parameters
 */
public class ParamGame {
	
	private int timeLimite;
	private boolean possibilityInterruption;
	private int sizeGoban;
	private boolean colorComputer;
	private boolean assistant;
	private int targetCaptureComputer;
	private int targetCapturePlayer;
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
		return possibilityInterruption;
	}
	
	
	/**
	 * @param possibilityInterruption
	 */
	public void setPossibilityInterruption(boolean possibilityInterruption) {
		this.possibilityInterruption = possibilityInterruption;
	}
	
	/**
	 * @return
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
		return targetCaptureComputer;
	}
	
	/**
	 * @param targetCaptureComputer
	 */
	public void setTargetCaptureComputer(int targetCaptureComputer) {
		this.targetCaptureComputer = targetCaptureComputer;
	}
	
	/**
	 * @return limit number of target capture of the player
	 */
	public int getTargetCapturePlayer() {
		return targetCapturePlayer;
	}
	
	/**
	 * @param targetCapturePlayer
	 */
	public void setTargetCapturePlayer(int targetCapturePlayer) {
		this.targetCapturePlayer = targetCapturePlayer;
	}

	/**
	 * @return
	 */
	public boolean getColorComputer() {
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
	public boolean getAssistant(){
		return assistant;
	}

	/**
	 * @param assistant
	 */
	public void setAssistant(boolean assistant) {
		this.assistant = assistant;
		
	}
	
	/**
	 * @return true si the second player opponent is the computer, false if human
	 */
	public boolean getOpponent(){
		return opponent;
	}
	
	/**
	 * @param opponent
	 */
	public void setOpponent(boolean opponent) {
		this.opponent = opponent;
	}
}
