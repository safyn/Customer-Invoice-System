package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import models.*;
/**
* sqlQueries contains methods used to manipulate the database
* @author Krzysztof Bas
*/
public class sqlQueries {//Start Class
final static Logger logger = Logger.getLogger(sqlQueries.class);
/**
*  <code>prepStatement</code> prepared statement object used to execute queries 
*  <code>rs</code> Result Set object stores the result of the queries
*/
	private PreparedStatement prepStatement = null;
	private ResultSet rs = null;
	private int row;

	/**
	* Verifies users credentials in the database in order to allow access to the application.
	* mySQL query checks if <code>Employee.getUsername</code> and <code>Employee.getPassword</code> are correct
	* @return true if credentials are correct , else return false
	*/
		public boolean verifyLogin() {//Start Method

			String verifyQuery = "Select * from employee where Username=? and Password=?";
			// try to verify credentials 
			try {
				logger.info("Verifying username and password");
				
				prepStatement = Connect.getInstance().prepareStatement(verifyQuery);
				prepStatement.setString(1, Employee.getUsername());
				prepStatement.setString(2, Employee.getPassword());
				rs = prepStatement.executeQuery();
				// if Username and password matches record in the database return true
				if(rs.next()) {
					logger.info("Login Succesfull");
					return true;
				}
					// catches exception into the log 
				} catch (SQLException e) {
					logger.error("Error while verifying username and password",e);
				}
			// return false if username and password are incorrect 
			return false;
		}//End Method
		
																/* ****** CUSTOMER TABLE  ****** */

		/**Creates entry in the Customer Table*/
		public void addCustomer(){//Start Method
			
			String addCustomerQuery ="INSERT INTO CUSTOMER (CustomerID,Name,Surname,DateOfBirth,Street,Town,County,Phone,Email,Gender) VALUES (?,?,?,?,?,?,?,?,?,?) ";
			
			try {
				logger.info("Adding Customer to the database");
				
				prepStatement = Connect.getInstance().prepareStatement(addCustomerQuery);
				prepStatement.setString(1, null);
				prepStatement.setString(2, Customer.getCustomerName());
				prepStatement.setString(3, Customer.getCustomerSurname());
				prepStatement.setString(4,Customer.getDateOfBirth());
				prepStatement.setString(5, Customer.getStreet());
				prepStatement.setString(6, Customer.getTown());
				prepStatement.setString(7, Customer.getCounty());
				prepStatement.setString(8, Customer.getPhone());
				prepStatement.setString(9, Customer.getEmail());
				prepStatement.setString(10, Customer.getGender());
				prepStatement.executeUpdate();
				
			} catch (SQLException e) {
				logger.error("Error while adding customer to the database", e);
				}
		}//End Method
		
		/** modifies the selected entry of the Customer table*/
		public void editCustomer() {//Start Method
			String editCustomerQuery="Update customer SET Name=?,Surname=?,DateOfBirth=?,Street=?,Town=?,County=?,Phone=?,Email=?,Gender=? WHERE CustomerID='"+Customer.getCustomerID()+"'";
			
				try {
					logger.info("Editing Customer record");
					
					prepStatement=Connect.getInstance().prepareStatement(editCustomerQuery);
					prepStatement.setString(1, Customer.getCustomerName());
					prepStatement.setString(2, Customer.getCustomerSurname());
					prepStatement.setString(3,Customer.getDateOfBirth());
					prepStatement.setString(4, Customer.getStreet());
					prepStatement.setString(5, Customer.getTown());
					prepStatement.setString(6, Customer.getCounty());
					prepStatement.setString(7, Customer.getPhone());
					prepStatement.setString(8, Customer.getEmail());
					prepStatement.setString(9, Customer.getGender());
					prepStatement.executeUpdate();
					
				} catch (SQLException e) {
					logger.error("Error while editing Customer record",e);
					
				}		
		}//End Method
		
		/**deletes selected entry of the Customer table*/
		public void deleteCustomer() {//Start Method
			
			String deleteCustomerQuery ="Delete from customer WHERE CustomerID='"+Customer.getCustomerID()+"'";
			
			try {
				logger.info("Deleting Customer");
				prepStatement=Connect.getInstance().prepareStatement(deleteCustomerQuery);
				prepStatement.executeUpdate();
			} catch (SQLException e) {
				logger.error("Error while deleting Customer",e);
			}
		}//End Method
		
