package go;

import java.util.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedHashSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * Class Goban.
 * @author Fred Dumont
 *
 */
public class Goban extends JPanel
                   implements Constants,
                              MouseListener,
                              MouseMotionListener {

    /**
     * Variable serialVersionUID.
     */
     private static final long serialVersionUID = 1L;

    /**
     * Variable game.
     */
     private transient Game game;

    /**
     * Variable myGoban.
     */
     private transient Main myGoban;

     /**
     * Variable size.
     */
     public transient int size;

    /**
     * Variable gap.
     */
     private transient int gap;

    /**
     * Variable border.
     */
     public transient int border = 20;

    /**
     * Variable stoneSize.
     */
     private transient int stoneSize;

    /**
     * Variable offscreen.
     */
     private transient Image offscreen;

    /**
     * Variable color.
     */
     public transient int color;

    /**
     * Variable gOff.
     */
     private transient Graphics gOff;

    /**
     * Variable oldWidth.
     */
     private transient int oldWidth = -1;

     /**
     * Variable oldHeight.
     */
     private transient int oldHeight = -1;

     /**
     * Variable oldSize.
     */
     private transient int oldSize = -1;

    /**
     * Variable globalState.
     */
     public transient State globalState;

    /**
     * Variable lastX.
     */
     private transient int lastX;

    /**
     * Variable lastY.
     */
     private transient int lastY;

    /**
     * Variable catchNumberInit.
     */
     private transient int catchNumberInit;

    /**
     * Variable catchNumberBlack.
     */
     private transient int catchNumberBlack;

    /**
     * Variable catchNumberWhite.
     */
     private transient int catchNumberWhite;

     /**
      * Constructor.
      */
     public Goban() {
          //Empty constructor
          super();
     }

     /**
      * init.
      * @param paramAtariGo the main
      * @param paramGame    the game
      * @param paramSize    the size
      */
     public final void init(final Main paramAtariGo,
                              final Game paramGame,
                              final int paramSize) {

          myGoban = paramAtariGo;
          game   = paramGame;
          size   = paramSize;
          color = -1;
          globalState = new State(myGoban);

     }


     /**
      * reset.
      */
     public final void reset() {

          color = -1;
          globalState = new State(myGoban);
          System.out.println(globalState);
          catchNumberInit = myGoban.getCatchNumber();
          catchNumberBlack = catchNumberInit;
          catchNumberWhite = catchNumberInit;
     }


     /**
      * Draw Goban.
      * @param graphic the graphic
      */
     public final void paintComponent(final Graphics graphic) {

          //Resize all elements of the window if needed
          reSize();

          //Draw the background image of the panel
          Main.drawBackground(gOff, this.getSize(), this);

          //Draw lines
          drawLines(gOff);

        //Draw all hoshis
          drawHoshis(gOff);

          //Draw all stones
          drawStones(gOff);

          //Draw the "ghost" stone
          drawFictiveStone(gOff);

          //Draw all elements
          graphic.drawImage(offscreen, 0, 0, this);
          requestFocus();
     }

     /**
      *  Resize all elements of the window if needed.
      */
     public final void reSize() {

          int width0, width1, height0, height1;
          width0 = this.getSize().width;
          width1 = width0  - (2 * border);
          height0 = this.getSize().height;
          height1 = height0 - (2 * border);

          // No scaling
          if ((width1 == oldWidth) && (height1 == oldHeight)
                                     && (oldSize == size)) {
               return;
          }

          // Calculating line separation
          if (width1 * RGGOBAN > height1 * RPGOBAN) {
               gap = (height1 * RPGOBAN) / (size * RGGOBAN);
          } else {
               gap =  width1 / size;
          }

          // Calculating stone size
          stoneSize = (gap * RPGOBAN) / RGGOBAN;
          myGoban.ChangeStoneSize(stoneSize);

          offscreen = createImage(width0, height0);
          gOff = offscreen.getGraphics();
          oldWidth  = width1;
          oldHeight = height1;
          oldSize   = size;
     }

     /**
      * Draw all Hoshis.
      * @param graphic graphic
      */
     public final void drawHoshis(final Graphics graphic) {

          if (size == 19) {
               for (int i = 3; i < 19; i = i + 6) {
                    for (int j = 3; j < 19; j = j + 6) {
                         drawHoshi(graphic, i, j);
                    }
               }
          } else {
               if (size >= 10 && size <= 19) {
                    drawHoshi(graphic,        3,        3);
                    drawHoshi(graphic,        3, size - 4);
                    drawHoshi(graphic, size - 4,        3);
                    drawHoshi(graphic, size - 4, size - 4);
               } else {
                    if (size >= 7 && size <= 9) {
                         drawHoshi(graphic,          2,        2);
                         drawHoshi(graphic,        2, size - 3);
                         drawHoshi(graphic, size - 3,        2);
                         drawHoshi(graphic, size - 3, size - 3);
                    }
               }
          }
     }



     /**
      * Draw Hoshi.
      * @param graphic graphic
      * @param coordx  coordx
      * @param coordy  coordy
      */
     public final void drawHoshi(final Graphics graphic,
                                   final int coordx,
                                   final int coordy) {
          graphic.fillOval(posx(coordx) - 2, posy(coordy) - 2, 5, 5);
     }


     /**
      * Actions when clicking the mouse.
      * @param event  event
      * @param coordx coordx
      * @param coordy coordy
      * @return true
      */
     public final boolean mouseDown(final MouseEvent event,
                                      final int coordx,
                                      final int coordy) {

          int positionx, positiony;

          // Calculating position
          positionx = (coordx - border) / gap;
          positiony = ((coordy - border) / gap) - 1;

          // Associated action
          actionMove(positionx, positiony);

          System.out.println(positionx + "   :   "
                             + positiony + " " + color);

          return true;
     }

     /**
      * Actions when flying over the window.
      * @param event   event
      * @param coordx  coordx
      * @param coordy  coordy
      * @return true
      */
     public final boolean mouseOver(final MouseEvent event,
                                      final int coordx,
                                      final int coordy) {

          int positionx, positiony;

          // Calculating position
          positionx = (coordx - border) / gap;
          positiony = ((coordy - border) / gap) - 1;

          // Associated action
          fictiveActionMove(positionx, positiony);

          return true;
     }


     /**
      * Move action.
      * @param coordx coordx
      * @param coordy coordy
      */
     public final void actionMove(final int coordx,
                                    final int coordy) {

          if (myGoban.getGameMode() == 3) {

               // Verification valid blow
               if ((coordx < size) && (coordy < size)
               && (coordx >= 0) && (coordy >= 0)) {

                    if (!myGoban.lock
                    && globalState.isLegalMove(coordy, coordx, color)
                    && game.move[coordx][coordy] == null) {


                         if(catchNumberWhite > 0 && catchNumberBlack > 0) {

                              lastY = coordy; lastX = coordx;
                              game.newStoneMove(coordx, coordy, color);
                              game.fictiveEnd();
                              LinkedHashSet<Coord> toRemove = new LinkedHashSet<Coord>();
                              toRemove.iterator();

                              try {
                                   toRemove = globalState.applyMove(coordy, coordx, (byte)color);
                              } catch (ExceptionGlobal e) {
                                   e.writeMessage();
                              }

                              System.out.println(globalState);

                              if (toRemove.size() > 0) {
                                   final Iterator<Coord> iterator = toRemove.iterator();
                                   while (iterator.hasNext()) {
                                        final Coord coordc = iterator.next();
                                        game.move[coordc.coordY][coordc.coordX] = null;
                                   }
                                   if (color == BLACK) { catchNumberBlack -= toRemove.size(); }
                                   if (color == WHITE) { catchNumberWhite -= toRemove.size(); }

                                   if(catchNumberWhite <= 0 || catchNumberBlack <= 0) {
                                        if (color == -1) {
                                             String player = "Noir";
                                             String winner = "Le joueur " + player + " gagne la partie";
                                             JOptionPane.showMessageDialog(this, winner,
                                                       "Felicitations",
                                                       JOptionPane.WARNING_MESSAGE);
                                             myGoban.lock = true;
                                             myGoban.statusMessage.setText(winner);

                                        }
                                        if (color == 1) {
                                             String player = "Blanc";
                                             String winner = "Le joueur " + player + " gagne la partie";
                                             JOptionPane.showMessageDialog(this, winner,
                                                       "Felicitations",
                                                       JOptionPane.WARNING_MESSAGE);
                                             myGoban.lock = true;
                                             myGoban.statusMessage.setText(winner);
                                        }
                                   }
                              }


                              // Initializing a thread for communication with the interface
                              final Thread thread = new Thread() {
                                   public void run() {
                                        myGoban.lock = true;
                                        // Instantiation and initiation of treatment
                                        long time = System.currentTimeMillis();
                                        //goban.generate(50,3);
                                        Move move = globalState.computeNext(lastY, lastX, color);
                                        long secondTime = System.currentTimeMillis();

                                        System.out.println("Temps :");
                                        System.out.println((float) (secondTime - time) / 1000);
                                        System.out.println();

                                        myGoban.lock = false;
                                        actionMove(move.y, move.x);
                                   }
                              };

                              color = color == 1 ? -1 : 1;
                              repaint();

                              if (color == WHITE && catchNumberBlack >= 0 && catchNumberWhite >= 0)
                              {
                                   thread.start();
                              }
                         }
                    }
               }
          } else if (myGoban.getGameMode() == 2) {  //Ordinateur vs Ordinateur


               if(catchNumberWhite > 0 && catchNumberBlack > 0) {

                    game.newStoneMove(coordx, coordy, color);
                    LinkedHashSet<Coord> toRemove = new LinkedHashSet<Coord>();
                    toRemove.iterator();

                    try {
                         toRemove = globalState.applyMove(coordy, coordx, (byte) color);
                    } catch (ExceptionGlobal e) {
                         e.writeMessage();
                    }

                    System.out.println(globalState);

                    if (toRemove.size() > 0) {
                         final Iterator<Coord> iterator = toRemove.iterator();
                         while (iterator.hasNext()) {
                              final Coord coordc = iterator.next();
                              game.move[coordc.coordY][coordc.coordX] = null;
                         }
                         if (color == BLACK) { catchNumberBlack -= toRemove.size(); }
                         if (color == WHITE) { catchNumberWhite -= toRemove.size(); }
                    }


                    // Initializing a thread for communication with the interface
                    final Thread thread = new Thread() {
                         public void run() {
                              myGoban.lock = true;
                              // Instantiation and initiation of treatment
                              long time = System.currentTimeMillis();
                              //goban.generate(50,3);
                              Move move = globalState.computeNext(lastY, lastX, color);
                              long secondTime = System.currentTimeMillis();

                              System.out.println("Temps :");
                              System.out.println((float) (secondTime - time) / 1000);
                              System.out.println();

                              actionMove(move.y, move.x);
                         }
                    };

                    color = color == 1 ? -1 : 1;
                    thread.start();
                    repaint();
               } else {

                    if (color == -1){
                         String player = "Blanc";
                         String winner = "Le joueur " + player + " gagne la partie";
                         JOptionPane.showMessageDialog(this, winner,
                                   "Felicitations",
                                   JOptionPane.WARNING_MESSAGE);
                         myGoban.lock = true;
                         myGoban.statusMessage.setText(winner);

                    }
                    if (color == 1) {
                         String player = "Noir";
                         String winner = "Le joueur " + player + " gagne la partie";
                         JOptionPane.showMessageDialog(this, winner,
                                   "Felicitations",
                                   JOptionPane.WARNING_MESSAGE);
                         myGoban.lock = true;
                         myGoban.statusMessage.setText(winner);
                    }
               }
          } else if (myGoban.getGameMode() == 1) { //Joueur vs Joueur


               if(catchNumberWhite > 0 && catchNumberBlack > 0) {

                    // Verification valid blow
                    if ((coordx < size) && (coordy < size) && (coordx >= 0) && (coordy >= 0)) {
                         if (!myGoban.lock && globalState.isLegalMove(coordy, coordx, color) && game.move[coordx][coordy] == null) {

                              lastY = coordy; lastX = coordx;
                              game.newStoneMove( coordx, coordy, color);
                              game.fictiveEnd();
                              LinkedHashSet<Coord> to_remove = new LinkedHashSet<Coord>();
                              to_remove.iterator();


                              try {
                                   to_remove = globalState.applyMove(coordy, coordx, (byte) color);
                              } catch (ExceptionGlobal e) {
                                   e.writeMessage();
                              }

                              System.out.println(globalState);

                              if (to_remove.size() > 0) {
                                   final Iterator<Coord> iterator = to_remove.iterator();
                                   while (iterator.hasNext()) {
                                        final Coord coordc = iterator.next();
                                        game.move[coordc.coordY][coordc.coordX] = null;
                                   }
                                   if (color == BLACK) { catchNumberBlack -= to_remove.size(); }
                                   if (color == WHITE) { catchNumberWhite -= to_remove.size(); }

                                   System.out.println(to_remove.size());

                                   if (catchNumberBlack <= 0 || catchNumberWhite <= 0) {
                                        if (color == -1) {
                                             String player = "Noir";
                                             String winner = "Le joueur " + player + " gagne la partie";
                                             JOptionPane.showMessageDialog(this, winner,
                                                       "Felicitations",
                                                       JOptionPane.WARNING_MESSAGE);
                                             myGoban.lock = true;
                                             myGoban.statusMessage.setText(winner);

                                        }
                                        if (color == 1) {
                                             String player = "Blanc";
                                             String winner = "Le joueur " + player + " gagne la partie";
                                             JOptionPane.showMessageDialog(this, winner,
                                                       "Felicitations",
                                                       JOptionPane.WARNING_MESSAGE);
                                             myGoban.lock = true;
                                             myGoban.statusMessage.setText(winner);
                                        }
                                   }
                              }

                              color = color == 1 ? -1 : 1;
                              repaint();
                         }
                    }
               }
          } else if (myGoban.getGameMode() == 4) { //Ordinateur vs Joueur


               // Verification valid blow
               if ((coordx < size) && (coordy < size) && (coordx >= 0) && (coordy >= 0)) {
                    if (!myGoban.lock && globalState.isLegalMove(coordy, coordx, color) && game.move[coordx][coordy] == null) {

                         if(catchNumberWhite > 0 && catchNumberBlack > 0) {
                              
                              LinkedHashSet<Coord> toRemove = new LinkedHashSet<Coord>();
                              toRemove.iterator();

                              try {
                                   toRemove = globalState.applyMove(coordy, coordx, (byte) color);
                              } catch (ExceptionGlobal e) {
                                   e.writeMessage();
                              }

                              System.out.println(globalState);

                              if (toRemove.size() > 0) {
                                   final Iterator<Coord> iterator = toRemove.iterator();
                                   while (iterator.hasNext()) {
                                        final Coord coordc = iterator.next();
                                        game.move[coordc.coordY][coordc.coordX] = null;
                                   }
                                   if (color == BLACK) { catchNumberBlack -= toRemove.size(); }
                                   if (color == WHITE) { catchNumberWhite -= toRemove.size(); }

                                   if (catchNumberBlack <= 0 || catchNumberWhite <= 0) {
                                        if (color == -1){
                                             String player = "Noir";
                                             String winner = "Le joueur " + player + " gagne la partie";
                                             JOptionPane.showMessageDialog(this, winner,
                                                       "Felicitations",
                                                       JOptionPane.WARNING_MESSAGE);
                                             myGoban.lock = true;
                                             myGoban.statusMessage.setText(winner);

                                        }
                                        if (color == 1) {
                                             String player = "Blanc";
                                             String winner = "Le joueur " + player + " gagne la partie";
                                             JOptionPane.showMessageDialog(this, winner,
                                                       "Felicitations",
                                                       JOptionPane.WARNING_MESSAGE);
                                             myGoban.lock = true;
                                             myGoban.statusMessage.setText(winner);
                                        }
                                   }

                              }


                              // Initializing a thread for communication with the interface
                              final Thread thread = new Thread() {
                                   public void run() {
                                        myGoban.lock = true;
                                        // Instantiation and initiation of treatment
                                        long time = System.currentTimeMillis();
                                        //goban.generate(50,3);
                                        Move move = globalState.computeNext(lastY, lastX, color);
                                        long secondTime = System.currentTimeMillis();

                                        System.out.println("Temps :");
                                        System.out.println((float)(secondTime - time) / 1000);
                                        System.out.println();
                                        myGoban.lock = false;
                                        actionMove(move.y, move.x);
                                   }
                              };


                              lastY = coordy; lastX = coordx;
                              game.newStoneMove( coordx, coordy, color);
                              game.fictiveEnd();


                              color = color == 1 ? -1 : 1;
                              repaint();

                              if (color == BLACK && catchNumberBlack >= 0 && catchNumberWhite >= 0)
                              {
                                   thread.start();
                              }
                         } else {

                              if (color == -1){
                                   String player = "Blanc";
                                   String winner = "Le joueur " + player + " gagne la partie";
                                   JOptionPane.showMessageDialog(this, winner,
                                             "Felicitations",
                                             JOptionPane.WARNING_MESSAGE);
                                   myGoban.lock = true;
                                   myGoban.statusMessage.setText(winner);

                              }
                              if (color == 1) {
                                   String player = "Noir";
                                   String winner = "Le joueur " + player + " gagne la partie";
                                   JOptionPane.showMessageDialog(this, winner,
                                             "Felicitations",
                                             JOptionPane.WARNING_MESSAGE);
                                   myGoban.lock = true;
                                   myGoban.statusMessage.setText(winner);
                              }
                         }
                    }
               }
          }
     }


     /**
      * Move action for the "ghost" stone.
      * @param coordx coordx
      * @param coordy coordy
      */
     public final void fictiveActionMove(final int coordx,
                                            final int coordy) {

          // Verification valid blow
          if ((coordx < size) && (coordy < size) && (coordx >= 0) && (coordy >= 0)
                    && (!myGoban.lock && globalState.isLegalMove(coordy, coordx, color) && game.move[coordx][coordy] == null)) {
               game.newFictiveStoneMove(coordx, coordy, color);
               repaint();
          }
     }

     /**
      * Draw all stones.
      * @param graphic graphic
      */
     public final void drawStones(final Graphics graphic) {

          Movement moveI;

          for (int i = 0; i < GOBAN_SIZE; i++) {
               for (int j = 0; j < GOBAN_SIZE; j++) {
                    if (game.move[i][j] != null) {
                         moveI = game.move[i][j];
                         drawStone(graphic, moveI, i , j, true);
                    }
               }
          }
     }

     /**
      * Draw "ghost" stone.
      * @param graphic graphic
      */
     public final void drawFictiveStone(final Graphics graphic) {

          Movement moveI;

          for (int i = 0; i < GOBAN_SIZE; i++) {
               for (int j = 0; j < GOBAN_SIZE; j++) {
                    if (game.fictiveMove[i][j] != null) {
                         moveI = game.fictiveMove[i][j];
                         drawStone(graphic, moveI, i , j, false);
                    }
               }
          }
     }

     /**
      * Draw a stone.
      * @param graphic graphic
      * @param move    move
      * @param coordx  coordx
      * @param coordy  coordy
      * @param real    real
      */
     public final void drawStone(final Graphics graphic,
                                   final Movement move,
                                   final int coordx,
                                   final int coordy,
                                   final boolean real) {

          Main.drawStoneImage(graphic,
                    move.getColor(),
                    posx(coordx) - stoneSize / 2,
                    posy(coordy) - stoneSize / 2,
                    stoneSize,
                    this,
                    real);
     }


     /**
      * Draw lines .
      * @param graphic graphic
      */
     public final void drawLines(final Graphics graphic) {

          graphic.setColor(Color.black);
          for (int i = 0; i < size; i++) {
               graphic.drawLine(posx(i), posy(0),
                                  posx(i), posy(size - 1));
               graphic.drawLine(posx(0), posy(i),
                                  posx(size - 1), posy(i));
          }
     }

     /**
      * Get X position.
      * @param coordx coordx
      * @return position
      */
     public final int posx(final int coordx) {

          final int position = gap * coordx + gap / 2 + border;
          return position;
     }

     /**
      * Get Y position.
      * @param coordy coordy
      * @return position
      */
     public final int posy(final int coordy) {

          final int position = ((gap * coordy + gap / 2) * RGGOBAN)
                               / RPGOBAN  + border;
          return position;
     }


     public void mouseEntered(final MouseEvent event) {
          //TODO mouseEntered method
     }


     public void mouseExited(final MouseEvent event) {
          //TODO mouseExited method
     }


     public void mousePressed(final MouseEvent event) {
          //TODO mousePressed method
     }


     public void mouseReleased(final MouseEvent event) {
          //TODO mouseReleased method
     }


     /**
      * Actions when clicking the mouse.
      * @param event  event
      */
     public final void mouseClicked(final MouseEvent event) {
          myGoban.setFocusableWindowState(true);
          final int coordx = event.getX();
          final int coordy = event.getY();
          mouseDown(event, coordx, coordy);
     }


     public void mouseDragged(final MouseEvent event) {
          //TODO mouseDragged Method
     }

     /**
      * Actions when hovering the mouse.
      * @param event event
      */

     public final void mouseMoved(final MouseEvent event) {
          final int coordx = event.getX();
          final int coordy = event.getY();
          game.fictiveEnd();
          mouseOver(event, coordx, coordy);

     }

     /**
      * If the computer starts the game.
      */
     public final void go() {

          //Random for the first position
          Random rand = new Random();

          //x between 2 and 7
          lastX = rand.nextInt(10) - 1;

          while (lastX < 2 || lastX > 7) {
               lastX = rand.nextInt(10) - 1;
          }

          //y between 2 and 7
          lastY = rand.nextInt(10) - 1;

          while (lastY < 2 || lastY > 7) {
               lastY = rand.nextInt(10) - 1;
          }
          //First stone for the cpu
          actionMove(lastX, lastY);
     }
}
