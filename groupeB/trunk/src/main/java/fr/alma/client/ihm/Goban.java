/*
 * IA Project ATARI-GO
 * UNIVERSITY OF NANTES
 * MASTER ALMA 1
 * 2009 - 2010
 * Version 1.0
 * @author Romain Gournay & Bruno Belin
 * 
 * Copyright 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * Use is subject to license terms.
 */
package fr.alma.client.ihm;

import java.awt.Graphics;

import javax.swing.JPanel;

import fr.alma.client.action.Context;

/**
 * The game area
 */
@SuppressWarnings("serial")
public class Goban extends JPanel {
	
	private Context context = null;
	
	
	public Goban(Context context) {
		super(null);
		this.context = context;
	}
	
	
	/**
	 * Redefine the paint component method to paint the Grid and stones of the goban 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Grid.paintGrid(g, context);
		StoneRepresentation.paintStone(g, context);
	}

}


