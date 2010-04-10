/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public enum CellContent {

    /**
     * cellule disponible
     */
    Empty(1),

    /**
     * cellule possédé par SHIRO
     */
    Shiro(2),

    /**
     * cellule possédée par KURO
     */
    Kuro(3),
    
    /**
     * cellule impliquant un suicide pour Shiro
     */
    ShiroSuicide(4),

    /**
     * cellule impliquant un suicide pour Kuro
     */
    KuroSuicide(5),
    
//    /**
//     * cellule impliquant la victoire de Shiro
//     */
//    ShiroWins(6),
//
//    /**
//     * cellule impliquant la victoire de Shiro
//     */    
//    KuroWins(7)
    ;

    private Integer id;

    private CellContent(int id){
        this.id = id;
    }

    public boolean isEqualsTo(Object object){
        if(object!=null && object instanceof CellContent){
            return id == ((CellContent)object).id;
        }
        return false;
    }

    public boolean isEmpty(){
        return id==1;
    }

    public boolean isKuro(){
        return id==3;
    }

    public boolean isShiro(){
        return id==2;
    }
    
    public boolean isShiroSuicide(){
    	return id==4;
    }

    public boolean isKuroSuicide(){
    	return id==5;
    }
    
    public void setShiroSuicide(){
    	id = 4;
    }

    public void setKuroSuicide(){
    	id = 5;
    }
    
    public void setShiroWins(){
    	id = 6;
    }

    public void setKuroWins(){
    	id = 7;
    }
    
    public boolean isEnemy(CellContent content){
        return !isEmpty() && content!=null && !content.isEmpty() && !content.id.equals(id);
    }

    public boolean isFriend(CellContent content){
        return !isEmpty() && content!=null && content.id.equals(id);
    }

    public CellContent getEnemy(){
        return (id==2)?CellContent.Kuro:CellContent.Shiro;
    }

}
