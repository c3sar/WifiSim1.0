package Representation3D;

import java.awt.Color;


import jxl.format.Colour;

public class Line2 extends Line{

	Color colour;
	public char type;
	public Line2(Point3D pi, Point3D pf,char type,Color c) {
		super(pi, pf);
		this.type=type;
		this.colour=c;
	}
	public Color getColor(){
		return colour;
	}
}
