package ElementsForSim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JPanel;

import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import Diagram.Diagram;
import Diagram.ObjectPlotted;
import Interfaz.MainFrame;
import Interfaz.NodeConfigurationPanel;
import Main.main;

public class Node{
	
	public NodeConfigurationPanel nodeConfigurationPanel;
	
	Channel entryChannel; // entry channel.
	Channel outputChannel;// channel for outputs.    
	
	Frame ObjetFrameReceived;
	
	WirelessChannel objectForWC;//Through this object , the  node can comunicate with his
	                            //access point.
		
	boolean nowIsTranssmitting;
	int numberOfPackages;
	int RtsThreshold;
	boolean requestToSendActivated = false;
	
	int backoffCounter;	//The backoff counter.	
	//--location variables...
	    int x;
	    int y;
        int z;
	
    int contWind;//The contention window
    int idNode;//
	long STT;// Succefull transmision time. (timpo de transmisiones exitosas).
	long IT;// inactive time .(tiempo no activo ,... ejecutando backof).
	float Throughput; // throwput time of these node.
	
	
    float rate_NodeToAp;
    float rate_APtoNode;
	
    ArrayList<Float> sensitivity = new ArrayList<Float>();
	ArrayList<Float> TransmissionSpeed = new ArrayList();
	ArrayList<Frame> FramesForSend = new ArrayList();
	public ObjectPlotted objPlotted;    
	
	int numberOfPackets;
    int numberOfRemainingPackets;
    int numberOfColisions;
      
    int framesSent;
    boolean nodeCanTransmit = false;
    boolean backoffHasPreviouslyExecuted = false;
    int state=1;
    int numberOfPacketsSent=0;
    int indexFrame=0;
    int idAccessPointConected = -1;//id del AP al ke se ha conectado.
    int idAccessPointHeard=-1;//id del AP del cual ha recibido el primer beacon frame.
    boolean beaconFrameReceived;
	boolean requestFrameSent;
	boolean AckReceived;
	int timeStoped;
	int previousState = -1;
	int numberTotalOfFramesSent=0;
	
	//Energia irradiada Energía irradiada [dBm] = Energía de transmisor [dBm] - pérdida de cable [dB] + ganancia de antena[dBi]
	//maximo legal 100mW
	float Energy_mW;//estará en mW.
	public float Energy_W;
	int successfulTransmissionTime=0;
	
	
	public int successfulBits; //-> numero de bits transmitidos con exito
	public int successfulTransmissions; //-> numero de tramas transmitidos con exito
	public int transmissionDuration;// -> suma de todos los tiempos de transmission desde que se intenta mandar el paquete hasta que se manda con exito
	public int queuingDelay; //-> suma de todos los tiempos de retraso en cola.... 
	                  //el tiempo de retraso en cola es el tiempo desde que nace
	                  //hasta que se intenta mandar el paquete 
    public int jitter;
	
	int interArTime;	
	
	int BackoffTime; 
    ArrayList<Frame> ExitBuffer = new ArrayList();
    AccessPoint AP = null;
    
    char distr_ForInterArTime = 'c';//tipo de distribucion (c - constante,u - uniforme ,e - exponencial) 
    int mean_ForInterArTime = 10;
    char distr_ForLenght = 'c';//tipo de distribucion (c - constante,u - uniforme ,e - exponencial) 
    int mean_ForLenght = 256;//...recordar que esta variable debe darse en bytes.
    //mínimo=256 y máximo=2312 Bytes)
		
   int GenLength(char distr_ForLenght, int mean_ForLenght){
    	
	   int frameLength;
	   int MAX_PKT = 2312;//2312 Bytes
	   switch(distr_ForLenght){
	   
    		case 'c':    			
    			frameLength = mean_ForLenght;
    		break;    			
    		case 'u':
    			
				//We must generate a value between 1 and 2*mean-1.
				//We generate a value between 0 and 2*mean-2:
				//(2*mean-1)*rand01. We add 1 in order to
				//generate between 1 and 2*mean-1.
				/*
    			float rand01 = (float)rand()/(float)(RAND_MAX+1);
				pktLength = (int) ((2*mean_ForLenght-2)*rand01+1);
				*/
    			frameLength = (int) main.RANDOM(256, 2*mean_ForLenght-1);
    			
				//If the packet is larger than the maximum packet
				//then split the packet but ignore the small packet.
				//This has a small effect in the simulation.
				if (frameLength > MAX_PKT)
					frameLength = MAX_PKT;
				break;    			
    		default:
    			//This means in exponential case ('e') and in any
				//other case (poisson or mistake), the exponential
				//distribution will be used
			
				/*
				float rand01 = (float)rand()/(float)(RAND_MAX+1);
				//Generate a packet greater or equal to 1.
				pktLength = 1-(int)(mean*log(rand01));
				*/
    				
    			float rand01 = main.RANDOM(0, 1);
			    frameLength = 1-(int)(mean_ForLenght*Math.log(rand01));
			
				//If the packet is larger than the maximum packet
				//then split the packet but ignore the small packet.
				//This has a small effect in the simulation.
				if (frameLength > MAX_PKT)
					frameLength = MAX_PKT;
				break;
    		}
	   
	    return frameLength;
    }

    //Generates the interarrival time parameters.
    void GenInterArTime(char distr_ForInterArTime, int mean_ForInterArTime){
    		
    	switch(distr_ForInterArTime){
    		
    	case 'c':
    			
				interArTime = mean_ForInterArTime;
				break;
    			
    	case 'u':
    			
				//We must generate a value between 1 and 2*mean-1.
				//We generate a value between 0 and 2*mean-2:
				//(2*mean-1)*rand01. We add 1 in order to
				//generate between 1 and 2*mean-1.
				/*
    		    float rand01 = (float)rand()/(float)(RAND_MAX+1);
				interArTime = (int) ((2*mean_ForInterArTime-2)*rand01+1);
				*/
				interArTime = (int) main.RANDOM(1, 2*mean_ForInterArTime-1);
				break;
    			
		default: //This means in exponential case ('e') and in any
				//other case of mistake, the exponential distribution
				//will be used
			
				/*
			    float rand01 = (float)rand()/(float)(RAND_MAX+1);
				//Generate an arrival interval greater or equal to 1.
				interArTime = 1-(int)(mean_ForInterArTime*log(rand01));
				*/
				
				float rand01 = main.RANDOM(0, 1);
			    interArTime = 1-(int)(mean_ForInterArTime*Math.log(rand01));
			
				
				break;
			
		}
    }
   
    public void ResetObjPlotted(){
    	
    	this.objPlotted = new ObjectPlotted("Node.jpg","Node "+String.valueOf((Integer)this.getIdNode()));
   }
     
    public void initialize(){    	
    	
    	state = 1;
    	BackoffTime = 0;
    	i=3;
    	contWind = 0;
    	//SlotsForWait=0;
    	timeStoped  = -1; 
        
    	beaconFrameReceived = false;
        requestFrameSent = false;
        AckReceived = false;
        
        this.numberOfPackets = 1;
    	this.numberOfColisions = 0;
    	this.framesSent = 0;
    	
    	this.successfulBits = 0;
    	this.successfulTransmissions = 0;
    	this.transmissionDuration = 0;
    	this.queuingDelay = 0;
    	
    	this.rate_APtoNode = 0;
    	this.rate_NodeToAp = 0;
    }
    
    public int getIdAccessPointHeard(){
    	
    	return this.idAccessPointHeard;
    }
    
    public Node(WirelessChannel objectForWC,int idNode,int x, int y, int z) throws BiffException, IOException{
    	
    	//initialize();
    	this.x = x;
    	this.y = y;
    	this.z = z;
    	nowIsTranssmitting = false;
    	this.objectForWC = objectForWC;
    	entryChannel = new Channel(new String("Node"+this.idNode));
    	this.idNode = idNode;
    	
    	initialize();
    	//_________________________________________
    	    	
    	//Estos datos los vamos a coger de un .exel   	
		   
    	
		   FileInputStream in ;
		   Workbook workbook;
		   Sheet sheet;
		   
		   Cell cell;
		   int row;
		   int column;
		   boolean carryOn = true;
		   
		   String content;
		   NumberCell numCell;
		   Double num;
		   
		   //Ahora vamos a colocar en cada nodo la lista de velocidades posibles y sus respectivas sensibilidades
		     

		   column = 0;
		   
	   	   in = new FileInputStream("basic configuration.xls");
		   workbook = Workbook.getWorkbook(in);
		   sheet = workbook.getSheet(0);   	
		
		   
		  
		   row = 2;
		   carryOn = true;		   

		   
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
				   
				  if(content.equals("Energy radiated by Nodes")){
					  
					  column++;					  
					  cell = sheet.getCell(column,row);
					  content = cell.getContents();
					  numCell = (NumberCell) cell;
					  num = numCell.getValue();	
					  Energy_mW = num.floatValue();
					  carryOn = false;
				  }
				  else{
					  row++;
				  }
			   }	
			   
		}while(carryOn == true);
		   
		this.Energy_W = (float) (Energy_mW*Math.pow(10, -3));//Energy  in mW , energyEmitted in W.    		
		this.objPlotted = new ObjectPlotted("Node.jpg","Node "+String.valueOf((Integer)this.getIdNode()));
		
		//...
		configuration();
	} 

    public void configuration() throws BiffException, IOException{
    	
    	String type = String.valueOf(MainFrame.jComboBoxPhysicalLayer.getSelectedItem());
    	ArrayList Aux = main.getRatesAndSensitivityForPhysicalTechnology(type);    	
	     
	     for(int t=0;t<Aux.size();t++){
	    	 
	    	 TransmissionSpeed.add((Float) ((ArrayList)Aux.get(t)).get(0));
	    	 sensitivity.add((Float) ((ArrayList)Aux.get(t)).get(1));
	     }		
		    	     
    }
    
    public float getEnergyEmitedmW(){
    	
    	return this.Energy_mW;
    }
    
    public float getEnergyEmitedW(){
    	
    	return this.Energy_W;
    }
	
    public ObjectPlotted getObjectPlotted(){
		return this.objPlotted;
	}
	
    public void addElementPlotted(Diagram ele){    	
    	this.objPlotted.addElement(ele);
    }   
	     
    public void removeLastElementPlottedAddition(){
    	
    	this.objPlotted.removeLastElement();
    }
    
    public ArrayList getSensitivity(){    	
    	
    	return sensitivity;
    }
    
    public ArrayList getTransmissionSpeed(){
    	
    	return TransmissionSpeed;
    }
    
    public ArrayList getPossition(){
    	
    	ArrayList pos = new ArrayList();
    	pos.add(x);
    	pos.add(y);
    	pos.add(z);    	    	
    	return pos;
    }
     
    public void setPossition(ArrayList<Integer> pos){
    	
    	this.x = (int) pos.get(0);
    	this.y = (int) pos.get(1);
    	this.z = (int) pos.get(2);
    }
    
    private void transmitFrameToAccesPoint(Frame frame)throws InterruptedException{
 	
    	
    	this.outputChannel.Write(frame);			    			    	    					
        
    }
   
    
