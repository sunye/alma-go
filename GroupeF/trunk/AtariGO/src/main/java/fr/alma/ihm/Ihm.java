package fr.alma.ihm;

import static fr.alma.jeu.Jeu.*;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Point;


import javax.swing.ImageIcon;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.HashMap;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;



import fr.alma.jeu.Pion.Couleur;
import fr.alma.jeu.Grille;
import javax.swing.JDialog;
import java.awt.Rectangle;
import javax.swing.JComboBox;

/**
 * Classe de l'interface graphique.
 * @author lahuidi
 *
 */
public class Ihm {

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="120,127"
	private JMenuBar jJMenuBar = null;
	private JMenu MFichier = null;
	private JMenuItem IQuitter = null;
	private JMenu MAide = null;
	private JMenuItem IApropos = null;
	private JMenu MJeu = null;
	private JMenuItem INouveau = null;
	private JPanel jPanel = null;
	private JLabel Grille = null;
	private JPanel PGrille = null;
	private JLabel ATarigo = null;
	private JPanel PAtarigo = null;
	private JLabel Logo = null;
	private JPanel PJeu = null;
	
	private JNoir Noir = null;
	private JBlanc Blanc = null;
	
	private JButton BNouveau = null;
	private JButton BApropos = null;
	private JButton BQuitter = null;
	
	//--------------------------------
	
	private boolean jeuEnCours = false;
	
	private Point p = null;
	private HashMap <Point, Point> ghmp; 
		
	/**
	 * Enumération pour le tour des joueurs.
	 * @author lahuidi
	 *
	 */
	public enum Tour {NOIR,BLANC};
	
	public Tour t = Tour.NOIR;
	
	//--------------------------------
	public static Grille grille = null;
	private JDialog jDialogNouveau = null;  //  @jve:decl-index=0:visual-constraint="739,469"
	private JPanel jContentPane = null;
	private JLabel jLCouleur = null;
	private JComboBox jCouleur = null;
	private JButton jBOk = null;
	private JButton jBAnnuler = null;
	private JButton BInterrompre = null;
	
		
	/**
	 * Constructeur de la classe.
	 */
	public Ihm() {
		
		getJFrame();
		grille = new Grille();
		initHashTable();
		
	}

	/****************************************************************
	 * 					INITIALISATION DE L'IHM 
	 ****************************************************************/
	
	/**
	 * Cette méthode initialise le JFrame.	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setResizable(false);
			jFrame.setTitle("AtariGO V 1.0");
			jFrame.setSize(new Dimension(580, 557));
			jFrame.setLocation(new Point(300, 120));
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setContentPane(getJPanel());
			getJDialogNouveau();
			jFrame.setVisible(true);
		}
		return jFrame;
	}

	/****************************************************************
	 * 					INITIALISATION DU MENU 
	 ****************************************************************/

	/**
	 * Cette méthode initialise le JMenuBar.
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getMFichier());
			jJMenuBar.add(getMJeu());
			jJMenuBar.add(getMAide());
		}
		return jJMenuBar;
	}
	

	/**
	 * Cette méthode initialise le menu Fichier.	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMFichier() {
		if (MFichier == null) {
			MFichier = new JMenu();
			MFichier.setText("Fichier");
			MFichier.add(getIQuitter());
		}
		return MFichier;
	}
	
	
	/**
	 * Cette méthode initialise l'item Quitter.	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getIQuitter() {
		if (IQuitter == null) {
			IQuitter = new JMenuItem();
			IQuitter.setText("Quitter");
			IQuitter.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return IQuitter;
	}
	
	
	/**
	 * Cette méthode initialise le menu Aide.	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMAide() {
		if (MAide == null) {
			MAide = new JMenu();
			MAide.setText("Aide");
			MAide.add(getIApropos());
		}
		return MAide;
	}
	

	/**
	 * Cette méthode initialise l'item Apropos.	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getIApropos() {
		if (IApropos == null) {
			IApropos = new JMenuItem();
			IApropos.setText("A propos");
			IApropos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ShowApropos();
				}
			});
		}
		return IApropos;
	}

	
	/**
	 * Cette méthode initialise le menu MJeu.	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getMJeu() {
		if (MJeu == null) {
			MJeu = new JMenu();
			MJeu.setText("Jeu");
			MJeu.add(getINouveau());
		}
		return MJeu;
	}


	/**
	 * Cette méthode initialise l'item Nouveau.	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getINouveau() {
		if (INouveau == null) {
			INouveau = new JMenuItem();
			INouveau.setText("Nouveau jeu");
			INouveau.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					nouveauJeu();
				}
			});
		}
		return INouveau;
	}
	
	/****************************************************************
	 * 					INITIALISATION DES PANEAUX 
	 ****************************************************************/
	
