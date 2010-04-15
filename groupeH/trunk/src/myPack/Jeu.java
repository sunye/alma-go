package myPack;

import java.util.*;

public class Jeu {
	static int fileFixe; // File FileFija;
	static int colonneFixe;
	static int valeurFixe;
	Tableau tableauFixe;
	Tableau tableau;
	Vector<Tableau> listeTableaux;
	int dimension;
	Cellule[] parties = new Cellule[82];

	Jeu() {
		initialisation();
	}

	void initialisation() {
		listeTableaux = new Vector<Tableau>();
		fileFixe = 0;
		colonneFixe = 0;
		valeurFixe = 0;
		tableauFixe = new Tableau();
		tableau = new Tableau();
		dimension = Tableau.dimension;
		for (int z = 0; z < dimension * dimension; z++)
			parties[z] = new Cellule(0, 0, 0);
		for (int x = 1; x <= dimension; x++)
			for (int y = 1; y <= dimension; y++)
				putParties(((x - 1) * 9) + y - 1, x - 1, y - 1, 0);
	}

	void putParties(int p, int f, int c, int v) {
		parties[p].putFile(f);
		parties[p].putColonne(c);
		parties[p].putValeur(v);
	}

	boolean listeVide() {
		return listeTableaux.isEmpty();
	}

	Tableau copyTableau(Tableau t) {
		Tableau tt = new Tableau();
		tt.uFile = t.uFile;
		tt.uColonne = t.uColonne;
		tt.uValeur = t.uValeur;
		for (int File = 0; File < dimension; File++)
			for (int columna = 0; columna < dimension; columna++)
				tt.casee[File][columna].putValeur(t.casee[File][columna]
						.getValeur());
		return tt;
	}

	void putTableau(Tableau t) {
		Tableau tt = new Tableau();
		tt = copyTableau(t);
		listeTableaux.addElement(tt);
	}

	Tableau getTableau() {
		Tableau t;
		t = (Tableau) listeTableaux.lastElement();
		return copyTableau(t);
	}

	void delTableau() {
		listeTableaux.remove(listeTableaux.size() - 1);
	}

	boolean incrementPosition() {
		boolean returne = true;
		while (returne) {
			if (Jeu.fileFixe < dimension - 1 && Jeu.colonneFixe < dimension - 1) {
				Jeu.colonneFixe++;
			} else if (Jeu.fileFixe == dimension - 1
					&& Jeu.colonneFixe < dimension - 1) {
				Jeu.colonneFixe++;
			} else if (Jeu.colonneFixe == dimension - 1
					&& Jeu.fileFixe < dimension - 1) {
				Jeu.colonneFixe = 0;
				Jeu.fileFixe++;
			} else {
				returne = false;
				break;
			}
			if (tableauFixe.getValeur(Jeu.fileFixe, Jeu.colonneFixe) < 1)
				break;
		}
		return returne;
	}

	boolean resolver() {
		boolean activated;
		Jeu.fileFixe = 0;
		Jeu.colonneFixe = -1;
		Jeu.valeurFixe = 0;
		activated = true;
		putTableau(tableauFixe);
		while (!tableau.isComplet() && !listeVide()) {
			tableau = getTableau();
			Jeu.valeurFixe = 0;
			Jeu.fileFixe = tableau.getUFile();
			Jeu.colonneFixe = tableau.getUColonne();
			if (activated) {
				Jeu.fileFixe = 0;
				Jeu.colonneFixe = -1;
				Jeu.valeurFixe = 0;
				activated = false;
			}
			delTableau();
			if (incrementPosition()) {
				while (Jeu.valeurFixe < dimension) {
					Jeu.valeurFixe++;
					if (tableau.caseValide(Jeu.fileFixe, Jeu.colonneFixe,
							Jeu.valeurFixe)) {
						tableau.putValeur(Jeu.fileFixe, Jeu.colonneFixe,
								Jeu.valeurFixe);
						putTableau(tableau);
					}
				}
			}
		}
		return tableau.isComplet();
	}

	void printListOfTables() {
		Tableau t;
		for (int File = 0; File < dimension; File++) {
			for (int x = 0; x < listeTableaux.size(); x++) {
				t = (Tableau) listeTableaux.elementAt(x);
				String chain = "  ";
				for (int y = 0; y < dimension; y++)
					chain = chain + t.getValeur(File, y) + " ";
				System.out.print(chain);
			}
			System.out.println(" ");
		}
	}

	public static void main(String[] args) {
		Jeu j = new Jeu();
		Tableau t = new Tableau();
		j.tableauFixe = new Tableau();
		j.tableauFixe.putValeur(0, 0, 2);
		j.tableauFixe.putValeur(1, 3, 1);
		j.tableauFixe.putValeur(2, 1, 4);
		Jeu.fileFixe = 0;
		Jeu.colonneFixe = -1;
		Jeu.valeurFixe = 0;
		boolean activated = true;
		j.putTableau(j.tableauFixe);
		while (!t.isComplet() && !j.listeVide()) {
			t = j.getTableau();
			// System.out.print("SACO");
			// System.out.println("->"+t.uFile+" "+t.uColonne+" "+t.uValor+" ");
			// j.printListaTableros();
			Jeu.valeurFixe = 0;
			Jeu.fileFixe = t.uFile;
			Jeu.colonneFixe = t.uColonne;
			if (activated == true) {
				Jeu.fileFixe = 0;
				Jeu.colonneFixe = -1;
				Jeu.valeurFixe = 0;
				activated = false;
			}
			j.delTableau();
			if (j.incrementPosition() == true) {
				while (Jeu.valeurFixe < j.dimension) {
					Jeu.valeurFixe++;
					if (t.caseValide(Jeu.fileFixe, Jeu.colonneFixe,
							Jeu.valeurFixe)) {
						t.putValeur(Jeu.fileFixe, Jeu.colonneFixe,
								Jeu.valeurFixe);
						j.putTableau(t);
						// System.out.print("METO");
						// System.out.println("->"+Juego.FileFija+" "+Juego.ColonneFija+" "+Juego.valorFijo);
						// j.printListaTableros();
					}
				}
			}
		}
		t.printTableau();
	}
}