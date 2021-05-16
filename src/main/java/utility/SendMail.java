package utility;

import java.io.File;
import java.io.FileReader;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.SentDateTerm;

import com.opencsv.CSVReader;

import Annotations.testInfo;
import batchExecution.DriverScript;
import exceptions.ElementNotFoundException;

import javax.activation.*;

public class SendMail extends DriverScript
{
	public String strTestCaseFunctionality;
	public String strTestCaseDescription;

	public SendMail(String strTestCaseFunctionality,String strTestCaseDescription)
	{
		this.strTestCaseFunctionality = strTestCaseFunctionality;
		this.strTestCaseDescription = strTestCaseDescription;
	}
	
	public SendMail()
	{
		
	}
	
	
	//This Method Created the Mail body with correct execution status
	private  String getMailContent()
	{
		String strMailContent = null;
		StringBuffer strBuffer = new StringBuffer(1000);

		HashMap<String, String> objHmap = new HashMap<>();
		try
		{	
			//Creating HTML table to display the High Level Report
			strBuffer.append("<font face=\"calibri\" color=\"black\" size=\"3\">");
			strBuffer.append("<p>\r\n" + 
					" Hi All,\r\n" + 
					" <br>\r\n" + 
					" Good Day!\r\n" + 
					" <br>\r\n" + 
					" <br>\r\n" + 
					" We have executed new Cockpit Automation Suite. Please find below test results:\r\n" + 
					" <br>\r\n" + 
					" <br>\r\n" + 
					" </p>");			
			strBuffer.append("<!DOCTYPE html>");
			strBuffer.append("<html>");
			strBuffer.append("<head>");
			strBuffer.append("<style>");
			strBuffer.append("table, th, td {");
			strBuffer.append("border: 1px solid black; }; th{background:#000066}; th{font-family:calibri};");
			strBuffer.append("</style>");
			strBuffer.append("</head>");
			strBuffer.append("<body>");
			strBuffer.append("<table>\n");
			strBuffer.append("<tr>");
			strBuffer.append("<th align='center'>Sr.No.</th>");
			strBuffer.append("<th align='center'>Test Case Name</th>");
			strBuffer.append("<th align='center'>Functionality</th>");
			strBuffer.append("<th align='center'>Test Case Description</th>");
			strBuffer.append("<th align='center'>Execution Status</th>");
			strBuffer.append("</tr>");
			strBuffer.append("</font>");

			//Reading CSV file
			objHmap = readCSVFile();

			//Populating the HTML table with API name and Execution status
			Set<String> objSet = objHmap.keySet();
			Iterator<String> objItr= objSet.iterator();  
			int i = 1;
			while(objItr.hasNext())  
			{  

				String strMapKeys = objItr.next();
				if (!(strMapKeys.equalsIgnoreCase("CSVFilename") || strMapKeys.equalsIgnoreCase("HtmlReportFile"))) 
				{
					strBuffer.append("<font face=\"calibri\" color=\"black\" size=\"3\">");
					strBuffer.append("<tr>");
					if(objHmap.get(strMapKeys).contains("PASSED"))
					{
						strBuffer.append("<td align='center'>");
						strBuffer.append(i);
						strBuffer.append("</td>");
						strBuffer.append("<td>");
						strBuffer.append(strMapKeys);
						strBuffer.append("</td>");
						strBuffer.append("<td>");
						String[] strArrValue = objHmap.get(strMapKeys).split(":");
						strBuffer.append(strArrValue[0]);
						strBuffer.append("</td>");
						strBuffer.append("<td>");
						strBuffer.append(strArrValue[1]);
						strBuffer.append("</td>");
						strBuffer.append("<td align = 'center' , bgcolor = #33cc33>");
						strBuffer.append(strArrValue[2]);
						strBuffer.append("</td>");
						strBuffer.append("</tr>\n");
						strBuffer.append("</font>");
						i++;
					}
					else
					{
						strBuffer.append("<td align='center'>");
						strBuffer.append(i);
						strBuffer.append("</td>");
						strBuffer.append("<td>");
						strBuffer.append(strMapKeys);
						strBuffer.append("</td>");
						strBuffer.append("<td>");
						String[] strArrValue = objHmap.get(strMapKeys).split(":");
						strBuffer.append(strArrValue[0]);
						strBuffer.append("</td>");
						strBuffer.append("<td>");
						strBuffer.append(strArrValue[1]);
						strBuffer.append("</td>");
						strBuffer.append("<td align = 'center' , bgcolor = #ff5c33>");
						strBuffer.append(strArrValue[2]);
						strBuffer.append("</td>");
						strBuffer.append("</tr>\n");
						strBuffer.append("</font>");
						i++;
					}
				}  
			}

			strBuffer.append("</table>");
			strBuffer.append("<font face=\"calibri\" color=\"black\" size=\"3\">");
			strBuffer.append("<br>\r\n" + 
					"Also, kindly find attached <b>HTML</b> format report for more details.\r\n <p><br>\r\n" + 
					" Thanks and Regards,\r\n" + 
					" <br>\r\n" + 
					" <b>Tru<font face=\"calibri\" color=\"maroon\" size=\"3\">Bot</font> | Automation Team</b>\r\n" + 
					" <br>\r\n" +
					" <img src=\"DGSL_TruBot.png\"></p>");
			strBuffer.append("</font>");
			strBuffer.append("</body>");
			strBuffer.append("</html>");
		} 
		catch (Exception e) 
		{
			LOGGER.severe("Exception occured while Prepairing mail content"+e.getMessage());
			return null;
		}
		return strBuffer.toString();
	}

