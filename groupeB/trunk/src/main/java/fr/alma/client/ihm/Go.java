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


import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fr.alma.client.action.Context;
import fr.alma.client.action.IAction;
import fr.alma.common.ui.Tools;
import fr.alma.server.core.Factory;


/**
 * Launcher Class.
 */
@SuppressWarnings("serial")
public class Go extends JFrame {

	private KeyListener keyListener = null;
	private IAction actionEscape = null;

	
	/**
	 * Constructor of the game - IHM
	 */
	public Go() {
		super("Go Game");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 360);
		
		Context context = new Context();
		context.setMainFrame(this);
		context.setStatusBar(Factory.getStatutBarre());
			
		setJMenuBar(Factory.getMenuBar(context));
		add(context.getStatusBar(), BorderLayout.SOUTH);
		
		actionEscape = Factory.getActionEscape(context);
		addKeyListener(getKeyListener());
		
		Tools.center(this);
		setVisible(true);
	}
	
	
	/**
	 * Define the key which interrupt the computer calculation
	 * @return The listener match with the key
	 */
	private KeyListener getKeyListener() {
		if (keyListener == null) {
			keyListener = new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						actionEscape.run();
					}	
				}
			};
		}
		return keyListener;
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Go();	
			}				
		});
	}

}
