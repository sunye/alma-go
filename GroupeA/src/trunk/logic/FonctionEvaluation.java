package trunk.logic;

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
	private static int[][] gobanInc;    // utilisable pour evaluer les feuilles
	static int[][] l;					// une matrice qui met a jour les libertes des pierres, apres chaque coup - sauver des calculs -> -1, si il n'y a pas de pierres
	static int[][] lInc;				// matrice des libertes de pierres, mais qui servira a l'evaluation incrementale de feuilles 		
	static boolean[][] de;				// matrice des pierres deja evaluees
	private static int points;			// on memorise le nombre de points afin de profiter de la evaluation incrementale
	private static int exp;				// l'exponentielle maximale ou l'arite maximal de l'arbre
	private static int pO;				// le nombre de pierres de l'ordinateur situees sur le goban
	private static int pA;				// le nombre de pierres de l'adversair situees sur le goban
	private static int profondeur;		// le profondeur de l'arbre, calculable a partir de l'exponentielle
	private static Vector<Point> groupe;// groupe des noeuds chaines afin de reduire les calculs des libertes
	private static int[] cSuivant;		// coup suivant, forme de deux entiers
	
	// bloc d'initialisation statique, execute pendant le chargement de la classe
	static {
		
		int i,j;

		goban = new int[9][9];
		gobanInc = new int[goban.length][goban[0].length];
		cSuivant = new int[2];
		
		points = pO = pA =0;
		
		// l'exponentielle souhaitee
		exp = 10;		// par défaut c'est 10
		
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
	
	// fonction recursive qui calcule le nombre de libertes d'une pierre de type défini
	// attention: avant l'appel de cette methode, il faut pas oublier reinitialiserDE
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

		// si la casse au nord existe et on ne l'a pas teste 
		if(x>0 && goban[x-1][y]==type && !de[x-1][y]) {
			groupe.add(new Point(x-1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
			lr += libertes(x-1, y, type);
		}
			
		
		// si la casse a l'ouest existe et on ne l'a pas teste
		if(y>0 && goban[x][y-1]==type && !de[x][y-1]) {
			groupe.add(new Point(x,y-1));		// on ajoute au groupe; cette pierre aura la meme liberte
			lr += libertes(x, y-1, type);
		}
			
		
		// si la casse au sud existe et on ne l'a pas teste
		if(x<goban.length-1 && goban[x+1][y]==type && !de[x+1][y]) {
			groupe.add(new Point(x+1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
			lr += libertes(x+1, y, type);
		}
			
		
		// si la casse a l'est existe et on ne l'a pas teste
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

		// si la case au nord existe et on ne l'a pas teste 
		if(x>0 && gobanInc[x-1][y]==type && !de[x-1][y]) {
			groupe.add(new Point(x-1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
			lr += libertesInc(x-1, y, type);
		}
			
		
		// si la case a l'ouest existe et on ne l'a pas teste
		if(y>0 && gobanInc[x][y-1]==type && !de[x][y-1]) {
			groupe.add(new Point(x,y-1));		// on ajoute au groupe; cette pierre aura la meme liberte
			lr += libertesInc(x, y-1, type);
		}
			
		
		// si la case au sud existe et on ne l'a pas teste
		if(x<gobanInc.length-1 && gobanInc[x+1][y]==type && !de[x+1][y]) {
			groupe.add(new Point(x+1,y));		// on ajoute au groupe; cette pierre aura la meme liberte
			lr += libertesInc(x+1, y, type);
		}
			
		
		// si la case a l'est existe et on ne l'a pas teste
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
					aux = l[i][j] = libertes(i, j, goban[i][j]);
					
					while(! groupe.isEmpty()) {
						
						Point p = groupe.remove(0);
						l[p.x][p.y] = aux;
					}					
				}		
	}
	
	// evaluation de la racine de l'arbre, non-incrementale
	static void evaluation() {
	
		int i, j, pointsadv, aux;
		
		points = 0; 		// on reinitialise, parce qu'on va evaluer toute le grille, pas incrementale
		calculerLibertes(); // calculer les libertes de toutes les pierres de facon efficiente
	
		// on compte dans le double for les libertes de l'ordinateur et du l'adversaire	
		for(i=0; i<goban.length; i++)
			for(j=0; j<goban.length; j++) 
				if(goban[i][j]!=0) {
					if(goban[i][j]==1) { // ordinateur				
																		
						if( (aux = l[i][j]) > 2) {
							points += aux;
						}
						else if(aux == 2) {
							points -= 30;
						}
						else {  // ca veut dire une seule liberte pour une pierre, tres mal 
							points -= 100;
						}
						
					}
					else {  // adversair
						pointsadv = 0;
												
						if( (aux = l[i][j]) > 2) {
							pointsadv -= aux;
						}
						else if(aux==2) {
							pointsadv += 5;
						}
						else { // ca veut dire une seule liberte pour une pierre, tres mal 
							pointsadv += 20;
						}			
						points += (int)(pointsadv*2/3);
					}

				}
	}
	
	// evaluation incrementale, utilisee pour les feuilles de l'arbre
	static int evaluationInc(int[] vo, int[] va) {
		
		int i, pointsadv, aux, pointsV = 0;		
		calculerLibertesInc(vo, va);
		
		i = 0;
		while(i < vo.length) {		// les coups de l'ordinateur dans l'arbre alpha-beta
			if( (aux = lInc[vo[i]][vo[i+1]]) > 2) {
				pointsV += aux;
			}
			else if(aux == 2) {
				pointsV -= 30;
			}
			else {  // ca veut dire une seule liberte pour une pierre, tres mal 
				pointsV -= 100;
			}	
			
			i += 2;		// deux valeurs x et y deja utilises
		} // fin while
			
		i = 0;
		while(i < va.length) {		// les coups de l'adversair dans l'arbre alpha-beta
			pointsadv = 0;
			
			if( (aux = lInc[va[i]][va[i+1]]) > 2) {
				pointsadv -= aux;
			}
			else if(aux == 2) {
				pointsadv += 5;
			}
			else { // ca veut dire une seule liberte pour une pierre, tres mal 
				pointsadv += 20;
			}			
			pointsV += (int)(pointsadv*2/3);	
			
			i+=2;
		}
		
		return points + pointsV;
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
		
		// reinitialiser la matrice des visites
		reinitialiserDE();
		groupe = new Vector<Point>();		// et la groupe qui sera toujours utilise		
		
		actualiserLibertes(vo);
		actualiserLibertes(va);
	}

	private static void actualiserLibertes(int[] vo) {
		// il nous reste simplement appeler libertesInc pour les coups concernes
		int i = 0, v1, v2, aux;
		
		if(vo!= null)
			while( i < vo.length ) {
				v1 = vo[i]; v2 = vo[i+1];
				if(!de[v1][v2]) {					
					aux = lInc[v1][v2] = libertesInc(v1, v2, 1);
					while(! groupe.isEmpty()) {						
						Point p = groupe.remove(0);
						lInc[p.x][p.y] = aux;
					}
				}
				i += 2;
			}	
	}

	private static void copierLibertesEtGoban() {
		int i, j;
		
		// on profite du fait qu'on a deja calcule les libertes pour le goban 
		// avant les coups decrits par les vecteurs vo et va		
		for(i = 0; i < l.length; i++)
			for(j = 0; j < l[0].length; j++)
				lInc[i][j] = l[i][j];
		
		// de la meme facon on initialise gobanInc a partir de goban
		for(i = 0; i < goban.length; i++)
			for(j = 0; j < goban[0].length; j++)
				gobanInc[i][j] = goban[i][j];
		
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
			
		int i = 2, j, nProf, k1, k2;
		int nProfSuivant;					// tc est la taille de carre a explorer	
		boolean raccourci;
		
		Queue<Noeud_LA> qn = new ArrayDeque<Noeud_LA>(); // la file des noeuds a traiter sur un certain profondeur
		
		// d'avantage, on evalue la situation initiale, qui nous aidera a l'evaluation incrementale dans les feuilles
		FonctionEvaluation.evaluation();
		
		// arbre alpha_beta avec arite exp et valeur 0, puisqu'on n'a pas remonter les valeurs depuis les feuilles
		Arbre_LA ab = new Arbre_LA(FonctionEvaluation.exp, 0);
		
		// on recupere la racine
		Noeud_LA racine = (Noeud_LA)(ab.c);
		
		qn.add(racine);
		nProf = 1;							// 1 seul noeud sur profondeur 1 - la racine
		
		// instanciation des comparateurs ascendent et descendent
		Comparator<Noeud_LA> cAsc = new ComparatorAscendent();
		Comparator<Noeud_LA> cDesc = new ComparatorDescendent();
		
		// calcul de la profondeur
		profondeur = 8 - (int)(Math.ceil(Math.sqrt(FonctionEvaluation.exp+1)));		
		System.out.println("Profondeur actuel " + profondeur);
		
		// boucle principale de l'algorithme alpha-beta
		for( ; i <= profondeur; i++) {
			
			System.out.println("arrive au profondeur " + i + " ; nombre des noeuds parent a develloper "+nProf);
			
			j = 0; nProfSuivant = 0;
			while(j < nProf) {		// pour chaque noeud du profondeur 		
			
				raccourci = false;
				Noeud_LA nc = ((ArrayDeque<Noeud_LA>)qn).pollFirst();  // on extrait le premier noeud				
				
				// libertes et goban mises a jours selon le nouveau coup
				FonctionEvaluation.evaluationInc(nc.vo, nc.va);				
					
				// le tree set qui retiendra les noueds a ajouter a nc pour etre ensuite devellope
				Set<Noeud_LA> ts;	// le tree set qui contiendra les noeuds selectionnes

				/* selon le profondeur, on initialise le treeSet avec un  comparateur ascendent ou descendent
				*/ 
				if(i%2 == 0) {
					ts = new TreeSet<Noeud_LA>(cAsc);	
				} else {				
					ts = new TreeSet<Noeud_LA>(cDesc);	
				}				

				/* la case doit etre libre pour etre prise en charge on doit jetter un coup d'oeil aussi dans les coups supplementairs
				* du parent, parce qu'on ne peut pas avoir deux coups sur une meme case, explication : on joue de l'atari-go et si on a eu une prise, le jeu est fini
				*/				
				for( k1 = 0; k1 < goban.length; k1++) {
					for( k2 = 0; k2 < goban[0].length; k2++) {
						FonctionEvaluation.evaluationInc(nc.vo, nc.va);	
						if(goban[k1][k2] == 0 && caseLibre(k1, k2, nc.vo, nc.va) && FonctionEvaluation.permissibleInc(i%2, k1, k2)){
							Noeud_LA fils = new Noeud_LA(i%2, k1, k2, nc.vo, nc.va);
							// evaluation du noeud							
							fils.valeur = FonctionEvaluation.evaluationInc(fils.vo, fils.va);
							
							if(ts.size() < FonctionEvaluation.exp) {								
								if(ts.add(fils)) {		//ajout dans le tree set seulement s'il n'y a pas un noeud avec la meme valeur
									System.out.println("profondeur "+i+" ;valeur parent "+nc.valeur+" ; valeur noeud ajoute "+fils.valeur+" ; coup "+k1+"  "+k2);
									nProfSuivant++;							
								}				
							} else {					// sinon, on compare la valeur de ce fils avec ceux dans tree set
								Noeud_LA premier = ts.iterator().next();
								if(i%2 == 0 && fils.valeur > premier.valeur) { // c'est un coup max
									System.out.println("profondeur "+i+" ;valeur parent "+nc.valeur+" ; valeur noeud supprime "+premier.valeur);
									ts.remove(premier);									
									if(!ts.add(fils)) {
										nProfSuivant--;
									}		
									else {
										System.out.println("profondeur "+i+" ;valeur parent "+nc.valeur+" ; valeur noeud ajoute "+fils.valeur+" ; coup "+k1+"  "+k2+"\n");
									}
								} else if(i%2==1 && fils.valeur < premier.valeur){ // c'est un coup min, de l'adversaire
									System.out.println("profondeur "+i+" ;valeur parent "+nc.valeur+" ; valeur noeud supprime "+premier.valeur);
									ts.remove(premier);
									if(!ts.add(fils)) {
										nProfSuivant--;
									}
									else {
										System.out.println("profondeur "+i+" ;valeur parent "+nc.valeur+" ; valeur noeud ajoute "+fils.valeur+" ; coup "+k1+"  "+k2+"\n");
									}
								}								
							} // comparation de valeur						
								
							// si on a atteint le profondeur maximal, on remonte vers la racine
							if(i == profondeur) {
								nc.ajouterFils(fils);	// on doit ajouter le fils avant 
								raccourci = remonterRacine(fils, i%2);
								
								if(raccourci) {
									break;
								} else {
									fils.supprimer();	// supprimer le fils de nc
								}
							}
													
							if ( k1 == goban.length-1 && k2 == goban[0].length-1) {
								copyTreeSet(ts, nc, qn);
							} // end if k1 et k2 dernieres valeurs							
						} // fin if case du goban libre et permissible						
					} // fin for k2
					
					if(raccourci) {
						break;	// on a fini avec 
					}					
				} // fin for k1
				
				j++;
			} // fin while j < nProf
			
			nProf = nProfSuivant; // pour le profondeur suivant
		} // fin for boucle profondeur

		return cSuivant;
	}

	/* maintenant, a partir de la racine, on decouvre quel est le fils 
	 * avec la meme valeur que celle de la racine; si il y en a plusieurs, le premier sera choisi 	
	 */
	private static int[] trouveFilsDeRacine(Noeud_LA racine) {
	
		for(Iterator<Noeud_LA> it = racine.liste_fils.iterator(); it.hasNext(); ) {
			
			Noeud_LA n = it.next();
			if(n.valeur == racine.valeur) {
				
				System.out.print("nouveau coup  ");
				for(int k=0;k<n.vo.length;k++)
					System.out.print(n.vo[k]+"  ");
				
				System.out.println("");
				return n.vo;
			}
		}
				
		System.out.println("> Erreur; on ne devait pas arriver ici");
		return new int[] {1, 1};
	}

	private static void copyTreeSet(Set<Noeud_LA> ts, Noeud_LA parent, Queue<Noeud_LA> ad) {

		for(Iterator<Noeud_LA> it = ts.iterator(); it.hasNext(); ) {
			
			Noeud_LA nSelect = it.next();
			parent.ajouterFils(nSelect);
			ad.add(nSelect);
			
		}
		
	}

	private static boolean remonterRacine(Noeud_LA n, int i) {
		n.valeurPresent = true;
		
		switch(i) {
	
			case 0: // ordinateur -> coup max
					if(n.pere.pere != null) {
						
						// si on a un raccourci de alpha-beta, on retourne false
						//if(n.pere.pere.valeurPresent && n.pere.pere.valeur < n.valeur) {
							//return true;
						//}
						
						if(n.pere.valeurPresent) {
							if(n.pere.valeur < n.valeur) { // c'est un coup max	
								
								System.out.println("coup max valeur parent remplace "+n.pere.valeur+" ; valeur de fils "+n.valeur);
								n.pere.valeur = n.valeur;
								
								// seulement dans ce cas on remonte toujours vers la racine
								return remonterRacine(n.pere, 1);	// le pere sera un coup min
							}
						}
						else {	
							
							System.out.println("coup max valeur parent vide "+n.pere.valeur+" ; valeur de fils "+n.valeur);
							n.pere.valeur = n.valeur;
							n.pere.valeurPresent = true;
							
							return remonterRacine(n.pere, 1);	// le pere sera un coup min
						}
												
					}
					else { // le pere est la racine
						
						if(n.pere.valeurPresent) {	
							System.out.println("coup max valeur racine remplace "+n.pere.valeur+" ; valeur de fils "+n.valeur);
							if(n.pere.valeur < n.valeur) { // c'est un coup max							
								n.pere.valeur = n.valeur;
								cSuivant[0] = n.vo[0];
								cSuivant[1] = n.vo[1];
								System.out.println("--------");
								System.out.println("valeur racine "+n.pere.valeur);
								System.out.println("coup "+n.vo[0]+"  "+n.vo[1]);
								System.out.println("---------");
							}
						}
						else {
							System.out.println("coup max valeur racine vide "+n.pere.valeur+" ; valeur de fils "+n.valeur);
							
							n.pere.valeur = n.valeur;
							n.pere.valeurPresent = true;
							cSuivant[0] = n.vo[0];
							cSuivant[1] = n.vo[1];
							System.out.println("--------");
							System.out.println("valeur racine "+n.pere.valeur);
							System.out.println("coup "+n.vo[0]+"  "+n.vo[1]);
							System.out.println("---------");
						}
						

					}
				break;
				
			case 1: // adversair -> coup min
					// si on a un raccourci de alpha-beta, on retourne false
					//if(n.pere.pere.valeurPresent && n.pere.pere.valeur > n.valeur){
						//return true;
					//}
					if(n.pere.valeurPresent) {
						if(n.pere.valeur > n.valeur) { // c'est un coup min	
							System.out.println("coup min valeur parent remplace "+n.pere.valeur+" ; valeur de fils "+n.valeur);
							n.pere.valeur = n.valeur;
							
							// seulement dans ce cas on remonte toujours vers la racine
							return remonterRacine(n.pere, 0);	// le pere sera un coup max
						}
					}
					else {
						System.out.println("coup min valeur parent vide "+n.pere.valeur+" ; valeur de fils "+n.valeur);
						n.pere.valeur = n.valeur;
						n.pere.valeurPresent = true;
						
						return remonterRacine(n.pere, 0);	// le pere sera un coup max
					}					
					
				break;
				
			default:
				break;
		}
		
		return false;
	}

	private static boolean caseLibre(int i, int j, int[] vo, int[] va) {
		int k;
		
		if(vo == null && va == null)
			return true;
		
		if(vo==null && (va.length < 2 || va[0]!=i || va[1]!=j)) return true;
		if(va==null && (vo.length < 2 || vo[0]!=i || vo[1]!=j)) return true;
		
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
			
			if(exp < 2)
				System.out.println("> L'exponentielle souhaitee est trop petite");
			else
				System.out.println("> L'exponentielle souhaitee est trop grande");
			
			return;
		}
		
		FonctionEvaluation.exp = exp;
	}
	
}
