package ElementsForSim;

public class Frame {

	private String type;
	private int Origin;
	private int Destiny;
	private int numberOfFrame;
	private int size;
	private int rate;
	private int momentThatIsCreated;
	private int startTransmission;
	private int transmissionDuration;
    private int jitter;
    private float energyOfSignal;
    
    public Frame(String type,int origin,int destiny,int numberOfFrame,int momentThatIsCreated,int frameSize){
    	
    	this.Origin = origin;
    	this.Destiny = destiny;
    	this.type = type;
    	this.numberOfFrame = numberOfFrame;
    	this.size = frameSize;
    	this.momentThatIsCreated = momentThatIsCreated; 
        
	}   
	public Frame(String type,int origin,int destiny,int numberOfFrame,int momentThatIsCreated,int frameSize,float energyOfSignal){
    	
    	this.Origin = origin;
    	this.Destiny = destiny;
    	this.type = type;
    	this.numberOfFrame = numberOfFrame;
    	this.size = frameSize;
    	this.momentThatIsCreated = momentThatIsCreated; 
        this.energyOfSignal =  energyOfSignal;
	}    
	public String getType(){
	
		return type;
	}
	public int getOrigin(){
		return Origin;
	}
	public int getDestiny(){
		return Destiny;
	}	
	public int getNumberOfFrame(){
	    return this.numberOfFrame;	
	}	
	
	public void setRate(int rate){
		this.rate = rate;
	}
	public int getRate(){
		return rate;
	}
	public int getMomentThatIsCreated(){
		return momentThatIsCreated;
	}
    public int getSize(){
		
		return size;
	}
    public void setStartTransmission(int moment){
    	this.startTransmission = moment;
    }
    
    public int getStartTransmission(){
    	return this.startTransmission;
    }
    
    public void setTransmissionDuration(int transmissionDuration){
    	
    	this.transmissionDuration = transmissionDuration;
    }
    
    public void setJitter(int jitter){
    	this.jitter =  jitter;
    }
    
    public int getJitter(){
    	return this.jitter;
    }
    
    public float getEnergyOfSignal(){
    	
    	return this.energyOfSignal;
    }

}
