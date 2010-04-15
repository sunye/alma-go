package fr.alma.go.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import fr.alma.go.Game;

public class GoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private GoPanel goPan;
	
	private Game game;
	
	private JMenuBar barreMenu = new JMenuBar();
	private JMenu jeu = new JMenu("Let's go play go !");
	private JMenu nouv = new JMenu("New game");
	private JMenuItem item1 = new JMenuItem("Human vs CPU");
	private JMenuItem item2 = new JMenuItem("Human vs Human");
	private JMenuItem item3 = new JMenuItem("Alien vs Predator");
	private JMenuItem quitter = new JMenuItem("Leave me alone !");

	public GoFrame() {
		this.setTitle("Atari Go");
		this.setSize(600, 600); // 4;23
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		game=new Game();

		this.setBackground(new Color(105, 155, 105));
		goPan = new GoPanel(game);
		goPan.enableClick();
		this.setContentPane(goPan);
		this.addMouseListener(goPan.getClick());

		quitter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		nouv.add(item1);
		nouv.add(item2);
		nouv.add(item3);
		jeu.add(nouv);
		jeu.addSeparator();
		jeu.add(quitter);
		barreMenu.add(jeu);
		setJMenuBar(barreMenu);

		this.setVisible(true);
	} // GoFrame()

} // class GoFrame
