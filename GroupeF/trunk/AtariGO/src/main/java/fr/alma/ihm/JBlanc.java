package fr.alma.ihm;

import javax.swing.JLabel;

/**
 * Class which permits to clone a white pawn .
 * @author ZERBITA Mohamed El Hadi
 *
 */
public class JBlanc extends JLabel implements Cloneable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the class
	 */
	public JBlanc(){
		super();
	}
	
	/**
	 * Method which permits to clone a white pawn.
	 * @return the object cloned.
	 */
	 public Object clone()
     {
          JLabel cl = null;
          
		try {
			cl = (JBlanc)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
        return cl;
     }


}
