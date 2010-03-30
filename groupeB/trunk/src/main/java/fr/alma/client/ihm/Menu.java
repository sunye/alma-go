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

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.alma.client.action.ActionManager;


/**
 * Define the Menu Bar
 */
public class Menu {

	public JMenuBar getMenuBar(ActionManager actionManager) {
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menu = null;
		JMenuItem menuItem = null;
		
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);
		
		menuItem = new JMenuItem(actionManager.getActionNew());
		menu.add(menuItem);
		
		menuItem = new JMenuItem(actionManager.getActionOpen());
		menu.add(menuItem);

		menuItem = new JMenuItem(actionManager.getActionSave());
		menu.add(menuItem);

		menuItem = new JMenuItem(actionManager.getActionSaveAs());
		menu.add(menuItem);

		menuItem = new JMenuItem(actionManager.getActionExit());
		menu.add(menuItem);

		menu = new JMenu("Help");
		menu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(menu);

		menuItem = new JMenuItem(actionManager.getActionAbout());
		menu.add(menuItem);
		return menuBar;
	}
}
