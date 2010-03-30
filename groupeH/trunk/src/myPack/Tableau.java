package myPack;

public class Tableau{

	Cellule[][] casee=new Cellule[9][9];
	int uFile;
	int uColonne;
	int uValeur;
	static int dimension=9;

	Tableau() {
		initialisation();
	}

	void initialisation() {
		uFile=0;
		uColonne=0;
		uValeur=0;
		for(int f=0;f<9;f++)
			for(int c=0;c<9;c++) {
                          casee[f][c]=new Cellule();
                          casee[f][c].putFile(f);
                          casee[f][c].putColonne(c);
                          casee[f][c].putValeur(0);
                        }
	}



	//nb casee vides du tableau
	int numCasesVides() {
		int contador=0;
		for(int x=0;x<dimension;x++) for(int y=0;y<dimension;y++) if(casee[x][y].getValeur()<1) contador++;
		return contador;
	}

	
	
	
	/**
	 * @return the uFile
	 */
	public int getUFile() {
		return uFile;
	}

	/**
	 * @param file the uFile to set
	 */
	public void setUFile(int file) {
		uFile = file;
	}

	/**
	 * @return the uColonne
	 */
	public int getUColonne() {
		return uColonne;
	}

	/**
	 * @param colonne the uColonne to set
	 */
	public void setUColonne(int colonne) {
		uColonne = colonne;
	}

	/**
	 * @return the uValeur
	 */
	public int getUValeur() {
		return uValeur;
	}

	/**
	 * @param valeur the uValeur to set
	 */
	public void setUValeur(int valeur) {
		uValeur = valeur;
	}

	void putValeur(int fila,int columna,int valor) {
		casee[fila][columna].putValeur(valor);
		uFile=fila;
		uColonne=columna;
		uValeur=valor;
	}

	int getValeur(int fila,int columna) {
		return casee[fila][columna].valeur;
	}

	boolean existeValeurFile(int fila,int columna,int valor) {
		boolean retourne=false;
		for(int x=0;x<dimension;x++)
			if(casee[fila][x].valeur==valor && x!=columna) {
				retourne=true;
				break;
			}
		return retourne;
	}

	boolean existeValeurColonne(int fila,int columna,int valor) {
		boolean retourne=false;
		for(int x=0;x<dimension;x++)
			if(casee[x][columna].valeur==valor && x!=fila) {
				retourne=true;
				break;
			}
		return retourne;
	}

	//verifie q ia deja valeur ds 1 secteur
	boolean existeValorSector(int fila,int columna,int valor) {
		boolean retorno=false;
		if(dimension==9) {
			int sector=getSector(fila,columna);
			int f1,c1;
			f1=(((sector-1)/3)*3);
			c1=(((sector-1)%3)*3);
			for(int x=f1;x<f1+3;x++)
				for(int y=c1;y<c1+3;y++)
					if(casee[x][y].valeur==valor && x!=fila && y!=columna) {
						retorno=true;
						break;
					}
			}
		return retorno;
	}

	
	int getSector(int fila,int columna) {
		return (columna/3)+((fila/3)*3)+1;
	}

	//verifie si tableau est valide
	boolean isValide(Tableau t) {
		boolean retourne=true;
		for(int fila=0;fila<dimension;fila++)
			for(int columna=0;columna<dimension;columna++) {
				if(existeValeurFile(fila,columna,t.casee[fila][columna].valeur) ||
					existeValeurColonne(fila,columna,t.casee[fila][columna].valeur) ||
					existeValorSector(fila,columna,t.casee[fila][columna].valeur)) {
						retourne=false;
						break;
				}
			}
		return retourne;
	}

	boolean caseValide(int fila,int columna,int valor) {
		return !existeValorSector(fila,columna,valor) && !existeValeurFile(fila,columna,valor) && !existeValeurColonne(fila,columna,valor);
	}

	boolean isComplet() {
		boolean retorno=true;
		for(int fila=0;fila<dimension;fila++)
			for(int columna=0;columna<dimension;columna++)
				if(casee[fila][columna].valeur<1) {
					retorno=false;
					break;
				}
		return retorno;
	}

	void printTableau() {
		for(int f=0;f<dimension;f++) {
			for(int c=0;c<dimension;c++) {
				System.out.print(" "+getValeur(f,c));
			}
			System.out.println(" ");
		}
	}


}