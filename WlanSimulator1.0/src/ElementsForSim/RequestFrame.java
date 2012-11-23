package ElementsForSim;

public class RequestFrame extends Frame{

	float rate_APtoNode;
	
	public RequestFrame(String type, int origin, int destiny,
			int numberOfFrame, int momentThatIsCreated, int frameSize,
			float energyOfSignal) {
		super(type, origin, destiny, numberOfFrame, momentThatIsCreated, frameSize,
				energyOfSignal);
		// TODO Auto-generated constructor stub
	}

	public void setRate_APtoNode(float rate){
		
		this.rate_APtoNode = rate;
	}
    public float getRate_APtoNode(){
    	return this.rate_APtoNode;
    }
}
