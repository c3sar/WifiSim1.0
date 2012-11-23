package Diagram;
import java.util.ArrayList;

public class ObjectPlotted {

	public String direccion;
	public int x;
	public int y;
	private int id;
	private String name;
	private  ArrayList<Diagram> elementsPlotted = new ArrayList();
    
    public ObjectPlotted(String direccion,String name){

		this.direccion=direccion;
		this.name=name;
	}	
	
	public void addElement(Diagram ele){
		
		Diagram lastElementPlotted;
		
		if(elementsPlotted.size()>0){
		
			lastElementPlotted = this.elementsPlotted.get(this.elementsPlotted.size()-1);
			if(lastElementPlotted.Type.equals(ele.Type)){
				lastElementPlotted.final_time=ele.final_time;
			}		
			else{
				ele.initial_time = lastElementPlotted.final_time+1;
				this.elementsPlotted.add(ele);
			}
		}
		else{
		   ele.initial_time = 0;
		   this.elementsPlotted.add(ele);
		}	
		
	}
	
	public void removeLastElement(){//solo va a remover un difs ultimo ke es le ke da problemas.
		if(
		   this.elementsPlotted.get(this.elementsPlotted.size()-1).getType().equals("spend_dif")
		   ){
			this.elementsPlotted.remove(this.elementsPlotted.size()-1);
		}
	}
	
	public ArrayList  getElementsPlotted(){		
		return elementsPlotted;
	}
	public String getName(){
		return name;
	}
}
