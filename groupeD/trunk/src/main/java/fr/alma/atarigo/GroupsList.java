package fr.alma.atarigo;

import java.util.ArrayList;
import java.util.ListIterator;

/**
 * GroupsList.java represent the list of all the list of stones in a Atarigo game.
 * @author VINCENT FERREIRA, ADRIEN GUILLE
 *
 */
public class GroupsList implements Cloneable{
	/** encapsulated groups of stones*/
	public ArrayList<Group> gList;
	/**
	 * logic constructor
	 */
	public GroupsList(){
		gList = new ArrayList<Group>();
	}
	
	/**
	 * return a copy of the list of groups.
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

	/**
	 * accessor for list of groups
	 */
	public ArrayList<Group >getListe(){
		return gList;
	}
	
	/**
	 * return the group containing the position
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
 	 * print the groups of the current game
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
	 * calculate a new list of group after a new move has been played.
	 * there is a copy of the current list, actualisation on the clone.
	 * @return the new list
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
			ArrayList<Position> linkedList = new ArrayList<Position>();
 			linkedList.add(position);
 			Group groupe = new Group(linkedList,plateau.readCell(position));
 			nouvListeG.gList.add(groupe);
 			nouveauGroupe = groupe;
		}
				
 		return nouvListeG;
	}
 	
 	/**
 	 * add the group2 to the group1 and return the group1 .
 	 * @param group1
 	 * @param group2
 	 * @return merged group
 	 */
 	public Group fusionner(Group groupe1,Group groupe2){
 		groupe1.linkedStones.addAll(groupe2.linkedStones);
 		return groupe1;
 	}
 	
 	/**
 	 * return if the list is empty or not.
 	 */
 	public boolean isEmpty(){
 		return gList.size()==0;
 	}
 	
 	/**
 	 * return the total amount of stones of the list of groups.
 	 */
 	public int totalStones(){
 		int nb = 0;
 		for(Group current : gList){
 			nb += current.linkedStones.size();
 		}
 		return nb;
 	}
	
}
