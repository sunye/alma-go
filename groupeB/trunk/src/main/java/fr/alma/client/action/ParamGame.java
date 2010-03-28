package fr.alma.client.action;


public class ParamGame {
	private int timeLimite;
	private boolean possibilityInterruption;
	private int grille;
	private boolean colorComputer;
	private boolean assistant;
	private int targetCaptureComputer;
	private int targetCapturePlayer;
	private boolean opponent;
	
	public int getTimeLimite() {
		return timeLimite;
	}
	
	public void setTimeLimite(int timeLimite) {
		this.timeLimite = timeLimite;
	}
	
	public boolean isPossibilityInterruption() {
		return possibilityInterruption;
	}
	
	public void setPossibilityInterruption(boolean possibilityInterruption) {
		this.possibilityInterruption = possibilityInterruption;
	}
	
	public int getGrille() {
		return grille;
	}
	
	public void setGrille(int grille) {
		this.grille = grille;
	}
	
	public int getTargetCaptureComputer() {
		return targetCaptureComputer;
	}
	
	public void setTargetCaptureComputer(int targetCaptureComputer) {
		this.targetCaptureComputer = targetCaptureComputer;
	}
	
	public int getTargetCapturePlayer() {
		return targetCapturePlayer;
	}
	
	public void setTargetCapturePlayer(int targetCapturePlayer) {
		this.targetCapturePlayer = targetCapturePlayer;
	}

	public boolean getColorComputer() {
		return colorComputer;
	}

	public void setColorComputer(boolean colorComputer) {
		this.colorComputer = colorComputer;
	}
	
	public boolean getAssistant(){
		return assistant;
	}

	public void setAssistant(boolean assistant) {
		this.assistant = assistant;
		
	}
	
	public boolean getOpponent(){
		return opponent;
	}
	
	public void setOpponent(boolean opponent) {
		this.opponent = opponent;
	}
}
