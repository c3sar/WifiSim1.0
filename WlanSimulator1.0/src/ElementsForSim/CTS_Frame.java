package ElementsForSim;

public class CTS_Frame extends Frame{

	int timeReserve;
	
	public CTS_Frame(String type,int origin,int destiny,int numberOfFrame,int momentThatIsCreated,int frameSize,int timeReserve){
	
		super(type,origin,destiny,numberOfFrame,momentThatIsCreated,frameSize);
	    this.timeReserve = timeReserve; 
	}
	
	public int getTimeReserve(){
		
		return timeReserve;
	}
}
