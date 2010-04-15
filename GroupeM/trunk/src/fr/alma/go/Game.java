package fr.alma.go;

import fr.alma.go.goban.Goban;
import fr.alma.go.goban.Place;
import fr.alma.go.heuristics.Computer;
import fr.alma.go.ui.GoPanel;

public class Game {

	private Goban goban;

	private char turn;

	private boolean CPUTurn;

	public Game() {
		goban = new Goban();
		turn = 'b';
	}

	public void initHumanCPU() {
		new Thread() {
			public void run() {
				Computer cpu = new Computer(false, 1);
				CPUTurn = false;
				while (!goban.gameOver()) {
					if (CPUTurn) {
						Place place = cpu.getPlace(goban);
						goban.play(place.getAbs(), place.getOrd(), turn);
						// ACTUALISER GoFrame
						CPUTurn = false;
						switchTurn();
					} // if
				} // while
			} // void run()
		};
	} // void initHumanCPU

	public boolean play(int abs, int ord) {
		boolean ok = goban.play(abs, ord, turn);
		if (ok) {
			// ACTUALISER GoFrame
			this.switchTurn();
			return true;
		}
		return false;
	}

	private void switchTurn() {
		if (turn == 'b') {
			turn = 'w';
		} else {
			turn = 'w';
		} // if
	}

}
