package Interfaz;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import ElementsForSim.AccessPoint;
import ElementsForSim.Node;
import java.awt.Dimension;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;

public class NodeConfigurationPanel extends Configuration{

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel_name = null;
	private JLabel jLabel = null;
	private JPanel jPanel_picture = null;	
	
    private Node node=null;
	private JPanel jPanel_Picture = null;
	private JCheckBox jCheckBox = null;
	private JLabel jLabel_RTS_Threshold = null;
	private JTextField jTextField_RTS_Threshold = null;
	private JButton jButton_Accept = null;
	private JLabel jLabel_distr_ForInterArTime = null;
	private JLabel jLabel_distr_ForLenght = null;
	private JComboBox jComboBox_distr_InterArTime = null;
	private JComboBox jComboBox_distr_ForLenght = null;
	private JLabel jLabel_meanForLenght = null;
	private JTextField jTextField_meanForLenght = null;
	private JLabel jLabel1 = null;
	private JTextField jTextField1 = null;
	private JTabbedPane jTabbedPane = null;
	private JPanel jPanelA = null;
	private JPanel jPanelB = null;
    static  JTable jTable_ForSensistivity = null;
	private MyTableModel_ForSensitivity myTableModel_ForSensitivity = null;
	private JScrollPane jScrollPane = null;
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
				
