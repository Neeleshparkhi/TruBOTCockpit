/**
 * 
 */
package functionalLibrary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.tools.ant.taskdefs.Sleep;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import batchExecution.DriverScript;
import exceptions.ElementNotFoundException;
import pageObject.IDashboard;
import pageObject.IProcess;
import utility.DBVerification;
import utility.FL_GeneralUtils;

/**
 * @author aditya.soni
 *
 */
public class Process extends DriverScript implements IDashboard,IProcess 
{
	private WebDriver driver;
	private Logger LOGGER;
	protected WebDriverWait wait;
	private FL_GeneralUtils objExefunction;
	private DBVerification objDbVerification;

	public Process(WebDriver driver, WebDriverWait wait, Logger LOGGER,Properties driverProperties) 
	{
		this.driver = driver;
		this.wait = wait;
		this.LOGGER = LOGGER;
		objExefunction = new FL_GeneralUtils(driver, wait, LOGGER);
		objDbVerification = new DBVerification(driver, wait, LOGGER, driverProperties);
	}

	/**
	 * Method to Click on Process Button.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickProcess() throws ElementNotFoundException
	{
		objExefunction.mouserHover(spn_botManagement, "Bot Management Button on Dashboard");
		objExefunction.clickOnElement(spn_Process, "Process Button on Dashboard");
		objExefunction.sleep(2000);
	}

	/**
	 * Method to Click on Add Process Button.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickAddProcess() throws ElementNotFoundException
	{
		objExefunction.clickOnElement(btn_addProcess, "Add process Button");
	}

	/**
	 * Method to Add Process file on Add Process Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickProcessFile(String strPackegeFilePath) throws ElementNotFoundException
	{ 
		int i = 0;
		String strAbsolutFolePath = objExefunction.getAbsoluteFilePath(strPackegeFilePath);

		objExefunction.findElement(btn_addProcess_BrowsePackege, "Process").sendKeys(strAbsolutFolePath);
		while (i <= 100) 
		{
			objExefunction.sleep(3000);
			try 
			{
				String str = driver.findElement(txt_addProcessName).getAttribute("value");
				if (!str.isEmpty() && str != null) 
				{
					break;
				}
			} 
			catch (Exception e) 
			{
			}	
			i++;
		}
	}
	/**
	 * Method to Add Process file on Add Process Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickAddProcessFile(String strPackegeFilePath) throws ElementNotFoundException
	{ 
		clickProcessFile(strPackegeFilePath);
		try 
		{
			if (driver.findElement(div_ProcessAlreadyExists).isDisplayed())
			{
				objExefunction.clickOnElement(btn_addProcess_OK, "Ok Button on Process Already Exists Error Popup");
			}
		}
		catch (Exception e)
		{	

		}
	}


	/**
	 * Method to verify add Process error message on Add Process Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void verifyAddProcessErrorMsg() throws ElementNotFoundException
	{ 
		wait.until(ExpectedConditions.elementToBeClickable(div_ProcessAlreadyExists));
		LOGGER.info("Succesfully verify Process already exists erroe message");
	}

	/**
	 * Method to Verify Process GUID on Add Process Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void verifyProcessGuid() throws ElementNotFoundException
	{ 
		String strGuid = null;	

		strGuid = objExefunction.findElement(txt_addProcessGUID, "GUID on Add Process form").getAttribute("value");
		if (strGuid != null && !strGuid.isEmpty()) 
		{
			LOGGER.info("Succesly verify GUID as "+strGuid);
			strGuid = null;
		}
		else
		{
			throw new ElementNotFoundException("Exception Occured verifying GUID",LOGGER);
		}
	}

	/**
	 * Method to Verify Process Name on Add Process Form.
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public boolean verifyProcessName() throws ElementNotFoundException
	{ 
		String strName = null;	

		strName = objExefunction.findElement(txt_addProcessName, "Name on Add Process form").getAttribute("value");
		if (strName != null && !strName.isEmpty()) 
		{
			LOGGER.info("Succesly verify Name as "+strName);
			strName = null;
			return true;
		}
		else
		{
			throw new ElementNotFoundException("Exception Occured verifying GUID",LOGGER);
		}
	}

	/**
	 * Method to insert Date on Add/Edit Process Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void insertDateOnProcessForm(String strDateType,String strNoOfDays) throws ElementNotFoundException
	{ 
		if (strDateType.equalsIgnoreCase("ActiveFrom")) 
		{
			objExefunction.dateHandler(txt_addProcess_ActiveFromDate, strNoOfDays, "Active From Date");
		} 
		else if (strDateType.equalsIgnoreCase("ActiveTo"))
		{
			objExefunction.dateHandler(txt_addProcess_ActiveToDate, strNoOfDays, "Active To Date");
		}
	}

	/**
	 * Method to Verify ActiveTo on Add Process Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void verifyProcessActiveToDate(String noOfDays) throws ElementNotFoundException
	{ 
		String strActiveToDate = null;	
		int i = Integer.parseInt(noOfDays);

		strActiveToDate = objExefunction.findElement(txt_addProcess_ActiveToDate, "Active To date on Add Process form").getAttribute("value");
		if (strActiveToDate != null && !strActiveToDate.isEmpty() && strActiveToDate.equalsIgnoreCase(objExefunction.getFutureDate(i,"dd-MMM-yyyy"))) 
		{
			LOGGER.info("Succesly verify Active To Date as Current date + 30 Days as "+strActiveToDate);
			strActiveToDate = null;
		}
		else
		{
			throw new ElementNotFoundException("Exception Occured verifying Active To date on Add Process form",LOGGER);
		}
	}

	/**
	 * Method to Verify Active From on Add Process Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void verifyProcessActiveFromDate(String noOfDays) throws ElementNotFoundException
	{ 
		String strActiveFromDate = null;	
		int i = Integer.parseInt(noOfDays);
		strActiveFromDate = objExefunction.findElement(txt_addProcess_ActiveFromDate, "Active To date on Add Process form").getAttribute("value");
		if (strActiveFromDate != null && !strActiveFromDate.isEmpty() && strActiveFromDate.equalsIgnoreCase(objExefunction.getFutureDate(i,"dd-MMM-yyyy"))) 
		{
			LOGGER.info("Succesly verify Active from Date as Current date  as "+strActiveFromDate);
			strActiveFromDate = null;
		}
		else
		{
			throw new ElementNotFoundException("Exception Occured verifying Active from date on Add Process form",LOGGER);
		}
	}
	/**
	 * Method to Add Process Version on Add Process Form.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickAddProcessVersion(String strVersion) throws ElementNotFoundException
	{
		objExefunction.setText(txt_addProcess_Version, "Process Version No.", strVersion);
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
	 * Method to Click on Save button on Add Process form.
	 * @return boolean
	 * throws ElementNotFoundException
	 * @throws SQLException 
	 */
	public void clickSaveOnAddProcesForm(String strProcessName) throws ElementNotFoundException, SQLException
	{
		objExefunction.pageScrollDowntoElement(btn_addProcess_Save);
		objExefunction.clickOnElement(btn_addProcess_Save, "Save button on add process form");		
		objExefunction.sleep(10000);
		/*boolean blnIsVersionPresent = objExefunction.isDisplayedWarn(div_addProcess_VersionErrorMessage, "Version Already Exists Error Message");
		 if (blnIsVersionPresent) 
		 {
			 objDbVerification.getProessVersion(strProcessName);
		}*/
	}
	
