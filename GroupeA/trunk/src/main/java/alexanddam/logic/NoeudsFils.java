package main.java.alexanddam.logic;

import java.util.ArrayList;
import java.util.List;

public class NoeudsFils {

    /* le support de notre structure -> un vecteur de type liste chainee
     */
    private List<Noeud_LA> alNoeuds;
    private int arite;  		
    boolean ascendent;

    /*
     * @param  1) arite = nombre maximal des elements dans la structure;
     *         2) ascendent = vrai si les valeurs sont maintenues dans la liste dans ordre croissant et faux si
     *              elles sont dans ordre decroissant
     * @return  Une nouvelle structure NoeudFils = le constructeur
     */
    NoeudsFils(int arite, boolean ascendent) {

        this.arite = arite;
        alNoeuds = new ArrayList<Noeud_LA>();
        this.ascendent = ascendent;
    }

    /*
     * @param  index = l'index de l'element qu'on souhaite recuperer
     * @return  l'element situe a� l'index cherche
     */
    public Noeud_LA get(int index) {

        if(index < 0 || index >= alNoeuds.size()) {
                System.out.println("Index out of range in get");
            return null;
        }

        return alNoeuds.get(index);
    }

    /*
     * @param  index = l'index du l'element qu'on souhaite supprimer
     * @return  l'element supprime a� l'index demande
     */
    public Noeud_LA remove(int index) {
        if(index < 0 || index >= alNoeuds.size()) {
            System.out.println("Index out of range in remove");
            return null;
        }

        return alNoeuds.remove(index);
    }

    /*
     * @param vide
     * @return la taille actuelle de la structure
     */
    public int size() {
        return this.alNoeuds.size();
    }

    /*
     * @param   nla = l'element a� ajouter dans la liste
     * @param   un booleen vrai si l'element a ete ajoute ou faux sinon
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

    /*
     * @param   	nla = le noeud dont on veut l'index à inserer dans la liste, en gardant l'ordonencement
     * @return    	l'index qui peut être positif ou -1 si l'element ne doit pas se trouver dans la liste
     * description : methode privee
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