	//This Method Trigger the Auto generated mail which contains High level execution report and Extent report in HTML format as the attachment
	public void sendMail(String... strRecipents)
	{    
		HashMap<String, String> objHmap = new HashMap<>();
		String strMessage = null;

		// Recipient's email ID needs to be mentioned.
		String[] to = strRecipents;

		// Sender's email ID needs to be mentioned
		String from = "TruBot2Automation@datamatics.com";

		// Assuming you are sending email from localhost
		String host = "172.1.201.195";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getInstance(properties, null);
		//Session session = Session.getDefaultInstance(properties);

		try 
		{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			for (String string : to)
			{
				message.addRecipient(Message.RecipientType.TO,new InternetAddress(string));
			}

			//Getting the Mail body
			strMessage = getMailContent();

			// Set Subject: header field
			message.setSubject("Trubot Cockpit Automation Execution Report for Build Number- "+driverProperties.getProperty("BuildNumber")+" - "+new Date().toString().substring(4, 28));
			message.setSentDate(new Date());

			// Create the message part 
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(strMessage, "text/html");

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			//Image Part
			MimeBodyPart imagePart = new MimeBodyPart();
			imagePart.attachFile("DGSL_TruBot.png");


			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			//Add signature logo in message body
			multipart.addBodyPart(imagePart);
			message.setContent(multipart);


			// Adding attachment
			messageBodyPart = new MimeBodyPart();
			File file = new File(readCSVFile().get("HtmlReportFile"));
			String filename = file.getName();
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);

		}
		catch (MessagingException mex)
		{
			LOGGER.severe("Exception occured while sending the Report mail "+mex.getMessage());
		}
		catch (Throwable e)
		{
			LOGGER.severe("Exception occured while sending the Report mail "+e.getMessage());
		}
	}

	//This method reads the CSV file and put the result in Hashmap & Returns it
	private static HashMap<String, String> readCSVFile() 
	{ 
		HashMap<String, String> objHMap = new HashMap<>();

		try
		{ 
			File[] filesInDirectory = new File("./").listFiles();
			for (File objFile : filesInDirectory)
			{
				String fileName = objFile.getName();
				String fileExtenstion = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
				if (("csv".equals(fileExtenstion)))
				{
					objHMap.put("CSVFilename", objFile.getName());
					FileReader filereader = new FileReader(objFile.toString()); 
					// create csvReader object passing  file reader as a parameter 
					CSVReader csvReader = new CSVReader(filereader); 
					String[] nextRecord; 

					// we are going to read data line by line 
					while ((nextRecord = csvReader.readNext()) != null) 
					{ 
						String strValueRead =  nextRecord[2]+":"+nextRecord[3]+":"+nextRecord[4];
						objHMap.put(nextRecord[1], strValueRead/*nextRecord[2]*/);       
					} 
				}
				//else if block for html report
				else if ("html".equals(fileExtenstion)) 
				{
					objHMap.put("HtmlReportFile", objFile.getName());
				}
			}
		} 
		catch (Exception e)
		{ 
			//LOGGER.severe("Exception occured while reading the CSV file"+e.getMessage()); 
			return null;
		}

		return objHMap;
	} 
	
	public static void main(String [] args) throws ElementNotFoundException
	{
			SendMail obj = new SendMail();
			obj.sendMail("neelesh.parkhi@datamatics.com");
	}

}