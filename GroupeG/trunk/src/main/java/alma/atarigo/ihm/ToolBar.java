/**
 * 
 */
package alma.atarigo.ihm;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import alma.atarigo.*;

/**
 * @author E055983B
 *
 */
public class ToolBar extends JToolBar implements ItemListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8857162900337976286L;
	JButton restart = GKit.doButton("icons/restart.png", "R", "Redemarrer la partie", ActionHandler.RESTART_GAME);
	JButton save = GKit.doButton("icons/save.png", "S", "Sauvegarder la partie", ActionHandler.SAVE);
	JButton load = GKit.doButton("icons/load.png", "L", "Charger une partie", ActionHandler.LOAD_GAME);
	JButton giveUp = GKit.doButton("icons/giveup.png", "A", "Abandonner la partie", ActionHandler.END_GAME);
	JButton exit = GKit.doButton("icons/exit.png", "Q", "Quitter la partie", ActionHandler.EXIT);
//	JButton testButton = GKit.doButton(null,"JOUER", "Bouton de test", new ActionListener(){
//		@Override
//		public void actionPerformed(ActionEvent arg0) {
//			Player player = GameController.getGameTabs().getActiveGame().getCurrentPlayer();
//			if(player!=null){
//				if(player instanceof ArtificialPlayer){
//					ArtificialPlayer ap = (ArtificialPlayer)player;
//					ap.abort();
//				}
//			}
//		}});
	
	JButton buttons[] = {
			 restart
			//,save
			//,load
			,giveUp
			,exit
	};
	JComboBox level = new JComboBox();
	
	private ToolBar(){
		configure();
	}
	
	private void configure(){
		setFloatable(false);
		setRollover(true);
		setMaximumSize(new Dimension(1920,60));
                setLayout(new BorderLayout());

                JToolBar buttonPanel = new JToolBar();
                buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
                buttonPanel.setFloatable(false);
                buttonPanel.setRollover(true);
                
		//ajout des boutons
		for(JButton button : buttons){
                    button.setBorderPainted(false);
                    buttonPanel.add(button);
		}
		add(buttonPanel,BorderLayout.WEST);
                
		//ajout du menu de selection des boutons
		level.setBorder(BorderFactory.createTitledBorder("Niveau"));
		level.addItemListener(this);
		for(Level l : Level.values()){
			level.addItem(l);
		}
		level.setSelectedItem(Level.Debutant);
		level.setMaximumSize(new Dimension(100,50));
//		add(level,BorderLayout.EAST);
		
	}
	
	private static ToolBar toolBar = null;
	
	public static ToolBar getToolBar(){
		if(toolBar==null){
			toolBar = new ToolBar();
		}
		return toolBar;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		Level lvl = (Level)level.getSelectedItem();
		ActionHandler.fireLevelChange(lvl);
	}
	
}
