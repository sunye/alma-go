package fr.alma.go;

import fr.alma.go.goban.Goban;
import fr.alma.go.interfaces.IPlayer;
import fr.alma.go.player.Computer;
import fr.alma.go.player.Human;

public class Game {
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Goban goban = new Goban();
		IPlayer black=new Human('b');
		IPlayer white=new Computer('w',1);
		boolean turn = true; // true for black turn, false for white turn
		while (true) {
			if (turn) {
				int place = black.getPlace();
				int abs = place / 10;
				int ord = place % 10;
				goban.play(abs, ord, 'b');
			} else {
				int place = white.getPlace();
				int abs = place / 10;
				int ord = place % 10;
				goban.play(abs, ord, 'w');
			} // if
			if (goban.gameOver()) {
				if (turn) {
					// TODO black wins
				} else {
					// TODO white wins
				} // if
				break;
			}
			turn = !turn;
		} // while

	} // static void main(String[])
} // class Game
