/*
 * IA Project ATARI-GO
 * UNIVERSITY OF NANTES
 * MASTER ALMA 1
 * 2009 - 2010
 * Version 1.0
 * @author Romain Gournay & Bruno Belin
 * 
 * Copyright 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * Use is subject to license terms.
 */
package fr.alma.client.ihm;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import fr.alma.client.action.Context;
import fr.alma.common.ui.Tools;


/**
 * Define the Grid of the Goban 
 */
public class Grid {

	private static BufferedImage[] arrayImage = new BufferedImage[10];

	
	public static void paintGrid(Graphics g, Context context) { 
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
			BufferedImage image = Tools.getImage(context.getGoban().getClass(), imageFileName);

			arrayImage[indice-1] = image;
		}
		return arrayImage[indice-1];
	}
		
}
