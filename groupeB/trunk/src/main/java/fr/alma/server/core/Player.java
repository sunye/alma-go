package fr.alma.server.core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import fr.alma.client.ihm.Goban;

public class Player extends AbstractPlayer {

	Goban goban = null;
	IStateGame stateGame; 
	MouseListener listener = null;
	List<PlayListener> listPlayerActionListener = null;
	boolean enable = false;
	
	
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

					if (stateGame.play(x, y, getColor())) {
						goban.repaint();
						raiseEvent(new PlayEvent());
					}
				}
			};
			goban.addMouseListener(listener);
		}
	}
	
	
	public boolean addPlayListener(PlayListener playListener) {
		return getPlayerListeners().add(playListener);
	}
	
	
	public boolean removePlayListener(PlayListener playListener) {
		return getPlayerListeners().remove(playListener);
	}

	
	public List<PlayListener> getPlayerListeners() {
		if (listPlayerActionListener == null) {
			listPlayerActionListener = new Vector<PlayListener>();
		}
		return listPlayerActionListener;
	}
	
	
	private boolean raiseEvent(PlayEvent event) {
		boolean retour = true;
		for (PlayListener listener : getPlayerListeners()) {
			if (!listener.actionPerformed(event)) {
				retour = false;
			}
		}
		return retour;
	}
	
	@Override
	public void setEnabled(boolean enable) {
		this.enable = enable;
	}
	
}

