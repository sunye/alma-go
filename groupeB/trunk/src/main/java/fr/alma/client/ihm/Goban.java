/*
 *   
 *   IA Project ATARI-GO
 *   UNIVERSITY OF NANTES
 *   MASTER ALMA 1
 *   2009 - 2010
 * 	 Version 1.0
 *   
 *   $Date$
 *   $Author$
 * 	 $Revision$
 * 
 *   Copyright (C) 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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
	protected void paintComponent(Graphics graph) {
		super.paintComponent(graph);
		Grid.paintGrid(graph, context);
		StoneRepresentation.paintStone(graph, context);
	}

}


