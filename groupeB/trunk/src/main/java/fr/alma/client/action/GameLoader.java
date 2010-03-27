package fr.alma.client.action;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



public class GameLoader {

	private Boolean[][] intersection = new Boolean[9][9];


	public Boolean[][] loadGame(String fileName)  {
		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);

			String line = null;
			int i = 0;
			while (sc.hasNextLine() && i < 9) {
				line = sc.nextLine(); // lecture de la ligne courante
				if (line != null)
					for (int j = 0; j < 9; j++) {
						if (line.charAt(j) == 'x') intersection[j][i] = false;
						else if (line.charAt(j)=='o') intersection[j][i] = true;
					}
				i++; // pour traitement de la ligne suivante
			}
			sc.close();
			System.out.println("GameLoaded.loadGame()");
		} catch (FileNotFoundException e) {
			System.err.println("File " + fileName + " doesn't exist!");
		}

		return intersection;
	}

}
