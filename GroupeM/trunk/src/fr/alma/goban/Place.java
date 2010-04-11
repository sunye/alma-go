package fr.alma.goban;

public class Place {

	private int abs;
	private int ord;

	public Place(int a, int o) {
		abs = a;
		ord = o;
	}

	public int getAbs() {
		return abs;
	}

	public int getOrd() {
		return ord;
	}

}
