package fr.alma.ihm;

import static org.junit.Assert.*;


import org.junit.Test;

	public class TestJNoir {

		@Test
		public void testClone() {
			
			JNoir label1 = new JNoir();
			JNoir label2 = (JNoir) label1.clone();
							
			assertTrue(label1.getText() == label2.getText());
		}
}

