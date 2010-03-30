package myPack;

public class Cellule{

	int file;
	int columna;
	int valeur;

        Cellule() {
          file=0;
          columna=0;
          valeur=0;
        }

	Cellule(int f,int c,int v) {
		file=f;
		columna=c;
		valeur=v;
	}

	void putFile(int f) {
		file=f;
	}

	void putColonne(int c) {
		columna=c;
	}

	void putValeur(int v) {
		valeur=v;
	}

	int getValeur() {
		return valeur;
	}

        int getFile() {
          return file;
        }

        int getColonne() {
          return columna;
        }
}