package Interfaz;

import javax.swing.table.AbstractTableModel;

class MyTableModel_ForObstacles extends AbstractTableModel {
	 
    
    private String[] columnNames = {"Device A",
    		                        "Dist. between Dev.A and Obst.", 
                                    "Obstacle",
                                    "Device B",
                                    "View graphic"};
    int numberOfRows;
    int numberOfColumns;
    int index = 0;
    
    private Object[][] data=new Object[100][3];             
   
    
    MyTableModel_ForObstacles(){
    	
    	super();
    	data = new Object[100][5];
    	
    	for(int i=0;i<100;i++){

    		data[i][0]= " ";
        	data[i][1]= " ";
        	data[i][2]= " ";
        	data[i][3]= " ";
        	data[i][4]= " ";
    	}
    	
    	numberOfRows = 100;
    	numberOfColumns = 5;
    	
    }
    
    public int getColumnCount() {
        
    	return columnNames.length;	        	
    }
 
    public int getRowCount() {
    	
        return  this.numberOfRows;	        	
    }
    
    public boolean addRow(String Node_1,String Distance,String Obstacle,String Node_2){
    	       	
        data[index][0] = new String(Node_1);
        data[index][1] = new String(Distance);
     	data[index][2] = new String(Obstacle);
    	data[index][3] = new String(Node_2);
    	data[index][4] = new String("click here for view graphic");
    	index++; 
    	
    	MainFrame.jTable_ForObstacles.repaint(); 	            
       
    	return true;
    }	       

    public boolean removeRow(int numberOfRow){
    	
    	Boolean reordenar = true;
    	
    	for(int i = numberOfRow;i<index+1;i++){
    		
    		data[i][0]=data[i+1][0];
			data[i][1]=data[i+1][1];
			data[i][2]=data[i+1][2];
			data[i][3]=data[i+1][3];
			data[i][4]=data[i+1][4];
			
    	}
    	
    	index--;
    	
    	MainFrame.jTable_ForObstacles.repaint(); 	   
        
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
        
    	if(col == 1) return true;
    	else return false;	           
    }
  
    public void setValueAt(Object value, int row, int col){        
    	
        data[row][col] = value;
        fireTableCellUpdated(row, col);	            
    }
    
    public void removeAllRows(){
    	
    	for(int i=0;i<100;i++){
        	
    		data[i][0]= " ";
        	data[i][1]= " ";
        	data[i][2]= " ";
        	data[i][3]= " ";
        	data[i][4]= " ";
    	}
    	
    	MainFrame.jTable_ForObstacles.repaint(); 	   
    }
}
