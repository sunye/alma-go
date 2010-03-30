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
		setBounds(100, 100, 300, 340);
		
		Context context = new Context();
		context.setMainFrame(this);
		
		setJMenuBar(Factory.getMenuBar(context));
		
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
