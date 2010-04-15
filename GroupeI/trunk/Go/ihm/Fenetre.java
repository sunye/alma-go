/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ihm;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import coeur.Actualisation;
import coeur.GoDonnees;
import coeur.Partie;
import coeur.Plateau;

/**
 * Classe permettant de creer la fenetre principale
 */
public class Fenetre extends JFrame implements Actualisation {

	/** Generation d'un serial pour cette fenetre */
	private static final long serialVersionUID = 1L;
	/** Le plateau où on joue */
	private Plateau plateau;
	/** La zone graphique du plateau */
	private Panneau panneau;
	/** La partie */
	private Partie partie;	
	/** le panel de chronometrage et pour forcer a jouer */
	private JPanel chronometrage;
	/** le nombre de pions noir */
	private JLabel nbrNoir;
	/** le nombre de pions blanc */
	private JLabel nbrBlanc;
	/** A qui c'est le tour*/
	private JLabel auTourDe;
	/** Le container d'affichage */
	private Container container;
	/** Nouvelle partie */
	private JMenuItem nouvellePartie;
	/** Option concernants les deux joueurs */
	private JMenuItem optionJoueur;
	/** Parametrage du temps maximal accorde a l'ia pour jouer */
	private JMenuItem optionChronometre;	
	/** A propos */
	private JMenuItem apropos;
	/** Quitter */
	private JMenuItem quitter;	
	/** zone de saisie*/
	private JLabel tempsRestant;
	/** bouton forcer a jouer */
	private JButton boutonForcerAjouer;
	/** bouton abandonner **/
	private JButton boutonAbandonner;
	/** bouton d'aide **/
	private JButton boutonAide;
	/** menu d'option pour les objectif de capture **/
	private JMenuItem optionobjectifcapture;

	/** Fenetre permettant de parametrer les joueurs */
	private FenetreOptionJoueurs fenetreOptionJoueurs;
	/** Fenetre permettant de parametrer le chronometre */
	private FenetreOptionChrono fenetreOptionChrono;
	/** Fenetre permettant de regler les objectifs de capture **/
	private FenetreOptionobjectifcapture fenetreobjectifcapture;

	/**
	 * Pour faire la mise en page de la fenêtre
	 */
	private void miseEnPage(){
		container = this.getContentPane();
		container.setLayout(new BorderLayout());
		
		// Creations des composants
		this.panneau = new Panneau(plateau);	
		JPanel droite = paneldroite();	
		JPanel centre = panelCentre();
		
		// Ajout des composants
		this.setJMenuBar(menu());
	
		this.getContentPane().add(droite, BorderLayout.EAST);	
		this.getContentPane().add(centre, BorderLayout.CENTER);
	}
	
	/**
	 * Le centre du jeu compose des lettres et du plateau.
	 * @return le centre du jeu
	 */
	private JPanel panelCentre(){
		JPanel tmp = new JPanel();	
		tmp.setLayout(new BorderLayout());
		
        // Creation du composant
		this.panneau = new Panneau(plateau);			
		
        // Ajout du composant 		
		tmp.add(panneau, BorderLayout.CENTER);
		
		return tmp;
	}
	
	
	
	/**
	 * Le panneau de droite qui contient la zone d'info pour savoir le nombre de point de chaque joueur
	 * ,la zone de saisie de coordonnees pour jouer un coup, la liste des coups qui ont ete joue et la zone de chronometrage.
	 * @return le panneau
	 */
	private JPanel paneldroite(){
		JPanel tmp = new JPanel();		
		tmp.setLayout(new GridLayout (3,1));
		
		JPanel infos = panelInfos();
		this.chronometrage = panelChronometrage();	
		
		tmp.add(infos);
		tmp.add(chronometrage);		
		
		this.boutonAbandonner = new JButton("Abandonner");		
		JPanel panelBoutonAbandon = new JPanel();		
     	panelBoutonAbandon.add(boutonAbandonner);
     	
     	this.boutonAide = new JButton("Aide");
       	JPanel panelboutonaide = new JPanel();
     	panelboutonaide.add(boutonAide);
     	
     	//Panel de controle regroupant le bouton d'aide et d'abandon
     	JPanel Panelcontrole = new JPanel();
     	Panelcontrole.setLayout(new GridLayout(2,1));
     	Panelcontrole.add(panelboutonaide);
     	Panelcontrole.add(panelBoutonAbandon);     	
     	
     	tmp.add(Panelcontrole);     		
		
		return tmp;
	}
	
