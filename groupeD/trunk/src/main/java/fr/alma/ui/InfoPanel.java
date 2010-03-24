package fr.alma.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	private JLabel title;
	private JPanel container;
	private JPanel challengeContainer;
	private JLabel challengeTitle;
	private JLabel challengeNb;
	private JPanel blackAndWhiteInfos;
	private JLabel capturedTitle1;
	private JLabel capturedTitle2;
	JLabel labelBlack = new JLabel("NOIR",JLabel.CENTER);
	JLabel labelWhite = new JLabel("BLANC",JLabel.CENTER);
	
	
	
	
	public void buildAll(){
		title = new JLabel("Panneau d'infos");
		
		challengeContainer = new JPanel();
		challengeContainer.setLayout(new BorderLayout());
		challengeTitle = new JLabel("Nombre de captures pour gagner");
		challengeNb = new JLabel("0");
		challengeContainer.add(challengeTitle,BorderLayout.WEST);
		challengeContainer.add(challengeNb,BorderLayout.EAST);
		blackAndWhiteInfos = new JPanel();
		GridLayout gridLayout1 = new GridLayout(4, 2);
		blackAndWhiteInfos.setLayout(gridLayout1);
		capturedTitle1 = new JLabel("Captures");
		capturedTitle2 = new JLabel("Captures");
		//labelBlack.setIcon(new ImageIcon(this.getClass().getResource("/fr/alma/resources/images/black-stone.gif")));
		//labelWhite.setIcon(new ImageIcon(myApp.getClass().getResource("/fr/alma/resources/images/white-stone.gif")));
		blackAndWhiteInfos.add(capturedTitle1);
		blackAndWhiteInfos.add(capturedTitle2);
		
		container = new JPanel();
		container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        
        
        container.add(challengeContainer, BorderLayout.NORTH);
        container.add(blackAndWhiteInfos, BorderLayout.CENTER);
        
        
        //container.add(bouton, BorderLayout.SOUTH);

		
		add(title);
		add(container);
		
	}
	
	
	
	
	
}
