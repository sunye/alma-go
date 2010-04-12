package alma.atarigo.ihm;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import alma.atarigo.IProgressMonitor;


public class ProgressMonitor extends JPanel implements ActionListener,IProgressMonitor {

	private static Map<ProgressMonitor,Boolean> cancelStates 
	= Collections.synchronizedMap(new HashMap<ProgressMonitor, Boolean>());
	private static Map<ProgressMonitor,Boolean> firstStates 
	= Collections.synchronizedMap(new HashMap<ProgressMonitor, Boolean>());

	/**
	 * 
	 */
	private static final long serialVersionUID = 1230137104150925738L;
	private JProgressBar progressBar = new JProgressBar(0,100);
	private JButton abortButton = new JButton(" x ");
	private Timer timer = new Timer("Monitor Timer",true);

	private long milliSecondToDecideToPopup = 500;
	private long milliSecondToPopup = 250;
	private long max = 0;
	private long min = 0;
	private long cur = 0;

	public ProgressMonitor(){
		//setVisible(false);

		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
//		setRollover(true);
//		setFloatable(false);


		progressBar.setStringPainted(true);
		add(progressBar);

		abortButton.setActionCommand("CANCEL");
		abortButton.addActionListener(this);
		abortButton.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		add(abortButton);

		clear();
	}

	@Override
	public void close() {
		cancelStates.put(this, true);
		//setVisible(false);
	}

	public void clear(){
		cancelStates.put(this, false);
		firstStates.put(this, true);
		cur = 0;
	}

	@Override
	public boolean isCancelled() {
		return cancelStates.get(this);
	}

	public boolean isDone(){
		return this.progressBar.getMaximum() ==  this.progressBar.getValue();
	}

	@Override
	public void setMaximum(long maximum) {
		this.max = maximum;
	}

	@Override
	public void setMinimum(long minimum) {
		this.min = minimum;
		this.cur = minimum;
	}

	@Override
	public void setProgress(long value) {
		if(value>max){
			close();
			return;
		}
		
		boolean first = firstStates.get(this);
		if(first){
			firstStates.put(this, false);
			//armTimer();
		}

		int percent = (int)(((double)(100*value))/(max - min));

		progressBar.setValue(percent);
		progressBar.setString(String.format("%d%%", percent));
	}

	@Override
	public void setRange(long minimum, long maximum) {
		setMaximum(maximum);
		setMinimum(minimum);
	}		

	public void cancel(){
		cancelStates.put(this, true);
	}
	
	@Override		
	public void actionPerformed(ActionEvent e) {
		if("CANCEL".equals(e.getActionCommand())){
			cancel();
		}
	}

	private void createTimer(){
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if(!isDone() && !isVisible()){
					setVisible(true);
				}
			}

		}, milliSecondToDecideToPopup+milliSecondToPopup);
	}


	public void setCancelButtonEnabled(boolean value){
		abortButton.setVisible(value);
		if(value){
			this.invalidate();
		}
	}
	
	public void setCancelButtonText(String text){
		abortButton.setText(text);
	}

	@Override
	public void nextValue() {
		setProgress(++cur);
	}
}
