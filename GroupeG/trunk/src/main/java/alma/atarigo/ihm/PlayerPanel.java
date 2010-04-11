/**
 * 
 */
package alma.atarigo.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXPanel;

import alma.atarigo.AbstractPlayer;
import alma.atarigo.Cell;
import alma.atarigo.CellPosition;
import alma.atarigo.Level;
import alma.atarigo.Player;
import alma.atarigo.ia.ArtificialPlayer;
import alma.atarigo.ia.IA;

/**
 * @author E055983B
 *
 */
public final class PlayerPanel extends JXPanel implements CellMouseListener,ChangeListener,ActionListener,ItemListener,Runnable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3256085444776660113L;
	
	private static final String HELP_COMMAND = "help-command";
	private static final String FORCE_COMMAND = "force-command";
	
	private static final int ICON_SIZE = 32;
    public static final Icon HUMAN_ICON = GKit.loadIcon("icons/human.png", ICON_SIZE, ICON_SIZE);
    public static final Icon IA_ICON = GKit.loadIcon("icons/ia.png", ICON_SIZE, ICON_SIZE);    
	
	AbstractPlayer player;
	BanPanel view;
	boolean isComputer;
	Thread helpThread = null;
	final PlayerPanel SELF = this;
	JTextField score = new JTextField();
	JTextField capture = new JTextField();
	JLabel identity = new JLabel();
	JToolBar actions = new JToolBar();
	JComboBox level = new JComboBox();
	JSpinner goal = new JSpinner();
	JFormattedTextField goalEditor = new JFormattedTextField(NumberFormat.getIntegerInstance());
    ProgressMonitor monitor = new ProgressMonitor();
	
	public PlayerPanel(BanPanel view,Player player,String identity){
		this.player = (AbstractPlayer)player;
		this.view = view;
		isComputer = (player instanceof ArtificialPlayer);
		this.identity.setText(identity);
		configure();
	}
	
	private void configure(){
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
//		setOrientation(JToolBar.VERTICAL); 		
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(2, 2, 2, 2)
				,BorderFactory.createLineBorder(Color.red)));
		//setBackground(view.getBanColor());
		setMinimumSize(new Dimension(150,400));
		setMaximumSize(new Dimension(150,400));
		//setPreferredSize(new Dimension(200,400));

		Icon ic = !isComputer?HUMAN_ICON:IA_ICON;
		if(ic!=null){
			identity.setIcon(ic);
		}
		identity.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
