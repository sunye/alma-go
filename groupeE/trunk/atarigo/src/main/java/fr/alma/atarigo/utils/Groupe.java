package fr.alma.atarigo.utils;

import java.util.HashSet;

import fr.alma.atarigo.utils.exceptions.BadCouleurException;

public class Groupe {

    private HashSet<Pion> pions;
    private HashSet<Pion> yeux;
    private int libertes;
    private PionVal couleur;

    public Groupe(PionVal couleur) {
        super();
        this.couleur = couleur;
        pions = new HashSet<Pion>();
        yeux = new HashSet<Pion>();
    }

    public boolean contains(Pion p) {
        return pions.contains(p);
    }

    public void addPion(Pion p) throws BadCouleurException {
        if (p.getCouleur() == couleur) {
            this.pions.add(p);
        } else {
            throw new BadCouleurException("Ce groupe est de couleur " + couleur.valeur());
        }
    }

    public boolean intersectionNonVide(Groupe autre) {
        if (!couleur.equals(autre.getCouleur())) {
            return false;
        }
        for (Pion p : pions) {
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
            sortie.pions.addAll(pions);
            sortie.pions.addAll(autre.pions);
        }

        return sortie;
    }

    public void addAll(Groupe autre) {
        if (this.getCouleur() == autre.getCouleur()) {
            this.pions.addAll(autre.pions);
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
        if (this.pions != other.pions && (this.pions == null || !this.pions.equals(other.pions))) {
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
        hash = 59 * hash + (this.pions != null ? this.pions.hashCode() : 0);
        hash = 59 * hash + (this.couleur != null ? this.couleur.hashCode() : 0);
        return hash;
    }

    public void addOeil(Pion oeil) {
        yeux.add(oeil);
    }

    public int getLibertes() {
        return libertes;
    }

    public void setLibertes(int libertes) {
        this.libertes = libertes;
    }

    public Iterable<Pion> getPions() {
        return pions;
    }

    public int size(){
        return pions.size();
    }

}
