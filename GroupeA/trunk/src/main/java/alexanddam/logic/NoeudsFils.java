package main.java.alexanddam.logic;

import java.util.ArrayList;
import java.util.List;

public class NoeudsFils {


    private List<Noeud_LA> alNoeuds; //le support de notre structure -> un vecteur de type liste chainee
    private int arite;  		
    boolean ascendent;

    /**
     * Constructor of the class
     * 
     * @param arite Arity = maximal number of elements in the structure
     * @param ascendent Ascendent  = true if the values are kept in the list in increasing order and false if they are in a decreasing order 
     */
    NoeudsFils(int arite, boolean ascendent) {

        this.arite = arite;
        alNoeuds = new ArrayList<Noeud_LA>();
        this.ascendent = ascendent;
    }

    /**
     * Getter of Noeud_LA with its index
     * @param index Index of the element we want to get
     * @return Researched Element
     */
    public Noeud_LA get(int index) {

        if(index < 0 || index >= alNoeuds.size()) {
                System.out.println("Index out of range in get");
            return null;
        }

        return alNoeuds.get(index);
    }

    /**
     * Get and element and delete it with its index 
     * 
     * @param index Index of the element we want to delete
     * @return Deleted element at the index
     */
    public Noeud_LA remove(int index) {
        if(index < 0 || index >= alNoeuds.size()) {
            System.out.println("Index out of range in remove");
            return null;
        }

        return alNoeuds.remove(index);
    }

    /**
     * Get the size of the List
     * @return current size of the structure
     */
    public int size() {
        return this.alNoeuds.size();
    }

    /**
     * Add an element in the list
     * @param nla Element to add in the list
     * @return True if the element has been added, otherwise, return false
     */
    public boolean addFils(Noeud_LA nla) {
        boolean insere = false;
        int index = -1;

        index = getIndex(nla);
        if(this.alNoeuds.size() < this.arite) {
            insere = true;
            if(index == -1) {
                    index = 0;
            }
        } else {
            if(index != -1) {
                insere = true;

                // il faut aussi supprimer le premier element
                this.alNoeuds.remove(0);
                index -= 1;		// l'index doit decrementer, suite a la suppression de l'element en position 0
            }
        }

        if(insere) {
            alNoeuds.add(index, nla);
        }

        return insere;
    }

    /**
     * Get an index with and element
     * @param nla Node from which we want the index in the list, keeping the  
     * @return Index of the element, or -1 if not found in the list
     */
    private int getIndex(Noeud_LA nla) {
        int index = -1, ind;

        if(this.alNoeuds.size() == 0) {
            return 0;
        }

        if(ascendent) {
            if(nla.valeur > alNoeuds.get(0).valeur) {
                for(ind=1; ind < alNoeuds.size(); ind++) {
                    if(nla.valeur <= alNoeuds.get(ind).valeur) {
                        break;
                    }
                }
                index = ind;
            }
        } else {
            if(nla.valeur < alNoeuds.get(0).valeur) {
                for(ind=1; ind < alNoeuds.size(); ind++) {
                    if(nla.valeur >= alNoeuds.get(ind).valeur) {
                        break;
                    }
                }
                index = ind;
            }
        }

        return index;
    }

}