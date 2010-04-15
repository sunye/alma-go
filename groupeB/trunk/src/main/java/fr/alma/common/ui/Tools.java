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
package fr.alma.common.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


/**
 * Graphical tools
 */
public class Tools {

	/** Focus Listener for all text fields */
	private static FocusListener focusListener;
	
	
	public static void center(Window win) {
		int x = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - win.getWidth()) / 2;
		int y = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - win.getHeight()) / 2;

		win.setLocation(x, y);
	}
	
	
	public static void setFixedSize(JComponent comp, int x, int y) {
		comp.setPreferredSize(new Dimension(x, y));
		comp.setMinimumSize(new Dimension(x, y));
		comp.setMaximumSize(new Dimension(x, y));
	}
	
	
	/**
	 * Manage the focus on all text fields
	 * @param tf
	 */
	public static void addFocusListener(JTextField tf) {
		if (focusListener == null) {
			
			focusListener = new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					((JTextField)e.getComponent()).selectAll();
				}
			};	
		}
		tf.addFocusListener(focusListener);
	}
	
	
	/**
	 * Show a box message (Information, error, warning, ...)
	 * @param parent
	 * @param title
	 * @param message
	 * @param msgType
	 */
	public static void message(Component parent, String title, Object message, int msgType) {
		JOptionPane pane = new JOptionPane(message, msgType);
		final JDialog dialog = pane.createDialog(parent, title);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				dialog.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(runnable);
	}
	
	/**
	 * Cette methode charge une image qui se situe relativement
	 * à une classe donnée.
	 */	
	public static BufferedImage getImage(Class cls, String filename) {
		try {
			return ImageIO.read(cls.getResourceAsStream(filename));
		}
		catch (Throwable e) {
			return null;
		}
	}
	
}
