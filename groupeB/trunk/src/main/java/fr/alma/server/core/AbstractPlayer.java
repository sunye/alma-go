package fr.alma.server.core;

public abstract class AbstractPlayer implements IPlayer {
	
	private boolean color;
	private String name;
	
	public AbstractPlayer(String name, boolean color) {
		this.name = name;
		this.color = color;
	}
	
	public String getName() {
		return name;
	}

	public Boolean getColor() {
		return color;
	};
}
