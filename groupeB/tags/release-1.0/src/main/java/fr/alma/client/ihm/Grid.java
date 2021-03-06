/*
 *   
 *   IA Project ATARI-GO
 *   UNIVERSITY OF NANTES
 *   MASTER ALMA 1
 *   2009 - 2010
 * 	 Version 1.0
 *   
 *   $Date$
 *   $Author$
 * 	 $Revision$
 * 
 *   Copyright (C) 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * 
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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

	
	public static void paintGrid(Graphics graph, Context context) { 
		for (int x = 0; x < context.getSizeGoban(); x++)
			for (int y = 0; y < context.getSizeGoban(); y++) {
				BufferedImage image = getImage(x, y, context);
				graph.drawImage(image, 10+(image.getWidth()*x), 10+(image.getHeight()*y), null);
			}
	}
	
	
	public static BufferedImage getImage(int col, int row, Context context) {
		int indice = 0;

		if (row == 0) {
			if (col == 0)
				indice = 1;
			else if (col == context.getSizeGoban()-1)
				indice = 3;
			else 
				indice = 2;
		} else if (row == context.getSizeGoban()-1) {
			if (col == 0)
				indice = 7;
			else if (col == context.getSizeGoban()-1)
				indice = 9;
			else 
				indice = 8;
		} else {
			if (col == 0)
				indice = 4;
			else if (col == context.getSizeGoban()-1)
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
