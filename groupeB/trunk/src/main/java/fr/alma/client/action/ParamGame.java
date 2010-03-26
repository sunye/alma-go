package fr.alma.client.action;


public class ParamGame {
	private short timeLimite;
	private boolean possibilityInterruption;
	private short grille;
	private boolean colorComputer;
	private String roleComputer;
	private short targetCaptureComputer;
	private short targetCapturePlayer;
	
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
	
	public String getRoleComputer() {
		return roleComputer;
	}
	
	public void setRoleComputer(String roleComputer) {
		this.roleComputer = roleComputer;
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
}
