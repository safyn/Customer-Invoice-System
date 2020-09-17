package models;

public class ProductInvoice {//Start of Class
	
	private static int productInvoiceID;
	private static int productID;
	private static String productName;
	private static int productQuantity;
	private static float productPrice;
	private static int invoiceID;
	
	//GETTER AND SETTER METHODS
	
	public static int getProductInvoiceID() {
		return productInvoiceID;
	}
	public static void setProductInvoiceID(int productInvoiceID) {
		ProductInvoice.productInvoiceID = productInvoiceID;
	}
	public static int getProductID() {
		return productID;
	}
	public static void setProductID(int productID) {
		ProductInvoice.productID = productID;
	}
	public static String getProductName() {
		return productName;
	}
	public static void setProductName(String productName) {
		ProductInvoice.productName = productName;
	}
	public static int getProductQuantity() {
		return productQuantity;
	}
	public static void setProductQuantity(int productQuantity) {
		ProductInvoice.productQuantity = productQuantity;
	}
	public static float getProductPrice() {
		return productPrice;
	}
	public static void setProductPrice(float productPrice) {
		ProductInvoice.productPrice = productPrice;
	}
	public static int getInvoiceID() {
		return invoiceID;
	}
	public static void setInvoiceID(int invoiceID) {
		ProductInvoice.invoiceID = invoiceID;
	}
	
}//End of Class
