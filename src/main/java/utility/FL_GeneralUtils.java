package utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


import org.openqa.selenium.support.ui.WebDriverWait;

import batchExecution.DriverScript;
import exceptions.ElementNotFoundException;


public class FL_GeneralUtils
{ 

	WebDriver driver;
	WebDriverWait wait;
	Logger LOGGER;
	JavascriptExecutor js;

	//	Initializing the driver object
	public FL_GeneralUtils(WebDriver driver, WebDriverWait wait, Logger LOGGER) 
	{

		this.driver = driver;
		this.wait = wait;
		this.LOGGER = LOGGER;
		js = (JavascriptExecutor) driver;
	}

	public FL_GeneralUtils() 
	{

	}

	//This Method Find the Web element Based upon Passed parameters
	public WebElement checkElementExists(By strValue,String strObjName)
	{
		WebElement element = null;
		try 
		{
			element = driver.findElement(strValue);
		}
		
		catch (Exception e) 
		{
			LOGGER.warning("Unable to found "+strObjName+" on The Screen"+e);

		}
		return element;
	}
	
	//This Method Find the Web element Based upon Passed parameters
		public WebElement checkElementExists(String strObjName,By strValue)
		{
			WebElement element = null;
			try 
			{
				element = driver.findElement(strValue);
			}

			catch (Exception e) 
			{
				LOGGER.warning("Unable to found "+strObjName+" on The Screen"+e);

			}
			return element;
		}

	//This Method Find the Web element Based upon Passed parameters
	public WebElement findElement(By strValue,String strObjName) throws ElementNotFoundException
	{
		WebElement element = null;
		try {

			Thread.sleep(250);
			element = driver.findElement(strValue);
			Thread.sleep(250);
			if (element != null) 
			{
				LOGGER.info("Successfully found "+strObjName+" on The Screen");
				return element;
			}
			else
			{
				throw new ElementNotFoundException("Unable to Find found "+strObjName+" on The Screen", LOGGER);	
			}
		}		
		catch (Exception e) 
		{
			throw new ElementNotFoundException("Exception Occured while Find Element"+strObjName+" on The Screen"+e, LOGGER);

		}

	}

	//This Method Find the Web element Based upon Passed parameters
	public List<WebElement> findElements(By strValue,String strObjName) throws ElementNotFoundException
	{
		List<WebElement> elementName;
		try {

			Thread.sleep(250);
			elementName = driver.findElements(strValue);
			Thread.sleep(250);
			if (!elementName.isEmpty()) 
			{
				LOGGER.info("Successfully found "+strObjName+" on The Screen");
				return elementName;
			}
			else
			{
				throw new ElementNotFoundException("Unable to Find found "+strObjName+" on The Screen", LOGGER);	
			}
		}		
		catch (Exception e) 
		{
			throw new ElementNotFoundException("Exception Occured while Find Element"+strObjName+" on The Screen"+e, LOGGER);

		}

	}


	//This Method to get the Text from Textbox
	public String getText(By element,String strObjectName) throws ElementNotFoundException
	{
		String iptext=null;
		try {
			iptext=driver.findElement(element).getText();
			LOGGER.info("Successfully got the Text from "+strObjectName);
			return iptext;
		} catch (Exception e) {
			throw new ElementNotFoundException("Exception occured at getText from "+strObjectName+" "+e, LOGGER);
		}

	}


	//This Method Inserts the text in the Textbox
	public void setText(By element,String strObjectName, String strValue) throws ElementNotFoundException
	{
		try {
			if (element != null)
			{
				driver.findElement(element).clear();
				driver.findElement(element).sendKeys(strValue);
				LOGGER.info("Successfully Entered Text "+strValue+" at "+strObjectName);
			}
		} 
		catch (NullPointerException e)
		{
			throw new ElementNotFoundException("Doesnot find data for "+strObjectName+" "+e, LOGGER);
		}
		catch (Exception e) 
		{
			throw new ElementNotFoundException("Exception occured at SetText "+strValue+" at "+strObjectName+" "+e, LOGGER);
		}

	}


	//This Method Inserts the text in the Textbox
	public boolean setText(String strXpath,String strValue,String strObjectName) throws ElementNotFoundException
	{
		try 
		{
			if (strXpath != null)
			{
				driver.findElement(By.xpath(strXpath)).clear();
				driver.findElement(By.xpath(strXpath)).sendKeys(strValue);
				LOGGER.info("Successfully Entered Text "+strValue+" at "+strObjectName);
			}
		}
		catch (NullPointerException e)
		{
			throw new ElementNotFoundException("Doesnot find data for "+strObjectName+" "+e, LOGGER);
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception occured at SetText "+strValue+" at "+strObjectName+" "+e, LOGGER);
		}
		return true;

	}

	/*Created By Aditya*/
	public boolean isEnabled(By element, String strObjName) throws ElementNotFoundException
	{
		WebElement objElement = null;
		Boolean blnIsEnabled = false;
		try 
		{
			objElement = driver.findElement(element);
			blnIsEnabled = objElement.isEnabled();
		}
		catch (Exception e2) 
		{
			throw new ElementNotFoundException("Exception occured at wait Until "+strObjName+" : "+e2, LOGGER);
		}

		if (blnIsEnabled)
		{
			LOGGER.info("Successfully Found "+strObjName+" is Enabled on UI");	
			return true;
		}

		else 
		{
			throw new ElementNotFoundException(strObjName+" is Not Enabled on UI", LOGGER);			
		}
	}


