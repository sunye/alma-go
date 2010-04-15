/**
 * @author Bouvet Frederic
 * @author Altuntas Murat
 */
package coeur;

/**
 * Classe permettant de creer un plateau qui contiendra les pions des deux joueurs  
 */
public class Plateau {

	/** Le goban */
	private int[][] goban;	
	/** Le nombre de pions blanc poses */
	private int nbrPionsBlanc;	
	/** Le nombre de pions blanc poses */
	private int nbrPionsNoir;
	/** objectifs de capture de pion */
	private int objectifblanc=5;
	private int objectifnoir=5;
	
	/** Pions capture **/
	private int nbrPionBlanccapture;
	private int nbrPionNoircapture;
	
	
	/** 
	 * Constructeur qui met toutes les cases à vide 
	 */
	public Plateau() {
		goban = new int[GoDonnees.DIM_GOBAN_MAX][GoDonnees.DIM_GOBAN_MAX];
		for(int i=0; i<GoDonnees.DIM_GOBAN_MAX; i++)
			for(int j=0; j<GoDonnees.DIM_GOBAN_MAX; j++)
				goban[i][j] = GoDonnees.VIDE; 
	}
	
	/** 
	 * Pour initialiser le plateau 
	 */
	public void initialiser(){
		for(int i=0; i<GoDonnees.DIM_GOBAN_MAX; i++)
			for(int j=0; j<GoDonnees.DIM_GOBAN_MAX; j++)
				goban[i][j] = GoDonnees.VIDE; 		
		
		nbrPionsBlanc = 0;
		nbrPionsNoir = 0;	
		
		nbrPionBlanccapture=0;
		nbrPionNoircapture=0;
	}
	
	
	public void setojectifnoir(int a){
		objectifnoir=a;
	}
	
	public void setobjectifblanc(int a){
		objectifblanc=a;
	}
	
	/**
	 * Permet d'ajouter un certain nombre de pion a l'un des joueurs
	 * @param couleur La couleur du joueur
	 * @param nombre Le nombre de pion que l'on veut ajouter
	 */
	private void ajouteNbPions(int couleur,int nombre){
		if (couleur == GoDonnees.BLANC){
			 nbrPionsBlanc += nombre;
		}else { nbrPionsNoir += nombre; }
		
	}
	
	/** 
	 * Retire un nombre de pion a un joueur
	 * @param couleur la couleur du joueur
	 * @param nombre le nombre de pions a retirer
	 *  **/
	private void retireNbPions(int couleur, int nombre){
		if (couleur == GoDonnees.BLANC){
			 nbrPionsBlanc -= nombre;
			 nbrPionBlanccapture+=nombre;
		}else { 
			nbrPionsNoir -= nombre; 
			nbrPionNoircapture+=nombre;
		}		
	}
	
	/** 
	 * Connaitre le nombres de pions joue
	 * @return le nombre
	 */ 
	public int getNbrPions(){
		return nbrPionsBlanc + nbrPionsNoir;
	}
	
	/**
	 * Savoir si le plateau est plein
	 * @return si le plateau est rempli
	 */
	public boolean estRempli(){
		return (nbrPionsBlanc + nbrPionsNoir == GoDonnees.DIM_GOBAN_MAX*GoDonnees.DIM_GOBAN_MAX);
	}
	
	/** 
	 * Connaitre la couleur d'une case
	 * @param i la ligne
	 * @param j la colonne
	 * @return la couleur
	 * @throws ArrayIndexOutOfBoundsException hors du tableau
	 */
	public int getCouleur(int i, int j) throws ArrayIndexOutOfBoundsException{
		return goban[i][j];
	}
	
	/** 
	 * Connaitre le nombre de pions d'une certaine couleur
	 * @param couleur la couleur voulue
	 * @return leur nombre
	 */
	public int getNbrPions(int couleur){
		if (couleur == GoDonnees.BLANC){
			return nbrPionsBlanc;
		}else { 
			return nbrPionsNoir;
		}		
	}
	
