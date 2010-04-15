package fr.alma.go.goban;

import java.util.ArrayList;

public class StonesGroup {

	private ArrayList<Stone> group;

	public StonesGroup() {
		group = new ArrayList<Stone>();
	} // StonesGroup()

	public boolean add(Stone stone) {
		return group.add(stone);
	} // boolean add(Stone)
	
	public boolean remove(Stone stone) {
		return group.remove(stone);
	} // boolean remove(Stone)
	
	public ArrayList<Stone> getGroup(){
		return group;
	}

	public boolean contains(Stone stone) {
		for (Stone innerStone : group) {
			if (innerStone == stone) {
				return true;
			} // if
		} // for
		return false;
	} // boolean contains(stone)

	public int size() {
		return group.size();
	}

}
