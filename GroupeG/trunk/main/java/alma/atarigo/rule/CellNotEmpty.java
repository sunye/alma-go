/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.rule;

import alma.atarigo.CellContent;
import alma.atarigo.CellEvent;
import alma.atarigo.CellPosition;
import alma.atarigo.CaptureException;
import alma.atarigo.GobanModel;
import alma.atarigo.Rule;
import alma.atarigo.RuleViolationException;

/**
 *
 * @author gass-mouy
 */
public class CellNotEmpty extends AbstractRule implements Rule {

    private String why = "Il est interdit de superposer des pions";
    
    private CellNotEmpty(){
    }

    public static final Rule RULE = new CellNotEmpty();


    public void check(GobanModel goban, CellEvent event) throws RuleViolationException {

//        System.out.print("territories de "+event.getContent()+": ");
//        System.out.println(((AbstractGobanModel)goban).getTerritories());
    	
        CellContent content = goban.getCellContent(event.getPosition());

        if(content==null){
            throw new UnsupportedOperationException("Erreur interne la case ne doit pas etre a null, revoit ton programme mec: MDR!!");
        }

        if(content.isShiro() || content.isKuro()){
            throw new RuleViolationException(getWhy(event.getPosition()));
        }
    }

    String getWhy(CellPosition pos){
        return String.format("%s :\n%s est deja occupé", why,pos);
    }

}
