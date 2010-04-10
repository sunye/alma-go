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
public class Suicide extends AbstractRule implements Rule {

    private String name = "Suicide";

    private Suicide(){
    }

    public static final Rule RULE = new Suicide();

    public void check(GobanModel goban, CellEvent event) throws RuleViolationException, CaptureException {


        CellContent content = goban.getCellContent(event.getPosition());
//        if((event.getContent().isKuro() && content.isKuroSuicide())
//        		|| (event.getContent().isShiro() && content.isShiroSuicide())) {
//            throw new RuleViolationException("Jouer a la position "+event.getPosition()+" est un suicide");
//        }

        
        //=============================================================
        //Application de l'algorithme pour la premiere et derniere fois
        
        content = event.getContent();
        CellPosition position = event.getPosition();
        
        //on recupere les libertés du territoire de la cellule jouée
        List<CellPosition> libertiesOfTerritory =
                goban.getLibertiesFor(goban.buildTerritory(position,content),position);
                
        //si les libertés sont vides, alors c'est un suicide !
        //MAIS le coup est accepté s'il y a une capture
        if(libertiesOfTerritory.isEmpty()){
            
            List<CellPosition> enemyLiberties = new ArrayList<CellPosition>();
            
            //recupération des enemis adjacents
            List<CellPosition> enemies = goban.getEnemies(position,content);
            
            List<Territory> loosings = new ArrayList<Territory>();
            List<CellPosition> winnings = new ArrayList<CellPosition>();
            
            //pour chaque voisin enemi...
            for(CellPosition enemy : enemies){
                enemyLiberties.clear();

                //on recupere les libertés du territoire de l'ennemi
                enemyLiberties = goban.getLibertiesFor(goban.buildTerritory(enemy), position);

                //l'evenement est accepté dès lors qu'il y a une capture
                //Ainsi, le jeu est terminé : game Over
                if(enemyLiberties.isEmpty()){
                    Territory losingArea = goban.buildTerritory(enemy);
                    winnings.addAll(goban.getEnemiesFor(losingArea,content,position));
                    loosings.add(losingArea);
//                    content = event.getContent();
//                    if(content.isKuro()){
//                            goban.setCellContent(position, CellContent.KuroWins);
//                    }else if(content.isShiro()){
//                            goban.setCellContent(position, CellContent.ShiroWins);
//                    }
                }
            }//for

            if(!loosings.isEmpty()){
            	throw new CaptureException(name,winnings,loosings);
            }
            
            //suicide seul s'il n'y a pas de capture
            if(content.isKuro()){
                goban.setCellContent(position, CellContent.KuroSuicide);
            }else if(content.isShiro()){
                goban.setCellContent(position, CellContent.ShiroSuicide);
            }
            
            throw new RuleViolationException("Jouer a la position "+event.getPosition()+" est un suicide");
        }//if
    }

}
