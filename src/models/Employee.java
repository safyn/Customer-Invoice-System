package models;

public class Employee {//Start of Class
	
	// Variables
	private static int employeeID;
	private static String username;
	private static String password;
	
	//GETTER AND SETTER METHODS
	
	public int getID() {
		return employeeID;
	}
	public void setID(int iD) {
		employeeID = iD;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		Employee.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		Employee.password = password;
	}
}//End of Class
