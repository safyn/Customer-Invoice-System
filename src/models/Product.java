package models;

public class Product {//Start of Class
	
	static int productID;
	static String productName;
	static float productPrice;
	static int productQuantity;
	static int productReorderLevel;
	static String productDescription;
	static String productSupplier;
	
	//GETTER AND SETTER METHODS
	
	public static int getProductID() {
		return productID;
	}
	public static void setProductID(int productID) {
		Product.productID = productID;
	}
	public static String getProductName() {
		return productName;
	}
	public static void setProductName(String productName) {
		Product.productName = productName;
	}
	public static float getProductPrice() {
		return productPrice;
	}
	public static void setProductPrice(float productPrice) {
		Product.productPrice = productPrice;
	}
	public static int getProductQuantity() {
		return productQuantity;
	}
	public static void setProductQuantity(int productQuantity) {
		Product.productQuantity = productQuantity;
	}
	public static int getProductReorderLevel() {
		return productReorderLevel;
	}
	public static void setProductReorderLevel(int productReorderLevel) {
		Product.productReorderLevel = productReorderLevel;
	}
	public static String getProductDescription() {
		return productDescription;
	}
	public static void setProductDescription(String productDescription) {
		Product.productDescription = productDescription;
	}
	public static String getProductSupplier() {
		return productSupplier;
	}
	public static void setProductSupplier(String productSupplier) {
		Product.productSupplier = productSupplier;
	}
		
}//End of Class
