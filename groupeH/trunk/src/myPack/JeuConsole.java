package myPack;

import java.io.IOException;
import java.util.*;

/**
 * @author Peter MOUEZA
 */
public class JeuConsole {
	static int fileFixe; // File FileFija;
	static int colonneFixe;
	static int valeurFixe;
	static Tableau snapshot;
	static Tableau tableauFixe;
	Tableau tableau;
	Vector<Tableau> listeTableaux;
	int dimension;
	Cellule[] parties = new Cellule[82];

	public static int X, Y;// ,alafbet2IntCoordY;

	JeuConsole() throws IOException {
		initialisation();
	}

	void initialisation() throws IOException {
		System.out.println("in JeuConsole.initialisation");// ! comments on
		// relative
		// potential renamed
		// method name
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

		// Tableau snapshot = tableau.getSnap();
		tableauFixe = tableau.getSnap();

		System.out.println("fin of JeuConsole.initialisation");

	}

	void putParties(int p, int f, int c, int v) {
		parties[p].putFile(f);
		parties[p].putColumn(c);
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
			for (int column = 0; column < dimension; column++)
				tt.casee[File][column].putValeur(t.casee[File][column]
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
			if (JeuConsole.fileFixe < dimension - 1
					&& JeuConsole.colonneFixe < dimension - 1) {
				JeuConsole.colonneFixe++;
			} else if (JeuConsole.fileFixe == dimension - 1
					&& JeuConsole.colonneFixe < dimension - 1) {
				JeuConsole.colonneFixe++;
			} else if (JeuConsole.colonneFixe == dimension - 1
					&& JeuConsole.fileFixe < dimension - 1) {
				JeuConsole.colonneFixe = 0;
				JeuConsole.fileFixe++;
			} else {
				returne = false;
				break;
			}
			if (tableauFixe.getValeur(JeuConsole.fileFixe,
					JeuConsole.colonneFixe) < 1)
				break;
		}
		return returne;
	}

	boolean resolver() {
		boolean activated;
		JeuConsole.fileFixe = 0;
		JeuConsole.colonneFixe = -1;
		JeuConsole.valeurFixe = 0;
		activated = true;
		putTableau(tableauFixe);
		while (!tableau.isComplet() && !listeVide()) {
			tableau = getTableau();
			JeuConsole.valeurFixe = 0;
			JeuConsole.fileFixe = tableau.getUFile();
			JeuConsole.colonneFixe = tableau.getUColonne();
			if (activated) {
				JeuConsole.fileFixe = 0;
				JeuConsole.colonneFixe = -1;
				JeuConsole.valeurFixe = 0;
				activated = false;
			}
			delTableau();
			if (incrementPosition()) {
				while (JeuConsole.valeurFixe < dimension) {
					JeuConsole.valeurFixe++;
					if (tableau.caseValide(JeuConsole.fileFixe,
							JeuConsole.colonneFixe, JeuConsole.valeurFixe)) {
						tableau.putValeur(JeuConsole.fileFixe,
								JeuConsole.colonneFixe, JeuConsole.valeurFixe);
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

	/** changes coordinate system */
	public static void alafbet2IntCoord(char alafX, int alafY) {
		/** a->0 , b->1 */
		System.out.println("alafX=" + alafX + "=" + (int) alafX);
		System.out.println(alafY);

		X = ((int) alafX) - 96;
		/** X Y static to solve main() static state */
		Y = ((int) alafY);

		System.out.println(X);
		System.out.println("in alafbet2IntCoord Y=" + Y);
	}

	/**
	 * changes coordinate system from gobanLetters.png to conventional notation
	 * a9=19->00 c8=38=21
	 */
	public static void gobanIntCoord2Matrix(int gobX, int gobY) {
		X = gobX - 1;
		Y = 5 + (5 - gobY) - 1;// 1..5..9 symetric axis=5 abs((9-5)|(1-5))=4
		// -1 for starting at 0

		/*
		 * String s ="a"; int i = s.charAt(0) ;
		 * 
		 * System.out.println("charAt="+i);
		 */
	}

	public static void main(String[] args) throws IOException {
		alafbet2IntCoord('a', 5);
		System.out.println("alafbet2IntCoord X=" + X + "Y=" + Y);

		alafbet2IntCoord('b', 5);
		System.out.println("X=" + X + "Y=" + Y + "\n\n\n");

		System.out.println("gobanIntCoord2Matrix");
		gobanIntCoord2Matrix(1, 9);
		System.out.println("X=" + X + "Y=" + Y);

		gobanIntCoord2Matrix(0, 0);
		/** Menu */
		int choice;
		do {
			System.out.println("1)Live\n2)Placer pions ");
			System.out.println("Which choice?");

			Scanner in = new Scanner(System.in);
			choice = in.nextInt();// !!
			// choice = 2;// !!
			System.out.println("Your choice is:" + choice);

			switch (choice) {
			case 2:
				break;
			default:
				System.out.println("Unknown choice");
			}
			// choice = 2;// !!

			System.out.println("lbl Main40");
			Tableau snapshot = new Tableau();
			// snapshot = snapshot.getSnap();
			System.out.println("lbl Main41");

			System.out.println("\n        MyEnd\n");

			JeuConsole j = new JeuConsole(); // !!affiche le tableau av des 0,-1
			// et 1

			// System.out.println("lbl Main44");
			// Tableau t = new Tableau();
			// System.out.println("lbl Main45");
			//
			// j.tableauFixe = new Tableau();
			// j.tableauFixe.putValeur(0, 0, 2);
			// j.tableauFixe.putValeur(1, 3, 1);
			// j.tableauFixe.putValeur(2, 1, 4);
			// JeuConsole.fileFixe = 0;
			// JeuConsole.colonneFixe = -1;
			// JeuConsole.valeurFixe = 0;
			// boolean activated = true;
			// j.putTableau(j.tableauFixe);
			// while (!t.isComplet() && !j.listeVide()) {
			// t = j.getTableau();
			// // System.out.print("SACO");
			// //
			// System.out.println("->"+t.uFile+" "+t.uColonne+" "+t.uValor+" ");
			// // j.printListaTableros();
			// JeuConsole.valeurFixe = 0;
			// JeuConsole.fileFixe = t.uFile;
			// JeuConsole.colonneFixe = t.uColonne;
			// if (activated == true) {
			// JeuConsole.fileFixe = 0;
			// JeuConsole.colonneFixe = -1;
			// JeuConsole.valeurFixe = 0;
			// activated = false;
			// }
			// j.delTableau();
			// if (j.incrementPosition() == true) {
			// while (JeuConsole.valeurFixe < j.dimension) {
			// JeuConsole.valeurFixe++;
			// if (t.caseValide(JeuConsole.fileFixe,
			// JeuConsole.colonneFixe, JeuConsole.valeurFixe)) {
			// t.putValeur(JeuConsole.fileFixe,
			// JeuConsole.colonneFixe,
			// JeuConsole.valeurFixe);
			// j.putTableau(t);
			// // System.out.print("METO");
			// //
			// System.out.println("->"+Juego.FileFija+" "+Juego.ColonneFija+" "+Juego.valorFijo);
			// // j.printListaTableros();
			// }
			// }
			// }
			// }
			// t.printTableau();
			//
			// System.out.println("my choice=" + choice);
			//		

		} while ((choice != -1) && (choice != 2));// !à modifier

	}
}