ArrayList NodesUnderCoverage = new ArrayList();

public void sendsInBroadcast(Frame frameToSend) throws BiffException, IOException{
    	
	
	
	if(NodesUnderCoverage.size()==0){
		
	
    	//Accede al wirlesschannel para recoger todos los nodos
		ArrayList arrayOfNodes = WirelessChannel.getNodes();
		//busca los nodos bajo su cobertura.
		//se solapa.		
		
		
		for(int i=0;i<arrayOfNodes.size();i++){
			
			ArrayList possitionNode = ((Node)arrayOfNodes.get(i)).getPossition();		   
		    
		    if(     ((Node)arrayOfNodes.get(i)).getIdNode()!=this.getIdNode()
		    		&&		    		
		    		WirelessChannel.deviceAunderTheCoverageOfdeviceB("Node "+((Node)arrayOfNodes.get(i)).getIdNode()
		    		                                                 ,"Node "+this.getIdNode()
		    		                                                  )	                                                  
		       ){
		    	
		    	//Si el nodo esta bajo la cobertura de este nodo
		    	//debe mandarle la trama RTS...
		    	NodesUnderCoverage.add((Node)arrayOfNodes.get(i));
		    	try {
		    		
					((Node)arrayOfNodes.get(i)).WriteBuffer(frameToSend);
				
		    	} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    }
		}	
	}else{
		for(int i=0;i<NodesUnderCoverage.size();i++){
			try {
	    		
				((Node)NodesUnderCoverage.get(i)).WriteBuffer(frameToSend);
			
	    	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
  }
    
    
    int timeReserve;
    int destinyOfCTS;
    
    private String checkChannel(){
    	
    	ObjetFrameReceived = (Frame)this.entryChannel.Read();
    	
    	if(ObjetFrameReceived != null){
    		
    		if(ObjetFrameReceived.getType().equals(new String("BeaconFrame")) == true){
	    		
	    		this.entryChannel.Write(null);
	   		    return "BeaconFrame";
	    	}
    		else if(ObjetFrameReceived.getType().equals(new String("ACK")) == true){	
	    		 
	    		 this.entryChannel.Write(null);
	    		 return "Ack";  		 
	    	}
	    	else if(ObjetFrameReceived.getType().equals(new String("CTS")) == true){
	    		
	    		
	    		int idOrigin = ObjetFrameReceived.getOrigin();           	
	    		
	    		this.entryChannel.Write(null);
	    		
	    		timeReserve = ((CTS_Frame)ObjetFrameReceived).getTimeReserve();
	    		
	    		destinyOfCTS = ((CTS_Frame)ObjetFrameReceived).getDestiny(); 
	    		
	    		return "Cts";
	    	}
	    	else if(ObjetFrameReceived.getType().equals(new String("AssociationResponse")) == true){
	    		
	    		this.entryChannel.Write(null);
	   		    return "AssociationResponse";
	    	}
            else if(ObjetFrameReceived.getType().equals(new String("RTS")) == true){
            	
            	int idOrigin = ObjetFrameReceived.getOrigin();
            	
	    		this.entryChannel.Write(null);
	   		    return "RTS";
	    	}
    	}
    	return null;
    } 
  
    public boolean WriteBuffer(Frame flow) throws InterruptedException{
		
		this.entryChannel.Write(flow);
        return true;
	}
	
    public Frame ReadBuffer() throws InterruptedException{
		
	    Frame frame = (Frame)this.entryChannel.Read();		
		return frame;
	}
      
    public int getIdNode(){
    	
    	return this.idNode;
    }
    
    public void viewChannelAsFree(){  //esta funcion es ejecutada por el punto de acceso 
    	                             //al que esta enlazado este nodo cuando el canal es liberado
    	
    	if(state == 3 ){
    		timeStoped = WirelessChannel.timeForDif;
    		state = 2;
    	}
    }
    
    
    public int ejectBackoff() throws InterruptedException{
    	
        return BackoffAlgorithm("null");
    }
    
    
    public int ejectBackoff(String aux) throws InterruptedException{    	
    	
        return BackoffAlgorithm(aux);
    }
    
    
    int i=3;
    int SlotsForWait;
    
    public int BackoffAlgorithm(String IN)throws InterruptedException{
    
    	//int SlotsForWait = 0;
    	
    	if(IN.equals("inc") && this.contWind<255){
    		i++;    			
    	}
    	else if(IN.equals("res")){
    		i=3;
    		return -1;
    	} 
    	
    	contWind     = (int) (Math.pow(2,this.i)-1);            //Backoff_time = [CW x rand()]x SlotTime.
    	SlotsForWait = (int) (main.RANDOM(0,this.contWind));//
    	
		return (int) ((SlotsForWait)*(float)WirelessChannel.timeForSlot);   	       
    }
    
    public int getIdAccessPointConected(){
    
    	return this.idAccessPointConected;
    }
     
    public int getInterArTime(){
    	
    	return interArTime;
    }
    
    boolean configurated = false; 
    boolean itHasJustSendRts = false;
    boolean collisionDetected = false;
    int sizeOfTheLastFrameSent = 0;
        
    public String Run(int currentTime) throws InterruptedException, BiffException, IOException{	
    	
   	    if(configurated==false){
   		
    		configuration();
    		configurated=true;
    	}
    	
    	if(this.rate_NodeToAp==0){    	 
    		
    		//hay que inicializar esta variable con la menor de las velocidades permitidas    		
    		this.rate_NodeToAp = (Integer) WirelessChannel.AllowedTransferRates.get(0);		
    	}
    		
    	// sizeOfFrame ->  bits.
        // transmissionRate -> bits/s.
    	
    	int dataFrameSize = 0;
    	int sizeOfFrameCTSorACK = 0;
    	float transmissionRate = 0;
    	
    	// Si interArTime esta a cero tenemos que generar un nuevo interArTime y un nuevo frame y añadirlo 
    	//a la cola de frames  
        
    	if(interArTime == 0){
    		
    		dataFrameSize = GenLength(distr_ForLenght,mean_ForLenght);
    		this.FramesForSend.add(
    				                new Frame("Data",this.idNode,this.idAccessPointConected,i,currentTime,dataFrameSize)
    				               );        		
    		
    		GenInterArTime(distr_ForInterArTime,mean_ForInterArTime);    	
    	}
    	else interArTime--;
       
    	if(ExitBuffer.size()==0 && this.FramesForSend.size()>0){//sacamos de la cola de tramas una trama y la ponemos en el buffer de salida
             
	    	 ExitBuffer.add(this.FramesForSend.get(0));//el frame sale de la cola
	    	 this.FramesForSend.remove(0);
			 this.queuingDelay += currentTime-ExitBuffer.get(0).getMomentThatIsCreated();
			 ExitBuffer.get(0).setStartTransmission(currentTime);
	    }   
    	
    	String typeOfFrameReceived = checkChannel();   	
    	 	
    	switch(state){    	
	
    	case 1://1.- ...se pone a la escucha de algún beacón frame   	
    			
    		if(typeOfFrameReceived != null){
	    		
    			if(typeOfFrameReceived.equals("BeaconFrame")){
	    			
	    			beaconFrameReceived = true;
	    		}
	    		else beaconFrameReceived = false;
    		}
    		
    		if(beaconFrameReceived == false){
    			
    			
    			main.TextOutFlow("NODE: "+this.idNode+" searching beacom frames (passive mode)...");
    			
    			state = 1;//... continúa en el mismo estado.    			
    		}
    		else if(beaconFrameReceived == true){    			 
    			
    			beaconFrameReceived = false;
    			
    			//...analiza el beacon frame recogido (esto ya lo hace la funcion waiting for beacon frame)
    			//y se prepara para mandar una solicitud de conexión.
    			outputChannel = ((BeaconFrame)this.ObjetFrameReceived).getReferenceOfAP();//referencia al canal de entrada del AP
    			this.idAccessPointHeard = ((BeaconFrame)this.ObjetFrameReceived).getOrigin();
    			//System.out.println("NODO: "+this.idNode+" capta beacon frame de AP "+this.idAccessPointConected);
    			main.TextOutFlow("NODE: "+this.idNode+" capta beacon frame de AP "+this.idAccessPointHeard);
    			
    			
    		    AP = WirelessChannel.getAccessPoint(this.idAccessPointHeard);
    			
    		    ArrayList possition_device1 = AP.getPossition();		   
			   			    
			    ArrayList possition_device2 = this.getPossition();		   
			  
    		    float signalLossByDistanceIndBm = WirelessChannel.calculateEnergyReceivedByDevice_A(possition_device1,possition_device2,this.Energy_W);

    		    float impedanced = WirelessChannel.Impedanced("Node "+this.idNode,"Access point "+AP.getID());
    		    
    		    if(impedanced != 0){
    		    	impedanced-=30;//la impedancia de los osbtáculos viene en dB , para pasar a dBm restamos 30 
    		    }
    		    
    		    
    		    float energyReceivedByTheAPindBm = WirelessChannel.converterWtodBm(this.Energy_W) - signalLossByDistanceIndBm -impedanced;
    		   	    	
    		    
		    	//tenemos que calcular la velocidad de transmision a la que el AP 
		    	//debe transmitir este nodo , en funcion de la energia captada del beacon frame
		    	
		    	float energyReceivedFromAP = ((BeaconFrame)ObjetFrameReceived).getEnergyOfSignal();
		    	
		    	//estas es la velocidad que debe comunicar el nodo al punto de acceso para ke se comunique con el:
    		    this.rate_APtoNode = WirelessChannel.calculateSpeedMoreApropriate(this.TransmissionSpeed, this.sensitivity,energyReceivedFromAP);
    		    	
    		    if(rate_APtoNode == 0){
    		    	
    		    	//el AP no alcanza a este nodo...
    		    	//...esto no deberia pasar nunca ya que el AP solo manda beacon frames 
    		    	//a los nodos que tiene alcance
    		        state = 0;
    		    }
    		    else{
    		    	
    		    	Frame AUX = new RequestFrame("RequestFrame",this.idNode,this.idAccessPointHeard,1,currentTime,WirelessChannel.RequestFrameSize,energyReceivedByTheAPindBm);
				    
        		    ((RequestFrame)AUX).setRate_APtoNode((int)this.rate_APtoNode);
        		    
        			//colocamos el frame el primero en la cola de envios.
        			this.ExitBuffer.set(0, AUX);
        			
        			timeStoped = WirelessChannel.timeForDif;
        			state = 2; 	
    		    }    		    		 
    			
    		}
    		this.nowIsTranssmitting = false;    		
    		
    		if(state!=1 || (this.objectForWC.getSimulationTime()-1) == currentTime){
    			//************DIBUJA******************************************************************
    			//************************************************************************************  
    			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 			    			
    			//************************************************************************************
    			//************************************************************************************
    		}
 			
 		break;    	
    	//	    Una vez que el nodo ha detectado un beacon frame de un punto de acceso , ya puede transmitir,
    	// para transmitir espera un tiempo dif y si el medio no ha sido ocupado entonces puede transmitir...
    	//		Si se esta utilizando protocolo RTS/CTS,entonces una vez consumido un tiempo dif debe transmitir una trama RTS (request to send).		
    	case 2:// Consumiendo primer dif    		
    				
    		if(timeStoped>0){//...todavia esta contando dif
    			
    			if(this.AP.ChannelIsBusy(idNode) == true){//si mientras consume dif ve el canal ocupado entonces deja de consumir y espera...
    				
    				state = 3;    				
    				main.TextOutFlow("NODE: "+this.idNode+"  stop difs."); 
    				//************DIBUJA******************************************************************
          			//************************************************************************************         			
          			this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime-1));    			
          			//************************************************************************************
          			//************************************************************************************  
    			}
    			else{
    				
    				 main.TextOutFlow("NODE: "+this.idNode+"  spend DIF("+timeStoped+"us).");  			     
    			     timeStoped--;  
    			      
    			     if((this.objectForWC.getSimulationTime()-1) == currentTime){
    			    	    //************DIBUJA******************************************************************
    	          			//************************************************************************************         			
    	          			this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime));    			
    	          			//************************************************************************************
    	          			//************************************************************************************  
    			     }
    			}
    		}	 		
    		else{ //... a terminado de contar dif.    	    	
    			// Una vez consumido un tiempo Difs ...    			
    			main.TextOutFlow("NODE: "+this.idNode+"  spend DIF("+timeStoped+"us).");
    			//************DIBUJA******************************************************************
      			//************************************************************************************         			
      			this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime));    			
      			//************************************************************************************
      			//************************************************************************************  
    			
    			
    			if(this.AP.ChannelIsBusy(idNode) == false){// Si el medio esta libre...    				
    				
    				if(this.BackoffTime > 0){//...si tiene tiempo de backoff almacenado...
    				         
    					this.timeStoped = this.BackoffTime;
    				    state = 4;  //va al estado 4 para consumir backoff...    				      
    			    }
    				else{//... si no tiene tiempo de backoff almacenado.
	    				
    					if( requestToSendActivated == true
	    				    && this.ExitBuffer.get(0).getSize() > RtsThreshold){
    						
	    					 //Añadimos a la trama RTS el tiempo que queremos reservar el medio para este nodo
	    					 //ExitBuffer.get(0) contiene la trama de datos que desea enviar
    						 
    						 int AckSizeInBits = WirelessChannel.AckSize * 8;  				
            				
    						 int timeForTransferData = WirelessChannel.calculateChannelBusyTime(ExitBuffer.get(0).getSize()*8, this.rate_NodeToAp*1024*1024);
	    					 int timeForTransferACK  = WirelessChannel.calculateChannelBusyTime(AckSizeInBits, this.rate_APtoNode*1024*1024);	            				
	    				     int timeForTransferCTS  = WirelessChannel.calculateChannelBusyTime(WirelessChannel.CtsSize*8, this.rate_APtoNode*1024*1024);
	    				     
    						 int timeForReserve =   (WirelessChannel.timeForSif)
			    	               				  + timeForTransferCTS
			    	               				  + (WirelessChannel.timeForSif)
    						 					  + timeForTransferData 
			    	                              + (WirelessChannel.timeForSif)
			    	                              + timeForTransferACK;
			    	
    						 
    						 
    						 
    						 this.ExitBuffer.set(0,new RTS_Frame("RTS",this.idNode,this.idAccessPointConected,0,currentTime,WirelessChannel.RtsSize,timeForReserve));
    						 //ExitBuffer.get(0) ahora contiene la trama RTS
    						 //y ExitBuffer.get(1) contiene la trama de datos    						 
    					}	    				
	    				state = 5;
	    				nowIsTranssmitting = true;
	    				
	    				//if(ExitBuffer.get(0).getType().equals("RTS")){ 
					 		 
	    					//return "occupies_channels_at_the end_of_this_microsecond";
					 	//}
	    				return "occupies_channel_at_the end_of_this_microsecond";
    				}
    		    }    			
    			else{
    				
    				state = 3;
    			} 
    			
    	    }    
    		this.nowIsTranssmitting = false;
    		//************DIBUJA******************************************************************
  			//************************************************************************************         			
  			//this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime));    			
  			//************************************************************************************
  			//************************************************************************************    		
    	break;
    	case 3:// ...estado de espera...
    		   //debe de tener guardado el tiempo de backoff que no ha podido consumir.
    		
    		
    		if(AP.ChannelIsBusy(idNode) == false){// Si el canal vuelve a estar libre... va a consumir de nuevo un tiempo igual a dif.
    			
    			if(this.BackoffTime == 0){
                	this.BackoffTime = this.ejectBackoff();    		    	
                }
    			timeStoped = WirelessChannel.timeForDif;
    			state = 2; 
    			main.TextOutFlow("NODE: "+this.idNode+" notes channel as idle.Star to spend DIF.("+timeStoped+"us)."); 
    			main.TextOutFlow("NODE: "+this.idNode+" estado 3"); 
    			
    			this.nowIsTranssmitting = false;

    			//************DIBUJA******************************************************************
      			//************************************************************************************         			
      			//this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime));    			
      			//************************************************************************************
      			//************************************************************************************    			
    		}
    		else{    			
    			main.TextOutFlow("NODE: "+this.idNode+" notes channel as busy."); 
    			this.nowIsTranssmitting = false;
    		}
    		
    		if(timeStoped>0)timeStoped--;    
			
			if((this.objectForWC.getSimulationTime()-1) == currentTime && state == 3){
				
    			//************DIBUJA******************************************************************
     			//************************************************************************************  
     			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 			    			
     			//************************************************************************************
     			//************************************************************************************
			}
			else if(
					((this.objectForWC.getSimulationTime()-1) == currentTime && state == 2)
					|| state !=3){
				
				//************DIBUJA******************************************************************
     			//************************************************************************************  
     			this.addElementPlotted(new Diagram(Diagram.inactive_wait,(currentTime-1))); 			    			
     			//************************************************************************************
     			//************************************************************************************
    			//************DIBUJA******************************************************************
     			//************************************************************************************  
				this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime)); 		    			
     			//************************************************************************************
     			//************************************************************************************
			}
			
    		break;        	
    	case 4://  estado de consumo de backoff...
    		
    	    if(timeStoped>0  && !AP.ChannelIsBusy(idNode)){//si keda tiempo de backoff y el medio 
    		    	                                    //esta libre entonces consume backoff
    	    	if((BackoffTime % WirelessChannel.timeForSlot) == 0)if((BackoffTime % WirelessChannel.timeForSlot) == 0)
    	    	    
    	    	    
    	    		    	    		
    	    		
    	      	    main.TextOutFlow("NODE: "+this.idNode+" running backoff  ("+timeStoped+" us, slots left:"+this.SlotsForWait);	
		    	
    		    	this.BackoffTime--;		
    		    	if(timeStoped>0)timeStoped--;
    		    	
    		    	//ahora comprobamos si ha  consumido un slot completo:
    		    	
    		    	if((BackoffTime % WirelessChannel.timeForSlot) == 0){//si el resto de dividiar el tiempo de backoff restante y
    		    		                                             //el tamaños de un slot es cer, significa que ha consmuido almenos
    		    		                                            //un slot   		
    		    	 if(this.SlotsForWait>0)this.SlotsForWait--;
    		    	}
    		    	
    		    	 
    		    	 this.nowIsTranssmitting = false;	    	 
    		    	 
    		    	 if((this.objectForWC.getSimulationTime()-1) == currentTime){
	    		    	 
    		    		 //************DIBUJA******************************************************************
					 	 //************************************************************************************  
					 	 this.addElementPlotted(new Diagram(Diagram.spend_backoff,currentTime)); 			    			
					 	 //************************************************************************************
					 	 //************************************************************************************
    		    	 }
    		}
    		else 
    			if(timeStoped>0  && AP.ChannelIsBusy(idNode)){//Si ve el canal como ocupado y tiene todavia tiempo 
    				                                   //de backoff sin consumir entonces espera...
    				
    				//System.out.println("NODO: "+this.idNode+" espera a que el canal esté libre."); 
    				main.TextOutFlow("NODO: "+this.idNode+" waiting for channel release."); 
    	    		
    				if((BackoffTime % WirelessChannel.timeForSlot)!=0){//si el resto de dividiar el tiempo de backoff restante y
                        //el tamaño de un slot es distinto de cer0, significa que no ha llegado a consumir un slot completo antes de 
    					//bloquearse. 
    					
    				   int rest = BackoffTime % WirelessChannel.timeForSlot;
    					
    				   this.BackoffTime -= rest;
    				   timeStoped -= rest;
    				   
    				   this.BackoffTime += WirelessChannel.timeForSlot;
					   timeStoped += WirelessChannel.timeForSlot;			   
	    	    		
					}
    			
    		    main.TextOutFlow("NODE: "+this.idNode+" Stop backoff  ("+timeStoped+" us, slots left:"+this.SlotsForWait);	
    		    		
    			state = 3;//estado de espera...
    			timeStoped=0;
    			this.nowIsTranssmitting = false;
    			
    			
    			 //************DIBUJA******************************************************************
			 	 //************************************************************************************  
			 	 this.addElementPlotted(new Diagram(Diagram.spend_backoff,(currentTime-1))); 			    			
			 	 //************************************************************************************
			 	 //************************************************************************************
    			//************DIBUJA******************************************************************
     			//************************************************************************************  
     			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 			    			
     			//************************************************************************************
     			//************************************************************************************
    		}
    		else 
    			if(timeStoped <=0 && !AP.ChannelIsBusy(this.idNode)){// Si el canal esta libre después de haber consumido 
    				                                     //un tiempo aleatorio de backoff entonces podemos 
    				                                    //transmitir.    				
    	    		
    							
    				if(requestToSendActivated == true 
        				    && this.ExitBuffer.get(0).getSize()>RtsThreshold){    					
    					
    					
    					
    					 int AckSizeInBits = WirelessChannel.AckSize * 8;  				
         				
						 int timeForTransferData = WirelessChannel.calculateChannelBusyTime(ExitBuffer.get(0).getSize()*8, this.rate_NodeToAp*1024*1024);
    					 int timeForTransferACK  = WirelessChannel.calculateChannelBusyTime(AckSizeInBits, this.rate_APtoNode*1024*1024);	            				
    				     int timeForTransferCTS  = WirelessChannel.calculateChannelBusyTime(WirelessChannel.CtsSize*8, this.rate_APtoNode*1024*1024);
    				     
						 int timeForReserve =   (WirelessChannel.timeForSif)
		    	               				  + timeForTransferCTS
		    	               				  + (WirelessChannel.timeForSif)
						 					  + timeForTransferData 
		    	                              + (WirelessChannel.timeForSif)
		    	                              + timeForTransferACK;
    					
    					ExitBuffer.set(0,new RTS_Frame("RTS",this.idNode,this.idAccessPointConected,0,currentTime,WirelessChannel.RtsSize,timeForReserve));    					
    				}    				
    				
    				this.ejectBackoff("res");//como ha podido acceder al medio , resetea la ventana de backoff.    	    		
    	    		state = 5;    
    	    		//System.out.println("NODO: "+this.idNode+" backoff  ("+timeStoped+" us).");
    				//System.out.println("NODO: "+this.idNode+" transmitirá a partir del siguiente us.");
    				main.TextOutFlow("NODE: "+this.idNode+" ending backoff ("+timeStoped+" us, slots left:"+this.SlotsForWait+".");
    				
    				
    			 	 this.nowIsTranssmitting = false;
    				 //************DIBUJA******************************************************************
				 	 //************************************************************************************  
				 	  this.addElementPlotted(new Diagram(Diagram.spend_backoff,currentTime)); 			    			
				 	 //************************************************************************************
				 	 //************************************************************************************    	
    	    		
				 	 if(ExitBuffer.get(0).getType().equals("RTS")){ 
				 		 
				 		 return "occupies_channels_at_the end_of_this_microsecond";
				 	 }
				 	 return "occupies_channel_at_the end_of_this_microsecond";
    	    }
    		else
    		    if(timeStoped <= 0 && AP.ChannelIsBusy(idNode)){
        	    	// Si ha consumido su tiempo de backoff y vuelve a ver el canal como ocupado entonces debe aumentar la ventana
    		    	//contención y volver a ejecutar backoff.
    		    	
    		    	
    		    	BackoffTime = this.ejectBackoff();
    		    	this.ejectBackoff("inc");//incrementamos la ventana de contencion.
    		    	
    		    	timeStoped = BackoffTime; 
    		    	state = 3;//como el canal stá ocupado (AP.ChannelIsBusy()) entonces debe ir al estado 3 para esperar ... 
    	
    		    	main.TextOutFlow("NODE: "+this.idNode+" increases content window(CW="+this.contWind+").");
    				
    		    	this.nowIsTranssmitting = false;
    		    	//************DIBUJA******************************************************************
         			//************************************************************************************  
         			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 			    			
         			//************************************************************************************
         			//************************************************************************************
    		    
    		    }
       	    break;    	    
    	case 5:
    		
    		 // Aqui comienza a mandar la trama RTS  Dates o RequestFrame
    	     //primero calcula el tiempo que tardará en llegar:     		
    		
    		 this.nowIsTranssmitting = true;
    		 if(ExitBuffer.get(0).getType().equals("RTS")){
    			 	
    			main.TextOutFlow("NODE: "+this.idNode+"  sends RTS to AP "+this.idAccessPointConected);    			 
    			timeStoped = WirelessChannel.calculateChannelBusyTime(WirelessChannel.RtsSize*8,this.rate_NodeToAp*1024*1024);
    			
    		 }    		 
    		 else{    			 
    			     			 
    			 transmissionDuration += currentTime-this.ExitBuffer.get(0).getStartTransmission();
    			 //System.out.println("NODO: "+this.idNode+"  manda trama a AP "+this.idAccessPointConected);
    			 
    			 if(ExitBuffer.get(0).getType().equals("RequestFrame")){
    				 
    				 main.TextOutFlow("NODE: "+this.idNode+"  sends request frame to AP "+this.idAccessPointHeard);    	    			
    			 }else{    				 
    				 main.TextOutFlow("NODE: "+this.idNode+"  sends frame to AP "+this.idAccessPointConected);    			
    			 }
    			 
    			 timeStoped = WirelessChannel.calculateChannelBusyTime(ExitBuffer.get(0).getSize()*8, this.rate_NodeToAp*1024*1024);
    		 }
    		 
    		 if((this.objectForWC.getSimulationTime()-1) == currentTime){
    			 
	    		if(ExitBuffer.get(0).getType().equals("RTS")){
	    			
	    			//************DIBUJA******************************************************************
	      			//************************************************************************************  
	      			this.addElementPlotted(new Diagram(Diagram.transmiting_RTS,currentTime)); 			    			
	      			//************************************************************************************
	      			//************************************************************************************ 
	    		}
	    		else if(ExitBuffer.get(0).getType().equals("RequestFrame")){
				
					//************DIBUJA******************************************************************
		   			//************************************************************************************  
		    		  this.addElementPlotted(new Diagram(Diagram.transmiting_RequestFrame,currentTime)); 			    			
		   			//************************************************************************************
		   			//************************************************************************************ 
			    } 
			    else{
				
					//************DIBUJA******************************************************************
		   			//************************************************************************************  
		   			  this.addElementPlotted(new Diagram(Diagram.transmiting,currentTime)); 			    			
		   			//************************************************************************************
		   			//************************************************************************************ 
			    }  
    		}
    		 //y pasamos al estado 6 para esperar su recepcion.
    		state = 6;    		
    		break;
    	case 6:// Aquí espera el tiempo de transmision de la trama RTS,RequestFrame o Dates.
    		
    		
    		this.nowIsTranssmitting = true;
    		
    		if(ExitBuffer.get(0).getType().equals("RTS")){   
    			
    			main.TextOutFlow("NODE: "+this.idNode+" sending RTS... ("+timeStoped+" us).");  			
    		}
    		else{    
    			
    			main.TextOutFlow("NODE: "+this.idNode+" sending frame... ("+timeStoped+" us).");
    		}
    		
    		if(timeStoped>0)timeStoped--;//si queda tiempo en timeStoped entonces se queda consumiendolo
    	    
    		if(  timeStoped<=0 //significa que ha terminado de mandar la trama 
    	       ||(this.objectForWC.getSimulationTime()-1) == currentTime){ 
    	    	
    			if(ExitBuffer.get(0).getType().equals("RTS")){
	    			
	    			//************DIBUJA******************************************************************
	      			//************************************************************************************  
	      			 this.addElementPlotted(new Diagram(Diagram.transmiting_RTS,currentTime)); 			    			
	      			//************************************************************************************
	      			//************************************************************************************ 
	    		}
	    		else if(ExitBuffer.get(0).getType().equals("RequestFrame")){
				
					//************DIBUJA******************************************************************
		   			//************************************************************************************  
		    		  this.addElementPlotted(new Diagram(Diagram.transmiting_RequestFrame,currentTime)); 			    			
		   			//************************************************************************************
		   			//************************************************************************************ 
			    } 
			    else{
				
					//************DIBUJA******************************************************************
		   			//************************************************************************************  
		   			  this.addElementPlotted(new Diagram(Diagram.transmiting,currentTime)); 			    			
		   			//************************************************************************************
		   			//************************************************************************************ 
			    }  
    	    	
               if(timeStoped<=0){
            	   
            	   if(ExitBuffer.get(0).getType().equals("RTS")){
            		   System.out.println("Borrame");
            	   }
            	   
	    	    	this.transmitFrameToAccesPoint(
	                                                ExitBuffer.get(0)
	                                               );
	    	    	
	    	    	//Si va a mandar una trama RTS esta debe ser mandada a todos los nodos que esten
	    	    	//bajo su cobertura. 
			    	 if(ExitBuffer.get(0).getType().equals("RTS")){
			    		 
			    		     itHasJustSendRts = true;    	    		 	
			    			 sendsInBroadcast(ExitBuffer.get(0));			    			 
			     	    	 //sizeOfTheLastFrameSent = ExitBuffer.get(0).getSize()*8;
			     	    	 //return "free_channels_at_the_end_of_this_microsecond";//los demas nodos veran el canal que comparten con el punto de acceso al final         	    	    		
			    	 }
			    	state = 7;	    	    	
	    	    	sizeOfTheLastFrameSent = ExitBuffer.get(0).getSize()*8;
	    	    	return "free_channels_at_the_end_of_this_microsecond";//los demas nodos veran el canal que comparten con el punto de acceso al final 
        		                                                         //del microsegundo en curso... 
               }
    	    }
    	    break;
    	case 7: // Comienza a contar SIFS. 
    		
    		timeStoped = WirelessChannel.timeForSif;
    		state = 8;//tiene ke consumir sif 
    		
    		this.nowIsTranssmitting = false;
    		
    		if((this.objectForWC.getSimulationTime()-1) == currentTime){
    			 
    			 //************DIBUJA******************************************************************
    			 //************************************************************************************  
    			 this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime)); 			    			
   				//************************************************************************************
   				//************************************************************************************ 
    		}
   			break;    				   	
    	case 8: //consume Sif...
    		
    		//System.out.println("NODO: "+this.idNode+" consumiendo sif :"+timeStoped+" us restantes.");
    		
    		main.TextOutFlow("NODE: "+this.idNode+" spending SIF :"+timeStoped+" us.");    		
    		
    		if(timeStoped>0)timeStoped--;
    		
    		
    		this.nowIsTranssmitting = false;
    		
    	    if(timeStoped<=0){
    	    	
    	    	//si acaba de mandar un RTS al punto de acceso para reservar el medio entonces 
    	    	//debe esperar la recepccion de un CTS...
    	    	
    	    	if(ExitBuffer.get(0).getType().equals("RTS")){  
    	    		
    	    		sizeOfFrameCTSorACK = WirelessChannel.CtsSize*8;//pasamos de bytes a bits.
  
    	    	}
    	    	else{//tiempo de espera de un ack
    	    		
    	    		//timeStoped = Math.round(WirelessChannel.AckSize/((float)rate/(float)Math.pow(10, 6)???));// vamos a esperar la llegada del ack un tiempo igual a su transmision.
    	    		sizeOfFrameCTSorACK = WirelessChannel.AckSize*8;//pasamos de bytes a bits.    	    		
    	    		
    	    		//igual al tiempo de recepcion de la trama ack (propagacion mas transmision).
    	    	}    	    	
    	    	
    	    	timeStoped = WirelessChannel.calculateChannelBusyTime(sizeOfFrameCTSorACK, this.rate_APtoNode*1024*1024);
    	    	
    	    	state = 10;
    	    	
    	    }
    	    
    	    if(state!=8 ||(this.objectForWC.getSimulationTime()-1) == currentTime){
    	    	
    	    	//************DIBUJA******************************************************************
    	    	//************************************************************************************  
    	    	this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime)); 			    			
   				//************************************************************************************
   				//************************************************************************************ 
    	    }
   			break;
    	case 10: //consume time-out de ACK / CTS / Association response   		
    		
    		this.nowIsTranssmitting = false;   		

    		
    		if(timeStoped>0 && (typeOfFrameReceived == null)){ //... si todavia no ha llegado el ack/cts.
    		
    			//System.out.println("NODO: "+this.idNode+" esperando ACK/CTS  "+timeStoped+" us restantes de time-out.");    			
    			main.TextOutFlow("NODE: "+this.idNode+" waiting for ACK or CTS  "+timeStoped+" us remaining of time-out.");    			
    			if(timeStoped>0)timeStoped--;
    			
    		}
    		else if(timeStoped<=0 && typeOfFrameReceived == null){//... si ha consumido el tiempo de espera ...	    	
    			//Si ha consumido un tiempo de espera igual a ack time-out y no ha llegado el ack/cts entonces
    			//debe ejecutar backoff    	    	
    			//System.out.println("NODO: "+this.idNode+" Ack/Cts no recibido  ");   	    	
    	    	
    			
    	    	this.BackoffTime = this.ejectBackoff("inc");//INCREMENTAMOS LA VENTANA DE CONTENCIÓN.
    	    	//timeStoped = this.BackoffTime;
    	    	
    	    	main.TextOutFlow("NODE: "+this.idNode+" ACK or CTS not received, increases content window(CW="+this.contWind+").");   	    	
    	    	main.TextOutFlow("NODE: "+this.idNode+" backoff time generated "+this.BackoffTime+" number of slots:"+timeStoped/WirelessChannel.timeForSlot);   	    	
               
    	    //NUEVO
    	    			//si el medio esta libre ... y tiene que enviar tramasasd
    	    			if(AP.ChannelIsBusy(idNode) == false && this.ExitBuffer.size()!=0){// Si el canal vuelve a estar libre... va a consumir de nuevo un tiempo igual a dif.
    	        			
    	        			if(this.BackoffTime == 0){
    	                    	this.BackoffTime = this.ejectBackoff();    		    	
    	                    }
    	        			timeStoped = WirelessChannel.timeForDif;
    	        			state = 2; 
    	        			main.TextOutFlow("NODE: "+this.idNode+" notes channel as idle.Star to spend DIF.("+timeStoped+"us).");   
    	        			
    	        			if(timeStoped>0)timeStoped--;    
    	        			
    	        			this.nowIsTranssmitting = false;

    	        			//************DIBUJA******************************************************************
    	          			//************************************************************************************         			
    	          			//this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime));    			
    	          			//************************************************************************************
    	          			//************************************************************************************    			
    	        		}
    	    			else{
    	    				
    	    				state = 3;// estado de espera hasta que el medio quede libre    	        	    	
    	    			}
    	    			
    	    	nowIsTranssmitting = false;
    	    	
    	    	 
    	    	//************DIBUJA******************************************************************
       			//************************************************************************************  
    	    	//this.addElementPlotted(new Diagram(Diagram.time_out,currentTime)); 			    			
       			//************************************************************************************
       			//************************************************************************************ 
    	    }    	    
    	    else if( typeOfFrameReceived != null && (typeOfFrameReceived).equals("Ack")){//...recibe el ACK mientras consume time-out.
    	    

    	    	//System.out.println("NODO: "+this.idNode+" Ack recibido. ");
    	    	main.TextOutFlow("NODE: "+this.idNode+" Ack received. ");
    	    	//************DIBUJA******************************************************************
       			//************************************************************************************  
    	    	//this.addElementPlotted(new Diagram(Diagram.time_out,currentTime)); 			    			
       			//************************************************************************************
       			//************************************************************************************ 
    	    	
    	    	this.framesSent++;//Frames mandados
    	    	this.successfulTransmissions++;
    	    	this.successfulBits += sizeOfTheLastFrameSent;
    	    	this.jitter += Math.pow(currentTime - this.ExitBuffer.get(0).getMomentThatIsCreated(),2); 
    	    	//this.jitter += currentTime - this.ExitBuffer.get(0).getMomentThatIsCreated(); 
    	        
    	    	typeOfFrameReceived = null;
    	    	this.ExitBuffer.remove(0);
    	    	
		    	    	if(this.FramesForSend.size() == 0){//si era la ultima trama en transmitir...
		    	    		 
		    		    	state = 12;
		    	    	}
		    	    	else if(this.ExitBuffer.size()==0){
		    	    		//si no era la ultima trama en transmitir...    	    		
		    	    		
		    	    		timeStoped = WirelessChannel.timeForDif;		        			
		    	    		state = 11;
		    	    	}    	    	
    	    	
    	    	//return "free_channel_at_the_end_of_this_microsecond";//los demas nodos veran el canal que comparten con el punto de acceso al final 
    			//del microsegundo en curso... 
    	    }    		
    	    else if(typeOfFrameReceived != null && (typeOfFrameReceived).equals("Cts")){//...recibe un CTS
    	    	
    	        if(this.destinyOfCTS == this.idNode){
    	        	typeOfFrameReceived = null;
        	    	this.ExitBuffer.remove(0);//esto debe quitar la trama RTS del bufer de salida
        	    	//System.out.println("NODO: "+this.idNode+" Cts recibido. ");    	    	
        	    	main.TextOutFlow("NODE: "+this.idNode+" CTS received. ");    	    	
        	    	
        	    	    //en ExitBuffer debe quedar un frame 
        	    	    timeStoped = WirelessChannel.timeForSif;
        	    		state = 13;//en el estado 13 pasará a consumir un tiempo sif 
    	        }else{	
    	        	
    	        	state = 14; 	
    	        	timeStoped=0;
    	        } 	
    	    	
    	    	//************DIBUJA******************************************************************
       			//************************************************************************************  
    	        //this.addElementPlotted(new Diagram(Diagram.time_out,currentTime)); 			    			
       			//************************************************************************************
       			//************************************************************************************ 

    	    }else if( typeOfFrameReceived != null && (typeOfFrameReceived).equals("Ack")){//...recibe el ACK.
    	    

    	    	//System.out.println("NODO: "+this.idNode+" Ack recibido. ");
    	    	main.TextOutFlow("NODE: "+this.idNode+" ACK received. ");
    	    	
    	    	this.framesSent++;//Frames mandados
    	    	this.successfulTransmissions++;
    	    	this.successfulBits += sizeOfTheLastFrameSent;
    	    	this.jitter += Math.pow(currentTime - this.ExitBuffer.get(0).getMomentThatIsCreated(),2);
    	    	
    	    	typeOfFrameReceived = null;
    	    	this.ExitBuffer.remove(0);
    	    	
		    	    	if(this.FramesForSend.size() == 0){//si era la ultima trama en transmitir...
		    	    		 
		    		    	state = 12;
		    	    	}
		    	    	else if(this.ExitBuffer.size()==0){
		    	    		
		    	    		//si no era la ultima trama en transmitir...		
		    	    		
		    	    		timeStoped = WirelessChannel.timeForDif;		        			
		    	    		state = 11;
		    	    	}    	    	
		    	    	//************DIBUJA******************************************************************
		       			//************************************************************************************  
		    	    	//this.addElementPlotted(new Diagram(Diagram.time_out,currentTime)); 			    			
		       			//************************************************************************************
		       			//************************************************************************************ 
    	    }
    	    else if( typeOfFrameReceived != null && (typeOfFrameReceived).equals("AssociationResponse")){//association response recibido.
        	    

    	    	//System.out.println("NODO: "+this.idNode+" association response recibido. ");
    	    	
    	    	this.idAccessPointConected =  this.idAccessPointHeard;    	    	
    	    	this.rate_NodeToAp = ((AssociationResponse)ObjetFrameReceived).getRate_NodeToAp();    	    	
    	    	this.framesSent++;//Frames mandados
    	    	this.successfulTransmissions++;
    	    	this.successfulBits += sizeOfTheLastFrameSent;
    	    	this.jitter += Math.pow(currentTime - this.ExitBuffer.get(0).getMomentThatIsCreated(),2);     	    	
    	    	
    	    	typeOfFrameReceived = null;
    	    	this.ExitBuffer.remove(0);
    	    	
		    	    	if(this.FramesForSend.size() == 0){//si era la ultima trama en transmitir...
		    	    		 
		    		    	state = 12;
		    	    	}
		    	    	else if(this.ExitBuffer.size()==0){
		    	    		//si no era la ultima trama en transmitir...    	    		
		    	    		
		    	    		//timeStoped = WirelessChannel.timeForDif;		        			
		    	    		//state = 11;
		    	    		state = 17;
		    	    		timeStoped=0;
		    	    	}
		    	//************DIBUJA******************************************************************
		       	//************************************************************************************  
		    	//this.addElementPlotted(new Diagram(Diagram.time_out,currentTime)); 			    			
		       	//************************************************************************************
		        //************************************************************************************ 
    	    }
    		

    		if(state != 10 || (this.objectForWC.getSimulationTime()-1) == currentTime){//si llega al final de la simulación o cambia de estado...
		    	//************DIBUJA******************************************************************
		       	//************************************************************************************  
		       	this.addElementPlotted(new Diagram(Diagram.time_out,currentTime)); 			    			
		       	//************************************************************************************
		        //************************************************************************************ 
    		}

    	    break;   	    
    	case 11://segundo dif ...    	
    		
    		if(this.AP.ChannelIsBusy(idNode) == true){//si ve el canal ocupado entonces deja de consumir y espera...
				
    			timeStoped = 0;
				//System.out.println("NODO: "+this.idNode+"  consumiendo tiempo Dif ("+timeStoped+"us).");
				main.TextOutFlow("NODE: "+this.idNode+"  waiting for channel release.");				
				state = 15; 
				this.nowIsTranssmitting = false;
				
				if((this.objectForWC.getSimulationTime()-1) == currentTime){//si llega al final de la simulación ...
					//************DIBUJA******************************************************************
		 			//************************************************************************************  
		 			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 			    			
		 			//************************************************************************************
		 			//************************************************************************************
				}	 			
			}else{//canal libre, sigue consumiendo difs 
				
				this.nowIsTranssmitting = false;				
				
				if(timeStoped>0){//consume difs    			
	    			
	    			main.TextOutFlow("NODO: "+this.idNode+"  spend DIF ("+timeStoped+"us).");	    			
	    			timeStoped--;
	    			if((this.objectForWC.getSimulationTime()-1) == currentTime){//si llega al final de la simulación...
	    				
	    				//************DIBUJA******************************************************************
	    	   			//************************************************************************************  
	    	   			this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime)); 			    			
	    	   			//************************************************************************************
	    	   			//************************************************************************************
	    			}	    			
	    		}
	    		else{//ha terminado de consumir difs después de una transmision con éxito.	    			
				    			
				    			main.TextOutFlow("NODE: "+this.idNode+"  spend DIF ("+timeStoped+"us), reset content window.");
				    			//************DIBUJA******************************************************************
			    	   			//************************************************************************************  
			    	   			this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime)); 			    			
			    	   			//************************************************************************************
			    	   			//************************************************************************************
			    	   						    	   			
				    			this.ejectBackoff("res");
				    			this.BackoffTime = this.ejectBackoff();    			                                    
				    			timeStoped = this.BackoffTime;				    			
				    			
				    			if(timeStoped<=0){
				    				if( requestToSendActivated == true
					    				    && this.ExitBuffer.get(0).getSize() > RtsThreshold){
				    						
					    					 //Añadimos a la trama RTS el tiempo que queremos reservar el medio para este nodo
					    					 //ExitBuffer.get(0) contiene la trama de datos que desea enviar
				    					 int AckSizeInBits = WirelessChannel.AckSize * 8;  				
				            				
			    						 int timeForTransferData = WirelessChannel.calculateChannelBusyTime(ExitBuffer.get(0).getSize()*8, this.rate_NodeToAp*1024*1024);
				    					 int timeForTransferACK  = WirelessChannel.calculateChannelBusyTime(AckSizeInBits, this.rate_APtoNode*1024*1024);	            				
				    				     int timeForTransferCTS  = WirelessChannel.calculateChannelBusyTime(WirelessChannel.CtsSize*8, this.rate_APtoNode*1024*1024);
				    				     
			    						 int timeForReserve =   (WirelessChannel.timeForSif)
						    	               				  + timeForTransferCTS
						    	               				  + (WirelessChannel.timeForSif)
			    						 					  + timeForTransferData 
						    	                              + (WirelessChannel.timeForSif)
						    	                              + timeForTransferACK;
				    						 this.ExitBuffer.set(0,new RTS_Frame("RTS",this.idNode,this.idAccessPointConected,0,currentTime,WirelessChannel.RtsSize,timeForReserve));
				    						 //ExitBuffer.get(0) ahora contiene la trama RTS
				    						 //y ExitBuffer.get(1) contiene la trama de datos    						 
				    					}	   
				    				
					    				state = 5;
					    				
					    				nowIsTranssmitting = true;					    				
					    	   			
					    	   			if(ExitBuffer.get(0).getType().equals("RTS")){ 
									 		 
									 		 return "occupies_channels_at_the end_of_this_microsecond";
									 	}
					    	   			return "occupies_channel_at_the end_of_this_microsecond";
				    			}else{
				    				state = 4;  
				    			}	    			  			
	    		}    		 
			}
    		break;    		
    	case 12://Ha este estado solo puede llegar en el caso en el que la cola de tramas quede vacía ,
    		    //es decir no tiene ninguna trama que enviar.
    		
    		if(this.FramesForSend.size()>0){
    			
    			timeStoped = WirelessChannel.timeForDif;
    			state = 2;  
    			
    		}
    		
    		break;
    	case 13:
    		
    		//consume Sif...
    		//System.out.println("NODO: "+this.idNode+" consumiendo sif :"+timeStoped+" us restantes.");
    		main.TextOutFlow("NODE: "+this.idNode+" spending SIF :"+timeStoped+" us.");
    		
    		this.nowIsTranssmitting = false;   		
    	    
    	    if(timeStoped>0)timeStoped--;
    	    if(timeStoped<=0){    	    	
    	    	
    	    	state = 5;
    	    	//************DIBUJA******************************************************************
       			//************************************************************************************  
       			this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime)); 			    			
       			//************************************************************************************
       			//************************************************************************************   
    	    	//if(ExitBuffer.get(0).getType().equals("RTS")){ 
			 		 
       			//	 return "occupies_channels_at_the end_of_this_microsecond";
       			//}
    	    	return "occupies_channel_at_the end_of_this_microsecond";
    	    }
    	    
    	    if((this.objectForWC.getSimulationTime()-1) == currentTime){
    	    	
    	    	//************DIBUJA******************************************************************
       			//************************************************************************************  
       			this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime)); 			    			
       			//************************************************************************************
       			//************************************************************************************         		
    	    }    	    
    		break;
    	case 14:// a este estado llega un nodo si ha recibido un CTS que no iba dirigido hacia el     				
    		
    		main.TextOutFlow("NODE: "+this.idNode+" spending N.A.V. state=14 ("+this.timeReserve+")"); 
    		
    		state = 19;		     		
    		
    		this.nowIsTranssmitting = false;
    		
    		if((this.objectForWC.getSimulationTime()-1) == currentTime){
    			
    			//************DIBUJA******************************************************************
    			//************************************************************************************  
    			this.addElementPlotted(new Diagram(Diagram.CTS_NAV,currentTime)); 			    			
				//************************************************************************************
				//************************************************************************************
    		}
    		break;
          case 19:  //estado de consumo de N.A.V.  				
    		
    		main.TextOutFlow("NODE: "+this.idNode+" spending N.A.V.  ("+this.timeReserve+")"); 
    		
    		if(this.timeReserve<=0){
    			
    			state = 3;
    		}
    		else{
    			this.timeReserve--;
    		} 		     		
    		
    		this.nowIsTranssmitting = false;
    		
    		if(state!=19 || (this.objectForWC.getSimulationTime()-1) == currentTime){
    			
    			//************DIBUJA******************************************************************
    			//************************************************************************************  
    			this.addElementPlotted(new Diagram(Diagram.CTS_NAV,currentTime)); 			    			
				//************************************************************************************
				//************************************************************************************
    		}
    		break;
    	case 15:
    		
    		if(this.AP.ChannelIsBusy(idNode) == true){// estado de espera hasta que el medio quede libre
    			
    			main.TextOutFlow("NODE: "+this.idNode+" notes channel as busy.");
    			state = 15;
    		}
    		else{
    			
    			main.TextOutFlow("NODE: "+this.idNode+" notes channel as idle.");
    			timeStoped = WirelessChannel.timeForDif;
    			state = 11;
    		}
    		
    		if(timeStoped>0)timeStoped--;
    		
    		this.nowIsTranssmitting = false;
			
    		if(state != 15 || (this.objectForWC.getSimulationTime()-1) == currentTime){//si llega al final de la simulación o cambia de estado...){
	    		//************DIBUJA******************************************************************
	 			//************************************************************************************  
	 			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 			    			
	 			//************************************************************************************
	 			//************************************************************************************		
    		}
 			break;
    	case 16:// a este estado llega un nodo si un nodo ha escuchado una trama RTS de otro nodo que
    		   // quiere reservar el medio.    		
    		
    		main.TextOutFlow("NODE: "+this.idNode+" spending N.A.V. state 16 ("+this.timeReserve+")");      		
    		state=18;
    		this.nowIsTranssmitting = false;
    		
    		if((this.objectForWC.getSimulationTime()-1) == currentTime){//si llega al final de la simulación o cambia de estado...){
    		    
    			//************DIBUJA******************************************************************
    			//************************************************************************************  
    			this.addElementPlotted(new Diagram(Diagram.RTS_NAV,currentTime)); 			    			
				//************************************************************************************
				//************************************************************************************
    		}
			break;
    	case 18:// estado de consumo del N.A.V.    		
 		
 		main.TextOutFlow("NODE: "+this.idNode+" spending N.A.V. ("+this.timeReserve+")");      		
 		
 		if(this.timeReserve<=0){
 			
 			state = 3;
 			
 		}
 		else{
 			this.timeReserve--;
 		}
 		
 		this.nowIsTranssmitting = false;
 		
 		if(state != 18 || (this.objectForWC.getSimulationTime()-1) == currentTime){//si llega al final de la simulación o cambia de estado...){
 		    
 			//************DIBUJA******************************************************************
 			//************************************************************************************  
 			this.addElementPlotted(new Diagram(Diagram.RTS_NAV,currentTime)); 			    			
				//************************************************************************************
				//************************************************************************************
 		}
			break;
    	case 17: //carga DIF
    		
    		timeStoped = WirelessChannel.timeForDif;	
    		state = 11;
    		

    		if(this.AP.ChannelIsBusy(idNode) == true){//si ve el canal ocupado entonces deja de consumir y espera...
				
    			timeStoped = 0;
				//System.out.println("NODO: "+this.idNode+"  consumiendo tiempo Dif ("+timeStoped+"us).");
				main.TextOutFlow("NODE: "+this.idNode+"  waiting for channel release.");				
				state = 15; 
				this.nowIsTranssmitting = false;
				
				if((this.objectForWC.getSimulationTime()-1) == currentTime){//si llega al final de la simulación ...
					//************DIBUJA******************************************************************
		 			//************************************************************************************  
		 			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 			    			
		 			//************************************************************************************
		 			//************************************************************************************
				}	 			
			}else{//canal libre, sigue consumiendo difs 
				
				this.nowIsTranssmitting = false;				
				
				if(timeStoped>0){//consume difs    			
	    			
	    			main.TextOutFlow("NODO: "+this.idNode+"  spend DIF ("+timeStoped+"us).");	    			
	    			timeStoped--;
	    			if((this.objectForWC.getSimulationTime()-1) == currentTime){//si llega al final de la simulación...
	    				
	    				//************DIBUJA******************************************************************
	    	   			//************************************************************************************  
	    	   			this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime)); 			    			
	    	   			//************************************************************************************
	    	   			//************************************************************************************
	    			}	    			
	    		}
    		
			} 		
    		

    		
    		break;
    		
    	}
    	
    	
    	//FUERA DEL SWITCH
    	if(typeOfFrameReceived != null && (typeOfFrameReceived).equals("Cts")){
    		
    		
    		main.TextOutFlow("NODE: "+this.idNode+" CTS received. ");
    		
    		if(this.destinyOfCTS == this.idNode){
	        	
    			typeOfFrameReceived = null;
    	    	
    			this.ExitBuffer.remove(0);//esto debe quitar la trama RTS del bufer de salida    	
    	    	    	    	
    	    	
    	    	    //en ExitBuffer debe quedar un frame 
    	    	    timeStoped = WirelessChannel.timeForSif;
    	    		state = 13;//en el estado 13 pasará a consumir un tiempo sif 
	         }
    	     else{
	        	if(state==16){
	        		
	        		//************DIBUJA******************************************************************
	    			//************************************************************************************  
	    			this.addElementPlotted(new Diagram(Diagram.RTS_NAV,currentTime)); 			    			
					//************************************************************************************
					//************************************************************************************
	        	}        	
	        	
	        	state = 14;
	        	int aux = this.timeReserve;//???	        	
	        	this.nowIsTranssmitting = false;
	        } 	
    	}else if(typeOfFrameReceived != null 
    			&& (typeOfFrameReceived).equals("RTS")
    			&& itHasJustSendRts == false){//..esta ultima variable indica si acaba de mandar una trama 
    		                                  //RTS, de ser asi ignoraria las posibles tramas RTS de recibidas por otros desde otros nodos 
    		                                  //en este caso en el que  dos nodos enviasen justo en el mismo instante dos tramas RTS, 
    		                                  //el cada uno ignoraria la trama enviada por el otro y el AP no recibiria ninguna
    		//Si recibe un RTS tiene que comprobar que el id de destino coincide con el id 
    		//del ultimo beacon frame escuchado...con esto queremos simular que el nodo que mando 
    		//el RTS es detectado por los demas nodos bajo su cobertura y que estan escuxando el mismo canal.
    		
    		if(this.idAccessPointHeard == this.ObjetFrameReceived.getDestiny()){//..si ha recibido un beacon frame
    			                                                      //guarda su id en idAccessPointHeard 
    			                                                     //si el RTS tenia como destino el AP que envia en el mismo canal que este nodo...
    			
    			timeReserve = ((RTS_Frame)this.ObjetFrameReceived).getTimeReserve();
    			timeStoped=0;
    			state = 16;
    			this.removeLastElementPlottedAddition();
    			
    			this.nowIsTranssmitting = false;
    			//************DIBUJA******************************************************************
	 			//************************************************************************************  
	 			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 			    			
	 			//************************************************************************************
	 			//************************************************************************************		
    		}
    	}
    	//La siguiente condicion evita que cuando dos  nodos manden a la vez una trama RTS 
   	   //se bloqueen y no lleguen ningun RTS al otro.
    	if(   state == 8 
    	   && itHasJustSendRts == true){
    		itHasJustSendRts = false;
    	}
		return "null";
    }
      
    public int getFramesSent(){
    
    	return this.framesSent;//get the number of succesfull frames sent...
    }
    
    public float getUtilization(){
    	
    	//recodar que la variable rate contiene la velocidad establecidad entre el nodo y el 
    	//punto de acceso en bits/seg.    	
    	//la utilizacion vendrá dada en segundos.
    	
    	return ((float)this.getSuccessfulBitsTransmitted()) / (float)(this.rate_NodeToAp);
    }
   
    public int getTransmissionDuration(){
    	
    	return transmissionDuration;
    } 
     
    public int getQueuingDelay(){
    	
    	return queuingDelay;
    }
    
    public int getSuccesfulTransmissions(){
        
    	return this.successfulTransmissions;	
    }
    
    public int getQueueLenght(){
    	return  this.FramesForSend.size();
    }
    
    public void setRTSThreshold(int RtsThreshold){
    	
    	requestToSendActivated = true;
    	this.RtsThreshold = RtsThreshold;
    }
    
    public void distr_for_interArTime(String aux){
    	
    	this.distr_ForInterArTime=aux.charAt(0);
    }
    
    public void distr_for_lenght(String aux){
    	
    	this.distr_ForLenght = aux.charAt(0);
    }
    
    public void ModifyEnergyRadiatedByNode(Float Energy_mW){
    	
    	this.Energy_mW = Energy_mW;
    	this.Energy_W = (float) (this.Energy_mW*(float)Math.pow(10,-3));
    }
	
    public char getDistr_ForInterArTime(){
    	
    	return distr_ForInterArTime;
    }
    
    public char getdistr_ForLenght(){
    	
    	return distr_ForLenght;
    }

    public void setSensitiviy(ArrayList Sens){
    	
    	sensitivity = Sens;    	
    }
    
    public void setTransmissionSpeed(ArrayList Trans){
    	
    	TransmissionSpeed = Trans;
    }
    
    public void setMeanForLength(int mean){
    
    	this.mean_ForLenght = mean;
    }

    public void setMeanForInterArTime(int mean){
        
    	this.mean_ForInterArTime = mean;
    }
    
    public static float converterWtodB(double W){
		
		return (float) (10*Math.log10((double)W));
	}

    public static double signalLossByDistance(float d,float f){//f-> frequency in Ghz. d-> distance in meters
    	
    	//Pp = 20log10(d/1000) + 20log10(f*1000) + 32,4    	
    	//[10^(Pp-20*log10(f) - 32,4)/20]*1000=d;//f-> frequency in Ghz. d-> distance in meters
    	
    	float lossInDb;
    	double Pp;
    	if(d==0)return 0;
    	else{
    		
    		Pp = 20*Math.log10((double)(d/(float)1000))+20*Math.log10(f*1000)+32.4;
    	}
    	return Pp;
    } 
    
    public void Update(ArrayList rates,ArrayList sens){
    	   
    	sensitivity = sens;    	
    	TransmissionSpeed = rates; 
    	if(this.nodeConfigurationPanel != null){
    		 
    		this.nodeConfigurationPanel.Update(rates, sens);
    	}
    }
    
    public int getSuccessfulBitsTransmitted(){
    	return this.successfulBits;
    }
    
    public NodeConfigurationPanel getNodeConfigurationPanel(){
    	
    	nodeConfigurationPanel.getJTextField();
    	nodeConfigurationPanel.getJTable();
    	return nodeConfigurationPanel;
    }
    
    public void setEntryChannelAsFree(){
    	this.entryChannel.setIsBusy_AsFalse(-1);
    }
    public void setEntryChannelAsBusy(int id){
    	this.entryChannel.setIsBusy_AsTrue(id);
    }
    public void setOutputChannelAsFree(){
 
    	//El nodo que ha ejecutado esta acción ocupa el canal.			        	     
		 int idAP = getIdAccessPointConected();
		 if(idAP==-1){
			 
			 //si no se puede obtener el id del access point al que esta conectado el nodo que ha solicitado 
			 //la reserva del medio significa que quiere reservar el medio para mandar una solicitud de conexion 
			 //entonces tenemos que utilizar el id del acces point al cual quiere mandar su request frame.
			 idAP = getIdAccessPointHeard();
			 AccessPoint AP_aux = WirelessChannel.getAccessPoint(idAP);
			 AP_aux.setChannelAsFree(this.idNode);//tenemos que avisar al punto de acceso de quien ha ocupado el canal
			                          //con el fin de simular el problema de el nodo oculto...(si un nodo 
			                         //que intenta ocupar el canal no es capaz de ver a quien ya lo tiene ocupado
			                        //verá el canal como libre).
		 }
		 else{
			 this.outputChannel.setIsBusy_AsFalse(this.idNode);
		 }
    	
    }
    
    //Ocupa su canal de salida...este es el canal que le comunica con el punto de acceso
    public void setOutputChannelAsBusy(){
    	
    	int idAP = getIdAccessPointConected();
		 if(idAP==-1){
			 
			 //si no se puede obtener el id del access point al que esta conectado el nodo que ha solicitado 
			 //la reserva del medio significa que quiere reservar el medio para mandar una solicitud de conexion 
			 //entonces tenemos que utilizar el id del acces point al cual quiere mandar su request frame.
			 idAP = getIdAccessPointHeard();
			 AccessPoint AP_aux = WirelessChannel.getAccessPoint(idAP);
			 AP_aux.setChannelAsBusy(this.idNode);//tenemos que avisar al punto de acceso de quien ha ocupado el canal
			                          //con el fin de simular el problema de el nodo oculto...(si un nodo 
			                         //que intenta ocupar el canal no es capaz de ver a quien ya lo tiene ocupado
			                        //verá el canal como libre).
		 }
		 else{
			 
			 this.outputChannel.setIsBusy_AsTrue(this.idNode);  
		 }    	  	
    }
    
   //libera los canales de entrada de los nodos que estaban bajo su cobertura y estan conectados al mismo punto de acceso que este nodo o no estan conectados a ningun punto de acceso
    public void setOutputChannelsAsFree(){
    	
    	for(int i=0;i<NodesUnderCoverage.size();i++){
    		
			((Node)NodesUnderCoverage.get(i)).setEntryChannelAsFree();
		}    	
    }
  
    //Ocupa los canales de entrada de los nodos que estaban bajo su cobertura y estan conectados al mismo punto de acceso que este nodo o no estan conectados a ningun punto de acceso
    public void setOutputChannelsAsBusy(){
    	
    	for(int i=0;i<NodesUnderCoverage.size();i++){
    		
			((Node)NodesUnderCoverage.get(i)).setEntryChannelAsBusy(this.idNode);
		}    	
    }
   
    public int getTimeStoped(){
    	
    	if( 
    		state==3 
    		&&timeStoped==0
    		)
    		    return -1;//state 3 estado de espera por medio ocupado
    	else 
    		if(
    		   (state==14 && timeReserve>0)
    		 ||(state==16 && timeReserve>0)
    		 ) 
    			return timeReserve;//ha recibido un CTS y esta consumiendo el tiempo que ha reservado el emisor deL RTS
    	else 
    		if(state==12)
    			return -1;//se queda sin tramas que enviar
    	else
    		    return timeStoped;
    }
   
    public void goForward(int time){
    	
    	if(timeStoped>0){
    	   timeStoped-=time;
    	}
    	if(state == 14 || state ==16){//ha recibido un CTS y esta consumiendo el tiempo que ha reservado el emisor deL RTS
    		
    		timeReserve-=time;
    	}
    	if(state==4){// ... si esta en el estado de consumo de backoff
    		
    		BackoffTime-=time;
    		this.SlotsForWait = BackoffTime / WirelessChannel.timeForSlot;    		
    	}
    }
}