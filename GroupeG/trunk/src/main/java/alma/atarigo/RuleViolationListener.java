/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public interface RuleViolationListener {

    /**
     * 
     * @param event
     */
    public void ruleViolated(RuleViolationEvent event);
}
