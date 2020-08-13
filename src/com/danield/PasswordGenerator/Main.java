/**
 * Program Entry Point & Window
 */
package com.danield.PasswordGenerator;
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

/**
 * @author Daniel D
 * @version 0.2
 */
public class Main extends JFrame {
	
	/**
	 * Entry Point of the Application
	 * @param args
	 */
	public static void main(String[] args) {
		new Main(winTitle);
	}
	
	private static final long serialVersionUID = -7381740107971603050L; // unique id
	private static final String winTitle = "Password Generator"; // Window Title
	private static final int winSize[] = {280, 414}; // Window Size
	
	private boolean darkModeIsOn = true; // flag for ui color scheme
	private String cachedOutput = ""; // store txtOutput-Text, when switch color scheme
	private String cachedLength = "10"; // store txtbxLength-Text, when switch color scheme
	private boolean cachedSym = false; // store whether or not ckbxSymbols is checked, when switch color scheme
	private int cachedMultiplier = 0; // store index of ckbxMultiplier, when switch color scheme
	
	private JButton btnGenerate; // Button to generate
	private JButton btnClear; // Button to clear previous generated passwords
	private JTextArea txtOutput; // Text Area to display passwords
	private JTextField txtbxLength; // Text Field to choose a password length
	private JCheckBox ckbxSymbols; // Check Box to choose whether or not to use symbols
	private JLabel lblLength;
	private JLabel lblMultiplier;
	private JComboBox<Integer> cbxMultiplier; // Combo Box to choose num of pws to gen
	private JScrollPane scrollOutput; // Scroll Pane -> txtOutput
	private JButton btnSwitchLDMode; // Button to switch between light and dark mode
	private PwGen pwGen = new PwGen(); // instance of Password Generator Class
	
	/**
	 * Constructor
	 *@param arg0 - Window Title
	 */
	public Main(String arg0) {
		super(arg0);
		this.setSize(winSize[0], winSize[1]);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		initUI();
		this.setVisible(true);
	}
	
