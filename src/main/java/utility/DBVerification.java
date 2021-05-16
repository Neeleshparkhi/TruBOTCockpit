package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import batchExecution.DriverScript;
import exceptions.ElementNotFoundException;

public class DBVerification extends DriverScript
{
	private static Properties driverProperties = null;
	private WebDriver driver;
	private Logger LOGGER;
	protected WebDriverWait wait;
	private FL_GeneralUtils objExefunction;

	public DBVerification(WebDriver driver, WebDriverWait wait, Logger LOGGER,Properties driverProperties)
	{

		this.driver = driver;
		this.wait = wait;
		this.LOGGER = LOGGER;
		this.driverProperties = driverProperties;
		objExefunction = new FL_GeneralUtils(driver, wait, LOGGER);
	}

	/**
	 * Method to verify Bot execution status .
	 * @param strExpectedStatus  Expected Bot Execution status
	 * @return boolean blnIsVerified
	 * throws ElementNotFoundException
	 * @throws SQLException 
	 */
	public boolean getCurrentStatusOfBotExecution(String strExpectedStatus) throws ElementNotFoundException, SQLException
	{
		boolean blnIsVerified = false;
		String strCurrentStatus = null;
		ResultSet rs = null;
		Connection con1 = null;

		try
		{
			objExefunction.sleep(4000);
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con1 = DriverManager.getConnection(driverProperties.getProperty("dbURL"),driverProperties.getProperty("username"),driverProperties.getProperty("password"));
			Statement st1 = con1.createStatement();

			//Executing the SQL Query and store the results in ResultSet
			rs = st1.executeQuery("Select top 1 EXECUTION_STATUS from con_trn_job_execution_log order by id desc");

			while (rs.next())
			{
				strCurrentStatus = rs.getString(1);
				LOGGER.info("Succesfully find current  Bot Execution status as "+strCurrentStatus);
			}

			if (strCurrentStatus.equalsIgnoreCase(strExpectedStatus)) 
			{
				LOGGER.info("Succesfully verify Bot Execution status as "+strExpectedStatus);
			} 
			else 
			{
				throw new ElementNotFoundException("Not able to verify Bot Execution status as "+strExpectedStatus,LOGGER);
			}
		}
		catch (ClassNotFoundException e) 
		{

			throw new ElementNotFoundException("Exception Occured while Awhile verifying the Bot execution status  "+e,LOGGER);
		} 
		catch (SQLException e)
		{
			con1.close();
			throw new ElementNotFoundException("Exception Occured while Awhile verifying the Bot execution status  "+e,LOGGER);
		}	
		catch (Exception e)
		{
			con1.close();
			throw new ElementNotFoundException("Exception Occured while Awhile verifying the Bot execution status  "+e,LOGGER);
		}	

		return true;
	}

	/**
	 * Method to verify Group Assigned .
	 * @param strUserName User Name
	 * @return ArrayList<String> listGroup
	 * throws ElementNotFoundException
	 * @throws SQLException 
	 */
	public ArrayList<String> verifyAssignedGroup(String strUserName) throws ElementNotFoundException, SQLException
	{
		ArrayList<String> listGroup = new ArrayList<>();
		String strCurrentGroup = null;
		ResultSet rs = null;
		Connection con1 = null;

		try
		{
			objExefunction.sleep(4000);
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con1 = DriverManager.getConnection(driverProperties.getProperty("dbURL"),driverProperties.getProperty("username"),driverProperties.getProperty("password"));
			Statement st1 = con1.createStatement();

			//Executing the SQL Query and store the results in ResultSet
			rs = st1.executeQuery("select NAME from idn_trn_User Usr\r\n" + 
					"inner join [IDN_TRN_USER_GROUP] Ugrp on Usr.USER_CODE = Ugrp.USER_CODE\r\n" + 
					"inner join [IDN_MST_GROUP] grp on Ugrp.GROUP_ID = grp.ID\r\n" + 
					"where UserName='"+strUserName+"'");

			while (rs.next())
			{
				strCurrentGroup = rs.getString(1);
				if (!strCurrentGroup.isEmpty())
				{
					listGroup.add(strCurrentGroup);
				}
			}		
		}
		catch (ClassNotFoundException e) 
		{
			throw new ElementNotFoundException("Exception Occured while while verifying the Bot execution status  "+e,LOGGER);
		} 
		catch (SQLException e)
		{
			con1.close();
			throw new ElementNotFoundException("Exception Occured while while verifying the Bot execution status  "+e,LOGGER);
		}	
		catch (Exception e)
		{
			con1.close();
			throw new ElementNotFoundException("Exception Occured while while verifying the Bot execution status  "+e,LOGGER);
		}	

		if (listGroup.size() > 0)
		{
			con1.close();
			return listGroup;
		}
		else 
		{
			throw new ElementNotFoundException("Unable to Fetch assigned for the user  "+strUserName+" from DataBase",LOGGER);
		}

	}