	/**
	 * Cette méthode initialise le panneau principale.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			
			ATarigo = new JLabel();
			ATarigo.setIcon(new ImageIcon(getClass().getResource("/fr/alma/images/ago.PNG")));
			ATarigo.setSize(new Dimension(388, 82));
			ATarigo.setLocation(new Point(15, 20));
			
			Grille = new JLabel();
			Grille.setName("Noir");
			Grille.setEnabled(false);
			
			ImageIcon img = new ImageIcon(getClass().getResource("/fr/alma/images/atariGo.png"));
			
			Grille.setIcon(img);
			
			Grille.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					
					 Point position = getValidePosition();
					 					 
					 if(position != null) {
				
						 int RHomme = SimulerJeu(grille, position, t);
											 						 
						 switch(RHomme){
						 	
						     case INVALIDE : {
						    	 JOptionPane.showMessageDialog(jFrame, "Position invalide veuillez choisir une autre case !");
						    	 break;
						     }
						 	 case VALIDE : {
						 		
								 mettrePion(position);
								 System.out.println("positionné");
				
								 position = jouerMachine(grille);
								 System.out.println("probleme ?");
								 								 
								 int RMachine = SimulerJeu(grille, position, t);
								 
								 if(RMachine == VALIDE) mettrePion(position); 
								 else {
									 	miseAjourGrille(miseAjourGrilleApresCapture(grille, position));
									 	JOptionPane.showMessageDialog(jFrame, "Capture éffectué ! Vous avez perdu !");
									    mettrePion(position);
								 }
								 break;						 
								}
							 
							 case CAPTURE : {
								 miseAjourGrille(miseAjourGrilleApresCapture(grille, position));
								 JOptionPane.showMessageDialog(jFrame, "Capture éffectué ! Bravo vous avez gagner");
								 BInterrompre.setEnabled(false);
									Grille.setEnabled(false);
									BNouveau.setEnabled(true);
									INouveau.setEnabled(true);
									jeuEnCours = false;
									effacerGrille();
								 break;
								}
							 
							 case SUICIDE : {
								 JOptionPane.showMessageDialog(jFrame,
										 "Le suicide est intérdit, veuillez choisir une autre case  ",
										 "Suicide interdit", 
										 JOptionPane.WARNING_MESSAGE);
								 break;
								}
								 
						}
							 
					 }  
				}
			});
			
			Blanc = new JBlanc();
			Blanc.setIcon(new ImageIcon(getClass().getResource("/fr/alma/images/blanc.PNG")));
			Blanc.setLocation(new Point(57, 146));
			Blanc.setSize(new Dimension(30, 30));
			Blanc.setVisible(false);
						
			Noir = new JNoir();
			Noir.setIcon(new ImageIcon(getClass().getResource("/fr/alma/images/noir.PNG")));
			Noir.setSize(new Dimension(30, 30));
			Noir.setLocation(new Point(15, 146));
			Noir.setVisible(false);
			
			jPanel = new JPanel();
			jPanel.setLayout(null);
			
			Grille.add(Noir, null);
			Grille.add(Blanc, null);
			jPanel.add(getPGrille(), null);
			jPanel.add(getPAtarigo(), null);
			jPanel.add(getPJeu(), null);
			
			
			}
		return jPanel;
	}

	/**
	 * Cette méthode initialise la grille.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPGrille() {
		if (PGrille == null) {
			PGrille = new JPanel();
			PGrille.setLayout(new GridBagLayout());
			PGrille.setBorder(BorderFactory.createTitledBorder(null, " Grille : ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			PGrille.setSize(new Dimension(311, 325));
			PGrille.setLocation(new Point(20, 160));
			PGrille.add(Grille, new GridBagConstraints());
		}
		return PGrille;
	}

	/**
	 * Cette méthode initialise le panneau du logo.
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPAtarigo() {
		if (PAtarigo == null) {
			Logo = new JLabel();
			Logo.setIcon(new ImageIcon(getClass().getResource("/fr/alma/images/logo.png")));
			Logo.setSize(new Dimension(103, 98));
			Logo.setLocation(new Point(415, 12));
			
			PAtarigo = new JPanel();
			PAtarigo.setLayout(null);
			PAtarigo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
			PAtarigo.setSize(new Dimension(535, 125));
			PAtarigo.setLocation(new Point(20, 20));
			PAtarigo.add(ATarigo, null);
			PAtarigo.add(Logo, null);
		}
		return PAtarigo;
	}
	
	/**
	 * Cette méthode initialise le panneau Jeu.	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPJeu() {
		if (PJeu == null) {
			PJeu = new JPanel();
			PJeu.setLayout(null);
			PJeu.setBorder(BorderFactory.createTitledBorder(null, " Jeu : ", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Dialog", Font.BOLD, 12), new Color(51, 51, 51)));
			PJeu.setLocation(new Point(355, 160));
			PJeu.setSize(new Dimension(200, 325));
			PJeu.add(getBNouveau(), null);
			PJeu.add(getBApropos(), null);
			PJeu.add(getBQuitter(), null);
			PJeu.add(getBInterrompre(), null);
		}
		return PJeu;
	}
	
	/****************************************************************
	 * 					INITIALISATION LES BOUTONS 
	 ****************************************************************/
	
