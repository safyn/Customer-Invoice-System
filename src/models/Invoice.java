package models;

public class Invoice {//Start of Class

	// VARIABLES
	private static int invoiceID;
	private static int customerID;
	private static int productID;
	private static int productQuantity;
	private static String invoiceDate;
	
	//GETTER AND SETTER METHODS
	
	public static int getInvoiceID() {
		return invoiceID;
	}
	public static void setInvoiceID(int invoiceID) {
		Invoice.invoiceID = invoiceID;
	}
	public static int getCustomerID() {
		return customerID;
	}
	public static void setCustomerID(int customerID) {
		Invoice.customerID = customerID;
	}
	public static int getProductID() {
		return productID;
	}
	public static void setProductID(int productID) {
		Invoice.productID = productID;
	}
	public static int getProductQuantity() {
		return productQuantity;
	}
	public static void setProductQuantity(int productQuantity) {
		Invoice.productQuantity = productQuantity;
	}
	public static String getInvoiceDate() {
		return invoiceDate;
	}
	public static void setInvoiceDate(String invoiceDate) {
		Invoice.invoiceDate = invoiceDate;
	}
	
}//End of Class
