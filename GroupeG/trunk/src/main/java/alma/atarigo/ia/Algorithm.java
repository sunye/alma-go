/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ia;

import java.util.List;

import alma.atarigo.ia.valuation.Valuation;
import alma.atarigo.*;

/**
 * Interface pour les algorithmes d'analyse du jeu
 * @author gass-mouy
 */
public interface Algorithm {


	/**
	 * Executer l'algorithme pour renvoyer une solution
	 * @return La liste des positions trouvees
	 * 
	 */
    public List<CellPosition> run(IProgressMonitor progressMonitor);
    
    /**
     * Mettre a jour tous les parametres de l'algo
     * @param goban
     * @param content
     * @param height
     */
    public void setAll(GobanModel goban, CellContent content, int height);

    /**
     * Modifier la valeur qui permet a l'algorithme de connaitre
     * la couleur du joueur courant
     * @param content La couleur du joueur courant
     */
    public void setContent(CellContent content);

    /**
     * Modifier la profondeur de l'analyze de l'algorithme
     * @param depth La nouvelle profondeur
     */
    public void setDepth(int depth);
    
    /**
     * Modifier le goban d'origine de l'analyse
     * @param goban Le goban d'origine
     */
    public void setGoban(GobanModel goban);

    /**
     * Modifier la fonction d'evaluation
     * @param valuation La nouvelle fonction d'evaluation
     */
    public void setValuation(Valuation valuation);
    
    /**
     * Obtenir la fonction d'evaluation courante
     * @return Une fonction d'evaluation
     */
    public Valuation getValuation();
    
    /**
     * L'appel de cette fonction doit permettre d'arreter le deroulement
     * de la methode run de l'algorithme qui doit dans ce cas retourner
     * une reponse immediate
     */
    public void abort();
    
//    public void kill();
    
}
