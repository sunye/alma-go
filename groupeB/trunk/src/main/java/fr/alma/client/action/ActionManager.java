package fr.alma.client.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import fr.alma.server.core.Factory;


public class ActionManager {

	private AbstractAction actionNew;
	private AbstractAction actionOpen;
	private AbstractAction actionSave;
	private AbstractAction actionSaveAs;
	private AbstractAction actionExit;
	private AbstractAction actionAbout;
	
	private Context context = null;
	
	
	
	public ActionManager(Context context) {
		super();
		this.context = context;
	}
	
	
	@SuppressWarnings("serial")
	public Action getActionNew() {
		if (actionNew == null) {
			actionNew = new AbstractAction("New game") {
				@Override
				public void actionPerformed(ActionEvent e) {
					Factory.getIHMParam(context).setVisible(true);
				}
			};
		}
		actionNew.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		return actionNew;
	}
	
	
	@SuppressWarnings("serial")
	public Action getActionOpen() {
		if (actionOpen == null) {
			actionOpen = new AbstractAction("Open game") {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("open");					
				}
			};
		}
		actionOpen.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		return actionOpen;
	}

	
	@SuppressWarnings("serial")
	public Action getActionSave() {
		if (actionSave == null) {
			actionSave = new AbstractAction("Save game") {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("save");
				}
			};
		}
		actionSave.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		return actionSave;
	}
	

	@SuppressWarnings("serial")
	public Action getActionSaveAs() {
		if (actionSaveAs == null) {
			actionSaveAs = new AbstractAction("Save game as") {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("saveAs");					
				}
			};
		}
		actionSaveAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		return actionSaveAs;
	}

	
	@SuppressWarnings("serial")
	public Action getActionExit() {
		if (actionExit == null) {
			actionExit = new AbstractAction("Exit") {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);				
				}
			};
		}
		actionExit.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		return actionExit;
	}
	
	@SuppressWarnings("serial")
	public Action getActionAbout() {
		if (actionAbout == null) {
			actionAbout = new AbstractAction("About") {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("about");					
				}
			};
		}
		actionAbout.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		return actionAbout;
	}


	public Context getContext() {
		return context;
	}
}
