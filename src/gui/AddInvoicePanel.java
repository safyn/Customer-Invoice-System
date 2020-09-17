package gui;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import controllers.TableData;
import controllers.sqlQueries;
import gui.DateLabelFormatter;
import models.Invoice;
import models.ProductInvoice;

public class AddInvoicePanel extends JPanel {//Start of Class
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	/*       VARIABLES     */
	
	// Logger object used to create logs
	private final static Logger logger = Logger.getLogger(sqlQueries.class);

	private JPanel invoicePanel = new JPanel();
	private JPanel formPanel = new JPanel();
	private JPanel tablesPanel = new JPanel();
	private JPanel leftPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	
	private JTable invoiceProducTable = new JTable();
	private JTable invoiceTable = new JTable();
	
	private JLabel invoiceIDLabel = new JLabel("Invoice ID");
	private JLabel customerIDLabel = new JLabel("Customer ID");
	private JLabel productIDLabel = new JLabel("Product ID");
	private JLabel productQuantityLabel = new JLabel("Quantity Sold  "); 
	private JLabel invoiceDate = new JLabel("Invoice Date");
	private JLabel invoicesTableLabel = new JLabel("Invoices");
	private JLabel productsTableLabel = new JLabel("Products");
	private JLabel invoiceLabel = new JLabel("Invoice");
	private JLabel productLabel = new JLabel("Product");	
	
	private JTextField invoiceIDTextField = new JTextField();
	private JTextField quantityField = new JTextField();
	
	private static  JComboBox<Integer> customerIDBox = new JComboBox<Integer>();
	private static JComboBox<Integer> productIDBox = new JComboBox<Integer>();
	
	private JScrollPane productsPane = new JScrollPane(invoiceProducTable);
	private JScrollPane invoiceTablePane = new JScrollPane(invoiceTable);
	
	private JButton addProductButton =new JButton("Add Product");
	private JButton deleteProductButton =new JButton("Delete Product");
	private JButton newInvoiceButton = new JButton("New Invoice");
	private JButton processInvoiceButton = new JButton("Process Invoice");
	
	private JDatePickerImpl datePicker;
	private UtilDateModel model;
	private static ResultSet custoemerIDResultSet=null;
	private static ResultSet productIDResultSet=null;
	private static int invoiceID=1 ;
	private int ID;
	private static sqlQueries invoiceQueries = new sqlQueries();
	
	/*       CONSTRUCTOR     */
	/**
	 * Constructor for the AddInvoicePanel class
	 */
	protected AddInvoicePanel() { //Start of constructor
		initializeForm();
		initialiseButtons();
		setLaybout();
		initialiseTables();
		loadComboBoxes();
		
		}//End of constructor
	
    /*       METHODS     */

    /**
     * setLayout Initialises the layouts of the Invoice Panel
     */
	private void setLaybout() {
		invoicePanel.setLayout(new BoxLayout(invoicePanel, BoxLayout.X_AXIS));
		
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(formPanel);
		leftPanel.add(buttonPanel);
		
		buttonPanel.setLayout(new GridLayout(2,2));
		buttonPanel.setPreferredSize(new Dimension(0, 115));
		
		tablesPanel.setPreferredSize(new Dimension(800, 640));
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		
		tablesPanel.add(Box.createRigidArea(new Dimension(5,10)));
		tablesPanel.add(productsTableLabel);
		tablesPanel.add(productsPane);
		tablesPanel.add(Box.createRigidArea(new Dimension(5,10)));
		tablesPanel.add(invoicesTableLabel);
		tablesPanel.add(invoiceTablePane);

		invoicePanel.add(leftPanel);
		invoicePanel.add(tablesPanel);
		add(invoicePanel);
		
	}//End of Method

