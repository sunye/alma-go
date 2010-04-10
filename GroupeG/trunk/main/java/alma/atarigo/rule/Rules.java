/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.rule;

import java.util.*;
import alma.atarigo.*;

/**
 *
 * @author steg
 */
public interface Rules {

    public final static List<Rule> ATARI_RULES = Arrays.asList(
                                                    Suicide.RULE
                                                    ,Capture.RULE
                                                    ,CellNotEmpty.RULE
                                                    );

    public final static List<Rule> IA_RULES = Arrays.asList(
                                                    Suicide.RULE
                                                    ,Capture.RULE
                                                    );

}
