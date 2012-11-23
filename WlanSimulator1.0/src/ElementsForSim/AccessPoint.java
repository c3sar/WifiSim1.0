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
import Interfaz.AccessPointConfigurationPanel;
import Interfaz.MainFrame;
import Main.main;


public class AccessPoint{
	
	public AccessPointConfigurationPanel accessPointConfigurationPanel;
	
	ArrayList<Float> sensitivity;       //contiene la sensibilidad del dispositivo en dBm.
	ArrayList<Float> TransmissionSpeed; //contiene la lista de posibles velocidades en mb/s.
	
	int idAccesPoint;
	
	float Coverage = 0;//Must be calculated through the power.
	boolean ChannelIsBusy;//is attended a transmission.	
	float Energy_mW = 0;
	float Energy_W;//energy in W.   
	

	//--location variables...
    int x=0;
    int y=0;
    int z=0;

    WirelessChannel objectForWC = null; 
    Channel entryChannel;
    ArrayList<Node> accessibleNodes;//este array contiene referencias a los nodos que estan bajo la cobertura de este punto de acceso 
    ArrayList<Node> conectedNodes;//este array contiene referencias a los nodos que estan conectados a este punto de acceso 
    public ObjectPlotted objPlotted;        
    Hashtable TransmisionSpeedForNode;  
  
    private int state = -2;
	Frame frameReceived;
	Frame theLastFrameReceived;
	int timeStoped;
	int previousState;
	float BeaconFrameRate = 0;//(Mb/s) DE MOMENTO ESCOGEREMOS LA MAS BAJA PERMITIDA POR LA TECNOLOGIA ESCOGIDA POR EL USUARIO.....<<<<----
    int BeaconFrameTime;
    int BeaconFrameTimeCounter=0;
	int ID_of_the_LastNodeTransmiting = -1;	
	int timeReserve;//esta variable contendra el tiempo que un nodo quiere servar el medio a traves de un RTS
	               // se utilizara para insertar este tiempo en una trama CTS y enviarse en broadcast a todos los nodos conectados a este AP
	
	boolean RtsReceived;
	boolean AssociationRequestReceived;
	Frame ExitBuffer;
	boolean configurated;
	
	
	public int successfulBits; //-> numero de bits transmitidos con exito
	public int successfulTransmissions; //-> numero de tramas transmitidos con exito
	public int numberOfAckSends;
	public int numberOfCtsSends;
	public int numberOfAssociationResponseFrameSends;
	public int transmissionDuration;// -> suma de todos los tiempos de transmission desde que se intenta mandar el paquete hasta que se manda con exito
	public int queuingDelay; //-> suma de todos los tiempos de retraso en cola.... 
	                  //el tiempo de retraso en cola es el tiempo desde que nace
	                  //hasta que se intenta mandar la trama
	public float utilization;
	
	
	
	int BackoffTime;
	int contWind;//The contention window
	int i=3;
    int SlotsForWait;
    
	public int ejectBackoff() throws InterruptedException{
	    	
	
		return BackoffAlgorithm("null");
	}
	        
	public int ejectBackoff(String aux) throws InterruptedException{    	
	    	
	    return BackoffAlgorithm(aux);
	}
	  
	public int BackoffAlgorithm(String IN)throws InterruptedException{
	    
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
	
	public void setBeaconFrameTime(int BeaconFrameTime){
		
		this.BeaconFrameTime = BeaconFrameTime;
	}
	
	public int getBeaconFrameTime(){
		
		return this.BeaconFrameTime;
	}
	
	public void initialize(){
		
		this.state= -2;
		this.timeStoped = 0;
		this.frameReceived=null;
		this.theLastFrameReceived =null;
		
		this.RtsReceived = false;
		this.AssociationRequestReceived = false;
		this.configurated = false;
		this.Coverage=0;	
		this.ChannelIsBusy = false;
    	this.entryChannel = new Channel(new String("AP"+this.idAccesPoint));
    	this.accessibleNodes = new ArrayList();
    	this.conectedNodes = new ArrayList();
    	this.TransmisionSpeedForNode = new Hashtable(); 
    	this.BeaconFrameTime = 100;
    	
        successfulBits = 0; 
    	successfulTransmissions = 0; 
    	numberOfAckSends = 0;
    	numberOfCtsSends = 0;
    	numberOfAssociationResponseFrameSends = 0;
    	utilization = 0;
    	transmissionDuration = 0;
    	queuingDelay = 0;
    	
    	BackoffTime = 0;    	
    	i=3;
    	contWind = 0;
    	//SlotsForWait=0;
    	timeStoped  = 0; 
	}	
	
    public AccessPoint(WirelessChannel wirelessChannel,int idAccesPoint,int x,int y, int z) throws BiffException, IOException{
    	
    
    	   BeaconFrameTimeCounter = 0;
    	   //Estos datos los vamos a coger de un .exel   	
		   
     	   initialize();
		   FileInputStream in;
		   Workbook workbook;
		   Sheet sheet;
		   
		   Cell cell;
		   int row;
		   int column;
		   boolean carryOn = true;
		   
		   String content;
		   NumberCell numCell;
		   Double num;

    	   in = new FileInputStream("basic configuration.xls");
		   workbook = Workbook.getWorkbook(in);
		   sheet = workbook.getSheet(0);   	
		   column = 0;
		   row = 2;
		   carryOn = true;

		   this.idAccesPoint = idAccesPoint;
		   
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
				   
				  if(content.equals("Energy radiated by AP")){
					  
					  column++;
					  cell = sheet.getCell(column,row);
					  content = cell.getContents();
					  numCell = (NumberCell) cell;
					  num = numCell.getValue();	
					  Energy_mW = num.floatValue();
					  carryOn = false;
				  }
			   }	
			  row++; 
		}while(carryOn == true);
		
    	
		this.Energy_W = (float) (Energy_mW*Math.pow(10, -3));// Energy in mW , energyEmitted in W.
	       	
