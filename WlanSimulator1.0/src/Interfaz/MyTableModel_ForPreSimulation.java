package Interfaz;

import javax.swing.table.AbstractTableModel;

public class MyTableModel_ForPreSimulation extends AbstractTableModel{
	
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

			MyTableModel_ForPreSimulation(){
				
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
				//repaint();             
			return true;
			}	       

			public boolean clearAll(){
				
				data = new Object[100][6];				
				for(int i=0;i<100;i++){
					data[i][0]= " ";
					data[i][1]= " ";
					data[i][2]= new Float(0);
					data[i][3]= new Float(0);
					data[i][4]= new Float(0);
					data[i][5]= new Boolean(false);
				}
				index=0;
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


			
}
