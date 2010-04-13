package fr.alma.go.heuristics;

import fr.alma.go.goban.Place;

public class GameNode {

	private Place place;

	private int note;
	
	public GameNode(){
		note=-1;
	}

	public void setCoords(int a, int o) {
		place = new Place(a, o);
	} // void setCoords(int,int)

	public void setPlace(Place pl) {
		place = pl;
	} // void setCoords(int,int)

	public void setNote(int n) {
		note = n;
	} // void setNote(int)

	public Place getPlace() {
		return place;
	} // int getAbs()

	public int getNote() {
		return note;
	} // int getNote()

} // class GameNode