	/**
	 * Method to Click on Save button on Add Process form.
	 * @return boolean
	 * throws ElementNotFoundException
	 * @throws SQLException 
	 */
	public void clickSaveOnAddProcesForm() throws ElementNotFoundException, SQLException
	{
		objExefunction.pageScrollDowntoElement(btn_addProcess_Save);
		objExefunction.clickOnElement(btn_addProcess_Save, "Save button on add process form");		
		objExefunction.sleep(10000);
	}

	/**
	 * Method to verify Process is Present on Process Grid.
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public boolean verifyProcessinProcessGrid(String strProcessName) throws ElementNotFoundException
	{
		wait = new WebDriverWait(driver, 300);
		try
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='tableCard']//self::*[contains(text(),'"+strProcessName+"')]")));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='tableCard']//self::*[contains(text(),'"+strProcessName+"')]")));
			LOGGER.info("Succesfully verify Process in Process Grid");
		}
		catch (Exception e) 
		{
			driver.navigate().refresh();
			objExefunction.sleep(10000);
		}	
		objExefunction.waitUntil(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='tableCard']//self::*[contains(text(),'"+strProcessName+"')]")), "Added Proces on Process Grid");
		wait = new WebDriverWait(driver, 30);
		return true;
	}



	/**
	 * Method to Click on Cancel button on Add Process form.
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public boolean clickCancelOnAddProcesForm() throws ElementNotFoundException
	{
		objExefunction.pageScrollDowntoElement(btn_addProcess_Cancel);
		objExefunction.clickOnElement(btn_addProcess_Cancel, "Cancel button on add process form");
		objExefunction.waitUntil(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class='tableCard']")), " Process Grid");
		return true;
	}

	/**
	 * Method to add Process on Add Process form.
	 * @param strPackegeFilePath  Process File Path
	 * @param strVersion  Process version Name
	 * @param strActiveFromDate  Active From Date
	 * @param strActiveToDate  Active To Date
	 * @param strgroup  Group Name
	 * @return void
	 * @throws SQLException 
	 */
	public boolean addProcess(String strPackegeFilePath,String strVersion,String strActiveFrom,String strActiveTo,String strgroup,String strProcessName ) throws ElementNotFoundException, SQLException
	{
		boolean blnIsProcessAddSuccesfull = false;
		clickProcess();
		clickAddProcess();
		clickAddProcessFile(strPackegeFilePath);
		clickAddProcessVersion(strVersion);	
		objExefunction.dateHandler(txt_addProcess_ActiveFromDate, strActiveFrom, "Active From Date");
		objExefunction.dateHandler(txt_addProcess_ActiveToDate, strActiveTo, "Active To Date");
		objExefunction.pageScrollDowntoElement(btn_addProcess_Save);
		selectGroup(strgroup);
		clickSaveOnAddProcesForm(strProcessName);
		blnIsProcessAddSuccesfull = verifyProcessinProcessGrid(strProcessName);
		if (blnIsProcessAddSuccesfull) 
		{
			LOGGER.info("Successfully add Process in add process form");
		}
		else 
		{
			throw new ElementNotFoundException("Exception Occured while Uploading the process  ",LOGGER);
		}
		return true;
	}
	/**
	 * Method to Click on down arrow  on Particular Process Name.
	 * @param strProcessName  Process Name
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickonDownArrow(String strProcessName) throws ElementNotFoundException
	{
		int i = 0;
		while(i <= 100)
		{
			objExefunction.sleep(2000);
			try 
			{
				driver.findElement(By.xpath("//*[contains(text(),'"+strProcessName+"')]//preceding::td[1]")).click();
				LOGGER.info("Succesfuly clicked on Down Arow against the process "+strProcessName);
				objExefunction.sleep(4000);
				break;
			}
			catch (Exception e)
			{

			}	
			i++;
		}	
		if (i == 100) 
		{
			throw new ElementNotFoundException("Exception Occured while clicking the down arraow buttom for the proces  "+strProcessName,LOGGER);		
		}
	}

	/**
	 * Method to Click on Edit button on Process.
	 * @param strProcessName  Process Name
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public void clickEditProcess(String strProcessName) throws ElementNotFoundException
	{
		clickonDownArrow(strProcessName);
		objExefunction.clickOnElement(By.xpath("//*[contains(text(),'"+strProcessName+"')]//following::tr//self::a"), "Edit Buton on Proces "+strProcessName);
		
	}
	
	/**
	 * Method to verify Mapping status for  Process.
	 * @param strProcessName  Process Name
	 * @param strMappingStatus  Mapping Status
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public void verifyMappingStatus(String strProcessName,String strMappingStatus) throws ElementNotFoundException
	{
		objExefunction.checkTextinElements(By.xpath("(//*[text()='"+strProcessName+"']//parent::tr//self::td)[5]"), "Mapping Status for Process "+strProcessName, strMappingStatus);	
	}

	/**
	 * Method to verify Process Info on Process Grid.
	 * @param strStatus  Process Status
	 * @return boolean
	 * throws ElementNotFoundException
	 */
	public void verifyProcessGrid(String strProcessName,String strVerificationPoint,String strDataToVerify) throws ElementNotFoundException
	{
		switch (strVerificationPoint)
		{
		case "Versions": 
			objExefunction.checkTextinSingleElement(By.xpath("(//*[contains(text(),'"+strProcessName+"')]/parent::tr/following-sibling::tr//self::*[@class='dx-row dx-data-row dx-column-lines']//self::td)[1]"), "Verison No.", strDataToVerify);
			break;

		case "Process ID": 
			objExefunction.checkTextinSingleElement(By.xpath("(//*[contains(text(),'"+strProcessName+"')]/parent::tr/following-sibling::tr//self::*[@class='dx-row dx-data-row dx-column-lines']//self::td)[2]"), strVerificationPoint, strDataToVerify);
			break;

		case "Activate From": 
			int j = Integer.parseInt(strDataToVerify);
			String strActiveFromDate = objExefunction.getFutureDate(j,"dd-MMM-yyyy");
			objExefunction.checkTextinSingleElement(By.xpath("(//*[contains(text(),'"+strProcessName+"')]/parent::tr/following-sibling::tr//self::*[@class='dx-row dx-data-row dx-column-lines']//self::td)[3]"), strVerificationPoint, strActiveFromDate);
			break;

		case "Active To": 
			int k = Integer.parseInt(strDataToVerify);
			String strActiveToDate = objExefunction.getFutureDate(k,"dd-MMM-yyyy");
			objExefunction.checkTextinSingleElement(By.xpath("(//*[contains(text(),'"+strProcessName+"')]/parent::tr/following-sibling::tr//self::*[@class='dx-row dx-data-row dx-column-lines']//self::td)[4]"), strVerificationPoint, strActiveToDate);
			break;

		case "Mapping Status": 
			objExefunction.checkTextinSingleElement(By.xpath("(//*[contains(text(),'"+strProcessName+"')]/parent::tr/following-sibling::tr//self::*[@class='dx-row dx-data-row dx-column-lines']//self::td)[5]"), strVerificationPoint, strDataToVerify);
			break;

		case "Status": 
			objExefunction.checkToggleState(By.xpath("(//*[contains(text(),'"+strProcessName+"')]/parent::tr/following-sibling::tr//self::*[@class='dx-row dx-data-row dx-column-lines']//self::td)[6]//self::dx-switch"), strVerificationPoint, strDataToVerify);
			break;

		case "DisplayParameterIcon": 
			objExefunction.isDisplayed(By.xpath("((//*[contains(text(),'"+strProcessName+"')]/parent::tr/following-sibling::tr//self::*[@class='dx-row dx-data-row dx-column-lines']//self::td)//self::a)[2]"), "Parameter Icon");
			break;

		case "NonDisplayOfParameterIcon": 
			objExefunction.isNotDisplayed(By.xpath("((//*[contains(text(),'"+strProcessName+"')]/parent::tr/following-sibling::tr//self::*[@class='dx-row dx-data-row dx-column-lines']//self::td)//self::a)[2]"), "Parameter Icon");
			break;

		default:
			LOGGER.warning(strVerificationPoint+ "Column is not present Kindly enter Correct Column Name");
		}	
	}