    /**
     * Initialise table variables and action listeners
     */
    private void initialiseTables()  {//Start of Method
		
		try {
			logger.info("Initializing invoice panel tables");
			// load tables
			invoiceProducTable.setModel(TableData.buildTableModel(invoiceQueries.loadProductInvoiceTable(invoiceID)));
			invoiceTable.setModel(TableData.buildTableModel(invoiceQueries.loadInvoiceTable()));
		} catch (SQLException e) {
			
			logger.error("Error while initializing invoice panel tables",e);
		}
		// Add mouse listener
		invoiceTable.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent click) {
					// Select Invoice ID and assign it to the productInvoice object
					int row = invoiceTable.getSelectedRow();
					String productInvoiceID = (invoiceTable.getModel().getValueAt(row, 0).toString());
					Invoice.setInvoiceID(Integer.parseInt(productInvoiceID));
					invoiceIDTextField.setText(productInvoiceID);

					try {
						// log info
						logger.info("Retreiving Invoice Products");
						// load table of products for selected invoice ID
						invoiceProducTable.setModel(TableData.buildTableModel(invoiceQueries.loadProductInvoiceTable(Invoice.getInvoiceID())));
					} catch (SQLException e) {
						//log error
						logger.error("Error while retreiving invoice products",e);
					}
					
				}//End of Method
			});
		}//End of Method
	
    /**
     * Add buttons to the panel and initialise action listeners 
     */
	public void initialiseButtons() {
		// Add buttons to the panel
		buttonPanel.add(addProductButton);
		buttonPanel.add(deleteProductButton);
		buttonPanel.add(newInvoiceButton);
		buttonPanel.add(processInvoiceButton);
		
		// Add action listener to the add button
		addProductButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// Assign field values to the objects
				ID = Integer.parseInt(invoiceIDTextField.getText());
				Invoice.setInvoiceID(ID);
				ProductInvoice.setInvoiceID(ID);
				Invoice.setInvoiceDate(datePicker.getJFormattedTextField().getText());
				
				Invoice.setCustomerID(Integer.parseInt(customerIDBox.getSelectedItem().toString()));
				Invoice.setProductID(Integer.parseInt(productIDBox.getSelectedItem().toString()));
				ProductInvoice.setProductQuantity(Integer.parseInt(quantityField.getText()));
				
				//Add product to the invoice
				invoiceQueries.addProductToInvoice();
				//load invoice panel tables
				initialiseTables();
			}
		});
		// Add action listener to the delete button
		deleteProductButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// Delete product from the invoice
				invoiceQueries.deleteProductFromInvoice(invoiceProducTable);
				// load invoice panel tables
				initialiseTables();
			}
		});
		// Add action listener to the processInvoice button
		processInvoiceButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				Invoice.setCustomerID(Integer.parseInt(customerIDBox.getSelectedItem().toString()));
				//Invoice.setInvoiceID(Invoice.getCustomerID());
				Invoice.setInvoiceDate(datePicker.getJFormattedTextField().getText());
				// Add invoice to the database
				invoiceQueries.addInvoice();
				//load invoice panel tables
				initialiseTables();
			}
		});
		// Add action listener to the invoice panel button
		newInvoiceButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// get the next available InvoiceID
				int nextInvoice =invoiceQueries.newInvoice();
				// Assign new invoiceID to the Invoice object
				Invoice.setInvoiceID(nextInvoice);
				invoiceIDTextField.setText(Integer.toString(nextInvoice));
				// load invoice panel tables 
				initialiseTables();
			}
		});
		
		
	}//End of Method
	
	/**
	 * loadComboBoxes Inserts values into the combo boxes 
	 */
	static void loadComboBoxes() {
		// get tables to be inserted into combo boxes 
		custoemerIDResultSet = invoiceQueries.invoiceCustomerID();
		productIDResultSet = invoiceQueries.invoiceProductID();
		// Remove all items from combo boxes 
		customerIDBox.removeAllItems();
		productIDBox.removeAllItems();
		try {
			logger.info("Load combo boxes");
			// load CustomerID combo box
			while(custoemerIDResultSet.next()) {
				customerIDBox.addItem(custoemerIDResultSet.getInt("CustomerID"));
			}
			// load ProductID combo box
			while (productIDResultSet.next()) {
				productIDBox.addItem(productIDResultSet.getInt("ProductsID"));
				
			}
			// catch exception
		} catch (SQLException e) {
			logger.error("Error while inserting values into combo boxes",e);
		}
		
	}//End of Method

	/**
	 * Initialise Form variables and layout
	 */
	private void initializeForm() {

		//create model for date panel
		model = new UtilDateModel();		
		//create properties object for date panel
		Properties properties = new Properties();
		JDatePanelImpl datePanel = new JDatePanelImpl(model,properties);
		//Initialise date picker 
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		// Initialise layout for the Invoice form
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints form = new GridBagConstraints();
		formPanel.setLayout(layout);
		layout.rowHeights = new int[] {30,30,30};
		layout.columnWidths= new int[] {10,60,215,30,41};
		form.insets= new Insets(10,5,10,0);
		form.ipadx=10;
		form.ipady=5;
		form.fill = GridBagConstraints.HORIZONTAL;
		
		// Insert and position variables
		form.gridx=1;
		form.gridy=1;
		formPanel.add(productIDLabel,form);
		
		form.gridx=2;
		form.gridy=1;
		formPanel.add(productIDBox,form);
		
		form.gridx=1;
		form.gridy=2;
		formPanel.add(productQuantityLabel,form);
		
		form.gridx=2;
		form.gridy=2;
		formPanel.add(quantityField,form);
		
		
		form.gridx=1;
		form.gridy=4;
		formPanel.add(customerIDLabel,form);

		form.gridx=2;
		form.gridy=4;
		formPanel.add(customerIDBox,form);
		
		form.gridx=1;
		form.gridy=5;
		formPanel.add(invoiceIDLabel,form);
		
		form.gridx=2;
		form.gridy=5;
		invoiceIDTextField.setEditable(false);
		invoiceIDTextField.setText(Integer.toString(invoiceID));
		formPanel.add(invoiceIDTextField,form);
		
		form.gridx=1;
		form.gridy=6;
		formPanel.add(invoiceDate,form);

		form.gridx=2;
		form.gridy=6;
		formPanel.add(datePicker,form);
		
		form.anchor = GridBagConstraints.CENTER;
		form.fill = GridBagConstraints.NONE;
		
		form.gridx=2;
		form.gridy=0;
		invoiceLabel.setFont(new Font("Arial",Font.BOLD,22));
		formPanel.add(productLabel,form);
		
		form.gridx=2;
		form.gridy=3;
		productLabel.setFont(new Font("Arial",Font.BOLD,22));
		formPanel.add(invoiceLabel,form);
		
		
	}//End of Method

}// End of Class
	