	/** Renvoie le nombre de pion capture de la couleur
	 * @param couleur
	 * @return le nombre de pions capture
	 *  **/
	public int getnbrPioncapture(int couleur){
		if (couleur == GoDonnees.BLANC){
			return nbrPionBlanccapture;
		}else { 
			return nbrPionNoircapture;
		}	
	}
	
	/** Renvoie l'objectif du joueur
	 * @param couleur la couleur du joueur
	 * @return l'objectif du joueur
	 *  **/
	public int getObjectif(int couleur){
		if (couleur == GoDonnees.BLANC){
			return objectifblanc;
		}else { 
			return objectifnoir;
		}		
	}
	
	/** 
	 * Copie le plateau courant
	 * @return la copie
	 */
	public Plateau copie(){
		Plateau copie = new Plateau();
		copie.nbrPionsBlanc = nbrPionsBlanc;
		copie.nbrPionsNoir = nbrPionsNoir;
		for(int i=0; i<GoDonnees.DIM_GOBAN_MAX; i++)
			for(int j=0; j<GoDonnees.DIM_GOBAN_MAX; j++)
				copie.goban[i][j] = goban[i][j];
		return copie;
	}
	
	/**
	 * Pour savoir s'il est possible de poser un pion
	 * @param i la ligne
	 * @param j la colonne
	 * @param couleur la couleur
	 * @return si c'est possible
	 */
	public boolean coupPossible(int i, int j, int couleur){		
		boolean valide = false;
		int nb=nombre_liberte(i, j,couleur, null);
		if (goban[i][j]==GoDonnees.VIDE){			
			if ((nb!=0) || ((nb==0) && (capturable(i,j, couleur)))){
				valide = true;	
			}			
		}
		
		return valide;
	}
	
	/** Renvoie vrai si on fait une capture si le joueur joue sur la case ij
	 * @param i la ligne
	 * @param j la colone
	 * @param couleur la couleur du joueur
	 * **/
	private boolean capturable(int i, int j, int couleur) {
	
		int couleur_adverse;
		if (couleur==GoDonnees.BLANC){
			couleur_adverse=GoDonnees.NOIR;
		} else {
			couleur_adverse=GoDonnees.BLANC;
		}
				
		return (((i<(GoDonnees.DIM_GOBAN_MAX-1)) && (goban[i+1][j]==couleur_adverse) && ((nombre_liberte(i+1, j, null))==1)) ||
				((j<(GoDonnees.DIM_GOBAN_MAX-1)) && (goban[i][j+1]==couleur_adverse) && ((nombre_liberte(i, j+1, null))==1)) ||
				((i>0) && (goban[i-1][j]==couleur_adverse) && ((nombre_liberte(i-1, j, null))==1)) ||
				((j>0) && (goban[i][j-1]==couleur_adverse) && ((nombre_liberte(i, j-1, null))==1)));	
	}

	
	/**
	 * Pour savoir si un joueur donne peut encore jouer, ici on ne s'interesse pas a l'ensemble des coups possibles.
	 * @param couleur La couleur du joueur.
	 * @return true si un coup a ete trouve.
	 */
	public boolean existeCoupPossible(int couleur){
		boolean trouve = false;
				
		int i=0;
		int j;
		while(!trouve && (i!=GoDonnees.DIM_GOBAN_MAX )){
			j=0;
			while(!trouve && (j!=GoDonnees.DIM_GOBAN_MAX)){			
			if(coupPossible(i,j,couleur)){trouve = true;};
				j++;
			}
			i++;
		}
		
		if (couleur==GoDonnees.BLANC){			
			trouve= !(nbrPionNoircapture>=objectifblanc);
		} else{
			trouve= !(nbrPionBlanccapture>=objectifnoir);
		}
		
		return trouve;
	}
	
