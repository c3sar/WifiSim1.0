package Interfaz;

import javax.swing.JPanel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import java.awt.Rectangle;
import java.awt.Color;

public class Chart_Panel_ForObstacles extends JPanel{

	private ChartPanel jPanel = null;
	private JFreeChart chart = null;
	private DefaultCategoryDataset dataset = null;

	Chart_Panel_ForObstacles(JFreeChart c){
		
		chart = c;
		initialize();
	}
		
	private void initialize(){
		
		this.setLayout(null);
		this.setSize(646, 221);
		this.add(getJPanel(), null);
	}

	private DefaultCategoryDataset getDataset() {
		if (dataset == null) {
			dataset = new DefaultCategoryDataset();
		}
		return dataset;
	}


	/**
	 * This method initializes chart	
	 * 	
	 * @return org.jfree.chart.JFreeChart	
	 */
	private JFreeChart getChart() {
		if (chart == null) {
			chart = ChartFactory.createLineChart("", " ", "dB.", getDataset(),
					PlotOrientation.VERTICAL, true, true, false);
		}
		return chart;
	}


	/**
	 * This method initializes jPanel	
	 * 	
	 * @return org.jfree.chart.ChartPanel	
	 */
	private ChartPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new ChartPanel(getChart());
			jPanel.setLayout(null);
			jPanel.setBounds(new Rectangle(0,0, 646, 221));
			jPanel.repaint();
			jPanel.updateUI();
		}
		return jPanel;
	}
}  
