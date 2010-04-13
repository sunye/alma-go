package fr.alma.atarigo.utils;

import java.util.HashSet;

import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import java.util.Collection;
import java.util.Set;

/**
 * This class describes a group of stones. We have here the stones (all the same color), the eyes, the liberties, and the group's color.
 * @author judu
 */
public class Groupe {

    /// Stones with the same color
    private Set<Stone> stones;
    private Set<Groupe> eyes;
    /// All the free places surrounding the group
    private Set<Stone> liberties;
    /// The color of the stones.
    private PionVal couleur;


    /**
     * We define a group by its color. A colorless group does not mean anything.
     * @param couleur
     */
    public Groupe(PionVal couleur) {
        super();
        this.couleur = couleur;
        stones = new HashSet<Stone>();
        eyes = new HashSet<Groupe>();
    }

    /**
     * Check if the group contains the Stone.
     * @param p
     * @return
     */
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

    public PionVal getCouleur() {
        return couleur;
    }


    /**
     * Creates a new group containing the union of <em>this</em>, and <em>other</em>.
     * @param other
     * @return
     */
    public Groupe fusion(Groupe other) {
        Groupe sortie = null;
        if (this.intersectionNonVide(other)) {
            //Si on partage un pion, alors on doit fusionner les deux groupes.
            sortie = new Groupe(couleur);
            sortie.stones.addAll(stones);
            sortie.stones.addAll(other.stones);
        }

        return sortie;
    }

    /**
     * Adds all the stones of <em>other</em> to this.
     * @param other
     */
    public void addAll(Groupe other) {
        if (this.getCouleur() == other.getCouleur()) {
            this.stones.addAll(other.stones);
        }
    }

    /**
     * Adds a collection of stones, one-by-one, to ensure all added stones have the same color.
     * @param collec
     * @throws BadCouleurException
     */
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

    @Override
    public String toString() {
        StringBuilder toStr = new StringBuilder();
        toStr.append("{ ").append("color : ").append(this.couleur).append(", ");
        toStr.append("Stones : ").append(stones.toString()).append(", ");
        toStr.append("Eyes : ").append(eyes).append(" }");
        return toStr.toString();
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

    public Set<Stone> getLiberties() {
        return liberties;
    }

    public void setLiberties(Set<Stone> libertes) {
        this.liberties = libertes;
    }

    public Collection<Stone> getStones() {
        return stones;
    }

    public int size(){
        return stones.size();
    }

}
