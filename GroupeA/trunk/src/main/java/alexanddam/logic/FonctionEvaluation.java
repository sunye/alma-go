package main.java.alexanddam.logic;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class FonctionEvaluation {

    public static int[][] goban;		// 0 - rien, 1 - ordinateur, 2 - adversair
    private static int[][] gobanInc;            // utilisable pour evaluer les feuilles
    static int[][] l;				// une matrice qui met a jour les libertes des pierres, apres chaque coup - sauver des calculs -> -1, si il n'y a pas de pierres
    static int[][] lInc;			// matrice des libertes de pierres, mais qui servira a l'evaluation incrementale de feuilles
    static boolean[][] de;			// matrice des pierres deja evaluees
    private static int points;			// on memorise le nombre de points afin de profiter de la evaluation incrementale
    private static int exp;			// l'exponentielle maximale ou l'arite maximal de l'arbre
    private static int pO;			// le nombre de pierres de l'ordinateur situees sur le goban
    private static int pA;			// le nombre de pierres de l'adversair situees sur le goban
    private static int profondeur;		// le profondeur de l'arbre, calculable a partir de l'exponentielle
    private static Vector<Point> groupe;        // groupe des noeuds chaines afin de reduire les calculs des libertes
    private static int[] cSuivant;		// coup suivant, forme de deux entiers
	
// bloc d'initialisation statique, execute pendant le chargement de la classe
static {

        int i,j;

        goban = new int[9][9];
        gobanInc = new int[goban.length][goban[0].length];
        cSuivant = new int[2];

        points = pO = pA =0;

        // l'exponentielle souhaitee
        exp = 10;
        
        l = new int[goban.length][goban[0].length];
        lInc = new int[l.length][l[0].length];  // initialisation matrice des libertes incrementale

        for(i=0; i<goban.length; i++)
                for(j=0; j<goban.length; j++)
                        l[i][j] = -1;

        de = new boolean[goban.length][goban[0].length]; // false
        groupe = new Vector<Point>();
}

public static void jeuNeu() {

        int i,j;

        goban = new int[9][9];
        cSuivant = new int[2];
        points = pO = pA =0;

        // l'exponentielle souhaitee
        exp = 10;		// par défaut c'est 10

        l = new int[goban.length][goban[0].length];

        for(i=0; i<goban.length; i++)
                for(j=0; j<goban.length; j++)
                        l[i][j] = -1;

        reinitialiserDE();
        groupe = new Vector<Point>();
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

static void reinitialiserVideDE() {
    int i=0,j;

    for(; i < de.length; i++)
        for(j=0; j< de[0].length; j++)
            if(de[i][j] && goban[i][j]==0) {
                de[i][j] = false;
            }
}

static void reinitialiserVideDEInc() {
    int i=0,j;

    for(; i < de.length; i++)
        for(j=0; j< de[0].length; j++)
            if(de[i][j] && gobanInc[i][j]==0) {
                de[i][j] = false;
            }
}
	
/*fonction recursive qui calcule le nombre de libertes d'une pierre de type défini
 * attention: avant l'appel de cette methode, il faut pas oublier reinitialiserDE
 */
static int libertes(int x, int y, int type) {

        int lr = 0;
        de[x][y] = true;			// cette case est en cours de visite -> on marque la visite

        if(x>0 && goban[x-1][y]==0 && !de[x-1][y]) {
                lr += 1; de[x-1][y] = true;
        }
        if(y>0 && goban[x][y-1]==0 && !de[x][y-1]) {
                lr += 1; de[x][y-1] = true;
        }
        if(x<goban.length-1 && goban[x+1][y]==0 && !de[x+1][y]) {
                lr += 1; de[x+1][y] = true;
        }
        if(y<goban.length-1 && goban[x][y+1]==0 && !de[x][y+1]) {
                lr += 1; de[x][y+1] = true;
        }

        // si la casse a l'est existe et on ne l'a pas teste
        if(x>0 && goban[x-1][y]==type && !de[x-1][y]) {
                groupe.add(new Point(x-1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
                lr += libertes(x-1, y, type);
        }

        // si la casse au nord existe et on ne l'a pas teste
        if(y>0 && goban[x][y-1]==type && !de[x][y-1]) {
                groupe.add(new Point(x,y-1));		// on ajoute au groupe; cette pierre aura la meme liberte
                lr += libertes(x, y-1, type);
        }

        // si la casse a l'ouest existe et on ne l'a pas teste
        if(x<goban.length-1 && goban[x+1][y]==type && !de[x+1][y]) {
                groupe.add(new Point(x+1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
                lr += libertes(x+1, y, type);
        }

        // si la casse au sud existe et on ne l'a pas teste
        if(y<goban.length-1 && goban[x][y+1]==type && !de[x][y+1]) {
                groupe.add(new Point(x,y+1));		// on ajoute au groupe; cette pierre aura la meme liberte
                lr += libertes(x, y+1, type);
        }

        return lr;
}

static int libertesInc(int x, int y, int type) {

        int lr = 0;
        de[x][y] = true;			// cette casse est en cours de visite -> on marque la visite

        if(x>0 && gobanInc[x-1][y]==0 && !de[x-1][y]) {
                lr += 1; de[x-1][y] = true;
        }
        if(y>0 && gobanInc[x][y-1]==0 && !de[x][y-1]) {
                lr += 1; de[x][y-1] = true;
        }
        if(x<gobanInc.length-1 && gobanInc[x+1][y]==0 && !de[x+1][y]) {
                lr += 1; de[x+1][y] = true;
        }
        if(y<gobanInc.length-1 && gobanInc[x][y+1]==0 && !de[x][y+1]) {
                lr += 1; de[x][y+1] = true;
        }

        // si la case a l'est  existe et on ne l'a pas teste
        if(x>0 && gobanInc[x-1][y]==type && !de[x-1][y]) {
                groupe.add(new Point(x-1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
                lr += libertesInc(x-1, y, type);
        }

        // si la case au nord existe et on ne l'a pas teste
        if(y>0 && gobanInc[x][y-1]==type && !de[x][y-1]) {
                groupe.add(new Point(x,y-1));		// on ajoute au groupe; cette pierre aura la meme liberte
                lr += libertesInc(x, y-1, type);
        }

        // si la case a l'ouest existe et on ne l'a pas teste
        if(x<gobanInc.length-1 && gobanInc[x+1][y]==type && !de[x+1][y]) {
                groupe.add(new Point(x+1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
                lr += libertesInc(x+1, y, type);
        }

        // si la case au sud existe et on ne l'a pas teste
        if(y<gobanInc.length-1 && gobanInc[x][y+1]==type && !de[x][y+1]) {
                groupe.add(new Point(x,y+1));		// on ajoute au groupe; cette pierre aura la meme liberte
                lr += libertesInc(x, y+1, type);
        }

        return lr;
}
	
	
// calculer toutes les libertes de pierres
static void calculerLibertes() {

        int i = 0, j = 0, aux;

        // reinitialiser la matrice des visites
        reinitialiserDE();
        groupe = new Vector<Point>();

        for( ; i < goban.length; i++)
            for(j = 0 ; j < goban.length; j++)
                if(goban[i][j]!=0 && !de[i][j]) {
                    reinitialiserVideDE();
                    aux = l[i][j] = libertes(i, j, goban[i][j]);

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
static int evaluationInc(int[] vo, int[] va) {

    int pointsV = 0, i, j;
    calculerLibertesInc(vo, va);

     for(i=0; i<gobanInc.length; i++)
        for(j=0; j<gobanInc.length; j++)
            if(gobanInc[i][j]!=0) {
                pointsV += pointsPerCasse(lInc[i][j], 2 * gobanInc[i][j] - 3);
            }

     return pointsV;
}

static int pointsPerCasse(int libertes,int sign) {
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

        copierLibertesEtGoban();

        // maintenant on realise les changements necessaires dans gobanInc
        i = 0;
        if(vo!= null)
            while(i < vo.length) {
                gobanInc[vo[i]][vo[i+1]] = 1;	// l'ordinateur
                i += 2;
            }

        i = 0;
        if(va != null)
            while(i < va.length) {
                gobanInc[va[i]][va[i+1]] = 2;	// l'adversair
                i += 2;
            }

        groupe = new Vector<Point>();		// et la groupe qui sera toujours utilise
        actualiserLibertes(vo, 1);
        actualiserLibertes(va, 2);
}

private static void actualiserLibertes(int[] v, int type) {
    // il nous reste simplement appeler libertesInc pour les coups concernes
    int i = 0, v1, v2, aux;
    reinitialiserDE();

    if(v!= null)
        while( i < v.length ) {
            v1 = v[i]; v2 = v[i+1];
            if(!de[v1][v2]) {
                reinitialiserVideDEInc();
                aux = lInc[v1][v2] = libertesInc(v1, v2, type);
                
                for(int hh=0;hh<groupe.size();hh++) {
                    Point p = groupe.elementAt(hh);
                }
                while(! groupe.isEmpty()) {
                    Point p = groupe.remove(0);                    
                    lInc[p.x][p.y] = aux;
                }
            }
            i += 2;
        }
}

/* on copie le goban et on profite du fait qu'on a deja calcule des libertes
 * dans l'evaluation de la racine des chaines qui ne seront pas influences par vo et va
 */
private static void copierLibertesEtGoban() {
        int i, j;

        for(i = 0; i < goban.length; i++)
            for(j = 0; j < goban[0].length; j++)
                gobanInc[i][j] = goban[i][j];

        for(i = 0; i < lInc.length; i++)
            for(j = 0; j < lInc[0].length; j++)
                lInc[i][j] = l[i][j];

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

    /* si les propres pierres qui l'entoure ont le nombre de libertes == 1
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
public static boolean permissibleInc(int tour, int x, int y) {		
    if((x > 0 && gobanInc[x-1][y]==0) || (x < gobanInc.length - 1 && gobanInc[x+1][y]==0) ||
       (y > 0 && gobanInc[x][y-1]==0) || (y < gobanInc.length - 1 && gobanInc[x][y+1]==0))
            return true;

    if( ( !(x > 0) || (gobanInc[x-1][y]==tour+1 && lInc[x-1][y]==1) || (gobanInc[x-1][y]==2-tour && lInc[x-1][y]>1)) &&
        ( !(x < gobanInc.length - 1) || (gobanInc[x+1][y]==tour+1 && lInc[x+1][y]==1) || (gobanInc[x+1][y]==2-tour && lInc[x+1][y]>1)) &&
        ( !(y > 0) || (gobanInc[x][y-1]==tour+1 && lInc[x][y-1]==1) || (gobanInc[x][y-1]==2-tour && lInc[x][y-1]>1)) &&
        ( !(y < gobanInc.length - 1) || (gobanInc[x][y+1]==tour+1 && lInc[x][y+1]==1) || (gobanInc[x][y+1]==2-tour && lInc[x][y+1]>1)) )
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

    profondeur = 9 - (int)(Math.ceil(Math.sqrt(FonctionEvaluation.exp+1)));
    profondeur = 2;
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
		if( i == 2) {
        	System.out.println("valeur "+nla.valeur + "  ; k1 k2 "+nla.vo[0]+", "+nla.vo[1]);
        }
		parent.ajouterFils(nla);
		ad.add(nla);
	}

}

private static int parcoursProfondeur(int i, Noeud_LA nc, NoeudsFils nf, Queue<Noeud_LA> qn) {
    int k1, k2, nProfSuivant=0;
    boolean raccourci = false;

     for( k1 = 0; k1 < goban.length; k1++) {
        for( k2 = 0; k2 < goban[0].length; k2++) {
        	
            if(goban[k1][k2] == 0 && caseLibre(k1, k2, nc.vo, nc.va) && 
                    FonctionEvaluation.permissibleInc(i%2, k1, k2)) {
                
                Noeud_LA fils = new Noeud_LA(i%2, k1, k2, nc.vo, nc.va);               
                fils.valeur = FonctionEvaluation.evaluationInc(fils.vo, fils.va);  // evaluation du noeud
                
                if(i == 2) {
                	System.out.println("coup "+k1+" , "+k2+" ; valeur "+fils.valeur);
                }

                if(nf.size() < FonctionEvaluation.exp && nf.addFils(fils)) {
                	nProfSuivant++;
                }
                
                // si on a atteint le profondeur maximale, on remonte vers la racine
                if(i == profondeur) {
                    nc.ajouterFils(fils);	// on doit ajouter le fils avant
                    raccourci = remonterRacine(fils, i%2);

                    if(raccourci) {  break; }
                    else { fils.supprimer(); }
                }

                if ( k1 == goban.length-1 && k2 == goban[0].length-1) {
                        copyNoeudsFils(nf, nc, qn, i);                                               
                } 
             } 
          } 

          if(raccourci) { break; }
        } 

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

private static boolean caseLibre(int i, int j, int[] vo, int[] va) {
    int k;

    if(vo.length==0)
        return true;

    if(va.length==0) {
        if(vo[0]!=i || vo[1]!=j) { return true; }
        else { return false; }
    }

    k=0;
    while(k < vo.length) {
        if(vo[k]==i && vo[k+1]==j) return false;
        k += 2;
    }

    k=0;
    while(k < va.length) {
        if(va[k]==i && va[k+1]==j) return false;
        k += 2;
    }

    return true;
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

}