	//This Method perform Mouser hover on the element  having parameter By 
	public boolean mouserHover(By element,String strObjName)throws ElementNotFoundException
	{
		try
		{
			sleep(1000);
			waitUntil(ExpectedConditions.visibilityOfElementLocated(element), "wait for element to be visible");
			Actions action = new Actions(driver);
			WebElement we = driver.findElement(element);
			action.moveToElement(we).build().perform();
			LOGGER.info("successfully dragged the Mouser Pointer one Element: "+strObjName);
			return true;
		} 
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception occured while Perform Mouse hover on the element with Name "+strObjName+" : "+e, LOGGER);
		}
	}

	//This Method click on element having parameter By 
	public boolean clickOnElement(By element,String strObjName)throws ElementNotFoundException
	{
		try
		{
			sleep(1000);
			//pageScrollDowntoElement(element);
			waitUntil(ExpectedConditions.visibilityOfElementLocated(element), strObjName+" element to be visible");
			waitUntil(ExpectedConditions.elementToBeClickable(element), strObjName+" element to be clickable");
			driver.findElement(element).click();     
			LOGGER.info("successfully clicked on the Element: "+strObjName);
			return true;
		} 
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception occured at ClickOnElement with Name "+strObjName+" : "+e, LOGGER);
		}
	}

	//This Method click on element having parameter By 
	public void clickOnElements(By element,String strObjName)throws ElementNotFoundException
	{
		boolean blnIsClicked = false;
		sleep(1000);
		//pageScrollDowntoElement(element);
		waitUntil(ExpectedConditions.visibilityOfElementLocated(element), strObjName+" element to be visible");
		waitUntil(ExpectedConditions.elementToBeClickable(element), strObjName+" element to be clickable");
		List<WebElement> eleList = driver.findElements(element);
		if (!eleList.isEmpty()) 
		{
			for (WebElement webElement : eleList) 
			{
				try 
				{
					webElement.click();
					LOGGER.info("successfully clicked on the Element: "+strObjName);
					blnIsClicked = true;
					break;
				} 
				catch (Exception e) 
				{
				}		
			}
		}
		else
		{
			throw new ElementNotFoundException("Exception occured at ClickOnElement with Name "+strObjName+" : ", LOGGER);
		}

		if (!blnIsClicked)
		{
			throw new ElementNotFoundException("Exception occured at ClickOnElement with Name "+strObjName+" : ", LOGGER);
		} 


	}

	//This Method click on element having parameter By 
	public boolean clickOnElement(String element,String strObjName)throws ElementNotFoundException
	{
		try
		{
			driver.findElement(By.xpath(element)).click();     
			LOGGER.info("successfully clicked on the Element: "+strObjName);
		}
		catch (Exception e) 
		{
			throw new ElementNotFoundException("Exception occured at ClickOnElement with Name "+strObjName+" : "+e, LOGGER);
		}
		return true;
	}


	//This Method click on element having parameter WebElement
	public void clickOnElement(WebElement element,String strObjName)throws ElementNotFoundException
	{
		try 
		{
			waitUntil(ExpectedConditions.visibilityOf(element), "wait for element to be visible");
			waitUntil(ExpectedConditions.elementToBeClickable(element), "wait for element to be clickable");
			element.click();     
			LOGGER.info("successfully clicked on the Element: "+strObjName);
		} catch (Exception e) {
			throw new ElementNotFoundException("Exception occured at ClickOnElement with Name "+strObjName+" : "+e, LOGGER);
		}
	}

	//This Method clicks on Element and retrun boolean type
	public boolean clickOnElementWarn(By element,String strObjName)
	{
		try
		{
			driver.findElement(element).click();
			LOGGER.info("successfully click the Element: "+strObjName);
			return true;
		}
		catch (Throwable e)
		{
			LOGGER.warning("Entered Category is not Found in category List");
			return false;
		}
	}


	public boolean checkTextinElements(By element, String strObjName, String StrMessage)throws ElementNotFoundException
	{
		Boolean blncheck = false;
		try 
		{
			List<WebElement> webelments = driver.findElements(element);
			for (WebElement webElement : webelments)
			{
				if(webElement.getText().contains(StrMessage)) {
					blncheck = true;
					LOGGER.info("Succesfully verify "+StrMessage+" is Present in "+strObjName);
					break;
				}
			}			
			return blncheck;
		} catch (Exception e) {
			throw new ElementNotFoundException("Exception occured at check Text with Name "+strObjName+" : "+e, LOGGER);
		}
	}


	//This Method Verify text in WebElement which is find by 'find' Method Created By Aditya	
	public boolean checkTextinSingleElement(By element, String strObjName, String StrMessage)throws ElementNotFoundException
	{
		Boolean blncheck = false;
		sleep(2000);
		try 
		{
			WebElement webelment = driver.findElement(element);	
			String strText = webelment.getText();
			String strValue = webelment.getAttribute("value");
			if(strText.contains(StrMessage) || strValue.contains(StrMessage)) 
			{
				blncheck = true;
				LOGGER.info(StrMessage+" is found in "+strObjName);
			}			
			return blncheck;
		} 
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception occured at check Text with Name "+strObjName+" : "+e, LOGGER);
		}
	}

	//This Method Verify Status toggle in WebElement which is find by 'find' Method Created By Aditya	
	public boolean checkToggleState(By element, String strObjName, String StrMessage)throws ElementNotFoundException
	{
		Boolean blncheck = false;
		try 
		{
			WebElement webelment = driver.findElement(element);	
			String strValue = webelment.getAttribute("aria-label");
			if(strValue.contains(StrMessage)) 
			{
				blncheck = true;
				LOGGER.info("Succesfuly verify Toggle State as:"+StrMessage+" in "+strObjName);
			}			
			return blncheck;
		} 
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception occured at check Text with Name "+strObjName+" : "+e, LOGGER);
		}
	}

	public void waitUntil(ExpectedCondition<WebElement> e, String strObjName) throws ElementNotFoundException
	{
		sleep(1);
		try {
			wait.until(e);
			LOGGER.info("Successfully Found "+strObjName);
		} catch (Throwable e2) {
			throw new ElementNotFoundException("Exception occured at wait Until "+strObjName+" : "+e2, LOGGER);
		}
	}


	public void waitUntilUsingRefresh(By element, String strObjName) throws ElementNotFoundException
	{
		int intWidth = 0;
		int intHeight = 0;
		WebElement objElement = null;
		try
		{
			for (int i = 0; i < 5; i++) 
			{
				try 
				{
					objElement = driver.findElement(element);
					intWidth = objElement.getSize().getWidth();
					intHeight = objElement.getSize().getWidth();
				}
				catch (Exception e)
				{
					driver.navigate().refresh();
					Thread.sleep(500);
					LOGGER.info("Page Refreshed "+i+" Times for "+strObjName);
				}
				if (intWidth > 0 && intHeight > 0) 
				{
					LOGGER.info("Successfully Found "+strObjName);
					break;
				}	
				else
				{
					driver.navigate().refresh();
					Thread.sleep(500);
					LOGGER.info("Page Refreshed "+i+" Times for "+strObjName);
				}
			}
		} 
		catch (Exception e2) 
		{
			throw new ElementNotFoundException("Exception occured at wait Until Refresh "+strObjName+" : "+e2, LOGGER);

		}
	}
	public void waitUntilAll(ExpectedCondition<List<WebElement>> expectedCondition, String strObjName) throws ElementNotFoundException
	{
		try {
			wait.until(expectedCondition);
			LOGGER.info("Successfully Found "+strObjName);
		} catch (Exception e2) {
			throw new ElementNotFoundException("Exception occured at wait Until "+strObjName+" : "+e2, LOGGER);
		}
	}


	//this Method gives the options from Dropdown
	public List<WebElement> getOptions(By element)throws ElementNotFoundException
	{
		try {

			Select seloption =  new Select(driver.findElement(element));
			return seloption.getOptions();			
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured at getOptions "+e, LOGGER);
		}
	}



	//this Method selects the option by visible text from Dropdown
	public boolean selectoption(By element,String strOption)throws ElementNotFoundException
	{
		try 
		{		
			Select seloption =  new Select(driver.findElement(element));		

			seloption.selectByVisibleText(strOption);

			for (int i = 0; i < 50; i++) {
				Thread.sleep(100);
				if(!(seloption.getFirstSelectedOption().getText().toString().equalsIgnoreCase(strOption)))
				{
					seloption.selectByVisibleText(strOption);
				}
				else
				{
					break;
				}
			}
			WebElement selectedElement =  seloption.getFirstSelectedOption();

			if ((selectedElement.getText().toString().equalsIgnoreCase(strOption))) 
			{

				LOGGER.info("Succesfully Selected option "+strOption);
				return true;
			}
			else
			{
				throw new ElementNotFoundException("Unable to Selecte option "+strOption, LOGGER);
			}
		} 
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured at Selecting "+strOption+" "+e, LOGGER);
		}
	}


	public boolean checkOption(By element,String strOption)
	{
		boolean blnOptionFound = false;
		Select seloption =  new Select(driver.findElement(element));
		List<WebElement> lstWebElement = seloption.getOptions();
		for (WebElement webElement : lstWebElement) {
			if(webElement.getText().toLowerCase().contains(strOption.toLowerCase()))
			{
				LOGGER.info(strOption+" is found in List");
				blnOptionFound = true;
				break;
			}
		}
		if(!blnOptionFound)
		{
			LOGGER.info(strOption+" is not found in List");
			return false;
		}
		return true;
	}



	//this Method selects the value from Dropdown
	public boolean selectoption(WebElement element,String strOption)throws ElementNotFoundException
	{
		try 
		{
			boolean blnOptionFound = false;
			Select seloption =  new Select(element);
			List<WebElement> lstWebElement = seloption.getOptions();
			for (WebElement webElement : lstWebElement) {
				if(webElement.getText().toLowerCase().contains(strOption.toLowerCase()))
				{
					LOGGER.info(strOption+" is found in List");
					blnOptionFound = true;
					break;
				}
			}
			if(!blnOptionFound)
			{
				throw new ElementNotFoundException("option "+strOption+" is not present in the List", LOGGER);
			}

			seloption.selectByVisibleText(strOption);

			for (int i = 0; i < 5; i++) {
				if(!(seloption.getFirstSelectedOption().getText().toString().equalsIgnoreCase(strOption)))
				{

					Thread.sleep(1000);
					seloption.selectByVisibleText(strOption);
					Thread.sleep(1000);
					if(!(seloption.getFirstSelectedOption().getText().toString().equalsIgnoreCase(strOption)))
					{
						break;
					}
				}

			}

			seloption.selectByVisibleText(strOption);
			WebElement selectedElement =  seloption.getFirstSelectedOption();
			if ((selectedElement.getText().toString().equalsIgnoreCase(strOption))) 
			{
				LOGGER.info("Succesfully Selected option "+strOption);
				return true;
			}
			else
			{
				throw new ElementNotFoundException("Unable to Selecte option "+strOption, LOGGER);
			}
		} 
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured at Selecting "+strOption+" "+e, LOGGER);
		}
	}


	//This Method Subtract the Current date and Passed parameter & returns the Previous date as the String.
	public String getPreviousDate(int intDays) throws ElementNotFoundException
	{
		String strPreviousDate = null;
		try {
			strPreviousDate = null;
			Calendar c = Calendar.getInstance(); // starts with today's date and time
			c.add(Calendar.DAY_OF_YEAR, intDays);  // advances day by 2
			//				java.util.Date date = c.getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			strPreviousDate = dateFormat.format(c.getTime());

			return strPreviousDate;
		} catch (Exception e)
		{
			throw new ElementNotFoundException("Exception occured while getting Previous date "+e, LOGGER);
		}

	}

	//This Method returns the Current date as a String.
	public String getCurrentDate() throws ElementNotFoundException
	{
		String strCurrentDate = null;
		try 
		{		
			Date date = new Date();
			strCurrentDate = new SimpleDateFormat("yyyy/MM/dd").format(date);

			return strCurrentDate;
		} catch (Exception e)
		{				
			throw new ElementNotFoundException("Exception occured while getting Current date "+e, LOGGER);
		}

	}


	//This Method returns the Current date as a String.
	public String getCurrentDatewithformat(String strFormat) throws ElementNotFoundException
	{
		String strCurrentDate = null;
		try 
		{		
			Date date = new Date();
			strCurrentDate = new SimpleDateFormat(strFormat).format(date);

			return strCurrentDate;
		} catch (Exception e)
		{				
			throw new ElementNotFoundException("Exception occured while getting Current date "+e, LOGGER);
		}

	}



	//This Method ADD the Current date and Passed parameter & returns the Future date as the String.
	public String getFutureDate(int intDays) throws ElementNotFoundException
	{
		String strFutureDate = null;
		try {
			strFutureDate = null;
			Calendar c = Calendar.getInstance(); // starts with today's date and time
			c.add(Calendar.DAY_OF_YEAR, intDays);  // advances day by intDays
			//				java.util.Date date = c.getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			strFutureDate = dateFormat.format(c.getTime());
		} catch (Exception e)
		{

			throw new ElementNotFoundException("Exception occured while getting Future date "+e, LOGGER);
		}
		return strFutureDate;
	}

	//This Method ADD the Current date and Passed parameter & returns the Future date in dd-MMM-yyyy format as the String.
	public String getFutureDate(int intDays,String strFormat) throws ElementNotFoundException
	{
		String strFutureDate = null;;
		try {
			strFutureDate = null;
			Calendar c = Calendar.getInstance(); // starts with today's date and time
			c.add(Calendar.DAY_OF_YEAR, intDays);  // advances day by intDays
			//				java.util.Date date = c.getTime();
			DateFormat dateFormat = new SimpleDateFormat(strFormat);
			strFutureDate = dateFormat.format(c.getTime());
		} catch (Exception e)
		{

			throw new ElementNotFoundException("Exception occured while getting Future date "+e, LOGGER);
		}
		return strFutureDate;
	}

	//This Method gives the first Selected options from Dropdown
	public String getFirstSelectedOptions(By element) throws ElementNotFoundException
	{
		String strFirstSelctedOption = null;
		try {

			Select seloption =  new Select(driver.findElement(element));
			strFirstSelctedOption = seloption.getFirstSelectedOption().getText().toString();
			if (strFirstSelctedOption != null)
			{
				LOGGER.info("Successfully find first selected option as: "+strFirstSelctedOption);
				return strFirstSelctedOption;
			}
			else
			{
				throw new ElementNotFoundException("Not able to find first selected option from List",LOGGER);				
			}

		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured at getOptions "+e, LOGGER);
		}
	}

	/* This method is created for scrolling Page down to  Element
	 * Aditya Soni
	 */
	/* This method is created for scrolling Page down to  Element
	 */
	public void pageScrollDowntoElement(By elem)
	{
		try 
		{
			//Find element by link text and store in variable "Element"        		
			WebElement Element = driver.findElement(elem);

			//This will scroll the page till the element is found		
			js.executeScript("arguments[0].scrollIntoView();", Element);
			sleep(2000);
		}
		catch (Exception e)
		{
			LOGGER.severe("Exception occured while performing the scroll");
		}
	}

	/* This method is created for scrolling Page Height
	 * Aditya
	 */
	public void pageScrollDowntoHeight()
	{

		//This will scroll the page till the element is found		
		js.executeScript("window.scrollBy(0,-1000)", "");

	}

	/* This method is created for scrolling using sendkeys method.
	 * Aditya.
	 */
	public void sendKeysPageDown(By elem)
	{
		//Find element by link text and store in variable "Element"        		
		WebElement Element = driver.findElement(elem);
		Element.sendKeys("Keys.PAGE_DOWN");

	}
	/*This method is created for refreshing the page
	 * Aditya
	 */
	public void pageRefresh()
	{
		driver.navigate().refresh();
	}


	/*This method is created for clicking the element with javascript clickway
	 * Aditya
	 */
	public void javascriptClickOnElement(WebElement we)
	{
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", we);
	}

	public boolean isSelected(By ele)
	{
		return driver.findElement(ele).isSelected();
	}

	/*This Method Checks the whether the object is displayed on UI created By Aditya */
	public boolean isDisplayed(By element, String strObjName) throws ElementNotFoundException
	{
		WebElement objElement = null;
		Boolean blnIsDisplayed = false;
		try 
		{
			objElement = driver.findElement(element);
			blnIsDisplayed = objElement.isDisplayed();
		}
		catch (Throwable e2) 
		{
			throw new ElementNotFoundException("Exception occured at wait Until "+strObjName+" : "+e2, LOGGER);
		}

		if (blnIsDisplayed)
		{
			LOGGER.info("Successfully Found "+strObjName+" is Displayed on UI");
			return true;
		}

		else 
		{
			throw new ElementNotFoundException(strObjName+" is Not Displayed on UI", LOGGER);
		}
	}

	/*This Method Checks the whether the object is Not displayed on UI created By Aditya */
	public void isNotDisplayed(By element, String strObjName) throws ElementNotFoundException
	{
		WebElement objElement = null;
		Boolean blnIsEnabled = false;
		try 
		{
			sleep(1);
			objElement = driver.findElement(By.xpath("(//*[contains(text(),'4072020')]/parent::tr/following-sibling::tr//self::*[@class='dx-row dx-data-row dx-column-lines']//self::td)//self::a[@title='Parameters']"));
			blnIsEnabled = objElement.isDisplayed();
		}
		catch (Exception e2) 
		{
			LOGGER.info("Successfully Found "+strObjName+" is Not Displayed on UI");
		}
		if (blnIsEnabled)
		{
			throw new ElementNotFoundException(strObjName+" is Displayed on UI", LOGGER);
		}
		else
		{
			LOGGER.info("Successfully Found "+strObjName+" is Not Displayed on UI");
		}
	}

	/*This Method Checks the whether the object is disabled created By Aditya*/
	public boolean isDisabled(By element, String strObjName) throws ElementNotFoundException
	{
		WebElement objElement = null;
		try 
		{
			//modified by Aditya
			objElement = driver.findElement(element);
			if (objElement.isEnabled())
			{
				throw new ElementNotFoundException(strObjName+" is Enabled on UI", LOGGER);
			}           
			else 
			{
				LOGGER.info("Successfully Found "+strObjName+" is not Enabled on UI");
				return true;
			}
		}
		catch (Exception e2) 
		{
			throw new ElementNotFoundException("Exception occured at wait Until "+strObjName+" : "+e2, LOGGER);
		}

	}

	/* Thios method is created :- waiting for element which is to be selected. Aditya*/
	public boolean waitUntilToBeSelected(ExpectedCondition<Boolean> flag, String strObjName) throws ElementNotFoundException
	{
		try {
			wait.until(flag);
			LOGGER.info("Successfully Found "+strObjName);
			return true;
		} catch (Exception e2) {
			throw new ElementNotFoundException("Exception occured at wait Until "+strObjName+" : "+e2, LOGGER);
		}
	}

	public void waituntilselected(Boolean blnChecked, By element)
	{
		for (int i = 0; i < 10; i++) {
			if(blnChecked == isSelected(element))
			{
				break;
			}
			try {Thread.sleep(200);} catch (InterruptedException e) {LOGGER.severe("Exception Occured at Verify Readmission date : "+e);}
		}
	}


	/*This method will Pause the execution for the certain time interval created by Aditya*/
	public void sleep(int intMiliSeconds)
	{
		try
		{
			Thread.sleep(intMiliSeconds);
		}
		catch (Exception e)
		{
		}	
	}
	//This method selects multiple checkboxes on page
	//AdityaUdherani_25/7/2018
	public void selectMultipleCheckBoxes(String ...strFields) throws ElementNotFoundException
	{

		WebElement objFieldElement = null;
		for (String strField : strFields)
		{
			objFieldElement = driver.findElement(By.xpath("//span[text()='"+strField+"']/preceding-sibling::input"));

			if (objFieldElement != null) 
			{
				clickOnElement(objFieldElement, strField);
			}
			else 
			{
				LOGGER.severe("Not able to find checkbox on UI");
				throw new ElementNotFoundException(strField+" is Not available on UI", LOGGER);
			}
		}

		LOGGER.fine("Successfully selected options on UI");
	}

	//This method checks presence of element and returns boolean value
	public boolean isPresent(By element, String strObjName) {

		try {

			driver.findElement(element);
			return true;
		}
		catch(Exception e) {

			return false;
		}
	}

	//This method checks message  of element and returns boolean value
	public boolean verifyErrorMsg(String strMessage) throws ElementNotFoundException 
	{
		try 
		{
			WebElement objGroup = findElement(By.xpath("//div[contains(text(),'"+strMessage+"')]"), strMessage);
			if (objGroup.isDisplayed())
			{
				LOGGER.info("Successfully verify error message as "+strMessage);
			}
			else
			{
				throw new ElementNotFoundException(strMessage+" is Not displayed on UI", LOGGER);
			}
		} catch (Exception e1) 
		{
			throw new ElementNotFoundException(strMessage+" is Not displayed on UI", LOGGER);
		}
		try
		{
			driver.findElement(By.xpath("//div[@class='dx-button-content'][contains(.,'OK')]")).click();
		} 
		catch (Throwable e)
		{
		}
		return true;
	}


	/**
	 * Method to Click on Select all check box  on Add Process Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickSelectAll(String steExpectedValue) throws ElementNotFoundException
	{
		try 
		{
			WebElement ele = findElement(By.xpath("//*[@class='dx-list-select-all']//self::input"), "Select all Checkbox");
			pageScrollDowntoElement(By.xpath("//*[@class='dx-list-select-all']//self::span"));
			String str = ele.getAttribute("value");
			if (!str.equalsIgnoreCase(steExpectedValue))
			{
				if (str.isEmpty())
				{
					clickOnElement( By.xpath("//div[@class='dx-list-select-all']"), "Select All checkbox");
					sleep(2000);
					clickOnElement( By.xpath("//div[contains(text(),'Select All')]"), "Select All checkbox");
				}
				else
				{
					clickOnElement( By.xpath("//*[@class='dx-list-select-all']//self::span"), "Select All checkbox");
				}

			}
		} 
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception occured at wait Clicking the Select all checkbox "+e, LOGGER);
		}
	}
	/*Wait until Element is visible--Aditya	 */
	public void waitUntilInVisible(By elemennt,String strObjName) throws ElementNotFoundException {
		try {
			wait.until(ExpectedConditions.invisibilityOf(driver.findElement(elemennt)));
			LOGGER.info("Successfully Found "+strObjName);
		} catch (Exception e2) {
			throw new ElementNotFoundException("Exception occured at wait Until "+strObjName+" : "+e2, LOGGER);
		}

	}

	//This method is created to perform Back to previous page for browser
	//Aditya
	public void navigateBack() throws ElementNotFoundException
	{
		try
		{
			sleep(1000);
			driver.navigate().back();
			sleep(1000);
			LOGGER.info("Successfully navigated to previous page");
		}
		catch (Exception e2) 
		{
			throw new ElementNotFoundException("Exception occured while navigating to previous page "+e2, LOGGER);
		}
	}

	//This method is created to verify page url
	//Aditya
	public void verifyPageUrlContains(String strPartialUrl) throws ElementNotFoundException
	{	try
	{
		if (driver.getCurrentUrl().toString().contains(strPartialUrl)) 
		{
			LOGGER.info("Successfully verified Url");
		}
		else 
		{
			throw new ElementNotFoundException("Error in verification of Page url", LOGGER);
		}
	}
	catch (Exception e2) 
	{
		throw new ElementNotFoundException("Exception occured while verifing page url "+e2, LOGGER);
	}
	}

	//This method verifies selected multiple checkboxes on page
	//Aditya
	public void verifySelectedCheckBoxes(String ...strFields) throws ElementNotFoundException
	{
		By objFieldElement = null;
		for (String strValue:strFields)
		{
			objFieldElement = By.xpath("//span[text()='"+strValue+"']/preceding-sibling::input");
			if(isSelected(objFieldElement))
			{
				LOGGER.info("Successfully verified "+strValue+" is selected on UI");
			}
			else
			{
				throw new ElementNotFoundException("Unable to verify "+strValue+" is selected on UI or not", LOGGER);
			}
		}
		LOGGER.fine("Successfully verified selection of options on UI");
	}

	//This method compares text in 2 strings
	public boolean compareText(String strMessage1, String strMessage2)throws ElementNotFoundException
	{
		Boolean blncheck = false;
		try 
		{
			if(strMessage1.equals(strMessage2))	
			{
				blncheck = true;
			}
			LOGGER.fine("Comparing message "+strMessage1+" and "+strMessage2);
			return blncheck;
		} 
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception occured while compoaring strings"+e, LOGGER);
		}
	}

	/*This Method Checks the whether the object is displayed on UI and gives warning if not present created By Aditya */
	public boolean isDisplayedWarn(By element, String strObjName) throws ElementNotFoundException
	{
		WebElement objElement = null;
		Boolean blnIsEnabled = false;
		try 
		{
			objElement = driver.findElement(element);
			blnIsEnabled = objElement.isDisplayed();
		}
		catch (Exception e2) 
		{
		}

		if (blnIsEnabled)
		{
			LOGGER.info("Successfully Found "+strObjName+" is Displayed on UI");
			return true;
		}

		else 
		{
			LOGGER.warning(strObjName+" is Not Displayed on UI");
			return false;
		}
	}

	//This method is created for verifying that the element is not present on UI.
	//Aditya
	public boolean elementIsNotPresent(By element, String strObjName) throws ElementNotFoundException
	{
		try {
			driver.findElement(element);
			throw new ElementNotFoundException(strObjName+" should not be present on UI", LOGGER);

		} catch (Exception e) {
			LOGGER.info("Successfully Not Found "+strObjName+" on UI");
			return true;
		}
	}
	//This method is created to get Title of Window.
	//Aditya
	public String getPageTitle() throws ElementNotFoundException
	{
		try {
			return driver.getTitle();
		} catch (Exception e) {
			throw new ElementNotFoundException("Cannot get window title", LOGGER);
		}
	}
	//This method is created to switch window by passing name or title
	//Aditya
	public void switchWindow(String title) throws ElementNotFoundException
	{
		try {
			driver.switchTo().window(title);
		} catch (Exception e) {
			throw new ElementNotFoundException("Cannot switch window ", LOGGER);
		}
	}
	public void switchToParentWindow() throws ElementNotFoundException
	{
		try {
			driver.switchTo().defaultContent();
		} catch (Exception e) {
			throw new ElementNotFoundException("Cannot switch to parent window ", LOGGER);
		}
	}
	/*This Method Checks the whether the object is Enabled or Not created By Aditya*/
	public boolean isEnabledWarn(WebElement element, String strObjName) throws ElementNotFoundException
	{
		try 
		{
			if (element.isEnabled())
			{
				LOGGER.info("Successfully Found "+strObjName+" is Enabled on UI");	
				return true;
			}

			else 
			{
				LOGGER.warning(strObjName+" is Not Enabled on UI");	
				return false;
			}
		}
		catch (Exception e2) 
		{
			throw new ElementNotFoundException("Exception occured at wait Until "+strObjName+" : "+e2, LOGGER);
		}	
	}

	/*This Method Checks the whether the object is disabled or Not created By Aditya*/
	public boolean isDisabledWarn(WebElement element, String strObjName) throws ElementNotFoundException
	{
		try 
		{
			if (!element.isEnabled())
			{
				LOGGER.info("Successfully Found "+strObjName+" is Disabled on UI");	
				return true;
			}

			else 
			{
				LOGGER.warning(strObjName+" is Enabled on UI");	
				return false;
			}
		}
		catch (Exception e2) 
		{
			return false;
		}	
	}

	/*This Method Checks the whether the object is not  displayed on UI and gives warning if not present created By Aditya */
	public boolean isNotDisplayedWarn(WebElement element, String strObjName) throws ElementNotFoundException
	{
		try 
		{

			if (!element.isDisplayed())
			{
				LOGGER.info("Successfully Found "+strObjName+" is not Displayed on UI");
				return true;
			}

			else 
			{
				LOGGER.warning(strObjName+" is Displayed on UI");
				return false;
			}
		}
		catch (Exception e2) 
		{
			LOGGER.info("Successfully Found "+strObjName+" is not Displayed on UI");
			return false;
		}
	}

	//Method to check whether element is clickable or not on UI
	public boolean isClickableWarn(By element, String strObjectName) throws ElementNotFoundException
	{

		try {
			WebDriverWait wait = new WebDriverWait(driver, 3);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//Method to check if object is read only on UI
	public boolean objectReadAccessOnUI(By element, String strUIObj) throws ElementNotFoundException
	{
		try {

			if(isDisplayedWarn(element, strUIObj) && !(isClickableWarn(element, strUIObj)))
			{
				LOGGER.fine(strUIObj+" has read only access on UI");
				return true;
			}

			LOGGER.warning(strUIObj+" do not have read only access on UI");
			return false;

		} catch (Exception e) {
			throw new ElementNotFoundException("Error on UI Principle access", LOGGER);
		}
	}

	//Method to check if object is read and write on UI
	public boolean objectReadAndWriteAccessOnUI(By element, String strUIObj) throws ElementNotFoundException
	{
		try {

			if(isDisplayedWarn(element, strUIObj) && (isClickableWarn(element, strUIObj)))
			{
				LOGGER.fine(strUIObj+" has read and write access on UI");
				return true;
			}

			LOGGER.fine(strUIObj+" do not have read and write access on UI");
			return false;

		} catch (Exception e) {
			throw new ElementNotFoundException("Error on UI Principle access", LOGGER);
		}
	}

	//Method to check if object is N.A. on UI
	public boolean objectNotAccessOnUI(By element, String strUIObj) throws ElementNotFoundException
	{
		try {

			if(!isDisplayedWarn(element, strUIObj))
			{
				LOGGER.fine(strUIObj+" is not available access on UI");
				return true;
			}

			LOGGER.warning(strUIObj+" is available access on UI");
			return false;

		} catch (Exception e) {
			throw new ElementNotFoundException("Error on UI Principle access", LOGGER);
		}
	}

	// This Method delete the delete batch run result CSV file in c-drive
	public void deleteBatchCSV()
	{
		String strdirectoryPath = "./";
		String strLogsdirectoryPath = "./logs";
		String strLogFileName = DriverScript.driverProperties.getProperty("BatchFileName");

		try
		{
			deleteFolder(new File(strLogsdirectoryPath));
			File[] filesInDirectory = new File(strdirectoryPath).listFiles();
			for (File objFile : filesInDirectory)
			{
				String fileName = objFile.getName();
				String fileExtenstion = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				if (("csv".equals(fileExtenstion) || "html".equals(fileExtenstion)) && (fileName.contains(strLogFileName)))
				{
					objFile.delete();
				}
			}
		}
		catch (Exception e)
		{
			LOGGER.severe("Exception Occured while deleting previous batch result csv file" + e);
		}

	}

	//This Method delete the previously created Files 
	private void deleteFolder(File folder)
	{
		try
		{
			File[] files = folder.listFiles();
			if(files!=null)
			{ 
				//some JVMs return null for empty dirs
				for(File objFile: files) 
				{
					if(objFile.isDirectory()) 
					{
						deleteFolder(objFile);
					}
					else 
					{
						objFile.delete();
					}
				}
			}
			folder.delete();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//This Method get the absolute File Path from relative file path
	@SuppressWarnings("unused")
	public String getAbsoluteFilePath(String strRelativePath) throws ElementNotFoundException
	{
		String strAbsolutPath = null;
		try
		{
			File file  = new File(strRelativePath);
			if(file != null)
			{ 
				strAbsolutPath = file.getAbsolutePath();
				if (!strAbsolutPath.isEmpty() && strAbsolutPath != null) 
				{
					return strAbsolutPath;
				}
				else
				{
					throw new ElementNotFoundException("Unable to find Absolute File Path ", LOGGER);
				}
			}
			else
			{
				throw new ElementNotFoundException("File Path is not correct", LOGGER);
			}

		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("File Path is not correct", LOGGER);
		}
	}

	/**
	 * Method to insert the Date in different forms.
	 * @param numberOfDays  No of Days i.e. for current date pass 0, For Future date pass no of future days
	 * @return void
	 */
	public void dateHandler(By locator , String strnumberOfDays , String strDateType) throws ElementNotFoundException
	{
		boolean blnIsSelected = false;
		int numberOfDays = Integer.parseInt(strnumberOfDays);
		try 
		{
			clickOnElement(locator,"Active To Date Field");			
		} 
		catch (Throwable e)
		{
			throw new ElementNotFoundException("Exception Occured while handling the date field"+e,LOGGER);
		}
		if (numberOfDays == 0) 
		{
			List<WebElement> elementList = findElements(By.xpath("//*[@data-value='"+getCurrentDate()+"']"), strDateType+" Date field");
			for (WebElement webElement : elementList)
			{
				try 
				{
					webElement.click();
					LOGGER.info("Successfully Selected "+strDateType+" as "+getFutureDate(numberOfDays));
					blnIsSelected = true;
					break;
				} 
				catch (Exception e)
				{
					continue;
				}		
			}
		}
		else if(numberOfDays > 0)
		{
			List<WebElement> elementList = findElements(By.xpath("//*[@data-value='"+getFutureDate(numberOfDays)+"']"), strDateType+" Date field");
			for (WebElement webElement : elementList)
			{
				try 
				{
					webElement.click();
					LOGGER.info("Successfully Selected "+strDateType+" as "+getFutureDate(numberOfDays));
					blnIsSelected = true;
					break;
				} 
				catch (Exception e)
				{
					continue;
				}		
			}
			if (!blnIsSelected)
			{
				//LOGGER.severe("Unable to select "+strDateType);
			} 
		}	
	}
	
	
	
	//This Method Checks whether the list is sorted or Not
	@SuppressWarnings("unchecked")
	public boolean isSortedList(List<? extends Comparable> list)
	{
		boolean blnflag = false;
		if(list == null || list.isEmpty())
			return false;

		if(list.size() == 1)  
			return true;

		for(int i=1; i<list.size();i++)
		{
			if(list.get(i).compareTo(list.get(i-1)) < 0 )
				return false;
		}
		return true;    
	}
	
	/**
	 * Method to verify Sorting functionality
	 * @param locator column Locator
	 * @return void
	 */
	public boolean verifySortingFunctionality(String strHeaderName) throws ElementNotFoundException
	{
		boolean blnIsVerified = false;
		ArrayList<Integer> objList = new ArrayList<>();
		
		clickOnElement(By.xpath("//div[contains(text(),'"+strHeaderName+"')]"), "Table Header as "+strHeaderName);
		try 
		{		
			List<WebElement> members = findElements(By.xpath("//*[contains(text(),'"+strHeaderName+"')]//following::table//self::tbody//following::tr/td[2]"), "Table rows of Table Header as "+strHeaderName);

			for (WebElement webElement : members) 
			{
				if ( !webElement.getText().isEmpty())
				{
					String strVaure = webElement.getText().split("-")[1];
					int i = Integer.parseInt(strVaure);
					objList.add(i);
				}

			}	
			blnIsVerified = isSortedList(objList);
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Sorting functionality is failed for Column "+strHeaderName+" : "+e,LOGGER);
		}	

		if (blnIsVerified)
		{
			LOGGER.fine("Successfuly verify Sorting functionality for the Column as "+strHeaderName);
		}
		else
		{
			throw new ElementNotFoundException("Sorting functionality is failed for Column "+strHeaderName,LOGGER);
		}
		return blnIsVerified;
	}

}

