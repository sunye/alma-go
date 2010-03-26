package fr.alma.client.ihm;

import java.awt.Graphics;
import javax.swing.JPanel;

import fr.alma.server.core.IStateGame;

@SuppressWarnings("serial")
public class Goban extends JPanel {
	
	private IStateGame stateGame;
	
	
	public Goban(IStateGame stateGame) {
		super(null);
		this.stateGame = stateGame; 		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Cadrillage.paintCadrillage(g);
		Pierre.paintPierre(g, stateGame);
	}

}


