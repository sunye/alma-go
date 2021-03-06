	package fr.alma.ihm;

	import fr.alma.jeu.Couleur;
	import fr.alma.jeu.Grille;
	import fr.alma.jeu.Tour;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Font;
	import java.awt.GridBagConstraints;
	import java.awt.GridBagLayout;
	import java.awt.Point;
	import java.awt.Rectangle;
	import javax.swing.BorderFactory;
	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JComboBox;
	import javax.swing.JDialog;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JMenu;
	import javax.swing.JMenuBar;
	import javax.swing.JMenuItem;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.border.EtchedBorder;
	import javax.swing.border.TitledBorder;
	
	import static fr.alma.jeu.Jeu.*;

	/**
	 * User interface and managing events Class. 
	 * @author ZERBITA Mohamed El Hadi
	 * 
	 */
	public class Ihm {

		private JFrame jFrame = null; // @jve:decl-index=0:visual-constraint="120,127"
		private JMenuBar jJMenuBar = null;
		// ------------------------------
		private JMenu MFichier = null;
		private JMenuItem IQuitter = null;
		// ------------------------------
		private JMenu MJeu = null;
		private JMenuItem INouveau = null;
		private JMenuItem IInterrompre = null;
		// ------------------------------
		private JMenu MAide = null;
		private JMenuItem IApropos = null;
		// ------------------------------
		private JPanel jPanel = null;
		private JLabel LGrille = null;
		private JPanel PGrille = null;
		private JLabel ATarigo = null;
		private JPanel PAtarigo = null;
		private JLabel Logo = null;
		private JPanel PJeu = null;
		// ------------------------------
		private JButton BNouveau = null;
		private JButton BApropos = null;
		private JButton BQuitter = null;
		private JButton BInterrompre = null;
		// ------------------------------
		private JDialog jDialogNouveau = null; // @jve:decl-index=0:visual-constraint="739,469"
		private JPanel jContentPane = null;
		private JLabel jLCouleur = null;
		private JComboBox jCouleur = null;
		private JButton jBOk = null;
		private JButton jBAnnuler = null;
		// -------------------------------
		private JNoir Noir = null;
		private JBlanc Blanc = null;
		private Grille grille = null;
		private boolean jeuEnCours = false;
		private Tour t = Tour.NOIR;

		// -------------------------------

		/**
		 * Constructor of the Class.
		 */
		public Ihm() {

			getJFrame();
			grille = new Grille();
		}

		/****************************************************************
		 * INITIALISATION OF THE USER INTERFACE
		 ****************************************************************/

		/**
		 * This method initializes thevJFrame.
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
		 * INITIALISATION OF THE MENU
		 ****************************************************************/

		/**
		 * This Method initializes the JMenuBar.
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
		 * This Method initializes the File Menu.
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
		 *This Method initializes the item Quitter.
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
		 *This Method initializes the menu MJeu.
		 * @return javax.swing.JMenu
		 */
		private JMenu getMJeu() {
			if (MJeu == null) {
				MJeu = new JMenu();
				MJeu.setText("Jeu");
				MJeu.add(getINouveau());
				MJeu.add(getIInterrompre());
			}
			return MJeu;
		}

		/**
		 * This Method initializes the menu Aide.
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
		 * This Method initializes the item Apropos.
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
		 *This Method initializes the item Nouveau.
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

		/**
		 * This Method initializes the item Interrompre.
		 * @return javax.swing.JMenuItem
		 */
		
		private JMenuItem getIInterrompre() {
			if (IInterrompre == null) {
				IInterrompre = new JMenuItem();
				IInterrompre.setEnabled(false);
				IInterrompre.setText("Interrompre");
				IInterrompre
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent e) {
								interrompreJeu();
							}

						});
			}
			return IInterrompre;
		}

		/****************************************************************
		 * INITIALISATION OF PANELS
		 ****************************************************************/

		/**
		 * This Method initializes the main panel and manage the position of the pawns.
		 * @return javax.swing.JPanel
		 */
		private JPanel getJPanel() {
			if (jPanel == null) {
				ATarigo = new JLabel();
				ATarigo.setIcon(new ImageIcon(getClass().getResource(
						"/fr/alma/images/ago.PNG")));
				ATarigo.setSize(new Dimension(388, 82));
				ATarigo.setLocation(new Point(15, 20));
				LGrille = new JLabel();
				LGrille.setName("Noir");
				LGrille.setEnabled(false);
				ImageIcon img = new ImageIcon(getClass().getResource(
						"/fr/alma/images/atariGo.png"));
				LGrille.setIcon(img);

				LGrille.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent e) {

						Point position = JCase.getValidePosition(LGrille,
								jeuEnCours);

						if (position != null) {
							int RHomme = SimulerJeu(grille, position, t);

							switch (RHomme) {

							case INVALIDE: {
								JOptionPane
										.showMessageDialog(jFrame,
												"Position invalide veuillez choisir une autre case !");
								break;
							}
							case VALIDE: {

								t = grille.mettrePion(position, LGrille, Blanc,
										Noir, t);
								position = jouerMachine(grille, t);
								
								int RMachine = INVALIDE;
								
								while(RMachine == INVALIDE || RMachine == SUICIDE)
									RMachine = SimulerJeu(grille, position, t);

								if (RMachine == VALIDE) {
									t = grille.mettrePion(position, LGrille,
											Blanc, Noir, t);

								} else {
									t = grille.mettrePion(position, LGrille,
											Blanc, Noir, t);

									grille.miseAjourGrille(
											miseAjourGrilleApresCapture(grille),
											LGrille, Blanc, Noir);
									JOptionPane
											.showMessageDialog(jFrame,
													"Capture �ffectu�... Vous avez perdu !");
									BInterrompre.setEnabled(false);
									IInterrompre.setEnabled(false);
									BNouveau.setEnabled(true);
									INouveau.setEnabled(true);
									jeuEnCours = false;

								}
								break;
							}

							case CAPTURE: {

								t = grille.mettrePion(position, LGrille, Blanc,
										Noir, t);

								grille.miseAjourGrille(
										miseAjourGrilleApresCapture(grille),
										LGrille, Blanc, Noir);
								JOptionPane
										.showMessageDialog(jFrame,
												"Capture �ffectu�... Bravo vous avez gagn� !");
								BInterrompre.setEnabled(false);
								IInterrompre.setEnabled(false);

								BNouveau.setEnabled(true);
								INouveau.setEnabled(true);
								jeuEnCours = false;

								break;
							}

							case SUICIDE: {
								JOptionPane
										.showMessageDialog(
												jFrame,
												"Le suicide est interdit, veuillez choisir une autre case  !",
												"Suicide interdit",
												JOptionPane.WARNING_MESSAGE);
								break;
							}

							}

						}
					}

				});
				Blanc = new JBlanc();
				Blanc.setIcon(new ImageIcon(getClass().getResource(
						"/fr/alma/images/blanc.PNG")));
				Blanc.setLocation(new Point(57, 146));
				Blanc.setSize(new Dimension(30, 30));
				Blanc.setVisible(false);
				Noir = new JNoir();
				Noir.setIcon(new ImageIcon(getClass().getResource(
						"/fr/alma/images/noir.PNG")));
				Noir.setSize(new Dimension(30, 30));
				Noir.setLocation(new Point(15, 146));
				Noir.setVisible(false);
				jPanel = new JPanel();
				jPanel.setLayout(null);
				LGrille.add(Noir, null);
				LGrille.add(Blanc, null);
				jPanel.add(getPGrille(), null);
				jPanel.add(getPAtarigo(), null);
				jPanel.add(getPJeu(), null);
			}
			return jPanel;
		}

		/**
		 * This Method initializes the Grid.
		 * @return javax.swing.JPanel
		 */
		private JPanel getPGrille() {
			if (PGrille == null) {
				PGrille = new JPanel();
				PGrille.setLayout(new GridBagLayout());
				PGrille.setBorder(BorderFactory.createTitledBorder(null,
						" Grille : ", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Dialog",
								Font.BOLD, 12), new Color(51, 51, 51)));
				PGrille.setSize(new Dimension(311, 325));
				PGrille.setLocation(new Point(20, 160));
				PGrille.add(LGrille, new GridBagConstraints());
			}
			return PGrille;
		}

		/**
		 * This Method initializes the logo panel.
		 * @return javax.swing.JPanel
		 */
		private JPanel getPAtarigo() {
			if (PAtarigo == null) {
				Logo = new JLabel();
				Logo.setIcon(new ImageIcon(getClass().getResource(
						"/fr/alma/images/logo.png")));
				Logo.setSize(new Dimension(103, 98));
				Logo.setLocation(new Point(415, 12));

				PAtarigo = new JPanel();
				PAtarigo.setLayout(null);
				PAtarigo.setBorder(BorderFactory
						.createEtchedBorder(EtchedBorder.RAISED));
				PAtarigo.setSize(new Dimension(535, 125));
				PAtarigo.setLocation(new Point(20, 20));
				PAtarigo.add(ATarigo, null);
				PAtarigo.add(Logo, null);
			}
			return PAtarigo;
		}

		/**
		 * This Method initializes the panel Jeu.
		 * 
		 * @return javax.swing.JPanel
		 */
		private JPanel getPJeu() {
			if (PJeu == null) {
				PJeu = new JPanel();
				PJeu.setLayout(null);
				PJeu.setBorder(BorderFactory.createTitledBorder(null,
						" Jeu : ", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Dialog",
								Font.BOLD, 12), new Color(51, 51, 51)));
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
		 * INITIALISATION LES BOUTONS
		 ****************************************************************/

		/**
		 * This Method initializes the button Nouveau.
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
		 * This Method initializes the button Apropos.
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

		/**
		 * This Method initializes the button Quitter.
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

		/**
		 * This method initializes BInterrompre
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
				BInterrompre
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent e) {
								interrompreJeu();
							}

						});
			}
			return BInterrompre;
		}

		/****************************************************************
		 * TREATMENT OG THE GRID
		 ****************************************************************/

		/**
		 * Method which erase the grid and initializes its contents
		 */
		private void nouveauJeu() {

			jDialogNouveau.setVisible(true);

		}

		/**
		 * Method used to show the window Apropos
		 */
		private void ShowApropos() {
			JOptionPane
					.showMessageDialog(
							null,
							"Universit� de Nantes\nMaster 1 ALMA\nAtariGo V 1.0\nR�aliser par :\n	- ZERBITA Mohamed El Hadi\n		- Ngassa Hubert",
							"AtariGo V 1.0", 1);
		}

		/****************************************************************
	             INITIALISATION OF THE DIALOG
		 ****************************************************************/
		/**
		 * Method which initializes The dialog
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
		 * Initialization of combobox
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

						jeuEnCours = true;
						LGrille.setEnabled(true);
						BInterrompre.setEnabled(true);
						IInterrompre.setEnabled(true);
						BNouveau.setEnabled(false);
						INouveau.setEnabled(false);

						grille.effacerGrille(LGrille);
						for (int i = 0; i < 9; i++)
							for (int j = 0; j < 9; j++)
								grille.Contenu[i][j].couleur = Couleur.NULL;

						jDialogNouveau.dispose();

						if (jCouleur.getSelectedItem() == "Blanc") {

							t = Tour.NOIR;

							Point position = jouerMachine(grille, t);
							t = grille.mettrePion(position, LGrille, Blanc,
									Noir, t);
						} else {
							t = Tour.NOIR;

						}

					}
				});
			}
			return jBOk;
		}

		/**
		 * This method initializes jBAnnuler
		 * @return javax.swing.JButton
		 */
		private JButton getJBAnnuler() {
			if (jBAnnuler == null) {
				jBAnnuler = new JButton();
				jBAnnuler.setText("Annuler");
				jBAnnuler.setSize(new Dimension(85, 25));
				jBAnnuler.setLocation(new Point(125, 95));
				jBAnnuler
						.addActionListener(new java.awt.event.ActionListener() {
							public void actionPerformed(
									java.awt.event.ActionEvent e) {
								jDialogNouveau.dispose();
							}
						});
			}
			return jBAnnuler;
		}

		/**
		 * Method to interrupt the game
		 */
		private void interrompreJeu() {
			if (JOptionPane.showConfirmDialog(null,
					"Etes-vous sur de vouloir int�rrompre le jeu en cours ?",
					"Confirmation", 0) == JOptionPane.OK_OPTION
					&& jeuEnCours) {
				BInterrompre.setEnabled(false);
				IInterrompre.setEnabled(false);
				LGrille.setEnabled(false);
				BNouveau.setEnabled(true);
				INouveau.setEnabled(true);
				jeuEnCours = false;
				grille.effacerGrille(LGrille);

			}
		}
	}