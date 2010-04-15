package fr.alma.go.goban;

public class Stone {

	private char color; // u : undefined ; w : white ; b : black

	public Stone() {
		color = 'u'; // undefined by default
	} // Stone()

	public Stone(char col) {
		color = col;
	} // Stone(char)

	public void setColor(char col) {
		color = col;
	} // void setColor(char)

	public char getColor() {
		return color;
	} // char getColor()

	public boolean isUndefined() {
		return color=='u';
	}

} // class Stone
