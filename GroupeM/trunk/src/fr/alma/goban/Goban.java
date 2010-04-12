package fr.alma.goban;

public class Goban implements Cloneable {

	private Color[][] goban;

	private int wStonesTaken;

	private int bStonesTaken;

	public Goban() {
		goban = new Color[9][9];
		wStonesTaken = 0;
		bStonesTaken = 0;
	} // Goban()

	private boolean isFree(Place pl) {
		int a = pl.getAbs();
		int o = pl.getOrd();
		return goban[a][o] == null;
	} // isFree()

	private boolean isNotSuicide(Place pl, String col) {
		Goban test = null;
		try {
			test = (Goban) this.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return false;
		} // try
		int a = pl.getAbs();
		int o = pl.getOrd();
		test.goban[a][o].set(col);
		int taken;
		if (col.equals("white")) {
			taken = test.wStonesTaken;
		} else {
			taken = test.bStonesTaken;
		}
		if (test.changes()) {
			if (col.equals("white")) {
				return taken<test.wStonesTaken;
			} else {
				return taken<test.bStonesTaken;
			} // if
		} // if
		return true;
	} // isNotSuicide(Place,String)

	private boolean changes() {
		// TODO Auto-generated method stub
		return false;
	} // changes()

	private boolean isPlayable(Place pl, String col) {
		return isFree(pl) && isNotSuicide(pl, col);
	} // isPlayable(Place,String)

	public boolean play(Place pl, String col) {
		if (isPlayable(pl, col)) {
			int a = pl.getAbs();
			int o = pl.getOrd();
			goban[a][o].set(col);
			return true;
		} // if
		return false;
	} // play(Place,String);

}