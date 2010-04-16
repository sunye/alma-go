package TestIa;

import static org.junit.Assert.*;
import static fr.alma.ia.Ia.fonctionEvaluation;

import org.junit.Test;

import fr.alma.jeu.Grille;

public class TestIa {

	@Test
	public void testFonctionEvaluation() {
		assertTrue(fonctionEvaluation(new Grille())==0);
	}

}
