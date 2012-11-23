package Diagram;
import java.awt.Color;


public class Diagram {

	
	String Type;
	public int initial_time;
	public int final_time;
	int last;//duracion...

	
	public static int inactive_wait   = 1;
	public static int spend_dif       = 2;
	public static int spend_sif       = 3;
	public static int transmiting     = 4;
    public static int transmiting_ACK = 5;
    public static int transmiting_RTS = 6;
    public static int transmiting_CTS = 7;    
    public static int spend_backoff   = 8;
    public static int transmiting_BeaconFrame = 9;
    public static int transmiting_RespFrame = 10; 
    public static int transmiting_RequestFrame = 11;
    public static int time_out = 12;
    public static int RTS_NAV = 13;
    public static int CTS_NAV = 14;
    
    public  String getType(){
		
		return Type;
	}
    
	public Diagram(int type,int final_time){//time en us.
		
		
		this.final_time = final_time;
		this.last = final_time;
		
		
		switch(type){
		
		case 1://spera inactiva.
			
			Type = new String("inactive_wait");
			break;
		case 2://consumiendo dif.
			Type = new String("spend_dif");
			break;
		case 3://consumiendo sif.
			Type = new String("spend_sif");
			break;	
		case 4://transmiting.
			Type = new String("transmiting");
			break;	
		case 5://transmiting ACK.
			Type = new String("transmiting_ACK");
			break;
		case 6://transmiting RTS.
			Type = new String("transmiting_RTS");
			break;
		case 7://transmiting CTS.
			Type = new String("transmiting_CTS");
			break;	
		case 8://spend backoff
			Type = new String("spend_backoff");
			break;
		case 9://transmiting BeaconFrame
			Type = new String("transmiting_BeaconFrame");
			break;
		case 10://transmiting response frame (accept connection)
			Type = new String("transmiting_RespFrame");
			break; 
		case 11://transmiting request frame (request connection) 
			Type = new String("transmiting_RequestFrame");
			break;
		case 12://time-out 
			Type = new String("time-out");
			break;	
		case 13://spend nav received by RTS. 
			Type = new String("RTS_NAV");
			break;	
		case 14://spend nav received by CTS.
			Type = new String("CTS_NAV");
			break;	
		}		
	}
	
}
