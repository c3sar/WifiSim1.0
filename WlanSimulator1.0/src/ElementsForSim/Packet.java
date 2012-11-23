package ElementsForSim;

public class Packet {

	private int Origin;
	private int Destiny;
	private String Type;
	private int size;
	
	public Packet(String type,int Origin,int Destiny){
		
		//type : RTS, CLS , ACK ,Data 
		this.Type=type;
		this.Origin=Origin;		
		this.Destiny=Destiny;
		this.size=0;
	}
	public String getType(){
		return Type;
	}
	
	public int getOrigin(){
		
	     return Origin;
	}
	public int getDestiny(){
		
		return Destiny;
	}
}
