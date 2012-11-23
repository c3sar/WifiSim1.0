package Interfaz;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import ElementsForSim.AccessPoint;
import ElementsForSim.Node;

public class Chart_Panel extends JPanel{

	BufferedImage image = null;
	static String energy_mW = new String("0");
	
	
	public Chart_Panel(){
	
		 addMouseListener ( new MouseAdapter (){
    		 
             public void mouseClicked(java.awt.event.MouseEvent e) {
     	    	if( e.getButton() == java.awt.event.MouseEvent.BUTTON3 )
     	    	{
     	    		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
					Float value;
					String distance;
					double signalLoss; 
					
					String Energy_mW = (MainFrame.jPanel).jTextField_energy.getText();
					 
					float Energy_W = (float) (Float.valueOf(Energy_mW)*Math.pow(10, -3));
					float f = 0;
					
					
					switch(MainFrame.jComboBoxPhysicalLayer.getSelectedIndex()){
					 
					 case 0://802.11							
						 f =(float) 2.4;						 
						 break;
					 case 1://802.11a								 		 
						 f=(float) 5;						
						 break;
					 case 2://802.11b									
						 f =(float) 2.4;
						 break;
					 case 3://802.11g								 
						 f = (float) 2.4;								
						 break;
					 case 4://802.11n								 
						 f = (float) (float) Float.valueOf((String) MainFrame.jComboBox_Frequency.getSelectedItem());								
						 break;								 		 
					 }			
					for(int i=0;i<250;i=i+10){
						
						distance = (String)Integer.toString(i);
						signalLoss =  AccessPoint.signalLossByDistance(Float.valueOf(distance),f);//f-> frequency in Ghz. d-> distance in meters
						value = (float) (Node.converterWtodB(Energy_W)-signalLoss);
						dataset.addValue(value.floatValue(), "Node coverage in m.", distance);									
					}
			
					JFreeChart chart = ChartFactory.createLineChart(" Representation of the coverage "," ","Db.",dataset,PlotOrientation.VERTICAL,true,true,false);
						
					
					//crear y visualizar una ventana...								
					ChartFrame frame = new ChartFrame("Cover", chart);								
					frame.pack();			
					frame.setSize(700, 350);
					Dimension display  = Toolkit.getDefaultToolkit().getScreenSize();				            
					frame. setLocation((display.width - frame.WIDTH) / 4,
                            (display.height - frame.HEIGHT) / 4);
					frame.setVisible(true);
     	    		
     	    	}
     	     }
           });
		
		
	}
	
	public Chart_Panel(String string, JFreeChart chart) {
		// TODO Auto-generated constructor stub
	}

	public void setEenergy_mW(String e){
		
		
		energy_mW = e;
	}
	

	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		paintChart(energy_mW);
		g.drawImage(image,1,1,null);
	}
	
	public void paintChart(String Energy_mW){
		
		
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Float value;
		String distance;
		double signalLoss;
		
		float Energy_W = (float) (Float.valueOf(Energy_mW)*Math.pow(10, -3));
		float f = 0;
		
		
		switch(MainFrame.jComboBoxPhysicalLayer.getSelectedIndex()){
		 
		 case 0://802.11							
			 f =(float) 2.4;						 
			 break;
		 case 1://802.11a								 		 
			 f=(float) 5;						
			 break;
		 case 2://802.11b									
			 f =(float) 2.4;
			 break;
		 case 3://802.11g								 
			 f = (float) 2.4;								
			 break;
		 case 4://802.11n								 
			 f = (float) (float) Float.valueOf((String) MainFrame.jComboBox_Frequency.getSelectedItem());								
			 break;								 		 
		 }			
		for(int i=0;i<300;i=i+20){
			
			distance = (String)Integer.toString(i);
			signalLoss =  AccessPoint.signalLossByDistance(Float.valueOf(distance),f);//f-> frequency in Ghz. d-> distance in meters
			value = (float) (Node.converterWtodB(Energy_W)-signalLoss);
			dataset.addValue(value.floatValue(), "Node coverage in m.", distance);									
		}

		JFreeChart chart = ChartFactory.createLineChart(""," ","dB.",dataset,PlotOrientation.VERTICAL,true,true,false);
			
		
		//crear y visualizar una ventana...								
		//ChartFrame frame = new ChartFrame("Coverage", chart);								
		
		this.image = chart.createBufferedImage(592, 180);
	    	
	}

}


