package gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.plaf.FontUIResource;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.apache.log4j.Logger;
import javax.swing.UIManager;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import controllers.*;
import models.Employee;

/**
* loginWindow Initialises application Login Window
* @author Krzysztof Bas
*/
public class LoginWindow extends JFrame {// Start of Class

	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;
	
	/*       VARIABLES     */
	
	// Logger object used to create logs
	private Logger logger = Logger.getLogger(LoginWindow.class.getName());
	
	private JButton loginButton = new JButton("Login");
	private JButton clearButton = new JButton("Clear");
	private JLabel usernameJLabel = new JLabel("Username");
	private JLabel passwordJLabel =new JLabel("Password");
	private JTextField usernameField =new JTextField();
	private JPasswordField passwordField =new JPasswordField();		
	private JPanel loginJPanel =new JPanel();
	private JPanel buttonPanel =new JPanel();
	
	/**
	 * Constructor for the LoginWindow class
	 */
	public LoginWindow() {//Start of Constructor
		
		logger.info("Starting Login Screen");
		
		initialiseLoginWindow();
		initializeLoginPanel();
		initializeButtonPanel();
			
		getContentPane().add(loginJPanel, BorderLayout.WEST);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	
	}//End of Constructor
	
    /**
     * Initialises layout of login panel window and its components 
     */
	private void initializeLoginPanel() {//Start of Method
		// log info
		logger.info("Initializing Login Panel");
		// create layout
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints cBagConstraints =new GridBagConstraints();
		// add layout to the JPanel
		loginJPanel.setLayout(layout);
		
		// Initialise layout for the login form
		layout.columnWidths = new int[] {35,55,50,40,40,40};
		layout.rowHeights = new int[] {35,55,55};
		
		cBagConstraints.weightx=2;
		cBagConstraints.fill = GridBagConstraints.BOTH;
		cBagConstraints.insets = new Insets(20, 38, 15, 0);
	
		// Insert and position Components using x and y grid coordinates 
		cBagConstraints.gridx=0;
		cBagConstraints.gridy=1;
		loginJPanel.add(usernameJLabel,cBagConstraints);
		
		cBagConstraints.gridx=0;
		cBagConstraints.gridy=2;
		loginJPanel.add(passwordJLabel,cBagConstraints);
		
		cBagConstraints.gridwidth=3;

		cBagConstraints.gridx=1;
		cBagConstraints.gridy=1;
		loginJPanel.add(usernameField,cBagConstraints);

		cBagConstraints.gridx=1;
		cBagConstraints.gridy=2;
		loginJPanel.add(passwordField,cBagConstraints);
		
	}//End of Method
	
    /**
     *  Initialises the layout, verification and action listeners of buttons 
     */
	private void initializeButtonPanel() {//Start of Method
		//log info
		logger.info("Initializing buttons");
		// set layout
		FlowLayout fl_buttonPanel = new FlowLayout(FlowLayout.CENTER,30, 30);
		buttonPanel.setLayout(fl_buttonPanel);
		// add button to the panel
		buttonPanel.add(loginButton);
		//Add action listener to the login button
		loginButton.addActionListener(new ActionListener() {//Start of Action Listener

			public void actionPerformed(ActionEvent e) {
				// Assign field values to the Employee object
				// get password and username
				Employee.setPassword(String.valueOf(passwordField.getPassword()));
				System.err.println(Employee.getPassword());
				Employee.setUsername(usernameField.getText());
				sqlQueries login = new sqlQueries();
				
				// Verify credentials 
				if(login.verifyLogin() == true) {
					logger.info("Correct details entered  ==>> Starting Database APP");
					MainFrame startApp = new MainFrame();
					dispose();
				}
				// Print warning message  
				else {
					//log error
					logger.error("Incorrect details entered");
					JOptionPane.showMessageDialog(null, "Incorrect Details Entered", "Alert", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		// Add button to the button pannel
		buttonPanel.add(clearButton);
		//Add action listener to the button
		clearButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// clear the fields
				usernameField.setText("");
				passwordField.setText("");	
			}
		});
		// set button size
		loginButton.setPreferredSize(new Dimension(100,30));
		clearButton.setPreferredSize(new Dimension(100,30));
		
	}//End of Method
	
	/**
	 * Initialise the properties of the frame 
	 */
	private void initialiseLoginWindow() {// Start of Method
		logger.info("Initialising Login Window");
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(300, 275);
		
	}//End of Method
	
	/**
	 * Initialise the default font properties of the components
	 * @param myFont Font to be selected as default 
	 */
	private static void setFont(FontUIResource myFont) {//Start of Method
		// set font for label and button components
		UIManager.put("Label.font", myFont);
		UIManager.put("Button.font", myFont);
		
	}//End of Method
	
	// Main method that sets the font and creates loginWindow object
	public static void main(String[] args) {// Start of Main Method
		setFont(new FontUIResource(new Font("Arial", Font.BOLD, 17)));
		// Create loginWindow object
		LoginWindow login =new LoginWindow();

	}//End of Main Method
}//End of Class