package main.java.alexanddam.logic;

import java.awt.Point;
import java.util.AbstractList;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Vector;

public class FonctionEvaluation {

	private static  int[][] goban;				// 0 - rien, 1 - coup ordinateur, 2 - coup adversair humain
	private static  int[][] gobanInc;          	// la matrice des coups, mais qui est utilisable afin d'évaluer les noeuds intérieurs
	private static  int[][] lib;				// une matrice qui met a jour les libertes des pierres, apres chaque coup - sauver des calculs -> -1, si il n'y a pas de pierres
	private static  int[][] libInc;				// matrice des libertes de pierres, mais qui servira a l'evaluation incrementale de feuilles
	private static  boolean[][] dejaEvalue;		// matrice des pierres deja evaluées
	private static  int points;					// on memorise le nombre de points afin de profiter de la evaluation incrementale
	private static  int exponentielle;			// l'exponentielle maximale ou l'arité maximale de chaque noeud
	private static  int cOrdinateur;			// le nombre de pierres de l'ordinateur situées sur le goban
	private static  int cHumain;				// le nombre de pierres de l'adversair situées sur le goban
	private static  int profondeur;				// le profondeur de l'arbre, calculable à partir de l'exponentielle
	private static  AbstractList<Point> groupe;      	// groupe des noeuds chaines afin de reduire les calculs des libertés
	private static  int[] cSuivant;				// coup suivant, composé de deux entiers
	private static  boolean liberteZero;      	// vrai si le jeu est fini, faux sinon

	/**
	 * This method is called by the constructor of FonctionEvaluation and each time the user wants to start a new game;
	 * concritely when he presses "New Game" 
	 */
	public static  void jeuNeu() {

		goban = new int[9][9];
		gobanInc = new int[goban.length][goban[0].length];
		cSuivant = new int[2];

		points = 0 ;
		cOrdinateur = 0;
		cHumain = 0;

		// l'exponentielle et profondeur par defaut
		exponentielle = 10;
		profondeur = 9 - (int)(Math.ceil(Math.sqrt(exponentielle+1)));

		lib = new int[goban.length][goban[0].length];
		libInc = new int[lib.length][lib[0].length];  // initialisation matrice des libertes incrementale

		for(int i=0; i<goban.length; i++){
			for(int j=0; j<goban.length; j++){
				lib[i][j] = -1;
			}
		}
		reinitialiserDE(); // false
		groupe = new Vector<Point>();
		liberteZero = false;
	}

	/**
	 * Getter of the specific point of the goban
	 * @param coordX X coordinate of the goban
	 * @param coordY Y coordinate of the goban
	 * @return value at this point - 0 = empty, 1 = computer and 2 = adversary
	 */
	public static  int getGobanValue(int coordX, int coordY) {
		return goban[coordX][coordY];
	}

	/**
	 * Set the value of a speciafic case of the area, if comprised between 0 and 2
	 * @param coordX X coordinate of the goban
	 * @param coordY Y coordinate of the goban
	 * @param val the value to set
	 */
	public static  void setGobanValue(int coordX, int coordY, int val) {
		if(val<0 || val>2 || goban[coordX][coordY]!=0) {  // atari go, pas go
			return;
		}

		goban[coordX][coordY] = val;
	}

	/**
	 * Increment of the number of stones in the goban
	 */
	public static  void incPO() {
		cOrdinateur++;
	}

	/**
	 * Increment the number of human adversary's stones in the goban
	 */
	public static  void incPA() {
		cHumain++;
	}

	/**
	 * The only constructor of the FonctionEvaluation class; initialize the data of the new game
	 */
	public FonctionEvaluation() {
		jeuNeu();
	}

	/**
	 * Method which reinitialize the matrix of the already evaluated cases during a calculation of a stone's liberty
	 */
	static void reinitialiserDE() {

		dejaEvalue = new boolean[goban.length][goban[0].length]; // false
	}