				 img = new ImageIcon("Node2.jpg").getImage();
			}
			
    		g.drawImage(img, 0,0, this);    		
		}
	}
	
	
	public NodeConfigurationPanel(Node node) {
		
		super();
		this.node = node;
		initialize();
		ArrayList sensitivity = node.getSensitivity();
	
		
		//(MyTableModel_Sensitivity this.jTable).addRow(Float speed,Integer sensitivity);
	
	}

	
	private void initialize() {
		
		jLabel1 = new JLabel();
		jLabel1.setText("Mean interval");
		jLabel1.setBounds(new Rectangle(210, 49, 104, 19));
		this.setSize(656, 230);
		jLabel_meanForLenght = new JLabel();
		jLabel_meanForLenght.setText("Length (bytes)");
		jLabel_meanForLenght.setBounds(new Rectangle(211, 71, 99, 19));
		jLabel_distr_ForLenght = new JLabel();
		jLabel_distr_ForLenght.setText("Distr. for lenght");
		jLabel_distr_ForLenght.setBounds(new Rectangle(8, 69, 129, 21));
		jLabel_distr_ForInterArTime = new JLabel();
		jLabel_distr_ForInterArTime.setText("Distr. for inter time");
		jLabel_distr_ForInterArTime.setBounds(new Rectangle(8, 49, 130, 18));
		jLabel_RTS_Threshold = new JLabel();
		jLabel_RTS_Threshold.setText("RTS Threshold (bytes)");
		jLabel_RTS_Threshold.setBounds(new Rectangle(23, 30, 127, 17));
		jLabel_RTS_Threshold.setEnabled(false);
		jLabel = new JLabel();
		jLabel.setText("Power emitted by node (mW)");
		jLabel.setBounds(new Rectangle(4, 10, 174, 17));
		jLabel_name = new JLabel();
		jLabel_name.setBounds(new Rectangle(294, 3, 120, 17));
		jLabel_name.setText("Node "+node.getIdNode());
		
		this.setLayout(null);
		this.add(jLabel_name, null);
		this.add(getJPanel_picture(), null);
			
		this.setVisible(true);
		this.add(getJTabbedPane(), null);
	}



	
	public  JTextField getJTextField() {
		
			
			jTextField_energy = new JTextField();
			jTextField_energy.setText(String.valueOf(node.getEnergyEmitedmW()));
			jTextField_energy.setBounds(new Rectangle(175, 10, 61, 18));
		
		return jTextField_energy;
	}

	private JPanel getJPanel_picture() {
		if (jPanel_picture == null) {
			jPanel_picture = new DrawingPane("Node");
			jPanel_picture.setLayout(new GridBagLayout());
			jPanel_picture.setBounds(new Rectangle(588,2,28,28));
			jPanel_picture.repaint();
		}
		return jPanel_picture;
	}

	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(3, 29, 21, 20));
			jCheckBox.addActionListener(
					new ActionListener(){				
						public void actionPerformed(ActionEvent actionEvent) {
							AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
							boolean selected = abstractButton.getModel().isSelected();
				        
				       
								if(selected == true){
									System.out.println(selected);
									jLabel_RTS_Threshold.setEnabled(true);
									jTextField_RTS_Threshold.setEnabled(true);
								}
								else{
									System.out.println(selected);
									jLabel_RTS_Threshold.setEnabled(false);
									jTextField_RTS_Threshold.setEnabled(false);
								}
				      }
			
					}
		);
		}
		return jCheckBox;
	}

	private JTextField getJTextField_RTS_Threshold() {
		if (jTextField_RTS_Threshold == null) {
			jTextField_RTS_Threshold = new JTextField();
			jTextField_RTS_Threshold.setText("125");
			jTextField_RTS_Threshold.setBounds(new Rectangle(153, 30, 78, 17));
			jTextField_RTS_Threshold.setEnabled(false);
		}
		return jTextField_RTS_Threshold;
	}

	private JButton getJButton_Accept() {
		if (jButton_Accept == null) {
			jButton_Accept = new JButton();
			jButton_Accept.setText("Save");
			jButton_Accept.setBounds(new Rectangle(483, 154, 139, 20));
			jButton_Accept.setBackground(Color.yellow);
			
			
			
			jButton_Accept.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							
							if (jButton_Accept == arg0.getSource()){								
								
								
								String Energy_mW = jTextField_energy.getText();
								String distr_for_interArTime = (String)jComboBox_distr_InterArTime.getSelectedItem();
								String distr_for_lenght = (String)jComboBox_distr_ForLenght.getSelectedItem();
								String RTSThreshold;
								if(jCheckBox.isSelected()){
									RTSThreshold = jTextField_RTS_Threshold.getText();
									if(RTSThreshold!=null
											|| RTSThreshold.equals("")){
										
										if(RTSThreshold.equals("0")){
											//No se puede permitir un umbral RTS igual a 0
											jTextField_RTS_Threshold.setText("125");
											JOptionPane.showMessageDialog(null," RTS threshold must be greater than 0 ","Error:",JOptionPane.INFORMATION_MESSAGE); 
										}
										else node.setRTSThreshold(Integer.valueOf(RTSThreshold));
									}
									
								}
								
								node.ModifyEnergyRadiatedByNode(Float.valueOf(Energy_mW));
								node.distr_for_interArTime(distr_for_interArTime);
								node.distr_for_lenght(distr_for_lenght);
								node.setMeanForLength(Integer.valueOf(jTextField_meanForLenght.getText()));								
								node.setMeanForInterArTime(Integer.valueOf(jTextField1.getText()));
								MainFrame.returnNode(node);
								
							}
						}	
					}
			);			
			
		}
		return jButton_Accept;
	}

	private JComboBox getJComboBox_distr_InterArTime(){
		
		if (jComboBox_distr_InterArTime == null) {
			jComboBox_distr_InterArTime = new JComboBox();
			jComboBox_distr_InterArTime.setBounds(new Rectangle(124, 48, 78, 20));
			
			jComboBox_distr_InterArTime.addItem("constant");
			jComboBox_distr_InterArTime.addItem("uniform");
			jComboBox_distr_InterArTime.addItem("exponential");
			
			
			char aux=node.getDistr_ForInterArTime();
			switch(aux){
				case 'c':
			    
					jComboBox_distr_InterArTime.setSelectedItem("constant");
			    break;
				case 'u':
					
					jComboBox_distr_InterArTime.setSelectedItem("uniform");
		        break;
				case 'e':
					
					jComboBox_distr_InterArTime.setSelectedItem("exponential");
			    break;
			}
		    
		    
			
			
			jComboBox_distr_InterArTime.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							String selectedItem = (String) jComboBox_distr_InterArTime.getSelectedItem();
				
							
							
							char aux=selectedItem.charAt(0);
							switch(aux){
								case 'c':
									jComboBox_distr_InterArTime.removeAllItems();
									jComboBox_distr_InterArTime.addItem("constant");
									jComboBox_distr_InterArTime.addItem("uniform");
									jComboBox_distr_InterArTime.addItem("exponential");
							    break;
								case 'u':
									
									jComboBox_distr_InterArTime.removeAllItems();
									jComboBox_distr_InterArTime.addItem("uniform");
									jComboBox_distr_InterArTime.addItem("constant");									
									jComboBox_distr_InterArTime.addItem("exponential");
						        break;
								case 'e':
									
									jComboBox_distr_InterArTime.removeAllItems();
									jComboBox_distr_InterArTime.addItem("exponential");
									jComboBox_distr_InterArTime.addItem("constant");
									jComboBox_distr_InterArTime.addItem("uniform");									
							    break;
							}
						}
					}
			);
			
		}
		return jComboBox_distr_InterArTime;
	}


	/**
	 * This method initializes jComboBox_distr_ForLenght	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox_distr_ForLenght() {
		
		if (jComboBox_distr_ForLenght == null) {
			jComboBox_distr_ForLenght = new JComboBox();
			jComboBox_distr_ForLenght.setBounds(new Rectangle(124, 70,78, 20));
			
			jComboBox_distr_ForLenght.addItem("constant");
			jComboBox_distr_ForLenght.addItem("uniform");
			jComboBox_distr_ForLenght.addItem("exponential");
			
			
			char aux = node.getdistr_ForLenght();
			
			switch(aux){
				case 'c':
			    
					jComboBox_distr_ForLenght.setSelectedItem("constant");
			    break;
				case 'u':
					
					jComboBox_distr_ForLenght.setSelectedItem("uniform");
		        break;
				case 'e':
					
					jComboBox_distr_ForLenght.setSelectedItem("exponential");
			    break;
			}
		   		
			
			jComboBox_distr_ForLenght.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							String selectedItem = (String) jComboBox_distr_ForLenght.getSelectedItem();
		
							char aux=selectedItem.charAt(0);
							switch(aux){
								case 'c':
									jComboBox_distr_ForLenght.removeAllItems();
									jComboBox_distr_ForLenght.addItem("constant");
									jComboBox_distr_ForLenght.addItem("uniform");
									jComboBox_distr_ForLenght.addItem("exponential");
							    break;
								case 'u':
									
									jComboBox_distr_ForLenght.removeAllItems();
									jComboBox_distr_ForLenght.addItem("uniform");
									jComboBox_distr_ForLenght.addItem("constant");									
									jComboBox_distr_ForLenght.addItem("exponential");
						        break;
								case 'e':
									
									jComboBox_distr_ForLenght.removeAllItems();
									jComboBox_distr_ForLenght.addItem("exponential");
									jComboBox_distr_ForLenght.addItem("constant");
									jComboBox_distr_ForLenght.addItem("uniform");									
							    break;
							}
						}
					}
			);
			
		}
		return jComboBox_distr_ForLenght;
	}


	/**
	 * This method initializes jTextField_meanForLenght	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_meanForLenght() {
		if (jTextField_meanForLenght == null) {
			jTextField_meanForLenght = new JTextField();
			jTextField_meanForLenght.setText("256");
			jTextField_meanForLenght.setBounds(new Rectangle(321, 72, 51, 19));
			
			jTextField_meanForLenght.addInputMethodListener(
					new InputMethodListener(){

						@Override
						public void caretPositionChanged(InputMethodEvent event) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void inputMethodTextChanged(
								InputMethodEvent event) {
							
							int value = Integer.valueOf(jTextField_meanForLenght.getText());
							
							if(value<256)jTextField_meanForLenght.setText("256");
							else if(value>2312)jTextField_meanForLenght.setText("2312");
						}

						
						
				
			        }
			);
				
			
			jTextField_meanForLenght.addActionListener(
					new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						
					}
			);
		}
		return jTextField_meanForLenght;
	}


	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
			jTextField1.setText(String.valueOf(node.getInterArTime()));
			jTextField1.setBounds(new Rectangle(320, 49, 51, 19));
		}
		return jTextField1;
	}


	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.setBounds(new Rectangle(9, 9, 636, 209));
			jTabbedPane.addTab("Configuration", null, getJPanelA(), null);
			jTabbedPane.addTab("View coverage", null, getJPanelB(), null);
			jTabbedPane.addChangeListener(new ChangeListener(){

				@Override
				public void stateChanged(ChangeEvent arg0) {
					
					if(jPanelB.isShowing()){
						

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
							dataset.addValue(value.floatValue(), "Node coverage(m).", distance);									
						}

						JFreeChart chart = ChartFactory.createLineChart(""," ","dB.",dataset,PlotOrientation.VERTICAL,true,true,false);
						jPanel = new ChartPanel(chart);
						jPanel.setBackground(Color.white);
						jPanel.setLayout(null);
						jPanel.setBounds(new Rectangle(6, 6, 602, 127));
						
						
						
						
						
						/*
						jPanel = new Chart_Panel();
						jPanel.setBackground(Color.white);
						jPanel.setLayout(null);
						jPanel.setBounds(new Rectangle(6, 6, 602, 127));
						System.out.println("energy="+(MainFrame.jPanel).jTextField_energy.getText());
						((Chart_Panel)jPanel).setEenergy_mW((MainFrame.jPanel).jTextField_energy.getText());
						((Chart_Panel)jPanel).repaint();
						*/
					} 					
				}

				
				
				
			});
		}
		return jTabbedPane;
	}

	private JPanel getJPanelA() {
		if (jPanelA == null) {
			jPanelA = new JPanel();
			jPanelA.setLayout(null);
			jPanelA.add(jLabel, null);
			jPanelA.add(getJTextField(), null);
			jPanelA.add(getJCheckBox(), null);
			jPanelA.add(jLabel_RTS_Threshold, null);
			jPanelA.add(getJTextField_RTS_Threshold(), null);
			jPanelA.add(jLabel_distr_ForInterArTime, null);
			jPanelA.add(jLabel_distr_ForLenght, null);
			jPanelA.add(getJComboBox_distr_InterArTime(), null);
			jPanelA.add(getJComboBox_distr_ForLenght(), null);
			jPanelA.add(jLabel1, null);
			jPanelA.add(jLabel_meanForLenght, null);
			jPanelA.add(getJTextField_meanForLenght(), null);
			jPanelA.add(getJTextField1(), null);
			jPanelA.add(getJScrollPane(), null);
			jPanelA.add(getJButton_Accept(), null);
		}
		return jPanelA;
	}


	/**
	 * This method initializes jPanelB	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelB() {
		if (jPanelB == null) {
			jPanelB = new JPanel();
			jPanelB.setLayout(null);
			jPanelB.add(getJPanel(), null);
		}
		return jPanelB;
	}


	/**
	 * This method initializes jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane(){
		
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(382, 7, 241, 137));
			jScrollPane.setViewportView(getJTable());
		}		
		return jScrollPane;
	}


	/**
	 * This method initializes jTable	
	 * 	
	 * @return javax.swing.JTable	
	 */
	public JTable getJTable(){
		
		//if (jTable_ForSensistivity == null){
			
			myTableModel_ForSensitivity = new MyTableModel_ForSensitivity(node.getSensitivity(),node.getTransmissionSpeed());			
			jTable_ForSensistivity = new JTable(myTableModel_ForSensitivity);			
		//}
		return jTable_ForSensistivity;
	}

	public void Update(ArrayList rates,ArrayList sens){
		
		
		jScrollPane.setViewportView(getJTable());
		jScrollPane.updateUI();
		jScrollPane.repaint();
		this.jTable_ForSensistivity.repaint();
	
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
				dataset.addValue(value.floatValue(), "Node coverage(m).", distance);									
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
			((Chart_Panel)jPanel).setEenergy_mW(jTextField_energy.getText());
			((Chart_Panel)jPanel).repaint();
			*/
		}
		return jPanel;
	}
	
}   