	/**
	 * Method to Select Process Status on Add Process Form .
	 * @param strStatus  Process Status
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void selectProcessStatus(String strStatus) throws ElementNotFoundException
	{
		if (!strStatus.equalsIgnoreCase("ON"))
		{
			objExefunction.clickOnElement(switch_addProcess_Status,"Process Status");
		} 		
		objExefunction.sleep(2000);
	}

	/**
	 * Method to click on Parameterization icon against Process in Process Grid Form .
	 * @param strProcess  Process Name
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickParameterIcon(String strProcess) throws ElementNotFoundException
	{			
		objExefunction.clickOnElement(By.xpath("((//*[contains(text(),'"+strProcess+"')]/parent::tr/following-sibling::tr//self::*[@class='dx-row dx-data-row dx-column-lines']//self::td)//self::a)[2]"), "Parameter Icon on Proces "+strProcess);
		objExefunction.sleep(5000);
	}

	/**
	 * Method to Verify All assigned group for Particular user present in Add/Edit Process Form .
	 * @param strUser  User Name
	 * @return void
	 * throws ElementNotFoundException
	 * @throws SQLException 
	 */
	public void verifyAssignedGroup(String strUser) throws ElementNotFoundException, SQLException
	{
		ArrayList<String> groupList = objDbVerification.verifyAssignedGroup(strUser);	
		try 
		{
			for (String string : groupList)
			{
				objExefunction.findElement(By.xpath("//*[contains(text(),'"+string+"')]"), string+" Group");	
			}

			LOGGER.info("Succesfully verify all the assign group is displayed on UI for the User "+strUser);
		}
		catch (Exception e) 
		{
			throw new ElementNotFoundException("Exception Occured while Verifying assigned groups  ",LOGGER);
		}
	}