	/**
	 * @param String strState
	 * Method to get Process Name associated with active bot .
	 * @return HashMap<String, String> mapProcess
	 * throws ElementNotFoundException
	 * @throws SQLException 
	 */
	public HashMap<String, String> processAssociatedWithActiveBot(String strState) throws ElementNotFoundException, SQLException
	{
		HashMap<String, String> mapProcess = new HashMap<>();
		String strProcessName = null;
		String strProcessCode = null;
		ResultSet rs = null;
		Connection con1 = null;

		try
		{
			objExefunction.sleep(1000);
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con1 = DriverManager.getConnection(driverProperties.getProperty("dbURL"),driverProperties.getProperty("username"),driverProperties.getProperty("password"));
			Statement st1 = con1.createStatement();

			if (strState.equalsIgnoreCase("Active")) 
			{
				//Executing the SQL Query and store the results in ResultSet
				rs = st1.executeQuery("select b.NAME,b.BOT_CODE from con_mst_job_repository j join con_Trn_bot_repository b on j.BOTID=b.Id where j.STATUS=1 and getutcdate() between j.STARTDATE and j.ENDDATE");
			}
			else 
			{
				//Executing the SQL Query and store the results in ResultSet
				rs = st1.executeQuery("select b.NAME,b.BOT_CODE from con_mst_job_repository j join con_Trn_bot_repository b on j.BOTID=b.Id where j.STATUS=0 or getutcdate() not between j.STARTDATE and j.ENDDATE");
			}

			while (rs.next())
			{
				strProcessName = rs.getString(1);
				strProcessCode = rs.getString(2);
				if (!strProcessName.isEmpty() && !strProcessCode.isEmpty() )
				{
					mapProcess.put(strProcessName, strProcessCode);
				}
			}		
		}
		catch (ClassNotFoundException e) 
		{
			throw new ElementNotFoundException("Exception Occured while while verifying the Bot execution status  "+e,LOGGER);
		} 
		catch (SQLException e)
		{
			con1.close();
			throw new ElementNotFoundException("Exception Occured while while verifying the Bot execution status  "+e,LOGGER);
		}	
		catch (Exception e)
		{
			con1.close();
			throw new ElementNotFoundException("Exception Occured while while verifying the Bot execution status  "+e,LOGGER);
		}	


		if (mapProcess.size() > 0)
		{
			return mapProcess;
		}
		else 
		{
			throw new ElementNotFoundException("Unable to Fetch Proess attached to Active Bots from DataBase",LOGGER);
		}

	}

