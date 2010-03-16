package fr.alma.server.core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.alma.client.ihm.Goban;

public class Player extends AbstractPlayer {

	Goban goban = null;
	IStateGame stateGame; 
	MouseListener listener = null;
	boolean enable = false;
	IPlayer player = this; 
	
	
	public Player(String name, boolean color, Goban goban, IStateGame stateGame) {
		super(name, color);
		this.goban = goban;
		this.stateGame = stateGame;
		play();
	}

	@Override
	/**
	 * A appeler une seule fois pour le joueur
	 */
	public void play() {
		if (listener == null) {	
			listener = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					short x = (short)Math.round((e.getX()-25.0)/30.0);
					short y = (short)Math.round((e.getY()-25.0)/30.0);

					if (raiseEvent(new PlayEvent(player, PlayEvent.AVANT, new Emplacement(x, y)))) {
					
						if (stateGame.play(x, y, getColor())) {
							//goban.repaint();
							raiseEvent(new PlayEvent(player, PlayEvent.APRES, new Emplacement(x, y)));
						}
					}
				}
			};
			goban.addMouseListener(listener);
		}
	}
	
	
	@Override
	public void setEnabled(boolean enable) {
		this.enable = enable;
	}
	
	
	public String toString() {
		return getName();
	}
	
}

