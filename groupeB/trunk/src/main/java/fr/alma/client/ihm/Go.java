package fr.alma.client.ihm;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import fr.alma.client.action.Context;
import fr.alma.common.ui.Tools;
import fr.alma.server.core.Computer;
import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Factory;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;
import fr.alma.server.core.Player;
import fr.alma.server.rule.Configuration;
import fr.alma.server.rule.RuleManager;

@SuppressWarnings("serial")
public class Go extends JFrame {

	Goban goban;
	
	public Go() {
		super("Go Game");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 340);
		
		
		/*
		 * Demander qui commence
		 * Celui qui commence dispose des pierres blanches.
		 */
		IStateGame stateGame = Factory.getStateGame();
		//stateGame.load(new GameLoader(), "TestCaptureSuicidaire.txt");
		
		Goban goban = Factory.getGoban(stateGame);
		RuleManager ruleManager = Factory.getRuleManager();
		
		Coordinator coordinator = new Coordinator(goban, stateGame, ruleManager);
		
		IPlayer computer = new Computer("computer", Configuration.getColorComputer());		
		IPlayer player = new Player("player", Configuration.getColorPlayer(), goban, stateGame);
		
		coordinator.setPlayers(player, computer);
		
		IStrategy strategy = Factory.getStrategy(coordinator);
		computer.setStrategieGame(strategy);

		Context context = new Context();
		context.setMainFrame(this);
		context.setCoordinator(coordinator);

		setJMenuBar(Factory.getMenuBar(context));
		setContentPane(goban);
		Tools.center(this);
		setVisible(true);
	}
	
	
    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);
        
        //Add the goban to the content pane.
        contentPane.add(goban, BorderLayout.CENTER);

        return contentPane;
    }
    
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Go();	
			}				
		});
	}
	
}
