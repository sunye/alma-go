package myPack;

public class Celda{

	int fila;
	int columna;
	int valor;

        Celda() {
          fila=0;
          columna=0;
          valor=0;
        }

	Celda(int f,int c,int v) {
		fila=f;
		columna=c;
		valor=v;
	}

	void putFila(int f) {
		fila=f;
	}

	void putColumna(int c) {
		columna=c;
	}

	void putValor(int v) {
		valor=v;
	}

	int getValor() {
		return valor;
	}

        int getFila() {
          return fila;
        }

        int getColumna() {
          return columna;
        }
}