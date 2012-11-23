package ElementsForSim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import Interfaz.MainFrame;

import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class WirelessChannel{
	
	
	public static int nowTime = 0;
	
	static boolean busy = false;
	static int time = 0;//time in us..
    static int simulationTime;//simulation time.     
    public static int timeForSlot;//tiempo en microsegundos.
    public static int timeForDif;
    public static int timeForSif;
    public static int contentWind;		    
    //...el tamaño de paquete se define en el main para poderse ir variando segun la simulacion
    public static int PacketSize; //1.500 bytes <-tamaño normal del paquete.(en un principio la trama sera del mismo tamaño que el paquete).    
    public static int AckSize;
    public static int AssociationRespSize;
    public static int RtsSize;
    public static int CtsSize; 
    public static int RequestFrameSize;
    public static int RtsThreshold;
    public static int frameSize;
    public static int BeaconFrameSize;
    static  ArrayList<AccessPoint> arrayOfAccessPoints;
    static  ArrayList<Node> arrayOfNodes;
    public static int numberOfNodes;
	private static int nodesCompleted;
	public  static float frequency;//frequency depend of physical layer choice.	must be in Ghz
	//public  static float BER;
	public  static float maximumTransmissionSpeed;//speed of transfer selected by the customer
	public  static ArrayList AllowedTransferRates;//list of allowed speed for transmission 
	public  static boolean requestToSendActivated = false;	
	
	private static float totalThroughput;
	
	private static ArrayList ListOfObstacles; 
	
	static Hashtable Collisions =  new Hashtable();//guardará los instantes en los que se han producido
    //colisiones en este canal.
	
	
	public WirelessChannel() throws BiffException, IOException{
		
		
		totalThroughput=0;		
		arrayOfNodes = new ArrayList();	
		AllowedTransferRates = new ArrayList();
		Hashtable Collisions = new Hashtable();
		
		   FileInputStream in = new FileInputStream("basic configuration.xls");
		   Workbook workbook = Workbook.getWorkbook(in);
		   Sheet sheet = workbook.getSheet(0);
		   
		   Cell cell;
		   int column = 0;
		   int row = 2;
		   boolean carryOn = true;
		   
		   String content;
		   NumberCell numCell;
		   Double num;
		   
		   do{
			   cell = sheet.getCell(column,row);
			   content = cell.getContents();
			   
			   if(content == null){
				   carryOn = false;
			   }
			   else if(content.equals("")){
				   carryOn = false;
			   }
			   else{			 				   
				   
				  if(content.equals("Ack size")){
					  
					  column++;
					  cell = sheet.getCell(column,row);
					  content = cell.getContents();
					  numCell = (NumberCell) cell;
					  num = numCell.getValue();	
					  this.AckSize = num.intValue();
				  }
				  else if(content.equals("Association resp. Size")){
					  
					  column++;
					  cell = sheet.getCell(column,row);
					  content = cell.getContents();
					  numCell = (NumberCell) cell;
					  num = numCell.getValue();	
					  this.AssociationRespSize = num.intValue();
				  }
				  else if(content.equals("Rts size")){
					  
					  column++;
					  cell = sheet.getCell(column,row);
					  content = cell.getContents();
					  numCell = (NumberCell) cell;
					  num = numCell.getValue();
					  this.RtsSize = num.intValue();
				  }
				  else if(content.equals("Cts size")){
					  
					  column++;
					  cell = sheet.getCell(column,row);
					  content = cell.getContents();
					  numCell = (NumberCell) cell;
					  num = numCell.getValue();
					  this.CtsSize = num.intValue();
				  }
				  else if(content.equals("Request frame size")){
					  
					  column++;
					  cell = sheet.getCell(column,row);
					  content = cell.getContents();
					  numCell = (NumberCell) cell;
					  num = numCell.getValue();
					  
					  this.RequestFrameSize = num.intValue();
				  }
				  else if(content.equals("Rts threhold")){
					  
					  column++;
					  cell = sheet.getCell(column,row);
					  content = cell.getContents();
					  numCell = (NumberCell) cell;
					  num = numCell.getValue();
					 
					  this.RtsThreshold = num.intValue();
				  }
				  else if(content.equals("Frame size")){
					  
					  column++;
					  cell = sheet.getCell(column,row);
					  content = cell.getContents();
					  numCell = (NumberCell) cell;
					  num = numCell.getValue();
					 
					  this.frameSize = num.intValue();
					  // ... cuando lo lee del archivo xml , esta en bytes
					  //hay que pasarlo a bites
					  this.frameSize = this.frameSize*8;
				  }
				  else if(content.equals("Beacon frame size")){
					  
					  column++;
					  cell = sheet.getCell(column,row);
					  content = cell.getContents();
					  numCell = (NumberCell) cell;
					  num = numCell.getValue();
					  
					  this.BeaconFrameSize = num.intValue();
				  }				   
				  
			       column=0;
			       row++;
			   }
			   
		   }while(carryOn == true);	
		
	}
	
	static public void setListOfObstacles(ArrayList list){
		
		ListOfObstacles = list;
	} 
	
	static public ArrayList getListOfObstacles(){
		
		return ListOfObstacles;
	}
	
	static public void setSimulationTime(int simTime){
		
		simulationTime = simTime;
	}
    static public int  getSimulationTime(){
		
		return simulationTime;
	}
	
	

	static public void incCollisions(String property){
		
		if(property.charAt(0)=='A'){//solo van a contabilizar las colisiones los canales de puntos de acceso.
			
			time = WirelessChannel.nowTime;
			
			if(!Collisions.containsKey(property)){
				
				Collisions.put(property ,new ArrayList());
				((ArrayList)Collisions.get(property)).add(time);
					
			}
			else{
				if(!((ArrayList)Collisions.get(property)).contains(time)){
					((ArrayList)Collisions.get(property)).add(time);
				}
			}
		}
	}
	
	static public Hashtable getCollisions(){
		
		return Collisions;
	}
	static public AccessPoint getAccessPoint(int id){
		
		for(int k=0;k<getAccessPoints().size();k++){
	    	 
   		 if(((AccessPoint)getAccessPoints().get(k)).getID()==id){
   			
   			 return (AccessPoint)getAccessPoints().get(k);
   		 }   		 
	    }		
	return null;
	}
	
    static public void incNodesCompled(){
		
		nodesCompleted++;
	}
    
    public static ArrayList getAccessPoints(){
    
    	return arrayOfAccessPoints;
    }
    
	public int getCurrentTime(){
    	
    	return time;
    }
	
    public void increaseTime(){
    	
    	time = time+1;
    } 
    
    public int getTime(){
    	
    	return time;//return current time.
    }
    
    public void setNodes(ArrayList nodes){
    	
    	this.arrayOfNodes = nodes;
    }
    
    public void setAccessPoints(ArrayList arrayOfAccessPoints){
    	
    	this.arrayOfAccessPoints=arrayOfAccessPoints;
    }
    
    public static ArrayList getNodes(){
	
	return arrayOfNodes;
    }
    
    public static Node getNode(int idNode){
    	
    	for(int i=0;i<arrayOfNodes.size();i++){
    		
    		if((arrayOfNodes.get(i)).getIdNode()==idNode)return arrayOfNodes.get(i);	
    	}
    	return null;
    }
    
    private static double signalLossByDistance(float d,float f){
    	//f-> frequency in Ghz.
    	//d-> distance in meters
    	//Pp = 20log10(d/1000) + 20log10(f*1000) + 32,4
    	
    	if(d==0)return 0;    	
    	float lossInDb;
    	double Pp;
    	Pp = 20*Math.log10((double)(d/(float)1000))+20*Math.log10(f*1000)+32.4;
    	return Pp;
    } 
    
    public static ArrayList collectingResults(){    
    	
    	ArrayList<ArrayList> aux = new ArrayList();
    	float thrBt;
    	int numberOfSuccesfullyFramesSent = 0;
    	int transmissionDuration = 0;
    	int queuingDelay = 0;
    	float utilizationSummation = 0;
    	float mDel;
    	float qDel;
    	float tDel;
    	float jitter = 0;
    	float util = 0;    	
    	
    	for(int j=0;j<arrayOfAccessPoints.size();j++){
    		
    		AccessPoint APaux = (AccessPoint)arrayOfAccessPoints.get(j);
    		
    		thrBt = 0;
    		float timeInSec = WirelessChannel.simulationTime/(float)Math.pow(10,6);
    		
    		float bytesSent = APaux.getNumberOfAckSends()*WirelessChannel.AckSize+APaux.getNumberOfAssociationResponseFrameSends()*WirelessChannel.AssociationRespSize;
    		
    		
    		if(bytesSent!=0){
    			
    			thrBt = (float)((float) (bytesSent*8) / timeInSec)/1024;
        		util  = ((AccessPoint)arrayOfAccessPoints.get(j)).getUtilization();
        		
        		
        		mDel = APaux.getTransmissionDuration()/(float)APaux.getSuccesfulTransmission();
        		//mDel = (float)(mDel * ((float)WirelessChannel.timeForSlot / (float)Math.pow(10, 3)));//dividimos por Math.pow(10,3) para pasar de microsegundos a mili segundos
                	
        		
        		qDel = (float)APaux.getQueuingDelay() /(float) APaux.successfulTransmissions;
        		//qDel = (float)(qDel * ((float)WirelessChannel.timeForSlot / (float)Math.pow(10, 3)));//dividimos por Math.pow(10,3) para pasar de microsegundos a mili segundos
        		
        		
        		tDel = mDel + qDel;
        		
    		}
    		else{//bytesSent==0
    			
    			thrBt = 0;
        		util  = 0;  		
        		
        		mDel = 0;
        		//mDel = (float)(mDel * ((float)WirelessChannel.timeForSlot / (float)Math.pow(10, 3)));//dividimos por Math.pow(10,3) para pasar de microsegundos a mili segundos

        		qDel = (float)APaux.getQueuingDelay() /(float) APaux.successfulTransmissions;
        		//qDel = (float)(qDel * ((float)WirelessChannel.timeForSlot / (float)Math.pow(10, 3)));//dividimos por Math.pow(10,3) para pasar de microsegundos a mili segundos
        			
        		tDel = mDel + qDel;
       	
    		}	
 	    		
    		/*   		
    		
    		thrBt = (float)((float) (bytesSent*8) / timeInSec)/1024;
    		util  = ((AccessPoint)arrayOfAccessPoints.get(j)).getUtilization();
    		
    		
    		mDel = APaux.getTransmissionDuration()/(float)APaux.getSuccesfulTransmission();
    		//mDel = (float)(mDel * ((float)WirelessChannel.timeForSlot / (float)Math.pow(10, 3)));//dividimos por Math.pow(10,3) para pasar de microsegundos a mili segundos
            
    		
    		
    		qDel = (float)APaux.getQueuingDelay() /(float) APaux.successfulTransmissions;
    		//qDel = (float)(qDel * ((float)WirelessChannel.timeForSlot / (float)Math.pow(10, 3)));//dividimos por Math.pow(10,3) para pasar de microsegundos a mili segundos
    		
    		
    		tDel = mDel + qDel;
    	
    		*/
    		

    		
    		jitter = 0;    		
    		float qLngth = 0;
   	
    		ArrayList rows = new ArrayList();
        	rows.add("Access point_"+((AccessPoint)arrayOfAccessPoints.get(j)).getID());
        	rows.add((float)thrBt);
        	rows.add((float)util);
        	rows.add((float)mDel);        	
        	
        	rows.add((float)qDel);
        	rows.add((float)tDel);
        	rows.add((float)jitter);    	
        	rows.add((float)qLngth);
        	aux.add(rows);
    	}
    	
    	for(int i=0;i<arrayOfNodes.size();i++){
    		
    		float timeInSec = WirelessChannel.simulationTime/(float)Math.pow(10,6);
    		thrBt = (float)(((float) ((Node)arrayOfNodes.get(i)).getSuccessfulBitsTransmitted()/(float)timeInSec))/1024;
    		
    			                                                                                                                                                           //throughput en kb/s
    		numberOfSuccesfullyFramesSent+=((Node)arrayOfNodes.get(i)).getFramesSent();
    		
    		util = ((Node)arrayOfNodes.get(i)).getUtilization();
    		utilizationSummation += util;
    		
    		transmissionDuration += ((Node)arrayOfNodes.get(i)).getTransmissionDuration();
    		queuingDelay += ((Node)arrayOfNodes.get(i)).getQueuingDelay();
    	
            if(((Node)arrayOfNodes.get(i)).getFramesSent()>0){
            	
            	mDel = (float)((Node)arrayOfNodes.get(i)).getTransmissionDuration()/(float)((Node)arrayOfNodes.get(i)).getSuccesfulTransmissions();
    			//mDel = (float)(mDel * ((float)WirelessChannel.timeForSlot / (float)Math.pow(10, 3)));//dividimos por Math.pow(10,3) para pasar de microsegundos a mili segundos
              
    			qDel = (float)((Node)arrayOfNodes.get(i)).getQueuingDelay() /(float) ((Node)arrayOfNodes.get(i)).successfulTransmissions;
        		//qDel = (float)(qDel * ((float)WirelessChannel.timeForSlot / (float)Math.pow(10, 3)));//dividimos por Math.pow(10,3) para pasar de microsegundos a mili segundos
        		
        		
        		tDel = mDel + qDel;
        		
        		
        		//(http://www.um.es/atica/qos-en-telefonia-ip) El jitter se define como la variación del tiempo de tránsito de los paquetes. No todos los paquetes sufren un 
        		//retardo constante. Este retardo variable o jitter disminuye la calidad de la voz al superar el umbral de los 50ms.
        		
        		
        		jitter = (float)((Node)arrayOfNodes.get(i)).jitter/ (float)Math.pow(((Node)arrayOfNodes.get(i)).successfulTransmissions - (float)tDel,2);	
        		jitter = (float)Math.sqrt((double)jitter);
        		//jitter = (float)(jitter * ((float)WirelessChannel.timeForSlot / (float)Math.pow(10, 3)));
        		
                
            }
            else {
            	
            	tDel   = 0;
            	mDel   = 0;
            	qDel   = 0;
            	jitter = 0;
            }
    			
    		
    		
    		 float qLngth = ((Node)arrayOfNodes.get(i)).getQueueLenght();
    
		
		ArrayList rows = new ArrayList();
    	rows.add("Node_"+((Node)arrayOfNodes.get(i)).getIdNode());//añadimos el id del nodo
    	rows.add((float)thrBt);
    	rows.add((float)util);
    	rows.add((float)mDel);
    	rows.add((float)qDel);
    	rows.add((float)tDel);
    	rows.add((float)jitter);    	
    	rows.add((float)qLngth);
    	aux.add(rows);
    	
    	}    	
    	
    	return aux;
    }    
    
    private static float converterWtodB(double W){
		
		return (float) (10*Math.log10((double)W));
	}
     
    public static float converterWtodBm(double W){
		
    	double energydB = 10*Math.log10((double)W);
		
    	return (float)(energydB-30);
	}
   
    public static float Impedanced(String element1,String element2) throws BiffException, IOException{// This function calculates the impedece as a result of obstacles
	
    	Float impedanced = new Float(0);
		String material;
    	ArrayList ListOfObstacles = WirelessChannel.getListOfObstacles();
    	ArrayList ListOfMaterials = MainFrame.getListOfMaterials();
    	
    	if(ListOfObstacles!=null){
    		
	    	for(int i=0;i<ListOfObstacles.size();i++){
	    		
	    		ArrayList Row = (ArrayList) ListOfObstacles.get(i);
	    		if(
	    		   Row.get(0).equals(element1)&&Row.get(3).equals(element2)    		   
	    		   ||(Row.get(3).equals(element1)&& Row.get(0).equals(element2))){
	    		   
	    			material = (String) Row.get(2);	
	
	    		   for(int j=0;j<ListOfMaterials.size();j++){
	    			   
	    			   if(((ArrayList)ListOfMaterials.get(j)).get(0).equals(material)){
	    				   
	    				   Float aux = (Float) ((ArrayList)ListOfMaterials.get(j)).get(1);
	    				   impedanced += Float.valueOf(((Float)((ArrayList)ListOfMaterials.get(j)).get(1)));
	    				   break;
	    			   }
	    		   }
	    		}
	    		
	    		
	    	}
    	
    	}
    	else return 0;
    	return impedanced;
    }
   
    public static float ImpedacedForMaterial(String material) throws BiffException, IOException{
    	
    	   ArrayList ListOfMaterials = MainFrame.getListOfMaterials();
    	   float impedanced = 0;  
		   for(int j=0;j<ListOfMaterials.size();j++){
			   
			   if(((ArrayList)ListOfMaterials.get(j)).get(0).equals(material)){
				   
				   Float aux = (Float) ((ArrayList)ListOfMaterials.get(j)).get(1);
				   impedanced += Float.valueOf(((Float)((ArrayList)ListOfMaterials.get(j)).get(1)));
				   break;
			   }
		   }
    	return impedanced;
    }
    
    /*
    public static boolean nodeAunderTheCoverageOfNodeB(Node NodeA,Node NodeB) throws BiffException, IOException{
    	
    	//el nodo A se encuentra bajo la cobertura del nodo B si el nodo A tiene una sensibilidad superior a la potencia
    	//recibida del nodo B
    	
    	
    	//1.-primero calculamos la potencia que llega al nodo A del nodo B
    	
    	float energyOfNodeB_dB = converterWtodB(NodeB.getEnergyEmitedW());
    	
    	ArrayList possitionNodeA = NodeA.getPossition();
    	ArrayList possitionNodeB = NodeB.getPossition();
    	
    	
    	int distanceX = (Integer)possitionNodeA.get(0)-(Integer)possitionNodeB.get(0);
	    int distanceY = (Integer)possitionNodeA.get(1)-(Integer)possitionNodeB.get(1);
	    int distanceZ = (Integer)possitionNodeA.get(2)-(Integer)possitionNodeB.get(2);
	    
	    float distanceBetweenNodesAandB = (float) Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2)+Math.pow(distanceZ,2));		    
	    float energyLossByDistance = (float) signalLossByDistance(distanceBetweenNodesAandB,WirelessChannel.frequency);
    	
    	
    	//2.- a la perdida de potencia debida a la distancia debemos restarle la pérdida debido a obstáculos.
    	
    	//...FALTA RESTAR LA IMPEDANCIA DE LOS OBSTACULOS
    	
    	
    	float impedanced = Impedanced("Node "+NodeA.getIdNode(),"Node "+NodeB.getIdNode());
    	
    	
    	
    	float energyReceivedByNodeA = energyOfNodeB_dB - energyLossByDistance-impedanced;
    	
    	//3.- como el array de sensibilidad del nodo esta ordenado de mayor a menor , cogemos la menor de las sensibilidades 
    	ArrayList sensitivity = NodeA.getSensitivity();
    	float lowerSensitivity_dB = (Float) sensitivity.get(sensitivity.size()-1)+30;//sumamos  30 para pasar de dBm a dB.
    	
    	
    	if(energyReceivedByNodeA < lowerSensitivity_dB)return false;
    	else return true;
    	
    }
    */
    
    static float calculateEnergyReceivedByDevice_A(ArrayList possitionDeviceA,ArrayList possitionDeviceB,float energyDeviceBinW){

    	//1.-primero calculamos la potencia que llega al device A del device B
    	
    	float energyOfNodeB_dB = converterWtodB(energyDeviceBinW);
    	
    	int distanceX = (Integer)possitionDeviceA.get(0)-(Integer)possitionDeviceB.get(0);
	    int distanceY = (Integer)possitionDeviceA.get(1)-(Integer)possitionDeviceB.get(1);
	    int distanceZ = (Integer)possitionDeviceA.get(2)-(Integer)possitionDeviceB.get(2);
	    
	    float distanceBetweenNodesAandB = (float) Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2)+Math.pow(distanceZ,2));		    
	    float signalLoss_dB = (float) signalLossByDistance(distanceBetweenNodesAandB,WirelessChannel.frequency);
	    float signalLoss_dBm = signalLoss_dB -30;
    	return signalLoss_dBm;
    }
    
    
    
    public static boolean deviceAunderTheCoverageOfdeviceB(String deviceA,String deviceB) throws BiffException, IOException{

	
	boolean deviceAisAP = false;
	boolean deviceBisAP = false;
	AccessPoint accessPointA = null;
	Node nodeA = null;
	AccessPoint accessPointB = null;
	Node nodeB = null;
	
	if(deviceA.charAt(0)=='A'){//vamos a modificar un punto de acceso		
		//buscar en el array de APs el ap a modificar
		int idAP = Integer.parseInt(String.valueOf(deviceA.charAt(deviceA.length()-1)));		
		//mostrar la ventana para modificar puntos de acceso
			
		for(int i=0;i<MainFrame.ArrayOfAPs.size();i++){			
			if(MainFrame.ArrayOfAPs.get(i).getID()==idAP){				
				accessPointA = MainFrame.ArrayOfAPs.get(i);				
			}
		}
		deviceAisAP=true;
	}
	else{
		int idNode = Integer.parseInt(String.valueOf(deviceA.charAt(deviceA.length()-1)));
		
		//mostrar la ventana para modificar puntos de acceso
		Node nodeAux = null; 
		
		for(int i=0;i<MainFrame.ArrayOfNodes.size();i++){
			
			if(MainFrame.ArrayOfNodes.get(i).getIdNode()==idNode){
				
				nodeA = MainFrame.ArrayOfNodes.get(i);
				
			}
		}		
		
	}
	
	if(deviceB.charAt(0)=='A'){//vamos a modificar un punto de acceso		
		//buscar en el array de APs el ap a modificar
		int idAP = Integer.parseInt(String.valueOf(deviceB.charAt(deviceB.length()-1)));		
		//mostrar la ventana para modificar puntos de acceso
		AccessPoint APaux = null;		
		for(int i=0;i<MainFrame.ArrayOfAPs.size();i++){			
			if(MainFrame.ArrayOfAPs.get(i).getID()==idAP){				
				accessPointB = MainFrame.ArrayOfAPs.get(i);				
			}
		}
		deviceBisAP=true;
	}
	else{
		int idNode = Integer.parseInt(String.valueOf(deviceB.charAt(deviceB.length()-1)));
		
		//mostrar la ventana para modificar puntos de acceso
		Node nodeAux = null; 
		
		for(int i=0;i<MainFrame.ArrayOfNodes.size();i++){
			
			if(MainFrame.ArrayOfNodes.get(i).getIdNode()==idNode){
				
				nodeB = MainFrame.ArrayOfNodes.get(i);
				
			}
		}		
		
	}
	
	
	if(deviceAisAP && !deviceBisAP){// device A es un AP device B es un nodo 
		
      	
    	//1.-primero calculamos la potencia que llega al device A del device B
    	
    	float energyOfNodeB_dB = converterWtodB(nodeB.getEnergyEmitedW());
    	
    	ArrayList possitionAccessPointA = accessPointA.getPossition();
    	ArrayList possitionNodeB = nodeB.getPossition();
    	
    	
    	 float  signalLoss_dBm = calculateEnergyReceivedByDevice_A(possitionAccessPointA,possitionNodeB,nodeB.getEnergyEmitedW());
		    
		    float energyOfDeviceB_dBm = converterWtodBm(nodeB.getEnergyEmitedW());	    
		    
		    float impedanced = Impedanced("Access point "+accessPointA.getID(),"Node "+nodeB.getIdNode());
	    	
		    if(impedanced>0){
		    	
		    	impedanced-=30;
		    }
		//2.- a la perdida de potencia debida a la distancia debemos restarle la pérdida debido a obstáculos.
	    	
		float energyReceivedByDeviceA = energyOfDeviceB_dBm - signalLoss_dBm - impedanced;    	
	        	
    	//3.- como el array de sensibilidad del nodo esta ordenado de mayor a menor , cogemos la menor de las sensibilidades 
    	ArrayList sensitivity = accessPointA.getSensitivity();
    	float lowerSensitivity_dB = (Float) sensitivity.get(sensitivity.size()-1);
    	
    	
    	if(energyReceivedByDeviceA < lowerSensitivity_dB)return false;
    	else return true;
	}
	
	if(!deviceAisAP && deviceBisAP){//deviceA is node  ... deviceB is AP
			
		 // ESTA ES LA QUE VALE
	      	
	    	//1.-primero calculamos la potencia que llega al device A del device B
	    	
	    	
	    	
	    	ArrayList possitionNodeA = nodeA.getPossition();
	    	ArrayList possitionAccessPointB = accessPointB.getPossition();
	    		    
		    float  signalLoss_dBm = calculateEnergyReceivedByDevice_A(possitionNodeA,possitionAccessPointB,accessPointB.getEnergyEmitedW());
		    
		    float energyOfDeviceB_dBm = converterWtodBm(accessPointB.getEnergyEmitedW());	    
		    
		    float impedanced = Impedanced("Node "+nodeA.getIdNode(),"Access point "+accessPointB.getID());
	    	
		    if(impedanced>0){
		    	
		    	impedanced-=30;
		    }
		  //2.- a la perdida de potencia debida a la distancia debemos restarle la pérdida debido a obstáculos.
	    	
		    float energyReceivedByDeviceA = energyOfDeviceB_dBm - signalLoss_dBm - impedanced;    	
	    
	    	
	    	//3.- como el array de sensibilidad del nodo esta ordenado de mayor a menor , cogemos la menor de las sensibilidades 
	    	
		    ArrayList sensitivity = nodeA.getSensitivity();
	    	float lowerSensitivity_dBm = (Float) sensitivity.get(sensitivity.size()-1);
	    	
	    	
	    	if(energyReceivedByDeviceA < lowerSensitivity_dBm)return false;
	    	else return true;
		}
		
	if(!deviceAisAP && !deviceBisAP){//deviceA is node  ... deviceB is node
		
      	
    	//1.-primero calculamos la potencia que llega al device A del device B
    	
    	float energyOfDeviceB_dB = converterWtodB(nodeB.getEnergyEmitedW());
    	
    	ArrayList possitionNodeA = nodeA.getPossition();
    	ArrayList possitionNodeB = nodeB.getPossition();
    	
    	
    	 float  signalLoss_dBm = calculateEnergyReceivedByDevice_A(possitionNodeA,possitionNodeB,nodeB.getEnergyEmitedW());
		    
		    float energyOfDeviceB_dBm = converterWtodBm(nodeB.getEnergyEmitedW());	    
		    
		    float impedanced = Impedanced("Node "+nodeA.getIdNode(),"Node "+nodeB.getIdNode());
	    	
		    if(impedanced>0){
		    	
		    	impedanced-=30;
		    }
    	
    	//2.- a la perdida de potencia debida a la distancia debemos restarle la pérdida debido a obstáculos.
    	
    	//...FALTA RESTAR LA IMPEDANCIA DE LOS OBSTACULOS
    	
        	
    	float energyReceivedByDeviceA = energyOfDeviceB_dBm - signalLoss_dBm-impedanced;
    	
    	//3.- como el array de sensibilidad del nodo esta ordenado de mayor a menor , cogemos la menor de las sensibilidades 
    	ArrayList sensitivity = nodeA.getSensitivity();
    	float lowerSensitivity_dBm = (Float) sensitivity.get(sensitivity.size()-1); 
    	
    	
    	if(energyReceivedByDeviceA < lowerSensitivity_dBm)return false;
    	else return true;
	}
	
    	return false;
    }
     
    // sizeOfFrame ->  bits.
    // transmissionRate -> bits/s.
    public static int calculateChannelBusyTime(int sizeOfFrame , float transmissionRate){
    
       //transmission rate debe llevar a esta función en bits/seg.
    	//como esta funcion debe calcular el tiempo en micro segundos debemos hacer la siguiente operacion
       float transmissionRateInUs = (float)transmissionRate/(float)Math.pow(10, 6);
       
       int ret= Math.round((float)sizeOfFrame/(float)transmissionRateInUs);	
       if(ret==0)return 1;
       else return ret;
    }
    
    public static float getMaximumTransmissionSpeed(){
    	
    	return maximumTransmissionSpeed;
    }
    
    public static float calculateSpeedMoreApropriate(ArrayList TransmissionSpeed, ArrayList sensitivity,float energy){
    	
    	int k = 0;
		boolean carryOn = false;
		float rate = 0;//el valor de esta variable debe enviarse al nodo
		//que esta solicitando la conexion
		//hay que empezar mirando la sensibilidad que tiene el AP para la velocidad de tranmision 
		//seleccionada por el usuario...
		 
		 //la variable maxSpeed contiene la velocidad escogida por el usuario en la interface
		 float maxSpeed =  WirelessChannel.getMaximumTransmissionSpeed();
		 //maxSpeed viene en bits/seg.
		 float maxSpeedMbS = (float)maxSpeed /(float)Math.pow(10, 6);
		 int possitionOfmaxSpeed = TransmissionSpeed.indexOf(maxSpeedMbS);
		 
		 /*
		//- - - - - - - - - - - - - - - - - - - - - - - - - - -
		//pasamos la lista "sensitivity" de dBm dB- - - - - - - 
		 ArrayList sensitivity_dB = new ArrayList();
		 for(int i=0;i<sensitivity.size();i++){
			 
			 sensitivity_dB.add((Float)sensitivity.get(i)+30);
		 }//- - - - - - - - - - - - - - - - - - - - - - - - - - - 
		 */
		 
		 for(k = possitionOfmaxSpeed;k<sensitivity.size();k++){
			 
			 if(energy >= (Float)sensitivity.get(k)){				 
				 
				 rate = (Float)TransmissionSpeed.get(k);		    			    
				
				 break;
			 }
		 }
		 
		 if(k == sensitivity.size()){
			 // el nodo no alcanza al punto de acceso , no puede comunicarse con el 
			 // deberia seguir escuchando beacon frames de otros puntos de acceso ...
			 
			 return 0;
		 }		    		 
		 else{
			 
			 return rate;
		 }
		
    }

    public static void resetListOfObstacles(){
    	
    	ListOfObstacles = new ArrayList();
    }
    
    public static ArrayList getPossition(String device){
    	
    	ArrayList possition = null;
    	if(device.charAt(0)=='A'){//vamos a modificar un punto de acceso
			
			//buscar en el array de APs el ap a modificar									
			int idAP = Integer.parseInt(String.valueOf(device.charAt(device.length()-1)));
			
			
			AccessPoint APselected = null; 
			
			for(int i=0;i<arrayOfAccessPoints.size();i++){
				
				if(arrayOfAccessPoints.get(i).getID()==idAP){
					
					APselected = arrayOfAccessPoints.get(i);
					possition = APselected.getPossition();
				    return possition;
				}
			}			
    	}	
    	else{
    		
    		int idNode = Integer.parseInt(String.valueOf(device.charAt(device.length()-1)));
			
			//mostrar la ventana para modificar puntos de acceso
			Node NodeSelected = null; 
			
			for(int i=0;i<arrayOfNodes.size();i++){
				
				if(arrayOfNodes.get(i).getIdNode()==idNode){
					
					NodeSelected = arrayOfNodes.get(i);
					possition = NodeSelected.getPossition();
				    return possition;
				}
			}
    	}
    	return possition;
    }
    
    
}
