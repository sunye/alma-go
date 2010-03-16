package fr.alma.atarigo;


/**
 * @deprecated
 * test l'atarigo en mode console
 * @author vincent
 *
 */
public class TestAtariGo {

	public static void main(String[] argv) {
		AtariGo obiGo = new AtariGo(9, 9);
		obiGo.jouer(Pion.BLANC, System.in, System.out);
	    }

	}