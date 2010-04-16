package fr.alma.ihm;

import javax.swing.JLabel;

/**
 * Class which permits to clone black pawn .
 * @author ZERBITA Mohamed El Hadi
 *
 */
public class JNoir extends JLabel implements Cloneable {

		private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor of the class
	 */
	public JNoir(){
		
		super();
		
	}
	
	/**
	 * Class which permits to clone a black pawn..
	 * @return the object cloned.
	 */
	 public Object clone()
     {
          JLabel cl = null;
          
		try {
			
			cl = (JNoir)super.clone();
						
		} catch (CloneNotSupportedException e) {
			
			e.printStackTrace();
		}
          
		  
          return cl;
     }


}
	
	