		/**
		 *  Retrieves row values from the specified table
		 * @param table Row is selected from this table
		 * @param dbTableName name of the table in the Database 
		 * @return Selected row of the selected Table 
		 */
		public ResultSet getRowValues(JTable table,String dbTableName){
			row = table.getSelectedRow();

			String ID = (table.getModel().getValueAt(row, 0).toString());		
			String getIDQuery = "select * from "+dbTableName+" where "+dbTableName+"ID ='"+ID+"'";
			try {
				logger.info("Retreiving "+dbTableName+" details from the database");
				prepStatement = Connect.getInstance().prepareStatement(getIDQuery);
				rs = prepStatement.executeQuery();
			} catch (SQLException e) {
				logger.error("Error while retreiving "+dbTableName+" details",e);
			}
			 
			return rs;
		}//End Method
	
																/* ****** Product TABLE  ****** */	

		/**Creates entry in the Product table*/
		public void addProduct() {//Start Method
			
				String addProductQuery ="INSERT INTO PRODUCTS (ProductsID,Name,Price,Quantity,ReorderLevel,Supplier,Description) VALUES (?,?,?,?,?,?,?) ";
			
			try {
				logger.info("Adding Product to the database");
				
				prepStatement = Connect.getInstance().prepareStatement(addProductQuery);
				prepStatement.setString(1, null);
				prepStatement.setString(2,Product.getProductName() );
				prepStatement.setFloat(3, Product.getProductPrice());
				prepStatement.setInt(4, Product.getProductQuantity());
				prepStatement.setInt(5, Product.getProductReorderLevel());
				prepStatement.setString(6, Product.getProductSupplier());
				prepStatement.setString(7, Product.getProductDescription());

				prepStatement.executeUpdate();
				
			} catch (SQLException e) {
				logger.error("Error while adding product to the database", e);
				}
		}//End Method

		/** Removes selected entry of the Product table*/
		public void deleteProduct() {//Start Method
			
			String deleteProductQuery ="Delete from products WHERE ProductsID='"+Product.getProductID()+"'";
			try {
				logger.info("Deleting Customer");
				
				prepStatement=Connect.getInstance().prepareStatement(deleteProductQuery);
				prepStatement.executeUpdate();
				
			} catch (SQLException e) {
				logger.error("Error while deleting Product");
			}
			
		}//End Method
		
		/** Modifies selected entry of the Product table*/
		public void editProduct() {//Start Method
			
			String editProductQuery="Update products SET Name=?,Price=?,Quantity=?,ReorderLevel=?,Supplier=?,Description=? WHERE ProductsID='"+Product.getProductID()+"'";
			
			try {
				logger.info("Editing Customer record");
				
				prepStatement = Connect.getInstance().prepareStatement(editProductQuery);
				prepStatement.setString(1,Product.getProductName() );
				prepStatement.setFloat(2, Product.getProductPrice());
				prepStatement.setInt(3, Product.getProductQuantity());
				prepStatement.setInt(4, Product.getProductReorderLevel());
				prepStatement.setString(5, Product.getProductSupplier());
				prepStatement.setString(6, Product.getProductDescription());

				prepStatement.executeUpdate();
				
			} catch (SQLException e) {
				logger.error("Error while editing Customer record",e);
			
			}
		}//End Method
		
																/* ****** INVOICE COMBO BOXES  ****** */
		/**
		* Selects CustomerID's from the Customer table 
		* @return Table of Customer's ID
		*/
		public ResultSet invoiceCustomerID() {//Start Method
			
			String getCustomersID ="select CustomerID from customer";
			
			try {
				logger.info("Selecting Customers ID");
				
				prepStatement= Connect.getInstance().prepareStatement(getCustomersID);
				rs = prepStatement.executeQuery();
			} catch (Exception e) {
				logger.error("Error while selecting customers ID",e);
			}
			
			return rs;	
		}//End Method
		
		
		/**Selects ProductID's from the Customer table 
		 @return Table of Product's ID */
		
		public ResultSet invoiceProductID() {//Start Method
			
			String getProductID ="select ProductsID from products";
			
			try {
				logger.info("Selecting Product ID");
				
				prepStatement= Connect.getInstance().prepareStatement(getProductID);
				rs = prepStatement.executeQuery();
			} catch (Exception e) {
				logger.error("Error while selecting products ID",e);
			}
			
			return rs;
			
		}//End Method
		
																/* ****** PRODUCT-INVOICE TABLE  ****** */
		
		/** Insert selected product to the productinvoice table. */ 

		public void addProductToInvoice() {//Start Method
			
			String addProductInvoiceString = "Insert into productinvoice (ProductID,ProductName,ProductPrice,QuantitySold,InvoiceID) VALUES(?,?,?,?,?)";
					 						
			String name = null;
		    String price=null;
			try {
				prepStatement=Connect.getInstance().prepareStatement("Select * from products WHERE ProductsID='"+Invoice.getProductID()+"'");
				rs=prepStatement.executeQuery();
				   while (rs.next()) {
					   name = rs.getString("Name");
					   price = rs.getString("Price");

				   }   
				logger.info("Adding Product to the invoice");
				prepStatement=Connect.getInstance().prepareStatement(addProductInvoiceString);
				prepStatement.setInt(1, Invoice.getProductID());
				prepStatement.setString(2,name);
				prepStatement.setString(3,price);
				prepStatement.setInt(4, ProductInvoice.getProductQuantity());
				prepStatement.setInt(5, ProductInvoice.getInvoiceID());
				prepStatement.executeUpdate();
				
			} catch (Exception e) {
				logger.error("Error while adding product to the invoice table",e);
			}
			
		}//End Method
		
