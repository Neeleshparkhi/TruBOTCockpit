package batchExecution;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import logging.BatchLog;
import logging.MyLogger;
import pageObject.ILoginPage;
import utility.FL_GeneralUtils;
import utility.SendMail;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.io.Files;
import com.paulhammant.ngwebdriver.NgWebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import exceptions.ElementNotFoundException;
import functionalLibrary.*;
import functionalLibrary.Process;
import utility.*;


public class DriverScript implements ILoginPage
{
	public Logger LOGGER;
	public MyLogger logs;
	private static final String PropertyFilePath = "./src/main/resources/resources/project.properties";
	private static final String LocatorFilePath = "./src/main/resources/resources/locator.properties";
	public static Properties driverProperties = null;
	public static Properties locatorProperties = null;
	protected static Hashtable<String, String> dicTestData = new Hashtable<String, String>();
	static String strCurrentDate = null;
	static Date date = new Date();
	protected String strScrintResultDesc;
	String strScriptFunctionality;
	String strScriptDesc;
	protected Boolean blnStatus = false;
	public WebDriver driver;
	public WebDriverWait wait;
	protected static ExtentReports extent = null;
	public static ExtentTest logger;
	public NgWebDriver ngDriver;
	public FL_GeneralUtils objExefunction;
	public Login objLogin;
	public Dashboard objDashBoard;
	public Process objProcess;
	public Credential objCredential;
	public BotStation objBotStation;
	public Bot objBot;
	public SendMail objSendMail;
	public DBVerification objVerifyDB;


	public static void deleteFolder(File folder) 
	{
		File[] files = folder.listFiles();
		if(files!=null) { //some JVMs return null for empty dirs
			for(File f: files)
			{
				if(f.isDirectory())
				{
					deleteFolder(f);
				}
				else
				{
					f.delete();
				}
			}
		}
		folder.delete();
	}

	//This Method Fetch the Test Cases from Excel sheet which is Put There for Execution
	public  ArrayList<String> readTestCase() throws ElementNotFoundException, ClassNotFoundException, FileNotFoundException
	{
		ArrayList<String> objList = new ArrayList<>();

		FileInputStream fileTestData = new FileInputStream(new	File(driverProperties.getProperty("BatchWorkBook")));
		/*File fileTestData = new	File(driverProperties.getProperty("BatchWorkBook"));*/
		HSSFWorkbook wrkBook;
		HSSFSheet sheetTestData;
		String strSheet = driverProperties.getProperty("BatchWorkBookSheet");
		int intRowCount;
		try
		{
			HSSFWorkbook workbook = new HSSFWorkbook(fileTestData);
			sheetTestData = workbook.getSheet(strSheet);
			intRowCount = 65000;/* sheetTestData.getLastRowNum();*/

			for(int row = 0; row < intRowCount; row++)
			{
				try 
				{
					String strFlag;
					strFlag = sheetTestData.getRow(row).getCell(1).toString().replaceAll("\\.0*$", "");
					if(strFlag.equalsIgnoreCase("Yes"))
					{
						String strTestCase = sheetTestData.getRow(row).getCell(0).toString().replaceAll("\\.0*$", "");
						objList.add(strTestCase);
					}
				} 
				catch (Exception e)
				{
				}
			}
			if (objList.size()>0)
			{
				return objList;
			}
			else
			{
				return null;
			}
		}
		catch(Exception ex)
		{
			throw new ElementNotFoundException(" Failed to fetch test Cases for Execution due to Exception :"+ex, LOGGER);
		}
	}


