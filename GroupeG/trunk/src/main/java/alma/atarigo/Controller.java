/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import alma.atarigo.ia.ArtificialPlayer;
import alma.atarigo.ihm.HumanPlayer;
import alma.atarigo.model.CellImpl;
import alma.atarigo.model.TerritoryImpl;
import static alma.atarigo.CellContent.*;

/**
 *
 * @author steg
 */
public abstract class Controller implements GameData{

    private final static String HUMAN_PLAYER = "alma.atarigo.ihm.HumanPlayer";
    private final static String COMPUTER_PLAYER ="alma.atarigo.ia.ArtificialPlayer";

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
    private GobanModel model;

    /**
     * Test de la fin du jeu
     */
    private boolean gameOver = true;

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
     * Cette propriété est a true juste a après l'appel de loadFrom
     */
    private boolean isReloaded = false;
    
    /**
     * Historique du jeu
     */
    private List<Cell> history = new ArrayList<Cell>();
    
    /**
     * Constructeur1
     * @param kuro : le joueur possédant les noirs
     * @param shiro : le joueur possédant les blancs
     * @param goban : le plateau de jeu
     * @param level : le niveau de jeu
     * @param rules : les regles du jeu
     */
    public Controller(Player kuro,Player shiro,GobanModel goban,Level level,Rule ... rules){
    	this(kuro,shiro,goban,level,Arrays.asList(rules));
    }

    /**
     * Constructeur1
     * @param kuro : le joueur possédant les noirs
     * @param shiro : le joueur possédant les blancs
     * @param goban : le plateau de jeu
     * @param level : le niveau de jeu
     * @param rules : les regles du jeu
     */
    public Controller(Player kuro,Player shiro,GobanModel goban,Level level,List<Rule> rules){
    	this(kuro,shiro,goban,level,rules,1,1);
    }

    /**
     * Constructeur1
     * @param kuro : le joueur possédant les noirs
     * @param shiro : le joueur possédant les blancs
     * @param goban : le plateau de jeu
     * @param level : le niveau de jeu
     * @param rules : les regles du jeu
     * @param kuroObj : Objectif de capture pour kuro
     * @param shiroObj : objectif de capture pour shiro
     */
    public Controller(Player kuro,Player shiro,GobanModel goban,Level level,List<Rule> rules,int kuroObj,int shiroObj){
        this.kuro = kuro;
        this.shiro = shiro;
        this.model = goban;
        this.level = level;
        this.current = this.kuro;
        this.kuro.setObjective(kuroObj);
        this.shiro.setObjective(shiroObj);        
        this.addRules(rules);
    }
    
    /**
     * Teste la validité des règles sur une cellule
     * @param event : la cellule
     * @throws RuleViolationException : échec
     * @throws CaptureException : fin du jeu
     */
    public void checkRules(CellEvent event) throws RuleViolationException, CaptureException{
        for(Rule rule : rules){
            rule.check(model, event);
        }
    }

    /**
     * Modifier le goban. A ne pas appeler en cours de partie ce qui provoquera un plantage.
     * Le joueur courant est modifié en fonction du goban.
     * @param model
     */
    public void setGoban(GobanModel model){
    	if(model!=null && this.model!=model){
    		isReloaded = true;
    		gameOver = false;
    		this.model = model;
    		this.current = model.getNextPlayer().isKuro()?this.kuro:this.shiro;
    	}
    }
    
