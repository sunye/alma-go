// File Group.java
// Last commited $Date$
// By $Author$
// Revision $Rev$
//
// Copyright (C) 2010 Clotilde Massot & Julien Durillon
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//
// The above copyright notice and this permission notice shall be
// included in all copies or substantial portions of this program.
package fr.alma.atarigo.utils;

import java.util.HashSet;

import fr.alma.atarigo.utils.exceptions.BadCouleurException;
import java.util.Collection;
import java.util.Set;

/**
 * This class describes a group of stones. We have here the stones (all the same color), the eyes, the liberties, and the group's color.
 * @author judu
 */
public class Group {

    /// Stones with the same color
    private Set<Stone> stones;
    private Set<Group> eyes;
    /// All the free places surrounding the group
    private Set<Stone> liberties;
    /// The color of the stones.
    private StoneVal couleur;


    /**
     * We define a group by its color. A colorless group does not mean anything.
     * @param couleur
     */
    public Group(StoneVal couleur) {
        super();
        this.couleur = couleur;
        stones = new HashSet<Stone>();
        eyes = new HashSet<Group>();
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
     * Checks if the <em>autre</em> Group share at least one Stone with <em>this</em>.
     * @param autre
     * @return
     */
    public boolean notEmptyIntersection(Group autre) {
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

    public StoneVal getCouleur() {
        return couleur;
    }


    /**
     * Creates a new group containing the union of <em>this</em>, and <em>other</em>.
     * @param other
     * @return
     */
    public Group fusion(Group other) {
        Group sortie = null;
        if (this.notEmptyIntersection(other)) {
            //Si on partage un pion, alors on doit fusionner les deux groupes.
            sortie = new Group(couleur);
            sortie.stones.addAll(stones);
            sortie.stones.addAll(other.stones);
        }

        return sortie;
    }

    /**
     * Adds all the stones of <em>other</em> to this.
     * @param other
     */
    public void addAll(Group other) {
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
        final Group other = (Group) obj;
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



    public void addEye(Group group) {
        eyes.add(group);
    }

    public int nbEyes(){
        return eyes.size();
    }

    public Set<Group> getEyes(){
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
