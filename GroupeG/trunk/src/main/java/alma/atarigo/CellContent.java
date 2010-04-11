/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *Le contenu d'une case du goban
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

    /**
     * Tester l'egalité de 2 contenus
     * @param object L'autre contenu
     * @return true Si les contenues ont les memes identifiants
     */
    public boolean sameId(CellContent object){
    	return id == ((CellContent)object).id;
    }

    /**
     * 
     * @return true s'il s'agit de <code>CellContent.Empty</code>
     */
    public boolean isEmpty(){
        return id==CellContent.Empty.id;
    }

    /**
     * 
     * @return true s'il s'agit de <code>CellContent.Kuro</code>
     */
    public boolean isKuro(){
        return id==CellContent.Kuro.id;
    }

    /**
     * 
     * @return true s'il s'agit de <code>CellContent.Shiro</code>
     */
    public boolean isShiro(){
        return id==CellContent.Shiro.id;
    }
    
    /**
     * 
     * @return true s'il s'agit de <code>CellContent.ShiroSuicide</code>
     */
    public boolean isShiroSuicide(){
    	return id==CellContent.ShiroSuicide.id;
    }

    /**
     * 
     * @return true s'il s'agit de <code>CellContent.KuroSuice</code>
     */
    public boolean isKuroSuicide(){
    	return id==CellContent.KuroSuicide.id;
    }
       
    /**
     * Test si le content est de la couleur opposée
     * @param content Le contenu à tester
     * @return true si <code> (isKuro() && content.isShiro()) || (isShiro() && content.isKuro())</code>
     */
    public boolean isEnemy(CellContent content){
        return (isKuro() && content.isShiro()) 
        		|| (isShiro() && content.isKuro());
    }

    /**
     * Test si le content est de la même couleur
     * @param content Le contenu à tester
     * @return true si <code> this.sameId(content) </code>
     */
    public boolean isFriend(CellContent content){
        return sameId(content);
    }

    /**
     * Obtenir le contenu de couleur opposée
     * @return Kuro si <code> isShiro() </code>, Shiro si <code> isKuro </code>, null sinon
     */
    public CellContent getEnemy(){
    	if(isShiro()){
    		return Kuro;
    	}
    	else if(isKuro()){
    		return Shiro;
    	}
    	return null;
    }

}
