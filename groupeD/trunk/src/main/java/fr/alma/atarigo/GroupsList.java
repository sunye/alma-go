package fr.alma.atarigo;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Classe implémentant la liste de toutes les listes de pierres de go.
 * @author vincent
 *
 */
public class GroupsList implements Cloneable{

	public LinkedList<Group> gList;
	
	public GroupsList(){
		gList = new LinkedList<Group>();
	}
	
	/**
	 * renvoie une copie de la liste de groupes.
	 */
	public GroupsList clone(){
		GroupsList groupList;
		groupList = new GroupsList();
		Group groupEnCours;
 		ListIterator itr = gList.listIterator();
 		while(itr.hasNext()){
 			groupEnCours = (Group)itr.next();
 			groupList.gList.add(groupEnCours.clone());
 		}
		return groupList;
	}

	
	public LinkedList<Group >getListe(){
		return gList;
	}
	
	/**
	 * cherche le groupe contenant la position recherchée
	 */
 	public Group getGroup(Position position){
 		Group currentGroup;
 		Group group = null;
 		ListIterator itr = gList.listIterator();
 		while(itr.hasNext()){
 			currentGroup = (Group)itr.next();
 			//groupEnCours.afficher();
 			if(currentGroup.hasPos(position)){
 				group = currentGroup;
 			}
 		}
 		return group;
 	}

 	/**
 	 * affiche tous les groupes du jeu
 	 */
	public void print(){
		System.out.println("affichage de la liste des groupes :");
		System.out.println(toString());
		Group currentGroup;
		ListIterator itr = gList.listIterator();
 		while(itr.hasNext()){
 			currentGroup = (Group)itr.next();
 			System.out.print(currentGroup.toString());
 			currentGroup.print();
 			
 		}
		System.out.println("fin de l'affichage");
	}
	
	/**
	 * renvoie une nouvelle liste de groupe en fonction d'un pion joué
	 * il y a donc clonage de la liste en cours, actualisation sur le clone et renvoi du clone.
	 */
 	public GroupsList updateGroups(Goban plateau, Position position,Stone joueur){
 		boolean estAjoute=false;
 		Group nouveauGroupe = null;
		// clonage de la liste de groupe
 		GroupsList nouvListeG=null;
		nouvListeG = (GroupsList) this.clone();
		for (Direction maDirection : Direction.values()) {
			if(plateau.isValid(position.neighbor(maDirection))){
				//System.out.println("position voisine : "+position.voisine(maDirection).lireColonne()+position.voisine(maDirection).lireLigne());
				if (plateau.readCell(position.neighbor(maDirection)) == joueur & !estAjoute){
					nouvListeG.getGroup(position.neighbor(maDirection)).add(position);
					nouveauGroupe = nouvListeG.getGroup(position.neighbor(maDirection));
					estAjoute=true;
				}
				else if (plateau.readCell(position.neighbor(maDirection)) == joueur && estAjoute && !nouveauGroupe.hasPos(position.neighbor(maDirection))){
						Group groupeOld = nouvListeG.getGroup(position.neighbor(maDirection));
						nouveauGroupe = fusionner(nouveauGroupe,nouvListeG.getGroup(position.neighbor(maDirection)));
						nouvListeG.gList.remove(groupeOld);
						estAjoute=true;
				}
			}
		}
		//n'appartient à aucun groupe donc constitue un groupe à lui seul
		if (!estAjoute){
			LinkedList<Position> linkedList = new LinkedList<Position>();
 			linkedList.add(position);
 			Group groupe = new Group(linkedList,plateau.readCell(position));
 			nouvListeG.gList.add(groupe);
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
 	public Group fusionner(Group groupe1,Group groupe2){
 		groupe1.linkedStones.addAll(groupe2.linkedStones);
 		return groupe1;
 	}
 	
 	public boolean isEmpty(){
 		return gList.size()==0;
 	}
 	
 	public int totalStones(){
 		int nb = 0;
 		for(Group current : gList){
 			nb += current.linkedStones.size();
 		}
 		return nb;
 	}
	
}
