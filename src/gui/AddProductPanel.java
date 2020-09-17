package gui;

import org.apache.log4j.Logger;
import java.awt.Dimension;
import  javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import models.Product;
import controllers.sqlQueries;

public class AddProductPanel extends JPanel{ //Start of Class
	 /**
	 * Version
	 */
	private static final long serialVersionUID = 1L;

	/*       VARIABLES     */
	
	// Logger object used to create logs
	static Logger logger = Logger.getLogger(AddProductPanel.class);
	
	private JPanel formPanel =new JPanel();
	private JPanel buttonJPanel =new JPanel();
	private JPanel leftouterJPanel = new JPanel();
	private JPanel productJPanel =new JPanel();
	
	private JButton addButton =new JButton("Add");
	private JButton editButton =new JButton("Edit");
	private JButton deleteButton =new JButton("Delete");
	private JButton clearButton = new JButton("Clear");
	
	private JLabel productIDLabel =new JLabel("Product ID");
	private JLabel productNameJLabel = new JLabel("Name");
	private JLabel productPriceJLabel = new JLabel(" Unit Price");
	private JLabel productQuantity = new JLabel("Quantity");
	private JLabel reorderLevelJLabel = new JLabel("Reorder Level");
	private JLabel supplierNameJLabel = new JLabel("Supplier");
	private JLabel descriptionJLabel = new JLabel("Description");
	
	private JTextField productNameField = new JTextField();
	private JTextField productIDField = new JTextField();
	private JTextField productPriceField = new JTextField();
	private JTextField productQuanitityField = new JTextField();
	private JTextField productReorderField = new JTextField();
	private JTextField productSupplierField = new JTextField();
	private JTextArea descriptionArea = new JTextArea(5,5);
	private static JTable productTable = new JTable();
	
	private sqlQueries productQueries = new sqlQueries();
	private ResultSet rs;

	/*       CONSTRUCTOR     */
	/**
	 * Constructor for the AddProductPanel class
	 */
    public AddProductPanel()  {// Start of Constructor
    	initialiseForm();
    	initialiseButtons();
		initializeTable();
		setLayout();
		setButtonsActionListeners();

    }// End of Constructor
    
    
    /*       METHODS     */

    /**
     * setLayout Initialises the layouts of the Customer Panel
     */  
    private void setLayout() {//Start of Method
    	
    	productJPanel.setLayout(new BoxLayout(productJPanel, BoxLayout.X_AXIS));
    	
		leftouterJPanel.setLayout(new BoxLayout(leftouterJPanel, BoxLayout.Y_AXIS));
		leftouterJPanel.add(formPanel);
		leftouterJPanel.add(buttonJPanel);
		
		buttonJPanel.setLayout(new GridLayout(2,2));
		buttonJPanel.setPreferredSize(new Dimension(0, 115));
		
		JScrollPane scrollPane = new JScrollPane(productTable);
		scrollPane.setPreferredSize(new Dimension(800,640));
	
		productJPanel.add(leftouterJPanel);
		productJPanel.add(scrollPane);
		add(productJPanel);
		
    }//End of Method
    
