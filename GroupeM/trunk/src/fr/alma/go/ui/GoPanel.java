package fr.alma.go.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.alma.go.Game;

public class GoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MouseAdapter click;
	private Game game;

	public GoPanel(Game gm) {
		super();
		game = gm;
	}

	public void enableClick() {
		click = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				processMouseClicked(e);
			}
		};
	}

	public void disableClick() {
		removeMouseListener(click);
	}

	public void paintComponent(Graphics g) {
		try {
			Image img = ImageIO.read(new File(
					"src/fr/alma/go/ui/images/Goban2.png"));
			g.drawImage(img, 0, 0, this);
			// Pour une image de fond
			// g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error");
			e.printStackTrace();
		} // try
	} // void paintComponent(Graphics)

	public void processMouseClicked(final MouseEvent event) {
		boolean ok = false;
		while (!ok) {
			int abs = event.getX();
			int ord = event.getY();
			if (abs < 500 && ord < 500) {
				abs = ((abs - 2) * 9) / 500;
				ord = ((ord - 45) * 9) / 500;
				ok = game.play(abs, ord);
			} // if
		} // while
	} // void processMouseClicked(MouseEvent)

	public MouseListener getClick() {
		return click;
	}

}
