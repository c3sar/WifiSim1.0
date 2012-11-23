package ElementsForSim;

import java.util.ArrayList;

import Main.main;


public class Channel {
	
   private int numberOfChannel;
   private boolean isBusy;
   private Object container = null;//inicializado a null
    
   String stateOfDates = "correct";//correct or corrupt
   int nodesWriting = 0; 
   private ArrayList<Integer> WhoISOcupingThisChannel;

   String property;
   
   public void initialize(){
	   
	   container = null;
	   stateOfDates = "correct";
	   nodesWriting = 0;
       this.isBusy = false;
       WhoISOcupingThisChannel = new ArrayList();
   }
   
   public Channel(String prop){
	   this.property = prop;
	   initialize();
   }      
   /*
   public Channel(int numberOfChannel){
	   this.isBusy=false;
	   this.numberOfChannel = numberOfChannel;
       container = null;
   }
   */
   
   public String getType(){
	   
	   return ((Frame)container).getType();	   
   }  
   
   public void Write(Frame frame){
	   
	   if(this.stateOfDates.equals("corrupt")){
		   
		   //si el canal es escrito aun conteniendo datos,
		   //significa que alguien ha escrito en el antes de que el receptor leyera los datos		   
		   container = null;
	   }
	   else{
		   
	   container = frame;	   
	   }   
   }  
   
   public Object Read(){
	 
	   if(container != null){
		 
		   Object aux = this.container;// una vez leido el canal , lo borramos , es decir se pone a null.
		   this.container = null;
		   return aux;
	   }
	   else {
		   // el valor del canal esta nulo.(puede ser porque todavia nadie haya escrito en el o porque se ha producido una colisión).
		   return this.container;
	   } 
   }
  
   public void setIsBusy_AsTrue(int idNode){
	   
		   
		   this.nodesWriting++;	
		   this.WhoISOcupingThisChannel.add(idNode);//Guardamos un identificador del nodo que ha ocupado el canal ... 
		   //si esta función es invocada estando el canal ya ocupado por otro nodo , entonces se produce colisión.
		   
		   if(this.nodesWriting > 1){
			   
			   System.out.println("***** error el canal "+this.numberOfChannel+" ha sido sobreescrito!!!**** ");
			   System.out.println("instante ->"+WirelessChannel.nowTime);
			
			   System.out.println("nodo: "+idNode);
			  
			   main.TextOutFlow(" Colision caused by nodes : ");
			   for(int p=0;p<WhoISOcupingThisChannel.size();p++){
				   
				   main.TextOutFlow(" Node: "+WhoISOcupingThisChannel.get(p));   
			   }
		   }
		   if(this.isBusy==true){
			   
			   this.stateOfDates = "corrupt";
			   WirelessChannel.incCollisions(this.property);		   
		   }
		   else{
			   
			   this.isBusy = true;		   
		   }		   
	     
   }
   
   public void setIsBusy_AsFalse(Integer idNode){
	   
	  if(nodesWriting!=0){
		   nodesWriting--;//contamos con la posibilidad de que haya varios escribiendo(por colisión) entonces si todavia queda alguno 
           //escribiendo el canal no puede dejar de ser ocupado.
		   this.WhoISOcupingThisChannel.remove(idNode);
	  }
		   if(nodesWriting == 0){
			   this.isBusy = false;
			   if(this.stateOfDates.equals("corrupt"))this.stateOfDates = "correct";
		   }	  
	  
   }
   
   public boolean isBusy(){
	   
	   return this.isBusy;
   }
   
   public ArrayList WhoIsOccupingThisChannel(){
	   return this.WhoISOcupingThisChannel;
   }
   
   public String getStateOfDates(){
	   
	   return this.stateOfDates;
   }
}
