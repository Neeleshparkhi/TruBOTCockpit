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
import pageObject.IBot;
import pageObject.IDashboard;
import utility.FL_GeneralUtils;

/**
 * @author aditya.soni
 *
 */
public class Bot extends DriverScript implements IDashboard,IBot 
{
	private WebDriver driver;
	private Logger LOGGER;
	protected WebDriverWait wait;
	private FL_GeneralUtils objExefunction;

	public Bot(WebDriver driver, WebDriverWait wait, Logger LOGGER) 
	{
		this.driver = driver;
		this.wait = wait;
		this.LOGGER = LOGGER;
		objExefunction = new FL_GeneralUtils(driver, wait, LOGGER);
	}

	/**
	 * Method to Click on Bot Button on Dashboard.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickBot() throws ElementNotFoundException
	{
		objExefunction.mouserHover(spn_botManagement, "Bot Button on Dashboard");
		objExefunction.clickOnElement(spn_Bot, "Bot Button on Dashboard");
		objExefunction.sleep(2000);
	}
	
	/**
	 * Method to Click on AddBot Button on Bot Page.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickaddBot() throws ElementNotFoundException
	{
		objExefunction.clickOnElement(spn_addBot, "Add Bot Button on Bot Page");
		objExefunction.sleep(2000);
	}


	/**
	 * Method to Click on Add Bot Button.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickAddBotStation() throws ElementNotFoundException
	{
		objExefunction.clickOnElement(spn_addBot, "Add Bot Button on Bot Station Page");
		objExefunction.sleep(2000);
	}

	/**
	 * Method to Select BotStation on Add Bot Form .
	 * @param strStation BotStation
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void selectBotStation(String strStation) throws ElementNotFoundException
	{
		objExefunction.clickOnElement(By.xpath("//*[@class='dx-item dx-list-item']//self::div[contains(text(),'"+strStation+"')]"), 
				strStation+" as Bot Station");
		objExefunction.sleep(2000);
	}

	/**
	 * Method to Select Bot Status on Add Bot Form .
	 * @param strStatus  Bot Status
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void selectBotStatus(String strStatus) throws ElementNotFoundException
	{
		if (!strStatus.equalsIgnoreCase("ON"))
		{
			objExefunction.clickOnElement(switch_addBot_Status,"Bot Status");
		} 		
		objExefunction.sleep(2000);
	}

	/**
	 * Method to Select Attended Status on Add Bot Form .
	 * @param strStatus  Bot Status 
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void selectAttendedStatus(String strAttended) throws ElementNotFoundException
	{
		if (strAttended.equalsIgnoreCase("ON"))
		{
			objExefunction.clickOnElement(switch_addBot_Status,"Bot Status");
		} 		
		objExefunction.sleep(2000);
	}

	/**
	 * Method to Select Process .
	 * @param strProcess  Process
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void selectProcess(String strProcess) throws ElementNotFoundException
	{
		objExefunction.clickOnElement(list_addBot_Process,"Proces Fields");
		objExefunction.setText(list_addBot_Process, "Proces Fields", strProcess);
		objExefunction.sleep(2000);
		objExefunction.clickOnElement(By.xpath("//*[contains(text(),'"+strProcess+"')]"), strProcess+" as Process");		
	}
	
	/**
	 * Method to Insert BotName on Add Bot Form .
	 * @param strName  BotName
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void insertName(String strName) throws ElementNotFoundException
	{
		objExefunction.setText(txt_addBot_Name, "Bot Name", strName);	
	}
	
	/**
	 * Method to Insert Bot Description on Add Bot Form .
	 * @param strstrDescriptionName  Bot Description
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void insertBotDescription(String strName) throws ElementNotFoundException
	{
		objExefunction.setText(txt_addBot_Description, "Bot Description", strName);	
	}
	
	/**
	 * Method to Click on Save button on Add Bot Form .
	 * @return void
	 * throws ElementNotFoundException
	 */
	public boolean clickSave(String srtBotName) throws ElementNotFoundException
	{
		objExefunction.clickOnElement(btn_addBot_Save, "Save buton");
		wait = new WebDriverWait(driver, 150);
		try
		{
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='tableCard']//self::*[contains(text(),'"+srtBotName+"')]")));
		}
		catch (Exception e) 
		{
			System.out.println();
		}
		objExefunction.waitUntil(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='tableCard']//self::*[contains(text(),'"+srtBotName+"')]")), "Newly Added Bot on Bot Grid");
		return true;
	}
	
	/**
	 * Method to add Bot on Add Bot  form.
	 * @param strStats  Bot Status	i.e. ON or OFF
	 * @param strAttended  Attended Type
	 * @param strProcess  Process Name
	 * @param strBotStation  BotStation Name
	 * @param strName  Bot Name
	 * @param strDescription Bot Description
	 * @param strActiveFrom  Active from Date
	 * @param strActiveTo  Active to Date
	 * @return 
	 * @return boolean
	 */
	public void addBot(String strStatus,String strAttended,String strProcess,String strBotStation,
			String strName,String strDescription,String strActiveFrom,String strActiveTo ) throws ElementNotFoundException
	{
		boolean blnIsBotStationAddSuccesfull = false;
		try
		{
			clickBot();
			clickaddBot();
			selectBotStatus(strStatus);
			selectAttendedStatus(strAttended);
			selectProcess(strProcess);
			selectBotStation(strBotStation);
			insertName(strName);
			insertBotDescription(strDescription);
			objExefunction.pageScrollDowntoElement(btn_addBot_Save);
			objExefunction.dateHandler(txt_addBotStation_ActiveFromDate, strActiveFrom, "Active From Date");
			objExefunction.dateHandler(txt_addBotStation_ActiveToDate, strActiveTo, "Active To Date");
			clickSave(strName);
			blnIsBotStationAddSuccesfull = true;
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured while Adding the bot Station  ",LOGGER);
		}

		
	}
	
	/**
	 * Method to Click on Play button for Specific Bot.
	 * @param strBotName  Bot Name
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void playBot(String strBotName) throws ElementNotFoundException
	{
		objExefunction.clickOnElement(By.xpath("//*[contains(text(),'"+strBotName+"')]//parent::tr//self::a[@title='play']"), strBotName+" Bot");	
	}
	
	

}