	/**
	 * Method to verify Active  from & Active To date in 24 time format .
	 * @param strProcessName  Process Name
	 * @return boolean blnIsVerified
	 * throws ElementNotFoundException
	 * @throws SQLException 
	 */
	public boolean getDatesOfProcess(String strProcessVersion) throws ElementNotFoundException, SQLException
	{
		boolean blnIsVerified = false;
		String strActivaFromdate = null;
		String strActivaToDate = null;
		ResultSet rs = null;
		Connection con1 = null;

		try
		{
			objExefunction.sleep(12);
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con1 = DriverManager.getConnection(driverProperties.getProperty("dbURL"),driverProperties.getProperty("username"),driverProperties.getProperty("password"));
			Statement st1 = con1.createStatement();

			//Executing the SQL Query and store the results in ResultSet
			rs = st1.executeQuery("select ACTIVATIONDATE,EXPIRATIONDATE from con_Trn_bot_repository where VERSION= '"+strProcessVersion+"' order by id desc");

			while (rs.next())
			{
				strActivaFromdate = rs.getString(1);
				strActivaToDate = rs.getString(2);
				LOGGER.info("Succesfully find Active From Date of Proces "+strActivaFromdate);
				LOGGER.info("Succesfully find Active From Date of Proces "+strActivaToDate);
			}

			String[] ActiveFrom = strActivaFromdate.split(" ")[1].split(":");
			String[] ActivaToDate = strActivaToDate.split(" ")[1].split(":");

			if (ActiveFrom.length == 3) 
			{
				LOGGER.info("Succesfully Process Active From date is saved in 24 hours format in DB ");
			} 
			else 
			{
				throw new ElementNotFoundException("Not able to verify Process Active From date is saved in 24 hours format in DB",LOGGER);
			}

			if (ActivaToDate.length == 3) 
			{
				LOGGER.info("Succesfully Process Active From date is saved in 24 hours format in DB ");
			} 
			else 
			{
				throw new ElementNotFoundException("Not able to verify Process Active From date is saved in 24 hours format in DB",LOGGER);
			}
		}
		catch (ClassNotFoundException e) 
		{

			throw new ElementNotFoundException("Exception Occured while  verify Process Active From date is saved in 24 hours format in DB  "+e,LOGGER);
		} 
		catch (SQLException e)
		{
			con1.close();
			throw new ElementNotFoundException("Exception Occured while  verify Process Active From date is saved in 24 hours format in DB  "+e,LOGGER);
		}	
		catch (Exception e)
		{
			con1.close();
			throw new ElementNotFoundException("Exception Occured while  verify Process Active From date is saved in 24 hours format in DB  "+e,LOGGER);
		}	

		return true;
	}

	/**
	 * Method to Fetch highest version of Process.
	 * @param String strProcessName  Process Name
	 * @return boolean blnIsVerified
	 * @throws ElementNotFoundException
	 * @throws SQLException 
	 */
	public String getProessVersion(String strProcessName) throws ElementNotFoundException, SQLException
	{
		String strVersion = null;
		ResultSet rs = null;
		Connection con1 = null;
		int k;

		try
		{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			con1 = DriverManager.getConnection(driverProperties.getProperty("dbURL"),driverProperties.getProperty("username"),driverProperties.getProperty("password"));
			Statement st1 = con1.createStatement();

			//Executing the SQL Query and store the results in ResultSet
			rs = st1.executeQuery("select Top 1 VERSION from con_Trn_bot_repository where NAME Like '%"+strProcessName+"%' order by VERSION desc");

			while (rs.next())
			{
				strVersion = rs.getString(1);
			}

			if (strVersion == null)
			{
				strVersion = "1";
			} 
		}
		catch (ClassNotFoundException e) 
		{
			throw new ElementNotFoundException("Exception Occured while Awhile verifying the Bot execution status  "+e,LOGGER);
		} 
		catch (SQLException e)
		{
			throw new ElementNotFoundException("Exception Occured while Awhile verifying the Bot execution status  "+e,LOGGER);
		}	
		catch (Exception e)
		{		
			throw new ElementNotFoundException("Exception Occured while Awhile verifying the Bot execution status  "+e,LOGGER);
		}
		finally
		{
			rs = null;
			con1.close();
		}
		return  String.valueOf((Integer.parseInt(strVersion) + 1));
	}
}