	/**
	 *  Cette méthode initialise le bouton Nouveau.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBNouveau() {
		if (BNouveau == null) {
			BNouveau = new JButton();
			BNouveau.setText("Nouveau jeu");
			BNouveau.setSize(new Dimension(160, 35));
			BNouveau.setPreferredSize(new Dimension(100, 25));
			BNouveau.setLocation(new Point(20, 30));
			BNouveau.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					nouveauJeu();
				}
			});
		}
		return BNouveau;
	}

	/**
	 * Cette méthode initialise le bouton Apropos.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBApropos() {
		if (BApropos == null) {
			BApropos = new JButton();
			BApropos.setText("A Propos ...");
			BApropos.setSize(new Dimension(160, 35));
			BApropos.setPreferredSize(new Dimension(100, 25));
			BApropos.setLocation(new Point(20, 175));
			BApropos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ShowApropos();
				}
			});
		}
		return BApropos;
	}

	private void ShowApropos(){
		JOptionPane.showMessageDialog(null, "Université de Nantes\nMaster 1 ALMA\nAtariGo V 1.0\nRéaliser par :\n	- ZERBITA Mohamed El Hadi\n		- Ngassa Hubert", 
				"AtariGo V 1.0",
				1);
		
	}
	/**
	 * Cette méthode initialise le bouton Quitter.	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBQuitter() {
		if (BQuitter == null) {
			BQuitter = new JButton();
			BQuitter.setText("Quitter");
			BQuitter.setSize(new Dimension(160, 35));
			BQuitter.setLocation(new Point(20, 270));
			BQuitter.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return BQuitter;
	}
	
	/****************************************************************
	 * 					TRAITEMENT DE LA GRILLE 
	 ****************************************************************/
	
	/**
	 * Méthode qui permet d'effacer la grille de tout les pions
	 */
	private void effacerGrille(){
		Component[]contenu;
		contenu = Grille.getComponents();
		for(Component each : contenu){
			each.setVisible(false);
			each = null;
		}
	}
	
	/**
	 * Méthode qui efface la grille et initialise son contenu
	 */
	private void nouveauJeu(){
			
		jDialogNouveau.setVisible(true);
		
		
			
	}
	
