package Main;
import Interfaz.DataSet;
import Interfaz.MainFrame;
import java.util.*;

import Diagram.*;
import ElementsForSim.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.*;
import javax.swing.JFrame;


import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class main{
	
	private static final String SwingUtilities = null;
	private static ArrayList<Node> arrayOfNodes = new ArrayList();	
	private static int ConexionType;
	private static int macLayerConfiguration;
	private static int physicalLayerConfiguration;
	private static int numAP = 0;
	private static int numNodes;
	public  static WirelessChannel wirelessChannel;
	private static int simulation_time;
	private static String buffer;
	static BufferedReader lee;	
    static float  ran = 1;
    static float previous_random = -1;    
    static int currentTime;     
    static Hashtable RelationshipBetweenRateAndPower;
   
    public static ArrayList getRatesAndSensitivityForPhysicalTechnology(String techonology){
    	
    	if(RelationshipBetweenRateAndPower != null){
    		return (ArrayList) RelationshipBetweenRateAndPower.get(techonology);
    	}
    	else return null;
    }
    	
    
    public static void getRelationshipBetweenRateAndPower(){
    
    	RelationshipBetweenRateAndPower = new Hashtable();
    	   
    	FileInputStream in = null;
		try {
			in = new FileInputStream("relationship between rate and power received.xls");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(in);
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Sheet sheet = workbook.getSheet(0);
		   
		   Cell cell;
		   int row;
		   int column;		   
		   
		   String content;
		   NumberCell numCell;
		   Double num;		   
		  
		     row = 1;
		     column = 0;
		     
		      
		     ArrayList rateAndSensitivity;
		     
			 int numberOfRates = -1;
		     String type;
		     boolean carryOn = true;
		     
		     do{
		    	 
				   cell = sheet.getCell(0,row);
				   content = cell.getContents();
				  	
				   numberOfRates=0;
				   if(content.equals("802.11")){
				    	 
						 numberOfRates = 2;
				     }else if(content.equals("802.11a")){
				    	 
				    	 numberOfRates = 8;
				     }else if(content.equals("802.11b")){
				    	 
				    	 numberOfRates = 4;
				     }else if(content.equals("802.11g")){
				    	 
				    	 numberOfRates = 8;
		             }else if(content.equals("802.11n")){
				    	 
				    	 numberOfRates = 8;
				    	 carryOn = false;
						   
				     }	   
				    
				   
				   if(numberOfRates!=0){
					   
					   
					   row+=2;//nos saltamos dos filas hacia abajo en la tabla para empezar a leer las velocidades
				   
					   ArrayList listOfRates = new ArrayList();
					   for(int i=0;i<numberOfRates;i++){
						   
						   rateAndSensitivity = new ArrayList();
						   cell = (Cell)sheet.getCell(0,row);					   
						   numCell = (NumberCell) cell;
						   num = numCell.getValue();
						   rateAndSensitivity.add(new Float(num));				  
						   
						   cell = (Cell)sheet.getCell(1,row);					   
						   numCell = (NumberCell) cell;
						   num = numCell.getValue();					   
						   rateAndSensitivity.add(new Float(num));
						   row++;
						   listOfRates.add(rateAndSensitivity);
					   }
					   RelationshipBetweenRateAndPower.put(new String(content), new ArrayList(listOfRates));
					   
				   }else{
					   
					   row++;
				   }			   
	           
			   }while(carryOn == true);
    	
    }   
    
    public static void TextOutFlow(String txt){
    	
    	try{
			
    		MainFrame.TextInFlow(txt);
		} catch (IOException e){
			
			e.printStackTrace();
		}
		
    }
    
    public static float RANDOM(int l1,int l2){
    
       long AUX = System.currentTimeMillis();
       
       long AUX2 =  AUX % 10;
       
       Random random = new Random((long)(Math.pow(AUX2, ran)));  
       ran++;
   	   float next_random =  random.nextFloat();
   	   
   	   //nos quedamos solo con los tres ultimos decimales
   	   next_random = next_random * 100000;
   	   next_random = next_random % 1; 
   	   
   	   
       if(previous_random == -1)previous_random = next_random;
       else{
    	   
    	   if(previous_random == next_random){    		   
    		   do{    			   
    			   next_random =  random.nextFloat();
    		   }while(previous_random == next_random);
    	   }
    	   previous_random = next_random;
       }   
       
       int distance = l2-l1;
       float randomValue = distance * next_random;
       
       //redondeamos la variable randomValue;
       
       int a = (int)randomValue;
       float b = (randomValue-a)*10;
       float c = randomValue-a;
      
       if(b>5){    	   
    	   a++;
    	   randomValue = a;
       }
       else{
    	   
    	 randomValue -= c;  
       }
       
   	   return l1+randomValue;
   	}    
    
    public static int getCurrentTime(){
    	
    	return currentTime;
    }
    
    public static int getSimulationTime(){
    	
    	return simulation_time;
    }
    
	private static void generateSimulation(
												int ConexionType,
												boolean requestToSendActivated,
									 			int physicalLayerConfiguration,
									 			float maximumTransmissionSpeed,
									 		    int simulation_time,
									 			int PacketSize,
									 			ArrayList ArrayOfAPs,
									 			ArrayList ArrayOfNodes
	
	)throws IOException, InterruptedException, BiffException{
		

		 ArrayList nodesMustEjectBackoff = new ArrayList(); 
		 WirelessChannel.setSimulationTime(simulation_time); 		 
		 
		 //_________________________________________________________________________________________________________________________
		 
		   FileInputStream in;
		   Workbook workbook;
		   Sheet sheet;
		   
		   Cell cell;
		   int row = 2;
		   boolean carryOn = true;
		   
		   String content;
		   NumberCell numCell;
		   Double num;	 
		 
		 //_________________________________________________________________________________________________________________________
		 
		
		 int numberOfRates = 0;
		 
		 switch(physicalLayerConfiguration){
			 
			 case 1://802.11
				 main.TextOutFlow("Physical layer : 802.11 ");
				 WirelessChannel.timeForSlot=9;//tiempo en microsegundos.
				 WirelessChannel.timeForDif=34;
				 WirelessChannel.timeForSif=16;
				 //WirelessChannel.contentWind=16;
				 WirelessChannel.frequency=(float) 2.4;
				
				 numberOfRates=2;
				 break;
			 case 2://802.11a
				 main.TextOutFlow("Physical layer : 802.11a ");
				 WirelessChannel.timeForSlot=9;
				 WirelessChannel.timeForDif=34;
				 WirelessChannel.timeForSif=16;
				 //WirelessChannel.contentWind=16;
				 WirelessChannel.frequency=(float) 5;
				
				 numberOfRates=8;
				 break;
			 case 3://802.11b			 
	
				 main.TextOutFlow("Physical layer : 802.11b ");
				 WirelessChannel.timeForSlot=20;
				 WirelessChannel.timeForDif=50;
				 WirelessChannel.timeForSif=10;
				 //WirelessChannel.contentWind=32;
				 WirelessChannel.frequency=(float) 2.4;
				 	
				 numberOfRates=4;
				 break;
			 case 4://802.11g
				 
				 main.TextOutFlow("Physical layer : 802.11g ");
				 WirelessChannel.timeForSlot =20;
				 WirelessChannel.timeForDif =30;
				 WirelessChannel.timeForSif =10;
				 //WirelessChannel.contentWind = 32;
				 WirelessChannel.frequency = (float) 2.4;
				
				 numberOfRates=8;
				 break;	
			 case 5://802.11n
				 
				 main.TextOutFlow("Physical layer : 802.11n ");
				 WirelessChannel.timeForSlot = 9;
				 WirelessChannel.timeForDif = 34;
				 WirelessChannel.timeForSif = 16;
				 //WirelessChannel.contentWind = 34;
				 String aux = String.valueOf(MainFrame.jComboBox_Frequency.getSelectedItem());
				 WirelessChannel.frequency = Float.valueOf(aux);
				
				 numberOfRates = 8;
				 break;	 
			 }			
		
		 main.TextOutFlow("Maximum transmission speed: "+maximumTransmissionSpeed/(float)Math.pow(10, 6)+"Mb/s");
		 WirelessChannel.maximumTransmissionSpeed = maximumTransmissionSpeed;		
		 WirelessChannel.PacketSize = PacketSize;
		 WirelessChannel.requestToSendActivated = requestToSendActivated;
	     int x = 0,y = 0,z = 0;     
	    
	     ArrayList columns;     
	     
	     
	     //ReCUERDA... hay dos bucles repetidos iguales porque los puntos de acceso deben ser 
	     //creados antes.
	    
	     currentTime = 0;	     
	         
	     wirelessChannel.setNodes(ArrayOfNodes);
	     
	     main.TextOutFlow("Nodes:");
	     for(int p=0;p<ArrayOfNodes.size();p++){
	    	
	    	 main.TextOutFlow("   Node "+((Node)ArrayOfNodes.get(p)).getIdNode()+" ("+((Node)ArrayOfNodes.get(p)).getPossition().get(0)+","+((Node)ArrayOfNodes.get(p)).getPossition().get(1)+","+((Node)ArrayOfNodes.get(p)).getPossition().get(2)+")");
	     }    
	     
	     
	     if(ConexionType == 1){//esta condicion siempre se va a cumplir hasta la proxima actualizacion cuando se incluya el modo ad-hoc
	    	 wirelessChannel.setAccessPoints(ArrayOfAPs);
	    	 
	    	 
	    	 main.TextOutFlow("Acces points:");
		     for(int p=0;p<ArrayOfAPs.size();p++){
		    	
		    	 main.TextOutFlow("   A.P.  "+((AccessPoint)ArrayOfAPs.get(p)).getID()+" ("+((AccessPoint)ArrayOfAPs.get(p)).getPossition().get(0)+","+((AccessPoint)ArrayOfAPs.get(p)).getPossition().get(1)+","+((AccessPoint)ArrayOfAPs.get(p)).getPossition().get(2)+")");
		     } 
	     }
	    	    
	     String actionOfNode = null;
	     String actionOfAP = null;
	     Hashtable nodesWantsToTransmit = new Hashtable();
	     int key;
	     ArrayList AUX;
	     ArrayList listOfIdsOfAccesPoints=new ArrayList();
	     
	     
	     ArrayList actions = new ArrayList();//guardará las acciones que van a tomar los nodos cuyo identificador sera guardado
	     ArrayList ids = new ArrayList();//en la mismaposicion en el array ids.
	     
	     ArrayList actionsOfAPs = new ArrayList();//guardará las acciones que van a tomar los nodos cuyo identificador sera guardado
	     ArrayList idsOfAps = new ArrayList();//en la mismaposicion en el array ids.
	     
	   
	     for(int k=0;k<wirelessChannel.getAccessPoints().size();k++){
	    	 
    		 key = ((AccessPoint)wirelessChannel.getAccessPoints().get(k)).getID();
    		 nodesWantsToTransmit.put(key, new ArrayList());
    		 listOfIdsOfAccesPoints.add(key);
	     }	
	     
	     //... BEGIN SIMULATION:	     
	     //... recordar que no hay colisiones en csma/ca (ca->colision avoided) solo se producen en modo ad-hoc sin RTS/CTS...
	     //... colision: cuando dos nodos intentan transmitir a la vez y el que va a transmitir no detecta al que esta transmitiendo.
	     	
	     
	     int numOfAps = wirelessChannel.getAccessPoints().size();
	     int numOfNodes = wirelessChannel.getNodes().size();
	     
	     for(int i=0;i<simulation_time;i++){	    	 
	    	
	    	
	    	 WirelessChannel.nowTime = i;
	    	 currentTime = i;
	    	 
	    	 TextOutFlow("***********************************************"); 
	    	 TextOutFlow(" Time: "+i+" us.");
	    	 TextOutFlow("***********************************************");
	    	 
	    	 //*************************************************************************************************************************************
	    	 int saveTime = 0;
	    	 int timeStopedOfNode = 0;
	    	 int timeStopedOfAccesPoint = 0;
	    	 
	    	 if(i == 184){
	    		 System.out.println("punto de ruptura alcanzado");
	    	 }
	    	 
	    	 for(int k=0;k<numOfAps;k++){
	    		 
	    		 actionOfAP = ((AccessPoint)wirelessChannel.getAccessPoints().get(k)).Run(i);
	    		 
	    		 //__Tiempo que se va a llevar haciendo lo mismo sea lo que sea ____________________________________________
	    		 timeStopedOfAccesPoint = ((AccessPoint)wirelessChannel.getAccessPoints().get(k)).getTimeStoped();
	    		 
	    		 // el -1 significa que su timeStoped es 0 pero puede adelantar tiempo (estado de solicitud del medio para mandar una trama beacon)
	    		if(k==0)saveTime = timeStopedOfAccesPoint;	    		 
	    		else    			
	    		 	if(timeStopedOfAccesPoint != -1){
	    			 
	    		 		if(saveTime>timeStopedOfNode || saveTime==-1){
	    				 
	    		 			saveTime = timeStopedOfAccesPoint;
	    		 		}
	    		 	}
    			
	    		 
	    		 //_________________________________________________________________________________________________________
	    		 
	    		 if(!actionOfAP.equals("null")){
	    			 
	    			 actionsOfAPs.add(actionOfAP);	    		 
		    		 idsOfAps.add(((AccessPoint)wirelessChannel.getAccessPoints().get(k)).getID());
	    		 }
	    		 actionOfAP = null;
	    	 }	  
	    	 
	    	 for(int j=0;j<numOfNodes;j++){
	    		 
	    		 
	    		 // actions va a ser un vector de string , que definiran las acciones de los nodos al final de el microsegundo en 
	    		 // curso , si un mismo canal es ocupado dos o mas veces antes de ser liberado entonces los datos en el canal 
	    		 // estarán corruptos.	    		 
	    		 
	    		 actionOfNode = ((Node)wirelessChannel.getNodes().get(j)).Run(i);    		 
	    		 
	    		//__Tiempo que se va a llevar haciendo lo mismo sea lo que sea ____________________________________________
	    		 
	    		 
	    		 timeStopedOfNode = ((Node)wirelessChannel.getNodes().get(j)).getTimeStoped();
	    		 
	    		 if((saveTime==-1) && (j==0) && (timeStopedOfNode!=-1)) saveTime=timeStopedOfNode;
	    		 else 
	    			  if(timeStopedOfNode!=-1){
	    			 
		    			 if(saveTime>timeStopedOfNode || saveTime==-1){
		    				 
		    				 saveTime = timeStopedOfNode;
		    			 } 
	    		      }	    		 
	    		 
	    		 TextOutFlow("Node "+(j)+" timeStoped "+timeStopedOfNode);
	    		 TextOutFlow("AP timeStoped "+timeStopedOfAccesPoint);
	    		 
	    		 //________________________________________________________________________________________________________	    		 
	    		 if(!actionOfNode.equals("null")){
	    			 
	    			actions.add(actionOfNode);    		 
	    		 	ids.add(((Node)wirelessChannel.getNodes().get(j)).getIdNode());
	    		 }
	    	 }    	 
	    	 //____________________________________________________________________________________________________________________________________	    	 
	    	 //*************************************************************************************************************************************
		    	
	    	  
	    	 //Miramos si algun punto de acceso tiene alguna accion que realizar 
             if(actionsOfAPs.size()>0){
	    		 
	    		 for(int k=0;k<actionsOfAPs.size();k++){
	    			
	    			 actionOfAP = (String)actionsOfAPs.get(k);
	    			 if(actionOfAP.equals("free_channel_at_the_end_of_this_microsecond")){	//LIBERAR CANALES    				 
	    				//libera su canal de entrada	    				 
	    				((AccessPoint)wirelessChannel.getAccessPoint((Integer)idsOfAps.get(k))).setChannelAsFree(-1);
	    				//aqui debe liberar el canal de entrada de los nodos conectados a el
	    				((AccessPoint)wirelessChannel.getAccessPoint((Integer)idsOfAps.get(k))).setChannelsAsFree();
	    			 }
	    			 else if(actionOfAP.equals("occupies_channel_at_the_end_of_this_microsecond")){ //OCUPAR CANALES
	    				 //ocupa su canal de entrada
	    				((AccessPoint)wirelessChannel.getAccessPoint((Integer)idsOfAps.get(k))).setChannelAsBusy(-1);
	    				//aqui debe ocupar tambien el canal de entrada del los demas nodos conectados a el
	    				((AccessPoint)wirelessChannel.getAccessPoint((Integer)idsOfAps.get(k))).setChannelsAsBusy();
	    			 }
	    		 }
	    	 }
			 //De esta manera ocupamos (y liberamos) el canal asignado a cada punto de acceso al final de cada microsegundo 
			 if(actions.size()>0){ 
				
				 for(int k = 0;k<actions.size();k++){
					 
					 actionOfNode = (String) actions.get(k);
					 
			    	 if(actionOfNode.equals("free_channel_at_the_end_of_this_microsecond")){
		    			 //Liberamos el canal al cual esta conectado el nodo que he ejecutado esta acción.
		    		     //Debe liberar su propio canal
			    		 //el canal del punto de acceso que es su canal de salida
			    		 //y los canales de entrada de los nodos bajo su cobertura y conectados al mismo AP
			    		//------------------------------------------------------			    		 
			    		((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setEntryChannelAsFree();
		    		    ((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setOutputChannelAsFree();
			    		//((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setOutputChannelsAsFree();			    		 
			    		//------------------------------------------------------
			         }
			    	 else if(actionOfNode.equals("free_channels_at_the_end_of_this_microsecond")){
			    		 //este caso se ejuta al final de un microsegundo en el que un nodo ha mandado una trama
			    		 //RTS.
			    		 
			    		 //debe liberar su propio canal
			    		 //el canal del punto de acceso que es su canal de salida
			    		 //y los canales de entrada de los nodos a los que ha enviado RTS
			    		//------------------------------------------------------			    		 
			    		((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setEntryChannelAsFree();
			    		((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setOutputChannelAsFree();
			    		((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setOutputChannelsAsFree();			    		 
			    		//------------------------------------------------------
			    		 
			    	 }
			         else if(actionOfNode.equals("occupies_channel_at_the end_of_this_microsecond")){
			        	 
			        	 
			        	 //debe ocupar el canal de entrada de el mismo
			        	 //y de todos los dispositivos bajo su cobertura que esten 
			        	 //conectados al mismo punto de acceso (ademas de ocupar el canal de entrada del AP)
			        	//------------------------------------------------------
			        	 ((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setEntryChannelAsBusy(-1);
			        	 ((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setOutputChannelAsBusy();
			        	 //((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setOutputChannelsAsBusy();
			        	//------------------------------------------------------
			    			 
			         }
			         else if(actionOfNode.equals("occupies_channels_at_the end_of_this_microsecond")){
		        	       // Si un nodo va a mandar una trama de tipo RTS entonces debe ocupar 
		        	       //el canal del AP al que va a mandar la trama y los canales de los nodos
		        	       //que estan bajo su cobertura y estan conectados al mismo punto de acceso.		        	 
		        	     //------------------------------------------------------
	    			      ((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setEntryChannelAsBusy(-1);
				          ((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setOutputChannelAsBusy();
				          ((Node)wirelessChannel.getNodes().get((Integer) ids.get(k))).setOutputChannelsAsBusy();
				        //------------------------------------------------------
			         }		    	 
				 }
			 } 
			 	 
			 
	    	 actions = new ArrayList();
	    	 ids = new ArrayList();	
	    	 
	    	 actionsOfAPs = new ArrayList();
	    	 idsOfAps = new ArrayList();
	     
	    	 if(MainFrame.Cancel()==true){
	    		 i=simulation_time;
	    	 }
	     
	    	 
	    	 
	    	// NOTA: cuando se le pidel el time stoped al AP, si este es cero que devuelva el tiempo que le queda para transmitir el próximo beaconframe
 	    	 
	    	 
	         if(saveTime+i>simulation_time){
	        	 
	        	 saveTime = simulation_time-i;
	         } 	 
	    	 
	    	 //TextOutFlow("(*)PODEMOS SALTARNOS "+saveTime+" microsegundos" );
	    	
	    	 if(saveTime>2 && (i+saveTime-2<simulation_time)){
		
	    		 for(int k=0;k<numOfAps;k++){
		    		
		    		 ((AccessPoint)wirelessChannel.getAccessPoints().get(k)).goForward(saveTime-2);
		    	 }
		    	 for(int j=0;j<numOfNodes;j++){
		    		 
		    		 ((Node)wirelessChannel.getNodes().get(j)).goForward(saveTime-2);
		    	 }
		    	 System.out.println("antes i valia "+i);
		    	 i+=(saveTime-2);		    	 
		    	 System.out.println("ahora i vale "+i);
	    	 }
	    	 
	    	 
	    	 //____________________________________________________________________________________________________________________________________	    	 
	    	 //*************************************************************************************************************************************
	    	
	    	 //...vamos a intentar que el jprogressbar vaya mas fluido
	    	 MainFrame.updadateJProgressBar(i+1);
	    	 //_____________________________________________________________________________________________________________________________________
	     }	 	   
	     
	     
	     Hashtable collisions = WirelessChannel.getCollisions();
	     Enumeration<String> keys =  collisions.keys();
	     
	     main.TextOutFlow("Collisions: ");
	     while(keys.hasMoreElements()){
	    	 
	    	 String KEY = keys.nextElement();
	    	 ArrayList times = (ArrayList) collisions.get(KEY);
	    	 
	    	 
	    	 for(int j=0;j<times.size();j++){
	    		 
	    		 main.TextOutFlow(" time "+times.get(j));
	    	 }	    	 
	     }
	     
	     ArrayList results = WirelessChannel.collectingResults();     
	     
	} 
	
	

	public static void main(String[] args) throws IOException, InterruptedException, BiffException {		
		
		        
			MainFrame intFace;
			DataSet parameters;
			boolean dataSetStatus;			
		
		wirelessChannel = new WirelessChannel();
		getRelationshipBetweenRateAndPower();
	    intFace = new MainFrame();
	    parameters = null;		
	    dataSetStatus = false;
	    	
	    	do{
	    		
			   do{	
				   
					    	 dataSetStatus = MainFrame.getStatusDataSet();
					    	 
					    	 
			   }while(dataSetStatus == false);
					    
					    
					    
					    if(dataSetStatus == true){//significa que hemos pulsado el boton Run Simulation
					    	
					    
					    parameters = MainFrame.getDataSet();
					     	 
					     	 
					     // ConexionType : 1 DCF , 2 adhoc.
					     // physicalLayerConfiguration : 1-802.11 2-802.11a 3-802.11b 4-802.11g.
					     // macLayerConfiguration (basic access or access by RTS/CTS)
					     // simulation_time (seg.).
					     // Packet Size(bites). 
					     // numAP : number of access points.
					     // numNodes : number of nodes wich will conected with access point or between their selves.
					     
						
						if(parameters.getInfraestructureMode().equals("DCF")){
							
							ConexionType = 1;		
						}else{
							
							ConexionType = 2;
						}
						
						Boolean requestToSendActivated = false;
						
						if(parameters.getFisicalLayer().equals("802.11")){
							physicalLayerConfiguration = 1;
							WirelessChannel.AllowedTransferRates.add(1);
							WirelessChannel.AllowedTransferRates.add(2);
							
						}else if(parameters.getFisicalLayer().equals("802.11a")){
							physicalLayerConfiguration = 2;
							WirelessChannel.AllowedTransferRates.add(6);
							WirelessChannel.AllowedTransferRates.add(9);
							WirelessChannel.AllowedTransferRates.add(12);
							WirelessChannel.AllowedTransferRates.add(18);
							WirelessChannel.AllowedTransferRates.add(24);
							WirelessChannel.AllowedTransferRates.add(36);
							WirelessChannel.AllowedTransferRates.add(48);	 		 
							WirelessChannel.AllowedTransferRates.add(54);	
						}
						else if(parameters.getFisicalLayer().equals("802.11b")){
							physicalLayerConfiguration = 3;
							 WirelessChannel.AllowedTransferRates.add(1);
							 WirelessChannel.AllowedTransferRates.add(2);
							 WirelessChannel.AllowedTransferRates.add(5.5);
							 WirelessChannel.AllowedTransferRates.add(11);
						}
						else if(parameters.getFisicalLayer().equals("802.11g")){
							physicalLayerConfiguration = 4;
							WirelessChannel.AllowedTransferRates.add(1);
							WirelessChannel.AllowedTransferRates.add(9);
							WirelessChannel.AllowedTransferRates.add(12);
							WirelessChannel.AllowedTransferRates.add(18);
							WirelessChannel.AllowedTransferRates.add(24);
							WirelessChannel.AllowedTransferRates.add(36);
							WirelessChannel.AllowedTransferRates.add(48);				 
							WirelessChannel.AllowedTransferRates.add(54);
						}
						else if(parameters.getFisicalLayer().equals("802.11n")){
							physicalLayerConfiguration = 5;
							
							WirelessChannel.AllowedTransferRates.add(1);
							WirelessChannel.AllowedTransferRates.add(6);
							WirelessChannel.AllowedTransferRates.add(11);
							WirelessChannel.AllowedTransferRates.add(54);
							WirelessChannel.AllowedTransferRates.add(108);
							WirelessChannel.AllowedTransferRates.add(130);
							WirelessChannel.AllowedTransferRates.add(150);
							WirelessChannel.AllowedTransferRates.add(300);
							
						}
						
						float maximumTransmissionSpeed = (float) ((float)parameters.getSpeed()*Math.pow(10, 6));//pasamos a bits por segundo
						 
					    simulation_time = parameters.getSimulationTime();
					    
					    int PacketSize = 3*8; //1.500 bytes <-tamaño normal del paquete.(en un principio la trama sera del mismo tamaño que el paquete).    
				
					    ArrayList ArrayOfAPs = parameters.getArrayOfAPs();
					    ArrayList ArrayOfNodes = parameters.getArrayOfNodes();
					    
					    	    
					    generateSimulation(
					    		 			ConexionType,
					    		 			requestToSendActivated,
					    		 			physicalLayerConfiguration,
					    		 			maximumTransmissionSpeed,
					    		 			simulation_time,
					    		 			PacketSize,
					    		 			ArrayOfAPs,
					    		 			ArrayOfNodes
					     ); 
				}  
	    }while(true);//espramos a que se pulse el boton Run Simuulation 	    	
		 	
	}//aki termina el main
	
}
