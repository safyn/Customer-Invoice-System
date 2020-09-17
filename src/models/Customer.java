package models;

public class Customer { //Start of Class
	
	private static int customerID;
	private static String customerName;
	private static String customerSurname;
	private static String dateOfBirth;
	private static String street;
	private static String town;
	private static String county;
	private static String phone;
	private static String email;
	private static String gender;
	
	//GETTER AND SETTER METHODS
	
	public static int getCustomerID() {
		return customerID;
	}
	public static void setCustomerID(int customerID) {
		Customer.customerID = customerID;
	}
	public static String getCustomerName() {
		return customerName;
	}
	public static void setCustomerName(String customerName) {
		Customer.customerName = customerName;
	}
	public static String getCustomerSurname() {
		return customerSurname;
	}
	public static void setCustomerSurname(String customerSurname) {
		Customer.customerSurname = customerSurname;
	}
	public static String getDateOfBirth() {
		return dateOfBirth;
	}
	public static void setDateOfBirth(String dateOfBirth) {
		Customer.dateOfBirth = dateOfBirth;
	}
	public static String getStreet() {
		return street;
	}
	public static void setStreet(String street) {
		Customer.street = street;
	}
	public static String getTown() {
		return town;
	}
	public static void setTown(String town) {
		Customer.town = town;
	}
	public static String getCounty() {
		return county;
	}
	public static void setCounty(String county) {
		Customer.county = county;
	}
	public static String getPhone() {
		return phone;
	}
	public static void setPhone(String phone) {
		Customer.phone = phone;
	}
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		Customer.email = email;
	}
	public static String getGender() {
		return gender;
	}
	public static void setGender(String gender) {
		Customer.gender = gender;
	}
	
}// Class End
