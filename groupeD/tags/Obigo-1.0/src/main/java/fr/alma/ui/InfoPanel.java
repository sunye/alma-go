package fr.alma.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

	private JLabel title;
	private JLabel challengeTitle;
	private JLabel challengeNb;
	private JPanel blackAndWhiteInfos;
	private JLabel capturedBlack;
	private JLabel capturedWhite;
	private JLabel difficultyTitle;
	private JLabel dif1;
	private JLabel dif2;
	
	
	
	
	public void buildAll(){
		
		title = new JLabel("Panneau d'infos");
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gBC = new GridBagConstraints();
        gBC.fill = GridBagConstraints.HORIZONTAL;
		// title "Capture pour gagner"
		challengeTitle = new JLabel("Captures pour gagner");
		gBC.ipady = 40;
		gBC.weightx = 0.5;
        gBC.gridx = 0;
        gBC.gridy = 0;
        add(challengeTitle, gBC);
        
        // left captures to win for player1
		challengeNb = new JLabel("0");
        gBC.gridx = 2;
        gBC.gridy = 0;
        add(challengeNb, gBC);
        
		// title "vies restantes"
		challengeTitle = new JLabel("vies restantes");
		challengeTitle.setHorizontalTextPosition(JLabel.RIGHT);
		gBC.ipady = 20;
		gBC.weightx = 0.5;
		gBC.gridwidth = 4;
        gBC.gridx = 0;
        gBC.gridy = 1;
        gBC.fill = gBC.CENTER;
        add(challengeTitle, gBC);

        capturedBlack = new JLabel("0");
        capturedBlack.setIcon(new ImageIcon(ImageIcon.class.getClassLoader().getSystemResource("images/black-stone.gif")));
        gBC.gridx = 0;
        gBC.gridy = 2;
        gBC.fill = gBC.HORIZONTAL;
        add(capturedBlack, gBC);
        
		capturedWhite = new JLabel("0");
		capturedWhite.setIcon(new ImageIcon(ImageIcon.class.getClassLoader().getSystemResource("images/white-stone.gif")));
		gBC.gridx = 2;
        gBC.gridy = 2;
        gBC.ipady = 40;
        add(capturedWhite, gBC);
        
        
		// title "Niveau du joueur"
        dif1 = new JLabel();
		gBC.weightx = 0.5;
        gBC.gridx = 0;
        gBC.gridy = 3;
        add(dif1, gBC);
        
        // left captures to win for player2
        dif2 = new JLabel();
        gBC.gridx = 2;
        gBC.gridy = 3;
        add(dif2, gBC);
        
	}
	
	public void setChallenge(int n){
		challengeNb.setText(String.valueOf(n));
	}
	
	public void setBlackLife(int n){
		capturedBlack.setText(String.valueOf(n));
	}
	
	public void setWhiteLife(int n){
		capturedWhite.setText(String.valueOf(n));
	}
	
	public void setBlackDif(int n){
		dif1.setText("dif : "+String.valueOf(n));
	}
	
	public void rmBlackDif(){
		dif1.setText("");
	}
	
	
	public void setWhiteDif(int n){
		dif2.setText("dif : "+String.valueOf(n));
	}
	
	public void rmWhiteDif(){
		dif2.setText("");
	}
	
	
	
	
	
}