	/**
	 * Reinitialize at false the all the goban
	 * this method is used between two calls of the fonction of stones' liberty, since the liberties can be shared
	 * between several chains of stones
	 *  
	 * @param mat goban of the game
	 */
	static void reinitialiserVideDE(int[][] mat) {

		for(int i=0; i < dejaEvalue.length; i++){
			for(int j=0; j< dejaEvalue[0].length; j++){
				if(dejaEvalue[i][j] && mat[i][j]==0) {
					dejaEvalue[i][j] = false;
				}
			}
		}
	}

	/**
	 * 
	 * @param coordX x coordinate of the stone to calculate
	 * @param coordY y coordinate of the stone to calculate
	 * @param mat matrix that we compute
	 * @return number of liberties of the stone at this position
	 */
	static int libertes(int coordX, int coordY, int[][] mat) {

		int nbLib = 0, type = mat[coordX][coordY];
		dejaEvalue[coordX][coordY] = true;			// cette case est en cours de visite -> on marque la visite

		// tout ce qui est vide autour de cette position est compte comme une liberte
		if(coordX>0 && mat[coordX-1][coordY]==0 && !dejaEvalue[coordX-1][coordY]) {
			nbLib += 1;
			dejaEvalue[coordX-1][coordY] = true;
		}
		if(coordY>0 && mat[coordX][coordY-1]==0 && !dejaEvalue[coordX][coordY-1]) {
			nbLib += 1; 
			dejaEvalue[coordX][coordY-1] = true;
		}
		if(coordX<mat.length-1 && mat[coordX+1][coordY]==0 && !dejaEvalue[coordX+1][coordY]) {
			nbLib += 1; 
			dejaEvalue[coordX+1][coordY] = true;
		}
		if(coordY<mat.length-1 && mat[coordX][coordY+1]==0 && !dejaEvalue[coordX][coordY+1]) {
			nbLib += 1; 
			dejaEvalue[coordX][coordY+1] = true;
		}

		// si la case a l'est existe et on ne l'a pas teste
		if(coordX>0 && mat[coordX-1][coordY]==type && !dejaEvalue[coordX-1][coordY]) {
			groupe.add(new Point(coordX-1,coordY));		// on ajoute au groupe; cette pierre aura la meme liberte
			nbLib += libertes(coordX-1, coordY, mat);
		}

		// si la case au nord existe et on ne l'a pas teste
		if(coordY>0 && mat[coordX][coordY-1]==type && !dejaEvalue[coordX][coordY-1]) {
			groupe.add(new Point(coordX,coordY-1));		// on ajoute au groupe; cette pierre aura la meme liberte
			nbLib += libertes(coordX, coordY-1, mat);
		}

		// si la case a l'ouest existe et on ne l'a pas teste
		if(coordX<mat.length-1 && mat[coordX+1][coordY]==type && !dejaEvalue[coordX+1][coordY]) {
			groupe.add(new Point(coordX+1,coordY));		// on ajoute au groupe; cette pierre aura la meme liberte
			nbLib += libertes(coordX+1, coordY, mat);
		}

		// si la case au sud existe et on ne l'a pas teste
		if(coordY<mat.length-1 && mat[coordX][coordY+1]==type && !dejaEvalue[coordX][coordY+1]) {
			groupe.add(new Point(coordX,coordY+1));		// on ajoute au groupe; cette pierre aura la meme liberte
			nbLib += libertes(coordX, coordY+1, mat);
		}

		return nbLib;
	}

	/**
	 * Calculates the liberties of the actual game which is in the root of the min-max tree
	 * we dont need 2 vectors of moves, because they are empty at the root
	 */
	static void calculerLibertes() {

		int aux;
		boolean start = true;

		// reinitialiser la matrice des visites
		reinitialiserDE();

		for(int i = 0 ; i < goban.length; i++){
			for(int j = 0 ; j < goban.length; j++){
				if(goban[i][j]!=0 && !dejaEvalue[i][j]) {
					if(!start) {
						reinitialiserVideDE(goban); 
					}
					start = false;
					aux = lib[i][j] = libertes(i, j, goban);
					if(aux==0) { liberteZero = true; }   // la classe du jeu se rend compte de la fin de jeu

					while(! groupe.isEmpty()) {
						Point point = groupe.remove(0);
						lib[point.x][point.y] = aux;
					}
				}
			}
		}
	}

