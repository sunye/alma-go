/*
 * IA Project ATARI-GO
 * UNIVERSITY OF NANTES
 * MASTER ALMA 1
 * 2009 - 2010
 * Version 1.0
 * @author Romain Gournay & Bruno Belin
 * 
 * Copyright 2010 Romain Gournay & Bruno Belin, All rights reserved.
 * Use is subject to license terms.
 */
package fr.alma.client.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import fr.alma.server.core.Computer;
import fr.alma.server.core.Coordinator;
import fr.alma.server.core.Factory;
import fr.alma.server.core.IPlayer;
import fr.alma.server.core.IStateGame;
import fr.alma.server.core.IStrategy;
import fr.alma.server.core.Player;
import fr.alma.server.ia.IEvaluation;
import fr.alma.server.rule.RuleManager;

/**
 * To load Go's parties from a file
 */
public class GameLoader {

	private static final int GAME_PARAMETERS = 8;

	/**
	 * Constructor 
	 * @param context
	 */
	public GameLoader(){
		super();
	}


	/**
	 * To load a party in a particular state
	 * @param fileName which is loaded
	 * @return a goban corresponding to the file
	 */
	public void load(String fileName, Context context) {
		ParamGame param = new ParamGame();
		File file = new File(fileName);
		Scanner sc = null;
		try {
			sc = new Scanner(file);

			String line = null;
			int i = 0;
			while (sc.hasNextLine() && i < GAME_PARAMETERS+1) {
				line = sc.nextLine();
				line = line.substring(line.indexOf('=')+1, line.length());

				if (line != null){
					switch (i){
					case 0 : param.setTimeLimite(12); break;
					case 1 : param.setPossibilityInterruption(Boolean.parseBoolean(line)); break;
					case 2 : 
						if(Integer.parseInt(line)==6 || Integer.parseInt(line)==9) 
							param.setSizeGoban(Integer.parseInt(line));
						break;
					case 3 : 
						if(line.equals("x"))
							param.setColorComputer(false);
						else if (line.equals("o"))
							param.setColorComputer(true);
						break;
					case 4 : param.setAssistant(Boolean.parseBoolean(line)); break;
					case 5 : param.setTargetCaptureComputer(Integer.parseInt(line)); break;
					case 6 : param.setTargetCapturePlayer(Integer.parseInt(line)); break;
					case 7 : 
						if(line.equals("Computer"))
							param.setOpponent(true);
						else if (line.equals("Player"))
							param.setOpponent(false);
						break;
					default : break;
					}
				}
				i++;

			}


			Boolean[][] intersection = new Boolean[param.getSizeGoban()][param.getSizeGoban()];
			i = 0;
			while (sc.hasNextLine() && i < param.getSizeGoban()) {
				line = sc.nextLine(); // read the current line
				if (line != null)
					for (int j = 0; j <  param.getSizeGoban(); j++) {
						if (line.charAt(j) == 'x') intersection[j][i] = false;
						else if (line.charAt(j)=='o') intersection[j][i] = true;
					}
				i++; // to treat the next line
			}
			sc.close();
			context.setParamGame(param);
			IStateGame stateGame = Factory.getStateGame(context);
			context.setStateGame(stateGame);

			RuleManager ruleManager = Factory.getRuleManager(context);
			context.setRuleManager(ruleManager);

			Coordinator coordinator = new Coordinator(context);


			context.setGoban(Factory.getGoban(context));
			context.getMainFrame().setContentPane(context.getGoban());

			context.getGoban().revalidate();

			IPlayer computer = new Computer("Computer", context);
			IPlayer player = new Player("Player", ! context.getParamGame().getColorComputer(), context.getGoban(), stateGame);

			IEvaluation evaluation = Factory.getEvaluation(context);
			IStrategy strategy = Factory.getStrategy(context);
			computer.setStrategy(strategy);
			computer.setEvaluation(evaluation);
			context.setPlayer(player);
			context.setComputer(computer);

			context.setCoordinator(coordinator);
			context.getStateGame().load(intersection);
			context.getGoban().repaint();
			context.getCoordinator().startGame();

		} catch (FileNotFoundException e) {
			System.err.println("File " + fileName + " doesn't exist!");
		}

	}

}
