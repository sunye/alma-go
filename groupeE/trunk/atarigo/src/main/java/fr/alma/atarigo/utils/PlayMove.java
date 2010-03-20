package fr.alma.atarigo.utils;

import fr.alma.atarigo.utils.exceptions.BadGobanStateException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * This class is a Set of modifications, describing the effects of a move on the goban during the game
 * @author judu
 */
public class PlayMove {
    private Set<Modif> diff;

    public PlayMove() {
        diff = new HashSet<Modif>();
    }

    public void Apply(PionVal[][] goban) throws BadGobanStateException {
        Iterator<Modif> iterator = diff.iterator();
        while(iterator.hasNext()){
            Modif curM = iterator.next();
            curM.apply(goban);
        }
    }
    

}