	/**
	 * Cette méthode met à jour la grille avec le pion ajouté.
	 * @param position position du pion ajouté. 
	 */
	private void mettrePion(Point position) {
				
		if(t == Tour.NOIR ){
			
				grille.Contenu[position.x][position.y].couleur = Couleur.NOIR;
				(grille.Contenu[position.x][position.y]).position=position;
				mettrePionNoir(position);
				t = Tour.BLANC;
			}else{
				grille.Contenu[position.x][position.y].couleur = Couleur.BLANC;
				(grille.Contenu[position.x][position.y]).position=position;
				mettrePionBlanc(position);
				t = Tour.NOIR;
			}
	}
	
	/**
	 * Méthode qui met un pion blanc dans une position
	 * @param position position du pion
	 */
	private void mettrePionBlanc(Point position) {
		JLabel blanc = (JLabel) Blanc.clone();
		Grille.add(blanc);
		blanc.setLocation(ghmp.get(position).x-15,ghmp.get(position).y-15);
		blanc.setVisible(true);
	}

	/**
	 * Méthode qui met un pion noir dans une position
	 * @param position position du pion
	 */
	private void mettrePionNoir(Point position) {
		JLabel noir = (JLabel) Noir.clone();
		Grille.add(noir);
		
		noir.setLocation(ghmp.get(position).x-15,ghmp.get(position).y-15);
		noir.setVisible(true);
	}
	
	/**
	 * Méthode qui met à jour toute la grille lors d'une capture
	 * @param other contenu de la nouvelle grille
	 */
	private void miseAjourGrille(Grille other){
		effacerGrille();
		for(int i=0;i<9;i++) 
			for(int j=0;j<9;j++) 
                 if(other.Contenu[i][j]!= grille.Contenu[i][j] && other.Contenu[i][j].couleur == Couleur.NOIR){
                	grille.Contenu[i][j] = other.Contenu[i][j];
                	mettrePionNoir(new Point(i,j));
                }else if(other.Contenu[i][j]!= grille.Contenu[i][j] && other.Contenu[i][j].couleur == Couleur.BLANC){
                	grille.Contenu[i][j] = other.Contenu[i][j];
               	 	mettrePionBlanc(new Point(i,j));
                }
    }
	