	public HashMap<String, HashMap<String, HashMap<String, String>>> readTestCaseFlow(String strCadeID)throws ElementNotFoundException, FileNotFoundException
	{
		HashMap<String, HashMap<String, HashMap<String, String>>> objFinalMap = new LinkedHashMap<>();
		HashMap<String,	HashMap<String, String>> objInnermap = new LinkedHashMap<>();
		HashMap<String, String> objDataMap = new LinkedHashMap<>();

		FileInputStream fileTestData = new FileInputStream(new	File(driverProperties.getProperty("BatchWorkBook")));
		HSSFWorkbook wrkBook;
		HSSFSheet sheetTestData;
		String strSheet = driverProperties.getProperty("Sheet");
		int intRowCount;
		try
		{		
			Thread.sleep(2000);
			wrkBook = new HSSFWorkbook(fileTestData);/*getWorkbook(fileTestData);*/
			sheetTestData = wrkBook.getSheet(strSheet);
			intRowCount = 65470;/*sheetTestData.getRows();*/		
			outer:
				for(int row = 1; row < intRowCount; row++)
				{
					objDataMap = new HashMap<>();
					String strDataName = null;
					String strDataValue = null;
					if(sheetTestData.getRow(row) != null)
					{
						String strTestCaseID = sheetTestData.getRow(row).getCell(0).toString().replaceAll("\\.0*$", "");
						if(strCadeID.equalsIgnoreCase(strTestCaseID))
						{
							String strCaseSrNo = sheetTestData.getRow(row).getCell(1).toString().replaceAll("\\.0*$", "");
							String strTrnType = sheetTestData.getRow(row).getCell(3).toString().replaceAll("\\.0*$", "")+strCaseSrNo.replaceAll("\\.0*$", "");
							if (!(sheetTestData.getRow(row).getCell(4) == null) && !(sheetTestData.getRow(row).getCell(5) == null) )
							{
								strDataName = sheetTestData.getRow(row).getCell(4).toString().replaceAll("\\.0*$", "");
								strDataValue = sheetTestData.getRow(row).getCell(5).toString().replaceAll("\\.0*$", "");
								objDataMap.put(strDataName, strDataValue);

								int x = row;
								for (int i = 1; i < 25; i++) 
								{
									if(sheetTestData.getRow(x+i) != null && sheetTestData.getRow(x+i).getCell(3) !=null && sheetTestData.getRow(x+i).getCell(1)!= null) 
									{
										String strTrnType_1 = sheetTestData.getRow(x+i).getCell(3).toString().replaceAll("\\.0*$", "")+sheetTestData.getRow(x+i).getCell(1).toString().replaceAll("\\.0*$", "");
										if(strTrnType_1.equals(strTrnType) && sheetTestData.getRow(x+i).getCell(4) != null && sheetTestData.getRow(x+i).getCell(5) !=null)
										{
											objDataMap.put(sheetTestData.getRow(x+i).getCell(4).toString().replaceAll("\\.0*$", ""), sheetTestData.getRow(x+i).getCell(5).toString().replaceAll("\\.0*$", ""));
											row++;
										}
										else
											break;
									}
								}
							}
							objInnermap.put(strTrnType, objDataMap);
							continue outer;
						}	
						objFinalMap.put(strCadeID, objInnermap);
					}
				}
			objInnermap = new HashMap<>();
		}
		catch(NullPointerException ex)
		{
			ex.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new ElementNotFoundException("Test Script with ID - " + strCadeID + " Failed to fetch test data with exception "+ex, LOGGER);
		}
		return objFinalMap;
	}

	// To delete batch run result CSV file in c-drive
	public void deleteBatchCSV()
	{
		String strdirectoryPath = "./";
		String strLogsdirectoryPath = "./logs";
		String strLogFileName = driverProperties.getProperty("BatchFileName");

		try 
		{
			deleteFolder(new File(strLogsdirectoryPath));
			File[] filesInDirectory = new File(strdirectoryPath).listFiles();
			for (File f : filesInDirectory)
			{
				String fileName = f.getName();
				String fileExtenstion = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				if (("csv".equals(fileExtenstion) || "html".equals(fileExtenstion)) && (fileName.contains(strLogFileName)))
				{
					f.delete();
				}
			}
		} 
		catch (Exception e)
		{
			LOGGER.severe("Exception Occured while deleting previous batch result csv file" + e);
		}

	}

	@DataProvider(name = "data-provider")
	public Object[][] dataandFlow() throws ElementNotFoundException
	{
		try
		{		
			ArrayList<String> objTestCaseList = new ArrayList<>();
			//ArrayList<String> objTestCaseFlow = new ArrayList<>();
			HashMap<String, HashMap<String, HashMap<String, String>>> objDataAndFlowMap = new LinkedHashMap<>();

			objTestCaseList = readTestCase();
			Object[][] dataProvider = new Object[objTestCaseList.size()][2]; 

			for (int i = 0; i < objTestCaseList.size(); i++)
			{
				try 
				{
					objDataAndFlowMap = readTestCaseFlow(objTestCaseList.get(i));
					dataProvider[i][0] = objTestCaseList.get(i);
					dataProvider[i][1] =  objDataAndFlowMap;
				} 
				catch (Exception e)
				{
					throw new ElementNotFoundException(e.getMessage(), LOGGER);
				}
			}
			return dataProvider;
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException(e.getMessage(), LOGGER);
		}
	}


