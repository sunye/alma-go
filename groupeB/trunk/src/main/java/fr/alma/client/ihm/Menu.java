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