	/**
	 * Initialize the UI and all of its components including inline ActionListener
	 */
	private void initUI() {
		this.getContentPane().removeAll();
		this.getContentPane().revalidate();
		if (darkModeIsOn) {
			this.getContentPane().setBackground(Color.DARK_GRAY);
		}
		else {
			this.getContentPane().setBackground(Color.LIGHT_GRAY);
		}
		this.setLayout(null);
		
		//##########################################################
		btnGenerate = new JButton("Generate");
		if (darkModeIsOn) {
			btnGenerate.setBackground(Color.DARK_GRAY);
			btnGenerate.setForeground(Color.LIGHT_GRAY);
		}
		else {
			btnGenerate.setBackground(Color.LIGHT_GRAY);
		}
		btnGenerate.setSize(120, 75);
		btnGenerate.setLocation(137, 279);
		btnGenerate.addActionListener( new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						int length;
						try {
							length = Integer.parseInt(txtbxLength.getText());
						} catch (NumberFormatException ex) { length = 0; }
						
						String text;
						for (int i = 0; i < cbxMultiplier.getSelectedIndex()+1; i++) {
							try {
								text = txtOutput.getText();
							} catch (NullPointerException ex) { text = ""; }
							txtOutput.setText(text + pwGen.Generate(length, ckbxSymbols.isSelected()) + "\n");
						}
					}
				});
		this.getContentPane().add(btnGenerate);
			
		//##########################################################
		btnClear = new JButton("Clear");
		if (darkModeIsOn) {
			btnClear.setBackground(Color.DARK_GRAY);
			btnClear.setForeground(Color.LIGHT_GRAY);
		}
		else {
			btnClear.setBackground(Color.LIGHT_GRAY);
		}
		btnClear.setFocusable(false);
		btnClear.setSize(btnGenerate.getWidth(), 15);
		btnClear.setLocation(btnGenerate.getX(), btnGenerate.getY() + btnGenerate.getHeight());
		btnClear.addActionListener( new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						txtOutput.setText("");
					}
				});
		this.getContentPane().add(btnClear);
		
		//##########################################################
		txtOutput = new JTextArea(cachedOutput);
		if (darkModeIsOn) {
			txtOutput.setBackground(Color.LIGHT_GRAY);
		}
		
		scrollOutput = new JScrollPane(txtOutput,
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				   JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollOutput.setSize(this.getWidth()-30, this.getHeight()-150);
		scrollOutput.setLocation(8, 10);
		this.getContentPane().add(scrollOutput);
		
		//##########################################################
		ckbxSymbols = new JCheckBox("Use Symbols");
		if (darkModeIsOn) {
			ckbxSymbols.setBackground(Color.DARK_GRAY);
			ckbxSymbols.setForeground(Color.LIGHT_GRAY);
		}
		else {
			ckbxSymbols.setBackground(Color.LIGHT_GRAY);
		}
		ckbxSymbols.setSelected(cachedSym);
		ckbxSymbols.setFocusable(false);
		ckbxSymbols.setSize(102, 20);
		ckbxSymbols.setLocation(9, this.getHeight()-100);
		this.getContentPane().add(ckbxSymbols);
		
		//##########################################################
		txtbxLength = new JTextField(cachedLength);
		if (darkModeIsOn) {
			txtbxLength.setBackground(Color.DARK_GRAY);
			txtbxLength.setForeground(Color.LIGHT_GRAY);
			txtbxLength.setCaretColor(Color.LIGHT_GRAY);
		}
		else {
			txtbxLength.setBackground(Color.LIGHT_GRAY);
		}
		txtbxLength.setSize(52, 18);
		txtbxLength.setLocation(59, this.getHeight()-125);
		this.getContentPane().add(txtbxLength);
		
		//##########################################################
		lblLength = new JLabel("Length:");
		if (darkModeIsOn) {
			lblLength.setForeground(Color.LIGHT_GRAY);
		}
		lblLength.setSize(80, 20);
		lblLength.setLocation(12, this.getHeight()-127);
		this.getContentPane().add(lblLength);
		
		//##########################################################
		lblMultiplier = new JLabel("x");
		if (darkModeIsOn) {
			lblMultiplier.setForeground(Color.LIGHT_GRAY);
		}
		lblMultiplier.setSize(20, 20);
		lblMultiplier.setLocation(14, this.getHeight()-70);
		this.getContentPane().add(lblMultiplier);
		
		//##########################################################
		cbxMultiplier = new JComboBox<Integer>();
		if (darkModeIsOn) {
			cbxMultiplier.setBackground(Color.DARK_GRAY);
			cbxMultiplier.setForeground(Color.LIGHT_GRAY);
		}
		else {
			cbxMultiplier.setBackground(Color.LIGHT_GRAY);
		}
		cbxMultiplier.setFocusable(false);
		cbxMultiplier.setSize(56, 20);
		cbxMultiplier.setLocation(24, this.getHeight()-70);
		for (int i = 1; i < 1001; i++) {
			cbxMultiplier.addItem(i);
		}
		cbxMultiplier.setSelectedIndex(cachedMultiplier);
		this.getContentPane().add(cbxMultiplier);
		
		//##########################################################
		btnSwitchLDMode = new JButton();
		btnSwitchLDMode.setFont(new Font("Tahoma", Font.PLAIN, 10));
		if (darkModeIsOn) {
			btnSwitchLDMode.setBackground(Color.LIGHT_GRAY);
			btnSwitchLDMode.setForeground(Color.DARK_GRAY);
			btnSwitchLDMode.setText("Light");
		}
		else {
			btnSwitchLDMode.setBackground(Color.DARK_GRAY);
			btnSwitchLDMode.setForeground(Color.LIGHT_GRAY);
			btnSwitchLDMode.setText("Dark");
		}
		btnSwitchLDMode.setFocusable(false);
		btnSwitchLDMode.setSize(56, 10);
		btnSwitchLDMode.setLocation(0, 0);
		btnSwitchLDMode.addActionListener( new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						darkModeIsOn = (darkModeIsOn) ? false : true; // set theme flg
						/* cache */
						cachedOutput = txtOutput.getText();
						cachedSym = ckbxSymbols.isSelected();
						cachedLength = txtbxLength.getText();
						cachedMultiplier = cbxMultiplier.getSelectedIndex();
						
						scrollOutput.remove(txtOutput); // remove JTextArea from JScrollPane or encounter bugs
						initUI(); // rebuild the ui
					}
				});
		this.getContentPane().add(btnSwitchLDMode);
	}
	
}
