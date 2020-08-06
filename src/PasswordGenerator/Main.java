package PasswordGenerator;
import javax.swing.*;

import java.awt.event.*;

public class Main extends JFrame {

	public static void main(String[] args) {
		new Main(winTitle);
	}
	
	private static final long serialVersionUID = -7381740107971603050L; // unique id
	private static final String winTitle = "Password Generator"; // Window Title
	private static final int winSize[] = {280, 414}; // Window Size
	
	private JButton btnGenerate; // Button to generate
	private JButton btnClear; // Button to clear previous generated passwords
	private JTextArea txtOutput; // Text Area to display passwords
	private JTextField txtbxPwLength; // Text Field to choose a password length
	private JCheckBox ckbxSymbols; // Check Box to choose whether or not to use symbols
	private JLabel lblLength;
	private JLabel lblMultipl;
	private JComboBox<Integer> cbxMultiplicator; // Combo Box to choose num of pws to gen
	private JScrollPane scrollOutput; // Scroll Pane -> txtOutput
	private PwGen pwGen = new PwGen(); // instance of Password Generator Class
	
	public Main(String arg0) {
		super(arg0);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		InitUI();
		this.setVisible(true);
	}
	
	// Init the UI and all of its components including inline ActionListener,
	// except for the window title
	private void InitUI() {
		this.setSize(winSize[0], winSize[1]);
		this.setResizable(false);
		this.setLayout(null);
		
		//##########################################################
		btnGenerate = new JButton();
		btnGenerate.setText("Generate");
		btnGenerate.setSize(120, 75);
		btnGenerate.setLocation(137, 279);
		btnGenerate.addActionListener( new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						int length;
						try {
							length = Integer.parseInt(txtbxPwLength.getText());
						} catch (NumberFormatException ex) { length = 0; }
						
						String text;
						for (int i = 0; i < cbxMultiplicator.getSelectedIndex()+1; i++) {
							try {
								text = txtOutput.getText();
							} catch (NullPointerException ex) { text = ""; }
							txtOutput.setText(text + pwGen.Generate(length, ckbxSymbols.isSelected()) + "\n");
						}
					}
				});
		this.add(btnGenerate);
			
		//##########################################################
		btnClear = new JButton();
		btnClear.setText("Clear");
		btnClear.setSize(btnGenerate.getWidth(), 15);
		btnClear.setLocation(btnGenerate.getX(), btnGenerate.getY() + btnGenerate.getHeight());
		btnClear.addActionListener( new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent e) {
						txtOutput.setText("");
					}
				});
		this.add(btnClear);
		
		//##########################################################
		txtOutput = new JTextArea();
		
		scrollOutput = new JScrollPane(txtOutput,
				   JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				   JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollOutput.setSize(this.getWidth()-30, this.getHeight()-150);
		scrollOutput.setLocation(8, 10);
		this.add(scrollOutput);
		
		//##########################################################
		ckbxSymbols = new JCheckBox();
		ckbxSymbols.setSize(120, 20);
		ckbxSymbols.setLocation(9, this.getHeight()-100);
		ckbxSymbols.setText("Use Symbols");
		this.add(ckbxSymbols);
		
		//##########################################################
		txtbxPwLength = new JTextField();
		txtbxPwLength.setSize(35, 18);
		txtbxPwLength.setLocation(59, this.getHeight()-125);
		txtbxPwLength.setText("10");
		this.add(txtbxPwLength);
		
		//##########################################################
		lblLength = new JLabel();
		lblLength.setSize(80, 20);
		lblLength.setLocation(12, this.getHeight()-127);
		lblLength.setText("Length:");
		this.add(lblLength);
		
		//##########################################################
		lblMultipl = new JLabel();
		lblMultipl.setSize(20, 20);
		lblMultipl.setLocation(14, this.getHeight()-70);
		lblMultipl.setText("x");
		this.add(lblMultipl);
		
		//##########################################################
		cbxMultiplicator = new JComboBox<Integer>();
		cbxMultiplicator.setSize(62, 20);
		cbxMultiplicator.setLocation(24, this.getHeight()-70);
		for (int i = 1; i < 1001; i++) {
			cbxMultiplicator.addItem(i);
		}
		this.add(cbxMultiplicator);

	}
	
}
