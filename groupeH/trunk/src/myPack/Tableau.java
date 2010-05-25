package myPack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.stream.FileImageInputStream;

public class Tableau {

	Cellule[][] casee = new Cellule[9][9];
	int uFile;
	int uColonne;
	int uValeur;
	static int dimension = 9;
	
	
	Tableau() {
		initialisation();
	}

	void initialisation() {
		uFile = 0;
		uColonne = 0;
		uValeur = 0;
		for (int f = 0; f < 9; f++)
			for (int c = 0; c < 9; c++) {
				casee[f][c] = new Cellule();
				casee[f][c].putFile(f);
				casee[f][c].putColumn(c);
				casee[f][c].putValeur(0);
			}
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public Tableau getSnap() throws IOException {
		// TODO Auto-generated method stub
		Tableau t = new Tableau();

		// String snapFile = new String("snapfile.txt");
		// FileInputStream snapFile = new FileInputStream("snapfile.txt");

		File filtaic = File.createTempFile("tac", "tic");
		// FileOutputStream

		// String s = new BufferedReader(System.in)).readLine();//p340
		File out = new File("src/myPack/foo.txt");
		FileWriter fw = new FileWriter(out);// marche
		// PrintWriter pw = new PrintWriter(fw);
		// pw.println(s);
		// fw.close();

		File snapFile = new File("src/myPack/snapfile.txt");
		/*
		 * File dirr = new File("dirrr");//fichier file dirr.mkdir();//marche
		 */

		FileReader fr = new FileReader(snapFile); // p339
		BufferedReader in = new BufferedReader(fr);
		String line;
		line = in.readLine();// neglect 1st line of comments
		while (((line = in.readLine()) != null) && (!line.equals("//black"))) {
			System.out.println("Line=" + line);
			int u = line.substring(0, 1).charAt(0) - 97;
			System.out.println("u=" + u);

			String v0 = line.substring(1, 2);
			System.out.println("substr=" + v0);
			int v = new Integer(v0);// .intValue
			// casee[u][(v)] = (new Cellule(u, v, -1));
			putValeur(-(v - 5) + 5 - 1, u, -1);
		}
		System.out.println("printTabl");
		while (((line = in.readLine()) != null) && (!line.equals("//black"))) {
			System.out.println("Line=" + line);
			int u = line.substring(0, 1).charAt(0) - 97;
			System.out.println("u=" + u);

			String v0 = line.substring(1, 2);
			System.out.println("substr=" + v0);
			int v = new Integer(v0);// .intValue
			// casee[u][(v)] = (new Cellule(u, v, -1));
			putValeur(-(v - 5) + 5 - 1, u, 1);
		}
		// this.printTableau();
		this.printSmartTable();
		return this;
	}

	// nb casee vides du tableau
	int numCasesVides() {
		int counter = 0;
		for (int x = 0; x < dimension; x++)
			for (int y = 0; y < dimension; y++)
				if (casee[x][y].getValeur() < 1)
					counter++;
		return counter;
	}

	/**
	 * @return the uFile
	 */
	public int getUFile() {
		return uFile;
	}

	/**
	 * @param file
	 *            the uFile to set
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
	 * @param colonne
	 *            the uColonne to set
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
	 * @param valeur
	 *            the uValeur to set
	 */
	public void setUValeur(int valeur) {
		uValeur = valeur;
	}

	void putValeur(int fila, int columna, int valor) {
		casee[fila][columna].putValeur(valor);
		uFile = fila;
		uColonne = columna;
		uValeur = valor;
	}

	int getValeur(int fila, int columna) {
		return casee[fila][columna].valeur;
	}

	boolean existeValeurFile(int fila, int columna, int valor) {
		boolean retourne = false;
		for (int x = 0; x < dimension; x++)
			if (casee[fila][x].valeur == valor && x != columna) {
				retourne = true;
				break;
			}
		return retourne;
	}

	boolean existeValeurColonne(int fila, int columna, int valor) {
		boolean retourne = false;
		for (int x = 0; x < dimension; x++)
			if (casee[x][columna].valeur == valor && x != fila) {
				retourne = true;
				break;
			}
		return retourne;
	}

	// verifie q ia deja valeur ds 1 secteur
	boolean existeValorSector(int fila, int columna, int valor) {
		boolean retour = false;
		if (dimension == 9) {
			int sector = getSector(fila, columna);
			int f1, c1;
			f1 = (((sector - 1) / 3) * 3);
			c1 = (((sector - 1) % 3) * 3);
			for (int x = f1; x < f1 + 3; x++)
				for (int y = c1; y < c1 + 3; y++)
					if (casee[x][y].valeur == valor && x != fila
							&& y != columna) {
						retour = true;
						break;
					}
		}
		return retour;
	}

	int getSector(int fila, int columna) {
		return (columna / 3) + ((fila / 3) * 3) + 1;
	}

	// verifie si tableau est valide
	boolean isValide(Tableau t) {
		boolean retourne = true;
		for (int fila = 0; fila < dimension; fila++)
			for (int columna = 0; columna < dimension; columna++) {
				if (existeValeurFile(fila, columna,
						t.casee[fila][columna].valeur)
						|| existeValeurColonne(fila, columna,
								t.casee[fila][columna].valeur)
						|| existeValorSector(fila, columna,
								t.casee[fila][columna].valeur)) {
					retourne = false;
					break;
				}
			}
		return retourne;
	}

	boolean caseValide(int fila, int columna, int valor) {
		return !existeValorSector(fila, columna, valor)
				&& !existeValeurFile(fila, columna, valor)
				&& !existeValeurColonne(fila, columna, valor);
	}

	boolean isComplet() {
		boolean retour = true;
		for (int fila = 0; fila < dimension; fila++)
			for (int columna = 0; columna < dimension; columna++)
				if (casee[fila][columna].valeur < 1) {
					retour = false;
					break;
				}
		return retour;
	}

	void printTableau() {
		for (int f = 0; f < dimension; f++) {
			for (int c = 0; c < dimension; c++) {
				System.out.print(" " + getValeur(f, c));
			}
			System.out.println(" ");
		}
	}

	/** x=Point notation -> x=Absc ; i=Matrix notation ->row */
	public void printSmartTable() {
		System.out.println("   A  B  C  D  E  F  G  H  I");// label before data
		String s;
		for (int f = 0; f < dimension; f++) {
			System.out.print(-((f + 1) - 5) + 5);
			for (int c = 0; c < dimension; c++) {
				int gv = getValeur(f, c);
				if (gv != -1)
					s=" "+gv;
				else
					 s=""+gv;
					
				System.out.print(" " + s);
			}
			System.out.println(" ");
		}
		System.out.println("   A  B  C  D  E  F  G  H  I");
		
	}

}
