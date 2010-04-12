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

	/**.
	 * methode qui est appele dans le constructeur de FonctionEvaluation et chaque fois
	 * que l'utilisateur desire commencer un nouveau jeu = typiquement, lors d'appui de boutton "New Game"
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

	/**.
	 * Getter de la valeur du goban a un point specifique
	 * 
	 * @param  les deux entiers coordonees de la matrice de goban
	 * @return  la valeur du goban dans ce point specifique <=> 0 = vide, 1 = ordinateur, 2 = adversair humain
	 */
	public static  int getGobanValue(int coordX, int coordY) {
		return goban[coordX][coordY];
	}

	/**.
	 * la valeur du goban a l'endroit specifie est modifie, si cette valeur fournie est entre 0 et 2, y compris
	 * 
	 * @param  les deux entiers coordonees de la matrice de goban et la valeur qu'on veut etablir
	 * @return  void
	 * 
	 */
	public static  void setGobanValue(int coordX, int coordY, int val) {
		if(val<0 || val>2 || goban[coordX][coordY]!=0) {  // atari go, pas go
			return;
		}

		goban[coordX][coordY] = val;
	}

	/**.
	 * incrementation du nombre de pierres d'ordinateur sur le goban
	 */
	public static  void incPO() {
		cOrdinateur++;
	}

	/**.
	 * incrementation de nombre de pierres adversair humain sur le goban
	 */
	public static  void incPA() {
		cHumain++;
	}

	/**.
	 * le seul constructeur de la classe FonctionEvaluation = initialisation des donnees pour un nouveau jeu
	 */
	public FonctionEvaluation() {
		jeuNeu();
	}

	/**.
	 * methode qui reinitialise la matrice des cases deja� evaluees lors d'un calcul de libertes d'une pierre
	 */
	static void reinitialiserDE() {

		dejaEvalue = new boolean[goban.length][goban[0].length]; // false
	}

	/**.
	 * on a besoin de cette fonction afin de reinitialiser a faux les cases vides dans le goban,
	 * entre deux appels de la fonction de libertes de pierres, puisque les libertes peuvent etre
	 * partagees entre plusieures chaines de pierres
	 * 
	 * @param  la matrice dont les positions correspondant aux cases vides seront initialises
	 *         dans la matrice des evaluations de cases
	 * @return void
	 * 
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

	/**.
	 * fonction recursive qui calcule le nombre de libertes d'une pierre de type defini
	 * attention: avant l'appel de cette methode, il faut pas oublier reinitialiserDE
	 * 
	 * @param  les coordonnes de la pierre à calculer ses libertes et l'adresse de la matrice pour
	 *          laquelle on calcule 
	 * @return  le nombre de libertes de la pierre sur cette position
	 * 
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

	/**.
	 * calcule des libertes de l'etat du jeu qui se trouve dans la racine de l'arbre min-max
	 * par ailleurs, on n'a pas besoin des 2 vecteurs de coups, parce qu'ils sont vides à la racine
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

	/**.
	 * l'evaluation en racine de l'arbre min-max; le resultat est retenu dans la variable globale 'points'
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

	/**.
	 * evaluation incrementale, utilisee pour les feuilles de l'arbre
	 * 
	 * @param les deux vecteurs de coups -ordinateur et humain respectivement-
	 * @return  l'evaluation de l'etat dans ce noeud particulier du devellopement de l'arbre, comme entier
	 * 
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

	/**.
	 * on aura un resultat positif si le nombre de libertes est superieur a 3 et que le joueur est l'ordinateur =
	 * sign -1 ou si le nombre de libertes est inferieur ou egal a 3 et que le joueur est l'adversair humain;
	 * tous les autres cas correspondent aux resultats negatifs
	 * 
	 * @param  le nombre de libertes et le sign = -1 si on veut les points gagnes par l'ordinateur
	 *         dans ce cas là ou 1 si on veut les points gagnes dans la même situation, par l'adversair humain
	 * @return  le nombre de points associes
	 * 
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

	/**.
	 * actualise dans la matrice libInc les libertes correspondantes a l'etat du jeu dans
	 * la racine et les coups representes par les deux vecteurs
	 * 
	 * @param  les deux vecteurs de coups ordinateur et adversair humain
	 * @return  void
	 * 
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

	/**.
	 * fonction qui est appele depuis la classe interface du jeu et etabli si le jeu est fini
	 * 
	 * @param  void
	 * @return  vrai si on a une prise et le jeu est fini, faux sinon
	 * 
	 */
	public static  boolean jeuFini() {
		evaluation();    
		return liberteZero;
	}

	/**.
	 * Retourne les pierres prises en cas de defaite.
	 * 
	 * @param  l'entier correspondant a un coup de joueur gagnant
	 * @return  le Vector des pierres prises du joueur perdant
	 */

	public static AbstractList<Point> pierresPrises(int type) {
		boolean trouve = false;
		Vector<Point> point = new Vector<Point>();
		reinitialiserDE();

		//afficheGobanLibertes();

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

	/**.
	 * Actualisation incrementale de libertes affectees par un des deux vecteurs de coups
	 * 
	 * @param int[], int : le vecteur de coups et le type selon cette ordinateur ou adversair humain
	 * @return  void
	 * 
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

	/**.
	 * calcul des libertes d'une case particuliere le resultat est retenu dans la matrice libInc
	 * 
	 * @param  int, int : les deux coordonnes de la case
	 * @return  void
	 * 
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

	/**.
	 * on copie le goban et on profite du fait qu'on a deja calcule des libertes
	 * dans l'evaluation de la racine et que certaines chaines qui ne seront pas influences par vo et va
	 * 
	 * @param  adresses goban source et goban destination et matrice de libertes source et destination
	 * @return void
	 * 
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

	/**.
	 * Getter des points calcules par la fonction d'evaluation de la racine, non-incrementale
	 * @return int Point calcules par la fonction d'evaluation
	 * 
	 */
	public  int getPoints() {
		return points;
	}


	/**.
	 * fonction qui etablie si l'on peut inserer dans une case vide une pierre
	 * par exemple, on ne peut pas se suicider; cependant, on peut gagner si l'autre n'a plus de libertes
	 * 
	 * @param  le tour de la personne a jouer et les coordonnes de la case pour laquelle on veut savoir
	 *         si il est permis d'y inserer une pierre
	 * @return  vrai si il est permis ou faux sinon
	 * int[]
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

	/**.
	 * la version incrementale de la fonction de permission d'inserer une pierre dans une case vide
	 * 
	 * @param  premiers 3 arguments les memes que ceux de la version non-incrementale et
	 *         les adresses des matrices goban et libertes
	 * @return  vrai si il est permis ou faux sinon
	 * 
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

	/**.
	 * on developpe l'arbre alpha-beta, en employant la fonction d'evaluation incrementale,
	 * puis on retourne les coordonnes entiers x et y du coup suivant de l'ordinateur
	 * les noeuds interieurs et non-feuilles seront eux-memes evalues, afin d'en choisir les meilleurs pour le
	 * developpement de l'arbre min-max
	 * 
	 * @return  le vecteur de 2 elements entiers qui representent les coordonnes du coup suivant de l'ordinateur
	 * 
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


	/**.
	 * copie apra�s l'exploration des noeuds fils d'un noeud de tous les fils choisis,
	 * dans la file parcourue en algorithme BFS - breadth first search
	 * 
	 * @param  la structure qui contient les meilleurs noeuds fils, le noeud parent et
	 *         la file dans laquelle on va mettre les noeuds de la structure
	 * @return  void
	 * 
	 */
	private static  void copyNoeudsFils(NoeudsFils noeudFils, Noeud_LA parent, Queue<Noeud_LA> fileNoeuds) {
		while(noeudFils.size() > 0) {
			Noeud_LA nla = noeudFils.remove(0);
			parent.ajouterFils(nla);
			fileNoeuds.add(nla);
		}
	}

	/**.
	 * l'exploration d'un noeud afin de trouver les meilleurs fils a developper
	 * 
	 * @param  entier profondeur, noeud parent a developper, structure qui retiendra les
	 *         meilleurs noeuds et la file dans laquelle on va mettre les fils trouves, a la fin
	 *         d'etape d'exploration
	 * @return  le nombre de noeuds fils a explorer pour le noeud parent nc; typiquement, ce nombre
	 *          est egal
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

	/**.
	 * cette fonction va changer la valeur de la racine lorsqu'on a remonte une valeur meilleure
	 * et va changer selon le coup qui a permi l'obtention de cette valeur le vecteur retourne par
	 * la fonction coupSuivant
	 * 
	 * @param  un noued qui sera le noeud racine
	 * @return  void
	 * 
	 */
	private static  void changerValeurRacine(Noeud_LA noeud) {
		noeud.pere.valeur = noeud.valeur;
		cSuivant[0] = noeud.vOrdi[0];
		cSuivant[1] = noeud.vOrdi[1];
	}

	/**.
	 * cette fonction recursive va essayer de remonter la valeur trouvee dans la feuille jusqu'a la racine;
	 * si on reussi ou si on ne peux plus remonter a partir d'un profondeur, on va retourner faux,
	 * puisqu'on a pas eu raccourci alpha-beta; si le test alpha-beta reussi, on retourne vrai
	 * 
	 * @param  le noeud feuille a remonte et la parite du profondeur dans lequel il se trouve
	 * @return  vrai si on a un raccourci alpha-beta et faux sinon
	 * 
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

	/**.
	 * Getter de l'exponentielle choisie ou l'arite maximale de chaque noeud
	 * 
	 * @param  void
	 * @return  l'exponentielle choisie ou l'arite maximale de chaque noeud
	 */
	public  int getExp() {
		return exponentielle;
	}

	/**.
	 * Setter de l'exponentielle choisie ou l'arite maximale de chaque noeud
	 * 
	 * @param  l'exponentielle souhaitee ou l'arite maximale de chaque noeud
	 * @return  void
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

	/**.
	 * Getter de le profondeur du developpement de l'arbre min-max
	 * 
	 * @param  void
	 * @return  le profondeur du developpement de l'arbre min-max
	 */
	public  int getProfondeur() {
		return profondeur;
	}

	/**.
	 * Setter de le profondeur souhaite de l'arbre min-max
	 * 
	 * @param  le profondeur souhaite de l'arbre min-max
	 * @return  void
	 */
	public static  void setProfondeur(int prof) {
		if(prof < 2 || prof > 10) {
			System.out.println("Valeur du profondeur mauvaise");
			return;
		}
		profondeur = prof;
	}

}