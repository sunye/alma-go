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
public abstract class AbstractPlayer implements Player {

	private static long COUNT = Long.MIN_VALUE;
	
	protected Properties metadata = new Properties();
	protected long playerId;
	protected Controller owner;
	protected CellContent content;
	protected int objective;
    protected IA brain;
	
	public AbstractPlayer(CellContent content){
		this(content, MIN_OBJECTIVE);
	}
	
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
	
	public CellContent getColor(){
		return content;
	}
	
	public void setColor(CellContent content){
		this.content = content;
		metadata.setProperty("content", this.content.name());
	}
	
	public int getObjective(){
		return objective;
	}
	
	public void setObjective(int newOne){
		this.objective = newOne;//Math.max(Math.min(newOne, MAX_OBJECTIVE),MIN_OBJECTIVE);
//		if(objective<MIN_OBJECTIVE || objective>MAX_OBJECTIVE){
//			objective = MIN_OBJECTIVE;
//		}
		metadata.setProperty("objective",""+objective);
	}

    public void setIA(IA ia){
        this.brain = ia;
        if(ia!=null){
        	ia.setController(this.owner);
        }
    }
    
    public IA getIA(){
    	return brain;
    }
    
	public void setIALevel(Level level){
		if(brain!=null){
			brain.setLevel(level);
		}
	}
    
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
	
	public boolean sameColor(Player player){
		return content.equals(player.getColor());
	}
	
	public Properties getMetadata(){
		if(brain!=null){
			brain.saveInto(metadata);
		}
		return metadata;
	}

    public void abort(){
    	if(brain!=null){
    		Algorithm algo = brain.getAlgorithm();
    		if(algo!=null){
    			algo.abort();
    		}
    	}
    }      
	
}

