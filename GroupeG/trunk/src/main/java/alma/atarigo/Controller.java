/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

import java.util.*;

/**
 *
 * @author steg
 */
public class Controller implements GameData{

    /**
     * le niveau de jeu
     */
    private Level level;
    
    /**
     * Le joueur possédant les blancs : KURO
     */
    private Player kuro;

    /**
     * Le joueur possédant les noirs : SHIRO
     */
    private Player shiro;

    /**
     * Le joueur en train de jouer
     */
    private Player current;

    /**
     * Le plateau de jeu entier
     */
    private Goban model;

    /**
     * Test de la fin du jeu
     */
    private boolean gameOver = false;

    /**
     * Liste des règles associées au jeu
     */
    private List<Rule> rules = new ArrayList<Rule>();

    /**
     * 
     */
    private List<CellListener> cellListeners = new ArrayList<CellListener>();

    /**
     * 
     */
    private List<RuleViolationListener> ruleListeners = new ArrayList<RuleViolationListener>();

    /**
     * 
     */
    private List<EndOfGameListener> gameOverListeners = new ArrayList<EndOfGameListener>();

    /**
     * Constructeur1
     * @param kuro : le joueur possédant les noirs
     * @param shiro : le joueur possédant les blancs
     * @param goban : le plateau de jeu
     * @param level : le niveau de jeu
     * @param rules : les regles du jeu
     */
    public Controller(Player kuro,Player shiro,Goban goban,Level level,Rule ... rules){
        this.kuro = kuro;
        this.shiro = shiro;
        this.model = goban;
        this.level = level;
        this.addRules(rules);
    }

    /**
     * Constructeur1
     * @param kuro : le joueur possédant les noirs
     * @param shiro : le joueur possédant les blancs
     * @param goban : le plateau de jeu
     * @param level : le niveau de jeu
     * @param rules : les regles du jeu
     */
    public Controller(Player kuro,Player shiro,Goban goban,Level level,List<Rule> rules){
        this.kuro = kuro;
        this.shiro = shiro;
        this.model = goban;
        this.level = level;
        this.addRules(rules);
    }

    /**
     * Teste la validité des règles sur une cellule
     * @param event : la cellule
     * @throws RuleViolationException : échec
     * @throws EndOfGameException : fin du jeu
     */
    public void checkRules(CellEvent event) throws RuleViolationException, EndOfGameException{
        for(Rule rule : rules){
            rule.check(model, event);
        }
    }

    /**
     * Methode principale de jeu
     */
    public void playGame(){
        current = kuro;
        CellPosition position = null;
        CellContent content = null;
        CellEvent event = null;

        do{
            content = current==kuro?CellContent.Black:CellContent.White;
            position = current.nextPlay();
            event = createCellEvent(position,content);

            try{
                checkRules(event);

                //on met a jour la valeur de la case
                model.setCellContent(position,content);

                //notifie que la case a été mis jour
                fireCellChanged(event);

                //on passe le tour
                current=current==kuro?shiro:kuro;
                
            } catch (EndOfGameException ex) {
                gameOver = true;
            }catch(RuleViolationException ex){
                //notifier l'erreur
                fireRuleViolation(createViolationEvent(ex.getRuleName(),position,content));
            }
        }while(!gameOver);

        //on signale le gagnant
        Player murray = current;
        Player nadal = current==kuro?shiro:kuro;
        for(EndOfGameListener listener : this.gameOverListeners){
            listener.gameOver(murray, nadal);
        }
    }

    /**
     * Créer un évenement sur une cellule
     * @param position : la position de la cellule
     * @param content : l'état de la cellule
     * @return : l'evenement
     */
    private CellEvent createCellEvent(final CellPosition position,final CellContent content){
        return new CellEvent() {
            public CellPosition getPosition() {  return position; }
            public CellContent getContent() { return content; }
        };
    }

    /**
     * Créer un évenement sur la violation d'une cellule à une regle
     * @param ruleName : le nom de la regle
     * @param position : la position de la cellule
     * @param content : l'état de la cellule
     * @return : l'évenement
     */
    private RuleViolationEvent createViolationEvent(final String ruleName,final CellPosition position,final CellContent content){
        return new RuleViolationEvent() {
            public CellPosition getPosition() {  return position; }
            public CellContent getContent() { return content; }
            public String getRuleName() { return ruleName; }
        };
    }

    /**
     * Recuperer le plateau de jeu
     * @return : le Goban
     */
    public Goban getGoban(){
        return model;
    }

    /**
     * Recuperer les regles du jeu
     * @return : Les regles sous forme de collection
     */
    public Collection<Rule> getRules(){
        return rules;
    }

    /**
     * Déclanchement d'évenement sur un ensemble de cellule
     * @param event : une cellule
     */
    public void fireCellChanged(CellEvent event){
        for(CellListener listener : cellListeners){
            listener.cellChanged(event);
        }
    }

    public void addCellListener(CellListener listener){
        if(listener!=null){
            this.cellListeners.add(listener);
        }
    }

    public void removeCellListener(CellListener listener){
        cellListeners.remove(listener);
    }

    public void fireRuleViolation(RuleViolationEvent event){
        for(RuleViolationListener listener : ruleListeners){
            listener.ruleViolated(event);
        }
    }

    public void addRuleViolationListener(RuleViolationListener listener){
        if(listener!=null){
            this.ruleListeners.add(listener);
        }
    }

    public void removeRuleViolationListener(RuleViolationListener listener){
        ruleListeners.remove(listener);
    }

    public void addEndOfGameListener(EndOfGameListener listener){
        if(listener!=null){
            this.gameOverListeners.add(listener);
        }
    }

    public void removeEndOfGameListener(EndOfGameListener listener){
        if(listener!=null){
            this.gameOverListeners.add(listener);
        }
    }

    public alma.atarigo.Level getLevel() {
        return level;
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public void addRules(Rule ... rules){
        for(Rule rule : rules){
            this.rules.add(rule);
        }
    }

    public void addRules(Iterable<Rule> rules){
        for(Rule rule : rules){
            this.rules.add(rule);
        }
    }

}
