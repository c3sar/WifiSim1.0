package Interfaz;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ElementsForSim.AccessPoint;
import ElementsForSim.Node;
import ElementsForSim.WirelessChannel;


import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class AccessPointConfigurationPanel extends Configuration{
	
	private AccessPoint AP = null;
	private ArrayList<Float> Rates;
    private ArrayList<Float> Sensitivities;  
	private static final long serialVersionUID = 1L;
	protected static final AccessPoint AccesPoint = null;
	private String name;
	private float EnergyEmited;	
	private JLabel jLabel_name = null;
	private JLabel jLabel_energy = null;
	private JPanel jPanel_pictureAP = null;
	//private JTextField jTextField_energy  = null;
	private Button button_save = null;
	private JLabel jLabel_BeaconFrameSize = null;
	private JTextField jTextField_BeaconFrameSize = null;
	private JLabel jLabel_interTimeBeaconFrame = null;
	private JTextField jTextField_interTimeBeaconFrame = null;
	private String Energy_mW;  //  @jve:decl-index=0:
	private JButton jButton_ConfigSensitivity = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanel_ConfigA = null;
	private JPanel jPanel_ConfigB = null;
	private JPanel jPanel_ViewCover = null;
	public static JTable jTable_ForSensistivity = null;
	private MyTableModel_ForSensitivity myTableModel_ForSensitivity = null;
	private JScrollPane jScrollPane_ForSensitivity = null;
	private JPanel jPanel = null;
	
	public class DrawingPane extends JPanel{ 
		
		String type;
		
		DrawingPane(String type){
			
			this.type=type;
		}
		
		 protected void paintComponent(Graphics g){
			
			super.paintComponent(g);
			Image img = null;
			
			if(type.equals("AccessPoint")){
				
			     img = new ImageIcon("AP2.jpg").getImage();
			}
			else{
				
				 img = new ImageIcon("Node.jpg").getImage();
			}
			
    		g.drawImage(img, 0,0, this);    		
		}
	}
	
	
	public AccessPointConfigurationPanel(AccessPoint AP) {
		super();
		this.AP=AP;
		this.name = "Access point "+AP.getID();
		this.EnergyEmited = AP.getEnergyEmitedmW();
		this.AP = AP;
		initialize();	
		setVisible(true);
	}

	
	private void initialize(){
		
		jLabel_interTimeBeaconFrame = new JLabel();
		jLabel_interTimeBeaconFrame.setText("Inter time beacon frame (us)");
		jLabel_interTimeBeaconFrame.setBounds(new Rectangle(7, 43, 171, 23));
		
		jLabel_energy = new JLabel();
		jLabel_energy.setText("Power emitted by AP (mW)");
		jLabel_energy.setBounds(new Rectangle(7, 19, 207, 16));
		
		jLabel_name = new JLabel();
		jLabel_name.setText(name);
		jLabel_name.setBounds(new Rectangle(294, 3, 190, 20));
		
		this.setSize(656, 230);
		this.setLayout(null);
		this.add(jLabel_name, null);
		this.add(getJPanel_pictureAP(), null);
		this.add(getJTabbedPane(), null);
	}

    private JPanel getJPanel_pictureAP() {
		
		if (jPanel_pictureAP == null)
		{
			jPanel_pictureAP = new DrawingPane("AccessPoint");
			jPanel_pictureAP.setLayout(new GridBagLayout());			
			jPanel_pictureAP.setBounds(new Rectangle(588,2,25,25));
			jPanel_pictureAP.repaint();
		}
		return jPanel_pictureAP;
	}

   public JTextField getJTextField_energy(){
	   
		
			jTextField_energy = new JTextField();
			jTextField_energy.setText(String.valueOf(this.EnergyEmited));
			jTextField_energy.setBounds(new Rectangle(220, 15, 83, 23));
		
			
			
		return jTextField_energy;
   }

	private Button getButton_save() {
		
		if (button_save == null) {
			button_save = new Button();
			button_save.setLabel("Save");
			button_save.setBounds(new Rectangle(483, 154, 139, 20));
			button_save.setBackground(Color.yellow);
			button_save.addActionListener(
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0) {
							
							if (button_save == arg0.getSource()){	
								
							    Energy_mW = jTextField_energy.getText();
								AP.ModifyEnergyRadiatedByAP(Float.valueOf(Energy_mW));
								String timeBeaconFrame = jTextField_interTimeBeaconFrame.getText();
								Integer  aux = Integer.valueOf(timeBeaconFrame);
								AP.setBeaconFrameTime(aux);
								MainFrame.returnAP(AP);
							}
						}	
					}
			);
		}
		return button_save;
	}

	private JTextField getJTextField_interTimeBeaconFrame() {
		
		if (jTextField_interTimeBeaconFrame == null){
			
			jTextField_interTimeBeaconFrame = new JTextField();
			jTextField_interTimeBeaconFrame.setText(String.valueOf(AP.getBeaconFrameTime()));
			jTextField_interTimeBeaconFrame.setBounds(new Rectangle(220, 44, 82, 23));
		}
		return jTextField_interTimeBeaconFrame;
	}

	private JTabbedPane getJTabbedPane() {
		
		if (jTabbedPane == null){
			
			jTabbedPane = new JTabbedPane();			  
			jTabbedPane.setBounds(new Rectangle(6, 6, 636, 209));
			jTabbedPane.addTab("Configuration", null, getJPanel_ConfigA(), null);
			jTabbedPane.addTab("View coverage", null, getJPanel_ConfigB(), null);	
			jTabbedPane.addChangeListener(new ChangeListener(){

				@Override
				public void stateChanged(ChangeEvent arg0) {
					
					if(jPanel_ConfigB.isShowing()){
						
						jPanel = new Chart_Panel();
						jPanel.setBackground(Color.white);
						jPanel.setLayout(null);
						jPanel.setBounds(new Rectangle(6, 6, 602, 127));
						System.out.println("energy="+(MainFrame.jPanel).jTextField_energy.getText());
						((Chart_Panel)jPanel).setEenergy_mW((MainFrame.jPanel).jTextField_energy.getText());
						((Chart_Panel)jPanel).repaint();						
					} 					
				}
				
			});
			
		}
		return jTabbedPane;
	}

	private JPanel getJPanel_ConfigA(){
		
		if (jPanel_ConfigA == null){
			
			jPanel_ConfigA = new JPanel();
			jPanel_ConfigA.setLayout(null);
			jPanel_ConfigA.add(jLabel_energy, null);
			jPanel_ConfigA.add(jLabel_interTimeBeaconFrame, null);
			jPanel_ConfigA.add(getJTextField_energy(), null);
			jPanel_ConfigA.add(getJTextField_interTimeBeaconFrame(), null);
			jPanel_ConfigA.add(getJScrollPane_ForSensitivity(), null);
			jPanel_ConfigA.add(getButton_save(), null);
		}
		return jPanel_ConfigA;
	}

	private JPanel getJPanel_ConfigB(){
		
		if (jPanel_ConfigB == null) {
			jPanel_ConfigB = new JPanel();
			jPanel_ConfigB.setLayout(null);
			jPanel_ConfigB.add(getJPanel(), null);			
			
		}
			
		return jPanel_ConfigB;
	}
	
	private JTable getJTable_ForSensistivity(){
		
			myTableModel_ForSensitivity = new MyTableModel_ForSensitivity(AP.getSensitivity(),AP.getTransmissionSpeed()); 
			jTable_ForSensistivity = new JTable(myTableModel_ForSensitivity);	
			
		return jTable_ForSensistivity;
	 }
  
	JFreeChart paintGraphic(){
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Float value;
		String distance;
		double signalLoss;
		Energy_mW = MainFrame.jPanel.jTextField_energy.getText();
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
		 }			
		for(int i=0;i<200;i=i+10){
			
			distance = (String)Integer.toString(i);
			signalLoss =  AccessPoint.signalLossByDistance(Float.valueOf(distance),f);//f-> frequency in Ghz. d-> distance in meters
			value = (float) (AccesPoint.converterWtodB(Energy_W)-signalLoss);
			dataset.addValue(value.floatValue(), "AP coverage(m)", distance);									
		}

		JFreeChart chart = ChartFactory.createLineChart(" Representation of the access point coverage "," ","Db.",dataset,PlotOrientation.VERTICAL,true,true,false);
		return chart;
	}

	public void Update(ArrayList rates,ArrayList sens){
	
		jScrollPane_ForSensitivity.setViewportView(getJTable_ForSensistivity());
		jScrollPane_ForSensitivity.updateUI();
		jScrollPane_ForSensitivity.repaint();
		this.jTable_ForSensistivity.repaint();
	}

	/**
	 * This method initializes myTableModel_ForSensitivity1	
	 * 	
	 * @return Interfaz.MyTableModel_ForSensitivity	
	 */    
	private MyTableModel_ForSensitivity getMyTableModel_ForSensitivity() {
		if (myTableModel_ForSensitivity == null) {
			myTableModel_ForSensitivity = new MyTableModel_ForSensitivity(AP.getSensitivity(),AP.getTransmissionSpeed());
		}
		return myTableModel_ForSensitivity;
	}

	/**
	 * This method initializes jScrollPane_ForSensitivity	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_ForSensitivity(){
		
		if (jScrollPane_ForSensitivity == null){
			
			jScrollPane_ForSensitivity = new JScrollPane();			
			jScrollPane_ForSensitivity.setBounds(new Rectangle(382, 7, 241, 137));
			jScrollPane_ForSensitivity.setViewportView(getJTable_ForSensistivity());
		}
		return jScrollPane_ForSensitivity;
	}


	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			
			
			
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			Float value;
			String distance;
			double signalLoss;
			
			float Energy_W = (float) (Float.valueOf((MainFrame.jPanel).jTextField_energy.getText())*Math.pow(10, -3));
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
				dataset.addValue(value.floatValue(), "A.P. coverage(m).", distance);									
			}

			JFreeChart chart = ChartFactory.createLineChart(""," ","dB.",dataset,PlotOrientation.VERTICAL,true,true,false);
			jPanel = new ChartPanel(chart);
			jPanel.setBackground(Color.white);
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(6, 6, 619, 171));
			
				
			/*
			jPanel = new Chart_Panel();
			jPanel.setBackground(Color.white);
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(6, 6, 602, 127));
			((Chart_Panel)jPanel).setEenergy_mW((MainFrame.jPanel).jTextField_energy.getText());
			((Chart_Panel)jPanel).repaint();
			*/
		}
		return jPanel;
	}
}  