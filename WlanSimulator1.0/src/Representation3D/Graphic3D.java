package Representation3D;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JPanel;


public class Graphic3D extends JPanel{
	
	
	    public   Vector	V;
		public   Vector	W; 
		int		   A = 35;
		public int C =-135;
		int B=0, r = 450, d =  3000, f = 5000;//d = 3000
		public int G=5;
		public int th = 210;//240;
		public int tv = 210;
		int At=0, Bt=0, Ct=0, indexSeleccionado=0;
		//pasamos de grados a radianes...
		double	Alfa = A*Math.PI/180, Beta = B*Math.PI/180;
		public double Theta = C*Math.PI/180;
		double Alfat = At*Math.PI/180, Betat = Bt*Math.PI/180, Thetat = Ct*Math.PI/180;
		public double amp = 150;
		double h = 0, k = 0, l = 0;
		
		
	public Graphic3D(){
		
		V = new Vector();
		W = new Vector(); 
	}
		
	public void paint(Graphics g) {				
			
			
			double Xx=0, Yx=0, Xy=0, Yy=0, Xz=0, Yz=0, X=0, Y=0, xx=0, yy=0, zz=0;
			
			Line l = new Line(0,0);
			
			Point3D pi,pf;
			
			Point2D Pi,Pf;
			
			
			this.setBorder(getBorder());
			g.drawRect(0,0,640,480);
			g.setColor(Color.red);
			
			
			//---------------------------DIBUJA LOS EJES COORDENADOS---------------------------
			
				
				X = (d*r*Cos(Theta))/(d+f+r*Sen(Theta)*Cos(Alfa));
				Y = (d*r*Sen(Theta)*Sen(Alfa))/(d+f+r*Sen(Theta)*Cos(Alfa));
				Xx = X*Cos(Beta)-Y*Sen(Beta);
				Yx = X*Sen(Beta)+Y*Cos(Beta);
				g.drawLine(th,tv,th+(int)Xx,tv-(int)Yx);
				g.drawString("X", th+(int)Xx+(int)(10*Xx/Math.sqrt(Xx*Xx+Yx*Yx)), tv-(int)Yx-(int)(10*Yx/Math.sqrt(Xx*Xx+Yx*Yx)));
		
				X = (-d*r*Sen(Theta))/(d+f+r*Cos(Theta)*Cos(Alfa));
				Y = (d*r*Cos(Theta)*Sen(Alfa))/(d+f+r*Cos(Theta)*Cos(Alfa));
				Xy = X*Cos(Beta)-Y*Sen(Beta);
				Yy = X*Sen(Beta)+Y*Cos(Beta);
				g.drawLine(th,tv,th+(int)Xy,tv-(int)Yy);
				g.drawString("Y", th+(int)Xy+(int)(10*Xy/Math.sqrt(Xy*Xy+Yy*Yy)), tv-(int)Yy-(int)(10*Yy/Math.sqrt(Xy*Xy+Yy*Yy)));

				X = 0;
				Y = (d*r*Cos(Alfa))/(d+f-r*Sen(Alfa));
				Xz = X*Cos(Beta)-Y*Sen(Beta);
				Yz = X*Sen(Beta)+Y*Cos(Beta);
				g.drawLine(th,tv,th+(int)Xz,tv-(int)Yz);
				g.drawString("Z", th+(int)Xz+(int)(10*Xz/Math.sqrt(Xz*Xz+Yz*Yz)), tv-(int)Yz-(int)(10*Yz/Math.sqrt(Xz*Xz+Yz*Yz)));
			
			//---------------------------------------------------------------------------------
				Point3D p;
				Point2D P;
				Line l1;
				
				for(int i=0; i<V.size(); i++){
					
					p = (Point3D)V.elementAt(i);
					
					P = pto3Da2D(traslacionPtos(p));
					
					
					
					if(p.c==null){
						
						g.setColor(Color.BLUE);	
					}
					else{
						
						g.setColor(p.c);
					}
					
					g.drawLine(th+(int)P.x,tv-(int)P.y,th+(int)P.x,tv-(int)P.y);
					
					if(p.c!=null){
						g.drawOval(th+(int)P.x, tv-(int)P.y, 1, 1);						
						g.drawOval(th+(int)P.x, tv-(int)P.y, 2, 2);
						g.drawOval(th+(int)P.x, tv-(int)P.y, 3, 3);						
						g.drawOval(th+(int)P.x, tv-(int)P.y, 4, 4);
						g.drawOval(th+(int)P.x, tv-(int)P.y, 5, 5);
						g.setColor(Color.black);
						g.drawOval(th+(int)P.x, tv-(int)P.y, 6, 6);
					}
					else{
						g.drawOval(th+(int)P.x, tv-(int)P.y, 3, 3);
					}			
					
					g.setColor(Color.gray);
					g.drawString(p.Nombre+"",(th+5)+(int)P.x,(tv-5)-(int)P.y);
				}
			
				Point3D p_i,p_f;
				Point2D P_i,P_f;
				
				for(int i=0; i<W.size(); i++){
					
					l1 = (Line2)W.elementAt(i);
					p_i = l1.pi;
					p_f = l1.pf;
					
					P_i = pto3Da2D(traslacionPtos(p_i));
					P_f = pto3Da2D(traslacionPtos(p_f));
					Color c = ((Line2)l1).getColor();
					g.setColor(c);
					g.drawLine(th+(int)P_i.x, tv-(int)P_i.y, th+(int)P_f.x, tv-(int)P_f.y);
				}
				
		}
	
   //--------------------------------------------------------------------------------------
	
