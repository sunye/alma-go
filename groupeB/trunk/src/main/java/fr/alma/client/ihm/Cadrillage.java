package fr.alma.client.ihm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import fr.alma.client.action.Context;


public class Cadrillage {

	private static BufferedImage[] arrayImage = new BufferedImage[10];

	
	public static void paintCadrillage(Graphics g, Context context) { 
		for (int x = 0; x < context.getSizeGoban(); x++)
			for (int y = 0; y < context.getSizeGoban(); y++) {
				BufferedImage image = getImage(x, y, context);
				g.drawImage(image, 10+(image.getWidth()*x), 10+(image.getHeight()*y), null);
			}
	}
	
	
	public static BufferedImage getImage(int x, int y, Context context) {
		int indice = 0;

		if (y == 0) {
			if (x == 0)
				indice = 1;
			else if (x == context.getSizeGoban()-1)
				indice = 3;
			else 
				indice = 2;
		} else if (y == context.getSizeGoban()-1) {
			if (x == 0)
				indice = 7;
			else if (x == context.getSizeGoban()-1)
				indice = 9;
			else 
				indice = 8;
		} else {
			if (x == 0)
				indice = 4;
			else if (x == context.getSizeGoban()-1)
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
