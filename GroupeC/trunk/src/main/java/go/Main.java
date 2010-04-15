package go;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.net.URI;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;



/**
 * Class Main
 * @author Fred Dumont
 *
 */
public class Main extends JFrame implements Constants, ActionListener {

     /**
      * Serial ID
      */
     private static final long serialVersionUID = 1L;

     //Menu
     private JMenuBar jMenuBar1;
     private JMenu jMenu6;
     private JMenuItem jMenuItem7;
     private JMenuItem jMenuItem6;
     private JMenuItem jMenuItem5;
     private JMenuItem jMenuItem4;
     private JMenuItem jMenuItem3;
     private JMenuItem jMenuItem2;
     private JMenuItem jMenuItem1;
     private JMenu jMenu2;
     private JMenu jMenu1;
     public JLabel statusMessage;
     private JProgressBar jProgressBar1;
     private Button stopButton;


     // Image for the Goban
     static protected Image goban_image;

     // Table Image for stones and ghost
     static protected Image stone []   = new Image [2];
     static protected Image stone_ghost [] = new Image[1];

     // Goban and Game
     protected Goban  goban;
     private Game game;

     // Progress Bar value
     private int progressValue;

     public boolean lock = true;

     public int catchNumber;
     
     public int GameMode = 1;

     /**
      * Main
      * @param args
      */
     public static void main(String[] args) {

          //Interface Choice
          try {
               //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
               //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
               //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
          } catch (ClassNotFoundException e) {
               e.printStackTrace();
          } catch (InstantiationException e) {
               e.printStackTrace();
          } catch (IllegalAccessException e) {
               e.printStackTrace();
          } catch (UnsupportedLookAndFeelException e) {
               e.printStackTrace();
          }

          SwingUtilities.invokeLater(new Runnable() {
               public void run() {
                    Main inst = new Main();
                    inst.setTitle("AtariGo");
                    inst.setLocationRelativeTo(null);
                    inst.setVisible(true);
                    inst.setResizable(false);
               }
          });
     }

     /**
      * Constructor
      */
     public Main() {
          super();
          initImage();
          initGUI();
          progressValue = 0;
     }

     /**
      * Initialisation method
      */
     private void initGUI() {
          try {
               setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
               {
                    initComposant();
                    addMouseListener(goban);
                    addMouseMotionListener(goban);

               }
               {
                    jMenuBar1 = new JMenuBar();
                    setJMenuBar(jMenuBar1);
                    {
                         jMenu1 = new JMenu();
                         jMenuBar1.add(jMenu1);
                         jMenu1.setText("Jeu");
                         {
                              jMenu2 = new JMenu();
                              jMenu1.add(jMenu2);
                              jMenu2.setText("Nouveau");
                              {
                                   jMenuItem2 = new JMenuItem();
                                   jMenu2.add(jMenuItem2);
                                   jMenuItem2.setText("Joueur vs Ordinateur");
                                   jMenuItem2.addActionListener(this);
                              }
                              {
                                   jMenuItem7 = new JMenuItem();
                                   jMenu2.add(jMenuItem7);
                                   jMenuItem7.setText("Joueur vs Joueur");
                                   jMenuItem7.addActionListener(this);
                              }
                              {
                                   jMenuItem3 = new JMenuItem();
                                   jMenu2.add(jMenuItem3);
                                   jMenuItem3.setText("Ordinateur vs Joueur");
                                   jMenuItem3.addActionListener(this);
                              }
                              {
                                   jMenuItem6 = new JMenuItem();
                                   jMenu2.add(jMenuItem6);
                                   jMenuItem6.setText("Ordinateur vs Ordinateur");
                                   jMenuItem6.addActionListener(this);
                              }
                         }
                         {
                              jMenu1.add(new JSeparator());
                              jMenuItem1 = new JMenuItem();
                              jMenu1.add(jMenuItem1);
                              jMenuItem1.setText("Quitter");
                              jMenuItem1.addActionListener(this);
                         }
                    }

                    {
                         jMenu6 = new JMenu();
                         jMenuBar1.add(jMenu6);
                         jMenu6.setText("?");
                         {
                              jMenuItem4 = new JMenuItem();
                              jMenu6.add(jMenuItem4);
                              jMenuItem4.setText("Règles du Jeu");
                              jMenuItem4.addActionListener(this);
                              jMenu6.add(new JSeparator());
                              jMenuItem5 = new JMenuItem();
                              jMenu6.add(jMenuItem5);
                              jMenuItem5.setText("A Propos De ...");
                              jMenuItem5.addActionListener(this);
                         }
                    }
               }
               pack();
               setSize(500, 600);
          } catch (Exception e) {
               e.printStackTrace();
          }
     }

