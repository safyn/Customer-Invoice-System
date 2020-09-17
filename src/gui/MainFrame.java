package gui;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import org.apache.log4j.Logger;
import controllers.Connect;
/**
 * MainFrame Main window of the Application	
 * @author krzysztof
 */
public class MainFrame extends JFrame {//Start of Class
	/**
	 * Version
	 */
		/*       VARIABLES     */
	
	private static final long serialVersionUID =1L;
	
	// Logger object used to create logs
	private Logger logger = Logger.getLogger(MainFrame.class.getName());
	// panel and tabbed pane
	JTabbedPane tabbedPane = new JTabbedPane();;
	AddCustomerPanel customerPanel = new AddCustomerPanel();
	AddProductPanel productPanel =new AddProductPanel();
	AddInvoicePanel invoicePanel = new AddInvoicePanel();

	/**
	 * Constructor for the MainFrame class
	 */
	public MainFrame() {//Start of Constructor
		// initialise frame
		initializeWindow();
		//Add panels to the JTabbedPane 
		tabbedPane.addTab("Customer", customerPanel);
		tabbedPane.addTab("Product", productPanel);
		tabbedPane.addTab("Invoice", invoicePanel);
		getContentPane().add(tabbedPane);
		
	}//End of Constructor
	
	/**
	 * Initialise the properties of the frame 
	 */
	public void initializeWindow() {//Start of Method
		//log info
		logger.info("Initialising MainFrame");
		// set properties
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1225, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		
		// Add window listener to the frame
		addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		        super.windowClosing(e);
		        // close connection
		        Connect.close();
		        // log info 
		        logger.info("Closing Application");
		        
		    }
		});
	}//End of Method
	
	/**
	 * Initialise the default font properties of the components
	 * @param myFont Font to be selected as default 
	 */
	private static void setFont(FontUIResource myFont) {//Start of Method
		UIManager.put("Label.font", myFont);
		UIManager.put("Button.font", myFont);

	}//End of Method
	
	public static void main(String[] args) {//Start of main Method
		//set default font 
		setFont(new FontUIResource(new Font("Arial", Font.BOLD, 15)));
		// Create MainFrame Application
		MainFrame frame = new MainFrame();

	}//End of main method 
	
}//End of Class
