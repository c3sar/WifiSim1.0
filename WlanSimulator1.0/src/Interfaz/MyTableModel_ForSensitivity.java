package Interfaz;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

class MyTableModel_ForSensitivity extends AbstractTableModel {
	 
	   
	
    private String[] columnNames = {"Rate (Mb/s)",
                                    "Sensitivity (dBm)"};
    int numberOfRows;
    int numberOfColumns;
    int index = 0;
    ArrayList Sensitivities;
    ArrayList Rates;
    
    private Object[][] data;             
       
    public final Object[] longValues = {new Float(0),new Float(0)};
    
    MyTableModel_ForSensitivity(ArrayList sens,ArrayList rates){
    	
    	super();
    	  
    	Update(sens,rates);    	
    }
    
    
    public void Update(ArrayList sens,ArrayList rates){
    	
    	Rates = rates;    	
    	Sensitivities = sens;
        data = new Object[20][2];
        numberOfRows = 0;
        numberOfColumns = 2;
    	
        for(int i=0;i<rates.size();i++){
        	
    		data[i][0] = String.valueOf(Rates.get(i));
        	data[i][1] = String.valueOf(Sensitivities.get(i));	
        	fireTableCellUpdated(i,0);
        	fireTableCellUpdated(i,1);	
        	numberOfRows++;
    	}  
        
        if(AccessPointConfigurationPanel.jTable_ForSensistivity != null){
        	
        	AccessPointConfigurationPanel.jTable_ForSensistivity.repaint();
        	AccessPointConfigurationPanel.jTable_ForSensistivity.updateUI();
        } 
        
        if(NodeConfigurationPanel.jTable_ForSensistivity != null){
        	
        	NodeConfigurationPanel.jTable_ForSensistivity.repaint();
        	NodeConfigurationPanel.jTable_ForSensistivity.updateUI();
        }
        
    }
    
    public int getColumnCount(){
        
    	return columnNames.length;        	
    }
    
    public int getRowCount(){
    	
        return  this.numberOfRows;        	
    }
    
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    public boolean addRow(Float c1,Float c2){
    	
    	
        data[index][0] = c1;
    	data[index][1] = c2;    	
        index++;
       AccessPointConfigurationPanel.jTable_ForSensistivity.repaint(); 
       return true;
    }	       

    public void setValueAt(Object value, int row, int col){	            

        data[row][col] = value;
        fireTableCellUpdated(row, col);
    } 	
    public boolean isCellEditable(int row, int col){
        
        if(col>0){
        	
            return true;
        }else{
        	
            return false;
        }
    }

	public Object getValueAt(int row, int col){
		
		return data[row][col];
	}	
	
	
}

