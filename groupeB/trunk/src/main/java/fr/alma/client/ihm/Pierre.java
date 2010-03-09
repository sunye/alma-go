package fr.alma.client.ihm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import fr.alma.server.core.IStateGame;

public class Pierre {

	public static final String PIERRE_BLANCHE = "wmoku";
	public static final String PIERRE_NOIRE = "bmoku";
	
	private static BufferedImage[] arrayImageB = new BufferedImage[18];
	private static BufferedImage[] arrayImageN = new BufferedImage[18];
	private static BufferedImage[] arrayImage = null;
	
	public static void paintPierre(Graphics g, IStateGame stateGame) {
		for (short x = 0; x < Goban.LINE_V; x++)
			for (short y = 0; y < Goban.LINE_H; y++) {
				if (! stateGame.isFree(x, y)) {
					BufferedImage image = getImage(x, y, stateGame.isComputer(x, y));
					g.drawImage(image, 10+(image.getWidth()*x), 10+(image.getHeight()*y), null);
				}
			}
	}
	
	
	public static BufferedImage getImage(short x, short y, boolean computer) {
		short indice = 1;
		
		if (y == 0) {
			if (x == 0)
				indice = 1;
			else if (x == Goban.LINE_V-1)
				indice = 3;
			else 
				indice = 2;
		} else if (y == Goban.LINE_H-1) {
			if (x == 0)
				indice = 7;
			else if (x == Goban.LINE_V-1)
				indice = 9;
			else 
				indice = 8;
		} else {
			if (x == 0)
				indice = 4;
			else if (x == Goban.LINE_V-1)
				indice = 6;
			else
				indice = 5;
		}
		
		arrayImage = (computer ? arrayImageB : arrayImageN);
		
		if (arrayImage[indice-1] == null) {
			String imageFileName = "image/" + (computer ? PIERRE_BLANCHE : PIERRE_NOIRE) + indice + ".png"; 
			URL imageSrc = null;
			try {
				imageSrc = ((new File(imageFileName)).toURI()).toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
				System.exit(1);
			}
			BufferedImage image = null;
			try {
				image = ImageIO.read(imageSrc);

			} catch (IOException e) {
				System.out.println("Image could not be read");
				System.exit(1);
			}
			arrayImage[indice-1] = image;
		}
		return arrayImage[indice-1];
	}

}