    /**
     * Methode principale de jeu
     */
    public void playGame(){
    	try{

            if(!isReloaded){
                current = kuro;
                gameOver = false;
            }else {
                isReloaded = false;
            }

            CellPosition position = null;
            CellContent content = null;
            CellEvent event = null;
            EndOfGameEvent eogEvent= null;
            
            do{
                content = current.getColor();
                
//            	System.out.println(String.format("Controller:: %s(%s) Playing ... ",current instanceof HumanPlayer?"Human":"Computer",content));
            	position = current.nextPlay(getMonitorFor(current));
//            	System.out.println("Result = "+position);

            	if(position!=null){
                    event = createCellEvent(position,content);
                    try{
                    	
                    	//Verification des règles
                    	checkRules(event);

                        //on passe le tour
                        current=isKuroTurn()?shiro:kuro;
                    	
                        //on met a jour la valeur de la case
                        model.setCellContent(position,content);

                        //on enregistre dans l'historique
                        history.add(new CellImpl(position,content));
                        
                        //notifie que la case a ete mis jour
                        fireCellChanged(event);
                        
                    } catch (CaptureException ex) {
                    	List<CellPosition> capturer = ex.getWinningArea();
                    	List<Territory> captured = ex.getLosingArea();
                    	
                		gameOver = true;
                    	                    	
                		//verification des objectif de capture
//                		List<CellPosition> prisoners = isKuroTurn()?model.getKuroPrisoners():model.getShiroPrisoners();
                		int capturedSize = (isKuroTurn()?model.getKuroPrisoners():model.getShiroPrisoners()).size();
                		for(Territory ter : captured){
            				model.makePrisoner(current.getColor(), ter);
            				capturedSize+=ter.size();
                		}

                		gameOver = capturedSize>=current.getObjective();

                		//notifie que la case a été mis jour
                    	model.setCellContent(position,content);
                        fireCellChanged(event);
                    	
                    	if(gameOver){
                    		//on cree l'evenement de fin de jeu
                    		eogEvent = createEOGEvent(content.isKuro()?kuro:shiro,capturer,captured);
                    	}else{
                            //on passe le tour
                            current=content.isKuro()?shiro:kuro;
                    	}

                    }catch(RuleViolationException ex){
                        //notifier l'erreur
                        fireRuleViolation(createViolationEvent(ex.getWhy(),position,content));
                    }
            	}
            	else{
            		gameOver = true;
            		List<Territory> captured = new ArrayList<Territory>();
            		for(CellPosition pos : model.getPositionsFor(content.isKuro()?KuroSuicide:ShiroSuicide)){
            			captured.add(new TerritoryImpl(pos, content));
            		}
            		
            		eogEvent = createEOGEvent(content.isKuro()?shiro:kuro,new ArrayList<CellPosition>(),captured);
            	}
            	
            }while(!gameOver);

            //on signale le gagnant
            if(eogEvent!=null){
                for(EndOfGameListener listener : this.gameOverListeners){
                    listener.gameOver(eogEvent);
                }
            }
        
    	}finally{
    		abortGame();
    	}
    	
    }

