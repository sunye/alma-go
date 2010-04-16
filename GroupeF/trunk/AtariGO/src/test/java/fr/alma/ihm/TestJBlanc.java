package fr.alma.ihm;

import static org.junit.Assert.*;


import org.junit.Test;

public class TestJBlanc {

	@Test
	public void testClone() {
		
		JBlanc label1 = new JBlanc();
		JBlanc label2 = (JBlanc) label1.clone();
						
		assertTrue(label1.getText() == label2.getText());
	}

}