	/**
	 * Evaluates the root of the min-max tree; the result is saved in the global variable "points"
	 */
	static void evaluation() {

		points = 0; 	// on reinitialise, parce qu'on va evaluer toute le grille, pas incrementale
		calculerLibertes(); // calculer les libertes de toutes les pierres de facon efficiente

		// on compte dans le double for les libertes de l'ordinateur et du l'adversaire
		for(int i=0; i<goban.length; i++){
			for(int j=0; j<goban.length; j++){
				if(goban[i][j]!=0) {
					points += pointsPercase(lib[i][j], 2 * goban[i][j] - 3);
				}
			}
		}
	}

		
	/**
	 * Incremental evaluation, used for the tree's leaves 
	 * @param vOrdi moves vector of the computer
	 * @param vAdver moves vector of the adversary
	 * @return evaluation of the state in a node, particularly in the tree's development, as integer 
	 */
	static int evaluationInc(int[] vOrdi, int[] vAdver) {

		int pointsV = 0;
		calculerLibertesInc(vOrdi, vAdver);

		for(int i=0; i<gobanInc.length; i++){
			for(int j=0; j<gobanInc.length; j++){
				if(gobanInc[i][j]!=0) {

					/* explication deuxieme argument: on a besoin de -1 quand la case est occupee
					 * par une pierre ordinateuer et 1 lors d'une pierre d'adversair humain
					 */
					pointsV += pointsPercase(libInc[i][j], 2 * gobanInc[i][j] - 3);
				}
			}
		}

		return pointsV;
	}

	/**
	 * 
	 * @param libertes number of liberties 
	 * @param sign sign = -1 if we need computer's won points, or sign = 1 if we need won points of the human adversary 
	 * @return number of associated points 
	 */
	static int pointsPercase(int libertes, int sign) {
		int result = 0;

		switch(libertes) {
			case 3: result += sign * 10; break;
			case 2: result += sign * 50; break;
			case 1: result += sign * 100; break;
			case 0: result += sign * 1000; break;
			default: result -= sign * 2 * libertes;
		}

		return result;
	}

	/**
	 * Actualize the libInc matrix, the corresponding liberties at the actual game in the root and the moves reprensented by the two vectors
	 * 
	 * @param vOrdi moves vertor of the computer
	 * @param vAdver moves vertor of the human adversary
	 */
	private static  void calculerLibertesInc(int[] vOrdi, int[] vAdver) {
		int opt;		// variables pour optimisation

		/* on copie les libertes et le goban de la racine dans les matrices incrementales,
		 * afin de reduire des nombreuses calculs, surtout pour les libertes des pierres qui
		 * ne sont pas affectes par la suite des coups decrite par les deux vecteurs 
		 */
		copierLibertesEtGoban(gobanInc, goban, libInc, lib);

		// on realise les changements necessaires dans gobanInc
		opt = 0;
		while(opt < vOrdi.length) {
			gobanInc[vOrdi[opt]][vOrdi[opt+1]] = 1;	// l'ordinateur
			opt += 2;
		}

		opt = 0;
		while(opt < vAdver.length) {
			gobanInc[vAdver[opt]][vAdver[opt+1]] = 2;	// l'adversair
			opt += 2;
		}

		actualiserLibertes(vOrdi, 1);  // actualise les libertes affectes par le vecteur de coups d'ordinateur
		actualiserLibertes(vAdver, 2);  // actualise les libertes affectes par le vecteur de coups d'adversair humain
	}

	/**
	 * Function called from the interface class of the game and indicates if the game is ended 
	 * @return true if we have a catch and then the game ends, otherwise false
	 */
	public static  boolean jeuFini() {
		evaluation();    
		return liberteZero;
	}

