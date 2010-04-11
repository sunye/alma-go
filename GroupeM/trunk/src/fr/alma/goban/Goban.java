package fr.alma.goban;

public class Goban {

	private Color[][] goban;

	public Goban() {
		goban = new Color[9][9];
	}

	private boolean isFree(Place pl) {
		int a = pl.getAbs();
		int o = pl.getOrd();
		return goban[a][o] == null;
	}

	private boolean isNotSuicide(Place pl) {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isPlayable(Place pl) {
		return isFree(pl) && isNotSuicide(pl);
	}

	public boolean play(Place pl, String col) {
		if (isPlayable(pl)) {
			int a = pl.getAbs();
			int o = pl.getOrd();
			goban[a][o].set(col);
			return true;
		}
		return false;
	}

}