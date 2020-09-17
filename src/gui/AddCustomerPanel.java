package gui;

import org.apache.log4j.Logger;
import java.awt.Dimension;
import  javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import models.Customer;
import controllers.sqlQueries;
/**
* AddCustomerPanel Initialises Customer Panel
* @author Krzysztof Bas
*/
	public class AddCustomerPanel extends JPanel{//End of Method
	/**
	 * Version 
	 */
	private static final long serialVersionUID = 1L;


	/*       VARIABLES     */
	
	// Logger object used to create logs
	private Logger logger = Logger.getLogger(AddCustomerPanel.class);
	
	private String County[] = {"","Antrim","Armagh","Carlow","Cavan","Clare","Cork"
								,"Derry","Donegal","Down","Dublin","Fermanagh"
								,"Galway","Kerry","Kildare","Kilkenny","Laois"
								,"Leitrim","Limerick","Longford","Louth","Mayo"
								,"Meath","Monaghan","Offaly","Roscommon","Sligo"
								,"Tipperary","Tyrone","Waterford","Westmeath"
								,"Wexford","Wicklow"};
			

	private JPanel formPanel =new JPanel();
	private JPanel buttonJPanel =new JPanel();
	private JPanel leftouterJPanel = new JPanel();
	private JPanel customerJPanel =new JPanel();

	private JButton addButton =new JButton("Add");
	private JButton editButton =new JButton("Edit");
	private JButton deleteButton =new JButton("Delete");
	private JButton clearButton = new JButton("Clear");
	
	private JLabel customerIDLabel =new JLabel("Customer ID");
	private JLabel nameJLabel = new JLabel("Name");
	private JLabel surnameJLabel = new JLabel("Surname");
	private JLabel ageJLabel = new JLabel("Date of Birth");
	private JLabel genderJLabel = new JLabel("Gender");
	private JLabel streetJLabel = new JLabel("Street");
	private JLabel townJLabel = new JLabel("Town");
	private JLabel countytJLabel = new JLabel("County");
	private JLabel phoneJLabel = new JLabel("Phone");
	private JLabel emailJLabel = new JLabel("E-mail");
	
	private JTextField nameField = new JTextField();
	private JTextField surnameField = new JTextField();
	private JTextField streeField = new JTextField();
	private JTextField townField = new JTextField();
	private JTextField phoneField = new JTextField();
	private JTextField emailField = new JTextField();
	private JTextField customerIDField =new JTextField();
	
	private JDatePickerImpl datePicker;
	private JRadioButton maleJRadioButton = new JRadioButton("Male");
	private JRadioButton femaleJRadioButton = new JRadioButton("Female");
	private ButtonGroup genderButtonGroup = new ButtonGroup();
	private JComboBox<String>  countyBox = new JComboBox<String>(County);
	private static JTable customertTable = new JTable();
	private sqlQueries customerQueries = new sqlQueries();
	private static ResultSet rs;
	private UtilDateModel model;
	
	
	
	/*       CONSTRUCTOR     */
	/**
	 * Constructor for the AdCustomerPanel class
	 */
    public AddCustomerPanel()  {//Start of constructor
    	initialiseForm();
    	initialiseButtons();
		initialiseTable();
		setLayout();
		setButtonsActionListeners();

    }//end of constructor
    
    
    /*       METHODS     */

    /**
     * setLayout Initialises the layouts of the Customer Panel
     */
    private void setLayout() {//Start of Method
    	
    	customerJPanel.setLayout(new BoxLayout(customerJPanel, BoxLayout.X_AXIS));
    	
		leftouterJPanel.setLayout(new BoxLayout(leftouterJPanel, BoxLayout.Y_AXIS));
		leftouterJPanel.add(formPanel);
		leftouterJPanel.add(buttonJPanel);
		
		buttonJPanel.setLayout(new GridLayout(2,2));
		buttonJPanel.setPreferredSize(new Dimension(0, 115));
		
		JScrollPane scrollPane = new JScrollPane(customertTable);
		scrollPane.setPreferredSize(new Dimension(800,640));
	
		customerJPanel.add(leftouterJPanel);
		customerJPanel.add(scrollPane);
		add(customerJPanel);
    }//End of Method
    
    /**
     * Initialise table variables and action listeners
     */
    private void initialiseTable()  {//Start of Method
    	//load customer table
    	customerQueries.loadTableSetModel(customertTable,"customer");
    	// add mouse listener to the customer table
			customertTable.addMouseListener(new MouseAdapter() {
				
				@SuppressWarnings("deprecation")
				public void mouseClicked(MouseEvent click) {//Start of Method
					// save table values 
					try {
						logger.info("Assigning row values to the Customer object");
						// get values of the selected row
						rs = customerQueries.getRowValues(customertTable,"customer");
					
					// Assign values of the Result Set to the Customer Object
					while(rs.next()) {
						customerIDField.setText(rs.getString("CustomerID"));
						nameField.setText(rs.getString("Name"));
						surnameField.setText(rs.getString("Surname"));
						String dob = rs.getString("DateOfBirth");
						// Set the format for the date of Birth
						DateLabelFormatter dlf = new DateLabelFormatter();
						Date d = (Date) dlf.stringToValue(dob);
						model.setSelected(true);
						model.setDate(d.getYear()+1900, d.getMonth(), d.getDate());
						if(rs.getString("Gender").equalsIgnoreCase("male")) {
							maleJRadioButton.setSelected(true);
							Customer.setGender("Male");
						}
						else {
							femaleJRadioButton.setSelected(true);
							Customer.setGender("Female");
						}
						streeField.setText(rs.getString("Street"));
						townField.setText(rs.getString("Town"));
						countyBox.setSelectedItem(rs.getString("County"));
						emailField.setText(rs.getString("Email"));
						phoneField.setText(rs.getString("Phone"));
						Customer.setCustomerID(Integer.parseInt(customerIDField.getText()));
					}
					} catch (Exception e) {
						logger.error("Error while assigning row values to the Customer Object",e);
					}
				}//End of Method		
			
			});
		
    }//End of Method
    
    /**
     * setButtonsActionListeners Adds action listeners to the buttons
     */
    private void setButtonsActionListeners() {//Start of Method
    	//Add action listener 
    	addButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {//Start of Method
				// assign field values to the customer object 
				Customer.setCustomerName(nameField.getText());
				Customer.setCustomerSurname(surnameField.getText());
				Customer.setDateOfBirth(datePicker.getJFormattedTextField().getText());
				Customer.setStreet(streeField.getText());
				Customer.setTown(townField.getText());
				Customer.setCounty(countyBox.getSelectedItem().toString());
				Customer.setPhone(phoneField.getText());
				Customer.setEmail(emailField.getText());
				Customer.setGender(genderButtonGroup.getSelection().getActionCommand());
				
				//add customer to the table
				customerQueries.addCustomer();
				// load invoicePanel combo boxes with new values
				AddInvoicePanel.loadComboBoxes();
				//reload customer table 
				customerQueries.loadTableSetModel(customertTable,"customer");
				
			}//End of Method
		});
    	
    	deleteButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {//Start of Method
				
					// delete customer from the database
					customerQueries.deleteCustomer();
					// load invoicePanel combo boxes with new values
					AddInvoicePanel.loadComboBoxes();
					// reload customer table
					customerQueries.loadTableSetModel(customertTable,"customer");
				
			}//End of Method
		});
    	
    	editButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {//Start of Method
				// assign field values to the customer object 
				Customer.setCustomerName(nameField.getText());
				Customer.setCustomerSurname(surnameField.getText());
				Customer.setDateOfBirth(datePicker.getJFormattedTextField().getText());
				Customer.setStreet(streeField.getText());
				Customer.setTown(townField.getText());
				Customer.setCounty(countyBox.getSelectedItem().toString());
				Customer.setPhone(phoneField.getText());
				Customer.setEmail(emailField.getText());
				Customer.setGender(genderButtonGroup.getSelection().getActionCommand());
				
				// Edit customer record in the database
				customerQueries.editCustomer();
				// Reload customer table
				customerQueries.loadTableSetModel(customertTable,"customer");
				
			}//End of Method
		});
    	
    	clearButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {//Start of Method
				// Clear the form
				customerIDField.setText(customerIDField.getText());
				nameField.setText("");
				surnameField.setText("");
				model.setSelected(false);
				genderButtonGroup.clearSelection();
				streeField.setText("");
				townField.setText("");
				countyBox.setSelectedItem("");
				emailField.setText("");
				phoneField.setText("");

			}//End of Method
		});
    }//End of Method
    
    /**
     * initialise Buttons
     */
	private void initialiseButtons() {//Start of Method
		//initialise buttons
		maleJRadioButton.setActionCommand("Male");
		femaleJRadioButton.setActionCommand("Female");
		genderButtonGroup.add(maleJRadioButton);
		genderButtonGroup.add(femaleJRadioButton);
		
		//add buttons to the button Panel
		buttonJPanel.add(addButton);
		buttonJPanel.add(editButton);
		buttonJPanel.add(deleteButton);
		buttonJPanel.add(clearButton);
		
	}//End of Method
	
	/**
	 * Initialise Form variables and layout
	 */
	private void initialiseForm() {//Start of Method
		//create model for date panel
		model = new UtilDateModel();
		//create properties object for date panel
		Properties properties = new Properties();
		JDatePanelImpl datePanel = new JDatePanelImpl(model,properties);
		//Initialise date picker 
		 datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		
		 // Initialise layout for the Customer form
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints form = new GridBagConstraints();
		formPanel.setLayout(layout);
		layout.rowHeights = new int[] {30,30,30};
		layout.columnWidths= new int[] {10, 60, 60, 30,2};
		form.insets= new Insets(10,5,10,0);
		form.ipadx=10;
		form.ipady=5;
		form.anchor = GridBagConstraints.WEST;
		form.fill = GridBagConstraints.BOTH;
		
		// Insert and position Components using x and y grid coordinates 
		form.gridx=1;
		form.gridy=0;
		formPanel.add(customerIDLabel,form);

		form.gridx=2;
		form.gridy=0;
		customerIDField.setEditable(false);
		formPanel.add(customerIDField,form);
		
		form.gridx=1;
		form.gridy=1;
		formPanel.add(nameJLabel,form);
		
		form.gridx=2;
		formPanel.add(nameField,form);
		
		form.gridx=1;
		form.gridy=2;
		formPanel.add(surnameJLabel,form);

		form.gridx=2;
		form.gridy=2;
		formPanel.add(surnameField,form);

		form.gridx=1;
		form.gridy=3;
		formPanel.add(ageJLabel,form);
		
		form.gridx=2;
		form.gridy=3;
		formPanel.add(datePicker,form);
		
		form.gridx=1;
		form.gridy=4;
		formPanel.add(genderJLabel,form);
		
		form.gridx=2;
		form.gridy=4;
		formPanel.add(maleJRadioButton,form);
		
		form.gridx=3;
		formPanel.add(femaleJRadioButton,form);
		form.gridx=1;
		form.gridy=5;
		formPanel.add(streetJLabel,form);
		
		form.gridx=2;
		form.gridy=5;
		formPanel.add(streeField,form);
		
		form.gridx=1;
		form.gridy=6;
		formPanel.add(townJLabel,form);
		
		form.gridx=2;
		form.gridy=6;
		formPanel.add(townField,form);
		
		form.gridx=1;
		form.gridy=7;
		formPanel.add(countytJLabel,form);
		
		form.gridx=2;
		form.gridy=7;
		formPanel.add(countyBox,form);
		
		form.gridx=1;
		form.gridy=8;
		formPanel.add(phoneJLabel,form);
		
		form.gridx=2;
		form.gridy=8;
		formPanel.add(phoneField,form);
		
		form.gridx=1;
		form.gridy=9;
		formPanel.add(emailJLabel,form);
		
		form.gridx=2;
		form.gridy=9;
		formPanel.add(emailField,form);
		
	}//End of Method

}//End of Class