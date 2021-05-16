package logging;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class MyHtmlFormatter extends Formatter
{

	int stepCounter = 0;
	String strCallerClass = "";
	WebDriver driver;
	String strMethod = "";
	ExtentTest testlogger;

	public MyHtmlFormatter(String strCallerClass, WebDriver driver, ExtentTest testlogger)
	{
		this.strCallerClass = strCallerClass;
		this.driver = driver;
		this.testlogger = testlogger;
	}



	public MyHtmlFormatter(String strCallerClass, ExtentTest testlogger)
	{
		this.strCallerClass = strCallerClass;
		this.testlogger = testlogger;
	}



	// this method is called for every log records
	public String format(LogRecord rec) {


		StringBuffer buf = new StringBuffer(1000);
		String s = trace(Thread.currentThread().getStackTrace());
		if(!strMethod.equalsIgnoreCase(s) && strMethod != null && s != null)
		{
			buf.append("<tr>\n");
			buf.append("\t<td>");
			buf.append("<b>");	
			buf.append("Method:");
			buf.append("<b>");
			buf.append("</td>");
			buf.append("\t<td>");
			buf.append("<b>");	
			buf.append(s);
			buf.append("<b>");	
			buf.append("</td>");

			buf.append("\t<td>");				
			buf.append("</td>");
			buf.append("</tr>\n");
			strMethod = s;
		}		


		buf.append("<tr>\n");
		buf.append("\t<td>");
		buf.append(calcDate(rec.getMillis()));
		buf.append("</td>");


		buf.append("\t<td>");
		buf.append(formatMessage(rec));
		buf.append("</td>");	

		// colorise any levels >= WARNING in red
		if (rec.getLevel().intValue() >= Level.SEVERE.intValue()) {
			buf.append("\t<td bgcolor=\"#FF0000\">");
			buf.append("<b>");			
			buf.append("<a href=\""+stepCounter+".jpg\">");
			driver.switchTo().window(driver.getWindowHandle());
			takeSnapShot(driver, "./logs/"+strCallerClass+"/"+stepCounter+".jpg");
			if(rec.getMessage().contains("Exception"))
			{
				testlogger.log(LogStatus.FAIL, "Exception Occured See attached ScreenShot Below :"+testlogger.addScreenCapture(addScreenshot(driver)));
				//Reporter.log(s);
			}
			else
			{
				testlogger.log(LogStatus.FAIL, formatMessage(rec)+" See attached ScreenShot Below :"+testlogger.addScreenCapture(addScreenshot(driver)));
			}
			
			stepCounter++;
			buf.append("FAIL");	
			buf.append("</a>");
			buf.append("</b>");

		}
		else if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
			buf.append("\t<td 	bgcolor=\"#FFD700\">");
			buf.append("<b>");
			buf.append("WARN");
			testlogger.log(LogStatus.WARNING, formatMessage(rec));
			buf.append("</b>");
		}
		else if (rec.getLevel().intValue() >= Level.INFO.intValue()) {
			buf.append("\t<td>");
			buf.append("<b>");

			testlogger.log(LogStatus.INFO, formatMessage(rec));

			buf.append("INFO");
			buf.append("</b>");
		}
		else if (rec.getLevel().intValue() >= Level.FINE.intValue() || rec.getLevel().intValue() >= Level.FINEST.intValue()) {
			buf.append("\t<td bgcolor=\"#ADFF2F\">");
			buf.append("<b>");

			testlogger.log(LogStatus.PASS, formatMessage(rec));

			buf.append("PASS");
			buf.append("</b>");
		}
		buf.append("</td>");
		buf.append("</tr>");

		return buf.toString();
	}

	private String calcDate(long millisecs) {
		//		SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		SimpleDateFormat date_format = new SimpleDateFormat("HH:mm:ss");
		Date resultdate = new Date(millisecs);
		return date_format.format(resultdate);
	}

	// this method is called just after the handler using this
	// formatter is created
	public String getHead(Handler h) {
		String x = null;
		try {
			x = "<!DOCTYPE html>\n<head>\n<style>\n"
					+ "table { width: 100% }\n"
					+ "th { font:bold 10pt Tahoma; }\n"
					+ "td { font:normal 10pt Tahoma; }\n"
					+ "h1 {font:normal 11pt Tahoma;}\n"
					+ "</style>\n"
					+ "</head>\n"
					+ "<body>\n"
					+ "<h1><b><p style='text-align: left'>"+ strCallerClass+ "</b>" 
					+ "<span style=\"float:right;\">"+ (new Date()) + "</span></p></h1>\n"
					+ "<table border=\"1\" cellpadding=\"5\" cellspacing=\"2\">\n"
					+ "<tr align=\"left\">\n"
					+ "\t<th style=\"width:8%\">Time</th>\n"
					+ "\t<th style=\"width:82%\">Log Message</th>\n"
					+ "\t<th style=\"width:10%\">ScreenShot</th>\n"
					+ "</tr>\n";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return x;
	}

	// this method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h) {
		return "</table>\n</body>\n</html>";
	}


	private void takescreenshot()
	{
		try {
			Robot robot = new Robot();
			String format = "jpg";         

			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
			ImageIO.write(screenFullImage, format, new File("./logs/"+strCallerClass+"/"+stepCounter+".jpg"));

		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	public void takeSnapShot(WebDriver driver,String fileWithPath) {

		try {
			//			//Convert web driver object to TakeScreenshot
			//			TakesScreenshot scrShot =((TakesScreenshot)driver);
			//
			//			//Call getScreenshotAs method to create image files
			//			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);



			Screenshot fpScreenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
			ImageIO.write(fpScreenshot.getImage(),"JPEG",new File(fileWithPath));



			//			//Move image file to new destination
			//			File DestFile=new File(fileWithPath);
			//
			//			//Copy file at destinations
			//			FileUtils.copyFile(SrcFile, DestFile);

		} catch (Exception e) {

			takescreenshot();
		}
	}

	public  String trace(StackTraceElement e[]) {
		for (int i = 0; i < e.length; i++) {
			if(e[i].getClassName().contains("functionalLibrary."))
			{
				return (e[i].getMethodName());
			}

		}

		return null;		
	}


	@SuppressWarnings("resource")
	public static String addScreenshot(WebDriver driver)
	{
		File SrcFile = null;
		TakesScreenshot scrShot =((TakesScreenshot) driver );	
		SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		String encodedBase64 = null;
		FileInputStream fileInputStreamReader = null;
		try 
		{
			fileInputStreamReader = new FileInputStream(SrcFile);
			byte[] bytes = new byte[(int)SrcFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = new String(Base64.getEncoder().encode(bytes));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return "data:image/png;base64,"+encodedBase64;
	}
}