     /**
      *  Initialization of images
      *    - goban background image
      *    - black stone
      *    - white stone
      *    - ghost stone
      */
     protected void initImage()
     {
          goban_image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/gob.jpg"));
          stone[BLACK_IMAGE] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/black.png"));
          stone[WHITE_IMAGE] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/white.png"));
          stone_ghost [0]= Toolkit.getDefaultToolkit().getImage(getClass().getResource("/ghost.png"));

          // Using MediaTracker to image management
          MediaTracker imageTracker = new MediaTracker(this) ;

          try {
               imageTracker.waitForAll();
          }
          catch ( InterruptedException e ) { 
               System.out.println(e);
          }
     }


     /**
      *  Initialization of Goban JPanel, Game,
      *  Button, Progress Bar and Status Bar
      *  
      */
     protected void initComposant()
     {
          game  = new Game();
          game.init();

          goban = new Goban();
          goban.init(this, game, GOBAN_SIZE);
          goban.setPreferredSize(new java.awt.Dimension(500, 508));
          getContentPane().add(goban, BorderLayout.NORTH);

          //Add button
          stopButton = new Button();
          //stopButton.setIcon(new ImageIcon("src/resources/stop.jpg"));
          stopButton.setPreferredSize(new java.awt.Dimension(500, 25));
          stopButton.setBackground(Color.red);
          stopButton.setText("STOPPER LA RECHERCHE");
          stopButton.setForeground(Color.WHITE);
          stopButton.setEnabled(false);
          getContentPane().add(stopButton, BorderLayout.CENTER);

          //Add progress bar
          jProgressBar1 = new JProgressBar();
          jProgressBar1.setLayout(new FlowLayout(FlowLayout.LEFT));
          jProgressBar1.setBackground(new Color(219,217,215));
          jProgressBar1.setForeground(Color.black);
          jProgressBar1.setBorderPainted(false);
          jProgressBar1.setValue(progressValue);


          //Add status bar
          statusMessage = new JLabel();
          JPanel myStatusBar = new JPanel();
          statusMessage.setText("Initialisation du Goban");
          myStatusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
          myStatusBar.setBackground(new Color(219,217,215));
          myStatusBar.add(statusMessage);

          JSplitPane jSplitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                    myStatusBar, jProgressBar1);

          jSplitPane1.add(jProgressBar1, JSplitPane.RIGHT);
          jSplitPane1.add(myStatusBar, JSplitPane.LEFT);

          myStatusBar.setPreferredSize(new java.awt.Dimension(400, 22));
          myStatusBar.setMinimumSize(new java.awt.Dimension(400, 22));

          jProgressBar1.setPreferredSize(new java.awt.Dimension(0,22));
          jSplitPane1.setOneTouchExpandable(true);

