package myPack;

public class Tablero{

	Celda[][] casilla=new Celda[9][9];
	int uFila;
	int uColumna;
	int uValor;
	static int dimension=9;

	Tablero() {
		init();
	}

	void init() {
		uFila=0;
		uColumna=0;
		uValor=0;
		for(int f=0;f<9;f++)
			for(int c=0;c<9;c++) {
                          casilla[f][c]=new Celda();
                          casilla[f][c].putFila(f);
                          casilla[f][c].putColumna(c);
                          casilla[f][c].putValor(0);
                        }
	}

	int getUFila() {
		return uFila;
	}

	int getUColumna() {
		return uColumna;
	}

	int getUValor() {
		return uValor;
	}

	int numbZeros() {
		int contador=0;
		for(int x=0;x<dimension;x++) for(int y=0;y<dimension;y++) if(casilla[x][y].getValor()<1) contador++;
		return contador;
	}

	void putValor(int fila,int columna,int valor) {
		casilla[fila][columna].putValor(valor);
		uFila=fila;
		uColumna=columna;
		uValor=valor;
	}

	int getValor(int fila,int columna) {
		return casilla[fila][columna].valor;
	}

	boolean existeValorFila(int fila,int columna,int valor) {
		boolean retour=false;
		for(int x=0;x<dimension;x++)
			if(casilla[fila][x].valor==valor && x!=columna) {
				retour=true;
				break;
			}
		return retour;
	}

	boolean existeValorColumna(int fila,int columna,int valor) {
		boolean retour=false;
		for(int x=0;x<dimension;x++)
			if(casilla[x][columna].valor==valor && x!=fila) {
				retour=true;
				break;
			}
		return retour;
	}

	boolean existeValorSector(int fila,int columna,int valor) {
		boolean retour=false;
		if(dimension==9) {
			int sector=getSector(fila,columna);
			int f1,c1;
			f1=(((sector-1)/3)*3);
			c1=(((sector-1)%3)*3);
			for(int x=f1;x<f1+3;x++)
				for(int y=c1;y<c1+3;y++)
					if(casilla[x][y].valor==valor && x!=fila && y!=columna) {
						retour=true;
						break;
					}
			}
		return retour;
	}

	int getSector(int fila,int columna) {
		return (columna/3)+((fila/3)*3)+1;
	}

	boolean valido(Tablero t) {
		boolean retour=true;
		for(int fila=0;fila<dimension;fila++)
			for(int columna=0;columna<dimension;columna++) {
				if(existeValorFila(fila,columna,t.casilla[fila][columna].valor) ||
					existeValorColumna(fila,columna,t.casilla[fila][columna].valor) ||
					existeValorSector(fila,columna,t.casilla[fila][columna].valor)) {
						retour=false;
						break;
				}
			}
		return retour;
	}

	boolean validCase(int fila,int columna,int valor) {
		return !existeValorSector(fila,columna,valor) && !existeValorFila(fila,columna,valor) && !existeValorColumna(fila,columna,valor);
	}

	boolean completo() {
		boolean retour=true;
		for(int fila=0;fila<dimension;fila++)
			for(int columna=0;columna<dimension;columna++)
				if(casilla[fila][columna].valor<1) {
					retour=false;
					break;
				}
		return retour;
	}

	void printTablero() {
		for(int f=0;f<dimension;f++) {
			for(int c=0;c<dimension;c++) {
				System.out.print(" "+getValor(f,c));
			}
			System.out.println(" ");
		}
	}

}