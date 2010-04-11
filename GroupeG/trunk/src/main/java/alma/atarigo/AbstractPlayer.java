/**
 * 
 */
package alma.atarigo;

import java.util.Properties;

import alma.atarigo.ia.Algorithm;
import alma.atarigo.ia.AlphaBeta;
import alma.atarigo.ia.IA;
import alma.atarigo.ia.valuation.Intelligent;


/**
 * @author E055983B
 *
 */
/**
 * @author steg
 *
 */
public abstract class AbstractPlayer implements Player {

	private static long COUNT = Long.MIN_VALUE;
	
	protected Properties metadata = new Properties();
	protected long playerId;
	protected Controller owner;
	protected CellContent content;
	protected int objective;
    protected IA brain;
	
	/**
	 * On construit un joueur a partir de sa couleur
	 * @param content La couleur du joueur
	 */
	public AbstractPlayer(CellContent content){
		this(content, MIN_OBJECTIVE);
	}
	
	/**
	 * @param content La couleur du joueur
	 * @param objective Les objectifs de capture
	 */
	public AbstractPlayer(CellContent content,int objective){
		setColor(content);
		setObjective(objective);
		playerId = ++COUNT;
		
		brain = new IA();
		brain.setAlgorithm(new AlphaBeta());
		brain.setValuation(new Intelligent(content));
		brain.setLevel(Level.Debutant);		
	}
	

	/* (non-Javadoc)
	 * @see alma.atarigo.Player#getOwner(alma.atarigo.Controller)
	 */
	public Controller getOwner(){
		return owner;
	}
	
	/* (non-Javadoc)
	 * @see alma.atarigo.Player#getColor()
	 */
	public CellContent getColor(){
		return content;
	}
	
	/**
	 * Modifier la couleur du joueur.
	 * Convention bean.
	 * @param content La couleur du joueur
	 */
	public void setColor(CellContent content){
		this.content = content;
		metadata.setProperty("content", this.content.name());
	}
	
	/* (non-Javadoc)
	 * @see alma.atarigo.Player#getObjective()
	 */
	public int getObjective(){
		return objective;
	}
	
	/* (non-Javadoc)
	 * @see alma.atarigo.Player#setObjective(int)
	 */
	public void setObjective(int newOne){
		this.objective = newOne;//Math.max(Math.min(newOne, MAX_OBJECTIVE),MIN_OBJECTIVE);
//		if(objective<MIN_OBJECTIVE || objective>MAX_OBJECTIVE){
//			objective = MIN_OBJECTIVE;
//		}
		metadata.setProperty("objective",""+objective);
	}

    /**
     * Modifier l'IA associer au joueur
     * @param ia
     */
    public void setIA(IA ia){
        this.brain = ia;
        if(ia!=null){
        	ia.setController(this.owner);
        }
    }
    
    /**
     * Obtenir l'IA du joueur
     * @return Une IA
     */
    public IA getIA(){
    	return brain;
    }
    
	/**
	 * Modifier le niveau d'intelligence du joueur
	 * @param level Le nouveau niveau du joueur
	 */
	public void setIALevel(Level level){
		if(brain!=null){
			brain.setLevel(level);
		}
	}
    
    /* (non-Javadoc)
     * @see alma.atarigo.Player#setOwner(alma.atarigo.Controller)
     */
    public void setOwner(Controller owner){
    	this.owner = owner;
    	if(brain!=null){
    		brain.setController(owner);
    	}
    }
	
	
	public boolean equals(Object player){
		if(player instanceof AbstractPlayer && player!=null){
			return playerId==((AbstractPlayer)player).playerId;
		}
		return false;
	}
	
	/**
	 * Verifier que deux jouers sont de la meme couleur
	 * @param player L'autre joueur
	 * @return true si <code>getColor().equals(player.getColor())</code>
	 */
	public boolean sameColor(Player player){
		return content.equals(player.getColor());
	}
	
	/* (non-Javadoc)
	 * @see alma.atarigo.Player#getMetadata()
	 */
	public Properties getMetadata(){
		if(brain!=null){
			brain.saveInto(metadata);
		}
		return metadata;
	}

    /**
     * Annuler la reflexion de L'IA du joueur
     */
    public void abort(){
    	if(brain!=null){
    		Algorithm algo = brain.getAlgorithm();
    		if(algo!=null){
    			algo.abort();
    		}
    	}
    }      
	
}

