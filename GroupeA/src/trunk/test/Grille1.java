package trunk.test;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import javax.swing.JPanel;

public class Grille1 extends JPanel{
	private static final long serialVersionUID = 1L;
	private int rows; // nombre de lignes
	private int columns; // nombre de colonnes
	private int hDist; // distance horizontale entre 2 croix
	private int vDist; // distance verticale entre 2 croix
	private final int LENGTH = 6; // longueur et hauteur de la croix dessinee
	private Point2D start; // point de click de la souris
	private Point2D end; // point de relachement
	
	public Grille1 (int r, int c, int hd, int vd) {
		rows = r;
		columns = c;
		hDist = hd;
		vDist = vd;

		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent mEvt){
				// si le click est dans la grille:
				if (mEvt.getPoint().getX() <= (rows * hDist) && mEvt.getPoint().getY() <= (columns * vDist)){
					start = mEvt.getPoint();
					System.out.println("x = "+(int)start.getX() + " y = "+(int)start.getY());
				}
			}

			public void mouseRelesed(MouseEvent mEvt){
				start = null;
				end = null;
			}
		});

		addMouseMotionListener(new MouseAdapter(){
			public void mouseDragged(MouseEvent mEvt){
				if (mEvt.getPoint().getX() <= (rows * hDist) && mEvt.getPoint().getY() <= (columns * vDist)){
					end = mEvt.getPoint();
					System.out.println("xm = "+end.getX() + " ym = "+end.getY());
					repaint();
				}
			}
		});
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		/* pour une meilleur lisibilite du code, on va separer
		 * le dessin des cellules du dessin des croix bien que
		 * ces premieres ne qoient pas vraiment necessaires
		 */
		/* Commencons par dessiner les lignes horizontales.
		 * n'oublier pas que le point avec les coordonn�es (0,0)
		 * se situe au coin superieur gauche de la fenetre
		 */

		for(int i = 1; i <= rows; i++)
			g2D.drawLine(0, i * vDist , columns * hDist, i * vDist );

		/* dessiner les lignes verticales */
		for(int i = 1; i <= columns; i++)
			g2D.drawLine(i * hDist, 0, i * hDist, columns * vDist );
		/* Dessiner les croix:
		 * chaque croix se situe au milieu d'une cellule
		 */

		for(int i = 1; i <= rows; i++){
			for (int j = 1; j <= columns; j++){
				// une fois on est dans la cellule (i,j):
				// dessinons une petite ligne horizontale
				int x1 = (i * hDist) - ((hDist + LENGTH) /2);
				int y1 = (j * vDist) - (vDist / 2);
				int x2 = x1 + LENGTH;
				int y2 = y1;
				g2D.drawLine(x1, y1, x2, y2);
				// et une petite ligne verticale
				x1 = (i * hDist) - (hDist / 2);
				y1 = (j * vDist) - ((vDist + LENGTH) /2);
				x2 = x1;
				y2 = y1 + LENGTH;
				g2D.drawLine(x1, y1, x2, y2);
				// Dessinon la ligne que l'utlisateur construit
				if ((start != null) && (end != null)){
					int sRow = (int)start.getX()/ hDist +1 ;
					int sCol = (int)start.getY()/ vDist +1;
					int sx = (sRow * hDist) - (hDist / 2);
					int sy = (sCol * vDist) - (vDist / 2);
					int eRow = (int)end.getX()/ hDist +1;
					int eCol = (int)end.getY()/ vDist +1;
					int ex = (eRow * hDist) - (hDist / 2);
					int ey = (eCol * vDist) - (vDist / 2);
					g2D.drawLine(sx, sy, ex, ey);
				}
			}
		}
	}
}