package functionalLibrary;

import java.util.Hashtable;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import pageObject.IUserManagement;
import utility.FL_GeneralUtils;

public class UserManagement implements IUserManagement
{
	private WebDriver driver;
	private Logger LOGGER;
	protected WebDriverWait wait;
	private FL_GeneralUtils objExefunction;
	private Hashtable<String, String> dicTestData;
	
	public UserManagement(WebDriver driver, WebDriverWait wait, Logger LOGGER, Hashtable<String, String> dicTestData) 
	{

		this.driver = driver;
		this.wait = wait;
		this.LOGGER = LOGGER;
		this.dicTestData = dicTestData;
		objExefunction = new FL_GeneralUtils(driver, wait, LOGGER);
	}
	
	public UserManagement()
	{
		System.out.println("Const");
	}
	
	public boolean verifyButton() throws ElementNotFoundException, exceptions.ElementNotFoundException
	{
		boolean blnVerifyButton = false;
		
		try
		{
			blnVerifyButton = objExefunction.isDisplayed(btn_AddUser, "Add User");
			
			if(!blnVerifyButton)
			{
				LOGGER.fine("Add User button is not present");
			}
			else
			{
				LOGGER.fine("Add User button is present");
			}
		}
		catch(Exception e)
		{
			LOGGER.severe("Exception in User Management");
		}
		
		return blnVerifyButton;
		
	}
	
	public boolean m3() throws ElementNotFoundException
	{
		System.out.println("M3");
		LOGGER.info(dicTestData.get("t5")+" = t5");
		return true;
	}
	
	public boolean m4() throws ElementNotFoundException
	{
		System.out.println("M4");
		LOGGER.info(dicTestData.get("t7")+" = t7");
		return false;
	}
}