	/**
	 * Le panneau d'informations
	 * @return le panneau
	 */
	private JPanel panelInfos(){
		JPanel tmp = new JPanel();
		tmp.setLayout(new GridLayout(4,2));
	
		// Creation des composants
		JLabel noir = new JLabel("Noir :");
		this.nbrNoir = new JLabel("0");
		JLabel blanc = new JLabel("Blanc :");
		this.nbrBlanc = new JLabel("0");
		JLabel joueur = new JLabel("Au tour de :");
		this.auTourDe = new JLabel("----");
		
			
		JLabel vide = new JLabel(" ");	
			
		// Ajout des composants 
		tmp.add(noir);
		tmp.add(nbrNoir);
		tmp.add(blanc);
		tmp.add(nbrBlanc);
		tmp.add(joueur);
		tmp.add(auTourDe);
		tmp.add(vide);			
		
		return tmp;
	}
	

	
		
	/**
	 * Le panneau de chronometrage
	 * @return le panneau
	 */
	private JPanel panelChronometrage(){
		JPanel tmp = new JPanel();
		tmp.setLayout(new GridLayout(4,1));
		
        //Creation des composants
		JLabel textTempsRestant = new JLabel("  Temps restant :");
		this.tempsRestant = new JLabel("    -- s");
	    Font police = new Font("Arial", Font.BOLD, 30);		 
		this.tempsRestant.setFont(police);
		this.boutonForcerAjouer = new JButton ("Forcer à jouer");
		this.boutonForcerAjouer.setEnabled(false);
		JPanel panelBouton = new JPanel();
		
        //Ajout des composants 
		panelBouton.add(boutonForcerAjouer);
		tmp.add(textTempsRestant);
		tmp.add(tempsRestant);
		tmp.add(panelBouton);		

		return tmp;
	}
	
	/**
	 * Creation du menu	 
	 */
	private JMenuBar menu(){
		// Creation
		JMenuBar tmp = new JMenuBar();
		JMenu menuPartie = new JMenu("Partie");
		JMenu options = new JMenu("Options");
		JMenu aide = new JMenu("?");
		
		apropos = new JMenuItem("A propos");		
		optionJoueur = new JMenuItem("Joueurs");
		optionChronometre = new JMenuItem("Chronometre");
		optionobjectifcapture =new JMenuItem("Objectif de capture");
		nouvellePartie = new JMenuItem("Nouvelle partie");
		quitter = new JMenuItem("Quitter");
	
		//Ajout des Item au menu
		menuPartie.add(nouvellePartie);	;
		menuPartie.addSeparator();
		menuPartie.add(quitter);	
		options.add(optionJoueur);
		options.add(optionChronometre);
		options.add(optionobjectifcapture);
		aide.add(apropos);
		
		//Ajout menus à barre menu
		tmp.add(menuPartie);
		tmp.add(options);
		tmp.add(aide);
		
		return tmp;
	}

	
	/**
	 * Constructeur d'une fenetre de jeu principale
	 * @param plateau Le plateau contenant les pions
	 * @param partie La partie en cours
	 */
	public Fenetre(Plateau plateau, Partie partie) {		
		super("Attari Go");		
		this.plateau = plateau;
		this.partie = partie;
		this.miseEnPage();
		this.setResizable(false);
		this.setBounds(100,100,580,430);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/* La fenêtre d'option des joueurs */
		fenetreOptionJoueurs = new FenetreOptionJoueurs();
		/* La fenêtre d'option du chronometrage */
		fenetreOptionChrono = new FenetreOptionChrono();
		fenetreobjectifcapture = new FenetreOptionobjectifcapture();
	}
	
	/**
	 * Permet de connaitre le panneau d'affichage
	 * @return le panneau
	 */
	public Panneau getPanneau(){
		return this.panneau;
	}
		
	
	/**
	 * Permet d'obtenir le composant texte servant a afficher le temps restant à jouer
	 * @return le temps restant
	 */
	public JLabel getTempsRestant(){
		return this.tempsRestant;
	}
	