    /**
     * Terminer proprement le jeu
     */
    public void abortGame(){
    	gameOver = true;
    	
		if(kuro!=null && kuro instanceof ArtificialPlayer){
			((ArtificialPlayer)kuro).abort();
		}
		
		if(shiro!=null && shiro instanceof ArtificialPlayer){
			((ArtificialPlayer)shiro).abort();
		}
		
		this.cellListeners.clear();
		this.gameOverListeners.clear();
		this.ruleListeners.clear();
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
     * Creer un Evenement sur la violation d'une cellule à une regle
     * @param ruleName : le nom de la regle
     * @param position : la position de la cellule
     * @param content : l'etat de la cellule
     * @return : l'evenement
     */
    private RuleViolationEvent createViolationEvent(final String ruleName,final CellPosition position,final CellContent content){
        return new RuleViolationEvent() {
            public CellPosition getPosition() {  return position; }
            public CellContent getContent() { return content; }
            public String getRuleName() { return ruleName; }
        };
    }

    /**
     * Créer un évenement de fin de jeu
     * @param winningArea
     * @param losingArea
     * @return
     */
    private EndOfGameEvent createEOGEvent(Player winr,final List<CellPosition> winningArea,final List<Territory> losingArea){
        final Player winner = winr;
        final Player looser = winner==kuro?shiro:kuro;
        return new EndOfGameEvent() {
            public Player getWinner() {
                return winner;
            }

            public Player getLooser() {
                return looser;
            }

            public List<CellPosition> getWinningArea() {
                return winningArea;
            }

            public List<Territory> getLosingArea() {
                return losingArea;
            }
        };
    }
    
    /**
     * Recuperer le plateau de jeu
     * @return : le Goban
     */
    public GobanModel getGoban(){
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
     * Déclanchement d'evenement sur un ensemble de cellule
     * @param event : une cellule
     */
    public void fireCellChanged(CellEvent event){
        for(CellListener listener : cellListeners){
            listener.cellChanged(event);
        }
    }

    /**
     * Ajouter un listener qui ecoutera les evenements sur les cellules
     * @param listener
     */
    public void addCellListener(CellListener listener){
        if(listener!=null){
            this.cellListeners.add(listener);
        }
    }

    /**
     * Retirer un ecouteur d'évènement sur cellule
     * @param listener
     */
    public void removeCellListener(CellListener listener){
        cellListeners.remove(listener);
    }

    /**
     * Appeler tous les ecouteurs de violiation de rèbles lorsque une règle 
     * est violée
     * @param event
     */
    public void fireRuleViolation(RuleViolationEvent event){
        for(RuleViolationListener listener : ruleListeners){
            listener.ruleViolated(event);
        }
    }

    /**
     * Ajouter un listener pour surveiller les violations de règle
     * @param listener
     */
    public void addRuleViolationListener(RuleViolationListener listener){
        if(listener!=null){
            this.ruleListeners.add(listener);
        }
    }

    /**
     * @param listener
     */
    public void removeRuleViolationListener(RuleViolationListener listener){
        ruleListeners.remove(listener);
    }

    /**
     * Ajouter un listener qui surveille la fin du jeu
     * @param listener
     */
    public void addEndOfGameListener(EndOfGameListener listener){
        if(listener!=null){
            this.gameOverListeners.add(listener);
        }
    }

    /**
     * @param listener
     */
    public void removeEndOfGameListener(EndOfGameListener listener){
        if(listener!=null){
            this.gameOverListeners.add(listener);
        }
    }

    /* (non-Javadoc)
     * @see alma.atarigo.GameData#getLevel()
     */
    public alma.atarigo.Level getLevel() {
        return level;
    }

    /**
     * Modifier le niveau du jeu
     * @param level
     */
    public void setLevel(Level level){
        this.level = level;
    }

    /**
     * Ajouter de nouvelles règles au jeu
     * @param rules
     */
    public void addRules(Rule ... rules){
        for(Rule rule : rules){
            this.rules.add(rule);
        }
    }

    /**
     * Ajouter de nouvelles règles au jeu
     * @param rules
     */
    public void addRules(Iterable<Rule> rules){
        for(Rule rule : rules){
            this.rules.add(rule);
        }
    }

    /**
     * 
     * @return true Si c'est au Noir de jouer
     */
    public boolean isKuroTurn(){
        return current == kuro;
    }

    /**
     * @return true si c'est au blanc de jouer
     */
    public boolean isShiroTurn(){
        return current == shiro;
    }

    /**
     * Modifier la grille du jeu
     * @param model
     */
    protected void setModel(GobanModel model){
        this.model = model;
    }

    /**
     * 
     * @return L'instance du joueur noir
     */
    public Player getKuro(){
        return kuro;
    }

    /**
     * @return L'instance du joueur blanc
     */
    public Player getShiro(){
        return shiro;
    }

    /**
     * @return Le joueur courant
     */
    public Player getCurrentPlayer(){
        return current;
    }

    /**
     * Sauvegarder la partie dans un fichier
     * @param file Le fichier de sortie
     * @throws IOException
     */
    public void saveTo(File file) throws IOException{
        Properties properties = new Properties();

        properties.put("level",getLevel().toString());
        properties.put("current.player",isKuroTurn()?"kuro":"shiro");
        properties.put("kuro.class",(getKuro() instanceof HumanPlayer)?HUMAN_PLAYER:COMPUTER_PLAYER);
        properties.put("shiro.class",(getShiro() instanceof HumanPlayer)?HUMAN_PLAYER:COMPUTER_PLAYER);
        properties.put("game.over",""+gameOver);
        for(int i=0;i<model.getSize();i++){
            int row = i+1;
            for(int j=0;j<model.getSize();j++){
                int col = j+1;
                properties.put(
                        String.format("row%d.column%d", row, col),
                        model.getCellContent(row, col).toString()
                        );
            }
        }

        FileWriter writer  = new FileWriter(file);
        properties.store(writer, "Sauvegarde du go");
        writer.close();
    }

    /**
     * Charger une partie sauvegardée
     * @param file
     * @throws Throwable
     */
    public void loadFrom(File file) throws Throwable{
        Properties properties = new Properties();

        FileReader reader = new FileReader(file);
        properties.load(reader);

        setLevel(Level.valueOf(properties.getProperty("level")));

        kuro = (Player) Class.forName(properties.getProperty("kuro.class")).newInstance();
        shiro = (Player) Class.forName(properties.getProperty("shiro.class")).newInstance();
        gameOver = Boolean.valueOf(properties.getProperty("game.over"));
        if("kuro".equals(properties.getProperty("current.player"))){
            current = kuro;
        }else{
            current = shiro;
        }

        Pattern pattern = Pattern.compile("row(\\d)\\.column(\\d)",Pattern.DOTALL);
        for(String key : properties.stringPropertyNames()){
            Matcher matcher = pattern.matcher(key);
            if(matcher.matches()){
                Integer row = Integer.valueOf(matcher.group(1));
                Integer col  = Integer.valueOf(matcher.group(2));
                CellContent content = CellContent.valueOf(properties.getProperty(key));
                model.setCellContent(row, col, content);
            }
        }

        isReloaded = true;
    }

    /**
     * 
     * @return true si on est pas en fin de jeu
     */
    public boolean isRunning(){
    	return !gameOver;
    }
    
    /**
     * Obtenir le moniteur de progrès de reflexion pour un joueur
     * @param player Le joueur concerné
     * @return
     */
    public abstract IProgressMonitor getMonitorFor(Player player);
}
