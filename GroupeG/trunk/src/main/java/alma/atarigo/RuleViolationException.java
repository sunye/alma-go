/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public class RuleViolationException extends Throwable {

    /**
     * le nom de la regle, initialisé à NULL
     */
    String name = null;

    /**
     * Constructeur1 : avec le nom de la regle et le message d'erreur
     * @param ruleName : le nom de la regle
     * @param message : le message correspondant à l'exception
     */
    public RuleViolationException(String ruleName,String message){
        super(message);
        this.name=ruleName;
    }

    /**
     * Constructeur2 : avec le nom de la regle =>appel du constructeur1
     * @param name : 
     */
    public RuleViolationException(String ruleName){
        this(ruleName,ruleName+" has been violated");
    }

    /**
     * Constructeur3 : avec l'exception de la superclasse
     * @param ex : l'exceptino de la superclasse
     */
    public RuleViolationException(Throwable ex){
        this(ex.getMessage());
        setStackTrace(ex.getStackTrace());
    }

    /**
     * Recupere le nom de la regle
     * @return : le nom de la regle
     */
    public String getRuleName(){
        return name;
    }

}