	/**
	 * Returns the taken stones in case of defeat
	 * 
	 * @param type integer corresponding to a player's move
	 * @return Vector of taken stones of the loosing player
	 */
	public static AbstractList<Point> pierresPrises(int type) {
		boolean trouve = false;
		Vector<Point> point = new Vector<Point>();
		reinitialiserDE();

		for(int i = 0; i < goban.length; i++) {
			for(int j=0; j < goban.length; j++) {
				if(lib[i][j] == 0 && goban[i][j] != type) {
					groupe.add(new Point(i, j));    // celui-ci n'est pas ajoute dans libertes
					libertes(i, j, goban);
					trouve = true;
					break;
				}
			}
			if(trouve) {
				break;
			}
		}

		while(!groupe.isEmpty()) {
			point.add(groupe.remove(0));
		}

		return point;
	}

	/**
	 * Incremental actualization of the affected liberties from one of the two moves vectors 
	 * 
	 * @param vCoup Moves vector 
	 * @param type either the computer or human adversary
	 */
	private static  void actualiserLibertes(int[] vCoup, int type) {
		int iInc = 0, coupX, coupY;
		reinitialiserDE();

		while( iInc < vCoup.length ) {
			coupX = vCoup[iInc]; coupY = vCoup[iInc+1];
			if(!dejaEvalue[coupX][coupY]) {
				modifierLibertesInc(coupX, coupY);

				// il y a peut-etre des pierres de l'autre joueur autour de v1 et v2 dont les libertes seront affectees
				if(coupX > 0 && gobanInc[coupX - 1][coupY] == 3 - type) {
					modifierLibertesInc(coupX - 1, coupY);
				}
				if(coupX < gobanInc.length - 1 && gobanInc[coupX + 1][coupY] == 3 - type) {
					modifierLibertesInc(coupX + 1, coupY);
				}
				if(coupY > 0 && gobanInc[coupX][coupY - 1] == 3 - type) {
					modifierLibertesInc(coupX, coupY - 1);
				}
				if(coupY < gobanInc.length - 1 && gobanInc[coupX][coupY + 1] == 3 - type) {
					modifierLibertesInc(coupX, coupY + 1);
				}
			}
			iInc += 2;
		}
	}

	/**
	 * Calculates the liberties of a particular case. The result is saved in teh libInc matrix 
	 * @param coordX x coordinate of the case
	 * @param coordY y coordinate of the case
	 */
	private static  void modifierLibertesInc(int coordX, int coordY) {
		int aux;

		reinitialiserVideDE(gobanInc);
		aux = libInc[coordX][coordY] = libertes(coordX, coordY, gobanInc);
		while(! groupe.isEmpty()) {
			Point point = groupe.remove(0);
			libInc[point.x][point.y] = aux;
		}
	}

	/**
	 * 
	 * @param gDest address of the source goban
	 * @param gSource address of the destination goban
	 * @param lDest source of matrix of the liberties
	 * @param lSource destination of matrix of the liberties
	 */
	private static  void copierLibertesEtGoban(int[][] gDest, int[][] gSource, int[][] lDest, int[][] lSource) {

		for(int i = 0; i < gSource.length; i++){
			for(int j = 0; j < gSource[0].length; j++){
				gDest[i][j] = gSource[i][j];
			}
		}

		for(int i = 0; i < lSource.length; i++){
			for(int j = 0; j < lSource[0].length; j++){
				lDest[i][j] = lSource[i][j];
			}
		}
	}

	/**
	 * Get the calculated points by the evaluation function of the root, non-incremental
	 * @return Calculated points by the evaluation function
	 */
	public  int getPoints() {
		return points;
	}
	

