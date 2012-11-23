package Interfaz;

import javax.swing.table.AbstractTableModel;

public class MyTableModel_ForSimulation extends AbstractTableModel {
	 
    
    private String[] columnNames = {"Object",
                                    "Position X",
                                    "Position Y",
                                    "Position Z"
    								};
    
    int numberOfRows;
    int numberOfColumns;
    int numberOfAccesPoints;
    int numberOfNodes;
    
    private Object[][] data=new Object[100][5];           
 
    
    
    public final Object[] longValues = {"_",new Integer(0),new Integer(0),new Integer(0)};
 
    MyTableModel_ForSimulation(){
    	super();
    	data = new Object[100][4];
    	numberOfRows=0; 
    	numberOfColumns = 4;
    	numberOfAccesPoints = 0;
    	
    	for(int i=0;i<100;i++){
    		
        	data[i][0]= " ";
        	data[i][1]= new Integer(0);
        	data[i][2]= new Integer(0);
        	data[i][3]= new Integer(0);	
        	numberOfRows++;
    	}
    	
    }
    
    public int getColumnCount() {
        
    	return columnNames.length;    	
    }
 
    public int getRowCount() {
    	
        return  this.numberOfRows;
    }
    
    public boolean addRow(String Type,Integer c2,Integer c3,Integer c4){        	
       
    	
        data[numberOfAccesPoints+numberOfNodes][0] = new String(Type);
    	data[numberOfAccesPoints+numberOfNodes][1] = new Integer(c2);
    	data[numberOfAccesPoints+numberOfNodes][2] = new Integer(c3);
        data[numberOfAccesPoints+numberOfNodes][3] = new Integer(c4);
       
        if(Type.equals("Acces point")){
            
        	this.numberOfAccesPoints++;
        }
        else{
        	
            this.numberOfNodes++;	
        }
        
        MainFrame.jTable_ForSimulation.repaint();         
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
       
       return false;	            
    }

        
    
}
