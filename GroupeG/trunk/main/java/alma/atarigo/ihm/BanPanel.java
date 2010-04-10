package alma.atarigo.ihm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.Painter;
import org.jdesktop.swingx.painter.PinstripePainter;

import alma.atarigo.Cell;
import alma.atarigo.CellContent;
import alma.atarigo.CellPosition;
import alma.atarigo.GobanModel;
import alma.atarigo.model.CellImpl;
import alma.atarigo.model.CellPositionImpl;


public class BanPanel extends JXPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1560400719940621965L;

	/**
     * La taille maximum d'une grille de goban
     */
    private static final int MAX_BOARD_SIZE = 640;

    /**
     * Pourcentage permet de calculer la distance entre 2 pieces
     * par rapport la taille d'une cellule de la grille
     */
    private static final double pieceFactor = 0.10;

    /**
     * Pourcentage qui permet de calculer la distance entre le bord
     * du tableau et le bord de la grille
     */
    //private static final double borderFactor = 0.05;

    private final BasicStroke surroundStroke = new BasicStroke(1.55f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    private final BasicStroke borderStroke = new BasicStroke(1.55f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
    private final Color borderColor = Color.BLACK;

    private final BanPanel self = this;
    
    /**
     * La taille d'un table Grille + Bord compris
     */
    int boardSize = 0;

    /**
     * Les coordonées du tableau
     */
    int boardX=0,boardY=0;

    /**
     * L'abcisse de la premiere case de la grille
     */
    private int banX = 0;

    /**
     * L'ordonnee de la  premiere case de la grille
     */
    private int banY = 0;

    /**
     * Le nombre de pixel entre le bord du panel
     * et le bord du ban, a ne pas confondre avec
     * la difference entre le bord du ban et celui de la grille
     */
    private int banXBounds = 30;
    private int banYBounds = 30;
    private final double boundFactor = 0.10;

    /**
     * La largeur total de la grille
     */
    private int banSize = 0;

    /**
     * Le nombre de pixel entre le bord du tableau et le bord de la grille
     */
    private int banSizeOffset = 30;

    /**
     * La taille d'une cellule
     */
    private int pieceSize = 0;

    /**
     * La moitié de la distance entre 2 pièces
     */
    private int pieceSizeOffset = 2;

    /**
     * Le nombre de piece par ligne et par colonne du Goban
     */
    private int pieceCount = 9;

    /**
     * La couleur d'arriere plan du Goban
     */
    private final Color banBackground = new Color(222,184,135);
    private Paint banPaint = null;
    private Painter banPainter = null;

    /**
     * La couleur du kuro
     */
    private final Color kuroColor = Color.BLACK;
    private Paint kuroPaint = null;
    private Object kuroPainted = kuroColor;

    /**
     * La couleur du shiro
     */
    private final Color shiroColor = Color.WHITE;
    private Paint shiroPaint = null;
    private Object shiroPainted = shiroColor;
    
    /**
     * La couleur de l'aide
     */
    private final Color hintColor = Color.GREEN;
    
    /**
     * La taille de la fonte pour l'affichage des index.
     * Qui tient compte de la distance entre le bord du tableau
     * et l'extremité des pions situés au bord
     */
    int indexTextFontSize = 12;

    /**
     * La position pour le dessin des index
     */
    Font indexTextFont = new Font("Helvetica",Font.BOLD, 12);

    /**
     * Le nombre de pixel qui doit separer le bord d'un index et le bord d'une piece
     */
    int indexTextOffset = 2;

    /**
     * La couleur du text d'index
     */
    Color indexFontColor = Color.BLACK;

	/**
     * La localization des pieces a l'écran
     */
    private final Piece[][] pieces = new Piece[pieceCount][pieceCount];

    /**
     * Utiliser lors de la demande d'une piece au click de la souris
     */
    CellPosition selectedPiece;
    
    /**
     * Les hints represente l'aide apporté aux joueurs
     */
    List<CellPosition> hintCells = new ArrayList<CellPosition>();
    /**
     * La liste de piece en encadré sur le goban
     */
    List<CellPosition> surrounded = new ArrayList<CellPosition>();
    /**
     * Les pièces entourées qui ne le seront plus au prochain affichage du goban
     */
    List<CellPosition> tmpSurrounded = new ArrayList<CellPosition>();
    List<Color> tmpColors = new ArrayList<Color>();
    
    /**
     * Utiliser pour synchroniser le click de la souris avec le goban
     */
    final Integer clickLock = 1;
    /**
     * Contiendra les actions a exécuter lors d'un click
     */
    MouseListener adapter = null;
    
    List<CellMouseListener> cellListeners = new ArrayList<CellMouseListener>();
    
    /**
     * Le goban a afficher
     */
    GobanModel model;

	public BanPanel(GobanModel model){
		this.model = model;
		this.pieceCount = model.getSize();
		
        //on localise les pieces
        for(int i=0;i<pieceCount;++i){
            final int row = i+1;
            for(int j=0;j<pieceCount;++j){
                final int column = j+1;
                pieces[i][j] = new Piece(row,column);
            }
        }
        
        configureIndexFont();
        setupBanPainter();
        setupKuroPaint();
        setupShiroPaint();
	}

	@Override
    public void paint(final Graphics g){
        super.paint(g);
        //on recalcule les dimensions du goban
        invalidateBan();
        //on redessine
        repaintBan((Graphics2D) g);
    }
	
    /**
     * Configurer la font des index
     */
    private void configureIndexFont(){
        indexTextFont = indexTextFont.deriveFont(Font.ITALIC).deriveFont(Font.BOLD);
    }
	
    /**
     * Recalculer les dimensions du ban en fonction des dimension du panel
     * Un repaint doit etre fait pour voir apparaitre les modifications
     */
    private void invalidateBan(){
        final int w = getWidth();
        final int h = getHeight();

        banXBounds = (int) (boundFactor * w);
        banYBounds = (int) (boundFactor * h);

        //========== On recalcule les dimensions
        //banSize = 320;
        boardSize = Math.min(Math.min(w - 2*banXBounds, h - 2*banYBounds),MAX_BOARD_SIZE); //la taille du tableau

        //========= on reajuste la distance au board
        banXBounds = (w - boardSize)/2;
        banYBounds = (h - boardSize)/2;

        //les nombres pixel entre le bord du tableau et le bord de la grille est fixé au minimum a 10% de la taille
        //banSizeOffset = (int) (borderFactor * boardSize);

        /*
         * On determine la taille de chaque piece
         */
        pieceSize = boardSize / (pieceCount+1);

        /*
         * On fixe la distance entre 2 pieces a 10% de la taille d'une cellule
         */
        pieceSizeOffset = (int) (pieceFactor * pieceSize);

        /*
         * La taille du ban donc de toutes les cellules doit etre include dans le boardSize
         * Et doit respecter les bords du tableau qui ont au minimum banSizeOffset pixel
         */
        banSize = pieceSize*(pieceCount-1);

        /*
         * On reajuste la taille du bord si le carré intérieur a été rétrécit
         */
        banSizeOffset = (boardSize - banSize)/2;

        /*
         * Les pixels supplémentaires on les utilises pour séparer les indexs des extremités des pions
         */
        indexTextOffset = banSizeOffset - pieceSize;
        
//        /*
//         * Si le taille ne forme pas un carre on prend le plus petit carré inclus dans la zone
//         */
//        banSize = banSize - indexTextOffset;
//

        /*
         * On determine les coordonnées de la premiere cellule
         */
        banX = banXBounds + banSizeOffset;
        banY = banYBounds + banSizeOffset;

//        banX = Math.max(banX, (w - banSize)/2);
//        banY = Math.max(banY, (h - banSize)/2);

        /**
         * Les coordonnées du tableau sont celle de la grille moins la taille du bord
         */
        boardX = banX - banSizeOffset;
        boardY = banY - banSizeOffset;
        
        /*
         * Les index doivent suffire au bord
         */
        indexTextOffset = Math.max(indexTextOffset,4);
        indexTextFontSize = banSizeOffset - pieceSize/2 - indexTextOffset;
        indexTextFont = indexTextFont.deriveFont((float)indexTextFontSize);

        //========== On invalide la position de toute les pieces
        for(int i=0;i<pieceCount;++i){
            for(int j=0;j<pieceCount;++j){
                pieces[i][j].invalidate();
            }
        }

        //setMinimumSize(new Dimension(banSize+2*(banBounds+banSizeOffset),banSize+2*(banBounds+banSizeOffset)));
    }
	
    /**
     * Redessiner tout le go ban
     * @param g Le context graphique
     */
    private void repaintBan(final Graphics2D g){

        /*
         * On redessine la grille
         */
        drawBan((Graphics2D) g);

        /*
         * On redessine les pions deja positionné
         */
        for(int row = 1;row<=pieceCount;++row){
            for(int column=1; column<=pieceCount ; ++column){
            	final CellPositionImpl p = new CellPositionImpl(row, column);            	
                final CellContent content = model.getCellContent(row, column);
                if(content!=null){
                    if(content.isKuro()){
                        pieces[row-1][column-1].renderCircle(g,kuroPainted);
                        if(surrounded.contains(pieces[row-1][column-1])){
                            pieces[row-1][column-1].surround(g, shiroPainted);
                        }
                        clearTemporarilySurrounded(p);
                    }

                    else if(content.isShiro()){
                        pieces[row-1][column-1].renderCircle(g, shiroPainted);
                        if(surrounded.contains(pieces[row-1][column-1])){
                            pieces[row-1][column-1].surround(g, kuroPainted);
                        }
                        clearTemporarilySurrounded(p);
                    }

                    else if(Preferences.SHOW_HINTS){
                    	if(content.isKuroSuicide()){
                    		pieces[row-1][column-1].renderCross(g, kuroPainted);
                    		if(surrounded.contains(pieces[row-1][column-1])){
                    			pieces[row-1][column-1].surround(g, shiroPainted);
                    		}
                    		clearTemporarilySurrounded(p);
                    	}
                    	else if(content.isShiroSuicide()){
                    		pieces[row-1][column-1].renderCross(g, shiroPainted);
                    		if(surrounded.contains(pieces[row-1][column-1])){
                    			pieces[row-1][column-1].surround(g, kuroPainted);
                    		}
                    		clearTemporarilySurrounded(p);
                    	}
                    	//                    else if(content.equals(CellContent.KuroWins)){
                    	//                        pieces[row-1][column-1].renderTriangle(g, kuroColor);
                    	//                        if(surrounded.contains(pieces[row-1][column-1])){
                    	//                            pieces[row-1][column-1].surround(g, kuroColor);
                    	//                        }
                    	//                        clearTemporarilySurrounded(p);
                    	//                    }
                    	//                    else if(content.equals(CellContent.ShiroWins)){
                    	//                        pieces[row-1][column-1].renderTriangle(g, shiroColor);
                    	//                        if(surrounded.contains(pieces[row-1][column-1])){
                    	//                            pieces[row-1][column-1].surround(g, shiroColor);
                    	//                        }
                    	//                        clearTemporarilySurrounded(p);
                    	//                    }
                    }
                    
                    else if(tmpSurrounded.contains(p)){
                    	Color sColor = tmpColors.get(tmpSurrounded.indexOf(p));
                    	if(sColor!=null){
                    		pieces[row-1][column-1].surround(g, sColor);                    		
                    	}
                    }
                                        
                }
            }
        }//for
        
        //Les hints
        for(CellPosition hint : hintCells){
        	pieces[hint.getRow()-1][hint.getColumn()-1].surround(g, hintColor);
        }
    }
    
    /**
     * Dessiner la grille
     * @param g2D Le contexte graphique
     */
    private void drawBan(final Graphics2D g2D){
        /*
         * On redessine le tableau
         */
        //GKit.fillRect(g2D,boardX, boardY, boardSize, boardSize,banBackground);

        final int x = banX;
        final int y = banY;
        final Color color = banBackground;

        //les contours
        GKit.drawRect(g2D, x, y, banSize, banSize, borderStroke, color);

        //la grille
        for(int i=0;i<pieceCount;++i){
            g2D.drawLine(x+i*pieceSize, y, x+i*pieceSize, y+banSize);
            g2D.drawLine(x, y+i*pieceSize, x+banSize, y+i*pieceSize);
        }


        //Affichage des index
        final Color savedColor = g2D.getColor();
        g2D.setColor(indexFontColor);

        //Utilitaires pour le dessin final de texte
        final FontRenderContext frc = g2D.getFontRenderContext();
        final FontMetrics metrics = g2D.getFontMetrics(indexTextFont);
        final int fontHeight = metrics.getHeight();

        //distance entre les bord du text et le bord de la grille
        final int offset = + (pieceSize + indexTextOffset)/2;

        //ordonnee haut de l'index
        final int topY = y - offset;
        //ordonnee bas de l'index
        final int bottomY =  y+banSize+offset+fontHeight/2;

        //on dessine les index des colonnes
        for(int i=0;i<pieceCount;++i){
            final char index = (char) ('A' + i);
            final int textWidth = metrics.stringWidth(""+index);

            final TextLayout textLayout = new TextLayout(""+index,indexTextFont,frc);
            final int tx = x+i*pieceSize - textWidth/2;

            //index en haut
            textLayout.draw(g2D,tx,topY);

            //index du bas
            textLayout.draw(g2D,tx,bottomY);
        }

        //on dessine les index des lignes
        for(int i=0;i<pieceCount;++i){
            final int index = pieceCount - i;
            final int textWidth = metrics.stringWidth(""+index);

            final TextLayout textLayout = new TextLayout(""+index,indexTextFont,frc);
            final int ty = y+i*pieceSize + fontHeight/2;
            final int leftX = x - offset - textWidth;
            final int rightX = x + banSize + offset;

            //index en haut
            textLayout.draw(g2D,leftX,ty);

            //index du bas
            textLayout.draw(g2D,rightX,ty);
        }

        //on affiche les points de repère du goban
        for(int i=1;i<pieceCount-1;++i){
        	for(int j=1;j<pieceCount - 1;++j){
        		if(i%2==0 && j%2==0){
        			int r = (int)(0.11*pieceSize);
        			int cx = x+i*pieceSize - r;
        			int cy = y+j*pieceSize - r;
        			GKit.fillOval(g2D, cx, cy, 2*r, 2*r, borderColor);
        		}
        	}
        }
        
        if(savedColor!=null){
            g2D.setColor(savedColor);
        }

    }
    
    private Piece getPieceUnder(final int x,final int y){
        for(int row=1;row<=pieceCount;++row){
            for(int col=1;col<=pieceCount;++col){
                if(pieces[row-1][col-1].hover(x, y)){
                    return pieces[row-1][col-1];
                }
            }
        }
        return null;
    }

    /*!
     * D�sactive l'attente de la souris d'un click
     */
    public void clearMouse(){
        if(adapter!=null){
        	removeMouseListener(adapter);
        }
		synchronized(clickLock){
			clickLock.notifyAll();
		}
		adapter=null;
    }

    /**
     * 
     * @return
     */
    public CellPosition getPositionOnUIEvent(){
        adapter = new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e){            	
                if(self==e.getSource()){
                	selectedPiece = getPieceUnder(e.getX(),e.getY());
                }
                if(selectedPiece!=null){
                	try{
                		fireMouseClicked(selectedPiece, CellContent.Empty);
                	}finally{
                		synchronized(clickLock){
                			clickLock.notify();
                		}
                	}
                }
            }

			@Override
			public void mouseEntered(MouseEvent e) {
                if(self==e.getSource()){
                	Piece p = getPieceUnder(e.getX(),e.getY());
                	if(p!=null){
                		fireMouseEntered(p, CellContent.Empty);
                	}
                }
			}

			@Override
			public void mouseExited(MouseEvent e) {
                if(self==e.getSource()){
                	Piece p = getPieceUnder(e.getX(),e.getY());
                	if(p!=null){
                		fireMouseExited(p, CellContent.Empty);
                	}
                }
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
        };
        
    	try{
            selectedPiece = null;
            //====== On attend le click de la souris ou un raccourci clavier
            addMouseListener(adapter);
            synchronized(clickLock){
                try {
                    clickLock.wait();
                } catch (final InterruptedException ex) {
                    Logger.getLogger(GameView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }

            //on nettoie
            return selectedPiece;
    	}finally{
    		clearMouse();
    	}
    }
    
    /**
     * Marquer des positions comme entourée
     * @param area L'ensemble de pion à entourer
     * @param wColor La couleur du bord
     */
    public void surroundArea(Iterable<CellPosition> area,Color wColor){ 
        for(final CellPosition p : area){
        	surrounded.add(p);
            pieces[p.getRow()-1][p.getColumn()-1].surround((Graphics2D) getGraphics(), wColor);
        }
    }
    
    /**
     * Marquer des pions comme entouré.
     * C'est pions ne seront plus entourés au prochain redessinage du goban
     * @param wColor La couleur du bord
     * @param positions Les positions des pions à entourer
     */
    public void surroundTemporarily(Color wColor,CellPosition ... positions){    	
    	for(CellPosition p : positions){
    		tmpSurrounded.add(p);
    		tmpColors.add(wColor);
            pieces[p.getRow()-1][p.getColumn()-1].surround((Graphics2D) getGraphics(), wColor);
    	}
    }

    /**
     * Marquer des pions comme entouré.
     * C'est pions ne seront plus entourés au prochain redessinage du goban
     * @param wColor La couleur du bord
     * @param positions Les positions des pions à entourer
     */
    public void surroundTemporarily(Color wColor,Iterable<CellPosition> positions){    	
    	for(CellPosition p : positions){
    		tmpSurrounded.add(p);
    		tmpColors.add(wColor);
            pieces[p.getRow()-1][p.getColumn()-1].surround((Graphics2D) getGraphics(), wColor);
    	}
    }

    /**
     * Effacer les contour des pions entourés
     * @param p
     */
    public void clearTemporarilySurrounded(CellPosition p){    	
    	int index = tmpSurrounded.indexOf(p);
    	if(index>-1){
    		tmpSurrounded.remove(index);
    		tmpColors.remove(index);
    	}
    }
    
    /**
     * @return La couleur considérée comme noir
     */
    public Color getKuroColor(){
    	return kuroColor;
    }
    
    /**
     * 
     * @return La couleur considéré comme blanche
     */
    public Color getShiroColor(){
    	return shiroColor;
    }
    
    /**
     * 
     * @return La couleur d'arrière plan du goban
     */
    public Color getBanColor(){
    	return banBackground;    	
    }
    
    /**
     * Permet de marquer un ensemble de positions comme étant des coups jouables
     * @param hints
     */
    public void addHints(Iterable<CellPosition> hints){
    	for(CellPosition p : hints){
    		this.hintCells.add(p);
    	}
    }
    
    /**
     * Enlever le marquage de coup jouable
     */
    public void clearHints(){
    	this.hintCells.clear();
    }
    
    /**
     * Nettoyer l'affichage
     */
    public void clear(){
    	clearMouse();
    	surrounded.clear();
    	tmpSurrounded.clear();
    	tmpColors.clear();
    }
    
    public void addCellListener(CellMouseListener listener){
    	this.cellListeners.add(listener);
    }
    
    public void removeCellListener(CellMouseListener listener){
    	this.cellListeners.remove(listener);
    }
    
    public void removeAllListeners(){
    	this.clear();
    }
    
    /**
     * Fonction permettant de notifier la présence de la souris au dessus d'une pièce
     * @param p
     * @param c
     */
    public void fireMouseEntered(CellPosition p,CellContent c){
    	for(CellMouseListener l : this.cellListeners){
    		if(l!=null){
    			l.mouseEntered(makeEvent(p,c));
    		}
    	}
    }
    
    /**
     * Méthode appeler pour notifier la sortie de la souris de la zone d'un pion
     * @param p
     * @param c
     */
    public void fireMouseExited(CellPosition p,CellContent c){
    	for(CellMouseListener l : this.cellListeners){
    		if(l!=null){
    			l.mouseExited(makeEvent(p,c));
    		}
    	}
    }
    
    /**
     * Notifie le click sur pion
     * @param p
     * @param c
     */
    public void fireMouseClicked(CellPosition p,CellContent c){
    	for(CellMouseListener l : this.cellListeners){
    		if(l!=null){
    			l.mouseClicked(makeEvent(p,c));
    		}
    	}
    }
    
    private static Cell makeEvent(final CellPosition p,final CellContent c){
    	return new CellImpl(p, c);
    }
    
    private void setupBanPainter(){
    	GlossPainter gloss = new GlossPainter(new Color(1.0f, 1.0f, 1.0f, 0.2f),
    			GlossPainter.GlossPosition.TOP);

    	PinstripePainter stripes = new PinstripePainter();
    	stripes.setPaint(new Color(1.0f, 1.0f, 1.0f, 0.17f));
    	stripes.setSpacing(5.0);

    	MattePainter matte = new MattePainter(banBackground);

    	banPainter = new CompoundPainter(matte, stripes, gloss);
    	this.setBackgroundPainter(banPainter);

    }
    
    private void setupKuroPaint(){
    	 kuroPaint = new GradientPaint(75, 75, Color.black, 95, 95,
    		        Color.gray, true);
    }

    private void setupShiroPaint(){
    	 shiroPaint = new GradientPaint(75, 75, Color.white, 95, 95,
    		        Color.gray, true);
    }
    
    class Piece extends CellPositionImpl implements CellPosition{

        private Rectangle rect;

        public Piece(final int row,final int column){
            super(row,column);
        }

        /**
         *
         * @return le rayon de la piece
         */
        public int rayon(){
            return (pieceSize - pieceSizeOffset)/2;
        }

        /**
         *
         * @return L'abcisse du centre
         */
        public int getX(){
            return banX+(getColumn()-1)*pieceSize;
        }

        /**
         *
         * @return L'ordonnée du centre
         */
        public int getY(){
            return banY+(getRow()-1)*pieceSize;
        }

        /**
         *
         * @return Le rectangle qui englobe la piece
         */
        public Rectangle getRect(){
            return rect;
        }

        /**
         * La souris est elle au dessus de la piece
         * @param x L'abcisse a tester
         * @param y L'ordonnee  a tester
         * @return true si les coordonnées sont dans la zone de la piece
         */
        public boolean hover(final int x,final int y){
            return rect.contains(x,y);
        }

        /**
         * Dessiner la piece
         * @param g2D Le context graphique
         * @param color  La couleur de tracer
         */
        public void renderCircle(final Graphics2D g2D,final Object color){
            final int r = rayon();
            GKit.fillOval(g2D,getX() - r, getY() - r, 2*r, 2*r,color);
        }

        /**
         * Dessiner la piece
         * @param g2D Le context graphique
         * @param color  La couleur de tracer
         */
        public void renderSquare(final Graphics2D g2D,final Color color){
            final int r = rayon();
            GKit.fillRect(g2D,getX() - r, getY() - r, 2*r, 2*r,color);
        }
        
        /**
         * Dessiner une croix a la position de la piece
         * @param g2D
         * @param color
         */
        public void renderCross(final Graphics2D g2D,final Object color){
        	int r = rayon();
        	int x1 = getX() - r;
        	int y1 = getY() - r;
        	GKit.drawLine(g2D, x1, y1, x1+2*r, y1+2*r, borderStroke, color);
        	GKit.drawLine(g2D, x1, y1+2*r, x1+2*r, y1, borderStroke, color);
        }

        /**
         * Dessiner un triangle plein
         * @param g2D
         * @param color
         */
        public void renderTriangle(final Graphics2D g2D,final Object color){
        	int r = rayon();
        	int x1 = getX() - r;
        	int y1 = getY() - r;
        	GKit.fillTriangle(g2D, x1, y1, 2*r, 2*r, color, borderStroke);
        	
        }
        
        /**
         * Entourer la cellule de la couleur voulue
         * @param g2D
         * @param color
         */
        public void surround(final Graphics2D g2D,final Object color){
            final int r = rayon();
            GKit.drawRect(g2D, getX() - r, getY() - r, 2*r, 2*r,surroundStroke, color);
        }

        /**
         * La couleur d'arriere plan utiliser est celui du ban
         * @param g2D Le context graphique
         */
//        public void clear(final Graphics2D g2D){
//            final int x = getX();
//            final int y = getY();
//            final int r = rayon();
//
//            GKit.fillRect(g2D,x - r, y - r, 2*r, 2*r,banBackground);
//
//            if(x==banX && y==banY){ //le coin haut gauche
//                GKit.drawLine(g2D,x, y, x+r, y,borderStroke,borderColor);
//                GKit.drawLine(g2D,x, y, x,y+r,borderStroke,borderColor);
//            }
//            else if(x==(banX + banSize) && y==banY){ //le coin haut droit
//                GKit.drawLine(g2D,x-r, y, x, y,borderStroke,borderColor);
//                GKit.drawLine(g2D,x, y, x,y+r,borderStroke,borderColor);
//            }
//            else if(x==(banX+banSize) && y==(banY+banSize)){//le coin bas gauche
//                GKit.drawLine(g2D,x, y, x-r, y,borderStroke,borderColor);
//                GKit.drawLine(g2D,x, y-r, x,y,borderStroke,borderColor);
//            }
//            else if(x==banX && y==(banY+banSize)){ //le coin bas droite
//                GKit.drawLine(g2D,x, y-r, x, y,borderStroke,borderColor);
//                GKit.drawLine(g2D,x+r, y, x,y,borderStroke,borderColor);
//            }
//            else if(x==banX){ //le bord gauche
//                g2D.drawLine(x, y, x+r, y);
//                g2D.drawLine(x, y-r, x,y+r);
//            }
//            else if(y==banY){//le bord en haut
//                g2D.drawLine(x-r, y, x+r, y);
//                g2D.drawLine(x, y, x,y+r);
//            }else if(x==(banX+banSize)){//le bord droit		        		
//                g2D.drawLine(x-r, y, x, y);
//                g2D.drawLine(x, y-r, x,y+r);
//            }else if(y==(banY+banSize)){//le bord d'en bas
//                g2D.drawLine(x-r, y, x+r, y);
//                g2D.drawLine(x, y-r, x,y);
//            }else{ //a l'interieur
//                g2D.drawLine(x-r, y, x+r, y);
//                g2D.drawLine(x, y-r, x,y+r);
//            }
//        }

        public void invalidate(){
            final int r = rayon();
            rect = new Rectangle(getX() - r,getY()-r,2*r,2*r);
        }

    }
    
}
