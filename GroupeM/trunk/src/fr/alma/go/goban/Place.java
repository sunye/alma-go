package fr.alma.go.goban;

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

	public boolean equals(Place place) {
		return (this.abs == place.abs && this.ord == place.ord);
	}
	
	public String toString(){
		return abs+","+ord;
	}

}
