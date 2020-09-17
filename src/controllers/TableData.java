package controllers;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
/**
* Processes tables data
*/
public class TableData {//Start of Class
	
	/**
	* BuildTableModel Builds Table from the Result Set
	* @return Model of the complete table 
	* @param rs Result set used to build the table
	* @throws SQLException if table doesn't exist 
	*/
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {// Start of Method

        ResultSetMetaData metaData = rs.getMetaData();

	    //selects names of columns
       // columnNames is a vector containing the names of the table columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    //selects data of the table
	    // data object contains the data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);  
	    }
	    // Return table model including table data and column names 
	    return new DefaultTableModel(data, columnNames);

	}//End of Method
}//End of Class
