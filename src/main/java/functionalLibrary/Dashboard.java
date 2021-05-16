package functionalLibrary;


import java.util.logging.Logger;

import org.apache.tools.ant.taskdefs.Sleep;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import exceptions.ElementNotFoundException;
import pageObject.IDashboard;
import pageObject.ILoginPage;
import utility.FL_GeneralUtils;

public class Dashboard implements IDashboard ,ILoginPage
{

	private WebDriver driver;
	private Logger LOGGER;
	protected WebDriverWait wait;
	private FL_GeneralUtils objExefunction;

	public Dashboard(WebDriver driver, WebDriverWait wait, Logger LOGGER) 
	{

		this.driver = driver;
		this.wait = wait;
		this.LOGGER = LOGGER;
		objExefunction = new FL_GeneralUtils(driver, wait, LOGGER);
	}


	public boolean verifyImage() throws ElementNotFoundException 
	{
		boolean blnverifyImageIsSuccessfull = false;

		try
		{

			//Boolean ImagePresent = (Boolean) ((JavascriptExecutor)driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", img_DatamaticsLogo);
			blnverifyImageIsSuccessfull = objExefunction.isDisplayed(img_DatamaticsLogo,  "Datamatics Logo");

			if(!blnverifyImageIsSuccessfull)
			{
				LOGGER.fine("Datamatics logo is not present");
			}

			else
			{
				LOGGER.fine("Datamatics logo is present");
			}

		}
		catch(Exception e)
		{
			LOGGER.severe("Exception in Dashboard");
		}

		return blnverifyImageIsSuccessfull;
	}

	public void clickUsers() throws exceptions.ElementNotFoundException
	{
		objExefunction.clickOnElement(btn_Users, "Users Tab");
	}


	//This method performs logout from application
	public boolean performLogout() throws exceptions.ElementNotFoundException
	{
		boolean blnIsLogoutSuccessfull = false;
		objExefunction.clickOnElement(spn_Role, "Role Span");
		objExefunction.sleep(2000);
		objExefunction.clickOnElement(spn_Logout, "Logout Buton");
		objExefunction.sleep(3000);
		blnIsLogoutSuccessfull = objExefunction.isDisplayed(txt_UserName, "User Name Text box on Login Page");

		if (blnIsLogoutSuccessfull)
		{
			LOGGER.info("User Successfully logged out from cockpit");
			return true;
		} 
		else 
		{
			throw new ElementNotFoundException("Exception occured while logout from Cockpit", LOGGER);		
		}
	}
}
