package test.java.alexanddam;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import main.java.alexanddam.logic.FonctionEvaluation;

import org.junit.Test;

/** This is the test class of the project
* @author Alexandru Schiaucu
* @version 1.0
* @see org.junit.Assert
* @since 15 april 2010
*/
public class TestProject {

	@Test
	public void main() {
		
		int aux1, aux2, aux3, ev1, ev2;
		FonctionEvaluation.jeuNeu();
		
		// on teste differents configurations du goban
		FonctionEvaluation.setGobanValue(4, 4, 1);			// l'ordinateur commence
		FonctionEvaluation.setGobanValue(3, 3, 2);
		FonctionEvaluation.setGobanValue(3, 4, 1);
		FonctionEvaluation.setGobanValue(3, 5, 2);
		FonctionEvaluation.setGobanValue(2, 5, 1);
		FonctionEvaluation.setGobanValue(4, 5, 2);
		FonctionEvaluation.setGobanValue(4, 3, 1);
		FonctionEvaluation.setGobanValue(4, 2, 2);
		
		/*
		 * disposition du goban
		 * 
		 * 		-	-	-	-	-	-	-	-	-
		 * 		-	-	-	-	-	-	-	-	- 
		 * 		-	-	-	-	-	O	-	-	- 
		 * 		-	-	-	H	O	H	-	-	-  
		 * 		-	-	H	O	O	H	-	-	- 
		 * 		-	-	-	-	-	-	-	-	- 
		 * 		-	-	-	-	-	-	-	-	- 
		 * 		-	-	-	-	-	-	-	-	- 
		 * 		-	-	-	-	-	-	-	-	- 
		 * 
		 */

		/********************************************************************************************************/
		// les tests
		boolean troisLibertes1 = true;
		FonctionEvaluation.evaluation();
		ev1 = FonctionEvaluation.getPoints();
		aux1 = FonctionEvaluation.getLibertesValue(3, 5);
		aux2 = FonctionEvaluation.getLibertesValue(4, 5);
		
		if(aux1 != 3 || aux2 !=3){
			troisLibertes1 = false;
		}
		
		assertTrue(troisLibertes1);
		System.out.println("Test libertes egales meme chaine de pierre .. ok");

		/********************************************************************************************************/
		aux1 = FonctionEvaluation.getLibertesValue(4, 4);
		aux2 = FonctionEvaluation.getLibertesValue(3, 4);
		aux3 = FonctionEvaluation.getLibertesValue(4, 3);
		boolean libDiff = false;
		if(aux1 != aux2 || aux2 != aux3){
			libDiff = true;
		}
		
		assertFalse(libDiff);
		System.out.println("Test libertes egales meme chaine de pierre no. 2 .. ok");
		
		
		/********************************************************************************************************/
		ev2 = FonctionEvaluation.evaluationInc(new int[] {3, 2}, new int[0]);
		boolean bon_eval1 = true;
		if(ev2 < ev1){
			bon_eval1 = false;
		}
		assertTrue(bon_eval1);
		System.out.println("Test bon evaluation pour un possible coup prochain .. ok");

		/********************************************************************************************************/
		ev2 = FonctionEvaluation.evaluationInc(new int[] {3, 6}, new int[] {5, 4});
		boolean bon_eval2 = true;
		if(ev2 > ev1){
			bon_eval2 = false;
		}
		assertTrue(bon_eval2);
		System.out.println("Test bon evaluation pour un possible coup prochain no. 2 .. ok");
	}
}