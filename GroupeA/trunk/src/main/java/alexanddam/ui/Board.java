package main.java.alexanddam.ui;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Collection;
import java.util.Vector;

class Board extends Canvas {
	private static final long serialVersionUID = 1L;

	int width = 300;
	int height = 300;
	int nbCases = 8;
	static int space = 15;
	static int diametreBile = 20;
	float diam1, diam2;
	int nbC;

	
	private Collection<Point> whitePoints = new Vector<Point>();  // noirs
	private Collection<Point> blackPoints = new Vector<Point>(); // blancs
	private Collection<Point> vLoosers = new Vector<Point>(); // blancs

	private WhiteStone wStone;
	private BlackStone bStone;

	public void resetTable() {
		whitePoints.clear();
		blackPoints.clear();
		vLoosers.clear();
		this.repaint();
	}
	
	public void updateLoosers(Collection<Point> loo){
		vLoosers.addAll(loo);
	}

	public Collection<Point> getvPoints2() {
		return blackPoints;
	}

	public void setBlack(Point point) {
		this.blackPoints.add(point);
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

	public Collection<Point> getV() {
		return whitePoints;
	}

	public void setWhite(Point whitePoint) {
		this.whitePoints.add(whitePoint);
	}

	public Board() {
		nbC = ((Board) this).getNbCases();
		diam1 = nbC / (float)(this.width - 2 * Board.space);
		diam2 = nbC / (float)(this.height - 2 * Board.space);
	}

	@Override
	public void paint(Graphics graph) {
		graph.setColor(new Color(139, 69, 19));
		graph.fillRect(0, 0, width, height);
		graph.setColor(new Color(0, 0, 0));

		for (int i = 0; i <= this.nbCases ; i++) {

			graph.drawLine(i * (this.width - 2*space) / this.nbCases + space, space,
					i * (this.width - 2*space) / this.nbCases + space, this.height-space);

			graph.drawLine(space, i * (this.height - 2*space) / this.nbCases + space,
					this.width-space, i * (this.height - 2*space) / this.nbCases + space);

		}

		// Dessins des points noirs
		graph.setColor(new Color(0, 0, 0));
		for(Point v:whitePoints){
			wStone = new WhiteStone();
			graph.drawImage(wStone.image, v.x, v.y, wStone);
		}

		// Dessins des points blancs
		graph.setColor(new Color(255, 255, 255));
		for(Point v2:blackPoints) {
			bStone = new BlackStone();
			graph.drawImage(bStone.image, v2.x, v2.y, bStone);
		}

		// Dessins des pierres perdantes
		Graphics2D graph2 = (Graphics2D) graph;
			graph2.setColor(new Color(0, 204 , 255));
			float dash1[] = {5.0f};
		    BasicStroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
		    graph2.setStroke(dashed); 
		    
		for(Point v2:vLoosers) {
			int coord1 = (int) (v2.x / diam2 - diametreBile / 2);
			int coord2 = (int) (v2.y / diam1 - diametreBile / 2);
			
			graph2.drawRect(coord2+10, coord1+10, 30, 30);
		}
	}
}