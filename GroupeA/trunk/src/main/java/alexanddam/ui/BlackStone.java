package main.java.alexanddam.ui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

public class BlackStone extends Canvas{
	private static final long serialVersionUID = 1L;
	Image image;

	String img = "black.gif";
	
	/**
	 * Constructor of the black stone
	 */
	public BlackStone() {
		MediaTracker media = new MediaTracker(this);
		image = Toolkit.getDefaultToolkit().getImage(img);
		media.addImage(image, 0);
		try {
			media.waitForID(0); 
		}
		catch (Exception e) {}
	}

	/**
	 * draw the black stone
	 */
	public void paint(Graphics grap) {
		grap.drawImage(image, 0,0, this);
	}

}