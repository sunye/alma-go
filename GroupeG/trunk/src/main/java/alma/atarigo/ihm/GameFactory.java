package alma.atarigo.ihm;

import java.io.File;
import java.util.Properties;


import alma.atarigo.AbstractPlayer;
import alma.atarigo.CellContent;
import alma.atarigo.GobanModel;
import alma.atarigo.Player;
import alma.atarigo.ia.ArtificialPlayer;
import alma.atarigo.ia.IA;
import static alma.atarigo.CellContent.*;

public class GameFactory {

	/**
	 * Créer une partie humain contre humain
	 * @return
	 */
	public static Game newHumanVsHumanGame(){
		try {
			return new Game(
						 new HumanPlayer(Kuro)
						,new HumanPlayer(Shiro)
					);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Créer une partie humain contre machine.
	 * C'est l'humain qui debute (Kuro)
	 * @return
	 */
	public static Game newHumanVsComputerGame(){
		try {
			return new Game(
						 new HumanPlayer(Kuro)
						,new ArtificialPlayer(Shiro)
					);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Créer une partie Machine contre humain.
	 *  C'est la machine qui debute la partie
	 * @return La nouvelle partie
	 */
	public static Game newComputerVsHumanGame(){
		try {
			return new Game(
						 new ArtificialPlayer(Kuro)
						,new HumanPlayer(Shiro)
					);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Créer une partie Machine contre Machine
	 * @return La nouvelle partie
	 */
	public static Game newComputerVsComputerGame(){
		try {
			return new Game(
						 new ArtificialPlayer(Kuro)
						,new ArtificialPlayer(Shiro)
					);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Créer une partie a partir d'une sauvegarder
	 * @param file
	 * @return La nouvelle partie
	 */
	public static Game newGameFromFile(File file){
		return null;
	}

	/**
	 * Créer une nouvelle partie
	 * @param goban
	 * @param kuro
	 * @param shiro
	 * @return La nouvelle partie
	 */
	public static Game newGame(GobanModel goban,Player kuro,Player shiro){
		return new Game(goban,kuro,shiro,Preferences.GENERAL_LEVEL);
	}
	
	/**
	 * Permet de recommencer une partie
	 * @param game La partie a recommencer
	 * @return La nouvelle partie
	 */
	public static Game newGame(Game game){
		try {
			return new Game(
					 game.getInitialModel()
					,newPlayer(game.getKuro().getMetadata())
					,newPlayer(game.getShiro().getMetadata())
					,Preferences.GENERAL_LEVEL);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Player newPlayer(Properties metadata) throws Throwable{
		CellContent content = CellContent.valueOf(metadata.getProperty("content"));
		int objective  = Integer.valueOf(metadata.getProperty("objective"));
		IA ia = IA.loadFrom(metadata,content);
		
		AbstractPlayer player = (AbstractPlayer)ClassLoader
						.getSystemClassLoader()
						.loadClass(metadata.getProperty("class"))
						.getConstructor(CellContent.class)
						.newInstance(content);
		player.setObjective(objective);
		player.setIA(ia);
		
		return player;
	}
	
}
