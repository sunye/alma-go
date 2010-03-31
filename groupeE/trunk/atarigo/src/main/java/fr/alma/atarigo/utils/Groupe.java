package fr.alma.atarigo.utils;

import java.util.HashSet;

import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import java.util.Collection;
import java.util.Set;

public class Groupe {

    private HashSet<Stone> stones;
    private HashSet<Groupe> eyes;
    private int libertes;
    private PionVal couleur;

    public Groupe(PionVal couleur) {
        super();
        this.couleur = couleur;
        stones = new HashSet<Stone>();
        eyes = new HashSet<Groupe>();
    }

    public boolean contains(Stone p) {
        return stones.contains(p);
    }

    /**
     * Add a Stone to this group, but only a Stone with the same color.
     * @param p The Stone to add
     * @throws BadCouleurException
     */
    public void addStone(Stone p) throws BadCouleurException {
        if (p.getCouleur() == couleur) {
            this.stones.add(p);
        } else {
            throw new BadCouleurException("Ce groupe est de couleur " + couleur.valeur());
        }
    }

    /**
     * Checks if the <em>autre</em> Groupe share at least one Stone with <em>this</em>.
     * @param autre
     * @return
     */
    public boolean intersectionNonVide(Groupe autre) {
        if (!couleur.equals(autre.getCouleur())) {
            return false;
        }
        for (Stone p : stones) {
            if (autre.contains(p)) {
                return true;
            }
        }
        return false;
    }

    public Object getCouleur() {
        return couleur;
    }

    public Groupe fusion(Groupe autre) {
        Groupe sortie = null;
        if (this.intersectionNonVide(autre)) {
            //Si on partage un pion, alors on doit fusionner les deux groupes.
            sortie = new Groupe(couleur);
            sortie.stones.addAll(stones);
            sortie.stones.addAll(autre.stones);
        }

        return sortie;
    }

    public void addAll(Groupe autre) {
        if (this.getCouleur() == autre.getCouleur()) {
            this.stones.addAll(autre.stones);
        }
    }

    public void addAll(Collection<Stone> collec) throws BadCouleurException{
        for(Stone pion:collec){
            this.addStone(pion);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Groupe other = (Groupe) obj;
        if (this.stones != other.stones && (this.stones == null || !this.stones.equals(other.stones))) {
            return false;
        }
        if (this.couleur != other.couleur && (this.couleur == null || !this.couleur.equals(other.couleur))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.stones != null ? this.stones.hashCode() : 0);
        hash = 59 * hash + (this.couleur != null ? this.couleur.hashCode() : 0);
        return hash;
    }

    public void addEye(Groupe group) {
        eyes.add(group);
    }

    public int nbEyes(){
        return eyes.size();
    }

    public Set<Groupe> getEyes(){
        return eyes;
    }

    public int getLibertes() {
        return libertes;
    }

    public void setLibertes(int libertes) {
        this.libertes = libertes;
    }

    public Collection<Stone> getStones() {
        return stones;
    }

    public int size(){
        return stones.size();
    }

}
