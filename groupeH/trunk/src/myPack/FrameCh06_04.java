/**
 * 
 */
package myPack;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * @author peter
 * 
 */
public class FrameCh06_04 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JRadioButton b1 = new JRadioButton("Metal"),
			b2 = new JRadioButton("Motif"), b3 = new JRadioButton("Windows");

	public FrameCh06_04() {
		super("Swing application");

		Container contentPane = getContentPane();
		contentPane.add(new Panel(), BorderLayout.CENTER);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final JFrame f = new FrameCh06_04();

		for (int a = 0; a >= 1000; a++) {
			f.setBounds(100, 100, 300, 300);
			f.setVisible(true);
			f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

			f.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		}

	}

	public class Panel extends JPanel implements ActionListener {
		// classe interne

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Panel() {

			add(new JButton("JButton"));
			add(new JTextField("JTextField"));
			add(new JCheckBox("JCheckBox"));
			add(new JRadioButton("JRadioButton"));
			add(new JLabel("JLabel"));
			add(new JList(new String[] { "JList Item 1", "JList Item 2",
					"JList Item 3" }));
			add(new JScrollBar(SwingConstants.HORIZONTAL));

			ButtonGroup group = new ButtonGroup();
			group.add(b1);
			group.add(b2);
			group.add(b3);

			b1.addActionListener(this);
			b2.addActionListener(this);
			b3.addActionListener(this);

			add(b1);
			add(b2);
			add(b3);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			JRadioButton src = (JRadioButton) e.getSource();

			try {
				if ((JRadioButton) e.getSource() == b1)
					UIManager
							.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
				else if ((JRadioButton) e.getSource() == b2)
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
				else if ((JRadioButton) e.getSource() == b3)
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			} catch (Exception ex) {
			}

			SwingUtilities.updateComponentTreeUI(getContentPane());

		}

	}

}
