package fr.alma.ihm;

import javax.swing.JLabel;

/**
 * Classe qui permet de cloner un Pion noir.
 * @author lahuidi
 *
 */
public class JNoir extends JLabel implements Cloneable {

		private static final long serialVersionUID = 1L;
	
	
	public JNoir(){
		
		super();
		
	}
	
	/**
	 * Méthode qui permet de cloner un objet Pion blanc.
	 * @return Le clone de l'objet.
	 */
	 public Object clone()
     {
          JLabel cl = null;
          
		try {
			
			cl = (JNoir)super.clone();
						
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
		  
          return cl;
     }


}
	
	


