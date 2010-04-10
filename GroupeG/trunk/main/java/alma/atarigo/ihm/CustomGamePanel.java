package alma.atarigo.ihm;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.jdesktop.swingx.JXPanel;

import alma.atarigo.CellContent;
import alma.atarigo.GobanModel;
import alma.atarigo.Player;

public class CustomGamePanel extends JXPanel implements ActionListener,ListSelectionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6253451874204282469L;
	PlayerTemplatePanel kuroPanel = new PlayerTemplatePanel(CellContent.Kuro);
	PlayerTemplatePanel shiroPanel = new PlayerTemplatePanel(CellContent.Shiro);
	GobanTemplatePanel gobanPanel = new GobanTemplatePanel();

	String[] datas = { "Kuro (Noir)", "Shiro (Blanc)", "Goban (Plateau)" };
	JList options = new JList(datas);
	JButton create = new JButton("Creer la partie");
	Component parent = null;
	JSplitPane splitPane = null;
	
	GridLayout hLayout = new GridLayout(2,1);
	
	public CustomGamePanel(Component parent) {
		this.parent = parent;

		create.addActionListener(this);
		create.setActionCommand("CREATE-GAME");
		create.setMinimumSize(new Dimension(200, 50));
		create.setMaximumSize(new Dimension(1920, 50));

		options.setBorder(PlayerPanel.makeBorder("OPTIONS"));
		options.setSelectedIndex(0);
		options.addListSelectionListener(this);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(options), new JScrollPane(kuroPanel));
		splitPane.setDividerSize(2);
		splitPane.setOneTouchExpandable(false);
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(1,1));
		contentPane.add(splitPane);
		
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		add(contentPane);
		add(create);		
		create.setAlignmentX(CENTER_ALIGNMENT);		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if ("CREATE-GAME".equals(arg0.getActionCommand())) {
			Player kuro = kuroPanel.getPlayer();
			Player shiro = shiroPanel.getPlayer();
			GobanModel model = gobanPanel.getModel();

			Game game = GameFactory.newGame(model,kuro, shiro);

			if (parent != null) {
				parent.setVisible(false);
			}

			GameTabs.instance.addGame(game);
			game.start();
		}
	}

	public static void showDialog(Component parent) {
		JDialog dialog = new JDialog();
		dialog.setModal(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setTitle("Partie personalisée");
		dialog.setMinimumSize(new Dimension(600,400));
		dialog.setLocationRelativeTo(parent);
		dialog.setResizable(false);
		
		CustomGamePanel panel = new CustomGamePanel(dialog); 
		dialog.setContentPane(panel);
		
		dialog.setVisible(true);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()){
			switch(options.getSelectedIndex()){
			case 0:
				splitPane.setRightComponent(new JScrollPane(kuroPanel));
				break;
			case 1:
				splitPane.setRightComponent(new JScrollPane(shiroPanel));
				break;
			case 2:
				splitPane.setRightComponent(new JScrollPane(gobanPanel));
				break;
			}
		}
	}
		

}