    /**
     * Initialise table variables and action listeners
     */    
    private void initializeTable()  {//Start of Method
    	// load products table
    	productQueries.loadTableSetModel(productTable, "products");
    	// Add mouse listener to the product table
		productTable.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent click) {//Start of method
				// save table values 
				try {
					// log info
					logger.info("Assigning row values to the Products object");
					// get values of the selected row 
					rs = productQueries.getRowValues(productTable, "products");
				
				// Assign values of the Result Set to the Product Object
				while(rs.next()) {
					productIDField.setText(rs.getString("ProductsID"));
					productNameField.setText(rs.getString("Name"));
					productPriceField.setText(rs.getString("Price"));
					productQuanitityField.setText(rs.getString("Quantity"));

					productReorderField.setText(rs.getString("ReorderLevel"));
					productSupplierField.setText(rs.getString("Supplier"));
					descriptionArea.setText(rs.getString("Description"));
					Product.setProductID(Integer.parseInt(productIDField.getText()));
					
				}
				} catch (Exception e) {
					// log error 
					logger.error("Error while retrieving data from the table",e);
				}
			}//End of Method
			
		});
        	
    }//End of Method
    
    /**
     * setButtonsActionListeners Adds action listeners to the buttons
     */
    private void setButtonsActionListeners() {//Start of Method
    	
    	//Add action listener to the addButton
    	addButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// Assign field values to the Product object 
				Product.setProductName(productNameField.getText());
				Product.setProductPrice(Float.parseFloat(productPriceField.getText()));
				Product.setProductQuantity(Integer.parseInt(productQuanitityField.getText()));
				Product.setProductReorderLevel(Integer.parseInt(productReorderField.getText()));
				Product.setProductSupplier(productSupplierField.getText());
				Product.setProductDescription(descriptionArea.getText());
				
				//add product to the table
				productQueries.addProduct();
				// load invoicePanel combo boxes with new values
				AddInvoicePanel.loadComboBoxes();
				//load product table 
				productQueries.loadTableSetModel(productTable, "products");
			}
		});
    	
    	// Add action listener to the deleteButton
    	deleteButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
					//Delete product from the table
					productQueries.deleteProduct();
					// Load invoicePanel combo boxes with new values
					AddInvoicePanel.loadComboBoxes();
					//Load product table
					productQueries.loadTableSetModel(productTable, "products");
				
			}
		});
    	
    	// Add action listener to the editButton
    	editButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// Assign field values to the Product object
				Product.setProductName(productNameField.getText());
				Product.setProductPrice(Float.parseFloat(productPriceField.getText()));
				Product.setProductQuantity(Integer.parseInt(productQuanitityField.getText()));
				Product.setProductReorderLevel(Integer.parseInt(productReorderField.getText()));
				Product.setProductSupplier(productSupplierField.getText());
				Product.setProductDescription(descriptionArea.getText());
				
				//Edit product record in the database
				productQueries.editProduct();
				//Reload product table
				productQueries.loadTableSetModel(productTable, "products");
			}
		});
    	
    	//Add action listener to the clearButton
    	clearButton.addActionListener(new ActionListener() {
			// Clear the form
			public void actionPerformed(ActionEvent arg0) {
				productNameField.setText("");
				productPriceField.setText("");
				productQuanitityField.setText("");
				productReorderField.setText("");
				productSupplierField.setText("");
				descriptionArea.setText("");
				
			}
		});
    }//End of Method
    
    
    /**
     * initialise Buttons
     */
	public void initialiseButtons() {// Start of Method
		//Add buttons to the buttonJPanel
		buttonJPanel.add(addButton);
		buttonJPanel.add(editButton);
		buttonJPanel.add(deleteButton);
		buttonJPanel.add(clearButton);
		
	}//End of Method
	
	/**
	 * Initialise Form variables and layout
	 */
	public void initialiseForm() {// Start of Method
		
		// Initialise layout for the Product Panel
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints form = new GridBagConstraints();
		formPanel.setLayout(layout);
		layout.rowHeights = new int[] {30,30,30};
		layout.columnWidths= new int[] {10, 60, 215,30,41};
		form.insets= new Insets(10,5,10,0);
		form.ipadx=10;
		form.ipady=5;
		form.anchor = GridBagConstraints.WEST;
		form.fill = GridBagConstraints.BOTH;
		
		// Insert and position Components using x and y grid coordinates 
		form.gridx=1;
		form.gridy=0;
		formPanel.add(productIDLabel,form);

		form.gridx=2;
		form.gridy=0;
		productIDField.setEditable(false);
		formPanel.add(productIDField,form);
		
		form.gridx=1;
		form.gridy=1;
		formPanel.add(productNameJLabel,form);
		
		form.gridx=2;
		formPanel.add(productNameField,form);
		
		form.gridx=1;
		form.gridy=2;
		formPanel.add(productPriceJLabel,form);

		form.gridx=2;
		form.gridy=2;
		formPanel.add(productPriceField,form);

		form.gridx=1;
		form.gridy=3;
		formPanel.add(productQuantity,form);
		
		form.gridx=2;
		form.gridy=3;
		formPanel.add(productQuanitityField,form);
		
		form.gridx=1;
		form.gridy=4;
		formPanel.add(reorderLevelJLabel,form);
		
		form.gridx=2;
		form.gridy=4;
		formPanel.add(productReorderField,form);
		
		form.gridx=1;
		form.gridy=5;
		formPanel.add(supplierNameJLabel,form);
		
		form.gridx=2;
		form.gridy=5;
		formPanel.add(productSupplierField,form);
		
		form.gridx=1;
		form.gridy=6;
		formPanel.add(descriptionJLabel,form);
		
		form.gridheight=3;
		form.gridx=2;
		form.gridy=6;
		JScrollPane descriptionPane = new JScrollPane();
		descriptionPane.add(descriptionArea);
		formPanel.add(descriptionPane,form);
		// set size
		descriptionArea.setPreferredSize(new Dimension(100,100));
		// set line wrap 
		descriptionArea.setLineWrap(true);
		
	}//End of Method
	 
	
}//End of Class