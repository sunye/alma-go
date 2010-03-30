package main.java.alexanddam.logic;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Vector;

public class FonctionEvaluation {

    private static int[][] goban;		// 0 - rien, 1 - ordinateur, 2 - adversair
    private static int[][] gobanInc;            // utilisable pour evaluer les feuilles
    private static int[][] l;			// une matrice qui met a jour les libertes des pierres, apres chaque coup - sauver des calculs -> -1, si il n'y a pas de pierres
    private static int[][] lInc;		// matrice des libertes de pierres, mais qui servira a l'evaluation incrementale de feuilles
    private static boolean[][] de;		// matrice des pierres deja evaluees
    private static int points;			// on memorise le nombre de points afin de profiter de la evaluation incrementale
    private static int exp;			// l'exponentielle maximale ou l'arite maximal de l'arbre
    private static int pO;			// le nombre de pierres de l'ordinateur situees sur le goban
    private static int pA;			// le nombre de pierres de l'adversair situees sur le goban
    private static int profondeur;		// le profondeur de l'arbre, calculable a partir de l'exponentielle
    private static Vector<Point> groupe;        // groupe des noeuds chaines afin de reduire les calculs des libertes
    private static int[] cSuivant;		// coup suivant, forme de deux entiers
    private static boolean liberteZero;             // si le jeu est fini

// bloc d'initialisation statique, execute pendant le chargement de la classe
static {
    jeuNeu();
}

public static void jeuNeu() {

    int i,j;

    goban = new int[9][9];
    gobanInc = new int[goban.length][goban[0].length];
    cSuivant = new int[2];

    points = pO = pA =0;

    // l'exponentielle et profondeur par defaut
    FonctionEvaluation.exp = 10;
    profondeur = 9 - (int)(Math.ceil(Math.sqrt(FonctionEvaluation.exp+1)));

    l = new int[goban.length][goban[0].length];
    lInc = new int[l.length][l[0].length];  // initialisation matrice des libertes incrementale

    for(i=0; i<goban.length; i++)
            for(j=0; j<goban.length; j++)
                    l[i][j] = -1;

    reinitialiserDE(); // false
    groupe = new Vector<Point>();
    liberteZero = false;
}

public static int getGobanValue(int x, int y) {
    return goban[x][y];
}

public static void setGobanValue(int x, int y, int t) {
    if(t<0 || t>2 || goban[x][y]!=0) {  // atari go, pas go
        return;
    }

    goban[x][y] = t;
}

// incrementation de nombre de pierres ordinateur
public static void incPO() {
        pO++;
}

// incrementation de nombre de pierres adversair
public static void incPA() {
        pA++;
}

// on bloque l'access au contructeur, puisqu'on n'a pas besoin des objets
private FonctionEvaluation() {

}

static void reinitialiserDE() {

        de = new boolean[goban.length][goban[0].length]; // false
}

static void reinitialiserVideDE(int[][] g) {
    int i=0,j;

    for(; i < de.length; i++)
        for(j=0; j< de[0].length; j++)
            if(de[i][j] && g[i][j]==0) {
                de[i][j] = false;
            }
}

/*fonction recursive qui calcule le nombre de libertes d'une pierre de type défini
 * attention: avant l'appel de cette methode, il faut pas oublier reinitialiserDE
 */
static int libertes(int x, int y, int[][] g) {

    int lr = 0, type = g[x][y];
    de[x][y] = true;			// cette case est en cours de visite -> on marque la visite

    if(x>0 && g[x-1][y]==0 && !de[x-1][y]) {
            lr += 1; de[x-1][y] = true;
    }
    if(y>0 && g[x][y-1]==0 && !de[x][y-1]) {
            lr += 1; de[x][y-1] = true;
    }
    if(x<g.length-1 && g[x+1][y]==0 && !de[x+1][y]) {
            lr += 1; de[x+1][y] = true;
    }
    if(y<g.length-1 && g[x][y+1]==0 && !de[x][y+1]) {
            lr += 1; de[x][y+1] = true;
    }

    // si la casse a l'est existe et on ne l'a pas teste
    if(x>0 && g[x-1][y]==type && !de[x-1][y]) {
            groupe.add(new Point(x-1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
            lr += libertes(x-1, y, g);
    }

    // si la casse au nord existe et on ne l'a pas teste
    if(y>0 && g[x][y-1]==type && !de[x][y-1]) {
            groupe.add(new Point(x,y-1));		// on ajoute au groupe; cette pierre aura la meme liberte
            lr += libertes(x, y-1, g);
    }

    // si la casse a l'ouest existe et on ne l'a pas teste
    if(x<g.length-1 && g[x+1][y]==type && !de[x+1][y]) {
            groupe.add(new Point(x+1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
            lr += libertes(x+1, y, g);
    }

    // si la casse au sud existe et on ne l'a pas teste
    if(y<g.length-1 && g[x][y+1]==type && !de[x][y+1]) {
            groupe.add(new Point(x,y+1));		// on ajoute au groupe; cette pierre aura la meme liberte
            lr += libertes(x, y+1, g);
    }

    return lr;
}

// calculer toutes les libertes de pierres
static void calculerLibertes() {

    int i = 0, j = 0, aux;
    boolean start = true;

    // reinitialiser la matrice des visites
    reinitialiserDE();
   
    for( ; i < goban.length; i++)
        for(j = 0 ; j < goban.length; j++)
            if(goban[i][j]!=0 && !de[i][j]) {
                if(!start) {
                    reinitialiserVideDE(goban); 
                }
                start = false;
                aux = l[i][j] = libertes(i, j, goban);
                if(aux==0) { liberteZero = true; }

                while(! groupe.isEmpty()) {
                    Point p = groupe.remove(0);
                    l[p.x][p.y] = aux;
                }
            }
}

// evaluation de la racine de l'arbre, non-incrementale
static void evaluation() {
    int i, j;

    points = 0; 		// on reinitialise, parce qu'on va evaluer toute le grille, pas incrementale
    calculerLibertes(); // calculer les libertes de toutes les pierres de facon efficiente

    // on compte dans le double for les libertes de l'ordinateur et du l'adversaire
    for(i=0; i<goban.length; i++)
        for(j=0; j<goban.length; j++)
            if(goban[i][j]!=0) {
                points += pointsPerCasse(l[i][j], 2 * goban[i][j] - 3);
           }
}

// evaluation incrementale, utilisee pour les feuilles de l'arbre
static int evaluationInc(int[] vo, int[] va, int profActuel) {

    int pointsV = 0, i, j;
    calculerLibertesInc(vo, va);

     for(i=0; i<gobanInc.length; i++)
        for(j=0; j<gobanInc.length; j++)
            if(gobanInc[i][j]!=0) {
                pointsV += pointsPerCasse(lInc[i][j], 2 * gobanInc[i][j] - 3);
            }

     return pointsV;
}

static int pointsPerCasse(int libertes, int sign) {
    int p = 0;

    switch(libertes) {
        case 2: p += sign * 50; break;
        case 1: p += sign * 100; break;
        case 0: p += sign * 1000; break;
        default: p -= sign * 2 * libertes;
    }

    return p;
}

private static void calculerLibertesInc(int[] vo, int[] va) {
    int i;		// variables pour optimisation
    copierLibertesEtGoban(gobanInc, goban, lInc, l);

    // maintenant on realise les changements necessaires dans gobanInc
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

    //groupe = new Vector<Point>();		// et la groupe qui sera toujours utilise
    actualiserLibertes(vo, 1);
    actualiserLibertes(va, 2);
}

public static boolean jeuFini() {
        FonctionEvaluation.evaluation();
        
    return liberteZero;
}

public static Vector<Point> pierresPrises(int type) {
    int i=0, j=0;
    boolean trouve = false;
    Vector<Point> p = new Vector<Point>();

    afficheGobanLibertes();

    for(; i < goban.length; i++) {
        for( j=0; j < goban.length; j++) {
            if(l[i][j] == 0 && goban[i][j] != type) {
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
           System.out.print(l[i][j]+"  ");
       }
       System.out.println();
   }
}

private static void actualiserLibertes(int[] v, int type) {
    // il nous reste simplement appeler libertesInc pour les coups concernes
    int i = 0, v1, v2;
    reinitialiserDE();

    while( i < v.length ) {
        v1 = v[i]; v2 = v[i+1];
        if(!de[v1][v2]) {
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

private static void modifierLibertesInc(int v1, int v2) {
    int aux;

    reinitialiserVideDE(gobanInc);
    aux = lInc[v1][v2] = libertes(v1, v2, gobanInc);
    while(! groupe.isEmpty()) {
        Point p = groupe.remove(0);
        lInc[p.x][p.y] = aux;
    }
}

/* on copie le goban et on profite du fait qu'on a deja calcule des libertes
 * dans l'evaluation de la racine des chaines qui ne seront pas influences par vo et va
 */
private static void copierLibertesEtGoban(int[][] gDest, int[][] gSource, int[][] lDest, int[][] lSource) {
    int i, j;

    for(i = 0; i < gSource.length; i++)
        for(j = 0; j < gSource[0].length; j++)
            gDest[i][j] = gSource[i][j];

    for(i = 0; i < lSource.length; i++)
        for(j = 0; j < lSource[0].length; j++)
            lDest[i][j] = lSource[i][j];

}

public static int getPoints() {
        return points;
}

static void setPoints(int points) {
        FonctionEvaluation.points = points;
}

/* fonction qui etablie si l'on peut inserer dans un endroit particulier une piece
 * par exemple, on ne peut pas se suicider, mais on peut gagner si l'autre n'a plus de libertes
 */
public static boolean permissible(int tour, int x, int y) {
    /* si il y au moins une place libre autour de lui, c'est permissible
     * le cas le plus repandu, c'est pour ca qu'on l'a mis comme premier test
     */
    if((x > 0 && goban[x-1][y]==0) || (x < goban.length - 1 && goban[x+1][y]==0) ||
            (y > 0 && goban[x][y-1]==0) || (y < goban.length - 1 && goban[x][y+1]==0))
        return true;

    /* si les propres pierres qui l'entourent ont le nombre de libertes == 1
     * et toutes les autres pierres ont un liberte > 1, c'est pas permissible
     */
    if( ( !(x > 0) || (goban[x-1][y]==tour+1 && l[x-1][y]==1) || (goban[x-1][y]==2-tour && l[x-1][y]>1)) &&
            ( !(x < goban.length - 1) || (goban[x+1][y]==tour+1 && l[x+1][y]==1) || (goban[x+1][y]==2-tour && l[x+1][y]>1)) &&
            ( !(y > 0) || (goban[x][y-1]==tour+1 && l[x][y-1]==1) || (goban[x][y-1]==2-tour && l[x][y-1]>1)) &&
            ( !(y < goban.length - 1) || (goban[x][y+1]==tour+1 && l[x][y+1]==1) || (goban[x][y+1]==2-tour && l[x][y+1]>1)) )
        return false;

    // toutes les conditions sont accomplies
    return true;
}

/* la version incrementale de la fonction de permissible
 * les memes parametres, seulement goban et l changent vers leurs versions incrementale
 */
public static boolean permissibleInc(int tour, int x, int y, int[][] gI, int[][] lI) {
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

/* on developpe l'arbre alpha-beta, en employant la fonction d'evaluation,
 * puis on retourne les coordones entiers x et y du coup suivant de l'ordinateur
 */
public static int[] coupSuivant() {
    int i = 2, j, nProf;
    int nProfSuivant;					// tc est la taille de carre a explorer

    Queue<Noeud_LA> qn = new ArrayDeque<Noeud_LA>(); // la file des noeuds a traiter sur un certain profondeur
    FonctionEvaluation.evaluation();
    Arbre_LA ab = new Arbre_LA(FonctionEvaluation.exp, 0);

    Noeud_LA racine = (Noeud_LA)(ab.c);   // on recupere la racine
    qn.add(racine);  nProf = 1;		  // 1 seul noeud sur profondeur 1 - la racine

    System.out.println("Profondeur actuel " + profondeur);
    for(i=2 ; i <= profondeur; i++) {     // boucle principale de l'algorithme alpha-beta
        j = 0; nProfSuivant = 0;

        while(j < nProf) {		// pour chaque noeud du profondeur
            Noeud_LA nc = ((ArrayDeque<Noeud_LA>)qn).pollFirst();  // on extrait le premier noeud
            NoeudsFils nf = new NoeudsFils(FonctionEvaluation.exp, i%2 == 0);
            nProfSuivant += parcoursProfondeur(i, nc, nf, qn);

            j++;
        } // fin while j < nProf

        nProf = nProfSuivant; // pour le profondeur suivant
    } // fin for boucle profondeur

    return cSuivant;
}

private static void copyNoeudsFils(NoeudsFils nf, Noeud_LA parent, Queue<Noeud_LA> ad, int i) {
    while(nf.size() > 0) {
        Noeud_LA nla = nf.remove(0);
        parent.ajouterFils(nla);
        ad.add(nla);
    }
}

private static int parcoursProfondeur(int i, Noeud_LA nc, NoeudsFils nf, Queue<Noeud_LA> qn) {
    int k1, k2, nProfSuivant;
    boolean raccourci = false;
    int[][] gIncParent = new int[gobanInc.length][gobanInc[0].length],
            lIncParent = new int[lInc.length][lInc[0].length];

    // calculer le goban et libertes en etat du noeud nc
    FonctionEvaluation.calculerLibertesInc(nc.vo, nc.va);
    copierLibertesEtGoban(gIncParent, gobanInc, lIncParent, lInc);

    for( k1 = 0; k1 < gobanInc.length; k1++) {
        for( k2 = 0; k2 < gobanInc[0].length; k2++) {
            if(gIncParent[k1][k2] == 0 && FonctionEvaluation.permissibleInc(i%2, k1, k2, gIncParent, lIncParent)) {

                Noeud_LA fils = new Noeud_LA(i%2, k1, k2, nc.vo, nc.va);
                fils.valeur = FonctionEvaluation.evaluationInc(fils.vo, fils.va, i);  // evaluation du noeud
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
    copyNoeudsFils(nf, nc, qn, i);

    return nProfSuivant;
}

private static void changerValeurRacine(Noeud_LA n) {
    n.pere.valeur = n.valeur;
    cSuivant[0] = n.vo[0];
    cSuivant[1] = n.vo[1];
}

private static boolean remonterRacine(Noeud_LA n, int i) {
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

public static int getExp() {
        return exp;
}

public static void setExp(int exp) {

    if(exp < 2 || exp>40) {
        if(exp < 2) {
                System.out.println("> L'exponentielle souhaitee est trop petite");
        } else {
                System.out.println("> L'exponentielle souhaitee est trop grande");
        }
        return;
    }

    FonctionEvaluation.exp = exp;
}

public static int getProfondeur() {
    return profondeur;
}

public static void setProfondeur(int prof) {
    if(prof < 2 || prof > 10) {
        System.out.println("Valeur du profondeur mauvaise");
        return;
    }
    profondeur = prof;
}

}