package Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.JButton;

public class viewPreSimulation extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JScrollPane jScrollPane = null;
	private JTable jTable = null;
	private JButton jButton_Close = null;
    private AbstractTableModel myTableModel=null;

	
class MyTableModel extends AbstractTableModel {
		 
	   
		
        private String[] columnNames = {"Device A",
                                        "Device B",
                                        "Distance ",
                                        "Attenuation by distance ",
                                        "Attenuation by obstacles ",
                                        " A reaches B "};
        int numberOfRows;
        int numberOfColumns;
        int index = 0;
        
        private Object[][] data=new Object[100][6];             
           
        public final Object[] longValues = {" "," ",new Float(0),new Float(0),new Float(0),false};
        
        MyTableModel(){
        	super();
        	data = new Object[100][6];
        	
        	for(int i=0;i<100;i++){
	        	data[i][0]= " ";
	        	data[i][1]= " ";
	        	data[i][2]= new Float(0);
	        	data[i][3]= new Float(0);
	        	data[i][4]= new Float(0);
	        	data[i][5]= new Boolean(false);
        	}
        	numberOfRows = 100;
        	numberOfColumns = 6;
        		        	
        }
        
        public int getColumnCount() {
            
        	return this.numberOfColumns;
        	
        }
        
        public int getRowCount() {
        	
            return  this.numberOfRows;
        	
        }
        
        public boolean addRow(String deviceA,String deviceB,Float distance,Float attenuation_dis,Float attenuation_obs,Boolean reach){
            data[index][0] = deviceA;
        	data[index][1] = deviceB;
        	data[index][2] = distance;
            data[index][3] = attenuation_dis;
            data[index][4] = attenuation_obs;
            data[index][5] = reach;
            
            index++;
            repaint();             
        	return true;
        }	       

        public boolean removeRow(String type){
        	
        	return true;
        }
        
        public String getColumnName(int col){
        	
            return columnNames[col];
        }

        public Object getValueAt(int index_row, int index_col) {	            
        	        	
        	return data[index_row][index_col];           
        }

       
        public Class getColumnClass(int c) {
            return getValueAt(0,c).getClass();
        }

       
        public boolean isCellEditable(int row, int col) {
           
        	return false;
        }

       
        public void setValueAt(Object value, int row, int col){	            

            data[row][col] = value;
            fireTableCellUpdated(row, col);
        } 			
 }
	
	
	
	
	public viewPreSimulation() {
		super();
		Dimension display  = Toolkit.getDefaultToolkit().getScreenSize();
        
        Dimension window = getSize();
        
        setLocation(((display.width - window.width) / 2)-430,
                        ((display.height - window.height) / 2)-230);
		initialize();
	}
	
	private void initialize() {
		this.setSize(811, 231);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJScrollPane(), null);
			jContentPane.add(getJButton_Close(), null);
		}
		return jContentPane;
	}
	
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setBounds(new Rectangle(19, 17, 762, 147));
			jScrollPane.setViewportView(getJTable());
		}
		return jScrollPane;
	}

	private JTable getJTable() {
		if (jTable == null) {
			myTableModel = new MyTableModel();
			jTable = new JTable(myTableModel);
		}
		return jTable;
	}

	/**
	 * This method initializes jButton_Close	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_Close() {
		if (jButton_Close == null) {
			jButton_Close = new JButton();
			jButton_Close.setBounds(new Rectangle(541, 170, 240, 20));
			jButton_Close.setText("Close");
			
			jButton_Close.addActionListener(
					new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							
							
							if (jButton_Close == arg0.getSource()){	
								setVisible(false);
							}
							
						}
					}
			);
			
		}
		return jButton_Close;
	}
	
	public void addRow(String deviceA,String deviceB,Float distance,Float attenuation_dis,Float attenuation_obs,Boolean reach){
		
		((MyTableModel) myTableModel).addRow(deviceA,deviceB,distance,attenuation_dis,attenuation_obs,reach);  	
	}
	
}  //  @jve:decl-index=0:visual-constraint="10,10"    