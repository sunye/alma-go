package fr.alma.go.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class GoFrame extends JFrame {

	private JLabel label;

	private GoPanel goPan;

	public GoFrame() {
		ImageIcon gobanImage = new ImageIcon(
				"src/fr/alma/go/ui/images/Goban2.png");
		this.setTitle("Atari Go");
		this.setSize(gobanImage.getIconWidth(), gobanImage.getIconHeight()); // 4;23
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);

		goPan = new GoPanel();
		this.setContentPane(goPan);
		this.addMouseListener(goPan);
		label=new JLabel("Bob");
		goPan.add(label, BorderLayout.NORTH);

		this.setVisible(true);
	} // GoFrame()

} // class GoFrame