	public void AddPoint3D_toGraphic(String nombre,double x,double y,double z){
		
		Point3D p = new Point3D(x,y,z);		
		
		
		p.Nombre = nombre;
		
		V.addElement(p);

	}
	
	public void AddPoint3D_toGraphic(String nombre,double x,double y,double z,Color color){
		
		Point3D p = new Point3D(x,y,z);		
	
		p.Nombre = nombre;
	
		p.c = color;
		
		V.addElement(p);

	}
	
	//--------------------------------------------------------------------------------------
	
	public void AddLine_toGraphic3D(double xi,double yi,double zi,double xf,double yf,double zf,char type,Color colour){
		
		Point3D pi=new Point3D(xi,yi,zi);
		Point3D pf=new Point3D(xf,yf,zf);
		W.add(new Line2(pi,pf,type,colour));
	 }	

	//--------------------------------------------------------------------------------------
	 double Sen(double x){
		return Math.sin(x);
	}
    //--------------------------------------------------------------------------------------
	 double Cos(double x){
		return Math.cos(x);
	}
    //--------------------------------------------------------------------------------------			
   private Point2D pto3Da2D(Point3D p){//pasa a perspectiva isometrica
		//  La transformación de coordenadas cartesianas se utiliza para calcular las vistas a 
	   // partir de las coordenadas de los puntos
		Point2D P;
		double Radio, Ang, Xp, Yp, X, Y;
		
		if(p.x==0){
			if(p.y>0) Ang = Math.PI/2;
			else Ang = 3*Math.PI/2;
		}
		else{
			if(p.x<0) Ang = Math.atan(p.y/p.x)+Math.PI;
			else Ang = Math.atan(p.y/p.x);
		}
		
		Radio = amp*Math.sqrt(p.x*p.x+p.y*p.y);

		X = (d*Radio*Cos(Theta+Ang))/(d+f+Radio*Sen(Theta+Ang)*Cos(Alfa)-p.z*amp*Sen(Alfa));
		Y = (d*p.z*amp*Cos(Alfa)+d*Radio*Sen(Theta+Ang)*Sen(Alfa))/(d+f+Radio*Sen(Theta+Ang)*Cos(Alfa)-p.z*amp*Sen(Alfa));
		Xp = X*Cos(Beta)-Y*Sen(Beta);
		Yp = X*Sen(Beta)+Y*Cos(Beta);
		
		P = new Point2D(Xp,Yp);
		return P;
	}
    //---------------------------------------------------------------------------------------	
   private Point3D traslacionPtos(Point3D p){
	
		if(selectedPoint(p)){
			
			double xx,yy,zz;
			Point3D r = new Point3D();
		
			xx = h + p.x*Cos(Thetat)*Cos(Betat) - p.y*(Sen(Alfat)*Sen(Betat)*Cos(Thetat)+Sen(Thetat)*Cos(Alfat)) + p.z*(Sen(Alfat)*Sen(Thetat)-Sen(Betat)*Cos(Alfat)*Cos(Thetat));
			yy = k + p.x*Sen(Thetat)*Cos(Betat) + p.y*(Cos(Alfat)*Cos(Thetat)-Sen(Alfat)*Sen(Betat)*Sen(Thetat)) - p.z*(Sen(Alfat)*Cos(Thetat)+Sen(Thetat)*Sen(Betat)*Cos(Alfat));
			zz = l + p.x*Sen(Betat) + p.y*Sen(Alfat)*Cos(Betat) + p.z*Cos(Alfat)*Cos(Betat);
			
			r.Numero = p.Numero;
			r.x = xx;
			r.y = yy;
			r.z = zz;
			
			return r;
		}
		else
			return p;
    }
  //--------------------------------------------------------------------------------------
	boolean selectedPoint(Point3D p){
		
		return false;
	}
  //--------------------------------------------------------------------------------------
	
	public void Reset(){
		
		  	V = new Vector();  
			W = new Vector();
			A = 35;
			C =-135;
			B=0;
			r = 450;
			d =  3000;
			f = 5000;//d = 3000
			G=5;
			th = 210;//240;
			tv = 210;
			At=0;
			Bt=0;
			Ct=0;
			indexSeleccionado=0;
			
			//pasamos de grados a radianes...
			Alfa = A*Math.PI/180;
			Beta = B*Math.PI/180;
			Theta = C*Math.PI/180;
			Alfat = At*Math.PI/180;
			Betat = Bt*Math.PI/180;
			Thetat = Ct*Math.PI/180;
			amp = 150;
			double h = 0, k = 0, l = 0;
	}


	public void autoZoom(){	
			
		Point3D p;
		Point2D Punto2D;
		Boolean estaDentro=false;
		Boolean repetir=false;
		do{
			repetir = false;
				for(int i=0; i<V.size(); i++){
					
					p = (Point3D)V.elementAt(i);			
					Punto2D = pto3Da2D(traslacionPtos(p));			
					
					//comprobamos si el punto añadido a la lista se encuentra dentro del marco
					//tamaño del panel(marco)->x=379,y=311
					
					int aux1 = th+(int)Punto2D.x;
					int aux2 = tv-(int)Punto2D.y;
					
					if(   th+(int)Punto2D.x>20
					   && th+(int)Punto2D.x<=300){
						  
						  if(   tv-(int)Punto2D.y>20 
						     && tv-(int)Punto2D.y<=300){
					
							  estaDentro = true;
				          }else{
							  estaDentro = false;	
					      }
					}
					else{
						estaDentro = false;
					}
					
					
						
					if(!estaDentro){
						
						amp-=1;//esto hará que la supuesta camara se aleje...	
						repetir = true;
						break;
					}
					
				}
		}while(repetir);
		
		
	}
		
	
		
}