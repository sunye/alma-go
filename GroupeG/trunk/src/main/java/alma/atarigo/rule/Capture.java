/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.rule;

import alma.atarigo.*;

import java.util.*;

/**
 *
 * @author gass-mouy
 */
public class Capture extends AbstractRule implements Rule {

    private String name = "Game Over";

    private Capture(){
    }

    public static final Rule RULE = new Capture();

    
    public void check(GobanModel goban, CellEvent event) throws RuleViolationException, CaptureException {

    	
    	CellContent content = goban.getCellContent(event.getPosition());
        if((event.getContent().isKuro() && content.isKuroSuicide())
        		|| (event.getContent().isShiro() && content.isShiroSuicide())){
            throw new RuleViolationException(name);
        }
    	
        
        //=============================================================
        //Application de l'algorithme pour la premiere et derniere fois

        content = event.getContent();
        CellPosition position = event.getPosition();
        
        List<CellPosition> enemyLiberties = new ArrayList<CellPosition>();

        //recupération des enemis adjacents
        List<CellPosition> enemies = goban.getEnemies(position,content);
        
        List<Territory> loosings = new ArrayList<Territory>();
        List<CellPosition> winnings = new ArrayList<CellPosition>();
        
        //pour chaque voisin enemi...
        for(CellPosition enemy : enemies){
            
            //on recupere les libertés du territoire de l'ennemi
            enemyLiberties = goban.getLibertiesFor(goban.buildTerritory(enemy), position);

            //Game Over s'il y a une capture
            if(enemyLiberties.isEmpty()){            	
                Territory losingArea = goban.buildTerritory(enemy);
                winnings.addAll(goban.getEnemiesFor(losingArea,content,position));
                loosings.add(losingArea);
                
//                if(event.getContent().isKuro()){
//                	goban.setCellContent(position, CellContent.KuroWins);
//                }else if(content.isShiro()){
//                	goban.setCellContent(position, CellContent.ShiroWins);
//                }
            }            
        }
        
        if(!loosings.isEmpty()){
        	throw new CaptureException(name,winnings,loosings);
        }
    }

}
