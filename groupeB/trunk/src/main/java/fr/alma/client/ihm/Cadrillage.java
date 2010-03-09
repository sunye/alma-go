package fr.alma.client.ihm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Cadrillage {

	private static BufferedImage[] arrayImage = new BufferedImage[10];

	
	public static void paintCadrillage(Graphics g) { 
		for (short x = 0; x < Goban.LINE_V; x++)
			for (short y = 0; y < Goban.LINE_H; y++) {
				BufferedImage image = getImage(x, y);
				g.drawImage(image, 10+(image.getWidth()*x), 10+(image.getHeight()*y), null);
			}
	}
	
	
	public static BufferedImage getImage(short x, short y) {
		short indice = 0;

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
		
		if (arrayImage[indice-1] == null) {
		
			String imageFileName = "image/empty" + indice + ".png";
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
