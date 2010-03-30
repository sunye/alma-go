package main.java.alexanddam.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Vector;

class Board extends Canvas {
	private static final long serialVersionUID = 1L;

	int width = 300;
	int height = 300;
	int nbCases = 8;
	static int space = 15;
	static int diametreBile = 20;

	private Vector<Point> whitePoints = new Vector<Point>();  // noirs
	private Vector<Point> blackPoints = new Vector<Point>(); // blancs
	private Vector<Point> vLoosers = new Vector<Point>(); // blancs

	private WhiteStone wStone;
	private BlackStone bStone;

	public void resetTable() {

		whitePoints.clear();
		blackPoints.clear();
		this.repaint();
	}

	public Vector<Point> getvPoints2() {
		return blackPoints;
	}

	public void setBlack(Point p) {
		this.blackPoints.add(p);
	}

	public int getWidth() {
		return width;
	}

	void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	void setHeight(int height) {
		this.height = height;
	}

	public int getNbCases() {
		return nbCases;
	}

	void setNbCases(int nbCases) {
		this.nbCases = nbCases;
	}

	public Vector<Point> getV() {
		return whitePoints;
	}

	public void setWhite(Point v) {
		this.whitePoints.add(v);
	}

	public Board() {
	}

	@Override
	public void paint(Graphics g) {

		g.setColor(new Color(139, 69, 19));
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(0, 0, 0));

		for (int i = 0; i <= this.nbCases ; i++) {

			g.drawLine(i * (this.width - 2*space) / this.nbCases + space, space,
					i * (this.width - 2*space) / this.nbCases + space, this.height-space);

			g.drawLine(space, i * (this.height - 2*space) / this.nbCases + space,
					this.width-space, i * (this.height - 2*space) / this.nbCases + space);

		}

		// Dessins des points noirs
		g.setColor(new Color(0, 0, 0));
		for(Point v:whitePoints){
			wStone = new WhiteStone();
			g.drawImage(wStone.image, v.x, v.y, wStone);
		}

		// Dessins des points blancs
		g.setColor(new Color(255, 255, 255));
		for(Point v2:blackPoints) {
			bStone = new BlackStone();
			g.drawImage(bStone.image, v2.x, v2.y, bStone);
		}

		// Dessins des pierres perdantes
		g.setColor(new Color(255, 0, 0));
		for(Point v2:vLoosers) {
			g.drawRect(v2.x-5, v2.y-5, 30, 30);
		}
	}
}