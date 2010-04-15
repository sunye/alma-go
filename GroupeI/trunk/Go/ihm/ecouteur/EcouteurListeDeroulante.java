/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package ihm.ecouteur;

import ihm.Fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import coeur.Partie;
import coeur.GoDonnees;

/**
 * Classe permettant d'ecouter toutes les actions des listes deroulantes 
 */
public class EcouteurListeDeroulante implements ActionListener{
	/** La fenetre du jeu principale */
	private Fenetre fenetre;
	/** La partie en cours */
	private Partie partie;
	
	
	/**
	 * Constructeur d'un ecouteur de liste deroulante
	 * @param fenetre La fenetre du jeu principale
	 * @param partie La partie en cours
	 */
	public EcouteurListeDeroulante(Fenetre fenetre, Partie partie) {
		this.fenetre = fenetre;
		this.partie = partie;
	}

	/**
	 * Implementation des reactions aux evenements des listes deroulantes
	 */
	public void actionPerformed(ActionEvent ae) {		
        // Si l'on a selectionne la liste deroulante du joueur blanc dans le menu joueur
		if(ae.getSource() == this.fenetre.getFenetreOptionJoueurs().getListeDeroulanteBlanc()){			
			 String texteSelectionne = this.fenetre.getFenetreOptionJoueurs().getListeDeroulanteBlanc().getSelectedItem().toString();
			 // Si l'on a decide que le joueur blanc soit humain
			 if(texteSelectionne == GoDonnees.texteHumain){
			 	 this.partie.getJoueurBlanc().setType(GoDonnees.HUMAIN);				
			 }
             // Si l'on a decide que le joueur blanc soit soit un ordi au niveau debutant
			 else if(texteSelectionne == GoDonnees.texteIA_Debutant){
				 this.partie.getJoueurBlanc().setType(GoDonnees.IA);
				 this.partie.getJoueurBlanc().setNiveauIA(GoDonnees.IA_Debutant);				
			 }
			 // Si l'on a decide que le joueur blanc soit un ordi au niveau intermediaire
			 else if(texteSelectionne == GoDonnees.texteIA_Intermediaire){
				 this.partie.getJoueurBlanc().setType(GoDonnees.IA);
				 this.partie.getJoueurBlanc().setNiveauIA(GoDonnees.IA_Intermediaire);					
			 } 
             // Si l'on a decide que le joueur blanc soit un ordi au niveau expert
			 else if(texteSelectionne == GoDonnees.texteIA_Expert){
				 this.partie.getJoueurBlanc().setType(GoDonnees.IA);
				 this.partie.getJoueurBlanc().setNiveauIA(GoDonnees.IA_Expert);					
			 } 
		}		
        // Si l'on a selectionne la liste deroulante du joueur Noir du menu joueur
		else if(ae.getSource() == this.fenetre.getFenetreOptionJoueurs().getListeDeroulanteNoir()){
			String texteSelectionne = this.fenetre.getFenetreOptionJoueurs().getListeDeroulanteNoir().getSelectedItem().toString();
			// Si l'on a decide que le joueur noir soit humain
			if(texteSelectionne == GoDonnees.texteHumain){
				this.partie.getJoueurNoir().setType(GoDonnees.HUMAIN);				
			}
            // Si l'on a decide que le joueur noir soit soit un ordi au niveau debutant
			else if(texteSelectionne == GoDonnees.texteIA_Debutant){
				this.partie.getJoueurNoir().setType(GoDonnees.IA);
				this.partie.getJoueurNoir().setNiveauIA(GoDonnees.IA_Debutant);					
			}
			// Si l'on a decide que le joueur noir soit un ordi au niveau intermediaire
			else if(texteSelectionne == GoDonnees.texteIA_Intermediaire){
				this.partie.getJoueurNoir().setType(GoDonnees.IA);
				this.partie.getJoueurNoir().setNiveauIA(GoDonnees.IA_Intermediaire);
			} 
            // Si l'on a decide que le joueur noir soit un ordi au niveau expert
			else if(texteSelectionne == GoDonnees.texteIA_Expert){
				this.partie.getJoueurNoir().setType(GoDonnees.IA);
				this.partie.getJoueurNoir().setNiveauIA(GoDonnees.IA_Expert);	
			} 		
		}
		else if(ae.getSource() == this.fenetre.getfenetreobjectifcapture().getListeDeroulanteBlanc()){
			String texteSelectionne = this.fenetre.getfenetreobjectifcapture().getListeDeroulanteBlanc().getSelectedItem().toString();
			
			for(int i=0;i<GoDonnees.capturemax;i++){
				Integer s=new Integer(i);
				if (texteSelectionne.equals(s.toString())){
					this.partie.getPlateau().setobjectifblanc(i);		
				}
				
			}
			
		}
		else if(ae.getSource() == this.fenetre.getfenetreobjectifcapture().getListeDeroulanteNoir()){
			String texteSelectionne = this.fenetre.getfenetreobjectifcapture().getListeDeroulanteNoir().getSelectedItem().toString();
			
			for(int i=0;i<GoDonnees.capturemax;i++){
				Integer s=new Integer(i);
				if (texteSelectionne.equals(s.toString())){
					this.partie.getPlateau().setojectifnoir(i);
				}
				
			}
			
		}
		
	}
	
	
}