	/**
	 * Compte le nombre de coup possible pour un joueur donne
	 * @param couleur du joueur
	 * @return le nombre de coup
	 */
	public int nbrCoupPossible(int couleur){
		int nbr = 0;
		for(int i=0; i<GoDonnees.DIM_GOBAN_MAX; i++)
			for(int j=0; j<GoDonnees.DIM_GOBAN_MAX; j++)
				if(coupPossible(i,j,couleur))
						nbr++;
		return nbr;
	}
	
	/**
	 * Methode permettant de poser un pion sur le plateau
	 * @param i la ligne
	 * @param j la colonne 
	 * @param couleur la couleur du pion	 
	 */
	public void poserPion(int i, int j, int couleur){
		   	goban[i][j] = couleur;
			ajouteNbPions(couleur,1);
			capture(i,j);
	}
	
	/** Renvoie le nombre de liberte associe a une case vide et au groupe auquel elle appartiendra si le joueur y joue
	 * @param i la ligne
	 * @param j la colone
	 * @param couleur la couleur du joueur
	 * @param case_visite matrice de booleen indiquand si la case a deja ete visite (mettre a null au 1er appel) 
	 * @return le nombre de liberte
	 *  **/
	public int nombre_liberte(int i, int j,int couleur, boolean[][] case_visite){
						
		if (case_visite==null){				
			case_visite= new boolean[GoDonnees.DIM_GOBAN_MAX][GoDonnees.DIM_GOBAN_MAX];
			for (int k=0;k<GoDonnees.DIM_GOBAN_MAX;k++){
				for (int l=0;l<GoDonnees.DIM_GOBAN_MAX;l++){
					case_visite[k][l]=false;
				}
			}
		}
		case_visite[i][j]=true;
		int liberte=0;
				
		if (i<GoDonnees.DIM_GOBAN_MAX-1){
			if (!case_visite[i+1][j]){	
				case_visite[i+1][j]=true;
				if (goban[i+1][j]==GoDonnees.VIDE){
					liberte++;
				} else if (goban[i+1][j]==couleur){
					liberte+=nombre_liberte(i+1,j,couleur,case_visite);
				}
			}
		}
		
		if (j<GoDonnees.DIM_GOBAN_MAX-1){
			if (!case_visite[i][j+1]){	
				case_visite[i][j+1]=true;
				if (goban[i][j+1]==GoDonnees.VIDE){
					liberte++;
				} else if (goban[i][j+1]==couleur){					
					liberte+=nombre_liberte(i,j+1,couleur,case_visite);
				}
			}
		}
		
		if (i>0){
			if (!case_visite[i-1][j]){		
				case_visite[i-1][j]=true;
				if (goban[i-1][j]==GoDonnees.VIDE){
					liberte++;
				} else if (goban[i-1][j]==couleur){					
					liberte+=nombre_liberte(i-1,j,couleur,case_visite);
				}
			}
		}
		
		if (j>0){
			if (!case_visite[i][j-1]){		
				case_visite[i][j-1]=true;
				if (goban[i][j-1]==GoDonnees.VIDE){
					liberte++;
				} else if (goban[i][j-1]==couleur){					
					liberte+=nombre_liberte(i,j-1,couleur,case_visite);
				}
			}
		}
		
		return liberte;
		
	}
	