	@BeforeSuite
	public void beforeSuite()
	{
		try
		{
			FileReader propReader = new FileReader(PropertyFilePath);
			driverProperties = new Properties();
			driverProperties.load(propReader);
			strCurrentDate = new SimpleDateFormat(driverProperties.getProperty("logFolderDateFormat")).format(date);
			extent = new ExtentReports("./"+driverProperties.getProperty("BatchFileName")+strCurrentDate+".html", false);
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\extent-config.xml"));
			extent.addSystemInfo("Environment", "QA");
			extent.addSystemInfo("Cockpit Build No.", driverProperties.get("BuildNumber").toString());
		} 
		catch (Exception e) 
		{
			throw new IllegalStateException("Failed to initialize " + DriverScript.class.getSimpleName() +
					" properties from " + PropertyFilePath);
		}	
		deleteBatchCSV();
	}


	/*
	 * This method gets the test case parameters from an excel file and then goes and executes the test case
	 */
	@Test(dataProvider = "data-provider")	
	public void testMethod(String strTestcase, HashMap<String, HashMap<String, HashMap<String, String>>> testandFlow) throws ElementNotFoundException
	{
		try 
		{	
			HashMap<String,	HashMap<String, String>> objFlowMap = new LinkedHashMap<>();
			objFlowMap = testandFlow.get(strTestcase);
			driver = getdriver();
			logger = extent.startTest(strTestcase);
			logs = new MyLogger(strCurrentDate);
			LOGGER = logs.getLogger(strTestcase, driver, logger);
			wait = retrieveExplicitWait();
			objExefunction = new FL_GeneralUtils(driver, wait, LOGGER);
			objLogin= new Login(driver, wait, LOGGER);
			objDashBoard = new Dashboard(driver, wait, LOGGER);
			objProcess =  new Process(driver, wait, LOGGER,driverProperties);
			objCredential = new Credential(driver, wait, LOGGER);
			objBotStation = new BotStation(driver, wait, LOGGER);
			objBot = new Bot(driver, wait, LOGGER);
			objVerifyDB = new DBVerification(driver, wait, LOGGER,driverProperties);
			invokeSwitch(strTestcase, objFlowMap);			
		}
		catch (Exception e)
		{
			throw new ElementNotFoundException(e.getMessage(), LOGGER);
		}
	}



