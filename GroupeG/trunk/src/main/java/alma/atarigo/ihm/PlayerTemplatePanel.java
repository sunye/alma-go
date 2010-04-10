package alma.atarigo.ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import alma.atarigo.AbstractPlayer;
import alma.atarigo.CellContent;
import alma.atarigo.Level;
import alma.atarigo.Player;
import alma.atarigo.ia.Algorithm;
import alma.atarigo.ia.AlphaBeta;
import alma.atarigo.ia.ArtificialPlayer;
import alma.atarigo.ia.IA;
import alma.atarigo.ia.MinMax;
import alma.atarigo.ia.valuation.Attack;
import alma.atarigo.ia.valuation.Border;
import alma.atarigo.ia.valuation.Intelligent;
import alma.atarigo.ia.valuation.LibertyVal;
import alma.atarigo.ia.valuation.Linear;
import alma.atarigo.ia.valuation.Random;
import alma.atarigo.ia.valuation.TerritoryVal;
import alma.atarigo.ia.valuation.Valuation;

public class PlayerTemplatePanel extends JPanel implements ActionListener,ItemListener,ChangeListener,FocusListener{

	final static String HUMAN = "Homo Sapiens";
	final static String IA_NAME = "Ordinateur";
	final static String AC_TYPE = "ac-type";
	final static String AC_LEVELS = "ac-levels";
	final static String AC_GOALS = "ac-goals";
	final static String AC_ALGOS = "ac-algos";
	final static String AC_EVALS = "ac-evals";
	
	CellContent content = CellContent.Kuro;
	JLabel identity = new JLabel();
	JComboBox type = new JComboBox();
	JComboBox levels = new JComboBox();
	JComboBox algos = new JComboBox();
	JComboBox evals = new JComboBox();
	JSpinner goals = new JSpinner();
	JFormattedTextField goalEditor = new JFormattedTextField(NumberFormat.getIntegerInstance());
	
	static Valuation[] valuations = null;

	public PlayerTemplatePanel(CellContent content,Valuation ... vals){
		this.content = content;
//		for(Valuation v : vals){
//			evals.addItem(v);
//		}
		valuations = new Valuation[]{
			  new Intelligent(this.content)
			, new Random(this.content)
			, new Linear(this.content)
			, new LibertyVal(this.content)
			, new TerritoryVal(this.content)
			, new Attack(this.content)
			, new Border(this.content)
		};
		
		configure();
	}
	
