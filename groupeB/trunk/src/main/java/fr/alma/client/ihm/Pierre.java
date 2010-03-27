package fr.alma.client.ihm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import fr.alma.client.action.Context;
import fr.alma.server.rule.Configuration;


public class Pierre {

	public static final String PIERRE_BLANCHE = "wmoku";
	public static final String PIERRE_NOIRE = "bmoku";
	
	private static BufferedImage[] arrayImageB = new BufferedImage[18];
	private static BufferedImage[] arrayImageN = new BufferedImage[18];
	private static BufferedImage[] arrayImage = null;
	
	public static void paintPierre(Graphics g, Context context) {
		for (int x = 0; x < context.getSizeGoban(); x++)
			for (int y = 0; y < context.getSizeGoban(); y++) {
				if (! context.getStateGame().isFree(x, y)) {
					BufferedImage image = getImage(x, y, context);
					g.drawImage(image, 10+(image.getWidth()*x), 10+(image.getHeight()*y), null);
				}
			}
	}
	
	
	public static BufferedImage getImage(int x, int y, Context context) {
		int indice = 1;
		// 
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
		
		if (context.getStateGame().isComputer(x, y)) {
			if (context.getComputer().getColor() == Configuration.WHITE) {
				arrayImage = arrayImageB;
			} else {
				arrayImage = arrayImageN;
			}
		} else {
			if (context.getPlayer().getColor() == Configuration.WHITE) {
				arrayImage = arrayImageB;
			} else {
				arrayImage = arrayImageN;
			} 
		}
		
		if (arrayImage[indice-1] == null) {
			String imageFileName = "image/" + getColorPierre(x, y, context) + indice + ".png"; 
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

	
	private static String getColorPierre(int x, int y, Context context) {
		String color = null;
		
		if (context.getStateGame().isComputer(x, y)) {
			if (context.getComputer().getColor() == Configuration.WHITE) {
				color = PIERRE_BLANCHE;
			} else {
				color = PIERRE_NOIRE;
			}
		} else {
			if (context.getPlayer().getColor() == Configuration.WHITE) {
				color = PIERRE_BLANCHE;
			} else {
				color = PIERRE_NOIRE;
			}
		}
		return color;
	}

	
}
