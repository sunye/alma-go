package main.java.alexanddam.logic;

import java.awt.Point;
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
    private static  Vector<Point> groupe;      	// groupe des noeuds chaines afin de reduire les calculs des libertés
    private static  int[] cSuivant;				// coup suivant, composé de deux entiers
    private static  boolean liberteZero;      	// vrai si le jeu est fini, faux sinon

 /*
  * methode qui est appelé dans le constructeur de FonctionEvaluation et chaque fois
  * que l'utilisateur desire commencer un nouveau jeu = typiquement, lors d'appui de boutton "New Game"
  */
public static  void jeuNeu() {

    int i,j;

    goban = new int[9][9];
    gobanInc = new int[goban.length][goban[0].length];
    cSuivant = new int[2];

    points = cOrdinateur = cHumain =0;

    // l'exponentielle et profondeur par defaut
    exponentielle = 10;
    profondeur = 9 - (int)(Math.ceil(Math.sqrt(exponentielle+1)));

    lib = new int[goban.length][goban[0].length];
    libInc = new int[lib.length][lib[0].length];  // initialisation matrice des libertes incrementale

    for(i=0; i<goban.length; i++)
            for(j=0; j<goban.length; j++)
                    lib[i][j] = -1;

    reinitialiserDE(); // false
    groupe = new Vector<Point>();
    liberteZero = false;
}

/*
 * @param  les deux entiers coordonées de la matrice de goban
 * @return  la valeur du goban dans ce point specifique <=> 0 = vide, 1 = ordinateur, 2 = adversair humain
 */
public static  int getGobanValue(int x, int y) {
    return goban[x][y];
}

/*
 * @param  les deux entiers coordonees de la matrice de goban et la valeur qu'on veut etablir
 * @return  void
 * la valeur du goban a� l'endroit specifie est modifié, si cette valeur fournie est entre 0 et 2, y compris
 */
public static  void setGobanValue(int x, int y, int t) {
    if(t<0 || t>2 || goban[x][y]!=0) {  // atari go, pas go
        return;
    }

    goban[x][y] = t;
}

/*
 * incrementation du nombre de pierres d'ordinateur sur le goban
 */
public static  void incPO() {
        cOrdinateur++;
}

/* 
 * incrementation de nombre de pierres adversair humain sur le goban
 */
public static  void incPA() {
        cHumain++;
}

/*
 * le seul constructeur de la classe FonctionEvaluation = initialisation des données pour un nouveau jeu
 */
public FonctionEvaluation() {
    jeuNeu();
}

 /*
  * méthode qui réinitialise la matrice des casses déjà évaluées lors d'un calcul de libertés d'une pierre
  */
 static void reinitialiserDE() {

        dejaEvalue = new boolean[goban.length][goban[0].length]; // false
}

 /*
  * @param  la matrice dont les positions correspondant aux casses vides seront initialisés
  *         dans la matrice des évaluations de casses
  * @return void
  * on a besoin de cette fonction afin de reinitialiser à faux les casses vides dans le goban,
  * entre deux appels de la fonction de libertes de pierres, puisque les libertés peuvent être
  * partagées entre plusisuers chaines de pierres
  */
 static void reinitialiserVideDE(int[][] g) {
    int i=0,j;

    for(; i < dejaEvalue.length; i++)
        for(j=0; j< dejaEvalue[0].length; j++)
            if(dejaEvalue[i][j] && g[i][j]==0) {
                dejaEvalue[i][j] = false;
            }
}

