package fr.alma.atarigo.utils;

import fr.alma.atarigo.utils.exceptions.BadGobanStateException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class is a Set of modifications, describing the effects of a move on the goban during the game
 * @author judu
 */
public class PlayMove {

    private Set<Modif> diff;
    private Modif putStone;
    private ArrayList<Groupe> groups;
    private int eval;

    public int getEval() {
        return eval;
    }

    public void setEval(int eval) {
        this.eval = eval;
    }

    /**
     * Retourne le premier (normalement le seul) groupe de pierres contenant la pierre passée en paramètre.
     * @param pion
     * @return
     */
    public Groupe getGroupeContaining(Stone pion) {
        for (Groupe groupe : groups) {
            if (groupe.contains(pion)) {
                return groupe;
            }
        }
        return null;
    }

    public ArrayList<Groupe> getGroupes() {
        return groups;
    }

    public PlayMove() {
        diff = new HashSet<Modif>();
        groups = new ArrayList<Groupe>();
    }

    public void addModif(Modif modif) {
        diff.add(modif);
    }

    public void apply(PionVal[][] goban) throws BadGobanStateException {
        Iterator<Modif> iterator = diff.iterator();
        ArrayList<Modif> done = new ArrayList<Modif>(diff.size());
        try {
            while (iterator.hasNext()) {
                Modif curM = iterator.next();
                curM.apply(goban);
                done.add(curM);
            }
        } catch (BadGobanStateException ex) {
            iterator = done.iterator();
            while(iterator.hasNext()){
                Modif curM = iterator.next();
                curM.revert(goban);
            }
            throw ex;
        }
    }

    public void revert(PionVal[][] goban) throws BadGobanStateException {
        Iterator<Modif> iterator = diff.iterator();
        ArrayList<Modif> done = new ArrayList<Modif>(diff.size());
        try {
            while (iterator.hasNext()) {
                Modif curM = iterator.next();
                curM.revert(goban);
                done.add(curM);
            }
        } catch (BadGobanStateException ex) {
            iterator = done.iterator();
            while(iterator.hasNext()){
                Modif curM = iterator.next();
                curM.apply(goban);
            }
            throw ex;
        }
    }

    public void setGroupes(ArrayList<Groupe> groupes) {
        groups = groupes;
    }

    public Modif getPutStone() {
        return putStone;
    }

    public void setPutStone(Modif putPion) {
        this.putStone = putPion;
    }

    
}
