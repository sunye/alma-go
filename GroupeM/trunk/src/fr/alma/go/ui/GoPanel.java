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

public class GoPanel extends JPanel implements MouseListener {
	
	private int abs;
	
	private int ord;
	
	private MouseAdapter click;
	
	public GoPanel(){
		super();
		click = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClicked(e);
            }
        };
	}
	
	public int getAbs(){
		return abs;
	}
	
	public int getOrd(){
		return ord;
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

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
		abs=event.getX();
		ord=event.getY();
		StaticDummy.print(abs+";"+ord);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
