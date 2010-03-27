package fr.alma.client.action;


public class ParamGame {
	private short timeLimite;
	private boolean possibilityInterruption;
	private short grille;
	private boolean colorComputer;
	private boolean assistant;
	private short targetCaptureComputer;
	private short targetCapturePlayer;
	private boolean opponent;
	
	public short getTimeLimite() {
		return timeLimite;
	}
	
	public void setTimeLimite(short timeLimite) {
		this.timeLimite = timeLimite;
	}
	
	public boolean isPossibilityInterruption() {
		return possibilityInterruption;
	}
	
	public void setPossibilityInterruption(boolean possibilityInterruption) {
		this.possibilityInterruption = possibilityInterruption;
	}
	
	public short getGrille() {
		return grille;
	}
	
	public void setGrille(short grille) {
		this.grille = grille;
	}
	
	public short getTargetCaptureComputer() {
		return targetCaptureComputer;
	}
	
	public void setTargetCaptureComputer(short targetCaptureComputer) {
		this.targetCaptureComputer = targetCaptureComputer;
	}
	
	public short getTargetCapturePlayer() {
		return targetCapturePlayer;
	}
	
	public void setTargetCapturePlayer(short targetCapturePlayer) {
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
