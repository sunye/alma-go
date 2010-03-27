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
import fr.alma.server.ia.IEvaluation;
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
		 * ask who start
		 * who starts has the black stones
		 */
		
		/*
		IStateGame stateGame = Factory.getStateGame();

		Goban goban = Factory.getGoban(stateGame);
		RuleManager ruleManager = Factory.getRuleManager();

		Coordinator coordinator = new Coordinator(goban, stateGame, ruleManager);
		
		IPlayer computer = new Computer("Computer", context.getParamGame().getColorComputer());
		IPlayer player = new Player("Player", Configuration.getColorPlayer(), goban, stateGame);

		IEvaluation evaluation = Factory.getEvaluation(computer, player);

		IStrategy strategy = Factory.getStrategy(coordinator);
		computer.setStrategy(strategy);
		computer.setEvaluation(evaluation);
		coordinator.setPlayers(player, computer);
		context.setCoordinator(coordinator);
		setContentPane(goban);
		 */
		
		Context context = new Context();
		context.setMainFrame(this);
		
		setJMenuBar(Factory.getMenuBar(context));
		
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