//				BorderFactory.createCompoundBorder(
//						BorderFactory.createLoweredBevelBorder(), 
//						BorderFactory.createRaisedBevelBorder()));
		identity.setVerticalTextPosition(JLabel.BOTTOM);
		identity.setHorizontalTextPosition(JLabel.CENTER);
		add(identity);
		identity.setAlignmentX(CENTER_ALIGNMENT);
		
		
		JLabel label = new JLabel();
		label.setMinimumSize(new Dimension(10,20));
		label.setMaximumSize(new Dimension(10,20));
		add(label);
		
		int hField = 40;
		
		//le champ des objectifs de capture
		goal.setBorder(makeBorder("OBJECTIF"));
		goal.setModel(new SpinnerNumberModel(player.getObjective(), Player.MIN_OBJECTIVE, Player.MAX_OBJECTIVE, 1));
		goal.setMinimumSize(new Dimension(120,hField));
		goal.setMaximumSize(new Dimension(120,hField));
		goal.addChangeListener(this);
		goal.setToolTipText("Objectifs de capture");
		goalEditor.setEditable(false);
		goalEditor.setText(""+player.getObjective());
		goalEditor.setHorizontalAlignment(JTextField.CENTER);
		goal.setEditor(goalEditor);
		add(goal);
		
		//le champ du score		
		score.setBorder(makeBorder("SCORE"));
        score.setHorizontalAlignment(JTextField.CENTER);
		score.setText("---");		
		score.setEditable(false);
		score.setColumns(10);
		score.setMinimumSize(new Dimension(120,hField));
		score.setMaximumSize(new Dimension(120,hField));
		score.setToolTipText("Le nombre de pion "+player.getColor()+" sur le goban");
		score.setSize(120, 50);
		add(score);

		//le champ des captures
		capture.setBorder(makeBorder("CAPTURE"));
        capture.setHorizontalAlignment(JTextField.CENTER);
        capture.setText("---");		
        capture.setEditable(false);
        capture.setColumns(10);
        capture.setMinimumSize(new Dimension(120,hField));
        capture.setMaximumSize(new Dimension(120,hField));
        capture.setToolTipText("Le nombre de pion capture par "+player.getColor());
        capture.setSize(120,50);
		add(capture);
		
		//le niveau du joueur
		level.setBorder(makeBorder("NIVEAU"));
		for(Level l : Level.values()){
			level.addItem(l);
		}
        //level.setMinimumSize(new Dimension(120,hField));
        level.setMaximumSize(new Dimension(120,60));
		//level.setSize(120,80);
		level.addItemListener(this);
		add(level);
		try{
			level.setSelectedItem(player.getIA().getLevel());
		}finally{			
		}

		//Le progress Monitor
		monitor.setCancelButtonEnabled(false);
		monitor.setMaximumSize(new Dimension(100,20));
		add(monitor,BorderLayout.SOUTH);
		monitor.setAlignmentX(CENTER_ALIGNMENT);
		monitor.setAlignmentY(BOTTOM_ALIGNMENT);
		
		actions.setOrientation(JToolBar.VERTICAL);
		actions.setFloatable(false);
		actions.setRollover(true);				
		if(!isComputer){			
			JButton helpButton =GKit.doButton(null, "AIDE", "Aider moi a jouer", this);
			helpButton.setActionCommand(HELP_COMMAND);
			actions.add(helpButton);
			helpButton.setAlignmentX(CENTER_ALIGNMENT);
		}else{
			JButton forceButton =GKit.doButton(null, "FORCER", "Forcer moi a joueur",this);
			forceButton.setActionCommand(FORCE_COMMAND);
			actions.add(forceButton);
			forceButton.setAlignmentX(CENTER_ALIGNMENT);
		}		
		add(actions,BorderLayout.CENTER);
		
		
		view.addCellListener(this);
	}
	
	public static Border makeBorder(String title){
		return BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(Color.YELLOW, Color.BLUE)
				,title
				, TitledBorder.CENTER
				, TitledBorder.TOP
		);
	}
	
	public void updateScore(int value){
		score.setText(String.format("%d", value));
	}

	public void updateCapture(int value){
		capture.setText(String.format("%d", value));
	}
	
	@Override
	public void mouseClicked(Cell event) {
		if(helpThread!=null){
			monitor.cancel();
			view.clearHints();
			try{
				helpThread.interrupt();
			}finally{
				helpThread = null;
			}
		}
	}

	@Override
	public void mouseEntered(Cell event) {
	}

	@Override
	public void mouseExited(Cell event) {
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		SpinnerModel model = goal.getModel();
		if(model instanceof SpinnerNumberModel && model!=null && player!=null){
			int number = (Integer)((SpinnerNumberModel)model).getNumber();
			goalEditor.setText(""+number);
			player.setObjective(number);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(FORCE_COMMAND.equals(e.getActionCommand())){
			monitor.cancel();
		}//force command
		
		else if(HELP_COMMAND.equals(e.getActionCommand())){
	    		if(helpThread==null){
	    			helpThread = new Thread(this);
	    			helpThread.setDaemon(true);
	    			helpThread.start();
	    	}
		}//help command
	}

	/**
	 * Executer pour l'aider un joueur humain
	 */
	@Override
	public void run() {
		if(view.isVisible() && !SELF.isComputer){
			Game game = (Game)player.getOwner();
			if(game!=null && player.equals(game.getCurrentPlayer())){	    						
				monitor.clear();
				monitor.setCancelButtonEnabled(true);

				IA ia = ((HumanPlayer)player).getIA();
				List<CellPosition> helpCell = ia.runAll(monitor);

				System.out.println("Aide au jeu "+player.getColor()+": "+helpCell);
				if(helpCell!=null){
					view.addHints(helpCell);
					view.repaint();
				}
			}
		}			
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(level==e.getSource()){
			player.setIALevel((Level)level.getSelectedItem());
		}
	}

}
