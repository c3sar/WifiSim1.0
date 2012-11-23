package Interfaz;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

class MyTableModel_ForResults extends AbstractTableModel {
	 
    
    private String[] columnNames = {"Node",
                                    "Throughput", 
                                    "Utilization",
                                    "Media access delay",
                                    "Queuing delay",
                                    "Total packet delay",
                                    "Jitter",
                                    "Queue length"	                                        
    								};
    int numberOfRows;
    int numberOfColumns;
    int numberOfCompleteRows;
    
    private Object[][] data = new Object[100][8];             
 
    
    
    public final Object[] longValues = {"_",new Float(0.0)};
 
   
    
    MyTableModel_ForResults(){
    
    	super();
    	data = new Object[100][8];
    	numberOfRows=0; 
    	numberOfColumns = 8;
    	
    	
    	for(int i=0;i<100;i++){
    		
        	data[i][0]= " ";
        	data[i][1]= new Float(0.0);
        	data[i][2]= new Float(0.0);
        	data[i][3]= new Float(0.0);
        	data[i][4]= new Float(0.0);
        	data[i][5]= new Float(0.0);
        	data[i][6]= new Float(0.0);
        	data[i][7]= new Float(0.0);
        	numberOfRows++;
    	}
  	
    }        
    
    public void setResults(ArrayList parameters){
    	
    	for(int i=0;i<parameters.size();i++){
    		
    		ArrayList row = (ArrayList) parameters.get(i);
    		
    		for(int j=0;j<row.size();j++){	        		
    			
    			data[i][j] = row.get(j);		        	
    		}
    		this.numberOfCompleteRows++;
    	}
    }
     
    public int getColumnCount() {
        
    	return columnNames.length;
    }
 
    public int getRowCount() {
    	
        return  this.numberOfRows;
    }
    
    public int getNumberOfCompleteRows(){
    	return this.numberOfCompleteRows;
    }
    
    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int index_row, int index_col){	            
    	        	
    	return data[index_row][index_col];	           
    }
   
    public Class getColumnClass(int c){
    	
        return getValueAt(0,c).getClass();
    }
   
    public boolean isCellEditable(int row, int col) {
       
       return false;	            
    }	        

}