    	this.objectForWC = wirelessChannel;
    	this.x = x;
    	this.y = y;
    	this.z = z;
        	
    	
        this.objPlotted = new ObjectPlotted("AP.jpg","A.P."+String.valueOf((Integer)this.getID())); 
        
        
        //...
        configuration();
    }    
    
    public void ResetObjPlotted(){
    	this.objPlotted = new ObjectPlotted("AP.jpg","A.P."+String.valueOf((Integer)this.getID())); 
        
    }
    
    public void configuration() throws BiffException, IOException{
    	
    	String type = String.valueOf(MainFrame.jComboBoxPhysicalLayer.getSelectedItem());
    	ArrayList Aux = main.getRatesAndSensitivityForPhysicalTechnology(type);
    	
    	TransmissionSpeed = new ArrayList();
    	sensitivity = new ArrayList();
    	
	     for(int t=0;t<Aux.size();t++){
	    	 
	    	 TransmissionSpeed.add((Float) ((ArrayList)Aux.get(t)).get(0));
	    	 sensitivity.add((Float) ((ArrayList)Aux.get(t)).get(1));
	     }		
		     
		     
    }
    
    public void ModifyEnergyRadiatedByAP(Float Energy_mW){
    	
        this.Energy_mW = Energy_mW;
    	this.Energy_W = (float) (Energy_mW*(float)Math.pow(10, -3));//Energy in mW.
    }
    
    public ObjectPlotted getObjectPlotted(){
		return this.objPlotted;
	}
	
    public void addElementPlotted(Diagram ele){    	
    	this.objPlotted.addElement(ele);
    }  
	 
	private int getID_of_the_LastNodeTransmiting(){
		
		ID_of_the_LastNodeTransmiting = this.theLastFrameReceived.getOrigin();
		return ID_of_the_LastNodeTransmiting;
	}
	
	public float getEnergyEmitedmW(){
    	
    	return this.Energy_mW;
    }
    public float getEnergyEmitedW(){
    	
    	return this.Energy_W;
    }
	
	
	//ESTA FUNCION SIRVE PARA QUE CADA NODO TESTEE EL MEDIO , SI EL MEDIO ES OCUPADO
	//POR UN NODO QUE NO ESTA BAJO SU COBERTURA , ENTONCES VERA EL MEDIO COMO LIBRE
	
	public boolean ChannelIsBusy(int idNode) throws BiffException, IOException{
		
		// El nodo que ejecute esta funcion vera el canal como libre si el nodo que lo tiene ocupado no esta 
		//a su alcance , es decir , no esta bajo su radio de cobertura.
		if(this.entryChannel.isBusy()== false){
			
			return false;
		}
		else{//... si esta ocupado ... entonces devolvemos que esta libre si idNode no esta en la cobertura del nodo 
			 // que actualmente esta ocupando dicho canal.
			
			if(idNode == -1){//significa que la consulta la esta haciendo el AP "propietario" del canal
				
				//si el canal esta siendo ocupado por el mismo AP que esta haciendo la consulta
				//entonces devolverá que el canal esta libre...								
				ArrayList NodesOccupingThisChannel = this.entryChannel.WhoIsOccupingThisChannel(); 
				if(NodesOccupingThisChannel.size() == 1){
					if(NodesOccupingThisChannel.contains(-1)){
						
						return false;
					}
				}
				
					
					return true;//devolvemos que esta ocupado.Esta es la situacion: el canal esta siendo ocupado  
				           //por alguno de los nodos conectados a este AP .
				
			}
			
			ArrayList NodesOccupingThisChannel = this.entryChannel.WhoIsOccupingThisChannel(); 
			//Ahora tenemos que ver si el nodo que consulta el estado del canal , puede ver al nodo que lo este 
			//ocupando.
			
			if(NodesOccupingThisChannel.contains(-1)){//si contiene -1 significa que el medio ha sido 
				//ocupado por el AP (todos ven el medio como ocupado)
				//TODOS LOS NODOS MENOS AQUELLOS NODOS QUE QUE NO ESTAN CONECTADOS A ESTE PUNTO DE ACCESO
				return true;
			}
			else{
				for(int i=0;i<NodesOccupingThisChannel.size();i++){
					
					Node nodeOccupingThisChannel = WirelessChannel.getNode((Integer)NodesOccupingThisChannel.get(i));
					Node nodeWhoWantsOccupeThisChannel = WirelessChannel.getNode(idNode);
					
					//si el nodo que quiere conectarse puede ver al nodo que ya esta conectado entonces
					//paramos el bucle,si no ve al nodo que actualmente estamos tratando , entonces seguimos buscando...
					
					String deviceA = new String("Node "+nodeWhoWantsOccupeThisChannel.getIdNode());
					String deviceB = new String("Node "+nodeOccupingThisChannel.getIdNode());
					
					if(WirelessChannel.deviceAunderTheCoverageOfdeviceB(deviceA,deviceB)){
						
						return true;
					}				
				}
			}
			return false;
		}
	}
	
	public void setChannelAsBusy(int idNode){
		
		this.entryChannel.setIsBusy_AsTrue(idNode);		
    }
    
	public void setChannelAsFree(int idNode){
		
		this.entryChannel.setIsBusy_AsFalse(idNode);
		
		//AVISAMOS A TODOS LOS NODOS CONECTADOS A ESTE PUNTO DE ACCESO DE QUE EL CANAL QUEDA LIBRE
		//...recordatorio: si esto no se hace , los nodos que estan esperando a que el medio quede libre
		//tardan un microsegundo mas que el nodo que ha terminado de transmitir , en contar el dif antes de ejecutar
		//backoff...
		
	}
    
    public ArrayList getAccessibleNodes(){
    	
    	return accessibleNodes;
    }
    
    public ArrayList getConectedNodes(){
    	
    	return conectedNodes;
    }
   
    public static  double convertToW(double a){
    	return Math.pow(10,a/(double)10);
    }
    
    public static float converterWtodB(double W){
		
		return (float) (10*Math.log10((double)W));
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
    
    public int getID(){
    	
    	return this.idAccesPoint;
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
    
    public static double signalLossByDistance_dBm(float d,float f){//f-> frequency in Ghz. d-> distance in meters
    	
    	return signalLossByDistance(d,f)-30;
    } 
    
	private void APSendsBeaconFrames() throws BiffException, IOException{
		
		
		//Accede al wirlesschannel para recoger todos los nodos
		ArrayList arrayOfNodes = objectForWC.getNodes();
		//busca los nodos bajo su covertura.
		//se solapa.		
		
		for(int i=0;i<arrayOfNodes.size();i++){
			
			ArrayList possitionNode = ((Node)arrayOfNodes.get(i)).getPossition();		   
		    
		    if(WirelessChannel.deviceAunderTheCoverageOfdeviceB("Node "+((Node)arrayOfNodes.get(i)).getIdNode()
		    		                                           ,"Access point "+this.getID()
		    		                                           )){//Si el nodo esta bajo la cobertura del punto de acceso...
		    	
		    	if(!this.accessibleNodes.contains((Node)arrayOfNodes.get(i))){
						    	
		    		            this.accessibleNodes.add((Node)arrayOfNodes.get(i));
						    			    	
						    	//coloca en el buffer de entrada del nodo su beacon frame. 
								//el beacon frame lleva consigo una referencia al canal de entrada del punto de acceso.
								
						    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" manda beacom frame a nodo "+((Node)arrayOfNodes.get(i)).getIdNode()+" (distancia "+distance+"). ");
						    	//main.TextOutFlow("Access point: "+this.idAccesPoint+" send beacom frame to node "+((Node)arrayOfNodes.get(i)).getIdNode()+" (distance "+distance+"). ");
						    	
						    	
						    	//el beacon frame va a llevar un campo que indique la intensidad en debilios con la que llega   	
						    	//signal loss between acces point and this node.
		    		            
		    		            ArrayList possitionDeviceA = ((Node)arrayOfNodes.get(i)).getPossition();
		    		            ArrayList possitionDeviceB = this.getPossition();
                                
		    		            int distanceX = (Integer)possitionDeviceA.get(0)-(Integer)possitionDeviceB.get(0);
		    		    	    int distanceY = (Integer)possitionDeviceA.get(1)-(Integer)possitionDeviceB.get(1);
		    		    	    int distanceZ = (Integer)possitionDeviceA.get(2)-(Integer)possitionDeviceB.get(2);
		    		    	    
		    		    	    float distance = (float) Math.sqrt(Math.pow(distanceX,2)+Math.pow(distanceY,2)+Math.pow(distanceZ,2));		    
		    		    	  
						    	float signalLossByDist = (float) this.signalLossByDistance(distance , WirelessChannel.frequency);
						    	
						    	float signalLossByObst = WirelessChannel.Impedanced("Access point "+this.getID(),"Node "+((Node)arrayOfNodes.get(i)).getIdNode());
						    	
						    	float energyReceivedByThisNode = this.converterWtodB(this.Energy_W)-(signalLossByDist+signalLossByObst);		    	
						   
						    	Frame frameToSend = new BeaconFrame("BeaconFrame",
						    										this.entryChannel,
						    										this.idAccesPoint,
						    										((Node)arrayOfNodes.get(i)).getIdNode(),
						    										-1,
						    										WirelessChannel.BeaconFrameSize,
						    										energyReceivedByThisNode);
						    	
						    	try {		    		
									((Node)arrayOfNodes.get(i)).WriteBuffer(frameToSend);
								} catch (InterruptedException e) {
									
									e.printStackTrace();
								}
									   
		        }	
		    }
		}	
	}
	
	boolean finalFrameReceived = false;
	
	private Frame waiting(){
		
		this.frameReceived = (Frame) this.entryChannel.Read();			
		if(this.frameReceived != null){
			
			this.theLastFrameReceived = this.frameReceived;
			//this.ChannelIsBusy = true;//ponemos el canal como ocupado
			this.entryChannel.Write(null);	//borramos el contendio actual del buffer del canal de entrada			
			
			//...ahora trataremos el frame que le ha llegado al punto de acceso desde otro nodo
			//leemos el origen del paquete y mandamos ACK a su corresponiente 
			//destinatario.
			
			int OriginOfFrame = this.frameReceived.getOrigin();	
			
			
			boolean nodeFound=false;
			
			//SI EL FRAME ES UNA SOLICITUD DE CONEXIÓN...			
			if(this.frameReceived.getType().equals("RequestFrame")){			  
				
				this.finalFrameReceived = true;
			}	
			//SI EL FRAME RECIBIDO ERA EL ÚLTIMO FRAME MANDADO POR UN NODO ...
			else if(this.frameReceived.getType().equals("FinalFrame")){
				
				this.finalFrameReceived = true;// la variable "finalFrameReceived" sirve para ...(de momento no tiene utilidad)
			}
			//SI EL PUNTO DE ACCESO VA A RECIBIR UNA CADENA DE FRAMES POR PARTE DE UN NODO ...
			else if(this.frameReceived.getType().equals("Data")){
				
				//SI ES EL PRIMER FRAME DE UNA CADENA DE FRAMES QUE SE VAN A MANDAR DURANTE LA CONEXIóN...
				if(this.frameReceived.getNumberOfFrame() == 0)this.ChannelIsBusy = true;
			}
				
		}
		return this.frameReceived;			
	}
	
 	
	private void send(Frame frame){
		
		    if(!entryChannel.getStateOfDates().equals("corrupt")){
		    	
		    
					    if(frame.getType().equals("CTS")){
					    	
					    	//Si va a enviar un Cts este debe mandarse en broadcast a todos los nodos conectados a el 					    	
					    	//for(int k=0;k<this.conectedNodes.size();k++){
						    	
					    	for(int k=0;k<this.accessibleNodes.size();k++){	
						    		try{	
						    			//conectedNodes.get(k).WriteBuffer(frame);			    			
						    			accessibleNodes.get(k).WriteBuffer(frame);
									} catch (InterruptedException e){
										
										e.printStackTrace();
									}					
						    	
						    }	
					    }
					    else{
					    	
					    	for(int k=0;k<this.accessibleNodes.size();k++){
						    	
						    	//if(accessibleNodes.get(k).getIdNode() == frameReceived.getOrigin()){
						    	if(accessibleNodes.get(k).getIdNode() == frame.getDestiny()){
						    	    		
						    		//System.out.println("PUNTO DE ACCESO: "+this.ID+" manda ACK a nodo "+frameReceived.getOrigin());
						    		
						    		try{	
										accessibleNodes.get(k).WriteBuffer(frame);
									} catch (InterruptedException e){
										
										e.printStackTrace();
									}					
						    	}
						    }	
					    }	
		 }
	}
	
	public void advance(int time){
		
		timeStoped+=time;//timeStoped: recordemos que es la variable que indica el tiempo que permanecera realizando una misma función.
	}

	public String Run(int currentTime) throws BiffException, IOException, InterruptedException{		
		
		if(this.configurated == false){
    		configuration();
    		configurated=true;		
		}	
		
		if(this.BeaconFrameTimeCounter>0){
		
			BeaconFrameTimeCounter--; //restamos el contador para una nueva transmision de beacon frame
		}
		if(BeaconFrameTimeCounter == 0){//si BeaconFrameTimeCounter==0 significa que 
			                           //que debe volver a mandar tramas beacon
			
			if(state == 3){
				
				state = -2;
				
				if(BackoffTime<=0){
					
					BackoffTime = this.ejectBackoff();
				}
						
		    	timeStoped = BackoffTime; 
		    	state = -2;//como el canal stá ocupado entonces debe ir al estado de espera.		   
			}
			

			queuingDelay++;//el retardo en cola de un beacon frame , es el tiempo desde que se debe mandar , hasta que es mandado	
		}
	
    		switch (state){
    			
    		case -3://estado de consumo de backoff...
    			
    			if(timeStoped>0 && !this.ChannelIsBusy(-1)){//si keda tiempo de backoff y el medio esta libre entonces consume backoff
                
    				main.TextOutFlow("Access point: "+this.idAccesPoint+" spending backoff  ("+timeStoped+" us, slots left:"+this.SlotsForWait);	
    		    	
    				this.BackoffTime--;		
    		    	if(timeStoped>0)timeStoped--;
    		    	
    		    	//ahora comprobamos si ha  consumido un slot completo:
    		    	
    		    	if((BackoffTime % WirelessChannel.timeForSlot) == 0){//si el resto de dividiar el tiempo de backoff restante y
    		    		                                             //el tamaños de un slot es cer, significa que ha consmuido almenos
    		    		                                            //un slot 
    		    		
    		    		if(this.SlotsForWait>0)this.SlotsForWait--;
    		    	}
    		    	
    		    	 		    	 
    		    	 //************DIBUJA******************************************************************
				 	 //************************************************************************************  
				 	 //this.addElementPlotted(new Diagram(Diagram.spend_backoff,currentTime)); 			    			
				 	 //************************************************************************************
				 	 //************************************************************************************
    			}
    			else if(timeStoped>0  && this.ChannelIsBusy(-1)){ //Si ve el canal como ocupado y tiene todavia tiempo 
        				                                         //de backoff sin consumir entonces espera...
        				
        				//System.out.println("NODO: "+this.idNode+" espera a que el canal esté libre."); 
        				main.TextOutFlow("Access point: "+this.idAccesPoint+" waiting for channel release."); 
        	    		
        				if((BackoffTime % WirelessChannel.timeForSlot)!=0){//si el resto de dividiar el tiempo de backoff restante y
                            //el tamaño de un slot es distinto de cer0, significa que no ha llegado a consumir un slot completo antes de 
        					//bloquearse 
        					
        				   int rest = BackoffTime % WirelessChannel.timeForSlot;
        					
        				   this.BackoffTime -= rest;
        				   timeStoped -= rest;
        				   
        				   this.BackoffTime+=WirelessChannel.timeForSlot;
    					   timeStoped+=WirelessChannel.timeForSlot;
    					   
    					   main.TextOutFlow("Access point: "+this.idAccesPoint+" incrementa el timeStoped "+timeStoped); 
    	    	    		
    					}
        			
        		   main.TextOutFlow("Access point: "+this.idAccesPoint+" Stop backoff  ("+timeStoped+" us, slots left:"+this.SlotsForWait);	
        		    		
        		   state = -2;//estado de espera...
        			
        		}
        		else 
        			if(timeStoped <=0 && !this.ChannelIsBusy(-1)){// Si el canal esta libre después de haber consumido 
        				                                     //un tiempo aleatorio de backoff entonces podemos 
        				                                    //transmitir.
        				
        				this.ejectBackoff("res");//como ha podido acceder al medio , resetea la ventana de backoff.    	    		
        	    		state = 0; //en este estado debe mandar la trama (beacon frame)   
        	    		main.TextOutFlow("Access point: "+this.idAccesPoint+" ending backoff ("+timeStoped+" us, slots left:"+this.SlotsForWait+".");
        				
        				 //************DIBUJA******************************************************************
    				 	 //************************************************************************************  
    				 	  this.addElementPlotted(new Diagram(Diagram.spend_backoff,currentTime)); 			    			
    				 	 //************************************************************************************
    				 	 //************************************************************************************    		
        	             return "occupies_channel_at_the_end_of_this_microsecond";
        		}
        		else
        		    if(timeStoped<=0 && this.ChannelIsBusy(-1)){
        		    	
            	    	// Si ha consumido su tiempo de backoff y vuelve a ver el canal como ocupado entonces debe aumentar la ventana
        		    	//contención y volver a ejecutar backoff.
        		    	
        		    	BackoffTime = this.ejectBackoff();
        		    	this.ejectBackoff("inc");//incrementamos la ventana de contención.
        		    	
        		    	timeStoped = BackoffTime; 
        		    	state = -2;//como el canal stá ocupado entonces debe ir al estado de espera
        		   
        		    	main.TextOutFlow("Access point: "+this.idAccesPoint+" increases content window(CW="+this.contWind+").");        		    	
        		    }    			
    			if(state!=-3 || (this.objectForWC.getSimulationTime()-1) == currentTime){
    				
    				 //************DIBUJA******************************************************************
				 	 //************************************************************************************  
				 	 this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 			    			
				 	 //************************************************************************************
				 	 //************************************************************************************
    			}   			
    			
    	    break;
    		case -2://estado de solicitud del medio para mandar tramas beacon...
    		    		
                frameReceived = this.waiting();
    			
    			if(frameReceived != null){//Si llega una trama, se trata...	    
    			    
					    			    if(frameReceived.getType().equals("FinalFrame")){    
					    			    	
					    			    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" recibe trama final de nodo "+frameReceived.getOrigin()+".");
					    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive  final frame from node "+frameReceived.getOrigin()+".");
					    			    	
					    			    	timeStoped = WirelessChannel.timeForSif;
					    			    	state = 4;    			    	
					    			    	finalFrameReceived = true;   			    	
					    			    }
					    			    else if(frameReceived.getType().equals("RequestFrame")){ // ...AP recibe una solicitud de conexión.
					    			    	
					    			    	AssociationRequestReceived = true;
					    			    	
					    			    	//...aquí lo que se hace es añadir a la lsita de nodos conectados a
					    			    	//este punto de acceso....
					    			    	this.conectedNodes.add(WirelessChannel.getNode(frameReceived.getOrigin()));
					    			    	
					    			    	this.TransmisionSpeedForNode.put(frameReceived.getOrigin(),((RequestFrame)this.frameReceived).getRate_APtoNode());
											
					    			    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" recibe 'request frame' de nodo "+frameReceived.getOrigin()+".");
					    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive 'request frame' from node "+frameReceived.getOrigin()+".");
					    			    	
					    			    	timeStoped = WirelessChannel.timeForSif;
					    			    	state = 4;    			    	
					    			    	finalFrameReceived = true;   	
					    
					    			    }
					    			    else if(frameReceived.getType().equals("RTS")){
					    			    	
					    			    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" recibe RTS de nodo "+frameReceived.getOrigin()+".");
					    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive RTS from node "+frameReceived.getOrigin()+".");
					    			    	
					    			    	
					    			    	float speedOfTransmission = (Float) TransmisionSpeedForNode.get(frameReceived.getOrigin());        				
					    			    	//int AckSizeInBits = WirelessChannel.AckSize * 8;        				
					        				//float transmissionRateInBitsPerSeg = speedOfTransmission*1024*1024; 
					        				 		
					    			    	//int timeReserveForSendAck = WirelessChannel.calculateChannelBusyTime(AckSizeInBits, transmissionRateInBitsPerSeg);
					        			
					    			    	//timeReserve = (WirelessChannel.timeForSif)
					    			    	//			  +((RTS_Frame)frameReceived).getTimeReserve()   AKI
					    			    	//			  +(WirelessChannel.timeForSif)
					    			    	//			  +timeReserveForSendAck;
					    			    	

					    			    	//Remember...
					    			    	//int timeForReserve =   (WirelessChannel.timeForSif)
					    			    	//  + timeForTransferCTS
					    			    	//  + (WirelessChannel.timeForSif)
					    			    	//  + timeForTransferData 
					    			    	//  + (WirelessChannel.timeForSif)
					    			    	//  + timeForTransferACK;
					    			    	
					    			    	int timeForTransferCTS  = WirelessChannel.calculateChannelBusyTime(WirelessChannel.CtsSize*8, speedOfTransmission*1024*1024);
					    			    	timeReserve = ((RTS_Frame)frameReceived).getTimeReserve();
					    			    	timeReserve = timeReserve
					    			    	              - (WirelessChannel.timeForSif)
					    			    	              - timeForTransferCTS;
					    			    	
					    			    	timeStoped = WirelessChannel.timeForSif;
					    			    	RtsReceived = true;    			    	
					    			    	state = 4;
					    			    }
					    			    else{
					    			    	
					    			    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" recibe correctamente una trama del nodo "+frameReceived.getOrigin()+".");
					    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive frame from node "+frameReceived.getOrigin()+".");
					    			    	
					    			    	timeStoped = WirelessChannel.timeForSif;
					    			    	state = 4;
					    			    }
					    			    
					    			    //************DIBUJA******************************************************************
						     			//************************************************************************************   			
						     			this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime));    			
						     			//************************************************************************************
						     			//************************************************************************************
    			}
    			else {//si no recibe ninguna trama
    				
					    				// si no ha llegado ninguna trama y el medio esta ocupado ...	
					    	    		//sigue esperando...        			
					    				if(this.ChannelIsBusy(-1) == true){//si medio ocupado...
					    					
					    					state = -2;
					    				}
					    				else{
					    	    		// si no ha llegado ninguna trama y el medio esta libre ...	
					    				// entonces comienza a contar difs...	
					    				    //timeStoped = WirelessChannel.timeForDif;
					    	    			state = 11;//vamos al estado ke carga el tiempo Dif en la variable timeStoped y comenzamos a consumirlo	
					    				}
					    				
					    				main.TextOutFlow("Access point: "+this.idAccesPoint+" checking ...");
					    				//************DIBUJA******************************************************************
					         			//************************************************************************************   			
					         			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime));    			
					         			//************************************************************************************
					         			//************************************************************************************
    			}	
	
    		break;
    		case 11://primer estado de consumo de difs
    			
    			//se supone que en este estado devuelve timeStoped = 0
    			
    			timeStoped = WirelessChannel.timeForDif;
    			state = -1;
    			
    			if(timeStoped>0){//...consume DIFS.
    				
    				if(this.ChannelIsBusy(-1) == true){//el -1 es para indicar al canal que es el propio AP que genera dicho canal el 
    					                               //que pregunta si está ocupado
    					
    					//si mientras consume difs ve el canal como ocupado entonces deja de contar 
    					//va al estado de espera...
    					state = -2;
    					timeStoped=0;
        				main.TextOutFlow("Access point: "+this.idAccesPoint+"  stop difs.");
        				
        				//************DIBUJA******************************************************************
        	 			//************************************************************************************  
        	 			//this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 
        				this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime));
        	 			//************************************************************************************
        	 			//************************************************************************************
    				}
    				else{//si el canal esta libre sigue contando, consumiendo DIF   				
       			        
    					main.TextOutFlow("Access point: "+this.idAccesPoint+"  spend DIF ("+timeStoped+"us).");        			       
    					if(timeStoped>0)timeStoped--;
       			     	  
       			        if((objectForWC.getSimulationTime()-1) == currentTime){ 
       			        	
	       			     	//************DIBUJA******************************************************************
	       		 			//************************************************************************************        		 			 
	       					this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime));
	       		 			//************************************************************************************
	       		 			//************************************************************************************
       			        }
    				}
    				
    			}
    			else{//termina de contar DIFS.
    				
    				
    				main.TextOutFlow("Access point: "+this.idAccesPoint+"  spend DIF ("+timeStoped+"us).");       			     
      			     
    				//Si el medio esta libre deberá comenzar a contar tiempo de backoff.
    				if(this.ChannelIsBusy(-1) == false){// Si el medio esta libre...    				
    					
    					
    					//************DIBUJA******************************************************************
    		 			//************************************************************************************  
    		 			//this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 
    					this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime));
    		 			//************************************************************************************
    		 			//************************************************************************************
    					
    					if(this.BackoffTime>0){//Si tiene tiempo de backoff...
    						//va al estado de consumo de backoff...
    						timeStoped = this.BackoffTime;
    						state = -3;
    					}
    					else{//...si no tiene tiempo de backoff almacenado entonces transmitirá.
            				
        					state = 0;
        					return "occupies_channel_at_the_end_of_this_microsecond";
        				}   					
    				} 
    				else{//deberá bloquearse...
    					
    					//si al terminar de contar difs es otro dispositivo el que tiene el medio ocupado 
    					//va al estado de espera...
    					state = -2;
        				main.TextOutFlow("Access point: "+this.idAccesPoint+"  stop difs.");
        				
        				
        				//************DIBUJA******************************************************************
        	 			//************************************************************************************  
        	 			//this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 
        				this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime));
        	 			//************************************************************************************
        	 			//************************************************************************************
    				}
    			}   			
    			
    			
    			break;
    		case -1://... consumiendo de DIFS.
    		
    			
    			if(timeStoped>0){//...consume DIFS.
    				
    				if(this.ChannelIsBusy(-1) == true){//el -1 es para indicar al canal que es el propio AP que genera dicho ganal el 
    					                               //que pregunta si está ocupado
    					
    					//si mientras consume difs ve el canal como ocupado entonces deja de contar 
    					//va al estado de espera...
    					state = -2;
        				main.TextOutFlow("Access point: "+this.idAccesPoint+"  stop difs.");
        				
        				//************DIBUJA******************************************************************
       		 			//************************************************************************************        		 			 
       					this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime-1));
       		 			//************************************************************************************
       		 			//************************************************************************************
        				//************DIBUJA******************************************************************
        	 			//************************************************************************************  
        	 			//this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 
        				this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime));
        	 			//************************************************************************************
        	 			//************************************************************************************
    				}
    				else{//si el canal esta libre sigue contando, consumiendo DIF   				
       			        
    					main.TextOutFlow("Access point: "+this.idAccesPoint+"  spend DIF ("+timeStoped+"us).");        			       
    					if(timeStoped>0)timeStoped--;
       			     	  
       			        if((objectForWC.getSimulationTime()-1) == currentTime){ 
       			        	
	       			     	//************DIBUJA******************************************************************
	       		 			//************************************************************************************        		 			 
	       					this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime));
	       		 			//************************************************************************************
	       		 			//************************************************************************************
       			        }
    				}
    				
    			}
    			else{//termina de contar DIFS.
    				
    				
    				main.TextOutFlow("Access point: "+this.idAccesPoint+"  spend DIF ("+timeStoped+"us).");       			     
      			     
    				//Si el medio esta libre deberá comenzar a contar tiempo de backoff.
    				if(this.ChannelIsBusy(-1) == false){// Si el medio esta libre...    				
    				
    					
    					
    					//************DIBUJA******************************************************************
    		 			//************************************************************************************  
    		 			//this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 
    					this.addElementPlotted(new Diagram(Diagram.spend_dif,currentTime));
    		 			//************************************************************************************
    		 			//************************************************************************************
    					
    					if(this.BackoffTime>0){//Si tiene tiempo de backoff...
    						//va al estado de consumo de backoff...
    						timeStoped = this.BackoffTime;
    						state = -3;
    					}
    					else{//...si no tiene tiempo de backoff almacenado entonces transmitirá.
            				
        					state = 0;
        					return "occupies_channel_at_the_end_of_this_microsecond";
        				} 
    					
    				} 
    				else{//deberá bloquearse...
    					
    					//si al terminar de contar difs es otro dispositivo el que tiene el medio ocupado 
    					//va al estado de espera...
    					state = -2;
        				main.TextOutFlow("Access point: "+this.idAccesPoint+"  stop difs.");
        				//************DIBUJA******************************************************************
        	 			//************************************************************************************  
        	 			//this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime)); 
        				this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime));
        	 			//************************************************************************************
        	 			//************************************************************************************
    				}
    			}   			
    		break;
    		case 0: //en este estado manda beacon frames a todos los nodos en difusion    		
	    			//En este primer estado el punto de acceso manda los beacon frames para anunciar la red a la que da cobertura		
	    			
    			    main.TextOutFlow("Access point: "+this.idAccesPoint+" send beacon frame in broadcast to all");
				
	    			int BeaconFrameSizeInBits = WirelessChannel.BeaconFrameSize*8;
	    					
	    			if( this.BeaconFrameRate == 0 ){
	    				
	    				//EL BEACON FRAME SE MANDA CON LA MENOR DE LAS VELOCIDADES POSIBLES EN EL ESTANDAR EN EL QUE ESTEMOS TRABAJANDO 
	    				Integer rateAux = (Integer) WirelessChannel.AllowedTransferRates.get(0);
	    			    this.BeaconFrameRate = ((float)rateAux.intValue()*(float)1024*(float)1024);
	    			}    			
	    			
	    			float transmissionRateInBitsPerSec = this.BeaconFrameRate;
	    			//...recordar timeStoped debe estar en microsegundos.
	    			
	    			timeStoped = WirelessChannel.calculateChannelBusyTime(BeaconFrameSizeInBits, transmissionRateInBitsPerSec);
	    			if(timeStoped>0)timeStoped--;
	    			state = 1;   			
    			
    			//************DIBUJA******************************************************************
    			//************************************************************************************  
    			this.addElementPlotted(new Diagram(Diagram.transmiting_BeaconFrame,currentTime));    			   			
    			//************************************************************************************
    			//************************************************************************************
    		
    			break;
    		case 1: //  Consumiendo tiempo de transmision de tramas beacon.
    			
    			main.TextOutFlow("Access point: "+this.idAccesPoint+" transmitting beacon frames ("+timeStoped+"us).");  
    			
    			if(timeStoped>1)timeStoped--;
    			else{ 	    	   	    	
    	
        	    	state = 2;
        	    }        	    
    				 
    			if((this.objectForWC.getSimulationTime()-1) == currentTime){
	        	    //************DIBUJA******************************************************************
	    			//************************************************************************************   			
	    			this.addElementPlotted(new Diagram(Diagram.transmiting_BeaconFrame,currentTime));    			
	    			//************************************************************************************
	    			//************************************************************************************
    			}
    			break;  			
    		case 2:// ... una vez consumido el tiempo de transmision de las tramas , las tramas llegan a sus destinatarios.    			
    			
    			main.TextOutFlow("Access point: "+this.idAccesPoint+" transmitting  beacon ("+timeStoped+"us).");  
     			
    			if(timeStoped>0)timeStoped--;
    			
    			this.successfulTransmissions++;
    			APSendsBeaconFrames();//manda TODOS los beacon frames 
    			state = 9; 
    			//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" transmitiendo beacon frames ("+timeStoped+"us).");  
    			//AQUI DEBE LIBERAR EL CANAL 
    			BeaconFrameTimeCounter = BeaconFrameTime;//volvemos a cargar el contador
    			//************DIBUJA******************************************************************
     			//************************************************************************************   			
     			this.addElementPlotted(new Diagram(Diagram.transmiting_BeaconFrame,currentTime));    			
     			//************************************************************************************
     			//************************************************************************************    			
     			this.successfulTransmissions++;
     			BeaconFrameTimeCounter = BeaconFrameTime;
		
     			return "free_channel_at_the_end_of_this_microsecond";    
    		case 9:// transicion entre el estado 2 y 3. los nodos tardan un microsegundo en ver el medio libre, en ese us el AP le dice al main que 
    			//puede adelantar la ejecución un tiempo igual al beaconframetimeconter mientras ke los nodos estan esperando a que el medio quede libre 
    			//y permiten que la ejecion se adelante el tiempo que indiquen otros dispositivos
    			
    			//..este estado es igual al 3 pero indicara al main que no adelante tiempo de ejecion
                frameReceived = this.waiting();
    			
    			if(frameReceived != null){	    
    			    
    			    if(frameReceived.getType().equals("FinalFrame")){    
    			    	
    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive  final frame from node "+frameReceived.getOrigin()+".");
    			    	
    			    	timeStoped = WirelessChannel.timeForSif;
    			    	state=4;    			    	
    			    	finalFrameReceived = true;  	
    			   }
    			    else if(frameReceived.getType().equals("RequestFrame")){ // ...AP recibe una solicitú de conexión.
    			    	
    			    	AssociationRequestReceived = true;

    			    	//...aquí lo que se hace es añadir a la lsita de nodos conectados a
    			    	//este punto de acceso....
    			    	this.conectedNodes.add(WirelessChannel.getNode(frameReceived.getOrigin()));
    			    	
    			    	this.TransmisionSpeedForNode.put(frameReceived.getOrigin(),((RequestFrame)this.frameReceived).getRate_APtoNode());
						
    			    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" recibe 'request frame' de nodo "+frameReceived.getOrigin()+".");
    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive 'request frame' from node "+frameReceived.getOrigin()+".");
    			    	
    			    	timeStoped = WirelessChannel.timeForSif;
    			    	state = 4;    			    	
    			    	finalFrameReceived = true;   	
    			    	
    			    	
    			    	//************DIBUJA******************************************************************
    	     			//************************************************************************************   			
    	     			this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime));    			
    	     			//************************************************************************************
    	     			//************************************************************************************
    			    }
    			    else if(frameReceived.getType().equals("RTS")){
    			    	
    			    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" recibe RTS de nodo "+frameReceived.getOrigin()+".");
    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive RTS from node "+frameReceived.getOrigin()+".");
    			    	
    			    	
    			    	
    			    	//int AckSizeInBits = WirelessChannel.AckSize * 8;  				
    			    	float speedOfTransmission = (Float) TransmisionSpeedForNode.get(frameReceived.getOrigin());    			
        				float transmissionRateInBitsPerSeg = speedOfTransmission*1024*1024; 
        				 		
        				//int timeReserveForSendAck = WirelessChannel.calculateChannelBusyTime(AckSizeInBits, transmissionRateInBitsPerSeg);
        				
    			    	//timeReserve = (WirelessChannel.timeForSif)
    			    	//               +((RTS_Frame)frameReceived).getTimeReserve() AKI
    			    	//               +(WirelessChannel.timeForSif)
    			    	//               +timeReserveForSendAck;
    			    	
    			    	
    			    	int timeForTransferCTS  = WirelessChannel.calculateChannelBusyTime(WirelessChannel.CtsSize*8, speedOfTransmission*1024*1024);
    			    	timeReserve = ((RTS_Frame)frameReceived).getTimeReserve();
    			    	timeReserve = timeReserve
    			    	              - (WirelessChannel.timeForSif)
    			    	              - timeForTransferCTS;
    			    	
    			    	timeStoped = WirelessChannel.timeForSif;
    			    	RtsReceived = true;    			    	
    			    	state = 4;
    			    	
    			    	
    			    	//************DIBUJA******************************************************************
    	     			//************************************************************************************   			
    	     			this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime));    			
    	     			//************************************************************************************
    	     			//************************************************************************************
    			    }
    			    else {
    			    	
    			    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" recibe correctamente una trama del nodo "+frameReceived.getOrigin()+".");
    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive frame from node "+frameReceived.getOrigin()+".");
    			    	
    			    	timeStoped = WirelessChannel.timeForSif;
    			    	state = 4;  
    			    	
    			    	
    			    	//************DIBUJA******************************************************************
    	     			//************************************************************************************   			
    	     			this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime));    			
    	     			//************************************************************************************
    	     			//************************************************************************************
    			    }
    			    
    			}
    			else {// si no llega trama
    				
    				//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" escuchando ...");
    				main.TextOutFlow("Access point: "+this.idAccesPoint+" checking ...");
    				state = 3;    	
    				//************DIBUJA******************************************************************
         			//************************************************************************************   			
         			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime));    			
         			//************************************************************************************
         			//************************************************************************************
    			}    			
    		break;
    		case 3:// En este estado el punto de acceso espera a recibir tramas de nodos a las que tiene alcance.    			
    			 
    			frameReceived = this.waiting();
    			
    			if(frameReceived != null){	    
    			    
    			    if(frameReceived.getType().equals("FinalFrame")){    
    			    	
    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive  final frame from node "+frameReceived.getOrigin()+".");
    			    	
    			    	timeStoped = WirelessChannel.timeForSif;
    			    	state=4;    			    	
    			    	finalFrameReceived = true;  	
    			   }
    			    else if(frameReceived.getType().equals("RequestFrame")){ // ...AP recibe una solicitú de conexión.
    			    	
    			    	AssociationRequestReceived = true;

    			    	//...aquí lo que se hace es añadir a la lsita de nodos conectados a
    			    	//este punto de acceso....
    			    	this.conectedNodes.add(WirelessChannel.getNode(frameReceived.getOrigin()));
    			    	
    			    	this.TransmisionSpeedForNode.put(frameReceived.getOrigin(),((RequestFrame)this.frameReceived).getRate_APtoNode());
						
    			    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" recibe 'request frame' de nodo "+frameReceived.getOrigin()+".");
    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive 'request frame' from node "+frameReceived.getOrigin()+".");
    			    	
    			    	timeStoped = WirelessChannel.timeForSif;
    			    	state = 4;    			    	
    			    	finalFrameReceived = true;   	
    			    	
    			    	
    			    	//************DIBUJA******************************************************************
    	     			//************************************************************************************   			
    	     			this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime));    			
    	     			//************************************************************************************
    	     			//************************************************************************************
    			    }
    			    else if(frameReceived.getType().equals("RTS")){
    			    	
    			    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" recibe RTS de nodo "+frameReceived.getOrigin()+".");
    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive RTS from node "+frameReceived.getOrigin()+".");
    			    	
    			    	
    			    	float speedOfTransmission = (Float) TransmisionSpeedForNode.get(frameReceived.getOrigin());    			
        				
        				int AckSizeInBits = WirelessChannel.AckSize * 8;  				
        				
        				float transmissionRateInBitsPerSeg = speedOfTransmission*1024*1024; 
        				 		
    			    	int timeReserveForSendAck = WirelessChannel.calculateChannelBusyTime(AckSizeInBits, transmissionRateInBitsPerSeg);
        				
    			    	int timeForTransferCTS  = WirelessChannel.calculateChannelBusyTime(WirelessChannel.CtsSize*8, speedOfTransmission*1024*1024);
    			    	
    			    	//timeReserve = (WirelessChannel.timeForSif)
    			    	//               +((RTS_Frame)frameReceived).getTimeReserve()
    			    	//               +(WirelessChannel.timeForSif)
    			    	//               +timeReserveForSendAck;
    			    	//if(timeReserve>89){
    			    	//	System.out.println("Borrame");
    			    	//}
    			    	
    			    	timeReserve = ((RTS_Frame)frameReceived).getTimeReserve()
	    	              - (WirelessChannel.timeForSif)
	    	              - timeForTransferCTS;
    			    	
    			    	
    			    	timeStoped = WirelessChannel.timeForSif;
    			    	RtsReceived = true;    			    	
    			    	state = 4;
    			    	
    			    	
    			    	//************DIBUJA******************************************************************
    	     			//************************************************************************************   			
    	     			this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime));    			
    	     			//************************************************************************************
    	     			//************************************************************************************
    			    }
    			    else {
    			    	
    			    	//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" recibe correctamente una trama del nodo "+frameReceived.getOrigin()+".");
    			    	main.TextOutFlow("Access point: "+this.idAccesPoint+" receive frame from node "+frameReceived.getOrigin()+".");
    			    	
    			    	timeStoped = WirelessChannel.timeForSif;
    			    	state = 4;  
    			    	
    			    	
    			    	//************DIBUJA******************************************************************
    	     			//************************************************************************************   			
    	     			this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime));    			
    	     			//************************************************************************************
    	     			//************************************************************************************
    			    }
    			    
    			}
    			else {// si no llega trama
    				
    				//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" escuchando ...");
    				main.TextOutFlow("Access point: "+this.idAccesPoint+" checking ...");
    				state = 3;    	
    				//************DIBUJA******************************************************************
         			//************************************************************************************   			
         			this.addElementPlotted(new Diagram(Diagram.inactive_wait,currentTime));    			
         			//************************************************************************************
         			//************************************************************************************
    			}    			
    		break;
    		case 4://consumiendo sif (inter space frame)
    			
    			main.TextOutFlow("Access point: "+this.idAccesPoint+" spending sif ("+timeStoped+"us)");    			
    						
    			if(timeStoped>0)timeStoped--;
    			
    			//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" consumiendo sif ("+timeStoped+"us)");
    			
    			if((this.objectForWC.getSimulationTime()-1) == currentTime){
	    			//************DIBUJA******************************************************************
	     			//************************************************************************************   			
	     			//this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime));    			
	     			//************************************************************************************
	     			//************************************************************************************
    			}     			
     			if(timeStoped <=0 ){ 
    				
    				//Termina de contar SIFS:    				
    				
     				//************DIBUJA******************************************************************
         			//************************************************************************************   			
         			this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime));    			
         			//************************************************************************************
         			//************************************************************************************
     				
     				
    				if(this.ChannelIsBusy(-1) == false){// Si el medio esta libre... 
    					state = 5;
    					
    					//if(RtsReceived){
    						
    						//return "occupies_channels_at_the_end_of_this_microsecond";
    					//}
    					return "occupies_channel_at_the_end_of_this_microsecond";
        	    	}     			
    			} 
     		
     		break;
    		case 5: //comienza a mandar ack/cts/associationresponse
    			
    			
    			if(RtsReceived){
    				RtsReceived = false;
    				//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" manda CTS a nodo "+frameReceived.getOrigin()+".");
    				main.TextOutFlow("Access point: "+this.idAccesPoint+" sends CTS (in broadcast).");    				
    			
    				float speedOfTransmission = (Float)TransmisionSpeedForNode.get(frameReceived.getOrigin());    			
    				//float speedOfTransmission = (Float)WirelessChannel.maximumTransmissionSpeed;
    				
    				timeStoped = WirelessChannel.calculateChannelBusyTime(WirelessChannel.CtsSize*8, speedOfTransmission*1024*1024);
    				ExitBuffer = new CTS_Frame("CTS",this.idAccesPoint,frameReceived.getOrigin(),0,currentTime,WirelessChannel.CtsSize,timeReserve);
    			    timeReserve = 0;
    				//************DIBUJA******************************************************************
	    			//************************************************************************************    			
	    			this.addElementPlotted(new Diagram(Diagram.transmiting_CTS,currentTime+timeStoped));    			
	    			//************************************************************************************
	    			//************************************************************************************
	    			
	    			
	    			float transmissionRateInUs = ((float)speedOfTransmission*1024*1024)/(float)Math.pow(10, 6);	    			
	    			utilization += (WirelessChannel.CtsSize*8/transmissionRateInUs);
	    			transmissionDuration +=timeStoped;
	    		    		
	    			    	
    			}else if(AssociationRequestReceived){
    				
    				AssociationRequestReceived = false;   
    				//el punto de acceso mide la intensidad que le llega del nodo que se quiere
    				//conectar a el para comprobar cual es la velocidad mas apropiada entre el nodo y 
    				// este punto de acceso.
    				float intensityReceivedFromNode = this.frameReceived.getEnergyOfSignal();
    				//__________________________________________________________________________________________________________________________    				
    				    				
    				
    				float rate_NodeToAp = WirelessChannel.calculateSpeedMoreApropriate(TransmissionSpeed,sensitivity,intensityReceivedFromNode);
    			    	
    				//__________________________________________________________________________________________________________________________
    				
    				
		    		if(rate_NodeToAp >0 ){ //si rate_NodeToAp == 0 significa que el nodo no tiene potenciia para alcanzar este AP
		    			
			    				//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" manda 'AssociationResponse' a nodo "+frameReceived.getOrigin()+".");
		    			        main.TextOutFlow("Access point: "+this.idAccesPoint+" sends 'AssociationResponse' to node "+frameReceived.getOrigin()+".");
	    				
			    				float speedOfTransmission = (Float) TransmisionSpeedForNode.get(frameReceived.getOrigin());    			
			    				
			    				//timeStoped = Math.round(WirelessChannel.AckSize/((float)(float)speedOfTransmission/(float)Math.pow(10, 6)));//frame size en bits
			    				int responseInBits = WirelessChannel.AssociationRespSize * 8;    				
			    				
			    				float transmissionRateInBitsPerSeg = speedOfTransmission*1024*1024; 
			    				timeStoped = WirelessChannel.calculateChannelBusyTime(responseInBits, transmissionRateInBitsPerSeg);
			    				
					            Frame frameAux = new AssociationResponse("AssociationResponse",this.idAccesPoint,frameReceived.getOrigin(),0,currentTime,WirelessChannel.AssociationRespSize);
			    			    
					            ((AssociationResponse)frameAux).setRate_NodeToAP(rate_NodeToAp);
			    				ExitBuffer = frameAux;
			    				//************DIBUJA******************************************************************
				    			//************************************************************************************	    			
 				    			this.addElementPlotted(new Diagram(Diagram.transmiting_RespFrame,currentTime));    			
				    			//************************************************************************************
				    			//************************************************************************************
			    			
 				    			float transmissionRateInUs = ((float)speedOfTransmission*1024*1024)/(float)Math.pow(10, 6);	    			
 				    			utilization += (WirelessChannel.CtsSize*8/transmissionRateInUs);
 				    			transmissionDuration += timeStoped;
		    		}		    		
		        }
    			else{
    				
    				//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" manda ACK a nodo "+frameReceived.getOrigin()+".");
    			    main.TextOutFlow("Access point: "+this.idAccesPoint+" sends ACK to node "+frameReceived.getOrigin()+".");
    					
    				float speedOfTransmission = (Float) TransmisionSpeedForNode.get(frameReceived.getOrigin());    			
    				
    				int AckSizeInBits = WirelessChannel.AckSize * 8;    				
    				
    				float transmissionRateInBitsPerSeg = speedOfTransmission*1024*1024; 
    				timeStoped = WirelessChannel.calculateChannelBusyTime(AckSizeInBits, transmissionRateInBitsPerSeg);
    						//AKI FALLA 
    				ExitBuffer = new Frame("ACK",this.idAccesPoint,frameReceived.getOrigin(),0,currentTime,WirelessChannel.AckSize);
    			
    				//************DIBUJA******************************************************************
	    			//************************************************************************************	    			
	    			this.addElementPlotted(new Diagram(Diagram.transmiting_ACK,currentTime));    			
	    			//************************************************************************************
	    			//************************************************************************************
	    			float transmissionRateInUs = ((float)speedOfTransmission*1024*1024)/(float)Math.pow(10, 6);	    			
	    			utilization += (WirelessChannel.CtsSize*8/transmissionRateInUs);
	    			transmissionDuration +=timeStoped;
    			}
    			 			
    			
    			if(timeStoped>0)timeStoped--;
    			
    			if(timeStoped<=0){
    				
    				state = 7;
    				timeStoped = 0;
    			}
    			else{   				
    				state = 6;
    			}
    			
    		    break;
    		case 6://consume tiempo de transmision de la trama ACK/CTS/Association Response
    			
    			
    			main.TextOutFlow("Access point: "+this.idAccesPoint+" transmitting CTS  (in broadcast).");
				
    			if(timeStoped>0)timeStoped--;
    			if(ExitBuffer.getType().equals("CTS")){
    				
    				System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" transmitiendo CTS  ("+timeStoped+"us)");
    				
    				
    				//************DIBUJA******************************************************************
	    			//************************************************************************************    			
	    			//this.addElementPlotted(new Diagram(Diagram.transmiting_CTS,currentTime));    			
	    			//************************************************************************************
	    			//************************************************************************************
    			} 
    			else if(ExitBuffer.getType().equals("AssociationResponse")){
    				//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" transmitiendo 'Association Response Frame'  ("+timeStoped+"us)");
    				main.TextOutFlow("Access point: "+this.idAccesPoint+" transmitting 'Association Response Frame'  ("+timeStoped+"us)");
    				
    				
    				//************DIBUJA******************************************************************
	    			//************************************************************************************    			
	    			//this.addElementPlotted(new Diagram(Diagram.transmiting_RespFrame,currentTime));    			
	    			//************************************************************************************
	    			//************************************************************************************
    			}
    			else {
    				
    				//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" transmitiendo ACK  ("+timeStoped+"us)");
    				main.TextOutFlow("Access point: "+this.idAccesPoint+" transmitting ACK  ("+timeStoped+"us)");
    				
    				
    				//************DIBUJA******************************************************************
	    			//************************************************************************************    			
	    			//this.addElementPlotted(new Diagram(Diagram.transmiting_ACK,currentTime));    			
	    			//************************************************************************************
	    			//************************************************************************************
    			}
    			
        	    if(timeStoped<=1){ 
        	    	
        	    	state = 7; 
        	    	
        	    }           	    
        	    
        	    if(state != 6 || (objectForWC.getSimulationTime()-1) == currentTime){ // Si cambia de estado o finaliza la simulacion
        	    	
        			if(ExitBuffer.getType().equals("CTS")){
        				
        				main.TextOutFlow("Access point: "+this.idAccesPoint+" transmitting CTS  (in broadcast).");				
        				//************DIBUJA******************************************************************
    	    			//************************************************************************************    			
    	    			this.addElementPlotted(new Diagram(Diagram.transmiting_CTS,currentTime));    			
    	    			//************************************************************************************
    	    			//************************************************************************************
        			} 
        			else if(ExitBuffer.getType().equals("AssociationResponse")){
        				
        				main.TextOutFlow("Access point: "+this.idAccesPoint+" transmitting 'Association Response Frame'  ("+timeStoped+"us)");
        				
        				//************DIBUJA******************************************************************
    	    			//************************************************************************************    			
    	    			this.addElementPlotted(new Diagram(Diagram.transmiting_RespFrame,currentTime));    			
    	    			//************************************************************************************
    	    			//************************************************************************************
        			}
        			else {
        				
        				main.TextOutFlow("Access point: "+this.idAccesPoint+" transmitting ACK  ("+timeStoped+"us)");
        				
        				//************DIBUJA******************************************************************
    	    			//************************************************************************************    			
    	    			this.addElementPlotted(new Diagram(Diagram.transmiting_ACK,currentTime));    			
    	    			//************************************************************************************
    	    			//************************************************************************************
        			}
        	    }
    			break;
    		case 7:	//envia la trama ACK/CTS/Response frame llega a su destinatario.
    		
    			this.send(ExitBuffer);//si esta funcion recibe un Cts lo manda en broadcast a todos los nodos conectados a este AP
    			
    			if(timeStoped>0)timeStoped--;
    			
    			if(ExitBuffer.getType().equals("CTS")){
    				
    				//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" ...nodo "+frameReceived.getOrigin()+" debe recibir Cts en este instate.");
    				main.TextOutFlow("Access piont: "+this.idAccesPoint+" ...node "+frameReceived.getOrigin()+" should receive Cts in this moment.");
    				
    				
    				//************DIBUJA******************************************************************
	    			//************************************************************************************    			
	    			this.addElementPlotted(new Diagram(Diagram.transmiting_CTS,currentTime));    			
	    			//************************************************************************************
	    			//************************************************************************************
	    		
	    			numberOfCtsSends++;
	    			timeStoped = WirelessChannel.timeForSif;
	    			
	    			state = 8;
    			} 
    			else if(ExitBuffer.getType().equals("AssociationResponse")){
    				
    				
    				//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" ...nodo "+frameReceived.getOrigin()+" debe recibir 'Association Response Frame' en este instate.");
    				main.TextOutFlow("Access point: "+this.idAccesPoint+" ...node "+frameReceived.getOrigin()+" should receive 'Association Response Frame' en este instate.");
    				
    				
    				//************DIBUJA******************************************************************
	    			//************************************************************************************    			
	    			this.addElementPlotted(new Diagram(Diagram.transmiting_RespFrame,currentTime));    			
	    			//************************************************************************************
	    			//************************************************************************************
    			
	    			numberOfAssociationResponseFrameSends++;     			 

	    			if(BeaconFrameTimeCounter == 0){
	    				//si el medio esta libre
	    				if(this.ChannelIsBusy(-1) == false){
	    					//timeStoped = WirelessChannel.timeForDif;
	    	    			state = 11;//estado que carga el dif y comienza a consumirlo
	    				}
	    				else state = -2;
	    			} 
	    			else{//estado de espera (estado 9 igual que estado 3 pero timeStoped=0)
	    				state = 9;
	    			}
    			}
    			else {// manda ACK
    				
    				//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" ...nodo "+frameReceived.getOrigin()+" debe recibir Ack en este instate.");
    				main.TextOutFlow("Access point: "+this.idAccesPoint+" ...node "+frameReceived.getOrigin()+" should receive Ack in this moment.");
    				
    				//************DIBUJA******************************************************************
	    			//************************************************************************************    			
 	    			this.addElementPlotted(new Diagram(Diagram.transmiting_ACK,currentTime));    			
	    			//************************************************************************************
	    			//************************************************************************************
    			   
	    			numberOfAckSends++;  	    			
	    				
	    			if(BeaconFrameTimeCounter == 0){
	    				//si el medio esta libre
	    				if(this.ChannelIsBusy(-1) == false){
	    					//timeStoped = WirelessChannel.timeForDif;
	    	    			state = 11;//carga el dif y comienza a consumirlo (timeStoped=0)
	    				}
	    				else state = -2;
	    			} 
	    			else{//va al estado de espera...
	    				//estado de espera (estado 9 igual que estado 3 pero timeStoped=0)
	    				state=9;
	    			}
    			}
    			
    			if(this.finalFrameReceived == true){    
    				
    				finalFrameReceived = false;	//... de momento no tiene ninguna utilidad controlar que ha recibido la ultima trama
    			}    		
    					
    	    this.successfulTransmissions++;   
    	    //liberamos el canal al final del microsegundo 
    	    //para ke los nodos no vean libre el canal en el mismo us en el ke es liberado
  
    	    return "free_channel_at_the_end_of_this_microsecond";  		
    		
    		case 8://ha este estado llega cuando recibe una trama CTS 
    		
    			main.TextOutFlow("Access point: "+this.idAccesPoint+" spending sif ("+timeStoped+"us)");
          	   
    			if(timeStoped>0)timeStoped--;
    			
    			//System.out.println("PUNTO DE ACCESO: "+this.idAccesPoint+" consumiendo sif ("+timeStoped+"us)");
    			
    			if(timeStoped<=0){ 
    				
    				//Termina de contar SIFS:
    			
    					state = 3;		
    			} 
    			if(state != 8 || (this.objectForWC.getSimulationTime()-1) == currentTime){//si llega al final de la simulación o cambia de estado...){
    			    
    				//************DIBUJA******************************************************************
    				//************************************************************************************   			
    				this.addElementPlotted(new Diagram(Diagram.spend_sif,currentTime));    			
    				//************************************************************************************
    				//************************************************************************************
    			}
    		break;
    		}   		
    		
    		/*
    		//si tiene que volver a mandar tramas beacon y el canal no  esta ocupado ...
    		if(BeaconFrameTimeCounter < 0 && !this.entryChannel.isBusy()){   			
                //significa que le están mandando una trama, debe seguir en el estado 3 
    				if(state == 3 || state == 0)
    				{//estado 3 es un estado de espera de tramas ...
    					
    					
    					state = -2;//vuelve mandar tramas de baliza , beacon frames...
    					BeaconFrameTimeCounter = BeaconFrameTime;
    				}
    				else queuingDelay++;//el retardo en cola de un beacon frame , es el tiempo desde que se debe mandar , hasta que es mandado			  
    		}
    		*/		
    					
	return "null";
	
	}
	
	public ArrayList getSensitivity(){
		return this.sensitivity;
	}
	  
    public ArrayList getTransmissionSpeed(){
    	
    	return TransmissionSpeed;
    }
	
	public int getNumberOfAckSends(){
		
		return numberOfAckSends;
	}
	
    public int getNumberOfCtsSends(){
    
    	return numberOfCtsSends;
    }
   
    public int getNumberOfAssociationResponseFrameSends(){
    
    	return numberOfAssociationResponseFrameSends; 
    }
    
    public float getUtilization(){
    	
    	//recodar que la variable rate contiene la velocidad establecidad entre el nodo y el 
    	//punto de acceso en bits/seg.
    	return  utilization;
    }
    
    public float getTransmissionDuration(){
    
    	return this.transmissionDuration;
    }
    
    public float getSuccesfulTransmission(){
    	
    	return this.numberOfAckSends+this.numberOfAssociationResponseFrameSends+this.numberOfCtsSends;
    }
    
    public int getQueuingDelay(){//retardo en cola
    	
    	return queuingDelay;
    }
    
    public void Update(ArrayList rates,ArrayList sens){
 	   
    	sensitivity = sens;    	
    	TransmissionSpeed = rates;
        if(accessPointConfigurationPanel!=null){	
        	accessPointConfigurationPanel.Update(rates,sens);
        	
        }
    }
    
    public JPanel getConfigurationPanel(){
    	
    	accessPointConfigurationPanel.getJTextField_energy();
    	return accessPointConfigurationPanel;
    }
    
    public void setChannelsAsFree(){
    	for(int k=0;k<this.accessibleNodes.size();k++){	
    					    			
    			accessibleNodes.get(k).setEntryChannelAsFree();
					
        }	
    }
    public void setChannelsAsBusy(){
    	for(int k=0;k<this.accessibleNodes.size();k++){	
    								    			
    			accessibleNodes.get(k).setEntryChannelAsBusy(-1);				
        }	
    }
    
    public int getTimeStoped(){
    	
    	if(state==5)return 0;//para indicar que no se puede adelantar la simulación
    	else if(state == 9)return 0;//estado en el que termina de mandar una trama beacon
    	else if(state == 11)return 0;//comienza a consumir dif
    	else if (state== -2) return -1;//state -2 estado de solicitud del medio para mandar tramas beacon
    	else if(timeStoped == 0) return BeaconFrameTimeCounter;    	
    	else
    	    return timeStoped;
    }
    
    public void goForward(int time){
    	
    	
    	if(this.BeaconFrameTimeCounter > 0){
    		
    		if(time>=BeaconFrameTimeCounter)BeaconFrameTimeCounter=0;
    		else BeaconFrameTimeCounter-=time; //restamos el contador para una nueva transmision de beacon frame
		}
    	
    	if(timeStoped>0){
     	   
    		timeStoped-=time;
     	}
    	if(state == -3){// ... si esta en el estado de consumo de backoff
    		
    		BackoffTime-=time;
    		this.SlotsForWait = BackoffTime / WirelessChannel.timeForSlot;    		
    	}
    }    
}
