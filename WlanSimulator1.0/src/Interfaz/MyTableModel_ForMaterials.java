package Interfaz;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import jxl.read.biff.BiffException;

class MyTableModel_ForMaterials extends AbstractTableModel {
	 
	private ArrayList ListOfMaterials; 
	
    private String[] columnNames = {"Material",
                                    "Attenuation"};
    int numberOfRows;
    int numberOfColumns;
    int index=0;
    
    private Object[][] data=new Object[100][3];             
   
    public final Object[] longValues = {"_",new Float(0)};
 
    
    MyTableModel_ForMaterials(ArrayList ListOfMaterials){
    	
    	super();
    	data = new Object[100][3];
    	
    	for(int i=0;i<100;i++){
    		
        	data[i][0]= " ";
        	data[i][1]= " ";
        	
    	}
        	
    	if(ListOfMaterials != null){
    		
        	for(int i=0;i<ListOfMaterials.size();i++){
        		    		
        		data[i][0]= ((ArrayList)ListOfMaterials.get(i)).get(0);
	        	data[i][1]= String.valueOf(((ArrayList)ListOfMaterials.get(i)).get(1));
        	}	        	
    	}
    	
    	numberOfRows = 100;
    	numberOfColumns = 2;        	
    }
    
    public void reload(){
    	
       for(int i=0;i<100;i++){
    		
        	data[i][0]= " ";
        	data[i][1]= " ";
        	
    	}
    	try {
    		
			ListOfMaterials = MainFrame.getListOfMaterials();
		}catch (BiffException e){
			
			e.printStackTrace();
		}catch (IOException e){
			
			e.printStackTrace();
		}
    	if(ListOfMaterials != null){
    		
        	for(int i=0;i<ListOfMaterials.size();i++){
        		    		
        		data[i][0]= ((ArrayList)ListOfMaterials.get(i)).get(0);
	        	data[i][1]= String.valueOf(((ArrayList)ListOfMaterials.get(i)).get(1));
        	}
        	
          MainFrame.JTable_ForMaterials.repaint();
    	
    	}
    }
    
    public int getColumnCount() {
        
    	return columnNames.length;	        	
    }
 
    public int getRowCount() {
    	
        return  this.numberOfRows;	        	
    }
    
    public boolean addRow(String Material,Float Attenuation){
    	       	
        data[index][0] = new String(Material);
    	data[index][1] = new Float(Attenuation);
    	
    	index++;            
        MainFrame.JTable_ForMaterials.repaint(); 	            
       
    	return true;
    }	       
    
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int index_row, int index_col) {	            
    	        	
    	return data[index_row][index_col];	          
    }
   
    public Class getColumnClass(int c) {
        return getValueAt(0,c).getClass();
    }
   
    public boolean isCellEditable(int row, int col) {	           
        
    	return true;     
    }
  
    public void setValueAt(Object value, int row, int col){        
    	
        data[row][col] = value;
        fireTableCellUpdated(row, col);	            
    }
}
