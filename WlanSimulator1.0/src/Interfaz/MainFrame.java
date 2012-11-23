package Interfaz;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Diagram.GraficRepresentation;
import Diagram.ObjectPlotted;
import ElementsForSim.AccessPoint;
import ElementsForSim.BeaconFrame;
import ElementsForSim.Node;
import ElementsForSim.WirelessChannel;
import Main.main;
import Representation3D.Graphic3D;
import Representation3D.Line2;
import Representation3D.Point3D;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.String;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;

import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.awt.GridBagLayout;
import javax.swing.JList;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JCheckBox;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class MainFrame extends JFrame implements PropertyChangeListener{

	
	private static Dimension dim_aux = new Dimension(759,244);//dimensiones del jPanelForRepresentation
	
	private final static Color colors[] = {
			        Color.red, Color.blue, Color.green, Color.orange,
			        Color.cyan, Color.magenta, Color.darkGray, Color.yellow
	        };
	
	private static boolean dataSetPrepared = false;
	
	public static ArrayList<AccessPoint> ArrayOfAPs;
    public static ArrayList<Node>   ArrayOfNodes;
	
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel_setObstacles = null;
	private JPanel jPanelConfiguration = null;
	
	private JMenuBar jJMenuBar = null;
	private static JTabbedPane jTabbedPane = null;
	
	private JLabel jLabel_PhysicalLayer = null;
	public static JComboBox jComboBoxPhysicalLayer = null;
	private JLabel jLabelSimulationTime = null;
	private static JTextField jTextFieldSimulationTime = null;
	private static JComboBox jComboBoxRates = null;
	static ArrayList ListOfMaterials;  //  @jve:decl-index=0:
	
	
	
	String[] listPhysicalLayer = {"802.11"
			 ,"802.11a"
		 	 ,"802.11b"
	 	 	 ,"802.11g"
	 	 	 ,"802.11n"
	 	 	 		
			     };
	private JLabel JLabel_ConnectionMode = null;
	private JComboBox jComboBoxConnectionMode = null;
	private JLabel jLabel = null;
	

	private JLabel jLabel_NumAccesPoints = null;
	private static JTextField jTextField_NumberOfAP = null;
	private JButton jButton = null;
	private JButton jButton2 = null;
	private JLabel jLabel_NumOfNodes = null;
	private static JTextField jTextField_NumberOfNodes = null;
	private JButton jButton1 = null;
	private JButton jButton3 = null;
	private JScrollPane jScrollPane = null;
	private static JScrollPane jScrollPane1 = null;
	private JScrollPane jScrollPane_forTableObstacles = null;

	
	private static MyTableModel_ForPossition  myTableModel_ForPossition  = null;
	private static MyTableModel_ForMaterials  myTableModel_ForMaterials  = null;
	private static MyTableModel_ForObstacles  myTableModel_ForObstacles  = null;
	private static MyTableModel_ForResults    myTableModel_ForResults    = null;	
	private static MyTableModel_ForSimulation myTableModel_ForSimulation = null;
	private static MyTableModel_ForPreSimulation myTableModel_ForPreSimulation = null;  //  @jve:decl-index=0:
	
	
	public static JTable jTable_ForPossition = null;
	public static JTable jTable_ForObstacles = null;
	public static JTable JTable_ForMaterials = null;
	public static JTable jTable_ForSimulation = null;
	public static JTable jTable_ForPreSimulation = null;
	
	public static Configuration jPanel = null;
	private JPanel jPanel1 = null;
	private JTabbedPane jTabbedPane2 = null;
	private JPanel jPanelSetUpNodes = null;


	private static JComboBox jComboBox_1 = null;
	private static JComboBox jComboBox_2 = null;
	private static JComboBox jComboBox_3 = null;	
	private JButton jButton_Set = null;
	int numRowsOfTableObstacles=0;
	private JScrollPane jScrollPane_Materials = null;
	
	private JLabel jLabel_lisOfMaterials = null;
	
	private TaskForJProgressBar task;  //  @jve:decl-index=0:
	static DataSet dataSet = null;

	
	
	
	private JPanel jPanelResults = null;
	private JScrollPane jScrollPane2 = null;
	private static JScrollPane jScrollPaneForGraficRepresentation = null;
	private JScrollPane jScrollPaneForResults = null;
	//private JTable jTableForResults = null;
	private static GraficRepresentation jPanelForRepresentation = null;
	private JPanel jPanelFor3DRepresentation = null;
	private static JProgressBar jProgressBar = null;
	private static JButton jButtonRun = null;
	private JButton jButtonCancel = null;
	private JScrollPane jScrollPane21 = null;
	private static JTextArea jTextArea = null;
	
	static int index_X = 10;
	static int index_Y = 10;
	static int inc_Y = 50;
	
	private static Dimension dim = new Dimension(632,189); 
    
	private static ArrayList arrayOfObjectsForPlot  = new ArrayList();  //  @jve:decl-index=0:
    private static ArrayList arrayOfElementsPlotted = new ArrayList();

	private static JPanel jPanel_Graphic3D = null;

	private JButton jButton_Left = null;

	private JButton jButton_GraficUP = null;

	private JButton jButton_GraficDown = null;

	private JButton jButton_Right = null;

	private JButton jButton_RotateLeft = null;

	private JButton jButton_RotateRight = null;

	private JCheckBox jCheckBox = null;

	private JLabel jLabel_situation = null;

	private JScrollPane jScrollPaneForTableResults = null;

	private JTable jTable1 = null;

	private JTable jTable_ForResults = null;

	private JPanel jPanel_ColorDIF = null;

	private JLabel jLabel_ColorDIF = null;

	private JPanel jPanel_ColorSIF = null;

	private JLabel jLabel_ColorSIF = null;

	private JPanel jPanel_ColorTransmission = null;

	private JLabel jLabel_ColorTransmision = null;

	private JPanel jPanel_ColorBackoff = null;

	private JLabel jLabel_ColorBackoff = null;

	private JPanel jPanel_ColorTransACK = null;

	private JLabel jLabel_ColorACK = null;

	private JLabel jLabel_ColorACK1 = null;

	private JButton jButton_PositiveZoom = null;

	private JButton jButton_NegativeZoom = null;

	private JButton jButton_viewTroughput = null;

	private JButton jButton4 = null;

	private JButton jButton_viewUtilization = null;

	private JButton jButton_viewMediaAccessDelay = null;

	private JButton jButton_viewQueuingDelay = null;

	private JButton jButton_TotalPacketDelay = null;

	private JButton jButton_viewJitter = null;

	private JPanel jPanel_ColorBeaconFrame = null;

	private JLabel jLabel_ColorPink = null;

	private JCheckBox jCheckBox_viewLinks = null;

	private JLabel jLabel_viewLinks = null;

	private static boolean CancelStatus = false;
	
	public static boolean Cancel(){
		return CancelStatus;
	}
	
	static class DrawingPane extends JPanel{
		
		public DrawingPane(){
			this.setBackground(Color.WHITE);
		}
		protected void paintComponent(Graphics g){
			
			this.setBackground(Color.WHITE);
			boolean draw = false;
			for(int k=0;k<100;k++){
				
				if(draw)g.setColor(Color.black);
				else g.setColor(Color.white);
					
				g.fillRect(    			
                			k,11,k+1,1
                		   );
				k+=1;
				
				if(draw == true)draw=false;
				else draw=true;
			}
			
		}
	}
	
	
	
	
	public static void TextInFlow(String text) throws IOException{
		
		if(jTextArea != null){	 	
			    
			 jTextArea.append(text+(char)13+(char)10);
			    // Make sure the new text is visible, even if there
		        // was a selection in the text area.
			 jTextArea.setCaretPosition(jTextArea.getDocument().getLength());			   
			  
			}
	}
	
	
	public static void NewSimulation(){
		  
		    jScrollPane1.removeAll();
			myTableModel_ForPossition.removeAllRows();
			ArrayOfAPs.removeAll(ArrayOfAPs);
		    ArrayOfNodes.removeAll(ArrayOfNodes);
		    
		    jTextFieldSimulationTime.setText("1000");
		    jComboBoxPhysicalLayer.setSelectedIndex(0);
		    jComboBoxRates.addItem("1Mb/s");
			jComboBoxRates.addItem("2Mb/s");
			jTextField_NumberOfAP.setText(new String("0"));
			
			jTextField_NumberOfNodes.setText(new String("0"));
			
			jComboBox_1.removeAllItems();
			jComboBox_3.removeAllItems();
			
			myTableModel_ForObstacles.removeAllRows();
			
			jButtonRun.setEnabled(true);
			
			jProgressBar.setValue(0);
	}
	
	
	
	
	private void initColumnSizes(JTable table) {
			
		    MyTableModel_ForPossition model = (MyTableModel_ForPossition)table.getModel();
	        TableColumn column = null;
	        Component comp = null;
	        int headerWidth = 0;
	        int cellWidth = 0;
	        Object[] longValues = model.longValues;
	        TableCellRenderer headerRenderer =
	            table.getTableHeader().getDefaultRenderer();
	
	        for (int i = 0; i < model.getColumnCount(); i++) {
	        	
	            column = table.getColumnModel().getColumn(i);
	
	            comp = headerRenderer.getTableCellRendererComponent(
	                                 null, column.getHeaderValue(),
	                                 false, false, 0, 0);
	            headerWidth = comp.getPreferredSize().width;
	
	            comp = table.getDefaultRenderer(model.getColumnClass(i)).
	                             getTableCellRendererComponent(
	                                 table, longValues[i],
	                                 false, false, 0, i);
	            
	            cellWidth = comp.getPreferredSize().width;    
	
	            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
	        }
	 }
		
	
	
	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	static  JMenuItem subMenuSave;
	private JMenuBar getJJMenuBar() {
			
		if (jJMenuBar == null) {
			
			jJMenuBar = new JMenuBar();			
			JMenu menuFile    = new JMenu("File    ");
			JMenu menuHelp    = new JMenu("Help    ");
			
			final JMenuItem subMenuNewSimulation = new JMenuItem(" New simulation ");
			                subMenuSave = new JMenuItem(" Save results ");			
			final JMenuItem subMenuExit = new JMenuItem(" Exit        ");
			      
			
			
			subMenuNewSimulation.addActionListener(
		    		  new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent arg0){
								
								if (subMenuNewSimulation == arg0.getSource()){	
									
									jTabbedPane.setEnabledAt(1,false);
									NewSimulation();
								}
							}
		    		  }
		       );
			subMenuExit.setMnemonic(KeyEvent.VK_B);
			      subMenuExit.addActionListener(
			    		  new ActionListener(){
								@Override
								public void actionPerformed(ActionEvent arg0){
									
									if (subMenuExit == arg0.getSource()){	
										System.exit(0);
									}
								}
			    		  }
			       );
			      
		    subMenuSave.setEnabled(false);
			subMenuSave.addActionListener(
		    		  new ActionListener(){
							
							public void actionPerformed(ActionEvent arg0){
							      
								JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));							
								
							
								int selected = fileChooser.showSaveDialog(null);
								
								
										if (selected == JFileChooser.APPROVE_OPTION)
										{
										   
										   String nameFile = fileChooser.getSelectedFile().getAbsolutePath();
										   
										   if(nameFile != null){											   
											   
											OutputStream salida = null;
											
											try {
												salida = new FileOutputStream(nameFile.concat(".xls"));
											} catch (FileNotFoundException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										       
										    WritableWorkbook w = null;
										    
											try {
												w = Workbook.createWorkbook(salida);
											} catch (IOException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}	
											
										       WritableSheet s = w.createSheet("page 1", 0);
										       Label text = null;
										       Number number = null;
										       
										       
											   ArrayList parameters = WirelessChannel.collectingResults();
							
										       ArrayList row = null;
										    		
										    		for(int j = 0;j<parameters.size();j++){//filas
										    			
										    			row = (ArrayList) parameters.get(j);
										    			
										    			for(int k=0;k<row.size();k++){//columnas
										    				
										    				if(k == 0){
										    					
										    					text = new Label(k,j+1,(String)row.get(k));
										    					try {
																	s.addCell(text);
																} catch (RowsExceededException e) {
																	// TODO Auto-generated catch block
																	e.printStackTrace();
																} catch (WriteException e) {
																	// TODO Auto-generated catch block
																	e.printStackTrace();
																}
										    				}
										    				else{
										    					
										    					number = new Number (k,j+1,(Float)row.get(k));
										    					try {
																	s.addCell(number);
																} catch (RowsExceededException e) {
																	// TODO Auto-generated catch block
																	e.printStackTrace();
																} catch (WriteException e) {
																	// TODO Auto-generated catch block
																	e.printStackTrace();
																}
										    				}
										    				
										    			}		        	
										    		}
										    	
											   
											try {
												
												w.write();
												
											} catch (IOException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											
											try {
												w.close();
											} catch (WriteException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											} catch (IOException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
											    
											
											   File file = new File(nameFile.concat(".txt"));
											   PrintWriter writer = null;
												try{
													
													writer = new PrintWriter(file);
													
												}catch (FileNotFoundException e){													
													
													e.printStackTrace();
												}
											

											   writer.print(jTextArea.getText());											    
											   writer.close();
											   
											   
											   
											   
											   BufferedImage img = new BufferedImage(jPanelForRepresentation.getWidth(),
													                                 jPanelForRepresentation.getHeight(),
													                                 BufferedImage.TYPE_INT_RGB);
											   Graphics g = img.getGraphics();
											   jPanelForRepresentation.paint(g);
											   
											   File fichero = new File("foto.jpg");
											   String formato = "jpg";

											   // Escribimos la imagen en el archivo.
												try {
													ImageIO.write(img, formato, fichero);
												} catch (IOException e) {
													System.out.println("Error de escritura");
												}

											  }							  
										}
								 	
							}
					 }
		    );
		    
			menuFile.add(subMenuNewSimulation);
			menuFile.add(subMenuSave);      
			menuFile.add(subMenuExit);
			jJMenuBar.add(menuFile);
			menuHelp.setEnabled(false);
			jJMenuBar.add(menuHelp);
        }
		return jJMenuBar;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		
		if (jTabbedPane == null){
			
			
			jTabbedPane = new JTabbedPane();			
			
 		    jTabbedPane.addTab("Configuration", null, getJPanelConfiguration(), null);
		  
		    jTabbedPane.addTab("Results", null,getJPanelResults(), null);
			
			jTabbedPane.setEnabledAt(1,false);
			
			jTabbedPane.setBounds(new Rectangle(5, 5, 994, 686));
			
		}
		return jTabbedPane;
	}

	public static void reactiveJtabbedPane(){
		
		jTabbedPane.setEnabledAt(1,true);
	}
	
	
	/**
	 * This method initializes jTextFieldSimulationTime	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldSimulationTime() {
		if (jTextFieldSimulationTime == null) {
			jTextFieldSimulationTime = new JTextField();
			jTextFieldSimulationTime.setBounds(new Rectangle(131, 15, 50, 20));
			jTextFieldSimulationTime.setText("1000");
			jTextFieldSimulationTime.addInputMethodListener(new InputMethodListener(){
					
					public void inputMethodTextChanged(InputMethodEvent event){
						
						String txt = jTextFieldSimulationTime.getText();
						
					}

					@Override
					public void caretPositionChanged(InputMethodEvent arg0) {
						// TODO Auto-generated method stub
						
					}
			
			});
			
			jTextFieldSimulationTime.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent evt){
					
				
						String txt = jTextFieldSimulationTime.getText()+evt.getKeyChar();
						
					
				}
			});
			

		}
		return jTextFieldSimulationTime;
	}
	

	/**
	 * This method initializes jComboBoxRates	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxRates() {
		if (jComboBoxRates == null) {
			
			jComboBoxRates = new JComboBox();
			
			jComboBoxRates.addItem("1Mb/s");
			jComboBoxRates.addItem("2Mb/s");
			
			
			jComboBoxRates.setBounds(new Rectangle(132, 74, 93, 20));
		}
		return jComboBoxRates;
	}

	
	
	
	/**
	 * This method initializes jTextField_NumberOfAP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_NumberOfAP() {
		if (jTextField_NumberOfAP == null) {
			jTextField_NumberOfAP = new JTextField();
			jTextField_NumberOfAP.setText(new String("0"));
			jTextField_NumberOfAP.setBounds(new Rectangle(172,36,35,25));
		}
		return jTextField_NumberOfAP;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton(){
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("+");
			jButton.setBounds(new Rectangle(236, 35, 47, 26));
			jButton.addActionListener(
					new ActionListener(){
						
						private boolean add;
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							
							if (jButton == arg0.getSource()){
								
								
								myTableModel_ForPossition.addRow("Access point "+ArrayOfAPs.size(), new Integer(0),new Integer(0), new Integer(0), "          Click here");
								
								
							    try {
									 ArrayOfAPs.add(new AccessPoint(main.wirelessChannel,ArrayOfAPs.size(),0,0,0));
								} catch (BiffException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								jTextField_NumberOfAP.setText(String.valueOf(ArrayOfAPs.size()));
								
								jTable_ForPossition.setModel(myTableModel_ForPossition);
								ModifyComboBox();  
							}
						}	
					}
			);
		}
		return jButton;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2(){
		
		
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setText("-");
			jButton2.setBounds(new Rectangle(297, 36, 47, 26));
			jButton2.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0){
							
							
							if (
									jButton2 == arg0.getSource()
								){								
								
								if(ArrayOfAPs.size()!=0){
									
									
									//kitamos el ultimo punto de acceso añadido
									myTableModel_ForPossition.removeRow("Access point "+(ArrayOfAPs.size()-1));
									ArrayOfAPs.remove(ArrayOfAPs.size()-1);	
									jTextField_NumberOfAP.setText(String.valueOf(ArrayOfAPs.size()));
									jTable_ForPossition.setModel(myTableModel_ForPossition);
									ModifyComboBox();  
								}
											
							}
						}	
					}
			);
		}
		return jButton2;
		
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_NumberOfNodes() {
		if (jTextField_NumberOfNodes == null) {
			jTextField_NumberOfNodes = new JTextField();
			jTextField_NumberOfNodes.setText(new String("0"));
			jTextField_NumberOfNodes.setBounds(new Rectangle(172,78,35,25));
		}
		return jTextField_NumberOfNodes;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setText("+");
			jButton1.setBounds(new Rectangle(236, 77, 47, 26));
			jButton1.addActionListener(
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton1 == arg0.getSource()){
								
								myTableModel_ForPossition.addRow("Node "+ArrayOfNodes.size(), new Integer(0),new Integer(0), new Integer(0), "          Click here");
								
							
									try{
										
										ArrayOfNodes.add(new Node(main.wirelessChannel,ArrayOfNodes.size(),0,0,0));
									
									} catch (BiffException e){										
										e.printStackTrace();
									} catch (IOException e){										
										e.printStackTrace();
									}
								
								jTextField_NumberOfNodes.setText(String.valueOf(ArrayOfNodes.size()));
								jTable_ForPossition.setModel(myTableModel_ForPossition);
								ModifyComboBox();  
							}
						}	
					}
			);
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3(){
		
		if (jButton3 == null){
			jButton3 = new JButton();
			jButton3.setText("-");
			jButton3.setBounds(new Rectangle(297, 77, 47, 26));
			jButton3.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							
							if (jButton3 == arg0.getSource()){
								
								
								if(ArrayOfNodes.size()!=0){
																		
									
									//kitamos el ultimo punto de acceso añadido
									myTableModel_ForPossition.removeRow("Node "+(ArrayOfNodes.size()-1));
									ArrayOfNodes.remove(ArrayOfNodes.size()-1);
									jTextField_NumberOfNodes.setText(String.valueOf(ArrayOfNodes.size()));
									jTable_ForPossition.setModel(myTableModel_ForPossition);
									ModifyComboBox();  
								}
										
							}
						}	
					}
			);
		}
		return jButton3;
	}


	



	/**
	 * This method initializes jScrollPane1	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(new Rectangle(5,5, 646, 221));
			jScrollPane1.setViewportView(getJPanel());
		}
		return jScrollPane1;
	}



	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new Configuration();
			jPanel.setLayout(null);
		}
		return jPanel;
	}



	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(null);
			jPanel1.setBounds(new Rectangle(325, 6, 656, 230));
			jPanel1.setBackground(Color.WHITE);
			jPanel1.add(getJScrollPane1(), null);
		}
		return jPanel1;
	}


	/**
	 * This method initializes jTabbedPane2	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane2(){
		
		if (jTabbedPane2 == null) {
			jTabbedPane2 = new JTabbedPane();
			
			jTabbedPane2.addTab("Set nodes ", null, getJPanelSetUpNodes(), null);
			jTabbedPane2.addTab("Set obstacles ", null, getJPanelSetObstacles(), null);		
			jTabbedPane2.addTab("Load pre-simulation", null, getJPanel_preSimulation(), null);
			jTabbedPane2.setBounds(new Rectangle(16, 246, 968, 258));
			
		}
		return jTabbedPane2;
	}


	/**
	 * This method initializes jPanelSetUpNodes	
	 * 	
	 * @return javax.swing.JPanel	
	 */

	private JPanel getJPanelSetUpNodes() {
		
		if (jPanelSetUpNodes == null) {
			jPanelSetUpNodes = new JPanel();
			jPanelSetUpNodes.setLayout(null);
			jPanelSetUpNodes.add(jLabel, null);
			jPanelSetUpNodes.add(jLabel_NumAccesPoints, null);
			jPanelSetUpNodes.add(getJTextField_NumberOfAP(), null);
			jPanelSetUpNodes.add(getJButton(), null);
			jPanelSetUpNodes.add(getJButton2(), null);
			jPanelSetUpNodes.add(jLabel_NumOfNodes, null);
			jPanelSetUpNodes.add(getJTextField_NumberOfNodes(), null);
			jPanelSetUpNodes.add(getJButton1(), null);
			jPanelSetUpNodes.add(getJButton3(), null);
			jPanelSetUpNodes.add(getJScrollPane(), null);
		}
		return jPanelSetUpNodes;
	}



	



	/**
	 * This method initializes jScrollPane2	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_Materials() {
		if (jScrollPane_Materials == null){
			jScrollPane_Materials = new JScrollPane();
			jScrollPane_Materials.setBounds(new Rectangle(13, 69, 228, 147));
			jScrollPane_Materials.setViewportView(getJTable_Materials());
		}
		return jScrollPane_Materials;
	}



	/**
	 * This method initializes jTable_Materials	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable_Materials() {
		
		if (JTable_ForMaterials == null) {
		    
			ArrayList list = null;
		    
			try{
				list = getListOfMaterials();
			}catch (BiffException e){
				
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			}
			myTableModel_ForMaterials = new MyTableModel_ForMaterials(list);
			JTable_ForMaterials = new JTable(myTableModel_ForMaterials);
			
			
			JTable_ForMaterials.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					jButton_CongigurationObstacles.setEnabled(true);
				}
			});
			
		}
		return JTable_ForMaterials;
	}


	/**
	 * This method initializes jPanelResults	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelResults(){		
			
		if (jPanelResults == null){			
			
			jLabel_timeout = new JLabel();
			jLabel_timeout.setBounds(new Rectangle(208, 313, 111, 18));
			jLabel_timeout.setText("TIME OUT");
			jLabel_medium = new JLabel();
			jLabel_medium.setBounds(new Rectangle(471, 372, 127, 17));
			jLabel_medium.setText("Medium");
			jLabel_High = new JLabel();
			jLabel_High.setBounds(new Rectangle(471, 390, 125, 13));
			jLabel_High.setText("High");
			jLabel_weak = new JLabel();
			jLabel_weak.setBounds(new Rectangle(471, 355, 125, 17));
			jLabel_weak.setText("Weak");
			jLabel_obstacles = new JLabel();
			jLabel_obstacles.setBounds(new Rectangle(416, 337, 191, 18));
			jLabel_obstacles.setText(" Attenuation caused by obstacles");
			jLabelAssociationResponse = new JLabel();
			jLabelAssociationResponse.setBounds(new Rectangle(39, 353, 169, 15));
			jLabelAssociationResponse.setText("ASSOCIATION RESPONSE");
			jLabelRequestFrame = new JLabel();
			jLabelRequestFrame.setBounds(new Rectangle(40, 335, 97, 15));
			jLabelRequestFrame.setText("REQUEST FRAME");
			jLabelCTS = new JLabel();
			jLabelCTS.setBounds(new Rectangle(109, 314, 77, 15));
			jLabelCTS.setText("CTS");
			jLabel_RTS = new JLabel();
			jLabel_RTS.setBounds(new Rectangle(39, 313, 38, 16));
			jLabel_RTS.setText("RTS");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(420, 266, 283, 16));
			jLabel1.setText("(Right button to save picture)");
			jLabel_viewLinks = new JLabel();
			jLabel_viewLinks.setBounds(new Rectangle(770, 402, 93, 19));
			jLabel_viewLinks.setText("view links");
			jLabel_ColorPink = new JLabel();
			jLabel_ColorPink.setBounds(new Rectangle(207, 293, 154, 16));
			jLabel_ColorPink.setText("BEACON");
			jLabel_ColorACK1 = new JLabel();
			jLabel_ColorACK1.setBounds(new Rectangle(112, 274, 80, 13));
			jLabel_ColorACK1.setText("ACK");					
			jLabel_ColorBackoff = new JLabel();
			jLabel_ColorBackoff.setBounds(new Rectangle(108, 292, 80, 16));
			jLabel_ColorBackoff.setText("BACKOFF");
			jLabel_ColorTransmision = new JLabel();
			jLabel_ColorTransmision.setBounds(new Rectangle(207, 273, 159, 14));
			jLabel_ColorTransmision.setText("DATA");
			jLabel_ColorSIF = new JLabel();
			jLabel_ColorSIF.setBounds(new Rectangle(39, 293, 62, 16));
			jLabel_ColorSIF.setText("SIFS");
			jLabel_ColorDIF = new JLabel();
			jLabel_ColorDIF.setBounds(new Rectangle(39, 272, 61, 20));
			jLabel_ColorDIF.setText("DIFS");
			
			jLabel_situation = new JLabel();
			jLabel_situation.setBounds(new Rectangle(884, 403, 95, 17));
			jLabel_situation.setText("mark situation");
			jPanelResults = new JPanel();
			jPanelResults.setLayout(null);
			jPanelResults.add(getJScrollPaneForGraficRepresentation(), null);
			//jPanelResults.add(getJScrollPaneForResults(), null);
			jPanelResults.add(getJPanel_Graphic3D(), null);
			//jPanelResults.add(getJPanelFor3DRepresentation(), null);
			jPanelResults.add(getJButton_Left(), null);
			jPanelResults.add(getJButton_GraficUP(), null);
			jPanelResults.add(getJButton_GraficDown(), null);
			jPanelResults.add(getJButton_Right(), null);
			jPanelResults.add(getJButton_RotateLeft(), null);
			jPanelResults.add(getJButton_RotateRight(), null);
			jPanelResults.add(getJCheckBox(), null);
			jPanelResults.add(jLabel_situation, null);
			jPanelResults.add(getJPanel_ColorDIF(), null);
			jPanelResults.add(jLabel_ColorDIF, null);
			jPanelResults.add(getJPanel_ColorSIF(), null);
			jPanelResults.add(jLabel_ColorSIF, null);
			jPanelResults.add(getJPanel_ColorTransmission(), null);
			jPanelResults.add(jLabel_ColorTransmision, null);
			jPanelResults.add(getJPanel_ColorBackoff(), null);
			jPanelResults.add(jLabel_ColorBackoff, null);
			jPanelResults.add(getJPanel_ColorTransACK(), null);
			jPanelResults.add(jLabel_ColorACK1, null);
			jPanelResults.add(getJButton_PositiveZoom(), null);
			jPanelResults.add(getJButton_NegativeZoom(), null);
			jPanelResults.add(getJPanel_ColorBeaconFrame(), null);
			jPanelResults.add(jLabel_ColorPink, null);
			jPanelResults.add(getJCheckBox_viewLinks(), null);
			jPanelResults.add(jLabel_viewLinks, null);
			jPanelResults.add(getJButton_expand(), null);
			jPanelResults.add(getJButton_contract(), null);
			jPanelResults.add(jLabel1, null);
			jPanelResults.add(getJPanel_ColorRTS(), null);
			jPanelResults.add(jLabel_RTS, null);
			jPanelResults.add(getJPanel_ColorCTS(), null);
			jPanelResults.add(jLabelCTS, null);
			jPanelResults.add(getJPanel_ColorRequestFrame(), null);
			jPanelResults.add(jLabelRequestFrame, null);
			jPanelResults.add(getJPanel_ColorAssociationResponse(), null);
			jPanelResults.add(jLabelAssociationResponse, null);
			jPanelResults.add(getJPanel_yellow(), null);
			jPanelResults.add(getJPanel_orange(), null);
			jPanelResults.add(getJPanel_red(), null);
			jPanelResults.add(jLabel_obstacles, null);
			jPanelResults.add(jLabel_weak, null);
			jPanelResults.add(jLabel_High, null);
			jPanelResults.add(jLabel_medium, null);
			jPanelResults.add(getJTabbedPane_forResults(), null);
			jPanelResults.add(getJPanel_timeOut(), null);
			jPanelResults.add(jLabel_timeout, null);
			
			return jPanelResults;
		}
		
		return jPanelResults;
	}



	






	


/*
	
	private JScrollPane getJScrollPaneForResults() {
		if (jScrollPaneForResults == null) {
			jScrollPaneForResults = new JScrollPane();
			jScrollPaneForResults.setBounds(new Rectangle(28, 313, 1181, 195));
			jScrollPaneForResults.setViewportView(getJTableForResults());
		}
		return jScrollPaneForResults;
	}
*/


	/**
	 * This method initializes jTableForResults	
	 * 	
	 * @return javax.swing.JTable	
	 */
	/*
	private JTable getJTableForResults() {
		if (jTableForResults == null) {
			jTableForResults = new JTable();
		}
		return jTableForResults;
	}
   */


	

	/**
	 * This is the default constructor
	 */
	public MainFrame(){
		
		super();		
		this.ArrayOfAPs = new ArrayList();		
		this.ArrayOfNodes = new ArrayList();		
		initialize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setIconImage (new ImageIcon("icon.gif").getImage());
		setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		
		this.setSize(1012, 760);
		this.setJMenuBar(getJJMenuBar());
			task = new TaskForJProgressBar();
			task.addPropertyChangeListener(this);  
		    
		    this.setContentPane(getJContentPane());
		this.setTitle(" WiFiSim ");		
	}

	 /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        
    	
    	if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            //jProgressBar.setValue(progress);
           
            if(
            		progress >=100 
               ){
            	
            	this.setCursor(null);           	
    	    	MakeDiagram();
    	    	jScrollPaneForGraficRepresentation.updateUI();
    	    	repaint();
    	    	MakeRepresentation3D();
    	    	myTableModel_ForResults.setResults(WirelessChannel.collectingResults());
    	    	jPanelForRepresentation.resetFactorExpansion();
    	    	jButtonRun.setEnabled(true);
    	    	jButtonCancel.setEnabled(false);
    	    	jTabbedPane.setEnabledAt(1,true);
    	    	subMenuSave.setEnabled(true);    	    	
    	    	
            }
        } 
    }
    
    
    
	public static void updadateJProgressBar(int value){
		
		int aux1 = main.getCurrentTime();
        int aux2 = main.getSimulationTime()-1;            
        
        float progress = (float)(aux1*100)/(float)aux2;
        //setProgress((int)Math.min(progress, 100));
        
        System.out.println("actualiza "+value+" "+progress);
		jProgressBar.setValue((int)progress);
		/* 
		if(
				 progress >=100 
            ){
         	
			//this.setCursor(null);           	
 	    	MakeDiagram();
 	    	jScrollPaneForGraficRepresentation.updateUI();
 	    	//repaint();
 	    	MakeRepresentation3D();
 	    	myTableModel_ForResults.setResults(WirelessChannel.collectingResults());
 	    	jPanelForRepresentation.resetFactorExpansion();
 	    	jButtonRun.setEnabled(true);
 	    	//jButtonCancel.setEnabled(false);
 	    	jTabbedPane.setEnabledAt(1,true);
 	    	subMenuSave.setEnabled(true);      	
 	    	
         }
         */
	}	
	
      public static void MakeDiagram(){	
		
		
		if(ArrayOfAPs!=null && ArrayOfAPs.size()>0){
			
			    //...(RECUERDA)la clase GraficRepresentation ordena los iconos según se vayan introduciendo.
				//primero pintamos los puntos de acceso:
			   
			 
			    ArrayList<Node> allNodes =  (ArrayList<Node>) ((ArrayList)WirelessChannel.getNodes()).clone();
		
			    
			    
				for(int i=0;i<ArrayOfAPs.size();i++){	
					
					    addObjectPlotted(ArrayOfAPs.get(i).getObjectPlotted());
				        ArrayList<Node> ConnectedNodes = ArrayOfAPs.get(i).getConectedNodes();
				
				        for(int j=0;j<ConnectedNodes.size();j++){
						
				        	allNodes.remove(ConnectedNodes.get(j));
				        	addObjectPlotted(ConnectedNodes.get(j).getObjectPlotted());						      
						}
				}	
				
				//finalmente en el array allNodes nos quedaran los nodos que no han conseguido 
				//conectarse a ningun nodo.
				for(int i=0;i<allNodes.size();i++){
					
					addObjectPlotted(allNodes.get(i).getObjectPlotted());
				}
				
				jPanelForRepresentation.setArrayOfObjectsForPlot(arrayOfObjectsForPlot);				
		}
		
		
		if(dim_aux.width<(60+Integer.parseInt(jTextFieldSimulationTime.getText()))){
			dim_aux.width = 60+20+Integer.parseInt(jTextFieldSimulationTime.getText());
			jPanelForRepresentation.setPreferredSize(dim_aux);
		}
		if(dim_aux.height<(45+(arrayOfObjectsForPlot.size()*50))){
			dim_aux.height = 45+(arrayOfObjectsForPlot.size()*50);
		}		
		jPanelForRepresentation.setPreferredSize(dim_aux);
		jPanelForRepresentation.setSize(dim_aux);
		
	  }	
    
      public static void MakeRepresentation3D(){
    	  
    	  if(ArrayOfAPs!=null && ArrayOfAPs.size()>0){
    		// ... A continuación colocamos en el gráfico 3D los elementos que queremos representar 
    			
    			for(int i=0;i<ArrayOfAPs.size();i++){
    				
    				AccessPoint APaux = (AccessPoint) ArrayOfAPs.get(i);
    				ArrayList possition = APaux.getPossition();
    				((Graphic3D) jPanel_Graphic3D).AddPoint3D_toGraphic("AP_"+APaux.getID(),
    										   (double)(Integer)possition.get(0),
    										   (double)(Integer)possition.get(1),
    										   (double)(Integer)possition.get(2)
    						                   );
    			}
    			for(int i=0;i<ArrayOfNodes.size();i++){
    				
    				Node Nodeaux = (Node) ArrayOfNodes.get(i);
    				ArrayList possition = Nodeaux.getPossition();
    				((Graphic3D) jPanel_Graphic3D).AddPoint3D_toGraphic("Node_"+Nodeaux.getIdNode(),
    						                   (double)(Integer)possition.get(0),
    						                   (double)(Integer)possition.get(1),
    						                   (double)(Integer)possition.get(2)
    						                   );
    			}
  
    			//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    			//Ahora inserto los obstáculos.
    			ArrayList ListOfObstacles = WirelessChannel.getListOfObstacles();
    			ArrayList CopyListOfObstacles = (ArrayList) ListOfObstacles.clone(); 
    			
    			for(int t=0;t<CopyListOfObstacles.size();t++){
    				String aux=(String) ((ArrayList)CopyListOfObstacles.get(t)).get(0); 
    				if(aux.equals(" ")){
    					CopyListOfObstacles.remove(t);
    				}
    			}
    			while(CopyListOfObstacles.size()>0){
    				
    				String deviceA = (String) ((ArrayList)CopyListOfObstacles.get(0)).get(0); 
    				ArrayList possitionDeviceA = WirelessChannel.getPossition(deviceA);
    				
    				String deviceB = (String) ((ArrayList)CopyListOfObstacles.get(0)).get(3);
    				ArrayList possitionDeviceB = WirelessChannel.getPossition(deviceB);
    				
    				ArrayList obstacles = new ArrayList();
    				
    				
    				int distanceX = (Integer)possitionDeviceA.get(0)-(Integer)possitionDeviceB.get(0);
		    	    int distanceY = (Integer)possitionDeviceA.get(1)-(Integer)possitionDeviceB.get(1);
		    	    int distanceZ = (Integer)possitionDeviceA.get(2)-(Integer)possitionDeviceB.get(2);
		    	    
		    	    float distance = (float) Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2)+Math.pow(distanceZ,2));		    
		    	      				
    				
    				float distanceBetweenDevices = distance;
    				
    				for(int i=0;i<CopyListOfObstacles.size();i++){
    					
    					boolean getObstacle=false;
    					if(((ArrayList)CopyListOfObstacles.get(0)).get(0).equals(deviceA)){
    						
    						if(((ArrayList)CopyListOfObstacles.get(0)).get(3).equals(deviceB)){
    							getObstacle=true;
    						}
    					}
    					if(((ArrayList)CopyListOfObstacles.get(0)).get(0).equals(deviceB)){
    						
    						if(((ArrayList)CopyListOfObstacles.get(0)).get(3).equals(deviceA)){
    							getObstacle=true;
    						}
    					}
    				
    					if(getObstacle == true){    						
    						    						
    						obstacles.add(CopyListOfObstacles.get(0));
    						CopyListOfObstacles.remove(0);
    						i--;
    					}
    				}
    		   
    		  //a partir de aquí en el array obstacles tenemos todos los obstáculos entre el deviceA y el deviceB 
    				
    	  if(obstacles.size()>0){
    		  
    					ArrayList direction = new ArrayList();
						  direction.add(0,(Integer)possitionDeviceB.get(0)-(Integer)possitionDeviceA.get(0));
						  direction.add(1,(Integer)possitionDeviceB.get(1)-(Integer)possitionDeviceA.get(1));
						  direction.add(2,(Integer)possitionDeviceB.get(2)-(Integer)possitionDeviceA.get(2));
					
						float moduleOfVectDirection = (float) Math.sqrt(Math.pow((Integer)direction.get(0),2)+
								                                		Math.pow((Integer)direction.get(1),2)+
								                                		Math.pow((Integer)direction.get(2),2)); 
				
				//sacamos el vector unitario de la direccion:
				 float aux1 = (Integer)direction.get(0)/(Float)moduleOfVectDirection;
				 float aux2 = (Integer)direction.get(1)/(Float)moduleOfVectDirection;
				 float aux3 = (Integer)direction.get(2)/(Float)moduleOfVectDirection;
				 
				 direction.clear();
			     direction.add(0,aux1);
			     direction.add(1,aux2);
			     direction.add(2,aux3);
			
			     ArrayList possitionObstacles = new ArrayList();
			      
			    for(int indexForObtacles=0;indexForObtacles<obstacles.size();indexForObtacles++){
			    	 
			    	 float DistanceFromNodeA = Float.parseFloat((String) ((ArrayList)obstacles.get(indexForObtacles)).get(1));//en la posicion 1 se guarda la dsitancia a la que se encuentra el obstaculo del primer nodo
				     possitionObstacles.add(0,DistanceFromNodeA*(Float)direction.get(0)+(Integer)possitionDeviceA.get(0));
				     possitionObstacles.add(1,DistanceFromNodeA*(Float)direction.get(1)+(Integer)possitionDeviceA.get(1));
				     possitionObstacles.add(2,DistanceFromNodeA*(Float)direction.get(2)+(Integer)possitionDeviceA.get(2));
				     
				     
			    	
			    	//ahora vamos a elegir el color...
			    	        String typeOfObstacle = (String)((ArrayList)obstacles.get(indexForObtacles)).get(2); 
						    //a partir del tipo de obstaculo colocado debemos calcular su impedancia.
						    
						    float impedance = 0;
							try {
								impedance = WirelessChannel.ImpedacedForMaterial(typeOfObstacle);
							} catch (BiffException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						     
						     Color color;
						     
						     if(impedance<33){
						    	 
						    	 color=Color.GREEN;
						     }else if(impedance<60){
						    	 
						    	 color=Color.BLUE;
						     }else{
						    	 
						    	 color=Color.red;
						     }			     			     
						    
						     ((Graphic3D) jPanel_Graphic3D).AddPoint3D_toGraphic(typeOfObstacle,
					                   (double)(Float)possitionObstacles.get(0),
					                   (double)(Float)possitionObstacles.get(1),
					                   (double)(Float)possitionObstacles.get(2),
					                   color
					                   );
			    }
    				}
    			}
    			
    			
    			((Graphic3D)jPanel_Graphic3D).autoZoom();
    			((Graphic3D)jPanel_Graphic3D).repaint();
    			
    	  }
    	
      } 
      
	  static public void addObjectPlotted(ObjectPlotted objectPlotted){
	  	
		  	objectPlotted.x = index_X;
		  	objectPlotted.y = index_Y;
		  	dim.height = index_Y+55;
		  	
		  	//jPanelForRepresentation.setPreferredSize(new Dimension(dim.width,dim.height));
		      
		  	index_Y+=inc_Y;    	
		  	
		  	arrayOfObjectsForPlot.add(objectPlotted);
	  }    
    
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTabbedPane(), null);
		}
		return jContentPane;
	}
	
	
	private JPanel getJPanelConfiguration() {
		
		if (jPanelConfiguration == null) {
			
			jLabel_Rates = new JLabel();
			jLabel_Rates.setBounds(new Rectangle(17, 76, 54, 18));
			jLabel_Rates.setText("Rates :");
			jLabel_Ghz = new JLabel();
			jLabel_Ghz.setBounds(new Rectangle(284, 41, 30, 21));
			jLabel_Ghz.setText("Ghz");
			jLabel_Ghz.setVisible(false);
			jLabel_NumOfNodes = new JLabel();
			jLabel_NumOfNodes.setText("Number of nodes:");
			jLabel_NumOfNodes.setBounds(new Rectangle(10, 85, 112, 16));
			jLabel_NumAccesPoints = new JLabel();
			jLabel_NumAccesPoints.setText("Number of access points:");
			jLabel_NumAccesPoints.setBounds(new Rectangle(8, 41, 147, 16));
			jLabel = new JLabel();
			//jLabel.setText("Set up nodes:");
			jLabel.setBounds(new Rectangle(7, 10, 91, 18));
			JLabel_ConnectionMode = new JLabel();
			JLabel_ConnectionMode.setBounds(new Rectangle(16, 106, 112, 16));
			JLabel_ConnectionMode.setText("Connection mode :");
			jLabelSimulationTime = new JLabel();
			jLabelSimulationTime.setBounds(new Rectangle(13, 15, 113, 16));
			jLabelSimulationTime.setText("Simulation (us):");
			jLabel_PhysicalLayer = new JLabel();
			jLabel_PhysicalLayer.setBounds(new Rectangle(14, 44, 102, 16));
			jLabel_PhysicalLayer.setText("Physical layer : ");
			jPanelConfiguration = new JPanel();
			jPanelConfiguration.setLayout(null);
			jPanelConfiguration.add(jLabel_PhysicalLayer, null);
			jPanelConfiguration.add(getJComboBoxPhysicalLayer(), null);
			jPanelConfiguration.add(jLabelSimulationTime, null);
			jPanelConfiguration.add(getJTextFieldSimulationTime(), null);
			jPanelConfiguration.add(getJComboBoxRates(), null);
			jPanelConfiguration.add(JLabel_ConnectionMode, null);
			jPanelConfiguration.add(getJComboBoxConnectionMode(), null);
			jPanelConfiguration.add(getJPanel1(), null);
			jPanelConfiguration.add(getJTabbedPane2(), null);
			jPanelConfiguration.add(getJProgressBar(), null);
			jPanelConfiguration.add(getJButtonRun(), null);
			jPanelConfiguration.add(getJButtonCancel(), null);
			jPanelConfiguration.add(getJScrollPane21(), null);
			jPanelConfiguration.add(getJComboBox_Frequency(), null);
			jPanelConfiguration.add(jLabel_Ghz, null);
			jPanelConfiguration.add(jLabel_Rates, null);
				
			
			
			/*
			JLabel_InfraestructureMode = new JLabel();
			JLabel_InfraestructureMode.setBounds(new Rectangle(15, 52, 128, 17));
			JLabel_InfraestructureMode.setText("Connection mode");
			jLabel_SimulationTime = new JLabel();
			jLabel_SimulationTime.setBounds(new Rectangle(15, 21, 121, 19));
			jLabel_SimulationTime.setText("Simulation time(us):");
			jLabel_Tittle = new JLabel();
			jLabel_Tittle.setBounds(new Rectangle(353, 3, 134, 16));
			jLabel_Tittle.setText("Version Beta ...");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);			
			jContentPane.add(jLabel_Tittle, null);
			jContentPane.add(jLabel_SimulationTime, null);
			jContentPane.add(JLabel_InfraestructureMode, null);
			jContentPane.add(getJComboBox(), null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJButton_SelectPossitionNodes(), null);
			jContentPane.add(jLabel_PhysicalLayer, null);
			jContentPane.add(getJComboBox1(), null);
			jContentPane.add(getJComboBox2(), null);
			jContentPane.add(getJButton_Cancel(), null);
			jContentPane.add(getJButton_Star(), null);
			
			*/
		}
		return jPanelConfiguration;
	}
	
	
	private JComboBox getJComboBoxPhysicalLayer(){
		
		if (jComboBoxPhysicalLayer == null){
			
			jComboBoxPhysicalLayer = new JComboBox(listPhysicalLayer);
			jComboBoxPhysicalLayer.setBounds(new Rectangle(132,41 ,93, 20));
			jComboBoxPhysicalLayer.setSelectedIndex(0); 
			
			jComboBoxPhysicalLayer.addActionListener(
					
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0){
							
							String selectedItem = String.valueOf(jComboBoxPhysicalLayer.getSelectedIndex());
				
							jComboBoxRates.removeAllItems();
							if(selectedItem.equals("0")){								
								
								jComboBoxRates.addItem("1Mb/s");
								jComboBoxRates.addItem("2Mb/s");
								
								jComboBox_Frequency.setVisible(false);
								jLabel_Ghz.setVisible(false);
								
							}else if(selectedItem.equals("1")){
								
								
								jComboBoxRates.addItem("6Mb/s");								
								jComboBoxRates.addItem("9Mb/s");
								jComboBoxRates.addItem("12Mb/s");
								jComboBoxRates.addItem("18Mb/s");
								jComboBoxRates.addItem("24Mb/s");
								jComboBoxRates.addItem("36Mb/s");
								jComboBoxRates.addItem("48Mb/s");
								jComboBoxRates.addItem("54Mb/s");
								
								jComboBox_Frequency.setVisible(false);
								jLabel_Ghz.setVisible(false);
							}
							if(selectedItem.equals("2")){
								
								
								jComboBoxRates.addItem("1Mb/s");
								jComboBoxRates.addItem("2Mb/s");
								jComboBoxRates.addItem("5.5Mb/s");
								jComboBoxRates.addItem("11Mb/s");
								
								jComboBox_Frequency.setVisible(false);
								jLabel_Ghz.setVisible(false);
							}else if(selectedItem.equals("3")){
								
								
								jComboBoxRates.addItem("6Mb/s");								
								jComboBoxRates.addItem("9Mb/s");
								jComboBoxRates.addItem("12Mb/s");
								jComboBoxRates.addItem("18Mb/s");
								jComboBoxRates.addItem("24Mb/s");
								jComboBoxRates.addItem("36Mb/s");
								jComboBoxRates.addItem("48Mb/s");
								jComboBoxRates.addItem("54Mb/s");
								
								jComboBox_Frequency.setVisible(false);
								jLabel_Ghz.setVisible(false);
						    }else if(selectedItem.equals("4")){
							
							
							jComboBoxRates.addItem("1Mb/s");								
							jComboBoxRates.addItem("6Mb/s");
							jComboBoxRates.addItem("11Mb/s");
							jComboBoxRates.addItem("54Mb/s");
							jComboBoxRates.addItem("108Mb/s");
							jComboBoxRates.addItem("130Mb/s");
							jComboBoxRates.addItem("150Mb/s");
							jComboBoxRates.addItem("300Mb/s");
							
							jComboBox_Frequency.setVisible(true);
							jLabel_Ghz.setVisible(true);
						}
							
							
							
							   
							    //Ahora vamos a colocar en cada nodo la lista de velocidades posibles y sus respectivas sensibilidades
							     
							     
							    
							     
							     
							     String type = String.valueOf(MainFrame.jComboBoxPhysicalLayer.getSelectedItem());
								 int numberOfRates = -1;
							     
								 
								 
							 
							     ArrayList Aux = main.getRatesAndSensitivityForPhysicalTechnology(type);
							     ArrayList TransmissionSpeed = new ArrayList();
							     ArrayList Sensitivity = new ArrayList();
							     
							     for(int t=0;t<Aux.size();t++){
							    	 
							    	 TransmissionSpeed.add(((ArrayList)Aux.get(t)).get(0));
							    	 Sensitivity.add(((ArrayList)Aux.get(t)).get(1));
							     }							     
							     
							     //NOTA : VAMOS A ACTUALIZAR LOS DISPOSITIVOS
							     //PARA QUE ACTUALICEN LAS TABLAS DE SENSIBILIDAD AL MEDIO
							     for(int i=0;i<ArrayOfNodes.size();i++){							    	 
							    	 
							    	 ArrayOfNodes.get(i).Update(TransmissionSpeed,Sensitivity);							    	 
							     }
							     
							     for(int j=0;j<ArrayOfAPs.size();j++){
							    	 
							    	 ArrayOfAPs.get(j).Update(TransmissionSpeed,Sensitivity);
							     }	
							     repaint();
						}	
					}
			);
		}
		return jComboBoxPhysicalLayer;
	}


	
	/**
	 * This method initializes jComboBoxConnectionMode	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBoxConnectionMode() {
		
		if (jComboBoxConnectionMode == null) {
			
			String[] conectionTypes = {"DCF"};
			jComboBoxConnectionMode = new JComboBox(conectionTypes);	
			jComboBoxConnectionMode.setBounds(new Rectangle(132, 106, 93, 20));
			jComboBoxConnectionMode.setSelectedIndex(0); 
		}
		return jComboBoxConnectionMode;
	}
	
   private void ModifyComboBox(){
		
		jComboBox_1.removeAllItems();
		jComboBox_3.removeAllItems();
		int numRows = ArrayOfAPs.size()+ArrayOfNodes.size();
		for(int i=0;i<ArrayOfNodes.size()+ArrayOfAPs.size();i++){				
			
			jComboBox_1.addItem(jTable_ForPossition.getValueAt(i,0));
			jComboBox_3.addItem(jTable_ForPossition.getValueAt(i,0));
		}
	}
   
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(360, 11, 600, 203));
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}
	
	private JTable getJTable(){
		
		if (jTable_ForPossition == null) {
			
			myTableModel_ForPossition = new MyTableModel_ForPossition();
			jTable_ForPossition = new JTable(myTableModel_ForPossition);		
			
			jTable_ForPossition.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					int fila = jTable_ForPossition.rowAtPoint(e.getPoint());
					int columna = jTable_ForPossition.columnAtPoint(e.getPoint());
					
					String typeOfRow =  (String) myTableModel_ForPossition.getValueAt(fila,0);
					
					
					if(!typeOfRow.equals(" ")){
									if(columna == 4){
											   if(typeOfRow.charAt(0)=='A'){//vamos a modificar un punto de acceso
												
												
												//buscar en el array de APs el ap a modificar						
												
												int idAP = Integer.parseInt(String.valueOf(typeOfRow.charAt(typeOfRow.length()-1)));
												
												//mostrar la ventana para modificar puntos de acceso
												AccessPoint APselected = null; 
												
												for(int i=0;i<ArrayOfAPs.size();i++){
													
													if(ArrayOfAPs.get(i).getID()==idAP){
														
														APselected = ArrayOfAPs.get(i);
														
													}
												}
												
												
												jScrollPane1.removeAll();
												
												JPanel configurationPanelForAP = null;
												if(APselected.accessPointConfigurationPanel==null){
													
													configurationPanelForAP = new AccessPointConfigurationPanel(APselected);					
													APselected.accessPointConfigurationPanel = (AccessPointConfigurationPanel) configurationPanelForAP;
												}
											    else{													
													
													configurationPanelForAP = APselected.getConfigurationPanel();
												}
												
												jScrollPane1.add(configurationPanelForAP);
												
												jScrollPane1.updateUI();
												
												//Recover los datos de la ventana
												//modificar el punto de acceso encontrado en el primer punto
												//cerrar la ventana, poner visible a false
									           }
											   else{												    
													
													
													int idNode = Integer.parseInt(String.valueOf(typeOfRow.charAt(typeOfRow.length()-1)));
													
													//mostrar la ventana para modificar puntos de acceso
													Node NodeSelected = null; 
													
													for(int i=0;i<ArrayOfNodes.size();i++){
														
														if(ArrayOfNodes.get(i).getIdNode()==idNode){
															
															NodeSelected = ArrayOfNodes.get(i);
															
														}
													}
													
													
													jScrollPane1.removeAll();						
													
													
													JPanel configurationPanelForNode = null;
													if(NodeSelected.nodeConfigurationPanel == null){
														
														configurationPanelForNode = new NodeConfigurationPanel(NodeSelected);					
														NodeSelected.nodeConfigurationPanel = (NodeConfigurationPanel) configurationPanelForNode;
													}
													else{
														
														configurationPanelForNode = NodeSelected.getNodeConfigurationPanel();
													}													
													
													jScrollPane1.add(configurationPanelForNode);
													
													jScrollPane1.updateUI();	
													  
											   }
											}					
						}
				}
			});	
			
			initColumnSizes(jTable_ForPossition);
		}
		return jTable_ForPossition;
	}
	
	
	
	private JPanel getJPanelSetObstacles() {
		
		if (jPanel_setObstacles == null) {
			jLabel_lisOfMaterials = new JLabel();
			jLabel_lisOfMaterials.setBounds(new Rectangle(12, 30, 104, 15));
			jLabel_lisOfMaterials.setText("List of materials:");
			jPanel_setObstacles = new JPanel();
			jPanel_setObstacles.setLayout(null);
			
			try {
				
				this.ListOfMaterials = getListOfMaterials();
			
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			jPanel_setObstacles.add(getJScrollPane_forTableObstacles(), null);
			jPanel_setObstacles.add(getJComboBox_1(), null);
			jPanel_setObstacles.add(getJComboBox_2(), null);
			jPanel_setObstacles.add(getJComboBox_3(), null);
			jPanel_setObstacles.add(getJButton_Set(), null);			
			jPanel_setObstacles.add(getJScrollPane_Materials(), null);
			jPanel_setObstacles.add(jLabel_lisOfMaterials, null);
			jPanel_setObstacles.add(getJButton_QuitObstacle(), null);
			jPanel_setObstacles.add(getJButton_CongigurationObstacles(), null);
		}
		return jPanel_setObstacles;
	}
	private JScrollPane getJScrollPane_forTableObstacles() {
		
		if (jScrollPane_forTableObstacles == null) {
			jScrollPane_forTableObstacles = new JScrollPane();
			jScrollPane_forTableObstacles.setBounds(new Rectangle(250, 66, 700, 154));
			jScrollPane_forTableObstacles.setViewportView(getJTable_Obstacles());
		}
		return jScrollPane_forTableObstacles;
	}
	
	
	private int rowSelectedInTableObstacles;
	private JTable getJTable_Obstacles(){
		
		if (jTable_ForObstacles == null){
			
			myTableModel_ForObstacles = new MyTableModel_ForObstacles();
			jTable_ForObstacles = new JTable(myTableModel_ForObstacles);
			jTable_ForObstacles.setBounds(new Rectangle(400, 66, 564, 154));			
			
			jTable_ForObstacles.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent e)
				{
					int fila = jTable_ForObstacles.rowAtPoint(e.getPoint());
					int columna = jTable_ForObstacles.columnAtPoint(e.getPoint());
					
					rowSelectedInTableObstacles = fila;
					
					if(columna == 4){						
					
					updateDevices();//con esta función actualizamos las posiciones de los dispositivos
					               //con las presentes en la tabla de la pestaña set nodes.
						
					String typeOfRow =  (String) myTableModel_ForObstacles.getValueAt(fila,0);					
					
					if(!typeOfRow.equals(" ")){
									
						DefaultCategoryDataset dataset = new DefaultCategoryDataset();
						Float value;
						String distance;
						double signalLoss;
						
						//aqui hay que mirar quien es el device A y coger 
						//la energia ke irradia
						
						 float Energy_mW=0;
						 
						 if(typeOfRow.charAt(0)=='A'){//vamos a modificar un punto de acceso
								
								
								//buscar en el array de APs el ap a modificar						
								
								int idAP = Integer.parseInt(String.valueOf(typeOfRow.charAt(typeOfRow.length()-1)));
								
								//mostrar la ventana para modificar puntos de acceso
								AccessPoint APaux = null; 
								
								for(int i=0;i<ArrayOfAPs.size();i++){
									
									if(ArrayOfAPs.get(i).getID()==idAP){
										
										APaux = ArrayOfAPs.get(i);
										Energy_mW = APaux.getEnergyEmitedmW();
										break;
									}
								}
														
					    }
						else{
							
							int idNode = Integer.parseInt(String.valueOf(typeOfRow.charAt(typeOfRow.length()-1)));
							
							//mostrar la ventana para modificar puntos de acceso
							Node nodeAux = null; 
							
							for(int i=0;i<ArrayOfNodes.size();i++){
								
								if(ArrayOfNodes.get(i).getIdNode()==idNode){
									
									nodeAux = ArrayOfNodes.get(i);
									Energy_mW = nodeAux.getEnergyEmitedmW();
									break;
								}
							}						
							
						}					
						
						
						
						
						
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
							 f = (float) Float.valueOf((String) MainFrame.jComboBox_Frequency.getSelectedItem());								
							 break;		
						 }	
				
						
						String distanceOfObstacleString =  (String) myTableModel_ForObstacles.getValueAt(fila,1);
						int  distanceOfOstacle = Integer.valueOf(distanceOfObstacleString);  
						
	
						String material = (String) myTableModel_ForObstacles.getValueAt(fila,2);
						float attenuationByObstacle = 0;
			    		for(int j=0;j<ListOfMaterials.size();j++){
			    			   
		    			   if(((ArrayList)ListOfMaterials.get(j)).get(0).equals(material)){
		    				   
		    				   Float aux = (Float) ((ArrayList)ListOfMaterials.get(j)).get(1);
		    				   attenuationByObstacle = Float.valueOf(((Float)((ArrayList)ListOfMaterials.get(j)).get(1)));
		    				   break;
		    			   }
			    		}
						
			    		//  Cogeremos los dos dispositivos implcados y calcularemos la 
			    		//distancia entre ellos.
			    		//  Para esta distancia tomaremos 15 puntos y calcularemos el descenso
			    		//de potencia en cada uno de ellos 
			    		fila = jTable_ForObstacles.rowAtPoint(e.getPoint());
						columna = jTable_ForObstacles.columnAtPoint(e.getPoint());
						
						String typeOfRow_A =  (String) myTableModel_ForObstacles.getValueAt(fila,0);
						String typeOfRow_B =  (String) myTableModel_ForObstacles.getValueAt(fila,3);
						int x_A = 0,y_A = 0,z_A = 0;
						int x_B = 0,y_B = 0,z_B = 0;
						int id_deviceA;
						int id_deviceB;
						ArrayList possition;
						
						if(!typeOfRow_A.equals(" ")){
							
							if(columna == 4){								
								
									   if(typeOfRow_A.charAt(0)=='A'){//vamos a modificar un punto de acceso
													
										//buscar en el array de APs el ap.					
										
										id_deviceA = Integer.parseInt(String.valueOf(typeOfRow_A.charAt(typeOfRow_A.length()-1)));
										
										
										AccessPoint APselected = null; 
										
										for(int i=0;i<ArrayOfAPs.size();i++){
											
											if(ArrayOfAPs.get(i).getID() == id_deviceA){
												
												APselected = ArrayOfAPs.get(i);
												possition = APselected.getPossition();
												x_A = (Integer) possition.get(0);
												y_A = (Integer) possition.get(1);
												z_A = (Integer) possition.get(2);
											}
										}									
						
									   }
									   else{//tenemos un nodo en la primera columna												
												
											
											id_deviceA = Integer.parseInt(String.valueOf(typeOfRow_A.charAt(typeOfRow_A.length()-1)));
											
											Node Nodeselected = null; 
											
											for(int i=0;i<ArrayOfNodes.size();i++){
												
												if(ArrayOfNodes.get(i).getIdNode() == id_deviceA){
													
													Nodeselected = ArrayOfNodes.get(i);
													possition = Nodeselected.getPossition();
													x_A = (Integer) possition.get(0);
													y_A = (Integer) possition.get(1);
													z_A = (Integer) possition.get(2);
												}
											}									
							
										 }
									   
									   
									   
									   if(typeOfRow_B.charAt(0)=='A'){//vamos a modificar un punto de acceso
											
											
											//buscar en el array de APs el ap					
											
											id_deviceB = Integer.parseInt(String.valueOf(typeOfRow_B.charAt(typeOfRow_B.length()-1)));
											
											
											AccessPoint APselected = null; 
											
											for(int i=0;i<ArrayOfAPs.size();i++){
												
												if(ArrayOfAPs.get(i).getID() == id_deviceB){
													
													APselected = ArrayOfAPs.get(i);
													possition = APselected.getPossition();
													x_B = (Integer) possition.get(0);
													y_B = (Integer) possition.get(1);
													z_B = (Integer) possition.get(2);
												}
											}									
							
										   }
										   else{//tenemos un nodo en la primera columna												
													
												
												id_deviceB = Integer.parseInt(String.valueOf(typeOfRow_B.charAt(typeOfRow_B.length()-1)));
												
												Node Nodeselected = null; 
												
												for(int i=0;i<ArrayOfNodes.size();i++){
													
													if(ArrayOfNodes.get(i).getIdNode() == id_deviceB){
														
														Nodeselected = ArrayOfNodes.get(i);
														possition = Nodeselected.getPossition();
														x_B = (Integer) possition.get(0);
														y_B = (Integer) possition.get(1);
														z_B = (Integer) possition.get(2);
													}
												}									
								
											 }
							}
						
					    }
						//Ahora calcularemos la distancia entre los dispositivos.
						
						int distanceX = x_A-x_B;
					    int distanceY = y_A-y_B;
					    int distanceZ = z_A-z_B;
					    float distanceBetweenDevicesAandB = (float) Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2)+Math.pow(distanceZ,2));		    
					    float aux = distanceBetweenDevicesAandB/15;
					    ArrayList quinceValores = new ArrayList();
					    float aux2=0;
					    
						//calcularemos 15 puntos y los metermos en un array
					    quinceValores.add(new Float(0));
					    for(int i=0;i<13;i++){
					    	
					    	aux2 += aux;
					    	
					    	//nos quedamos con solo un decimal de la variable aux2.
					    	aux2 *= 10;
					    	float resto = aux2%1;
					    	aux2 = aux2 - resto;
					    	aux2 /= 10;
					    	quinceValores.add(aux2);					    	
					    }
					    quinceValores.add(distanceBetweenDevicesAandB);
						//________________________________________________________________
			    		for(int i=0;i< quinceValores.size();i++){
							
							float Distance = (Float) quinceValores.get(i);
							
							if(
									Distance > distanceOfOstacle  
							   ){
								
								signalLoss =  AccessPoint.signalLossByDistance(Float.valueOf(Distance),f);//f-> frequency in Ghz. d-> distance in meters	
							}else{
								
								signalLoss =  AccessPoint.signalLossByDistance(Float.valueOf(Distance),f);//f-> frequency in Ghz. d-> distance in meters	
								signalLoss -= attenuationByObstacle;
							}
							value = (float) (AccessPoint.converterWtodB(Energy_W)-signalLoss);
							dataset.addValue(value.floatValue(), "coverage in m.", Float.toString(Distance));									
						}
				
						JFreeChart chart = ChartFactory.createLineChart(" Coverage representation with obstacles "," ","Db.",dataset,PlotOrientation.VERTICAL,true,true,false);
							
						
						Chart_Panel_ForObstacles panelAux = new Chart_Panel_ForObstacles(chart);
						
						jScrollPane1.removeAll();	
						jScrollPane1.add(panelAux);
						jScrollPane1.updateUI();
						
						
					}
				  }
				}
			});	
		}
		return jTable_ForObstacles;
	}
 

	private JComboBox getJComboBox_1() {
		
		if (jComboBox_1 == null) {
			jComboBox_1 = new JComboBox();		
			
			
			jComboBox_1.setBounds(new Rectangle(249, 9, 177, 20));
			
			jComboBox_1.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0){
							
							String selectedItem = String.valueOf(jComboBox_1.getSelectedIndex());
							
						}
					}
			);
		}
		return jComboBox_1;
	}
	
    public static ArrayList getListOfMaterials() throws BiffException, IOException{
		
    	
    	   FileInputStream in = new FileInputStream("Obstacles.xls");
		   Workbook workbook = Workbook.getWorkbook(in);
		   Sheet sheet = workbook.getSheet(0);   	
		
		   Cell cell;
		   int column = 0;
		   int row = 2;
		   boolean carryOn = true;
		   
		   String content;
		   NumberCell numCell;
		   Double num;
		   
		   String material;
		   Float attenuation;		   
		   
		   ListOfMaterials = new ArrayList();
		   ArrayList MaterialAndAttenuation;
		   
		   do{
			   cell = sheet.getCell(column,row);
			   content = cell.getContents();
			   
			   if(content == null){
				   carryOn = false;
			   }
			   else if(content.equals("")){
				   carryOn = false;
			   }
			   else if(content.equals(" ")){
				   carryOn = false;
			   }
			   else{	
				   
					material = content;
					
					cell = sheet.getCell(column+1,row);
					content = cell.getContents();
					numCell = (NumberCell) cell;
					num =  numCell.getValue();	
				    attenuation = num.floatValue();
					
					MaterialAndAttenuation = new ArrayList();		    
				    MaterialAndAttenuation.add(material);
				    MaterialAndAttenuation.add(attenuation);
				    ListOfMaterials.add(MaterialAndAttenuation);
				    
			   }	
			   
			   row++;
		}while(carryOn == true);
		workbook.close();
		return ListOfMaterials;
	
	}
	
	
	private JComboBox getJComboBox_2() {
		
		if (jComboBox_2 == null) {
			
			jComboBox_2 = new JComboBox();
			
			
			for(int i=0;i<ListOfMaterials.size();i++){
				
				jComboBox_2.addItem(((ArrayList)ListOfMaterials.get(i)).get(0));
			}
			
			jComboBox_2.setBounds(new Rectangle(429, 9, 177, 20));
			
		}
		
		return jComboBox_2;
	}
	
	private JComboBox getJComboBox_3() {
		if (jComboBox_3 == null) {			
			jComboBox_3 = new JComboBox();
			jComboBox_3.setBounds(new Rectangle(608, 9, 177, 20));			
		}
		return jComboBox_3;
	}
    
private JButton getJButton_Set() {
		
		if (jButton_Set == null){
			
			jButton_Set = new JButton();			
			jButton_Set.setBounds(new Rectangle(799, 9, 150, 20));			
			jButton_Set.setText("Add");
			
			jButton_Set.addActionListener(
					
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							
							if (jButton_Set == arg0.getSource()){
								
								
								myTableModel_ForObstacles.addRow(
										             (String)jComboBox_1.getSelectedItem()
													 ,"0"
										             ,(String)jComboBox_2.getSelectedItem()													
													 ,(String)jComboBox_3.getSelectedItem());
											 
			
								numRowsOfTableObstacles++;								
								jTable_ForObstacles.setModel(myTableModel_ForObstacles);
							}
						}	
					}
			);
		}
		return jButton_Set;
	} 


	static public void returnAP(AccessPoint AP){
		
		int idAP = AP.getID();
		for(int i=0;i<ArrayOfAPs.size();i++){
			
			if(ArrayOfAPs.get(i).getID()==idAP){
				
				ArrayOfAPs.remove(i);				                   
				ArrayOfAPs.add(AP);
			break;
			}
		}
		
	}
	
	static public void returnNode(Node node){
		
		int idNode = node.getIdNode();
		
		for(int i=0;i<ArrayOfNodes.size();i++){
			
			if(ArrayOfNodes.get(i).getIdNode()==idNode){
				
				ArrayOfNodes.remove(i);				                   
				ArrayOfNodes.add(node);
			break;
			}
		}		
	}
	
	//_______________________________________ELEMENTS FOR JPANEL SIMULATION________________________________________________
	//______________________________________________________________________________________________________________________
	

	
	private JTable getJTableForSimulation() {
		
		if (jTable_ForSimulation == null) {
			
			myTableModel_ForSimulation = new MyTableModel_ForSimulation();
			jTable_ForSimulation = new JTable(myTableModel_ForSimulation);
			
		}
		return jTable_ForSimulation;
	}
	
	/**
	 * This method initializes jPanelForRepresentation	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelForRepresentation(){
		
		if (jPanelForRepresentation == null){
			
			jPanelForRepresentation = new GraficRepresentation();			
			jPanelForRepresentation.setLayout(null);
			jPanelForRepresentation.setBackground(Color.WHITE);
			
		}
		return jPanelForRepresentation;
	}
	
	private JScrollPane getJScrollPaneForGraficRepresentation() {
		if (jScrollPaneForGraficRepresentation == null) {
			jScrollPaneForGraficRepresentation = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);			
			jScrollPaneForGraficRepresentation.setBounds(new Rectangle(12, 14,587,244));
			jScrollPaneForGraficRepresentation.setViewportView(getJPanelForRepresentation());
			jScrollPaneForGraficRepresentation.setBackground(Color.WHITE);
			
		}
		return jScrollPaneForGraficRepresentation;
	}
	

	//______________________________________________________________________________________________________________________
	//______________________________________________________________________________________________________________________
	

	
	/**
	 * This method initializes jPanelFor3DRepresentation	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelFor3DRepresentation() {
		
		if (jPanelFor3DRepresentation == null) {
			
			jPanelFor3DRepresentation = new Graphic3D();
			jPanelFor3DRepresentation.setLayout(new GridBagLayout());
			jPanelFor3DRepresentation.setBounds(new Rectangle(661, 6, 563, 284));
		}
		return jPanelFor3DRepresentation;
	}



	/**
	 * This method initializes jProgressBar	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getJProgressBar() {
		if (jProgressBar == null) {
			jProgressBar = new JProgressBar(0, 100);
			jProgressBar.setBounds(new Rectangle(15, 510, 790, 21));
			jProgressBar.setValue(0);
			jProgressBar.setStringPainted(true);
		}
		return jProgressBar;
	}


	
	/**
	 * This method initializes jButtonRun	
	 * 	
	 * @return javax.swing.JButton	
	 */
    boolean firsRun = true;

	private JButton jButton_QuitObstacle = null;

	public static JButton jButton_CongigurationObstacles = null;

	private JButton jButton_expand = null;

	private JButton jButton_contract = null;

	private JLabel jLabel1 = null;

	private JPanel jPanel_ColorRTS = null;

	private Color colorRTS = null;

	private JLabel jLabel_RTS = null;

	private JPanel jPanel_ColorCTS = null;

	private JLabel jLabelCTS = null;

	private JPanel jPanel_ColorRequestFrame = null;

	private JLabel jLabelRequestFrame = null;

	private JPanel jPanel_ColorAssociationResponse = null;

	private JLabel jLabelAssociationResponse = null;

	public static JComboBox jComboBox_Frequency = null;

	private JLabel jLabel_Ghz = null;

	private JPanel jPanel_yellow = null;

	private JPanel jPanel_orange = null;

	private JPanel jPanel_red = null;

	private JLabel jLabel_obstacles = null;

	private JLabel jLabel_weak = null;

	private JLabel jLabel_High = null;

	private JLabel jLabel_medium = null;

	private JPanel jPanel_preSimulation = null;

	private JButton jButton_LoadPreSimulation = null;

	private JScrollPane jScrollPane_ForPreSimulation = null;

	
	
	private JButton getJButtonRun(){
		
		if (jButtonRun == null){			
			
			jButtonRun = new JButton();
			jButtonRun.setBounds(new Rectangle(811, 510, 87, 21));
			jButtonRun.setText("Run");
			jButtonRun.setBackground(Color.lightGray);			
			jButtonRun.addActionListener(
					
				    
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							
							if (jButtonRun == arg0.getSource()){
								
								//Tenemos que comprobar que el tiempo introducido sea superior a un umbral establecido en 100 us
								// y que almenos la simulacion contenga un AP y un Nodo
								
								CancelStatus = false;
								if(
										Integer.parseInt(jTextFieldSimulationTime.getText())<100
								   ){
									
									main.TextOutFlow(" --Error:Simulation time should be above 100 micro seconds--");
									
								}else if(
										 ArrayOfAPs.size()==0
									||
										 ArrayOfNodes.size()==0
								){
									main.TextOutFlow(" --Error:Simulation must have at least one access point and a node--");									
								}else{
									
								
														if(!firsRun){
															
															Reset();
															
														}else firsRun = false;
														
														jButtonRun.setEnabled(false);
														jButtonCancel.setEnabled(true);
														
														jTabbedPane.setEnabledAt(1,false);
														
														ArrayList RowsOfTableObstacles = new ArrayList();
														ArrayList Columns;
														
														for(int i=0;i<numRowsOfTableObstacles;i++){
															
															Columns = new ArrayList();
															for(int j=0;j<4;j++){//...se supone que 3 es el numero de columnas de la tabla 
																                //jTable_Obstacles...
																
																 Columns.add(jTable_ForObstacles.getValueAt(i,j));									
															}
															RowsOfTableObstacles.add(Columns);				
														}	
														
														
														WirelessChannel.setListOfObstacles(RowsOfTableObstacles);
														
														//______________________________
													
														updateDevices();
														//______________________________				
														
														
														dataSet = new DataSet();
														dataSet.setSimulationTime(Integer.parseInt(jTextFieldSimulationTime.getText()));								
														dataSet.setFisicalLayer(listPhysicalLayer[jComboBoxPhysicalLayer.getSelectedIndex()]);
														String infraestructureMode = (String)jComboBoxConnectionMode.getSelectedItem();
														dataSet.setInfraestructureMode(infraestructureMode);
														String Item = (String)jComboBoxRates.getSelectedItem();
														
														char[] AUX;
														float speed;
														
														if(Item.length()==6){									
															
															AUX = new char[2]; 
															AUX[0] = Item.charAt(0);
															AUX[1] = Item.charAt(1);
														}
														else if(Item.length()==7){									
															
															AUX = new char[3]; 
															AUX[0] = Item.charAt(0);
															AUX[1] = Item.charAt(1);
															AUX[2] = Item.charAt(2);
														}
														else{
															
															AUX = new char[1];									
															AUX[0] = Item.charAt(0);									
														}
														String aux = new String(AUX);
														speed = Float.parseFloat(aux);
													    dataSet.setSpeed(speed);
													    
													    
													    
													    	dataSet.setArrayOfAPs(ArrayOfAPs);
													    	dataSet.setArrayOfNodes(ArrayOfNodes);
													   						    
													    	dataSetPrepared = true;
													    
													    	//setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
													    	    
													        task.execute();
								}
							}
						}						
					}		
			);
		}
		return jButtonRun;
	}

	
	private JButton getJButtonCancel(){
		
		if (jButtonCancel == null){			
			
			jButtonCancel = new JButton();
			jButtonCancel.setBounds(new Rectangle(898, 510, 87, 21));
			jButtonCancel.setText("Cancel");
			jButtonCancel.setBackground(Color.lightGray);	
			jButtonCancel.setEnabled(false);
			jButtonCancel.addActionListener(
					
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0){
							
							CancelStatus = true;
						    
							//jScrollPane1.removeAll();
							//myTableModel_ForPossition.removeAllRows();
							//ArrayOfAPs.removeAll(ArrayOfAPs);
						    //ArrayOfNodes.removeAll(ArrayOfNodes);
						    
						    //jTextFieldSimulationTime.setText("1000");
						    //jComboBoxPhysicalLayer.setSelectedIndex(0);
						    //jComboBoxRates.addItem("1Mb/s");
							//jComboBoxRates.addItem("2Mb/s");
							//jTextField_NumberOfAP.setText(new String("0"));
							
							//jTextField_NumberOfNodes.setText(new String("0"));
							
							//jComboBox_1.removeAllItems();
							//jComboBox_3.removeAllItems();
							
							//myTableModel_ForObstacles.removeAllRows();
							
							jButtonRun.setEnabled(true);
							
							
							if(!task.isCancelled()){
								task.cancel(true);
							}
							
							
							jProgressBar.setValue(0);	
							
							
							
						}
					}
		    );
		
	}
		return jButtonCancel;
	}
	
	
  public static DataSet getDataSet(){
	   
	   dataSetPrepared = false;//lo ponemos a false para que el bucle infinito que esta en el main
	                          //se bloqué.
	   return dataSet;
   }


	/**
	 * This method initializes jScrollPane21	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane21() {
		if (jScrollPane21 == null) {
			jScrollPane21 = new JScrollPane();
			jScrollPane21.setBounds(new Rectangle(16, 540, 970, 104));
			jScrollPane21.setViewportView(getJTextArea());
		}
		return jScrollPane21;
	}


	/**
	 * This method initializes jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
		}
		return jTextArea;
	}
   
	
	public static boolean getStatusDataSet(){
		
		return dataSetPrepared;
	}



	/**
	 * This method initializes jPanel_Graphic3D	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Graphic3D() {
		if (jPanel_Graphic3D == null) {
			jPanel_Graphic3D = new Graphic3D();
			jPanel_Graphic3D.setLayout(new GridBagLayout());
			jPanel_Graphic3D.setBounds(new Rectangle(604, 15, 379, 311));
		}
		return jPanel_Graphic3D;
	}



	/**
	 * This method initializes jButton_Left	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Left() {
		if (jButton_Left == null) {
			jButton_Left = new JButton();
			jButton_Left.setBounds(new Rectangle(609, 364, 77, 16));
			jButton_Left.setText("Left");
			jButton_Left.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_Left == arg0.getSource()){								
								
								((Graphic3D)jPanel_Graphic3D).th+=((Graphic3D)jPanel_Graphic3D).G;								
							    repaint();
							}
							
						}	
					}
			);
		}
		return jButton_Left;
	}



	/**
	 * This method initializes jButton_GraficUP	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_GraficUP() {
		if (jButton_GraficUP == null) {
			jButton_GraficUP = new JButton();
			jButton_GraficUP.setBounds(new Rectangle(688, 354, 77, 16));
			jButton_GraficUP.setText("up");
			jButton_GraficUP.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_GraficUP == arg0.getSource()){								
								
								((Graphic3D)jPanel_Graphic3D).tv+=((Graphic3D)jPanel_Graphic3D).G;							
							    repaint();
							}
						}	
					}
			);
		}
		return jButton_GraficUP;
	}



	/**
	 * This method initializes jButton_GraficDown	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_GraficDown() {
		if (jButton_GraficDown == null) {
			jButton_GraficDown = new JButton();
			jButton_GraficDown.setBounds(new Rectangle(688, 373, 77, 16));
			jButton_GraficDown.setText("Down");
			jButton_GraficDown.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_GraficDown == arg0.getSource()){								
								
								((Graphic3D)jPanel_Graphic3D).tv-=((Graphic3D)jPanel_Graphic3D).G;							
							    repaint();
							}
						}	
					}
			);
		}
		return jButton_GraficDown;
	}



	/**
	 * This method initializes jButton_Right	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Right() {
		if (jButton_Right == null) {
			jButton_Right = new JButton();
			jButton_Right.setBounds(new Rectangle(767, 364, 77, 16));
			jButton_Right.setText("Right");
			jButton_Right.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_Right == arg0.getSource()){								
								
								((Graphic3D)jPanel_Graphic3D).th-=((Graphic3D)jPanel_Graphic3D).G;								
							    repaint();
							}
							
						}	
					}
			);
		}
		return jButton_Right;
	}



	
	private JButton getJButton_RotateLeft() {
		if (jButton_RotateLeft == null) {
			jButton_RotateLeft = new JButton();
			jButton_RotateLeft.setBounds(new Rectangle(851, 355, 86, 16));
			jButton_RotateLeft.setText("Rot.Left");
			jButton_RotateLeft.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_RotateLeft == arg0.getSource()){								
								
								((Graphic3D)jPanel_Graphic3D).C+=((Graphic3D)jPanel_Graphic3D).G;
								((Graphic3D)jPanel_Graphic3D).Theta = ((Graphic3D)jPanel_Graphic3D).C*Math.PI/180;				
							    repaint();
							}
						}	
					}
			);
		}
		return jButton_RotateLeft;
	}

	/**
	 * This method initializes jButton_RotateRight	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_RotateRight() {
		if (jButton_RotateRight == null) {
			jButton_RotateRight = new JButton();
			jButton_RotateRight.setBounds(new Rectangle(851, 371, 86, 16));
			jButton_RotateRight.setText("Rot.Right");
			jButton_RotateRight.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_RotateRight == arg0.getSource()){								
								
								((Graphic3D)jPanel_Graphic3D).C-=((Graphic3D)jPanel_Graphic3D).G;
								((Graphic3D)jPanel_Graphic3D).Theta = ((Graphic3D)jPanel_Graphic3D).C*Math.PI/180;			
							    repaint();
							}
						}	
					}
			);
		}
		return jButton_RotateRight;
	}



	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(849, 402, 23, 19));
			
			jCheckBox.addActionListener(
					new ActionListener(){				
						public void actionPerformed(ActionEvent actionEvent) {
							AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
							boolean selected = abstractButton.getModel().isSelected();
				        
				       
								if(selected == true){
									
									situationMarked();
								}
								else{
									deleteSituationMarked();
								}
				      }
			
					}
		   );
		}
		return jCheckBox;
	}
	
	
	int indexColor=2;

	private JTabbedPane jTabbedPane_forResults = null;

	private JPanel Results = null;

	private JPanel jPanel2 = null;

	private JPanel jPanel_viewCharts= null;

	private JPanel jPanel_viewUtilization = null;

	private JPanel jPanel_viewMediaAccessDelay = null;

	private JPanel jPanel3 = null;

	private JPanel jPanel_viewQueuingDelay = null;

	private JPanel jPanel_forViewCharts = null;

	private JLabel jLabel_Rates = null;

	private JPanel jPanel_timeOut = null;

	private JLabel jLabel_timeout = null;
	public void linksMarked(){
		
		for(int i=0;i<this.ArrayOfNodes.size();i++){
			Node nodeAux = this.ArrayOfNodes.get(i);
			int id = nodeAux.getIdAccessPointConected();
			if(id != -1){
				for(int j=0;j<ArrayOfAPs.size();j++){
					if(ArrayOfAPs.get(j).getID()==id){
						
						AccessPoint APaux = ArrayOfAPs.get(j);
						ArrayList possitionNode = nodeAux.getPossition();
						ArrayList possitionAP   = APaux.getPossition();
						((Graphic3D)jPanel_Graphic3D).AddLine_toGraphic3D((double)((Integer)possitionNode.get(0)).intValue(),
								(double)((Integer)possitionNode.get(1)).intValue(),
								(double)((Integer)possitionNode.get(2)).intValue(),
								(double)((Integer)possitionAP.get(0)).intValue(),
								(double)((Integer)possitionAP.get(1)).intValue(),
								(double)((Integer)possitionAP.get(2)).intValue(),
								                                         'b',colors[indexColor]);
						indexColor++;
						if(indexColor==7)indexColor=0;
						
					}
				}
			}
		}
		indexColor = 2;
		repaint();
	}
	
	public void situationMarked(){
		
 		
		for(int i=0;i<((Graphic3D)jPanel_Graphic3D).V.size();i++){
			
			Point3D p = (Point3D) ((Graphic3D)jPanel_Graphic3D).V.get(i);
				
			((Graphic3D)jPanel_Graphic3D).AddLine_toGraphic3D(p.x,p.y,p.z,0,p.y,p.z,'a',Color.black);
			((Graphic3D)jPanel_Graphic3D).AddLine_toGraphic3D(p.x,p.y,p.z,p.x,0,p.z,'a',Color.black);
			((Graphic3D)jPanel_Graphic3D).AddLine_toGraphic3D(p.x,p.y,p.z,p.x,p.y,0,'a',Color.black);
		    						
			((Graphic3D)jPanel_Graphic3D).AddLine_toGraphic3D(p.x,0,p.z,0,0,p.z,'a',Color.black);
			((Graphic3D)jPanel_Graphic3D).AddLine_toGraphic3D(0,p.y,p.z,0,0,p.z,'a',Color.black);
		    
		    
			((Graphic3D)jPanel_Graphic3D).AddLine_toGraphic3D(p.x,p.y,0,p.x,0,0,'a',Color.black);
			((Graphic3D)jPanel_Graphic3D).AddLine_toGraphic3D(p.x,p.y,0,0,p.y,0,'a',Color.black);
		    
		    
			((Graphic3D)jPanel_Graphic3D).AddLine_toGraphic3D(p.x,0,p.z,p.x,0,0,'a',Color.black);
			((Graphic3D)jPanel_Graphic3D).AddLine_toGraphic3D(0,p.y,p.z,0,p.y,0,'a',Color.black);
		}
		
		  repaint();
	}
	
	public void deleteSituationMarked(){
		
		for(int i=0;i<((Graphic3D)jPanel_Graphic3D).W.size();i++){
			
			Line2 aux = (Line2) ((Graphic3D)jPanel_Graphic3D).W.get(i);
			if(aux.type=='a'){
				((Graphic3D)jPanel_Graphic3D).W.remove(i);
				i--;
			}
		}
		repaint();
	}

    public void deleteLinksMarked(){
    	
		for(int i=0;i<((Graphic3D)jPanel_Graphic3D).W.size();i++){
			
			Line2 aux = (Line2) ((Graphic3D)jPanel_Graphic3D).W.get(i);
			if(aux.type=='b'){
				((Graphic3D)jPanel_Graphic3D).W.remove(i);
				i--;
			}
		}
		repaint();
	}

	
	private JScrollPane getJScrollPaneForTableResults() {
		
		if (jScrollPaneForTableResults == null) {
			
			jScrollPaneForTableResults = new JScrollPane();
			jScrollPaneForTableResults.setBounds(new Rectangle(10, 11, 930, 165));
			jScrollPaneForTableResults.setViewportView(getJTable_ForResults());
		}
		return jScrollPaneForTableResults;
	}



	private JTable getJTable_ForResults() {
		
		if (jTable_ForResults == null) {
			myTableModel_ForResults=new MyTableModel_ForResults();
			jTable_ForResults = new JTable(myTableModel_ForResults);
		}
		return jTable_ForResults;
	}


	
	private JPanel getJPanel_ColorDIF() {
		if (jPanel_ColorDIF == null) {
			jPanel_ColorDIF = new JPanel();
			jPanel_ColorDIF.setLayout(new GridBagLayout());
			jPanel_ColorDIF.setBounds(new Rectangle(13, 275, 19, 13));
			jPanel_ColorDIF.setBackground(colors[1]);
		}
		return jPanel_ColorDIF;
	}



	private JPanel getJPanel_ColorSIF() {
		if (jPanel_ColorSIF == null) {
			jPanel_ColorSIF = new JPanel();
			jPanel_ColorSIF.setLayout(new GridBagLayout());
			jPanel_ColorSIF.setBounds(new Rectangle(13, 293, 19, 13));
			jPanel_ColorSIF.setBackground(colors[7]);
		}
		return jPanel_ColorSIF;
	}



	/**
	 * This method initializes jPanel_ColorTransmission	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_ColorTransmission() {
		if (jPanel_ColorTransmission == null) {
			jPanel_ColorTransmission = new JPanel();
			jPanel_ColorTransmission.setLayout(new GridBagLayout());
			jPanel_ColorTransmission.setBounds(new Rectangle(182, 274, 19, 13));
			jPanel_ColorTransmission.setBackground(colors[2]);
		}
		return jPanel_ColorTransmission;
	}



	/**
	 * This method initializes jPanel_ColorBackoff	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_ColorBackoff() {
		if (jPanel_ColorBackoff == null) {
			jPanel_ColorBackoff = new JPanel();
			jPanel_ColorBackoff.setLayout(new GridBagLayout());
			jPanel_ColorBackoff.setBounds(new Rectangle(80, 294, 19, 13));
			jPanel_ColorBackoff.setBackground(colors[0]);//jPanel_ColorBackoff.setBackground(colors[2]);
		}
		return jPanel_ColorBackoff;
	}



	/**
	 * This method initializes jPanel_ColorTransACK	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_ColorTransACK() {
		if (jPanel_ColorTransACK == null) {
			jPanel_ColorTransACK = new JPanel();
			jPanel_ColorTransACK.setLayout(new GridBagLayout());
			jPanel_ColorTransACK.setBounds(new Rectangle(80, 274, 19, 13));
			jPanel_ColorTransACK.setBackground(Color.black);
		}
		return jPanel_ColorTransACK;
	}



	/**
	 * This method initializes jButton_PositiveZoom	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_PositiveZoom() {
		if (jButton_PositiveZoom == null) {
			jButton_PositiveZoom = new JButton();
			jButton_PositiveZoom.setBounds(new Rectangle(943, 348, 42, 21));
			jButton_PositiveZoom.setText("+");
			jButton_PositiveZoom.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_PositiveZoom == arg0.getSource()){	
								
								double factor = ((Graphic3D)jPanel_Graphic3D).amp *((double)10/(double)56);
								
								((Graphic3D)jPanel_Graphic3D).amp+=factor;						
							    
								repaint();
							}
						}	
					}
			);
		}
		return jButton_PositiveZoom;
	}



	/**
	 * This method initializes jButton_NegativeZoom	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_NegativeZoom() {
		if (jButton_NegativeZoom == null) {
			jButton_NegativeZoom = new JButton();
			jButton_NegativeZoom.setBounds(new Rectangle(943, 373, 42, 21));
			jButton_NegativeZoom.setText("-");
			
			jButton_NegativeZoom.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_NegativeZoom == arg0.getSource()){								
								
								double factor;
								//if(((Graphic3D)jPanel_Graphic3D).amp>10){
								     
									factor = ((Graphic3D)jPanel_Graphic3D).amp *((double)10/(double)56);
									((Graphic3D)jPanel_Graphic3D).amp-=factor;		
								//}
							    repaint();
							}
						}	
					}
			);
		}
		return jButton_NegativeZoom;
	}



	/**
	 * This method initializes jButton_viewTroughput	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_viewTroughput() {
		if (jButton_viewTroughput == null) {
			jButton_viewTroughput = new JButton();
			jButton_viewTroughput.setText("Troughput");
			jButton_viewTroughput.setBounds(new Rectangle(764, 47,185, 20));
			jButton_viewTroughput.addActionListener(
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_viewTroughput == arg0.getSource()){
								
								//Crear el dataset...
								
								DefaultCategoryDataset dataset = new DefaultCategoryDataset();
								Float value;
								String id;
								
								for(int i=0;i<myTableModel_ForResults.getNumberOfCompleteRows();i++){
									
									id = (String) myTableModel_ForResults.getValueAt(i,0);//id of node
									value = (Float) myTableModel_ForResults.getValueAt(i,1);//id of node myTableModel_ForResults.getValueAt(i,1);//id of node
									dataset.addValue(value.floatValue(),id," ");									
								}
							   
								JFreeChart chart = ChartFactory.createBarChart("Troughput","Devices","kbps/s.",dataset,PlotOrientation.VERTICAL,true,true,false);
									
								((ChartPanel)jPanel_forViewCharts).setChart(chart);
								
								jPanel_forViewCharts.updateUI();								
								jPanel_forViewCharts.repaint();
							}
						}
					}
			);
			
		}
		return jButton_viewTroughput;
	}



	/**
	 * This method initializes jButton_viewUtilization	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_viewUtilization() {
		if (jButton_viewUtilization == null){
			jButton_viewUtilization = new JButton();
			jButton_viewUtilization.setText("Utilization");
			jButton_viewUtilization.setBounds(new Rectangle(764, 14, 185, 20));
			jButton_viewUtilization.addActionListener(
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_viewUtilization == arg0.getSource()){
								
								//Crear el dataset...
								
								DefaultCategoryDataset dataset = new DefaultCategoryDataset();
								Float value;
								String id;
								
								for(int i=0;i<myTableModel_ForResults.getNumberOfCompleteRows();i++){
									
									id = (String) myTableModel_ForResults.getValueAt(i,0);//id of node
									value = (Float) myTableModel_ForResults.getValueAt(i,2);//id of node myTableModel_ForResults.getValueAt(i,1);//id of node
									dataset.addValue(value.floatValue(), id, " ");									
								}
							
								JFreeChart chart = ChartFactory.createBarChart("Utilization ","Devices"," seg. ",dataset,PlotOrientation.VERTICAL,true,true,false);
							
								((ChartPanel)jPanel_forViewCharts).setChart(chart);
	
								jPanel_forViewCharts.updateUI();								
								jPanel_forViewCharts.repaint();
								
								
							}
						}
					}
			);
		}
		return jButton_viewUtilization;
	}



	/**
	 * This method initializes jButton_viewMediaAccessDelay	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_viewMediaAccessDelay() {
		if (jButton_viewMediaAccessDelay == null) {
			jButton_viewMediaAccessDelay = new JButton(); 
			jButton_viewMediaAccessDelay.setText("Media acces delay");
			jButton_viewMediaAccessDelay.setBounds(new Rectangle(764, 80, 185, 20));
			jButton_viewMediaAccessDelay.addActionListener(
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_viewMediaAccessDelay == arg0.getSource()){
								
								//Crear el dataset...
								
								DefaultCategoryDataset dataset = new DefaultCategoryDataset();
								Float value;
								String id;
								
								for(int i=0;i<myTableModel_ForResults.getNumberOfCompleteRows();i++){
									
									id = (String) myTableModel_ForResults.getValueAt(i,0);//id of node
									value = (Float) myTableModel_ForResults.getValueAt(i,3);//id of node myTableModel_ForResults.getValueAt(i,1);//id of node
									dataset.addValue(value.floatValue(), id, " ");									
								}
							
								JFreeChart chart = ChartFactory.createBarChart(" Media acces delay ","Device ","ms.",dataset,PlotOrientation.VERTICAL,true,true,false);
									
								((ChartPanel)jPanel_forViewCharts).setChart(chart);
								
								jPanel_forViewCharts.updateUI();								
								jPanel_forViewCharts.repaint();
							}
						}
					}
			);
		}
		return jButton_viewMediaAccessDelay;
	}



	/**
	 * This method initializes jButton_viewQueuingDelay	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_viewQueuingDelay() {
		if (jButton_viewQueuingDelay == null) {
			jButton_viewQueuingDelay = new JButton();
			jButton_viewQueuingDelay.setText("Queuing delay");
			jButton_viewQueuingDelay.setBounds(new Rectangle(764, 179, 185, 20));
			
			jButton_viewQueuingDelay.addActionListener(
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_viewQueuingDelay == arg0.getSource()){
								
								//Crear el dataset...
								
								DefaultCategoryDataset dataset = new DefaultCategoryDataset();
								Float value;
								String id;
								
								for(int i=0;i<myTableModel_ForResults.getNumberOfCompleteRows();i++){
									
									id = (String) myTableModel_ForResults.getValueAt(i,0);//id of node
									value = (Float) myTableModel_ForResults.getValueAt(i,4);//id of node myTableModel_ForResults.getValueAt(i,1);//id of node
									dataset.addValue(value.floatValue(), id, " ");									
								}
							
								JFreeChart chart = ChartFactory.createBarChart(" Queuing delay ","Device","ms.",dataset,PlotOrientation.VERTICAL,true,true,false);
									
								((ChartPanel)jPanel_forViewCharts).setChart(chart);
								
								jPanel_forViewCharts.updateUI();								
								jPanel_forViewCharts.repaint();
							}
						}
					}
			);
		}
		return jButton_viewQueuingDelay;
	}



	/**
	 * This method initializes jButton_TotalPacketDelay	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_TotalPacketDelay() {
		if (jButton_TotalPacketDelay == null) {
			jButton_TotalPacketDelay = new JButton();
			jButton_TotalPacketDelay.setText("View total packet delay");
			jButton_TotalPacketDelay.setBounds(new Rectangle(764, 113, 185, 20));
			
			jButton_TotalPacketDelay.addActionListener(
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_TotalPacketDelay == arg0.getSource()){
								
								//Crear el dataset...
								
								DefaultCategoryDataset dataset = new DefaultCategoryDataset();
								Float value;
								String id;
								
								for(int i=0;i<myTableModel_ForResults.getNumberOfCompleteRows();i++){
									
									id = (String) myTableModel_ForResults.getValueAt(i,0);//id of node
									value = (Float) myTableModel_ForResults.getValueAt(i,5);//id of node myTableModel_ForResults.getValueAt(i,1);//id of node
									dataset.addValue(value.floatValue(), id, " ");									
								}
							
								JFreeChart chart = ChartFactory.createBarChart(" Total packet delay "," ","ms",dataset,PlotOrientation.VERTICAL,true,true,false);
									
								((ChartPanel)jPanel_forViewCharts).setChart(chart);
								
								jPanel_forViewCharts.updateUI();								
								jPanel_forViewCharts.repaint();
							}
						}
					}
			);
		}
		return jButton_TotalPacketDelay;
	}



	/**
	 * This method initializes jButton_viewJitter	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_viewJitter() {
		if (jButton_viewJitter == null) {
			jButton_viewJitter = new JButton();
			jButton_viewJitter.setText("Jitter");
			jButton_viewJitter.setBounds(new Rectangle(764, 146,185, 20));
			
			jButton_viewJitter.addActionListener(
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0) {
							
							if (jButton_viewJitter == arg0.getSource()){
								
								//Crear el dataset...
								
								DefaultCategoryDataset dataset = new DefaultCategoryDataset();
								Float value;
								String id;
								
								for(int i=0;i<myTableModel_ForResults.getNumberOfCompleteRows();i++){
									
									id = (String) myTableModel_ForResults.getValueAt(i,0);//id of node
									value = (Float) myTableModel_ForResults.getValueAt(i,6);//id of node myTableModel_ForResults.getValueAt(i,1);//id of node
									dataset.addValue(value.floatValue(), id, " ");									
								}
							
								JFreeChart chart = ChartFactory.createBarChart(" Jitter "," ","ms",dataset,PlotOrientation.VERTICAL,true,true,false);
									
								((ChartPanel)jPanel_forViewCharts).setChart(chart);
								
								jPanel_forViewCharts.updateUI();								
								jPanel_forViewCharts.repaint();
							}
						}
					}
			);
		}
		return jButton_viewJitter;
	}



	/**
	 * This method initializes jPanel_ColorBeaconFrame	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_ColorBeaconFrame() {
		if (jPanel_ColorBeaconFrame == null) {
			jPanel_ColorBeaconFrame = new JPanel();
			jPanel_ColorBeaconFrame.setLayout(new GridBagLayout());
			jPanel_ColorBeaconFrame.setBounds(new Rectangle(182, 294, 19, 13));
			jPanel_ColorBeaconFrame.setBackground(Color.pink);
		}
		return jPanel_ColorBeaconFrame;
	}



	private float getAttenuationByObstacles(AccessPoint accessPoint,Node node) throws BiffException, IOException{
		
	
		return WirelessChannel.Impedanced("Access point "+accessPoint.getID(),"Node "+node.getIdNode());
		
	}

	private float getAttenuationByObstacles(Node node1,Node node2) throws BiffException, IOException{
		
		
		return WirelessChannel.Impedanced("Node "+node1.getIdNode(),"Node "+node2.getIdNode());
		
	}

	void updateDevices(){

		int fila=0;
		String typeOfRow;								
		typeOfRow =  (String) myTableModel_ForPossition.getValueAt(fila,0);
		while(!typeOfRow.equals(" ")){
			
			    
			    if(typeOfRow.charAt(0)=='A'){											
					
					int idAP = Integer.parseInt(String.valueOf(typeOfRow.charAt(typeOfRow.length()-1)));
					
					
					AccessPoint APaux = null; 
					
					for(int i=0;i<ArrayOfAPs.size();i++){
						
						if(ArrayOfAPs.get(i).getID()==idAP){
							
							APaux = ArrayOfAPs.get(i);
							ArrayOfAPs.remove(i);
							ArrayList possition=new ArrayList();
							possition.add(myTableModel_ForPossition.getValueAt(fila,1));
							possition.add(myTableModel_ForPossition.getValueAt(fila,2));
							possition.add(myTableModel_ForPossition.getValueAt(fila,3));
							APaux.setPossition(possition);
							ArrayOfAPs.add(APaux);
						}
					
					}
					
				}
				else{					
					
					int idNode = Integer.parseInt(String.valueOf(typeOfRow.charAt(typeOfRow.length()-1)));
					
					
					Node Nodeaux = null; 
					
					for(int i=0;i<ArrayOfNodes.size();i++){
						
						if(ArrayOfNodes.get(i).getIdNode()==idNode){
							
							Nodeaux = ArrayOfNodes.get(i);
							ArrayOfNodes.remove(i);
							ArrayList possition=new ArrayList();
							possition.add(myTableModel_ForPossition.getValueAt(fila,1));
							possition.add(myTableModel_ForPossition.getValueAt(fila,2));
							possition.add(myTableModel_ForPossition.getValueAt(fila,3));
							Nodeaux.setPossition(possition);
							ArrayOfNodes.add(Nodeaux);
						
						}											
					}
				}
			    
				fila++;
				typeOfRow =  (String) myTableModel_ForPossition.getValueAt(fila,0);
		}
	}
	
	public void updateListOfObstacles(){
		
		ArrayList RowsOfTableObstacles = new ArrayList();
		ArrayList Columns;
		
		for(int i=0;i<numRowsOfTableObstacles;i++){
			
			Columns = new ArrayList();
			for(int j=0;j<4;j++){//...se supone que 4 es el numero de columnas de la tabla 
				                //jTable_Obstacles...
				
				 Columns.add(jTable_ForObstacles.getValueAt(i,j));									
			}
			RowsOfTableObstacles.add(Columns);				
		}	
		
		
		WirelessChannel.setListOfObstacles(RowsOfTableObstacles);
		
	}

	/**
	 * This method initializes jCheckBox_viewLinks	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_viewLinks() {
		if (jCheckBox_viewLinks == null) {
			jCheckBox_viewLinks = new JCheckBox();
			jCheckBox_viewLinks.setBounds(new Rectangle(745, 401, 23, 19));
		
			jCheckBox_viewLinks.addActionListener(
					new ActionListener(){				
						public void actionPerformed(ActionEvent actionEvent) {
							AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
							boolean selected = abstractButton.getModel().isSelected();
				        
				       
								if(selected == true){
									
									linksMarked();
								}
								else{
									
									deleteLinksMarked();				
									
								}
				      }
			
					}
		   );
		}
		return jCheckBox_viewLinks;
	}
	
	
	private void Reset(){
		
		//1.- inicializamos los punteros para dibujar sobre el drawing pane
		index_X = 10;
		index_Y = 10;
		 
	    //2.- borramos el rastro de toda simulacion anterior
		for(int i=0;i<this.ArrayOfAPs.size();i++){
			
			ArrayOfAPs.get(i).initialize();
			ArrayOfAPs.get(i).ResetObjPlotted();
		}
		for(int i=0;i<this.ArrayOfNodes.size();i++){
			
			ArrayOfNodes.get(i).initialize();
			ArrayOfNodes.get(i).ResetObjPlotted();
		}
		
		//3.-borramos la respresentacion 2D
		this.jPanelForRepresentation.Reset();
		
		jPanelForRepresentation = new GraficRepresentation();
		jPanelForRepresentation.setPreferredSize(new Dimension(759, 244));
		jPanelForRepresentation.setLayout(null);
		jPanelForRepresentation.setBackground(Color.WHITE);
		
		arrayOfObjectsForPlot = new ArrayList();
		
		//4.-borramos la representacion 3D:
				
		((Graphic3D)jPanel_Graphic3D).Reset();
		
		
		//5.-borramos los obstaculos de la simulacion ya que en cada simulacion se crea una lista
		//nueva de obstaculos al leer la tabla de obstaculos.
		WirelessChannel.resetListOfObstacles();		
		
		
		
		
		task = new TaskForJProgressBar();
        task.addPropertyChangeListener(this);  
	}



	/**
	 * This method initializes jButton_QuitObstacle	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_QuitObstacle() {
		if (jButton_QuitObstacle == null) {
			jButton_QuitObstacle = new JButton();
			jButton_QuitObstacle.setBounds(new Rectangle(799, 36, 150, 20));
			jButton_QuitObstacle.setText("Remove");
			
			jButton_QuitObstacle.addActionListener(
					
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0){
							
							if (jButton_QuitObstacle == arg0.getSource()){
								
								myTableModel_ForObstacles.removeRow(rowSelectedInTableObstacles);
							}
						}
					}
			);
			
		}
		return jButton_QuitObstacle;
	}

	
	private JButton getJButton_CongigurationObstacles() {
		
		if (jButton_CongigurationObstacles == null) {
			
			jButton_CongigurationObstacles = new JButton();
			jButton_CongigurationObstacles.setBounds(new Rectangle(125, 31, 115, 17));
			jButton_CongigurationObstacles.setText("Save");
			jButton_CongigurationObstacles.setBackground(Color.yellow);
			jButton_CongigurationObstacles.setEnabled(false);
			
			jButton_CongigurationObstacles.addActionListener(
					
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0){
							
							if (jButton_CongigurationObstacles == arg0.getSource()){
									
								
								Workbook  workbook = null;
								WritableWorkbook workbookCopy = null;
								
						 		try {
									workbook = Workbook.getWorkbook(new File("Obstacles.xls"));
									workbookCopy = Workbook.createWorkbook(new File("Obstacles.xls"),workbook);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (BiffException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								   
								   
						 		   WritableSheet sheet = workbookCopy.getSheet(0);
					               WritableCell cell = null; 
								   
								   
								   int column = 0;
								   int row = 2;
								   
								   boolean carryOn = true;
								   
								   String content;
								   NumberCell numCell;
								   Double num;
								   
								   String material;
								   Float attenuation;		   
								   
								  
								   ArrayList MaterialAndAttenuation;
								   
								   int index_row=0;
								   int index_col=0;								   
								   
								   String Material;
								   String Attenuation;								   
								   

								
								   for(int i=0;i<100;i++){
									  
									   Material = (String) myTableModel_ForMaterials.getValueAt(index_row, index_col);
									   Attenuation = (String) myTableModel_ForMaterials.getValueAt(index_row, index_col+1);
									   index_row++;									   
									 
									   
									   if(Material == null){
										   carryOn = false;
									   }
									   else if(Material.equals("")){
										   carryOn = false;
									   }
									   else if(Material.equals(" ")){
										   carryOn = false;
									   }
									   else{	
										   										
										    Label label = new Label(column,row,Material);								    
										    
								            Number number = new Number(column+1,row,Float.valueOf(Attenuation));
										   
								            row++;
								            
											try {
												
												sheet.addCell(label);
												sheet.addCell(number);
												
											} catch (RowsExceededException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (WriteException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
										    
									   }	
								   }
								   
								   for(int j=row;row<100;j++){
									   
									    Label label = new Label(column,row," ");								    
									    
							            Number number = new Number(column+1,row,Float.valueOf("0"));
									   
							            row++;
							            
										try {
											
											sheet.addCell(label);
											sheet.addCell(number);
											
										} catch (RowsExceededException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} catch (WriteException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
								   }
								   
								   
								try {
									   
								   workbookCopy.write();
									
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								try {
									workbookCopy.close();
								} catch (WriteException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}								
								MainFrame.reloadObstacles();								
							}							
						}
					}
			);		
		}
		return jButton_CongigurationObstacles;
	}
	
	public static void reloadObstacles(){
		
		myTableModel_ForMaterials.reload();		
		jComboBox_2.removeAllItems();
		jComboBox_2.updateUI();
		
		for(int i=0;i<ListOfMaterials.size();i++){
			
			jComboBox_2.addItem(((ArrayList)ListOfMaterials.get(i)).get(0));
		}
	}


	
	private JButton getJButton_expand() {
		if (jButton_expand == null) {
			jButton_expand = new JButton();
			jButton_expand.setText("    << expand >>   ");
			jButton_expand.setBounds(new Rectangle(416, 287, 183, 20));
			
			jButton_expand.addActionListener(
					new ActionListener(){
					
						public void actionPerformed(ActionEvent arg0){
						
							jPanelForRepresentation.incFactorExpansion();
							repaint();
							int f = jPanelForRepresentation.getFactorExpansion();
							
							if(dim_aux.width<(60+f*Integer.parseInt(jTextFieldSimulationTime.getText()))){
								dim_aux.width = 60+20+f*Integer.parseInt(jTextFieldSimulationTime.getText());
								jPanelForRepresentation.setPreferredSize(dim_aux);
							}
							if(dim_aux.height<(45+(arrayOfObjectsForPlot.size()*50))){
								dim_aux.height = 45+(arrayOfObjectsForPlot.size()*50);
							}		
							jPanelForRepresentation.setPreferredSize(dim_aux);
							jPanelForRepresentation.setSize(dim_aux);
							jScrollPaneForGraficRepresentation.updateUI();
						}						
					}
		    );			
		}
		return jButton_expand;
	}


	
	
	private JButton getJButton_contract() {
		if (jButton_contract == null) {
			jButton_contract = new JButton();
			jButton_contract.setText("    << contract >>   ");
			jButton_contract.setBounds(new Rectangle(416, 315, 183, 20));
			
			jButton_contract.addActionListener(
					new ActionListener(){
					
						public void actionPerformed(ActionEvent arg0){
						
							jPanelForRepresentation.decFactorExpansion();
							
							int f = jPanelForRepresentation.getFactorExpansion();
							
							if(dim_aux.width>(60+f*Integer.parseInt(jTextFieldSimulationTime.getText()))){
								dim_aux.width = 60+20+f*Integer.parseInt(jTextFieldSimulationTime.getText());
								jPanelForRepresentation.setPreferredSize(dim_aux);
							}
							/*
							if(dim_aux.height<(45+(arrayOfObjectsForPlot.size()*50))){
								dim_aux.height = 45+(arrayOfObjectsForPlot.size()*50);
							}
							*/
							jPanelForRepresentation.setPreferredSize(dim_aux);
							jPanelForRepresentation.setSize(dim_aux);
							jScrollPaneForGraficRepresentation.updateUI();
							repaint();
						}						
					}
		    );			
		}
		return jButton_contract;
	}
	
	/**
	 * This method initializes jPanel_ColorRTS	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_ColorRTS() {
		if (jPanel_ColorRTS == null) {			
			jPanel_ColorRTS = new JPanel();
			jPanel_ColorRTS.setLayout(new GridBagLayout());
			jPanel_ColorRTS.setBounds(new Rectangle(13, 315, 19, 13));
			jPanel_ColorRTS.setBackground(Color.orange);
		}
		return jPanel_ColorRTS;
	}


	/**
	 * This method initializes jPanel_ColorCTS	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_ColorCTS() {
		if (jPanel_ColorCTS == null) {
			jPanel_ColorCTS = new JPanel();
			jPanel_ColorCTS.setLayout(new GridBagLayout());
			jPanel_ColorCTS.setBounds(new Rectangle(80, 315, 19, 13));
			jPanel_ColorCTS.setBackground(Color.magenta);
		}
		return jPanel_ColorCTS;
	}


	/**
	 * This method initializes jPanel_ColorRequestFrame	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_ColorRequestFrame() {
		if (jPanel_ColorRequestFrame == null) {
			jPanel_ColorRequestFrame = new JPanel();
			jPanel_ColorRequestFrame.setLayout(new GridBagLayout());
			jPanel_ColorRequestFrame.setBounds(new Rectangle(13, 335, 19, 13));
			jPanel_ColorRequestFrame.setBackground(Color.cyan);
		}
		return jPanel_ColorRequestFrame;
	}


	/**
	 * This method initializes jPanel_ColorAssociationResponse	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_ColorAssociationResponse() {
		if (jPanel_ColorAssociationResponse == null) {
			jPanel_ColorAssociationResponse = new JPanel();
			jPanel_ColorAssociationResponse.setLayout(new GridBagLayout());
			jPanel_ColorAssociationResponse.setBounds(new Rectangle(13, 354, 19, 13));
			jPanel_ColorAssociationResponse.setBackground(Color.lightGray);
		}
		return jPanel_ColorAssociationResponse;
	}


	/**
	 * This method initializes jComboBox_Frequency	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox_Frequency() {
		if (jComboBox_Frequency == null) {
			
			jComboBox_Frequency = new JComboBox();			
			jComboBox_Frequency.setVisible(false);
			jComboBox_Frequency.addItem("2.4");
			jComboBox_Frequency.addItem("  5");
			jComboBox_Frequency.setBounds(new Rectangle(231, 41, 44, 20));
		}
		return jComboBox_Frequency;
	}


	/**
	 * This method initializes jPanel_yellow	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_yellow() {
		if (jPanel_yellow == null) {
			jPanel_yellow = new JPanel();
			jPanel_yellow.setBackground(Color.GREEN);
			jPanel_yellow.setLayout(new GridBagLayout());
			jPanel_yellow.setBounds(new Rectangle(443, 355, 16, 16));
		}
		return jPanel_yellow;
	}


	/**
	 * This method initializes jPanel_orange	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_orange() {
		if (jPanel_orange == null) {
			jPanel_orange = new JPanel();
			jPanel_orange.setBackground(Color.BLUE);
			jPanel_orange.setLayout(new GridBagLayout());
			jPanel_orange.setBounds(new Rectangle(443, 372, 16, 16));
		}
		return jPanel_orange;
	}


	/**
	 * This method initializes jPanel_red	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_red() {
		if (jPanel_red == null) {
			jPanel_red = new JPanel();
			jPanel_red.setBackground(Color.red);
			jPanel_red.setLayout(new GridBagLayout());
			jPanel_red.setBounds(new Rectangle(443, 390, 16, 16));
		}
		return jPanel_red;
	}


	/**
	 * This method initializes jPanel_preSimulation	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_preSimulation() {
		if (jPanel_preSimulation == null) {
			jPanel_preSimulation = new JPanel();
			jPanel_preSimulation.setLayout(null);
			jPanel_preSimulation.add(getJButton_LoadPreSimulation(), null);
			jPanel_preSimulation.add(getJScrollPane_ForPreSimulation(), null);
		}
		return jPanel_preSimulation;
	}


	/**
	 * This method initializes jButton_LoadPreSimulation	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_LoadPreSimulation(){
		if (jButton_LoadPreSimulation == null) {
			jButton_LoadPreSimulation = new JButton();
			jButton_LoadPreSimulation.setBounds(new Rectangle(797, 58, 154, 85));
			jButton_LoadPreSimulation.setText("Load pre-simulation");
			
			
			jButton_LoadPreSimulation.addActionListener(
					
					new ActionListener(){
						
						public void actionPerformed(ActionEvent arg0){							
							
							if (jButton_LoadPreSimulation == arg0.getSource()){
								
								setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
								
								updateDevices();
								updateListOfObstacles();							
														
								myTableModel_ForPreSimulation.clearAll();
								repaint();
								
								//comparamos cada nodo con cada punto de acceso
								for(int i=0;i<ArrayOfNodes.size();i++){
									
									Node aux1 = ArrayOfNodes.get(i);
									
									for(int j=0;j<ArrayOfAPs.size();j++){
										
										AccessPoint aux2 = ArrayOfAPs.get(j);
										if(aux1.equals(aux2)==false){
											
											ArrayList possition_device1 = aux1.getPossition();		   
										    
										    int distanceX_device1 = (Integer)possition_device1.get(0);
										    int distanceY_device1 = (Integer)possition_device1.get(1);
										    int distanceZ_device1 = (Integer)possition_device1.get(2);
										    
										    ArrayList possition_device2 = aux2.getPossition();		   
										    
										    int distanceX_device2 = (Integer)possition_device2.get(0);
										    int distanceY_device2 = (Integer)possition_device2.get(1);
										    int distanceZ_device2 = (Integer)possition_device2.get(2);
										   
										    float distance = (float) Math.sqrt(Math.pow(distanceX_device1-distanceX_device2,2)+Math.pow(distanceY_device1-distanceY_device2,2)+Math.pow(distanceZ_device1-distanceZ_device2,2));		    
                                            
										    switch(MainFrame.jComboBoxPhysicalLayer.getSelectedIndex()){
											 
											 case 0://802.11							
												 WirelessChannel.frequency =(float) 2.4;						 
												 break;
											 case 1://802.11a								 		 
												 WirelessChannel.frequency=(float) 5;						
												 break;
											 case 2://802.11b									
												 WirelessChannel.frequency =(float) 2.4;
												 break;
											 case 3://802.11g								 
												 WirelessChannel.frequency = (float) 2.4;								
												 break;	
											 case 4://802.11n							 
												 WirelessChannel.frequency = (float) Float.valueOf((String) MainFrame.jComboBox_Frequency.getSelectedItem()) ;								
												 break;	
											 }	
										    
										    float attenuation_dis = (float) AccessPoint.signalLossByDistance(distance, WirelessChannel.frequency);
                                            float attenuation_obs = 0;
											
                                            try {
                                            	
												attenuation_obs = getAttenuationByObstacles(aux2,aux1);
											
											} catch (BiffException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											//___________________________________________________
											
											
                                            boolean reach = false;
											try {
												reach = WirelessChannel.deviceAunderTheCoverageOfdeviceB("Node "+aux1.getIdNode(), "Access point "+aux2.getID());
											} catch (BiffException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} 
											
											myTableModel_ForPreSimulation.addRow("Access point "+aux2.getID(),"Node "+aux1.getIdNode(), distance, attenuation_dis, attenuation_obs, reach);
											   
											repaint();
										}
									}		
									
								}		
									
								//comparamos cada punto de acceso con cada nodo
								for(int i=0;i<ArrayOfAPs.size();i++){
									
									AccessPoint aux1 = ArrayOfAPs.get(i);
									
									for(int j=0;j<ArrayOfNodes.size();j++){
										
										Node aux2 = ArrayOfNodes.get(j);
										
											ArrayList possition_device1 = aux1.getPossition();		   
										    
										    int distanceX_device1 = (Integer)possition_device1.get(0);
										    int distanceY_device1 = (Integer)possition_device1.get(1);
										    int distanceZ_device1 = (Integer)possition_device1.get(2);
										    
										    ArrayList possition_device2 = aux2.getPossition();		   
										    
										    int distanceX_device2 = (Integer)possition_device2.get(0);
										    int distanceY_device2 = (Integer)possition_device2.get(1);
										    int distanceZ_device2 = (Integer)possition_device2.get(2);
										   
										    float distance = (float) Math.sqrt(Math.pow(distanceX_device1-distanceX_device2,2)+Math.pow(distanceY_device1-distanceY_device2,2)+Math.pow(distanceZ_device1-distanceZ_device2,2));		    
                                            
										    switch(MainFrame.jComboBoxPhysicalLayer.getSelectedIndex()){
											 
											 case 0://802.11							
												 WirelessChannel.frequency =(float) 2.4;						 
												 break;
											 case 1://802.11a								 		 
												 WirelessChannel.frequency=(float) 5;						
												 break;
											 case 2://802.11b									
												 WirelessChannel.frequency =(float) 2.4;
												 break;
											 case 3://802.11g								 
												 WirelessChannel.frequency = (float) 2.4;								
												 break;											 	
											 case 4://802.11n							 
												 WirelessChannel.frequency = (float) Float.valueOf((String) MainFrame.jComboBox_Frequency.getSelectedItem()) ;								
												 break;	
										    }	 
										    float attenuation_dis = (float) AccessPoint.signalLossByDistance(distance, WirelessChannel.frequency);
                                            float attenuation_obs = 0;
											
                                            try {
                                            	
												attenuation_obs = getAttenuationByObstacles(aux1,aux2);
											
											} catch (BiffException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											
											//___________________________________________________
											
											
                                            boolean reach = false;
											try {
												reach = WirelessChannel.deviceAunderTheCoverageOfdeviceB("Access point "+aux1.getID(), "Node "+aux2.getIdNode());
											} catch (BiffException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											} 
											
											myTableModel_ForPreSimulation.addRow("Node "+aux2.getIdNode(),"Access point "+aux1.getID(), distance, attenuation_dis, attenuation_obs, reach);
									}									
								}		
								
								
								//Comparamos cada nodo con cada nodo 
								for(int i=0;i<ArrayOfNodes.size();i++){
									
									Node aux1 = ArrayOfNodes.get(i);
									
									for(int j=0;j<ArrayOfNodes.size();j++){
										
										Node aux2 = ArrayOfNodes.get(j);
										
										if(!aux1.equals(aux2)){											
										
											ArrayList possition_device1 = aux1.getPossition();		   
										    
										    int distanceX_device1 = (Integer)possition_device1.get(0);
										    int distanceY_device1 = (Integer)possition_device1.get(1);
										    int distanceZ_device1 = (Integer)possition_device1.get(2);
										    
										    ArrayList possition_device2 = aux2.getPossition();		   
										    
										    int distanceX_device2 = (Integer)possition_device2.get(0);
										    int distanceY_device2 = (Integer)possition_device2.get(1);
										    int distanceZ_device2 = (Integer)possition_device2.get(2);
										   
										    float distance = (float) Math.sqrt(Math.pow(distanceX_device1-distanceX_device2,2)+Math.pow(distanceY_device1-distanceY_device2,2)+Math.pow(distanceZ_device1-distanceZ_device2,2));		    
                                            
										    switch(MainFrame.jComboBoxPhysicalLayer.getSelectedIndex()){
											 
											 case 0://802.11							
												 WirelessChannel.frequency =(float) 2.4;						 
												 break;
											 case 1://802.11a								 		 
												 WirelessChannel.frequency=(float) 5;						
												 break;
											 case 2://802.11b									
												 WirelessChannel.frequency =(float) 2.4;
												 break;
											 case 3://802.11g								 
												 WirelessChannel.frequency = (float) 2.4;								
												 break;
											 case 4://802.11n							 
												 WirelessChannel.frequency = (float) Float.valueOf((String) MainFrame.jComboBox_Frequency.getSelectedItem()) ;								
												 break;	
											 }	
										    
										    float attenuation_dis = (float) AccessPoint.signalLossByDistance(distance, WirelessChannel.frequency);
                                            float attenuation_obs = 0;
											
                                            try {
                                            	
												attenuation_obs = getAttenuationByObstacles(aux1,aux2);
											
											} catch (BiffException e){
												
												e.printStackTrace();
											} catch (IOException e) {
												
												e.printStackTrace();
											}		
															
											
                                            boolean reach = false;
											try {
												reach = WirelessChannel.deviceAunderTheCoverageOfdeviceB("Node "+aux1.getIdNode(), "Node "+aux2.getIdNode());
											} catch (BiffException e) {
												
												e.printStackTrace();
											} catch (IOException e) {
												
												e.printStackTrace();
											} 
											
											myTableModel_ForPreSimulation.addRow("Node "+aux2.getIdNode(),"Node "+aux1.getIdNode(), distance, attenuation_dis, attenuation_obs, reach);
											   
										}
										
									}		
									
								}	

								
								setCursor(null);
							}
						}
					}
				);
		
		}
		return jButton_LoadPreSimulation;
	}


	/**
	 * This method initializes jScrollPane_ForPreSimulation	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getJScrollPane_ForPreSimulation() {
		if (jScrollPane_ForPreSimulation == null) {
			jScrollPane_ForPreSimulation = new JScrollPane();
			jScrollPane_ForPreSimulation.setBounds(new Rectangle(26, 28, 760, 169));
			jScrollPane_ForPreSimulation.setViewportView(getJTable_ForPreSimulation());
		}
		return jScrollPane_ForPreSimulation;
	}


	/**
	 * This method initializes jTable_ForPreSimulation	
	 * 	
	 * @return javax.swing.JTable	
	 */
	private JTable getJTable_ForPreSimulation() {
		if (jTable_ForPreSimulation == null) {
			myTableModel_ForPreSimulation = new MyTableModel_ForPreSimulation();
			jTable_ForPreSimulation = new JTable(myTableModel_ForPreSimulation);
		}
		return jTable_ForPreSimulation;
	}


	/**
	 * This method initializes jTabbedPane_forResults	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane_forResults(){
		
		if (jTabbedPane_forResults == null) {
			
			jTabbedPane_forResults = new JTabbedPane();
   		    jTabbedPane_forResults.setBounds(new Rectangle(19, 407, 958, 238));		
			jTabbedPane_forResults.addTab("Results", null, getResults(), null);			
			jTabbedPane_forResults.addTab("View charts", null, getJPanel_viewCharts(), null);			
			
		}
		return jTabbedPane_forResults;
	}


	/**
	 * This method initializes Results	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getResults() {
		if (Results == null) {
			Results = new JPanel();
			Results.setLayout(null);
			Results.add(getJScrollPaneForTableResults(), null);
		}
		return Results;
	}


	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
		}
		return jPanel2;
	}


	
	private JPanel getJPanel_viewCharts(){
		
		if (jPanel_viewCharts == null) {
			jPanel_viewCharts = new JPanel();
			jPanel_viewCharts.setLayout(null);
			jPanel_viewCharts.add(getJButton_viewJitter(), null);
			jPanel_viewCharts.add(getJButton_TotalPacketDelay(), null);
			jPanel_viewCharts.add(getJButton_viewQueuingDelay(), null);
			jPanel_viewCharts.add(getJButton_viewMediaAccessDelay(), null);
			jPanel_viewCharts.add(getJButton_viewTroughput(), null);
			jPanel_viewCharts.add(getJButton_viewUtilization(), null);
			jPanel_viewCharts.add(getJPanel_forViewCharts(), null);
		}
		return jPanel_viewCharts;
	}


	

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setLayout(new GridBagLayout());
		}
		return jPanel3;
	}


	/**
	 * This method initializes jPanel_forViewCharts	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_forViewCharts() {
		if (jPanel_forViewCharts == null) {
			jPanel_forViewCharts = new JPanel();
			
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			Float value;
			String id;
			for(int i=0;i<myTableModel_ForResults.getNumberOfCompleteRows();i++){
				
				id = (String) myTableModel_ForResults.getValueAt(i,0);//id of node
				value = (Float) myTableModel_ForResults.getValueAt(i,2);//id of node myTableModel_ForResults.getValueAt(i,1);//id of node
				dataset.addValue(value.floatValue(), id, " ");									
			}
		
			JFreeChart chart = ChartFactory.createBarChart(" ","Devices","  ",dataset,PlotOrientation.VERTICAL,true,true,false);
			
			jPanel_forViewCharts = new ChartPanel(chart);			
			jPanel_forViewCharts.setLayout(null);
			jPanel_forViewCharts.setBounds(new Rectangle(17, 12, 740, 192));
			
			

			jPanel_forViewCharts.updateUI();								
			jPanel_forViewCharts.repaint();
		
			
			
		
		}
		return jPanel_forViewCharts;
	}


	/**
	 * This method initializes jPanel_timeOut	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_timeOut(){
		
		if (jPanel_timeOut == null){
			
			jPanel_timeOut = new DrawingPane();
			jPanel_timeOut.setLayout(new GridBagLayout());
			jPanel_timeOut.setBounds(new Rectangle(182, 315, 17, 13));
			jPanel_timeOut.setBackground(Color.WHITE);			
			
		}
		return jPanel_timeOut;
	}


	
	
	
}  //  @jve:decl-index=0:visual-constraint="10,10"  