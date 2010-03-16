package fr.alma.ia;

import fr.alma.atarigo.AtariGo;
import fr.alma.atarigo.Pion;

public class TestAtariGo {

	    // +----------------------------------------------------------------------+
	    // | Methode   : main.                                                    |
	    // | Utilite   : point d'entree.                                          |
	    // | Remarques : aucune.                                                  |
	    // +----------------------------------------------------------------------+

	    public static void main(String[] argv) {
		AtariGo gomoku = new AtariGo(9, 9);
		gomoku.jouer(Pion.NOIR, System.in, System.out);
	    }

	}