	/**
	 * Function which set if we can play a stone in an empty case ; for instance we can not suicide
	 * Moreover we can win if the other one does not have liberties anymore 
	 * 
	 * @param tour Go the game of the person
	 * @param coordX x coordinate of the case
	 * @param coordY y coordinate of the case
	 * @return true if acceptable, otherwise false.
	 */
	public static  boolean permissible(int tour, int coordX, int coordY) {
		/* si il y au moins une place libre autour de lui, c'est permissible
		 * le cas le plus repandu, c'est pour ca qu'on l'a mis comme premier test
		 */
		if((coordX > 0 && goban[coordX-1][coordY]==0) || (coordX < goban.length - 1 && goban[coordX+1][coordY]==0) ||
				(coordY > 0 && goban[coordX][coordY-1]==0) || (coordY < goban.length - 1 && goban[coordX][coordY+1]==0)){
			return true;
		}

		/* si les propres pierres qui l'entourent ont le nombre de libertes == 1
		 * et toutes les autres pierres ont un liberte > 1, c'est pas permissible
		 */
		if( ( !(coordX > 0) || (goban[coordX-1][coordY]==tour+1 && lib[coordX-1][coordY]==1) || (goban[coordX-1][coordY]==2-tour && lib[coordX-1][coordY]>1)) &&
				( !(coordX < goban.length - 1) || (goban[coordX+1][coordY]==tour+1 && lib[coordX+1][coordY]==1) || (goban[coordX+1][coordY]==2-tour && lib[coordX+1][coordY]>1)) &&
				( !(coordY > 0) || (goban[coordX][coordY-1]==tour+1 && lib[coordX][coordY-1]==1) || (goban[coordX][coordY-1]==2-tour && lib[coordX][coordY-1]>1)) &&
				( !(coordY < goban.length - 1) || (goban[coordX][coordY+1]==tour+1 && lib[coordX][coordY+1]==1) || (goban[coordX][coordY+1]==2-tour && lib[coordX][coordY+1]>1))){
			return false;
		}

		// toutes les conditions sont accomplies
		return true;
	}

	/**
	 * Incremental version of the permissible() function
	 * 
	 * @param tour Go the game of the person
	 * @param coordX x coordinate of the case
	 * @param coordY y coordinate of the case
	 * @param matGob address of the goban's matrix
	 * @param matLib address of the liberties' matrix
	 * @return true if acceptable, otherwise false.
	 */
	public static  boolean permissibleInc(int tour, int coordX, int coordY, int[][] matGob, int[][] matLib) {
		if((coordX > 0 && matGob[coordX-1][coordY]==0) || (coordX < matGob.length - 1 && matGob[coordX+1][coordY]==0) ||
				(coordY > 0 && matGob[coordX][coordY-1]==0) || (coordY < matGob.length - 1 && matGob[coordX][coordY+1]==0)){
			return true;
		}
		if( ( !(coordX > 0) || (matGob[coordX-1][coordY]==tour+1 && matLib[coordX-1][coordY]==1) || (matGob[coordX-1][coordY]==2-tour && matLib[coordX-1][coordY]>1)) &&
				( !(coordX < matGob.length - 1) || (matGob[coordX+1][coordY]==tour+1 && matLib[coordX+1][coordY]==1) || (matGob[coordX+1][coordY]==2-tour && matLib[coordX+1][coordY]>1)) &&
				( !(coordY > 0) || (matGob[coordX][coordY-1]==tour+1 && matLib[coordX][coordY-1]==1) || (matGob[coordX][coordY-1]==2-tour && matLib[coordX][coordY-1]>1)) &&
				( !(coordY < matGob.length - 1) || (matGob[coordX][coordY+1]==tour+1 && matLib[coordX][coordY+1]==1) || (matGob[coordX][coordY+1]==2-tour && matLib[coordX][coordY+1]>1)) ){
			return false;
		}
		// toutes les conditions sont accomplies
		return true;
	}