          getContentPane().add(jSplitPane1, BorderLayout.SOUTH);
     }

     /**
      * Change size if needed
      * @param stone_size
      */
     void ChangeStoneSize( int stone_size )
     {

          MediaTracker imageTracker = new MediaTracker( this ) ;

          imageTracker.addImage( stone[BLACK_IMAGE], 0, stone_size, stone_size);
          imageTracker.addImage( stone[WHITE_IMAGE], 0, stone_size, stone_size);
          imageTracker.addImage( stone_ghost[0], 0, stone_size, stone_size);
          try { imageTracker.waitForAll(); }
          catch ( InterruptedException e ) { System.out.println(e); }
     }


     /**
      * Image for stones
      * @param g
      * @param color
      * @param x
      * @param y
      * @param size
      * @param observer
      */
     static public void drawStoneImage( Graphics g, int color, int x, int y, int size,  ImageObserver  observer, boolean real )
     {
          int color_image;  

          if (color == -1) {
               color_image = 0;
          }
          else {
               color_image = color;
          }

          if ( stone[color_image] != null && real == true) {
               if ( color == BLACK ) g.drawImage( Main.stone[BLACK_IMAGE], x, y, size, size, observer );
               if ( color == WHITE ) g.drawImage( Main.stone[WHITE_IMAGE], x, y, size, size, observer );
          }
          // If problems with images
          else {
               if ( stone[color_image] == null && real == true) {
                    // Couleur des pierres
                    if ( color == BLACK ) g.setColor( Color.black );
                    if ( color == WHITE ) g.setColor( Color.white );
                    g.fillOval( x, y, size, size );
                    g.setColor( Color.black );
               }
               //If you are in the ghost case
               else {
                    if ( stone_ghost[0] != null && real == false){
                         g.drawImage( Main.stone_ghost[0], x, y, size, size, observer );
                    }
                    //If problems with ghost image
                    else {
                         g.setColor( Color.GRAY );
                         g.fillOval( x, y, size, size );
                         g.setColor( Color.black );
                    }
               }
          }
     }


     /**
      * Draw the background
      * @param g
      * @param size
      * @param observer
      */
     static public void drawBackground( Graphics g, Dimension size, ImageObserver observer )
     {
          int x, y, w, h;

          if ( goban_image != null ){
               // Image size
               w = goban_image.getWidth(observer); 
               h = goban_image.getHeight(observer);        

               x = size.width / w  ;   
               y = size.height / h  ;

               // Lines
               for( int line=0; line <= y; line++ ){
                    // Rows
                    for( int column=0; column <= x; column++ ){
                         g.drawImage( goban_image, column*w, line*h, observer );
                    }
               }
          }
          else {
               g.setColor(Color.orange);
               g.fillRect( 0, 0, size.width-1, size.height-1 );
          }
     }

     /**
      * Menu actions
      * @param action
      */
     public void actionPerformed(ActionEvent action) {

          System.out.println(action.getActionCommand());


          //Item Nouveau -> Joueur vs Ordinateur
          if (action.getActionCommand().equals("Joueur vs Ordinateur")) {

               int reponse = JOptionPane.showConfirmDialog(this,
                         "Etes-vous sur de vouloir lancer une nouvelle partie ?",
                         "AtariGo", 
                         JOptionPane.YES_NO_OPTION);

               if (reponse == JOptionPane.YES_OPTION) {

                    Integer entier = null;
                    String reponse2;
                    String message = "Combien de prises ?";
                    reponse2 = JOptionPane.showInputDialog(this, message);

                    try {
                         entier = Integer.parseInt(reponse2);
                         if (entier > 0) {
                              //Reinitialization
                              game.stop();
                              System.out.println("Nouvelle partie Joueur vs Ordinateur");
                              statusMessage.setText("Nouvelle partie Joueur vs Ordinateur");
                              catchNumber = entier;
                              System.out.println("Nombre de prises : "+catchNumber);
                              GameMode = 3;
                              this.lock = false;
                              goban.reset();
                              repaint();
                              
                         }
                    }
                    catch (NumberFormatException e){
                         //System.out.println("oups");
                         JOptionPane.showMessageDialog(this, "Lancement d'une nouvelle partie impossible !",
                                    "Avertissement",
                                    JOptionPane.WARNING_MESSAGE);
                    }
               }
          }


          //Item Nouveau -> Ordinateur vs Joueur
          if (action.getActionCommand().equals("Ordinateur vs Joueur")) {

               int reponse = JOptionPane.showConfirmDialog(this,
                         "Etes-vous sur de vouloir lancer une nouvelle partie ?",
                         "AtariGo", 
                         JOptionPane.YES_NO_OPTION);

               if (reponse == JOptionPane.YES_OPTION) {

                    Integer entier = null;
                    String reponse2;
                    String message = "Combien de prises ?";
                    reponse2 = JOptionPane.showInputDialog(this, message);

                    try {
                         entier = Integer.parseInt(reponse2);
                         if (entier > 0) {
                              //Reinitialization
                              game.stop();
                              System.out.println("Nouvelle partie Ordinateur vs Joueur");
                              statusMessage.setText("Nouvelle partie Ordinateur vs Joueur");
                              catchNumber = entier;
                              System.out.println("Nombre de prises : "+catchNumber);
                              GameMode = 4;
                              goban.reset();
                              lock = false;
                              repaint();
                              goban.go();
                              
                         }
                    }
                    catch (NumberFormatException e){
                         //System.out.println("oups");
                         JOptionPane.showMessageDialog(this, "Lancement d'une nouvelle partie impossible !",
                                    "Avertissement",
                                    JOptionPane.WARNING_MESSAGE);
                    }
               }
          }

          //Item Nouveau -> Joueur vs Joueur
          if (action.getActionCommand().equals("Joueur vs Joueur")) {

               int reponse = JOptionPane.showConfirmDialog(this,
                         "Etes-vous sur de vouloir lancer une nouvelle partie ?",
                         "AtariGo", 
                         JOptionPane.YES_NO_OPTION);

               if (reponse == JOptionPane.YES_OPTION) {

                    Integer entier = null;
                    String reponse2;
                    String message = "Combien de prises ?";
                    reponse2 = JOptionPane.showInputDialog(this, message);

                    try {
                         entier = Integer.parseInt(reponse2);
                         if (entier > 0) {
                              //Reinitialization
                              game.stop();
                              System.out.println("Nouvelle partie Joueur vs Joueur");
                              statusMessage.setText("Nouvelle partie Joueur vs Joueur");
                              catchNumber = entier;
                              System.out.println("Nombre de prises : "+catchNumber);
                              GameMode = 1;
                              lock = false;
                              goban.reset();
                              repaint();
                              
                              
                         }
                    }
                    catch (NumberFormatException e){
                         //System.out.println("oups");
                         JOptionPane.showMessageDialog(this, "Lancement d'une nouvelle partie impossible !",
                                    "Avertissement",
                                    JOptionPane.WARNING_MESSAGE);
                    }
               }
          }

          //Item Nouveau -> Ordinateur vs Ordinateur
          if (action.getActionCommand().equals("Ordinateur vs Ordinateur")) {

               int reponse = JOptionPane.showConfirmDialog(this,
                         "Etes-vous sur de vouloir lancer une nouvelle partie ?",
                         "AtariGo", 
                         JOptionPane.YES_NO_OPTION);

               if (reponse == JOptionPane.YES_OPTION) {

                    Integer entier = null;
                    String reponse2;
                    String message = "Combien de prises ?";
                    reponse2 = JOptionPane.showInputDialog(this, message);
                    
                    try {
                         entier = Integer.parseInt(reponse2);
                         if (entier > 0) {
                              
                              //Reinitialization
                              game.stop();
                              System.out.println("Nouvelle partie Ordinateur vs Ordinateur");
                              statusMessage.setText("Nouvelle partie Ordinateur vs Ordinateur");
                              catchNumber = entier;
                              System.out.println("Nombre de prises : "+catchNumber);
                              GameMode = 2;
                              goban.reset();
                              repaint();
                              goban.go();
                              
                         }
                    }
                    catch (NumberFormatException e){
                         JOptionPane.showMessageDialog(this, "Lancement d'une nouvelle partie impossible",
                                    "Avertissement",
                                    JOptionPane.WARNING_MESSAGE);
                    }
               }
          }


          //Item Quitter
          if (action.getActionCommand().equals("Quitter")) {
               int reponse = JOptionPane.showConfirmDialog(this,
                         "Etes-vous sur de vouloir quitter l'application ?",
                         "AtariGo", 
                         JOptionPane.YES_NO_OPTION);

               if (reponse == JOptionPane.YES_OPTION) {
                    dispose();
               } 
          }


          //Item Regles du Jeu
          if (action.getActionCommand().equals("Règles du Jeu")) {
               try {
                    if (Desktop.isDesktopSupported()){
                         Desktop desktop = Desktop.getDesktop();
                         if (desktop.isSupported(Desktop.Action.BROWSE)){
                              desktop.browse(new URI("http://fr.wikibooks.org/wiki/Jeu_de_go/Règles_de_base"));
                         }
                    }
               } catch (Exception e) {
                    e.printStackTrace();
               }
          }

          //Item A Propos De ...
          if (action.getActionCommand().equals("A Propos De ...")) {

               this.setFocusableWindowState(false);
               JFrame about = new JFrame();
               about.setTitle("A Propos De ...");
               about.setLocationRelativeTo(null);
               about.setVisible(true);
               about.setSize(180, 200);
               about.setResizable(false);

               JPanel aboutPanel = new JPanel();
               aboutPanel.setBackground(Color.white);

               FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
               aboutPanel.setLayout(fl);

               JLabel titre = new JLabel("AtariGo v1.0");
               aboutPanel.add(titre);

               JTextArea area = new JTextArea();
               area.setBounds(14,14,180,200);
               area.setEditable(false);
               area.setBackground(Color.white);

               area.append('\n'+"Produit par :"+'\n');
               area.append("Dumonceaux Frédéric"+'\n');
               area.append("Dumont Frédéric"+'\n');

               aboutPanel.add(area);         
               about.add(aboutPanel);
               setDefaultCloseOperation(DISPOSE_ON_CLOSE);
               setFocusableWindowState(false);
          }


     }


     /**
      * Status bar message
      * @param x
      * @param y
      */
     public void setStatusMessage(int x, int y){
          statusMessage.setText("Coup joué en "+y+" : "+x);
          System.out.println("status bar a jour");
          progressValue+=10;
          jProgressBar1.setValue(progressValue);
     }


     /**
      * Reinitialization of progress bar
      */
     public void resetProgress(){
          System.out.println("status bar a jour");
          progressValue = 0;
          jProgressBar1.setValue(progressValue);
     }


     /**
      * ModProgress
      * @param val
      */
     public void modProgress(int val) {
          progressValue += val;
          jProgressBar1.setValue(progressValue);
          stopButton.setEnabled(true);

     }

     /**
      * Generate
      * @param nb_stones
      * @param depth
      */
     public void generate(int nb_stones, int depth) {
          Random rand = new Random();

          int i, j, color = BLACK;

          State s = new State(this);

          System.out.println(nb_stones+" pierres et profondeur "+depth);

          try {

               // place nb_stone on the goban
               i = 0;
               byte x = 0, y = 0;
               while (i < nb_stones){
                    x = (byte)rand.nextInt(GOBAN_SIZE);
                    y = (byte)rand.nextInt(GOBAN_SIZE);

                    if (s.isLegalMove(x, y, color) && !s.isTakingStones(x, y, color)){
                         ++i;
                         s.applyMove(x, y, (byte)color);
                         color = color == BLACK ? WHITE : BLACK;
                    }
               }

               int number = GOBAN_SIZE * GOBAN_SIZE - i;
               int total = number;

               j = 0;
               while (++j <= depth){
                    System.out.println(number +" => "+ total);

                    if (number % 2 == 0)
                         System.out.println("WHITE");
                    else
                         System.out.println("BLACK");


                    // simulates all necessary copies and treatment (ratings)
                    for (int k=1, inc = 0; k <= total; ++k, ++inc){     
                         new State(s);

                         if (number % 2 == 0){
                              //s.searchConnectionsAndFractions(WHITE);
                              s.groupsWithRemainingLiberties(WHITE,4);
                              //s.searchEyes(WHITE);
                         } else {
                              //s.searchConnectionsAndFractions(BLACK);
                              s.groupsWithRemainingLiberties(BLACK,4);
                              //s.searchEyes(BLACK);
                         }

                         if (inc == total / 100)
                         {
                              inc = 0;
                              modProgress(1);
                         }
                    }

                    // end
                    resetProgress();
                    --number;
                    total *= number;
                    stopButton.setEnabled(false);
               }     

               System.out.println(s);
          }
          catch (ExceptionGlobal e) {
               e.writeMessage();
          }
     }

     public int getGameMode() {
          return GameMode;
     }

     public int getCatchNumber() {
          return catchNumber;
     }

     public void setGameMode(int gameMode) {
          GameMode = gameMode;
     }
}