	/**
	 * Cette méthode retourne la case de la grille qui correspond au click de souris.
	 * @return la case de la grille.
	 */
	private Point getValidePosition(){
		p = Grille.getMousePosition();
		
		if(jeuEnCours){
		//---------------------- LIGNE 9
		if(p.x<57 && p.x>33 && p.y<57 && p.y>33) return new Point(0,0);
		if(p.x<82 && p.x>58 && p.y<57 && p.y>33) return new Point(1,0);
		if(p.x<106 && p.x>82 && p.y<57 && p.y>33) return new Point(2,0);
		if(p.x<130 && p.x>106 && p.y<57 && p.y>33) return new Point(3,0);
		if(p.x<154 && p.x>130 && p.y<57 && p.y>33) return new Point(4,0);
		if(p.x<179 && p.x>155 && p.y<57 && p.y>33) return new Point(5,0);
		if(p.x<202 && p.x>178 && p.y<57 && p.y>33) return new Point(6,0);
		if(p.x<227 && p.x>203 && p.y<57 && p.y>33) return new Point(7,0);
		if(p.x<252 && p.x>228 && p.y<57 && p.y>33) return new Point(8,0);
		
		//---------------------- LIGNE 8
		if(p.x<57 && p.x>33 && p.y<82 && p.y>58) return new Point(0,1);
		if(p.x<82 && p.x>58 && p.y<82 && p.y>58) return new Point(1,1);
		if(p.x<106 && p.x>82 && p.y<82 && p.y>58) return new Point(2,1);
		if(p.x<130 && p.x>106 && p.y<82 && p.y>58) return new Point(3,1);
		if(p.x<154 && p.x>130 && p.y<82 && p.y>58) return new Point(4,1);
		if(p.x<179 && p.x>155 && p.y<82 && p.y>58) return new Point(5,1);
		if(p.x<202 && p.x>178 && p.y<82 && p.y>58) return new Point(6,1);
		if(p.x<227 && p.x>203 && p.y<82 && p.y>58) return new Point(7,1);
		if(p.x<252 && p.x>228 && p.y<82 && p.y>58) return new Point(8,1);
		
		//---------------------- LIGNE 7
		if(p.x<57 && p.x>33 && p.y<106 && p.y>82) return new Point(0,2);
		if(p.x<82 && p.x>58 && p.y<106 && p.y>82) return new Point(1,2);
		if(p.x<106 && p.x>82 && p.y<106 && p.y>82) return new Point(2,2);
		if(p.x<130 && p.x>106 && p.y<106 && p.y>82) return new Point(3,2);
		if(p.x<154 && p.x>130 && p.y<106 && p.y>82) return new Point(4,2);
		if(p.x<179 && p.x>155 && p.y<106 && p.y>82) return new Point(5,2);
		if(p.x<202 && p.x>178 && p.y<106 && p.y>82) return new Point(6,2);
		if(p.x<227 && p.x>203 && p.y<106 && p.y>82) return new Point(7,2);
		if(p.x<252 && p.x>228 && p.y<106 && p.y>82) return new Point(8,2);
	
		//---------------------- LIGNE 6
		if(p.x<57 && p.x>33 && p.y<130 && p.y>106) return new Point(0,3);
		if(p.x<82 && p.x>58 && p.y<130 && p.y>106) return new Point(1,3);
		if(p.x<106 && p.x>82 && p.y<130 && p.y>106) return new Point(2,3);
		if(p.x<130 && p.x>106 && p.y<130 && p.y>106) return new Point(3,3);
		if(p.x<154 && p.x>130 && p.y<130 && p.y>106) return new Point(4,3);
		if(p.x<179 && p.x>155 && p.y<130 && p.y>106) return new Point(5,3);
		if(p.x<202 && p.x>178 && p.y<130 && p.y>106) return new Point(6,3);
		if(p.x<227 && p.x>203 && p.y<130 && p.y>106) return new Point(7,3);
		if(p.x<252 && p.x>228 && p.y<130 && p.y>106) return new Point(8,3);
		
		//---------------------- LIGNE 5
		if(p.x<57 && p.x>33 && p.y<154 && p.y>130) return new Point(0,4);
		if(p.x<82 && p.x>58 && p.y<154 && p.y>130) return new Point(1,4);
		if(p.x<106 && p.x>82 && p.y<154 && p.y>130) return new Point(2,4);
		if(p.x<130 && p.x>106 && p.y<154 && p.y>130) return new Point(3,4);
		if(p.x<154 && p.x>130 && p.y<154 && p.y>130) return new Point(4,4);
		if(p.x<179 && p.x>155 && p.y<154 && p.y>130) return new Point(5,4);
		if(p.x<202 && p.x>178 && p.y<154 && p.y>130) return new Point(6,4);
		if(p.x<227 && p.x>203 && p.y<154 && p.y>130) return new Point(7,4);
		if(p.x<252 && p.x>228 && p.y<154 && p.y>130) return new Point(8,4);
		
		//---------------------- LIGNE 4
		if(p.x<57 && p.x>33 && p.y<179 && p.y>155) return new Point(0,5);
		if(p.x<82 && p.x>58 && p.y<179 && p.y>155) return new Point(1,5);
		if(p.x<106 && p.x>82 && p.y<179 && p.y>155) return new Point(2,5);
		if(p.x<130 && p.x>106 && p.y<179 && p.y>155) return new Point(3,5);
		if(p.x<154 && p.x>130 && p.y<179 && p.y>155) return new Point(4,5);
		if(p.x<179 && p.x>155 && p.y<179 && p.y>155) return new Point(5,5);
		if(p.x<202 && p.x>178 && p.y<179 && p.y>155) return new Point(6,5);
		if(p.x<227 && p.x>203 && p.y<179 && p.y>155) return new Point(7,5);
		if(p.x<252 && p.x>228 && p.y<179 && p.y>155) return new Point(8,5);
		
		//---------------------- LIGNE 3
		if(p.x<57 && p.x>33 && p.y<202 && p.y>178) return new Point(0,6);
		if(p.x<82 && p.x>58 && p.y<202 && p.y>178) return new Point(1,6);
		if(p.x<106 && p.x>82 && p.y<202 && p.y>178) return new Point(2,6);
		if(p.x<130 && p.x>106 && p.y<202 && p.y>178) return new Point(3,6);
		if(p.x<154 && p.x>130 && p.y<202 && p.y>178) return new Point(4,6);
		if(p.x<179 && p.x>155 && p.y<202 && p.y>178) return new Point(5,6);
		if(p.x<202 && p.x>178 && p.y<202 && p.y>178) return new Point(6,6);
		if(p.x<227 && p.x>203 && p.y<202 && p.y>178) return new Point(7,6);
		if(p.x<252 && p.x>228 && p.y<202 && p.y>178) return new Point(8,6);
		
		//---------------------- LIGNE 2
		if(p.x<57 && p.x>33 && p.y<227 && p.y>203) return new Point(0,7);
		if(p.x<82 && p.x>58 && p.y<227 && p.y>203) return new Point(1,7);
		if(p.x<106 && p.x>82 && p.y<227 && p.y>203) return new Point(2,7);
		if(p.x<130 && p.x>106 && p.y<227 && p.y>203) return new Point(3,7);
		if(p.x<154 && p.x>130 && p.y<227 && p.y>203) return new Point(4,7);
		if(p.x<179 && p.x>155 && p.y<227 && p.y>203) return new Point(5,7);
		if(p.x<202 && p.x>178 && p.y<227 && p.y>203) return new Point(6,7);
		if(p.x<227 && p.x>203 && p.y<227 && p.y>203) return new Point(7,7);
		if(p.x<252 && p.x>228 && p.y<227 && p.y>203) return new Point(8,7);
		
		//---------------------- LIGNE 1
		if(p.x<57 && p.x>33 && p.y<252 && p.y>228) return new Point(0,8);
		if(p.x<82 && p.x>58 && p.y<252 && p.y>228) return new Point(1,8);
		if(p.x<106 && p.x>82 && p.y<252 && p.y>228) return new Point(2,8);
		if(p.x<130 && p.x>106 && p.y<252 && p.y>228) return new Point(3,8);
		if(p.x<154 && p.x>130 && p.y<252 && p.y>228) return new Point(4,8);
		if(p.x<179 && p.x>155 && p.y<252 && p.y>228) return new Point(5,8);
		if(p.x<202 && p.x>178 && p.y<252 && p.y>228) return new Point(6,8);
		if(p.x<227 && p.x>203 && p.y<252 && p.y>228) return new Point(7,8);
		if(p.x<252 && p.x>228 && p.y<252 && p.y>228) return new Point(8,8);
		}
		return null;
			
	}
	