	/** Calcule le nombre de liberte d'une case non vide et de son bloc associe
	 * @param i la ligne
	 * @param j la colone
	 * @param case_visite matrice de booleen indiquand si la case a deja ete visite (mettre a null au 1er appel) 
	 * @return le nombre de liberte
	 *  **/
	public int nombre_liberte(int i, int j, boolean[][] case_visite){
		int couleur=goban[i][j];
		return nombre_liberte(i,j,couleur,case_visite);
	}
	
	
	/** Calcule le nombre de liberte minimum des blocs d'un joueur
	 * @param couleur la couleur du joueur
	 * @return le nombre de liberte
	 *  **/
	public int nombre_liberte_min(int couleur){
					
		boolean[][] case_visite= new boolean[GoDonnees.DIM_GOBAN_MAX][GoDonnees.DIM_GOBAN_MAX];
		for (int k=0;k<GoDonnees.DIM_GOBAN_MAX;k++){
			for (int l=0;l<GoDonnees.DIM_GOBAN_MAX;l++){
				case_visite[k][l]=false;
			}
		}
				
		int liberte=9999;
				
		for (int i=0;i<GoDonnees.DIM_GOBAN_MAX;i++){
			for (int j=0;j<GoDonnees.DIM_GOBAN_MAX;j++){
				if ((goban[i][j]==couleur)&& (!case_visite[i][j])){
					int a=nombre_liberte(i,j,couleur,case_visite);
					if (a<liberte){
						liberte=a;
					}
				}
			}
		}
		
		//System.out.println("liberte joueur: "+liberte);
		return liberte;
	}
	
	/** calcule le nombre de liberte total d'un joueur 
	 * @param couleur la couleur du joueur
	 * @return le nombre de liberte
	 * **/
	public int nombre_liberte_total(int couleur){
		boolean[][] case_visite= new boolean[GoDonnees.DIM_GOBAN_MAX][GoDonnees.DIM_GOBAN_MAX];
		for (int k=0;k<GoDonnees.DIM_GOBAN_MAX;k++){
			for (int l=0;l<GoDonnees.DIM_GOBAN_MAX;l++){
				case_visite[k][l]=false;
			}
		}
				
		int liberte=0;
				
		for (int i=0;i<GoDonnees.DIM_GOBAN_MAX;i++){
			for (int j=0;j<GoDonnees.DIM_GOBAN_MAX;j++){
				if ((goban[i][j]==couleur)&& (!case_visite[i][j])){
					liberte+=nombre_liberte(i,j,couleur,case_visite);					
				}
			}
		}
	
		return liberte;
	}
	
	/** Suprime le bloc associe a la case ij
	 * @param i la ligne
	 * @param j la colone
	 * @param case_visite
	 * @return le nombre de pions supprime
	 * **/
	private int supprime_bloc(int i, int j, boolean[][] case_visite){
		if(goban[i][j]==GoDonnees.VIDE){
			return 0;
		} 
		
		if (case_visite==null){				
			case_visite= new boolean[GoDonnees.DIM_GOBAN_MAX][GoDonnees.DIM_GOBAN_MAX];
			for (int k=0;k<GoDonnees.DIM_GOBAN_MAX;k++){
				for (int l=0;l<GoDonnees.DIM_GOBAN_MAX;l++){
					case_visite[k][l]=false;
				}
			}
		}
		case_visite[i][j]=true;
		int couleur=goban[i][j];
		
		
		goban[i][j]=GoDonnees.VIDE;
		int sup=1;
		
		if (i<GoDonnees.DIM_GOBAN_MAX-1){
			if (!case_visite[i+1][j]){	
				case_visite[i+1][j]=true;
				if (goban[i+1][j]==couleur){
					sup+=supprime_bloc(i+1, j, case_visite);					
				}
			}
		}
		
		if (j<GoDonnees.DIM_GOBAN_MAX-1){
			if (!case_visite[i][j+1]){	
				case_visite[i][j+1]=true;
				if (goban[i][j+1]==couleur){					
					sup+=supprime_bloc(i, j+1, case_visite);
				}
			}
		}
		
		if (i>0){
			if (!case_visite[i-1][j]){		
				case_visite[i-1][j]=true;
				if (goban[i-1][j]==couleur){					
					sup+=supprime_bloc(i-1, j, case_visite);
				}
			}
		}
		
		if (j>0){
			if (!case_visite[i][j-1]){		
				case_visite[i][j-1]=true;
				if (goban[i][j-1]==couleur){					
					sup+=supprime_bloc(i, j-1, case_visite);
				}
			}
		}

		return sup;	
		
	}
	