	public void invokeSwitch(String objTestCaseList, HashMap<String, HashMap<String, String>> objTestCaseFlow) throws ElementNotFoundException
	{
		try
		{		
			strScriptFunctionality = "Not Given in Data Sheet";
			strScriptDesc = "Not Given in Data Sheet";
			java.util.Set<String> objKeys = new LinkedHashSet<>();
			objKeys = objTestCaseFlow.keySet();
			for (String strKeys : objKeys)
			{
				HashMap<String, String> objInnermap = new LinkedHashMap<>();
				objInnermap = objTestCaseFlow.get(strKeys);

				strKeys = strKeys.replaceAll("\\d","");
				switch (strKeys)
				{
				case "Login": 
					objLogin.loginUser(objInnermap.get("LoginID"), objInnermap.get("Password"));
					break;

				case "Vlogin": 
					objLogin.verifyLogin();
					break;

				case "performLogout": 
					objDashBoard.performLogout();
					break;

				case "Logout": 
					objDashBoard.performLogout();
					break;

				case "RunBot": 
					LOGGER.info("Third");
					break;

				case "Vbot": 
					LOGGER.info("Fourth");
					break;

				case "TestCaseInfo": 
					objExefunction.sleep(1);
					strScriptFunctionality = objInnermap.get("ScriptFunctionality");
					strScriptDesc = objInnermap.get("ScriptDescription");
					logger.assignCategory(strScriptFunctionality,strScriptDesc);
					break;

				case "FailStep": 
					objLogin.FailStep();
					break;	

				case "clickProcess": 
					objProcess.clickProcess();
					break;

				case "verifyProcessGrid": 
					objProcess.verifyProcessGrid(objInnermap.get("ProcessName"),objInnermap.get("ColumName"), objInnermap.get("Value"));
					break;

				case "verifyMappingStatus": 
					objProcess.verifyMappingStatus(objInnermap.get("ProcessName"),objInnermap.get("Mapping Status"));
					break;

				case "clickAddProcess": 
					objProcess.clickAddProcess();
					break;

				case "clickAddProcessFile":
					objProcess.clickAddProcessFile(objInnermap.get("ProcessFilePath"));
					break;

				case "clickProcessFile":
					objProcess.clickProcessFile(objInnermap.get("ProcessFilePath"));
					break;

				case "selectProcessStatus":
					objProcess.selectProcessStatus(objInnermap.get("Status"));
					break;

				case "verifyErrorMsg": 
					objExefunction.verifyErrorMsg(objInnermap.get("ErrorMesage"));
					break;

				case "clickSelectAll": 
					objExefunction.clickSelectAll(objInnermap.get("ExpectedValue"));
					break;

				case "clickAddProcessVersion": 
					objProcess.clickAddProcessVersion(objInnermap.get("ProcessVersion"));
					break;

				case "verifyProcessGuid": 
					objProcess.verifyProcessGuid();
					break;

				case "verifyProcessName": 
					objProcess.verifyProcessName();
					break;

				case "insertDateOnProcessForm":
					objProcess.insertDateOnProcessForm(objInnermap.get("DateType"),objInnermap.get("NoOfDays"));
					break;

				case "verifyProcessActiveToDate":
					objProcess.verifyProcessActiveToDate(objInnermap.get("NoOfDays"));
					break;

				case "verifyProcessActiveFromDate": 
					objProcess.verifyProcessActiveFromDate(objInnermap.get("NoOfDays"));
					break;

				case "addProcess": 
					objProcess.addProcess(objInnermap.get("ProcessFilePath"), objInnermap.get("Version"), objInnermap.get("ActiveFrom"),
							objInnermap.get("ActiveTo"), objInnermap.get("Group"), objInnermap.get("ProcessName"));
					break;	

				case "clickCancelOnAddProcesForm":
					objProcess.clickCancelOnAddProcesForm();
					break;

				case "clickSaveOnAddProcesForm": 
					objProcess.clickSaveOnAddProcesForm();
					break;

				case "verifyProcessinProcessGrid": 
					objProcess.verifyProcessinProcessGrid(objInnermap.get("ProcessName"));
					break;

				case "clickParameterIcon": 
					objProcess.clickParameterIcon(objInnermap.get("ProcessName"));
					break;

				case "addParameters": 
					objProcess.addParameters(objInnermap.get("Name"),objInnermap.get("Category"),objInnermap.get("Category description"),objInnermap.get("Parameter Name"));
					break;

				case "clickSaveOnAddParameterForm": 
					objProcess.clickSaveOnAddParameterForm();
					break;

				case "verifyParametersOnAddParameterForm": 
					objProcess.verifyParametersOnAddParameterForm(objInnermap.get("ParameterName"));
					break;

				case "visibilityOfMappingStatusHeader": 
					objProcess.visibilityOfMappingStatusHeader();
					break;

				case "verifySortingFunctionality": 
					objExefunction.verifySortingFunctionality(objInnermap.get("HeaderName"));
					break;
					
				case "verifySortingFunctionalityOnProcess": 
					objProcess.verifySortingFunctionalityOnProcess(objInnermap.get("HeaderName"));
				break;


				case "clickEditProcess": 
					objProcess.clickEditProcess(objInnermap.get("ProcessName"));
					break;

				case "clickonDownArrow": 
					objProcess.clickonDownArrow(objInnermap.get("ProcessName"));
					break;

				case "verifyProcessStatusChangeErrorMessage": 
					objProcess.verifyProcessStatusChangeErrorMessage(objInnermap.get("State"));
					break;


				case "verifyProcessStatusChange": 
					objProcess.verifyProcessStatusChange(objInnermap.get("State"));
					break;

				case "clickCredential": 
					objCredential.clickCredential();
					break;

				case "clickEditCredential": 
					objCredential.clickEditCredential(objInnermap.get("CredentialName"));
					break;

				case "clickAddCredential": 
					objCredential.clickAddCredential();
					break;


				case "insertDateOnCredentialForm": 
					objCredential.insertDateOnCredentialForm(objInnermap.get("DateType"), objInnermap.get("NoOfDays"));
					break;

				case "verifyCredentialDate": 
					objCredential.verifyCredentialDate(objInnermap.get("DateType"),objInnermap.get("NoOfDays"));
					break;

				case "enterDescription": 
					objCredential.enterDescription(objInnermap.get("Description"));
					break;

				case "addCredential": 
					objCredential.addCredential(objInnermap.get("Name"), objInnermap.get("Group"), objInnermap.get("ActiveFrom"), objInnermap.get("ActiveTo"), 
							objInnermap.get("Description"),objInnermap.get("Username"),objInnermap.get("Password"),objInnermap.get("Status"));
					break;	

				case "clickBotStation": 
					objBotStation.clickBotStation();
					break;	

				case "clickAddBotStation": 
					objBotStation.clickAddBotStation();
					break;	

				case "selectCredential": 
					objBotStation.selectCredential(objInnermap.get("Credential"));
					break;	

				case "addBotStation": 
					objBotStation.addBotStation(objInnermap.get("Name"), objInnermap.get("StationType"), objInnermap.get("Description"),objInnermap.get("Credential"), 
							objInnermap.get("IpAddress"), objInnermap.get("PortNumber"), objInnermap.get("ActiveFrom"), objInnermap.get("ActiveTo"));
					break;

				case "clickBot": 
					objBot.clickBot();
					break;	

				case "addBot": 
					objBot.addBot(objInnermap.get("Status"), objInnermap.get("Attended"), objInnermap.get("Process"),objInnermap.get("BotStation"), 
							objInnermap.get("Name"), objInnermap.get("Description"), objInnermap.get("ActiveFrom"), objInnermap.get("ActiveTo"));
					break;

				case "playBot": 
					objBot.playBot(objInnermap.get("Name"));
					break;

				case "getCurrentStatusOfBotExecution": 
					objVerifyDB.getCurrentStatusOfBotExecution(objInnermap.get("Status"));
					break;

				case "getDatesOfProcess": 
					objVerifyDB.getDatesOfProcess(objInnermap.get("strProcessVersion"));
					break;

				case "verifyAssignedGroup": 
					objProcess.verifyAssignedGroup(objInnermap.get("UserName"));
					break;

				default:
					LOGGER.warning("Entered Case is not Present");
				}
			}
			blnStatus = true;
		}

		catch (Exception e)
		{
			throw new ElementNotFoundException(e.getMessage(), LOGGER);
		}
		finally
		{
			batchResult(objTestCaseList, strScriptDesc, blnStatus);
			extent.endTest(logger);
			extent.flush();
			driver.quit();
		}
	}