	 /** 
     * Initialise la table de hachage avec les points qui correspondent aux valeurs. 
     */ 
    private void initHashTable(){ 
             
             
            ghmp = new HashMap< Point, Point>(); 
            //------------------------------ 
            ghmp.put(new Point(0,0), new Point(45,45)); 
            ghmp.put(new Point(1,0), new Point(70,45));   
            ghmp.put(new Point(2,0), new Point(94,45)); 
            ghmp.put(new Point(3,0), new Point(118,45)); 
            ghmp.put(new Point(4,0), new Point(142,45)); 
            ghmp.put(new Point(5,0), new Point(167,45)); 
            ghmp.put(new Point(6,0), new Point(190,45)); 
            ghmp.put(new Point(7,0), new Point(215,45)); 
            ghmp.put(new Point(8,0), new Point(240,45)); 
             
            //------------------------------ 
            ghmp.put(new Point(0,1), new Point(45,70)); 
            ghmp.put(new Point(1,1), new Point(70,70));  
            ghmp.put(new Point(2,1), new Point(94,70)); 
            ghmp.put(new Point(3,1), new Point(118,70)); 
            ghmp.put(new Point(4,1), new Point(142,70)); 
            ghmp.put(new Point(5,1), new Point(167,70)); 
            ghmp.put(new Point(6,1), new Point(190,70)); 
            ghmp.put(new Point(7,1), new Point(215,70)); 
            ghmp.put(new Point(8,1), new Point(240,70)); 
             
            //------------------------------ 
            ghmp.put(new Point(0,2), new Point(45,94)); 
            ghmp.put(new Point(1,2), new Point(70,94));  
            ghmp.put(new Point(2,2), new Point(94,94)); 
            ghmp.put(new Point(3,2), new Point(118,94)); 
            ghmp.put(new Point(4,2), new Point(142,94)); 
            ghmp.put(new Point(5,2), new Point(167,94)); 
            ghmp.put(new Point(6,2), new Point(190,94)); 
            ghmp.put(new Point(7,2), new Point(215,94)); 
            ghmp.put(new Point(8,2), new Point(240,94)); 
             
            //------------------------------ 
            ghmp.put(new Point(0,3), new Point(45,118)); 
            ghmp.put(new Point(1,3), new Point(70,118));         
            ghmp.put(new Point(2,3), new Point(94,118)); 
            ghmp.put(new Point(3,3), new Point(118,118)); 
            ghmp.put(new Point(4,3), new Point(142,118)); 
            ghmp.put(new Point(5,3), new Point(167,118)); 
            ghmp.put(new Point(6,3), new Point(190,118)); 
            ghmp.put(new Point(7,3), new Point(215,118)); 
            ghmp.put(new Point(8,3), new Point(240,118)); 
             
            //------------------------------ 
            ghmp.put(new Point(0,4), new Point(45,142)); 
            ghmp.put(new Point(1,4), new Point(70,142));         
            ghmp.put(new Point(2,4), new Point(94,142)); 
            ghmp.put(new Point(3,4), new Point(118,142)); 
            ghmp.put(new Point(4,4), new Point(142,142)); 
            ghmp.put(new Point(5,4), new Point(167,142)); 
            ghmp.put(new Point(6,4), new Point(190,142)); 
            ghmp.put(new Point(7,4), new Point(215,142)); 
            ghmp.put(new Point(8,4), new Point(240,142)); 
             
            //------------------------------ 
            ghmp.put(new Point(0,5), new Point(45,167)); 
            ghmp.put(new Point(1,5), new Point(70,167));         
            ghmp.put(new Point(2,5), new Point(94,167)); 
            ghmp.put(new Point(3,5), new Point(118,167)); 
            ghmp.put(new Point(4,5), new Point(142,167)); 
            ghmp.put(new Point(5,5), new Point(167,167)); 
            ghmp.put(new Point(6,5), new Point(190,167)); 
            ghmp.put(new Point(7,5), new Point(215,167)); 
            ghmp.put(new Point(8,5), new Point(240,167)); 
             
            //------------------------------ 
            ghmp.put(new Point(0,6), new Point(45,190)); 
            ghmp.put(new Point(1,6), new Point(70,190));         
            ghmp.put(new Point(2,6), new Point(94,190)); 
            ghmp.put(new Point(3,6), new Point(118,190)); 
            ghmp.put(new Point(4,6), new Point(142,190)); 
            ghmp.put(new Point(5,6), new Point(167,190)); 
            ghmp.put(new Point(6,6), new Point(190,190)); 
            ghmp.put(new Point(7,6), new Point(215,190)); 
            ghmp.put(new Point(8,6), new Point(240,190)); 
             
            //------------------------------ 
            ghmp.put(new Point(0,7), new Point(45,215)); 
            ghmp.put(new Point(1,7), new Point(70,215));         
            ghmp.put(new Point(2,7), new Point(94,215)); 
            ghmp.put(new Point(3,7), new Point(118,215)); 
            ghmp.put(new Point(4,7), new Point(142,215)); 
            ghmp.put(new Point(5,7), new Point(167,215)); 
            ghmp.put(new Point(6,7), new Point(190,215)); 
            ghmp.put(new Point(7,7), new Point(215,215)); 
            ghmp.put(new Point(8,7), new Point(240,215)); 
             
            //------------------------------ 
            ghmp.put(new Point(0,8), new Point(45,240)); 
            ghmp.put(new Point(1,8), new Point(70,240));         
            ghmp.put(new Point(2,8), new Point(94,240)); 
            ghmp.put(new Point(3,8), new Point(118,240)); 
            ghmp.put(new Point(4,8), new Point(142,240)); 
            ghmp.put(new Point(5,8), new Point(167,240)); 
            ghmp.put(new Point(6,8), new Point(190,240)); 
            ghmp.put(new Point(7,8), new Point(215,240)); 
            ghmp.put(new Point(8,8), new Point(240,240)); 
    }

