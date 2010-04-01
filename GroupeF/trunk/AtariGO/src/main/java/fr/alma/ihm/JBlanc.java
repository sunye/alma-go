package fr.alma.ihm;

import javax.swing.JLabel;

/**
 * Classe qui permet de cloner un Pion blanc.
 * @author lahuidi
 *
 */
public class JBlanc extends JLabel implements Cloneable{
	
	private static final long serialVersionUID = 1L;

	public JBlanc(){
		
		super();
		
	}
	
	/**
	 * Méthode qui permet de cloner un objet Pion noir.
	 * @return Le clone de l'objet.
	 */
	 public Object clone()
     {
          JLabel cl = null;
          
		try {
			
			cl = (JBlanc)super.clone();
						
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
		  
          return cl;
     }


}
