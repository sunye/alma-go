package main.java.alexanddam.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

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
	private boolean tourAdversair, compFirst, fini;
	float d1, d2;
	int wB, hB, nbC, coupsAleatoires;

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
					if (tourAdversair){
						//modification du curseur en sablier si le jeux est lancé
						board.setCursor(new Cursor(Cursor.WAIT_CURSOR));
					}
					
					int xc, yc;    // l'indice de casses oryzontales et verticales
					p.x = mEvt.getX();
					p.y = mEvt.getY();
					if(fini || !tourAdversair || p.x < Board.space || p.x > wB - Board.space ||
							p.y < Board.space || p.y > hB - Board.space) {
						return;
					}

					xc = Math.round((p.y - Board.space) * d1);
					yc = Math.round((p.x - Board.space) * d2);

					if(main.java.alexanddam.logic.FonctionEvaluation.getGobanValue(xc, yc) == 0 && main.java.alexanddam.logic.FonctionEvaluation.permissible(1, xc, yc)) {
						tourAdversair = false;
						if(!ajoutGobanEtVerifie(xc, yc, 2)) { return; }

						main.java.alexanddam.logic.FonctionEvaluation.incPA();            

						if(coupsAleatoires >= 1) {
							int[] prochainCoup = main.java.alexanddam.logic.FonctionEvaluation.coupSuivant();
							if(main.java.alexanddam.logic.FonctionEvaluation.getGobanValue(prochainCoup[0], prochainCoup[1]) != 0) {
								jeuFini(0);
							}
							if(!ajoutGobanEtVerifie(prochainCoup[0], prochainCoup[1], 1)) { return; }
						}
						else {
							coupsAleatoires ++;
							int r = -1, r2 = -1;
							while(r==-1 || r2==-1 || main.java.alexanddam.logic.FonctionEvaluation.getGobanValue(3 + r%4, 3 + r2%4)!=0
									|| main.java.alexanddam.logic.FonctionEvaluation.getGobanValue(2 + r%4, 3 + r2%4)==2
									|| main.java.alexanddam.logic.FonctionEvaluation.getGobanValue(4 + r%4, 3 + r2%4)==2
									|| main.java.alexanddam.logic.FonctionEvaluation.getGobanValue(3 + r%4, 2 + r2%4)==2
									|| main.java.alexanddam.logic.FonctionEvaluation.getGobanValue(3 + r%4, 4 + r2%4)==2) {
								r = (int)(Math.random()*10);
								r2 = (int)(Math.random()*10);
							}
							if(!ajoutGobanEtVerifie(3 + r%4, 3 + r2%4, 1)) { return; }
						}

						main.java.alexanddam.logic.FonctionEvaluation.incPO();            
						tourAdversair = true;
					}

					//On remet le curseur a la normal
					board.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

				@Override
				public void mouseReleased(MouseEvent mEvt){}
			});

		}
		return jPanel;
	}

	void recupererGagnant(int type) {
		int loser;

		Vector<Point> v = main.java.alexanddam.logic.FonctionEvaluation.pierresPrises(type);
		Point pierreLoser = v.get(0);
		System.out.println("Une des positions prises "+pierreLoser.x+" , "+pierreLoser.y);

		loser = main.java.alexanddam.logic.FonctionEvaluation.getGobanValue(pierreLoser.x, pierreLoser.y);
		jeuFini(3 - loser);

		//On remet le curseur a la normal
		board.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	void jeuFini(int gagnant) {
		String str = "";

		fini = true;
		switch(gagnant) {
		case 1:
			str = "L'ordinateur a gagne! \n Essayez de nouveau!";
			break;
		case 2:
			str = "Vous avez gagne! \n Felicitations!";
			break;
		default:
			str = "On sais pas quel est le gagnant \n Quelque chose de mal s'est passe";
			System.out.println(str);
			break;
		}

		JOptionPane.showMessageDialog(this, str, "Jeu fini", JOptionPane.INFORMATION_MESSAGE);
	}

	boolean ajoutGobanEtVerifie(int x, int y, int type) {
		switch(type) {
		case 1:
			if(compFirst) { // c'est a dire l'adversair avec le noir
				ajoutDansBlanc(x, y, 1);
			} else {
				ajoutDansNoire(x, y, 1);
			}
			break;
		case 2:
			if(compFirst) { // c'est a dire l'adversair avec le noir
				ajoutDansNoire(x, y, 2);
			} else {
				ajoutDansBlanc(x, y, 2);
			}
			break;
		default:
			System.out.println("Valeur non-permise");
			break;
		}

		if(main.java.alexanddam.logic.FonctionEvaluation.jeuFini()) { recupererGagnant(type); return false; }

		return true;
	}

	void ajoutDansBlanc(int xc, int yc, int type) {

		float f1 = xc / d2 - Board.diametreBile/2, f2 = yc / d1 - Board.diametreBile/2;

		main.java.alexanddam.logic.FonctionEvaluation.setGobanValue(xc, yc, type);
		((Board) board).setWhite(new Point((int) (Board.space + f2),
				(int) (Board.space + f1)));

		board.repaint();

	}

	void ajoutDansNoire(int xc, int yc, int type) {

		float f1 = xc / d2 - Board.diametreBile/2, f2 = yc / d1 - Board.diametreBile/2;

		main.java.alexanddam.logic.FonctionEvaluation.setGobanValue(xc, yc, type);
		((Board) board).setBlack(new Point((int) (Board.space + f2),
				(int) (Board.space + f1)));

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
			jLabelWhiteStone.setText("White Stone");
			jLabelWhiteStone.setLocation(new Point(10, 0));
			jLabelWhiteStone.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelWhiteStone.setSize(new Dimension(170, 25));

			jLabelBlackStone = new JLabel();
			jLabelBlackStone.setText("Black Stone");
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
			cbLevel.addItem("Hard");
			cbLevel.addItem("Medium");
			cbLevel.addItem("Easy");
			cbLevel.addItem("Very Easy");

			cbLevel.setSelectedItem((String)"Easy");
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

				@Override
				public void actionPerformed(ActionEvent e) {

					tourAdversair = false;
					String white, black, level;
					white = (String)cbWhiteStone.getModel().getSelectedItem();
					black = (String)cbBlackStone.getModel().getSelectedItem();
					level = (String)cbLevel.getModel().getSelectedItem();

					/************ SYSOUT DU LEVEL ************/
					System.out.println("Niveau de difficultÃ© choisi: " + level);

					if(white.equals(black)) {
						JOptionPane.showMessageDialog(null,
								(String)"Please choose correct values for \n white and black players",
								"Bad choice",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					if(white.equals("Computer")) {
						compFirst = true;
						System.out.println("compFirst with white");
					} else {
						compFirst = false;
					}
					((Board)board).resetTable();
					main.java.alexanddam.logic.FonctionEvaluation.jeuNeu();
					reglerExpEtProf(level);
					coupsAleatoires = 0;
					fini = false;

					if(compFirst) {  //  ordinateur premier, premier coup (blancs)
						int r = (int)(Math.random()*10), r2 = (int)(Math.random()*10);
						ajoutDansBlanc(3 + r%4, 3 + r2%4, 1);
						main.java.alexanddam.logic.FonctionEvaluation.incPO();
						coupsAleatoires++;
					}

					tourAdversair = true;
				}
			});
		}
		return jButtonPlay;
	}

	void reglerExpEtProf(String level) {
		int exp = 10, prof = 5;

		if(level.equals("Hard")) {
			exp = 30; prof = 7;
		} else if(level.equals("Medium")) {
			exp = 20; prof = 5;
		} else if(level.equals("Easy")) {
			// on reste avec les valeurs par defaut
		} else if(level.equals("Very Easy")) {
			exp = 8; prof = 3;
		}

		main.java.alexanddam.logic.FonctionEvaluation.setExp(exp);
		main.java.alexanddam.logic.FonctionEvaluation.setProfondeur(prof);
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
		this.setTitle("Jeu d'Atari-Go!");
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