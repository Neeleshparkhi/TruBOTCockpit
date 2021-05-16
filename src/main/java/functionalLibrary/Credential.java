/**
 * 
 */
package functionalLibrary;

import batchExecution.DriverScript;
import exceptions.ElementNotFoundException;
import pageObject.ICredential;
import pageObject.IDashboard;
import utility.FL_GeneralUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.mail.imap.Rights;

/**
 * @author aditya.soni
 *
 */
public class Credential extends DriverScript implements IDashboard,ICredential 
{
	private WebDriver driver;
	private Logger LOGGER;
	protected WebDriverWait wait;
	private FL_GeneralUtils objExefunction;

	public Credential(WebDriver driver, WebDriverWait wait, Logger LOGGER) 
	{

		this.driver = driver;
		this.wait = wait;
		this.LOGGER = LOGGER;
		objExefunction = new FL_GeneralUtils(driver, wait, LOGGER);
	}
	
	/**
	 * Method to Click on Credential Button.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickCredential() throws ElementNotFoundException
	{
		objExefunction.clickOnElement(spn_credential, "Credential Button on Dashboard");
		objExefunction.sleep(4000);
	}

	/**
	 * Method to Click on Add Credential Button.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickAddCredential() throws ElementNotFoundException
	{
		objExefunction.clickOnElement(btn_addCredential, "Add Credential button Process Button on Dashboard");
		objExefunction.sleep(2000);
	}
	

	/**
	 * Method to Click on Save button on Add Credential form.
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public boolean clickSaveOnAddCredentialForm() throws ElementNotFoundException
	{
		objExefunction.clickOnElement(btn_addCredential_Save, "Save button on Add Credential form");
		objExefunction.waitUntil(ExpectedConditions.elementToBeClickable(btn_addCredential_Save), "Add Credential button on Credential Grid");
		return true;
	}
	
	/**
	 * Method to insert description on Add Credential form.
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public void enterDescription(String strDescription) throws ElementNotFoundException
	{
		objExefunction.setText(txt_addCredential_Description, "Description on Add credential form", strDescription);
	}
	/**
	 * Method to add credential on Add Credential form.
	 * @param strName  Credential Name
	 * @param strGroupName  Group Name
	 * @param strActiveFromDate  Active From Date
	 * @param strActiveToDate  Active To Date
	 * @param strDescription  Description
	 * @param strUserName  User Name
	 * @param strPassword  Password
	 * @return 
	 * @return void
	 */
	public boolean addCredential(String strName,String strGroupName,String strActiveFromDate,String strActiveToDate,String strDescription,String strUserName,String strPassword,String strStatus) throws ElementNotFoundException
	{
		Boolean blnIsProcessAddSuccesfull = false;
		clickCredential();
		clickAddCredential();
		objExefunction.setText(txt_addCredential_Name, "Credential name in ad credential form", strName);
		selectGroup(strGroupName);
		objExefunction.dateHandler(txt_addCredential_ActiveFromDate, strActiveFromDate, "Active From Date");
		objExefunction.dateHandler(txt_addCredential_ActiveToDate, strActiveToDate, "Active To Date");
		enterDescription(strDescription);
		objExefunction.pageScrollDowntoElement(btn_addCredential_Save);
		objExefunction.setText(txt_addCredential_UserName, "User Name Add credential form", strUserName);
		objExefunction.setText(txt_addCredential_Pasword, "PAssword on Add credential form", strPassword);
		objExefunction.clickOnElement(btn_addCredential_Save, "Save button on Add credential form");
		blnIsProcessAddSuccesfull = objExefunction.isDisplayed(btn_addCredential, "Add Credential Button");
		if (blnIsProcessAddSuccesfull) 
		{
			LOGGER.info("Successfully add Credential in add Credential form");
		}
		else 
		{
			throw new ElementNotFoundException("Exception Occured while adding Credential  ",LOGGER);
		}
		return true;
	}
	/**
	 * Method to select the group Using Dynamics XPATH on Add Process form.
	 * @param strGroupName  Group Name
	 * @return void
	 */
	public void selectGroup(String strGroupName) throws ElementNotFoundException
	{
		String[] str = strGroupName.split(",");
		for(int k = 0; k < str.length; k++)
		{
			try 
			{
				WebElement objGroup = objExefunction.findElement(By.xpath("//div[contains(text(),'"+str[0]+"')]"), strGroupName+" Group");
				objGroup.click();
			} 
			catch (Throwable e)
			{
				throw new ElementNotFoundException("Exception Occured while Selecting the group as "+str +" "+e,LOGGER);
			}
		}
	}
	
	/**
	 * Method to insert Date on Add/Edit credential Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void insertDateOnCredentialForm(String strDateType,String strNoOfDays) throws ElementNotFoundException
	{ 
		if (strDateType.equalsIgnoreCase("ActiveFrom")) 
		{
			objExefunction.dateHandler(txt_addCredential_ActiveFromDate, strNoOfDays, "Active From Date");
		} 
		else if (strDateType.equalsIgnoreCase("ActiveTo"))
		{
			objExefunction.dateHandler(txt_addCredential_ActiveToDate, strNoOfDays, "Active To Date");
		}
	}
	
	/**
	 * Method to Verify Date on Add Credential Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void verifyCredentialDate(String strDateType,String noOfDays) throws ElementNotFoundException
	{ 
		String strDate = null;	
		int i = Integer.parseInt(noOfDays);

		if (strDateType.equalsIgnoreCase("ActiveFrom"))
		{
			strDate = objExefunction.findElement(txt_addCredential_ActiveFromDate, "Active To date on Add credential form").getAttribute("value");
		}
		else 
		{
			strDate = objExefunction.findElement(txt_addCredential_ActiveToDate, "Active To date on Add credential form").getAttribute("value");
		}
		if (strDate != null && !strDate.isEmpty() && strDate.equalsIgnoreCase(objExefunction.getFutureDate(i,"dd-MMM-yyyy"))) 
		{
			LOGGER.info("Succesly verify"+strDateType+" Date as Current date" + noOfDays +"30 Days as ");
			strDate = null;
		}
		else
		{
			throw new ElementNotFoundException("Exception Occured verifying Active To date on Add credential form",LOGGER);
		}
	}
	
	/**
	 * Method to Verify Active From on Add Process Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void verifyCredentialActiveFromDate(String noOfDays) throws ElementNotFoundException
	{ 
		String strActiveFromDate = null;	
		int i = Integer.parseInt(noOfDays);
		strActiveFromDate = objExefunction.findElement(txt_addCredential_ActiveFromDate, "Active To date on Add credential form").getAttribute("value");
		if (strActiveFromDate != null && !strActiveFromDate.isEmpty() && strActiveFromDate.equalsIgnoreCase(objExefunction.getFutureDate(i,"dd-MMM-yyyy"))) 
		{
			LOGGER.info("Succesly verify Active from Date as Current date  as "+strActiveFromDate);
			strActiveFromDate = null;
		}
		else
		{
			throw new ElementNotFoundException("Exception Occured verifying Active from date on Add credential form",LOGGER);
		}
	}
	
	/**
	 * Method to Click on Edit button on Credential.
	 * @param strcredentialName  Credential Name
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public void clickEditCredential(String strcredentialName) throws ElementNotFoundException
	{
		objExefunction.clickOnElement(By.xpath("//*[contains(text(),'"+strcredentialName+"')]//parent::tr//self::a"), "Edit Buton on Credential having Name as "+strcredentialName);	
	}
	

	
}
