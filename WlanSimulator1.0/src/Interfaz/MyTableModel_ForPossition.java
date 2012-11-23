package Interfaz;

import javax.swing.table.AbstractTableModel;

class MyTableModel_ForPossition extends AbstractTableModel{
	 

    private String[] columnNames = {"Object",
                                    "X position",
                                    "Y position",
                                    "Z position",
                                    "Configuration"};
    int numberOfRows;
    int numberOfColumns;
   
    
    private Object[][] data=new Object[100][5];             
       
    public final Object[] longValues = {"_",new Integer(0),new Integer(0),new Integer(0)," "};
    
    MyTableModel_ForPossition(){
    	super();
    	data = new Object[100][5];
    	
    	for(int i=0;i<100;i++){
        	data[i][0]= " ";
        	data[i][1]= new Integer(0);
        	data[i][2]= new Integer(0);
        	data[i][3]= new Integer(0);
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
    
    public boolean addRow(String Type,Integer c2,Integer c3,Integer c4,String c5){
    	
    	int numRows = MainFrame.ArrayOfAPs.size()+MainFrame.ArrayOfNodes.size();
    	
        data[numRows][0] = new String(Type);
    	data[numRows][1] = new Integer(c2);
    	data[numRows][2] = new Integer(c3);
        data[numRows][3] = new Integer(c4);
        data[numRows][4] = new String(c5);
         
        MainFrame.jTable_ForPossition.repaint();
        return true;
    }	       

    public boolean removeRow(String type){
    	
    	Boolean reordenar = false;
    	for(int i=MainFrame.ArrayOfNodes.size()+MainFrame.ArrayOfAPs.size()-1;i>=0;i--){//recorremos las filas desde la ultima a la primera
    		
    			
    			if(data[i][0].equals(type)){        					
    				
    				reordenar = true;
    			}
    			
    		//ahora tenemos que reordenar la tabla y mover todos los elementos hacia arriba
    			
    			if(reordenar){
    				int j;
        			for(j=i;j<MainFrame.ArrayOfAPs.size()+MainFrame.ArrayOfNodes.size()-1;j++){
        				
        				data[j][0]=data[j+1][0];
        				data[j][1]=data[j+1][1];
        				data[j][2]=data[j+1][2];
        				data[j][3]=data[j+1][3];
        				data[j][4]=data[j+1][4];
        			}
        			data[j][0]= new String(" ");
        			data[j][1]= new Integer(0);
        			data[j][2]= new Integer(0);
        			data[j][3]= new Integer(0);
        			data[j][4]= new String(" ");
        			
        			break;
    			}
    		        		
    	}
    	
    	MainFrame.jTable_ForPossition.repaint();
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
       
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }

    public void setValueAt(Object value, int row, int col){	            

        data[row][col] = value;
        fireTableCellUpdated(row, col);
    } 		
    
    public void removeAllRows(){
    	
    	for(int i=0;i<100;i++){
        	data[i][0]= " ";
        	data[i][1]= new Integer(0);
        	data[i][2]= new Integer(0);
        	data[i][3]= new Integer(0);
        	data[i][4]= " ";
    	}
    	MainFrame.jTable_ForPossition.repaint();
    }
}
