package fr.alma.ui;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import fr.alma.atarigo.Player;
import fr.alma.atarigo.HumanPlayer;
import fr.alma.atarigo.IAPlayer;
import fr.alma.atarigo.Stone;

/**
 * Dialogbox for the parameters of a new game.
 * @author vincent
 *
 */
public class NewGameDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private MyApplication myApp;

	public NewGameDialog(JFrame parent, String title, boolean modal,MyApplication myApp){
		super(parent, title, modal);
		this.myApp = myApp;
		this.setSize(250, 320);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.initComponent();
	}
	JPanel jpan0 = new JPanel();
	JPanel jpan1 = new JPanel();
	GridLayout gridLayout1 = new GridLayout(5, 2);
	JLabel labelChallenge = new JLabel("Prises min");
	JLabel labelBlack = new JLabel("NOIR",JLabel.CENTER);
	JLabel labelWhite = new JLabel("BLANC",JLabel.CENTER);
	JComboBox combo1 = new JComboBox();
	JComboBox combo2 = new JComboBox();
	JButton goButton = new JButton("Go !");
    JButton quitButton = new JButton("Quitter");
    SpinnerNumberModel model1 = new SpinnerNumberModel(1,1,10,1);
    SpinnerNumberModel dif1 = new SpinnerNumberModel(1,1,4,1);
    SpinnerNumberModel dif2 = new SpinnerNumberModel(1,1,4,1);
    JSpinner spinnerDif1 = new JSpinner(dif1);
    JSpinner spinnerDif2 = new JSpinner(dif2);
    JSpinner spinner1 = new JSpinner(model1);

	/**
	 * initialise the different components of the dialog box.
	 *
	 */
	public void initComponent(){
		jpan1.setLayout(gridLayout1);
		labelBlack.setVerticalTextPosition(JLabel.NORTH);
		labelBlack.setHorizontalTextPosition(JLabel.CENTER);
		labelBlack.setIcon(new ImageIcon(ImageIcon.class.getClassLoader().getSystemResource("resources/images/black-stone.gif")));
		labelWhite.setIcon(new ImageIcon(ImageIcon.class.getClassLoader().getSystemResource("resources/images/white-stone.gif")));
		labelWhite.setVerticalTextPosition(JLabel.NORTH);
		labelWhite.setHorizontalTextPosition(JLabel.CENTER);
		combo1.addItem("Ordinateur");
		combo1.addItem("Humain");
        combo2.addItem("Ordinateur");
        combo2.addItem("Humain");
        combo1.addActionListener(new StratListener());
        combo2.addActionListener(new StratListener());
        jpan1.add(labelChallenge);
        jpan1.add(spinner1);
		jpan1.add(labelBlack);
		jpan1.add(labelWhite);
		jpan1.add(combo1);
		jpan1.add(combo2);
		jpan1.add(spinnerDif1);
		jpan1.add(spinnerDif2);
		jpan1.add(goButton);
		jpan1.add(quitButton);
		goButton.addActionListener(new GoListener());             
		quitButton.addActionListener(new QuitListener());
		this.getContentPane().add(jpan1);
	}
	
	public class StratListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JComboBox cb = (JComboBox)e.getSource();
			System.out.println("eloo");
	        if(cb.getSelectedItem().equals("Humain")){
	        	if(cb.equals(combo1)){
	        		jpan1.remove(spinnerDif1);
	        	}
	        	else{
	        		jpan1.remove(spinnerDif2);
	        	}
	        }
	        else{
	        	if(cb.equals(combo1)){
	        		jpan1.add(spinnerDif1);
	        		System.out.println("ajout");
	        	}
	        	else{
	        		jpan1.add(spinnerDif2);
	        	}
	        }

		}
		
	}
	
	/**
     * listener of the go button starting the game
     */
    public class GoListener implements ActionListener{

            /**
             * Redéfinitions de la méthode actionPerformed
             */
            public void actionPerformed(ActionEvent arg0) {
           	 	Thread t = new Thread() {
        		public void run() {   	
            	//test options then run the game
            	JOptionPane jop = new JOptionPane();			
        		if(!myApp.atarigo.isOver()){
	            	int option = jop.showConfirmDialog(null, "Voulez-vous vraiment faire une nouvelle partie ? Votre partie sera définitivement perdue", "Nouvelle partie", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	        					
	        		if(option == JOptionPane.OK_OPTION)
	        		{
	        			try {
							startGame();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
	        		else{
	        			setVisible(false);
	        		}
        		}
        		else{
        			try {
						startGame();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		
       		 }
                };
                t.start();
            }
            
    }
    
    /**
     * save the parameters of the new game
     * and call the methode start of the class {MyApplication}
     * @throws InterruptedException
     */
    public void startGame() throws InterruptedException {
    	Player player1;
    	Player player2;
    	if(combo1.getSelectedItem().toString().equals("Humain")){
    		player1 = new HumanPlayer(Stone.BLACK,"bob");
    		myApp.panInfos.rmBlackDif();
    	}
    	else{
    		int difIA1 = dif1.getNumber().intValue();
    		player1 = new IAPlayer(Stone.BLACK,"bob",difIA1);
    		myApp.panInfos.setBlackDif(difIA1);
    	}
    	if(combo2.getSelectedItem().toString().equals("Humain")){
    		player2 = new HumanPlayer(Stone.WHITE,"obi");
    		myApp.panInfos.rmWhiteDif();
    	}
    	else{
    		int difIA2 = dif2.getNumber().intValue();
    		player2 = new IAPlayer(Stone.WHITE,"obi",difIA2);
    		myApp.panInfos.setWhiteDif(difIA2);
    	}
    	myApp.panInfos.setChallenge(model1.getNumber().intValue());
    	myApp.panInfos.setBlackLife(model1.getNumber().intValue());
    	myApp.panInfos.setWhiteLife(model1.getNumber().intValue());    	
    	myApp.startGame(player1,player2,model1.getNumber().intValue());
    	setVisible(false);
    	
    }
    
    /**
     * class listening the button quit
     */
    public class QuitListener implements ActionListener{

            /**
             * Redéfinitions de la méthode actionPerformed
             */
            public void actionPerformed(ActionEvent arg0) {
            	Thread t = new Thread() {
            		public void run() {  
            	setVisible(false);
           		 }
                };
                t.start();
            	
            }
            
    }


}
