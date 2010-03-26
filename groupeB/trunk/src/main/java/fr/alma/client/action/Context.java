package fr.alma.client.action;

import javax.swing.JFrame;

import fr.alma.server.core.Coordinator;

public class Context {

	Coordinator coordinator;
	JFrame MainFrame;
	ParamGame paramGame;
	
	public Context() {
	}


	public JFrame getMainFrame() {
		return MainFrame;
	}


	public void setMainFrame(JFrame mainFrame) {
		MainFrame = mainFrame;
	}


	public Coordinator getCoordinator() {
		return coordinator;
	}

	
	public void setCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
	}


	public ParamGame getParamGame() {
		return paramGame;
	}


	public void setParamGame(ParamGame paramGame) {
		this.paramGame = paramGame;
	}
	
	
}
