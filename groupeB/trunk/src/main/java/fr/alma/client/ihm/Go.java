package fr.alma.client.ihm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.alma.client.action.Context;
import fr.alma.client.action.IAction;
import fr.alma.common.ui.Tools;
import fr.alma.server.core.Factory;


@SuppressWarnings("serial")
public class Go extends JFrame {

	private Goban goban;
	private KeyListener keyListener = null;
	private IAction actionEscape = null;

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
		
		//GameLoader gl = new GameLoader();
		//gl.load("TestCaptureSuicidaire.txt", context);
		
	}


	public Container createContentPane() {
		//Create the content-pane-to-be.
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setOpaque(true);

		//Add the goban to the content pane.
		contentPane.add(goban, BorderLayout.CENTER);

		return contentPane;
	}
	
	
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