	/**
	 * Method to Verify Process status Change error message grid .
	 * @return void
	 * throws ElementNotFoundException
	 * @throws SQLException 
	 */
	public void verifyProcessStatusChangeErrorMessage(String strStatus) throws ElementNotFoundException, SQLException
	{
		HashMap<String, String> processMap = objDbVerification.processAssociatedWithActiveBot(strStatus);	
		Set<String> set = processMap.keySet();
		try
		{
			for (String process : set)
			{
				clickonDownArrow(process);
				objExefunction.clickOnElement(By.xpath("//*[contains(text(),'"+processMap.get(process)+"')]//following-sibling::td//self::dx-switch"),"Successfully Click on Status Button against Process name as: ");
				try 
				{
					objExefunction.sleep(2000);
					if (driver.findElement(By.xpath("//div[contains(text(),'C10155')]")).isDisplayed())
					{
						LOGGER.info("C10155- This resource has dependency with Bot. So please deactivate the linked resource and try again");
						break;
					}
				}
				catch (Exception e)
				{				
				}
			} 	
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured while Verifying Status change error message  ",LOGGER);
		}

		driver.findElement(By.xpath("//div[@class='dx-button-content'][contains(.,'OK')]")).click();
		objExefunction.sleep(2000);
	}

	/**
	 * Method to Verify Process status Change Successfully.
	 * @return void
	 * throws ElementNotFoundException
	 * @throws SQLException 
	 */
	public void verifyProcessStatusChange(String strStatus) throws ElementNotFoundException, SQLException
	{
		HashMap<String, String> processMap = objDbVerification.processAssociatedWithActiveBot(strStatus);	
		Set<String> set = processMap.keySet();
		try
		{
			for (String process : set)
			{
				clickonDownArrow(process);
				objExefunction.clickOnElement(By.xpath("//*[contains(text(),'"+processMap.get(process)+"')]//following-sibling::td//self::dx-switch"),"Successfully Click on Status Button against Process name as: ");
				try 
				{
					objExefunction.sleep(2000);
					if (driver.findElement(By.xpath("//div[contains(text(),'C10155')]")).isDisplayed())
					{
						throw new ElementNotFoundException("Exception Occured while Verifying Status change of Process Associated with INactive bot  ",LOGGER);
					}
				}
				catch (Exception e)
				{	
					LOGGER.info("Succefully change status of Process associated with Inactive Bot from Active to Inactive ");
					break;
				}
			} 	
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured while Verifying Status change error message  ",LOGGER);
		}
		objExefunction.sleep(2000);
	}
	/**
	 * Method to Add Parameters.
	 * @param strName  Name
	 * @param strCategory  Category Name
	 * @param strCategoryDescription  Category description
	 * @param strParameterName  Parameter Name
	 * @return void
	 * throws ElementNotFoundException 
	 */
	public void addParameters(String strName,String strCategory,String strCategoryDescription,String strParameterName) throws ElementNotFoundException
	{
		Boolean blnCategory = false;
		Boolean blnParameterName = false;
		By ele_CategoryList = By.xpath("//*[text()='"+strName+"']/parent::tr//self::*[@aria-autocomplete='list']");
		By ele_ParameterName = By.xpath("(//*[text()='"+strName+"']/parent::tr//self::*[@aria-autocomplete='list'])[2]");
		try
		{
			//Adding or Selecting Category
			objExefunction.clickOnElement(ele_CategoryList,"Category List");
			blnCategory = objExefunction.clickOnElementWarn(By.xpath("//*[text()='"+strName+"']/parent::tr//self::*[@aria-autocomplete='list']//following::*[contains(text(),'"+strCategory+"')]"), "Category as "+strCategory);

			if (!(blnCategory) )
			{
				addCateggory(strCategory, strCategoryDescription);
				objExefunction.pageScrollDowntoHeight();
				objExefunction.clickOnElement(ele_CategoryList,"Category List");		
				objExefunction.clickOnElementWarn(By.xpath("//*[text()='"+strName+"']/parent::tr//self::*[@aria-autocomplete='list']//following::*[contains(text(),'"+strCategory+"')]"), "Category as "+strCategory);
			}

			

			//Adding or Selecting Parameter Name
			objExefunction.clickOnElement(ele_ParameterName, "Parameter Name ");
			blnParameterName = objExefunction.clickOnElementWarn(By.xpath("(//*[text()='"+strName+"']/parent::tr//self::*[@aria-autocomplete='list'])[2]//following::*[contains(text(),'"+strParameterName+"')]"), "Parameter Name as "+strParameterName);
			if (!blnParameterName)
			{
				addParameter(strParameterName);
				objExefunction.pageScrollDowntoHeight();
				objExefunction.clickOnElement(ele_ParameterName, "Parameter Name ");
				objExefunction.clickOnElementWarn(By.xpath("(//*[text()='"+strName+"']/parent::tr//self::*[@aria-autocomplete='list'])[2]//following::*[contains(text(),'"+strParameterName+"')]"), "Parameter Name as "+strParameterName);
			}			
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured while Verifying Adding Parameter  ",LOGGER);
		}
		blnCategory = null;
		blnParameterName = null;
	}