	private void configure(){		
		//===== Le type du joueur
		type.setActionCommand(AC_TYPE);
		type.addItemListener(this);
		type.addItem("Ordinateur");
		type.addItem("Homo Sapiens");
		type.setSelectedIndex(0);
		type.setBorder(PlayerPanel.makeBorder("TYPE"));
		type.setToolTipText("Le type du joueur");
		type.setMaximumSize(new Dimension(150,60));
		type.addFocusListener(this);
		
		//===== Le niveau du joueur
		levels.setActionCommand(AC_LEVELS);
		levels.addItemListener(this);
		for(Level level : Level.values()){
			levels.addItem(level);
		}
		levels.setSelectedIndex(0);
		levels.setBorder(PlayerPanel.makeBorder("NIVEAU"));
		levels.setToolTipText(
				"Pour un humain, Le niveau à utiliser pour l'aide au jeu.\n" +
				"Pour l'ordinateur le niveau de l'intelligence artificielle.");
		levels.setMaximumSize(new Dimension(150,60));
		levels.addFocusListener(this);
		
		//==== Les algos de l'IA
		algos.setActionCommand(AC_ALGOS);
		algos.addItemListener(this);
		algos.addItem("ALPHA-BETA");
		algos.addItem("MIN-MAX");
		algos.setSelectedIndex(0);
		algos.setBorder(PlayerPanel.makeBorder("ALGORITHM"));	
		algos.setToolTipText(
				"Pour un humain, L'algorithme à utiliser l'aide au jeu.\n" +
				"Pour l'ordinateur l'algorithme de l'intelligence artificielle.");
		algos.setMaximumSize(new Dimension(150,60));
		algos.addFocusListener(this);
		
		//==== Les fonctions d'évaluation
		evals.setActionCommand(AC_EVALS);
		evals.addItemListener(this);
		//evals.setSelectedIndex(0);
		evals.setMaximumSize(new Dimension(150,60));
		evals.setBorder(PlayerPanel.makeBorder("EVALUATION"));				
		evals.setToolTipText(
				"Pour un humain, La fonction d'évaluation à utiliser pour l'aide au jeu.\n" +
				"Pour l'ordinateur la fonction d'évaluation de l'intelligence artificielle.");
		evals.addFocusListener(this);
		for(Object c : valuations){
			evals.addItem(c);
		}
		evals.setSelectedItem(0);
		
		//===== L'identité
		identity.setText(content.toString());
		identity.setIcon(PlayerPanel.IA_ICON);
		identity.setHorizontalTextPosition(JLabel.CENTER);
		identity.setVerticalTextPosition(JLabel.BOTTOM);
		identity.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLoweredBevelBorder(), 
						BorderFactory.createRaisedBevelBorder()));
		identity.setBackground(content.isKuro()?Color.BLACK:Color.WHITE);
		
		//===== Les objectifs de capture
		goals.setBorder(PlayerPanel.makeBorder("OBJECTIFS"));
		goals.setModel(new SpinnerNumberModel(
						  Player.MIN_OBJECTIVE
						, Player.MIN_OBJECTIVE
						, Player.MAX_OBJECTIVE, 1));
		goals.addChangeListener(this);
		goals.setToolTipText("L'objectif de capture pour "+content);
		goalEditor.setEditable(false);
		goalEditor.setText(""+Player.MIN_OBJECTIVE);
		goalEditor.setHorizontalAlignment(JTextField.LEFT);
		//goalEditor.setMaximumSize(new Dimension(150,60));
		goals.setMaximumSize(new Dimension(150,60));
		goals.setEditor(goalEditor);		
		goals.addFocusListener(this);
		goals.addChangeListener(this);
		
		//===== Assemblage
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(identity);
		identity.setAlignmentX(CENTER_ALIGNMENT);		
		add(new JLabel(" "));
		add(type);
		add(new JLabel(" "));
		add(levels);
		add(new JLabel(" "));
		add(goals);
		add(new JLabel(" "));
		add(algos);
		add(new JLabel(" "));
		add(evals);
	}

	public boolean isComputer(){
		return IA_NAME.equals(type.getSelectedItem());
	}
	
	public boolean isHuman(){
		return HUMAN.equals(type.getSelectedItem());
	}
	
	public boolean isMinMax(){
		return "MIN-MAX".equals(algos.getSelectedItem());
	}
	
	public boolean isAlphaBeta(){
		return "ALPHA-BETA".equals(algos.getSelectedItem());				
	}
	
	public Algorithm getAlgorithm(){
		//return new MinMax();
		return isAlphaBeta()
			? new AlphaBeta()
			: new MinMax();
	}
	
	public Valuation getValuation(){
		return (Valuation)evals.getSelectedItem();
	}
	
	public Level getLevel(){
		return (Level)levels.getSelectedItem();
	}
	
	public Player getPlayer(){
		IA brain = new IA();
		
		Algorithm algorithm = getAlgorithm();
		Valuation valuation = getValuation();		
		brain.setAlgorithm(algorithm);
		brain.setValuation(valuation);
		brain.setLevel(getLevel());

		AbstractPlayer result;
		
		if(isComputer()){
			result = new ArtificialPlayer(content);					
		}else{
			result = new HumanPlayer(content);
		}

		result.setIA(brain);
		result.setObjective(getObjective());			

		return result;
	}
	
	public int getObjective(){
		return (Integer)((SpinnerNumberModel)goals.getModel()).getNumber();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		if(type.equals(arg0.getSource())){
			if(isComputer()){
				identity.setIcon(PlayerPanel.IA_ICON);
			}else{
				identity.setIcon(PlayerPanel.HUMAN_ICON);
			}
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent arg0) {
		SpinnerModel mod = goals.getModel();
		if(mod instanceof SpinnerNumberModel){
			SpinnerNumberModel snm = (SpinnerNumberModel)mod;
			Number number = snm.getNumber();
			goalEditor.setText(""+number);
		}
	}
	
	@Override
	public void focusLost(FocusEvent e) {
//		e.getComponent().setBackground(Color.WHITE);
	}

	@Override
	public void focusGained(FocusEvent e) {
//		e.getComponent().setBackground(Color.YELLOW);
	}
	
}
