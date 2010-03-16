package fr.alma.atarigo;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Classe implémentant la liste de toutes les listes de pierres de go.
 * @author vincent
 *
 */
public class ListeGroupes implements Cloneable{

	public LinkedList<Groupe> listeG;
	
	public ListeGroupes(){
		listeG = new LinkedList<Groupe>();
	}
	
	/**
	 * renvoie une copie de la liste de groupes.
	 */
	public ListeGroupes clone(){
		ListeGroupes listeGroupes;
		listeGroupes = new ListeGroupes();
		Groupe groupEnCours;
 		ListIterator itr = listeG.listIterator();
 		while(itr.hasNext()){
 			groupEnCours = (Groupe)itr.next();
 			listeGroupes.listeG.add(groupEnCours.clone());
 		}
		return listeGroupes;
	}

	
	public LinkedList<Groupe >getListe(){
		return listeG;
	}
	
	/**
	 * cherche le groupe contenant la position recherchée
	 */
 	public Groupe getGroupe(Position position){
 		Groupe groupEnCours;
 		Groupe groupe = null;
 		ListIterator itr = listeG.listIterator();
 		while(itr.hasNext()){
 			groupEnCours = (Groupe)itr.next();
 			//groupEnCours.afficher();
 			if(groupEnCours.hasPos(position)){
 				groupe = groupEnCours;
 			}
 		}
 		return groupe;
 	}

 	/**
 	 * affiche tous les groupes du jeu
 	 */
	public void afficher(){
		System.out.println("affichage de la liste des groupes :");
		System.out.println(toString());
		Groupe groupeEnCours;
		ListIterator itr = listeG.listIterator();
 		while(itr.hasNext()){
 			groupeEnCours = (Groupe)itr.next();
 			System.out.print(groupeEnCours.toString());
 			groupeEnCours.afficher();
 			
 		}
		System.out.println("fin de l'affichage");
	}
	
	/**
	 * renvoie une nouvelle liste de groupe en fonction d'un pion joué
	 * il y a donc clonage de la liste en cours, actualisation sur le clone et renvoi du clone.
	 */
 	public ListeGroupes calculerGroupes(Plateau plateau, Position position,Pion joueur){
 		boolean estAjoute=false;
 		Groupe nouveauGroupe = null;
		// clonage de la liste de groupe
 		ListeGroupes nouvListeG=null;
		nouvListeG = (ListeGroupes) this.clone();
		for (Direction maDirection : Direction.values()) {
			if(plateau.estValide(position.voisine(maDirection))){
				//System.out.println("position voisine : "+position.voisine(maDirection).lireColonne()+position.voisine(maDirection).lireLigne());
				if (plateau.lireCase(position.voisine(maDirection)) == joueur & !estAjoute){
					nouvListeG.getGroupe(position.voisine(maDirection)).ajouter(position);
					nouveauGroupe = nouvListeG.getGroupe(position.voisine(maDirection));
					estAjoute=true;
				}
				else if (plateau.lireCase(position.voisine(maDirection)) == joueur && estAjoute && !nouveauGroupe.hasPos(position.voisine(maDirection))){
						Groupe groupeOld = nouvListeG.getGroupe(position.voisine(maDirection));
						nouveauGroupe = fusionner(nouveauGroupe,nouvListeG.getGroupe(position.voisine(maDirection)));
						nouvListeG.listeG.remove(groupeOld);
						estAjoute=true;
				}
			}
		}
		//n'appartient à aucun groupe donc constitue un groupe à lui seul
		if (!estAjoute){
			LinkedList<Position> linkedList = new LinkedList<Position>();
 			linkedList.add(position);
 			Groupe groupe = new Groupe(linkedList,plateau.lireCase(position));
 			nouvListeG.listeG.add(groupe);
 			nouveauGroupe = groupe;
		}
				
 		return nouvListeG;
	}
 	
 	/**
 	 * ajoute le groupe 1 au groupe 2 et renvoie le groupe 1.
 	 * @param groupe1
 	 * @param groupe2
 	 * @return le groupe fusionné
 	 */
 	public Groupe fusionner(Groupe groupe1,Groupe groupe2){
 		groupe1.linkedPions.addAll(groupe2.linkedPions);
 		return groupe1;
 	}
	
}
