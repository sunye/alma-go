/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */

package ihm;

import ia.CompteArebours;
import ihm.ecouteur.EcouteurBouton;
import ihm.ecouteur.EcouteurFenetre;
import ihm.ecouteur.EcouteurListeDeroulante;
import ihm.ecouteur.EcouteurMenu;
import ihm.ecouteur.EcouteurSlider;
import ihm.ecouteur.EcouteurSouris;
import coeur.Coeur;

/**
 * Classe permettant de creer l'interface graphique
 */
public class Ihm {	
	/**
	 * Construteur de l'IHM
	 * @param coeur le coeur sur lequel on se branche
	 */
	public Ihm(Coeur coeur){
		/* La fenetre */
		Fenetre fenetre = new Fenetre(coeur.getPlateau(),coeur.getPartie());
		/* Les ecouteurs */
		EcouteurFenetre ecouteurFenetre = new EcouteurFenetre();
		EcouteurSouris ecouteurSouris = new EcouteurSouris(coeur.getPartie());
		EcouteurMenu ecouteurMenu = new EcouteurMenu(fenetre, coeur.getPartie());
		EcouteurBouton ecouteurBouton = new EcouteurBouton(fenetre ,coeur.getPartie());
		EcouteurListeDeroulante ecouteurListeDeroulante = new EcouteurListeDeroulante(fenetre,coeur.getPartie());
		EcouteurSlider ecouteurSlider = new EcouteurSlider(fenetre,coeur.getPartie());
		
		/* Ajout des ecouteurs */
		fenetre.addWindowListener(ecouteurFenetre);
		
		fenetre.getPanneau().addMouseListener(ecouteurSouris);
		

		
		fenetre.getNouvellePartie().addActionListener(ecouteurMenu);
		fenetre.getApropos().addActionListener(ecouteurMenu);
		fenetre.getQuitter().addActionListener(ecouteurMenu);	
		fenetre.getOptionJoueur().addActionListener(ecouteurMenu);
		fenetre.getOptionChronometre().addActionListener(ecouteurMenu);		
		fenetre.getOptionobjectifcapture().addActionListener(ecouteurMenu);
		
		fenetre.getBoutonForcerAjouer().addActionListener(ecouteurBouton);		
		fenetre.getBoutonAbandonner().addActionListener(ecouteurBouton);
		fenetre.getBoutonAide().addActionListener(ecouteurBouton);
		fenetre.getFenetreOptionJoueurs().getBoutonOK().addActionListener(ecouteurBouton);
		fenetre.getFenetreOptionChrono().getBoutonOK().addActionListener(ecouteurBouton);
		fenetre.getfenetreobjectifcapture().getBoutonOK().addActionListener(ecouteurBouton);
	
		
		fenetre.getFenetreOptionJoueurs().getListeDeroulanteBlanc().addActionListener(ecouteurListeDeroulante);
		fenetre.getFenetreOptionJoueurs().getListeDeroulanteNoir().addActionListener(ecouteurListeDeroulante);	
		fenetre.getfenetreobjectifcapture().getListeDeroulanteBlanc().addActionListener(ecouteurListeDeroulante);
		fenetre.getfenetreobjectifcapture().getListeDeroulanteNoir().addActionListener(ecouteurListeDeroulante);
		
		fenetre.getFenetreOptionChrono().getReglageChrono().addChangeListener(ecouteurSlider);
		
		/* Fixation mise à jour */
		coeur.getPartie().setActualisation(fenetre);
		
		/* Creation du compte a rebours */
		CompteArebours compteArebours = new CompteArebours(fenetre,coeur.getPartie());
				
		/* Fixation du compte a rebours dans la partie */
		coeur.getPartie().setCompteArebours(compteArebours);
		
		

		
	}	
}
