package functionalLibrary;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import batchExecution.DriverScript;
import exceptions.ElementNotFoundException;
import pageObject.IDashboard;
import pageObject.ILoginPage;
import utility.FL_GeneralUtils;

public class Login extends DriverScript implements ILoginPage ,IDashboard
{	

	private WebDriver driver;
	protected WebDriverWait wait;
	private Logger LOGGER;
	private FL_GeneralUtils objExefunction;

	public Login(WebDriver driver, WebDriverWait wait, Logger LOGGER) 
	{

		this.driver = driver;
		this.wait = wait;
		this.LOGGER = LOGGER;
		objExefunction = new FL_GeneralUtils(driver, wait, LOGGER);
	}

	public Login() 
	{

	}

	//This method is used to login with one parameter
	public boolean loginUser(String strUserName, String strPassword) throws ElementNotFoundException
	{
		boolean blnIsLoginSuccessful = false;
		try 
		{
			if(strUserName.equalsIgnoreCase("") || strUserName.isEmpty() || strUserName.equals(null))
			{									
				throw new ElementNotFoundException(strUserName+" is not found in Testdata", LOGGER);	
			}
			driver.get(driverProperties.getProperty("applicationURL"));
			driver.manage().deleteAllCookies();
			wait = new WebDriverWait(driver, 30);

			objExefunction.waitUntil(ExpectedConditions.elementToBeClickable(txt_UserName),  "User Name Text box");
			wait = new WebDriverWait(driver, 20);
			objExefunction.setText(txt_UserName, "UserName TextBox on Login Page", strUserName);
			objExefunction.setText(txt_Password, "Password TextBox on Login Page",strPassword);
			objExefunction.clickOnElement(btn_SignIn, "Sign In Button after Password");
			
			/*WorkAround for Login Functionality*/
			try 
			{
				Thread.sleep(2000);
				driver.findElement(btn_Yes).click();
			} 
			catch (Exception e) 
			{				
			}
			blnIsLoginSuccessful = verifyLogin();


		}
		catch (Exception e) 
		{
			throw new ElementNotFoundException("Exception occured while login to Console"+e, LOGGER);		
		}

		if (blnIsLoginSuccessful) 
		{
			LOGGER.fine("Login to Trubot cockpit Performed Successfully");
		}
		else
		{
			throw new ElementNotFoundException("Exception occured while login to Console", LOGGER);
			//LOGGER.severe("Unable to Login to Console");
		}

		return blnIsLoginSuccessful;
	}

	
	public boolean verifyLogin() throws ElementNotFoundException
	{
		if(objExefunction.isDisplayed(div_DashboardTitle,  "Dashboard Page"))
		{
			LOGGER.info("Sucessfully verify Login is succesfull");
			return true;
		}
		else
		{
			throw new ElementNotFoundException("Exception occured while login to Cockpit", LOGGER);
		}
	}
	
	public boolean FailStep() throws ElementNotFoundException
	{
		throw new ElementNotFoundException("Exception occured while login to Cockpit", LOGGER);
	}

}
