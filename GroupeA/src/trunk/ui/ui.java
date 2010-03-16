package trunk.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import trunk.logic.FonctionEvaluation;
import static trunk.logic.FonctionEvaluation.goban;

public class ui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel = null;
	private JPanel jPanelSettings = null;
	
	private JLabel jLabelWhiteStone = null;
	private JComboBox cbWhiteStone = null;
	private JLabel jLabelBlackStone = null;
	private JComboBox cbBlackStone = null;
	private JLabel jLabelLevel = null;
	private JComboBox cbLevel = null;
	
	private JButton jButtonExit = null;
	private JButton jButtonPlay = null;
	private final Canvas board = new Board();
	private Point p = null;
	private boolean tourAdversair, compFirst;
	float d1, d2;
	int wB, hB, nbC, deuxCoups;
	
	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		
		if (jPanel == null) {
			
			jPanel = new JPanel();
			jPanel.setLayout(new FlowLayout());
			jPanel.setBackground(new Color(0, 0, 0));
			jPanel.setBorder(BorderFactory.createLineBorder(Color.red, 1));
			jPanel.setSize(new Dimension(310, 310));
			jPanel.setLocation(new Point(10, 10));
			
			board.setLocation(0, 0);
			board.setSize(300, 300);
			
			jPanel.add(board);
			wB = board.getWidth();
			hB = board.getHeight(); 
			nbC = ((Board) board).getNbCases();
			d1 = nbC / (float)(wB - 2 * Board.space);
			d2 = nbC / (float)(hB - 2 * Board.space);
			
			board.addMouseListener(new MouseAdapter(){
				
				@Override
				public void mousePressed(MouseEvent mEvt){
					
					int xc, yc;    // l'indice de casses oryzontales et verticales 
					p.x = mEvt.getX();
			        p.y = mEvt.getY();
			        			      			      
			       if(tourAdversair && p.x > Board.space && p.y > Board.space
			        && p.x < wB - Board.space && p.y < hB - Board.space) {			    	   			    	  
			    
			    	    xc = Math.round((p.x - Board.space) * d1);
				        yc = Math.round((p.y - Board.space) * d2);						        
				        
				        if(goban[xc][yc] == 0 && trunk.logic.FonctionEvaluation.permissible(1, xc, yc)) {
				        	
				        	tourAdversair = false;
				        	
				        	if(compFirst)  // c'est a dire l'adversair avec le noir
				        		ajoutDansNoire(xc, yc, 1);
				        	else
				        		ajoutDansBlanc(xc, yc, 1);
				        	
				        	FonctionEvaluation.incPA();
					        
				        	if(deuxCoups >=2) {
				        						        		
					        	int[] prochainCoup = FonctionEvaluation.coupSuivant();
					        	if(compFirst)
					        		ajoutDansBlanc(prochainCoup[0], prochainCoup[1], 0);
					        	else
					        		ajoutDansNoire(prochainCoup[0], prochainCoup[1], 0);
					        	
				        	}
				        	else {
				        		deuxCoups ++;
								int r = -1, r2 = -1;
								while(r==-1 || r2==-1 || goban[3 + r%4][3 + r2%4]!=0) {
									r = (int)(Math.random()*10);
									r2 = (int)(Math.random()*10);	
								}
								
								if(compFirst){
									ajoutDansBlanc(3 + r%4, 3 + r2%4, 0);
								} else {
									ajoutDansNoire(3 + r%4, 3 + r2%4, 0);
								}
							}
				        	
				        	FonctionEvaluation.incPO();	
				        	tourAdversair = true;
				        }
				        
			        }
			        
				}

				@Override
				public void mouseReleased(MouseEvent mEvt){}
			});
			
		}
		return jPanel;
	}
	
	void ajoutDansBlanc(int xc, int yc, int tour) {			
		    	
    	float f1 = xc / d1 - Board.diametreBile/2, f2 = yc/d2 - Board.diametreBile/2;
    	
    	goban[xc][yc] = tour + 1;
    	((Board) board).setV2(new Point((int) (Board.space + f1),
    					(int) (Board.space + f2)));
    	
        board.repaint();
        
	}
	
	void ajoutDansNoire(int xc, int yc, int tour) {			
    	
    	float f1 = xc / d1 - Board.diametreBile/2, f2 = yc/d2 - Board.diametreBile/2;
    	
    	goban[xc][yc] = tour + 1;
    	((Board) board).setV(new Point((int) (Board.space + f1),
    					(int) (Board.space + f2)));
    	
        board.repaint();
        
	}	

	/**
	 * This method initializes jPanelSettings
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelSettings() {
		if (jPanelSettings == null) {

			jLabelWhiteStone = new JLabel();
			jLabelWhiteStone.setText("Black Stone");
			jLabelWhiteStone.setLocation(new Point(10, 0));
			jLabelWhiteStone.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelWhiteStone.setSize(new Dimension(170, 25));
			
			jLabelBlackStone = new JLabel();
			jLabelBlackStone.setText("White Stone");
			jLabelBlackStone.setLocation(new Point(10, 40));
			jLabelBlackStone.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelBlackStone.setSize(new Dimension(170, 25));

			jLabelLevel = new JLabel();
			jLabelLevel.setText("Level of difficulty");
			jLabelLevel.setLocation(new Point(10, 80));
			jLabelLevel.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelLevel.setSize(new Dimension(170, 25));
			
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 0;

			jPanelSettings = new JPanel();
			jPanelSettings.setLayout(null);
			jPanelSettings.setLocation(new Point(320, 10));
			jPanelSettings.setSize(new Dimension(190, 300));
			jPanelSettings.add(jLabelWhiteStone, null);
			jPanelSettings.add(getCbWhiteStone(), null);
			jPanelSettings.add(jLabelBlackStone, null);
			jPanelSettings.add(getCbBlackStone(), null);
			jPanelSettings.add(jLabelLevel, null);
			jPanelSettings.add(getCbLevel(), null);
			jPanelSettings.add(getJButtonExit(), null);
			jPanelSettings.add(getJButtonPlay(), null);

		}
		return jPanelSettings;
	}

	/**
	 * This method initializes cbWhiteStone
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbWhiteStone() {
		if (cbWhiteStone == null) {
			cbWhiteStone = new JComboBox();
			cbWhiteStone.setName("White Stone");
			cbWhiteStone.setLocation(new Point(10, 20));
			cbWhiteStone.setSize(new Dimension(170, 25));
			cbWhiteStone.addItem("Human");
			cbWhiteStone.addItem("Computer");
		}
		return cbWhiteStone;
	}

	/**
	 * This method initializes cbBlackStone
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbBlackStone() {
		if (cbBlackStone == null) {
			cbBlackStone = new JComboBox();
			cbBlackStone.setLocation(new Point(10, 60));
			cbBlackStone.setSize(new Dimension(170, 25));
			cbBlackStone.addItem("Human");
			cbBlackStone.addItem("Computer");
		}
		return cbBlackStone;
	}

	/**
	 * This method initializes cbLevel
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCbLevel() {
		if (cbLevel == null) {
			cbLevel = new JComboBox();
			cbLevel.setLocation(new Point(10, 100));
			cbLevel.setSize(new Dimension(170, 25));
			cbLevel.addItem("Very Hard");
			cbLevel.addItem("Hard");
			cbLevel.addItem("Medium");
			cbLevel.addItem("Easy");
			cbLevel.addItem("Very Easy");
		}
		return cbLevel;
	}

	/**
	 * This method initializes jButtonExit
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonExit() {
		if (jButtonExit == null) {
			jButtonExit = new JButton();
			jButtonExit.setLocation(new Point(10, 270));
			jButtonExit.setText("End game");
			jButtonExit.setSize(new Dimension(170, 25));
			jButtonExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					tourAdversair = false;					
				}
			});
		}
		return jButtonExit;
	}

	/**
	 * This method initializes jButtonPlay
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButtonPlay() {
		if (jButtonPlay == null) {
			jButtonPlay = new JButton();
			jButtonPlay.setSize(new Dimension(170, 25));
			jButtonPlay.setText("New Game!");
			jButtonPlay.setLocation(new Point(10, 150));
			jButtonPlay.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					tourAdversair = false;
					String white, black, level;
					white = (String)cbWhiteStone.getModel().getSelectedItem();
					black = (String)cbBlackStone.getModel().getSelectedItem();
					level = (String)cbLevel.getModel().getSelectedItem();
					
					/************ SYSOUT DU LEVEL ************/
					System.out.println("Niveau de difficulté choisi: " + level);
					
					if(white == black) {
						JOptionPane.showMessageDialog(null, 
								(String)"Please choose correct values for \n white and black players", 
								"Bad choice", 
								JOptionPane.ERROR_MESSAGE);
						
						return;
					}
					
					if(white.equals("Computer")){
						compFirst = true;
					}else{
						compFirst = false;
					}
					((Board)board).resetTable();
					FonctionEvaluation.jeuNeu();
					deuxCoups = 0;
					
					if(compFirst) {  //  ordinateur premier, premier coup (blancs)						
						int r = (int)(Math.random()*10), r2 = (int)(Math.random()*10);
						ajoutDansBlanc(3 + r%4, 3 + r2%4, 0);						
						FonctionEvaluation.incPO();
						deuxCoups++;
					}
					
					tourAdversair = true;
				}
			});
		}
		return jButtonPlay;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ui thisClass = new ui();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}

	/**
	 * This is the default constructor
	 */
	public ui() {
		super();
		initialize();
		
        p = new Point();

        p.x = 0;
        p.y = 0;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(530, 350);
		this.setLocation(new Point(600, 300));
		this.setPreferredSize(new Dimension(550, 400));
		this.setResizable(false);
		this.setContentPane(getJContentPane());
		this.setTitle("Jeu de Go de fou!");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setPreferredSize(new Dimension(550, 550));
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJPanelSettings(), null);
		}
		return jContentPane;
	}

}