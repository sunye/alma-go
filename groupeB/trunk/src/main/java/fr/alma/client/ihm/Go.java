package fr.alma.client.ihm;

import javax.swing.JFrame;

import fr.alma.server.core.Computer;
import fr.alma.server.core.Coordinator;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;
import fr.alma.server.core.Player;
import fr.alma.server.core.StateGame;
import fr.alma.server.ia.AlphaBeta;
import fr.alma.server.rule.Rule;
import fr.alma.server.rule.RuleAlreadyBusy;
import fr.alma.server.rule.RuleManager;

public class Go extends JFrame {

	public Go() {
		super("Jeux de Go");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 320);
		
		/*
		 * Demander qui commence
		 * Celui qui commence dispose des pierres blanches.
		 */
		IStateGame stateGame = new StateGame();
		Goban goban = new Goban(stateGame);
		IStrategy strategy = new AlphaBeta(stateGame);
		Rule rule = new Rule();
		
		Coordinator coordinator = new Coordinator(rule, goban);
		
		IPlayer computer = new Computer("ordinateur", IPlayer.BLACK, strategy);
		strategy.setPlayer(computer);
		
		IPlayer player = new Player("vous", IPlayer.WHITE, goban, stateGame);
		goban.setPlayer(player);
		
		coordinator.setPlayers(player, computer);
		coordinator.startGame();
		
		
		setContentPane(goban);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new Go();
	}
	
	
}
