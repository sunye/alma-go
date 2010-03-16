/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo;

/**
 *
 * @author steg
 */
public enum Level {

    /**
     * Niveau débutant :
     * -temps infini à chaque coup
     * -profondeur de l'arbre de prédiction des coups peu profond (3)
     */
    Beginner(Long.MAX_VALUE,3),

    /**
     * Niveau intermédiaire :
     * -temps limité à 30secondes pour chaque coup
     * -profondeur de l'arbre égale à 5
     */
    Intermediate(30,5),

    /**
     * Niveau expert :
     * -temps limité à 15 secondes
     * -profondeur de l'arbre égale à 10
     */
    Expert(15,10),

    /**
     * Niveau psycho :
     * -temps limité à 5 secondes
     * -profondeur de l'arbre égale à 50
     */
    Psycho(5,50);

    /**
     * Implémentation de l'infini
     */
    public static long INFINITE = Long.MAX_VALUE;

    /**
     * Le temps du joueur
     */
    long timeToPlay;

    /**
     * La profondeur de l'arbre
     */
    int depth;

    /**
     * Constructeur de la classe
     * @param timeToPlay : le temps
     * @param depth : la profondeur de l'arbre
     */
    private Level(long timeToPlay,int depth){
        this.timeToPlay = timeToPlay;
        this.depth = depth;
    }

    /**
     * Recupere le temps du joueur
     * @return : le temps
     */
    public long getTimeToPlay(){
        return timeToPlay;
    }

    /**
     * Recupere la profondeur de l'arbre
     * @return : le profondeur
     */
    public int getAnalyzeDepth(){
        return depth;
    }

}
