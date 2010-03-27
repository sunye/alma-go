package fr.alma.common.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Tools {

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
	
	
	public static void message(Component parent, String title, Object message, int msgType) {
		JOptionPane pane = new JOptionPane(message, msgType);
		JDialog dialog = pane.createDialog(parent, title);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
}