/*
 * @param  les coordonnés de la pierre à calculer ses libertés et l'adresse de la matrice pour
 *          laquelle on calcule 
 * @return  le nombre de libertés de la pierre sur cette position
 * fonction recursive qui calcule le nombre de libertés d'une pierre de type défini
 * attention: avant l'appel de cette méthode, il faut pas oublier reinitialiserDE
 */
 static int libertes(int x, int y, int[][] g) {

    int lr = 0, type = g[x][y];
    dejaEvalue[x][y] = true;			// cette case est en cours de visite -> on marque la visite

    // tout ce qui est vide autour de cette position est compté comme une liberté
    if(x>0 && g[x-1][y]==0 && !dejaEvalue[x-1][y]) {
            lr += 1; dejaEvalue[x-1][y] = true;
    }
    if(y>0 && g[x][y-1]==0 && !dejaEvalue[x][y-1]) {
            lr += 1; dejaEvalue[x][y-1] = true;
    }
    if(x<g.length-1 && g[x+1][y]==0 && !dejaEvalue[x+1][y]) {
            lr += 1; dejaEvalue[x+1][y] = true;
    }
    if(y<g.length-1 && g[x][y+1]==0 && !dejaEvalue[x][y+1]) {
            lr += 1; dejaEvalue[x][y+1] = true;
    }

    // si la casse a l'est existe et on ne l'a pas testé
    if(x>0 && g[x-1][y]==type && !dejaEvalue[x-1][y]) {
            groupe.add(new Point(x-1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
            lr += libertes(x-1, y, g);
    }

    // si la casse au nord existe et on ne l'a pas testé
    if(y>0 && g[x][y-1]==type && !dejaEvalue[x][y-1]) {
            groupe.add(new Point(x,y-1));		// on ajoute au groupe; cette pierre aura la meme liberte
            lr += libertes(x, y-1, g);
    }

    // si la casse a l'ouest existe et on ne l'a pas testé
    if(x<g.length-1 && g[x+1][y]==type && !dejaEvalue[x+1][y]) {
            groupe.add(new Point(x+1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
            lr += libertes(x+1, y, g);
    }

    // si la casse au sud existe et on ne l'a pas testé
    if(y<g.length-1 && g[x][y+1]==type && !dejaEvalue[x][y+1]) {
            groupe.add(new Point(x,y+1));		// on ajoute au groupe; cette pierre aura la meme liberte
            lr += libertes(x, y+1, g);
    }

    return lr;
}

/*
 * calcule les libertés de l'état du jeu qui se trouve dans la racine de l'arbre min-max
 * par ailleurs, on n'a pas besoin des 2 vecteurs de coups, parce qu'ils sont vides à la racine
 */
 static void calculerLibertes() {

    int i = 0, j = 0, aux;
    boolean start = true;

    // reinitialiser la matrice des visites
    reinitialiserDE();
   
    for( ; i < goban.length; i++)
        for(j = 0 ; j < goban.length; j++)
            if(goban[i][j]!=0 && !dejaEvalue[i][j]) {
                if(!start) {
                    reinitialiserVideDE(goban); 
                }
                start = false;
                aux = lib[i][j] = libertes(i, j, goban);
                if(aux==0) { liberteZero = true; }   // la classe du jeu se rend compte de la fin de jeu

                while(! groupe.isEmpty()) {
                    Point p = groupe.remove(0);
                    lib[p.x][p.y] = aux;
                }
            }
}

/*
 * l'évaluation en racine de l'arbre min-max
 * le résultat est retenu dans la variable globale 'points'
 */
 static void evaluation() {
    int i, j;

    points = 0; 	// on reinitialise, parce qu'on va evaluer toute le grille, pas incrementale
    calculerLibertes(); // calculer les libertes de toutes les pierres de facon efficiente

    // on compte dans le double for les libertes de l'ordinateur et du l'adversaire
    for(i=0; i<goban.length; i++)
        for(j=0; j<goban.length; j++)
            if(goban[i][j]!=0) {
                points += pointsPerCasse(lib[i][j], 2 * goban[i][j] - 3);
           }
}

/*
 * @param les deux vecteurs de coups -ordinateur et humain respectivement-
 * @return  l'évaluation de l'état dans ce noeud particulier du dévellopement de l'arbre, comme entier
 * evaluation incrementale, utilisee pour les feuilles de l'arbre
 */
 static int evaluationInc(int[] vo, int[] va) {

    int pointsV = 0, i, j;
    calculerLibertesInc(vo, va);

     for(i=0; i<gobanInc.length; i++)
        for(j=0; j<gobanInc.length; j++)
            if(gobanInc[i][j]!=0) {

                /* explication deuxieme argument: on a besoin de -1 quand la casse est occupée
                 * par une pierre ordinateuer et 1 lors d'une pierre d'adversair humain
                 */
                pointsV += pointsPerCasse(libInc[i][j], 2 * gobanInc[i][j] - 3);
            }

     return pointsV;
}

 /*
  * @param  le nombre de libertés et le sign = -1 si on veut les points gagnés par l'ordinateur
  *         dans ce cas là ou 1 si on veut les points gagnés dans la même situation, par l'adversair humain
  * @return  le nombre de points associés
  * on aura un résultat positif si le nombre de libertés est supérieur à 3 et que le joueur est l'ordinateur =
  * sign -1 ou si le nombre de libertés est inférieur ou égal à 3 et que le joueur est l'adversair humain;
  * tous les autres cas corréspondent aux résultats négatifs
  */
 static int pointsPerCasse(int libertes, int sign) {
    int p = 0;

    switch(libertes) {
        case 3: p += sign * 10; break;
        case 2: p += sign * 50; break;
        case 1: p += sign * 100; break;
        case 0: p += sign * 1000; break;
        default: p -= sign * 2 * libertes;
    }

    return p;
}

/*
 * @param  les deux vecteurs de coups ordinateur et adversair humain
 * @return  void
 * actualise dans la matrice libInc les libertés corréspondantes à l'état du jeu dans
 * la racine et les coups representés par les deux vecteurs
 */
private static  void calculerLibertesInc(int[] vo, int[] va) {
    int i;		// variables pour optimisation

    /* on copie les libertés et le goban de la racine dans les matrices incrémentales,
     * afin de reduire des nombreuses calculs, surtout pour les libertés des pierres qui
     * ne sont pas affectés par la suite des coups décrite par les deux vecteurs 
     */
    copierLibertesEtGoban(gobanInc, goban, libInc, lib);

    // on réalise les changements necessaires dans gobanInc
    i = 0;
    while(i < vo.length) {
        gobanInc[vo[i]][vo[i+1]] = 1;	// l'ordinateur
        i += 2;
    }

    i = 0;
    while(i < va.length) {
        gobanInc[va[i]][va[i+1]] = 2;	// l'adversair
        i += 2;
    }

    actualiserLibertes(vo, 1);  // actualise les libertés affectés par le vecteur de coups d'ordinateur
    actualiserLibertes(va, 2);  // actualise les libertés affectés par le vecteur de coups d'adversair humain
}

/*
 * @param  void
 * @return  vrai si on a une prise et le jeu est fini, faux sinon
 * fonction qui est appelé depuis la classe interface du jeu et établi si le jeu est fini
 */
public static  boolean jeuFini() {
        evaluation();
        
    return liberteZero;
}

/*
 * @param  l'entier correspondant à un coup de joueur gagnant
 * @return  le Vector des pierres prises du joueur perdant
 */

public static Vector<Point> pierresPrises(int type) {
    int i=0, j=0;
    boolean trouve = false;
    Vector<Point> p = new Vector<Point>();
    reinitialiserDE();

    //afficheGobanLibertes();

    for(; i < goban.length; i++) {
        for( j=0; j < goban.length; j++) {
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
        p.add(groupe.remove(0));
    }

    return p;
}
/**.
 * Affiche les libert�s sur le gobans
 */
private static void afficheGobanLibertes() {
    int i = 0, j = 0;

    System.out.println("");
    System.out.println("goban");
   for(; i < goban.length; i++) {
       for( j=0; j < goban.length; j++) {
           System.out.print(goban[i][j]+"  ");
       }
       System.out.println();
   }

    System.out.println("");
    System.out.println("libertes");
   for(i = 0; i < goban.length; i++) {
       for( j=0; j < goban.length; j++) {
           System.out.print(lib[i][j]+"  ");
       }
       System.out.println();
   }
}
/*
 * @param  le vecteur de coups et le type selon cette ordinateur ou adversair humain
 * @return  void
 * actualisation incrémentale de libertés affectées par un des deux vecteurs de coups
 */
private static  void actualiserLibertes(int[] v, int type) {
    int i = 0, v1, v2;
    reinitialiserDE();

    while( i < v.length ) {
        v1 = v[i]; v2 = v[i+1];
        if(!dejaEvalue[v1][v2]) {
            modifierLibertesInc(v1, v2);
            
            // il y a peut-etre des pierres de l'autre joueur autour de v1 et v2 dont les libertes seront affectees
            if(v1 > 0 && gobanInc[v1 - 1][v2] == 3 - type) {
                modifierLibertesInc(v1 - 1, v2);
            }
            if(v1 < gobanInc.length - 1 && gobanInc[v1 + 1][v2] == 3 - type) {
                modifierLibertesInc(v1 + 1, v2);
            }
            if(v2 > 0 && gobanInc[v1][v2 - 1] == 3 - type) {
                modifierLibertesInc(v1, v2 - 1);
            }
            if(v2 < gobanInc.length - 1 && gobanInc[v1][v2 + 1] == 3 - type) {
                modifierLibertesInc(v1, v2 + 1);
            }
        }
        i += 2;
    }
}

/*
 * @param  les deux coordonnés de la casse
 * @return  void
 * calcul de libertés d'une casse particulière
 * le résultat est retenu dans la matrice libInc
 */
private static  void modifierLibertesInc(int v1, int v2) {
    int aux;

    reinitialiserVideDE(gobanInc);
    aux = libInc[v1][v2] = libertes(v1, v2, gobanInc);
    while(! groupe.isEmpty()) {
        Point p = groupe.remove(0);
        libInc[p.x][p.y] = aux;
    }
}

/* 
 * @param  adresses goban source et goban destination et matrice de libertés source et destination
 * @return void
 * on copie le goban et on profite du fait qu'on a déjà calculé des libertés
 * dans l'évaluation de la racine et que certaines chaines qui ne seront pas influences par vo et va
 */
private static  void copierLibertesEtGoban(int[][] gDest, int[][] gSource, int[][] lDest, int[][] lSource) {
    int i, j;

    for(i = 0; i < gSource.length; i++)
        for(j = 0; j < gSource[0].length; j++)
            gDest[i][j] = gSource[i][j];

    for(i = 0; i < lSource.length; i++)
        for(j = 0; j < lSource[0].length; j++)
            lDest[i][j] = lSource[i][j];

}

/*
 * on recupère les points calculés par la fonction d'évaluation de la racine, non-incrémentale
 */
public  int getPoints() {
        return points;
}

 
/*
 * @param  le tour de la personne à jouer et les coordonnés de la casse pour laquelle on veut savoir
 *         si il est permis d'y insérer une pierre
 * @return  vrai si il est permis ou faux sinon
 * fonction qui établie si l'on peut insérer dans une casse vide une pierre
 * par exemple, on ne peut pas se suicider; cependant, on peut gagner si l'autre n'a plus de libertés
 */
public static  boolean permissible(int tour, int x, int y) {
    /* si il y au moins une place libre autour de lui, c'est permissible
     * le cas le plus repandu, c'est pour ca qu'on l'a mis comme premier test
     */
    if((x > 0 && goban[x-1][y]==0) || (x < goban.length - 1 && goban[x+1][y]==0) ||
            (y > 0 && goban[x][y-1]==0) || (y < goban.length - 1 && goban[x][y+1]==0))
        return true;

    /* si les propres pierres qui l'entourent ont le nombre de libertes == 1
     * et toutes les autres pierres ont un liberte > 1, c'est pas permissible
     */
    if( ( !(x > 0) || (goban[x-1][y]==tour+1 && lib[x-1][y]==1) || (goban[x-1][y]==2-tour && lib[x-1][y]>1)) &&
            ( !(x < goban.length - 1) || (goban[x+1][y]==tour+1 && lib[x+1][y]==1) || (goban[x+1][y]==2-tour && lib[x+1][y]>1)) &&
            ( !(y > 0) || (goban[x][y-1]==tour+1 && lib[x][y-1]==1) || (goban[x][y-1]==2-tour && lib[x][y-1]>1)) &&
            ( !(y < goban.length - 1) || (goban[x][y+1]==tour+1 && lib[x][y+1]==1) || (goban[x][y+1]==2-tour && lib[x][y+1]>1)) )
        return false;

    // toutes les conditions sont accomplies
    return true;
}

/* @param  premiers 3 arguments les mêmes que ceux de la version non-incrémentale et
 *         les adresses des matrices goban et libertés
 * @return  vrai si il est permis ou faux sinon
 * la version incrémentale de la fonction de permission d'insérer une pierre dans une casse vide
 */
public static  boolean permissibleInc(int tour, int x, int y, int[][] gI, int[][] lI) {
    if((x > 0 && gI[x-1][y]==0) || (x < gI.length - 1 && gI[x+1][y]==0) ||
       (y > 0 && gI[x][y-1]==0) || (y < gI.length - 1 && gI[x][y+1]==0))
            return true;

    if( ( !(x > 0) || (gI[x-1][y]==tour+1 && lI[x-1][y]==1) || (gI[x-1][y]==2-tour && lI[x-1][y]>1)) &&
        ( !(x < gI.length - 1) || (gI[x+1][y]==tour+1 && lI[x+1][y]==1) || (gI[x+1][y]==2-tour && lI[x+1][y]>1)) &&
        ( !(y > 0) || (gI[x][y-1]==tour+1 && lI[x][y-1]==1) || (gI[x][y-1]==2-tour && lI[x][y-1]>1)) &&
        ( !(y < gI.length - 1) || (gI[x][y+1]==tour+1 && lI[x][y+1]==1) || (gI[x][y+1]==2-tour && lI[x][y+1]>1)) )
            return false;

    // toutes les conditions sont accomplies
    return true;
}

/* 
 * @param  void
 * @return  le vecteur de 2 éléments entiers qui représentent les coordonnés du coup suivant de l'ordinateur
 * on devéloppe l'arbre alpha-beta, en employant la fonction d'évaluation incrémentale,
 * puis on retourne les coordonnés entiers x et y du coup suivant de l'ordinateur
 * les noeuds intérieurs et non-feuilles seront eux-mêmes évalués, afin d'en choisir les meilleurs pour le
 * devéloppement de l'arbre min-max
 */
public static  int[] coupSuivant() {
    int i = 2, j, nProf;
    int nProfSuivant;					// tc est la taille de carre a explorer

    Queue<Noeud_LA> qn = new ArrayDeque<Noeud_LA>(); // la file des noeuds a traiter sur un certain profondeur
    evaluation();
    Arbre_LA ab = new Arbre_LA(exponentielle, 0);

    Noeud_LA racine = (Noeud_LA)(ab.c);   // on recupere la racine
    qn.add(racine);  nProf = 1;		  // 1 seul noeud sur profondeur 1 - la racine

    System.out.println("Profondeur actuel " + profondeur);
    for(i=2 ; i <= profondeur; i++) {     // boucle principale de l'algorithme alpha-beta
        j = 0; nProfSuivant = 0;

        while(j < nProf) {		// pour chaque noeud du profondeur
            Noeud_LA nc = ((ArrayDeque<Noeud_LA>)qn).pollFirst();  // on extrait le premier noeud
            NoeudsFils nf = new NoeudsFils(exponentielle, i%2 == 0);
            nProfSuivant += parcoursProfondeur(i, nc, nf, qn);

            j++;
        } // fin while j < nProf

        nProf = nProfSuivant; // pour le profondeur suivant
    } // fin for boucle profondeur

    return cSuivant;
}


/*
 * @param  la structure qui contient les meilleurs noeuds fils, le noeud parent et
 *         la file dans laquelle on va mettre les noeuds de la structure
 * @return  void
 * copie après l'exploration des noeuds fils d'un noeud de tous les fils choisis,
 * dans la file parcourue en algorithme BFS - breadth first search
 */
private static  void copyNoeudsFils(NoeudsFils nf, Noeud_LA parent, Queue<Noeud_LA> ad) {
    while(nf.size() > 0) {
        Noeud_LA nla = nf.remove(0);
        parent.ajouterFils(nla);
        ad.add(nla);
    }
}

/*
 * @param  entier profondeur, noeud parent à devélopper, structure qui retiendra les
 *         meilleurs noeuds et la file dans laquelle on va mettre les fils trouvés, à la fin
 *         d'étape d'exploration
 * @return  le nombre de noeuds fils à explorer pour le noeud parent nc; typiquement, ce nombre
 *          est égal
 * l'exploration d'un noeud afin de trouver les meilleurs fils à devélopper
 */
private static  int parcoursProfondeur(int i, Noeud_LA nc, NoeudsFils nf, Queue<Noeud_LA> qn) {
    int k1, k2, nProfSuivant;
    boolean raccourci = false;
    int[][] gIncParent = new int[gobanInc.length][gobanInc[0].length],
            lIncParent = new int[libInc.length][libInc[0].length];

    // calculer le goban et libertés en état du noeud nc
    calculerLibertesInc(nc.vo, nc.va);
    copierLibertesEtGoban(gIncParent, gobanInc, lIncParent, libInc);

    for( k1 = 0; k1 < gobanInc.length; k1++) {
        for( k2 = 0; k2 < gobanInc[0].length; k2++) {
            if(gIncParent[k1][k2] == 0 && permissibleInc(i%2, k1, k2, gIncParent, lIncParent)) {

                Noeud_LA fils = new Noeud_LA(i%2, k1, k2, nc.vo, nc.va);
                fils.valeur = evaluationInc(fils.vo, fils.va);  // evaluation du noeud
                nf.addFils(fils);

                // si on a atteint le profondeur maximale, on remonte vers la racine
                if(i == profondeur) {
                    nc.ajouterFils(fils);	// on doit ajouter le fils avant
                    raccourci = remonterRacine(fils, i%2);

                    if(raccourci) { break; }
                    else { fils.supprimer(); }
                }
           }
        }

        if(raccourci) { break; }
    }

    nProfSuivant = nf.size();
    if(i<profondeur) {
        copyNoeudsFils(nf, nc, qn);
    }

    return nProfSuivant;
}

/*
 * @param  un noued qui sera le noeud racine
 * @return  void
 * cette fonction va changer la valeur de la racine lorsqu'on a remonté une valeur meilleure
 * et va changer selon le coup qui a permi l'obtention de cette valeur le vecteur retourné par
 * la fonction coupSuivant
 */
private static  void changerValeurRacine(Noeud_LA n) {
    n.pere.valeur = n.valeur;
    cSuivant[0] = n.vo[0];
    cSuivant[1] = n.vo[1];
}

/*
 * @param  le noeud feuille à remonté et la parité du profondeur dans lequel il se trouve
 * @return  vrai si on a un raccourci alpha-beta et faux sinon
 * cette fonction recursive va essayer de remonter la valeur trouvée dans la feuille jusqu'à la racine;
 * si on réussi ou si on ne peux plus remonter à partir d'un profondeur, on va retourner faux,
 * puisqu'on a pas eu raccourci alpha-beta; si le test alpha-beta réussi, on retourne vrai
 */
private static  boolean remonterRacine(Noeud_LA n, int i) {
	n.valeurPresent = true;

    switch(i) {
        case 0: // ordinateur -> coup max
            if(n.pere.pere != null) {
                if(n.pere.pere.valeurPresent && n.pere.pere.valeur < n.valeur) {
                    return true;    // raccourci alpha-beta
                }

                if(!n.pere.valeurPresent || n.pere.valeur < n.valeur) {
                    n.pere.valeur = n.valeur;
                    if(!n.pere.valeurPresent) { n.pere.valeurPresent = true; }

                    return remonterRacine(n.pere, 1);	// le pere sera un coup min
                }
            }
            else { // le pere est la racine
                if(!n.pere.valeurPresent || n.pere.valeur < n.valeur) { changerValeurRacine(n); }
                if(!n.pere.valeurPresent) { n.pere.valeurPresent = true; }
            }
           break;
        case 1: // adversair -> coup min
            if(n.pere.pere.valeurPresent && n.pere.pere.valeur > n.valeur){
                return true;    // raccourci alpha-beta
            }
            if(!n.pere.valeurPresent || n.pere.valeur > n.valeur) {
                n.pere.valeur = n.valeur;
                if(!n.pere.valeurPresent) { n.pere.valeurPresent = true; }

                return remonterRacine(n.pere, 0);	// le pere sera un coup max
            }
            break;
        default: break;
    }

    return false;
}

/*
 * @param  void
 * @return  l'exponentielle choisie ou l'arité maximale de chaque noeud
 */
public  int getExp() {
        return exponentielle;
}

/*
 * @param  l'exponentielle souhaitée ou l'arité maximale de chaque noeud
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

/*
 * @param  void
 * @return  le profondeur du devéloppement de l'arbre min-max
 */
public  int getProfondeur() {
    return profondeur;
}

/*
 * @param  le profondeur souhaité de l'arbre min-max
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