	/**
	 * Method to Add Category.
	 * @param strCategory  Category Name
	 * @param strCategoryDescription  Category Description
	 * @return void
	 * throws ElementNotFoundException 
	 */
	public void addCateggory(String strCategory,String strCategoryDescription) throws ElementNotFoundException
	{
		try
		{
			objExefunction.clickOnElement(div_addCategory,"Add Category");
			objExefunction.setText(txt_categoryName, "Category Name Test Box", strCategory);
			objExefunction.setText(txt_categoryDescription, "Category Description Test Box", strCategoryDescription);
			if (driver.findElement(switch_CategoryStatus).getAttribute("aria-label") == "OFF")
			{
				objExefunction.clickOnElement(switch_CategoryStatus, "Category Status");
			}		
			objExefunction.pageScrollDowntoElement(btn_addCategory_Save);
			objExefunction.clickOnElement(btn_addCategory_Save, "Save button Add Category");
			LOGGER.info("Category "+strCategory+"Added Sucessfully ");
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured while Adding category ",LOGGER);
		}
		objExefunction.sleep(2000);
	}


	/**
	 * Method to Add Parameter.
	 * @param strCategory  Category Name
	 * @param strCategoryDescription  Category Description
	 * @return void
	 * throws ElementNotFoundException 
	 */
	public void addParameter(String strParameterName) throws ElementNotFoundException
	{
		try
		{
			objExefunction.setText(txt_parameterName, "Parameter Name Test Box", strParameterName);

			//Set Parameter Status Toggle Button as ON
			if (driver.findElement(switch_ParameterStatus).getAttribute("aria-label") == "OFF")
			{
				objExefunction.pageScrollDowntoElement(switch_ParameterStatus);
				objExefunction.clickOnElement(switch_ParameterStatus, "Parameter Status");
			}	

			//set required Toggle Button as ON
			if (driver.findElement(switch_Required).getAttribute("aria-label") == "OFF")
			{
				objExefunction.pageScrollDowntoElement(switch_Required);
				objExefunction.clickOnElement(switch_Required, "Required");
			}	
			objExefunction.pageScrollDowntoElement(btn_addParameter_Save);
			objExefunction.clickOnElement(btn_addParameter_Save, "Save buton on  Add Parameter");
			LOGGER.info("Parameter "+strParameterName+" Added Sucessfully ");
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured while Adding category ",LOGGER);
		}
		objExefunction.sleep(2000);
	}
	
