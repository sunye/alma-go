// File PlayMove.java
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
    private boolean end;

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

    public ArrayList<Groupe> getGroups() {
        return groups;
    }

    public PlayMove() {
        diff = new HashSet<Modif>();
        groups = new ArrayList<Groupe>();
        end = false;
        eval = Integer.MIN_VALUE;
    }

    public void addModif(Modif modif) {
        diff.add(modif);
    }

    public void apply(Goban goban) throws BadGobanStateException {
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

    public void revert(Goban goban) throws BadGobanStateException {
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

    public void setGroups(ArrayList<Groupe> groupes) {
        groups = groupes;
    }

    public Modif getPutStone() {
        return putStone;
    }

    public void setPutStone(Modif putPion) {
        this.putStone = putPion;
    }

    public Set<Modif> getDiff() {
        return diff;
    }

    public void setDiff(Set<Modif> diff) {
        this.diff = diff;
    }


    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    @Override
    public String toString() {
        StringBuilder strb = new StringBuilder();
        strb.append("{ ").append("putStone : ").append(putStone)
                .append(", diff : ").append(diff)
                .append(", eval : ").append(eval)
                .append(", groupes : ").append(groups)
                .append(" }");

        return strb.toString();
    }



    

    
}
