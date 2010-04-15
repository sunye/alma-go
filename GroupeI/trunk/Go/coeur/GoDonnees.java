/**
 * @author Bouvet Frédéric
 * @author Altuntas Murat
 */
package coeur;


/** Donnees de base sur le jeu */
public interface GoDonnees {

	/** Donnees sur les couleurs */	
	public final static int VIDE = 0;
	public final static int NOIR = 1;
	public final static int BLANC = 2;
		
	/** Donnée sur l'interface */
	public final static int RAYON_PION=16;
	public final static int DIM_CASE=42;
	
	/** Donnees sur les joueurs */
	public final static int HUMAIN = 0;
	public final static int IA = 1;
	
	/** Donnees sur les niveaux possibles de l'IA */
	public final static int IA_Debutant = 0;
	public final static int IA_Intermediaire = 1;
	public final static int IA_Expert = 2;
	
	public final static int DIM_GOBAN_MAX=9; 
	
	/** Donnees textes  */
	public final static String  texteHumain = "Humain";
	public final static String  texteIA_Debutant = "IA - Debutant";
	public final static String  texteIA_Intermediaire = "IA - Intermediaire";
	public final static String  texteIA_Expert = "IA - Expert";
	public final static String  texte_a_propos="Jeux d'attariGo programme par:\n \n - Frederic Bouvet\n - Murat Altuntas\n \n Dans le cadre d'un projet de TP propose par l'universite de Nantes:\n En Master 1 ALMA (arcitecture logicielle), annee 2009-2010.\n http://www.univ-nantes.fr/";
	
	/** Temps maximal accorde a l'IA pour jouer par defaut  */
	public final static int tempsMaxDefaut = 10;
	
	/** Nombre maximal de capture **/
	public final static int capturemax=10;
}
