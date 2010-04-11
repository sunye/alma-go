/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.rule;

import alma.atarigo.Cell;
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
public abstract class AbstractRule implements Rule {


    public void check(GobanModel goban) throws RuleViolationException, CaptureException {
        for(final Cell cell : goban){
            CellEvent event = new CellEvent(){
                public CellPosition getPosition() { return cell;}
                public CellContent getContent() { return cell.getContent();}
            };

            check(goban, event);
        }
    }


}
