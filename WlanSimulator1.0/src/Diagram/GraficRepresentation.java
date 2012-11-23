package Diagram;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import ElementsForSim.WirelessChannel;

//RECORDAR QUE LA FUNCION PaintarrayOfElementsPlotted DEVUELVE distinto de null si hay ke aumentar el tamaño de jpanel
//... antes de llamar al metodo que pinta hay ke inicializar arrayOfObjectsForPlot con la funcion arrayOfObjectsForPlot

public class GraficRepresentation extends JPanel{
	   
	    private boolean change = false;
		private final Color colors[] = {
									        Color.red,     //0
									        Color.blue,    //1
									        Color.green,   //2
									        Color.orange,  //3
									        Color.cyan,    //4
									        Color.magenta, //5
									        Color.darkGray,//6
									        Color.yellow,  //7
									        Color.PINK,    //8
									        Color.lightGray,//9
									        Color.black,   //10
									        Color.white    //11
								         };
	    
	    private final int color_n = colors.length;	   
	    private static ArrayList arrayOfObjectsForPlot;  
	    private static ArrayList arrayOfElementsPlotted;    	 
	    
	    private static int expandFactor = 1;
	    
	    
	    public GraficRepresentation(){
	 
	    	 addMouseListener ( new MouseAdapter (){
	    		 
	             public void mouseClicked(java.awt.event.MouseEvent e) {
	     	    	if( e.getButton() == java.awt.event.MouseEvent.BUTTON3 )
	     	    	{
	     	    		
	     	    		
	     	    		JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));							
						
						
						int selected = fileChooser.showSaveDialog(null);
						
						
								if (selected == JFileChooser.APPROVE_OPTION)
								{
								   
								   String nameFile = fileChooser.getSelectedFile().getAbsolutePath();
								   
								   if(nameFile != null){
									   
									    File file = new File(nameFile.concat(new String(".jpg")));
										
										//___ESTO ES UNA PRUEBA__
										   
									       Dimension dim = getSize();
										   BufferedImage img = new BufferedImage(dim.width,
												                                 dim.height,
												                                 BufferedImage.TYPE_INT_RGB);
										   Graphics g = img.getGraphics();
										   paint(g);
										   
										    
											String formato = "jpg";

										   // Escribimos la imagen en el archivo.
											try {
												ImageIO.write(img, formato, file);
											} catch (IOException e1) {
												System.out.println("Error de escritura");
											}

										   //_______________________
										
									  											    
									   	}			   
								}	     	    		
 	     	    		
	     	    		
	     	    	}
	     	     }
	           });
	    	 
	    }
	    
	    
	    public void incFactorExpansion(){
	    	
	
	    	expandFactor = expandFactor * 2; 	    	
	    }
	     
	    public void resetFactorExpansion(){
	    	
	    	expandFactor = 1;
	    }
	    
        public void decFactorExpansion(){
        	
        	if(expandFactor>1){
        		expandFactor = expandFactor/2; 
        	}
	    }
	    public int getFactorExpansion(){
	    	
	    	return expandFactor; 
	    }
	    
	    public void Reset(){
	    	
	    	arrayOfObjectsForPlot = new ArrayList();
	    	arrayOfElementsPlotted = new ArrayList();
	    }
	    
    	protected String paintPaintarrayOfElementsPlotted(Graphics g,ArrayList arrayOfElementsPlotted,int x,int y){
    		
    		//.......
        	x+=50;
        	y+=35;
        	//.......
        	int finalX = 0;
        	int finalY = 0;
        	
        	int Long = 0;
        	int alto = 0;
        	
        	for(int j=0;j<arrayOfElementsPlotted.size();j++){
        		
        		String type = ((Diagram)arrayOfElementsPlotted.get(j)).getType();
        		
        		if(type.equals("inactive_wait")){
        			
        			g.setColor(colors[6]);//darkgray
                    alto=1;
        			
        		}else if(type.equals("spend_dif")){
        			
        			g.setColor(colors[1]);//
                    alto = 20; 
        			 
        		}
        	    else if(type.equals("spend_sif")){
        	    	
        	    	g.setColor(colors[7]);//
                    alto = 20;
        				 
        	    }
        	    else if(type.equals("transmiting")){
        	    	
        	    	g.setColor(colors[2]);//g.setColor(colors[0]);//
                    alto = 20;                    
                   
        	    }
        	    else if(type.equals("transmiting_ACK")){
        	    	
        	    	g.setColor(colors[6]);//
                    alto = 20;
                    
        	    }
        	    else if(type.equals("transmiting_RTS")){
        	    	
        	    	g.setColor(colors[3]);//
                    alto = 20;
                        
        	    }
        	    else if(type.equals("transmiting_CTS")){
        	    	
        	    	g.setColor(colors[5]);//
                    alto = 20;
 
        	    }
        	    else if(type.equals("spend_backoff")){
        	    	
        	    	g.setColor(colors[0]);//g.setColor(colors[2]);//
                    alto = 20;
                    
        	    }        		
        	    else if(type.equals("transmiting_BeaconFrame")){
        	    	
        	    	g.setColor(colors[8]);//
                    alto = 20;                                     
     
        	    }   
        	    else if(type.equals("transmiting_RequestFrame")){
        	    	
        	    	g.setColor(colors[4]);//
                    alto = 20;
        	    }   
        	    else if(type.equals("transmiting_RespFrame")){
        	    	
        	    	g.setColor(colors[9]);
                    alto = 20;
        	    }  
        	    else if(type.equals("time-out")){
        	    	
        	    	g.setColor(colors[10]);
                    alto = 1;
        	    } 
			    else if(type.equals("RTS_NAV")){			        	    	
			         
			         alto = 1;		     
			    }
			    else if(type.equals("CTS_NAV")){			  	
			  	    
			         alto = 1;            
			    }
        		
        	   		      		
        		Long = ((Diagram)arrayOfElementsPlotted.get(j)).final_time - ((Diagram)arrayOfElementsPlotted.get(j)).initial_time+1; 
        		
        		Long =  Long*expandFactor;   			
    			   		
     		
    			if(type.equals("time-out")){
    				
    				boolean draw = true;
    				for(int k=x;k<x+Long;k++){
    					
    					if(draw)g.setColor(colors[10]);
    					else g.setColor(colors[11]);
    						
    					g.fillRect(    			
    	                			k,
    	                			y-alto,
    	                			k+5,
    	                			alto
    	                		   );
    					k++;
    					
    					if(draw==true)draw=false;
    					else draw=true;
    				}
    				g.setColor(colors[10]);
    			}
    			else if(type.equals("spend_backoff")){
    						
    				g.fillRect(    			
                    		x,
                    		y-alto,
                    		Long,
                    		alto);
                   			
    				int initialTime = ((Diagram)arrayOfElementsPlotted.get(j)).initial_time;
    				int finalTime = ((Diagram)arrayOfElementsPlotted.get(j)).final_time;
    				int duration = finalTime - initialTime; 
    				
    				int numberOfSlots = (duration) /WirelessChannel.timeForSlot;
    				int rest =  duration % WirelessChannel.timeForSlot;
    				
 
    				if(numberOfSlots!=0){
    					
    					int LongSlot = WirelessChannel.timeForSlot * expandFactor;
        				int ancho = 1;
        				
        				for(int k=1;k<=numberOfSlots;k++){
        					
        				int starSeparation = x + (k * LongSlot);
        				
        				
        				g.setColor(Color.black);        				
    					g.fillRect(    			
    	                		starSeparation-ancho+(1*expandFactor),
    	                		y-alto,
    	                		ancho,
    	                		alto);
        				}
    					
    				}				
    			}
    			else if(type.equals("RTS_NAV")){
    				
    				g.setColor(Color.BLACK);
    				
    				if(change){//abajo
    					g.drawString("NAV(RTS)",x,y+10);    		        	
    		        }
    		        else{//arriba
    		        	g.drawString("NAV(RTS)",x,y-3);
    		        	       		        
    		        }			
    				
    				
    				g.setColor(colors[3]);
    				g.fillRect(    			
                    		x,
                    		y-alto,
                    		Long,
                    		alto);
    			}
    			else if(type.equals("CTS_NAV")){
    				
    				g.setColor(Color.BLACK);   				
    				
    				if(change){//abajo
    					g.drawString("NAV(CTS)",x,y+10);    		        	
    		        }
    		        else{//arriba
    		        	g.drawString("NAV(CTS)",x,y-3);
    		        	       		        
    		        }	
    				g.setColor(colors[5]); 
    				g.fillRect(    			
                    		x,
                    		y-alto,
                    		Long,
                    		alto);
    			}
    			else{
    				g.fillRect(    			
                		x,
                		y-alto,
                		Long,
                		alto);
               
    			}
    			
    			
    			
    			
                x = x+Long;
        		
        		
        		    String aux = String.valueOf( ((Diagram)arrayOfElementsPlotted.get(j)).final_time );
        		   
        		    if(!aux.equals("0")){
        		        if(change){ //abajo
        		        	g.drawString(aux,x-5,y+10);
        		        	change=false;
        		        }
        		        else{       //arriba
        		        	g.drawString(aux,x-5,y-23);
            		        change=true;
        		        }
        		    }        		            		    
        		    
        		    finalX = x;
        	}  
        	change=false;
        	return null;
    	}
    		
    	public void setArrayOfObjectsForPlot(ArrayList array){
    		
    		arrayOfObjectsForPlot = array;
    	}
    		
        protected void paintComponent(Graphics g){
            
        	super.paintComponent(g);
            
            for (int i = 0; i < arrayOfObjectsForPlot.size(); i++){   	
            	
            	
            	int x = ((ObjectPlotted)arrayOfObjectsForPlot.get(i)).x; //posicion inicial x de la imagen que representa al nodo o al AP
            	int y = ((ObjectPlotted)arrayOfObjectsForPlot.get(i)).y; //posicion inicial y ...
            	
            	
            		Image img = new ImageIcon(((ObjectPlotted)arrayOfObjectsForPlot.get(i)).direccion).getImage();
            		
            		g.drawImage(img, x, y, this);
            		//g_FromImgForSave.drawImage(img, x, y, this);
            		
            		Font f = new Font("Times Roman", Font.BOLD + Font.ITALIC, 12);
            		g.setFont(f);
            		//g_FromImgForSave.setFont(f);
            		
            		g.setColor(colors[6]);//darkgray
            		//g_FromImgForSave.setColor(colors[6]);//darkgray
            		
            		g.drawString(((ObjectPlotted)arrayOfObjectsForPlot.get(i)).getName(), x, y+48);
            		//g_FromImgForSave.drawString(((ObjectPlotted)arrayOfObjectsForPlot.get(i)).getName(), x, y+48);
            		
            	//por cada nodo o array , dibujamos todos los elementos 
            	//que contienen ... 
            	
            	arrayOfElementsPlotted = ((ObjectPlotted)arrayOfObjectsForPlot.get(i)).getElementsPlotted();
            
            	paintPaintarrayOfElementsPlotted(g,arrayOfElementsPlotted,x,y);
     	        
            }            
        }       
}
    