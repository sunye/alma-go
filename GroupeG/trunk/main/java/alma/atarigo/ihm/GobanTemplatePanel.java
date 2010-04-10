package alma.atarigo.ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import alma.atarigo.CaptureException;
import alma.atarigo.Cell;
import alma.atarigo.CellContent;
import alma.atarigo.CellEvent;
import alma.atarigo.CellPosition;
import alma.atarigo.GobanModel;
import alma.atarigo.Rule;
import alma.atarigo.RuleViolationException;
import alma.atarigo.model.Goban;
import alma.atarigo.rule.Rules;

public class GobanTemplatePanel extends JPanel implements ActionListener,Runnable,ComponentListener{

	CellContent content = CellContent.Kuro;
	JButton toggle = new JButton();
	GobanModel model = new Goban();
	BanPanel panel = new BanPanel(model);
	Thread task = new Thread(this);
	
	public GobanTemplatePanel(){
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		toggleKuro();		
		
		toggle.setMinimumSize(new Dimension(150,50));
		toggle.addActionListener(this);
		
		panel.addComponentListener(this);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(toggle);
		add(panel);
		toggle.setAlignmentX(CENTER_ALIGNMENT);	
		
		task.start();
	}
	
	protected void finalize() throws Throwable{
		super.finalize();
		try{
			task.interrupt();
		}catch(Throwable e){			
		}
	}

	public void toggleKuro(){
		toggle.setBackground(Color.BLACK);
		toggle.setForeground(Color.WHITE);
		toggle.setText(""+CellContent.Kuro);
	}

	public void toggleShiro(){
		toggle.setForeground(Color.BLACK);
		toggle.setBackground(Color.WHITE);
		toggle.setText(""+CellContent.Shiro);
	}
	
	public GobanModel getModel(){
		return model;
	}

	public boolean isValid(final CellPosition position){
		CellEvent event = new CellEvent() {			
			@Override
			public CellPosition getPosition() { return position; }			
			@Override
			public CellContent getContent() { return content; }
		};
		for(Rule rule : Rules.ATARI_RULES){
			try{
				rule.check(model, event);
			}catch(RuleViolationException e){
				GKit.warning(this, e.getWhy());
				return false;
			} catch (CaptureException e) {
				model.setCellContent(position, CellContent.Empty);
				GKit.warning(this, "La capture est interdite avant de commencer la partie.");
				return false;
			}
		}
		return true;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(content.isKuro()){
			toggleShiro();
			content = CellContent.Shiro;
		}else if(content.isShiro()){
			toggleKuro();
			content = CellContent.Kuro;
		}
	}

	@Override
	public void run() {
		while(true){
			try{
				CellPosition position = panel.getPositionOnUIEvent();			
				if(position!=null && isValid(position)){
					model.setCellContent(position, content);
				}
				panel.repaint();
				Thread.sleep(200);
			}catch(Throwable e){
				Logger.getLogger(getClass().getName()).log(Level.SEVERE,null,e);
			}
		}
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		try{
			task.interrupt();
		}catch(Throwable ex){			
		}
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
		try{
			task.start();
		}catch(Throwable ex){			
			Logger.getLogger(getClass().getName()).log(Level.SEVERE,null,ex);
		}
	}

}
