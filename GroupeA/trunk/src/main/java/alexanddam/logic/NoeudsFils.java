package main.java.alexanddam.logic;

import java.util.ArrayList;

public class NoeudsFils {

    /* le support de notre structure -> un vecteur de type liste chainée
     */
    private ArrayList<Noeud_LA> al;
    private int arite;  		
    boolean ascendent;

    /*
     * @param  1) arite = nombre maximal des éléments dans la structure;
     *         2) ascendent = vrai si les valeurs sont maintenues dans la liste dans ordre croissant et faux si
     *              elles sont dans ordre decroissant
     * @return  Une nouvelle structure NoeudFils = le constructeur
     */
    NoeudsFils(int arite, boolean ascendent) {

        this.arite = arite;
        al = new ArrayList<Noeud_LA>();
        this.ascendent = ascendent;
    }

    /*
     * @param  index = l'index du l'élément qu'on souhaite récuperer
     * @return  l'élément situé à l'index cherché
     */
    public Noeud_LA get(int index) {

        if(index < 0 || index >= al.size()) {
                System.out.println("Index out of range in get");
            return null;
        }

        return al.get(index);
    }

    /*
     * @param  index = l'index du l'élément qu'on souhaite supprimer
     * @return  l'élément supprimé à l'index demandé
     */
    public Noeud_LA remove(int index) {
        if(index < 0 || index >= al.size()) {
                System.out.println("Index out of range in remove");
            return null;
        }

        return al.remove(index);
    }

    /*
     * @param vide
     * @return la taille actuelle de la structure
     */
    public int size() {
        return this.al.size();
    }

    /*
     * @param   nla = l'élément à ajouter dans la liste
     * @param   un booléan vrai si l'élément a été ajouté ou faux sinon
     */
    public boolean addFils(Noeud_LA nla) {
        boolean insere = false;
        int index = -1;

        index = getIndex(nla);
        if(this.al.size() < this.arite) {
            insere = true;
            if(index == -1) {
                    index = 0;
            }
        } else {
            if(index != -1) {
                insere = true;

                // il faut aussi supprimer le premier element
                this.al.remove(0);
                index -= 1;		// l'index doit decrementer, suite a la suppression de l'element en position 0
            }
        }

        if(insere) {
            al.add(index, nla);
        }

        return insere;
    }

    /*
     * @param   nla = le noeud dont on veut l'index à insérer dans la liste, en gardant l'ordonencement
     * @return    l'index qui peut être positif ou -1 si l'élément ne doit pas se trouver dans la liste
     * description : méthode privée
     */
    private int getIndex(Noeud_LA nla) {
        int index = -1, i;

        if(this.al.size() == 0) {
            return 0;
        }

        if(ascendent) {
            if(nla.valeur > al.get(0).valeur) {
                for(i=1; i < al.size(); i++) {
                    if(nla.valeur <= al.get(i).valeur) {
                        break;
                    }
                }
                index = i;
            }
        } else {
            if(nla.valeur < al.get(0).valeur) {
                for(i=1; i < al.size(); i++) {
                    if(nla.valeur >= al.get(i).valeur) {
                        break;
                    }
                }
                index = i;
            }
        }

        return index;
    }

}