	/**
	 * Method to Click on Save button on Add Parameter Screen.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void clickSaveOnAddParameterForm() throws ElementNotFoundException
	{
		objExefunction.pageScrollDowntoElement(btn_parameterMapping_Save);
		objExefunction.clickOnElement(btn_parameterMapping_Save, "Save button on add process form");		
		objExefunction.sleep(10000);
	}
	
	/**
	 * Method to verify visibility of Parameter in  Add Parameter Screen.
	 * @return void
	 * throws ElementNotFoundException
	 */
	public void verifyParametersOnAddParameterForm(String strName) throws ElementNotFoundException
	{
		objExefunction.sleep(2000);
		objExefunction.isDisplayed(By.xpath("//td[contains(text(),'"+strName+"')]"), "Paramere Name as "+strName);
	}
	
	/**
	 * Method to verify Mapping status Header  Process.
	 * throws ElementNotFoundException
	 */
	public void visibilityOfMappingStatusHeader() throws ElementNotFoundException
	{
		objExefunction.isDisplayed(div_mappingStatusHeader, "Mapping Status for Header ");	
	}
	
	/**
	 * Method to click on GridHeader Process grid.
	 * @param locator column Locator
	 * throws ElementNotFoundException
	 */
	public void clickOnGridHeader(String strHeaderName) throws ElementNotFoundException
	{
		objExefunction.clickOnElement(By.xpath("//*[contains(text(),'"+strHeaderName+"')]"), "Grid header as "+strHeaderName);
	}
	
	/**
	 * Method to verify Sorting functionality
	 * @param locator column Locator
	 * @return void
	 */
	public boolean verifySortingFunctionalityOnProcess(String strHeaderName) throws ElementNotFoundException
	{
		boolean blnIsVerified = false;
		ArrayList<Integer> objList = new ArrayList<>();
		
		objExefunction.clickOnElement(By.xpath("//div[contains(text(),'"+strHeaderName+"')]"), "Table Header as "+strHeaderName);
		try 
		{
			List<WebElement> members = objExefunction.findElements(By.xpath("//*[contains(text(),'"+strHeaderName+"')]//following::table//self::tbody//self::tr/td[1]"), "Table rows of Table Header as "+strHeaderName);

			for (WebElement webElement : members) 
			{
				if (!webElement.getText().isEmpty())
				{
					String strVaure = webElement.getText();
					int i = Integer.parseInt(strVaure);
					objList.add(i);
				}

			}	
			blnIsVerified = objExefunction.isSortedList(objList);
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
