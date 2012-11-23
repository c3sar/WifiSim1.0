package Representation3D;

  
public class Line {
	
	int	P1,
	    P2;
	
	public Point3D pi,pf;
	
	public Line(int P1, int P2){
		
		this.P1 = P1;
		this.P2 = P2;
	}	
    public Line(Point3D p1,Point3D p2){
    	
    	pi = p1;
    	pf = p2;
    }
}