	/**
	 * Permet de connaitre le bouton forcer à jouer
	 * @return le bouton pour forcer a jouer
	 */
	public JButton getBoutonForcerAjouer(){
		return this.boutonForcerAjouer;
	}
	
	/**
	 * Getter sur le bouton abandonner
	 * @return le bouton Abandonner
	 *  **/
	public JButton getBoutonAbandonner(){
		return this.boutonAbandonner;
	}
	
	/** 
	 * permet de recuperer le bouton aide
	 * @return le bouton aide
	 * **/
	public JButton getBoutonAide(){
		return this.boutonAide;
	}
	
	/**
	 * Permet de connaitre le menu nouvelle partie
	 * @return le menu nouvelle partie
	 */
	public JMenuItem getNouvellePartie(){
		return this.nouvellePartie;
	}
		
	/**
	 * Permet de connaitre le menu A propos
	 * @return le menu A propos
	 */
	public JMenuItem getApropos(){
		return this.apropos;
	}
		
	/**
	 * Permet de connaitre le menu quitter
	 * @return le menu quitter
	 */
	public JMenuItem getQuitter(){
		return this.quitter;
	}
	
	/**
	 * Permet de connaitre le sous menu joueur du menu option
	 * @return le menu joueur
	 */
	public JMenuItem getOptionJoueur(){
		return this.optionJoueur;
	}
		
	/** 
	 * Permet de connaitre le sous-menu capture du menu option
	 * @return le menu objectif de capture
	 *  **/
	public JMenuItem getOptionobjectifcapture(){
		return optionobjectifcapture;
	}
	
	/**
	 * Permet de connaitre le sous menu chronometre du menu option
	 * @return le menu joueur
	 */
	public JMenuItem getOptionChronometre(){
		return this.optionChronometre;
	}
	
	/**
	 * Permet de recuperer la fenetre concernant la configuration des deux joueurs
	 * @return la fenetre d'option de joueur
	 */
	public FenetreOptionJoueurs getFenetreOptionJoueurs(){
		return this.fenetreOptionJoueurs;
	}
		
	/**
	 * Permet de recuperer la fenetre concernant le reglage du chronometre
	 * @return la fenetre d'option du chronometre
	 */
	public FenetreOptionChrono getFenetreOptionChrono(){
		return this.fenetreOptionChrono;
	}
	
	/** Permet de recuperer l'objectif de capture
	 * @return l'objectif de capture
	 * **/
	public FenetreOptionobjectifcapture getfenetreobjectifcapture(){
		return fenetreobjectifcapture;
	}
	 
	
	/**
	 * Mise à jour de la fenêtre
	 */
	public void miseAJour(){
		// Mise à jour des differents composants graphique
		this.panneau.repaint();
		this.nbrNoir.setText((new Integer(plateau.getNbrPions(GoDonnees.NOIR))).toString());
		this.nbrBlanc.setText((new Integer(plateau.getNbrPions(GoDonnees.BLANC))).toString());
		if(partie.getJoueurActuel().getCouleur()==GoDonnees.NOIR)
			auTourDe.setText("Noir");
		else
			auTourDe.setText("Blanc");
		
		// On regarde si la partie est terminee
		if(!partie.enCours()){
			
				if ((plateau.getNbrPions(GoDonnees.NOIR)>plateau.getNbrPions(GoDonnees.BLANC)) || (partie.getjoueur_gagant()==GoDonnees.NOIR )){
					JOptionPane.showMessageDialog(this,"NOIR gagne","Fin",JOptionPane.INFORMATION_MESSAGE  );
				}
				else{
					if ((plateau.getNbrPions(GoDonnees.NOIR)<plateau.getNbrPions(GoDonnees.BLANC)) || (partie.getjoueur_gagant()==GoDonnees.BLANC )){
						JOptionPane.showMessageDialog(this,"BLANC gagne","Fin",JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(this,"Partie nulle","Fin",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				boutonAbandonner.setEnabled(false);
				boutonAide.setEnabled(false);
				
		}else{
			boutonAbandonner.setEnabled(true);
			boutonAide.setEnabled(true);
		}
	}
}