	/**
	 * This method initializes jDialogNouveau	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getJDialogNouveau() {
		if (jDialogNouveau == null) {
			jDialogNouveau = new JDialog(getJFrame());
			jDialogNouveau.setSize(new Dimension(250, 170));
			jDialogNouveau.setTitle("Choix de la couleur");
			jDialogNouveau.setModal(true);
			jDialogNouveau.setLocation(new Point(470, 300));
			jDialogNouveau.setContentPane(getJContentPane());
		}
		return jDialogNouveau;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLCouleur = new JLabel();
			jLCouleur.setBounds(new Rectangle(21, 23, 205, 16));
			jLCouleur.setText("Veuillez choisir la couleur du pion :");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLCouleur, null);
			jContentPane.add(getJCouleur(), null);
			jContentPane.add(getJBOk(), null);
			jContentPane.add(getJBAnnuler(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jCouleur	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJCouleur() {
		if (jCouleur == null) {
			jCouleur = new JComboBox();
			jCouleur.setBounds(new Rectangle(46, 52, 156, 25));
			jCouleur.addItem("Noir");
			jCouleur.addItem("Blanc");
		}
		return jCouleur;
	}

	/**
	 * This method initializes jBOk	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBOk() {
		if (jBOk == null) {
			jBOk = new JButton();
			jBOk.setText("Ok");
			jBOk.setSize(new Dimension(85, 25));
			jBOk.setLocation(new Point(30, 95));
			jBOk.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
				
						if(jCouleur.getSelectedItem()=="Blanc") t = Tour.BLANC;
						
						effacerGrille();
						for(int i=0;i<9;i++) 
				       	 for(int j=0;j<9;j++) 
				                grille.Contenu[i][j].couleur = Couleur.NULL; 
						
						jeuEnCours = true;
						Grille.setEnabled(true);
						BInterrompre.setEnabled(true);
						BNouveau.setEnabled(false);
						INouveau.setEnabled(false);
							
						jDialogNouveau.dispose();
				}
			});
		}
		return jBOk;
	}

	/**
	 * This method initializes jBAnnuler	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJBAnnuler() {
		if (jBAnnuler == null) {
			jBAnnuler = new JButton();
			jBAnnuler.setText("Annuler");
			jBAnnuler.setSize(new Dimension(85, 25));
			jBAnnuler.setLocation(new Point(125, 95));
			jBAnnuler.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jDialogNouveau.dispose();
				}
			});
		}
		return jBAnnuler;
	}

	/**
	 * This method initializes BInterrompre	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBInterrompre() {
		if (BInterrompre == null) {
			BInterrompre = new JButton();
			BInterrompre.setText("Interrompre");
			BInterrompre.setLocation(new Point(20, 85));
			BInterrompre.setSize(new Dimension(160, 35));
			BInterrompre.setEnabled(false);
			BInterrompre.setPreferredSize(new Dimension(100, 25));
			BInterrompre.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					if(!jeuEnCours){
						
					}else  if(JOptionPane.showConfirmDialog(null,
							"Etes-vous sur de vouloir intérompre le jeu en cours ?",
							"Confirmation",
							0) == JOptionPane.OK_OPTION){
						BInterrompre.setEnabled(false);
						Grille.setEnabled(false);
						BNouveau.setEnabled(true);
						INouveau.setEnabled(true);
						jeuEnCours = false;
						effacerGrille();
						
					}
				}
			});
		}
		return BInterrompre;
	} 

}
