package fr.alma.client.ihm;

import javax.swing.JFrame;

import fr.alma.server.core.Computer;
import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Factory;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;
import fr.alma.server.core.Player;
import fr.alma.server.rule.Configuration;

@SuppressWarnings("serial")
public class Go extends JFrame {

	public Go() {
		super("Jeux de Go");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 300, 320);
		
		
		/*
		 * Demander qui commence
		 * Celui qui commence dispose des pierres blanches.
		 */
		IStateGame stateGame = Factory.getStateGame();
		//stateGame.load(new GameLoader(), "TestCaptureSuicidaire.txt");
		Goban goban = Factory.getGoban(stateGame);
		Configuration rule = new Configuration();
		Coordinator coordinator = new Coordinator(rule, goban, stateGame);
		
		IPlayer computer = new Computer("ordinateur", IPlayer.BLACK);
		IStrategy strategy = Factory.getStrategy(coordinator, computer);
		computer.setStrategieGame(strategy);
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
