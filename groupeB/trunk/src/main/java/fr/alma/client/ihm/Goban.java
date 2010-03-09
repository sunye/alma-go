package fr.alma.client.ihm;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;

@SuppressWarnings("serial")
public class Goban extends JPanel {

	public static final short LINE_H = 9;
	public static final short LINE_V = 9;
	
	private IStateGame stateGame;
	private IPlayer player;
	
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

	public void setPlayer(IPlayer player) {
		this.player = player;
	}
}


