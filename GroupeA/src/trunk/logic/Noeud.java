package trunk.logic;

enum tnoeud  {
	RACINE, INTERIEUR, FEUILLE
};

abstract class Noeud {
		
	int[] vo;				// les differences de l'ordinateur
	int[] va;				// les differences de l'adversaire
	int valeur;

}