package fr.alma.atarigo.analyse;

import fr.alma.atarigo.utils.Goban;
import fr.alma.atarigo.utils.Groupe;
import fr.alma.atarigo.utils.Pion;
import fr.alma.atarigo.utils.PionVal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EvalFunc {

        public void calculeYeux(Goban goban){
            for(int ligne = 0; ligne < Goban.getTaille(); ++ligne){
                for(int col = 0; col < Goban.getTaille(); ++col){
                try {
                    if (goban.getCase(ligne, col) == PionVal.RIEN) {
                        if (goban.libertesPion(ligne, col) == 0) {
                            Pion oeil = new Pion(PionVal.RIEN,ligne,col);
                            HashSet<Groupe> groupes = goban.getVoisins(oeil);
                            if(groupes.size() == 1){
                                Iterator<Groupe> groupeIt = groupes.iterator();
                                while(groupeIt.hasNext()){
                                    Groupe gr = groupeIt.next();
                                    gr.addOeil(oeil);
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(EvalFunc.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            }
        }

}
