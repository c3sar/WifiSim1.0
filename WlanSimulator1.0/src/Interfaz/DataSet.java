package Interfaz;

import java.util.ArrayList;

import ElementsForSim.AccessPoint;
import ElementsForSim.Node;

public class DataSet {

	
	private int SimulationTime;
	private String InfraestructureMode;
	private String FisicalLayer;
	private float Speed;
	private ArrayList<AccessPoint> ArrayOfAPs;
	private ArrayList<Node> ArrayOfNodes;
	
	public void setArrayOfNodes(ArrayList<Node> ArrayOfNodes){
		this.ArrayOfNodes=ArrayOfNodes;
	}
	
	public void setArrayOfAPs(ArrayList<AccessPoint> ArrayOfAPs){
		this.ArrayOfAPs=ArrayOfAPs;
	}
	
	public ArrayList getArrayOfNodes(){
		
		return this.ArrayOfNodes;
	}
	
	public ArrayList getArrayOfAPs(){
		
		return this.ArrayOfAPs;
	}
	
	public int getSimulationTime() {
		return SimulationTime;
	}
	
	public void setSimulationTime(int simulationTime) {
		SimulationTime = simulationTime;
	}
	
	public String getInfraestructureMode() {
		return InfraestructureMode;
	}
	
	public void setInfraestructureMode(String infraestructureMode) {
		InfraestructureMode = infraestructureMode;
	}
	
	public String getFisicalLayer() {
		return FisicalLayer;
	}
	
	public void setFisicalLayer(String fisicalLayer) {
		FisicalLayer = fisicalLayer;
	}
	
	public float getSpeed() {
		return Speed;
	}
	
	public void setSpeed(float speed) {
		Speed = speed;
	}

}
