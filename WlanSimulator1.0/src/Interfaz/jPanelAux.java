package Interfaz;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class jPanelAux extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Color colors[] = {
	        Color.red, Color.blue, Color.green, Color.orange,
	        Color.cyan, Color.magenta, Color.darkGray, Color.yellow};
	    
	private JPanel  jPanel = null;
	private JPanel  jPanel1 = null;
	private JPanel  jPanel2 = null;
	private JButton jButton = null;
	private JButton jButton1 = null;
	private JButton jButton2 = null;
	private JButton jButton3 = null;
	private JButton jButton4 = null;
	private JButton jButton5 = null;
	private JCheckBox jCheckBox = null;
	private JLabel jLabel = null;
	private JPanel jPanel_ColorDif = null;
	private JLabel jLabel_ColorDif = null;
	private JButton jButton6 = null;
	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.setBounds(new Rectangle(12, 14, 795, 262));
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.setBounds(new Rectangle(819, 16, 381, 254));
		}
		return jPanel2;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(807, 298, 77, 16));
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(886, 307, 77, 16));
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(886, 288, 77, 16));
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(965, 298, 77, 16));
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(1064, 285, 95, 16));
			jButton4.setText("Rot.Left");
		}
		return jButton4;
	}

	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(new Rectangle(1064, 305, 95, 16));
		}
		return jButton5;
	}

	/**
	 * This method initializes jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox() {
		if (jCheckBox == null) {
			
			jCheckBox = new JCheckBox();
			jCheckBox.setBounds(new Rectangle(816, 332, 26, 22));			
		}
		return jCheckBox;
	}

	/**
	 * This method initializes jPanel_ColorDif	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_ColorDif() {
		if (jPanel_ColorDif == null) {
			jPanel_ColorDif = new JPanel();
			jPanel_ColorDif.setLayout(new GridBagLayout());
			jPanel_ColorDif.setBounds(new Rectangle(12, 289, 19, 13));
			jPanel_ColorDif.setBackground(colors[1]);
		}
		return jPanel_ColorDif;
	}

	/**
	 * This method initializes jButton6	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton6() {
		if (jButton6 == null) {
			jButton6 = new JButton();
			jButton6.setBounds(new Rectangle(1036, 389, 137, 20));
			jButton6.setText("view troughput");
		}
		return jButton6;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * This is the default constructor
	 */
	public jPanelAux() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel_ColorDif = new JLabel();
		jLabel_ColorDif.setBounds(new Rectangle(45, 286, 105, 20));
		jLabel_ColorDif.setText("DIF");
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(846, 333, 95, 26));
		jLabel.setText("Mark situation");
		GridLayout gridLayout = new GridLayout();
		gridLayout.setRows(1);
		this.setLayout(null);
		this.setSize(1221, 557);		
		this.add(getJPanel1(), null);
		this.add(getJPanel2(), null);
		this.add(getJButton(), null);
		this.add(getJButton1(), null);
		this.add(getJButton2(), null);
		this.add(getJButton3(), null);
		this.add(getJButton4(), null);
		this.add(getJButton5(), null);
		this.add(getJCheckBox(), null);
		this.add(jLabel, null);
		this.add(getJPanel_ColorDif(), null);
		this.add(jLabel_ColorDif, null);
		this.add(getJButton6(), null);
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
