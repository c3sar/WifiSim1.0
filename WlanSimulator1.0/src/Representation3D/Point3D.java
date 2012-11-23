package Representation3D;

import java.awt.Color;

/*Programa creado por Carlos Valdivieso Paredes
  <cvaldivieso27@hotmail.com>
  Lima-Perú
  2005*/
  
public class Point3D {
	
	public int	Numero;	
	public String  Nombre;
	public double	x;
	public double   y;
	public double   z;
	public Color c;

	public Point3D(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;	
	}
	
	public Point3D(){
		x = 0;
		y = 0;
		z = 0;
	}
		
}
