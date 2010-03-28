package fr.alma.client.action;

import javax.swing.JFrame;

import fr.alma.client.ihm.GoObject;
import fr.alma.client.ihm.Goban;
import fr.alma.server.core.ICoordinator;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.rule.RuleManager;

public class Context implements GoObject {

	private ICoordinator coordinator;
	private JFrame MainFrame;
	private ParamGame paramGame;
	private IStateGame stateGame;
	private Goban goban;
	private RuleManager ruleManager;
	private IPlayer computer;
	private IPlayer player;
	
	
	public Context() {
	}


	public JFrame getMainFrame() {
		return MainFrame;
	}


	public void setMainFrame(JFrame mainFrame) {
		MainFrame = mainFrame;
	}


	public ICoordinator getCoordinator() {
		return coordinator;
	}

	
	public void setCoordinator(ICoordinator coordinator) {
		this.coordinator = coordinator;
	}


	public ParamGame getParamGame() {
		return paramGame;
	}


	public void setParamGame(ParamGame paramGame) {
		this.paramGame = paramGame;
	}
	
	
	public int getSizeGoban() {
		return getParamGame().getGrille();
	}


	public IStateGame getStateGame() {
		return stateGame;
	}


	public void setStateGame(IStateGame stateGame) {
		this.stateGame = stateGame;
	}


	@Override
	public void cleanUp() {
		coordinator = null;
		coordinator = null;
		stateGame = null;
		goban = null;
		ruleManager = null;
	}


	public Goban getGoban() {
		return goban;
	}


	public void setGoban(Goban goban) {
		this.goban = goban;
	}


	public RuleManager getRuleManager() {
		return ruleManager;
	}


	public void setRuleManager(RuleManager ruleManager) {
		this.ruleManager = ruleManager;
	}


	public IPlayer getComputer() {
		return computer;
	}


	public void setComputer(IPlayer computer) {
		this.computer = computer;
	}


	public IPlayer getPlayer() {
		return player;
	}


	public void setPlayer(IPlayer player) {
		this.player = player;
	}
	
}
