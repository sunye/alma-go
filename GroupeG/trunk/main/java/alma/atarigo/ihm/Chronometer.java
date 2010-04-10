/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alma.atarigo.ihm;

import java.awt.Color;
import javax.swing.*;

/**
 * Compte a rebourg
 * @author steg
 */
public class Chronometer extends JProgressBar {
    private long timeToPlay;
    private long countDown;
    private Runnable timeOutAction;
    private CountTask countTask = new CountTask();
    private boolean paused = false;

    static{
        UIManager.put("ProgressBar.selectionBackground", Color.RED);
        UIManager.put("ProgressBar.selectionForeground", Color.RED);
    }

    /**
     * Chrono pour les temps de jeu
     * @param timeToPlay Le temps imparti en millisecond
     * @param timeOutAction L'action a effectuer en cas de timeout
     */
    public Chronometer(long timeToPlay,Runnable timeOutAction){
        super(0,100);
        super.setValue(0);
        this.timeToPlay = timeToPlay;
        this.timeOutAction = timeOutAction;
    }

    public Chronometer(long timeToPlay){
        this(timeToPlay,null);
    }

    /**
     * Modifier la valeur du chrono qui doit etre eteint
     * avant l'appel de cette methode
     * @param ttp Le nouveau delai
     */
    public void setTimeToPlay(long ttp){
        this.timeToPlay = ttp;
    }

    /**
     * Modifier l'action a executer en cas de time out
     * @param timeOutAction
     */
    public void setTimeOutAction(Runnable timeOutAction){
        this.timeOutAction = timeOutAction;
    }

    /**
     * Modifier la valeur du chrono
     * @param time
     */
    private void setValue(long time){
        if(timeToPlay<=0){
            super.setValue(100);
        }
        super.setValue((int) (((double) time * 100) / timeToPlay));
    }

    /**
     * Decrémenter le chrono
     */
    public void countDown(){
        countDown += 1000;
        setValue(countDown);
    }

    /**
     *
     * @return true Si le chrono est a zero
     */
    public boolean timeOut(){
        return countDown>=timeToPlay;
    }

    /**
     * Demarrer le chrono
     */
    public void start(){
        stop();
        if(!isPaused()){
            countDown = 0;
        }
        countTask = new CountTask();
        countTask.execute();
        paused = false;
    }

    /**
     * Arreter le chrono
     */
    public void stop(){
        if(countTask!=null){
            countTask.cancel(true);
            countTask = null;
        }
    }

    public void pause(){
        paused = true;
        stop();
    }

    private boolean isPaused(){
        return paused && countDown!=timeToPlay;
    }

    private class CountTask extends SwingWorker<Void,Void>{

        @Override
        protected Void doInBackground() throws Exception {
            Thread.sleep(500);
            while(!isCancelled() && !timeOut()){
                Thread.sleep(1000);
                countDown();
            }
            return null;
        }

        @Override
        public void done(){
            if(timeOut() && timeOutAction!=null){
                timeOutAction.run();
            }
        }
    }

}