		/**
		* Selects products corresponding to the supplied invoiceID
		* @param invoiceID ID of the invoice to be selected 
		* @return Table of data corresponding to the supplied value of invoiceID
		*/
		public ResultSet loadProductInvoiceTable(int invoiceID) {//Start Method
			
			String getProductInvoiceTable="Select* from productinvoice where InvoiceID='"+Invoice.getInvoiceID()+"'";
			try {
				
				prepStatement=Connect.getInstance().prepareStatement(getProductInvoiceTable);
				rs = prepStatement.executeQuery();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			return rs;
		}//End Method
	
		/**
		* Removes selected product from the table of products of a selected invoiceID
		* @param table to select row from
		*/
		public void deleteProductFromInvoice(JTable table) {//Start Method
			
			row = table.getSelectedRow();
			int productInvoiceID = Integer.parseInt(table.getModel().getValueAt(row, 1).toString());
			String deleteProductInvoiceQuery ="Delete from productinvoice WHERE ProductID='"+productInvoiceID+"' and InvoiceID='"+Invoice.getInvoiceID()+"'";
			try {
				logger.info("Deleting product from invoice");
				
				prepStatement=Connect.getInstance().prepareStatement(deleteProductInvoiceQuery);
				prepStatement.executeUpdate();
				
			} catch (SQLException e) {
				logger.error("Error while deleting Product from invoice");
			}
			
		}//End Method
		
		/**
		*Selects biggest invoice ID value in order to create new Invoice.
		* @return nextID of the new Invoice
		*/
		public int newInvoice() {//Start Method
			
			String maxInvoiceID = "SELECT Max(InvoiceID) from invoice";
			int nextID = 0;
			try {
				prepStatement=Connect.getInstance().prepareStatement(maxInvoiceID);
				rs = prepStatement.executeQuery();
				while(rs.next()) {
					nextID =rs.getInt("Max(InvoiceID)");
				}

			} catch (Exception e) {
				logger.error("error",e);
			}
			
			return nextID+1;
		}//End Method
			
															/* ****** INVOICE TABLE  ****** */		
		/**
		* Selects values for the Invoice Table
		* @return Invoice Table
		*/
		public ResultSet loadInvoiceTable() {//Start Method
			
			String getInvoiceTable = "SELECT productinvoice.InvoiceID,invoice.CustomerID,invoice.InvoiceDate, " + 
					"ROUND(SUM(productinvoice.productPrice * productinvoice.QuantitySold),2) as TotalPrice, " + 
					"SUM(productinvoice.QuantitySold) as itemsSold " + 
					"FROM productinvoice,invoice " + 
					"WHERE invoice.InvoiceID = productinvoice.InvoiceID " + 
					"GROUP BY invoiceID";
			
			try {
				logger.info("Loading invoice table data");
				
				prepStatement=Connect.getInstance().prepareStatement(getInvoiceTable);
				rs = prepStatement.executeQuery();
			} catch (Exception e) {
				
				logger.error("Error while loading invoice table data",e);
			}
					
			return rs;
		}//End Method
		
	

		
		/** Inserts row into the Invoice Table*/
		public void addInvoice() {//Start Method
			
			String addInvoice ="insert into invoice (CustomerID,InvoiceDate) values(?,?)";
			
			try {
				logger.info("Adding new invoice");
				prepStatement=Connect.getInstance().prepareStatement(addInvoice);
				prepStatement.setInt(1,Invoice.getCustomerID());
				prepStatement.setString(2,Invoice.getInvoiceDate());
				prepStatement.execute();
				
			} catch (Exception e) {
				logger.error("Error while adding invoice",e);
			}
			
		}//End Method
		
									
		/**
		* Selects table from the database and inserts the values into the jTable
		* @param jTable Table to insert the data into
		* @param dbtableName Name of the table in the actual database.
		*/
		   public void loadTableSetModel(JTable jTable,String dbtableName) {//Start Method
				try {
					logger.info("Loading "+jTable.getName()+" table");
					prepStatement = Connect.getInstance().prepareStatement(" select * from "+dbtableName);
					 rs = prepStatement.executeQuery();
					jTable.setModel(TableData.buildTableModel(rs));
				} catch (SQLException e) {
					logger.error("Error while loading "+jTable+" table", e);
				}
				
		   }//End of Method
}//End of Class
