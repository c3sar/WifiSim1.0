package ElementsForSim;

public class AssociationResponse extends Frame{

	float rate_NodeToAP;
	
	public AssociationResponse(String type, int origin, int destiny,int numberOfFrame, int momentThatIsCreated, int frameSize) {
		super(type, origin, destiny, numberOfFrame, momentThatIsCreated, frameSize);
	}
	public void setRate_NodeToAP(float rate){
		this.rate_NodeToAP = rate;
	}
	public float getRate_NodeToAp(){
		return this.rate_NodeToAP;
	}
}