	/** Permet de capturer des pions adverse apres avoir jouer sur la case ij
	 * @param i ligne
	 * @param j colone
	 *  **/
	private void capture(int i, int j){
		
		int couleur=goban[i][j];
		int couleur_adverse;
		if (couleur==GoDonnees.BLANC){
			couleur_adverse=GoDonnees.NOIR;
		} else {
			couleur_adverse=GoDonnees.BLANC;
		}				
		
		if ((i<GoDonnees.DIM_GOBAN_MAX-1) && (goban[i+1][j]==couleur_adverse) && ((nombre_liberte(i+1, j, null))==0)){			
			retireNbPions(couleur_adverse, supprime_bloc(i+1, j, null));
			
		}
		if ((j<GoDonnees.DIM_GOBAN_MAX-1) && (goban[i][j+1]==couleur_adverse) && ((nombre_liberte(i, j+1, null))==0)){			
			retireNbPions(couleur_adverse, supprime_bloc(i, j+1, null));
			
		}
		if ((i>0) && (goban[i-1][j]==couleur_adverse) && ((nombre_liberte(i-1, j, null))==0)){			
			retireNbPions(couleur_adverse, supprime_bloc(i-1, j, null));
			
		}
		if ((j>0) && (goban[i][j-1]==couleur_adverse) && ((nombre_liberte(i, j-1, null))==0)){			
			retireNbPions(couleur_adverse, supprime_bloc(i, j-1, null));
			
		}		
		
		
	}
	
	/** renvoie vrai si la position ij est une case vide entoure de pion de couleur couleur
	 * @param couleur
	 * @param i 
	 * @param j
	 * @param go un goban
	 * @return vrai si la position ij est une case vide entoure de pion de couleur couleur
	 * **/
	private static boolean entoure(int couleur, int i, int j, int[][] go){
		boolean b=(go[i][j]==GoDonnees.VIDE);
		
		if (i<GoDonnees.DIM_GOBAN_MAX-1) {
			b= b && (go[i+1][j]==couleur);
		}
		if ((i<GoDonnees.DIM_GOBAN_MAX-1)&& (j<GoDonnees.DIM_GOBAN_MAX-1)) {
			b= b && (go[i+1][j+1]==couleur);
		}
		if (j<GoDonnees.DIM_GOBAN_MAX-1) {
			b= b && (go[i][j+1]==couleur);
				}
		if ((i>0)&&(j<GoDonnees.DIM_GOBAN_MAX-1)) {
			b= b && (go[i-1][j+1]==couleur);
		}
		if (i>0) {
			b= b && (go[i-1][j]==couleur);
		}
		if ((i>0)&&(j>0)) {
			b= b && (go[i-1][j-1]==couleur);
		}
		if (j>0) {
			b= b && (go[i][j-1]==couleur);
		}
		if ((i<GoDonnees.DIM_GOBAN_MAX-1)&& (j>0)){
			b= b && (go[i+1][j-1]==couleur);
		}
		
		return b;
	}
	
	public static boolean case_valide(int i,int j){
		return ((i>=0)&&(i<GoDonnees.DIM_GOBAN_MAX) && (j>=0)&&(j<GoDonnees.DIM_GOBAN_MAX));
	}
	
