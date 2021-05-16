
package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.logging.Logger;

import batchExecution.DriverScript;
import exceptions.ElementNotFoundException;

public class BatchLog {
	
	
	public void batchResult(String strScriptName, String strScriptFunctionality,String strScriptDesc, String blnScriptResult, Logger LOGGER) throws ElementNotFoundException
	{
		PrintWriter objOut = null;
		BufferedWriter objBW = null;
		FileWriter objFW = null;
		String strLogFileName = DriverScript.driverProperties.getProperty("BatchFileName"); 

	

		Date dt = new Date();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-YYYY");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy HH:mm");

		try
		{
			objFW = new FileWriter("./" + strLogFileName + "_" + sdf1.format(dt) + ".csv", true);			  
			objBW = new BufferedWriter(objFW);
			objOut = new PrintWriter(objBW);
			objOut.println(sdf2.format(dt) + "," + strScriptName + "," + strScriptFunctionality.replace(",", "") + "," +strScriptDesc+ ","+ blnScriptResult);
			
		}
		catch( IOException e )
		{
			 throw new ElementNotFoundException("Exception Occured while Batch Logging "+e, LOGGER);
		}
		finally
		{
			try
			{
				if( objOut != null ){
					objOut.close(); // Will close bw and fw too
				}
				else if(objBW != null ){
					objBW.close(); // Will close fw too
				}
				else if(objFW != null ){
					objFW.close();
				}
				else{
					// Oh boy did it fail hard! :3
				}
			}
			catch( IOException e ){
				throw new ElementNotFoundException("Exception Occured while Batch Logging "+e, LOGGER);
			}
		}
	}
}
