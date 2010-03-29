package fr.alma.server.core;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.alma.client.ihm.Goban;
import fr.alma.server.ia.IEvaluation;

public class Player extends AbstractPlayer {

	private Goban goban = null;
	private IStateGame stateGame; 
	private MouseListener listener = null;
	private boolean enabled = false;
	private IPlayer player = this;
	
	
	public Player(String name, boolean color, Goban goban, IStateGame stateGame) {
		super(name, color);
		this.goban = goban;
		this.stateGame = stateGame;
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
					int x = (int)Math.round((e.getX()-25.0)/30.0);
					int y = (int)Math.round((e.getY()-25.0)/30.0);

					if (raiseEvent(new PlayEvent(player, PlayEvent.BEFORE, new Emplacement(x, y)))) {
						try {
							if (stateGame.play(x, y, getColor())) {
								raiseEvent(new PlayEvent(player, PlayEvent.AFTER, new Emplacement(x, y)));
							}							
						} catch (Exception e2) {
							System.out.println("Player - Internal error : " + e2.getLocalizedMessage());
						}

					}
				}
			};
			goban.addMouseListener(listener);
		}
	}
	
	
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	
	public String toString() {
		return getName();
	}
	
	
	@Override
	public void setStrategy(IStrategy strategieGame) {
	}

	@Override
	public void setEvaluation(IEvaluation evaluation) {
	}

	public boolean isEnable() {
		return enabled;
	}

	@Override
	public void cleanUp() {
		if (listener != null) {
			goban.removeMouseListener(listener);
			listener = null;
		}
		goban = null;
		stateGame = null;
		listener = null;
		player = null;		
	}

	@Override
	public void interrupt() {
	}
	
}