	/** renvoie vrai si le groupe qui contient le pion placé en ij possede 1 oeil
	 * @param i la ligne
	 * @param j la colone
	 * @param case_visite les cases visite
	 * @param go goban
	 * @return un tableau de 3 entier, le premier indique 0 ou 1 pour savoir si il y a un oeil, les 2 second sont la position de l'oeil s'il existe
	 *   **/
	private static int[] a_un_oeil(int i, int j, boolean[][] case_visite,  int[][] go){
		int[] resultat=new int[3];
		
		if ((!case_valide(i,j))||(go[i][j]==GoDonnees.VIDE)){
			resultat[0]=0;
			return resultat;
		} 
		
		if (case_visite==null){
			case_visite= new boolean[GoDonnees.DIM_GOBAN_MAX][GoDonnees.DIM_GOBAN_MAX];
			for (int k=0;k<GoDonnees.DIM_GOBAN_MAX;k++){
				for (int l=0;l<GoDonnees.DIM_GOBAN_MAX;l++){
					case_visite[k][l]=false;
				}
			}			
		}		
		
		int couleur=go[i][j];
		
		if (i<GoDonnees.DIM_GOBAN_MAX-1){				
			if (!case_visite[i+1][j]){	
				if (entoure(couleur,i+1,j, go)){
					resultat[0]=1;
					resultat[1]=i+1;
					resultat[2]=j;
					return resultat;
				} else {
					case_visite[i+1][j]=true;
					int[] r=a_un_oeil(i+1,j,case_visite, go);
					if (r[0]==1){
						return r;
					}
				}
			}
		}
		if (j<GoDonnees.DIM_GOBAN_MAX-1){	
			if (!case_visite[i][j+1]){	
				if (entoure(couleur,i,j+1, go)){
					resultat[0]=1;
					resultat[1]=i;
					resultat[2]=j+1;
					return resultat;
				} else {
					case_visite[i][j+1]=true;
					int[] r=a_un_oeil(i,j+1,case_visite, go);
					if (r[0]==1){
						return r;
					}
				}
			}
		}
		if (i>0){
			if (!case_visite[i-1][j]){	
				if (entoure(couleur,i-1,j, go)){
					resultat[0]=1;
					resultat[1]=i-1;
					resultat[2]=j;
					return resultat;
				} else {
					case_visite[i-1][j]=true;
					int[] r=a_un_oeil(i-1,j,case_visite, go);
					if (r[0]==1){
						return r;
					}
				}
			}
		}
		if (j>0){
			if (!case_visite[i][j-1]){	
				if (entoure(couleur,i,j-1, go)){
					resultat[0]=1;
					resultat[1]=i;
					resultat[2]=j-1;
					return resultat;
				} else {
					case_visite[i][j-1]=true;
					int[] r=a_un_oeil(i,j-1,case_visite, go);
					if (r[0]==1){
						return r;
					}
				}
			}	
		}
		
		return resultat;
			
	}
	
	/** Permet de savoir si le groupe qui contient la case ij a 2 yeux
	 * @param i ligne
	 * @param j colone
	 * @return vrai si le groupe a 2 yeux
	 * **/
	private boolean a_deux_yeux(int i, int j){
		int couleur=goban[i][j];
		int[] oeil=a_un_oeil(i,j,null, goban);
		if (oeil[0]==0) return false;
		int[][]copy= new int[GoDonnees.DIM_GOBAN_MAX][GoDonnees.DIM_GOBAN_MAX];
		for(int k=0; i<GoDonnees.DIM_GOBAN_MAX; i++)
			for(int l=0; j<GoDonnees.DIM_GOBAN_MAX; j++)
				copy[k][l]=goban[k][l];
		copy[oeil[1]][oeil[2]]=couleur;
		oeil=a_un_oeil(i,j,null, copy);
		if (oeil[0]==0){
			return false;
		} else{
			return true;
		}
	}
	
	/** Indique si le joueur possede un groupe qui a 2 yeux
	 * @param couleur la couleur du joueur
	 * @return vrai si le joueur possede un groupe qui a 2 yeux
	 *  **/
	public boolean a_deux_yeux(int couleur){
		for (int k=0;k<GoDonnees.DIM_GOBAN_MAX;k++){
			for (int l=0;l<GoDonnees.DIM_GOBAN_MAX;l++){
				if (goban[k][l]==couleur){
					if (a_deux_yeux(k, l)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
}

