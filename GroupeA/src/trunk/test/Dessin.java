package trunk.test;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Dessin extends JPanel implements MouseListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Vector<Point> v = null;

    Point p = null;

    public Dessin() {
        super();
        v = new Vector<Point>();
        p = new Point();

        p.x = 0;
        p.y = 0;

        v.add(new Point(p));

        this.addMouseListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        p.x = e.getX();
        p.y = e.getY();
        v.add(new Point(p));
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x1, x2, y1, y2;
        for (int i = 1; i < v.size(); i++) {
            x1 = ((Point) v.elementAt((i - 1))).x;
            y1 = ((Point) v.elementAt((i - 1))).y;
            x2 = ((Point) v.elementAt(i)).x;
            y2 = ((Point) v.elementAt(i)).y;
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public void mousePressed(MouseEvent arg0) {}

    public void mouseReleased(MouseEvent arg0) {}

    public void mouseEntered(MouseEvent arg0) {}

    public void mouseExited(MouseEvent arg0) {}

    public static void main(String[] args) {
        Dessin dessin = new Dessin();
        JFrame jf = new JFrame();

        jf.setSize(640, 480);

        jf.getContentPane().setLayout(new BorderLayout());
        jf.getContentPane().add(dessin, BorderLayout.CENTER);

        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}