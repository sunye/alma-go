package fr.alma.goban;

public class Color {

	private String value;

	public Color() {
		value = "undef";
	}

	public Color(String val) {
		value = val;
	}

	public void set(String val) {
		value = val;
	}

	public String toString() {
		return value;
	}

}
