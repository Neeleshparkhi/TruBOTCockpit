/**
 * 
 */
package functionalLibrary;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import batchExecution.DriverScript;
import exceptions.ElementNotFoundException;
import pageObject.IBotStation;
import pageObject.IDashboard;
import utility.FL_GeneralUtils;

/**
 * @author aditya.soni
 *
 */
public class BotStation extends DriverScript implements IDashboard,IBotStation
{
	private WebDriver driver;
	private Logger LOGGER;
	protected WebDriverWait wait;
	private FL_GeneralUtils objExefunction;

	public BotStation(WebDriver driver, WebDriverWait wait, Logger LOGGER) 
	{
		this.driver = driver;
		this.wait = wait;
		this.LOGGER = LOGGER;
		objExefunction = new FL_GeneralUtils(driver, wait, LOGGER);
	}

	/**
	 * Method to Click on BotStation Button.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickBotStation() throws ElementNotFoundException
	{
		objExefunction.mouserHover(spn_botStationManagement, "Bot Station Button on Dashboard");
		objExefunction.clickOnElement(spn_BotStation, "Bot Station Button on Dashboard");
		objExefunction.sleep(2000);
	}

	/**
	 * Method to Click on Add BotStation Button.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickAddBotStation() throws ElementNotFoundException
	{
		objExefunction.clickOnElement(spn_addBotStation, "Add Bot Station Button on Bot Station Page");
		objExefunction.sleep(2000);
	}

	/**
	 * Method to Select BotStation type.
	 * @param strStationType  BotStation Type eg:Laptop, Workstation,Cloud Machine
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void selectBotStationType(String strStationType) throws ElementNotFoundException
	{
		objExefunction.clickOnElement(list_addBotStation_StationType, "Bot Station Type List");
		objExefunction.clickOnElement(By.xpath("//*[@class='dx-item dx-list-item']//self::div[contains(text(),'"+strStationType+"')]"), 
				strStationType+" as Bot Station Type");
		objExefunction.sleep(2000);
	}

	/**
	 * Method to Select Credential.
	 * @param strCredential  Credential which is already added in Credential Page
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void selectCredential(String strCredential) throws ElementNotFoundException
	{
		objExefunction.clickOnElement(list_addBotStation_CredentialType, "Bot Station Credential List");
		objExefunction.clickOnElement(By.xpath("//*[@class='dx-item dx-list-item']//self::div[contains(text(),'"+strCredential+"')]"), 
				strCredential+" as Bot Station Credential");
		objExefunction.sleep(2000);
	}

	/**
	 * Method to Test Connection.
	 * @param strCredential  Credential which is already added in Credential Page
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public boolean testConnection(By element,String strIP) throws ElementNotFoundException
	{
		try 
		{
			WebElement objTestConnection = wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			if (objTestConnection != null)
			{
				LOGGER.info("Connection Tested Successfully");
				return true;
			}
			else
			{
				throw new ElementNotFoundException("Test Connection failed for IP address "+strIP, LOGGER);
			}
		} 
		catch (Exception e) 
		{
			throw new ElementNotFoundException("Test Connection failed for IP address "+strIP, LOGGER);
		}
	}

	/**
	 * Method to Click on Save button on Add Bot Station form.
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public boolean clickSaveOnAddBotStationForm() throws ElementNotFoundException
	{
		objExefunction.pageScrollDowntoElement(spn_addBotStation_Save);
		objExefunction.clickOnElement(spn_addBotStation_Save, "Save button on Add Bot station form");
		objExefunction.waitUntil(ExpectedConditions.elementToBeClickable(spn_addBotStation), "Add Bot Station button on Bot Station Grid");
		return true;
	}

	/**
	 * Method to add Bot Station on Add Bot Station form.
	 * @param strName  BotStation Name
	 * @param strStationType  BotStation Type
	 * @param strDescription  BotStation Description
	 * @param strCredential  Credential of BotStation
	 * @param strIPAddress  IP Address of BotStation
	 * @param strPortNumber  Port Number
	 * @param strActiveFrom  Active from Date
	 * @param strActiveTo  Active to Date
	 * @return boolean blnIsBotStationAddSuccesfull
	 */
	public boolean addBotStation(String strName,String strStationType,String strDescription,String strCredential,
			String strIPAddress,String strPortNumber,String strActiveFrom,String strActiveTo ) throws ElementNotFoundException
	{
		boolean blnIsBotStationAddSuccesfull = false;
		try
		{
			clickAddBotStation();
			objExefunction.setText(txt_addBotStation_Name, "Bot Station Name", strName);
			selectBotStationType(strStationType);
			driver.findElement(txt_addBotStation_Name).click();
			objExefunction.setText(txtArea_addBotStationDescription, "Bot Station Description", strDescription);
			selectCredential(strCredential);
			objExefunction.pageScrollDowntoElement(spn_addBotStation_TestConnection);
			objExefunction.setText(txt_addBotStation_IpAddress, "Ip Address Field", strIPAddress);
			if (!strPortNumber.isEmpty())
			{
				objExefunction.setText(txt_addBotStation_PortNumber, "Port Field", strPortNumber);
			} 

			objExefunction.dateHandler(txt_addBotStation_ActiveFromDate, strActiveFrom, "Active From Date");
			objExefunction.dateHandler(txt_addBotStation_ActiveToDate, strActiveTo, "Active To Date");
			objExefunction.clickOnElement(spn_addBotStation_TestConnection, "Test Connection Button");

			if (testConnection(txt_addBotStation_MacAddress, strIPAddress)) 
			{
				blnIsBotStationAddSuccesfull = clickSaveOnAddBotStationForm();
			}
			else
			{
				throw new ElementNotFoundException("Exception Occured while Adding the bot Station  ",LOGGER);
			}
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured while Adding the bot Station  ",LOGGER);
		}

		return blnIsBotStationAddSuccesfull;
	}
}
