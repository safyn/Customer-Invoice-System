package gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;
/**
 * Class used to change format of the date picker 
 */
public class DateLabelFormatter extends AbstractFormatter {//Start of Class
	/**
	 * Version
	 */
	private static final long serialVersionUID = 1L;
	// set pattern for a date 
    private String datePattern = "yyyy-MM-dd";
    // create date format object and assign date pattern to it
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    
    /**
     * Parses string value to date format
     * @param text String value to be parsed into the date format
     */
    public Object stringToValue(String text) throws ParseException {//Start of Method
        return dateFormatter.parseObject(text);
    }//End of Method

    /**
     * Parses date format to string value
     *@param value Date format object to be parsed to string
     *@return date in a string format, if <code>value</code> is null return empty string
     */
    public String valueToString(Object value) throws ParseException {//Start of Method
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }//End of Method
}//End of Class   