	//This Method Returns the driver object based on the Browser
	public WebDriver getdriver() throws ElementNotFoundException
	{
		try 
		{	
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

			//--------------------------------Browser Setup-------------------------------//

			String strBrowser = driverProperties.getProperty("browser");
			switch (strBrowser)
			{
			case "IE": 
				System.setProperty("webdriver.ie.driver", ".\\drivers\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
				break;
			case "Chrome":  
				System. setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();
				break;
			case "Chrome_Headless":  
				System. setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.setHeadless(true);
				chromeOptions.addArguments("window-size=1200x600");
				driver = new ChromeDriver(chromeOptions);
				//driver.manage().window().maximize();
				break;
			case "Firefox": 
				System. setProperty("webdriver.gecko.driver", ".\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				break;

			default:
				break;
			}
			if (driver != null) 
			{
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

				//--------------------------------Configure NgWebDriver-------------------------------//
				ngDriver = new NgWebDriver((JavascriptExecutor) driver);
				/*ngDriver.waitForAngularRequestsToFinish();*/

				return driver;
			}
			else
			{
				System.exit(0);
				return null;
			}
		}

		catch (Exception e) 
		{
			throw new ElementNotFoundException("Exception occured while invoking Browser "+e, LOGGER);
		}

	}

	//This Method writes the execution result in CSV file
	public void batchResult(String strScriptName, String strScrintResultDesc, Boolean blnScriptResult) throws ElementNotFoundException
	{
		try 
		{
			String strScriptResult = "";
			BatchLog x= new BatchLog();

			if (blnScriptResult)
			{
				strScriptResult = "PASSED";
				LOGGER.info(strScriptName+" : "+strScrintResultDesc+ " "+strScriptResult);
			}
			else
			{
				strScriptResult = "FAILED";
				logger.log(LogStatus.FAIL, strScriptName+" : "+strScrintResultDesc+ " "+strScriptResult);
			}
			x.batchResult(strScriptName, strScriptFunctionality, strScriptDesc, strScriptResult, LOGGER);
			blnStatus = false;
		} 
		catch (Exception e)
		{
			throw new ElementNotFoundException("Exception Occured at Batch Result "+e, LOGGER);
		}
	}

	//This Method returns the Explicit Wait instance
	public WebDriverWait retrieveExplicitWait()
	{
		wait = new WebDriverWait(driver, 30);
		return wait;
	}

	@AfterSuite
	public void afterSuite()
	{
		try 
		{
			System.gc();
			driver.quit();
		} 
		catch (Exception e) 
		{
		}
		SendMail objSendMail = new SendMail();
		objSendMail.sendMail("Aditya.soni@datamatics.com");
	}




}