	/**
	 * We develop the alpha-beta tree, with the incremental evaluation function.
	 * Then we return the X, Y coordinates of the computer's next move.
	 * The internal and non-leaf nodes are themselves evaluated, in order to chose the bests for the min-max tree's development  
	 * 
	 * @return Vector of the two elements which reprents the coordonates of the computer's next move
	 */
	public static  int[] coupSuivant() {
		int jParc, nProf;
		int nProfSuivant;					// tc est la taille de carre a explorer

		Queue<Noeud_LA> fileNoeuds = new ArrayDeque<Noeud_LA>(); // la file des noeuds a traiter sur un certain profondeur
		evaluation();
		Arbre_LA arbre = new Arbre_LA(exponentielle, 0);

		Noeud_LA racine = (Noeud_LA)(arbre.racine);   // on recupere la racine
		fileNoeuds.add(racine);  nProf = 1;		  // 1 seul noeud sur profondeur 1 - la racine

		System.out.println("Profondeur actuel " + profondeur);
		for(int i=2 ; i <= profondeur; i++) {     // boucle principale de l'algorithme alpha-beta
			jParc = 0; nProfSuivant = 0;

			while(jParc < nProf) {		// pour chaque noeud du profondeur
				Noeud_LA premNoeud = ((ArrayDeque<Noeud_LA>)fileNoeuds).pollFirst();  // on extrait le premier noeud
				NoeudsFils noeud = new NoeudsFils(exponentielle, i%2 == 0);
				nProfSuivant += parcoursProfondeur(i, premNoeud, noeud, fileNoeuds);

				jParc++;
			} // fin while j < nProf

			nProf = nProfSuivant; // pour le profondeur suivant
		} // fin for boucle profondeur
		
		int[] cpCSuivant = cSuivant;// on retourne une copie pour respecter une regle de sunsecure
		return cpCSuivant;
	}

	/**
	 * Copy after the exploration of the son nodes of all chosen nodes,
	 * in the explorated queue in  BFS algorithm - breadth first search
	 *
	 * @param noeudFils Best nodes structure
	 * @param parent Parent's node
	 * @param fileNoeuds Queue where we will put the node from the structure
	 */
	private static  void copyNoeudsFils(NoeudsFils noeudFils, Noeud_LA parent, Queue<Noeud_LA> fileNoeuds) {
		while(noeudFils.size() > 0) {
			Noeud_LA nla = noeudFils.remove(0);
			parent.ajouterFils(nla);
			fileNoeuds.add(nla);
		}
	}

	/**
	 * Exploration of a node in order to find the best sons to develop
	 * 
	 * @param prof Depth
	 * @param noeud Parent node to develop
	 * @param noeudFils Strutures which will save the best nodes
	 * @param fileNoeuds Queue in which we will put the found sons, at the end of the exploration step
	 * @return Number of son nodes to explorate for the parent node nc, concretely this number is equal
	 */
	private static  int parcoursProfondeur(int prof, Noeud_LA noeud, NoeudsFils noeudFils, Queue<Noeud_LA> fileNoeuds) {
		int nProfSuivant;
		boolean raccourci = false;
		int[][] gIncParent = new int[gobanInc.length][gobanInc[0].length],
		lIncParent = new int[libInc.length][libInc[0].length];

		// calculer le goban et libertes en etat du noeud nc
		calculerLibertesInc(noeud.vOrdi, noeud.vAdver);
		copierLibertesEtGoban(gIncParent, gobanInc, lIncParent, libInc);

		for(int k1 = 0; k1 < gobanInc.length; k1++) {
			for(int k2 = 0; k2 < gobanInc[0].length; k2++) {
				if(gIncParent[k1][k2] == 0 && permissibleInc(prof%2, k1, k2, gIncParent, lIncParent)) {

					Noeud_LA fils = new Noeud_LA(prof%2, k1, k2, noeud.vOrdi, noeud.vAdver);
					fils.valeur = evaluationInc(fils.vOrdi, fils.vAdver);  // evaluation du noeud
					noeudFils.addFils(fils);

					// si on a atteint le profondeur maximale, on remonte vers la racine
					if(prof == profondeur) {
						noeud.ajouterFils(fils);	// on doit ajouter le fils avant
						raccourci = remonterRacine(fils, prof%2);

						if(raccourci) {
							break; 
						} else { 
							fils.supprimer(); 
						}
					}
				}
			}

			if(raccourci) { break; }
		}

		nProfSuivant = noeudFils.size();
		if(prof<profondeur) {
			copyNoeudsFils(noeudFils, noeud, fileNoeuds);
		}

		return nProfSuivant;
	}

