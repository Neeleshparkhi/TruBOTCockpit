package logging;

import java.io.File;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;


public class MyLogger {


	private FileHandler fileTxt;
	private SimpleFormatter formatterTxt;
	String strCurrentDate;
	private FileHandler fileHTML;
	private Formatter formatterHTML;
	Logger logger;	
	Date date = new Date();
	String strLogFolder;
	
	//Default Constructor
	public MyLogger()
	{

	}
	
	//Constructor with Currentdate
	public MyLogger(String strCurrentDate)
	{
		this.strCurrentDate = strCurrentDate;
	}
	
	
	
	public Logger getLogger (String strlogger, WebDriver driver, ExtentTest testLogg) 
	{
		logger = Logger.getLogger(strlogger);
		strLogFolder = strlogger+strCurrentDate;
		
		createDir(strLogFolder);
		try {
			// suppress the logging output to the console
			Logger rootLogger = Logger.getLogger("");
			Handler[] handlers = rootLogger.getHandlers();
			if(handlers.length >0)
			{
				if (handlers[0] instanceof ConsoleHandler) {
					rootLogger.removeHandler(handlers[0]);
				}
			}		

			logger.setLevel(Level.FINE);

			fileTxt = new FileHandler(".//logs//"+strLogFolder+"//"+strlogger+".txt");
			fileHTML = new FileHandler(".//logs//"+strLogFolder+"//"+strlogger+".html");

			// create a TXT formatter
			formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			logger.addHandler(fileTxt);

			// create an HTML formatter
			formatterHTML = new MyHtmlFormatter(strLogFolder, driver, testLogg);
			fileHTML.setFormatter(formatterHTML);
			logger.addHandler(fileHTML);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logger;
	}
	
	public Logger getLogger (String strlogger, ExtentTest testLogg)  {
		logger = Logger.getLogger(strlogger);
			
		strLogFolder = strlogger+strCurrentDate;
		
		createDir(strLogFolder);
		try 
		{
			// suppress the logging output to the console
			Logger rootLogger = Logger.getLogger("");
			Handler[] handlers = rootLogger.getHandlers();
			if(handlers.length >0)
			{
				if (handlers[0] instanceof ConsoleHandler)
				{
					rootLogger.removeHandler(handlers[0]);
				}
			}		

			logger.setLevel(Level.INFO);

			fileTxt = new FileHandler(".//logs//"+strLogFolder+"//"+strlogger+".txt");
			fileHTML = new FileHandler(".//logs//"+strLogFolder+"//"+strlogger+".html");

			// create a TXT formatter
			formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			logger.addHandler(fileTxt);

			// create an HTML formatter
			formatterHTML = new MyHtmlFormatter(strLogFolder, testLogg);
			fileHTML.setFormatter(formatterHTML);
			logger.addHandler(fileHTML);


		} 
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return logger;
	}

	public static void createDir(String strlogger)
	{
		try 
		{
			String directoryName = ".//logs";
			File directory = new File(directoryName);
			if(!directory.exists())
			{
				directory.mkdir();
			}
			
			 directoryName = ".//logs//"+strlogger;
			 directory = new File(directoryName);
			
			if (directory.exists())
			{			
				String[]entries = directory.list();
				for(String s: entries){
				    File currentFile = new File(directory.getPath(),s);
				    currentFile.delete();				    
				}
				
				directory.delete();
				Thread.sleep(1000);
				}
			directory.mkdir();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
