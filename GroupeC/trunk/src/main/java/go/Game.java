package go;


/**
 * Classe Game.
 * @author Fred Dumont
 *
 */
public class Game implements Constants {

      /**
     * Variable size.
     */
     private int size;

      /**
     * table move.
     */
     public Movement[][] move;

      /**
     * table fictiveMove.
     */
     public Movement [][] fictiveMove;

      /**
     * Variable node.
     */
     private int node = 0;

      /**
     * Variable fictiveNode.
     */
     private int fictiveNode = 0;


     /**
      * initialization.
      * @param param_atariGo
      * @param param_game_size
      */
     public final void init() {

          node = 0;
          setFictiveNode(0);
          move = new Movement[GOBAN_SIZE][GOBAN_SIZE];
          fictiveMove = new Movement[GOBAN_SIZE][GOBAN_SIZE];
     }

     /**
      * empty the table.
      */
     public final void stop() {

          this.node = 0;
          System.out.println("stop");
          for (int i = 0; i < GOBAN_SIZE; i++) {
               for (int j = 0; j < GOBAN_SIZE; j++) {
                    move[i][j] = null;
               }
          }
     }

     /**
      * empty the fictive table.
      */
     public final void fictiveEnd() {

          this.setFictiveNode(0);
          for (int i = 0; i < GOBAN_SIZE; i++) {
               for (int j = 0; j < GOBAN_SIZE; j++) {
                    fictiveMove[i][j] = null;
               }
          }
     }

     /**
      * New Stone.
      * @param coordx coordx
      * @param coordy coordy
      * @param color color
      */
     public final void newStoneMove(final int coordx,
                                      final int coordy,
                                      final int color) {

          move[coordx][coordy]   = new Movement(color);
     }

     /**
      * New fictive stone.
      * @param coordx coordx
      * @param coordy coordy
      * @param color color
      */
     public final void newFictiveStoneMove(final int coordx,
                                             final int coordy,
                                             final int color) {

          fictiveMove[coordx][coordy]   = new Movement(color);
     }

     /**
      *   getNode.
      *   @return node
      */
     public final int getNode() {
          return node;
     }

     /**
      *   setNode.
      *   @param newnode a node
      */
     public final void setNode(final int newnode) {
          this.node = newnode;
     }

     /**
      *   getSize().
      *   @return size
      */
     public final int getSize() {
          return size;
     }

     /**
      *   setSize().
      *   @param newsize the new size
      */
     public final void setSize(final int newsize) {
          this.size = newsize;
     }

     /**
      * setFictiveNode.
      * @param newfictiveNode the new node
      */
     public final void setFictiveNode(final int newfictiveNode) {
          this.fictiveNode = newfictiveNode;
     }

     /**
      * getFictiveNode.
      * @return fictiveNode
      */
     public final int getFictiveNode() {
          return fictiveNode;
     }

     /**
      * getMove.
      * @return move
      */
     public final Movement[][] getMove() {
          return move;
     }

     /**
      * setMove.
      * @param newmove add a move
      */
     public final void setMove(final Movement[][] newmove) {
          this.move = newmove;
     }

     /**
      * getFictiveMove.
      * @return getFictiveMove
      */
     public final Movement[][] getFictiveMove() {
          return fictiveMove;
     }

     /**
      * setFictiveMove.
      * @param newfictiveMove the new fictive move
      */
     public final void setFictiveMove(final Movement[][] newfictiveMove) {
          this.fictiveMove = newfictiveMove;
     }

} //End class