	/**
	 * This function will change the value of the root when we "raise" a value better and 
	 * will change according to the move which permited the obtaining of this value the vector returned
	 * from the coupSuivant function.  
	 * 
	 * @param noeud Node which will become the root node
	 */
	private static  void changerValeurRacine(Noeud_LA noeud) {
		noeud.pere.valeur = noeud.valeur;
		cSuivant[0] = noeud.vOrdi[0];
		cSuivant[1] = noeud.vOrdi[1];
	}

	/**
	 * This recursive function will try to raise up the found value in the leaf to the root.
	 * If we success or if we ca not raise it up from a depth, we return false.
	 * Because we dont have an alpha-beta shortpath. If the test successes, we return true
	 * 
	 * @param noeud The leaf node to "raise" 
	 * @param parite Parity of the depth in which it is
	 * @return true we have an alpha-beta shortpath, otherwise, false 
	 */
	private static  boolean remonterRacine(Noeud_LA noeud, int parite) {
		noeud.valeurPresent = true;

		switch(parite) {
		case 0: // ordinateur -> coup max
			if(noeud.pere.pere != null) {
				if(noeud.pere.pere.valeurPresent && noeud.pere.pere.valeur < noeud.valeur) {
					return true;    // raccourci alpha-beta
				}

				if(!noeud.pere.valeurPresent || noeud.pere.valeur < noeud.valeur) {
					noeud.pere.valeur = noeud.valeur;
					if(!noeud.pere.valeurPresent) { noeud.pere.valeurPresent = true; }

					return remonterRacine(noeud.pere, 1);	// le pere sera un coup min
				}
			} else { // le pere est la racine
				if(!noeud.pere.valeurPresent || noeud.pere.valeur < noeud.valeur) { changerValeurRacine(noeud); }
				if(!noeud.pere.valeurPresent) { noeud.pere.valeurPresent = true; }
			}
			break;
		case 1: // adversair -> coup min
			if(noeud.pere.pere.valeurPresent && noeud.pere.pere.valeur > noeud.valeur){
				return true;    // raccourci alpha-beta
			}
			if(!noeud.pere.valeurPresent || noeud.pere.valeur > noeud.valeur) {
				noeud.pere.valeur = noeud.valeur;
				if(!noeud.pere.valeurPresent) { noeud.pere.valeurPresent = true; }

				return remonterRacine(noeud.pere, 0);	// le pere sera un coup max
			}
			break;
		default: break;
		}

		return false;
	}

	/**
	 * Getter of the exponantional or the maximal arity of each node
	 *  
	 * @return Chosen exponentional or maximal arity of each node
	 */
	public  int getExp() {
		return exponentielle;
	}

	/**
	 * Setter of the exponantional or the maximal arity of each node
	 * 
	 * @param exp Chosen exponentional or maximal arity of each node
	 */
	public static  void setExp(int exp) {

		if(exp < 2 || exp>40) {
			if(exp < 2) {
				System.out.println("> L'exponentielle souhaitee est trop petite");
			} else {
				System.out.println("> L'exponentielle souhaitee est trop grande");
			}
			return;
		}

		exponentielle = exp;
	}

	/**
	 * Getter of the min-max development depth
	 * 
	 * @return The min-max development depth
	 */
	public  int getProfondeur() {
		return profondeur;
	}

	/**
	 * Setter of the min-max development depth
	 * 
	 * @param prof The min-max development depth
	 */
	public static  void setProfondeur(int prof) {
		if(prof < 2 || prof > 10) {
			System.out.println("Valeur du profondeur mauvaise");
			return;
		}
		profondeur = prof;
	}

}