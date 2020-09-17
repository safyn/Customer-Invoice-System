

package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
* Connect class used to create and close connection with database
* @author Krzysztof Bas
* 
*/
	public class Connect { //Start Class
		
		// Logger object used to create logs
		 private static Logger log = Logger.getLogger(Connect.class);
		// variables
		/**
		 DATABASE_URL - database address
		 user - database user
		 password - password to the database;
		 connection - connection object that will hold connection
		 */
		private static String DATABASE_URL = "jdbc:mysql://localhost/project";
		private static String user = "root";
		private static String password = "";
		private static Connection connection =null;

		/**
		* Creates connection with the database, if connection exists uses the existing connection. 
		* @return connection with the server 
		*/
		public static  Connection getInstance() { // Start Method
			if (connection != null) {
				return connection;
			} else {
				try {
					log.info("Connecting to database...");
					connection = DriverManager.getConnection(DATABASE_URL,user,password );
				}
				catch(SQLException e) {
					log.error("Error while connecting to database",e);
				}
				return connection;
			}
		}//End Method
		
		/**
		* Closes connection with the server/
		*/
		public static void close() {// Start Method
			
			 	try {
			 		log.info("Closing connection");
			 		connection.close();
			 	}
			 	catch(Exception e) {
			 		log.error("Error while closing connection",e);
			 	}
		}//End Method
		
	}//End Class

