package ElementsForSim;

public class BeaconFrame extends Frame{

	Channel channelOfAP;
    float Intensity;	
    float maximumTransmissionSpeed;
    int frameSize;//esta variable esta en bytes.
    
    
	public BeaconFrame(String type,Channel channelOfAP,int Origin,int Destiny,int currentTime,int frameSize,float intens) {
		
		super(type,Origin,Destiny,0,currentTime,WirelessChannel.BeaconFrameSize);
		this.channelOfAP = channelOfAP;
		this.frameSize = frameSize;
		this.Intensity=intens;
	} 
	
	public Channel getReferenceOfAP(){
		
		return this.channelOfAP;
	}
	
	public float getEnergyOfSignal(){
		
		return this.Intensity;
	}
	
	public float getMaximumTransmissionSpeed(){
		
		return this.maximumTransmissionSpeed;
	}
	
	public int getSupportedFrameSize(){
		
		return this.frameSize;
	}
}
