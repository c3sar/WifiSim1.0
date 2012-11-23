package Interfaz;

import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.EventListener;
import java.util.Random;

import javax.swing.SwingWorker;

import Main.main;

public class TaskForJProgressBar extends SwingWorker<Void, Void> {
    
	
    
    public Void doInBackground() {
       
        float progress = 0;       
        setProgress(0);
        while (progress < 100) {
        	
            int aux1 = main.getCurrentTime();
            int aux2 = main.getSimulationTime()-1;            
            
            progress = (float)(aux1*100)/(float)aux2;
            setProgress((int)Math.min(progress, 100));
        }
        return null;
    }

 
    public void done() {
    	
        Toolkit.getDefaultToolkit().beep(); 
        MainFrame.reactiveJtabbedPane();    
    }    
    
}