package fr.alma.client.ihm;

import java.awt.Graphics;
import javax.swing.JPanel;

import fr.alma.client.action.Context;

@SuppressWarnings("serial")
public class Goban extends JPanel {
	
	private Context context;
	
	
	public Goban(Context context) {
		super(null);
		this.context = context; 		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Cadrillage.paintCadrillage(g, context);
		Pierre.paintPierre(g, context);
	}

}


